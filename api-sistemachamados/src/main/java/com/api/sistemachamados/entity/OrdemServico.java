package com.api.sistemachamados.entity;

import com.api.sistemachamados.enums.SituacaoOsEnum;
import com.api.sistemachamados.enums.TipoAtendimentoEnum;
import com.api.sistemachamados.enums.TipoOrdemServicoEnum;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "ordem_servico")
public class OrdemServico extends Auditoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate data;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Usuario usuario;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoOrdemServicoEnum tipoOrdemServico;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SituacaoOsEnum situacaoOs;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoAtendimentoEnum tipoAtendimento;

    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Collection<Servico> servicos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Equipamento equipamento;

    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Collection<Produto> produtos = new ArrayList<>();
}

