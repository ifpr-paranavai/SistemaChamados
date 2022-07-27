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
public enum SituacaoOsEnum {
    ABERTO("Aberto"),
    FECHADO("Fechado"),
    ANDAMENTO("Andamento");

    private String situacao;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    static SituacaoOsEnum findValue(@JsonProperty("situacao") String situacao) {
        return Arrays.stream(SituacaoOsEnum.values()).filter(pt -> pt.situacao.equals(situacao)).findFirst().get();
    }

}

