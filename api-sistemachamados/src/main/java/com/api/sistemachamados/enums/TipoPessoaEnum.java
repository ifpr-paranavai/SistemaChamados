package com.api.sistemachamados.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoPessoaEnum {
    FISICA("Física"),
    JURIDICA("Juridica");
    private String tipoPessoa;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    static TipoPessoaEnum findValue(@JsonProperty("tipoPessoa") String tipoPessoa) {
        return Arrays.stream(TipoPessoaEnum.values()).filter(pt -> pt.tipoPessoa.equals(tipoPessoa)).findFirst().get();
    }

}

