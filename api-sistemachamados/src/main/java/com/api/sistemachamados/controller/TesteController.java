package com.api.sistemachamados.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/test")
@PreAuthorize("hasPermission('null', {'ROLE_USER', 'PERM_PROCURACAO'})")
public class TesteController {

    @GetMapping
    public ResponseEntity<String> auth() {
        System.out.println("Teste.");
        return ResponseEntity.ok("Testado");
    }

    public void validUserRequest(String role) {

    }
}
