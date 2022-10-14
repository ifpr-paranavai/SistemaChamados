import { Observable } from 'rxjs';
import { OrdersChart } from './orders-chart';
import { ProfitChart  } from './profit-chart';

export interface OrderProfitChartSummaryDashboard {
  title: string;
  value: number;
}

export abstract class OrdersProfitChartDataDashboard {
  abstract getOrderProfitChartSummary(): Observable<() => Promise<any>>;
  abstract getOrdersChartData(period: string): Observable<OrdersChart>;
  abstract getProfitChartData(period: string): Observable<ProfitChart>;
}
