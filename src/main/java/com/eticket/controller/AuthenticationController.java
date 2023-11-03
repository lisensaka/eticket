package com.eticket.controller;

import com.eticket.exceptions.UniqueEmailValidationException;
import com.eticket.models.dto.request.SignUpRequest;
import com.eticket.models.dto.request.SigninRequest;
import com.eticket.models.dto.response.JwtAuthenticationResponse;
import com.eticket.service.interfaces.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@Valid @RequestBody SignUpRequest request) throws UniqueEmailValidationException {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}
