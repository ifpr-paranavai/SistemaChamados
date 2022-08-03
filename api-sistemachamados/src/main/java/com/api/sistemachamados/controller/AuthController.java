package com.api.sistemachamados.controller;

import com.api.sistemachamados.dto.ApiResponseDTO;
import com.api.sistemachamados.dto.LoginDTO;
import com.api.sistemachamados.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@AllArgsConstructor
public class AuthController {
    final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<Object>> auth(@RequestBody @Validated LoginDTO loginDTO) {
        var apiResponseDTO = authService.login(loginDTO);
        return ResponseEntity.status(apiResponseDTO.getStatus().value()).body(apiResponseDTO);
    }
}
