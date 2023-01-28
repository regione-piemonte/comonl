/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { NgbAccordion } from '@ng-bootstrap/ng-bootstrap';
import { PaginationDataChange, SortEvent } from 'src/app/models';
import { AnagraficaDelegatoService, FormRicercaAccreditamentoAnagrafiche, PagedResponseRicercaAccreditamentoAzienda } from 'src/app/modules/comonlapi';
import { PagedResponseRicercaAccreditamentoConsulente } from 'src/app/modules/comonlapi/model/pagedResponseRicercaAccreditamentoConsulente';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { LogService, UtilitiesService } from 'src/app/services';
import { Utils } from 'src/app/utils';

@Component({
  selector: 'comonl-ricerca-accreditamento-anagrafiche',
  templateUrl: './ricerca-accreditamento-anagrafiche.component.html',
  styleUrls: ['./ricerca-accreditamento-anagrafiche.component.scss']
})
export class RicercaAccreditamentoAnagraficheComponent implements OnInit {

  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="ricercaAccreditamento"]';
  @ViewChild('accordionRicercaAccredAnag', {static: false}) accordionRicercaAccredAnag: NgbAccordion;
  activeIds = ['panelRicercaAccredAnag'];

  ricercaEffettuata = false;
  currentPaginationData: PaginationDataChange;

  formPristine: FormRicercaAccreditamentoAnagrafiche;
  formRicerca: FormRicercaAccreditamentoAnagrafiche;
  ricercaToSend: FormRicercaAccreditamentoAnagrafiche;
  tipoRicerca: string;

  pagedResponseConsulente: PagedResponseRicercaAccreditamentoConsulente;
  pagedResponseAzienda: PagedResponseRicercaAccreditamentoAzienda;

  constructor(
    private logService: LogService,
    private activatedRoute: ActivatedRoute,
    private utilitiesService: UtilitiesService,
    private router: Router,
    private anagraficaDelegatoService: AnagraficaDelegatoService,
    private alertMessageService: AlertMessageService
  ) { }

  ngOnInit() {
    this.utilitiesService.scrollTo(RicercaAccreditamentoAnagraficheComponent.SCROLL_TARGET);
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

    this.formRicerca = params;
  }

  private initForm(){
    this.formPristine = {
      cf: null,
      cognome: null,
      nome: null,
      cfAzienda: null,
      getpIva: null,
      denom: null,
      annullate: null
    }
    this.formRicerca = this.formPristine;
  }


  onResetForm() {
    this.ricercaEffettuata = false;
  }

  async onCerca(ricerca: RicercaAccreditamentoAnagrafiche) {
    this.ricercaEffettuata = false;
    this.formRicerca = ricerca.formRicerca;
    this.ricercaToSend = Utils.clone(ricerca.formRicerca);
    this.logService.info(this.constructor.name, 'onCercaAccreditamentoAnagrafiche', this.formRicerca);
    this.tipoRicerca = ricerca.tipoRicerca;
    this.effettuaRicerca(this.currentPaginationData.page, this.currentPaginationData.limit);
  }

  ripristinaAnnulla(){
    if(
        (this.pagedResponseConsulente && this.pagedResponseConsulente.totalElements === 1) ||
        (this.pagedResponseAzienda && this.pagedResponseAzienda.totalElements === 1)
      ){
        this.ricercaEffettuata = false;
      }
    this.effettuaRicerca(this.currentPaginationData.page, this.currentPaginationData.limit);
    this.accordionRicercaAccredAnag.expandAll();
  }

  async onChangePaginationData(paginationData: PaginationDataChange) {
    this.effettuaRicerca(paginationData.page, paginationData.limit, paginationData.sort);
  }

  //private async effettuaRicerca(page: number, limit: number, sort?: SortEvent) {}

  private async effettuaRicerca(page: number, limit: number, sort?: SortEvent) {
    await this.utilitiesService.showSpinner();
    console.log("effettuaRicerca");
    try {

      if(this.tipoRicerca === "Consulente"){
        this.pagedResponseConsulente = await this.anagraficaDelegatoService.postRicercaAccreditamentoConsulente(
          this.ricercaToSend,
          page,
          limit,
          sort ? sort.column : undefined,
          sort ? sort.direction : undefined
          )
          .toPromise();
          this.pagedResponseAzienda = undefined;
      }else{
        this.pagedResponseAzienda = await this.anagraficaDelegatoService.postRicercaAccreditamentoAzienda(
          this.ricercaToSend,
          page,
          limit,
          sort ? sort.column : undefined,
          sort ? sort.direction : undefined
          )
          .toPromise();
          this.pagedResponseConsulente = undefined
      }


      this.ricercaEffettuata = true;

      // collassa l'accordion quando la ricerca ottiene dei risultati. Commentare la seguente istruzione per disabilitare l'automatismo
      this.accordionRicercaAccredAnag.collapseAll();

      this.router.navigate(
        [
          this.clearObject({
            tipoForm: this.tipoRicerca,
            cf: this.formRicerca.cf,
            cognome: this.formRicerca.cognome,
            nome: this.formRicerca.nome,
            cfAzienda: this.formRicerca.cfAzienda,
            getpIva: this.formRicerca.getpIva,
            denom: this.formRicerca.denom,
            annullate: this.formRicerca.annullate,
            page,
            limit,
            sort: JSON.stringify(sort)
          })
        ],
        {
            relativeTo: this.activatedRoute,
            // NOTE: By using the replaceUrl option, we don't increase the Browser's
            // history depth with every filtering keystroke. This way, the List-View
            // remains a single item in the Browser's history, which allows the back
            // button to function much more naturally for the user.
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

}

export interface RicercaAccreditamentoAnagrafiche{
  formRicerca?: FormRicercaAccreditamentoAnagrafiche,
  tipoRicerca?: string
}
