package com.hoang.indentity_service.service;

import com.hoang.indentity_service.dto.request.AuthenticationRequest;
import com.hoang.indentity_service.dto.request.IntrospectRequest;
import com.hoang.indentity_service.dto.response.AuthenticationReponse;
import com.hoang.indentity_service.dto.response.IntrospectResponse;
import com.hoang.indentity_service.entity.Roles;
import com.hoang.indentity_service.entity.UserEntity;
import com.hoang.indentity_service.exception.AppException;
import com.hoang.indentity_service.exception.ErrorCode;
import com.hoang.indentity_service.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Array;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;
    //tạo ngẫu nhiên ở : https://generate-random.org/encryption-key-generator?count=1&bytes=32&cipher=aes-256-cbc&string=&password=
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public AuthenticationReponse authenticate(AuthenticationRequest authenticationRequest) throws JOSEException {
        System.out.println(authenticationRequest.getUsername()+" "+authenticationRequest.getPassword());
        UserEntity userEntity = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), userEntity.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String token = generateJWToken(userEntity);
        return AuthenticationReponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private String generateJWToken(UserEntity user) throws JOSEException {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername()) //authentication.name
                .issuer("indentity-service")
                .claim("scope", buildScope(user)) //authentication.authorities
                .claim("Id", user.getId())
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
        return jwsObject.serialize();
    }

    public String[] buildScope(UserEntity user) {
        Set<String> scope = new HashSet<>();
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                scope.add("ROLE_"+role.getCodeRole());
                role.getPermissions().forEach(permission -> {
                    System.out.println(permission.getCodePermission());
                    scope.add(permission.getCodePermission());
                });
            });
        }
        return scope.toArray(String[]::new);
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean verified = signedJWT.verify(verifier);
        return IntrospectResponse.builder()
                .valid(verified && expirationDate.after(new Date()))
                .build();
    }
}
