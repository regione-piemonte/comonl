/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { CONSTANTS_MODE, CONTROL_STATE, TYPE_DECODIFICA_GENERICA } from 'src/app/constants';
import { Comune, Comunicazione, ComunicazioneService, ComunicazioneUrgHolder, Datore, DecodificaGenerica, DecodificaService, Lavoratore, Rapporto, Ruolo, TipoComunicazioneTu, TipoSoggettoAbilitato, WrapperComunicazione } from 'src/app/modules/comonlapi';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { LogService, UtilitiesService } from 'src/app/services';
import { Utils } from 'src/app/utils';
import { AdComponent } from '../../../models/ad-component';
import { PersistenceComunicazioneWreapper } from '../../nav-main-comunicazione/nav-main-comunicazione.component';

@Component({
  selector: 'comonl-dati-generali-urg',
  templateUrl: './dati-generali-urg.component.html',
  styleUrls: ['./dati-generali-urg.component.scss']
})
export class DatiGeneraliUrgComponent implements OnInit, AdComponent {

  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="dati-generali-urg"]';


  @Input() data: any;

  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();
  @Output() goTonext: EventEmitter<any> = new EventEmitter<any>();
  @Output() persistenceNotification = new EventEmitter<PersistenceComunicazioneWreapper>();

  form: FormGroup;

  routingParams;
  comunicazione: Comunicazione;
  comunicazioneToSave: Comunicazione;
  formModalParams;


  tipoSoggettoAbilitatos: TipoSoggettoAbilitato[] = [];
  showForm: boolean;

  ruolo: Ruolo;

  

  // restituisce formControls
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

  get AGGIORNAMENTO_MODE() {
    const nuovaComParams = this.routingParams;
    return nuovaComParams.mode === CONSTANTS_MODE.AGGIORNAMENTO;
  }
  constructor(
    private logService: LogService,
    private utilitiesService: UtilitiesService,
    private decodificaService: DecodificaService,
    private promptModalService: PromptModalService,
    private comunicazioneService: ComunicazioneService,
    private alertMessageService: AlertMessageService
  ) { }

  async ngOnInit() {
    try {
      this.utilitiesService.showSpinner();
      this.logService.info(this.constructor.name, 'ngOnInit');
      this.utilitiesService.scrollTo(DatiGeneraliUrgComponent.SCROLL_TARGET);
      this.ruolo = this.data.ruolo;
      this.comunicazione = this.data.comunicazione;
      this.comunicazioneToSave = this.data.comunicazioneToSave;
      this.routingParams = this.data.routingParams;
      this.formModalParams = this.data.routingParams.formModalParams;

      this.tipoSoggettoAbilitatos = await this.decodificaService.getTipoSoggettoAbilitato().toPromise();
      this.initForm();
      this.patchValueInForm(this.comunicazioneToSave);
      this.setMode();
      this.showForm = true;
    } catch (e) {

    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  private initForm() {
    this.form = new FormGroup({
      flgComPerDatore: new FormControl(null),
      comunicazione: new FormGroup({
        id: new FormControl(),
        email: new FormControl(null, Validators.compose([Validators.required])),
        tipoSoggettoAbilitato: new FormControl(),
        codiceFiscaleSoggetto: new FormControl(),
        emailsoggetto: new FormControl(null),
        dtTimbroPostale: new FormControl(),
        dsMotivoUrgenza: new FormControl(null, Validators.required)
      }),
      datore: new FormGroup({
        id: new FormControl(),
        codiceFiscale: new FormControl(null, Validators.required),
        dsDenominazioneDatore: new FormControl(null, Validators.required),
      }),
      comune: new FormGroup({
        id: new FormControl(),
        codComuneMin: new FormControl(null,
          Validators.compose([Validators.required, Validators.minLength(3)])),
        dsComTComune: new FormControl(null, Validators.compose([Validators.required, Validators.minLength(3)]))
      }),
      lavoratore: new FormGroup({
        id: new FormControl(),
        codiceFiscale: new FormControl(null, [Validators.required,cfLengthValidator()]),
        cognome: new FormControl(null, Validators.required),
        nome: new FormControl(null, Validators.required)
      }),
      rapporto: new FormGroup({
        id: new FormControl(),
        dtInizioRapporto: new FormControl(Validators.required),
      })
    });
    if (this.ruolo.amministratore || this.ruolo.operatoreProvinciale) {
      this.f.flgComPerDatore.setValidators(Validators.required);
      this.f.comunicazione.get('tipoSoggettoAbilitato').setValidators(Validators.required);
      this.f.comunicazione.get('codiceFiscaleSoggetto').setValidators(Validators.required);
      this.f.comunicazione.get('emailsoggetto').setValidators([Validators.required]);
      this.f.comunicazione.get('dtTimbroPostale').setValidators(Validators.required);
      this.f.comunicazione.get('tipoSoggettoAbilitato').updateValueAndValidity();
      this.f.comunicazione.get('codiceFiscaleSoggetto').updateValueAndValidity();
      this.f.comunicazione.get('emailsoggetto').updateValueAndValidity();
      this.f.comunicazione.get('dtTimbroPostale').updateValueAndValidity();
      this.f.flgComPerDatore.updateValueAndValidity();
    }
  }

  private async patchValueInForm(comunicazione: Comunicazione){
    const datore: Datore = comunicazione.datoreAttuale;
    const rapporto: Rapporto = comunicazione.rapporto;
    const lavoratore : Lavoratore = comunicazione.lavoratori && comunicazione.lavoratori.length > 0 ? comunicazione.lavoratori[0] : null;
    let email = comunicazione.email;
    if (!email) {
      // se email non è presente sono in inserimento
        if (this.INS_MODE || this.RETTIFICA_MODE || this.AGGIORNAMENTO_MODE) {
          email = this.ruolo.email;
        }
      }
      this.f.comunicazione.controls.email.patchValue(email);
      this.f.comunicazione.controls.dsMotivoUrgenza.patchValue(comunicazione.dsMotivoUrgenza);
      /*Sezione: Dati del soggetto per cui si effettua la comunicazione (solo per ruolo APL e Amministratore)*/
    if (
        this.ruolo.amministratore || 
        this.ruolo.operatoreProvinciale || 
        this.EDIT_MODE
      ) {
      if (comunicazione.flgComDatLav) {
        this.f.flgComPerDatore.setValue(comunicazione.flgComDatLav);
      } else {
        if (this.ruolo.amministratore) {
          this.f.flgComPerDatore.setValue('N');
        } else if (this.ruolo.operatoreProvinciale) {
          this.f.flgComPerDatore.setValue('S');
        }
      }
      const tipoSoggettoAbilitato = comunicazione.tipoSoggettoAbilitato;
      if (tipoSoggettoAbilitato) {
        /**
         * PER TUTTI I CASI ESCLUSO L'INSERIMENTO(Per L'inserimento comanda direttamente il valore di flgComunicazioneDatore)
         * se (tipoSoggettoAbilitato è valorizzato) => flgComunicazioneDatore = 'N'
         * se flgComunicazioneDatore = N => sono obbligatori e devono avere un valore:
         * tipoSoggettoAbilitato, codiceFiscaleSoggetto. emailsoggetto è opzionale ma non può avere un valore se flgComunicazioneDatore = 'S'
         */
        const tipoSoggettoAbilitatoFinded = this.tipoSoggettoAbilitatos.find(el => {
          return el.id === tipoSoggettoAbilitato.id;
        });
        this.f.comunicazione.controls.tipoSoggettoAbilitato.patchValue(tipoSoggettoAbilitatoFinded);

        this.f.comunicazione.controls.codiceFiscaleSoggetto.patchValue(comunicazione.codiceFiscaleSoggetto);
        this.f.comunicazione.controls.emailsoggetto.patchValue(comunicazione.emailsoggetto);
      }
      this.f.comunicazione.controls.dtTimbroPostale.patchValue(comunicazione.dtTimbroPostale);
    }
    if(datore){
      this.f.datore.patchValue(datore);
      const comune = datore.sedeOperativa ? datore.sedeOperativa.comune : null;
      if(comune){
        this.f.comune.patchValue(comune);
      }
    }
    if(rapporto){
      this.f.rapporto.patchValue(rapporto);
    }
    if(lavoratore){
      this.f.lavoratore.patchValue(lavoratore);
    }
  }
  

  setMode() {
   if(this.VIEW_MODE){
     this.form[CONTROL_STATE.DISABLE]();
   }else{
    if (this.ruolo.amministratore || this.ruolo.operatoreProvinciale) {
      this.setStateAltroSogg();
    }else{
      this.setModeRuoli();
    }
   }
   
  }

  private setModeRuoli(){
    if (!(this.ruolo.amministratore || this.ruolo.operatoreProvinciale || this.ruolo.consulenteRespo)){
      const codiceFiscale = this.ruolo.codiceFiscaleAzienda;
      const denominazione = this.ruolo.denominazioneAzienda;
      this.f.datore.controls.codiceFiscale.patchValue(codiceFiscale);
      this.f.datore.controls.dsDenominazioneDatore.patchValue(denominazione);
      this.utilitiesService.changeControlState(
        this.f.datore.controls.codiceFiscale,
        CONTROL_STATE.DISABLE,
       false
       )
       this.utilitiesService.changeControlState(
         this.f.datore.controls.dsDenominazioneDatore,
         CONTROL_STATE.DISABLE,
        false
        )
    }
  }


  setStateAltroSogg() {
    const flgComunicazioneDatore = this.f.flgComPerDatore.value;
    let state = flgComunicazioneDatore === 'S' ? CONTROL_STATE.DISABLE : CONTROL_STATE.ENABLE;
    this.utilitiesService.changeControlState(this.f.comunicazione.controls.tipoSoggettoAbilitato, state, state === CONTROL_STATE.DISABLE ? true : false);
    this.utilitiesService.changeControlState(this.f.comunicazione.controls.codiceFiscaleSoggetto, state, state === CONTROL_STATE.DISABLE ? true : false);
    this.utilitiesService.changeControlState(this.f.comunicazione.controls.emailsoggetto, state, state === CONTROL_STATE.DISABLE ? true : false);
  }

  async onClickFindComune() {
    this.utilitiesService.showSpinner();
    const decodifica: DecodificaGenerica = {
      codDecodifica: this.f.comune.get('codComuneMin').value,
      dsDecodifica: this.f.comune.get('dsComTComune').value
    };
    try {
     const res = await  this.decodificaService.postComuneDecodifica(decodifica).toPromise();
     let docodificaFinded: DecodificaGenerica;
     if (res && res.length === 1) {
      docodificaFinded = res[0];
     } else if (res && res.length > 1) {
      docodificaFinded = await this.promptModalService.openDecodificaPrompt('Selezionare un comune', res, TYPE_DECODIFICA_GENERICA.COMUNE, decodifica);
     }
     if (docodificaFinded) {
       const comune: Comune = {
         id: docodificaFinded.idDecodifica,
         codComuneMin: docodificaFinded.codDecodifica,
         dsComTComune: docodificaFinded.dsDecodifica
       };
       this.f.comune.patchValue(comune);
     }
    } catch (e) {
      this.alertMessageService.setWarningMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  onClickReset() {
    this.form.reset();
    this.patchValueInForm(this.comunicazione);
    this.setModeRuoli();
  }

  async onClickSubmit() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const flgComPerDatore = this.f.flgComPerDatore.value;
    const comunicazione: Comunicazione = this.form.controls.comunicazione.value as Comunicazione;
    const datore: Datore = this.f.datore.getRawValue() as Datore;
    const rapporto : Rapporto = this.form.controls.rapporto.value as Rapporto;
    const lavoratore: Lavoratore = this.form.controls.lavoratore.value as Lavoratore;
    const comune: Comune = this.form.controls.comune.value as Comune;
    //this.comunicazioneToSave.id = comunicazione.id;
    this.comunicazioneToSave.email = comunicazione.email;
    this.comunicazioneToSave.flgComDatLav = flgComPerDatore;
    if(flgComPerDatore === 'N'){
      this.comunicazioneToSave.tipoSoggettoAbilitato = comunicazione.tipoSoggettoAbilitato;
      this.comunicazioneToSave.codiceFiscaleSoggetto = comunicazione.codiceFiscaleSoggetto;
      this.comunicazioneToSave.emailsoggetto = comunicazione.emailsoggetto;
    }
    
    this.comunicazioneToSave.dtTimbroPostale = comunicazione.dtTimbroPostale;
    this.comunicazioneToSave.dsMotivoUrgenza = comunicazione.dsMotivoUrgenza;
    if(!this.comunicazioneToSave.datoreAttuale){
      this.comunicazioneToSave.datoreAttuale = new Object() as Datore;
    }
    this.comunicazioneToSave.datoreAttuale.codiceFiscale = datore.codiceFiscale;
    this.comunicazioneToSave.datoreAttuale.dsDenominazioneDatore = datore.dsDenominazioneDatore;

    if(!this.comunicazioneToSave.rapporto){
      this.comunicazioneToSave.rapporto = new Object() as Rapporto;
    }
    this.comunicazioneToSave.rapporto.dtInizioRapporto = rapporto.dtInizioRapporto;
    if(this.INS_MODE){
      this.comunicazioneToSave.datoreAttuale.sedeOperativa = {
        id: null
      };
      this.comunicazioneToSave.lavoratori = [];
    }

    if(this.comunicazioneToSave.lavoratori.length === 0){
      this.comunicazioneToSave.lavoratori.push(lavoratore);
    }else{
      this.comunicazioneToSave.lavoratori[0] = lavoratore;
    }

    this.comunicazioneToSave.datoreAttuale.sedeOperativa.flgSedeLegale = 'N';
    this.comunicazioneToSave.datoreAttuale.sedeOperativa.comune = comune;
    
    
    this.comunicazioneToSave.flgSommin = "N"
    this.comunicazioneToSave.flgMultiLav = "N";
    this.comunicazioneToSave.flgCurrentRecord = "S";
    this.comunicazioneToSave.tipoComunicazioneTu = {
      id: 1,
      codTipoComunicazioneMin: '01'
    } as TipoComunicazioneTu;
    let wrapperComunicazione: WrapperComunicazione = {
      ruolo: this.ruolo,
      comunicazione: this.comunicazioneToSave
    };
    let res;
    try {
      if(this.INS_MODE){
        res = await this.comunicazioneService.postComunicazioneUrgenza(wrapperComunicazione).toPromise();
      }else if(this.EDIT_MODE){
        res = await this.comunicazioneService.putComunicazioneUrgenza(wrapperComunicazione).toPromise();
      }
      if(res){
        this.prosegui(res);
      }
    } catch (e) {
      if(e.error && e.error.length > 0){
        this.alertMessageService.setErrorMsg(e.error);
      }else{
        this.alertMessageService.setSingleErrorMsg(e);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  private async prosegui(id: number) {
    let responseComunicazione = await this.comunicazioneService.getComunicazioneById(id).toPromise();
    const persistenceComunicazioneWreapper: PersistenceComunicazioneWreapper = {
           comunicazione: responseComunicazione,
           ricalcoloQuadri: false,
           switchEditMode: this.INS_MODE,
           success: true
         };
    this.persistenceNotification.emit(persistenceComunicazioneWreapper);
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

function cfLengthValidator(): ValidatorFn {
  return (control: AbstractControl): { [key: string]: boolean } | null => {
    const cf = control.value;
    if(!Utils.isNullOrUndefinedOrCampoVuoto(cf) && (cf.length < 11 || (cf.length > 11 && cf.length < 16)) ){
      return { cfLengthValidate: true };
    }
    return null;
  };
}
