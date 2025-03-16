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

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()){
                UserEntity user = UserEntity.builder()
                        .username("admin")
                        .role(new Roles(Role.ROLE_ADMIN.getCodeRole(), Role.ROLE_ADMIN.getNameRole()))
                        .password(passwordEncoder.encode("<PASSWORD>"))
                        .build();
                userRepository.save(user);
                log.warn("Create default user: admin");
            }
        };
    }
}
