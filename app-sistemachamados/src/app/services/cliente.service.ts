import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';


@Injectable({
  providedIn: 'root',
})
export class ClienteService {

  constructor(private http: HttpClient) {
  }

  private static URL_CLIENTE = '/api/cliente';

  async pegarClientes() {
    return await new Promise<any>( (resolve, reject) => {
      this.http.get(`${ClienteService.URL_CLIENTE}/clientes?page=0&size=10&sort=id`)
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
