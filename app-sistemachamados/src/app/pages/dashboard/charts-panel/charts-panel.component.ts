import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import { takeWhile } from 'rxjs/operators';

import { OrdersChartComponent } from './charts/orders-chart.component';
import { ProfitChartComponent } from './charts/profit-chart.component';
import { OrdersChart } from '../../../@core/data/orders-chart';
import { ProfitChart } from '../../../@core/data/profit-chart';
import { OrderProfitChartSummary, OrdersProfitChartData } from '../../../@core/data/orders-profit-chart';
import {ClienteService} from '../../../services/cliente.service';

@Component({
  selector: 'ngx-ecommerce-charts',
  styleUrls: ['./charts-panel.component.scss'],
  templateUrl: './charts-panel.component.html',
})
export class ECommerceChartsPanelComponent implements OnInit, OnDestroy {

  private alive = true;

  chartPanelSummary: OrderProfitChartSummary[];
  period: string = 'week';
  ordersChartData: OrdersChart;
  profitChartData: ProfitChart;

  @ViewChild('ordersChart', { static: true }) ordersChart: OrdersChartComponent;
  @ViewChild('profitChart', { static: true }) profitChart: ProfitChartComponent;

  // constructor(private ordersProfitChartService: OrdersProfitChartData) {
  //   this.ordersProfitChartService.getOrderProfitChartSummary()
  //     .pipe(takeWhile(() => this.alive))
  //     .subscribe((summary) => {
  //       // tslint:disable-next-line:no-console
  //       console.log('salvee');
  //       this.chartPanelSummary = summary;
  //     });
  //
  //   this.getOrdersChartData(this.period);
  //   this.getProfitChartData(this.period);
  // }


  constructor(private clienteService: ClienteService) {
  }


  ngOnInit() {
    this.buscarClientes();
  }


  async buscarClientes() {
    await this.clienteService.pegarClientes().then(response => {
      // tslint:disable-next-line:no-console
      console.log('Retorno :' + JSON.stringify(response));
      if (response.length > 0) {
        // tslint:disable-next-line:no-console
        console.log(response);
      } else {
        response;
      }
    });
  }

  setPeriodAndGetChartData(value: string): void {
    if (this.period !== value) {
      this.period = value;
    }

    // this.getOrdersChartData(value);
    // this.getProfitChartData(value);
  }

  changeTab(selectedTab) {
    if (selectedTab.tabTitle === 'Profit') {
      this.profitChart.resizeChart();
    } else {
      this.ordersChart.resizeChart();
    }
  }

  // getOrdersChartData(period: string) {
  //   this.ordersProfitChartService.getOrdersChartData(period)
  //     .pipe(takeWhile(() => this.alive))
  //     .subscribe(ordersChartData => {
  //       this.ordersChartData = ordersChartData;
  //     });
  // }
  //
  // getProfitChartData(period: string) {
  //   this.ordersProfitChartService.getProfitChartData(period)
  //     .pipe(takeWhile(() => this.alive))
  //     .subscribe(profitChartData => {
  //       this.profitChartData = profitChartData;
  //     });
  // }

  ngOnDestroy() {
    this.alive = false;
  }
}
