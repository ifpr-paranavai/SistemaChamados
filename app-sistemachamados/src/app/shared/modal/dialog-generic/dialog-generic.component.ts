import {Component, Input, OnInit} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';

@Component({
  selector: 'ngx-dialog-generic-delete',
  templateUrl: 'dialog-generic.component.html',
  styleUrls: ['dialog-generic.component.scss'],
})

export class DialogGenericComponent implements OnInit {

  @Input() titulo: string;
  @Input() primeiroLabel: string;
  @Input() segundoLabel: string;

  constructor(protected ref: NbDialogRef<DialogGenericComponent>) {
  }

  ngOnInit() {
  }

  cancel() {
    this.ref.close();
  }

  submit(event) {
    this.ref.close(event);
  }
}
