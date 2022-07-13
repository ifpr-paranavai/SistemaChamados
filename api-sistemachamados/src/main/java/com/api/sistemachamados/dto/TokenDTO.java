package com.api.sistemachamados.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDTO {
    String type;
    String token;
}
