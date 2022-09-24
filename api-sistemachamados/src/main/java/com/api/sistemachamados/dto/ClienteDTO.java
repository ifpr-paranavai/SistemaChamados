package com.api.sistemachamados.dto;

import com.api.sistemachamados.entity.Cidade;
import com.api.sistemachamados.entity.Equipamento;
import com.api.sistemachamados.enums.TipoPessoaEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;

@Data
public class ClienteDTO {

    @Schema(
        example = "João da Silva",
        description = "Identificação do nome do cliente",
        required = true
    )
    @JsonProperty("nome")
    @NotEmpty(message = "nome.null")
    private String nome;

    @Schema(
        example = "Rua Paulo Roberto",
        description = "Endereço do cliente",
        required = true
    )
    @JsonProperty("endereco")
    @NotEmpty(message = "endereco.null")
    private String endereco;

    @Schema(
        example = "450",
        description = "Número do endereço",
        required = true
    )
    @JsonProperty("numero")
    private Integer numero;

    @Schema(
        example = "Física",
        description = "Tipo da Pessoa",
        required = true
    )
    @JsonProperty("tipoPessoa")
    @Enumerated(EnumType.STRING)
    private TipoPessoaEnum tipoPessoa;

    @Schema(
        example = "111.111.111-11",
        description = "Cpf ou Cppj"
    )
    @JsonProperty("cpfCnpj")
    @NotEmpty(message = "cpf.cnpj.null")
    private String cpfCnpj;

    @Schema(
        example = "41 3423-1111",
        description = "Número de contato"
    )
    @JsonProperty("contato1")
    private String contato1;

    @Schema(
        example = "41 3423-1112",
        description = "Número de secundário"
    )
    @JsonProperty("contato2")
    private String contato2;

    @Schema(
        example = "Objeto",
        description = "Objeto Cidade"
    )
    @JsonProperty("cidade")
    private Cidade cidade;

    @Schema(
        example = "[1,2,3]",
        description = "Objeto de Equipamentos"
    )
    @JsonProperty("equipamentos")
    private Collection<Equipamento> equipamentos = new ArrayList<>();
}
