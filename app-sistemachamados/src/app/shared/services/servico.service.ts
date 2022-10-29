import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {take} from 'rxjs/operators';
import {Observable} from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class ServicoService {

  private readonly URL_SERVICO = '/api/servico';

  constructor(private http: HttpClient) {
  }

  async listarItens(page: number, size: number, sort: string) {
    return await new Promise<any>((resolve, reject) => {
      this.http.get(`${this.URL_SERVICO}/servicos?page=${page}&size=${size}&sort=${sort}`)
        .subscribe(
          res => {
            resolve(res);
          }, error => {
            reject(error.status + ' | ' + error.statusText);
          });
    });
  }

  buscarPorId(id) {
    return this.http.get(`${this.URL_SERVICO}/pegarId/${id}`).pipe(take(1));
  }

  salvar(obj: any): Observable<HttpResponse<any>> {
    return this.http.post(`${this.URL_SERVICO}/salvar-servico`, obj, {observe: 'response'});
  }

  deletar(id: number): Observable<HttpResponse<any>> {
    return this.http.delete(`${this.URL_SERVICO}/${id}`, {observe: 'response'});
  }

}
