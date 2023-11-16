import { HttpClient} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { ErrorService } from './error.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor( private http: HttpClient, private errorHandler: ErrorService) { }

  register(body: any): Observable<any> {
    return this.http.post<any>('http://localhost:8080/auth/register', body).pipe(
      catchError((error) => {
       return this.errorHandler.handleError(error);
      })
    );;
  }

  login(body: any): Observable<any> {
    return this.http.post<any>('http://localhost:8080/auth/login', body).pipe(
      catchError((error) => {
       return this.errorHandler.handleError(error);
      })
    );;
  }

}
