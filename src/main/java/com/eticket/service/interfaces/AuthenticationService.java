package com.eticket.service.interfaces;

import com.eticket.exceptions.UniqueEmailValidationException;
import com.eticket.models.dto.request.SignUpRequest;
import com.eticket.models.dto.request.SigninRequest;
import com.eticket.models.dto.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request) throws UniqueEmailValidationException;

    JwtAuthenticationResponse signin(SigninRequest request);
}
