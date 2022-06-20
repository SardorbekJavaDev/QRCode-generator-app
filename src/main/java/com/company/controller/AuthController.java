package com.company.controller;

import com.company.dto.RegistrationDTO;
import com.company.dto.request.AuthRequestDTO;
import com.company.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@Api(tags = "Auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @ApiOperation(value = "user login", notes = "Method used for user Login")
    @PostMapping("/login")
    public ResponseEntity<?> create(@RequestBody @Valid AuthRequestDTO dto) {
        log.info("Authorization: {}{}", dto, AuthService.class);
        return ResponseEntity.ok(authService.login(dto));
    }

    @ApiOperation(value = "user registration", notes = "Method used for user Registration")
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody @Valid RegistrationDTO dto) {
        log.info("Registration: {}{}", dto, AuthService.class);
        authService.registration(dto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "user verification", notes = "Method used for user verification")
    @GetMapping("/verification/{jwt}")
    public ResponseEntity<?> verification(@PathVariable("jwt") String jwt) {
        if (jwt.isBlank()) {
            log.warn("verification:  {}{}", jwt.isBlank(), AuthService.class);
        }
        authService.verification(jwt);
        return ResponseEntity.ok().build();
    }
}
