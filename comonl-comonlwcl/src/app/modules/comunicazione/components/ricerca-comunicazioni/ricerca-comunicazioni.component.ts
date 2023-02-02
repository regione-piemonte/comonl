/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { NgbAccordion, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TranslateService } from '@ngx-translate/core';
import { SortEvent } from 'src/app/models';
import { PaginationDataChange } from 'src/app/models/pagination-data-change';
import { RicercaComunicazioniParams } from 'src/app/models/ricerca-comunicazioni-params';
import { ComunicazioneService, FormRicercaComunicazione, PagedResponseRicercaComunicazioni, RicercaComunicazione, Ruolo } from 'src/app/modules/comonlapi';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { LogService, UtilitiesService } from 'src/app/services';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { Utils } from 'src/app/utils';
import { ModalNuovaComunicazioneComponent } from '../modal-nuova-comunicazione/modal-nuova-comunicazione.component';

@Component({
  selector: 'comonl-ricerca-comunicazioni',
  templateUrl: './ricerca-comunicazioni.component.html',
  styleUrls: ['./ricerca-comunicazioni.component.scss']
})
export class RicercaComunicazioniComponent implements OnInit {

  @ViewChild('accordionRicercaComunicazioni', {static: false}) accordionRicercaComunicazioni: NgbAccordion;
  activeIds = ['panelRicercaComunicazioni'];

  ricercaEffettuata = false;
  currentPaginationData: PaginationDataChange;

  formRicercaComunicazione: FormRicercaComunicazione; // mappa il form della ricerca
  formPristine: FormRicercaComunicazione;   // form iniziale
  ricercaComunicazioneToSend: FormRicercaComunicazione; // obj passato al servizio

  pagedResponse: PagedResponseRicercaComunicazioni;
  ruolo: Ruolo;
  ricercaComunicazioneParams: RicercaComunicazioniParams;

  constructor(
    private logService: LogService,
    private activatedRoute: ActivatedRoute,
    private utilitiesService: UtilitiesService,
    private router: Router,
    private modalService: NgbModal,
    private comunicazioneService: ComunicazioneService,
    private alertMessageService: AlertMessageService,
    private comonlStorageService: ComonlStorageService
  ) {
    const state = this.router.getCurrentNavigation().extras.state;
    this.ricercaComunicazioneParams = state ? state.ricercaComunicazioniParams : null;
    
   }

  ngOnInit() {
    this.comonlStorageService.ruolo$.subscribe(el => this.ruolo = el);
    this.initForm();
    this.currentPaginationData = {
      limit: this.ricercaComunicazioneParams && this.ricercaComunicazioneParams.ricercaComunicazioni ? this.ricercaComunicazioneParams.ricercaComunicazioni.limit : 10,
      page: this.ricercaComunicazioneParams && this.ricercaComunicazioneParams.ricercaComunicazioni ? this.ricercaComunicazioneParams.ricercaComunicazioni.page : 0,
      offset: 0,
      sort:  this.ricercaComunicazioneParams && this.ricercaComunicazioneParams? this.ricercaComunicazioneParams.ricercaComunicazioni.sort : null
    };
    this.patchFormParams();
  }


  patchFormParams() {
    if (!this.ricercaComunicazioneParams) {
      return;
    }

    this.formRicercaComunicazione = this.ricercaComunicazioneParams.ricercaComunicazioni;
  }

  private initForm() {
    const now = new Date();
    this.formPristine = {
      codiceFiscaleAzienda: this.ruolo && (!this.ruolo.amministratore && !this.ruolo.operatoreProvinciale && !this.ruolo.consulenteRespo) ? this.ruolo.codiceFiscaleAzienda : null,
      denominazioneAzienda: this.ruolo && (!this.ruolo.amministratore && !this.ruolo.operatoreProvinciale && !this.ruolo.consulenteRespo) ? this.ruolo.denominazioneAzienda : null,
      codiceFiscaleLavoratore: null,
      cognomeLavoratore: null,
      nomeLavoratore: null,
      dataInvioInserimentoDa: new Date(now.setMonth(now.getMonth() - 1)),
      dataInvioInserimentoA: new Date(),
      statoComunicaziones: null,
      codiceRegionale: null,
      tipoComunicazione: null,
      tipoComunicazioneTu: null,
      provinciaProtocollo: null,
      annoProtocollo: null,
      numeroProtocollo: null
    };
    this.formRicercaComunicazione = this.formPristine;
  }

  onResetForm() {
    this.ricercaEffettuata = false;
  }

  async onCerca(formRicercaComunicazione: RicercaComunicazione) {
    this.ricercaEffettuata = false;
    this.ricercaComunicazioneToSend = Utils.clone(formRicercaComunicazione);
    this.logService.info(this.constructor.name, 'onCercaComunicazioni', this.formRicercaComunicazione);
    this.effettuaRicerca(this.currentPaginationData.page, this.currentPaginationData.limit);
  }


  async onChangePaginationData(paginationData: PaginationDataChange) {
    this.effettuaRicerca(paginationData.page, paginationData.limit, paginationData.sort);
  }

  private async effettuaRicerca(page: number, limit: number, sort?: SortEvent) {
    await this.utilitiesService.showSpinner();
    console.log('effettuaRicerca');
    try {
      this.pagedResponse = await this.comunicazioneService.postRicercaComunicazioni(
        this.ricercaComunicazioneToSend,
        page,
        limit,
        sort ? sort.column : undefined,
        sort ? sort.direction : undefined
        )
        .toPromise();

      this.ricercaEffettuata = true;

      // collassa l'accordion quando la ricerca ottiene dei risultati. Commentare la seguente istruzione per disabilitare l'automatismo
      this.accordionRicercaComunicazioni.collapseAll();
      // const idStatiComunicazione: string[] = this.ricercaComunicazioneToSend.statoComunicaziones.map
      // this.router.navigate(
      //   [
      //     this.clearObject({
      //       codiceFiscaleAzienda: this.ricercaComunicazioneToSend.codiceFiscaleAzienda,
      //       denominazioneAzienda: this.ricercaComunicazioneToSend.denominazioneAzienda,
      //       codiceFiscaleLavoratore: this.ricercaComunicazioneToSend.codiceFiscaleLavoratore,
      //       cognomeLavoratore: this.ricercaComunicazioneToSend.cognomeLavoratore,
      //       nomeLavoratore: this.ricercaComunicazioneToSend.nomeLavoratore,
      //       dataInvioInserimentoDa: this.ricercaComunicazioneToSend.dataInvioInserimentoDa ? this.ricercaComunicazioneToSend.dataInvioInserimentoDa.toISOString() : null,
      //       dataInvioInserimentoA: this.ricercaComunicazioneToSend.dataInvioInserimentoA ? this.ricercaComunicazioneToSend.dataInvioInserimentoA.toISOString() : null,
      //       idStatiComunicazione: this.ricercaComunicazioneToSend.statoComunicaziones.map(el => el.id),
      //       codiceRegionale: this.ricercaComunicazioneToSend.codiceRegionale,
      //       idTipoComunicazione: this.ricercaComunicazioneToSend.tipoComunicazione ? this.ricercaComunicazioneToSend.tipoComunicazione.id : null,
      //       idTipoComunicazioneTu: this.ricercaComunicazioneToSend.tipoComunicazioneTu ? this.ricercaComunicazioneToSend.tipoComunicazioneTu.id : null,
      //       idProvinciaProtocollo: this.ricercaComunicazioneToSend.provinciaProtocollo ? this.ricercaComunicazioneToSend.provinciaProtocollo.id : null,
      //       annoProtocollo: this.ricercaComunicazioneToSend.annoProtocollo,
      //       numeroProtocollo: this.ricercaComunicazioneToSend.numeroProtocollo,
      //       page,
      //       limit,
      //       sort: JSON.stringify(sort)
      //     })
      //   ],
      //   {
      //       relativeTo: this.activatedRoute,
      //       // NOTE: By using the replaceUrl option, we don't increase the Browser's
      //       // history depth with every filtering keystroke. This way, the List-View
      //       // remains a single item in the Browser's history, which allows the back
      //       // button to function much more naturally for the user.
      //       replaceUrl: true
      //   }
      //);
      // const clearObject = this.clearObject({
      //   codiceFiscaleAzienda: this.ricercaComunicazioneToSend.codiceFiscaleAzienda,
      //   denominazioneAzienda: this.ricercaComunicazioneToSend.denominazioneAzienda,
      //   codiceFiscaleLavoratore: this.ricercaComunicazioneToSend.codiceFiscaleLavoratore,
      //   cognomeLavoratore: this.ricercaComunicazioneToSend.cognomeLavoratore,
      //   nomeLavoratore: this.ricercaComunicazioneToSend.nomeLavoratore,
      //   dataInvioInserimentoDa: this.ricercaComunicazioneToSend.dataInvioInserimentoDa ? this.ricercaComunicazioneToSend.dataInvioInserimentoDa.toISOString() : null,
      //   dataInvioInserimentoA: this.ricercaComunicazioneToSend.dataInvioInserimentoA ? this.ricercaComunicazioneToSend.dataInvioInserimentoA.toISOString() : null,
      //   idStatiComunicazione: this.ricercaComunicazioneToSend.statoComunicaziones.map(el => el.id),
      //   codiceRegionale: this.ricercaComunicazioneToSend.codiceRegionale,
      //   idTipoComunicazione: this.ricercaComunicazioneToSend.tipoComunicazione ? this.ricercaComunicazioneToSend.tipoComunicazione.id : null,
      //   idTipoComunicazioneTu: this.ricercaComunicazioneToSend.tipoComunicazioneTu ? this.ricercaComunicazioneToSend.tipoComunicazioneTu.id : null,
      //   idProvinciaProtocollo: this.ricercaComunicazioneToSend.provinciaProtocollo ? this.ricercaComunicazioneToSend.provinciaProtocollo.id : null,
      //   annoProtocollo: this.ricercaComunicazioneToSend.annoProtocollo,
      //   numeroProtocollo: this.ricercaComunicazioneToSend.numeroProtocollo,
      //   page,
      //   limit,
      //   sort: JSON.stringify(sort)
      // })
      this.ricercaComunicazioneParams = {
       ricercaComunicazioni: {
         formRicercaComunicazione: this.ricercaComunicazioneToSend,
         page:  page,
         limit: limit,
         sort: JSON.stringify(sort),
         goSearch: this.ricercaComunicazioneParams && this.ricercaComunicazioneParams.ricercaComunicazioni && this.ricercaComunicazioneParams.ricercaComunicazioni.goSearch ? this.ricercaComunicazioneParams.ricercaComunicazioni.goSearch : false
       }
        
      }
      this.comonlStorageService.setRicercaComunicazioniParams(this.ricercaComunicazioneParams);

    } catch (e) {
      this.logService.error(this.constructor.name, 'effettuaRicerca', e);
      this.alertMessageService.setErrorMsg(e.error);
      // TODO gestire errore
      // this.utilitiesService.handleApiErrors(e, 'SIDEBAR.RMS.RICHIESTA.TITLE');
    } finally {
      await this.utilitiesService.hideSpinner();
    }
  }

  private clearObject<T>(obj: T): T {
    const res = {} as T;
    Object.keys(obj)
      .filter(key => obj[key] !== null && obj[key] !== undefined)
      .forEach(key => res[key] = obj[key]);
    return res;
  }

  openModal() {
    const modalRef = this.modalService.open(ModalNuovaComunicazioneComponent, { centered: true, size: 'lg'});
    modalRef.componentInstance.title = 'Seleziona Comunicazione';
  }

  eliminaComunicazione(paginationData: PaginationDataChange){
      if(
          this.pagedResponse && this.pagedResponse.totalElements === 1
        ){
          this.ricercaEffettuata = false;
        }
      this.effettuaRicerca(paginationData.page, paginationData.limit);
      this.accordionRicercaComunicazioni.expandAll();
  }

}
