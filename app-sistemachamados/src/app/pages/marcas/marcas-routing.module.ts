import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { MarcasComponent } from './marcas.component';
import {CriarMarcaComponent} from './criar-marca/criar-marca.component';
import {ListarMarcaComponent} from './listar-marca/listar-marca.component';

const routes: Routes = [
  {
    path: '',
    component: MarcasComponent,
    children: [
      {
        path: 'criar',
        component: CriarMarcaComponent,
      },
      {
        path: 'editar/:id',
        component: CriarMarcaComponent,
      },
      {
        path: 'listar',
        component: ListarMarcaComponent,
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
export class MarcasRoutingModule {
}

