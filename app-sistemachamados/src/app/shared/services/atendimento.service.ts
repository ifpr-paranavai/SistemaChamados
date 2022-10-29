import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {take} from 'rxjs/operators';
import {Observable} from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class AtendimentoService {

  private readonly URL_ATENDIMENTO = '/api/ordem-servico';

  constructor(private http: HttpClient) {
  }

  listarItens(page: number, size: number, sort: string): Observable<any> {
    return this.http.get(`${this.URL_ATENDIMENTO}/ordem-servicos?page=${page}&size=${size}&sort=${sort}`,
      {observe: 'response'}).pipe(take(1));
  }

  buscarPorId(id) {
    return this.http.get(`${this.URL_ATENDIMENTO}/${id}`).pipe(take(1));
  }

  salvar(obj: any): Observable<HttpResponse<any>> {
    return this.http.post(`${this.URL_ATENDIMENTO}/salvar-ordem-servico`, obj, {observe: 'response'});
  }

  deletar(id: number): Observable<HttpResponse<any>> {
    return this.http.delete(`${this.URL_ATENDIMENTO}/${id}`, {observe: 'response'});
  }

}
