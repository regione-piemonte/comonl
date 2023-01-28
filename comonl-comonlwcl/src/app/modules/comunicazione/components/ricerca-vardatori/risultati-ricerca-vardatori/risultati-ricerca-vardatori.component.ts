/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { marker } from '@biesbjerg/ngx-translate-extract-marker';
import { TranslateService } from '@ngx-translate/core';
import { PaginationDataChange } from 'src/app/models';
import { FormRicercaComunicazione, RicercaComunicazione, PagedResponseRicercaComunicazioni, ComunicazioneService, Ruolo } from 'src/app/modules/comonlapi';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { UtilitiesService } from 'src/app/services';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { Utils } from 'src/app/utils';
import { NavComunicazioneParams } from '../../../modules/nav-main-comunicazione/models/nav-comunicazione-params';
import { TIPO_QUADRO_COM } from '../../../modules/nav-main-comunicazione/services/loader-service';

@Component({
  selector: 'comonl-risultati-ricerca-vardatori',
  templateUrl: './risultati-ricerca-vardatori.component.html',
  styleUrls: ['./risultati-ricerca-vardatori.component.scss']
})
export class RisultatiRicercaVardatoriComponent implements OnInit {

  @Input() pagedResponse: PagedResponseRicercaComunicazioni;

  @Input() formRicercaVardatori: FormRicercaComunicazione;


  @Input() currentPaginationData: PaginationDataChange;
  @Output() readonly changePaginationData = new EventEmitter<PaginationDataChange>();
  @Output() readonly eliminaComunicazioneNotify = new EventEmitter<PaginationDataChange>();

  ruolo: Ruolo;

  constructor(
    private router: Router,
    private utilitiesService: UtilitiesService,
    private comunicazioneService: ComunicazioneService,
    private alertMessaggeService: AlertMessageService,
    private promptModalService: PromptModalService,
    private translateService: TranslateService,
    private comonlStorageService: ComonlStorageService
  ) { }

  ngOnInit() {
    this.comonlStorageService.ruolo$.subscribe((el: Ruolo) => {this.ruolo = el})
  }

  onChangePaginationData(event: PaginationDataChange) {
    this.currentPaginationData = event;
    this.changePaginationData.emit(event);
  }


  async onClickElimina(idComunicazione: number) {
    const title = this.translate(marker('SIDEBAR.RICERCA_COMUNICAZIONI'));
    const message = this.translate(marker('MESSAGES.SYS-SYS-A-0011'));
    const pYes = this.translate(marker('APP.YES'));
    const pNo = this.translate(marker('APP.NO'));

    const userChoice = await this.promptModalService.openPrompt(title, message, pYes, pNo, 'warning');

    if(userChoice){
      this.eliminaComunicazione(idComunicazione);
    }
  }

  private async eliminaComunicazione(idComunicazione: number) {
    this.utilitiesService.showSpinner();
    this.alertMessaggeService.emptyMessages();
    try{
      const res = await this.comunicazioneService.cancellaComunicazione(idComunicazione).toPromise();
      this.eliminaComunicazioneNotify.emit(this.currentPaginationData);
    }catch(e){
      if(e.error && e.error.length > 0){
        this.alertMessaggeService.setErrorMsg(e.error);
      }else{
        this.alertMessaggeService.setSingleErrorMsg(e);
      }
    }finally{
      this.utilitiesService.hideSpinner();
    }
  }

  private translate(key: string) {
    return this.translateService.instant(key);
  }

  async goToStampa(idComunicazione: number) {
    console.log('passo da stampa');
    this.utilitiesService.showSpinner();
    try {
      console.log('stampa per la comunicazione con ID: ' + idComunicazione);
      const res = await this.comunicazioneService.stampaComunicazioneById(idComunicazione,this.ruolo.operatoreProvinciale, 'response').toPromise();
      if (res) {
        const fileName = Utils.extractFileNameFromContentDisposition(res.headers.get('Content-Disposition'));
        this.utilitiesService.downloadBlobFile(fileName, res.body);
      }
    } catch (e) {
      console.log(e);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  goToDettaglio(ricercaComunicazione: RicercaComunicazione, mode: string) {
    const idComunicazione = ricercaComunicazione.idComDComunicazione;
    console.log('idComunicazione: ' + idComunicazione);
    // const tipoComunicazione = ricercaComunicazione.idComTTipoComunicazione;
    // const tipoVariazione = ricercaComunicazione.idComVariazioneSomm;
    let configurazioneQuadri;
    console.log('codFiscDatorePrec: ' + ricercaComunicazione.codiceFiscaleDatorePrec);
    if (Utils.isNullOrUndefined(ricercaComunicazione.codiceFiscaleDatorePrec)) {
        configurazioneQuadri = TIPO_QUADRO_COM.VARIAZIONE_DATORE_RAG_SOCIALE;
    } else {
      configurazioneQuadri = TIPO_QUADRO_COM.VARIAZIONE_DATORE_TRASF;
    }
    console.log('configurazione quadri: ' + configurazioneQuadri);
    if (mode === 'edit') {
      if (ricercaComunicazione.idComTTipoComunicazioneTu === 4) {
        mode = 'annullo';
      }
      if (ricercaComunicazione.idComTTipoComunicazioneTu === 3) {
        mode = 'rettifica';
      }
    }
    const navComunicazioneParams: NavComunicazioneParams = {
      idComunicazione: idComunicazione,
      mode: mode,
      configurazioneQuadri,
      flgNuovaCO: false
    };
    this.router.navigate(['/nav-main-comunicazione'], { state: {parametriNavigazione: navComunicazioneParams}});
  }

  // goToRettifica(idComunicazione: number) {
  //   const navComunicazioneParams: NavComunicazioneParams = {
  //     idComunicazione: idComunicazione,
  //     mode: CONSTANTS_MODE.RETTIFICA
  //   };
  //   this.router.navigate(['/nav-main-comunicazione'], { state: {parametriNavigazione: navComunicazioneParams}});
  // }


  // async goToAnnulla(idComunicazione: number) {
  //   const navComunicazioneParams: NavComunicazioneParams = {
  //     idComunicazione: idComunicazione,
  //     mode: CONSTANTS_MODE.ANNULLO
  //   };
  //   this.router.navigate(['/nav-main-comunicazione'], { state: {parametriNavigazione: navComunicazioneParams}});
  // }

  // nuovaCoAggiornamento(el: RicercaComunicazione) {
  // }

  // showNuovaCoAggiornamento(el: RicercaComunicazione): boolean {
  //   const statoComunicazione = el.idComTStatoComunicazione;
  //   const tipoComunicazioneTu = el.idComTTipoComunicazioneTu;
  //   const tipoComunicazione = el.idComTTipoComunicazione;
  //   const tipoTracciato = el.idTipoTracciato;
  //   return (tipoTracciato === TIPO_TRACCIATO.UNILAV.ID
  //     || tipoTracciato === TIPO_TRACCIATO.UNISOMM.ID
  //     || tipoTracciato === TIPO_TRACCIATO.VARDATORI.ID)
  //     && statoComunicazione === STATO_COMUNICAZIONE.VALIDATA.ID
  //     && tipoComunicazioneTu !== TIPO_COMUNICAZIONE_TU_ID_ANNULLAMENTO
  //     && tipoComunicazione !== TIPO_COMUNICAZIONE.CESSAZIONE.ID;
  // }

  // showNuovaAssunzione(el: RicercaComunicazione): boolean {
  //   const statoComunicazione = el.idComTStatoComunicazione;
  //   const tipoTracciato = el.idTipoTracciato;
  //   return tipoTracciato === TIPO_TRACCIATO.URGENZA.ID &&
  //             statoComunicazione === STATO_COMUNICAZIONE.VALIDATA.ID;
  // }

  // showDettaglio(el: RicercaComunicazione): boolean {
  //   const tipoTracciato = el.idTipoTracciato;
  //   const statoComunicazione = el.idComTStatoComunicazione;
  //   return (
  //       (tipoTracciato === TIPO_TRACCIATO.UNILAV.ID ||
  //       tipoTracciato === TIPO_TRACCIATO.UNISOMM.ID ||
  //       tipoTracciato === TIPO_TRACCIATO.URGENZA.ID ||
  //       tipoTracciato === TIPO_TRACCIATO.UNIDOM.ID) &&
  //       statoComunicazione !== STATO_COMUNICAZIONE.INSERITA.ID
  //   );
  // }

  // showElimina(el: RicercaComunicazione): boolean {
  //   const statoComunicazione = el.idComTStatoComunicazione;
  //   const tipoTracciato = el.idTipoTracciato;
  //   const tipoComunicazioneTu = el.idComTTipoComunicazioneTu;
  //   return (
  //         tipoTracciato === TIPO_TRACCIATO.UNILAV.ID ||
  //         tipoTracciato === TIPO_TRACCIATO.UNISOMM.ID ||
  //         tipoTracciato === TIPO_TRACCIATO.URGENZA.ID
  //       ) && statoComunicazione === STATO_COMUNICAZIONE.INSERITA.ID
  //         && tipoComunicazioneTu !== TIPO_COMUNICAZIONE_TU_ID_ANNULLAMENTO;
  // }
  // showAnnulla(el: RicercaComunicazione): boolean {
  //   const statoComunicazione = el.idComTStatoComunicazione;
  //   const tipoTracciato = el.idTipoTracciato;
  //   const tipoComunicazioneTu = el.idComTTipoComunicazioneTu;
  //   return (
  //         tipoTracciato === TIPO_TRACCIATO.UNILAV.ID ||
  //         tipoTracciato === TIPO_TRACCIATO.UNISOMM.ID
  //       ) && statoComunicazione === STATO_COMUNICAZIONE.VALIDATA.ID
  //         && tipoComunicazioneTu !== TIPO_COMUNICAZIONE_TU_ID_ANNULLAMENTO;
  // }
  // showRettifica(el: RicercaComunicazione): boolean {
  //   const statoComunicazione = el.idComTStatoComunicazione;
  //   const tipoComunicazioneTu = el.idComTTipoComunicazioneTu;
  //   const tipoTracciato = el.idTipoTracciato;
  //   return (
  //         tipoTracciato === TIPO_TRACCIATO.UNILAV.ID ||
  //         tipoTracciato === TIPO_TRACCIATO.UNISOMM.ID
  //       ) && statoComunicazione === STATO_COMUNICAZIONE.VALIDATA.ID
  //         && tipoComunicazioneTu !== TIPO_COMUNICAZIONE_TU_ID_ANNULLAMENTO;
  // }
  // showStampaPdf(el: RicercaComunicazione): boolean {
  //   const statoComunicazione = el.idComTStatoComunicazione;
  //   const tipoTracciato = el.idTipoTracciato;
  //   return  (
  //             (tipoTracciato === TIPO_TRACCIATO.UNILAV.ID || tipoTracciato === TIPO_TRACCIATO.UNISOMM.ID) &&
  //             statoComunicazione === STATO_COMUNICAZIONE.INSERITA.ID ||
  //             statoComunicazione === STATO_COMUNICAZIONE.TRANSITO.ID ||
  //             statoComunicazione === STATO_COMUNICAZIONE.VALIDATA.ID ||
  //             statoComunicazione === STATO_COMUNICAZIONE.ANNULLATA.ID ||
  //             statoComunicazione === STATO_COMUNICAZIONE.RETTIFICATA.ID
  //           ) || (
  //             tipoTracciato === TIPO_TRACCIATO.URGENZA.ID &&
  //             statoComunicazione === STATO_COMUNICAZIONE.INSERITA.ID ||
  //             statoComunicazione === STATO_COMUNICAZIONE.TRANSITO.ID ||
  //             statoComunicazione === STATO_COMUNICAZIONE.VALIDATA.ID
  //           );

  // }

  // showModifica(el: RicercaComunicazione): boolean {
  //   const tipoTracciato = el.idTipoTracciato;
  //   const statoComunicazione = el.idComTStatoComunicazione;
  //   const tipoComunicazioneTu = el.idComTTipoComunicazioneTu;

  //   return (
  //   (tipoTracciato === TIPO_TRACCIATO.UNILAV.ID ||
  //       tipoTracciato === TIPO_TRACCIATO.UNISOMM.ID ||
  //       tipoTracciato === TIPO_TRACCIATO.URGENZA.ID ||
  //       tipoTracciato === TIPO_TRACCIATO.UNIDOM.ID)
  //       && statoComunicazione === STATO_COMUNICAZIONE.INSERITA.ID
  //       && tipoComunicazioneTu !== TIPO_COMUNICAZIONE_TU_ID_ANNULLAMENTO
  //   );
  // }

  async onClickEsportaElencoComunicazioni() {
    this.utilitiesService.showSpinner();
    this.alertMessaggeService.emptyMessages();
    try {
      const res = await this.comunicazioneService.stampaRicercaComunicazioniVardatori(this.formRicercaVardatori, 'response').toPromise();
      const fileName = Utils.extractFileNameFromContentDisposition(res.headers.get('Content-Disposition'));
      this.utilitiesService.downloadBlobFile(fileName, res.body);
    } catch (e) {
      if (e.error && e.error.length > 0) {
        this.alertMessaggeService.setErrorMsg(e.error);
      }else{
        this.alertMessaggeService.setSingleErrorMsg(e);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

}
