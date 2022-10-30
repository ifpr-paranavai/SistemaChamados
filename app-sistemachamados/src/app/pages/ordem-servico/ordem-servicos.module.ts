import {NgModule} from '@angular/core';
import {
  NbActionsModule,
  NbButtonModule,
  NbCardModule,
  NbCheckboxModule,
  NbDatepickerModule,
  NbDialogModule,
  NbIconModule,
  NbInputModule,
  NbRadioModule,
  NbSelectModule,
  NbSpinnerModule,
  NbUserModule,
} from '@nebular/theme';

import {ThemeModule} from '../../@theme/theme.module';
import {OrdemServicosRoutingModule} from './ordem-servicos-routing.module';
import {OrdemServicosComponent} from './ordem-servicos.component';
import {FormsModule as ngFormsModule, ReactiveFormsModule} from '@angular/forms';
import {Ng2SmartTableModule} from 'ng2-smart-table';
import {IConfig, NgxMaskModule} from 'ngx-mask';
import {CriarOrdemServicoComponent} from './criar-ordem-servico/criar-ordem-servico.component';
import {ListarOrdemServicoComponent} from './listar-ordem-servico/listar-ordem-servico.component';
import { registerLocaleData } from '@angular/common';
import br from '@angular/common/locales/br';

export const options: Partial<null | IConfig> | (() => Partial<IConfig>) = null;


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
    OrdemServicosRoutingModule,
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
    OrdemServicosComponent,
    CriarOrdemServicoComponent,
    ListarOrdemServicoComponent,
  ],
})
export class OrdemServicosModule {
}
