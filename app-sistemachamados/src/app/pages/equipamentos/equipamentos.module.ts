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
import {EquipamentosRoutingModule} from './equipamentos-routing.module';
import {EquipamentosComponent} from './equipamentos.component';
import {FormsModule as ngFormsModule, ReactiveFormsModule} from '@angular/forms';
import {Ng2SmartTableModule} from 'ng2-smart-table';
import {IConfig, NgxMaskModule} from 'ngx-mask';
import {CriarEquipamentoComponent} from './criar-equipamento/criar-equipamento.component';
import {ListarEquipamentoComponent} from './listar-equipamento/listar-equipamento.component';
import {NgToggleModule} from '@nth-cloud/ng-toggle';

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
        EquipamentosRoutingModule,
        NbSelectModule,
        NbIconModule,
        ngFormsModule,
        ReactiveFormsModule,
        NbSpinnerModule,
        Ng2SmartTableModule,
        NbDialogModule,
        NgxMaskModule.forRoot(),
        NgToggleModule,
    ],
  declarations: [
    EquipamentosComponent,
    CriarEquipamentoComponent,
    ListarEquipamentoComponent,
  ],
})
export class EquipamentosModule {
}
