/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Ruolo } from './../../../../../../comonlapi/model/ruolo';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LogService,  UtilitiesService } from 'src/app/services';
import { Atecofin, Cittadinanza, Comune, Comunicazione, ComunicazioneControlliService, DatiAzienda, Datore, DecodificaGenerica, DecodificaService, NaturaGiuridica, SedeLavoro, SilpService, WrapperComunicazione } from 'src/app/modules/comonlapi';
import { AdComponent } from '../../../models/ad-component';
import { WrapperHelper } from 'src/app/models/wrappwer-helper';
import { NavComunicazioneParams } from '../../../models/nav-comunicazione-params';
import { CONSTANTS_MODE, CONTROL_STATE, TIPO_TRACCIATO, TYPE_DECODIFICA_GENERICA } from 'src/app/constants';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { UtilsComonl } from 'src/app/services/utilsComonl';
import { Utils } from 'src/app/utils';
import { PersistenceComunicazioneWreapper } from '../../nav-main-comunicazione/nav-main-comunicazione.component';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';

@Component({
  selector: 'comonl-dati-azienda',
  templateUrl: './dati-azienda.component.html',
  styleUrls: ['./dati-azienda.component.scss']
})
export class DatiAziendaComponent implements OnInit, AdComponent {

  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="datiAzienda"]';

  formDatiAzienda: FormGroup;

  @Input() data: any;
  @Output() datiForm = new EventEmitter<any>();
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();
  @Output() persistenceNotification = new EventEmitter<PersistenceComunicazioneWreapper>();
  comunicazione: Comunicazione;
  comunicazioneToSave: Comunicazione;
  routingParams;

  flgNuovaCO: boolean;

  flgsalto: boolean;
  showForm;
  scadenzaPermesso = true;
  somministrazione = false;

  parametriNavigazione: NavComunicazioneParams;
  wrapperHelper: WrapperHelper;

  ruolo: Ruolo;

  datore: Datore;
  datoreForReset: Datore;
  aziendaSilpFinded: DatiAzienda;
  naturaGiuridicas: NaturaGiuridica[] = [];
  sedeOperativa: SedeLavoro;
  formTest: FormGroup;

  atecoScaduto: boolean = false;
  naturaGiuridicaNonValida: boolean = false;
  codiceFiscaleFinded: string;

  listaNaturaGiuridica: NaturaGiuridica[] = [];

  get CF_DATORE_NOT_EQUAL_CF_FORM(): boolean{
    if(this.datore){
      const cfForm = this.f.codiceFiscale.value;
      const cfDatore = this.datore.codiceFiscale;
      return Utils.isNullOrUndefinedOrCampoVuoto(cfDatore) || Utils.isNullOrUndefinedOrCampoVuoto(cfForm) || cfDatore !== cfForm;
    }else{
      return true;
    }
  }

  get f() {
    return this.formDatiAzienda.controls as any;
  }

  get PERMESSI_CERCA(): boolean {
    if (this.ruolo) {
      return (this.ruolo.amministratore || this.ruolo.operatoreProvinciale || this.ruolo.consulenteRespo);
    }
    return false;
  }

  get PARAMS_ARE_PRESENT(): boolean {
    return this.routingParams !== null && this.routingParams !== undefined;
  }

  get VIEW_MODE(): boolean {
    const nuovaComParams = this.routingParams;
    if (nuovaComParams) {
      return nuovaComParams.mode === CONSTANTS_MODE.VIEW;
    }
    return false;
  }

  get INS_MODE(): boolean {
    const nuovaComParams = this.routingParams;
    if (nuovaComParams) {
      return nuovaComParams.mode === CONSTANTS_MODE.INS;
    }
    return false;
  }

  get AGGIORNAMENTO_MODE() {
    const nuovaComParams = this.routingParams;
    return nuovaComParams.mode === CONSTANTS_MODE.AGGIORNAMENTO;
  }

  get RETTIFICA_MODE(): boolean {
    const nuovaComParams = this.routingParams;
    if (nuovaComParams) {
      return nuovaComParams.mode === CONSTANTS_MODE.RETTIFICA;
    }
    return false;
  }

  get INVALIDITA_ANNULLO_MODE(): boolean{
    if(this.ANNULLO_MODE && this.atecoScaduto){
      const ateco: Atecofin = this.f.atecofin.value;
      return Utils.isNullOrUndefinedOrCampoVuoto(ateco.codAtecofinMin) ||  Utils.isNullOrUndefinedOrCampoVuoto(ateco.dsComTAtecofin);
    }else{
      return false;
    }
  }

  get INVALIDITA_FORM(){
    if(this.ANNULLO_MODE){
      return false;
    }else{
      return !this.formDatiAzienda.valid
    }
  }

  get ANNULLO_MODE(): boolean {
    const nuovaComParams = this.routingParams;
    if (nuovaComParams) {
      return nuovaComParams.mode === CONSTANTS_MODE.ANNULLO;
    }
    return false;
  }

  get EDIT_MODE() {
    const nuovaComParams = this.routingParams;
    if (nuovaComParams) {
      return nuovaComParams.mode === CONSTANTS_MODE.EDIT;
    }
    return false;
  }

  get DISABLE_INTERACTIONS_VIEW(): boolean {
    return this.VIEW_MODE || this.EDIT_MODE;
  }

  get IS_VARDATORE() {
    return this.comunicazioneToSave.tipoTracciato.id === TIPO_TRACCIATO.VARDATORI.ID;
  }

  get IS_EXTRACOMUNITARIO() {
    let isExtraComunitario = false;
    if (this.datore) {
      const sedeLegale = this.datore.sedeLegale;
      if (sedeLegale && sedeLegale.statiEsteri) {
        const statiEsteri = sedeLegale.statiEsteri;
        isExtraComunitario = !Utils.isNullOrUndefinedOrCampoVuoto(statiEsteri.codNazioneMin);
      }
    }
    return isExtraComunitario;
  }

  get DATORE_IS_NOT_VALID(): boolean {
    if (this.datore) {
      const naturaGiuridica = this.f.naturaGiuridica.value;
      const notValid = Utils.isNullOrUndefinedOrCampoVuoto(this.f.codiceFiscale.value) ||
                       Utils.isNullOrUndefinedOrCampoVuoto(this.f.dsDenominazioneDatore.value) ||
                       !naturaGiuridica ||
                        (
                          this.comunicazioneToSave.flgSommin === 'S' &&
                          ( Utils.isNullOrUndefinedOrCampoVuoto(this.f.numAgenziaSommin.value) ||
                            Utils.isNullOrUndefinedOrCampoVuoto(this.f.numeroIscrizioneAlbo.value) ||
                              Utils.isNullOrUndefinedOrCampoVuoto(this.f.iscrAlboData.value) ||
                              Utils.isNullOrUndefinedOrCampoVuoto(this.f.flgPubAmm.value)
                          )
                        ) ||
                        Utils.isNullOrUndefinedOrCampoVuoto(this.f.flgAzArtigiana.value) ||
                        this.IS_CAP_SEDE_LEGALE_NOT_VALID ||
                        Utils.isNullOrUndefinedOrCampoVuoto(this.f.sedeLegale.controls.indirizzo.value) ||
                        this.TEL_FAX_MAIL_SEDE_LEGALE_INVALID || this.TEL_FAX_MAIL_SEDE_OPERATIVA_INVALID;
      console.log(notValid);
      return notValid;
    }
    return true;
  }

  get IS_CAP_SEDE_LEGALE_NOT_VALID(): boolean{
    const comune: Comune = this.f.sedeLegale.get('comune').value;
    if(comune && comune.id && Utils.isNullOrUndefinedOrCampoVuoto(this.f.sedeLegale.get('codCap').value)){
      return true;
    }else{
     return false;
    }
  }

  get IS_OBBL_ANAGRAFICA_LEGALE_RAPPR_EXTRA_COM() {
    const datore = this.comunicazioneToSave.datoreAttuale;
    if (datore && datore.legaleRappr) {
      const cittadinanza = datore.legaleRappr.cittadinanza;
      if (cittadinanza) {
        return cittadinanza.flgUe === 'N';
      }
    }
    return false;
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
    if (this.IS_VARDATORE) {
      return false;
    } else if (sedeOperativa) {
      return Utils.isNullOrUndefinedOrCampoVuoto(sedeOperativa.telefono) && Utils.isNullOrUndefinedOrCampoVuoto(sedeOperativa.fax) && Utils.isNullOrUndefinedOrCampoVuoto(sedeOperativa.email);
    }
    return true;
  }

  constructor(
    private logService: LogService,
    private utilitiesService: UtilitiesService,
    private router: Router,
    private silpService: SilpService,
    private alertMessageService: AlertMessageService,
    private decodificaService: DecodificaService,
    private promptModalService: PromptModalService,
    private comunicazioneControlliService: ComunicazioneControlliService
  ) {
  }


  async ngOnInit() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    this.logService.info(this.constructor.name, 'ngOnInit');
    try {
      if(!this.VIEW_MODE){
        this.naturaGiuridicas = await this.decodificaService.getNaturaGiuridica().toPromise();
      }
      this.initForm();
      this.utilitiesService.scrollTo(DatiAziendaComponent.SCROLL_TARGET);
      this.routingParams = this.data.routingParams;
      this.ruolo = this.data.ruolo;
      this.comunicazioneToSave = this.data.comunicazioneToSave;
      this.comunicazione = this.data.comunicazione;
      this.flgNuovaCO = this.data.routingParams.flgNuovaCO;
      
      let sedeOperativa: SedeLavoro;
      if (this.data.datore) {
        this.datore = this.data.datore;
        sedeOperativa = this.data.datore.sedeOperativa;
      } else {
        this.datore = this.comunicazioneToSave.datoreAttuale;
        if (this.datore) {
          sedeOperativa = this.datore.sedeOperativa;

        }
      }
      
      if (this.datore && (!this.VIEW_MODE && !this.ANNULLO_MODE)) {
        this.codiceFiscaleFinded = this.datore.codiceFiscale;
        this.f.codiceFiscale.patchValue(this.datore.codiceFiscale);
        this.aziendaSilpFinded = await  this.silpService.getAziendaSilp(this.datore.codiceFiscale).toPromise();
      }
      if (!(this.ruolo.amministratore || this.ruolo.operatoreProvinciale || this.ruolo.consulenteRespo) && this.INS_MODE) {
        this.codiceFiscaleFinded = this.ruolo.codiceFiscaleAzienda;
        this.f.codiceFiscale.patchValue(this.ruolo.codiceFiscaleAzienda);
        this.aziendaSilpFinded = await  this.silpService.getAziendaSilp(this.ruolo.codiceFiscaleAzienda).toPromise();
        this.datore = UtilsComonl.getDatoreFromDatiAzienda(this.aziendaSilpFinded);
        this.datoreForReset = Utils.clone(this.datore);
        if (sedeOperativa) {
         this.datore.sedeOperativa = sedeOperativa;
       }
      }
      if (this.EDIT_MODE || this.RETTIFICA_MODE || this.AGGIORNAMENTO_MODE) {
        let idDatore: number;
        let idSedeOperativa: number;
        let idSedeLegale: number;
        let idLegaleRappr: number;
        let dtInsertLegaleRappr: Date;
        let UserInsertLegaleRappr: string;
        if (this.EDIT_MODE) {
          idDatore = this.datore.id;
          if (this.datore.sedeOperativa) {
            idSedeOperativa = this.datore.sedeOperativa.id;
          }
          if (this.datore.sedeLegale) {
            idSedeLegale = this.datore.sedeLegale.id;
          }
          if (this.datore.legaleRappr) {
            idLegaleRappr = this.datore.legaleRappr.id;
            dtInsertLegaleRappr = this.datore.legaleRappr.dtInsert;
            UserInsertLegaleRappr = this.datore.legaleRappr.idUserInsert;
          }
        }
        if ((this.RETTIFICA_MODE || this.ANNULLO_MODE || this.AGGIORNAMENTO_MODE) && this.comunicazioneToSave.id) {
          idDatore = this.datore.id;
          if (this.datore.sedeOperativa) {
            idSedeOperativa = this.datore.sedeOperativa.id;
          }
          if (this.datore.sedeLegale) {
            idSedeLegale = this.datore.sedeLegale.id;
          }
          if (this.datore.legaleRappr) {
            idLegaleRappr = this.datore.legaleRappr.id;
            dtInsertLegaleRappr = this.datore.legaleRappr.dtInsert;
            UserInsertLegaleRappr = this.datore.legaleRappr.idUserInsert;
          }
        }
        const cf = this.datore.codiceFiscale;

        const atecoFin: Atecofin = this.datore.atecofin;
        const numeroIscrizioneAlbo = this.datore.numeroIscrizioneAlbo;
        const numAgenziaSommin = this.datore.numAgenziaSommin;
        let dsVariazioneRagSociale: string;
        if (this.IS_VARDATORE) {
          dsVariazioneRagSociale = this.datore.dsVariazioneRagSociale;
        }
        sedeOperativa = this.getSedeOperativa(this.aziendaSilpFinded, sedeOperativa);
        this.codiceFiscaleFinded = this.datore.codiceFiscale;
        this.f.codiceFiscale.patchValue(this.datore.codiceFiscale);
        this.datore = UtilsComonl.getDatoreFromDatiAzienda(this.aziendaSilpFinded);
        if (atecoFin) {
          this.datore.atecofin = atecoFin;
        }
        this.datore.id = idDatore;
        // this.datore.sedeLegale.id;
        if (this.datore.sedeLegale) {
          this.datore.sedeLegale.id = idSedeLegale;
        }
        if (sedeOperativa) {
          this.datore.sedeOperativa = sedeOperativa;
        }
        if (this.datore.sedeOperativa) {
          this.datore.sedeOperativa.id = idSedeOperativa;
        }
        if (this.datore.legaleRappr) {
          this.datore.legaleRappr.id = idLegaleRappr;
          this.datore.legaleRappr.dtInsert = dtInsertLegaleRappr;
          this.datore.legaleRappr.idUserInsert = UserInsertLegaleRappr;
        }
        this.datore.numeroIscrizioneAlbo = numeroIscrizioneAlbo;
        this.datore.numAgenziaSommin = numAgenziaSommin;
        if (this.IS_VARDATORE) {
          this.datore.dsVariazioneRagSociale = dsVariazioneRagSociale;
        }
        this.datore.codiceFiscale = cf;
        this.datoreForReset = Utils.clone(this.datore);
      }

      if (this.AGGIORNAMENTO_MODE) {
        this.utilitiesService.changeControlState(
          this.f.codiceFiscale,
          CONTROL_STATE.DISABLE,
          false
        );
      }

      if (this.datore) {
        this.patchValueInForm(this.datore);
      }
      this.setFormMode();
      if(this.datore){
        this.checkDecodificheScadute(this.datore);
      }
      this.showForm = true;
    } catch (e) {
      this.showForm = true;
      if (e.error && e.error.length > 0) {
        this.alertMessageService.setErrorMsg(e.error);
      }else{
        this.alertMessageService.setSingleErrorMsg(e);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }

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
    
    
  }

  private initForm() {
    this.formDatiAzienda = new FormGroup({
      id: new FormControl(),
      idAziendaSilp: new FormControl(),
      codiceFiscale: new FormControl({value: null, disabled: false },[cfLengthValidator()]),
      statoEsteroCf: new FormControl({value: null, disabled: true }),
      partitaIva: new FormControl({ value: null, disabled: true }),
      flgUtilEstera: new FormControl({ value: 'N', disabled: true }),
      numAgenziaSommin: new FormControl({ value: null, disabled: false }),
      numeroIscrizioneAlbo: new FormControl({ value: null, disabled: false }, Validators.minLength(10)),
      iscrAlboData: new FormControl({ value: null, disabled: false }),
      dsDenominazioneDatore: new FormControl({ value: null, disabled: true }),
      flgPubAmm: new FormControl({ value: 'N', disabled: true }),
      flgAzArtigiana: new FormControl({ value: 'N', disabled: true }),
      dsVariazioneRagSociale: new FormControl(), // per vardatore, non visibile in questo component
      naturaGiuridica: new FormControl({value: null, disabled: true}),
      atecofin: new FormGroup({
        id: new FormControl(),
        codAtecofinMin: new FormControl(null,
            Validators.compose([
              Validators.required,
              Validators.minLength(3)
            ])
          ),
        dsComTAtecofin: new FormControl(null,
            Validators.compose([
              Validators.required,
              Validators.minLength(3)
            ])
          )
      }),
      legaleRappr: new FormGroup({ // LegaleRappr
        id: new FormControl(),
        flgResidenzaItaliana: new FormControl({ value: null, disabled: true }),
        dsCognome: new FormControl({ value: null, disabled: true }),
        dsNome: new FormControl({ value: null, disabled: true }),
        dtNascita: new FormControl({ value: null, disabled: true }),
        sesso: new FormControl({ value: null, disabled: true }),
        idLegaleRapprSilp: new FormControl(),
        statiEsteri: new FormGroup({
          id: new FormControl(),
          codNazioneMin: new FormControl({ value: null, disabled: true }),
          dsStatiEsteri: new FormControl({ value: null, disabled: true })
        }),
        comune: new FormGroup({
          id: new FormControl(),
          codComuneMin: new FormControl({ value: null, disabled: true }),
          dsComTComune: new FormControl({ value: null, disabled: true })
        }),
        cittadinanza: new FormGroup({
          id: new FormControl(),
          dsComTCittadinanza: new FormControl({ value: null, disabled: true }),
          flgUe: new FormControl({value: null, disabled: true})
        }),
        statusStraniero: new FormGroup({
          id: new FormControl(),
          dsComTStatusStraniero: new FormControl({ value: null, disabled: true }),
          codStatusMin: new FormControl()
        }),
        numeroDocumento: new FormControl({ value: null, disabled: true }),
        motivoPermesso: new FormGroup({
          id: new FormControl(),
          dsComTMotivoPermesso: new FormControl({ value: null, disabled: true })
        }),
        dtScadenzaPermessoSogg: new FormControl({ value: null, disabled: true }),
        questura: new FormGroup({
          id: new FormControl(),
          dsQuestura: new FormControl({ value: null, disabled: true })
        }),
        flgSoggiornanteItalia: new FormControl({ value: 'N', disabled: true }),
        // capmi audit
        idUserInsert: new FormControl(),
        dtInsert: new FormControl()
      }),
      sedeLegale: new FormGroup({
        id: new FormControl(),
        idSedeSilp: new FormControl(),
        flgSedeLegale: new FormControl({ value: null, disabled: true }),
        statiEsteri: new FormGroup({
          id: new FormControl(),
          codNazioneMin: new FormControl({ value: null, disabled: true }),
          dsStatiEsteri: new FormControl({ value: null, disabled: true })
        }),
        comune: new FormGroup({
          id: new FormControl(),
          codComuneMin: new FormControl({value: null, disabled: true}),
          dsComTComune: new FormControl({ value: null, disabled: true }),
          provincia: new FormControl(),
          cpi: new FormControl()
        }),
        codCap: new FormControl({ value: null, disabled: true }),
        indirizzo: new FormControl({ value: null, disabled: true }),
        telefono:  new FormControl({ value: null, disabled: true }),
        fax: new FormControl({ value: null, disabled: true }),
        email: new FormControl({ value: null, disabled: true })
      }),
      sedeOperativa: new FormGroup({
        id: new FormControl(),
        flgSedeLegale: new FormControl({ value: null, disabled: true }),
        idSedeSilp: new FormControl(),
        statiEsteri: new FormGroup({
          id: new FormControl(),
          codNazioneMin: new FormControl({ value: null, disabled: true }),
          dsStatiEsteri: new FormControl({ value: null, disabled: true })
        }),
        comune: new FormGroup({
          id: new FormControl(),
          codComuneMin: new FormControl({value: null, disabled: true}),
          dsComTComune: new FormControl({ value: null, disabled: true }),
          provincia: new FormControl(),
          cpi: new FormControl()
        }),
        codCap: new FormControl({ value: null, disabled: true }),
        indirizzo: new FormControl({ value: null, disabled: true }),
        telefono:  new FormControl({ value: null, disabled: true }),
        fax: new FormControl({ value: null, disabled: true }),
        email: new FormControl({ value: null, disabled: true }),
      })
    });

  }


  private setFormMode() {
    if ( 
      this.VIEW_MODE || 
      this.ANNULLO_MODE
    ) {
      this.formDatiAzienda[CONTROL_STATE.DISABLE]();
    } else if (this.AGGIORNAMENTO_MODE) {
      this.f.codiceFiscale[CONTROL_STATE.DISABLE]();
    }
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
        this.f.atecofin.patchValue(atecofin);
      }
     } catch (e) {
       if (e.error && e.error.length > 0) {
         this.alertMessageService.setWarningMsg(e.error);
        }else{
          if( e!= 0)
            this.alertMessageService.setSingleErrorMsg(e);
       }
     } finally {
       this.utilitiesService.hideSpinner();
     }
  }

  patchValueInForm(datore: Datore) {
    this.f.sedeLegale.reset();
    if(!this.IS_VARDATORE){
      this.f.sedeOperativa.reset();
    }
    this.formDatiAzienda.patchValue(datore);
    if (datore.legaleRappr) {
       const flgSoggiornanteItalia = datore.legaleRappr.flgSoggiornanteItalia;
       this.f.legaleRappr.controls.flgSoggiornanteItalia.patchValue(flgSoggiornanteItalia);
       if (!flgSoggiornanteItalia) {
         if (datore.legaleRappr.statusStraniero && datore.legaleRappr.statusStraniero.id) {
           this.f.legaleRappr.controls.flgSoggiornanteItalia.setValue('S');
          } else {
            this.f.legaleRappr.controls.flgSoggiornanteItalia.setValue('N');
         }
        }
    }
    const sedeLegale: SedeLavoro = datore.sedeLegale;
    const sedeOperativa: SedeLavoro = datore.sedeOperativa;
    if (sedeLegale && sedeLegale.statiEsteri && sedeLegale.statiEsteri.codNazioneMin) {
      this.f.flgUtilEstera.patchValue('S');
    } else {
      this.f.flgUtilEstera.patchValue('N');
    }

    if (this.comunicazioneToSave.flgSommin === 'S') {
      this.f.numeroIscrizioneAlbo.patchValue(this.getNumeroAlbo(datore));
      this.f.iscrAlboData.patchValue(this.getalboData(datore));
    }

    this.patchValueNaturaGiuridica(datore);
  }

  reset() {
    this.alertMessageService.emptyMessages();
    this.formDatiAzienda.reset();
    this.aziendaSilpFinded = null;
    this.initForm();
    if (this.EDIT_MODE || this.RETTIFICA_MODE || this.AGGIORNAMENTO_MODE) {
      this.datore = this.datoreForReset;
      this.patchValueInForm(this.datoreForReset);
    } else if (this.ANNULLO_MODE) {
      this.patchValueInForm(this.datore);
    }
    if(this.INS_MODE && !(this.ruolo.amministratore || this.ruolo.operatoreProvinciale || this.ruolo.consulenteRespo)){
      this.datore = this.datoreForReset;
      this.patchValueInForm(this.datoreForReset);
    }
  }

  private patchValueNaturaGiuridica(datore: Datore){
    if(datore){
      const naturaGiuridica = datore.naturaGiuridica;
      if(naturaGiuridica){
        const naturaGiuridicaFinded = this.naturaGiuridicas.find((el: NaturaGiuridica) => {return el.id === naturaGiuridica.id});
        if(naturaGiuridicaFinded){
          this.f.naturaGiuridica.patchValue(naturaGiuridicaFinded);
        }
      }
    }
  }

  onClickFindSedeOperativa() {
    this.alertMessageService.emptyMessages();
    let datore: Datore = this.formDatiAzienda.getRawValue() as Datore;
    datore.codiceFiscale = this.datore.codiceFiscale;
    const iscrAlboData: Date = this.f.iscrAlboData.value;
    if (
        !Utils.isNullOrUndefinedOrCampoVuoto(iscrAlboData) &&
        !Utils.isNullOrUndefinedOrCampoVuoto(this.f.numeroIscrizioneAlbo)
      ) {
        datore.numeroIscrizioneAlbo = datore.numeroIscrizioneAlbo + iscrAlboData.toLocaleDateString('IT');
      }
    const wrapperHelper: WrapperHelper = {
       comunicazione: this.comunicazioneToSave,
       datore: datore
     };
    this.router.navigate(['./dettaglio-azienda'], { state: {parametriNavigazione: this.routingParams, wrapperHelper: wrapperHelper, mode: 'edit', azienda: this.aziendaSilpFinded } });
  }
  async onClickFindAziendaByCf() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    try {
      this.codiceFiscaleFinded = this.formDatiAzienda.controls.codiceFiscale.value;
      if (!Utils.isNullOrUndefinedOrCampoVuoto(this.codiceFiscaleFinded)) {
        this.aziendaSilpFinded = await this.silpService.getAziendaSilp(this.codiceFiscaleFinded).toPromise();
        this.datore = UtilsComonl.getDatoreFromDatiAzienda(this.aziendaSilpFinded);
        console.log(JSON.stringify(this.datore));
        console.log(this.datore.numAgenziaSommin);
        if (this.EDIT_MODE) {
          this.datore.id = this.datoreForReset.id;
          this.datore.sedeLegale.id = this.datoreForReset.sedeLegale.id;
          if (!this.IS_VARDATORE) {
            this.datore.sedeOperativa.id = this.datoreForReset.sedeOperativa.id;
          }
          if (this.datoreForReset.legaleRappr && this.datore.legaleRappr) {
            this.datore.legaleRappr.id = this.datoreForReset.legaleRappr.id
          }
        }
        //this.formDatiAzienda.reset();
        this.patchValueInForm(this.datore);
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

  async onSubmit() {
    this.alertMessageService.emptyMessages();
    this.utilitiesService.showSpinner();
    const datoreToSave = this.formDatiAzienda.getRawValue() as Datore;
    // if(datoreToSave.legaleRappr.idLegaleRapprSilp){
    //   datoreToSave.legaleRappr.cittadinanza.flgUe = this.comunicazioneToSave.datoreAttuale.legaleRappr.cittadinanza.flgUe;
    // }
    const iscrAlboData: Date = this.f.iscrAlboData.value;
    if (
        !Utils.isNullOrUndefinedOrCampoVuoto(iscrAlboData) &&
        !Utils.isNullOrUndefinedOrCampoVuoto(this.f.numeroIscrizioneAlbo)
      ) {
        // datoreToSave.numeroIscrizioneAlbo = datoreToSave.numeroIscrizioneAlbo + iscrAlboData.toLocaleDateString('IT', {
        //   month: '2-digit', day: '2-digit', year: 'numeric'});
        const day: number = iscrAlboData.getDate();
        const month: number = iscrAlboData.getMonth() + 1;
        const year: number = iscrAlboData.getFullYear();
        let dayString = String(day);
        let monthString = String(month);
        if (monthString.length === 1) {
          monthString = '0' + monthString;
        }
        if (dayString.length === 1) {
          dayString = '0' + dayString;
        }
        datoreToSave.numeroIscrizioneAlbo = datoreToSave.numeroIscrizioneAlbo + dayString + "/" + monthString + "/" + year;
      }
     
    if ((this.RETTIFICA_MODE || this.ANNULLO_MODE || this.AGGIORNAMENTO_MODE) && !this.comunicazioneToSave.id) {
      if(this.AGGIORNAMENTO_MODE || !datoreToSave.id){
        datoreToSave.id = null;
        if (datoreToSave.sedeLegale) {
          datoreToSave.sedeLegale.id = null;
        }
        if (datoreToSave.sedeOperativa) {
          datoreToSave.sedeOperativa.id = null;
        }
        if (datoreToSave.legaleRappr) {
          datoreToSave.legaleRappr.id = null;
        }
      }
    }
    datoreToSave.flgNoAaep = 'N';
    if (!this.IS_VARDATORE) {
      const sedeOperativa =  datoreToSave.sedeOperativa;
      this.comunicazioneToSave.provincia = sedeOperativa.comune.provincia;
      this.comunicazioneToSave.cpi = sedeOperativa.comune.cpi;
    } else {
      const sedeLegale =  datoreToSave.sedeLegale;
      this.comunicazioneToSave.provincia = sedeLegale.comune.provincia;
      this.comunicazioneToSave.cpi = sedeLegale.comune.cpi;
    }
    this.comunicazioneToSave.cfImpresa = datoreToSave.codiceFiscale;
    this.comunicazioneToSave.datoreAttuale = datoreToSave;
    const wrapperComunicazione: WrapperComunicazione = {
      ruolo: this.ruolo,
      comunicazione: this.comunicazioneToSave
    };
    try {
       const res = await this.comunicazioneControlliService.chkImpresa(wrapperComunicazione).toPromise();
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

  get extraComunitarioSi() {
    // se lo stato estero della sede legale è valorizzato
    let flgSi = false;
    // if(this.datore){
    //   const sedeLegale = this.datore.sedeLegale;
    //   if(sedeLegale && sedeLegale.statiEsteri){
    //     flgSi = !Utils.isNullOrUndefinedOrCampoVuoto(sedeLegale.statiEsteri.codNazioneMin);
    //   }
    // }
    if (this.VIEW_MODE) {
      const datore = this.comunicazioneToSave.datoreAttuale;
      if (datore && datore.legaleRappr) {
        const cittadinanza: Cittadinanza = datore.legaleRappr.cittadinanza;
        if (cittadinanza) {
          flgSi = cittadinanza.flgUe !== 'S';
        }
      }
    }
    return flgSi;
  }

  get extraComunitarioNo() {
    let flgNo = true;
    // if(this.datore){
    //   const sedeLegale = this.datore.sedeLegale;
    //   if(sedeLegale && sedeLegale.statiEsteri){
    //     flgNo = Utils.isNullOrUndefinedOrCampoVuoto(sedeLegale.statiEsteri.codNazioneMin);
    //   }
    // }
    if (this.VIEW_MODE) {
      const datore = this.comunicazioneToSave.datoreAttuale;
      if (datore && datore.legaleRappr) {
        const cittadinanza: Cittadinanza = datore.legaleRappr.cittadinanza;
        if (cittadinanza) {
          flgNo = cittadinanza.flgUe === 'S';
        }
      }
    }
    return flgNo;
  }

  getalboData(datore: Datore): Date {
    let data: string;
    if (datore) {
      const numeroIscrizioneAlbo = datore.numeroIscrizioneAlbo;
      if (numeroIscrizioneAlbo) {
        data = numeroIscrizioneAlbo.substring(10);
        data.trim();
      }
     }
    let dataTmp: Date;
    if (data) {
      const [day, month, year] = data.split('/');
      dataTmp = new Date(+year, Number(month) - 1, +day);
     }
    return dataTmp;
  }

  get agenziaEstSi() {
    let flgSi = false;
    if (this.comunicazioneToSave.datoreAttuale && this.comunicazioneToSave.datoreAttuale.sedeLegale) {
      const sedeLegale = this.comunicazioneToSave.datoreAttuale.sedeLegale;
      flgSi = !Utils.isNullOrUndefinedOrCampoVuoto(sedeLegale.statiEsteri);
    }
    return flgSi;
  }

  get agenziaEstNo() {
    let flgNo = false;
    if (this.comunicazioneToSave.datoreAttuale && this.comunicazioneToSave.datoreAttuale.sedeLegale) {
      const sedeLegale = this.comunicazioneToSave.datoreAttuale.sedeLegale;
      flgNo = Utils.isNullOrUndefinedOrCampoVuoto(sedeLegale.statiEsteri);
    }
    return flgNo;
  }

  getNumeroAlbo(datore: Datore) {
    if (datore && datore.numeroIscrizioneAlbo) {
      return datore.numeroIscrizioneAlbo.substring(0, 10);
    }
    return null;
  }

  findInvalidControls() {
    const controls = this.formDatiAzienda.controls;
    for (const name in controls) {
        if (controls[name].invalid) {
           console.log('invalid: ' + name);
        }
    }
  }

  changeStatePippo() {
    this.formTest[CONTROL_STATE.DISABLE]();
  }

  private checkDecodificheScadute(datore: Datore){
    let sysDate: Date = new Date();
    sysDate.setHours(0,0,0,0);
    if(datore && !(this.VIEW_MODE || this.INS_MODE)){
     let ateco: Atecofin = datore.atecofin;
     let dtFineAteco: Date;
      if(ateco && ateco.dtFine){
        dtFineAteco = new Date(ateco.dtFine);
        dtFineAteco.setHours(0,0,0,0);
      }
      if(dtFineAteco && dtFineAteco <= sysDate){
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
      if(!ateco){
        this.atecoScaduto = true;
        this.utilitiesService.changeControlState(
          this.f.atecofin,
          CONTROL_STATE.ENABLE,
          true
        );
      }
    }
    if(this.datore && this.ANNULLO_MODE){
      let naturaGiuridica: NaturaGiuridica = datore.naturaGiuridica;
      let dtFineVldt: Date;
      if(naturaGiuridica && naturaGiuridica.dtFineVldt){
        dtFineVldt = new Date(naturaGiuridica.dtFineVldt);
        dtFineVldt.setHours(0,0,0,0);
      }
      if(naturaGiuridica && dtFineVldt <= sysDate){
        this.naturaGiuridicaNonValida = true;
        naturaGiuridica = null;
       
        this.utilitiesService.changeControlState(
          this.f.naturaGiuridica,
          CONTROL_STATE.ENABLE,
          true
        );
      }
      if(!naturaGiuridica){
        this.naturaGiuridicaNonValida = true;
        this.utilitiesService.changeControlState(
          this.f.naturaGiuridica,
          CONTROL_STATE.ENABLE,
          true
        );
      }
    }
    console.log(this.formDatiAzienda);
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
