package com.eticket.service.interfaces.implementation;

import com.eticket.exceptions.UniqueEmailValidationException;
import com.eticket.models.Role;
import com.eticket.models.User;
import com.eticket.models.dto.request.SignUpRequest;
import com.eticket.models.dto.request.SigninRequest;
import com.eticket.models.dto.response.JwtAuthenticationResponse;
import com.eticket.repository.IUserRepository;
import com.eticket.service.interfaces.AuthenticationService;
import com.eticket.service.interfaces.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) throws UniqueEmailValidationException {
        if (getUserByEmailEqualsIgnoreCase(request.getEmail()).isEmpty()) {
            var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                    .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.valueOf(request.getRole())).build();
            userRepository.save(user);
            //making users password null in order to not be included
            var jwt = jwtService.generateToken(user);
            return JwtAuthenticationResponse.builder().token(jwt).build();
        } else {
            throw new UniqueEmailValidationException(String.format("Email:'%s', you provided exists on DB, please choose new email and try again!", request.getEmail()));
        }
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = getUserByEmail(request);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    private User getUserByEmail(SigninRequest request) {
        return getUserByEmailEqualsIgnoreCase(request.getEmail())
                .orElseThrow(() -> new NoSuchElementException(String.format("User with email: %s, was not found", request.getEmail())));
    }

    private Optional<User> getUserByEmailEqualsIgnoreCase(String email) {
        return userRepository.findUserByEmailEqualsIgnoreCase(email);
    }
}