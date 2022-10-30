import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {take} from 'rxjs/operators';
import {Observable} from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class UsuarioService {

  private readonly URL_USUARIO = '/api/usuarios';

  constructor(private http: HttpClient) {
  }

  listarItens(page: number, size: number, sort: string): Observable<any> {
    return this.http.get(`${this.URL_USUARIO}?page=${page}&size=${size}&sort=${sort}`,
      {observe: 'response'}).pipe(take(1));
  }

  buscarPorId(id) {
    return this.http.get(`${this.URL_USUARIO}/${id}`).pipe(take(1));
  }

  salvar(obj: any): Observable<HttpResponse<any>> {
    return this.http.post(`${this.URL_USUARIO}`, obj, {observe: 'response'});
  }

  deletar(id: number): Observable<HttpResponse<any>> {
    return this.http.delete(`${this.URL_USUARIO}/${id}`, {observe: 'response'});
  }

}
