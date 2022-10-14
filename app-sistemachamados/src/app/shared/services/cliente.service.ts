import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Cliente} from '../model/entity/cliente';
import {Observable} from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class ClienteService {

  constructor(private http: HttpClient) {
  }

  private readonly URL_CLIENTE = '/api/cliente';

  async pegarClientes(page: number, size: number) {
    return await new Promise<any>( (resolve, reject) => {
      this.http.get(`${this.URL_CLIENTE}/clientes?page=${page}&size=${size}&sort=id`)
        .subscribe(
          res => {
            resolve(res);
          }, error =>  {
            reject(error.status + ' | ' + error.statusText);
            console.error(error);
          });
      });
  }

  async salvarCLiente(cliente: Cliente) {
    return await new Promise<any>( (resolve, reject) => {
      console.log(cliente);
      this.http.post(`${this.URL_CLIENTE}/salvar-cliente`, cliente, {observe: 'response'})
        .subscribe(
          res => resolve(res),
          error => reject(error.status + ' | ' + error.statusText),
        );
    });
  }

  async deletarCliente(idCLiente: number) {
    return await new Promise<any>( (resolve, reject) => {
      this.http.delete(`${this.URL_CLIENTE}/${idCLiente}`, {observe: 'response'})
        .subscribe(
          res => {
            resolve(res);
          }, error =>  {
            reject(error.status + ' | ' + error.statusText);
            console.error(error);
          });
    });
  }
}
