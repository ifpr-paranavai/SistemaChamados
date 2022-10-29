import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NbToastrService} from '@nebular/theme';
import {ActivatedRoute, Router} from '@angular/router';
import {map, switchMap} from 'rxjs/operators';
import {ProdutoService} from '../../../shared/services/produto.service';
import {MarcaService} from '../../../shared/services/marca.service';
import {EquipamentoService} from '../../../shared/services/equipamento.service';

@Component({
  selector: 'ngx-criar-marca',
  styleUrls: ['./criar-equipamento.component.scss'],
  templateUrl: './criar-equipamento.component.html',
})
export class CriarEquipamentoComponent implements OnInit {

  form: FormGroup;
  submitted: boolean;
  fullWidth: boolean = true;
  loading = false;
  marcas = [];
  selectedMarca;
  errorToast: boolean;

  constructor(private formBuilder: FormBuilder,
              private service: EquipamentoService,
              private toastrService: NbToastrService,
              private marcaService: MarcaService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.carregaMarcas();
    this.carregaForm();
    this.carregaDadosEditar();
  }

  hasError(field: string) {
    return this.form.get(field)?.errors;
  }

  compareById(v1, v2): boolean {
    return v1.id === v2.id;
  }

  carregaDadosEditar() {
    this.loading = true;
    this.route.params
      .pipe(
        map((params: any) => params ['id']),
        switchMap(id => this.service.buscarPorId(id)))
      .subscribe(obj => this.atualizarForm(obj));
  }

  carregaForm() {
    this.form = this.formBuilder.group({
      id: [null],
      nomeEquipamento: ['', [Validators.required]],
      tensao: ['', [Validators.required]],
      amperagem: [''],
      numeroSerie: ['', [Validators.required]],
      tag: [''],
      especificacaoTecnica: [''],
      marca: ['', [Validators.required]],
    });
  }

  atualizarForm(data) {
    this.form.patchValue({
      id: data.id,
      nomeEquipamento: data.nomeEquipamento,
      tensao: data.tensao,
      amperagem: data.amperagem,
      numeroSerie: data.numeroSerie,
      tag: data.tag,
      especificacaoTecnica: data.especificacaoTecnica,
      marca: data.marca,
    });
    this.selectedMarca = data.marca;
  }

  carregaMarcas() {
    this.loading = true;
    this.marcaService.pegarMarcas(0, 500, 'nomeMarca').then(
      response => {
        this.marcas = response.content;
        this.loading = false;
      }, () => {
        this.loading = false;
      },
    );
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
            this.router.navigate(['/pages/equipamentos/listar']);
            this.showToast('top-right', 'success', 'Legal', 'Equipamento cadastrado com sucesso');
          }
        }, err => {
          if (err.status === 422 && this.errorToast === false) {
            this.showToast('top-right', 'danger', 'Ops!', err.error.details);
            this.errorToast = true;
          }
        });
    }
  }

  onCancel() {
    this.router.navigate(['/pages/equipamentos/listar']);
  }
}
