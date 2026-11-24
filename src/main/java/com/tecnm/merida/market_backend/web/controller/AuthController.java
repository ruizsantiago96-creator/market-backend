package com.tecnm.merida.market_backend.web.controller;

import com.tecnm.merida.market_backend.persistence.crud.UsuarioCrudRepository;
import com.tecnm.merida.market_backend.security.JwtUtil;
import com.tecnm.merida.market_backend.web.dto.LoginRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
public class AuthController {

    private final UsuarioCrudRepository users;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioCrudRepository users, BCryptPasswordEncoder encoder, JwtUtil jwtUtil) {
        this.users = users;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/auth/login")
    @SecurityRequirements
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String username = request.username();
        String password = request.password();
        if (username == null || username.isBlank() || password == null || password.isBlank()
                || password.getBytes(UTF_8).length > 72) {
            return ResponseEntity.badRequest().build();
        }

        return users.findById(username)
                .filter(user -> encoder.matches(password, user.getPasswordHash()))
                .map(user -> ResponseEntity.ok()
                        .cacheControl(CacheControl.noStore())
                        .body(new LoginResponse(jwtUtil.generateToken(username), "Bearer")))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    public record LoginResponse(String token, String tokenType) {
    }
}
