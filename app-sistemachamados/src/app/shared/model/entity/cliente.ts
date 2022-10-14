import {TipoPessoaEnum} from '../enums/tipo-pessoa.enum';
import {Cidade} from './cidade';

export interface Cliente {
  _id: number;
  nome: string;
  endereco: string;
  numero: number;
  tipoPessoa: string;
  cpfCnpj: string;
  contato1: string;
  contato2: string;
  cidade: Cidade;
}
