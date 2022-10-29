import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NbToastrService} from '@nebular/theme';
import {ActivatedRoute, Router} from '@angular/router';
import {map, switchMap} from 'rxjs/operators';
import {ServicoService} from '../../../shared/services/servico.service';


@Component({
  selector: 'ngx-criar-marca',
  styleUrls: ['./criar-servico.component.scss'],
  templateUrl: './criar-servico.component.html',
})
export class CriarServicoComponent implements OnInit {

  form: FormGroup;
  submitted: boolean;
  fullWidth: boolean = true;
  loading = false;
  errorToast = false;

  constructor(private formBuilder: FormBuilder,
              private service: ServicoService,
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
        switchMap(id => this.service.buscarPorId(id)))
      .subscribe(obj => this.atualizarForm(obj));
    this.loading = false;
  }

  carregaForm() {
    this.form = this.formBuilder.group({
      id: [null],
      nome: ['', [Validators.required]],
      observacao: [''],
    });
  }

  atualizarForm(data) {
    this.form.patchValue({
      id: data.id,
      nome: data.nome,
      observacao: data.observacao,
    });
  }

  showToast(position, status, titulo, message) {
    this.toastrService.show(`${message}`, `${titulo}`, {position, status});
  }


  onSubmit() {
    this.submitted = true;
    this.errorToast = false;
    if (this.form.valid) {
      this.service.salvar(this.form.value).subscribe(
        res => {
          if (res.status === 201) {
            this.router.navigate(['/pages/servicos/listar']);
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
    this.router.navigate(['/pages/servicos/listar']);
  }
}
