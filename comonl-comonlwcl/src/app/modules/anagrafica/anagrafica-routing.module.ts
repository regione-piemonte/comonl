/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { GestioneApplicativaComponent } from './components/gestione-applicativa/gestione-applicativa.component';
import { AccreditamentoDelegheComponent } from './components/accreditamento-deleghe/accreditamento-deleghe.component';
import { DettaglioAziendaComponent } from './components/anagrafica-azienda/dettaglio-azienda/dettaglio-azienda.component';
import { AnagraficaAziendaComponent } from './components/anagrafica-azienda/anagrafica-azienda.component';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AnagraficaLavoratoreComponent } from './components/anagrafica-lavoratore/anagrafica-lavoratore.component';
import { DatiComponent } from './components/anagrafica-lavoratore/dati/dati.component';
import { RicercaAccreditamentoAnagraficheComponent } from './components/ricerca-accreditamento-anagrafiche/ricerca-accreditamento-anagrafiche.component';
import { FormAccreditamentoConsulenteComponent } from './components/form-accreditamento-consulente/form-accreditamento-consulente.component';
import { DettaglioDelegaComponent } from './components/accreditamento-deleghe/dettaglio-delega/dettaglio-delega.component';
import { FormAnagraficaStudioProfessionaleComponent } from './components/form-anagrafica-studio-professionale/form-anagrafica-studio-professionale.component';
import { FormAnagraficaAziendaComponent } from './components/form-anagrafica-azienda/form-anagrafica-azienda.component';


const anagraficheRoutes: Routes = [
  { path: 'anagrafica-lavoratore', component: AnagraficaLavoratoreComponent },
  { path: 'anagrafica-azienda', component: AnagraficaAziendaComponent },
  { path: 'dati', component: DatiComponent },
  { path: 'dettaglio-azienda', component: DettaglioAziendaComponent },
  { path: 'accreditamento-anagrafiche', component: RicercaAccreditamentoAnagraficheComponent },
  { path: 'accreditamento-anagrafiche-consulente', component: FormAccreditamentoConsulenteComponent },
  { path: 'anagrafica-studio-professionale', component: FormAnagraficaStudioProfessionaleComponent },
  { path: 'anagrafica-dati-azienda', component: FormAnagraficaAziendaComponent },
  { path: 'accreditamento-deleghe', component:  AccreditamentoDelegheComponent},
  { path: 'gestione-applicativa', component: GestioneApplicativaComponent },
  { path: 'dettaglio-delega', component: DettaglioDelegaComponent }
];

@NgModule({
  imports: [
    RouterModule.forChild(anagraficheRoutes),
    CommonModule
  ],
  exports: [RouterModule]
})
export class AnagraficaRoutingModule { }
