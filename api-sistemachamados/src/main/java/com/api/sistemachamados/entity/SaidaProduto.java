package com.api.sistemachamados.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "saida_produto")
public class SaidaProduto extends Auditoria implements Serializable  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataSaida;

    @Column(nullable = false, precision = 19 , scale = 2)
    private BigDecimal valorTotalSaida;

    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Collection<Produto> produtos = new ArrayList<>();;
}

