/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CONSTANTS_MODE, CONTROL_STATE, TYPE_DECODIFICA_GENERICA } from 'src/app/constants';
import { WrapperHelper } from 'src/app/models/wrappwer-helper';
import { Atecofin, Comune, ComunicazioneControlliService, DatiAzienda, Datore, DecodificaGenerica, DecodificaService, Ruolo, SedeLavoro, SilpService, StatiEsteri, WrapperComunicazione } from 'src/app/modules/comonlapi';
import { Comunicazione } from 'src/app/modules/comonlapi/model/comunicazione';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { LogService,  UtilitiesService } from 'src/app/services';
import { UtilsComonl } from 'src/app/services/utilsComonl';
import { Utils } from 'src/app/utils';
import { PersistenceComunicazioneWreapper } from '../../nav-main-comunicazione/nav-main-comunicazione.component';

@Component({
  selector: 'comonl-azienda-utilizzatrice',
  templateUrl: './azienda-utilizzatrice.component.html',
  styleUrls: ['./azienda-utilizzatrice.component.scss']
})
export class AziendaUtilizzatriceComponent implements OnInit {

  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="azienda-utilizzatrice"]';

  formDatiAziendaUtilizzatrice: FormGroup;
  formRadio: FormGroup;

  @Input() data: any;
  @Output() datiForm = new EventEmitter<any>();
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();
  @Output() persistenceNotification = new EventEmitter<PersistenceComunicazioneWreapper>();

  routingParams;

  comunicazione: Comunicazione;
  comunicazioneToSave: Comunicazione;

  ruolo: Ruolo;

  atecoScaduto: boolean = false;
  statiEsteriSedeLegaleScaduto: boolean = false;
  comuneSedeOperativaScaduto: boolean = false;
  statiEsteriSedeOperativacaduto: boolean = false;


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

  get TEL_FAX_MAIL_SEDE_LEGALE_INVALID(): boolean {
    const sedeLegale: SedeLavoro = this.f.sedeLegale.getRawValue() as SedeLavoro;
    if (sedeLegale) {
      return Utils.isNullOrUndefinedOrCampoVuoto(sedeLegale.telefono) && Utils.isNullOrUndefinedOrCampoVuoto(sedeLegale.fax) && Utils.isNullOrUndefinedOrCampoVuoto(sedeLegale.email);
    }
    return true;
  }
  get TEL_FAX_MAIL_SEDE_OPERATIVA_INVALID(): boolean {
    const sedeOperativa: SedeLavoro = this.f.sedeOperativa.getRawValue() as SedeLavoro;
    if (sedeOperativa) {
      return Utils.isNullOrUndefinedOrCampoVuoto(sedeOperativa.telefono) && Utils.isNullOrUndefinedOrCampoVuoto(sedeOperativa.fax) && Utils.isNullOrUndefinedOrCampoVuoto(sedeOperativa.email);
    }
    return true;
  }

  get COMUNE_STATI_ESTERI_SEDE_LEGALE_INVALID(): boolean {
    const sedeLegale: SedeLavoro = this.f.sedeLegale.getRawValue() as SedeLavoro;
    const comune = sedeLegale.comune;
    const statiEsteri = sedeLegale.statiEsteri;
    if ((Utils.isNullOrUndefinedOrCampoVuoto(comune.codComuneMin) || Utils.isNullOrUndefinedOrCampoVuoto(comune.dsComTComune)) &&
          (Utils.isNullOrUndefinedOrCampoVuoto(statiEsteri.codNazioneMin) || Utils.isNullOrUndefinedOrCampoVuoto(statiEsteri.dsStatiEsteri))
        ) {
      return true;
    } else {
      return false;
    }
  }

  get COMUNE_STATI_ESTERI_SEDE_OPERATIVA_INVALID(): boolean {
    const sedeOperativa: SedeLavoro = this.f.sedeOperativa.getRawValue() as SedeLavoro;
    const comune = sedeOperativa.comune;
    const statiEsteri = sedeOperativa.statiEsteri;
    if ((Utils.isNullOrUndefinedOrCampoVuoto(comune.codComuneMin) || Utils.isNullOrUndefinedOrCampoVuoto(comune.dsComTComune)) &&
            (Utils.isNullOrUndefinedOrCampoVuoto(statiEsteri.codNazioneMin) || Utils.isNullOrUndefinedOrCampoVuoto(statiEsteri.dsStatiEsteri))
    ) {
        return true;
      } else {
        return false;
      }
  }

  get CAP_SEDE_OPERATIVA_INVALID(): boolean{
    const sedeOperativa: SedeLavoro = this.f.sedeOperativa.getRawValue() as SedeLavoro;
    const comune: Comune = sedeOperativa.comune;
    const statiEsteri = sedeOperativa.statiEsteri;
    const codCap = this.f.sedeOperativa.get('codCap').value;
    if(Utils.isNullOrUndefinedOrCampoVuoto(codCap) && !Utils.isNullOrUndefinedOrCampoVuoto(comune.codComuneMin) && !Utils.isNullOrUndefinedOrCampoVuoto(comune.dsComTComune)){
      return true;
    }
    return false;
  }

  get INVALIDITA_ANNULLO_MODE(): boolean {
    const flgSedeLegaleEstero = this.formRadio.controls.flgSedeLegaleEstero.value;
    if (this.ANNULLO_MODE && flgSedeLegaleEstero === 'S') {
      const ateco: Atecofin = this.f.atecofin.value;
      const statiesteriSedeLegale: StatiEsteri = this.f.sedeLegale.get('statiEsteri').value;
      return (
                Utils.isNullOrUndefinedOrCampoVuoto(ateco.codAtecofinMin) ||
                Utils.isNullOrUndefinedOrCampoVuoto(ateco.dsComTAtecofin) ||
                Utils.isNullOrUndefinedOrCampoVuoto(statiesteriSedeLegale.codNazioneMin) ||
                Utils.isNullOrUndefinedOrCampoVuoto(statiesteriSedeLegale.dsStatiEsteri) ||
                this.COMUNE_STATI_ESTERI_SEDE_OPERATIVA_INVALID
              );
    } else {
      return false;
    }
  }

  get INVALIDITA_FORM(): boolean {
    if (this.ANNULLO_MODE) {
      return false;
    } else {
        return !this.formDatiAziendaUtilizzatrice.valid;
      }
  }

  get campiNotValid(): boolean {
    const flgSedeLegaleEstero: boolean = this.formRadio.controls.flgSedeLegaleEstero.value;
    const codiceFiscale: string = this.f.codiceFiscale.value;
    const dsDenominazioneDatore: string =  this.f.dsDenominazioneDatore.value;
    const flgPubAmm: string = this.f.flgPubAmm.value;
    const codCapSedeLegale: string = this.f.sedeLegale.get('codCap').value;
    const indirizzoSedeLegale: string = this.f.sedeLegale.get('indirizzo').value;
    const codCapSedeOperativa: string = this.f.sedeOperativa.get('codCap').value;
    const indirizzoSedeOperativa: string = this.f.sedeOperativa.get('indirizzo').value;
    return (
              this.CAP_SEDE_OPERATIVA_INVALID ||
              Utils.isNullOrUndefinedOrCampoVuoto(dsDenominazioneDatore) ||
              Utils.isNullOrUndefinedOrCampoVuoto(flgPubAmm) ||
              (!flgSedeLegaleEstero && Utils.isNullOrUndefinedOrCampoVuoto(codCapSedeLegale)) ||
              Utils.isNullOrUndefinedOrCampoVuoto(indirizzoSedeLegale) ||
              Utils.isNullOrUndefinedOrCampoVuoto(indirizzoSedeOperativa) ||
              (this.obbligCodiceFiscale && Utils.isNullOrUndefinedOrCampoVuoto(codiceFiscale)) ||
              this.TEL_FAX_MAIL_SEDE_LEGALE_INVALID ||
              this.TEL_FAX_MAIL_SEDE_OPERATIVA_INVALID ||
              this.COMUNE_STATI_ESTERI_SEDE_LEGALE_INVALID ||
              this.COMUNE_STATI_ESTERI_SEDE_OPERATIVA_INVALID
              
            );

  }

  get obbligCodiceFiscale(): boolean {
    return this.formRadio.controls.flgSedeLegaleEstero.value === false;
  }

  flgSomm = false;
  scadenzaPermesso = true;
  somministrazione = false;
  showForm: boolean;
  aziUtilizzatrice: Datore;
  aziUtilizzatriceForReset: Datore;

  get PERMESSO_RUOLI(): boolean {
    return this.ruolo.amministratore || this.ruolo.consulenteRespo || this.ruolo.operatoreProvinciale;
  }

  get f() {
    return this.formDatiAziendaUtilizzatrice.controls as any;
  }



  constructor(
    private logService: LogService,
    private utilitiesService: UtilitiesService,
    private router: Router,
    private alertMessageService: AlertMessageService,
    private decodificaService: DecodificaService,
    private promptModalService: PromptModalService,
    private silpService: SilpService,
    private comunicazioneControlliService: ComunicazioneControlliService
  ) {

  }

  async ngOnInit() {
    await this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    this.utilitiesService.scrollTo(AziendaUtilizzatriceComponent.SCROLL_TARGET);
    this.routingParams = this.data.routingParams;
    this.ruolo = this.data.ruolo;
    this.comunicazione = this.data.comunicazione;
    this.comunicazioneToSave = this.data.comunicazioneToSave;
    try {
      this.initForm();
      if (this.data.datore) {
        // se this.data.datore è valorizzato, vuol dire che sto arrivando col dato dalla ricerca Angarfica azienda
        this.aziUtilizzatrice = this.data.datore;
      } else {
        let datiAzienda: DatiAzienda;
        if (this.comunicazioneToSave.missione) {
          this.aziUtilizzatrice = this.comunicazioneToSave.missione.aziUtilizzatrice;
        } else if (this.comunicazioneToSave.rapporto) {
          this.aziUtilizzatrice = this.comunicazioneToSave.rapporto.aziUtilizzatrice;
        }
        if(this.AGGIORNAMENTO_MODE && !this.comunicazioneToSave.id && this.aziUtilizzatrice && !Utils.isNullOrUndefinedOrCampoVuoto(this.aziUtilizzatrice.codiceFiscale)){
          const codiceFiscale: string = this.aziUtilizzatrice.codiceFiscale;
          this.f.codiceFiscale.patchValue(codiceFiscale);
          datiAzienda = await this.silpService.getAziendaSilp(codiceFiscale).toPromise();
          this.aziUtilizzatriceForReset = Utils.clone(this.aziUtilizzatrice);
        }
        if (this.EDIT_MODE || this.RETTIFICA_MODE || (this.AGGIORNAMENTO_MODE && this.comunicazioneToSave.id)) {
          const codiceFiscale: string = this.aziUtilizzatrice.codiceFiscale;
          const sedeLegale: SedeLavoro = this.aziUtilizzatrice.sedeLegale;
          if (sedeLegale && !sedeLegale.statiEsteri) {
            this.f.codiceFiscale.patchValue(codiceFiscale);
            datiAzienda = await this.silpService.getAziendaSilp(codiceFiscale).toPromise();
          }
          this.aziUtilizzatriceForReset = Utils.clone(this.aziUtilizzatrice);
        }
        if (datiAzienda) {
          let id: number;
          let idSedeLegale: number;
          let idSedeOperativa: number;
          let sedeOperativa: SedeLavoro;
          if(this.comunicazioneToSave.id){
            id = this.aziUtilizzatrice.id;
            idSedeLegale = this.aziUtilizzatrice.sedeLegale.id;
            idSedeOperativa = this.aziUtilizzatrice.sedeOperativa.id;
            if(this.aziUtilizzatrice){
              sedeOperativa = this.aziUtilizzatrice.sedeOperativa;
            }

          }
          const atecofin: Atecofin = this.aziUtilizzatrice.atecofin;
          const numContrSomm: number = this.aziUtilizzatrice.numContrSomm;
          const dtInizioContrattoSom: Date = this.aziUtilizzatrice.dtInizioContrattoSom;
          const dtFineContrattoSom: Date = this.aziUtilizzatrice.dtFineContrattoSom;
          this.aziUtilizzatrice = UtilsComonl.getDatoreFromDatiAzienda(datiAzienda);
          this.aziUtilizzatrice.id = id;
          this.aziUtilizzatrice.atecofin = atecofin;
          this.aziUtilizzatrice.numContrSomm = numContrSomm;
          this.aziUtilizzatrice.dtInizioContrattoSom = dtInizioContrattoSom;
          this.aziUtilizzatrice.dtFineContrattoSom = dtFineContrattoSom;
          this.aziUtilizzatrice.sedeLegale.id = idSedeLegale;
          if(sedeOperativa && sedeOperativa.id){
            this.aziUtilizzatrice.sedeOperativa = this.getSedeOperativa(datiAzienda,sedeOperativa);
          }
          this.aziUtilizzatrice.sedeOperativa.id = idSedeOperativa;
        }
      }
      
      this.patchValueInForm(this.aziUtilizzatrice);
      this.setFormMode();
      this.showForm = true;
      this.logService.info(this.constructor.name, 'ngOnInit');
    } catch (e) {
      this.setFormMode();
      this.showForm = true;
      if(e.error && e.error.length > 0){
        this.alertMessageService.setErrorMsg(e.error);
      }else{
        this.alertMessageService.setSingleErrorMsg(e);
      }
    } finally {
      await this.utilitiesService.hideSpinner();
    }
  }

  private initForm() {
    this.formRadio = new FormGroup({
      flgSedeLegaleEstero: new FormControl(false, Validators.required)
    });
    this.formDatiAziendaUtilizzatrice = new FormGroup({
      id: new FormControl(),
      idAziendaSilp: new FormControl(),
      codiceFiscale: new FormControl({value: null, disabled: true}),
      partitaIva: new FormControl({value: null, disabled: true}),
      dsDenominazioneDatore: new FormControl(null),
      flgPubAmm: new FormControl('N'),
      atecofin: new FormGroup({
        id: new FormControl(),
        codAtecofinMin: new FormControl(null,
          Validators.compose([
            Validators.required,
            Validators.minLength(3)])
          ),
        dsComTAtecofin: new FormControl(null,
          Validators.compose([
            Validators.required,
            Validators.minLength(3)
        ]))
      }),
      numContrSomm: new FormControl(null, Validators.required),
      dtInizioContrattoSom: new FormControl(null, Validators.required),
      dtFineContrattoSom: new FormControl(null),
      sedeLegale: new FormGroup({
        id: new FormControl(),
        flgSedeLegale: new FormControl('S'),
        idSedeSilp: new FormControl(),
        codCap: new FormControl(null),
        indirizzo: new FormControl(null),
        telefono: new FormControl(null),
        fax: new FormControl(null),
        email: new FormControl(null),
        comune: new FormGroup({
          id: new FormControl(null),
          codComuneMin: new FormControl(null, Validators.minLength(3)),
          dsComTComune: new FormControl(null, Validators.minLength(3))
        }),
        statiEsteri: new FormGroup({
          id: new FormControl(null),
          codNazioneMin: new FormControl(null, Validators.minLength(3)),
          dsStatiEsteri: new FormControl(null, Validators.minLength(3))
        })
      }),
      sedeOperativa: new FormGroup({
        id: new FormControl(),
        flgSedeLegale: new FormControl('N'),
        idSedeSilp: new FormControl(),
        codCap: new FormControl(null),
        indirizzo: new FormControl(null),
        telefono: new FormControl(null),
        fax: new FormControl(null),
        email: new FormControl(null),
        comune: new FormGroup({
          id: new FormControl(null),
          codComuneMin: new FormControl(null, Validators.minLength(3)),
          dsComTComune: new FormControl(null, Validators.minLength(3))
        }),
        statiEsteri: new FormGroup({
          id: new FormControl(null),
          codNazioneMin: new FormControl(null, Validators.minLength(3)),
          dsStatiEsteri: new FormControl(null, Validators.minLength(3))
        })
      })
    });
  }

  private getSedeOperativa(datiAzienda: DatiAzienda, sedeOperativa: SedeLavoro): SedeLavoro {
    if (datiAzienda && datiAzienda.sedeLegale) {
      if (sedeOperativa && String(sedeOperativa.idSedeSilp) === datiAzienda.sedeLegale.idSedeSilp) {
        return sedeOperativa;
      }
    }
    if (datiAzienda && sedeOperativa) {
      if (datiAzienda.elencoSedi) {
        const sedeOperativaFinded = datiAzienda.elencoSedi.find(el => {return el.idSedeSilp === String(sedeOperativa.idSedeSilp)});
        if (sedeOperativaFinded) {
          return UtilsComonl.getSedeLavoroFromSede(sedeOperativaFinded);
        }
      }
    }
    
    return sedeOperativa;
    
  }

  private patchValueInForm(aziUtilizzatrice: Datore) {
    if (aziUtilizzatrice) {
      this.formDatiAziendaUtilizzatrice.patchValue(aziUtilizzatrice);

      const sedeLegale = aziUtilizzatrice.sedeLegale;
      if (sedeLegale && sedeLegale.statiEsteri) {
        if (!Utils.isNullOrUndefinedOrCampoVuoto(sedeLegale.statiEsteri.codNazioneMin)) {
          this.formRadio.controls.flgSedeLegaleEstero.patchValue(true);
        }
      }
    }
  }

  private setFormMode() {
    if (this.VIEW_MODE || this.ANNULLO_MODE) {
      this.formRadio[CONTROL_STATE.DISABLE]();
      this.formDatiAziendaUtilizzatrice[CONTROL_STATE.DISABLE]();
    }
    if (this.RETTIFICA_MODE) {
      this.setFormControlsState();
    }
    if (this.INS_MODE || this.EDIT_MODE || this.AGGIORNAMENTO_MODE) {
      this.setFormControlsState();
    }
  }

  goToAnagrafica() {
    const comunicazione: Comunicazione = this.data.comunicazioneToSave;
    const wrapperHelper: WrapperHelper = {
      comunicazione: comunicazione
    };
    this.router.navigate(['/anagrafica-azienda'], { state: { parametriNavigazione: this.routingParams, wrapperHelper: wrapperHelper} } );
  }

  async onClickFindAteco() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const decodifica: DecodificaGenerica = {
      codDecodifica: this.f.atecofin.get('codAtecofinMin').value,
      dsDecodifica: this.f.atecofin.get('dsComTAtecofin').value
    };
    try {
      const res = await  this.decodificaService.postAtecofinDecodifica(decodifica).toPromise();
      let docodificaFinded: DecodificaGenerica;
      if (res && res.length === 1) {
       docodificaFinded = res[0];
      } else if (res && res.length > 1) {
       docodificaFinded = await this.promptModalService.openDecodificaPrompt('Selezione ATECO', res, TYPE_DECODIFICA_GENERICA.ATECO, decodifica);
      }
      if (docodificaFinded) {
        const atecofin: Atecofin = {
          id: docodificaFinded.idDecodifica,
          codAtecofinMin: docodificaFinded.codDecodifica,
          dsComTAtecofin: docodificaFinded.dsDecodifica
        };
        this.f.atecofin.get('id').setValue(atecofin.id);
        this.f.atecofin.get('codAtecofinMin').setValue(atecofin.codAtecofinMin);
        this.f.atecofin.get('dsComTAtecofin').setValue(atecofin.dsComTAtecofin);
      }
     } catch (e) {
       if(e.error && e.error.length > 0){
         this.alertMessageService.setWarningMsg(e.error);
       }else{
        if( e!= 0)
          this.alertMessageService.setSingleErrorMsg(e);
       }
     } finally {
       this.utilitiesService.hideSpinner();
     }
  }


  async onClickFindComune(control: AbstractControl) {
    this.utilitiesService.showSpinner();
    const decodifica: DecodificaGenerica = {
      codDecodifica: control.get('codComuneMin').value,
      dsDecodifica: control.get('dsComTComune').value
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
       control.patchValue(comune);
     }
    } catch (e) {
      if(e.error && e.error.length > 0){
        this.alertMessageService.setWarningMsg(e.error);
      }else{
        if( e!= 0)
          this.alertMessageService.setSingleErrorMsg(e);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  async onClickFindStatoEstero(control: AbstractControl) {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const decodifica: DecodificaGenerica = {
      codDecodifica: control.get('codNazioneMin').value,
      dsDecodifica: control.get('dsStatiEsteri').value
    };
    try {
      const res = await  this.decodificaService.postStatiEsteriDecodifica(decodifica).toPromise();
      let docodificaFinded: DecodificaGenerica;
      if (res && res.length === 1) {
       docodificaFinded = res[0];
      } else if (res && res.length > 1) {
       docodificaFinded = await this.promptModalService.openDecodificaPrompt('Selezionare uno stato', res, TYPE_DECODIFICA_GENERICA.STATI_ESTERI, decodifica);
      }
      if (docodificaFinded) {
        const stato: StatiEsteri = {
          id: docodificaFinded.idDecodifica,
          codNazioneMin: docodificaFinded.codDecodifica,
          dsStatiEsteri: docodificaFinded.dsDecodifica
        };
        control.patchValue(stato);
      }
     } catch (e) {
      if(e.error && e.error.length > 0){
        this.alertMessageService.setWarningMsg(e.error);
      }else{
        if( e!= 0)
          this.alertMessageService.setSingleErrorMsg(e);
      }
     } finally {
       this.utilitiesService.hideSpinner();
     }
  }

  onClickflgSedeLegaleEstero(clean: boolean) {
    this.setFormControlsState(clean);
  }

  private setFormControlsState(clean?: boolean) {
    const flgSedeLegaleEstero = this.formRadio.controls.flgSedeLegaleEstero.value;
    const controlState = flgSedeLegaleEstero ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE;
    if (clean) {
      this.f.codiceFiscale.setValue(null);
      this.f.partitaIva.setValue(null);
    }
    this.utilitiesService.changeControlState(
      this.f.dsDenominazioneDatore,
      controlState,
      clean
    );
    this.utilitiesService.changeControlState(
      this.f.flgPubAmm,
      controlState,
      clean
    );
    if (clean) {
      this.f.flgPubAmm.patchValue('N');
    }
    // this.utilitiesService.changeControlState(
    //   this.f.atecofin,
    //   controlState,
    //   clean
    // );
    // this.utilitiesService.changeControlState(
    //   this.f.numContrSomm,
    //   controlState,
    //   clean
    // );
    // this.utilitiesService.changeControlState(
    //   this.f.dtInizioContrattoSom,
    //   controlState,
    //   clean
    // );
    // this.utilitiesService.changeControlState(
    //   this.f.dtFineContrattoSom,
    //   controlState,
    //   clean
    // );
    this.utilitiesService.changeControlState(
      this.f.sedeOperativa,
      controlState,
      clean
    );
    const sedeLegaleControl = this.f.sedeLegale;
    this.utilitiesService.changeControlState(
      sedeLegaleControl.get('comune'),
      CONTROL_STATE.DISABLE,
      clean
    );
    this.utilitiesService.changeControlState(
      sedeLegaleControl.get('codCap'),
      CONTROL_STATE.DISABLE,
      clean
    );
    this.utilitiesService.changeControlState(
      sedeLegaleControl.get('indirizzo'),
      controlState,
      clean
    );
    this.utilitiesService.changeControlState(
      sedeLegaleControl.get('telefono'),
      controlState,
      clean
    );
    this.utilitiesService.changeControlState(
      sedeLegaleControl.get('fax'),
      controlState,
      clean
    );
    this.utilitiesService.changeControlState(
      sedeLegaleControl.get('email'),
      controlState,
      clean
    );
    this.utilitiesService.changeControlState(
      sedeLegaleControl.get('statiEsteri'),
      controlState,
      clean
    );
  }

  async onSubmit() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const aziUtilizzatrice: Datore = this.formDatiAziendaUtilizzatrice.getRawValue() as Datore;
    if (
        (this.INS_MODE ||
        this.RETTIFICA_MODE ||
        this.AGGIORNAMENTO_MODE) && 
        !this.comunicazioneToSave.id
      ) {
      aziUtilizzatrice.id = null;
      aziUtilizzatrice.sedeLegale.id = null;
      aziUtilizzatrice.sedeOperativa.id = null;
    }
    if (!this.comunicazioneToSave.missione) {
      this.comunicazioneToSave.missione = new Object() as Datore;
    }
    this.comunicazioneToSave.missione.aziUtilizzatrice = aziUtilizzatrice;
    const wrapperComunicazione: WrapperComunicazione = {
      comunicazione: this.comunicazioneToSave,
      ruolo: this.ruolo
    }
    try {
      const res = await this.comunicazioneControlliService.chkAziendaUtilizzatrice(wrapperComunicazione).toPromise();
      if (res) {
        this.setComunicazioneToSave.emit(this.comunicazioneToSave);
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

  onClickReset() {
    this.alertMessageService.emptyMessages();
    this.formDatiAziendaUtilizzatrice.reset();
    this.formRadio.controls.flgSedeLegaleEstero.patchValue(false);
    this.patchValueInForm(this.aziUtilizzatriceForReset);
    this.setFormMode();

    this.initForm();
    if (this.EDIT_MODE || this.RETTIFICA_MODE) {
      this.patchValueInForm(this.aziUtilizzatriceForReset);
    } else if (this.ANNULLO_MODE) {
      this.patchValueInForm(this.aziUtilizzatrice);
    }
  }

  async onClickFindSedeOperativa() {
    this.alertMessageService.emptyMessages();
    this.utilitiesService.showSpinner();
    const cf = this.f.codiceFiscale.value;
    try {
      const aziendaSilp: DatiAzienda = await this.silpService.getAziendaSilp(cf).toPromise();
      if (aziendaSilp) {
        this.utilitiesService.hideSpinner();
        const aziUtilizzatrice: Datore = this.formDatiAziendaUtilizzatrice.getRawValue() as Datore;
        const wrapperHelper: WrapperHelper = {
          comunicazione: this.comunicazioneToSave,
          datore: aziUtilizzatrice
        };
        this.router.navigate(['./dettaglio-azienda'], { state: {parametriNavigazione: this.routingParams, wrapperHelper: wrapperHelper, mode: 'edit', azienda: aziendaSilp } });
      }
    } catch (e) {

    } finally {
      this.utilitiesService.hideSpinner();
    }

  }

  private checkDecodificheScadute(datore: Datore) {
    let sysDate: Date = new Date();
    sysDate.setHours(0, 0, 0, 0);
    if (datore && !(this.VIEW_MODE || this.INS_MODE)) {
     const ateco: Atecofin = datore.atecofin;
     let dtFineAteco: Date;
     if (ateco && ateco.dtFine) {
        dtFineAteco = new Date(ateco.dtFine);
        dtFineAteco.setHours(0, 0, 0, 0);
      }
     if (dtFineAteco && dtFineAteco <= sysDate) {
        this.atecoScaduto = true;
        ateco.id = null;
        ateco.codAtecofinMin = null,
        ateco.dsComTAtecofin = null;
        this.utilitiesService.changeControlState(
          this.f.atecofin,
          CONTROL_STATE.ENABLE,
          true
        );
      }

     const flgSedeLegaleEstero = this.formRadio.controls.flgSedeLegaleEstero.value;
     if (flgSedeLegaleEstero) {
        // stato estero SedeLegale
        const sedeLegale: SedeLavoro = datore.sedeLegale;
        if (sedeLegale) {
          const statoEstero: StatiEsteri = sedeLegale.statiEsteri;
          if (statoEstero) {
            let dtFineStatoEstero: Date;
            if (statoEstero.dtFine) {
              dtFineStatoEstero = new Date(statoEstero.dtFine);
              dtFineStatoEstero.setHours(0, 0, 0, 0);
            }

            if (dtFineStatoEstero && dtFineStatoEstero <= sysDate) {
              statoEstero.id = null;
              statoEstero.codNazioneMin = null;
              statoEstero.dsStatiEsteri = null;
              this.statiEsteriSedeLegaleScaduto = true;
              this.utilitiesService.changeControlState(
                this.f.sedeLegale.controls.statiEsteri,
                CONTROL_STATE.ENABLE,
                true
              );
            }
          }
        }
        const sedeOperativa: SedeLavoro = datore.sedeOperativa;
        // comune e statoEstero sedeOperativa
        if (sedeOperativa) {
          const comune = sedeOperativa.comune;
          if (comune) {
            let dtFineComune: Date;
            if (comune.dtFine) {
              dtFineComune = new Date(comune.dtFine);
              dtFineComune.setHours(0, 0, 0, 0);
            }
            if (dtFineComune && dtFineComune <= sysDate) {
              comune.id = null;
              comune.codComuneMin = null;
              comune.dsComTComune = null;
              this.comuneSedeOperativaScaduto = true;
              this.utilitiesService.changeControlState(
                this.f.sedeOperativa.controls.comune,
                CONTROL_STATE.ENABLE,
                true
              );
            }
          }
          const statiEsteri: StatiEsteri = sedeOperativa.statiEsteri;
          if (statiEsteri) {
            let dtFineStatiEsteri: Date;
            if (statiEsteri.dtFine) {
              dtFineStatiEsteri = new Date(statiEsteri.dtFine);
              dtFineStatiEsteri.setHours(0, 0, 0, 0);
            }
            if (dtFineStatiEsteri) {
              statiEsteri.id = null;
              statiEsteri.codNazioneMin = null;
              statiEsteri.dsStatiEsteri = null;
              this.statiEsteriSedeOperativacaduto = true;
              this.utilitiesService.changeControlState(
                this.f.sedeOperativa.controls.statiEsteri,
                CONTROL_STATE.ENABLE,
                true
              );
            }
          }
        }
      }

    }
  }

  setControlStateCodCapSedeOperativa(){
    const flgSedeLegaleEstero: string = this.formRadio.controls.flgSedeLegaleEstero.value;
    if(flgSedeLegaleEstero){
      const sedeOperativa: SedeLavoro = this.f.sedeOperativa.getRawValue() as SedeLavoro;
      const statiEsteri = sedeOperativa.statiEsteri;
      if(statiEsteri){
        if(!Utils.isNullOrUndefinedOrCampoVuoto(statiEsteri.codNazioneMin) || !Utils.isNullOrUndefinedOrCampoVuoto(statiEsteri.dsStatiEsteri)){
          this.utilitiesService.changeControlState(
            this.f.sedeOperativa.get('codCap'),
            CONTROL_STATE.DISABLE,
            true
          )
        }else{
          this.utilitiesService.changeControlState(
            this.f.sedeOperativa.get('codCap'),
            CONTROL_STATE.ENABLE,
            false
          )
        }
      }
    }
  }

}
