import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataSharingService {

  constructor() { }

  private isUserLoggedIn = new BehaviorSubject<Boolean>(false);

  IsUserLoggedIn(){
    return this.isUserLoggedIn;
  }

  userLoggedIn(){
    this.isUserLoggedIn.next(true);
  }
}
