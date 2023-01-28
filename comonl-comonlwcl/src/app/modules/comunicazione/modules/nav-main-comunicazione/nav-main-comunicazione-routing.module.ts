/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NavMainComunicazioneComponent } from './components/nav-main-comunicazione/nav-main-comunicazione.component';



const routes: Routes = [
  {
    path: '',
    component: NavMainComunicazioneComponent,
   /* resolve: { testataOrdine: OrdineResolverService },
    pathMatch: 'full',
    canActivate: [ MayActivateByPermessoGuard ],
    data: { permessi: ['INS_ORDINE'] }*/
  },
  { path: '**', redirectTo: '/not-found' }
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NavMainComunicazioneRoutingModule { }
