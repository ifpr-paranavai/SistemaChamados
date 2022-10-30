import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ClienteService} from '../../../shared/services/cliente.service';
import {NbToastrService} from '@nebular/theme';
import {EstadoService} from '../../../shared/services/estado.service';
import {CidadeService} from '../../../shared/services/cidade.service';
import {ActivatedRoute, Router} from '@angular/router';
import {TipoPessoaEnum} from '../../../shared/model/enums/tipo-pessoa.enum';
import {error} from 'protractor';
import {map, switchMap} from 'rxjs/operators';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'ngx-criar-marca',
  styleUrls: ['./criar-cliente.component.scss'],
  templateUrl: './criar-cliente.component.html',
})
export class CriarClienteComponent implements OnInit {


  form: FormGroup;
  submitted: boolean;
  fullWidth: boolean = true;
  loading = false;
  estados = [];
  cidades = [];
  selectedEstado;
  selectedCidade;
  errorToast: boolean;

  constructor(private formBuilder: FormBuilder,
              private service: ClienteService,
              private toastrService: NbToastrService,
              private estadoService: EstadoService,
              private cidadeService: CidadeService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.carregaListaDeEstados();
    this.carregaFormCliente();
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
        switchMap(id => this.service.pegarClientePorId(id)))
      .subscribe(cliente => this.atualizarForm(cliente));
  }

  carregaFormCliente() {
    this.form = this.formBuilder.group({
      id: [null],
      nome: ['', [Validators.required]],
      endereco: ['', [Validators.required]],
      numero: [''],
      tipoPessoa: ['', [Validators.required]],
      cpfCnpj: ['', [Validators.required]],
      contato1: [''],
      contato2: [''],
      estado: [''],
      cidade: ['', [Validators.required]],
    });
  }

  atualizarForm(data) {
    this.form.patchValue({
      id: data.id,
      nome: data.nome,
      endereco: data.endereco,
      numero: data.numero,
      tipoPessoa: data.tipoPessoa.tipoPessoa,
      cpfCnpj: data.cpfCnpj,
      contato1: data.contato1,
      contato2: data.contato2,
      estado: data.cidade.estado,
      cidade: data.cidade,
    });
    this.selectedEstado = data.cidade.estado;
    this.carregarCidadeEstadoId(data.cidade.estado);
    this.selectedCidade = data.cidade;
  }

  carregaListaDeEstados() {
    this.loading = true;
    this.estadoService.listarEstados().then(
      response => {
        this.estados = response.content;
        this.loading = false;
      }, () => {
        this.loading = false;
      },
    );
  }

  carregarCidadeEstadoId(estado: any) {
    this.loading = true;
    this.cidadeService.listarCidadePorIdEstado(estado.id).then(
      response => {
        this.cidades.pop();
        this.cidades = response;
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
      this.service.salvarCliente(this.form.value).subscribe(
        res => {
          if (res.status === 201) {
            this.router.navigate(['/pages/clientes/listar']);
            this.showToast('top-right', 'success', 'Legal', 'Cliente cadastrado com sucesso');
          }
        }, err => {
          if (err.status === 422 && this.errorToast === false) {
            this.showToast('top-right', 'danger', 'Ops!', err.error.details);
            this.errorToast = true;
          }
        });
    }
  }

  onCancel() {
    this.router.navigate(['/pages/clientes/listar']);
  }
}
