import {Component, OnInit} from '@angular/core';
import {LocalDataSource} from 'ng2-smart-table';

import {ClienteService} from '../../../shared/services/cliente.service';
import {EstadoService} from '../../../shared/services/estado.service';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {Router} from '@angular/router';
import {DialogGenericComponent} from '../../../shared/modal/dialog-generic/dialog-generic.component';
import {Utils} from '../../../shared/utils/utils';

@Component({
  selector: 'ngx-listar-marca',
  templateUrl: './listar-cliente.component.html',
  styleUrls: ['./listar-cliente.component.scss'],
})
export class ListarClienteComponent implements OnInit {

  source: LocalDataSource;
  pageSize = 500;
  currentPage = 0;
  showPerPage = 10;
  showSelect = true;

  ngOnInit() {
    this.initOnChagedData();
  }

  constructor(private service: ClienteService,
              private estadoService: EstadoService,
              private toastrService: NbToastrService,
              private dialogService: NbDialogService,
              private router: Router) {
    this.initData();
  }

  initOnChagedData() {
    this.source.onChanged().subscribe((change) => {
      if (change.action === 'page') {
        this.pageChange(change.paging.page);
      }
    });
  }

  settings = {
    mode: 'external',
    pager: {
      display: true,
      perPage: this.showPerPage,
    },
    actions: {
      add: true,
      edit: true,
      delete: true,
    },
    columns: {
      id: {
        title: 'Código Identificador',
        type: 'number',
      },
      nome: {
        title: 'Nome',
        type: 'string',
      },
      tipoPessoa: {
        title: 'Tipo Pessoa',
        valuePrepareFunction: (data) => {
          return data.tipoPessoa;
        },
      },
      cpfCnpj: {
        title: 'CPF/CNPJ',
        valuePrepareFunction: (data) => {
          return Utils.transformCpfCnpj(data);
        },
      },
      contato1: {
        title: 'Contato',
        valuePrepareFunction: (data) => {
          return Utils.transformCelular(data);
        },
      },
      cidade: {
        title: 'Cidade',
        valuePrepareFunction: (data) => {
          return data.nome;
        },
      },
    },
    add: {
      addButtonContent: '<i class="nb-plus"></i>',
      createButtonContent: '<i class="nb-checkmark"></i>',
      cancelButtonContent: '<i class="nb-close"></i>',
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
  };

  setPager() {
    this.source.setPaging(1, this.showPerPage, true);
    this.settings = Object.assign({}, this.settings);
  }

  initData() {
    this.source = new LocalDataSource();
    this.listarItens();
  }


  onEdit(event) {
    this.router.navigate(['/pages/clientes/editar', event.data.id]);
  }

  pageChange(pageIndex) {
    const loadedRecordCount = this.source.count();
    const lastRequestedRecordIndex = pageIndex * this.pageSize;
    if (loadedRecordCount <= lastRequestedRecordIndex) {
      this.currentPage = this.currentPage + 1;
      this.listarItens();
    }
  }

  listarItens() {
    this.service.pegarClientes(this.currentPage, this.pageSize, 'id').then
    (data => {
      this.showSelect = data.totalElements < 10;
      if (this.source.count() > 0) {
        data.content.forEach(d => this.source.add(d));
        this.source.getAll()
          .then(d => this.source.load(d));
      } else
        this.source.load(data.content);
    });
  }

  showToast(position, status, titulo, message) {
    this.toastrService.show(`${message}`, `${titulo}`, {position, status});
  }

  onDeleteConfirm($event: any) {
    this.dialogService.open(DialogGenericComponent)
      .onClose.subscribe(evento => evento && this.apagarCliente($event));
  }

  onAdd() {
    this.router.navigate(['/pages/clientes/criar']);
  }

  apagarCliente(event) {
    this.service.deletarCliente(event.data.id).then(
      response => {
        if (response.status === 204) {
          this.initData();
          this.showToast('top-right', 'success', 'Legal', 'Cliente apagado com sucesso');
        }
      }, error => {
        this.showToast('top-right', 'danger', 'Ops!', error.error.details);
      });
  }
}
