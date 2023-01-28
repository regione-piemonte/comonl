/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { NgbAccordionModule, NgbDatepickerModule, NgbModalModule, NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { ComonlcommonModule } from '../comonlcommon/comonlcommon.module';
import { AnagraficaRoutingModule } from './anagrafica-routing.module';
import { AccreditamentoDelegheComponent } from './components/accreditamento-deleghe/accreditamento-deleghe.component';
import { DettaglioDelegaComponent } from './components/accreditamento-deleghe/dettaglio-delega/dettaglio-delega.component';
import { FormAccreditamentoDelegheComponent } from './components/accreditamento-deleghe/form-accreditamento-deleghe/form-accreditamento-deleghe.component';
import { RisultatiRicercaDelegheComponent } from './components/accreditamento-deleghe/risultati-ricerca-deleghe/risultati-ricerca-deleghe.component';
import { AnagraficaAziendaComponent } from './components/anagrafica-azienda/anagrafica-azienda.component';
import { DettaglioAziendaComponent } from './components/anagrafica-azienda/dettaglio-azienda/dettaglio-azienda.component';
import { FormRicercaAziendaComponent } from './components/anagrafica-azienda/form-ricerca-azienda/form-ricerca-azienda.component';
import { RisultatiRicercaAziendaComponent } from './components/anagrafica-azienda/risultati-ricerca-azienda/risultati-ricerca-azienda.component';
import { AnagraficaLavoratoreComponent } from './components/anagrafica-lavoratore/anagrafica-lavoratore.component';
import { DatiComponent } from './components/anagrafica-lavoratore/dati/dati.component';
import { FormRicercaLavoratoreComponent } from './components/anagrafica-lavoratore/form-ricerca-lavoratore/form-ricerca-lavoratore.component';
import { RisultatiRicercaLavoratoreComponent } from './components/anagrafica-lavoratore/risultati-ricerca-lavoratore/risultati-ricerca-lavoratore.component';
import { FormAccreditamentoConsulenteComponent } from './components/form-accreditamento-consulente/form-accreditamento-consulente.component';
import { FormAnagraficaAziendaComponent } from './components/form-anagrafica-azienda/form-anagrafica-azienda.component';
import { FormAnagraficaStudioProfessionaleComponent } from './components/form-anagrafica-studio-professionale/form-anagrafica-studio-professionale.component';
import { FormRicercaAccreditamentoAnagarficheComponent } from './components/ricerca-accreditamento-anagrafiche/form-ricerca-accreditamento-anagarfiche/form-ricerca-accreditamento-anagarfiche.component';
import { RicercaAccreditamentoAnagraficheComponent } from './components/ricerca-accreditamento-anagrafiche/ricerca-accreditamento-anagrafiche.component';
import { RisultatiRicercaAccreditamentoAnagraficheComponent } from './components/ricerca-accreditamento-anagrafiche/risultati-ricerca-accreditamento-anagrafiche-cons/risultati-ricerca-accreditamento-anagrafiche.component';
import { GestioneApplicativaComponent } from './components/gestione-applicativa/gestione-applicativa.component';




@NgModule({
  declarations: [
    AnagraficaLavoratoreComponent,
    AnagraficaAziendaComponent,
    FormRicercaLavoratoreComponent,
    RisultatiRicercaLavoratoreComponent,
    DatiComponent,
    RicercaAccreditamentoAnagraficheComponent,
    FormRicercaAccreditamentoAnagarficheComponent,
    FormRicercaAziendaComponent,
    RisultatiRicercaAziendaComponent,
    DettaglioAziendaComponent,
    RisultatiRicercaAccreditamentoAnagraficheComponent,
    FormAccreditamentoConsulenteComponent,
    AccreditamentoDelegheComponent,
    FormAccreditamentoDelegheComponent,
    DettaglioDelegaComponent,
    FormAnagraficaStudioProfessionaleComponent,
    RisultatiRicercaDelegheComponent,
    FormAnagraficaAziendaComponent,
    GestioneApplicativaComponent
  ],
  imports: [
    CommonModule,
    ComonlcommonModule,
    AnagraficaRoutingModule,
    ReactiveFormsModule,
    NgbAccordionModule,
    NgbDatepickerModule,
    NgbModalModule,
    NgbNavModule
  ]
})
export class AnagraficaModule { }
