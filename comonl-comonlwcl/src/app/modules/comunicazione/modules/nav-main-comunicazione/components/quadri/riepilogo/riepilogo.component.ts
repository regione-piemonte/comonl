/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Router } from '@angular/router';
import { ModalInvioComunicazioneComponent } from './../../../../../components/modal-invio-comunicazione/modal-invio-comunicazione.component';
import { PromptModalService } from './../../../../../../comonlcommon/services/prompt-modal.service';
import { AlertMessageService } from './../../../../../../comonlcommon/services/alert-message.service';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { NgbAccordion, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CONSTANTS_MODE, TIPO_CONTRATTI, TIPO_TRACCIATO } from 'src/app/constants';
import { Comunicazione, Ruolo, TipoContratti, TipoTracciato, ComunicazioneService, WrapperComunicazione, ApiError, ComunicazioneControlliService, VariazioneSomm, DecodificaService } from 'src/app/modules/comonlapi';
import { LogService, UtilitiesService } from 'src/app/services';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { AdComponent } from '../../../models/ad-component';
import { PersistenceComunicazioneWreapper } from '../../nav-main-comunicazione/nav-main-comunicazione.component';
import { TranslateService } from '@ngx-translate/core';
import { marker } from '@biesbjerg/ngx-translate-extract-marker';
import { Utils } from 'src/app/utils';

@Component({
  selector: 'comonl-riepilogo',
  templateUrl: './riepilogo.component.html',
  styleUrls: ['./riepilogo.component.scss']
})
export class RiepilogoComponent implements OnInit, AdComponent {

  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="riepilogo"]';

  @ViewChild('accordionRiepilogoDatiGenerali', {static: false}) accordionRiepilogoDatiGenerali: NgbAccordion;
  @ViewChild('accordionRiepilogoDatiAzienda', {static: false}) accordionRiepilogoDatiAzienda: NgbAccordion;
  @ViewChild('accordionRiepilogoDatiAziendaUtilizzatrice', {static: false}) accordionRiepilogoDatiAziendaUtilizzatrice: NgbAccordion;
  @ViewChild('accordionRiepilogoDatiLavoratore', {static: false}) accordionRiepilogoDatiLavoratore: NgbAccordion;
  @ViewChild('accordionRiepilogoDatiProroga', {static: false}) accordionRiepilogoDatiProroga: NgbAccordion;
  @ViewChild('accordionRiepilogoDatiCessazione', {static: false}) accordionRiepilogoDatiCessazione: NgbAccordion;
  @ViewChild('accordionRiepilogoDatiTrasformazione', {static: false}) accordionRiepilogoDatiTrasformazione: NgbAccordion;
  @ViewChild('accordionRiepilogoDatiTrasferimento', {static: false}) accordionRiepilogoDatiTrasferimento: NgbAccordion;
  @ViewChild('accordionRiepilogoDatiDistacco', {static: false}) accordionRiepilogoDatiDistacco: NgbAccordion;
  @ViewChild('accordionRiepilogoDatiMissione', {static: false}) accordionRiepilogoDatiMissione: NgbAccordion;
  @ViewChild('accordionRiepilogoDatiRapporto', {static: false}) accordionRiepilogoDatiRapporto: NgbAccordion;
  @ViewChild('accordionRiepilogoDatiTutore', {static: false}) accordionRiepilogoDatiTutore: NgbAccordion;
  @ViewChild('accordionRiepilogoDatiGeneraliVardatori', {static: false}) accordionRiepilogoDatiGeneraliVardatori: NgbAccordion;
  @ViewChild('accordionRiepilogoDatiVariazioneDatoreLavoro', {static: false}) accordionRiepilogoDatiVariazioneDatoreLavoro: NgbAccordion;
  @ViewChild('accordionRiepilogoDatiVariazioneRagioneSociale', {static: false}) accordionRiepilogoDatiVariazioneRagioneSociale: NgbAccordion;
  @ViewChild('accordionRiepilogoDatiSediLavoratoriVardatori', {static: false}) accordionRiepilogoDatiSediLavoratoriVardatori: NgbAccordion;
  activeIds = [
               'panelDatiGenerali',
               'panelDatiAzienda',
               'panelDatiAziendaUtilizzatrice',
               'panelDatiLavoratore',
               'panelDatiProroga',
               'panelDatiCessazione',
               'panelDatiTrasformazione',
               'panelDatiTrasferimento',
               'panelDatiDistacco',
               'panelDatiMissione',
               'panelDatiRapporto',
               'panelDatiTutore',
               'panelDatiGeneraliVardatori',
               'panelDatiVariazioneDatoreLavoro',
               'panelDatiVariazioneRagioneSociale',
               'panelDatiSediLavoratoriVardatori',
              ];


  formRiepilogo: FormGroup;

  @Input() data: any;
  @Output() datiForm = new EventEmitter<any>();
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();
  @Output() persistenceNotification = new EventEmitter<PersistenceComunicazioneWreapper>();

  comunicazione: Comunicazione;
  comunicazioneToSave: Comunicazione;
  routingParams;
  configurazioneQuadri;

  warnings: ApiError[] = [];

  ruolo: Ruolo;

  saveStatusSuccess: boolean;

  get f() {
    return this.formRiepilogo.controls as any;
  }

  get VIEW_MODE() {
    const nuovaComParams = this.routingParams;
    return nuovaComParams.mode === CONSTANTS_MODE.VIEW;
  }

  get INS_MODE() {
    const nuovaComParams = this.routingParams;
    return nuovaComParams.mode === CONSTANTS_MODE.INS;
  }

  get RETTIFICA_MODE() {
    const nuovaComParams = this.routingParams;
    return nuovaComParams.mode === CONSTANTS_MODE.RETTIFICA;
  }

  get ANNULLO_MODE() {
    const nuovaComParams = this.routingParams;
    return nuovaComParams.mode === CONSTANTS_MODE.ANNULLO;
  }

  get EDIT_MODE() {
    const nuovaComParams = this.routingParams;
    return nuovaComParams.mode === CONSTANTS_MODE.EDIT;
  }

  get DISABLE_INTERACTIONS_VIEW(): boolean {
    return this.VIEW_MODE || this.EDIT_MODE;
  }

  // CONFIGURAZIONE QUADRI PER RIEPILOGO

  get REQUIRED_DATI_GENERALI(): boolean {
    return this.configurazioneQuadri !== 'VAR_RS' ||
      this.configurazioneQuadri !== 'VAR_T';
  }

  get REQUIRED_AZIENDA_UTILIZZATRICE(): boolean {
    return this.configurazioneQuadri === '2SOMEMISS' ||
      this.configurazioneQuadri === '2PROEMISS' ||
      this.configurazioneQuadri === '3' ||
      this.configurazioneQuadri === '4' ||
      this.configurazioneQuadri === '6' ||
      this.configurazioneQuadri === '8';
  }

  get REQUIRED_LAVORATORE(): boolean {
    return this.configurazioneQuadri === 'ASS' ||
      this.configurazioneQuadri === '0' ||
      this.configurazioneQuadri === '2SOMEMISS' ||
      this.configurazioneQuadri === 'PRO' ||
      this.configurazioneQuadri === '1' ||
      this.configurazioneQuadri === '2PROEMISS' ||
      this.configurazioneQuadri === '3' ||
      this.configurazioneQuadri === 'TRS' ||
      this.configurazioneQuadri === '5' ||
      this.configurazioneQuadri === '4' ||
      this.configurazioneQuadri === '6' ||
      this.configurazioneQuadri === '7' ||
      this.configurazioneQuadri === 'TRD' ||
      this.configurazioneQuadri === '8' ||
      this.configurazioneQuadri === 'CES' ||
      this.configurazioneQuadri === '9';
  }

  get REQUIRED_RAPPORTO_LAVORO(): boolean {
    return this.configurazioneQuadri === 'ASS' ||
      this.configurazioneQuadri === '0' ||
      this.configurazioneQuadri === '2SOMEMISS' ||
      this.configurazioneQuadri === 'PRO' ||
      this.configurazioneQuadri === '1' ||
      this.configurazioneQuadri === '2PROEMISS' ||
      this.configurazioneQuadri === '3' ||
      this.configurazioneQuadri === 'TRS' ||
      this.configurazioneQuadri === '5' ||
      this.configurazioneQuadri === '4' ||
      this.configurazioneQuadri === '6' ||
      this.configurazioneQuadri === '7' ||
      this.configurazioneQuadri === 'TRD' ||
      this.configurazioneQuadri === '8' ||
      this.configurazioneQuadri === 'CES' ||
      this.configurazioneQuadri === '9';
  }

  get REQUIRED_TUTORE(): boolean {
    return this.configurazioneQuadri === 'ASS' ||
      this.configurazioneQuadri === '0' ||
      this.configurazioneQuadri === '2SOMEMISS' ||
      this.configurazioneQuadri === 'PRO' ||
      this.configurazioneQuadri === '1' ||
      this.configurazioneQuadri === 'TRS' ||
      this.configurazioneQuadri === '5' ||
      this.configurazioneQuadri === '7' ||
      this.configurazioneQuadri === 'TRD' ||
      this.configurazioneQuadri === 'CES' ||
      this.configurazioneQuadri === '9';
  }

  get REQUIRED_DATI_MISSIONE(): boolean {
    return this.comunicazioneToSave.tipoTracciato !== TIPO_TRACCIATO.UNILAV &&
      (this.configurazioneQuadri === '2SOMEMISS' ||
      this.configurazioneQuadri === '2PROEMISS' ||
      this.configurazioneQuadri === '3' ||
      this.configurazioneQuadri === '4' ||
      this.configurazioneQuadri === '6' ||
      this.configurazioneQuadri === '8');
  }

  get REQUIRED_PROROGA(): boolean {
    return this.configurazioneQuadri === 'PRO' ||
      this.configurazioneQuadri === '1' ||
      this.configurazioneQuadri === '2PROEMISS' ||
      this.configurazioneQuadri === '3';
  }

  get REQUIRED_TRASFORMAZIONE(): boolean {
    return this.configurazioneQuadri === 'TRS' ||
      this.configurazioneQuadri === '5' ||
      this.configurazioneQuadri === '4';
  }

  // SE SI DOVESSE SPACCARE PROVARE AD AGGIUNGERE ALLA RIGA 207 NELLA PARENTESI PRIMA DEL '?' QUESTO: && this.comunicazioneToSave.rapporto.trasformazionerl.codTrasformazionirlMin
  get REQUIRED_TRASFERIMENTO(): boolean {
    return (this.comunicazioneToSave.rapporto && this.comunicazioneToSave.rapporto.trasformazionerl) ? this.comunicazioneToSave.rapporto.trasformazionerl.codTrasformazionirlMin === 'TL' : false ||
    (this.configurazioneQuadri === '6' ||
      this.configurazioneQuadri === '7' ||
      this.configurazioneQuadri === 'TRD'
    );
  }

  get REQUIRED_DISTACCO(): boolean {
    if(this.IS_UNILAV){
      return (this.comunicazioneToSave.rapporto && this.comunicazioneToSave.rapporto.trasformazionerl) ? this.comunicazioneToSave.rapporto.trasformazionerl.codTrasformazionirlMin === 'DL' : false ||
      (this.configurazioneQuadri === '6' ||
        this.configurazioneQuadri === '7' ||
        this.configurazioneQuadri === 'TRD'
      );
    }else{
      return false;
    }
   
  }

  get REQUIRED_CESSAZIONE(): boolean {
    return this.configurazioneQuadri === '8' ||
      this.configurazioneQuadri === 'CES' ||
      this.configurazioneQuadri === '9';
  }

  get REQUIRED_DATI_GENERALI_VARDATORI(): boolean {
    return this.configurazioneQuadri === 'VAR_RS' ||
      this.configurazioneQuadri === 'VAR_T';
  }

  get REQUIRED_VARIAZIONE_RAGIONE_SOCIALE(): boolean {
    return this.configurazioneQuadri === 'VAR_RS';
  }

  get REQUIRED_VARIAZIONE_DATORE_LAVORO(): boolean {
    return this.configurazioneQuadri === 'VAR_T';
  }

  get REQUIRED_VARIAZIONE_SEDI_LAVORATORI(): boolean {
    return this.configurazioneQuadri === 'VAR_T';
  }

  get IS_UNILAV(): boolean{
    return this.comunicazioneToSave.tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID;
  }

  get IS_UNISOMM(): boolean{
    return this.comunicazioneToSave.tipoTracciato.id === TIPO_TRACCIATO.UNISOMM.ID;
  }

  get TUTORE(): boolean {
    if (this.comunicazioneToSave.tipoComunicazione.id !== 'VAR') {
      const tipoTracciato: TipoTracciato = this.comunicazioneToSave.tipoTracciato;
      if (
        tipoTracciato.id !== TIPO_TRACCIATO.VARDATORI.ID ||
        tipoTracciato.id !== TIPO_TRACCIATO.URGENZA.ID
        ) {
          const tipoContratti: TipoContratti = this.comunicazioneToSave.rapporto.tipoContratti;
          const codTipoContrattoMin = tipoContratti.codTipoContrattoMin;
          if (
              codTipoContrattoMin === TIPO_CONTRATTI.TIROCINIO.COD_TIPO_CONTARTTO_MIN ||
              codTipoContrattoMin === TIPO_CONTRATTI.APPR_QUAL_DIPLOMA_PROF_DIPLOMA_ISTR_SECONDARIA_SUPERIORE_CERT_SPECIALIZ_TECNICA_SUPERIORE.COD_TIPO_CONTARTTO_MIN ||
              codTipoContrattoMin === TIPO_CONTRATTI.APPR_PROF_CONTRATTO_MESTIERE.COD_TIPO_CONTARTTO_MIN ||
              codTipoContrattoMin === TIPO_CONTRATTI.APPR_ALTA_FORMAZIONE_RICERCA.COD_TIPO_CONTARTTO_MIN ||
              codTipoContrattoMin === TIPO_CONTRATTI.APPR_EX_ART16_196_97.COD_TIPO_CONTARTTO_MIN ||
              codTipoContrattoMin === TIPO_CONTRATTI.APPR_ESPLETAMENTO_DIRITTO_DOVERE_ISTRUZIONE_FORMAZIONE.COD_TIPO_CONTARTTO_MIN ||
              codTipoContrattoMin === TIPO_CONTRATTI.APPR_PROFESSIONALIZZANTE.COD_TIPO_CONTARTTO_MIN ||
              codTipoContrattoMin === TIPO_CONTRATTI.APPR_ACQUISIZIONE_DIPLOMA_PERCORSI_ALTA_FORMAZIONE.COD_TIPO_CONTARTTO_MIN ||
              codTipoContrattoMin === TIPO_CONTRATTI.APPR_PROF_CONTR_MESTIERE_LAV_MOBILITA.COD_TIPO_CONTARTTO_MIN ||
              codTipoContrattoMin === TIPO_CONTRATTI.APPR_ALTA_FORMAZIONE_RICERCA_LAVORATORI_MOBILITA.COD_TIPO_CONTARTTO_MIN ||
              codTipoContrattoMin === TIPO_CONTRATTI.TIROCINIO_ESTIVO_ORIENTAMENTO.COD_TIPO_CONTARTTO_MIN
            ) {
              return true;
            }
      }
    }
    return false;
  }

  variazioneSomm: VariazioneSomm

  constructor(
    private logService: LogService,
    private utilitiesService: UtilitiesService,
    private comonlStorageService: ComonlStorageService,
    private comunicazioneService: ComunicazioneService,
    private comunicazioneControlliService: ComunicazioneControlliService,
    private alertMessageService: AlertMessageService,
    private router: Router,
    private translateService: TranslateService,
    private promptModalService: PromptModalService,
    private decodificaService: DecodificaService 
  ) {
    this.initFormRiepilogo();
  }


  async ngOnInit() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    this.logService.info(this.constructor.name, 'ngOnInit');
    this.utilitiesService.scrollTo(RiepilogoComponent.SCROLL_TARGET);
    this.comonlStorageService.ruolo$.subscribe(async item => {
      this.ruolo = item;
    });
    this.comunicazione = this.data.comunicazione;
    this.comunicazioneToSave = this.data.comunicazioneToSave;
    this.routingParams = this.data.routingParams;
    const nuovaComunicazioneParams = this.routingParams.nuovaComParams;
    this.configurazioneQuadri = this.data.configurazioneQuadri;
    this.saveStatusSuccess = this.data.saveStatusSuccess;
    if (this.saveStatusSuccess) {
      this.alertMessageService.setSuccessSingleMsg('Salvataggio effettuato con successo');
    }
    try {
      let idTipoSomministrazione: number;
      if(this.comunicazione.tipoSomministrazione){
        idTipoSomministrazione = this.comunicazione.tipoSomministrazione.id;
        const idTipoComunicazione: string = this.comunicazione.tipoComunicazione ? this.comunicazione.tipoComunicazione.id : null;
        this.variazioneSomm = await this.decodificaService.getVariazioneSommByTipoSommAndTipoCom(idTipoSomministrazione,idTipoComunicazione).toPromise();

      }
      if(!this.VIEW_MODE){
        if (this.comunicazioneToSave.apiWarnings && this.comunicazioneToSave.apiWarnings.length > 0) {
          this.warnings = this.comunicazioneToSave.apiWarnings;
        }
        const res = await this.comunicazioneControlliService.chkRiepilogo(this.comunicazione.id).toPromise();
        if (res && res.length > 0) {
          this.warnings = this.warnings.concat(res);
        }
        if (this.warnings && this.warnings.length > 0) {
          this.alertMessageService.setWarningMsg(this.warnings);
        }
      }
    } catch (e) {
      if (e.error && e.error.length > 0) {
        this.alertMessageService.setErrorMsg(e.error);
      } else {
        this.alertMessageService.setSingleErrorMsg(e);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  initFormRiepilogo() {
    this.formRiepilogo = new FormGroup({});
  }

  onSubmit() {
  }

  async onClickInviaComunicazione(comunicazione, typeMessage) {
    console.log('comunicazione da inviare: ' + JSON.stringify(comunicazione));
    const title = this.translate(marker('APP.SEND_COMUNICAZIONE'));
    const message = this.translate(marker(typeMessage));
    const pYes = this.translate(marker('APP.SEND'));
    const pNo = this.translate(marker('APP.ANNULLA'));

    const userChoice = await this.promptModalService.openPrompt(title, message, pYes, pNo, 'warning');
    if (userChoice) {
      this.utilitiesService.showSpinner();
      this.alertMessageService.emptyMessages();
      const wrapperComunicazione: WrapperComunicazione = {
        ruolo: this.ruolo,
        comunicazione: comunicazione
      };
      try {
        let res: Comunicazione[];
        // let comunicazioneToSave: Comunicazione;
        res = await this.comunicazioneService.inviaComunicazione(wrapperComunicazione).toPromise();
        // console.log('res comunicazione: ' + JSON.stringify(res));
        if (res) {
          // comunicazioneToSave = await this.comunicazioneService.getComunicazioneById(res.id).toPromise();
          // console.log('comunicazioneToSave dopo l\'invio: ' + JSON.stringify(comunicazioneToSave));
          // this.alertMessageService.setSuccessSingleMsg('Salvataggio effettuato con successo');
         
          this.router.navigate(['/invio-comunicazione'], { state: { esitoComunicazione: res }});
        }
      } catch (e) {
        console.log(e);
        this.alertMessageService.setErrorMsg(e.error);
      } finally {
        this.utilitiesService.hideSpinner();
      }
    }
  }

  private translate(key: string) {
    return this.translateService.instant(key);
  }
}
