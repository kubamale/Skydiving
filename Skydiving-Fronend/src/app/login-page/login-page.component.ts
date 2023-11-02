import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from '../authentication.service';
import { Router } from '@angular/router';
import { TranslocoService } from '@ngneat/transloco';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {
  loginForm!: FormGroup;
  hide = true;
  constructor(private formBuilder: FormBuilder, private translocoService: TranslocoService, private auth: AuthenticationService, private router: Router){}
  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: ['',[Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
    });
  }


  login(){
    console.log(this.loginForm);
    if(this.loginForm.valid){
      this.auth.login(this.loginForm.value).subscribe(data => {
        window.localStorage.clear();
        window.localStorage.setItem('role', data.role);
        window.localStorage.setItem('token', data.token);
        window.localStorage.setItem('email', data.email);
        this.router.navigate(['/menu']);
      })
      
    }
  }

}
