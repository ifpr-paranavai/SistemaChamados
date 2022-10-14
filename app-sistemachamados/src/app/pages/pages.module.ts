import { NgModule } from '@angular/core';
import {NbButtonModule, NbCardModule, NbMenuModule} from '@nebular/theme';

import { ThemeModule } from '../@theme/theme.module';
import { PagesComponent } from './pages.component';
import { ECommerceModule } from './e-commerce/e-commerce.module';
import { PagesRoutingModule } from './pages-routing.module';
import { MiscellaneousModule } from './miscellaneous/miscellaneous.module';
import {DashboardModuleIot} from './dashboard-iot/dashboard-module-iot.module';
import {DashboardModule} from './dashboard/dashboard.module';

@NgModule({
  imports: [
    PagesRoutingModule,
    ThemeModule,
    NbMenuModule,
    DashboardModuleIot,
    ECommerceModule,
    MiscellaneousModule,
    DashboardModule,
    NbCardModule,
    NbButtonModule,
  ],
  declarations: [
    PagesComponent,
  ],
})
export class PagesModule {
}
