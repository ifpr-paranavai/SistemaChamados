import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {take} from 'rxjs/operators';
import {Observable} from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class EquipamentoService {

  private readonly URL_EQUIPAMENTO = '/api/equipamento';

  constructor(private http: HttpClient) {
  }

  // async listarItens(page: number, size: number, sort: string) {
  //   return await new Promise<any>((resolve, reject) => {
  //     this.http.get(`${this.URL_EQUIPAMENTO}/equipamentos?page=${page}&size=${size}&sort=${sort}`)
  //       .subscribe(
  //         res => {
  //           resolve(res);
  //         }, error => {
  //           reject(error.status + ' | ' + error.statusText);
  //         });
  //   });
  // }

  listarItens(page: number, size: number, sort: string): Observable<any> {
    return this.http.get(`${this.URL_EQUIPAMENTO}?page=${page}&size=${size}&sort=${sort}`,
      {observe: 'response'}).pipe(take(1));
  }

  buscarPorId(id) {
    return this.http.get(`${this.URL_EQUIPAMENTO}/pegarId/${id}`).pipe(take(1));
  }

  salvar(obj: any): Observable<HttpResponse<any>> {
    return this.http.post(`${this.URL_EQUIPAMENTO}/salvar-equipamento`, obj, {observe: 'response'});
  }

  deletar(id: number): Observable<HttpResponse<any>> {
    return this.http.delete(`${this.URL_EQUIPAMENTO}/${id}`, {observe: 'response'});
  }

}
