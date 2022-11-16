package com.api.sistemachamados.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "equipamento")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Equipamento extends Auditoria implements Serializable {

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

    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

}

