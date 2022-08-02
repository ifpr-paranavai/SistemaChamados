package com.api.sistemachamados.entity;

import com.api.sistemachamados.enums.SituacaoOsEnum;
import com.api.sistemachamados.enums.TipoAtendimentoEnum;
import com.api.sistemachamados.enums.TipoOrdemServicoEnum;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ordem_servico")
public class OrdemServico extends Auditoria implements Serializable  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(columnDefinition = "DATE")
    private Date data;

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

    @ManyToOne
    private Produto produto;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoAtendimentoEnum tipoAtendimento;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Servico> servico;

    @ManyToOne(fetch = FetchType.LAZY)
    private Equipamento equipamento;
}

