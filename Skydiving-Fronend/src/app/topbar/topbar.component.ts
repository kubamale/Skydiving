import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TranslocoService } from '@ngneat/transloco';
import { DataSharingService } from '../data-sharing.service';

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.css']
})
export class TopbarComponent implements OnInit{
  currentLanguage!: string
  isUserLoggedIn: Boolean = false;

  constructor(private translocoService: TranslocoService, private router: Router, private sharedDataService: DataSharingService) {
    this.currentLanguage = this.translocoService.getActiveLang();
    
  }
  ngOnInit(): void {
    this.sharedDataService.IsUserLoggedIn().subscribe(data => this.isUserLoggedIn = data);
    console.log(window.localStorage.getItem('token') !== null);
    if(window.localStorage.getItem('token') !== null){
      
      this.isUserLoggedIn = true;
    }
  }
 
  public changeLanguage(languageCode: any): void {
    this.translocoService.setActiveLang(languageCode.target.value);
  }

  logout(){
    this.isUserLoggedIn = false;
    window.localStorage.clear();
    this.router.navigate(['']);
  }


}
