import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, catchError } from 'rxjs';
import { SkydiverInfoModel } from 'src/shared/skydiver';
import { ErrorService } from './error.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private router: Router, private errorHandler: ErrorService){}

  bsaicUrl = 'http://localhost:8080/users/';

  getSkydivers(url: string): Observable<SkydiverInfoModel[]> {
    let headers = this.getHeaders();
    return this.http.get<SkydiverInfoModel[]>(this.bsaicUrl + url, {headers}).pipe(
      catchError((error) => {
       return this.errorHandler.handleError(error);
      })
    );;
  }


  private getHeaders(): HttpHeaders{
    if (window.localStorage.getItem('role') == null || window.localStorage.getItem('token') == null){
      this.router.navigate(['']);
    }
    var token = window.localStorage.getItem('token');
    return new HttpHeaders()
    .set("Authorization", "Bearer " + token);
  }
}
