import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from '../authentication.service';
import { Router} from '@angular/router';

@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.css']
})
export class RegistrationPageComponent implements OnInit{
  registrationForm!: FormGroup;
  hide = true;
  constructor(private formBuilder: FormBuilder, private auth: AuthenticationService, private router: Router){}
  ngOnInit(): void {
    this.registrationForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['',[Validators.required, Validators.email]],
      phone: ['', Validators.required],
      emergencyPhone: ['', Validators.required],
      weight: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(8)]],
      licence: ['', Validators.required],
      role: ['', Validators.required],

    });
  }


  register(){
    console.log(this.registrationForm);
    if(this.registrationForm.valid){
      this.auth.register(this.registrationForm.value).subscribe(data => {
        window.localStorage.setItem('role', data.role);
        window.localStorage.setItem('token', data.toke);
        window.localStorage.setItem('email', data.email);
        this.router.navigate(['/menu']);
      })
    }

  }
}
