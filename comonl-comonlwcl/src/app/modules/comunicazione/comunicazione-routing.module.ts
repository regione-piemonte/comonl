/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { InvioComunicazioneComponent } from './components/invio-comunicazione/invio-comunicazione.component';
import { SediLavoratoriVardatoriComponent } from './components/sedi-lavoratori-vardatori/sedi-lavoratori-vardatori.component';
import { VariazioneDatoreLavoroComponent } from './components/variazione-datore-lavoro/variazione-datore-lavoro.component';
import { VariazioneRagioneSocialeComponent } from './components/variazione-ragione-sociale/variazione-ragione-sociale.component';
import { DatiGeneraliVardatoriComponent } from './components/dati-generali-vardatori/dati-generali-vardatori.component';
import { DettaglioComunicazioneComponent } from './components/dettaglio-comunicazione/dettaglio-comunicazione.component';
import { ComunicazioneMassivaComponent } from './components/comunicazione-massiva/comunicazione-massiva.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CheckRuoloGuard } from 'src/app/guards';
import { RicercaComunicazioniComponent } from './components/ricerca-comunicazioni/ricerca-comunicazioni.component';
import { RicercaVardatoriComponent } from './components/ricerca-vardatori/ricerca-vardatori.component';
import { DatiAziendaComponent } from './modules/nav-main-comunicazione/components/quadri/dati-azienda/dati-azienda.component';

const comunicaziRoutes: Routes = [
  { path: 'ricerca-comunicazioni', component: RicercaComunicazioniComponent },
  { path: 'comunicazione-massiva', component: ComunicazioneMassivaComponent },
  { path: 'dettaglio-comunicazione', component: DettaglioComunicazioneComponent },
  { path: 'dati-generali-vardatori', component: DatiGeneraliVardatoriComponent },
  { path: 'variazione-ragione-sociale', component: VariazioneRagioneSocialeComponent },
  { path: 'variazione-datore-lavoro', component: VariazioneDatoreLavoroComponent },
  { path: 'ricerca-vardatori', component: RicercaVardatoriComponent },
  { path: 'sedi-lavoratori-vardatori', component: SediLavoratoriVardatoriComponent },
  { path: 'invio-comunicazione', component: InvioComunicazioneComponent },
  /*{ path: 'dati-rapporto', component: DatiRapportoComponent },
  { path: 'dati-azienda', component: DatiAziendaComponent },*/
  { path: 'nav-main-comunicazione', loadChildren: () => import('./modules/nav-main-comunicazione/nav-main-comunicazione.module').then(m => m.NavMainComunicazioneModule) },
];


@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(comunicaziRoutes),
    CommonModule
  ]
})
export class ComunicazioneRoutingModule { }
