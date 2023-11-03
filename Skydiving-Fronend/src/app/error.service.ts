import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  constructor(private router: Router, private snackBar: MatSnackBar) { }

  public handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } else if (error.status === 401){
      this.router.navigate(['']);
    }
    else if (error.status === 400){
      console.log(error);
      this.snackBar.open(error.error.message, "close");
    }

   return throwError('Tou are not authorized to perform this action');
  }
}
