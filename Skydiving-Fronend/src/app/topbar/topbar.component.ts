import { Component } from '@angular/core';
import { TranslocoService } from '@ngneat/transloco';

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.css']
})
export class TopbarComponent {
  currentLanguage!: string

  constructor(private translocoService: TranslocoService) {
    this.currentLanguage = this.translocoService.getActiveLang();
  }
 
  public changeLanguage(languageCode: any): void {
    this.translocoService.setActiveLang(languageCode.target.value);
  }
}
