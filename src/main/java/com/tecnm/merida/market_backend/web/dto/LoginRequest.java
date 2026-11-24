package com.tecnm.merida.market_backend.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequest(String username, @Schema(format = "password") String password) {
}
