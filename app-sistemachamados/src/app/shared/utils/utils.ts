
export class Utils {

  static transformCpfCnpj(value: string): string {
    if (!value) {
      return null;
    }
    const identificacao = value.replace(/[^0-9]/g, '');

    if (identificacao.length === 11) {
      return identificacao.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/g, '\$1.\$2.\$3\-\$4');
    } else if (identificacao.length === 14) {
      return identificacao.replace(/(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/g, '\$1.\$2.\$3\/\$4\-\$5');
    }
    return value;
  }

  static transformCelular(value: string): string {
    if (!value) {
      return null;
    }
    let r = value.replace(/\D/g, '');
    r = r.replace(/^0/, '');

    if (r.length > 11) {
      r = r.replace(/^(\d\d)(\d{5})(\d{4}).*/, '($1) $2-$3');
    } else if (r.length > 7) {
      r = r.replace(/^(\d\d)(\d{5})(\d{0,4}).*/, '($1) $2-$3');
    } else if (r.length > 2) {
      r = r.replace(/^(\d\d)(\d{0,5})/, '($1) $2');
    } else if (value.trim() !== '') {
      r = r.replace(/^(\d*)/, '($1');
    }
    return r;
  }
}
