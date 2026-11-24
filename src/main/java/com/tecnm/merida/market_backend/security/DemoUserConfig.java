package com.tecnm.merida.market_backend.security;

import com.tecnm.merida.market_backend.persistence.crud.UsuarioCrudRepository;
import com.tecnm.merida.market_backend.persistence.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@ConditionalOnProperty(name = "AUTH_DEMO_PASSWORD")
public class DemoUserConfig {

    @Bean
    ApplicationRunner createDemoUser(UsuarioCrudRepository users, BCryptPasswordEncoder encoder,
                                     @Value("${AUTH_DEMO_PASSWORD}") String password) {
        return args -> {
            if (password.isBlank()) {
                throw new IllegalArgumentException("AUTH_DEMO_PASSWORD no puede estar vacía");
            }
            if (!users.existsById("demo")) {
                users.save(new Usuario("demo", encoder.encode(password)));
            }
        };
    }
}
