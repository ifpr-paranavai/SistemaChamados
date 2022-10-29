import {Component, OnInit} from '@angular/core';
import {LocalDataSource} from 'ng2-smart-table';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {Router} from '@angular/router';
import {DialogGenericComponent} from '../../../shared/modal/dialog-generic/dialog-generic.component';
import {ServicoService} from '../../../shared/services/servico.service';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'ngx-listar-marca',
  templateUrl: './listar-servico.component.html',
  styleUrls: ['./listar-servico.component.scss'],
})
export class ListarServicoComponent implements OnInit {

  source: LocalDataSource;
  currentPage = 0;
  showPerPage = 10;
  pageSize = 500;
  showSelect = true;
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
        title: 'Nome servico',
        type: 'string',
      },
      createdDate: {
        title: 'Data criação',
        valuePrepareFunction: (data) => {
          return data === null ? 'Sem data' : new DatePipe('en-US').transform(data, 'dd/MM/yyyy');
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

  constructor(private service: ServicoService,
              private toastrService: NbToastrService,
              private dialogService: NbDialogService,
              private router: Router) {
    this.initData();
  }

  ngOnInit() {
    this.initOnChagedData();
  }

  initOnChagedData() {
    this.source.onChanged().subscribe((change) => {
      if (change.action === 'page') {
        this.pageChange(change.paging.page);
      }
    });
  }

  initData() {
    this.source = new LocalDataSource();
    this.listarItens();
  }


  onEdit(event) {
    this.router.navigate(['/pages/servicos/editar', event.data.id]);
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
    this.service.listarItens(this.currentPage, this.pageSize, 'id').then(
      data => {
        this.showSelect = data.totalElements < 10;
        if (this.source.count() > 0) {
          data.content.forEach(d => this.source.add(d));
          this.source.getAll()
            .then(d => this.source.load(d));
        } else
          this.source.load(data.content);
      },
    );
  }

  showToast(position, status, titulo, message) {
    this.toastrService.show(`${message}`, `${titulo}`, {position, status});
  }

  setPager() {
    this.source.setPaging(1, this.showPerPage, true);
    this.settings = Object.assign({}, this.settings);
  }

  onDeleteConfirm($event: any) {
    this.dialogService.open(DialogGenericComponent)
      .onClose.subscribe(evento => evento && this.apagarMarca($event));
  }

  onAdd($event: any) {
    this.router.navigate(['/pages/servicos/criar']);
  }

  apagarMarca(event) {
    this.service.deletar(event.data.id).subscribe(
      res => {
        if (res.status === 204) {
          this.initData();
          this.showToast('top-right', 'success', 'Legal', 'Serviço apagado com sucesso');
        }
      }, error => {
        this.showToast('top-right', 'danger', 'Ops!', error.error.details);
      });
  }
}
