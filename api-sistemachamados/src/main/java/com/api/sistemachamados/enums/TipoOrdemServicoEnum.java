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
public enum TipoOrdemServicoEnum {
    FAN_COIL("FAN COIL"),
    SELF("SELF"),
    SPLIT("SPLIT");

    private String tipoOrdemServico;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    static TipoOrdemServicoEnum findValue(@JsonProperty("tipoOrdemServico") String tipoOrdemServico) {
        return Arrays.stream(TipoOrdemServicoEnum.values()).filter(pt -> pt.tipoOrdemServico.equals(tipoOrdemServico)).findFirst().get();
    }

}

