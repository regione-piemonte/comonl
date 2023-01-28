/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Atecofin } from './../../../../../../comonlapi/model/atecofin';
import { Trasformazionerl } from './../../../../../../comonlapi/model/trasformazionerl';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CONSTANTS_MODE, CONTROL_STATE, TIPO_SOMMINISTRAZIONE, TIPO_TRACCIATO, TYPE_DECODIFICA_GENERICA } from 'src/app/constants';
import { Comune, ComunicazioneService, DatiAzienda, Datore, DecodificaGenerica, DecodificaService, Rapporto, Ruolo, SedeLavoro, SilpService, StatiEsteri, WrapperComunicazione } from 'src/app/modules/comonlapi';
import { Comunicazione } from 'src/app/modules/comonlapi/model/comunicazione';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { LogService, UtilitiesService } from 'src/app/services';
import { UtilsComonl } from 'src/app/services/utilsComonl';
import { WrapperHelper } from 'src/app/models/wrappwer-helper';
import { Router } from '@angular/router';
import { PersistenceComunicazioneWreapper } from '../../nav-main-comunicazione/nav-main-comunicazione.component';
import { FormDistacco, FormDtTrasfTipoSpost, TrasferimentoDistaccoForm } from 'src/app/models/TrasferimentoDistaccoForm';
import { Utils } from 'src/app/utils';

@Component({
  selector: 'comonl-trasferimento-distacco',
  templateUrl: './trasferimento-distacco.component.html',
  styleUrls: ['./trasferimento-distacco.component.scss']
})
export class TrasferimentoDistaccoComponent implements OnInit {

  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="trasferimentoDistacco"]';

  form: FormGroup;
  formDistacco: FormGroup;
  formTrasferimento: FormGroup;

  @Input() data: any;
  @Output() datiForm = new EventEmitter<any>();
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();
  @Output() persistenceNotification = new EventEmitter<PersistenceComunicazioneWreapper>();


  comunicazione: Comunicazione;
  comunicazioneToSave: Comunicazione;
  routingParams;

  listaTipoSpostamento: Trasformazionerl[] = [];
  showForm: boolean;
  editMode: boolean;
  showTrasferimento: boolean;
  showDistacco: boolean;
  disableCfDatoreDistaccatarioBtn: boolean;
  datoreSelected: Datore;
  aziendaSilpFinded: DatiAzienda;
  ruolo: Ruolo;
  wrapperHelper: WrapperHelper;
  trasferimentoDistaccoFormByData: TrasferimentoDistaccoForm;

  saveStatusSuccess;

  

  get f() {
    return this.form.controls as any;
  }

  get fD() {
    return this.formDistacco.controls as any;
  }

  get fT() {
    return this.formTrasferimento.controls as any;
  }

  get VIEW_MODE() {
    const nuovaComParams = this.routingParams;
    return nuovaComParams.mode === CONSTANTS_MODE.VIEW;
  }

  get EDIT_MODE() {
    const nuovaComParams = this.routingParams;
    return nuovaComParams.mode === CONSTANTS_MODE.EDIT;
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


  get DISABLE_INTERACTIONS_VIEW(): boolean {
    return this.VIEW_MODE || this.EDIT_MODE;
  }

  get UNISOMM(): boolean {
    return this.comunicazioneToSave.tipoTracciato.id === TIPO_TRACCIATO.UNISOMM.ID;
  }

  get UNILAV(): boolean {
    return this.comunicazioneToSave.tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID;
  }

  get TRASFERIMENTO_IS_INVALID(): boolean {
    const idStatoEstero = this.fT.statoEsteroSedePrec.get('id').value;
    return Utils.isNullOrUndefinedOrCampoVuoto(this.fT.indirizzoSedePrec.value) ||
            (!idStatoEstero && Utils.isNullOrUndefinedOrCampoVuoto(this.fT.cap.value)) ||
           this.STATI_ESTERI_COMUNE_TRASF_INVALID;

  }

  get DISTACCO_IS_INVALID(): boolean {
    return this.STATI_ESTERI_COMUNE_DISTACCO_INVALID ||
            Utils.isNullOrUndefinedOrCampoVuoto(this.fD.dataFineDistacco.value) ||
           Utils.isNullOrUndefinedOrCampoVuoto(this.fD.distaccoParziale.value) ||
           Utils.isNullOrUndefinedOrCampoVuoto(this.fD.distaccoAzEst.value) ||
           (this.fD.distaccoAzEst.value === 'N' && Utils.isNullOrUndefinedOrCampoVuoto(this.fD.cfDatoreDistaccatario.value)) ||
           Utils.isNullOrUndefinedOrCampoVuoto(this.fD.denominazione.value) ||
          (this.fD.distaccoAzEst.value === 'N' && Utils.isNullOrUndefinedOrCampoVuoto(this.fD.patINAIL.value)) ||
           Utils.isNullOrUndefinedOrCampoVuoto(this.fD.indirizzo.value) ||
           (!Utils.isNullOrUndefinedOrCampoVuoto(this.fD.comune.get('id').value) && Utils.isNullOrUndefinedOrCampoVuoto(this.fD.cap.value)) ||
           this.TEL_FAX_MAIL_DISTACCO_INVALID;



  }

  get STATI_ESTERI_COMUNE_TRASF_INVALID(): boolean {
    return Utils.isNullOrUndefinedOrCampoVuoto(this.fT.comuneSedePrec.get('id').value) && Utils.isNullOrUndefinedOrCampoVuoto(this.fT.statoEsteroSedePrec.get('id').value);
  }
  get STATI_ESTERI_COMUNE_DISTACCO_INVALID(): boolean {
    return Utils.isNullOrUndefinedOrCampoVuoto(this.fD.statoEstero.get('codNazioneMin').value) &&
           Utils.isNullOrUndefinedOrCampoVuoto(this.fD.statoEstero.get('dsStatiEsteri').value) &&
           Utils.isNullOrUndefinedOrCampoVuoto(this.fD.comune.get('id').value);
  }

  get TEL_FAX_MAIL_DISTACCO_INVALID(): boolean {
    return Utils.isNullOrUndefinedOrCampoVuoto(this.fD.telefono.value) && Utils.isNullOrUndefinedOrCampoVuoto(this.fD.fax.value) && Utils.isNullOrUndefinedOrCampoVuoto(this.fD.email.value);
  }

  constructor(
    private logService: LogService,
    private utilitiesService: UtilitiesService,
    private decodificaService: DecodificaService,
    private promptModalService: PromptModalService,
    private alertMessageService: AlertMessageService,
    private silpService: SilpService,
    private router: Router,
    private comunicazioneService: ComunicazioneService
  ) { }

  async ngOnInit() {
    this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    this.utilitiesService.scrollTo(TrasferimentoDistaccoComponent.SCROLL_TARGET);
    this.comunicazione = this.data.comunicazione;
    this.comunicazioneToSave = this.data.comunicazioneToSave;
    this.routingParams = this.data.routingParams;
    this.saveStatusSuccess = this.data.saveStatusSuccess;
    this.wrapperHelper = this.data.wrapperHelper;
    this.ruolo = this.data.ruolo;
    this.trasferimentoDistaccoFormByData = this.data.trasferimentoDistaccoForm;
    try {
      const [tipoSpostamento] = await Promise.all([
        this.decodificaService.getTrasformazionerlByTipo('TRD').toPromise()
      ]);
      this.listaTipoSpostamento = tipoSpostamento;
      if (this.UNISOMM) {
        this.listaTipoSpostamento = this.listaTipoSpostamento.filter(el => {
          return el.codTrasformazionirlMin === 'TL';
        });
      }
      if (this.saveStatusSuccess) {
        this.alertMessageService.setSuccessSingleMsg('Salvataggio effettuato con successo');
      }
      if(this.comunicazioneToSave.apiWarnings && this.comunicazioneToSave.apiWarnings.length > 0){
        this.alertMessageService.setWarningMsg(this.comunicazioneToSave.apiWarnings);
      }
      this.initForm();
      console.log(this.routingParams.mode);
      this.patchValueInForms(this.comunicazioneToSave);
      
      this.setFormMode();
      this.showForm = true;
      
    } catch (e) {

    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  private initForm() {
    this.form = new FormGroup({
      dtTrasferimento: new FormControl({value: null, disabled: this.VIEW_MODE || this.ANNULLO_MODE}, Validators.required),
      tipoSpostamento: new FormControl({value: null, disabled: this.VIEW_MODE || this.ANNULLO_MODE}, Validators.required)
    });
  }

  private setFormMode(){
    
  }

  private initFormTrasferimento() {
    this.formTrasferimento = new FormGroup({
      statoEsteroSedePrec: new FormGroup({
        id: new FormControl(),
        codNazioneMin: new FormControl({value: null, disabled: true}),
        dsStatiEsteri: new FormControl({value: null, disabled: true}),
      }),
      comuneSedePrec: new FormGroup({
        id: new FormControl(),
        codComuneMin: new FormControl({value: null, disabled: true}),
        dsComTComune: new FormControl({value: null, disabled: true}),
      }),
      indirizzoSedePrec: new FormControl({value: null, disabled: true}),
      cap: new FormControl({value: null, disabled: true})
    });
  }

  private initFormDistacco() {
    this.formDistacco = new FormGroup({
      dataFineDistacco: new FormControl({value: null, disabled: this.VIEW_MODE || this.ANNULLO_MODE }, Validators.required),
      distaccoParziale: new FormControl({ value: 'N', disabled: this.VIEW_MODE || this.ANNULLO_MODE }, Validators.required),
      distaccoAzEst: new FormControl({ value: 'N', disabled: this.VIEW_MODE || this.ANNULLO_MODE }, Validators.required),
      cfDatoreDistaccatario: new FormControl({value: null, disabled: this.VIEW_MODE || this.ANNULLO_MODE }),
      denominazione: new FormControl({value: null, disabled: this.VIEW_MODE || this.ANNULLO_MODE }),
      idAziendaSilp: new FormControl({value: null, disabled: this.VIEW_MODE || this.ANNULLO_MODE }),
      ateco: new FormGroup({
        id: new FormControl({value: null, disabled: this.VIEW_MODE || this.ANNULLO_MODE }),
        codAtecofinMin: new FormControl({value: null, disabled: this.VIEW_MODE || this.ANNULLO_MODE },
          Validators.compose([
            Validators.required,
            Validators.minLength(3)
          ])
        ),
        dsComTAtecofin: new FormControl({value: null, disabled: this.VIEW_MODE || this.ANNULLO_MODE },
          Validators.compose([
            Validators.required,
            Validators.minLength(3)
          ])
        ),
      }),
      patINAIL: new FormControl({value: null, disabled: this.VIEW_MODE || this.ANNULLO_MODE}),
      flgSedeLegale: new FormControl(),
      statoEstero: new FormGroup({
        id: new FormControl(null),
        codNazioneMin: new FormControl({value: null, disabled: this.VIEW_MODE || this.ANNULLO_MODE }, Validators.minLength(3)),
        dsStatiEsteri: new FormControl({value: null, disabled: this.VIEW_MODE || this.ANNULLO_MODE }, Validators.minLength(3)),
      }),
      comune: new FormGroup({
        id: new FormControl(null),
        codComuneMin: new FormControl({value: null, disabled: true}),
        dsComTComune: new FormControl({value: null, disabled: true}),
      }),
      indirizzo: new FormControl({value: null, disabled: this.VIEW_MODE || this.ANNULLO_MODE }),
      cap: new FormControl({value: null, disabled: true}),
      telefono: new FormControl({value: null, disabled: this.VIEW_MODE || this.ANNULLO_MODE }),
      fax: new FormControl({value: null, disabled: this.VIEW_MODE || this.ANNULLO_MODE }),
      email: new FormControl({value: null, disabled: this.VIEW_MODE || this.ANNULLO_MODE }),
      idSedeSilp: new FormControl()
    });
  }

  setFormTrasferimentoDistacco() {
    if (!this.UNISOMM) {
      const codTrasformazionirlMin = this.f.tipoSpostamento.value ? this.f.tipoSpostamento.value.codTrasformazionirlMin : null;
      switch (codTrasformazionirlMin) {
      case 'DL': {
        this.showTrasferimento = false;
        this.showDistacco = true;
        this.initFormDistacco();
        this.patchValueInFormDistacco(this.comunicazioneToSave);
        
        this.setFormModeDistacco(true);
        break;
      }
      case 'TL': {
        this.showDistacco = false;
        this.showTrasferimento = true;
        this.initFormTrasferimento();
        this.patchValueInFormTrasferimento(this.comunicazioneToSave);
        this.setFormModeTraferimento();
        break;
      }
      default : {
        this.showDistacco = this.showTrasferimento = false;
      }
    }
    }
  }

  setFormModeDistacco(cleanValue: boolean) {
    if(!this.VIEW_MODE && !this.ANNULLO_MODE){
      const distaccoAzEst = this.fD.distaccoAzEst.value;
      let stateCfDatoreDistaccatarioBtn = CONTROL_STATE.ENABLE;
      let altriStati = CONTROL_STATE.DISABLE;
      if (distaccoAzEst === 'S') {
        stateCfDatoreDistaccatarioBtn = CONTROL_STATE.DISABLE;
        altriStati = CONTROL_STATE.ENABLE;
        this.disableCfDatoreDistaccatarioBtn = true;
      }
      this.utilitiesService.changeControlState(
        this.fD.cfDatoreDistaccatario,
        stateCfDatoreDistaccatarioBtn,
        cleanValue
      );
      this.utilitiesService.changeControlState(
        this.fD.denominazione,
        altriStati,
        cleanValue
      );
      this.utilitiesService.changeControlState(
        this.fD.statoEstero,
        altriStati,
        cleanValue
      );
      this.utilitiesService.changeControlState(
        this.fD.indirizzo,
        altriStati,
        cleanValue
      );
      this.utilitiesService.changeControlState(
        this.fD.telefono,
        altriStati,
        cleanValue
      );
      this.utilitiesService.changeControlState(
        this.fD.fax,
        altriStati,
        cleanValue
      );
      this.utilitiesService.changeControlState(
        this.fD.email,
        altriStati,
        cleanValue
      );
      if (cleanValue) {
        this.fD.comune.reset();
        this.fD.ateco.reset();
      }
    }
      
  }

  private setFormModeTraferimento() {

  }

  private patchValueInForm(comunicazione: Comunicazione) {
    let rapporto: Rapporto;

    if (
        comunicazione.tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID || (comunicazione.tipoTracciato.id === TIPO_TRACCIATO.UNISOMM.ID &&
        comunicazione.tipoSomministrazione.id !== TIPO_SOMMINISTRAZIONE.MISSIONE.ID)
      ) {
      rapporto = comunicazione.rapporto;
    }
    if (comunicazione.tipoTracciato.id === TIPO_TRACCIATO.UNISOMM.ID &&
        comunicazione.tipoSomministrazione.id === TIPO_SOMMINISTRAZIONE.MISSIONE.ID) {
          rapporto = comunicazione.missione;
    }
    this.f.dtTrasferimento.patchValue(rapporto.dtTrasformazione);
    if (rapporto.trasformazionerl) {
      const TrasformazionerlFinded = this.listaTipoSpostamento.find((item: Trasformazionerl) => {
        return item.codTrasformazionirlMin === rapporto.trasformazionerl.codTrasformazionirlMin;
      });
      this.form.get('tipoSpostamento').patchValue(TrasformazionerlFinded);
    }
  }


  private patchValueInForms(comunicazione: Comunicazione) {
    let trasferimentoDistaccoForm: TrasferimentoDistaccoForm = this.trasferimentoDistaccoFormByData;
    const patInail = this.data.sedeOperativa ? this.data.sedeOperativa.patInail : null;
    const sedeOperativa: SedeLavoro = this.data.sedeOperativa;
    let formDistacco: FormDistacco;
    if (trasferimentoDistaccoForm) {
      formDistacco = trasferimentoDistaccoForm.formDistacco;
      const formDtTrasfTipoSpost = trasferimentoDistaccoForm.formDtTrasfTipoSpost;
      this.f.dtTrasferimento.patchValue(formDtTrasfTipoSpost.dtTrasferimento);
      if (formDtTrasfTipoSpost.tipoSpostamento) {
        const tipoSpostamentoFinded = this.listaTipoSpostamento.find(el => {
          return el.codTrasformazionirlMin === formDtTrasfTipoSpost.tipoSpostamento.codTrasformazionirlMin;
        });
        this.f.tipoSpostamento.patchValue(tipoSpostamentoFinded);
      }
    } else {
      this.patchValueInForm(comunicazione);
    }
    if (!this.UNISOMM) {
      switch (this.form.get('tipoSpostamento').value.codTrasformazionirlMin) {
        case 'DL': {
          this.showTrasferimento = false;
          this.showDistacco = true;
          this.initFormDistacco();
          if (formDistacco) {
            this.formDistacco.patchValue(formDistacco);
            this.fD.patINAIL.patchValue(patInail);
            if (sedeOperativa) {
              this.fD.flgSedeLegale.setValue(sedeOperativa.flgSedeLegale);
              const statiEsteri: StatiEsteri = sedeOperativa.statiEsteri;
              if (statiEsteri) {
                this.fD.statoEstero.patchValue(statiEsteri);
              }
              const comune: Comune = sedeOperativa.comune;
              if (comune) {
                this.fD.comune.patchValue(comune);
              }
              this.fD.indirizzo.patchValue(sedeOperativa.indirizzo);
              this.fD.cap.patchValue(sedeOperativa.codCap);
              this.fD.telefono.patchValue(sedeOperativa.telefono);
              this.fD.fax.patchValue(sedeOperativa.fax);
              this.fD.email.patchValue(sedeOperativa.email);
              this.fD.idSedeSilp.patchValue(sedeOperativa.idSedeSilp);
            }
          } else {
            this.patchValueInFormDistacco(comunicazione);
          }
          this.setFormModeDistacco(false);
          this.trasferimentoDistaccoFormByData = null;
          break;
        }
        case 'TL': {
          this.showDistacco = false;
          this.showTrasferimento = true;
          // this.showTrasferimento = true;
          this.initFormTrasferimento();
          if (sedeOperativa) {
              const statiEsteri: StatiEsteri = sedeOperativa.statiEsteri;
              if (statiEsteri) {
                this.fT.statoEsteroSedePrec.patchValue(statiEsteri);
              }
              const comune: Comune = sedeOperativa.comune;
              if (comune) {
                this.fT.comuneSedePrec.patchValue(comune);
              }
              this.fT.indirizzoSedePrec.patchValue(sedeOperativa.indirizzo);
              this.fT.cap.patchValue(sedeOperativa.codCap);
          } else {
            this.patchValueInFormTrasferimento(comunicazione);
          }
          this.trasferimentoDistaccoFormByData = null;
          break;
        }
      }
    }
  }

  private patchValueInFormDistacco(comunicazione: Comunicazione) {
    /**************************UNILAV***********************/
    let rapporto: Rapporto = comunicazione.rapporto;
    if (rapporto) {
      this.fD.dataFineDistacco.patchValue(rapporto.dtFineRapporto);
      this.fD.distaccoParziale.patchValue(rapporto.flgDistaccoParziale);
      this.fD.distaccoAzEst.patchValue(rapporto.flgDistaccoSuAziEstera);
      if (rapporto.datoreDistaccatario) {
        this.fD.cfDatoreDistaccatario.patchValue(rapporto.datoreDistaccatario.codiceFiscale);
        this.fD.denominazione.patchValue(rapporto.datoreDistaccatario.dsDenominazioneDatore);
        if (rapporto.datoreDistaccatario.atecofin) {
          this.fD.ateco.patchValue(rapporto.datoreDistaccatario.atecofin);
        }
        this.fD.patINAIL.patchValue(rapporto.datoreDistaccatario.patInail);
        if (rapporto.datoreDistaccatario.sedeLegale) {
          if (rapporto.datoreDistaccatario.sedeLegale.comune) {
            this.fD.comune.patchValue(rapporto.datoreDistaccatario.sedeLegale.comune);
          }
          if (rapporto.datoreDistaccatario.sedeLegale.statiEsteri) {
            this.fD.statoEstero.patchValue(rapporto.datoreDistaccatario.sedeLegale.statiEsteri);
          }
        }
        let sedeLegale: SedeLavoro = rapporto.datoreDistaccatario.sedeLegale;
        this.fD.indirizzo.patchValue(sedeLegale.indirizzo);
        this.fD.telefono.patchValue(sedeLegale.telefono);
        this.fD.fax.patchValue(sedeLegale.fax);
        this.fD.email.patchValue(sedeLegale.email);
        this.fD.cap.patchValue(sedeLegale.codCap);
        this.fD.idAziendaSilp.patchValue(rapporto.datoreDistaccatario.idAziendaSilp);
      }
    }

  }

  private patchValueInFormTrasferimento(comunicazione: Comunicazione) {
    let rapporto: Rapporto;
    if (this.comunicazioneToSave.tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID) {
      /**************************UNILAV***********************/
     rapporto = comunicazione.rapporto;
    }
    if (this.comunicazioneToSave.tipoTracciato.id === TIPO_TRACCIATO.UNISOMM.ID) {
      /**************************UNISOMM****************************/
      rapporto = comunicazione.missione;
    }
    if (rapporto) {

      if (rapporto.sedeLavoroPrecedente) {
        if (rapporto.sedeLavoroPrecedente.comune) {
          this.fT.comuneSedePrec.patchValue(rapporto.sedeLavoroPrecedente.comune);
        }
        if (rapporto.sedeLavoroPrecedente.statiEsteri) {

          this.fT.statoEsteroSedePrec.patchValue(rapporto.sedeLavoroPrecedente.statiEsteri);
        }
        this.fT.indirizzoSedePrec.patchValue(rapporto.sedeLavoroPrecedente.indirizzo);
        this.fT.cap.patchValue(rapporto.sedeLavoroPrecedente.codCap);
      }


    }
  }

  // MODALE CERCA COMUNE
  async onClickFindComune(section: string) {
    let decodifica: DecodificaGenerica;
    this.utilitiesService.showSpinner();
    if (section === 'distaccato') {
      decodifica = {
        codDecodifica: this.fD.comune.get('codComuneMin').value,
        dsDecodifica: this.fD.comune.get('dsComTComune').value
      };
    } else if (section === 'trasf') {
      decodifica = {
        codDecodifica: this.f.comuneSedePrec.get('codComuneMin').value,
        dsDecodifica: this.f.comuneSedePrec.get('dsComTComune').value
      };
    }
    try {
     const res = await  this.decodificaService.postComuneDecodifica(decodifica).toPromise();
     let decodificaFinded: DecodificaGenerica;
     if (res && res.length === 1) {
      decodificaFinded = res[0];
     } else if (res && res.length > 1) {
      decodificaFinded = await this.promptModalService.openDecodificaPrompt('Selezionare un comune', res, TYPE_DECODIFICA_GENERICA.COMUNE, decodifica);
     }
     if (decodificaFinded) {
       const comune: Comune = {
         id: decodificaFinded.idDecodifica,
         codComuneMin: decodificaFinded.codDecodifica,
         dsComTComune: decodificaFinded.dsDecodifica
       };
       if (section === 'distaccato') {
       this.fD.comune.patchValue(comune);
       } else if (section === 'trasf') {
        this.f.comuneSedePrec.patchValue(comune);
       }
     }
    } catch (e) {
      this.alertMessageService.setWarningMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  async onClickFindDatoreDistaccatario() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    try {
      const cf = this.fD.cfDatoreDistaccatario.value;
      this.aziendaSilpFinded = await this.silpService.getAziendaSilp(cf).toPromise();
      if (this.aziendaSilpFinded) {
        const sedeLegaleSilp = this.aziendaSilpFinded.sedeLegale;
        this.datoreSelected = UtilsComonl.getDatoreFromDatiAzienda(this.aziendaSilpFinded) as Datore;
        this.datoreSelected.codiceFiscale = cf;
        this.fD.denominazione.patchValue(this.datoreSelected.dsDenominazioneDatore);
        this.fD.idAziendaSilp.patchValue(this.datoreSelected.idAziendaSilp);
        const ateco = this.datoreSelected.atecofin;
        if (ateco) {
          this.fD.ateco.patchValue(ateco);
        }
        if (sedeLegaleSilp) {
          this.fD.patINAIL.setValue(sedeLegaleSilp.patInail);
        }

        const sedeLegale = this.datoreSelected.sedeLegale;

        if (sedeLegale) {
          this.fD.flgSedeLegale.setValue(sedeLegale.flgSedeLegale);
          const statiEsteri = this.datoreSelected.sedeLegale.statiEsteri;
          const comune = this.datoreSelected.sedeLegale.comune;
          if (statiEsteri) {
            this.fD.statoEstero.patchValue(statiEsteri);
          }
          if (comune) {
            this.fD.comune.patchValue(comune);
          }
          this.fD.indirizzo.patchValue(sedeLegale.indirizzo);
          this.fD.cap.patchValue(sedeLegale.codCap);
          this.fD.telefono.patchValue(sedeLegale.telefono);
          this.fD.fax.patchValue(sedeLegale.fax);
          this.fD.email.patchValue(sedeLegale.email);
          this.fD.idSedeSilp.patchValue(sedeLegale.idSedeSilp);
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

// MODALE CERCA STATO ESTERO
  async onClickFindStatoEstero(section: string) {
    let decodifica: DecodificaGenerica;
    this.utilitiesService.showSpinner();
    if (section === 'distaccato') {
      decodifica = {
        codDecodifica: this.fD.statoEstero.get('codNazioneMin').value,
        dsDecodifica: this.fD.statoEstero.get('dsStatiEsteri').value
      };
    } else if (section === 'trasf') {
      decodifica = {
        codDecodifica: this.f.statoEsteroSedePrec.get('codNazioneMin').value,
        dsDecodifica: this.f.statoEsteroSedePrec.get('dsStatiEsteri').value
      };
    }
    try {
     const res = await  this.decodificaService.postStatiEsteriDecodifica(decodifica).toPromise();
     let decodificaFinded: DecodificaGenerica;
     if (res && res.length === 1) {
      decodificaFinded = res[0];
     } else if (res && res.length > 1) {
      decodificaFinded = await this.promptModalService.openDecodificaPrompt('Selezionare uno stato estero', res, TYPE_DECODIFICA_GENERICA.STATI_ESTERI, decodifica);
     }
     if (decodificaFinded) {
       const statoEstero: StatiEsteri = {
         id: decodificaFinded.idDecodifica,
        codNazioneMin: decodificaFinded.codDecodifica,
        dsStatiEsteri: decodificaFinded.dsDecodifica
       };
       if (section === 'distaccato') {
        this.fD.statoEstero.patchValue(statoEstero);
        } else if (section === 'trasf') {
         this.f.statoEsteroSedePrec.patchValue(statoEstero);
        }
     }
    } catch (e) {
      this.alertMessageService.setWarningMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  async onClickFindSedeOperativaDistacco() {
    this.alertMessageService.emptyMessages();
    this.utilitiesService.showSpinner();
    try{
      const cf = this.fD.cfDatoreDistaccatario.value;
      if(!Utils.isNullOrUndefinedOrCampoVuoto(cf)){
        const aziendaSilpFinded = await this.silpService.getAziendaSilp(cf).toPromise();
        const comunicazione: Comunicazione = this.data.comunicazioneToSave;
    const formDtTrasfTipoSpost = this.form.getRawValue() as FormDtTrasfTipoSpost;
    let trasferimentoDistaccoForm: TrasferimentoDistaccoForm = {
      formDtTrasfTipoSpost: formDtTrasfTipoSpost
    };

    trasferimentoDistaccoForm.formDistacco = this.formDistacco.getRawValue() as FormDistacco;
   

    const wrapperHelper: WrapperHelper = {
      comunicazione: comunicazione,
      trasferimentoDistaccoForm: trasferimentoDistaccoForm
    };
    this.router.navigate(['./dettaglio-azienda'], { state: {parametriNavigazione: this.routingParams, wrapperHelper: wrapperHelper, mode: 'edit', azienda: aziendaSilpFinded } });
      }
    }catch(e){
      if(e.error && e.error.length > 0){
        this.alertMessageService.setErrorMsg(e.error);
      }else{
        this.alertMessageService.setSingleErrorMsg(e);
      }
    }finally{
      this.utilitiesService.hideSpinner();
    }
    
  }

  // MODALE CERCA ATTIVITA' ATECO
  async onClickFindAttAteco() {
    let decodifica: DecodificaGenerica;
    this.utilitiesService.showSpinner();
    decodifica = {
      codDecodifica: this.fD.ateco.get('codAtecofinMin').value,
      dsDecodifica: this.fD.ateco.get('dsComTAtecofin').value
    };
    try {
      const res = await  this.decodificaService.postAtecofinDecodifica(decodifica).toPromise();
      let decodificaFinded: DecodificaGenerica;
      if (res && res.length === 1) {
        decodificaFinded = res[0];
      } else if (res && res.length > 1) {
        decodificaFinded = await this.promptModalService.openDecodificaPrompt('Selezionare un\'attivita\'', res, TYPE_DECODIFICA_GENERICA.ATECO, decodifica);
      }
      if (decodificaFinded) {
        const attAteco: Atecofin = {
          id: decodificaFinded.idDecodifica,
          codAtecofinMin: decodificaFinded.codDecodifica,
          dsComTAtecofin: decodificaFinded.dsDecodifica
        };
        this.fD.ateco.patchValue(attAteco);
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

  onClickResetDistacco() {
    this.form.reset();
    this.formDistacco.reset();
    if (!this.INS_MODE) {
      this.patchValueInForms(this.comunicazione);
    }

  }

  onClickTrasferimento() {
    this.form.reset();
    this.formTrasferimento.reset();
    if (!this.INS_MODE) {
      this.patchValueInForms(this.comunicazione);
    }
  }

  async onSubmitDistacco() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    try {
      if (this.comunicazioneToSave.tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID) {
        this.comunicazioneToSave.rapporto.dtTrasformazione = this.f.dtTrasferimento.value;
        this.comunicazioneToSave.rapporto.trasformazionerl = this.f.tipoSpostamento.value;
        this.comunicazioneToSave.rapporto.dtFineRapporto = this.fD.dataFineDistacco.value;
        this.comunicazioneToSave.rapporto.flgDistaccoParziale = this.fD.distaccoParziale.value;
        this.comunicazioneToSave.rapporto.flgDistaccoSuAziEstera = this.fD.distaccoAzEst.value;
        this.comunicazioneToSave.rapporto.dtEvento = this.comunicazioneToSave.rapporto.dtTrasformazione;
        if (!this.comunicazioneToSave.rapporto.datoreDistaccatario) {
          this.comunicazioneToSave.rapporto.datoreDistaccatario = new Object() as Datore;
        }
        if (this.comunicazioneToSave.rapporto.flgDistaccoSuAziEstera === 'N') {
          this.comunicazioneToSave.rapporto.datoreDistaccatario.codiceFiscale = this.fD.cfDatoreDistaccatario.value;
        }
        this.comunicazioneToSave.rapporto.datoreDistaccatario.dsDenominazioneDatore = this.fD.denominazione.value;
        this.comunicazioneToSave.rapporto.datoreDistaccatario.atecofin = this.fD.ateco.value as Atecofin;
        this.comunicazioneToSave.rapporto.datoreDistaccatario.patInail = this.fD.patINAIL.value;
        this.comunicazioneToSave.rapporto.datoreDistaccatario.idAziendaSilp = this.fD.idAziendaSilp.value;
        
        // if (this.EDIT_MODE) {
        //   // this.onClickFindDatoreDistaccatario();
        //   const cfAzSilp = this.fD.cfDatoreDistaccatario.value;
        //   this.aziendaSilpFinded = await this.silpService.getAziendaSilp(cfAzSilp).toPromise();
        //   if (this.aziendaSilpFinded) {
        //     this.datoreSelected = UtilsComonl.getDatoreFromDatiAzienda(this.aziendaSilpFinded) as Datore;
        //     this.fD.idAziendaSilp.patchValue(this.datoreSelected.idAziendaSilp);
        //   }
        //   this.comunicazioneToSave.rapporto.datoreDistaccatario.idAziendaSilp = this.fD.idAziendaSilp.value;
        // }
        if (!this.comunicazioneToSave.rapporto.datoreDistaccatario.sedeLegale) {
          this.comunicazioneToSave.rapporto.datoreDistaccatario.sedeLegale = new Object() as SedeLavoro;
        }
        this.comunicazioneToSave.rapporto.datoreDistaccatario.sedeLegale.statiEsteri = this.fD.statoEstero.getRawValue() as StatiEsteri;
        this.comunicazioneToSave.rapporto.datoreDistaccatario.sedeLegale.comune = this.fD.comune.getRawValue() as Comune;
        this.comunicazioneToSave.rapporto.datoreDistaccatario.sedeLegale.indirizzo = this.fD.indirizzo.value;
        this.comunicazioneToSave.rapporto.datoreDistaccatario.sedeLegale.codCap = this.fD.cap.value;
        this.comunicazioneToSave.rapporto.datoreDistaccatario.sedeLegale.telefono = this.fD.telefono.value;
        this.comunicazioneToSave.rapporto.datoreDistaccatario.sedeLegale.fax = this.fD.fax.value;
        this.comunicazioneToSave.rapporto.datoreDistaccatario.sedeLegale.email = this.fD.email.value;
        this.comunicazioneToSave.rapporto.datoreDistaccatario.sedeLegale.flgSedeLegale = 'S';
        this.comunicazioneToSave.rapporto.datoreDistaccatario.sedeLegale.idSedeSilp = this.fD.idSedeSilp.value;
        // if(this.fD.flgSedeLegale === "S"){

        // }else{
        //   if(!this.comunicazioneToSave.rapporto.datoreDistaccatario.sedeOperativa){
        //     this.comunicazioneToSave.rapporto.datoreDistaccatario.sedeOperativa = new Object() as SedeLavoro;
        //   }
        //   this.comunicazioneToSave.rapporto.datoreDistaccatario.sedeOperativa.statiEsteri = this.fD.statoEstero.value;
        //   this.comunicazioneToSave.rapporto.datoreDistaccatario.sedeOperativa.comune = this.fD.comune.value;
        //   this.comunicazioneToSave.rapporto.datoreDistaccatario.sedeOperativa.indirizzo = this.fD.indirizzo.value;
        //   this.comunicazioneToSave.rapporto.datoreDistaccatario.sedeOperativa.codCap = this.fD.cap.value;
        //   this.comunicazioneToSave.rapporto.datoreDistaccatario.sedeOperativa.telefono = this.fD.telefono.value;
        //   this.comunicazioneToSave.rapporto.datoreDistaccatario.sedeOperativa.fax = this.fD.fax.value;
        //   this.comunicazioneToSave.rapporto.datoreDistaccatario.sedeOperativa.email = this.fD.email.value;
        //   this.comunicazioneToSave.rapporto.datoreDistaccatario.sedeOperativa.idSedeSilp = this.fD.idSedeSilp.value;
        // }
      }
      if (this.comunicazioneToSave.tipoTracciato.id === TIPO_TRACCIATO.UNISOMM.ID) {
        this.comunicazioneToSave.missione.dtTrasformazione = this.f.dtTrasferimento.value;
        this.comunicazioneToSave.missione.trasformazionerl = this.f.tipoSpostamento.value;
        this.comunicazioneToSave.missione.dtFineRapporto = this.fD.dataFineDistacco.value;
        this.comunicazioneToSave.missione.flgDistaccoParziale = this.fD.distaccoParziale.value;
        this.comunicazioneToSave.missione.flgDistaccoSuAziEstera = this.fD.distaccoAzEst.value;
        this.comunicazioneToSave.missione.dtEvento = this.comunicazioneToSave.missione.dtTrasformazione;
        if (!this.comunicazioneToSave.missione.datoreDistaccatario) {
          this.comunicazioneToSave.missione.datoreDistaccatario = new Object() as Datore;
        }
        if (this.comunicazioneToSave.missione.flgDistaccoSuAziEstera === 'N') {
          this.comunicazioneToSave.missione.datoreDistaccatario.codiceFiscale = this.fD.cfDatoreDistaccatario.value;
        }
        this.comunicazioneToSave.missione.datoreDistaccatario.dsDenominazioneDatore = this.fD.denominazione.value;
        this.comunicazioneToSave.missione.datoreDistaccatario.atecofin = this.fD.ateco.value as Atecofin;
        this.comunicazioneToSave.missione.datoreDistaccatario.patInail = this.fD.patINAIL.value;
        if (!this.comunicazioneToSave.missione.datoreDistaccatario.sedeLegale) {
          this.comunicazioneToSave.missione.datoreDistaccatario.sedeLegale = new Object() as SedeLavoro;
        }
        this.comunicazioneToSave.missione.datoreDistaccatario.sedeLegale.statiEsteri = this.fD.statoEstero.getRawValue() as StatiEsteri;
        this.comunicazioneToSave.missione.datoreDistaccatario.sedeLegale.comune = this.fD.comune.getRawValue() as Comune;
        this.comunicazioneToSave.missione.datoreDistaccatario.sedeLegale.indirizzo = this.fD.indirizzo.value;
        this.comunicazioneToSave.missione.datoreDistaccatario.sedeLegale.codCap = this.fD.cap.value;
        this.comunicazioneToSave.missione.datoreDistaccatario.sedeLegale.telefono = this.fD.telefono.value;
        this.comunicazioneToSave.missione.datoreDistaccatario.sedeLegale.fax = this.fD.fax.value;
        this.comunicazioneToSave.missione.datoreDistaccatario.sedeLegale.email = this.fD.email.value;
        this.comunicazioneToSave.missione.datoreDistaccatario.sedeLegale.flgSedeLegale = 'S';
        // if(this.fD.flgSedeLegale === "S"){

        // }else{
        //   if(!this.comunicazioneToSave.missione.datoreDistaccatario.sedeOperativa){
        //     this.comunicazioneToSave.missione.datoreDistaccatario.sedeOperativa = new Object() as SedeLavoro;
        //   }
        //   this.comunicazioneToSave.missione.datoreDistaccatario.sedeOperativa.statiEsteri = this.fD.statoEstero.getRawValue() as StatiEsteri;
        //   this.comunicazioneToSave.missione.datoreDistaccatario.sedeOperativa.comune = this.fD.comune.getRawValue() as Comune;
        //   this.comunicazioneToSave.missione.datoreDistaccatario.sedeOperativa.indirizzo = this.fD.indirizzo.value;
        //   this.comunicazioneToSave.missione.datoreDistaccatario.sedeOperativa.codCap = this.fD.cap.value;
        //   this.comunicazioneToSave.missione.datoreDistaccatario.sedeOperativa.telefono = this.fD.telefono.value;
        //   this.comunicazioneToSave.missione.datoreDistaccatario.sedeOperativa.fax = this.fD.fax.value;
        //   this.comunicazioneToSave.missione.datoreDistaccatario.sedeOperativa.email = this.fD.email.value;
        // }
      }
      const wrapperComunicazione: WrapperComunicazione = {
        comunicazione: this.comunicazioneToSave,
        ruolo: this.ruolo
      }
      const res = await this.comunicazioneService.putDatiTrasferimentoDistacco(wrapperComunicazione).toPromise();
      if (res) {
        this.comunicazioneToSave = await this.comunicazioneService.getComunicazioneById(this.comunicazioneToSave.id).toPromise();
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

  async onSubmitTrasferimento() {
    console.log('submit trasferimento');
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    let comunicazioneToSave = Utils.clone(this.comunicazioneToSave);
    try {
      if (comunicazioneToSave.tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID) {
        comunicazioneToSave.rapporto.dtTrasformazione = this.f.dtTrasferimento.value;
        comunicazioneToSave.rapporto.trasformazionerl = this.f.tipoSpostamento.value;
        if (!comunicazioneToSave.rapporto.sedeLavoroPrecedente) {
          comunicazioneToSave.rapporto.sedeLavoroPrecedente = new Object() as SedeLavoro;
        }
        const statiEsteri: StatiEsteri = this.fT.statoEsteroSedePrec.getRawValue() as StatiEsteri;
        comunicazioneToSave.rapporto.sedeLavoroPrecedente.statiEsteri = statiEsteri;
        const comune: Comune = this.fT.comuneSedePrec.getRawValue() as Comune;
        comunicazioneToSave.rapporto.sedeLavoroPrecedente.comune = comune;
        comunicazioneToSave.rapporto.sedeLavoroPrecedente.indirizzo = this.fT.indirizzoSedePrec.value;
        comunicazioneToSave.rapporto.sedeLavoroPrecedente.codCap = this.fT.cap.value;
      }
      if (comunicazioneToSave.tipoTracciato.id === TIPO_TRACCIATO.UNISOMM.ID) {
        if (comunicazioneToSave.missione) {
          comunicazioneToSave.missione.dtTrasformazione = this.f.dtTrasferimento.value;
          comunicazioneToSave.missione.trasformazionerl = this.f.tipoSpostamento.value;
          if (!comunicazioneToSave.missione.sedeLavoroPrecedente) {
            comunicazioneToSave.missione.sedeLavoroPrecedente = new Object() as SedeLavoro;
          }
          const statiEsteri: StatiEsteri = this.fT.statoEsteroSedePrec.getRawValue() as StatiEsteri;
          comunicazioneToSave.missione.sedeLavoroPrecedente.statiEsteri = statiEsteri;
          const comune: Comune = this.fT.comuneSedePrec.getRawValue() as StatiEsteri;
          comunicazioneToSave.missione.sedeLavoroPrecedente.comune = comune;
          comunicazioneToSave.missione.sedeLavoroPrecedente.indirizzo = this.fT.indirizzoSedePrec.value;
          comunicazioneToSave.missione.sedeLavoroPrecedente.codCap = this.fT.cap;
        } else {
          comunicazioneToSave.rapporto.dtTrasformazione = this.f.dtTrasferimento.value;
          comunicazioneToSave.rapporto.trasformazionerl = this.f.tipoSpostamento.value;
          if (!comunicazioneToSave.rapporto.sedeLavoroPrecedente) {
            comunicazioneToSave.rapporto.sedeLavoroPrecedente = new Object() as SedeLavoro;
          }
          const statiEsteri: StatiEsteri = this.fT.statoEsteroSedePrec.getRawValue() as StatiEsteri;
          comunicazioneToSave.rapporto.sedeLavoroPrecedente.statiEsteri = statiEsteri;
          const comune: Comune = this.fT.comuneSedePrec.getRawValue() as Comune;
          comunicazioneToSave.rapporto.sedeLavoroPrecedente.comune = comune;
          comunicazioneToSave.rapporto.sedeLavoroPrecedente.indirizzo = this.fT.indirizzoSedePrec.value;
          comunicazioneToSave.rapporto.sedeLavoroPrecedente.codCap = this.fT.cap.value;
        }
      }
      const wrapperComunicazione: WrapperComunicazione = {
        comunicazione: comunicazioneToSave,
        ruolo: this.ruolo
      }
      const res = await this.comunicazioneService.putDatiTrasferimentoDistacco(wrapperComunicazione).toPromise();
      if (res) {
        this.comunicazioneToSave = await this.comunicazioneService.getComunicazioneById(this.comunicazioneToSave.id).toPromise();
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

  async onClickFindSedeOperativaTrasferimento() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    try {
        const cf = this.comunicazioneToSave.datoreAttuale.codiceFiscale;
        this.aziendaSilpFinded = await this.silpService.getAziendaSilp(cf).toPromise();
        // this.aziendaSilpFinded = await this.silpService.getAziendaSilp('01995120019').toPromise();
        if (this.aziendaSilpFinded) {
          const formDtTrasfTipoSpost = this.form.getRawValue() as FormDtTrasfTipoSpost;
          let trasferimentoDistaccoForm: TrasferimentoDistaccoForm = {
              formDtTrasfTipoSpost: formDtTrasfTipoSpost
          };
          const wrapperHelper: WrapperHelper = {
            comunicazione: this.comunicazioneToSave,
            trasferimentoDistaccoForm: trasferimentoDistaccoForm
          };
          this.router.navigate(['./dettaglio-azienda'], { state: {parametriNavigazione: this.routingParams, wrapperHelper: wrapperHelper, mode: 'edit', azienda: this.aziendaSilpFinded } });
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

  async onSubmitTrasferimentoUnisomm() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    let comunicazioneToSave = Utils.clone(this.comunicazioneToSave);
    console.log(this.form.controls.dtTrasferimento.value);
    console.log(this.form.controls.tipoSpostamento.value);

    if (
      this.UNILAV ||
      (this.UNISOMM && comunicazioneToSave.tipoSomministrazione.id !== TIPO_SOMMINISTRAZIONE.MISSIONE.ID)
    ) {
      comunicazioneToSave.rapporto.dtTrasformazione = this.form.controls.dtTrasferimento.value;
      comunicazioneToSave.rapporto.trasformazionerl = this.form.controls.tipoSpostamento.value;
    }
    if (
        this.UNISOMM && comunicazioneToSave.tipoSomministrazione.id === TIPO_SOMMINISTRAZIONE.MISSIONE.ID
    ) {
      comunicazioneToSave.missione.dtTrasformazione = this.form.controls.dtTrasferimento.value;
      comunicazioneToSave.missione.trasformazionerl = this.form.controls.tipoSpostamento.value;
    }
    try {
      const wrapperComunicazione: WrapperComunicazione = {
        comunicazione: comunicazioneToSave,
        ruolo: this.ruolo
      }
      const res = await this.comunicazioneService.putDatiTrasferimentoDistacco(wrapperComunicazione).toPromise();
      if (res) {
        this.comunicazioneToSave = await this.comunicazioneService.getComunicazioneById(res.id).toPromise();
        const persistenceComunicazioneWreapper: PersistenceComunicazioneWreapper = {
          comunicazione: comunicazioneToSave,
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

  onClickResetForm() {
    this.form.reset();
    this.patchValueInForm(this.comunicazione);
  }

}
