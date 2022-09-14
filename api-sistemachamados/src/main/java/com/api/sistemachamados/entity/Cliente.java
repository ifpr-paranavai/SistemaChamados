package com.api.sistemachamados.entity;

import com.api.sistemachamados.enums.TipoPessoaEnum;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Table(name = "cliente")
@EqualsAndHashCode(callSuper = true)
public class Cliente extends Auditoria implements Serializable {

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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoPessoaEnum tipoPessoa;

    @Column(unique = true, length = 15)
    private String cpfCnpj;

    @Column()
    private String contato1;

    @Column()
    private String contato2;

    @ManyToOne
    private Cidade cidade;

    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Collection<Equipamento> equipamentos = new ArrayList<>();
}

