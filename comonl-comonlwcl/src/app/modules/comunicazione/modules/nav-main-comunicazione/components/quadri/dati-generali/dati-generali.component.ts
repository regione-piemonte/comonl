/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { NavComunicazioneParams } from 'src/app/modules/comunicazione/modules/nav-main-comunicazione/models/nav-comunicazione-params';

import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CONSTANTS_MODE, CONTROL_STATE, DEFAULT_CODICE_REGIONALE, REGIONE, TIPO_ACQUISIZIONE, TIPO_COMUNICAZIONE, TIPO_COMUNICAZIONE_TU, TIPO_PROVENIENZA, TIPO_TRACCIATO } from 'src/app/constants';
import { Comunicazione, ComunicazioneControlliService, DecodificaService, Provincia, Ruolo, TipoAcquisizione, TipoComunicazioneTu, TipoProvenienza, TipoSoggettoAbilitato, WrapperComunicazione } from 'src/app/modules/comonlapi';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { LogService, UtilitiesService } from 'src/app/services';
import { AdComponent } from '../../../models/ad-component';
import { PersistenceComunicazioneWreapper } from '../../nav-main-comunicazione/nav-main-comunicazione.component';

@Component({
  selector: 'comonl-dati-generali',
  templateUrl: './dati-generali.component.html',
  styleUrls: ['./dati-generali.component.scss']
})
export class DatiGeneraliComponent implements OnInit, AdComponent {

  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="datiGenerali"]';

  @Input() data: any;
  @Output() datiForm = new EventEmitter<any>();
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();
  @Output() persistenceNotification = new EventEmitter<PersistenceComunicazioneWreapper>();

  comunicazione: Comunicazione;
  comunicazioneToSave: Comunicazione;

  routingParams;
  formModalParams;

  flgNuovaCO: boolean;
  flgNuovaCOFromUrg: boolean;

  tipoComunicazioneTus: TipoComunicazioneTu[] = [];
  tipoSoggettoAbilitatos: TipoSoggettoAbilitato[] = [];
  provinces: Provincia[] = [];
  showForm: boolean;
  editMode: boolean;
  sezioneRettAnn: SezioneRettAnn;

  ruolo: Ruolo;

  // restituisce formControls
  get f() {
    return this.form.controls as any;
  }


  get conditionControlsRuoloEnable(): boolean {
    return this.f.flgComunicazioneDatore.value === 'S';
  }

  get conditionProvinciaEnable(): boolean {
    const tipoComunicazioneTu: TipoComunicazioneTu = this.f.tipoComunicazioneTu.value;
    return tipoComunicazioneTu ? tipoComunicazioneTu.codTipoComunicazioneMin === '07' : false;
  }

  get conditionAnnoProtocolloEnable(): boolean {
    return this.f.provincia.value;
  }
  get conditionNumeroProtocolloEnable(): boolean {
    return this.f.annoProtUrgenza.value && this.f.annoProtUrgenza.value !== '';
  }

  get conditionCausaForzaMaggioreEnable(): boolean {
    const tipoComunicazione = this.comunicazioneToSave.tipoComunicazione;
    const tipoTracciato = this.comunicazioneToSave.tipoTracciato;
    if (tipoComunicazione && tipoTracciato) {
      return  tipoComunicazione.id === TIPO_COMUNICAZIONE.ASSUNZIONE.ID && tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID;
    }
    return false;
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

  get DISABLE_INTERACTIONS_VIEW(): boolean {
    return this.VIEW_MODE || this.EDIT_MODE;
  }

  get IS_ASSUNZIONE_UNILAV(): boolean{
    return this.comunicazione.tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID && this.comunicazione.tipoComunicazione.id === TIPO_COMUNICAZIONE.ASSUNZIONE.ID;
  }

  sysDate: Date;


  constructor(
    private logService: LogService,
    private utilitiesService: UtilitiesService,
    private decodificaService: DecodificaService,
    private alertMessageService: AlertMessageService,
    private comunicazioneControlliService: ComunicazioneControlliService
  ) { }


  form: FormGroup;

  async ngOnInit() {
    this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    this.utilitiesService.scrollTo(DatiGeneraliComponent.SCROLL_TARGET);
    // console.log('data: ' + JSON.stringify(this.data));
    this.ruolo = this.data.ruolo;
    this.comunicazione = this.data.comunicazione;
    this.comunicazioneToSave = this.data.comunicazioneToSave;
    this.routingParams = this.data.routingParams;
    this.formModalParams = this.data.routingParams.formModalParams;
    console.log('formModalParams: ' + JSON.stringify(this.formModalParams));
    this.flgNuovaCOFromUrg = this.data.flgNuovaCOFromUrg;
    this.flgNuovaCO = this.data.routingParams.flgNuovaCO;
    console.log('flgNuovaCO: ' + this.flgNuovaCO);
    const nuovaComunicazioneParams = this.routingParams.nuovaComParams;
    // console.log('data: ' + JSON.stringify(this.data));
    console.log('Comunicazione' + JSON.stringify(this.comunicazione));
    // console.log('Params nuova comunicazione: ' + nuovaComunicazioneParams);
    this.sysDate = new Date();
    try {
      const [tipoComunicazioneTus, tipoSoggettoAbilitatos, provinces] = await Promise.all([
        this.decodificaService.getTipoComunicazioneTu(false).toPromise(),
        this.decodificaService.getTipoSoggettoAbilitato().toPromise(),
        this.decodificaService.getProvincia(REGIONE.PIEMONTE.COD_MIN).toPromise(),
      ]);
      this.provinces = provinces;
      this.tipoSoggettoAbilitatos = tipoSoggettoAbilitatos;
      this.tipoComunicazioneTus = tipoComunicazioneTus.filter(
        com => { return(
                  com.codTipoComunicazioneMin === TIPO_COMUNICAZIONE_TU.COD_MIN_07 ||
                  com.codTipoComunicazioneMin === TIPO_COMUNICAZIONE_TU.COD_MIN_08 ||
                  com.codTipoComunicazioneMin === TIPO_COMUNICAZIONE_TU.COD_MIN_09 );
        });
      this.logService.info('this.tipoComunicazioneTus', 'ngOnInit', this.tipoComunicazioneTus);
      this.initForm();

      // console.log(this.form.get('tipoComunicazioneTu').value);
      console.log(this.form.get('tipoComunicazioneTu').value === null);
      this.patchValueInForm(this.comunicazioneToSave);
      this.setFormMode();
      this.showForm = true;
    } catch (e) {
      console.log(e);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  private initForm() {
    this.form = new FormGroup({
      email: new FormControl(null,
        Validators.compose([Validators.required])
      ),
      flgSommin: new FormControl(),
      tipoTracciato: new FormGroup({
        id: new FormControl()
      }),
      flgCausaForzamaggiore: new FormControl({ value: null, disabled: true }),
      dsCausaForzamaggiore: new FormControl({ value: null, disabled: true }),
      annoProtUrgenza: new FormControl({ value: null, disabled: true }),
      numProtComUrgenza: new FormControl({ value: null, disabled: true }),
      codiceComunRegPrec: new FormControl(null, Validators.minLength(16)),
      comunicazioneDaRettificare: new FormControl(),
      comunicazionePrecedenteAnnullo: new FormControl(),
      tipoComunicazioneTu: new FormControl(null),
      provincia: new FormControl({value: null, disabled: true}),
      flgComunicazioneProvIsp: new FormControl('N'),
      /*SE AMMINISTARTORE O APL */
      flgComunicazioneDatore: new FormControl(),
      tipoSoggettoAbilitato: new FormControl(),
      codiceFiscaleSoggetto: new FormControl(),
      emailsoggetto: new FormControl(null),
      dtTimbroPostale: new FormControl()
    });
    if (this.ruolo.amministratore || this.ruolo.operatoreProvinciale) {
        this.f.tipoSoggettoAbilitato.setValidators(Validators.required);
        this.f.codiceFiscaleSoggetto.setValidators(Validators.required);
        this.f.emailsoggetto.setValidators([Validators.required]);
        this.f.dtTimbroPostale.setValidators(Validators.required);
    }
  }


  private patchValueInForm(comunicazione: Comunicazione) {
    const tipoTracciato = comunicazione.tipoTracciato;
    let email = comunicazione.email;
    if (!email) {
      // se email non è presente sono in inserimento
      if (tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID || tipoTracciato.id === TIPO_TRACCIATO.UNIDOM.ID) {
        if (this.INS_MODE || this.RETTIFICA_MODE || this.AGGIORNAMENTO_MODE) {
          email = this.ruolo.email;
        }
      }
      this.f.flgComunicazioneProvIsp.patchValue('N');
    }
    this.f.email.patchValue(email);

    let tipoComunicazioneTu = comunicazione.tipoComunicazioneTu ? comunicazione.tipoComunicazioneTu : null;
    if (tipoComunicazioneTu) {
      if (tipoComunicazioneTu.id === TIPO_COMUNICAZIONE_TU.INSERIMENTO_UFFICIO.ID) {
        this.f.flgComunicazioneProvIsp.setValue('S');
      } else {
        this.f.flgComunicazioneProvIsp.setValue('N');
      }
      if (
        tipoComunicazioneTu.codTipoComunicazioneMin === TIPO_COMUNICAZIONE_TU.COD_MIN_03 ||
        tipoComunicazioneTu.codTipoComunicazioneMin === TIPO_COMUNICAZIONE_TU.COD_MIN_04) {
        this.sezioneRettAnn = {
          provincia: comunicazione.provinciaPrec ? comunicazione.provinciaPrec : null,
          numProtCom: comunicazione.numProtComPrec ? comunicazione.numProtComPrec : null,
          annoProtCom: comunicazione.annoProtComPrec ? comunicazione.annoProtComPrec : null,
          tipoComunicazioneTu: comunicazione.tipoComunicazioneTu,
          codiceRegionale: comunicazione.codiceComunRegPrec ? comunicazione.codiceComunRegPrec : DEFAULT_CODICE_REGIONALE
        };
      }
      const tipoComunicazioneTuFinded: TipoComunicazioneTu = this.tipoComunicazioneTus.find(el => {
        return el.id === tipoComunicazioneTu.id;
      });
      tipoComunicazioneTu = tipoComunicazioneTuFinded ? tipoComunicazioneTuFinded : null;
      if (tipoComunicazioneTuFinded && this.IS_ASSUNZIONE_UNILAV) {
        this.f.codiceComunRegPrec.setValue(comunicazione.codiceComunRegPrec);
        this.f.annoProtUrgenza.setValue(comunicazione.annoProtUrgenza);
        this.f.numProtComUrgenza.setValue(comunicazione.numProtComUrgenza);
        const provincia = comunicazione.provincia;
        if (provincia) {
          const provinciaFinded = this.provinces.find(el => {
            return el.id === provincia.id;
          });
          this.f.provincia.patchValue(provinciaFinded);
        }

      }
    }
    this.f.tipoComunicazioneTu.patchValue(tipoComunicazioneTu);

    const tipoComunicazione = comunicazione.tipoComunicazione;

    if (this.conditionCausaForzaMaggioreEnable) {
      if (comunicazione.flgCausaForzamaggiore) {
        this.f.flgCausaForzamaggiore.patchValue(comunicazione.flgCausaForzamaggiore);
      } else {
        this.f.flgCausaForzamaggiore.setValue('N');
      }
      this.f.dsCausaForzamaggiore.setValue(comunicazione.dsCausaForzamaggiore);
      this.f.flgCausaForzamaggiore.setValidators(Validators.required);
      this.f.flgCausaForzamaggiore.updateValueAndValidity();
    } else {
      this.f.flgCausaForzamaggiore.setValue('N');
    }


      /*gestire comunicazione a seguito di urgenza */

      /*RETTIFICA ANNULLAMENTO*/

    if (this.ANNULLO_MODE || this.RETTIFICA_MODE) {
        this.sezioneRettAnn = {
          provincia: comunicazione.provinciaPrec ? comunicazione.provinciaPrec : null,
          numProtCom: comunicazione.numProtComPrec ? comunicazione.numProtComPrec : null,
          annoProtCom: comunicazione.annoProtComPrec ? comunicazione.annoProtComPrec : null,
          tipoComunicazioneTu: comunicazione.tipoComunicazioneTu,
          codiceRegionale: comunicazione.codiceComunRegPrec
        };
      }

    /*Sezione: Dati del soggetto per cui si effettua la comunicazione (solo per ruolo APL e Amministratore)*/
    if (
        this.ruolo.amministratore ||
        this.ruolo.operatoreProvinciale ||
        this.EDIT_MODE
        ) {
        if (comunicazione.flgComDatLav) {
          this.f.flgComunicazioneDatore.setValue(comunicazione.flgComDatLav);
        } else {
          if (this.ruolo.amministratore) {
            this.f.flgComunicazioneDatore.setValue('N');
          } else if (this.ruolo.operatoreProvinciale) {
            this.f.flgComunicazioneDatore.setValue('S');
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
          this.f.tipoSoggettoAbilitato.patchValue(tipoSoggettoAbilitatoFinded);

          this.f.codiceFiscaleSoggetto.patchValue(comunicazione.codiceFiscaleSoggetto);
          this.f.emailsoggetto.patchValue(comunicazione.emailsoggetto);
        }
        this.f.dtTimbroPostale.patchValue(comunicazione.dtTimbroPostale);
    }

    if (!this.INS_MODE && comunicazione.flgComDatLav) {
      if (comunicazione.flgComDatLav) {
        this.f.flgComunicazioneDatore.setValue(comunicazione.flgComDatLav);
      }
    }

    if (this.flgNuovaCOFromUrg) {
      const tipoComunicazioneTuFinded = this.tipoComunicazioneTus.find((el: TipoComunicazioneTu) => { return el.codTipoComunicazioneMin === '07'; });
      if (tipoComunicazioneTuFinded) {
        this.f.tipoComunicazioneTu.patchValue(tipoComunicazioneTuFinded);
      }
      const provinciaPrec = comunicazione.provinciaPrec;
      if (provinciaPrec) {
        const provinciaFinded = this.provinces.find((el: Provincia) => { return el.id === provinciaPrec.id; });
        if (provinciaFinded) {
          this.f.provincia.patchValue(provinciaFinded);
        }
      }
      this.f.annoProtUrgenza.patchValue(comunicazione.annoProtUrgenza);
      this.f.numProtComUrgenza.patchValue(comunicazione.numProtComUrgenza);
      this.f.codiceComunRegPrec.patchValue(comunicazione.codiceComunRegPrec);
    }

  }

  private setFormMode() {
    if (this.VIEW_MODE) {
      // READ MODE
      this.form[CONTROL_STATE.DISABLE]();
    } else {
      const tipoComunicazione = this.comunicazioneToSave.tipoComunicazione;

      if (this.comunicazioneToSave.tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID) {
        if (tipoComunicazione.id === TIPO_COMUNICAZIONE.ASSUNZIONE.ID) {
          if (!this.ANNULLO_MODE) {
            this.f.flgCausaForzamaggiore.enable();
            if (this.f.flgCausaForzamaggiore.value === 'S') {
              this.f.dsCausaForzamaggiore.enable();
            }

          }
        }
      }

      if (!this.flgNuovaCOFromUrg) {
        this.setControlStateDatiProtoccollo();
      }

      // Amministartore o apl

      if (this.ruolo.amministratore || this.ruolo.operatoreProvinciale) {
          this.setStateAltroSogg();
        }

      if (this.RETTIFICA_MODE || this.ANNULLO_MODE) {
        this.f.flgComunicazioneProvIsp.disable();
      }

      if (this.AGGIORNAMENTO_MODE && !this.comunicazione.id) {
        console.log('formModalParams: ' + JSON.stringify(this.formModalParams));
        this.comunicazioneToSave.flgSommin = this.formModalParams.somm;
        // this.comunicazioneToSave.tipoComunicazione.dsComTTipoComunicazione = this.formModalParams.comunicazione.dsDecodifica;
        // this.comunicazioneToSave.tipoComunicazione.id = this.formModalParams.comunicazione.idComTipoComunicazione;
        this.comunicazioneToSave.tipoTracciato.id = this.formModalParams.comunicazione.idComTipoTracciato;
        console.log('comunicazioneToSave: ' + JSON.stringify(this.comunicazioneToSave));
        this.f.flgComunicazioneProvIsp.patchValue('N');
      }

      if (this.flgNuovaCOFromUrg) {
        this.f.tipoComunicazioneTu[CONTROL_STATE.DISABLE]();
        this.f.provincia[CONTROL_STATE.DISABLE]();
        this.f.annoProtUrgenza[CONTROL_STATE.DISABLE]();
        this.f.numProtComUrgenza[CONTROL_STATE.DISABLE]();
        this.f.codiceComunRegPrec[CONTROL_STATE.DISABLE]();
      } else {
        this.setStatesUrgenza();
      }

      if (this.ANNULLO_MODE) {
        this.f.flgCausaForzamaggiore[CONTROL_STATE.DISABLE]();
        this.f.dsCausaForzamaggiore[CONTROL_STATE.DISABLE]();
        this.f.annoProtUrgenza[CONTROL_STATE.DISABLE]();
        this.f.numProtComUrgenza[CONTROL_STATE.DISABLE]();
        this.f.codiceComunRegPrec[CONTROL_STATE.DISABLE]();
        this.f.comunicazioneDaRettificare[CONTROL_STATE.DISABLE]();
        this.f.comunicazionePrecedenteAnnullo[CONTROL_STATE.DISABLE]();
        this.f.tipoComunicazioneTu[CONTROL_STATE.DISABLE]();
        this.f.provincia[CONTROL_STATE.DISABLE]();
        this.f.flgComunicazioneProvIsp[CONTROL_STATE.DISABLE]();
      }

    }
  }

  // id = 6 -> S else N


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

   // Enable/disable form control
   private changeFormState(controlDisabled: boolean) {
    this.logService.debug(this.constructor.name, 'changeFormState', 'controlDisabled', controlDisabled, typeof controlDisabled);
    /*
    const fnc = controlDisabled ? CONTROL_STATE.DISABLE : CONTROL_STATE.DISABLE;
    const fncDisabled = CONTROL_STATE.DISABLE;
    this.logService.debug(this.constructor.name, 'changeFormState', 'fnc', fnc);*/
   }

  setStateAltroSogg() {
    const flgComunicazioneDatore = this.f.flgComunicazioneDatore.value;
    let state = flgComunicazioneDatore === 'N' ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE;
    this.utilitiesService.changeControlState(this.f.tipoSoggettoAbilitato, state, state === CONTROL_STATE.DISABLE ? true : false);
    this.utilitiesService.changeControlState(this.f.codiceFiscaleSoggetto, state, state === CONTROL_STATE.DISABLE ? true : false);
    this.utilitiesService.changeControlState(this.f.emailsoggetto, state, state === CONTROL_STATE.DISABLE ? true : false);
  }

  async onClickSubmit() {
    if (!this.VIEW_MODE) {
      this.utilitiesService.showSpinner();
      this.alertMessageService.emptyMessages();
      const objForm = this.form.getRawValue() as any;
      this.comunicazioneToSave.email = objForm.email;
      const tipoComunicazione = this.comunicazioneToSave.tipoComunicazione;
      if (tipoComunicazione.id === TIPO_COMUNICAZIONE.ASSUNZIONE.ID) {
        this.comunicazioneToSave.flgCurrentRecord = 'S';
      }
      this.comunicazioneToSave.flgCausaForzamaggiore = objForm.flgCausaForzamaggiore;
      this.comunicazioneToSave.dsCausaForzamaggiore = objForm.dsCausaForzamaggiore;
      // VINCONO RETTIFICA E ANNULLAMENTO PER TipoComunicazioneTu
      if (!this.RETTIFICA_MODE && !this.ANNULLO_MODE) {
        if (this.f.flgComunicazioneProvIsp.value === 'S') {
          const tipoComunicazioneTu = {
            id: 6,
            codTipoComunicazioneMin: '06'
          } as TipoComunicazioneTu;
          this.comunicazioneToSave.tipoComunicazioneTu = tipoComunicazioneTu;
        } else {
          let tipoComunicazioneTu = objForm.tipoComunicazioneTu;
          if (!tipoComunicazioneTu) {
            tipoComunicazioneTu = {
              id: 1,
              codTipoComunicazioneMin: '01'
            } as TipoComunicazioneTu;
          }

          this.comunicazioneToSave.tipoComunicazioneTu = tipoComunicazioneTu;
          this.comunicazioneToSave.provincia = objForm.provincia;
          this.comunicazioneToSave.annoProtUrgenza = objForm.annoProtUrgenza;
          this.comunicazioneToSave.numProtComUrgenza = objForm.numProtComUrgenza;
        }
      }

      const flgComunicazioneDatore = objForm.flgComunicazioneDatore;
      this.comunicazioneToSave.flgComDatLav = flgComunicazioneDatore;
      if (flgComunicazioneDatore === 'N') {
        this.comunicazioneToSave.tipoSoggettoAbilitato = objForm.tipoSoggettoAbilitato;
        this.comunicazioneToSave.codiceFiscaleSoggetto = objForm.codiceFiscaleSoggetto;
        this.comunicazioneToSave.emailsoggetto = objForm.emailsoggetto;
      }
      this.comunicazioneToSave.dtTimbroPostale = objForm.dtTimbroPostale;
    }

    if (
      this.comunicazioneToSave.tipoComunicazione &&
      this.comunicazioneToSave.tipoComunicazione.id !== TIPO_COMUNICAZIONE.ASSUNZIONE.ID
    ) {
      this.comunicazioneToSave.flgCausaForzamaggiore = 'N';
    }
    const tipoComunicazioneTu: TipoComunicazioneTu = this.comunicazioneToSave.tipoComunicazioneTu;
    if (
        tipoComunicazioneTu &&
        (
          tipoComunicazioneTu.codTipoComunicazioneMin === TIPO_COMUNICAZIONE_TU.COD_MIN_07 ||
          tipoComunicazioneTu.codTipoComunicazioneMin === TIPO_COMUNICAZIONE_TU.COD_MIN_08 ||
          tipoComunicazioneTu.codTipoComunicazioneMin === TIPO_COMUNICAZIONE_TU.COD_MIN_09
        )
        ) {
          this.comunicazioneToSave.flgComunSegUrgenza = 'S';
        } else {
          this.comunicazioneToSave.flgComunSegUrgenza = 'N';
        }
    if (this.AGGIORNAMENTO_MODE) {
          this.comunicazioneToSave.provincia = this.comunicazione.provincia;
        }
    const tipoProvenienza: TipoProvenienza = {
          id: TIPO_PROVENIENZA.COMONL_VER_4.ID
        };
    const tipoAcquisizione: TipoAcquisizione = {
          id: TIPO_ACQUISIZIONE.comunicazione_completa.id
        };
    if (this.RETTIFICA_MODE) {
          this.comunicazioneToSave.flgRettifica = 'S';
        } else {
          this.comunicazioneToSave.flgRettifica = 'N';
        }
    this.comunicazioneToSave.tipoProvenienza = tipoProvenienza;
    this.comunicazioneToSave.flgInvioMinistero = 'N';
    this.comunicazioneToSave.tipoAcquisizione = tipoAcquisizione;
    if (this.comunicazioneToSave.tipoTracciato.id === TIPO_TRACCIATO.UNISOMM.ID) {
          this.comunicazioneToSave.flgSommin = 'S';
        } else {
          this.comunicazioneToSave.flgSommin = 'N';
        }
    const codiceComunRegPrec = this.f.codiceComunRegPrec.value;
    if(!this.ANNULLO_MODE && !this.RETTIFICA_MODE){
        this.comunicazioneToSave.codiceComunRegPrec = codiceComunRegPrec;
    }
   
    const wrapperComunicazione: WrapperComunicazione = {
          ruolo: this.ruolo,
          comunicazione: this.comunicazioneToSave
        };

    try {
      const res = await this.comunicazioneControlliService.chkDatiGenerali(wrapperComunicazione).toPromise();
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

  // private checkDati;

  onClickReset() {
    this.form.reset();
    this.patchValueInForm(this.comunicazione);
    this.setFormMode();
    this.alertMessageService.emptyMessages();
  }

  setRuoloControlsState() {

  }

  setStatesUrgenza() {
    const flgComunicazioneProvIsp = this.f.flgComunicazioneProvIsp.value;
    let state = flgComunicazioneProvIsp === 'S' ? CONTROL_STATE.DISABLE : CONTROL_STATE.ENABLE;
    this.utilitiesService.changeControlState(
      this.f.tipoComunicazioneTu,
      state,
      state === CONTROL_STATE.DISABLE ? true : false
    );
    // this.utilitiesService.changeControlState(
    //   this.f.codiceComunRegPrec,
    //   state,
    //   state === CONTROL_STATE.DISABLE ? true : false
    // );
    if (state === CONTROL_STATE.DISABLE) {
      this.setControlStateDatiProtoccollo();
    }

  }

  setControlStateDatiProtoccollo(clean?: boolean) {
    this.setControlStatecodiceComunRegPrec(clean);
    this.setControlStateProvincia();
    this.setControlstateAnnoNumeroProtocollo();
  }

  setControlStatecodiceComunRegPrec(clean?: boolean) {
    const tipoComunicazioneTu: TipoComunicazioneTu = this.f.tipoComunicazioneTu.value;
    let flgEnable: boolean = false;
    if (tipoComunicazioneTu) {
      if (tipoComunicazioneTu.codTipoComunicazioneMin === '07' ||  tipoComunicazioneTu.codTipoComunicazioneMin === '09') {
        flgEnable = true;
      } else if (tipoComunicazioneTu.codTipoComunicazioneMin === '08') {
        //this.f.codiceComunRegPrec.setValue(DEFAULT_CODICE_REGIONALE);
      } else if (tipoComunicazioneTu === null) {
        flgEnable = false;
      }
    }
    this.utilitiesService.changeControlState(
      this.f.codiceComunRegPrec,
      flgEnable ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE,
      clean
    );
  }

  setControlstateAnnoNumeroProtocollo() {
    this.setControlStateAnnoProtoccollo();
    this.setControlStateNumeroProtocollo();
  }

  setControlStateProvincia() {
    const flgEnable = this.conditionProvinciaEnable;
    this.utilitiesService.changeControlState(
      this.f.provincia,
      flgEnable ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE,
      !flgEnable
    );
  }

  setControlStateAnnoProtoccollo() {
    const flgEnable = this.conditionAnnoProtocolloEnable;
    this.utilitiesService.changeControlState(
      this.f.annoProtUrgenza,
      flgEnable ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE,
      !flgEnable
    );
  }

  setControlStateNumeroProtocollo() {
    const flgEnable = this.conditionNumeroProtocolloEnable;
    this.utilitiesService.changeControlState(
      this.f.numProtComUrgenza,
      flgEnable ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE,
      !flgEnable
    );
  }

}

interface SezioneRettAnn {
  provincia?: Provincia;
  annoProtCom?: number;
  numProtCom?: number;
  tipoComunicazioneTu?: TipoComunicazioneTu;
  codiceRegionale?: string;
}
