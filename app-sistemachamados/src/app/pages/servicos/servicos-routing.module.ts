import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ServicosComponent } from './servicos.component';
import {CriarServicoComponent} from './criar-servico/criar-servico.component';
import {ListarServicoComponent} from './listar-servico/listar-servico.component';

const routes: Routes = [
  {
    path: '',
    component: ServicosComponent,
    children: [
      {
        path: 'criar',
        component: CriarServicoComponent,
      },
      {
        path: 'editar/:id',
        component: CriarServicoComponent,
      },
      {
        path: 'listar',
        component: ListarServicoComponent,
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
export class ServicosRoutingModule {
}

