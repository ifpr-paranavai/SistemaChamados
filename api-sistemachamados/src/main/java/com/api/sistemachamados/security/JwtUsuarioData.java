package com.api.sistemachamados.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtUsuarioData {
    private Long id;
    private String email;
    private String nome;
    private List<String> roles;
}
