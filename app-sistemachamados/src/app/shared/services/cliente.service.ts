import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Cliente} from '../model/entity/cliente';
import {Observable} from 'rxjs';
import {take} from 'rxjs/operators';


@Injectable({
  providedIn: 'root',
})
export class ClienteService {

  constructor(private http: HttpClient) {
  }

  private readonly URL_CLIENTE = '/api/cliente';

  async pegarClientes(page: number, size: number, sort: string) {
    return await new Promise<any>((resolve, reject) => {
      this.http.get(`${this.URL_CLIENTE}/clientes?page=${page}&size=${size}&sort=${sort}`)
        .subscribe(
          res => {
            resolve(res);
          }, error => {
            reject(error.status + ' | ' + error.statusText);
            console.error(error);
          });
    });
  }

  pegarClientePorId(id) {
    return this.http.get(`${this.URL_CLIENTE}/${id}`).pipe(take(1));
  }


  // async salvarCLiente(cliente: Cliente) {
  //   return await new Promise<any>((resolve, reject) => {
  //     this.http.post(`${this.URL_CLIENTE}/salvar-cliente`, cliente, {observe: 'response'})
  //       .subscribe(
  //         res => resolve(res),
  //         error => reject(error));
  //   });
  // }

  salvarCliente(cliente: any): Observable<HttpResponse<any>> {
    return this.http.post(`${this.URL_CLIENTE}/salvar-cliente`, cliente, {observe: 'response'});
  }


  salvarCLiente2(cliente: Cliente) {
    return this.http.post(`${this.URL_CLIENTE}/salvar-cliente`, cliente).toPromise();
  }

  async deletarCliente(idCLiente: number) {
    return await new Promise<any>((resolve, reject) => {
      this.http.delete(`${this.URL_CLIENTE}/${idCLiente}`, {observe: 'response'})
        .subscribe(
          res => {
            resolve(res);
          }, error => {
            reject(error.status + ' | ' + error.statusText);
            console.error(error);
          });
    });
  }
}
