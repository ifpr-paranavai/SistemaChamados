package com.api.sistemachamados.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "servico")
@EqualsAndHashCode(callSuper = true)
public class Servico extends Auditoria implements Serializable  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(nullable = false,unique = true)
    private String nome;

    @Lob
    @Column()
    private String observacao;
}

