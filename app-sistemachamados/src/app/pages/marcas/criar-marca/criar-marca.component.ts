import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NbToastrService} from '@nebular/theme';
import {ActivatedRoute, Router} from '@angular/router';
import {TipoPessoaEnum} from '../../../shared/model/enums/tipo-pessoa.enum';
import {map, switchMap} from 'rxjs/operators';
import {MarcaService} from '../../../shared/services/marca.service';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';


@Component({
  selector: 'ngx-criar-marca',
  styleUrls: ['./criar-marca.component.scss'],
  templateUrl: './criar-marca.component.html',
})
export class CriarMarcaComponent implements OnInit {


  tipoPessoa = TipoPessoaEnum;
  form: FormGroup;
  submitted: boolean;
  fullWidth: boolean = true;
  loading = false;
  errorToast = false;

  constructor(private formBuilder: FormBuilder,
              private service: MarcaService,
              private toastrService: NbToastrService,
              private router: Router,
              private route: ActivatedRoute,
  ) {
  }

  ngOnInit(): void {
    this.carregaForm();
    this.carregaDadosEditar();
  }

  hasError(field: string) {
    return this.form.get(field)?.errors;
  }

  carregaDadosEditar() {
    this.loading = true;
    this.route.params
      .pipe(
        map((params: any) => params ['id']),
        switchMap(id => this.service.pegarMarcaPorId(id)))
      .subscribe(obj => this.atualizarForm(obj));
    this.loading = false;
  }

  carregaForm() {
    this.form = this.formBuilder.group({
      id: [null],
      nomeMarca: ['', [Validators.required]],
    });
  }

  atualizarForm(data) {
    this.form.patchValue({
      id: data.id,
      nomeMarca: data.nomeMarca,
    });
  }

  showToast(position, status, titulo, message) {
    this.toastrService.show(`${message}`, `${titulo}`, {position, status});
  }


  onSubmit() {
    this.submitted = true;
    this.errorToast = false;
    if (this.form.valid) {
      this.service.salvarMarca(this.form.value).subscribe(
        res => {
          if (res.status === 201) {
            this.router.navigate(['/pages/marcas/listar']);
            this.showToast('top-right', 'success', 'Legal', 'Salvo com sucesso');
          }
        }, error => {
          if (error.status === 422 && this.errorToast === false) {
            this.showToast('top-right', 'danger', 'Ops!', error.error.details);
            this.errorToast = true;
          }
        });
    }
  }

  onCancel() {
    this.router.navigate(['/pages/marcas/listar']);
  }
}
