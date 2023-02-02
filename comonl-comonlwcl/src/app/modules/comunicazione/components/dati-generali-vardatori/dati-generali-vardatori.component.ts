/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { TipoSoggettoAbilitato } from './../../../comonlapi/model/tipoSoggettoAbilitato';
import { AdComponent } from './../../modules/nav-main-comunicazione/models/ad-component';
import { AlertMessageService } from './../../../comonlcommon/services/alert-message.service';
import { Ruolo } from './../../../comonlapi/model/ruolo';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { LogService, UtilitiesService } from 'src/app/services';
import { Comunicazione, ComunicazioneControlliService, DecodificaService, Provincia, TipoComunicazioneTu, TipoProvenienza, WrapperComunicazione } from 'src/app/modules/comonlapi';
import { PersistenceComunicazioneWreapper } from '../../modules/nav-main-comunicazione/components/nav-main-comunicazione/nav-main-comunicazione.component';
import { CONSTANTS_MODE, CONTROL_STATE, DEFAULT_CODICE_REGIONALE, TIPO_COMUNICAZIONE, TIPO_COMUNICAZIONE_TU, TIPO_PROVENIENZA, TIPO_TRACCIATO } from 'src/app/constants';
import { Utils } from 'src/app/utils';


@Component({
  selector: 'comonl-dati-generali-vardatori',
  templateUrl: './dati-generali-vardatori.component.html',
  styleUrls: ['./dati-generali-vardatori.component.scss'],
})
export class DatiGeneraliVardatoriComponent implements OnInit, AdComponent {

  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="datiGeneraliVardatori"]';

  @Input() data: any;
  @Output() datiForm = new EventEmitter<any>();
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();
  @Output() persistenceNotification = new EventEmitter<PersistenceComunicazioneWreapper>();


  form: FormGroup;

  showForm = false;
  comunicazione: Comunicazione;
  comunicazioneToSave: Comunicazione;

  routingParams;
  ruolo: Ruolo;

  listaTipoSoggettoAbilitato: TipoSoggettoAbilitato[] = [];

  sezioneRettAnn: SezioneRettAnn;

  get conditionControlsRuoloEnable(): boolean {
    return this.f.flgComDatLav.value === 'S';
  }

  get conditionProvinciaEnable(): boolean {
    const tipoComunicazioneTu: TipoComunicazioneTu = this.f.tipoComunicazioneTu.value;
    return tipoComunicazioneTu ? tipoComunicazioneTu.codTipoComunicazioneMin === '07' : false;
  }

  get conditionAnnoProtocolloEnable(): boolean {
    return this.f.provinciaPrec.value;
  }
  get conditionNumeroProtocolloEnable(): boolean {
    return this.f.annoProtUrgenza.value && this.f.annoProtUrgenza.value !== '';
  }


  get VIEW_MODE() {
    const nuovaComParams = this.data.routingParams;
    return nuovaComParams.mode === CONSTANTS_MODE.VIEW;
  }

  get INS_MODE() {
    const nuovaComParams = this.data.routingParams;
    return nuovaComParams.mode === CONSTANTS_MODE.INS;
  }

  get EDIT_MODE() {
    const nuovaComParams = this.routingParams;
    return nuovaComParams.mode === CONSTANTS_MODE.EDIT;
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
  get f() {
    return this.form.controls as any;
  }

  sysDate: Date;

  constructor(
    private logService: LogService,
    private utilitiesService: UtilitiesService,
    private decodificaService: DecodificaService,
    private alertMessageService: AlertMessageService,
    private comunicazioneControlliService: ComunicazioneControlliService
  ) { }

  async ngOnInit() {
    await this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    this.utilitiesService.scrollTo(DatiGeneraliVardatoriComponent.SCROLL_TARGET);
    this.ruolo = this.data.ruolo;
    this.comunicazione = this.data.comunicazione;
    this.comunicazioneToSave = this.data.comunicazioneToSave;
    this.routingParams = this.data.routingParams;
    const nuovaComunicazioneParams = this.routingParams.nuovaComParams;
    console.log('Comunicazione' + this.comunicazione);
    console.log('Params nuova comunicazione: ' + nuovaComunicazioneParams);
    this.sysDate = new Date();
    try {
      const [
        tipoSoggettoAbilitato,
      ] = await Promise.all([
        this.decodificaService.getTipoSoggettoAbilitato().toPromise(),
      ]);
      this.listaTipoSoggettoAbilitato = tipoSoggettoAbilitato;
      this.initForm();
      this.patchValueInForm(this.comunicazioneToSave);
      this.setFormMode();
      this.showForm = true;
    } catch (e) {
      console.log(e);
    } finally {
      await this.utilitiesService.hideSpinner();
    }
  }

  private initForm() {
    this.form = new FormGroup({
      tipoComunicazione: new FormGroup({
        id: new FormControl(),
        dsComTTipoComunicazione: new FormControl({ value: null, disabled: true })
      }),
      email: new FormControl(null,
        Validators.compose([Validators.required])
      ),
      flgComunicazioneProvIsp: new FormControl('N'),
      tipoComunicazioneTu: new FormControl(),
      // tipoComunicazioneTu: new FormGroup({
      //   id: new FormControl(),
      //   codTipoComunicazioneMin: new FormControl({ value: null, disabled: true }),
      //   dsComTTipoComunicazione: new FormControl({ value: null, disabled: true })
      // }),
      codiceComunRegPrec: new FormControl({ value: null, disabled: true }),
      provinciaPrec: new FormGroup({
        id: new FormControl(),
        codProvinciaMin: new FormControl({ value: null, disabled: true }),
        dsComTProvincia: new FormControl({ value: null, disabled: true })
      }), // NON E' UNA COMBO
      annoProtComPrec: new FormControl({ value: null, disabled: true }),
      numProtComPrec: new FormControl({ value: null, disabled: true }),
      flgComDatLav: new FormControl(),
      tipoSoggettoAbilitato: new FormControl(), // DISABILITATO SE FLGDATORE = SI
      codiceFiscaleSoggetto: new FormControl(), // DISABILITATO SE FLGDATORE = SI
      emailsoggetto: new FormControl(), // DISABILITATO SE FLGDATORE = SI
      dtTimbroPostale: new FormControl(),
    });
    if (this.ruolo.amministratore || this.ruolo.operatoreProvinciale) {
      this.f.tipoSoggettoAbilitato.setValidators(Validators.required);
      this.f.codiceFiscaleSoggetto.setValidators(Validators.required);
      this.f.emailsoggetto.setValidators([Validators.required]);
      this.f.dtTimbroPostale.setValidators(Validators.required);
  }
  }

  patchValueInForm(comunicazioneToSave: Comunicazione) {
    if (this.VIEW_MODE) {
      this.form.disable();
      this.form.patchValue(comunicazioneToSave);
      this.f.dtTimbroPostale.patchValue(comunicazioneToSave.dtTimbroPostale);
      if (this.comunicazioneToSave.tipoComunicazioneTu.codTipoComunicazioneMin === '06') {
        this.f.flgComunicazioneProvIsp.patchValue('S');
      } else {
        this.f.flgComunicazioneProvIsp.patchValue('N');
      }
      console.log(this.comunicazioneToSave.tipoComunicazioneTu);
      // if (this.comunicazioneToSave.tipoComunicazioneTu.codTipoComunicazioneMin === '03' ||
      //     this.comunicazioneToSave.tipoComunicazioneTu.codTipoComunicazioneMin === '04') {
      //       this.f.tipoComunicazioneTu.get('dsComTTipoComunicazione').patchValue(this.comunicazioneToSave.tipoComunicazioneTu.dsComTTipoComunicazione);
      // } else {
      //   this.f.tipoComunicazioneTu.get('dsComTTipoComunicazione').setValue(null);
      // }

      if (this.comunicazioneToSave.rapLavSedeVD && this.comunicazioneToSave.rapLavSedeVD.length > 0) {
        this.f.tipoComunicazione.get('dsComTTipoComunicazione').patchValue(this.comunicazioneToSave.tipoComunicazione.dsComTTipoComunicazione + ' - DATORE LAVORO');
      } else if ((this.comunicazioneToSave.rapLavSedeVD && this.comunicazioneToSave.rapLavSedeVD.length === 0) || !this.comunicazioneToSave.rapLavSedeVD) {
        this.f.tipoComunicazione.get('dsComTTipoComunicazione').patchValue(this.comunicazioneToSave.tipoComunicazione.dsComTTipoComunicazione + ' - RAGIONE SOCIALE');
      }
    } else if (this.INS_MODE) {
      this.f.tipoComunicazione.get('dsComTTipoComunicazione').patchValue(this.routingParams.formModalParams.tipoComunicazione);
      const email = this.comunicazioneToSave.email;
      this.f.email.patchValue(email);
      if(Utils.isNullOrUndefinedOrCampoVuoto(email)){
        this.f.email.patchValue( this.ruolo.email);
      }
    }
    
    const tipoComunicazioneTu: TipoComunicazioneTu = this.comunicazioneToSave.tipoComunicazioneTu;

    if (this.ANNULLO_MODE || this.RETTIFICA_MODE) {
      if(this.RETTIFICA_MODE){
        this.f.email.patchValue( this.ruolo.email);
      }
      this.form.patchValue(comunicazioneToSave);
      if (this.comunicazioneToSave.rapLavSedeVD && this.comunicazioneToSave.rapLavSedeVD.length > 0) {
        this.f.tipoComunicazione.get('dsComTTipoComunicazione').patchValue(this.comunicazioneToSave.tipoComunicazione.dsComTTipoComunicazione + ' - DATORE LAVORO');
      } else if ((this.comunicazioneToSave.rapLavSedeVD && this.comunicazioneToSave.rapLavSedeVD.length === 0) || !this.comunicazioneToSave.rapLavSedeVD) {
        this.f.tipoComunicazione.get('dsComTTipoComunicazione').patchValue(this.comunicazioneToSave.tipoComunicazione.dsComTTipoComunicazione + ' - RAGIONE SOCIALE');
      }
      this.sezioneRettAnn = {
        provincia: comunicazioneToSave.provinciaPrec ? comunicazioneToSave.provinciaPrec : null,
        numProtCom: comunicazioneToSave.numProtComPrec ? comunicazioneToSave.numProtComPrec : null,
        annoProtCom: comunicazioneToSave.annoProtComPrec ? comunicazioneToSave.annoProtComPrec : null,
        tipoComunicazioneTu: comunicazioneToSave.tipoComunicazioneTu,
        codiceRegionale: comunicazioneToSave.codiceComunicazioneReg
      };
    }

    if (this.EDIT_MODE) {
      this.form.patchValue(comunicazioneToSave);
      if (this.comunicazioneToSave.rapLavSedeVD && this.comunicazioneToSave.rapLavSedeVD.length > 0) {
        this.f.tipoComunicazione.get('dsComTTipoComunicazione').patchValue(this.comunicazioneToSave.tipoComunicazione.dsComTTipoComunicazione + ' - DATORE LAVORO');
      } else if ((this.comunicazioneToSave.rapLavSedeVD && this.comunicazioneToSave.rapLavSedeVD.length === 0) || !this.comunicazioneToSave.rapLavSedeVD) {
        this.f.tipoComunicazione.get('dsComTTipoComunicazione').patchValue(this.comunicazioneToSave.tipoComunicazione.dsComTTipoComunicazione + ' - RAGIONE SOCIALE');
      }
    }

    this.sezioneRettAnn = {
        provincia: comunicazioneToSave.provinciaPrec ? comunicazioneToSave.provinciaPrec : null,
        numProtCom: comunicazioneToSave.numProtComPrec ? comunicazioneToSave.numProtComPrec : null,
        annoProtCom: comunicazioneToSave.annoProtComPrec ? comunicazioneToSave.annoProtComPrec : null,
        tipoComunicazioneTu: comunicazioneToSave.tipoComunicazioneTu,
        codiceRegionale: comunicazioneToSave.codiceComunRegPrec ? comunicazioneToSave.codiceComunRegPrec : DEFAULT_CODICE_REGIONALE
    };

    if (this.ruolo.amministratore || this.ruolo.operatoreProvinciale) {
      if (comunicazioneToSave.flgComDatLav) {
        this.f.flgComDatLav.setValue(comunicazioneToSave.flgComDatLav);
      } else {
        if (this.ruolo.amministratore) {
          this.f.flgComDatLav.setValue('N');
        } else if (this.ruolo.operatoreProvinciale) {
          this.f.flgComDatLav.setValue('S');
        }
      }
      const tipoSoggettoAbilitato = comunicazioneToSave.tipoSoggettoAbilitato;
      if (tipoSoggettoAbilitato) {
        /**
         * PER TUTTI I CASI ESCLUSO L'INSERIMENTO(Per L'inserimento comanda direttamente il valore di flgComDatLav)
         * se (tipoSoggettoAbilitato è valorizzato) => flgComDatLav = 'N'
         * se flgComDatLav = N => sono obbligatori e devono avere un valore:
         * tipoSoggettoAbilitato, codiceFiscaleSoggetto. emailsoggetto è opzionale ma non può avere un valore se flgComDatLav = 'S'
         */
        const tipoSoggettoAbilitatoFinded = this.listaTipoSoggettoAbilitato.find(el => {
          return el.id === tipoSoggettoAbilitato.id;
        });
        this.f.tipoSoggettoAbilitato.patchValue(tipoSoggettoAbilitatoFinded);

        this.f.codiceFiscaleSoggetto.patchValue(comunicazioneToSave.codiceFiscaleSoggetto);
        this.f.emailsoggetto.patchValue(comunicazioneToSave.emailsoggetto);
      }
      this.f.dtTimbroPostale.patchValue(comunicazioneToSave.dtTimbroPostale);
  }
  if(tipoComunicazioneTu && tipoComunicazioneTu.codTipoComunicazioneMin === TIPO_COMUNICAZIONE_TU.INSERIMENTO_UFFICIO.COD_MIN){
    this.f.flgComunicazioneProvIsp.setValue('S');
  }else{
    this.f.flgComunicazioneProvIsp.setValue('N');
  }

}

  onClickEnableDs() {
    const flgAnnulla: boolean = this.routingParams.mode === CONSTANTS_MODE.ANNULLO;
    if (!flgAnnulla) {
      this.utilitiesService.changeControlState(this.f.dsCausaForzamaggiore, CONTROL_STATE.ENABLE, false);
      this.f.dsCausaForzamaggiore.clearValidators();
      this.f.dsCausaForzamaggiore.setValidators(Validators.required);
      this.f.dsCausaForzamaggiore.updateValueAndValidity();
    }
  }

  onClickDisableDs() {
    this.utilitiesService.changeControlState(this.f.dsCausaForzamaggiore, CONTROL_STATE.DISABLE, true);
    this.f.dsCausaForzamaggiore.clearValidators();
    this.f.dsCausaForzamaggiore.updateValueAndValidity();

  }

  setControlStateDatiProtoccollo() {
    this.setControlStatecodiceComunRegPrec();
    // this.setControlStateProvincia();
    // this.setControlstateAnnoNumeroProtocollo();
  }

  setControlStatecodiceComunRegPrec() {
    const tipoComunicazioneTu: TipoComunicazioneTu = this.f.tipoComunicazioneTu.value;
    let flgEnable: boolean = false;
    if (tipoComunicazioneTu) {
      if (tipoComunicazioneTu.codTipoComunicazioneMin === '07' ||  tipoComunicazioneTu.codTipoComunicazioneMin === '09') {
        flgEnable = true;
      } else if (tipoComunicazioneTu.codTipoComunicazioneMin === '08') {
        this.f.codiceComunRegPrec.setValue(DEFAULT_CODICE_REGIONALE);
      }
    }
    this.utilitiesService.changeControlState(
      this.f.codiceComunRegPrec,
      flgEnable ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE,
      false
    );
  }

  private setFormMode() {
    if (this.VIEW_MODE || this.ANNULLO_MODE) {
      // READ MODE
      this.form[CONTROL_STATE.DISABLE]();
    } else {
      this.setControlStateDatiProtoccollo();

      // Amministartore o apl
      if (!this.ANNULLO_MODE) {
        if (this.ruolo.amministratore || this.ruolo.operatoreProvinciale) {
          this.setStateAltroSogg();
        }
      } else {
        if (this.ruolo.amministratore || this.ruolo.operatoreProvinciale) {
          this.utilitiesService.changeControlState(this.f.tipoSoggettoAbilitato, CONTROL_STATE.DISABLE, false);
          this.utilitiesService.changeControlState(this.f.codiceFiscaleSoggetto, CONTROL_STATE.DISABLE, false);
          this.utilitiesService.changeControlState(this.f.emailsoggetto, CONTROL_STATE.DISABLE, false);
          this.utilitiesService.changeControlState(this.f.dtTimbroPostale, CONTROL_STATE.DISABLE, false);
        }

      }

      if (this.RETTIFICA_MODE || this.ANNULLO_MODE) {
        this.f.flgComunicazioneProvIsp.disable();
      }

    }
  }

  onClickReset() {
    this.form.reset();
    this.initForm();
    this.patchValueInForm(this.comunicazione);
    this.setFormMode();
    this.alertMessageService.emptyMessages();
  }

  setStateAltroSogg() {
    const flgComDatLav = this.f.flgComDatLav.value;
    let state = flgComDatLav === 'N' ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE;
    this.utilitiesService.changeControlState(this.f.tipoSoggettoAbilitato, state, state === CONTROL_STATE.DISABLE ? true : false);
    this.utilitiesService.changeControlState(this.f.codiceFiscaleSoggetto, state, state === CONTROL_STATE.DISABLE ? true : false);
    this.utilitiesService.changeControlState(this.f.emailsoggetto, state, state === CONTROL_STATE.DISABLE ? true : false);
  }

  async onClickSubmit() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    if (!this.VIEW_MODE) {
      const objForm = this.form.getRawValue() as any;
      this.comunicazioneToSave.email = objForm.email;

      this.comunicazioneToSave.flgCausaForzamaggiore = objForm.flgCausaForzamaggiore;
      this.comunicazioneToSave.dsCausaForzamaggiore = objForm.dsCausaForzamaggiore;
      let tipoComunicazioneTu: TipoComunicazioneTu;
      if (this.f.flgComunicazioneProvIsp.value === 'S') {
         tipoComunicazioneTu = {
          id: 6,
          codTipoComunicazioneMin: '06'
        } as TipoComunicazioneTu;
        this.comunicazioneToSave.tipoComunicazioneTu = tipoComunicazioneTu;
      } else {
          tipoComunicazioneTu = {
            id: 1,
            codTipoComunicazioneMin: '01'
          } as TipoComunicazioneTu;

        this.comunicazioneToSave.tipoComunicazione = objForm.tipoComunicazione;
        this.comunicazioneToSave.tipoComunicazioneTu = tipoComunicazioneTu;
        this.comunicazioneToSave.codiceComunRegPrec = objForm.codiceComunRegPrec;
        this.comunicazioneToSave.provinciaPrec = objForm.provinciaPrec;
        this.comunicazioneToSave.annoProtUrgenza = objForm.annoProtUrgenza;
        this.comunicazioneToSave.numProtComUrgenza = objForm.numProtComUrgenza;

      }

      if(this.RETTIFICA_MODE){
        tipoComunicazioneTu = {
          id: 3,
          codTipoComunicazioneMin: '03'
        } as TipoComunicazioneTu;
      }else if(this.ANNULLO_MODE){
        tipoComunicazioneTu = {
          id: 4,
          codTipoComunicazioneMin: '04'
        } as TipoComunicazioneTu;
      }
      this.comunicazioneToSave.tipoComunicazioneTu = tipoComunicazioneTu;
      const flgComunicazioneDatore = objForm.flgComDatLav;
      this.comunicazioneToSave.flgComDatLav = flgComunicazioneDatore;
      if (flgComunicazioneDatore === 'N') {
        this.comunicazioneToSave.tipoSoggettoAbilitato = objForm.tipoSoggettoAbilitato;
        this.comunicazioneToSave.codiceFiscaleSoggetto = objForm.codiceFiscaleSoggetto;
        this.comunicazioneToSave.emailsoggetto = objForm.emailsoggetto;
      }
      this.comunicazioneToSave.dtTimbroPostale = objForm.dtTimbroPostale;
      this.comunicazioneToSave.tipoTracciato.id = TIPO_TRACCIATO.VARDATORI.ID;
      this.comunicazioneToSave.tipoComunicazione.id = TIPO_COMUNICAZIONE.VARIAZIONE_DATORE.ID;
    }
    this.comunicazioneToSave.flgSommin = 'N';
    const wrapperComunicazione: WrapperComunicazione = {
      ruolo: this.ruolo,
      comunicazione: this.comunicazioneToSave
    };
    const tipoProvenienza: TipoProvenienza = {
      id: TIPO_PROVENIENZA.COMONL_VER_4.ID
    }
    this.comunicazioneToSave.tipoProvenienza = tipoProvenienza;
    try {
      const res = await this.comunicazioneControlliService.chkDatiGeneraliVardatori(wrapperComunicazione).toPromise();
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

  findInvalidControls() {
    const controls = this.form.controls;
    for (const name in controls) {
        if (controls[name].invalid) {
           console.log('invalid: ' + name);
        }
    }
  }


}

interface SezioneRettAnn {
  provincia?: Provincia;
  annoProtCom?: number;
  numProtCom?: number;
  tipoComunicazioneTu?: TipoComunicazioneTu;
  codiceRegionale?: string;
}
