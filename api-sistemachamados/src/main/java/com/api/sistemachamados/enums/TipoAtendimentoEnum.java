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
public enum TipoAtendimentoEnum {
    CHAMADO("Chamado"),
    PREVENTIVA("Preventiva"),
    CORRETIVA("Corretiva"),
    GARANTIA("Garantia"),
    VISITA_TECNICA("Visita Técnica");

    private String tipoAtendimento;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    static TipoAtendimentoEnum findValue(@JsonProperty("tipoAtendimento") String tipoAtendimento) {
        return Arrays.stream(TipoAtendimentoEnum.values()).filter(pt -> pt.tipoAtendimento.equals(tipoAtendimento)).findFirst().get();
    }

}

