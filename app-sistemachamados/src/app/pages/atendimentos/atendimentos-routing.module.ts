import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {AtendimentosComponent} from './atendimentos.component';
import {CriarAtendimentoComponent} from './criar-atendimento/criar-atendimento.component';
import {ListarAtendimentoComponent} from './listar-atendimento/listar-atendimento.component';

const routes: Routes = [
  {
    path: '',
    component: AtendimentosComponent,
    children: [
      {
        path: 'criar',
        component: CriarAtendimentoComponent,
      },
      {
        path: 'editar/:id',
        component: CriarAtendimentoComponent,
      },
      {
        path: 'listar',
        component: ListarAtendimentoComponent,
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
export class AtendimentosRoutingModule {
}

