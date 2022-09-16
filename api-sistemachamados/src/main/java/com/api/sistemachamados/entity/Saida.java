package com.api.sistemachamados.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "saida")
@EqualsAndHashCode(callSuper = true)
public class Saida extends Auditoria implements Serializable  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataSaida;

    @Column(nullable = false, precision = 19 , scale = 2)
    private BigDecimal valorTotalSaida;

    @Column(nullable = false)
    private Boolean estaAberto;

    public Saida(OrdemServico ordemServico){
        this.dataSaida = ordemServico.getData();
    }
}

