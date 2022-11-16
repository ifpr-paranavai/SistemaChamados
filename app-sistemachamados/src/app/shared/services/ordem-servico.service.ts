import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {take} from 'rxjs/operators';
import {Observable} from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class OrdemServicoService {

  private readonly URL_OS = '/api/ordem-servico';

  constructor(private http: HttpClient) {
  }

  listarItens(page: number, size: number, sort: string): Observable<any> {
    return this.http.get(`${this.URL_OS}?page=${page}&size=${size}&sort=${sort}`,
      {observe: 'response'}).pipe(take(1));
  }

  buscarPorId(id) {
    return this.http.get(`${this.URL_OS}/${id}`).pipe(take(1));
  }

  salvar(obj: any): Observable<HttpResponse<any>> {
    return this.http.post(`${this.URL_OS}/`, obj, {observe: 'response'});
  }

  deletar(id: number): Observable<HttpResponse<any>> {
    return this.http.delete(`${this.URL_OS}/${id}`, {observe: 'response'});
  }

}
