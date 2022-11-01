import {NgModule} from '@angular/core';
import {
  NbActionsModule,
  NbButtonModule,
  NbCardModule,
  NbCheckboxModule,
  NbDatepickerModule, NbDateService,
  NbDialogModule,
  NbIconModule,
  NbInputModule, NbNativeDateService,
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
import {NgToggleModule} from '@nth-cloud/ng-toggle';
import {NbDateFnsDateModule} from '@nebular/date-fns';

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
    OrdemServicosRoutingModule,
    NbSelectModule,
    NbIconModule,
    ngFormsModule,
    ReactiveFormsModule,
    NbSpinnerModule,
    Ng2SmartTableModule,
    NbDialogModule,
    NgxMaskModule.forRoot(),
    NgToggleModule,
    NbDatepickerModule,
    NbDateFnsDateModule,
  ],
  declarations: [
    OrdemServicosComponent,
    CriarOrdemServicoComponent,
    ListarOrdemServicoComponent,
  ],
})
export class OrdemServicosModule {
}
