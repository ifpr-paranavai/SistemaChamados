import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NbDateService, NbToastrService} from '@nebular/theme';
import {ActivatedRoute, Router} from '@angular/router';
import {map, switchMap} from 'rxjs/operators';
import {EquipamentoService} from '../../../shared/services/equipamento.service';
import {OrdemServicoService} from '../../../shared/services/ordem-servico.service';
import {UsuarioService} from '../../../shared/services/usuario.service';
import {ClienteService} from '../../../shared/services/cliente.service';
import {ServicoService} from '../../../shared/services/servico.service';
import {LocalDataSource} from 'ng2-smart-table';
import {ProdutoService} from '../../../shared/services/produto.service';
import * as moment from 'moment';

@Component({
  selector: 'ngx-criar-ordem-servico',
  styleUrls: ['./criar-ordem-servico.component.scss'],
  templateUrl: './criar-ordem-servico.component.html',
})
export class CriarOrdemServicoComponent implements OnInit {

  form: FormGroup;
  dataMinima: Date;
  dataMaxima: Date;
  submitted: boolean;
  fullWidth: boolean = true;
  loading = false;
  clientes = [];
  usuarios = [];
  equipamentos = [];
  servicos = [];
  produtos = [];
  selectList = [];
  selectedCliente;
  selectedUsuario;
  selectedEquipamento;
  selectedServico;
  errorToast: boolean;
  hiddenFanCoil: boolean;
  hiddenSelf: boolean;
  hiddenProduto: boolean = true;
  settings: Object;

  source: LocalDataSource = new LocalDataSource();


  constructor(private formBuilder: FormBuilder,
              private service: OrdemServicoService,
              private produtoService: ProdutoService,
              private toastrService: NbToastrService,
              private clienteService: ClienteService,
              private usuarioService: UsuarioService,
              private equipamentoService: EquipamentoService,
              private servicosService: ServicoService,
              private router: Router,
              protected dateService: NbDateService<Date>,
              private route: ActivatedRoute) {
    this.dataMinima = this.dateService.addDay(this.dateService.today(), -15);
    this.dataMaxima = this.dateService.addDay(this.dateService.today(), 7);
    this.carregaListaProdutos();
  }

  ngOnInit(): void {
    this.carregarObjetos();
    this.carregaForm();
    this.carregaDadosEditar();
  }

  hasError(field: string) {
    return this.form.get(field)?.errors;
  }

  compareById(v1, v2): boolean {
    return v1.id === v2.id;
  }

  carregaDadosEditar() {
    this.loading = true;
    this.route.params
      .pipe(
        map((params: any) => params ['id']),
        switchMap(id => this.service.buscarPorId(id)))
      .subscribe(obj => this.atualizarForm(obj));
  }

  carregaListaProdutos() {
    this.loading = true;
    this.produtoService.listarItens(0, 500, 'nomeProduto').subscribe(
      data => {
        data.body.content.forEach(produto => {
          this.selectList.push({value: produto.nomeProduto, title: produto.nomeProduto});
        });
        this.settings = this.loadTableSettings();
        this.loading = false;
      }, () => {
        this.loading = false;
      },
    );
  }

  carregaForm() {
    this.form = this.formBuilder.group({
      /*
      Cabeçalho Ordem Servico
      */
      id: [null],
      data: ['', [Validators.required]],
      cliente: ['', [Validators.required]],
      usuario: ['', [Validators.required]],
      tipoOrdemServico: ['', [Validators.required]],
      situacaoOs: ['Aberto'],
      tipoAtendimento: ['', [Validators.required]],
      servicos: [[], [Validators.required]],
      equipamento: ['', [Validators.required]],

      /*
      Itens Ordem Servico
      */
      ordemServicoItem: this.formBuilder.group({
        observacao: [''],
        tensaoGeralRS: ['', [Validators.required]],
        tensaoGeralST: ['', [Validators.required]],
        tensaoGeralRT: ['', [Validators.required]],
        correnteA1: ['', [Validators.required]],
        correnteA2: ['', [Validators.required]],
        correnteA3: ['', [Validators.required]],
        motorEvaporadorA1A2A3: ['', [Validators.required]],
        alimentacaoComando24: [''],
        compressorRSRTST: [''],
        compressorA1A2A3: [''],
        aberturaValvulaAguaGelada: [''],
        motorCondensadorA1A2A3: [''],
        aquecimentoResistencia: [''],
        umidificadorRSRTST: ['', [Validators.required]],
        umidificadorA1A2A3: ['', [Validators.required]],
        temperaturaEntradaAguaGelada: [''],
        temperaturaSaidaAguaGelada: [''],
        diferencialTemperaturaEntradaSaida: [''],
        condensadorDryRSRTST: [''],
        vazaoAr: ['', [Validators.required]],
        temperaturaRetorno: ['', [Validators.required]],
        umidadeRelativaRetorno: ['', [Validators.required]],
        temperaturaInsuflacao: ['', [Validators.required]],
        diferencialEntradaSaidaAr: ['', [Validators.required]],
        temperaturaLinhaLiquido: [''],
        temperaturaLinhaEntradaSuccao: [''],
        pressaoBaixa: [''],
        pressAlta: [''],
        superAquecimento: [''],
        subResfriamento: [''],
        setPointResfriamento: ['', [Validators.required]],
        setPointUmidade: ['', [Validators.required]],
        aberturaValvulaG: [''],
        temperaturaEntradaAguaCondensada: [''],
        temperaturaSaidaAguaCondensada: [''],
        diferencialFiltroSecador: [''],
        // campos OK/NA
        filtroY: ['NA'],
        verificarVazamentoAguaGelada: ['NA'],
        verificarPressaoDiferencialFiltroSujo: ['NA'],
        verificarPressaoDiferencialFiltroAr: ['NA'],
        verificarComunicacaoIHM: ['NA'],
        verificarOffSetSensorTemperaturaUmidade: ['NA'],
        verificarValvulaBloqueioAguaGelada: ['NA'],
        verificarAlimentacaoDrenoUmidificador: ['NA'],
        testeDampers: ['NA'],
        averiguarHistoricoEventosIHM: ['NA'],
        verificarIsolamentoTermicasAcustico: ['NA'],
        verificarEliminarPontosCorrosao: ['NA'],
        verificarSistemaDrenagem: ['NA'],
        limpeza: ['NA'],
        painelEletrico: ['NA'],
        serpentinaEvaporador: ['NA'],
        gabinete: ['NA'],
        filtroAr: ['NA'],
        bandeja: ['NA'],
        ruidosAnormaisEvaporador: ['NA'],
        ruidosAnormaisCondensador: ['NA'],
        verificarDefletores: ['NA'],
        deteccaoAguaPiso: ['NA'],
        casaMaquinas: ['NA', [Validators.required]],
        quantidade: [''],
      }),
    });
  }

  atualizarForm(data) {
    this.form.patchValue({
      id: data.ordemServico.id,
      data: moment(data.ordemServico.data).toDate(),
      cliente: data.ordemServico.cliente,
      usuario: data.ordemServico.usuario,
      tipoOrdemServico: data.ordemServico.tipoOrdemServico.tipoOrdemServico,
      situacaoOs: data.ordemServico.situacaoOs.situacao,
      tipoAtendimento: data.ordemServico.tipoAtendimento.tipoAtendimento,
      servicos: data.ordemServico.servicos,
      equipamento: data.ordemServico.equipamento,

      ordemServicoItem:{
        observacao: data.observacao,
        tensaoGeralRS: data.tensaoGeralRT,
        tensaoGeralST: data.tensaoGeralST,
        tensaoGeralRT: data.tensaoGeralRT,
        correnteA1: data.correnteA1,
        correnteA2: data.correnteA2,
        correnteA3: data.correnteA3,
        motorEvaporadorA1A2A3: data.motorEvaporadorA1A2A3,
        alimentacaoComando24: data.alimentacaoComando24,
        compressorRSRTST: data.compressorRSRTST,
        compressorA1A2A3: data.compressorA1A2A3,
        aberturaValvulaAguaGelada: data.aberturaValvulaAguaGelada,
        motorCondensadorA1A2A3: data.motorCondensadorA1A2A3,
        aquecimentoResistencia: data.aquecimentoResistencia,
        umidificadorRSRTST: data.umidificadorRSRTST,
        umidificadorA1A2A3: data.umidificadorA1A2A3,
        temperaturaEntradaAguaGelada: data.temperaturaEntradaAguaGelada,
        temperaturaSaidaAguaGelada: data.temperaturaSaidaAguaGelada,
        diferencialTemperaturaEntradaSaida: data.diferencialTemperaturaEntradaSaida,
        condensadorDryRSRTST: data.condensadorDryRSRTST,
        vazaoAr: data.vazaoAr,
        temperaturaRetorno: data.temperaturaRetorno,
        umidadeRelativaRetorno: data.umidadeRelativaRetorno,
        temperaturaInsuflacao: data.temperaturaInsuflacao,
        diferencialEntradaSaidaAr: data.diferencialEntradaSaidaAr,
        temperaturaLinhaLiquido: data.temperaturaLinhaLiquido,
        temperaturaLinhaEntradaSuccao: data.temperaturaLinhaEntradaSuccao,
        pressaoBaixa: data.pressaoBaixa,
        pressAlta: data.pressAlta,
        superAquecimento: data.superAquecimento,
        subResfriamento: data.subResfriamento,
        setPointResfriamento: data.setPointResfriamento,
        setPointUmidade: data.setPointUmidade,
        aberturaValvulaG: data.aberturaValvulaG,
        temperaturaEntradaAguaCondensada: data.temperaturaEntradaAguaCondensada,
        temperaturaSaidaAguaCondensada: data.temperaturaSaidaAguaCondensada,
        diferencialFiltroSecador: data.diferencialFiltroSecador,
        // campos OK/NA
        filtroY: data.filtroY,
        verificarVazamentoAguaGelada: data.verificarVazamentoAguaGelada,
        verificarPressaoDiferencialFiltroSujo: data.verificarPressaoDiferencialFiltroSujo,
        verificarPressaoDiferencialFiltroAr: data.verificarPressaoDiferencialFiltroAr,
        verificarComunicacaoIHM: data.verificarComunicacaoIHM,
        verificarOffSetSensorTemperaturaUmidade: data.verificarOffSetSensorTemperaturaUmidade,
        verificarValvulaBloqueioAguaGelada: data.verificarValvulaBloqueioAguaGelada,
        verificarAlimentacaoDrenoUmidificador: data.verificarAlimentacaoDrenoUmidificador,
        testeDampers: data.testeDampers,
        averiguarHistoricoEventosIHM: data.averiguarHistoricoEventosIHM,
        verificarIsolamentoTermicasAcustico: data.verificarIsolamentoTermicasAcustico,
        verificarEliminarPontosCorrosao: data.verificarEliminarPontosCorrosao,
        verificarSistemaDrenagem: data.verificarSistemaDrenagem,
        limpeza: data.limpeza,
        painelEletrico: data.painelEletrico,
        serpentinaEvaporador: data.serpentinaEvaporador,
        gabinete: data.gabinete,
        filtroAr: data.filtroAr,
        bandeja: data.bandeja,
        ruidosAnormaisEvaporador: data.ruidosAnormaisEvaporador,
        ruidosAnormaisCondensador: data.ruidosAnormaisCondensador,
        verificarDefletores: data.verificarDefletores,
        deteccaoAguaPiso: data.deteccaoAguaPiso,
        casaMaquinas: data.casaMaquinas,
      },
    });
    this.selectedCliente = data.ordemServico.cliente;
    this.selectedUsuario = data.ordemServico.usuario;
    this.selectedServico = data.ordemServico.servicos;
    this.selectedEquipamento = data.ordemServico.equipamento;
  }

  carregarObjetos() {
    this.carregaListaClientes();
    this.carregaListaUsuarios();
    this.carregaListaEquipamentos();
    this.carregaListaServicos();
  }

  carregaListaClientes() {
    this.loading = true;
    this.clienteService.pegarClientes(0, 500, 'nome').then(
      response => {
        this.clientes = response.content;
        this.loading = false;
      }, () => {
        this.loading = false;
      },
    );
  }

  carregaListaUsuarios() {
    this.loading = true;
    this.usuarioService.listarItens(0, 500, 'nome').subscribe(
      response => {
        this.usuarios = response.body.content;
        this.loading = false;
      }, () => {
        this.loading = false;
      },
    );
  }

  carregaListaEquipamentos() {
    this.loading = true;
    this.equipamentoService.listarItens(0, 500, 'nomeEquipamento').subscribe(
      response => {
        this.equipamentos = response.body.content;
        this.loading = false;
      }, () => {
        this.loading = false;
      },
    );
  }

  carregaListaServicos() {
    this.loading = true;
    this.servicosService.listarItens(0, 500, 'nome').then(
      response => {
        this.servicos = response.content;
        this.loading = false;
      }, () => {
        this.loading = false;
      },
    );
  }

  showToast(position, status, titulo, message) {
    this.toastrService.show(`${message}`, `${titulo}`, {position, status});
  }

  onSubmit() {
    this.submitted = true;
    this.errorToast = false;
    if (this.form.valid) {
      this.service.salvar(this.form.value).subscribe(
        res => {
          if (res.status === 201) {
            this.router.navigate(['/pages/os/listar']);
            this.showToast('top-right', 'success', 'Legal', 'Cadastrado com sucesso');
          }
        }, err => {
          if (err.status > 400 && this.errorToast === false) {
            this.showToast('top-right', 'danger', 'Ops!', err.error.message);
            this.errorToast = true;
          }
        });
    }
  }

  onCancel() {
    this.router.navigate(['/pages/os/listar']);
  }

  onChange($event) {
    if ($event) {
      this.form.patchValue({tipoOrdemServico: 'SELF'});
    } else {
      this.form.patchValue({tipoOrdemServico: 'FAN COIL'});
    }
    this.hiddenSelf = $event;
    this.hiddenFanCoil = !$event;
  }

  addProduto($event) {
    this.carregaListaProdutos();
    this.hiddenProduto = !$event;
  }

  onChangeValue(event: string, campo: string) {
    this.form.patchValue({ordemServicoItem: {[campo]: event}});
  }

  onDeleteConfirm(event): void {
    if (window.confirm('Tem certeza que deseja apagar?')) {
      event.confirm.resolve();
    } else {
      event.confirm.reject();
    }
  }

  onAddClient(event): void {
    if (window.confirm('Tem certeza que deseja apagar?')) {
      event.confirm.resolve();
    } else {
      event.confirm.reject();
    }
  }

  onEditConfirm(event) {
    console.log(event);
  }

  loadTableSettings() {
    return {
      add: {
        addButtonContent: '<i class="nb-plus"></i>',
        createButtonContent: '<i class="nb-checkmark"></i>',
        cancelButtonContent: '<i class="nb-close"></i>',
        confirmCreate: true,
      },
      edit: {
        editButtonContent: '<i class="nb-edit"></i>',
        saveButtonContent: '<i class="nb-checkmark"></i>',
        cancelButtonContent: '<i class="nb-close"></i>',
      },
      delete: {
        deleteButtonContent: '<i class="nb-trash"></i>',
        confirmDelete: true,
      },
      columns: {
        produto: {
          title: 'Produto',
          type: 'html',
          editor: {
            type: 'list',
            config: {
              list: this.selectList,
            },
          },
        },
        quantidadeEstoque: {
          title: 'Quantidade',
          type: 'integer',
        },
      },
    };
  }
}
