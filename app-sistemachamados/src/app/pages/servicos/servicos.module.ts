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
import { ServicosRoutingModule } from './servicos-routing.module';
import { ServicosComponent } from './servicos.component';
import {FormsModule as ngFormsModule, ReactiveFormsModule} from '@angular/forms';
import {Ng2SmartTableModule} from 'ng2-smart-table';
import { NgxMaskModule, IConfig } from 'ngx-mask';
import {CriarServicoComponent} from './criar-servico/criar-servico.component';
import {ListarServicoComponent} from './listar-servico/listar-servico.component';

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
    ServicosRoutingModule,
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
    ServicosComponent,
    CriarServicoComponent,
    ListarServicoComponent,
  ],
})
export class ServicosModule { }
