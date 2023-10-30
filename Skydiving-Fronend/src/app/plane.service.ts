import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PlaneModel } from 'src/shared/plane';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class PlaneService {

  baseUrl: string = 'http://localhost:8080/plane/all';

  constructor(private http: HttpClient, private router:Router) { }

  public getAllPlanes(): Observable<PlaneModel[]>{
    let headers = this.getHeaders();

    return this.http.get<PlaneModel[]>(this.baseUrl, {headers});
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
