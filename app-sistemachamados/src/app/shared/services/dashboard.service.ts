import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of as observableOf} from 'rxjs';
import {OrdersChart, OrdersChartData} from '../../@core/data/orders-chart';
import {ProfitChart, ProfitChartData} from '../../@core/data/profit-chart';
import {OrderProfitChartSummaryDashboard, OrdersProfitChartDataDashboard} from '../../@core/data/dashboard-orders-profit-chart';

@Injectable({
  providedIn: 'root',
})
export class DashboardService extends OrdersProfitChartDataDashboard {

  constructor(private http: HttpClient,
              private ordersChartService: OrdersChartData,
              private profitChartService: ProfitChartData) {
    super();
  }

  private static URL_CLIENTE = '/api/cliente';

  private summary = [];

  async pegarDashboard() {
    return await new Promise<any>( (resolve, reject) => {
      this.http.get(`${DashboardService.URL_CLIENTE}/clientes?page=0&size=10&sort=id`)
        .subscribe(
          res => {
            resolve(res);
          }, error =>  {
            reject(error.status + ' | ' + error.statusText);
            console.error(error);
          });
    });
  }

  getOrderProfitChartSummary(): Observable<() => Promise<any>> {
    return observableOf(this.pegarDashboard);
  }

  getOrdersChartData(period: string): Observable<OrdersChart> {
    return observableOf(this.ordersChartService.getOrdersChartData(period));
  }

  getProfitChartData(period: string): Observable<ProfitChart> {
    return observableOf(this.profitChartService.getProfitChartData(period));
  }

  definirSummary (valor1: number, valor2: number, valor3: number, valor4) {
    return this.summary.push(
      {
        title: 'Titulo 1',
        value: valor1,
      },
      {
        title: 'Titulo 2',
        value: valor2,
      },
      {
        title: 'Titulo 3',
        value: valor3,
      },
      {
        title: 'Titulo 4',
        value: valor4,
      },
    );
  }
}
