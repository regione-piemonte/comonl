/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Sede } from './../../../comonlapi/model/sede';
import { RapportiLavoratoriSediInteressateVD } from './../../../comonlapi/model/rapportiLavoratoriSediInteressateVD';
import { LogService } from './../../../../services/log.service';
import { ComonlStorageService } from './../../../../services/storage/comonl-storage.service';
import { AlertMessageService } from './../../../comonlcommon/services/alert-message.service';
import { Ruolo } from './../../../comonlapi/model/ruolo';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Atecofin, CategLavAssObbl, Ccnl, Comune, DatiAzienda, DecodificaGenerica, DecodificaService, Istat2001livello5, LivelloRetribuzione, NaturaGiuridica, TipoContratti, TipoOrario, SilpService, Cpi, TipologiaTirocinio, CategTirocinante, TipoEntePromTirocinio, EntePrevidenziale, TipoAttoL68, Comunicazione, LavoratoreSilpEspanso, Rapporto, WrapperComunicazione, ComunicazioneService, ComunicazioneControlliService, StatiEsteri, SedeLavoro, Lavoratore } from 'src/app/modules/comonlapi';
import { CATEG_LAV_ASS_OBBL, CONSTANTS_MODE, CONTROL_STATE, STATO_COMUNICAZIONE, TIPO_COMUNICAZIONE, TIPO_CONTRATTI, TIPO_ORARIO, TIPO_TRACCIATO, TYPE_DECODIFICA_GENERICA } from 'src/app/constants';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';
import { UtilitiesService } from 'src/app/services';
import { Utils } from 'src/app/utils';
import { PersistenceComunicazioneWreapper } from '../../modules/nav-main-comunicazione/components/nav-main-comunicazione/nav-main-comunicazione.component';
import { UtilsComonl } from 'src/app/services/utilsComonl';
import { WrapperHelper } from 'src/app/models/wrappwer-helper';
import { utils } from 'protractor';

@Component({
  selector: 'comonl-sedi-lavoratori-vardatori',
  templateUrl: './sedi-lavoratori-vardatori.component.html',
  styleUrls: ['./sedi-lavoratori-vardatori.component.scss']
})
export class SediLavoratoriVardatoriComponent implements OnInit {

  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="sedi-lavoratori-vardatori"]';

  @Input() data: any;
  @Output() datiForm = new EventEmitter<any>();
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();
  @Output() persistenceNotification = new EventEmitter<PersistenceComunicazioneWreapper>();

  form: FormGroup;
  formLav: FormGroup;
  formDatiRapp: FormGroup;

  showForm = false;
  comunicazione: Comunicazione;
  comunicazioneToSave: Comunicazione;

  ruolo: Ruolo;
  routingParams;
  sedeDaDettaglio: Sede;
  flgStatiEsteriPresent: boolean;
  flgComunePresent: boolean;

  elencoLavoratori: RapportiLavoratoriSediInteressateVD[] = [];
  indexSelected: number;

  /*per modale di modifica lavoratore** */
  showModificaLavBtn: boolean;
  showNuovoLavBtn: boolean;
  indexItemSelected: number;
  cfSelected: string;


  listaTipologiaTirocinio: TipologiaTirocinio[] = [];
  listaCategTirocinante: CategTirocinante[] = [];
  listaTipoRapporto: TipoContratti[] = [];
  listaTipiEntePromTirocinio: TipoEntePromTirocinio[] = [];
  listaCpiEntePromTirocinio: Cpi[] = [];
  listaEntiPrevidenziali: EntePrevidenziale[] = [];
  listaTipoOrario: TipoOrario[] = [];
  listaCategLavAssObbl: CategLavAssObbl[] = [];
  listaTipoAttoL68: TipoAttoL68[] = [];
  listaNaturaGiuridica: NaturaGiuridica[] = [];

  obbligatorietaTriplaL68: boolean;
  obbligoDtFineRapporto: boolean;
  obbligocategLavAssObbl: boolean;

  disableDtfineRapportoBtn = true;
  disableDtfinePeriodoFormBtn = true;

  entePrevidenzialePerView: string = null;

  flgResetLavoratoreRapporto: boolean = false;

  flgVisualizzaLav: boolean;

  sediLavoratoriRapporti: RapportiLavoratoriSediInteressateVD[];

  get VIEW_MODE() {
    const nuovaComParams = this.routingParams;
    return nuovaComParams.mode === CONSTANTS_MODE.VIEW;
  }

  get SEDE_LAVORO_INVALID(): boolean{

    const comune: Comune = this.f.comune.value;
    const statiEsteri: StatiEsteri = this.f.statiEsteri.value;
    const indirizzo: string = this.f.indirizzo.value;
    const codCap: string = this.f.codCap.value;

    return Utils.isNullOrUndefinedOrCampoVuoto(indirizzo) || 
          this.TEL_FAX_MAIL_SEDE_LAVORO || (
                  Utils.isNullOrUndefinedOrCampoVuoto(comune.id) && Utils.isNullOrUndefinedOrCampoVuoto(statiEsteri.id)
           ) || (comune.id && Utils.isNullOrUndefinedOrCampoVuoto(codCap));

  }

  get TEL_FAX_MAIL_SEDE_LAVORO(): boolean {
    const sedeLavoro: SedeLavoro = this.form.getRawValue() as SedeLavoro;
    return Utils.isNullOrUndefinedOrCampoVuoto(sedeLavoro.telefono) && Utils.isNullOrUndefinedOrCampoVuoto(sedeLavoro.fax) && Utils.isNullOrUndefinedOrCampoVuoto(sedeLavoro.email);
   
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

  get IS_MULTI_LAVORATORE(): boolean {
    return this.comunicazioneToSave.tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID && this.comunicazioneToSave.tipoComunicazione.id === TIPO_COMUNICAZIONE.ASSUNZIONE.ID;
  }

  get IS_TIPO_CONTRATTO_RIPARTITO(): boolean {
    const rapporto = this.comunicazioneToSave.rapporto;
    if (rapporto) {
      const tipoContratti = rapporto.tipoContratti;
      if (tipoContratti) {
        return tipoContratti.codTipoContrattoMin === TIPO_CONTRATTI.LAVORO_RIPARTITO.COD_TIPO_CONTARTTO_MIN;
      }
      return false;
    }
    return false;
  }

  get f() {
    return this.form.controls as any;
  }

  get fL() {
    return this.formLav.controls as any;
  }

  get fDR() {
    return this.formDatiRapp.controls as any;
  }

  get IS_TIROCINIO() {
    const contratto: TipoContratti = this.fDR.tipoContratti.value as TipoContratti;
    if (contratto) {
      return contratto.codTipoContrattoMin === TIPO_CONTRATTI.TIROCINIO.COD_TIPO_CONTARTTO_MIN;
    }
    return false;
  }

  get IS_APPRENDISTATO() {
    const contratto: TipoContratti = this.fDR.tipoContratti.value as TipoContratti;
    if (contratto) {

    }
    return false;
  }

  get RAPPORTO_DETERMINATO() {
    const contratto: TipoContratti = this.fDR.tipoContratti.value as TipoContratti;
    if (contratto) {
      return contratto.codTipoContrattoMin === TIPO_CONTRATTI.T_DETERMINATO.COD_TIPO_CONTARTTO_MIN;
    }
    return false;
  }

  get conditionLegge68TipoEnable(): boolean {
    // console.log(this.f.categoriaLav.value);
    const categLavAssObbl: CategLavAssObbl = this.fDR.categLavAssObbl.value as CategLavAssObbl;
    if (categLavAssObbl) {
      return (
              categLavAssObbl.codCategLavAssObblMin === CATEG_LAV_ASS_OBBL.PERSONA_CON_DISABILITA_NOMINATIVA.COD_MIN ||
              categLavAssObbl.codCategLavAssObblMin === CATEG_LAV_ASS_OBBL.CATEGORIA_PROTETTA.COD_MIN ||
              categLavAssObbl.codCategLavAssObblMin === CATEG_LAV_ASS_OBBL.PERSONA_CON_DISABILITA_NUMERICA.COD_MIN
            );
    }
    return false;
  }

  get conditionLegge68DataEnable(): boolean {
    // console.log(this.f.legge68tipo.value);
    return this.fDR.tipoAttoL68.value && this.fDR.tipoAttoL68.value !== '';
  }

  get conditionLegge68NumEnable(): boolean {
    // console.log(this.f.legge68tipo.value);
    return this.fDR.tipoAttoL68.value && this.fDR.tipoAttoL68.value !== '';
  }

  get conditionEnableableDtFineRapporto(): boolean {
    const contratto: TipoContratti = this.fDR.tipoContratti.value as TipoContratti;
    if (contratto) {
     return contratto.flgForma !== 'I';
    }
    return false;
  }
  constructor(
    private utilitiesService: UtilitiesService,
    private decodificaService: DecodificaService,
    private router: Router,
    private logService: LogService,
    private comunicazioneService: ComunicazioneService,
    private silpService: SilpService,
    private promptModalService: PromptModalService,
    private comonlStorageService: ComonlStorageService,
    private alertMessageService: AlertMessageService,
    private comunicazioneControlliService: ComunicazioneControlliService
  ) {
    this.initForm();
  }

  async ngOnInit() {
    await this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    this.comonlStorageService.ruolo$.subscribe(el => this.ruolo = el);
    this.logService.info(this.constructor.name, 'ngOnInit');
    this.utilitiesService.scrollTo(SediLavoratoriVardatoriComponent.SCROLL_TARGET);
    const now = new Date();
    console.log(now);
    this.routingParams = this.data.routingParams;
    console.log('routing params: ' + JSON.stringify(this.routingParams));
    this.comunicazione = this.data.comunicazione;
    // console.log('comunicazione: ' + JSON.stringify(this.comunicazione));
    this.comunicazioneToSave = this.data.comunicazioneToSave;
    this.sediLavoratoriRapporti = this.data.sediLavoratoriRapporti;
    console.log('comunicazioneToSave: ' + JSON.stringify(this.comunicazioneToSave));
    this.ruolo = this.data.ruolo;
    this.sedeDaDettaglio = this.data.sedeOperativa;
    console.log('sedeDaDettaglio: ' + JSON.stringify(this.sedeDaDettaglio));
    try {
      const [
        tipoTirocinio,
        categTirocinante,
        tipoRapporto,
        tipoEntePromTirocinio,
        cpiEntePromTirocinio,
        entePrevidenziale,
        tipoOrario,
        categLavAssObbl,
        tipoAttoL68,
      ] = await Promise.all([
        this.decodificaService.getTipologiaTirocinio().toPromise(),
        this.decodificaService.getCategTirocinante().toPromise(),
        this.decodificaService.getTipoContrattiByTracciatoAndTipoComunicazione(TIPO_TRACCIATO.VARDATORI.ID, TIPO_COMUNICAZIONE.VARIAZIONE_DATORE.ID).toPromise(),
        this.decodificaService.getTipoEntePromTirocinio().toPromise(),
        this.decodificaService.getCpiEntePromTirocinio().toPromise(),
        this.decodificaService.getEntePrevidenziale().toPromise(),
        this.decodificaService.getTipoOrario().toPromise(),
        this.decodificaService.getCategLavoratoreAssObbl().toPromise(),
        this.decodificaService.getTipoAttoL68().toPromise(),
      ]);
      this.listaTipologiaTirocinio = tipoTirocinio;
      this.listaCategTirocinante = categTirocinante;
      this.listaTipoRapporto = tipoRapporto;
      this.listaTipiEntePromTirocinio = tipoEntePromTirocinio;
      this.listaCpiEntePromTirocinio = cpiEntePromTirocinio;
      this.listaEntiPrevidenziali = entePrevidenziale;
      this.listaTipoOrario = tipoOrario;
      this.listaCategLavAssObbl = categLavAssObbl;
      this.listaTipoAttoL68 = tipoAttoL68;
      if(this.INS_MODE || this.EDIT_MODE || this.RETTIFICA_MODE){
        const tipoAttoL68Convenzione: TipoAttoL68 = this.listaTipoAttoL68.find(el => el.id === "C");
        const index: number = this.listaTipoAttoL68.indexOf(tipoAttoL68Convenzione);
        if(index > -1){
          this.listaTipoAttoL68.splice(index,1);
        }
      }
      this.patchValueInForm(this.comunicazioneToSave);
      this.setInquadramentoMode(false);
      if(!this.VIEW_MODE){
        this.setGiornateLavPrev();
        this.setControlStateDatiTirocinio(false);
      }
      this.showForm = true;
    } catch (e) {
      this.alertMessageService.setSingleErrorMsg(e);
    } finally {
      await this.utilitiesService.hideSpinner();
    }
    if(this.sediLavoratoriRapporti && this.sediLavoratoriRapporti.length > 0){
      this.comunicazioneToSave.rapLavSedeVD = Utils.clone(this.sediLavoratoriRapporti);
      this.flgResetLavoratoreRapporto = true;
    }
    if (this.comunicazioneToSave.rapLavSedeVD && this.comunicazioneToSave.rapLavSedeVD.length > 0) {
      // POPOLAMENTO COMBO
      // console.log('entePrevidenziale: ' + this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.entePrevidenziale.id);
      // console.log('tipoOrario: ' + this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.tipoOrario.id);
      if( this.comunicazioneToSave.rapLavSedeVD[0] && this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD) {
        // Tipo Rapporto
        if (this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.tipoContratti) {
          const tipoRapporto = this.listaTipoRapporto.find((el: TipoContratti) => {
            return el.id === this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.tipoContratti.id;
          });
          this.fDR.tipoContratti.patchValue(tipoRapporto);
        }
        // Ente Previdenziale
        if (this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.entePrevidenziale) {
          const entePrevidenziale = this.listaEntiPrevidenziali.find((el: EntePrevidenziale) => {
            return el.id === this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.entePrevidenziale.id;
          });
          this.fDR.entePrevidenziale.patchValue(entePrevidenziale);
        }
        // Tipo Orario
        if (this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.tipoOrario) {
          const tipoOrario = this.listaTipoOrario.find((el: TipoOrario) => {
            return el.id === this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.tipoOrario.id;
          });
          this.fDR.tipoOrario.patchValue(tipoOrario);
        }
        // Categoria ass obbl
        if (this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.categLavAssObbl) {
          const categLavAssObbl = this.listaCategLavAssObbl.find((el: CategLavAssObbl) => {
            return el.id === this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.categLavAssObbl.id;
          });
          this.fDR.categLavAssObbl.patchValue(categLavAssObbl);
        }
        // Tipo Atto
        if (this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.tipoAttoL68) {
          const tipoAttoL68 = this.listaTipoAttoL68.find((el: TipoAttoL68) => {
            return el.id === this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.tipoAttoL68.id;
          });
          this.fDR.tipoAttoL68.patchValue(tipoAttoL68);
        }
      }
      if(this.comunicazioneToSave && this.comunicazioneToSave.rapLavSedeVD && this.comunicazioneToSave.rapLavSedeVD.length > 0){
        this.elencoLavoratori = Utils.clone(this.comunicazioneToSave.rapLavSedeVD);
      }
      console.log('elenco lavoratori: ' + JSON.stringify(this.elencoLavoratori));
    }

    if (!this.VIEW_MODE && this.sedeDaDettaglio) {
      this.form.patchValue(this.sedeDaDettaglio);
      this.f.indirizzo.patchValue(this.sedeDaDettaglio.indirizzo);
    }

    if(this.flgResetLavoratoreRapporto){
      this.formLav.reset();
      this.formDatiRapp.reset();
      this.fDR.flgLavStagionale.patchValue('N');
      this.fDR.flgSocioLavoratore.patchValue('N');
      this.fDR.flgLavoroAgricoltura.patchValue('N');
      this.fDR.flgLavInMobilita.patchValue('N');
    }
  }

  private initForm() {
    this.form = new FormGroup({
      comune: new FormGroup({
        id: new FormControl(),
        codComuneMin: new FormControl(),
        dsComTComune: new FormControl()
      }),
      statiEsteri: new FormGroup({
        id: new FormControl(),
        codNazioneMin: new FormControl(),
        dsStatiEsteri: new FormControl()
      }),
      indirizzo: new FormControl(),
      codCap: new FormControl(),
      telefono: new FormControl(),
      fax: new FormControl(),
      email: new FormControl(),
      idSedeSilp: new FormControl()
    }),
    this.formLav = new FormGroup({
      codiceFiscale: new FormControl(null, Validators.compose([
        Validators.required,
        Validators.pattern('[A-Z]{6}[0-9LMNPQRSTUV]{2}[A-Z][0-9LMNPQRSTUV]{2}[A-Z][0-9LMNPQRSTUV]{3}[A-Z]')
      ])),
      cognome: new FormControl(),
      nome: new FormControl(),
      comuneDom: new FormGroup({
        id: new FormControl(),
        codComuneMin: new FormControl(),
        dsComTComune: new FormControl(),
      }),
    }),
    this.formDatiRapp = new FormGroup({
      dtInizioRapporto: new FormControl(null, Validators.required),
      tipoContratti: new FormControl(null, Validators.required),
      dtFineRapporto: new FormControl(), // OBBL SE TIPOCONTRATTI = DETERMINATO
      dtFinePeriodoFormativo: new FormControl(), // OBBL SE TIPOCONTRATTI = APPRENDISTA || TIROCINIO (COPIA DA DATI RAPPORTO)
      flgLavStagionale: new FormControl('N', Validators.required),
      cfSoggPromotoreTirocinio: new FormControl(), // OBBL SE TIPOCONTRATTI = TIROCINIO
      ccnl: new FormGroup({
        id: new FormControl(),
        codCcnlMin: new FormControl(null),
        dsCcnl: new FormControl(null),
      }),
      retribuzioneCompenso: new FormControl(null, Validators.required),
      livelloInquadramento: new FormControl(null, Validators.required),
      istatLivello5: new FormGroup({
        id: new FormControl(),
        codIstat2001livello5Min: new FormControl(),
        dsComIstat2001livello5: new FormControl(),
      }),
      flgAssunzioneObbligatoria: new FormControl(null, Validators.required),
      patInail: new FormControl(), // MIN 8 MAX 10
      entePrevidenziale: new FormControl(null, Validators.required),
      codiceEntePrev: new FormControl(),
      tipoOrario: new FormControl(),
      numOreSettMed: new FormControl(), // COPIA DA DATI RAPPORTO
      categLavAssObbl: new FormControl(), // OBBL SE FLGASSOBBL = SI
      tipoAttoL68: new FormControl({
        value: null,
        disabled: true,
      }),
      dtLegge68: new FormControl({
        value: null,
        disabled: true,
      }),
      numeroAttoLegge68: new FormControl({
        value: null,
        disabled: true,
      }),
      flgSocioLavoratore: new FormControl('N',Validators.required),
      flgLavoroAgricoltura: new FormControl('N', Validators.required),
      giornateLavPreviste: new FormControl(), // ABILITATO SE LAV AGRICOLTURA = SI ALTRIMENTI DISABILITATO
      tipoLavorazione: new FormControl(), // ABILITATO SE LAV AGRICOLTURA = SI ALTRIMENTI DISABILITATO
      flgLavInMobilita: new FormControl('N', Validators.required),
    });
    this.fDR.ccnl.get('codCcnlMin').setValidators(
      Validators.required,
      Validators.minLength(2)
    );
    this.fDR.ccnl.get('dsCcnl').setValidators(
      Validators.required,
      Validators.minLength(3)
    );
    this.fDR.livelloInquadramento.setValidators(
      Validators.required,
        Validators.minLength(1)
    );
    // this.fDR.istatLivello5.get('codIstat2001livello5Min').setValidators(
    //   Validators.required,
    //   Validators.minLength(3)
    // );
    // this.fDR.istatLivello5.get('dsComIstat2001livello5').setValidators(
    //   Validators.required,
    //   Validators.minLength(3)
    // );
  }

  patchValueInForm(comunicazioneToSave: Comunicazione) {
    if (this.VIEW_MODE || this.ANNULLO_MODE) {
      this.form.disable();
      this.formLav.disable();
      this.formDatiRapp.disable();
      if(comunicazioneToSave.rapLavSedeVD[0]){
        this.form.patchValue(comunicazioneToSave.rapLavSedeVD[0].sedeLavoroVD);
        this.formLav.patchValue(comunicazioneToSave.rapLavSedeVD[0].lavoratoreVD);
        this.formDatiRapp.patchValue(comunicazioneToSave.rapLavSedeVD[0].rapportoVD);
      }
    } else {
      this.form.disable();
      this.fL.cognome.disable();
      this.fL.nome.disable();
      this.fL.comuneDom.get('codComuneMin').disable();
      this.fL.comuneDom.get('dsComTComune').disable();
      if (comunicazioneToSave.rapLavSedeVD) {
        this.form.patchValue(comunicazioneToSave.rapLavSedeVD[0].sedeLavoroVD);
        this.formLav.patchValue(comunicazioneToSave.rapLavSedeVD[0].lavoratoreVD);
        this.showModificaLavBtn = true;
        this.showNuovoLavBtn = false;
        this.indexItemSelected = 0;
        this.formDatiRapp.patchValue(comunicazioneToSave.rapLavSedeVD[0].rapportoVD);

        this.elencoLavoratori = this.comunicazioneToSave.rapLavSedeVD;
      }
      if (this.sedeDaDettaglio) {
        if (this.sedeDaDettaglio.statiEsteri && this.sedeDaDettaglio.statiEsteri.id) {
          this.flgStatiEsteriPresent = true;
        } else {
          this.flgStatiEsteriPresent = false;
          this.f.comune.setValidators(Validators.required);
        }

        if (this.sedeDaDettaglio.comune && this.sedeDaDettaglio.comune.id) {
          this.flgComunePresent = true;
        } else {
          this.flgComunePresent = false;
          this.f.statiEsteri.setValidators(Validators.required);
        }
      }

      // Obbligatorietà categLavAssObbl
      if (this.formDatiRapp.get('flgAssunzioneObbligatoria').value === 'S') {
        this.fDR.categLavAssObbl.setValidators(Validators.required);
        this.fDR.categLavAssObbl.updateValueAndValidity();
      }
      // Obbligatorietà tipoAttoL68, dtLegge68, numeroAttoLegge68
      if (this.formDatiRapp.get('flgAssunzioneObbligatoria').value === 'S' &&
          (this.formDatiRapp.get('categLavAssObbl').value.codNormAss68Min === '2' || // PERSONA CON DISABILITA' Nominativa
           this.formDatiRapp.get('categLavAssObbl').value.codNormAss68Min === '3' || // CATEGORIA PROTETTA
           this.formDatiRapp.get('categLavAssObbl').value.codNormAss68Min === '6'    // PERSONA CON DISABILITA' Numerica
          )) {
        // this.fDR.tipoAttoL68.setValidators(Validators.required);
        this.fDR.tipoAttoL68.enable();
        this.fDR.tipoAttoL68.updateValueAndValidity();

        // this.fDR.dtLegge68.setValidators(Validators.required);
        this.fDR.dtLegge68.enable();
        this.fDR.dtLegge68.updateValueAndValidity();

        // this.fDR.numeroAttoLegge68.setValidators(Validators.required);
        this.fDR.numeroAttoLegge68.enable();
        this.fDR.numeroAttoLegge68.updateValueAndValidity();
      }

      if (this.comunicazioneToSave.rapporto) {
        if (this.formDatiRapp.get('tipoContratti').value.codTipoContrattoMin === 'A.02.00') { // DETERMINATO
          this.fDR.dtFineRapporto.setValidators(Validators.required);
        }
      }
    }
  }

  get fieldsForm(): boolean {
    if (Utils.isNullOrUndefinedOrCampoVuoto(this.f.comune.get('codComuneMin').value) ||
        Utils.isNullOrUndefinedOrCampoVuoto(this.f.comune.get('dsComTComune').value) ||
        Utils.isNullOrUndefinedOrCampoVuoto(this.f.indirizzo.value) ||
        Utils.isNullOrUndefinedOrCampoVuoto(this.f.codCap.value) ||
        (
          Utils.isNullOrUndefinedOrCampoVuoto(this.f.telefono.value) &&
          Utils.isNullOrUndefinedOrCampoVuoto(this.f.fax.value) &&
          Utils.isNullOrUndefinedOrCampoVuoto(this.f.email.value)
        )
    ) {
      return true;
    }
    return false;
  }

  get fieldsLav(): boolean {
    if (Utils.isNullOrUndefinedOrCampoVuoto(this.fL.cognome.value) ||
        Utils.isNullOrUndefinedOrCampoVuoto(this.fL.nome.value) ||
        Utils.isNullOrUndefinedOrCampoVuoto(this.fL.comuneDom.get('codComuneMin').value) ||
        Utils.isNullOrUndefinedOrCampoVuoto(this.fL.comuneDom.get('dsComTComune').value)
    ) {
      return true;
    }
    return false;
  }

  caricaDatiLavoratore(lavoratore: RapportiLavoratoriSediInteressateVD, action: string) {
    // console.log('carico dati lavoratore scelto');
    this.alertMessageService.emptyMessages();
    if (action === 'visualizza') {
      this.flgVisualizzaLav = true;
      let lavoratoreFound = this.elencoLavoratori.find((el: RapportiLavoratoriSediInteressateVD) => {return el.lavoratoreVD.codiceFiscale === lavoratore.lavoratoreVD.codiceFiscale});
      this.indexItemSelected = this.elencoLavoratori.indexOf(lavoratoreFound);
      this.showModificaLavBtn = true;
      this.showNuovoLavBtn = false;
      this.cfSelected = null;
    } else {
      this.flgVisualizzaLav = false;
      this.cfSelected = this.indexItemSelected = null;
      this.showModificaLavBtn = false;
    }
    this.formLav[CONTROL_STATE.DISABLE]();
    this.formDatiRapp[CONTROL_STATE.DISABLE]();

    this.form.patchValue(lavoratore.sedeLavoroVD);
    this.formLav.patchValue(lavoratore.lavoratoreVD);
    this.formDatiRapp.patchValue(lavoratore.rapportoVD);

    if (lavoratore.rapportoVD.tipoContratti) {
      const tipoRapporto = this.listaTipoRapporto.find((el: TipoContratti) => {
        return el.id === lavoratore.rapportoVD.tipoContratti.id;
      });
      this.fDR.tipoContratti.patchValue(tipoRapporto);
    }

    if (lavoratore.rapportoVD.entePrevidenziale) {
      const entePrevidenziale = this.listaEntiPrevidenziali.find((el: EntePrevidenziale) => {
        return el.id === lavoratore.rapportoVD.entePrevidenziale.id;
      });
      this.fDR.entePrevidenziale.patchValue(entePrevidenziale);
    }

    if (lavoratore.rapportoVD.tipoOrario) {
      const tipoOrario = this.listaTipoOrario.find((el: TipoOrario) => {
        return el.id === lavoratore.rapportoVD.tipoOrario.id;
      });
      this.fDR.tipoOrario.patchValue(tipoOrario);
    }

    if (lavoratore.rapportoVD.categLavAssObbl) {
      const categLavAssObbl = this.listaCategLavAssObbl.find((el: CategLavAssObbl) => {
        return el.id === lavoratore.rapportoVD.categLavAssObbl.id;
      });
      this.fDR.categLavAssObbl.patchValue(categLavAssObbl);
    }

    if (lavoratore.rapportoVD.tipoAttoL68) {
      const tipoAttoL68 = this.listaTipoAttoL68.find((el: TipoAttoL68) => {
        return el.id === lavoratore.rapportoVD.tipoAttoL68.id;
      });
      this.fDR.tipoAttoL68.patchValue(tipoAttoL68);
    }
    // SCROLL TO TOP
    this.utilitiesService.scrollTo(SediLavoratoriVardatoriComponent.SCROLL_TARGET);
  }

  eliminaLavoratore(lavoratore) {
    // let isPresent = false;
    // this.elencoLavoratori.forEach((element, index) => {
    //   if (element.lavoratoreVD.id === lavoratore.id) {
    //     isPresent = true;
    //   }
    //   if (isPresent) {
    //     this.elencoLavoratori.splice(index, 1);
    //   }
    // });
    const index = this.elencoLavoratori.indexOf(lavoratore);
    if(index >= 0){
      this.elencoLavoratori.splice(index, 1);
      this.cfSelected = this.indexItemSelected = null;
      this.showModificaLavBtn = false;
    }
  }

  async aggiungiLavoratore() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    let lavoratoreDaAggiungere: RapportiLavoratoriSediInteressateVD = {};
    lavoratoreDaAggiungere.sedeLavoroVD = this.form.getRawValue();
    lavoratoreDaAggiungere.lavoratoreVD = this.formLav.getRawValue();
    lavoratoreDaAggiungere.rapportoVD = this.formDatiRapp.getRawValue();
    console.log(JSON.stringify('lavoratore da aggiungere alla tabella: ' + JSON.stringify(lavoratoreDaAggiungere)));
    let isPresent = false;

    // validatore di lavoratoreDaAggiungere
    // se va a buon fine
    try {
      if (this.elencoLavoratori && this.elencoLavoratori.length > 0) {
        // tslint:disable-next-line: prefer-for-of
        for (let index = 0; !isPresent && index < this.elencoLavoratori.length; index++) {
          if (lavoratoreDaAggiungere.lavoratoreVD.codiceFiscale === this.elencoLavoratori[index].lavoratoreVD.codiceFiscale) {
            isPresent = true;
          }
        }
        if (isPresent === false) {
          const comunicazioneToSend = Utils.clone(this.comunicazioneToSave);
          let elencoLavoratoriTmp: RapportiLavoratoriSediInteressateVD[] = [];
          elencoLavoratoriTmp.push(lavoratoreDaAggiungere);
          comunicazioneToSend.rapLavSedeVD = elencoLavoratoriTmp;
          const res = await this.comunicazioneControlliService.chkSediLavoro(comunicazioneToSend).toPromise();
          if (res) {
            this.elencoLavoratori.push(res.rapLavSedeVD[0]);
           // this.indexItemSelected = this.elencoLavoratori.indexOf(res.rapLavSedeVD[0]);
          }
        }
      } else {
        const comunicazioneToSend = Utils.clone(this.comunicazioneToSave);
        let elencoLavoratoriTmp: RapportiLavoratoriSediInteressateVD[] = [];
        elencoLavoratoriTmp.push(lavoratoreDaAggiungere);
        comunicazioneToSend.rapLavSedeVD = elencoLavoratoriTmp;
        const res = await this.comunicazioneControlliService.chkSediLavoro(comunicazioneToSend).toPromise();
        if (res) {
            this.elencoLavoratori.push(res.rapLavSedeVD[0]);
            //this.indexItemSelected = this.elencoLavoratori.indexOf(res.rapLavSedeVD[0]);
        }
      }
      this.caricaDatiLavoratore(lavoratoreDaAggiungere, 'visualizza');
    } catch (e) {
      if (e.error && e.error.length) {
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
       this.alertMessageService.setWarningMsg(e.error);
     } finally {
       this.utilitiesService.hideSpinner();
     }
  }

  async onSubmit() {
    // Controllo controlli invalidi (DA TOGLIERE)
    this.findInvalidControls();
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const formRapporto = this.formDatiRapp.getRawValue() as Rapporto;
    if (!this.comunicazioneToSave.rapporto) {
      this.comunicazioneToSave.rapporto = new Object() as Rapporto;
    }
    this.comunicazioneToSave.rapporto.id =  formRapporto.id;
    this.comunicazioneToSave.rapporto.dtInizioRapporto = formRapporto.dtInizioRapporto;
    this.comunicazioneToSave.rapporto.tipoContratti = formRapporto.tipoContratti;
    this.comunicazioneToSave.rapporto.dtFineRapporto = formRapporto.dtFineRapporto;
    this.comunicazioneToSave.rapporto.dtFinePeriodoFormativo = formRapporto.dtFinePeriodoFormativo;
    this.comunicazioneToSave.rapporto.flgLavStagionale = formRapporto.flgLavStagionale;
    this.comunicazioneToSave.rapporto.cfSoggPromotoreTirocinio = formRapporto.cfSoggPromotoreTirocinio;
    // this.comunicazioneToSave.rapporto.dsSoggPromTirocinio = formRapporto.dsSoggPromTirocinio;
    this.comunicazioneToSave.rapporto.ccnl = formRapporto.ccnl;
    this.comunicazioneToSave.rapporto.livelloInquadramento = formRapporto.livelloInquadramento;
    this.comunicazioneToSave.rapporto.retribuzioneCompenso = formRapporto.retribuzioneCompenso;

    this.comunicazioneToSave.rapporto.istatLivello5 = formRapporto.istatLivello5;
    this.comunicazioneToSave.rapporto.flgAssunzioneObbligatoria = formRapporto.flgAssunzioneObbligatoria;
    this.comunicazioneToSave.rapporto.patInail = formRapporto.patInail;
    this.comunicazioneToSave.rapporto.entePrevidenziale = formRapporto.entePrevidenziale;
    this.comunicazioneToSave.rapporto.codiceEntePrev = formRapporto.codiceEntePrev;

    this.comunicazioneToSave.rapporto.tipoOrario = formRapporto.tipoOrario;
    this.comunicazioneToSave.rapporto.numOreSettMed = formRapporto.numOreSettMed;
    this.comunicazioneToSave.rapporto.categLavAssObbl = formRapporto.categLavAssObbl;
    this.comunicazioneToSave.rapporto.tipoAttoL68 = formRapporto.tipoAttoL68;
    this.comunicazioneToSave.rapporto.dtLegge68 = formRapporto.dtLegge68;
    this.comunicazioneToSave.rapporto.numeroAttoLegge68 = formRapporto.numeroAttoLegge68;
    this.comunicazioneToSave.rapporto.flgSocioLavoratore = formRapporto.flgSocioLavoratore;

    this.comunicazioneToSave.rapporto.flgLavoroAgricoltura = formRapporto.flgLavoroAgricoltura;
    this.comunicazioneToSave.rapporto.giornateLavPreviste = formRapporto.giornateLavPreviste;
    this.comunicazioneToSave.rapporto.tipoLavorazione = formRapporto.tipoLavorazione;
    this.comunicazioneToSave.rapporto.flgLavInMobilita = formRapporto.flgLavInMobilita;

    if (!this.comunicazioneToSave.rapporto.flgLavStagionale) {
      this.comunicazioneToSave.rapporto.flgLavStagionale = 'N';
    }
    if (!this.comunicazioneToSave.rapporto.flgSocioLavoratore) {
      this.comunicazioneToSave.rapporto.flgSocioLavoratore = 'N';
    }
    if (!this.comunicazioneToSave.rapporto.flgLavInMobilita) {
      this.comunicazioneToSave.rapporto.flgLavInMobilita = 'N';
    }
    if (!this.comunicazioneToSave.rapporto.flgLavoroAgricoltura) {
      this.comunicazioneToSave.rapporto.flgLavoroAgricoltura = 'N';
    }
    this.comunicazioneToSave.rapLavSedeVD = this.elencoLavoratori;
    const wrapperComunicazione: WrapperComunicazione = {
      ruolo: this.ruolo,
      comunicazione: this.comunicazioneToSave
    };
    const id = this.comunicazioneToSave.id;
    let res: Comunicazione;
    try {
      if (!id) {
        this.comunicazioneToSave.statoComunicazione = {
          id: STATO_COMUNICAZIONE.INSERITA.ID
        };

        if (this.RETTIFICA_MODE) {
          res = await this.comunicazioneService.rettificaComunicazioneVardatore(wrapperComunicazione).toPromise();
        } else if (this.ANNULLO_MODE) {
          res = await this.comunicazioneService.annullaComunicazioneVardatore(wrapperComunicazione).toPromise();
        }else{
          res = await this.comunicazioneService.postComunicazioneVardatore(wrapperComunicazione).toPromise();
        }
      } else {
        res = await this.comunicazioneService.putComunicazioneVardatore(wrapperComunicazione).toPromise();
      }
      if (res) {
        this.prosegui(res);
      }
    } catch (e) {
      this.alertMessageService.setSingleErrorMsg(e);
      if ( e.error && e.error.length > 0) {
        this.alertMessageService.setErrorMsg(e.error);
      }

    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  private async prosegui(res: Comunicazione) {
    let responseComunicazione = await this.comunicazioneService.getComunicazioneById(res.id).toPromise();
    responseComunicazione.apiWarnings = res.apiWarnings;
    const persistenceComunicazioneWreapper: PersistenceComunicazioneWreapper = {
      comunicazione: responseComunicazione,
      ricalcoloQuadri: false,
      switchEditMode: this.INS_MODE,
      success: true
    };
    this.persistenceNotification.emit(persistenceComunicazioneWreapper);
  }

  onClickCercaSedi() {
    const wrapperHelper: WrapperHelper = {
      comunicazione: this.comunicazioneToSave,
      sediLavoratoriRapporti: this.elencoLavoratori
    };
    this.router.navigate(['./dettaglio-azienda'], { state: {parametriNavigazione: this.routingParams, wrapperHelper: wrapperHelper, mode: this.RETTIFICA_MODE ? this.RETTIFICA_MODE : this.EDIT_MODE, flgRicerca: true, azienda: this.comunicazioneToSave.datoreAttuale } });
  }

  onClickAnnulla() {
    this.alertMessageService.emptyMessages();
    this.showNuovoLavBtn = false;
    this.showModificaLavBtn = false;
    if (this.INS_MODE) {
      this.utilitiesService.changeControlState(
        this.fL.codiceFiscale,
        CONTROL_STATE.ENABLE,
        true
      );
      this.formLav.reset();
      this.formDatiRapp.enable();
      this.formDatiRapp.reset();
      this.elencoLavoratori.splice(0);
      this.flgVisualizzaLav = false;
    } else if (this.EDIT_MODE || this.RETTIFICA_MODE) {
      this.elencoLavoratori = this.comunicazioneToSave.rapLavSedeVD = Utils.clone(this.comunicazione.rapLavSedeVD);
      this.form.patchValue(this.comunicazioneToSave.rapLavSedeVD[0].sedeLavoroVD);
      this.formLav.patchValue(this.comunicazioneToSave.rapLavSedeVD[0].lavoratoreVD);
      this.showModificaLavBtn = true;
      this.formDatiRapp.patchValue(this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD);
      // POPOLAMENTO COMBO
      // console.log('entePrevidenziale: ' + this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.entePrevidenziale.id);
      // console.log('tipoOrario: ' + this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.tipoOrario.id);
      if( this.comunicazioneToSave.rapLavSedeVD[0] && this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD) {
        // Tipo Rapporto
        if (this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.tipoContratti) {
          const tipoRapporto = this.listaTipoRapporto.find((el: TipoContratti) => {
            return el.id === this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.tipoContratti.id;
          });
          this.fDR.tipoContratti.patchValue(tipoRapporto);
        }
        // Ente Previdenziale
        if (this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.entePrevidenziale) {
          const entePrevidenziale = this.listaEntiPrevidenziali.find((el: EntePrevidenziale) => {
            return el.id === this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.entePrevidenziale.id;
          });
          this.fDR.entePrevidenziale.patchValue(entePrevidenziale);
        }
        // Tipo Orario
        if (this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.tipoOrario) {
          const tipoOrario = this.listaTipoOrario.find((el: TipoOrario) => {
            return el.id === this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.tipoOrario.id;
          });
          this.fDR.tipoOrario.patchValue(tipoOrario);
        }
        // Categoria ass obbl
        if (this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.categLavAssObbl) {
          const categLavAssObbl = this.listaCategLavAssObbl.find((el: CategLavAssObbl) => {
            return el.id === this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.categLavAssObbl.id;
          });
          this.fDR.categLavAssObbl.patchValue(categLavAssObbl);
        }
        // Tipo Atto
        if (this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.tipoAttoL68) {
          const tipoAttoL68 = this.listaTipoAttoL68.find((el: TipoAttoL68) => {
            return el.id === this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.tipoAttoL68.id;
          });
          this.fDR.tipoAttoL68.patchValue(tipoAttoL68);
        }
      }
    }
    let cfSoggPromotoreTirocinio: string;
    if(this.comunicazioneToSave.rapLavSedeVD && this.comunicazioneToSave.rapLavSedeVD[0]) {
      if(this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD){
        cfSoggPromotoreTirocinio = this.comunicazioneToSave.rapLavSedeVD[0].rapportoVD.cfSoggPromotoreTirocinio;
      }
    }
    if(Utils.isNullOrUndefinedOrCampoVuoto(cfSoggPromotoreTirocinio)){
      this.setControlStateDatiTirocinio(true);
    }else{
      this.setControlStateDatiTirocinio(false);
    }
    this.setControlStateAltriDati();
  }

  private checkCfLavisPresent(codiceFiscale: string): boolean {
    let isPresent = false;
    if (this.elencoLavoratori &&
        this.elencoLavoratori.length > 0) {
          for (let index = 0; !isPresent && index < this.elencoLavoratori.length; index++) {
            isPresent = (codiceFiscale === this.elencoLavoratori[index].lavoratoreVD.codiceFiscale);
          }
        }
    return isPresent;
  }

  async onClickFindLavoratore(cf) {
    this.showModificaLavBtn = false;
    this.alertMessageService.emptyMessages();
    this.utilitiesService.showSpinner();
    if(Utils.isNullOrUndefinedOrCampoVuoto(cf)){
      this.utilitiesService.hideSpinner();
      return;
    }
    if (this.checkCfLavisPresent(cf)) {
      this.utilitiesService.hideSpinner();
      this.alertMessageService.setSingleErrorMsg('Il lavoratore è già presente');
      this.showModificaLavBtn = true;
      this.cfSelected = cf;
      return;
    }
    try {
      const resSilp: LavoratoreSilpEspanso = await this.silpService.getLavoratoreSilpEspanso(cf).toPromise();
      if (resSilp) {
        this.showModificaLavBtn = true;
        this.showNuovoLavBtn = false;
        console.log('res silp: ' + JSON.stringify(resSilp));
        let lavoratore = UtilsComonl.getLavoratoreFromLavoratoreSilpEspanso(resSilp);
        lavoratore.codiceFiscale = this.fL.codiceFiscale;
        // this.fL.codiceFiscale.patchValue(lavoratore.codiceFiscale);
        this.fL.cognome.patchValue(lavoratore.cognome);
        this.fL.nome.patchValue(lavoratore.nome);
        this.fL.comuneDom.patchValue(lavoratore.comuneDom);
        this.cfSelected = cf;
      } else {
        this.alertMessageService.setSingleErrorMsg('Nessun risultato trovato');
        this.showNuovoLavBtn = true;
        this.cfSelected = null;
      }
    } catch (e) {
      if (e.error && e.error.length) {
        this.alertMessageService.setErrorMsg(e.error);
      }else{
        this.alertMessageService.setSingleErrorMsg(e);
      }
      this.showNuovoLavBtn = true;
      this.cfSelected = null;
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  onClickSvuotaForm() {
    this.formLav.enable();
    this.formLav.reset();
    this.utilitiesService.changeControlState(
      this.fL.cognome,
      CONTROL_STATE.DISABLE,
      true
    );
    this.utilitiesService.changeControlState(
      this.fL.nome,
      CONTROL_STATE.DISABLE,
      true
    );
    this.utilitiesService.changeControlState(
      this.fL.comuneDom,
      CONTROL_STATE.DISABLE,
      true
    );
    this.formDatiRapp.enable();
    this.formDatiRapp.reset();
    this.fDR.flgLavStagionale.setValue('N');
    this.fDR.flgSocioLavoratore.setValue('N');
    this.fDR.flgLavoroAgricoltura.setValue('N');
    this.fDR.flgLavInMobilita.setValue('N');
    this.flgVisualizzaLav = false;
    this.indexItemSelected = null;
    this.cfSelected = null;
    this.showNuovoLavBtn = this.showModificaLavBtn = false;
  }

  setControlStateLegge68Tipo() {
    this.fDR.tipoAttoL68.clearValidators();
    if (!this.VIEW_MODE) {
      const flgEnable = this.conditionLegge68TipoEnable;
      this.utilitiesService.changeControlState(
      this.fDR.tipoAttoL68,
      flgEnable ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE,
      !flgEnable
      );
      this.utilitiesService.changeControlState(
        this.fDR.dtLegge68,
        flgEnable ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE,
        !flgEnable
      );
      this.utilitiesService.changeControlState(
        this.fDR.numeroAttoLegge68,
        flgEnable ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE,
        !flgEnable
      );
      if (flgEnable) {
        this.fDR.tipoAttoL68.setValidators(Validators.required);
      }
    }
    this.fDR.tipoAttoL68.updateValueAndValidity();
  }

  setFormControlsState() {
    this.setStateFormControlsDipendentidaTipoContratto();
    // this.setControlsStatePerCpi();
    // this.setDsSoggPromTirocinio();
    this.setControlStatenumOreSettMed();
    this.setControlStateAltriDati();
    this.setControlStateLegge68Tipo();
  }

  /*PAUL*/
  // setObbligatorieta() {
  //   this.setControlStateLeggeData();
  //   this.setControlStateLeggeNumAtto();
  // }

  // setControlStateLeggeData() {
  //   const flgEnable = this.conditionLegge68DataEnable;
  //   this.utilitiesService.changeControlState(
  //     this.fDR.tipoAttoL68,
  //     flgEnable ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE,
  //     !flgEnable
  //   );
  // }

  // setControlStateLeggeNumAtto() {
  //   const flgEnable = this.conditionLegge68NumEnable;
  //   this.utilitiesService.changeControlState(
  //     this.fDR.tipoAttoL68,
  //     flgEnable ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE,
  //     !flgEnable
  //   );
  // }

  /*FINE PAUL*/

  async onClickfindCcnl() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const decodifica: DecodificaGenerica = {
      codDecodifica: this.fDR.ccnl.get('codCcnlMin').value,
      dsDecodifica: this.fDR.ccnl.get('dsCcnl').value,
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
        this.fDR.ccnl.patchValue(ccnl);
      }
    } catch (e) {
      if (e.error && e.error.length > 0) {
        this.alertMessageService.setWarningMsg(e.error);
      }else{
        this.alertMessageService.setSingleErrorMsg(e);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
    this.setInquadramentoMode(true);
  }

  async onClickfindLivelloRetr() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const decodifica: DecodificaGenerica = {
      idCcnl: this.fDR.ccnl.controls.id.value,
      codDecodifica: this.fDR.livelloRetribuzione.get('codLivello').value,
      dsDecodifica: this.fDR.livelloRetribuzione.get('desLivello').value,
    };
    try {
      const res = await this.decodificaService.postLivelloRetribuzioneDecodifica(decodifica).toPromise();
      let docodificaFinded: DecodificaGenerica;
      if (res && res.length === 1) {
        docodificaFinded = res[0];
      } else if (res && res.length > 1) {
        docodificaFinded = await this.promptModalService.openDecodificaPrompt(
          'Selezionare un inquadramento', res, TYPE_DECODIFICA_GENERICA.LIV_RETR, decodifica);
      }
      if (docodificaFinded) {
        const livelloRetribuzione: LivelloRetribuzione = {
          codLivello: docodificaFinded.codDecodifica,
          desLivello: docodificaFinded.dsDecodifica,
        };
        this.fDR.livelloRetribuzione.patchValue(livelloRetribuzione);
      }
    } catch (e) {
      if (e.error && e.error.length > 0) {
        this.alertMessageService.setWarningMsg(e.error);
      }else{
        this.alertMessageService.setSingleErrorMsg(e);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  async onClickFindIstat() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const decodifica: DecodificaGenerica = {
      codDecodifica: this.fDR.istatLivello5.get('codIstat2001livello5Min').value,
      dsDecodifica: this.fDR.istatLivello5.get('dsComIstat2001livello5').value,
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
        this.fDR.istatLivello5.patchValue(istatLivello5);
      }
    } catch (e) {
      if (e.error && e.error.length > 0) {
        this.alertMessageService.setWarningMsg(e.error);
      }else{
        this.alertMessageService.setSingleErrorMsg(e);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  setStateFormControlsDipendentidaTipoContratto(cleanValue?: boolean) {
    this.setStatedtFineRapporto();
    this.setStateDtFinePeriodoFormativo();
    this.setStateFlgLvaoroStagionale();
    this.setControlStateDatiTirocinio(cleanValue);
  }

  private setStateDtFinePeriodoFormativo() {
    this.fDR.dtFinePeriodoFormativo.clearValidators();
    let state = CONTROL_STATE.DISABLE;
    let flgCleanValue = true;
    const contratto: TipoContratti = this.fDR.tipoContratti.value;
    if (contratto && contratto.tipo === 'AP') {
      this.disableDtfinePeriodoFormBtn = false;
      this.fDR.dtFinePeriodoFormativo.setValidators(Validators.required);
      state = CONTROL_STATE.ENABLE;
      flgCleanValue = false;
    } else {
      this.disableDtfinePeriodoFormBtn = true;
    }
    this.utilitiesService.changeControlState(
      this.fDR.dtFinePeriodoFormativo,
      state,
      flgCleanValue
    );
    this.fDR.dtFinePeriodoFormativo.updateValueAndValidity();
    console.log('dtFinePeriodoFormativo: ' + this.fDR.dtFinePeriodoFormativo.valid);
  }

  private setStatedtFineRapporto() {
    this.fDR.dtFineRapporto.clearValidators();
    let state = CONTROL_STATE.DISABLE;
    let flgCleanValue = true;
    const contratto: TipoContratti = this.fDR.tipoContratti.value;
    if (this.conditionEnableableDtFineRapporto) {
      this.disableDtfineRapportoBtn = false;
      if (contratto.flgForma === 'D') {
        this.fDR.dtFineRapporto.setValidators(Validators.required);
      }
      state = CONTROL_STATE.ENABLE;
      flgCleanValue = false;
    } else {
      this.disableDtfineRapportoBtn = true;
    }
    this.utilitiesService.changeControlState(
      this.fDR.dtFineRapporto,
      state,
      flgCleanValue
    );
    this.fDR.dtFineRapporto.updateValueAndValidity();
    if (contratto && contratto.flgForma === 'D') {
      this.obbligoDtFineRapporto = true;
    } else {
      this.obbligoDtFineRapporto = false;
    }
  }

  private setStateFlgLvaoroStagionale() {
    let state = CONTROL_STATE.ENABLE;
    const contratto: TipoContratti = this.fDR.tipoContratti.value;
    if (contratto && contratto.flgForma === 'I') {
      state = CONTROL_STATE.DISABLE;
    }
    this.utilitiesService.changeControlState(
      this.fDR.flgLavStagionale,
      state,
      state === CONTROL_STATE.DISABLE ? true : false
    );
  }

  private setControlStateDatiTirocinio(cleanValue?: boolean) {
    this.fDR.cfSoggPromotoreTirocinio.clearValidators();
    if (this.IS_TIROCINIO) {
      this.utilitiesService.changeControlState(
        this.fDR.cfSoggPromotoreTirocinio,
        CONTROL_STATE.ENABLE,
        cleanValue
      )
      this.fDR.cfSoggPromotoreTirocinio.setValidators(Validators.required);
    } else {
      this.utilitiesService.changeControlState(
        this.fDR.cfSoggPromotoreTirocinio,
        CONTROL_STATE.DISABLE,
        cleanValue
      )
    }
    this.fDR.cfSoggPromotoreTirocinio.updateValueAndValidity();
  }

  async onClickFindSoggPromotoreTirocinio() {
    this.utilitiesService.showSpinner();
    const cf = this.fDR.cfSoggPromotoreTirocinio.value;
    try {
      const res: DatiAzienda = await this.silpService.getAziendaSilp(cf).toPromise();
      if (res) {
        this.fDR.dsSoggPromTirocinio.setValue(res.denominazioneDatoreLavoro);
      }
    } catch (e) {
      if (e.error && e.error.length) {
        this.alertMessageService.setErrorMsg(e.error);
      }else{
        this.alertMessageService.setSingleErrorMsg(e);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  // setControlsStatePerCpi() {
  //   const entePromTirocinio = this.f.tipoEntePromTirocinio.value;
  //   let stateCpi = CONTROL_STATE.DISABLE;
  //   let stateDsSogg = CONTROL_STATE.ENABLE;
  //   console.log(JSON.stringify(entePromTirocinio));
  //   if (entePromTirocinio) {
  //     if (entePromTirocinio.codiceEntePromTirocinioMin === '01') {
  //       stateCpi = CONTROL_STATE.ENABLE;
  //       stateDsSogg = CONTROL_STATE.DISABLE;
  //     }
  //   }
  //   this.utilitiesService.changeControlState(
  //     this.f.cpi,
  //     stateCpi,
  //     stateCpi === CONTROL_STATE.DISABLE ? true : false
  //   );
  //   this.utilitiesService.changeControlState(
  //     this.f.dsSoggPromTirocinio,
  //     stateDsSogg,
  //     stateDsSogg === CONTROL_STATE.ENABLE ? true : false
  //   );
  // }

  // setDsSoggPromTirocinio() {
  //   const cpi: Cpi = this.f.cpi.value;
  //   if (cpi) {
  //     const str = 'CPI ' + cpi.dsComTCpi;
  //     this.f.dsSoggPromTirocinio.setValue(str);
  //   }
  // }

  setControlStatenumOreSettMed() {

    let state = CONTROL_STATE.DISABLE;
    const tipoOrario: TipoOrario = this.fDR.tipoOrario.value;
    this.fDR.numOreSettMed.clearValidators();
    if (
      tipoOrario && (
      tipoOrario.codTipoorarioMin === TIPO_ORARIO.TEMPO_PARZIALE_ORIZZONTALE.COD_TIPO_ORARIO_MIN ||
      tipoOrario.codTipoorarioMin === TIPO_ORARIO.TEMPO_PARZIALE_VERTICALE.COD_TIPO_ORARIO_MIN ||
      tipoOrario.codTipoorarioMin === TIPO_ORARIO.TEMPO_PARZIALE_MISTO.COD_TIPO_ORARIO_MIN )
    ) {
      state = CONTROL_STATE.ENABLE;
      this.fDR.numOreSettMed.setValidators(Validators.required);
    }
    this.utilitiesService.changeControlState(
      this.fDR.numOreSettMed,
      state,
      CONTROL_STATE.DISABLE ? true : false
    );
    this.fDR.numOreSettMed.updateValueAndValidity();
  }

  setControlStateAltriDati() {
    if (!this.VIEW_MODE) {
      const flgAssunzioneObbligatoria = this.fDR.flgAssunzioneObbligatoria.value;
      let state = CONTROL_STATE.DISABLE;
      this.fDR.categLavAssObbl.clearValidators();
      if (flgAssunzioneObbligatoria === 'S') {
        state = CONTROL_STATE.ENABLE;
        this.fDR.categLavAssObbl.setValidators(Validators.required);
        this.fDR.flgLavInMobilita.setValidators(Validators.required);
        this.fDR.flgSocioLavoratore.setValidators(Validators.required);
        this.obbligocategLavAssObbl = true;
      } else {
        this.obbligocategLavAssObbl = false;
      }
      this.fDR.categLavAssObbl.updateValueAndValidity();
      this.fDR.flgLavInMobilita.updateValueAndValidity();
      this.utilitiesService.changeControlState(
        this.fDR.categLavAssObbl,
        state,
        state === CONTROL_STATE.DISABLE ? true : false
      );
      this.setControlStateLegge68Tipo();
      this.setGiornateLavPrev();
    }
  }

  setbligatrietaTriplaL68() {
    const tipoAttoL68 = this.fDR.tipoAttoL68.value;
    const dtLegge68 = this.fDR.dtLegge68.value;
    const numeroAttoLegge68 = this.fDR.numeroAttoLegge68.value;
    this.fDR.tipoAttoL68.clearValidators();
    this.fDR.dtLegge68.clearValidators();
    this.fDR.numeroAttoLegge68.clearValidators();
    if (
        tipoAttoL68 ||
        !Utils.isNullOrUndefinedOrCampoVuoto(dtLegge68) ||
        !Utils.isNullOrUndefinedOrCampoVuoto(numeroAttoLegge68)
    ) {
      this.obbligatorietaTriplaL68 = true;
      this.fDR.tipoAttoL68.setValidators(Validators.required);
      this.fDR.dtLegge68.setValidators(Validators.required);
      this.fDR.numeroAttoLegge68.setValidators(Validators.required);
    } else {
      this.obbligatorietaTriplaL68 = false;
    }
    this.fDR.tipoAttoL68.updateValueAndValidity();
    this.fDR.dtLegge68.updateValueAndValidity();
    this.fDR.numeroAttoLegge68.updateValueAndValidity();
  }

  setGiornateLavPrev() {
    const flgLavoroAgricoltura = this.fDR.flgLavoroAgricoltura.value;
    const state = flgLavoroAgricoltura === 'S' ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE;
    this.utilitiesService.changeControlState(
      this.fDR.giornateLavPreviste,
      state,
      state === 'disable' ? true : false
    );
    this.utilitiesService.changeControlState(
      this.fDR.tipoLavorazione,
      state,
      state === 'disable' ? true : false
    );
  }

  private setInquadramentoMode(clean: boolean) {
    if (this.VIEW_MODE || this.ANNULLO_MODE) {
      this.fDR.livelloInquadramento[CONTROL_STATE.DISABLE]();
    } else if (this.formDatiRapp.controls.ccnl.get('id').value) {
      this.utilitiesService.changeControlState(
        this.fDR.livelloInquadramento,
        CONTROL_STATE.ENABLE,
        clean
      );
      // this.f.livelloRetribuzione[CONTROL_STATE.ENABLE]();
    } else {
      this.fDR.livelloInquadramento[CONTROL_STATE.DISABLE]();
    }
  }

  findInvalidControls() {
    const controls = this.formDatiRapp.controls;
    for (const name in controls) {
        if (controls[name].invalid) {
           console.log('invalid: ' + name);
        }
    }
    console.log('status: ' + this.formDatiRapp.status);
    console.log('valid: ' + this.formDatiRapp.valid);
  }

  /**START MODALE LAVORATORE VD**/
  async onClickOpenModalModificaLavoratore(){
    this.alertMessageService.emptyMessages();
    this.utilitiesService.showSpinner();
    let cf: string = this.cfSelected;
    let lavoratoreVD: Lavoratore
    if(Utils.isNullOrUndefinedOrCampoVuoto(cf)){
      lavoratoreVD = this.elencoLavoratori[this.indexItemSelected].lavoratoreVD;
      cf= lavoratoreVD.codiceFiscale;

    }
    let resSilp: LavoratoreSilpEspanso
    try{
     resSilp  =  await this.silpService.getLavoratoreSilpEspanso(cf).toPromise();
    }catch(e){
      this.alertMessageService.setSingleErrorMsg("Attenzione! Codice fiscale da modificare non valido");
    }finally{
      this.utilitiesService.hideSpinner();
    }
    try{
      let cfLavoratoreSilpSalvato = await this.promptModalService.openModalAnagraficaLavoratore("edit",this.comunicazione,resSilp);
      this.alertMessageService.emptyMessages();
      if(!Utils.isNullOrUndefinedOrCampoVuoto(cfLavoratoreSilpSalvato)){
        this.utilitiesService.showSpinner();
        const resSilp: LavoratoreSilpEspanso =  await this.silpService.getLavoratoreSilpEspanso(cfLavoratoreSilpSalvato).toPromise();
        if(resSilp && Utils.isNullOrUndefinedOrCampoVuoto(this.cfSelected)){
          lavoratoreVD.nome = resSilp.nome;
          lavoratoreVD.cognome = resSilp.cognome;
          lavoratoreVD.comuneDom = resSilp.comuneDomicilio;
          this.fL.cognome.patchValue(lavoratoreVD.cognome);
          this.fL.nome.patchValue(lavoratoreVD.nome);
          this.fL.comuneDom.patchValue(lavoratoreVD.comuneDom);
          this.cfSelected = null;
          this.alertMessageService.setSuccessSingleMsg("Salvataggio del lavoratore nell'anagrafica regionale eseguito con successo.</br>Per aggiornare i dati anagrafici del lavoratore è necessario confermare la comunicazione.");
        }else{
          this.fL.cognome.patchValue(resSilp.cognome);
          this.fL.nome.patchValue(resSilp.nome);
          this.fL.comuneDom.patchValue(resSilp.comuneDomicilio);
          this.cfSelected = null;
          this.alertMessageService.setSuccessSingleMsg("Salvataggio del lavoratore nell'anagrafica regionale eseguito con successo.");
        }
      }
    }catch(e){
      if (e.error && e.error.length > 0) {
        this.alertMessageService.setErrorMsg(e.error);
      } else {
        this.alertMessageService.setSingleErrorMsg(e);
      }
    }finally{
      this.utilitiesService.hideSpinner();
    }
  }

  async onClickOpenModalInserisciLavoratore(){
    this.alertMessageService.emptyMessages();
    
    try{
      const cfLavoratoreSilpSalvato = await this.promptModalService.openModalAnagraficaLavoratore('ins',this.comunicazione);
      this.alertMessageService.emptyMessages();
    
        if(!Utils.isNullOrUndefinedOrCampoVuoto(cfLavoratoreSilpSalvato)){
            this.onClickFindLavoratore(cfLavoratoreSilpSalvato);
            this.alertMessageService.setSuccessSingleMsg("Salvataggio del lavoratore nell'anagrafica regionale eseguito con successo.");
            this.cfSelected = cfLavoratoreSilpSalvato;
          }
        
    }catch(e){
      if (e.error && e.error.length > 0) {
        this.alertMessageService.setErrorMsg(e.error);
      } else {
        this.alertMessageService.setSingleErrorMsg(e);
      }
    }finally{
      this.utilitiesService.hideSpinner();
    }
  }

}
