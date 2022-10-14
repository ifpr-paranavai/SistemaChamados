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
import { ClientesRoutingModule } from './clientes-routing.module';
import { ClientesComponent } from './clientes.component';
import {FormsModule as ngFormsModule, ReactiveFormsModule} from '@angular/forms';
import {CriarClienteComponent} from './criar-cliente/criar-cliente.component';
import {ListarClienteComponent} from './listar-cliente/listar-cliente.component';
import {Ng2SmartTableModule} from 'ng2-smart-table';
import {NgxMaskModule} from 'ngx-mask';

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
        ClientesRoutingModule,
        NbSelectModule,
        NbIconModule,
        ngFormsModule,
        ReactiveFormsModule,
        NbSpinnerModule,
        Ng2SmartTableModule,
        NbDialogModule,
        NgxMaskModule,
    ],
  declarations: [
    ClientesComponent,
    CriarClienteComponent,
    ListarClienteComponent,
  ],
})
export class ClientesModule { }
