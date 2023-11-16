import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WelcomePageComponent } from './welcome-page/welcome-page.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { RegistrationPageComponent } from './registration-page/registration-page.component';
import { MenuComponent } from './menu/menu.component';
import { CallendarComponent } from './callendar/callendar.component';
import { DeparturePageComponent } from './departure-page/departure-page.component';
import { ApprovalsPageComponent } from './approvals-page/approvals-page.component';

const routes: Routes = [
  {path: '', component: WelcomePageComponent},
  {path: 'login', component: LoginPageComponent},
  {path: 'register', component: RegistrationPageComponent},
  {path: 'menu', component: MenuComponent},
  {path: 'departure-callendar', component: CallendarComponent},
  {path: 'departures', component: DeparturePageComponent},
  {path: 'approvals', component: ApprovalsPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
