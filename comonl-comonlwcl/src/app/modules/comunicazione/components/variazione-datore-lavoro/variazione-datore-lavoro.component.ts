/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { TipoTrasferimento } from './../../../comonlapi/model/tipoTrasferimento';
import { AdComponent } from './../../modules/nav-main-comunicazione/models/ad-component';
import { AlertMessageService } from './../../../comonlcommon/services/alert-message.service';
import { Ruolo } from './../../../comonlapi/model/ruolo';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { LogService, UtilitiesService } from 'src/app/services';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { Atecofin, Comune, Comunicazione, DecodificaGenerica, DecodificaService, NaturaGiuridica, ComunicazioneService, SilpService, Datore, DatiAzienda, ComunicazioneControlliService, WrapperComunicazione, SedeLavoro } from 'src/app/modules/comonlapi';
import { CONSTANTS_MODE, TYPE_DECODIFICA_GENERICA, CONTROL_STATE } from 'src/app/constants';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';
import { PersistenceComunicazioneWreapper } from '../../modules/nav-main-comunicazione/components/nav-main-comunicazione/nav-main-comunicazione.component';
import { Utils } from 'src/app/utils';
import { UtilsComonl } from 'src/app/services/utilsComonl';


@Component({
  selector: 'comonl-variazione-datore-lavoro',
  templateUrl: './variazione-datore-lavoro.component.html',
  styleUrls: ['./variazione-datore-lavoro.component.scss'],
})
export class VariazioneDatoreLavoroComponent implements OnInit, AdComponent {

  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="variazione-datore-lavoro"]';

  @Input() data: any;
  @Output() datiForm = new EventEmitter<any>();
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();
  @Output() persistenceNotification = new EventEmitter<PersistenceComunicazioneWreapper>();

  get VIEW_MODE() {
    const nuovaComParams = this.data.routingParams;
    return nuovaComParams.mode === CONSTANTS_MODE.VIEW;
  }

  get INS_MODE() {
    const nuovaComParams = this.data.routingParams;
    return nuovaComParams.mode === CONSTANTS_MODE.INS;
  }

  get RETTIFICA_MODE() {
    const nuovaComParams = this.data.routingParams;
    return nuovaComParams.mode === CONSTANTS_MODE.RETTIFICA;
  }

  get ANNULLO_MODE() {
    const nuovaComParams = this.data.routingParams;
    return nuovaComParams.mode === CONSTANTS_MODE.ANNULLO;
  }

  get EDIT_MODE() {
    const nuovaComParams = this.data.routingParams;
    return nuovaComParams.mode === CONSTANTS_MODE.EDIT;
  }

  get AGGIORNAMENTO_MODE() {
    const nuovaComParams = this.routingParams;
    return nuovaComParams.mode === CONSTANTS_MODE.AGGIORNAMENTO;
  }


  form: FormGroup;
  formDatore: FormGroup;
  formSedeLegale: FormGroup;
  showForm = false;
  comunicazione: Comunicazione;
  comunicazioneToSave: Comunicazione;

  datore: Datore;
  aziendaSilpFinded: DatiAzienda;
  flgStatiEsteriPresent = false;
  flgFieldsSedeLegale = false;
  flgAffittoModifica: boolean;

  routingParams;

  ruolo: Ruolo;

  flgAffittoRamo = false;

  listaNaturaGiuridica: NaturaGiuridica[] = [];
  listaTipoTrasferimento: TipoTrasferimento[] = [];


  get f() {
    return this.form.controls as any;
  }

  get fD() {
    return this.formDatore.controls as any;
  }

  get fSL() {
    return this.formSedeLegale.controls as any;
  }

  // get STATI_ESTERI_SEDE_VUOTO() {
  //   if (this.comunicazioneToSave.datorePrecedente) {
  //     return (Utils.isNullOrUndefinedOrCampoVuoto(this.comunicazioneToSave.datorePrecedente.sedeLegale.statiEsteri));
  //   }
  // }

  constructor(
    private logService: LogService,
    private utilitiesService: UtilitiesService,
    private router: Router,
    private silpService: SilpService,
    private promptModalService: PromptModalService,
    private alertMessageService: AlertMessageService,
    private decodificaService: DecodificaService,
    private location: Location,
    private comonlStorageService: ComonlStorageService,
    private comunicazioneControlliService: ComunicazioneControlliService
  ) {
    this.initForm();
  }

  async ngOnInit() {
    await this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    this.logService.info(this.constructor.name, 'ngOnInit');
    this.utilitiesService.scrollTo(VariazioneDatoreLavoroComponent.SCROLL_TARGET);
    // this.comonlStorageService.ruolo$.subscribe(el => this.ruolo = el);
    this.ruolo = this.data.ruolo;
    this.routingParams = this.data.routingParams;
    // console.log('routing params: ' + JSON.stringify(this.routingParams));
    this.comunicazione = this.data.comunicazione;
    // console.log('comunicazione: ' + JSON.stringify(this.comunicazione));
    this.comunicazioneToSave = this.data.comunicazioneToSave;
    // console.log('comunicazioneToSave: ' + JSON.stringify(this.comunicazioneToSave));
    this.ruolo = this.data.ruolo;
    try {
      const [
        naturaGiuridica,
        tipoTrasferimento
      ] = await Promise.all([
      this.decodificaService.getNaturaGiuridica().toPromise(),
      this.decodificaService.getTipoTrasferimento().toPromise(),
      ]);
      this.listaNaturaGiuridica = naturaGiuridica;
      this.listaTipoTrasferimento = tipoTrasferimento;
      this.patchValueInForm(this.comunicazioneToSave);
      this.showForm = true;
    } catch (e) {
    } finally {
      await this.utilitiesService.hideSpinner();
    }
    if (!this.INS_MODE) {
      // POPOLAMENTO COMBO
      if (this.comunicazioneToSave.datorePrecedente.naturaGiuridica) {
        const naturaGiuridica = this.listaNaturaGiuridica.find((el: NaturaGiuridica) => {
          return el.id === this.comunicazioneToSave.datorePrecedente.naturaGiuridica.id;
        });
        this.fD.naturaGiuridica.patchValue(naturaGiuridica);
      }

      if (this.comunicazioneToSave.tipoTrasferimento) {
        const tipoTrasferimento = this.listaTipoTrasferimento.find((el: TipoTrasferimento) => {
          return el.id === this.comunicazioneToSave.tipoTrasferimento.id;
        });
        this.f.tipoTrasferimento.patchValue(tipoTrasferimento);
      }
    }
  }


  private initForm() {
    this.form = new FormGroup({
      tipoTrasferimento: new FormControl(null, Validators.required),
      dtTrasferimentoVarDatori: new FormControl(null, Validators.required),
      dtFineAffittoRamo: new FormControl(),
    }),
    this.formDatore = new FormGroup({
      id: new FormControl(),
      codiceFiscale: new FormControl(null, Validators.required),
      partitaIva: new FormControl(),
      dsDenominazioneDatore: new FormControl(), // OBBLIGATORIO --> CONTROLLARE VALORE
      flgAzArtigiana: new FormControl(),
      naturaGiuridica: new FormControl(),
      atecofin: new FormGroup({
        id: new FormControl(),
        codAtecofinMin: new FormControl(null, Validators.required),
        dsComTAtecofin: new FormControl(null, Validators.required)
      })
    }),
    this.formSedeLegale = new FormGroup({
      id: new FormControl(),
      comune: new FormGroup({
        id: new FormControl(),
        codComuneMin: new FormControl(),
        dsComTComune: new FormControl()
      }),
      indirizzo: new FormControl(), // OBBLIGATORIO --> CONTROLLARE VALORE
      codCap: new FormControl(),    // OBBLIGATORIO --> CONTROLLARE VALORE
      telefono: new FormControl(),
      fax: new FormControl(),
      email: new FormControl()
    });
  }

  patchValueInForm(comunicazioneToSave: Comunicazione) {
    if (this.VIEW_MODE || this.ANNULLO_MODE) {
      // POPOLAMENTO COMBO
      this.form.disable();
      this.formDatore.disable();
      this.formSedeLegale.disable();
      this.form.patchValue(comunicazioneToSave);
      this.formDatore.patchValue(comunicazioneToSave.datorePrecedente);
      this.formSedeLegale.patchValue(comunicazioneToSave.datorePrecedente.sedeLegale);
      this.f.dtTrasferimentoVarDatori.patchValue(comunicazioneToSave.dtTrasferimentoVarDatori);
      this.form.clearValidators();
      this.formDatore.clearValidators();
      this.formSedeLegale.clearValidators();
    } else if (this.INS_MODE) {
      // Form
      this.f.dtFineAffittoRamo.disable();
      // FormDatore
      this.fD.partitaIva.disable();
      this.fD.dsDenominazioneDatore.disable();
      this.fD.flgAzArtigiana.disable();
      this.fD.naturaGiuridica.disable();
      // FormSedeLegale
      this.fSL.comune.disable();
      this.fSL.indirizzo.disable();
      this.fSL.codCap.disable();
      this.fSL.telefono.disable();
      this.fSL.fax.disable();
      this.fSL.email.disable();
      if (comunicazioneToSave) {
        this.form.patchValue(comunicazioneToSave);
        // TODO CARICARE LE COMBO
        const tipoTrasferimento = this.listaTipoTrasferimento.find((el: TipoTrasferimento) => {
          return el.id === comunicazioneToSave.tipoTrasferimento.id;
        });
        this.f.tipoTrasferimento.patchValue(tipoTrasferimento);
      }
      if (comunicazioneToSave.datorePrecedente) {
        this.formDatore.patchValue(comunicazioneToSave.datorePrecedente);

        const naturaGiuridica = this.listaNaturaGiuridica.find((el: NaturaGiuridica) => {
          return el.id === comunicazioneToSave.datorePrecedente.naturaGiuridica.id;
        });
        this.fD.naturaGiuridica.patchValue(naturaGiuridica);
      }
      if (comunicazioneToSave.datorePrecedente.sedeLegale) {
        this.formSedeLegale.patchValue(comunicazioneToSave.datorePrecedente.sedeLegale);
      }
      this.onClickTipoTrasferimento(true);
    }
    if (this.RETTIFICA_MODE || this.EDIT_MODE) {
      this.form.patchValue(comunicazioneToSave);
      this.formDatore.patchValue(comunicazioneToSave.datorePrecedente);
      this.formSedeLegale.patchValue(comunicazioneToSave.datorePrecedente.sedeLegale);
      // Form
      this.f.dtTrasferimentoVarDatori.patchValue(comunicazioneToSave.dtTrasferimentoVarDatori);
      this.f.dtFineAffittoRamo.patchValue(comunicazioneToSave.dtFineAffittoRamo);
      // FormDatore
      this.fD.partitaIva.disable();
      this.fD.dsDenominazioneDatore.disable();
      this.fD.flgAzArtigiana.disable();
      this.fD.naturaGiuridica.disable();
      // FormSedeLegale
      this.fSL.comune.disable();
      this.fSL.indirizzo.disable();
      this.fSL.codCap.disable();
      this.fSL.telefono.disable();
      this.fSL.fax.disable();
      this.fSL.email.disable();
      this.onClickTipoTrasferimento(false);
    }
  }

  get fieldsSedeLegale(): boolean {
    if (Utils.isNullOrUndefinedOrCampoVuoto(this.fSL.comune.get('codComuneMin').value) ||
        Utils.isNullOrUndefinedOrCampoVuoto(this.fSL.comune.get('dsComTComune').value) ||
        Utils.isNullOrUndefinedOrCampoVuoto(this.fSL.indirizzo.value) ||
        Utils.isNullOrUndefinedOrCampoVuoto(this.fSL.codCap.value) ||
        (
          Utils.isNullOrUndefinedOrCampoVuoto(this.fSL.telefono.value) &&
          Utils.isNullOrUndefinedOrCampoVuoto(this.fSL.fax.value) &&
          Utils.isNullOrUndefinedOrCampoVuoto(this.fSL.email.value)
        )
    ) {
      return true;
    }
    return false;
  }

  get fieldsAzPrec(): boolean {
    if (Utils.isNullOrUndefinedOrCampoVuoto(this.fD.codiceFiscale.value) ||
        Utils.isNullOrUndefinedOrCampoVuoto(this.fD.dsDenominazioneDatore.value) ||
        Utils.isNullOrUndefinedOrCampoVuoto(this.fD.atecofin.get('codAtecofinMin').value) ||
        Utils.isNullOrUndefinedOrCampoVuoto(this.fD.atecofin.get('dsComTAtecofin').value)
    ) {
      return true;
    }
    return false;
  }

  onClickTipoTrasferimento(flgPulisci: boolean) {
    if (this.form.get('tipoTrasferimento').value.dsComTTipoTrasferimento === 'AFFITTO RAMO') {
      this.flgAffittoRamo = true;
      this.utilitiesService.changeControlState(
        this.f.dtFineAffittoRamo,
        CONTROL_STATE.ENABLE,
        flgPulisci);
      this.f.dtFineAffittoRamo.setValidators(Validators.required);
      this.f.dtFineAffittoRamo.updateValueAndValidity();
    } else {
      this.flgAffittoRamo = false;
      this.utilitiesService.changeControlState(
        this.f.dtFineAffittoRamo,
        CONTROL_STATE.DISABLE,
        flgPulisci);
      this.f.dtFineAffittoRamo.clearValidators();
      this.f.dtFineAffittoRamo.updateValueAndValidity();
    }
  }

  async onClickFindAziendaPrec(cf) {
    try {
      this.utilitiesService.showSpinner();
      this.alertMessageService.emptyMessages();
      if (!Utils.isNullOrUndefinedOrCampoVuoto(cf)) {
        this.aziendaSilpFinded = await this.silpService.getAziendaSilp(cf).toPromise();
        this.datore = UtilsComonl.getDatoreFromDatiAzienda(this.aziendaSilpFinded);
        this.formDatore.patchValue(this.datore);
        this.formSedeLegale.patchValue(this.datore.sedeLegale);
      }
      if (this.datore.naturaGiuridica) {
        const naturaGiuridica = this.listaNaturaGiuridica.find((el: NaturaGiuridica) => {
          return el.id === this.datore.naturaGiuridica.id;
        });
        this.fD.naturaGiuridica.patchValue(naturaGiuridica);
      }

      if (this.datore.sedeLegale) {
        console.log(JSON.stringify(this.datore.sedeLegale));
        if (this.datore.sedeLegale.statiEsteri && this.datore.sedeLegale.statiEsteri.id) {
          this.flgStatiEsteriPresent = true;
        } else {
          this.flgStatiEsteriPresent = false;
          this.fSL.comune.setValidators(Validators.required);
        }
        console.log(this.flgStatiEsteriPresent);
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

  async onClickFindComune() {
    let decodifica: DecodificaGenerica;
    this.utilitiesService.showSpinner();
    decodifica = {
      codDecodifica: this.f.comSedeLegale.get('codComuneMin').value,
      dsDecodifica: this.f.comSedeLegale.get('dsComTComune').value
    };
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
          codComuneMin: decodificaFinded.codDecodifica,
          dsComTComune: decodificaFinded.dsDecodifica
        };
        this.f.comSedeLegale.patchValue(comune);
     }
    } catch (e) {
      this.alertMessageService.setWarningMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  async onClickFindAteco() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const decodifica: DecodificaGenerica = {
      codDecodifica: this.fD.atecofin.get('codAtecofinMin').value,
      dsDecodifica: this.fD.atecofin.get('dsComTAtecofin').value
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
        this.fD.atecofin.patchValue(atecofin);
      }
     } catch (e) {
       this.alertMessageService.setWarningMsg(e.error);
     } finally {
       this.utilitiesService.hideSpinner();
     }
  }

  async onSubmit() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    if (!this.VIEW_MODE) {
      const objForm = this.form.getRawValue() as any;
      const objFormDatore = this.formDatore.getRawValue() as any;
      const objformSedeLegale = this.formSedeLegale.getRawValue() as any;

      this.comunicazioneToSave.tipoTrasferimento = objForm.tipoTrasferimento;
      this.comunicazioneToSave.dtTrasferimentoVarDatori = objForm.dtTrasferimentoVarDatori;
      this.comunicazioneToSave.dtFineAffittoRamo = objForm.dtFineAffittoRamo;

      if (!this.comunicazioneToSave.datorePrecedente) {
        this.comunicazioneToSave.datorePrecedente = {} as Datore;
      }
      this.comunicazioneToSave.datorePrecedente.id = objFormDatore.id;
      this.comunicazioneToSave.datorePrecedente.codiceFiscale = objFormDatore.codiceFiscale;
      this.comunicazioneToSave.datorePrecedente.partitaIva = objFormDatore.partitaIva;
      this.comunicazioneToSave.datorePrecedente.dsDenominazioneDatore = objFormDatore.dsDenominazioneDatore;
      this.comunicazioneToSave.datorePrecedente.flgAzArtigiana = objFormDatore.flgAzArtigiana;
      this.comunicazioneToSave.datorePrecedente.naturaGiuridica = objFormDatore.naturaGiuridica;
      this.comunicazioneToSave.datorePrecedente.atecofin = objFormDatore.atecofin;

      if (!this.comunicazioneToSave.datorePrecedente.sedeLegale) {
        this.comunicazioneToSave.datorePrecedente.sedeLegale = {} as SedeLavoro;
      }
      this.comunicazioneToSave.datorePrecedente.sedeLegale.flgSedeLegale = 'S';
      this.comunicazioneToSave.datorePrecedente.sedeLegale.id = objformSedeLegale.id;
      this.comunicazioneToSave.datorePrecedente.sedeLegale.comune = objformSedeLegale.comune;
      this.comunicazioneToSave.datorePrecedente.sedeLegale.indirizzo = objformSedeLegale.indirizzo;
      this.comunicazioneToSave.datorePrecedente.sedeLegale.codCap = objformSedeLegale.codCap;
      this.comunicazioneToSave.datorePrecedente.sedeLegale.telefono = objformSedeLegale.telefono;
      this.comunicazioneToSave.datorePrecedente.sedeLegale.fax = objformSedeLegale.fax;
      this.comunicazioneToSave.datorePrecedente.sedeLegale.email = objformSedeLegale.email;
      if((this.ANNULLO_MODE || this.RETTIFICA_MODE || this.AGGIORNAMENTO_MODE) && !this.comunicazioneToSave.id){
        this.comunicazioneToSave.datorePrecedente.id = null;
        this.comunicazioneToSave.datorePrecedente.sedeLegale.id = null;
      }
      const wrapperComunicazione: WrapperComunicazione = {
        ruolo: this.ruolo,
        comunicazione: this.comunicazioneToSave
      };
    }
    try {
        const res = await this.comunicazioneControlliService.chkDatiAziendaPrecedente(this.comunicazioneToSave).toPromise();
       // const res = await this.comunicazioneControlliService.chkDatiAziendaPrecedente(wrapperComunicazione).toPromise();
        if (res) {
          this.setComunicazioneToSave.emit(res);
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

  goBack() {
    this.location.back();
  }
}
