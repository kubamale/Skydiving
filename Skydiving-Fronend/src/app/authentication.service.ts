import { HttpClient} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor( private http: HttpClient) { }

  register(body: any): Observable<any> {
    return this.http.post<any>('http://localhost:8080/auth/register', body);
  }

  login(body: any): Observable<any> {
    return this.http.post<any>('http://localhost:8080/auth/login', body);
  }

}
