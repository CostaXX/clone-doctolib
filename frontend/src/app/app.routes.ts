import { Routes } from '@angular/router';
import { HomePage } from './home-page/home-page';
import { LoginPage } from './login-page/login-page';
import { InscriptionPage } from './inscription-page/inscription-page';
import { ValidationPage } from './validation-page/validation-page';

export const routes: Routes = [
    {
        path: '',
        component: HomePage,
    },
    {
        path: 'login',
        component: LoginPage,
    },
    {
        path: 'inscription',
        component: InscriptionPage
    },
    {
        path: 'validation',
        component: ValidationPage
    }
    

];
