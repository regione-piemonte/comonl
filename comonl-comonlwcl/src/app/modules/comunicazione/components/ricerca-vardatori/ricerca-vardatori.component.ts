/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { RicercaVardatori } from './../../../comonlapi/model/ricercaVardatori';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { NgbAccordion, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PaginationDataChange, SortEvent } from 'src/app/models';
import { ComunicazioneService, FormRicercaComunicazione, PagedResponseRicercaComunicazioni, Ruolo } from 'src/app/modules/comonlapi';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { LogService, UtilitiesService } from 'src/app/services';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { Utils } from 'src/app/utils';
import { ModalNuovaVardatoriComponent } from '../modal-nuova-vardatori/modal-nuova-vardatori.component';
import { RicercaComunicazioniParams } from 'src/app/models/ricerca-comunicazioni-params';

@Component({
  selector: 'comonl-ricerca-vardatori',
  templateUrl: './ricerca-vardatori.component.html',
  styleUrls: ['./ricerca-vardatori.component.scss']
})
export class RicercaVardatoriComponent implements OnInit {
  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="ricercaVardatori"]';

  @ViewChild('accordionRicercaVardatori', {static: false}) accordionRicercaVardatori: NgbAccordion;
  activeIds = ['panelRicercaVardatori'];

  ricercaEffettuata = false;
  currentPaginationData: PaginationDataChange;

  formRicercaVardatori: FormRicercaComunicazione; // mappa il form della ricerca
  formPristine: FormRicercaComunicazione;   // form iniziale
  ricercaVardatoriToSend: FormRicercaComunicazione; // obj passato al servizio

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
    this.utilitiesService.scrollTo(RicercaVardatoriComponent.SCROLL_TARGET);
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

    this.formRicercaVardatori = this.ricercaComunicazioneParams.ricercaComunicazioni;
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
    this.formRicercaVardatori = this.formPristine;
  }

  onResetForm() {
    this.ricercaEffettuata = false;
  }

  async onCerca(formRicercaVardatori: RicercaVardatori) {
    this.ricercaEffettuata = false;
    this.ricercaVardatoriToSend = Utils.clone(formRicercaVardatori);
    this.logService.info(this.constructor.name, 'onCercaVardatori', this.formRicercaVardatori);
    this.effettuaRicerca(this.currentPaginationData.page, this.currentPaginationData.limit);
  }


  async onChangePaginationData(paginationData: PaginationDataChange) {
    this.effettuaRicerca(paginationData.page, paginationData.limit, paginationData.sort);
  }

  private async effettuaRicerca(page: number, limit: number, sort?: SortEvent) {
    await this.utilitiesService.showSpinner();
    console.log('effettuaRicerca');
    try {
      this.pagedResponse = await this.comunicazioneService.postRicercaVardatori(
        this.ricercaVardatoriToSend,
        page,
        limit,
        sort ? sort.column : undefined,
        sort ? sort.direction : undefined
        )
        .toPromise();

      this.ricercaEffettuata = true;

      // collassa l'accordion quando la ricerca ottiene dei risultati. Commentare la seguente istruzione per disabilitare l'automatismo
      this.accordionRicercaVardatori.collapseAll();
      this.ricercaComunicazioneParams = {
        ricercaComunicazioni: {
          formRicercaComunicazione: this.ricercaVardatoriToSend,
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

  goToNuovaComunicazione() {
    // TODO da implemnetare
    this.modalService.open(ModalNuovaVardatoriComponent, { centered: true, size: 'lg'});
    // this.router.navigate(['/dettaglio-comunicazione']);
  }

  eliminaComunicazione(paginationData: PaginationDataChange){
    if(
        this.pagedResponse && this.pagedResponse.totalElements === 1
      ){
        this.ricercaEffettuata = false;
      }
    this.effettuaRicerca(paginationData.page, paginationData.limit);
    this.accordionRicercaVardatori.expandAll();
}

}
