import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {EquipamentosComponent} from './equipamentos.component';
import {CriarEquipamentoComponent} from './criar-equipamento/criar-equipamento.component';
import {ListarEquipamentoComponent} from './listar-equipamento/listar-equipamento.component';

const routes: Routes = [
  {
    path: '',
    component: EquipamentosComponent,
    children: [
      {
        path: 'criar',
        component: CriarEquipamentoComponent,
      },
      {
        path: 'editar/:id',
        component: CriarEquipamentoComponent,
      },
      {
        path: 'listar',
        component: ListarEquipamentoComponent,
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
export class EquipamentosRoutingModule {
}

