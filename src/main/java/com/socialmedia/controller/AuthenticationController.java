package com.socialmedia.controller;

import com.socialmedia.dto.LoginUserDto;
import com.socialmedia.dto.RegisterUserDto;
import com.socialmedia.model.User;
import com.socialmedia.service.AuthenticationService;
import com.socialmedia.service.JwtService;
import com.socialmedia.response.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("api/v1/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String accessToken = jwtService.generateToken(authenticatedUser);
        String refreshToken = jwtService.generateRefreshToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(accessToken);
        loginResponse.setRefreshToken(refreshToken);
        loginResponse.setAccessExpiresIn(jwtService.getAccessExpirationTime());
        loginResponse.setRefreshExpiresIn(jwtService.getRefreshExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        UserDetails userDetails = authenticationService.loadUserByUsername(jwtService.extractUsername(refreshToken));

        if (jwtService.isTokenValid(refreshToken, userDetails)) {
            String newAccessToken = jwtService.generateToken(userDetails);
            LoginResponse loginResponse = new LoginResponse();

            loginResponse.setAccessToken(newAccessToken);
            loginResponse.setRefreshToken(refreshToken);
            loginResponse.setAccessExpiresIn(jwtService.getAccessExpirationTime());
            loginResponse.setRefreshExpiresIn(jwtService.getRefreshExpirationTime());

            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
    }
}