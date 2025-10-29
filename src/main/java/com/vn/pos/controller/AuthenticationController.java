package com.vn.pos.controller;

import com.nimbusds.jose.JOSEException;
import com.vn.pos.dto.AuthenticationDTO.AuthenticationRequest;
import com.vn.pos.dto.AuthenticationDTO.AuthenticationResponse;
import com.vn.pos.dto.IntrospectDTO.IntrospectRequest;
import com.vn.pos.dto.IntrospectDTO.IntrospectResponse;
import com.vn.pos.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;
    @PostMapping("/token")
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/introspect")
    ResponseEntity<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        IntrospectResponse response = authenticationService.introspect(request);
        return ResponseEntity.ok(response);
    }
}
