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
import { MarcasRoutingModule } from './marcas-routing.module';
import { MarcasComponent } from './marcas.component';
import {FormsModule as ngFormsModule, ReactiveFormsModule} from '@angular/forms';
import {CriarMarcaComponent} from './criar-marca/criar-marca.component';
import {ListarMarcaComponent} from './listar-marca/listar-marca.component';
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
    MarcasRoutingModule,
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
    MarcasComponent,
    CriarMarcaComponent,
    ListarMarcaComponent,
  ],
})
export class MarcasModule { }
