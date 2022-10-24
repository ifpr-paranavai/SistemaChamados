import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ProdutosComponent } from './produtos.component';
import {CriarProdutoComponent} from './criar-produto/criar-produto.component';
import {ListarProdutoComponent} from './listar-produto/listar-produto.component';

const routes: Routes = [
  {
    path: '',
    component: ProdutosComponent,
    children: [
      {
        path: 'criar',
        component: CriarProdutoComponent,
      },
      {
        path: 'editar/:id',
        component: CriarProdutoComponent,
      },
      {
        path: 'listar',
        component: ListarProdutoComponent,
      },
    ],
  },
];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
  ],
  exports: [
    RouterModule,
  ],
})
export class ProdutosRoutingModule {
}

