import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import {Observable, catchError} from 'rxjs'
import { SkydiverApprovalModel } from 'src/shared/skydiver';
import { ErrorService } from './error.service';

@Injectable({
  providedIn: 'root'
})
export class ApprovalService {

  constructor(private http: HttpClient, private router: Router, private errorHandler: ErrorService) {}
  url: string = 'http://localhost:8080/approval';

  getMyApprovalReqests(): Observable<SkydiverApprovalModel[]>{
    let headers = this.getHeaders(); 
    const params = new HttpParams()
    .set('email', window.localStorage.getItem('email') as string);
    return this.http.get<SkydiverApprovalModel[]>(this.url, {headers, params}).pipe(
      catchError((error) => {
       return this.errorHandler.handleError(error);
      })
    );
  }

  rejectApprovalService( email: string): Observable<SkydiverApprovalModel>{
    let headers = this.getHeaders(); 
    const params = new HttpParams()
    .set('email', email);
    return this.http.delete<SkydiverApprovalModel>(this.url, {headers, params}).pipe(
      catchError((error) => {
       return this.errorHandler.handleError(error);
      })
    );
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
