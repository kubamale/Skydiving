import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  constructor(private router: Router){}

  ngOnInit(): void {
    if(window.localStorage.getItem('role') == null || window.localStorage.getItem('token') == null) {
        this.router.navigate(['']);
    }
  }

  navigateTo(page: string){
    console.log('navigateTo');
    this.router.navigate([page]);
  }

}
