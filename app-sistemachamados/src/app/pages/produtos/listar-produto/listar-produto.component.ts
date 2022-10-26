import {Component, OnInit} from '@angular/core';
import {LocalDataSource} from 'ng2-smart-table';
import {EstadoService} from '../../../shared/services/estado.service';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {Router} from '@angular/router';
import {DialogGenericComponent} from '../../../shared/modal/dialog-generic/dialog-generic.component';
import {ProdutoService} from '../../../shared/services/produto.service';

@Component({
  selector: 'ngx-listar-marca',
  templateUrl: './listar-produto.component.html',
  styleUrls: ['./listar-produto.component.scss'],
})
export class ListarProdutoComponent implements OnInit {

  source: LocalDataSource;
  pageSize = 500;
  currentPage = 0;
  showPerPage = 10;
  showSelect = true;

  ngOnInit() {
    this.initOnChagedData();
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
        title: 'Código',
        type: 'number',
      },
      nomeProduto: {
        title: 'Nome produto',
        type: 'string',
      },
      quantidadeEstoque: {
        title: 'Estoque',
        valuePrepareFunction: (data) => {
          return data === null ? 'Sem Estoque' : data;
        },
      },
      valorCompra: {
        title: 'Valor compra',
        valuePrepareFunction: (value) => {
          return value === 'Total' ? value : Intl.NumberFormat('pt-BR',
            {style: 'currency', currency: 'BRL'}).format(value);
        },
      },
      valorVenda: {
        title: 'Valor venda',
        valuePrepareFunction: (value) => {
          return value === 'Total' ? value : Intl.NumberFormat('pt-BR',
            {style: 'currency', currency: 'BRL'}).format(value);
        },
      },
      marca: {
        title: 'Marca',
        valuePrepareFunction: (data) => {
          return data.nomeMarca;
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

  initOnChagedData() {
    this.source.onChanged().subscribe((change) => {
      if (change.action === 'page') {
        this.pageChange(change.paging.page);
      }
    });
  }

  constructor(private service: ProdutoService,
              private estadoService: EstadoService,
              private toastrService: NbToastrService,
              private dialogService: NbDialogService,
              private router: Router) {
    this.initData();
  }

  setPager() {
    this.source.setPaging(1, this.showPerPage, true);
    this.settings = Object.assign({}, this.settings);
  }

  initData() {
    this.source = new LocalDataSource();
    this.listarItens();
  }


  onEdit(event) {
    this.router.navigate(['/pages/produtos/editar', event.data.id]);
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
    this.service.listarItens(this.currentPage, this.pageSize).subscribe
    (data => {
      this.showSelect = data.body.totalElements < 10;
      if (this.source.count() > 0) {
        data.body.content.forEach(d => this.source.add(d));
        this.source.getAll()
          .then(d => this.source.load(d));
      } else
        this.source.load(data.body.content);
    });
  }

  showToast(position, status, titulo, message) {
    this.toastrService.show(`${message}`, `${titulo}`, {position, status});
  }

  onDeleteConfirm($event: any) {
    this.dialogService.open(DialogGenericComponent)
      .onClose.subscribe(evento => evento && this.apagarItem($event));
  }

  onAdd() {
    this.router.navigate(['/pages/produtos/criar']);
  }

  apagarItem(event) {
    this.service.apagar(event.data.id).subscribe(
      response => {
        if (response.status === 204) {
          this.initData();
          this.showToast('top-right', 'success', 'Legal', 'Produto apagado com sucesso');
        }
      }, error => {
        this.showToast('top-right', 'danger', 'Ops!', error.error.details);
      });
  }
}
