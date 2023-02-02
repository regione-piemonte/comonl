/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CONSTANTS_MODE, CONTROL_STATE, MESI, TIPO_ORARIO, TYPE_DECODIFICA_GENERICA } from 'src/app/constants';
import { CategLavAssObbl, ComunicazioneService, Datore, DecodificaService, Rapporto, Ruolo, TipoOrario, WrapperComunicazione, WrapperRapporto } from 'src/app/modules/comonlapi';
import {
  Ccnl,
  Comunicazione,
  DecodificaGenerica,
  Istat2001livello5,
  LivelloRetribuzione,
} from 'src/app/modules/comonlapi';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { LogService, UserService, UtilitiesService } from 'src/app/services';
import { Utils } from 'src/app/utils';
import { PersistenceComunicazioneWreapper } from '../../nav-main-comunicazione/nav-main-comunicazione.component';
@Component({
  selector: 'comonl-dati-missione',
  templateUrl: './dati-missione.component.html',
  styleUrls: ['./dati-missione.component.scss']
})
export class DatiMissioneComponent implements OnInit {

  //test push
  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="dati-missione"]';

  @Output() readonly datiForm = new EventEmitter<any>();
  @Output() readonly resetForm = new EventEmitter();
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();
  @Output() persistenceNotification = new EventEmitter<PersistenceComunicazioneWreapper>();

  @Input() data: any;

  formDatiMissione: FormGroup;
  showForm = false;

  listaTipoOrario: TipoOrario[] = [];
  listaCategLavAssObbl: CategLavAssObbl[] = [];
  comunicazione: Comunicazione;
  comunicazioneToSave: Comunicazione;
  routingParams;

  obbligNumOreSettMedie: boolean;
  obbligCategLavAssObbl: boolean;
  obbligGiornateLavPreviste: boolean;

  saveStatusSuccess: boolean;

  ccnlScaduto: boolean = false;
  livelloRetribuzioneScaduto: boolean = false;
  istatScaduto: boolean = false;

  ruolo: Ruolo;

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


  get INVALIDITA_ANNULLO_MODE(): boolean{
    if(this.ANNULLO_MODE){
      const ccnl: Ccnl = this.f.ccnl.value;
      const livelloRetribuzione: LivelloRetribuzione =  this.f.livelloRetribuzione.value;
      const istat: Istat2001livello5 =  this.f.istatLivello5.value;
      const tipoOrario: TipoOrario = this.f.tipoOrario.value;
      const flgAssunzioneObbligatoria = this.f.flgAssunzioneObbligatoria.value;
      const categLavAssObbl: CategLavAssObbl = this.f.categLavAssObbl.value;
      const decodificheObbligatorieInvalid: boolean = (ccnl && ccnl.id) && (livelloRetribuzione && livelloRetribuzione.id) && (istat && istat.id) && (tipoOrario && tipoOrario.id) ? false : true;
      let categLavAssObblInvalid = false;
      if(flgAssunzioneObbligatoria === 'S'){
        categLavAssObblInvalid = categLavAssObbl && categLavAssObbl.id ? false : true;
      }
      return decodificheObbligatorieInvalid || categLavAssObblInvalid;
    }else{
      return false;
    }
  }

  get INVALIDITA_FORM(){
    if(this.ANNULLO_MODE){
      return false;
    }else{
      return !this.formDatiMissione.valid
    }
  }

  get f() {
    return this.formDatiMissione.controls as any;
  }
  constructor(
    private logService: LogService,
    private decodificaService: DecodificaService,
    private utilitiesService: UtilitiesService,
    private formBuilder: FormBuilder,
    private promptModalService: PromptModalService,
    private alertMessageService: AlertMessageService,
    private comunicazioneService: ComunicazioneService
  ) {}

  async ngOnInit() {
    await this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    this.utilitiesService.scrollTo(DatiMissioneComponent.SCROLL_TARGET);
    this.comunicazione = this.data.comunicazione;
    this.comunicazioneToSave = this.data.comunicazioneToSave;
    this.routingParams = this.data.routingParams;
    this.ruolo = this.data.ruolo;
    this.saveStatusSuccess = this.data.saveStatusSuccess;
    try {
      const [
        tipoOrario,
        categLavAssObbl
      ] = await Promise.all([
        this.decodificaService.getTipoOrario().toPromise(),
        this.decodificaService.getCategLavoratoreAssObbl().toPromise(),
      ]);
      this.listaTipoOrario = tipoOrario;
      this.listaCategLavAssObbl = categLavAssObbl;
      this.initForm();
      this.patchValueInForm(this.comunicazioneToSave);
      this.setFormState();
      this.showForm = true;
      if (this.VIEW_MODE || this.ANNULLO_MODE) {
        this.formDatiMissione[CONTROL_STATE.DISABLE]();
      }
      this.checkDecodificheScadute(this.comunicazioneToSave.missione);
      if (this.saveStatusSuccess) {
        this.alertMessageService.setSuccessSingleMsg('Salvataggio effettuato con successo');
      }
      if (this.comunicazioneToSave.apiWarnings && this.comunicazioneToSave.apiWarnings.length > 0) {
        this.alertMessageService.setWarningMsg(this.comunicazioneToSave.apiWarnings);
      }
    } catch (e) {
    } finally {
      await this.utilitiesService.hideSpinner();
    }
    this.showForm = true;
    await this.utilitiesService.hideSpinner();
  }

  private initForm() {
    this.formDatiMissione = new FormGroup({
      id: new FormControl(),
      dtInizioMissione: new FormControl(null, Validators.required),
      dtFineMissione: new FormControl(null),
      ccnl: new FormGroup({
        id: new FormControl(),
        codCcnlMin: new FormControl(null, Validators.compose([
          Validators.required,
          Validators.minLength(2),
        ])),
        dsCcnl: new FormControl(null, Validators.compose([
          Validators.required,
          Validators.minLength(2),
        ])),
      }),
      livelloRetribuzione: new FormGroup({
        id: new FormControl(),
        codLivello: new FormControl(null, Validators.compose([
          Validators.required,
          Validators.minLength(2),
        ])),
        desLivello:  new FormControl(null, Validators.compose([
          Validators.required,
          Validators.minLength(1),
        ])),
      }),
      retribuzioneCompenso: new FormControl(null), // VERIFICARE SE è OBBLIGATORIO
      patInail: new FormControl(),
      istatLivello5: this.formBuilder.group({
        id: new FormControl(),
        codIstat2001livello5Min: this.formBuilder.control(null, Validators.compose([
          Validators.required,
          Validators.minLength(3),
        ])),
        dsComIstat2001livello5: this.formBuilder.control(null, Validators.compose([
          Validators.required,
          Validators.minLength(3),
        ])),
      }),
      tipoOrario: new FormControl(),
      livInquadramento: new FormControl(),
      flgAssunzioneObbligatoria: new FormControl('N', Validators.required),
      categLavAssObbl: new FormControl(),
      numOreSettMed: new FormControl(null, Validators.required),
      oreSettimanaliMedie: new FormControl(),
      flgLavoroAgricoltura: new FormControl('N', Validators.required),
      giornateLavPreviste: new FormControl(),
      dsAttivitaAgricoltura: new FormControl(),
      flgRischioSilicAsbe: new FormControl('N', Validators.required),
      dsVoceTariffa1: new FormControl(null,
        Validators.compose([
          Validators.required,
          Validators.minLength(4)
        ])
      ),
      dsVoceTariffa2: new FormControl(null, Validators.minLength(4)),
      dsVoceTariffa3: new FormControl(null, Validators.minLength(4)),
      dsAttivita: new FormControl(null, Validators.required),
      dtFineProroga: new FormControl(),
      dtCessazione: new FormControl(), //campi nel quadro della cessazione
      cessazionerl: new FormControl() //campi nel quadro della cessazione
    });

  }

  private setFormState() {
    this.setStateNumOreSettimanaliMedie();
    this.setStatecategLavAssObbl();
    this.setStateGiornateLavPreviste();
  }


  private patchValueInForm(comunicazione: Comunicazione) {
    const missione = comunicazione.missione;
    if (missione) {
      this.formDatiMissione.patchValue(missione);
      console.log(JSON.stringify(missione));
      const tipoOrario = missione.tipoOrario;
      if (tipoOrario) {
      const tipoOrarioFinded = this.listaTipoOrario.find(el => {
        return el.id === missione.tipoOrario.id;
      });
      this.f.tipoOrario.patchValue(tipoOrarioFinded);
    }
      const categLavAssObbl = missione.categLavAssObbl;
      if (categLavAssObbl) {
      const categLavAssObblFinded = this.listaCategLavAssObbl.find(el => {
        return el.id === missione.categLavAssObbl.id;
      });
      this.f.categLavAssObbl.patchValue(categLavAssObblFinded);
    }
      if (this.VIEW_MODE) {
        if (!comunicazione.missione.livelloRetribuzione) {
          const livelloRetribuzione: LivelloRetribuzione = {
            id: null,
            codLivello: null,
            desLivello: comunicazione.missione.livelloInquadramento
          };
          this.f.livelloRetribuzione.patchValue(livelloRetribuzione);
        }

    }
    }
  }

  async onSubmit() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    let missione: Rapporto = this.formDatiMissione.getRawValue() as Rapporto;
    missione.aziUtilizzatrice = this.comunicazioneToSave.missione && this.comunicazioneToSave.missione.id ? this.comunicazioneToSave.missione.aziUtilizzatrice : this.comunicazioneToSave.rapporto.aziUtilizzatrice;
    missione.dtTrasformazione = this.comunicazioneToSave.missione && this.comunicazioneToSave.missione.id ? this.comunicazioneToSave.missione.dtTrasformazione : null;
    missione.trasformazionerl = this.comunicazioneToSave.missione && this.comunicazioneToSave.missione.id ? this.comunicazioneToSave.missione.trasformazionerl : null;
    this.comunicazioneToSave.missione = missione;
    let res;
    try {
      if (missione.id) {
        // put
        const comunicazione = {
          id: this.comunicazioneToSave.id
        };
        missione.comunicazione = comunicazione;
        const wrapperRapporto: WrapperRapporto = {
          ruolo: this.ruolo,
          rapporto: missione
        }
        res = await this.comunicazioneService.putRapportoMissione(wrapperRapporto).toPromise();
      } else {
        // post
        const wrapperComunicazione: WrapperComunicazione = {
          comunicazione: this.comunicazioneToSave,
          ruolo: this.ruolo
        }
        res = await this.comunicazioneService.postRapportoMissione(wrapperComunicazione).toPromise();
      }
      if (res) {
        this.comunicazioneToSave = await this.comunicazioneService.getComunicazioneById(this.comunicazioneToSave.id).toPromise();
        this.comunicazioneToSave.apiWarnings = res.apiWarnings;
        const persistenceComunicazioneWreapper: PersistenceComunicazioneWreapper = {
          comunicazione: this.comunicazioneToSave,
          ricalcoloQuadri: false
        };
        this.persistenceNotification.emit(persistenceComunicazioneWreapper);
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

  private dataCf(codiceFiscale) {
    // tslint:disable-next-line: prefer-const
    let [anno, giorno ] = [ codiceFiscale.substring(6, 8), codiceFiscale.substring(9, 11) ];
    if (giorno > 40) {
      giorno -= 40;
      giorno = '0' + giorno;
    }
    return (anno < 20 ? '20' : '19' ) + anno + '-' + MESI[codiceFiscale.charAt(8)] + '-' + giorno;
  }

  private sessoCf(codiceFiscale) {
    return codiceFiscale.substring(9, 11) > 40 ? 'F' : 'M';
  }

  onClickPreCompila() {
    const cfInput: string = this.f.cfTutore.value;
    console.log(cfInput);
    if (cfInput && cfInput !== '') {
      const sessoTutore = this.sessoCf(cfInput);
      const dataNascita = this.dataCf(cfInput);
      this.f.sesso.setValue(sessoTutore);
      this.f.dataNascita.setValue(new Date(dataNascita));
    } else {
      this.utilitiesService.hideSpinner();
      console.log('Errore');
    }
    this.utilitiesService.hideSpinner();
  }

  onSearchQualifica() {

  }

  async onClickFindCcnl() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const decodifica: DecodificaGenerica = {
      codDecodifica: this.f.ccnl.get('codCcnlMin').value,
      dsDecodifica: this.f.ccnl.get('dsCcnl').value,
    };
    try {
      const res = await this.decodificaService.postCcnlDecodifica(decodifica).toPromise();
      let docodificaFinded: DecodificaGenerica;
      if (res && res.length === 1) {
        docodificaFinded = res[0];
      } else if (res && res.length > 1) {
        docodificaFinded = await this.promptModalService.openDecodificaPrompt(
          'Selezionare un Ccnl',
          res,
          TYPE_DECODIFICA_GENERICA.CCNL,
          decodifica
        );
      }
      if (docodificaFinded) {
        const ccnl: Ccnl = {
          id: docodificaFinded.idDecodifica,
          codCcnlMin: docodificaFinded.codDecodifica,
          dsCcnl: docodificaFinded.dsDecodifica,
        };
        this.f.ccnl.patchValue(ccnl);
      }
    } catch (e) {
      this.alertMessageService.setSingleErrorMsg(e);
      if (e.error && e.error.length > 0) {
        this.alertMessageService.setErrorMsg(e.error);
      }
      this.alertMessageService.setWarningMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
    this.setInquadramentoMode(true);
  }

  async onClickFindIstat() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const decodifica: DecodificaGenerica = {
      codDecodifica: this.f.istatLivello5.get('codIstat2001livello5Min')
        .value,
      dsDecodifica: this.f.istatLivello5.get('dsComIstat2001livello5')
        .value,
    };
    try {
      const res = await this.decodificaService
        .postQualificaDecodifica(decodifica)
        .toPromise();
      let docodificaFinded: DecodificaGenerica;
      if (res && res.length === 1) {
        docodificaFinded = res[0];
      } else if (res && res.length > 1) {
        docodificaFinded = await this.promptModalService.openDecodificaPrompt(
          'Selezionare una qualifica',
          res,
          TYPE_DECODIFICA_GENERICA.QUAL_ISTAT,
          decodifica
        );
      }
      if (docodificaFinded) {
        const istatLivello5: Istat2001livello5 = {
          id: docodificaFinded.idDecodifica,
          codIstat2001livello5Min: docodificaFinded.codDecodifica,
          dsComIstat2001livello5: docodificaFinded.dsDecodifica,
        };
        this.f.istatLivello5.patchValue(istatLivello5);
      }
    } catch (e) {
      this.alertMessageService.setSingleErrorMsg(e);
      if (e.error && e.error.length > 0) {
        this.alertMessageService.setWarningMsg(e.error);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  setStatecategLavAssObbl() {
    const flgAssunzioneObbligatoria = this.f.flgAssunzioneObbligatoria.value;
    const state = flgAssunzioneObbligatoria === 'S' ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE;
    this.obbligCategLavAssObbl = false;
    this.f.categLavAssObbl.clearValidators();
    if ( flgAssunzioneObbligatoria === 'S') {
      this.f.categLavAssObbl.setValidators(Validators.required);
      this.obbligCategLavAssObbl = true;
    }
    this.utilitiesService.changeControlState(
      this.f.categLavAssObbl,
      state,
      state === CONTROL_STATE.DISABLE ? true : false
    );
    this.f.categLavAssObbl.updateValueAndValidity();
  }

  setStateNumOreSettimanaliMedie() {
    const tipoOrario: TipoOrario = this.f.tipoOrario.value;
    this.obbligNumOreSettMedie = false;
    let state = CONTROL_STATE.DISABLE;
    this.f.numOreSettMed.clearValidators();
    if (
        tipoOrario &&
        tipoOrario.codTipoorarioMin !== TIPO_ORARIO.TEMPO_PIENO.COD_TIPO_ORARIO_MIN
      ) {
        state = CONTROL_STATE.ENABLE;
        this.f.numOreSettMed.setValidators(Validators.required);
        this.obbligNumOreSettMedie = true;
      }
    this.utilitiesService.changeControlState(
        this.f.numOreSettMed,
        state,
        state === CONTROL_STATE.DISABLE ? true : false
      );
    this.f.numOreSettMed.updateValueAndValidity();
  }

  async onClickFindLivelloRetr() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const decodifica: DecodificaGenerica = {
      idCcnl: this.f.ccnl.controls.id.value,
      codDecodifica: this.f.livelloRetribuzione.get('codLivello').value,
      dsDecodifica: this.f.livelloRetribuzione.get('desLivello').value
    };
    try {
      const res = await this.decodificaService.postLivelloRetribuzioneDecodifica(decodifica).toPromise();
      let docodificaFinded: DecodificaGenerica;
      if (res && res.length === 1) {
        docodificaFinded = res[0];
      } else if (res && res.length > 1) {
        docodificaFinded = await this.promptModalService.openDecodificaPrompt(
          'Selezionare un inquadramento', res.sort((a, b) => a.codDecodifica.localeCompare(b.codDecodifica)), TYPE_DECODIFICA_GENERICA.LIV_RETR, decodifica);
      }
      if (docodificaFinded) {
        const livelloRetribuzione: LivelloRetribuzione = {
          id: docodificaFinded.idDecodifica,
          codLivello: docodificaFinded.codDecodifica,
          desLivello: docodificaFinded.dsDecodifica
        };
        this.f.livelloRetribuzione.patchValue(livelloRetribuzione);
      }
    } catch (e) {
      if (e.error && e.error.length > 0) {
        this.alertMessageService.setWarningMsg(e.error);
      } else {
        this.alertMessageService.setSingleErrorMsg(e);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  setStateGiornateLavPreviste() {
    const flgLavoroAgricoltura = this.f.flgLavoroAgricoltura.value;
    let state = CONTROL_STATE.DISABLE;
    this.obbligGiornateLavPreviste = false;
    this.f.giornateLavPreviste.clearValidators();
    if (flgLavoroAgricoltura === 'S') {
      this.f.giornateLavPreviste.setValidators(Validators.required);
      state = CONTROL_STATE.ENABLE;
      this.obbligGiornateLavPreviste = true;
    }
    this.utilitiesService.changeControlState(
      this.f.giornateLavPreviste,
      state,
      state === CONTROL_STATE.DISABLE ? true : false
    );
    this.utilitiesService.changeControlState(
      this.f.dsAttivitaAgricoltura,
      state,
      state === CONTROL_STATE.DISABLE ? true : false
    );
    this.f.giornateLavPreviste.updateValueAndValidity();
    this.f.dsAttivitaAgricoltura.updateValueAndValidity();
  }

  reset() {
    this.formDatiMissione.reset();
    this.comunicazioneToSave = Utils.clone(this.comunicazione);
    this.patchValueInForm(this.comunicazioneToSave);
    this.setFormState();
  }

  private setInquadramentoMode(clean: boolean) {
    if (this.VIEW_MODE || this.ANNULLO_MODE) {
      this.f.livelloRetribuzione[CONTROL_STATE.DISABLE]();
    } else if (this.formDatiMissione.controls.ccnl.get('id').value) {
      this.utilitiesService.changeControlState(
        this.f.livelloRetribuzione,
        CONTROL_STATE.ENABLE,
        clean
      );
      // this.f.livelloRetribuzione[CONTROL_STATE.ENABLE]();
    } else {
      this.f.livelloRetribuzione[CONTROL_STATE.DISABLE]();
    }
  }

  private checkDecodificheScadute(rapporto: Rapporto){
    let sysDate: Date = new Date();
    sysDate.setHours(0,0,0,0);
    if(rapporto && !(this.VIEW_MODE || this.INS_MODE)){
      const ccnl: Ccnl = rapporto.ccnl;
      const livelloRetribuzione: LivelloRetribuzione = rapporto.livelloRetribuzione;
      const istat: Istat2001livello5 = rapporto.istatLivello5;
      sysDate.setHours(0,0,0,0);
      let dtFineCcnl: Date;
      if(ccnl.dtFine){
        dtFineCcnl = new Date(ccnl.dtFine);
        dtFineCcnl.setHours(0,0,0,0);
      }
      let dtFineLivelloRetribuzione: Date;
      if(livelloRetribuzione.dtFine){
        dtFineLivelloRetribuzione = new Date(livelloRetribuzione.dtFine);
        dtFineLivelloRetribuzione.setHours(0,0,0,0);
      }
      let dtFineIstat: Date;
      if(istat.dtFine){
        dtFineIstat = new Date(istat.dtFine);
        dtFineIstat.setHours(0,0,0,0);
      }
      if(dtFineCcnl && dtFineCcnl <= sysDate){
        //il ccnl è scaduto
        this.ccnlScaduto = true;
        this.utilitiesService.changeControlState(
          this.f.ccnl,
          CONTROL_STATE.ENABLE,
          true
        );
        this.utilitiesService.changeControlState(
          this.f.livelloRetribuzione,
          CONTROL_STATE.DISABLE,
          true
        );
        this.ccnlScaduto = true;
        this.livelloRetribuzioneScaduto = true;
      }
      if(dtFineLivelloRetribuzione && dtFineLivelloRetribuzione <= sysDate){
        const ccnl = this.f.ccnl.value;
        this.livelloRetribuzioneScaduto = true;
        let state: string = ccnl && ccnl.id ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE;
        this.utilitiesService.changeControlState(
          this.f.livelloRetribuzione,
          state,
          true
        )
      }
      if(dtFineIstat && dtFineIstat <= sysDate){
        this.utilitiesService.changeControlState(
          this.f.istatLivello5,
          CONTROL_STATE.ENABLE,
          true
        );
        this.istatScaduto = true;
      }
      const categLavAssObbl: CategLavAssObbl = rapporto.categLavAssObbl;
      let dtFineCategLavAssObbl: Date;
      if(categLavAssObbl){
        dtFineCategLavAssObbl = new Date(categLavAssObbl.dtFine);
        dtFineCategLavAssObbl.setHours(0,0,0,0);
      }
      if(dtFineCategLavAssObbl && dtFineCategLavAssObbl <= sysDate){
        this.utilitiesService.changeControlState(
          this.f.categLavAssObbl,
          CONTROL_STATE.ENABLE,
          true
        );
      }

      let dtFineTipoOrario: Date;
      const tipoOrario: TipoOrario = rapporto.tipoOrario;
      if(tipoOrario && tipoOrario.dtFine){
        dtFineTipoOrario = new Date(dtFineTipoOrario);
        dtFineTipoOrario.setHours(0,0,0,0);
      }
      if(dtFineTipoOrario && dtFineTipoOrario <= sysDate){
        if(dtFineIstat && dtFineIstat <= sysDate){
          this.utilitiesService.changeControlState(
            this.f.tipoOrario,
            CONTROL_STATE.ENABLE,
            true
          );
        }
      }

    }
  }

}
