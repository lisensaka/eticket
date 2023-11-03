package com.eticket.service;

import com.eticket.email.EmailSender;
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
    private final EmailSender emailSender;

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
        if (paymentRequestDto.getEmail() != null) {
            sendConfirmationEmailToTheUser(paymentRequestDto);
        }
    }

    private void sendConfirmationEmailToTheUser(PaymentRequestDto paymentRequestDto) {
        if ((!paymentRequestDto.getEmail().trim().isEmpty())){
            emailSender.send(paymentRequestDto.getEmail(), buildEmail(paymentRequestDto));
        }
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


    private String buildEmail(PaymentRequestDto paymentRequestDto) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirmation Payment</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + paymentRequestDto.getEmail() + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for using our e-Ticket app for paying your fines . Please check below details of the payment  <ul><li> Ticket Serial Number: "+paymentRequestDto.getSerialNumber() +"</li><li>Total Payment: "+paymentRequestDto.getTotalPayment() +"</li></ul>  \n Thank you! <p>Have a nice day</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}
