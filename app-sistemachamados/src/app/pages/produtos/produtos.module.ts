import { NgModule } from '@angular/core';
import {
  NbActionsModule,
  NbButtonModule,
  NbCardModule,
  NbCheckboxModule,
  NbDatepickerModule, NbDialogModule, NbDialogRef, NbIconModule,
  NbInputModule,
  NbRadioModule,
  NbSelectModule, NbSpinnerModule,
  NbUserModule,
} from '@nebular/theme';

import { ThemeModule } from '../../@theme/theme.module';
import { ProdutosRoutingModule } from './produtos-routing.module';
import { ProdutosComponent } from './produtos.component';
import {FormsModule as ngFormsModule, ReactiveFormsModule} from '@angular/forms';
import {CriarProdutoComponent} from './criar-produto/criar-produto.component';
import {ListarProdutoComponent} from './listar-produto/listar-produto.component';
import {Ng2SmartTableModule} from 'ng2-smart-table';
import { NgxMaskModule, IConfig } from 'ngx-mask';

export const options: Partial<null|IConfig> | (() => Partial<IConfig>) = null;


@NgModule({
  imports: [
    ThemeModule,
    NbInputModule,
    NbCardModule,
    NbButtonModule,
    NbActionsModule,
    NbUserModule,
    NbCheckboxModule,
    NbRadioModule,
    NbDatepickerModule,
    ProdutosRoutingModule,
    NbSelectModule,
    NbIconModule,
    ngFormsModule,
    ReactiveFormsModule,
    NbSpinnerModule,
    Ng2SmartTableModule,
    NbDialogModule,
    NgxMaskModule.forRoot(),
  ],
  declarations: [
    ProdutosComponent,
    CriarProdutoComponent,
    ListarProdutoComponent,
  ],
})
export class ProdutosModule { }
