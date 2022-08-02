package com.api.sistemachamados.entity;

import com.api.sistemachamados.enums.SituacaoOsEnum;
import com.api.sistemachamados.enums.TipoAtendimentoEnum;
import com.api.sistemachamados.enums.TipoOrdemServicoEnum;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ordem_servico_item")
public class OrdemServicoItem extends Auditoria implements Serializable  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column()
    @Lob
    private String observacao;

    @Column(nullable = false)
    private String tensaoGeralRS;

    @Column(nullable = false)
    private String tensaoGeralST;

    @Column(nullable = false)
    private String tensaoGeralRT;

    @Column(nullable = false)
    private String correnteA1;

    @Column(nullable = false)
    private String correnteA2;

    @Column(nullable = false)
    private String correnteA3;

    @Column(nullable = false)
    private String motorEvaporadorA1A2A3;

    @Column()
    private String alimentacaoComando24;

    @Column()
    private String compressorRSRTST;

    @Column()
    private String compressorA1A2A3;

    @Column()
    private String aberturaValvulaAguaGelada;

    @Column()
    private String motorCondensadorA1A2A3;

    @Column()
    private String aquecimentoResistencia;

    @Column(nullable = false)
    private String umidificadorRSRTST;

    @Column(nullable = false)
    private String umidificadorA1A2A3;

    @Column()
    private String temperaturaEntradaAguaGelada;

    @Column()
    private String temperaturaSaidaAguaGelada;

    @Column()
    private String diferencialTemperaturaEntradaSaida;

    @Column(nullable = false)
    private String condensadorDryRSRTST;

    @Column(nullable = false)
    private String vazaoAr;

    @Column(nullable = false)
    private String temperaturaRetorno;

    @Column(nullable = false)
    private String umidadeRelativaRetorno;

    @Column(nullable = false)
    private String temperaturaInsuflacao;

    @Column(nullable = false)
    private String diferencialEntradaSaidaAr;

    @Column()
    private String temperaturaLinhaLiquido;

    @Column()
    private String temperaturaLinhaEntradaSuccao;

    @Column()
    private String pressaoBaixa;

    @Column()
    private String pressAlta;

    @Column()
    private String superAquecimento;

    @Column()
    private String subResfriamento;

    @Column(nullable = false)
    private String setPointResfriamento;

    @Column(nullable = false)
    private String setPointUmidade;

    @Column()
    private String aberturaValvulaG;

    @Column()
    private String temperaturaEntradaAguaCondensada;

    @Column()
    private String temperaturaSaidaAguaCondensada;

    @Column()
    private String diferencialFiltroSecador;

    @Column()
    private String filtroY;

    @Column()
    private String verificarVazamentoAguaGelada;

    @Column()
    private String verificarPressaoDiferencialFiltroSujo;

    @Column()
    private String verificarPressaoDiferencialFiltroAr;

    @Column()
    private String verificarComunicacaoIHM;

    @Column()
    private String verificarOffSetSensorTemperaturaUmidade;

    @Column()
    private String verificarValvulaBloqueioAguaGelada;

    @Column()
    private String verificarAlimentacaoDrenoUmidificador;

    @Column(nullable = false)
    private String testeDampers;

    @Column(nullable = false)
    private String averiguarHistoricoEventosIHM;

    @Column(nullable = false)
    private String verificarIsolamentoTermicasAcustico;

    @Column()
    private String verificarEliminarPontosCorrosao;

    @Column(nullable = false)
    private String verificarSistemaDrenagem;

    @Column(nullable = false)
    private String limpeza;

    @Column(nullable = false)
    private String painelEletrico;

    @Column(nullable = false)
    private String serpentinaEvaporador;

    @Column()
    private String gabinete;

    @Column(nullable = false)
    private String filtroAr;

    @Column(nullable = false)
    private String bandeja;

    @Column(nullable = false)
    private String ruidosAnormaisEvaporador;

    @Column()
    private String ruidosAnormaisCondensador;

    @Column()
    private String verificarDefletores;

    @Column(nullable = false)
    private String deteccaoAguaPiso;

    @Column()
    private String casaMaquinas;

    @Column()
    private String quantidade;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private OrdemServico ordemServico;
}

