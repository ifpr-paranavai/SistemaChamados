package com.api.sistemachamados.entity;

import com.api.sistemachamados.enums.SituacaoOsEnum;
import com.api.sistemachamados.enums.TipoAtendimentoEnum;
import com.api.sistemachamados.enums.TipoOrdemServicoEnum;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
    @ToString.Exclude
    private Collection<Servico> servicos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Equipamento equipamento;
}

