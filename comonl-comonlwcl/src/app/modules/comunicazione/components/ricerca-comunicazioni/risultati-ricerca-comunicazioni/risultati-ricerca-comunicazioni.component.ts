/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { logging } from 'protractor';
import { CONSTANTS_MODE, STATO_COMUNICAZIONE, TIPO_COMUNICAZIONE, TIPO_COMUNICAZIONE_TU, TIPO_COMUNICAZIONE_TU_ID_ANNULLAMENTO, TIPO_TRACCIATO } from 'src/app/constants';
import { PaginationDataChange } from 'src/app/models';
import { WrapperHelper } from 'src/app/models/wrappwer-helper';
import { FormRicercaComunicazione, PagedResponseRicercaComunicazioni, RicercaComunicazione, ComunicazioneService, Ruolo } from 'src/app/modules/comonlapi';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { UtilitiesService } from 'src/app/services';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { Utils } from 'src/app/utils';
import { NavComunicazioneParams } from '../../../modules/nav-main-comunicazione/models/nav-comunicazione-params';
import { TIPO_QUADRO_COM } from '../../../modules/nav-main-comunicazione/services/loader-service';
import { ModalNuovaComunicazioneComponent } from '../../modal-nuova-comunicazione/modal-nuova-comunicazione.component';
import { TranslateService } from '@ngx-translate/core';
import { marker } from '@biesbjerg/ngx-translate-extract-marker';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';


@Component({
  selector: 'comonl-risultati-ricerca-comunicazioni',
  templateUrl: './risultati-ricerca-comunicazioni.component.html',
  styleUrls: ['./risultati-ricerca-comunicazioni.component.scss']
})
export class RisultatiRicercaComunicazioniComponent implements OnInit {


  @Input() pagedResponse: PagedResponseRicercaComunicazioni;

  @Input() formRicercaComunicazione: FormRicercaComunicazione;


  @Input() currentPaginationData: PaginationDataChange;
  @Output() readonly changePaginationData = new EventEmitter<PaginationDataChange>();
  @Output() readonly eliminaComunicazioneNotify = new EventEmitter<PaginationDataChange>();

  ruolo: Ruolo;


  constructor(
    private router: Router,
    private utilitiesService: UtilitiesService,
    private comonlStorageService: ComonlStorageService,
    private comunicazioneService: ComunicazioneService,
    private alertMessaggeService: AlertMessageService,
    private modalService: NgbModal,
    private translateService: TranslateService,
    private promptModalService: PromptModalService,
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

    if (userChoice) {
      this.eliminaComunicazione(idComunicazione);
    }
  }

  private async eliminaComunicazione(idComunicazione: number) {
    this.utilitiesService.showSpinner();
    this.alertMessaggeService.emptyMessages();
    try {
      const res = await this.comunicazioneService.cancellaComunicazione(idComunicazione).toPromise();
      this.eliminaComunicazioneNotify.emit(this.currentPaginationData);
      if (res) {
        this.alertMessaggeService.setSuccessSingleMsg('La comunicazione e\' stata cancellata con successo');
      }
    } catch (e) {
      if (e.error && e.error.length > 0) {
        this.alertMessaggeService.setErrorMsg(e.error);
      } else {
        this.alertMessaggeService.setSingleErrorMsg(e);
      }
    } finally {
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
    console.log('idComunicazione----------------->' + idComunicazione);
    const tipoComunicazione = ricercaComunicazione.idComTTipoComunicazione;
    const tipoSomm = ricercaComunicazione.idComTTipoSomministrazione;
    const tipoVariazione = ricercaComunicazione.idComVariazioneSomm;
    // const tipoVariazioneSomm =
    let configurazioneQuadri;
    switch (tipoComunicazione) {
      case TIPO_COMUNICAZIONE.ASSUNZIONE.ID: {
         if (tipoSomm === 0) {
           // assunzione in somministrazione
           configurazioneQuadri = TIPO_QUADRO_COM.SOMMINISTRAZIONE;
         } else if (tipoSomm === 2) {
           // assunzione in somministrazione e missione
           configurazioneQuadri = TIPO_QUADRO_COM.SOMMINISTRAZIONE_E_MISSIONE;
         } else {
           // assunzione UNILAV
           configurazioneQuadri = TIPO_QUADRO_COM.ASSUNZIONE;
         }
         break;
      }
      case TIPO_COMUNICAZIONE.PROROGA.ID: {
        if (1 === tipoVariazione) {
          // proroga del rapporto di lavoro in assenza di missione
          configurazioneQuadri = TIPO_QUADRO_COM.PROROGA_DEL_RAPPORTO_DI_LAVORO_IN_ASSENZA_DI_MISSIONE;
        } else if (2 === tipoVariazione) {
          // proroga del rapporto di lavoro e della missione
          configurazioneQuadri = TIPO_QUADRO_COM.PROROGA_DEL_RAPPORTO_DI_LAVORO_E_DELLA_MISSIONE;
        } else if (3 === tipoVariazione) {
          // proroga della mission in caso di rapporto indeterminato
          configurazioneQuadri = TIPO_QUADRO_COM.PROROGA_DELLA_MISSIONE_IN_CASO_DI_RAPPORTO_A_TEMPO_INDETERMINATO;
        } else {
          // proroga
          configurazioneQuadri = TIPO_QUADRO_COM.PROROGA;
        }
        break;
      }

      case TIPO_COMUNICAZIONE.TRASFORMAZIONE.ID: {
        if (4 === tipoVariazione) {
          // trasformazione in costanza di missione
          configurazioneQuadri = TIPO_QUADRO_COM.TRASFORMAZIONE_IN_COSTANZA_DI_MISSIONE;
        } else if (5 === tipoVariazione) {
          // // trasformazione in costanza in assenza di missione
          configurazioneQuadri = TIPO_QUADRO_COM.TRASFORMAZIONE_DEL_RAPPORTO_DI_LAVORO_IN_ASSENZA_DI_MISSIONE;
        }  else {
          // trasformazione
          configurazioneQuadri = TIPO_QUADRO_COM.TRASFORMAZIONE;
        }
        break;
      }

      case TIPO_COMUNICAZIONE.TRASFERIMENTO_DISTACCO.ID: {
        if (6 === tipoVariazione) {
          // trasferimento di altra seda lavoro ditta utilizzatrice
          configurazioneQuadri = TIPO_QUADRO_COM.TRASFERIMENTO_ALTRA_SEDE_LAVORO_DITTA_UTILIZZATRICE;
        } else if (7 === tipoVariazione) {
          // trasferimento altra sede operativa
          configurazioneQuadri = TIPO_QUADRO_COM.TRASFERIMENTO_ALTRA_SEDE_OPERATIVA_AGENZIA;
        } else {
          // if (comunicazione.getRapporto() != null && comunicazione.getRapporto().getTrasformazionerl() != null
          //     && "DL".equals(comunicazione.getRapporto().getTrasformazionerl().getCodTrasformazionirlMin())) {
          //   // distacco
          //   configurazioneQuadri = TIPO_QUADRO_COM.TRASFERIMENTO_DISTACCO
          // } else {
          //   // trasferimento
          // }
          configurazioneQuadri = TIPO_QUADRO_COM.TRASFERIMENTO_DISTACCO;
        }
        break;
      }

      case TIPO_COMUNICAZIONE.CESSAZIONE.ID: {
        if (8 === tipoVariazione) {
          // cessazione missione
          configurazioneQuadri = TIPO_QUADRO_COM.CESSAZIONE_MISSIONE;
        } else if (9 === tipoVariazione) {
          // cessazione del rapporto di lavoro
          configurazioneQuadri = TIPO_QUADRO_COM.CESSAZIONE_DEL_RAPPORTO_DI_LAVORO;
        } else {
          // cessazione
          configurazioneQuadri = TIPO_QUADRO_COM.CESSAZIONE;
        }
        break;
      }

      // case TIPO_COMUNICAZIONE.VARIAZIONE_DATORE.ID: {
      //   break;
      // }
      case TIPO_COMUNICAZIONE.URGENZA.ID: {
        configurazioneQuadri = TIPO_QUADRO_COM.URGENZA;
        break;
      }
    }

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

  nuovaCoAggiornamento(el: RicercaComunicazione) {
    const modalRef = this.modalService.open(ModalNuovaComunicazioneComponent, { centered: true, size: 'lg'});
    modalRef.componentInstance.title = 'Seleziona Comunicazione';
    modalRef.componentInstance.comunicazione = el;
    modalRef.componentInstance.tipo = 'assunzione';
    modalRef.componentInstance.idComunicazione = el.idComDComunicazione;
  }

  nuovaAssunzione(el: RicercaComunicazione) {
    const modalRef = this.modalService.open(ModalNuovaComunicazioneComponent, { centered: true, size: 'lg'});
    modalRef.componentInstance.title = 'Seleziona Comunicazione';
    modalRef.componentInstance.comunicazione = el;
    modalRef.componentInstance.tipo = 'urgenza';
    modalRef.componentInstance.idComunicazione = el.idComDComunicazione;
  }

  showNuovaCoAggiornamento(el: RicercaComunicazione): boolean {
    const statoComunicazione = el.idComTStatoComunicazione;
    const tipoComunicazioneTu = el.idComTTipoComunicazioneTu;
    const tipoComunicazione = el.idComTTipoComunicazione;
    const tipoTracciato = el.idTipoTracciato;
    const flgCurrentRecord: string = el.flgCurrentRecord;
    return (
      tipoTracciato === TIPO_TRACCIATO.UNILAV.ID
      || tipoTracciato === TIPO_TRACCIATO.UNISOMM.ID
      || tipoTracciato === TIPO_TRACCIATO.VARDATORI.ID)
      && statoComunicazione === STATO_COMUNICAZIONE.VALIDATA.ID
      && tipoComunicazioneTu !== TIPO_COMUNICAZIONE_TU_ID_ANNULLAMENTO
      && tipoComunicazione !== TIPO_COMUNICAZIONE.CESSAZIONE.ID
      && flgCurrentRecord === 'S';
  }

  showNuovaAssunzione(el: RicercaComunicazione): boolean {
    const statoComunicazione = el.idComTStatoComunicazione;
    const tipoTracciato = el.idTipoTracciato;
    const flgCurrentRecord = el.flgCurrentRecord;
    return tipoTracciato === TIPO_TRACCIATO.URGENZA.ID &&
              statoComunicazione === STATO_COMUNICAZIONE.VALIDATA.ID &&
              flgCurrentRecord === 'S';
  }

  showDettaglio(el: RicercaComunicazione): boolean {
    const tipoTracciato = el.idTipoTracciato;
    const statoComunicazione = el.idComTStatoComunicazione;
    return (
        (tipoTracciato === TIPO_TRACCIATO.UNILAV.ID ||
        tipoTracciato === TIPO_TRACCIATO.UNISOMM.ID ||
        tipoTracciato === TIPO_TRACCIATO.URGENZA.ID ||
        tipoTracciato === TIPO_TRACCIATO.UNIDOM.ID) &&
        statoComunicazione !== STATO_COMUNICAZIONE.INSERITA.ID
    );
  }

  showElimina(el: RicercaComunicazione): boolean {
    const statoComunicazione = el.idComTStatoComunicazione;
    const tipoTracciato = el.idTipoTracciato;
    const tipoComunicazioneTu = el.idComTTipoComunicazioneTu;
    return (
          tipoTracciato === TIPO_TRACCIATO.UNILAV.ID ||
          tipoTracciato === TIPO_TRACCIATO.UNISOMM.ID ||
          tipoTracciato === TIPO_TRACCIATO.URGENZA.ID
        ) && statoComunicazione === STATO_COMUNICAZIONE.INSERITA.ID;
  }

  showAnnulla(el: RicercaComunicazione): boolean {
    const statoComunicazione = el.idComTStatoComunicazione;
    const tipoTracciato = el.idTipoTracciato;
    const tipoComunicazioneTu = el.idComTTipoComunicazioneTu;
    const flgCurrentRecord: string = el.flgCurrentRecord;
    const primiDueNumeriCodiceRegComunicazione = el.codiceComunicazioneReg ? el.codiceComunicazioneReg.substring(0, 2) : null;
    return (
          tipoTracciato === TIPO_TRACCIATO.UNILAV.ID ||
          tipoTracciato === TIPO_TRACCIATO.UNISOMM.ID
        ) && statoComunicazione === STATO_COMUNICAZIONE.VALIDATA.ID
          && tipoComunicazioneTu !== TIPO_COMUNICAZIONE_TU_ID_ANNULLAMENTO
          && flgCurrentRecord === 'S'
          && primiDueNumeriCodiceRegComunicazione === '13';
  }
  showRettifica(el: RicercaComunicazione): boolean {
    const statoComunicazione = el.idComTStatoComunicazione;
    const tipoComunicazioneTu = el.idComTTipoComunicazioneTu;
    const tipoTracciato = el.idTipoTracciato;
    const flgCurrentRecord: string = el.flgCurrentRecord;
    const primiDueNumeriCodiceRegComunicazione = el.codiceComunicazioneReg ? el.codiceComunicazioneReg.substring(0, 2) : null;
    return (
          tipoTracciato === TIPO_TRACCIATO.UNILAV.ID ||
          tipoTracciato === TIPO_TRACCIATO.UNISOMM.ID
        ) && statoComunicazione === STATO_COMUNICAZIONE.VALIDATA.ID
          && tipoComunicazioneTu !== TIPO_COMUNICAZIONE_TU_ID_ANNULLAMENTO
          && flgCurrentRecord === 'S'
          && primiDueNumeriCodiceRegComunicazione === '13';
  }
  showStampaPdf(el: RicercaComunicazione): boolean {
    const statoComunicazione = el.idComTStatoComunicazione;
    const tipoTracciato = el.idTipoTracciato;
    return  (
              (tipoTracciato === TIPO_TRACCIATO.UNILAV.ID || tipoTracciato === TIPO_TRACCIATO.UNISOMM.ID) &&
              statoComunicazione === STATO_COMUNICAZIONE.INSERITA.ID ||
              statoComunicazione === STATO_COMUNICAZIONE.TRANSITO.ID ||
              statoComunicazione === STATO_COMUNICAZIONE.VALIDATA.ID ||
              statoComunicazione === STATO_COMUNICAZIONE.ANNULLATA.ID ||
              statoComunicazione === STATO_COMUNICAZIONE.RETTIFICATA.ID
            ) || (
              tipoTracciato === TIPO_TRACCIATO.URGENZA.ID &&
              statoComunicazione === STATO_COMUNICAZIONE.INSERITA.ID ||
              statoComunicazione === STATO_COMUNICAZIONE.TRANSITO.ID ||
              statoComunicazione === STATO_COMUNICAZIONE.VALIDATA.ID

            );

  }

  showModifica(el: RicercaComunicazione): boolean {
    const tipoTracciato = el.idTipoTracciato;
    const statoComunicazione = el.idComTStatoComunicazione;
    const tipoComunicazioneTu = el.idComTTipoComunicazioneTu;

    return (
    (tipoTracciato === TIPO_TRACCIATO.UNILAV.ID ||
        tipoTracciato === TIPO_TRACCIATO.UNISOMM.ID ||
        tipoTracciato === TIPO_TRACCIATO.URGENZA.ID ||
        tipoTracciato === TIPO_TRACCIATO.UNIDOM.ID)
        && statoComunicazione === STATO_COMUNICAZIONE.INSERITA.ID
    );
  }

  async onClickEsportaElencoComunicazioni() {
    this.utilitiesService.showSpinner();
    this.alertMessaggeService.emptyMessages();
    try {
      const res = await this.comunicazioneService.stampaRicercaComunicazioni(this.formRicercaComunicazione, 'response').toPromise();
      const fileName = Utils.extractFileNameFromContentDisposition(res.headers.get('Content-Disposition'));
      this.utilitiesService.downloadBlobFile(fileName, res.body);
    } catch (e) {
      this.alertMessaggeService.setSingleErrorMsg(e);
      if (e.error && e.error.length) {
        this.alertMessaggeService.setErrorMsg(e.error);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

}
