/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { DelegaService } from './../../../comonlapi/api/delega.service';
import { Delega } from './../../../comonlapi/model/delega';
import { PagedResponseRicercaDeleghe } from './../../../comonlapi/model/pagedResponseRicercaDeleghe';
import { FormRicercaDelega } from './../../../comonlapi/model/formRicercaDelega';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { NgbAccordion } from '@ng-bootstrap/ng-bootstrap';
import { PaginationDataChange, SortEvent } from 'src/app/models';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { LogService, UtilitiesService } from 'src/app/services';
import { Utils } from 'src/app/utils';
import { Ruolo } from 'src/app/modules/comonlapi';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';

@Component({
  selector: 'comonl-accreditamento-deleghe',
  templateUrl: './accreditamento-deleghe.component.html',
  styleUrls: ['./accreditamento-deleghe.component.scss']
})
export class AccreditamentoDelegheComponent implements OnInit {
  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="ricercaDelega"]';


  @ViewChild('accordionRicercaDeleghe', {static: false}) accordionRicercaDeleghe: NgbAccordion;
  activeIds = ['panelRicercaDeleghe'];

  ricercaEffettuata = false;
  currentPaginationData: PaginationDataChange;

  formRicercaDelega: FormRicercaDelega; // mappa il form della ricerca
  formPristine: FormRicercaDelega;   // form iniziale
  ricercaDelegaToSend: FormRicercaDelega; // obj passato al servizio

  pagedResponse: PagedResponseRicercaDeleghe;
  ruolo: Ruolo;

  flgDettaglio = false;

  constructor(
    private logService: LogService,
    private activatedRoute: ActivatedRoute,
    private utilitiesService: UtilitiesService,
    private router: Router,
    private alertMessageService: AlertMessageService,
    private delegaService: DelegaService,
    private comonlStorageService: ComonlStorageService
  ) { }

  ngOnInit() {
    this.utilitiesService.scrollTo(AccreditamentoDelegheComponent.SCROLL_TARGET);
    this.comonlStorageService.ruolo$.subscribe(el => this.ruolo = el);
    this.initForm();
    this.currentPaginationData = {
      limit: this.activatedRoute.snapshot.params.limit || 10,
      page: this.activatedRoute.snapshot.params.page || 0,
      offset: 0,
      sort: this.activatedRoute.snapshot.params.sort
    };
    this.patchFormParams(this.activatedRoute.snapshot.params);
  }

  patchFormParams(params: Params) {
    if (!params) {
      return;
    }

    this.formRicercaDelega = params;
  }

  private initForm() {
    this.formPristine = {
      codiceFiscaleDelegato: null,
      cognomeDelegato: null,
      nomeDelegato: null,
      codiceFiscaleDelegante: null,
      cognomeDelegante: null,
      nomeDelegante: null,
      codiceFiscaleImpresa: null,
      denominazione: null,
      statoDelegas: null,
    };
    this.formRicercaDelega = this.formPristine;
  }

  onResetForm() {
    this.ricercaEffettuata = false;
  }

  async onCerca(formRicercaDelega: Delega) {
    this.ricercaEffettuata = false;
    this.ricercaDelegaToSend = Utils.clone(formRicercaDelega);
    this.logService.info(this.constructor.name, 'onCercaDeleghe', this.formRicercaDelega);
    this.effettuaRicerca(this.currentPaginationData.page, this.currentPaginationData.limit);
  }

  async onChangePaginationData(paginationData: PaginationDataChange) {
    this.effettuaRicerca(paginationData.page, paginationData.limit, paginationData.sort);
  }

  private async effettuaRicerca(page: number, limit: number, sort?: SortEvent) {
    await this.utilitiesService.showSpinner();
    console.log('effettuaRicerca');
    try {
      this.pagedResponse = await this.delegaService.postRicercaDeleghe(
        this.ricercaDelegaToSend,
        page,
        limit,
        sort ? sort.column : undefined,
        sort ? sort.direction : undefined
        )
        .toPromise();

      this.ricercaEffettuata = true;

      // collassa l'accordion quando la ricerca ottiene dei risultati. Commentare la seguente istruzione per disabilitare l'automatismo
      this.accordionRicercaDeleghe.collapseAll();

      this.router.navigate(
        [
          this.clearObject({
            codiceFiscaleDelegato: this.ricercaDelegaToSend.codiceFiscaleDelegato,
            cognomeDelegato: this.ricercaDelegaToSend.cognomeDelegato,
            nomeDelegato: this.ricercaDelegaToSend.nomeDelegato,
            codiceFiscaleDelegante: this.ricercaDelegaToSend.codiceFiscaleDelegante,
            cognomeDelegante: this.ricercaDelegaToSend.cognomeDelegante,
            nomeDelegante: this.ricercaDelegaToSend.nomeDelegante,
            codiceFiscaleImpresa: this.ricercaDelegaToSend.codiceFiscaleImpresa,
            denominazione: this.ricercaDelegaToSend.denominazione,
            idStatiDelega: this.ricercaDelegaToSend.statoDelegas.map(el => el.id),
            page,
            limit,
            sort: JSON.stringify(sort)
          })
        ],
        {
            relativeTo: this.activatedRoute,
            replaceUrl: true
        }
      );

    } catch (e) {
      this.logService.error(this.constructor.name, 'effettuaRicerca', e);
      this.alertMessageService.setErrorMsg(e.error);
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

  goToNuovaDelega(modalita: string) {
    this.router.navigate(['./dettaglio-delega'], { state: {mode: modalita, parametriRicerca: this.formRicercaDelega}});
  }
}
