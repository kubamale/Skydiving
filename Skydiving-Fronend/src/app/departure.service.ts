import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, catchError, throwError } from 'rxjs';
import { DepartureCreateModel, DepartureDetailsModel, DepartureUpdateModel } from 'src/shared/departure';
import { ErrorService } from './error.service';
import { SkydiverModel } from 'src/shared/skydiver';

@Injectable({
  providedIn: 'root'
})
export class DepartureService {
  

  baseUrl: string = 'http://localhost:8080/departures';

  constructor(private http: HttpClient, private router:Router, private errorHandler:ErrorService) { }

  getDeparturesDates(startDate: string, endDate:string): Observable<string[]> {
    let url = this.baseUrl + '/dates';
    let headers = this.getHeaders();
    const params = new HttpParams()
    .set('startDate', startDate)
    .set('endDate', endDate);
    return this.http.get<string[]>(url, {headers, params }).pipe(
      catchError((error) => {
       return this.errorHandler.handleError(error);
      })
    );
  }

  cancelJump(email: string, id: number): Observable<DepartureDetailsModel> {
    let url = 'http://localhost:8080/departures/deleteUserFromDeparture';
    let headers = this.getHeaders();
    const params = new HttpParams()
    .set('email', email)
    .set('departureId', id);

    return this.http.post<DepartureDetailsModel>(url, {}, {headers, params}).pipe(
      catchError((error) => {
       return this.errorHandler.handleError(error);
      })
    );
  }

  getDeparturesDetails(date: string, page: number): Observable<DepartureDetailsModel[]> {
    let headers = this.getHeaders();
    const params = new HttpParams()
    .set('date', date)
    .set('page', page);
    console.log(headers.get('Authorization'));
    return this.http.get<DepartureDetailsModel[]>(this.baseUrl, {headers, params}).pipe(
      catchError((error) => {
       return this.errorHandler.handleError(error);
      })
    );
  }

  deleteDeparture(id: number){
    let headers = this.getHeaders();
    const params = new HttpParams()
    .set('id', id);
    return this.http.delete(this.baseUrl,{headers, params} ).pipe(
      catchError((error) => {
       return this.errorHandler.handleError(error);
      })
    );
  }

  updateDeparture(dep: DepartureUpdateModel): Observable<DepartureDetailsModel>{
    let headers = this.getHeaders();
    return this.http.put<DepartureDetailsModel>(this.baseUrl , dep , {headers}).pipe(
      catchError((error) => {
       return this.errorHandler.handleError(error);
      })
    );
  }

  createDeparture(body: any): Observable<DepartureDetailsModel>{
    let headers = this.getHeaders();
    return this.http.post<DepartureDetailsModel>(this.baseUrl ,body, {headers}).pipe(
      catchError((error) => {
       return this.errorHandler.handleError(error);
      })
    );
  }

  bookJump(body: DepartureCreateModel): Observable<DepartureDetailsModel> {
    let headers = this.getHeaders();
    return this.http.post<DepartureDetailsModel>(this.baseUrl + '/book', body, {headers}).pipe(
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
