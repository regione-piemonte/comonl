/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { GradoContrattuale } from './../../../../../../comonlapi/model/gradoContrattuale';
import { Istat2001livello5 } from './../../../../../../comonlapi/model/istat2001livello5';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CONSTANTS_MODE, CONTROL_STATE, PARAMETRO_COMONL_INTESTAZINE_FORMALE_DS, PARAMETRO_COMONL_INTESTAZIONE_ACCETTA, PARAMETRO_COMONL_TESTO_ACCETTAZZIONE, PARAMETRO_COMONL_TESTO_FORMALE_DS, TIPO_COMUNICAZIONE, TIPO_TRACCIATO, TYPE_DECODIFICA_GENERICA } from 'src/app/constants';
import { AnagraficaGenerica, CommonService, Comunicazione, ComunicazioneService, DecodificaGenerica, DecodificaService, Rapporto, Ruolo, SilpService, StatiEsteri, TipoComunicazione, Tutore } from 'src/app/modules/comonlapi';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { LogService, UtilitiesService } from 'src/app/services';
import { PersistenceComunicazioneWreapper } from '../../nav-main-comunicazione/nav-main-comunicazione.component';

@Component({
  selector: 'comonl-tutore',
  templateUrl: './tutore.component.html',
  styleUrls: ['./tutore.component.scss']
})
export class TutoreComponent implements OnInit {

  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="tutore"]';

  form: FormGroup;

  @Input() data: any;
  @Output() datiForm = new EventEmitter<any>();
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();
  @Output() persistenceNotification = new EventEmitter<PersistenceComunicazioneWreapper>();

  comunicazione: Comunicazione;
  comunicazioneToSave: Comunicazione;
  routingParams;

  listaGradiContrattuali: GradoContrattuale[] = [];

  showForm: boolean;

  ruolo: Ruolo;

  saveStatusSuccess: boolean;
  istatScaduto: boolean = false;

  get f() {
    return this.form.controls as any;
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

  get IS_ASSUNZIONE(): boolean {
    const tipoComunicazione: TipoComunicazione = this.comunicazioneToSave.tipoComunicazione;
    return tipoComunicazione.id === TIPO_COMUNICAZIONE.ASSUNZIONE.ID;
  }

  get INVALIDITA_FORM(){
    if(this.ANNULLO_MODE){
      return false;
    }else{
      return !this.form.valid;
    }
  }
  get INVALIDITA_ANNULLO_MODE(): boolean{
    if(this.ANNULLO_MODE){
      const tutore = this.comunicazioneToSave.rapporto.tutore;
      if(!tutore){
        return false;
      }else{
        const istat: Istat2001livello5 = this.f.istat.value;
        return istat && istat.id ? false : true;
      }
    }else{
      return false;
    }
  }

  // da prendere dalla tabella parametri
  intestazioneFormale: string;
  testoFormale: string;
  labelAccettazine: string;
  testoAccettazine: string;
  // da prendere dalla tabella persnalizzazine
  urlSitoLavoro: string;

  constructor(
    private logService: LogService,
    private decodificaService: DecodificaService,
    private utilitiesService: UtilitiesService,
    private promptModalService: PromptModalService,
    private alertMessageService: AlertMessageService,
    private commonService: CommonService,
    private comunicazioneService: ComunicazioneService
  ) {}

  async ngOnInit() {
    this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    this.utilitiesService.scrollTo(TutoreComponent.SCROLL_TARGET);
    this.ruolo = this.data.ruolo;
    this.comunicazione = this.data.comunicazione;
    this.comunicazioneToSave = this.data.comunicazioneToSave;
    this.routingParams = this.data.routingParams;
    const nuovaComunicazioneParams = this.routingParams.nuovaComParams;
    this.saveStatusSuccess = this.data.saveStatusSuccess;
    // console.log('Comunicazione' + this.comunicazione);
    // console.log('Params nuova comunicazione: ' + nuovaComunicazioneParams);
    let pv: string;
    const datore = this.comunicazioneToSave.datoreAttuale;
    if (
        datore &&
        datore.sedeOperativa &&
        datore.sedeOperativa.comune &&
        datore.sedeOperativa.comune.provincia
      ) {
      pv = datore.sedeOperativa.comune.provincia.dsTarga;
    }
    try {
      const [gradoContrattuale, intestazioneFormale, testoFormale, labelAccettazine, testoAccettazine] = await Promise.all([
        this.decodificaService.getGradoContrattuale().toPromise(),
        this.commonService.getParametroByDescrizione(PARAMETRO_COMONL_INTESTAZINE_FORMALE_DS).toPromise(),
        this.commonService.getParametroByDescrizione(PARAMETRO_COMONL_TESTO_FORMALE_DS).toPromise(),
        this.commonService.getParametroByDescrizione(PARAMETRO_COMONL_INTESTAZIONE_ACCETTA).toPromise(),
        this.commonService.getParametroByDescrizione(PARAMETRO_COMONL_TESTO_ACCETTAZZIONE).toPromise()
      ]);
      if (pv) {
        const personalizzazione = await this.decodificaService.getPersonalizzazioneByPv(pv).toPromise();
        this.urlSitoLavoro = personalizzazione ? personalizzazione.urlSitoLavoro : null;
      }
      this.intestazioneFormale = intestazioneFormale.valoreParametro;
      this.testoFormale = testoFormale.valoreParametro;
      this.labelAccettazine = labelAccettazine.valoreParametro;
      this.listaGradiContrattuali = gradoContrattuale;
      this.testoAccettazine = testoAccettazine.valoreParametro;
      // this.form.get('gradoContrattuale').patchValue(this.listaGradiContrattuali.find((item: GradoContrattuale) => {
      //   return item.dsComTGradoContrattuale === this.comunicazione.rapporto.tutore.gradoContrattuale.dsComTGradoContrattuale;
      //   }));
      this.initForm();
      this.patchValueInForm(this.comunicazioneToSave);
      this.setFormMode();
      this.checkDecodificheScadute(this.comunicazioneToSave);
      this.showForm = true;

      if (this.saveStatusSuccess) {
        this.alertMessageService.setSuccessSingleMsg('Salvataggio effettuato con successo');
      }
      if(this.comunicazioneToSave.apiWarnings && this.comunicazioneToSave.apiWarnings.length > 0){
        this.alertMessageService.setWarningMsg(this.comunicazioneToSave.apiWarnings);
      }
    } catch (e) {
      this.alertMessageService.setSingleErrorMsg(e);
      if (e.error && e.error.length > 0) {
        this.alertMessageService.setErrorMsg(e.error);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  private initForm() {
    this.form = new FormGroup({
      id: new FormControl(),
      cfTutore: new FormControl(null, Validators.compose([
        Validators.required,
        Validators.pattern('[A-Z]{6}[0-9LMNPQRSTUV]{2}[A-Z][0-9LMNPQRSTUV]{2}[A-Z][0-9LMNPQRSTUV]{3}[A-Z]')
      ])),
      nome: new FormControl(null, Validators.required),
      cognome: new FormControl(null, Validators.required),
      sesso: new FormControl(null, Validators.required),
      dtNascita: new FormControl(null, Validators.required),
      flgTitolare: new FormControl('N', Validators.required),
      istat: new FormGroup({
        id: new FormControl(),
        codIstat2001livello5Min: new FormControl(null,
          Validators.compose([
            Validators.required,
            Validators.minLength(3)
          ])),
        dsComIstat2001livello5: new FormControl(null,
          Validators.compose([
            Validators.required,
            Validators.minLength(3)
          ])),
      }),
      numAnniEsperienza: new FormControl(null, Validators.required),
      dsLivelloInquadramento: new FormControl(),
      gradoContrattuale: new FormControl(),
      dtVisitaMedica: new FormControl(null),
      flgAccettazione: new FormControl(null)
    });
    if (this.IS_ASSUNZIONE) {
      this.f.flgAccettazione.setValidators(Validators.required);
      this.f.flgAccettazione.updateValueAndValidity();
    }
  }

  async onSubmit() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    try {
      if(this.ANNULLO_MODE && !this.comunicazioneToSave.rapporto.tutore){
        const persistenceComunicazioneWreapper: PersistenceComunicazioneWreapper = {
          comunicazione: this.comunicazioneToSave,
          ricalcoloQuadri: false
        };
        this.persistenceNotification.emit(persistenceComunicazioneWreapper);
      }else{
        let tutoreToSend = this.form.getRawValue() as Tutore;
      const flgAccettazione = this.f.flgAccettazione.value;
      tutoreToSend.flgAccettazione = flgAccettazione ? 'S' : 'N';
      this.comunicazioneToSave.rapporto.tutore = tutoreToSend;
      let res: Tutore;
      if (tutoreToSend.id) {
        // put
        res = await this.comunicazioneService.putTutore(this.comunicazioneToSave).toPromise();
      } else {
        // post
        res = await this.comunicazioneService.postTutore(this.comunicazioneToSave).toPromise();
      }
      if (res) {
        this.comunicazioneToSave.rapporto.tutore = res;
        const persistenceComunicazioneWreapper: PersistenceComunicazioneWreapper = {
          comunicazione: this.comunicazioneToSave,
          ricalcoloQuadri: false
        };
        this.persistenceNotification.emit(persistenceComunicazioneWreapper);
      }
      }
    } catch (e) {
      this.comunicazioneToSave.rapporto.tutore = this.comunicazione.rapporto.tutore;
      this.alertMessageService.setSingleErrorMsg(e);
      if (e.error && e.error.length) {
        this.alertMessageService.setErrorMsg(e.error);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  async onClickPreCompila() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    try {
      const res: AnagraficaGenerica = await this.commonService.preCompilaAnagraficaByCf(this.f.cfTutore.value).toPromise();
      if (res) {
        this.f.sesso.setValue(res.sesso);
        this.f.dtNascita.setValue(res.dataNascita);
      }
    } catch (e) {
      if (e.error && e.error.length > 0) {
        this.alertMessageService.setErrorMsg(e.error);
      }else{
        this.alertMessageService.setSingleErrorMsg(e);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  async onSearchQualifica() {
    let decodifica: DecodificaGenerica;
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    decodifica = {
      codDecodifica: this.f.istat.get('codIstat2001livello5Min').value,
      dsDecodifica: this.f.istat.get('dsComIstat2001livello5').value
    };
    try {
     const res = await  this.decodificaService.postQualificaDecodifica(decodifica).toPromise();
     let decodificaFinded: DecodificaGenerica;
     if (res && res.length === 1) {
      decodificaFinded = res[0];
     } else if (res && res.length > 1) {
      decodificaFinded = await this.promptModalService.openDecodificaPrompt('Selezionare una qualifica', res, TYPE_DECODIFICA_GENERICA.QUAL_ISTAT, decodifica);
     }
     if (decodificaFinded) {
       const qualifica: Istat2001livello5 = {
         id: decodificaFinded.idDecodifica,
        codIstat2001livello5Min: decodificaFinded.codDecodifica,
        dsComIstat2001livello5: decodificaFinded.dsDecodifica
       };
       this.f.istat.patchValue(qualifica);
     }
    } catch (e) {
      this.alertMessageService.setSingleErrorMsg(e);
      if (e.error && e.error.length) {
        this.alertMessageService.setErrorMsg(e.error);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  private patchValueInForm(comunicazione: Comunicazione) {
    console.log('patchValueInForm: ' + JSON.stringify(comunicazione));
    const rapporto = comunicazione.rapporto;
    if (rapporto && rapporto.tutore) {
      this.form.patchValue(rapporto.tutore);
      const gradoContrattuale = rapporto.tutore.gradoContrattuale;
      if (gradoContrattuale) {
        const gradoContrattualeFinded = this.listaGradiContrattuali.find((el: GradoContrattuale) => {
          return el.id === gradoContrattuale.id;
        });
        this.f.gradoContrattuale.patchValue(gradoContrattualeFinded);
      }
      if (rapporto.tutore.flgAccettazione === 'S') {
        this.f.flgAccettazione.patchValue(true);
      } else {
        this.f.flgAccettazione.patchValue(false);
      }
    }
  }

  private setFormMode() {
    if (this.VIEW_MODE || this.ANNULLO_MODE) {
      // READ MODE
      this.form[CONTROL_STATE.DISABLE]();
    } 
  }

  private checkDecodificheScadute(comunicazione: Comunicazione){
    const rapporto: Rapporto = comunicazione.rapporto;
    if (rapporto && rapporto.tutore) {
      const tutore: Tutore = rapporto.tutore;
      if(!(this.VIEW_MODE || this.INS_MODE)){
        let sysDate = new Date();
        sysDate.setHours(0,0,0,0);
        let dtFineIstat: Date;
        const istat: Istat2001livello5 = tutore.istat;
        if(istat && istat.dtFine){
          dtFineIstat = new Date(istat.dtFine);
          dtFineIstat.setHours(0,0,0,0);
        }
        if(dtFineIstat && dtFineIstat <= sysDate){
          this.istatScaduto = true;
          tutore.istat.id = null;
          tutore.istat.codIstat2001livello5Min = null;
          tutore.istat.dsComIstat2001livello5 = null;
          this.utilitiesService.changeControlState(
            this.f.istat,
            CONTROL_STATE.ENABLE,
            true
          );
        }

        const gradoContrattuale: GradoContrattuale = tutore.gradoContrattuale;
        let dtFineGradoContrattuale: Date;
        if(gradoContrattuale && gradoContrattuale.dtFine){
          dtFineGradoContrattuale = new Date(gradoContrattuale.dtFine);
          dtFineGradoContrattuale.setHours(0,0,0,0);
        }
        if(dtFineGradoContrattuale && dtFineGradoContrattuale <= sysDate){
          this.utilitiesService.changeControlState(
            this.f.gradoContrattuale,
            CONTROL_STATE.ENABLE,
            true
          );
        }
      }
    }
  }

  onClickReset() {
    this.alertMessageService.emptyMessages();
    this.form.reset();
    this.patchValueInForm(this.comunicazione);
  }

  findInvalidControls() {
    const controls = this.form.controls;
    for (const name in controls) {
        if (controls[name].invalid) {
           console.log('invalid: ' + name);
        }
    }
  }

}
