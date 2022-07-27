package com.api.sistemachamados.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.AUTO;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "equipamento")
public class Equipamento extends Auditoria implements Serializable  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(nullable = false)
    private String nomeEquipamento;

    @Column(nullable = false)
    private String tensao;

    @Column()
    private String amperagem;

    @Column(nullable = false, unique = true)
    private String numeroSerie;

    @Column()
    private String tag;

    @Lob
    @Column()
    private String especificacaoTecnica;

    @ManyToOne
    private Marca marca;

}

