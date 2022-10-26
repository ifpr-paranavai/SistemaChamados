import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {take} from 'rxjs/operators';
import {Observable} from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class MarcaService {

  private readonly URL_MARCA = '/api/marca';

  constructor(private http: HttpClient) {
  }

  async pegarMarcas(page: number, size: number, sort: string) {
    return await new Promise<any>((resolve, reject) => {
      this.http.get(`${this.URL_MARCA}/marcas?page=${page}&size=${size}&sort=${sort}`)
        .subscribe(
          res => {
            resolve(res);
          }, error => {
            reject(error.status + ' | ' + error.statusText);
          });
    });
  }

  pegarMarcaPorId(id) {
    return this.http.get(`${this.URL_MARCA}/${id}`).pipe(take(1));
  }

  salvarMarca(marca: any): Observable<HttpResponse<any>> {
    return this.http.post(`${this.URL_MARCA}/salvar-marca`, marca, {observe: 'response'});
  }

  deletarMarca(id: number): Observable<HttpResponse<any>> {
    return this.http.delete(`${this.URL_MARCA}/${id}`, {observe: 'response'});
  }

}
