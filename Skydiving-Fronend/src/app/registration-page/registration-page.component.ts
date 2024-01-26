import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from '../authentication.service';
import { Router} from '@angular/router';
import { SkydiverInfoModel } from 'src/shared/skydiver';
import { UserService } from '../user.service';
import { DataSharingService } from '../data-sharing.service';

@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.css']
})
export class RegistrationPageComponent implements OnInit{
  registrationForm!: FormGroup;
  hide = true;
  approvers: SkydiverInfoModel[] = []; 
  constructor(private formBuilder: FormBuilder,private sharedDataService: DataSharingService, private auth: AuthenticationService, private router: Router, private userService: UserService){}
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
      approversEmail: ['', Validators.required]
    });

    this.userService.getApprovers().subscribe(data => this.approvers = data as SkydiverInfoModel []);
  }


  register(){
    console.log(this.registrationForm);
    if(this.registrationForm.valid){
      this.auth.register(this.registrationForm.value).subscribe(data => {
        window.localStorage.setItem('role', data.role);
        window.localStorage.setItem('token', data.token);
        window.localStorage.setItem('email', data.email);
        window.localStorage.setItem('privileges', data.privileges as string);
        this.router.navigate(['/menu']);
        this.sharedDataService.userLoggedIn();
      })
    }

  }
}
