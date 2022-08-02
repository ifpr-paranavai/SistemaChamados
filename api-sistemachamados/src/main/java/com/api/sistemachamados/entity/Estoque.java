package com.api.sistemachamados.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import java.io.Serializable;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "estoque")
public class Estoque extends Auditoria implements Serializable  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(nullable = false,unique = true)
    private String nomeProduto;

    @Column()
    private BigDecimal valorCommpra;

    @Column()
    private Integer quantidadeAdiquirida;

    @ManyToOne
    private Produto produto;

}

