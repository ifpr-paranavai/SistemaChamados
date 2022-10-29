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
import {AtendimentosRoutingModule} from './atendimentos-routing.module';
import {AtendimentosComponent} from './atendimentos.component';
import {FormsModule as ngFormsModule, ReactiveFormsModule} from '@angular/forms';
import {Ng2SmartTableModule} from 'ng2-smart-table';
import {IConfig, NgxMaskModule} from 'ngx-mask';
import {CriarAtendimentoComponent} from './criar-atendimento/criar-atendimento.component';
import {ListarAtendimentoComponent} from './listar-atendimento/listar-atendimento.component';

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
    AtendimentosRoutingModule,
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
    AtendimentosComponent,
    CriarAtendimentoComponent,
    ListarAtendimentoComponent,
  ],
})
export class AtendimentosModule {
}
