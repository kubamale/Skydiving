import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  constructor(private router: Router) { }

  public handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } else if (error.status === 401){
      this.router.navigate(['']);
    }
    // Return an observable with a user-facing error message.
   // return throwError(() => new Error('Something bad happened; please try again later.'));

   return throwError('Tou are not authorized to perform this action')
  }
}
