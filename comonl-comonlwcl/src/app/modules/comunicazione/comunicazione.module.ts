/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormRicercaComunicazioniComponent } from './components/ricerca-comunicazioni/form-ricerca-comunicazioni/form-ricerca-comunicazioni.component';
import { RisultatiRicercaComunicazioniComponent } from './components/ricerca-comunicazioni/risultati-ricerca-comunicazioni/risultati-ricerca-comunicazioni.component';
import { ComunicazioneRoutingModule } from './comunicazione-routing.module';
import { ComonlcommonModule } from '../comonlcommon/comonlcommon.module';
import { ReactiveFormsModule } from '@angular/forms';
import { NgbAccordionModule, NgbDatepickerModule, NgbModalModule, NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { ComunicazioneMassivaComponent } from './components/comunicazione-massiva/comunicazione-massiva.component';
import { RicercaComunicazioniComponent } from './components/ricerca-comunicazioni/ricerca-comunicazioni.component';
import { DettaglioComunicazioneComponent } from './components/dettaglio-comunicazione/dettaglio-comunicazione.component';
import { RicercaVardatoriComponent } from './components/ricerca-vardatori/ricerca-vardatori.component';
import { FormRicercaVardatoriComponent } from './components/ricerca-vardatori/form-ricerca-vardatori/form-ricerca-vardatori.component';
import { RisultatiRicercaVardatoriComponent } from './components/ricerca-vardatori/risultati-ricerca-vardatori/risultati-ricerca-vardatori.component';
import { ModalNuovaComunicazioneComponent } from './components/modal-nuova-comunicazione/modal-nuova-comunicazione.component';
import { DatiRapportoSommComponent } from './modules/nav-main-comunicazione/components/quadri/dati-rapporto-somm/dati-rapporto-somm.component';
import { ModalRicercaAziendaComponent } from './components/modal-ricerca-azienda/modal-ricerca-azienda.component';
import { ModalNuovaVardatoriComponent } from './components/modal-nuova-vardatori/modal-nuova-vardatori.component';
import { DatiGeneraliVardatoriComponent } from './components/dati-generali-vardatori/dati-generali-vardatori.component';
import { VariazioneRagioneSocialeComponent } from './components/variazione-ragione-sociale/variazione-ragione-sociale.component';
import { VariazioneDatoreLavoroComponent } from './components/variazione-datore-lavoro/variazione-datore-lavoro.component';
import { SediLavoratoriVardatoriComponent } from './components/sedi-lavoratori-vardatori/sedi-lavoratori-vardatori.component';
import { ModalInvioComunicazioneComponent } from './components/modal-invio-comunicazione/modal-invio-comunicazione.component';
import { InvioComunicazioneComponent } from './components/invio-comunicazione/invio-comunicazione.component';
import { FormModificaLavoratoreVardatoriModalComponent } from './components/sedi-lavoratori-vardatori/form-modifica-lavoratore-vardatori-modal/form-modifica-lavoratore-vardatori-modal.component';



@NgModule({
  declarations: [
    ComunicazioneMassivaComponent,
    RicercaComunicazioniComponent,
    FormRicercaComunicazioniComponent,
    RisultatiRicercaComunicazioniComponent,
    DettaglioComunicazioneComponent,
    RicercaVardatoriComponent,
    FormRicercaVardatoriComponent,
    RisultatiRicercaVardatoriComponent,
    ModalNuovaComunicazioneComponent,
    DatiRapportoSommComponent,
    ModalRicercaAziendaComponent,
    ModalNuovaVardatoriComponent,
    DatiGeneraliVardatoriComponent,
    VariazioneRagioneSocialeComponent,
    VariazioneDatoreLavoroComponent,
    SediLavoratoriVardatoriComponent,
    ModalInvioComunicazioneComponent,
    InvioComunicazioneComponent,
    FormModificaLavoratoreVardatoriModalComponent
  ],
  imports: [
    CommonModule,
    ComonlcommonModule,
    ComunicazioneRoutingModule,
    ReactiveFormsModule,
    NgbAccordionModule,
    NgbDatepickerModule,
    NgbModalModule,
    NgbNavModule
  ],
  entryComponents: [FormModificaLavoratoreVardatoriModalComponent]
})
export class ComunicazioneModule { }
