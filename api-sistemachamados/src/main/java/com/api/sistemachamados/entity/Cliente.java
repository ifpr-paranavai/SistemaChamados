package com.api.sistemachamados.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
public class Cliente extends Auditoria implements Serializable  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String endereco;

    @Column()
    private Integer numero;

    @Column(unique = true, length = 11)
    private Integer CPF;

    @Column(unique = true, length = 14)
    private Integer CNPJ;

    @Column()
    private String contato1;

    @Column()
    private String contato2;

    @ManyToOne
    private Cidade cidade;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Equipamento> equipamento;

}

