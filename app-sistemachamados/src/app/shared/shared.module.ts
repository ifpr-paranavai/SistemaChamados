import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DialogGenericComponent} from './modal/dialog-generic/dialog-generic.component';
import {NbButtonModule, NbCardModule} from '@nebular/theme';



@NgModule({
  declarations: [DialogGenericComponent],
  imports: [
    CommonModule,
    NbCardModule,
    NbButtonModule,
  ],
  exports: [DialogGenericComponent],
})
export class SharedModule { }
