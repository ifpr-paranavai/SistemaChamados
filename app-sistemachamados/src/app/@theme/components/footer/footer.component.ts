import { Component } from '@angular/core';

@Component({
  selector: 'ngx-footer',
  styleUrls: ['./footer.component.scss'],
  template: `
    <span class="created-by">
      Criado por <b>Isac Bandeira</b>
    </span>
    <div class="socials">
      <a href="https://github.com/Flag-fix" target="_blank" class="ion ion-social-github"></a>
      <a href="https://www.facebook.com/Nega0o" target="_blank" class="ion ion-social-facebook"></a>
      <a href="https://www.linkedin.com/in/isac-ponciano-bandeira-bb6560169/" target="_blank" class="ion ion-social-linkedin"></a>
    </div>
  `,
})
export class FooterComponent {
}
