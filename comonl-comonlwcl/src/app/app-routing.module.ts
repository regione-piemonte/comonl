/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ErrorPageComponent } from 'src/app/components/error-page/error-page.component';
import { ErrorPageResolverService } from 'src/app/services/resolver';
import { HomePageComponent } from './components';
import { SceltaRuoloComponent } from './components/scelta-ruolo/scelta-ruolo.component';
import { CheckRuoloGuard, MayLoadModuleGuard } from './guards';

const routes: Routes = [
  { path: '', redirectTo: '/scelta-ruolo', pathMatch: 'full' },
  {
    path: 'home',
    component: HomePageComponent,
     canLoad: [ CheckRuoloGuard ], canActivate: [ CheckRuoloGuard ], canActivateChild: [ CheckRuoloGuard ]
  },
  { path: 'scelta-ruolo', component: SceltaRuoloComponent },
  { path: 'not-found', component: ErrorPageComponent, data: { message: { code: 'ERROR.PAGE_NOT_FOUND', params: {} } } },
  { path: 'error', component: ErrorPageComponent, resolve: { message: ErrorPageResolverService } },
  // Lazy modules
  {
    path: 'int',
    canLoad: [ MayLoadModuleGuard ], canActivate: [ MayLoadModuleGuard ], canActivateChild: [ MayLoadModuleGuard ],
    data: { module: 'INT' },
    loadChildren: () => import('./modules/int/int.module').then(m => m.IntModule)
  },
  // Catch-all
  { path: '**', redirectTo: '/not-found' }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' }) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }

