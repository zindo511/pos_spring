package com.vn.pos.service;

import com.nimbusds.jose.JOSEException;
import com.vn.pos.dto.AuthenticationDTO.AuthenticationRequest;
import com.vn.pos.dto.AuthenticationDTO.AuthenticationResponse;
import com.vn.pos.dto.IntrospectDTO.IntrospectRequest;
import com.vn.pos.dto.IntrospectDTO.IntrospectResponse;

import java.text.ParseException;


public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
}
