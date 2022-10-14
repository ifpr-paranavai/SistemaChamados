import {Estado} from './estado';

export interface Cidade {
  _id: number;
  nome: string;
  codigo_ibge: string;
  estado: Estado;
}
