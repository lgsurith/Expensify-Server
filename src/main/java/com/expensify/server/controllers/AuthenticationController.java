package com.expensify.server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensify.server.dtos.LoginUserDto;
import com.expensify.server.dtos.RegisterUserDto;
import com.expensify.server.model.User;
import com.expensify.server.responses.LoginResponse;
import com.expensify.server.service.AuthenticationService;
import com.expensify.server.service.JWTService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
     private final JWTService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JWTService jwtService , AuthenticationService authenticationService){
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto){
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse LoginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(LoginResponse);

    }
}
