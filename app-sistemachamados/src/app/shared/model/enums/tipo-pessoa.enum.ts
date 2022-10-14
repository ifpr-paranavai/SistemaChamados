export enum TipoPessoaEnum {
  FISICA = 'Física',
  JURIDICA = 'Juridica',
}

export const TipoPessoaLabelMapping: Record<TipoPessoaEnum, string> = {
  [TipoPessoaEnum.FISICA]: 'Pessoa Física',
  [TipoPessoaEnum.JURIDICA]: 'Pessoa Juridica',
};
