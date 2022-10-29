import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Cliente} from '../model/entity/cliente';
import {Observable} from 'rxjs';
import {take} from 'rxjs/operators';


@Injectable({
  providedIn: 'root',
})
export class ProdutoService {

  constructor(private http: HttpClient) {
  }

  private readonly URL_PRODUTO = '/api/produto';

  listarItens(page: number, size: number): Observable<any> {
    return this.http.get(`${this.URL_PRODUTO}/produtos?page=${page}&size=${size}&sort=id`,
      {observe: 'response'}).pipe(take(1));
  }

  buscarPorId(id) {
    return this.http.get(`${this.URL_PRODUTO}/buscarPorId/${id}`).pipe(take(1));
  }

  salvar(cliente: any): Observable<HttpResponse<any>> {
    return this.http.post(`${this.URL_PRODUTO}/salvar-produto`, cliente, {observe: 'response'});
  }

  apagar(id: number): Observable<HttpResponse<any>> {
    return this.http.delete(`${this.URL_PRODUTO}/${id}`, {observe: 'response'});
  }
}
