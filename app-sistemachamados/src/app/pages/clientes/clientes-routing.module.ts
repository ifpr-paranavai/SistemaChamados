import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ClientesComponent } from './clientes.component';
import {CriarClienteComponent} from './criar-cliente/criar-cliente.component';
import {ListarClienteComponent} from './listar-cliente/listar-cliente.component';

const routes: Routes = [
  {
    path: '',
    component: ClientesComponent,
    children: [
      {
        path: 'criar',
        component: CriarClienteComponent,
      },
      {
        path: 'listar',
        component: ListarClienteComponent,
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
export class ClientesRoutingModule {
}

