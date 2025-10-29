package com.vn.pos.service.Impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.vn.pos.dto.AuthenticationDTO.AuthenticationRequest;
import com.vn.pos.dto.AuthenticationDTO.AuthenticationResponse;
import com.vn.pos.dto.IntrospectDTO.IntrospectRequest;
import com.vn.pos.dto.IntrospectDTO.IntrospectResponse;
import com.vn.pos.exception.Custom.InvalidOperationException;
import com.vn.pos.exception.Custom.ResourceNotFoundException;
import com.vn.pos.model.Employee;
import com.vn.pos.repository.EmployeeRepository;
import com.vn.pos.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {
    EmployeeRepository employeeRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY = "d8136d8524b6ff15699bb9e2f05a33b355cce61dc09ed2688e938858b142aa2d";

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        Employee employee = employeeRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Username", "username", request.getUsername()));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), employee.getPassword());
        if (!authenticated) {
            throw new InvalidOperationException("Wrong password");
        }

        String token = generateToken(request.getUsername());

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified = signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .valid(verified && expiryTime.after(new Date()))
                .build();
    }


    private String generateToken(String username) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("zio")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("customClaim", "Custom")
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot sign JWS object", e);
            throw new RuntimeException(e);
        }
    }
}
