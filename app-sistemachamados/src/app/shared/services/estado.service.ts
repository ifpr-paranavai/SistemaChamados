import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Cliente} from '../model/entity/cliente';
import {Observable} from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class EstadoService {

  constructor(private http: HttpClient) {
  }

  private readonly URL_ESTADO = '/api/estado';

  // async listarEstados() {
  async listarEstados() {
    return await new Promise<any>( (resolve, reject) => {
      this.http.get(`${this.URL_ESTADO}/estados?page=0&size=100&sort=id`)
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
