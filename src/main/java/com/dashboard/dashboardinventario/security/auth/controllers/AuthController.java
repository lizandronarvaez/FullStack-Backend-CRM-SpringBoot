package com.dashboard.dashboardinventario.security.auth.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.dashboardinventario.security.auth.models.dto.UserDto;
import com.dashboard.dashboardinventario.security.auth.services.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:5174/",
        "https://app-protein-shop-react.vercel.app/","https://crm-frontend-alpha.vercel.app/"  })
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {

        try {
            return new ResponseEntity<>(authService.registerUser(userDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto userDto) {

        try {
            return new ResponseEntity<>(authService.loginUser(userDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
