package com.hoang.indentity_service.configuration;

import com.hoang.indentity_service.entity.Roles;
import com.hoang.indentity_service.entity.UserEntity;
import com.hoang.indentity_service.enums.Role;
import com.hoang.indentity_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()){
                Set<Roles> roles = new HashSet<>();
                roles.add(new Roles(Role.ROLE_ADMIN.getCodeRole(), Role.ROLE_ADMIN.getNameRole()));
                UserEntity user = UserEntity.builder()
                        .username("admin")
                        .roles(roles)
                        .password(passwordEncoder.encode("<PASSWORD>"))
                        .build();
                userRepository.save(user);
                log.warn("Create default user: admin");
            }
        };
    }
}
