/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { RiepilogoDatiGeneraliVardatoriComponent } from './components/quadri/riepilogo/riepilogo-dati-generali-vardatori/riepilogo-dati-generali-vardatori.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavMainComunicazioneRoutingModule } from './nav-main-comunicazione-routing.module';
import { ComonlcommonModule } from 'src/app/modules/comonlcommon/comonlcommon.module';
import { NavMainComunicazioneComponent } from './components/nav-main-comunicazione/nav-main-comunicazione.component';
import { ReactiveFormsModule } from '@angular/forms';
import { NgbAccordionModule, NgbDatepickerModule, NgbModalModule, NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { AddQuadroDirective } from './directives/add-quadro.directive';
import { LoaderService } from './services/loader-service';
import { DatiRapportoComponent } from './components/quadri/dati-rapporto/dati-rapporto.component';
import { DatiAziendaComponent } from './components/quadri/dati-azienda/dati-azienda.component';
import { DatiGeneraliComponent } from './components/quadri/dati-generali/dati-generali.component';
import { AziendaUtilizzatriceComponent } from './components/quadri/azienda-utilizzatrice/azienda-utilizzatrice.component';
import { DatiMissioneComponent } from './components/quadri/dati-missione/dati-missione.component';
import { RiepilogoComponent } from './components/quadri/riepilogo/riepilogo.component';
import { ProrogaComponent } from './components/quadri/proroga/proroga.component';
import { LavoratoreComponent } from './components/quadri/lavoratore/lavoratore.component';
import { TutoreComponent } from './components/quadri/tutore/tutore.component';
import { DatiGeneraliUrgComponent } from './components/quadri/dati-generali-urg/dati-generali-urg.component';
import { RiepilogoUrgComponent } from './components/quadri/riepilogo-urg/riepilogo-urg.component';
import { RiepilogoDatiGeneraliComponent } from './components/quadri/riepilogo/riepilogo-dati-generali/riepilogo-dati-generali.component';
import { RiepilogoDatiAziendaComponent } from './components/quadri/riepilogo/riepilogo-dati-azienda/riepilogo-dati-azienda.component';
import { RiepilogoDatiRapportoComponent } from './components/quadri/riepilogo/riepilogo-dati-rapporto/riepilogo-dati-rapporto.component';
import { RiepilogoDatiLavoratoreComponent } from './components/quadri/riepilogo/riepilogo-dati-lavoratore/riepilogo-dati-lavoratore.component';
import { RiepilogoDatiTutoreComponent } from './components/quadri/riepilogo/riepilogo-dati-tutore/riepilogo-dati-tutore.component';
import { RiepilogoDatiAziendaUtilizzatriceComponent } from './components/quadri/riepilogo/riepilogo-dati-azienda-utilizzatrice/riepilogo-dati-azienda-utilizzatrice.component';
import { RiepilogoDatiProrogaComponent } from './components/quadri/riepilogo/riepilogo-dati-proroga/riepilogo-dati-proroga.component';
import { DatiCessazioneComponent } from './components/quadri/dati-cessazione/dati-cessazione.component';
import { RiepilogoDatiCessazioneComponent } from './components/quadri/riepilogo/riepilogo-dati-cessazione/riepilogo-dati-cessazione.component';
import { RiepilogoDatiMissioneComponent } from './components/quadri/riepilogo/riepilogo-dati-missione/riepilogo-dati-missione.component';
import { DatiTrasformazioneComponent } from './components/quadri/dati-trasformazione/dati-trasformazione.component';
import { RiepilogoDatiTrasformazioneComponent } from './components/quadri/riepilogo/riepilogo-dati-trasformazione/riepilogo-dati-trasformazione.component';
import { RiepilogoDatiTrasferimentoComponent } from './components/quadri/riepilogo/riepilogo-dati-trasferimento/riepilogo-dati-trasferimento.component';
import { RiepilogoDatiDistaccoComponent } from './components/quadri/riepilogo/riepilogo-dati-distacco/riepilogo-dati-distacco.component';
import { TrasferimentoDistaccoComponent } from './components/quadri/trasferimento-distacco/trasferimento-distacco.component';
import { RiepilogoVariazioneDatoreLavoroComponent } from './components/quadri/riepilogo/riepilogo-variazione-datore-lavoro/riepilogo-variazione-datore-lavoro.component';
import { RiepilogoVariazioneRagioneSocialeComponent } from './components/quadri/riepilogo/riepilogo-variazione-ragione-sociale/riepilogo-variazione-ragione-sociale.component';
import { RiepilogoSediLavoratoriVardatoriComponent } from './components/quadri/riepilogo/riepilogo-sedi-lavoratori-vardatori/riepilogo-sedi-lavoratori-vardatori.component';


@NgModule({
  declarations: [
    NavMainComunicazioneComponent,
    DatiRapportoComponent,
    DatiAziendaComponent,
    AddQuadroDirective,
    DatiGeneraliComponent,
    AziendaUtilizzatriceComponent,
    DatiMissioneComponent,
    RiepilogoComponent,
    LavoratoreComponent,
    TutoreComponent,
    ProrogaComponent,
    DatiGeneraliUrgComponent,
    RiepilogoUrgComponent,
    RiepilogoDatiGeneraliComponent,
    RiepilogoDatiAziendaComponent,
    RiepilogoDatiRapportoComponent,
    RiepilogoDatiLavoratoreComponent,
    RiepilogoDatiTutoreComponent,
    RiepilogoDatiAziendaUtilizzatriceComponent,
    RiepilogoDatiProrogaComponent,
    DatiCessazioneComponent,
    RiepilogoDatiCessazioneComponent,
    RiepilogoDatiMissioneComponent,
    DatiTrasformazioneComponent,
    RiepilogoDatiTrasformazioneComponent,
    RiepilogoDatiTrasferimentoComponent,
    RiepilogoDatiDistaccoComponent,
    TrasferimentoDistaccoComponent,
    RiepilogoDatiGeneraliVardatoriComponent,
    RiepilogoVariazioneDatoreLavoroComponent,
    RiepilogoVariazioneRagioneSocialeComponent,
    RiepilogoSediLavoratoriVardatoriComponent
  ],
  imports: [
    CommonModule,
    ComonlcommonModule,
    NavMainComunicazioneRoutingModule,
    ReactiveFormsModule,
    NgbAccordionModule,
    NgbDatepickerModule,
    NgbModalModule,
    NgbNavModule
  ],
  providers: [ LoaderService ],
  entryComponents: [
    DatiGeneraliComponent,
    DatiAziendaComponent,
    DatiRapportoComponent,
    ProrogaComponent,
    AziendaUtilizzatriceComponent,
    DatiCessazioneComponent,
    DatiMissioneComponent,
    LavoratoreComponent,
    TutoreComponent,
    DatiTrasformazioneComponent,
    TrasferimentoDistaccoComponent,
    RiepilogoComponent,
    DatiGeneraliUrgComponent,
    RiepilogoUrgComponent
  ]
})
export class NavMainComunicazioneModule { }
