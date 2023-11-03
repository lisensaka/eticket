package com.eticket.service;

import com.eticket.exceptions.CustomException;
import com.eticket.models.PaymentEntity;
import com.eticket.models.TicketDetailsEntity;
import com.eticket.models.dto.request.PaymentRequestDto;
import com.eticket.models.dto.response.PaymentResponseInformationForTicket;
import com.eticket.repository.IPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static com.eticket.configurations.constants.ConstantsConfiguration.MAX_NUMBER_OF_DAYS_FOR_DISCOUNT;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final IPaymentRepository iPaymentRepository;
    private final TicketDetailsService ticketDetailsService;


    /**
     * here just displaying the payment info before the user proceeds with the payment
     */
    public PaymentResponseInformationForTicket getPaymentDetailsForTicketBySerialNumber(String serialNumber) {

        TicketDetailsEntity ticketDetailEntityBySerialNumber = ticketDetailsService.getTicketBySerialNumberEqualsIgnoreCase(serialNumber);

        PaymentResponseInformationForTicket responseInformationForTicket = new PaymentResponseInformationForTicket();
        responseInformationForTicket.setSerialNumber(ticketDetailEntityBySerialNumber.getSerialNumber());
        responseInformationForTicket.setCommissionRate(checkIfTicketDateIsWithinLast15Days(ticketDetailEntityBySerialNumber.getTicketDate()));
        responseInformationForTicket.setTotalCalculatedPaymentWithCommission(calculateTotalPaymentBasedOnCommissionRate(responseInformationForTicket.getCommissionRate(), ticketDetailEntityBySerialNumber));
        responseInformationForTicket.setDescription(
                responseInformationForTicket.getCommissionRate() == 50 ?
                        String.format("Congratulation, you got -%d  discount on this Ticket payment!", responseInformationForTicket.getCommissionRate()) :
                        String.format("Hurry Up! Your Ticket has a commission rate of %d  per day! ", responseInformationForTicket.getCommissionRate())
        );
        return responseInformationForTicket;
    }

    public void createPaymentForTicketDetails(PaymentRequestDto paymentRequestDto) {
        PaymentEntity paymentEntity = new PaymentEntity();

        TicketDetailsEntity ticketDetailEntityBySerialNumber = ticketDetailsService.getTicketBySerialNumberEqualsIgnoreCase(paymentRequestDto.getSerialNumber());
        checkIfTicketDetailEntityIsPaid(ticketDetailEntityBySerialNumber);

        int commissionRate = checkIfTicketDateIsWithinLast15Days(ticketDetailEntityBySerialNumber.getTicketDate());
        double totalAmountCalculatedBasedOnCommissionRate = calculateTotalPaymentBasedOnCommissionRate(commissionRate, ticketDetailEntityBySerialNumber);
        checkIncomingTotalPaymentAmount(paymentRequestDto, ticketDetailEntityBySerialNumber);
        addPaymentDetailsToPaymentEntity(paymentRequestDto, paymentEntity, ticketDetailEntityBySerialNumber, commissionRate, totalAmountCalculatedBasedOnCommissionRate);
        ticketDetailEntityBySerialNumber.setIsTicketDetailPaid(true);
        iPaymentRepository.save(paymentEntity);
    }

    private void checkIfTicketDetailEntityIsPaid(TicketDetailsEntity ticketDetailsEntity) {
        if (ticketDetailsEntity.getIsTicketDetailPaid()) {
            throw new CustomException(String.format("Ticket with serial number: %s, have been paid!", ticketDetailsEntity.getSerialNumber()), HttpStatus.BAD_REQUEST);
        }
    }


    private double calculateTotalPaymentBasedOnCommissionRate(int commission, TicketDetailsEntity ticketDetailEntityBySerialNumber) {
        if (commission == 50) {
            double calculatedAmount = (ticketDetailEntityBySerialNumber.getAmount() * commission) / 100;
            ticketDetailEntityBySerialNumber.setCommissionAmount(calculatedAmount);
            return calculatedAmount;
        } else {
            long daysBetween = getDaysBetween(ticketDetailEntityBySerialNumber.getTicketDate());
            double calculatedPrice = (ticketDetailEntityBySerialNumber.getAmount() * commission) / 100;
            double calculatedAmount = ticketDetailEntityBySerialNumber.getAmount() + calculatedPrice * daysBetween;
            ticketDetailEntityBySerialNumber.setCommissionAmount(calculatedAmount);
            return calculatedAmount;
        }
    }

    private int checkIfTicketDateIsWithinLast15Days(ZonedDateTime ticketDate) {
        long daysBetween = getDaysBetween(ticketDate);

        if (daysBetween <= MAX_NUMBER_OF_DAYS_FOR_DISCOUNT)
            return 50;
        return 2;
    }

    private long getDaysBetween(ZonedDateTime ticketDate) {
        return ChronoUnit.DAYS.between(ticketDate, convertLocalDateTimeToZonedDateTime());
    }

    private ZonedDateTime convertLocalDateTimeToZonedDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        return localDateTime.atZone(ZoneId.systemDefault());
    }

    private static void checkIncomingTotalPaymentAmount(PaymentRequestDto paymentRequestDto, TicketDetailsEntity ticketDetailEntityBySerialNumber) {
        if (paymentRequestDto.getTotalPayment() != ticketDetailEntityBySerialNumber.getCommissionAmount()) {
            throw new CustomException("System does not allow the payment to be succeed if the total amount is greater or less than required amount!", HttpStatus.BAD_REQUEST);
        }
    }

    private static void addPaymentDetailsToPaymentEntity(PaymentRequestDto paymentRequestDto, PaymentEntity paymentEntity, TicketDetailsEntity ticketDetailEntityBySerialNumber, int commissionRate, double totalAmountCalculatedBasedOnCommissionRate) {
        paymentEntity.setTotalPayment(paymentRequestDto.getTotalPayment());
        paymentEntity.setTicketDetailsEntity(ticketDetailEntityBySerialNumber);
        paymentEntity.setPaymentDate(LocalDateTime.now());
        paymentEntity.setCommissionRate(commissionRate);
        paymentEntity.setTotalCalculatedPaymentWithCommission(totalAmountCalculatedBasedOnCommissionRate);
    }

}
