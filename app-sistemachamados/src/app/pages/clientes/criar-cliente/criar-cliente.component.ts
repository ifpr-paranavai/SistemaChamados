import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ClienteService} from '../../../shared/services/cliente.service';
import {NbToastrService} from '@nebular/theme';
import {EstadoService} from '../../../shared/services/estado.service';
import {CidadeService} from '../../../shared/services/cidade.service';
import { Router } from '@angular/router';
import {TipoPessoaEnum} from '../../../shared/model/enums/tipo-pessoa.enum';

@Component({
  selector: 'ngx-criar-cliente',
  styleUrls: ['./criar-cliente.component.scss'],
  templateUrl: './criar-cliente.component.html',
})
export class CriarClienteComponent implements  OnInit {


  tipoPessoa =  TipoPessoaEnum;
  form: FormGroup;
  fullWidth: boolean = true;
  loading = false;
  estados = [];
  cidades = [];
  enumKeys = [];

  constructor(private formBuilder: FormBuilder,
              private service: ClienteService,
              private toastrService: NbToastrService,
              private estadoService: EstadoService,
              private cidadeService: CidadeService,
              private router: Router) {
    this.form = this.formBuilder.group({
      nome: [null],
      endereco: [null],
      numero: [null],
      tipoPessoa: [null],
      cpfCnpj: [null],
      contato1: [null],
      contato2: [null],
      estado: [null],
      cidade: [null],
    });
    this.enumKeys = Object.keys(this.tipoPessoa);
  }

  ngOnInit(): void {
    this.carregaListaDeEstados();
  }


  carregaListaDeEstados() {
    this.loading = true;
    this.estadoService.listarEstados().then(
      response => {
        console.log(response.content);
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
    this.toastrService.show(`${message}`, `${titulo}`, { position, status });
  }

  onSubmit() {
    this.service.salvarCLiente(this.form.value).then(
      response => {
        console.log(response);
        if (response.status === 201) {
          this.router.navigate(['/pages/clientes/listar']);
          this.showToast('top-right', 'success', 'Legal', 'Cliente cadastrado com sucesso');
        } else {
          this.showToast('top-right', 'danger', 'Ops!', 'Erro ao cadastrar cliente');
        }
        },
    );
  }

  onCancel() {
    console.log('message: cancelar');
  }
}
