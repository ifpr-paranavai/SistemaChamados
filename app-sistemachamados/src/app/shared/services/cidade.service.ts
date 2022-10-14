import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Cliente} from '../model/entity/cliente';
import {Observable} from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class CidadeService {

  constructor(private http: HttpClient) {
  }

  private readonly URL_CIDADE = '/api/cidade';


  async listarCidadePorIdEstado(idEstado: number) {
    return await new Promise<any>( (resolve, reject) => {
      this.http.get(`${this.URL_CIDADE}/buscar-por-idEstado/${idEstado}`)
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
