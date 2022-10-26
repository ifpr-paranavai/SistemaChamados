import {Component, OnInit} from '@angular/core';
import {LocalDataSource} from 'ng2-smart-table';

import {EstadoService} from '../../../shared/services/estado.service';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {Router} from '@angular/router';
import {DialogGenericComponent} from '../../../shared/modal/dialog-generic/dialog-generic.component';
import {MarcaService} from '../../../shared/services/marca.service';

@Component({
  selector: 'ngx-listar-marca',
  templateUrl: './listar-marca.component.html',
  styleUrls: ['./listar-marca.component.scss'],
})
export class ListarMarcaComponent implements OnInit {

  source: LocalDataSource;
  currentPage = 0;
  showPerPage = 10;
  pageSize = 500;
  showSelect = true;

  ngOnInit() {
    this.initOnChagedData();
  }

  constructor(private service: MarcaService,
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
      nomeMarca: {
        title: 'Nome Marca',
        type: 'string',
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

  initData() {
    this.source = new LocalDataSource();
    this.listarMarcas();
  }


  onEdit(event) {
    this.router.navigate(['/pages/marcas/editar', event.data.id]);
  }

  pageChange(pageIndex) {
    const loadedRecordCount = this.source.count();
    const lastRequestedRecordIndex = pageIndex * this.pageSize;
    if (loadedRecordCount <= lastRequestedRecordIndex) {
      this.currentPage = this.currentPage + 1;
      this.listarMarcas();
    }
  }


  listarMarcas() {
    this.service.pegarMarcas(this.currentPage, this.pageSize, 'id').then(
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
    this.router.navigate(['/pages/marcas/criar']);
  }

  apagarMarca(event) {
    this.service.deletarMarca(event.data.id).subscribe(
      res => {
        if (res.status === 204) {
          this.initData();
          this.showToast('top-right', 'success', 'Legal', 'Marca apagada com sucesso');
        }
      }, error => {
        this.showToast('top-right', 'danger', 'Ops!', error.error.details);
      });
  }
}
