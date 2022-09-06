package com.api.sistemachamados.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "entrada_produto")
public class EntradaProduto extends Auditoria implements Serializable  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    // TODO: 04/09/2022 Remover data entrada dinamica?
    @Column(nullable = false, unique = true)
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate dataEntrada = LocalDate.from(LocalDateTime.now());

    @Column(nullable = false, precision = 19 , scale = 2)
    private BigDecimal valorTotalEntrada;

    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Collection<Produto> produtos = new ArrayList<>();

    public EntradaProduto (List<Produto> produtos, BigDecimal valorTotalEntrada){
        this.produtos = produtos;
        this.valorTotalEntrada = valorTotalEntrada;
    }
}

