package com.hoang.indentity_service.configuration;

import com.hoang.indentity_service.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenCleanUpScheduler {
    private final AuthenticationService authenticationService;

    @Scheduled(cron = "0 0 0,12 * * *")
    public void tokenCleanUp(){
        System.out.println("Token clean up started");
        authenticationService.removeExpiredTokens();
        System.out.println("Token clean up completed");
    }
}
