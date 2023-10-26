import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.css']
})
export class RegistrationPageComponent implements OnInit{
  registrationForm!: FormGroup;
  constructor(private formBuilder: FormBuilder){}
  ngOnInit(): void {
    this.registrationForm = this.formBuilder.group({
      firstName: ['', ],
      lastName: ['', ],
      email: ['',],
      phone: ['', ],
      emergencyPhone: ['', ],
      weight: ['', ],
      password: ['', ],
      licence: ['', ],
      role: ['', ],

    });
  }
}
