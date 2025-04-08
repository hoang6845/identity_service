package com.hoang.indentity_service.service;

import com.hoang.indentity_service.dto.request.AuthenticationRequest;
import com.hoang.indentity_service.dto.request.IntrospectRequest;
import com.hoang.indentity_service.dto.request.LogoutRequest;
import com.hoang.indentity_service.dto.request.RefreshRequest;
import com.hoang.indentity_service.dto.response.AuthenticationReponse;
import com.hoang.indentity_service.dto.response.IntrospectResponse;
import com.hoang.indentity_service.entity.InvalidatedToken;
import com.hoang.indentity_service.entity.UserEntity;
import com.hoang.indentity_service.exception.AppException;
import com.hoang.indentity_service.exception.ErrorCode;
import com.hoang.indentity_service.repository.InvalidatedTokenRepository;
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

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;
    //tạo ngẫu nhiên ở : https://generate-random.org/encryption-key-generator?count=1&bytes=32&cipher=aes-256-cbc&string=&password=
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long validDuration;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long refreshDuration;

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
                .jwtID(UUID.randomUUID().toString())
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(validDuration, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
        return jwsObject.serialize();
    }

    public void logout(LogoutRequest request ) throws ParseException, JOSEException {
        try{
            SignedJWT signedJWT = verifyToken(request.getToken(), true);

            String jwtid = signedJWT.getJWTClaimsSet().getJWTID();
            Date expirationTime = new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(refreshDuration, ChronoUnit.SECONDS).toEpochMilli());

            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jwtid)
                    .exp(expirationTime)
                    .build();

            invalidatedTokenRepository.save(invalidatedToken);
        }catch (AppException exception){
            log.info(exception.getMessage());
        }
    }

    public AuthenticationReponse refreshToken(RefreshRequest token) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(token.getToken(), true);

        String jwtid = signedJWT.getJWTClaimsSet().getJWTID();
        Date expirationTome = signedJWT.getJWTClaimsSet().getExpirationTime();

        invalidatedTokenRepository.save(InvalidatedToken.builder()
                .id(jwtid)
                .exp(expirationTome)
                .build());

        String username = signedJWT.getJWTClaimsSet().getSubject();
        String newToken = generateJWToken(userRepository.findByUsername(username).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED)));
        return AuthenticationReponse.builder()
                .token(newToken)
                .authenticated(true)
                .build();
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
        boolean isValid = true;
        try {
            verifyToken(token, false);
        }catch (Exception e) {
            isValid = false;
        }
        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expirationTime = isRefresh?
            new Date(signedJWT.getJWTClaimsSet()
                    .getIssueTime()
                    .toInstant()
                    .plus(refreshDuration, ChronoUnit.SECONDS)
                    .toEpochMilli())
        :signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean verified = signedJWT.verify(verifier);
        if (!(verified&&expirationTime.after((new Date())))){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }

    public void removeExpiredTokens(){
        List<InvalidatedToken> listToken =  invalidatedTokenRepository.findAll();
        List<String> listTokenId = listToken.stream().filter(token -> token.getExp().before(new Date())).map(InvalidatedToken::getId).toList();
        for (String id: listTokenId){
            invalidatedTokenRepository.deleteById(id);
        }
    }
}
