import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { DepartureDetailsModel } from 'src/shared/departure';

@Injectable({
  providedIn: 'root'
})
export class DepartureService {

  baseUrl: string = 'http://localhost:8080/departures';

  constructor(private http: HttpClient, private router:Router) { }

  getDeparturesDates(startDate: string, endDate:string): Observable<string[]> {
    let url = this.baseUrl + '/dates';
    let headers = this.getHeaders();
    const params = new HttpParams()
    .set('startDate', startDate)
    .set('endDate', endDate);
    return this.http.get<string[]>(url, {headers, params })
  }

  getDeparturesDetails(date: string): Observable<DepartureDetailsModel[]> {
    let headers = this.getHeaders();
    const params = new HttpParams()
    .set('date', date);

    return this.http.get<DepartureDetailsModel[]>(this.baseUrl, {headers, params});
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