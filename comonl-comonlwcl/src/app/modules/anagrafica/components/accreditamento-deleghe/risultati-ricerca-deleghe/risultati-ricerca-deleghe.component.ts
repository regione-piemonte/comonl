/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { FormRicercaDelega } from './../../../../comonlapi/model/formRicercaDelega';
import { PagedResponseRicercaDeleghe } from './../../../../comonlapi/model/pagedResponseRicercaDeleghe';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { PaginationDataChange } from 'src/app/models';
import { Router } from '@angular/router';
import { UtilitiesService } from 'src/app/services';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { TranslateService } from '@ngx-translate/core';
import { marker } from '@biesbjerg/ngx-translate-extract-marker';
import { DelegaService } from 'src/app/modules/comonlapi';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { Utils } from 'src/app/utils';

@Component({
  selector: 'comonl-risultati-ricerca-deleghe',
  templateUrl: './risultati-ricerca-deleghe.component.html',
  styleUrls: ['./risultati-ricerca-deleghe.component.scss']
})
export class RisultatiRicercaDelegheComponent implements OnInit {

  @Input() pagedResponse: PagedResponseRicercaDeleghe;

  @Input() formRicercaDelega: FormRicercaDelega;


  @Input() currentPaginationData: PaginationDataChange;
  @Output() readonly changePaginationData = new EventEmitter<PaginationDataChange>();

  constructor(
    private router: Router,
    private utilitiesService: UtilitiesService,
    private delegaService: DelegaService,
    private promptModalService: PromptModalService,
    private translateService: TranslateService,
    private alertMessageService: AlertMessageService
  ) { }

  ngOnInit() {}

  onChangePaginationData(event: PaginationDataChange) {
    this.currentPaginationData = event;
    this.changePaginationData.emit(event);
  }

  goToDettaglioDelega(delegaToSend: any, modalita: string) {
    // console.log('delegaToSend: ' + JSON.stringify(delegaToSend));
    this.router.navigate(['./dettaglio-delega'], { state: { mode: modalita, delega: delegaToSend } });
  }

  async onClickOpenModalOperazione(delega, stato, typeMessage) {
    console.log(JSON.stringify(delega));
    const title = this.translate(marker('SIDEBAR.ACCREDITAMENTO_DELEGHE'));
    const message = this.translate(marker(typeMessage));
    const pYes = this.translate(marker('APP.CONFIRM'));
    const pNo = this.translate(marker('APP.BACK'));

    const userChoice = await this.promptModalService.openPrompt(title, message, pYes, pNo, 'warning');

    try {
      if (userChoice) {
        switch (stato) {
          case 'revoca':
            delega.statoDelega.dsStatoDelega = 'REVOCATA';
            delega.statoDelega.id = '4';
            this.aggiornaStato(delega);
            break;
          case 'elimina':
            delega.statoDelega.dsStatoDelega = 'ANNULLATA';
            delega.statoDelega.id = '2';
            this.aggiornaStato(delega);
            break;
          case 'valida':
            delega.statoDelega.dsStatoDelega = 'VALIDATA';
            delega.statoDelega.id = '3';
            this.aggiornaStato(delega);
            break;
        }
      }
      // console.log('delega dopo aggiornaStatoDelega: ' + JSON.stringify(this.delega));
    } catch (e) {
      this.alertMessageService.setWarningMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  async aggiornaStato(delega) {
    this.utilitiesService.showSpinner();
    console.log('delega prima di invocare il servizio: ' + JSON.stringify(delega));
    const delegaAggiornata = await this.delegaService.aggiornaStatoDelega(delega).toPromise();
    this.utilitiesService.hideSpinner();
    console.log('delega aggiornata' + JSON.stringify(delegaAggiornata));
  }

  private translate(key: string) {
    return this.translateService.instant(key);
  }

  async onClickEsportaElencoDeleghe(){
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    try{
      const res = await this.delegaService.stampaRicercaDeleghe(this.formRicercaDelega,'response').toPromise();
      const fileName = Utils.extractFileNameFromContentDisposition(res.headers.get('Content-Disposition'));
      this.utilitiesService.downloadBlobFile(fileName, res.body);
    }catch(e){
      this.alertMessageService.setSingleErrorMsg(e);
      if(e.error && e.error.length){
        this.alertMessageService.setErrorMsg(e.error);
      }
    }finally{
      this.utilitiesService.hideSpinner();
    }
  }
}
