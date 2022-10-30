import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {OrdemServicosComponent} from './ordem-servicos.component';
import {CriarOrdemServicoComponent} from './criar-ordem-servico/criar-ordem-servico.component';
import {ListarOrdemServicoComponent} from './listar-ordem-servico/listar-ordem-servico.component';

const routes: Routes = [
  {
    path: '',
    component: OrdemServicosComponent,
    children: [
      {
        path: 'criar',
        component: CriarOrdemServicoComponent,
      },
      {
        path: 'editar/:id',
        component: CriarOrdemServicoComponent,
      },
      {
        path: 'listar',
        component: ListarOrdemServicoComponent,
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
export class OrdemServicosRoutingModule {
}

