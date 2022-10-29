import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

import { PagesComponent } from './pages.component';
import { ECommerceComponent } from './e-commerce/e-commerce.component';
import { NotFoundComponent } from './miscellaneous/not-found/not-found.component';
import {DashboardComponent} from './dashboard/dashboard.component';


const routes: Routes = [{
  path: '',
  component: PagesComponent,
  children: [
    {
      path: 'dashboard',
      component: DashboardComponent,
    },
    {
      path: 'dashboard-old',
      component: ECommerceComponent,
    },
    {
      path: 'layout',
      loadChildren: () => import('./layout/layout.module')
        .then(m => m.LayoutModule),
    },
    {
      path: 'clientes',
      loadChildren: () => import('./clientes/clientes.module')
        .then(m => m.ClientesModule),
    },
    {
      path: 'marcas',
      loadChildren: () => import('./marcas/marcas.module')
        .then(m => m.MarcasModule),
    },
    {
      path: 'produtos',
      loadChildren: () => import('./produtos/produtos.module')
        .then(m => m.ProdutosModule),
    },
    {
      path: 'servicos',
      loadChildren: () => import('./servicos/servicos.module')
        .then(m => m.ServicosModule),
    },
    {
      path: 'equipamentos',
      loadChildren: () => import('./equipamentos/equipamentos.module')
        .then(m => m.EquipamentosModule),
    },
    {
      path: 'atendimentos',
      loadChildren: () => import('./atendimentos/atendimentos.module')
        .then(m => m.AtendimentosModule),
    },
    {
      path: 'charts',
      loadChildren: () => import('./charts/charts.module')
        .then(m => m.ChartsModule),
    },
    {
      path: 'editors',
      loadChildren: () => import('./editors/editors.module')
        .then(m => m.EditorsModule),
    },
    {
      path: 'tables',
      loadChildren: () => import('./tables/tables.module')
        .then(m => m.TablesModule),
    },
    {
      path: 'miscellaneous',
      loadChildren: () => import('./miscellaneous/miscellaneous.module')
        .then(m => m.MiscellaneousModule),
    },
    {
      path: '',
      redirectTo: 'dashboard',
      pathMatch: 'full',
    },
    {
      path: '**',
      component: NotFoundComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule {
}
