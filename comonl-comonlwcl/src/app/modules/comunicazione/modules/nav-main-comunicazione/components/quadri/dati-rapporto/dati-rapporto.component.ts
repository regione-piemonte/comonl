/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { LogService, UtilitiesService } from 'src/app/services';
import { CATEG_LAV_ASS_OBBL, CONSTANTS_MODE, CONTROL_STATE, PARAMETRO_COMONL_ABILITAZIONE_LAVORO_STAGIONALE, STATO_COMUNICAZIONE, TIPO_COMUNICAZIONE, TIPO_CONTRATTI, TIPO_ORARIO, TIPO_SOMMINISTRAZIONE, TIPO_TRACCIATO, TYPE_DECODIFICA_GENERICA } from 'src/app/constants';
import { TipologiaTirocinio } from 'src/app/modules/comonlapi/model/tipologiaTirocinio';
import { CategTirocinante } from 'src/app/modules/comonlapi/model/categTirocinante';
import { TipoContratti } from 'src/app/modules/comonlapi/model/tipoContratti';
import { TipoEntePromTirocinio } from 'src/app/modules/comonlapi/model/tipoEntePromTirocinio';
import { Cpi } from 'src/app/modules/comonlapi/model/cpi';
import { EntePrevidenziale } from 'src/app/modules/comonlapi/model/entePrevidenziale';
import { TipoOrario } from 'src/app/modules/comonlapi/model/tipoOrario';
import { CategLavAssObbl } from 'src/app/modules/comonlapi/model/categLavAssObbl';
import { TipoAttoL68 } from 'src/app/modules/comonlapi/model/tipoAttoL68';
import { DecodificaService } from 'src/app/modules/comonlapi/api/decodifica.service';
import { AdComponent } from '../../../models/ad-component';
import {
  Ccnl,
  CommonService,
  ComonlsParametri,
  Comunicazione,
  ComunicazioneService,
  DatiAzienda,
  DecodificaGenerica,
  Istat2001livello5,
  Lavoratore,
  LivelloRetribuzione,
  Rapporto,
  Ruolo,
  SilpService,
  TipoComunicazioneTu,
  WrapperComunicazione,
} from 'src/app/modules/comonlapi';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { Utils } from 'src/app/utils';
import { PersistenceComunicazioneWreapper } from '../../nav-main-comunicazione/nav-main-comunicazione.component';
import { marker } from '@biesbjerg/ngx-translate-extract-marker';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'comonl-dati-rapporto',
  templateUrl: './dati-rapporto.component.html',
  styleUrls: ['./dati-rapporto.component.scss'],
})
export class DatiRapportoComponent implements OnInit, AdComponent {

  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="dati-rapporto"]';

  @Input() data: any;
  @Output() datiForm = new EventEmitter<any>();
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();
  @Output() persistenceNotification = new EventEmitter<PersistenceComunicazioneWreapper>();
  @Output() setSaveStatus = new EventEmitter<boolean>();


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

  get AGGIORNAMENTO_MODE() {
    const nuovaComParams = this.routingParams;
    return nuovaComParams.mode === CONSTANTS_MODE.AGGIORNAMENTO;
  }

  get EDIT_MODE() {
    const nuovaComParams = this.routingParams;
    return nuovaComParams.mode === CONSTANTS_MODE.EDIT;
  }

  get IS_ASSUNZIONE() {
    return this.comunicazioneToSave.tipoComunicazione.id === TIPO_COMUNICAZIONE.ASSUNZIONE.ID;
  }

  get IS_CESSAZIONE() {
    return this.comunicazioneToSave.tipoComunicazione.id === TIPO_COMUNICAZIONE.CESSAZIONE.ID;
  }

  get IS_TIROCINIO() {
    const contratto: TipoContratti = this.f.tipoContratti.value as TipoContratti;
    if (contratto) {
      return contratto.codTipoContrattoMin === TIPO_CONTRATTI.TIROCINIO.COD_TIPO_CONTARTTO_MIN;
    }
    return false;
  }



  tipoContrattiPerView: string = null;
  entePrevidenzialePerView: string = null;
  tipoOrarioPerView: string = null;

  listaTipologiaTirocinio: TipologiaTirocinio[] = [];
  listaCategTirocinante: CategTirocinante[] = [];
  listaTipoRapporto: TipoContratti[] = [];
  listaTipiEntePromTirocinio: TipoEntePromTirocinio[] = [];
  listaCpiEntePromTirocinio: Cpi[] = [];
  listaEntiPrevidenziali: EntePrevidenziale[] = [];
  listaTipoOrario: TipoOrario[] = [];
  listaCategLavAssObbl: CategLavAssObbl[] = [];
  listaTipoAttoL68: TipoAttoL68[] = [];

  formDatiRapporto: FormGroup;
  showForm: boolean;
  comunicazione: Comunicazione;
  comunicazioneToSave: Comunicazione;
  routingParams;
  disableDtfineRapportoBtn: boolean = true;
  disableDtfinePeriodoFormBtn: boolean = true;
  obbligoDtFineRapporto: boolean;
  obbligocategLavAssObbl: boolean;
  obbligatorietaTriplaL68: boolean;

  flgSoggPromTirDisabled = false;
  abiliatazioneLavStagionale: ComonlsParametri;
  ruolo: Ruolo;
  codiciContratti: string[] = [];

  //SCADENZA DECODIFICA
  ccnlScaduto: boolean = false;
  livelloRetribuzioneScaduto: boolean = false;
  istatScaduto: boolean = false;

  get ENABLE_DT_FINE_RAPPORTO_AND_DT_FINE_PERIODO_FORMATIVO(): boolean{
    return this.IS_UNISOMM || (this.IS_UNILAV && (this.IS_ASSUNZIONE || this.IS_CESSAZIONE))
  }

  get f() {
    return this.formDatiRapporto.controls as any;
  }

  get conditionLegge68TipoEnable(): boolean {
    // console.log(this.f.categoriaLav.value);
    const categLavAssObbl: CategLavAssObbl = this.f.categLavAssObbl.value as CategLavAssObbl;
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
    return this.f.tipoAttoL68.value && this.f.tipoAttoL68.value !== '';
  }

  get conditionLegge68NumEnable(): boolean {
    // console.log(this.f.legge68tipo.value);
    return this.f.tipoAttoL68.value && this.f.tipoAttoL68.value !== '';
  }

  get conditionEnableableDtFineRapporto(): boolean {
    const contratto: TipoContratti = this.f.tipoContratti.value as TipoContratti;
    if (contratto) {
     return contratto.flgForma !== 'I' || (contratto.flgForma === 'I' && contratto.tipo === 'AP' && this.f.flgLavStagionale.value === 'S');
    }
    return false;
  }

  get IS_APPRENDISTATO() {
    const contratto: TipoContratti = this.f.tipoContratti.value as TipoContratti;
    if (contratto) {

    }
    return false;
  }

  get IS_ASSUNZIONE_UNILAV() {
    const tipoComunicazione = this.comunicazioneToSave.tipoComunicazione;
    const tipoTracciato = this.comunicazioneToSave.tipoTracciato;
    return tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID && tipoComunicazione.id === TIPO_COMUNICAZIONE.ASSUNZIONE.ID;
  }

  get IS_UNISOMM(): boolean {
    const tipoTracciato = this.comunicazioneToSave.tipoTracciato;
    return tipoTracciato.id === TIPO_TRACCIATO.UNISOMM.ID;
  }

  get IS_UNILAV(): boolean {
    const tipoTracciato = this.comunicazioneToSave.tipoTracciato;
    return tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID;
  }

  disabeleContrattoRipartito(codTipoContrattoMin: string): boolean {
    const elencoLavoratori: Lavoratore[] = this.comunicazioneToSave.lavoratori;
    // console.log('selected: ' + codTipoContrattoMin === TIPO_CONTRATTI.LAVORO_RIPARTITO.COD_TIPO_CONTARTTO_MIN && (elencoLavoratori && elencoLavoratori.length == 2));
    return codTipoContrattoMin === TIPO_CONTRATTI.LAVORO_RIPARTITO.COD_TIPO_CONTARTTO_MIN && (!elencoLavoratori || elencoLavoratori.length !== 2);
  }

  constructor(
    private logService: LogService,
    private decodificaService: DecodificaService,
    private utilitiesService: UtilitiesService,
    private formBuilder: FormBuilder,
    private promptModalService: PromptModalService,
    private alertMessageService: AlertMessageService,
    private silpService: SilpService,
    private comunicazioneService: ComunicazioneService,
    private commonService: CommonService,
    private translateService: TranslateService
  ) {
  }

  async ngOnInit() {
    await this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    this.utilitiesService.scrollTo(DatiRapportoComponent.SCROLL_TARGET);
    this.routingParams = this.data.routingParams;
    this.comunicazione = this.data.comunicazione;
    this.comunicazioneToSave = this.data.comunicazioneToSave;
    this.ruolo = this.data.ruolo;
    const tipoComunicazione = this.comunicazioneToSave.tipoComunicazione.id;
    const tipoTracciato = this.comunicazioneToSave.tipoTracciato.id;
    const tipoSommin = this.comunicazioneToSave.tipoSomministrazione ? this.comunicazioneToSave.tipoSomministrazione.id : null;
    this.initForm();
    try {
      const [
        tipoTirocinio,
        categTirocinante,
        tipoEntePromTirocinio,
        cpiEntePromTirocinio,
        entePrevidenziale,
        tipoOrario,
        categLavAssObbl,
        tipoAttoL68,
        abiliatazioneLavStagionale
      ] = await Promise.all([
        this.decodificaService.getTipologiaTirocinio().toPromise(),
        this.decodificaService.getCategTirocinante().toPromise(),
        this.decodificaService.getTipoEntePromTirocinio().toPromise(),
        this.decodificaService.getCpiEntePromTirocinio().toPromise(),
        this.decodificaService.getEntePrevidenziale().toPromise(),
        this.decodificaService.getTipoOrario().toPromise(),
        this.decodificaService.getCategLavoratoreAssObbl().toPromise(),
        this.decodificaService.getTipoAttoL68().toPromise(),
        this.commonService.getParametroByDescrizione(PARAMETRO_COMONL_ABILITAZIONE_LAVORO_STAGIONALE).toPromise()
      ]);
      this.listaTipologiaTirocinio = tipoTirocinio;
      this.listaCategTirocinante = categTirocinante;
      this.listaTipiEntePromTirocinio = tipoEntePromTirocinio;
      this.listaCpiEntePromTirocinio = cpiEntePromTirocinio;
      this.listaEntiPrevidenziali = entePrevidenziale;
      this.listaTipoOrario = tipoOrario;
      this.listaCategLavAssObbl = categLavAssObbl;
      this.listaTipoAttoL68 = tipoAttoL68;
      this.abiliatazioneLavStagionale = abiliatazioneLavStagionale;
      this.codiciContratti = this.abiliatazioneLavStagionale.valoreParametro.split(';');
      if(this.INS_MODE || this.EDIT_MODE || this.AGGIORNAMENTO_MODE || this.RETTIFICA_MODE){
        const tipoAttoL68Convenzione: TipoAttoL68 = this.listaTipoAttoL68.find(el => el.id === "C");
        const index: number = this.listaTipoAttoL68.indexOf(tipoAttoL68Convenzione);
        if(index > -1){
          this.listaTipoAttoL68.splice(index,1);
        }
      }
      if (
        tipoSommin === TIPO_SOMMINISTRAZIONE.MISSIONE.ID &&
        tipoTracciato === TIPO_TRACCIATO.UNISOMM.ID &&
        tipoComunicazione === TIPO_COMUNICAZIONE.PROROGA.ID
      ) {
        this.listaTipoRapporto = await this.decodificaService.getTipoContrattiByTracciatoAndTipoComunicazione(TIPO_TRACCIATO.UNISOMM.ID, TIPO_COMUNICAZIONE.ASSUNZIONE.ID).toPromise();
      } else {
        this.listaTipoRapporto = await this.decodificaService.getTipoContrattiByTracciatoAndTipoComunicazione(tipoTracciato, tipoComunicazione).toPromise();
      }

      this.patchValueInForm(this.comunicazioneToSave);
      this.showForm = true;
      this.setFormMode();
      this.setInquadramentoMode(false);

      // ORDINA LA LISTA IN BASE ALLA DESCRIZIONE
      this.listaCpiEntePromTirocinio.sort((a, b) => a.dsComTCpi.localeCompare(b.dsComTCpi));
      this.listaCpiEntePromTirocinio = this.listaCpiEntePromTirocinio.filter( el => {
        return el.id !== 1;
      });

      if (this.comunicazioneToSave.tipoComunicazioneTu.codTipoComunicazioneMin === '06') {
        this.utilitiesService.changeControlState(
          this.f.flgTirocinio,
          CONTROL_STATE.DISABLE,
          true
        );
      }
      this.checkDecodificheScadute(this.comunicazioneToSave.rapporto);
    } catch (e) {
      this.alertMessageService.setSingleErrorMsg(e);
    } finally {
      await this.utilitiesService.hideSpinner();
    }
  }
  show() {
    console.log(this.f.tipoRapporto.value);
  }
  private initForm() {
    this.formDatiRapporto = new FormGroup({
      id: new FormControl(),
      dtInizioRapporto: new FormControl(null, Validators.required), // UNISOMM E UNILAV
      tipoContratti: new FormControl(null, Validators.required), // UNISOMM E UNILAV
      dtFineRapporto: new FormControl(null),
      dtFinePeriodoFormativo: new FormControl(null),
      flgLavStagionale: new FormControl('N'),
      flgTirocinio: new FormControl('N'),
      tipologiaTirocinio: new FormControl(null),
      categTirocinante: new FormControl(null),
      tipoEntePromTirocinio: new FormControl(null),
      cpi: new FormControl(null),
      cfSoggPromotoreTirocinio: new FormControl(null),
      dsSoggPromTirocinio: new FormControl(null),
      ccnl: new FormGroup({ // SOLO UNILAV
        id: new FormControl(),
        codCcnlMin: new FormControl(null),
        dsCcnl: new FormControl(null),
      }),
      livelloRetribuzione: this.formBuilder.group({ // SOLO UNILAV
        id: new FormControl(),
        codLivello: new FormControl(null),
        desLivello: new FormControl(null),
      }),
      retribuzioneCompenso: new FormControl(null),
      istatLivello5: new FormGroup({ // SOLO UNILAV
        id: new FormControl(),
        codIstat2001livello5Min: new FormControl(null),
        dsComIstat2001livello5: new FormControl(null),
      }),
      flgAssunzioneObbligatoria: new FormControl(null),
      patInail: new FormControl(null),
      entePrevidenziale: new FormControl(null, Validators.required), // UNISOMM E UNILAV
      codiceEntePrev: new FormControl(null),
      tipoOrario: new FormControl(null),
      numOreSettMed: new FormControl(null),
      categLavAssObbl: new FormControl(null),
      assObblAltri: new FormControl(null),
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
      flgSocioLavoratore: new FormControl('N'),
      flgLavoroAgricoltura: new FormControl('N'),
      giornateLavPreviste: new FormControl(null),
      tipoLavorazione: new FormControl(null),
      flgLavInMobilita: new FormControl('N', Validators.required), // UNISOMM E UNILAV
      numMatricolaLav: new FormControl(), // UNISOMM
      numIndennitaSomm: new FormControl() // UNISOMM
    });
    if (this.comunicazioneToSave.tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID) {
      this.f.flgLavStagionale.setValue('N');
      this.f.flgLavStagionale.setValidators(Validators.required);

      this.f.flgSocioLavoratore.setValidators(Validators.required);
      this.f.flgLavoroAgricoltura.setValidators(Validators.required);



      this.f.flgAssunzioneObbligatoria.setValue('N');
      this.f.flgAssunzioneObbligatoria.setValidators(Validators.required);
      this.f.patInail.setValidators(Validators.required,Validators.minLength(8));
      this.f.tipoOrario.setValidators(Validators.required);
      if (this.IS_ASSUNZIONE_UNILAV) {
        this.f.flgTirocinio.setValue('N');
        this.f.flgTirocinio.setValidators(Validators.required);
        this.f.flgTirocinio.updateValueAndValidity();
      }
      this.f.flgLavStagionale.updateValueAndValidity();
      this.f.flgAssunzioneObbligatoria.updateValueAndValidity();
      this.f.patInail.updateValueAndValidity();
      this.f.tipoOrario.updateValueAndValidity();

      this.f.flgSocioLavoratore.updateValueAndValidity();
      this.f.flgLavoroAgricoltura.updateValueAndValidity();

      if (this.AGGIORNAMENTO_MODE) {
        this.utilitiesService.changeControlState(
          this.f.dtInizioRapporto,
          CONTROL_STATE.DISABLE,
          false
        );
      }

      // sezione decodifiche unilav
      this.f.ccnl.get('codCcnlMin').setValidators(
          Validators.required,
          Validators.minLength(2)
      );
      this.f.ccnl.get('dsCcnl').setValidators(
        Validators.required,
        Validators.minLength(3)
      );
      this.f.livelloRetribuzione.get('codLivello').setValidators(
        Validators.required,
          Validators.minLength(2)
      );
      this.f.livelloRetribuzione.get('desLivello').setValidators(
        Validators.required,
          Validators.minLength(1)
      );
      this.f.istatLivello5.get('codIstat2001livello5Min').setValidators(
        Validators.required,
        Validators.minLength(3)
      );
      this.f.istatLivello5.get('dsComIstat2001livello5').setValidators(
        Validators.required,
        Validators.minLength(3)
      );
    } else if (this.comunicazioneToSave.tipoTracciato.id === TIPO_TRACCIATO.UNISOMM.ID) {
      this.f.numMatricolaLav.setValidators(Validators.required);
      this.f.numMatricolaLav.updateValueAndValidity();
    }

  }


  async onSubmit() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const formRapporto = this.formDatiRapporto.getRawValue() as Rapporto;
    if (!this.comunicazioneToSave.rapporto) {
      this.comunicazioneToSave.rapporto = new Object() as Rapporto;
    }
    this.comunicazioneToSave.rapporto.id =  formRapporto.id;
    this.comunicazioneToSave.rapporto.dtInizioRapporto = formRapporto.dtInizioRapporto;
    this.comunicazioneToSave.rapporto.tipoContratti = formRapporto.tipoContratti;

    this.comunicazioneToSave.rapporto.dtFineRapporto = formRapporto.dtFineRapporto;
    this.comunicazioneToSave.rapporto.dtFinePeriodoFormativo = formRapporto.dtFinePeriodoFormativo;

    this.comunicazioneToSave.rapporto.flgLavStagionale = formRapporto.flgLavStagionale;
    this.comunicazioneToSave.rapporto.flgTirocinio = formRapporto.flgTirocinio;
    this.comunicazioneToSave.rapporto.tipologiaTirocinio = formRapporto.tipologiaTirocinio;
    this.comunicazioneToSave.rapporto.categTirocinante = formRapporto.categTirocinante;
    this.comunicazioneToSave.rapporto.tipoEntePromTirocinio = formRapporto.tipoEntePromTirocinio;
    this.comunicazioneToSave.rapporto.cpi = formRapporto.cpi;
    this.comunicazioneToSave.rapporto.cfSoggPromotoreTirocinio = formRapporto.cfSoggPromotoreTirocinio;
    this.comunicazioneToSave.rapporto.dsSoggPromTirocinio = formRapporto.dsSoggPromTirocinio;
    this.comunicazioneToSave.rapporto.ccnl = formRapporto.ccnl;
    this.comunicazioneToSave.rapporto.livelloRetribuzione = formRapporto.livelloRetribuzione;
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
    this.comunicazioneToSave.rapporto.tipoLavorazione = formRapporto.tipoLavorazione;
    this.comunicazioneToSave.rapporto.flgLavInMobilita = formRapporto.flgLavInMobilita;
    if (this.comunicazioneToSave.tipoTracciato.id === TIPO_TRACCIATO.UNISOMM.ID) {
      this.comunicazioneToSave.rapporto.numMatricolaLav = formRapporto.numMatricolaLav;
      this.comunicazioneToSave.rapporto.numIndennitaSomm = formRapporto.numIndennitaSomm;
    }
    if (!this.IS_ASSUNZIONE_UNILAV || !this.comunicazioneToSave.rapporto.flgTirocinio) {
      this.comunicazioneToSave.rapporto.flgTirocinio = 'N';
     }
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

    this.comunicazioneToSave.flgMultiLav = 'N';

    if (this.comunicazioneToSave.lavoratori.length > 1 ) {
      this.comunicazioneToSave.flgMultiLav = 'S';

    }

    if(!this.RETTIFICA_MODE && !this.ANNULLO_MODE){
      if(this.IS_ASSUNZIONE_UNILAV && this.comunicazioneToSave.rapporto.flgTirocinio === 'S'){
        this.comunicazioneToSave.tipoComunicazioneTu = new Object() as TipoComunicazioneTu;
        this.comunicazioneToSave.tipoComunicazioneTu.id = 5;
        this.comunicazioneToSave.tipoComunicazioneTu.codTipoComunicazioneMin = "05"
        this.comunicazioneToSave.codiceComunRegPrec = null;
        this.comunicazioneToSave.annoProtUrgenza = null;
        this.comunicazioneToSave.numProtComUrgenza = null;
        this.comunicazioneToSave.provincia = null;
      }else{
        if(this.comunicazioneToSave.tipoComunicazioneTu.id === 5){
          this.comunicazioneToSave.tipoComunicazioneTu = new Object() as TipoComunicazioneTu;
          this.comunicazioneToSave.tipoComunicazioneTu.id = 1;
          this.comunicazioneToSave.tipoComunicazioneTu.codTipoComunicazioneMin = "01"
        }
      }
    }


    if (formRapporto.tipoContratti.codTipoContrattoMin === TIPO_CONTRATTI.LAVORO_RIPARTITO.COD_TIPO_CONTARTTO_MIN) {
      this.comunicazioneToSave.flgMultiLav = 'N';

      const lavoratori: Lavoratore[] = this.comunicazioneToSave.lavoratori;
      if (lavoratori && lavoratori.length === 2) {
        this.comunicazioneToSave.lavoratoreCoObbligato = lavoratori[1];
        this.comunicazioneToSave.lavoratori.splice(1, 1);
      }
    }

    if(this.IS_ASSUNZIONE){
      this.comunicazioneToSave.dataRiferimento = this.comunicazioneToSave.rapporto.dtInizioRapporto;
      this.comunicazioneToSave.rapporto.dtEvento = this.comunicazioneToSave.rapporto.dtInizioRapporto;
    }

    const id = this.comunicazioneToSave.id;
    if (!id) {
      this.comunicazioneToSave.statoComunicazione = {
        id: STATO_COMUNICAZIONE.INSERITA.ID
      };
      this.comunicazioneToSave.rapporto.comunicazione = null;
    }
    if (this.AGGIORNAMENTO_MODE && !this.comunicazioneToSave.id) {
      this.comunicazioneToSave.flgCurrentRecord = 'S';
    }
    let wrapperComunicazione: WrapperComunicazione = {
      ruolo: this.ruolo,
      comunicazione: this.comunicazioneToSave
    };
    let res;
    try {
      if(!id){
        if (this.RETTIFICA_MODE && !id) {
          wrapperComunicazione.comunicazione.rapporto.id = null;
          res = await this.comunicazioneService.rettificaComunicazione(wrapperComunicazione).toPromise();
        } else if (this.ANNULLO_MODE && !id) {
          wrapperComunicazione.comunicazione.rapporto.id = null;
          res = await this.comunicazioneService.annullaComunicazione(wrapperComunicazione).toPromise();
        }else{
          res = await this.comunicazioneService.postComunicazione(wrapperComunicazione).toPromise();
        }
      }else {
        res = await this.comunicazioneService.putComunicazione(wrapperComunicazione).toPromise();
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
           ricalcoloQuadri: true,
           switchEditMode: this.INS_MODE,
           success: true
         };
    this.persistenceNotification.emit(persistenceComunicazioneWreapper);
  }

  setControlStateLegge68Tipo() {
    this.f.tipoAttoL68.clearValidators();
    if (!this.VIEW_MODE) {
      const flgEnable = this.conditionLegge68TipoEnable;
      this.utilitiesService.changeControlState(
        this.f.tipoAttoL68,
        flgEnable ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE,
        !flgEnable
      );
      this.utilitiesService.changeControlState(
        this.f.dtLegge68,
        flgEnable ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE,
        !flgEnable
      );
      this.utilitiesService.changeControlState(
        this.f.numeroAttoLegge68,
        flgEnable ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE,
        !flgEnable
      );
      if (flgEnable) {
        this.f.tipoAttoL68.setValidators(Validators.required);
      }
    }
    this.f.tipoAttoL68.updateValueAndValidity();
  }

  private patchValueInForm(comunicazione: Comunicazione) {
    const rapporto = comunicazione.rapporto;
    console.log('rapporto: ' + JSON.stringify(rapporto));
    if (rapporto) {
      this.formDatiRapporto.patchValue(rapporto);
      if (rapporto.tipoContratti) {
        const tipoContratti = this.listaTipoRapporto.find(el => {
          return el.id === rapporto.tipoContratti.id;
        });
        this.f.tipoContratti.patchValue(tipoContratti);
        if (this.VIEW_MODE) {
          this.tipoContrattiPerView = rapporto.tipoContratti.dsComTTipoContratto;
        }
      }
      if (rapporto.entePrevidenziale) {
        const entePrevidenziale = this.listaEntiPrevidenziali.find(el => {
          return el.id === rapporto.entePrevidenziale.id;
        });
        this.f.entePrevidenziale.patchValue(entePrevidenziale);
        if (this.VIEW_MODE) {
          this.entePrevidenzialePerView = rapporto.entePrevidenziale.dsComTEntePrevidenziale;
        }
      }
      if (rapporto.tipoOrario) {
        const tipoOrario = this.listaTipoOrario.find(el => {
          return el.id === rapporto.tipoOrario.id;
        });
        this.f.tipoOrario.patchValue(tipoOrario);
        if (this.VIEW_MODE) {
          this.tipoOrarioPerView = rapporto.tipoOrario.dsComTTipoOrario;
        }
      }
      if (rapporto.categLavAssObbl) {
        // **********************qui
        const categLavAssObbl = this.listaCategLavAssObbl.find(el => {
          return el.id === rapporto.categLavAssObbl.id;
        });
        this.f.categLavAssObbl.patchValue(categLavAssObbl);
      }
      if (rapporto.tipoAttoL68) {
        const tipoAttoL68 = this.listaTipoAttoL68.find(el => {
          return el.id === rapporto.tipoAttoL68.id;
        });
        this.f.tipoAttoL68.patchValue(tipoAttoL68);
      }

      if (rapporto.tipoContratti && rapporto.tipoContratti.id === TIPO_CONTRATTI.TIROCINIO.ID) {

      if (rapporto.tipologiaTirocinio) {
        // ****************QUA************************ */
        const tipologiaTirocinio = this.listaTipologiaTirocinio.find(el => {
          return el.id === rapporto.tipologiaTirocinio.id;
         });
        this.f.tipologiaTirocinio.patchValue(tipologiaTirocinio);
      }

      if (rapporto.categTirocinante) {
        const categTirocinante = this.listaCategTirocinante.find(el => {
          return el.id === rapporto.categTirocinante.id;
        });
        this.f.categTirocinante.patchValue(categTirocinante);
      }
      if (rapporto.tipoEntePromTirocinio) {
        const tipoEntePromTirocinio = this.listaTipiEntePromTirocinio.find( el => {
          return el.id === rapporto.tipoEntePromTirocinio.id;
        });
        this.f.tipoEntePromTirocinio.patchValue(tipoEntePromTirocinio);
      }
      if (rapporto.cpi) {
        const cpi = this.listaCpiEntePromTirocinio.find( el => {
          return el.id === rapporto.cpi.id;
        });
        this.f.cpi.patchValue(cpi);
      }
    }
      if (this.VIEW_MODE) {
        if (comunicazione && comunicazione.rapporto) {
          if (!comunicazione.rapporto.livelloRetribuzione) {
            const livelloRetribuzione: LivelloRetribuzione = {
              id: null,
              codLivello: null,
              desLivello: comunicazione.rapporto.livelloInquadramento
            };
            this.f.livelloRetribuzione.patchValue(livelloRetribuzione);
          }
        }
      }
  }
    console.log('dopo il patchvalue');
}

  private setFormMode() {
    if (this.VIEW_MODE) {
      this.formDatiRapporto[CONTROL_STATE.DISABLE]();
    } else {
      const tipoTracciato = this.comunicazioneToSave.tipoTracciato;
      if (tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID) {
        this.setFormControlsStateUnilav();
      } else if (tipoTracciato.id === TIPO_TRACCIATO.UNISOMM.ID) {
        this.setFormControlsStateUnisomm();
      }
    }
    if(this.AGGIORNAMENTO_MODE){
      this.formDatiRapporto.controls.dtInizioRapporto[CONTROL_STATE.DISABLE]();
    }
    if(this.ANNULLO_MODE){
      this.formDatiRapporto[CONTROL_STATE.DISABLE]();
    }
    if(this.comunicazione && this.comunicazione.rapporto){
      this.f.dsSoggPromTirocinio.setValue(this.comunicazioneToSave.rapporto.dsSoggPromTirocinio);
    }
  }

  setFormControlsStateUnilav() {
    this.setStateFormControlsDipendentidaTipoContratto();
    this.setControlsStatePerCpi();
    this.setDsSoggPromTirocinio();
    this.setControlStatenumOreSettMed();
    this.setControlStateAltriDati();
    this.setControlStateLegge68Tipo();
  }

  setFormControlsStateUnisomm() {
    this.setStateFormControlsDipendentidaTipoContratto();
  }

  /*PAUL*/
  setbbligatorieta() {
    this.setControlStateLeggeData();
    this.setControlStateLeggeNumAtto();
  }

  setControlStateLeggeData() {
    const flgEnable = this.conditionLegge68DataEnable;
    this.utilitiesService.changeControlState(
      this.f.tipoAttoL68,
      flgEnable ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE,
      !flgEnable
    );
  }

  setControlStateLeggeNumAtto() {
    const flgEnable = this.conditionLegge68NumEnable;
    this.utilitiesService.changeControlState(
      this.f.tipoAttoL68,
      flgEnable ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE,
      !flgEnable
    );
  }

  /*FINE PAUL*/

  async onClickfindCcnl() {
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
      if (e.error && e.error.length > 0) {
        this.alertMessageService.setWarningMsg(e.error);
      } else {
        if( e!= 0)
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
        if( e!= 0)
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
          dsComIstat2001livello5: docodificaFinded.dsDecodifica
        };
        this.f.istatLivello5.patchValue(istatLivello5);
      }
    } catch (e) {
      if (e.error && e.error.length > 0) {
        this.alertMessageService.setWarningMsg(e.error);
      } else {
        if( e!= 0)
          this.alertMessageService.setSingleErrorMsg(e);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  setStateFormControlsDipendentidaTipoContratto() {
    this.alertMessageService.emptyMessages();
    console.log('tipo comunicazione: ' + JSON.stringify(this.comunicazioneToSave.tipoComunicazione.dsComTTipoComunicazione));
    if (this.ENABLE_DT_FINE_RAPPORTO_AND_DT_FINE_PERIODO_FORMATIVO) {
      this.setStatedtFineRapporto();
      this.setStateDtFinePeriodoFormativo();
    }
    this.setStateFlgLvaoroStagionale();
    this.setControlStateDatiTirocinio();
  }

  setStateCodiceEntePrevidenziale(ente: EntePrevidenziale) {
    if (ente.id === 29) {
      this.utilitiesService.changeControlState(
        this.f.codiceEntePrev,
        CONTROL_STATE.DISABLE,
        true
      );
    }
  }

  private setStateDtFinePeriodoFormativo() {
    this.f.dtFinePeriodoFormativo.clearValidators();
    let state = CONTROL_STATE.DISABLE;
    let flgCleanValue = true;
    const contratto: TipoContratti = this.f.tipoContratti.value;
    if (contratto && contratto.tipo === 'AP') {
      this.disableDtfinePeriodoFormBtn = false;
      this.f.dtFinePeriodoFormativo.setValidators(Validators.required);
      state = CONTROL_STATE.ENABLE;
      flgCleanValue = false;
    } else {
      this.disableDtfinePeriodoFormBtn = true;
    }
    this.utilitiesService.changeControlState(
      this.f.dtFinePeriodoFormativo,
      state,
      flgCleanValue
    );
    this.f.dtFinePeriodoFormativo.updateValueAndValidity();
  }

  setStatedtFineRapporto() {
    if (this.ENABLE_DT_FINE_RAPPORTO_AND_DT_FINE_PERIODO_FORMATIVO) {

      this.f.dtFineRapporto.clearValidators();
      let state = CONTROL_STATE.DISABLE;
      let flgCleanValue = true;
      const contratto: TipoContratti = this.f.tipoContratti.value;
      if (this.conditionEnableableDtFineRapporto) {
      this.disableDtfineRapportoBtn = false;
      if (
        contratto.flgForma === 'D' ||
        (this.IS_UNILAV && contratto.flgForma === 'E' && this.f.flgLavStagionale.value === 'S') ||
        (this.IS_UNILAV && contratto.flgForma === 'I' && contratto.tipo === 'AP' && this.f.flgLavStagionale.value === 'S')
      ) {
        this.f.dtFineRapporto.setValidators(Validators.required);
        this.obbligoDtFineRapporto = true;
      } else {
        this.obbligoDtFineRapporto = false;
      }
      state = CONTROL_STATE.ENABLE;
      flgCleanValue = false;
    } else {
      this.disableDtfineRapportoBtn = true;
      this.obbligoDtFineRapporto = false;
    }
      this.utilitiesService.changeControlState(
      this.f.dtFineRapporto,
      state,
      flgCleanValue
    );
      this.f.dtFineRapporto.updateValueAndValidity();



    }
  }

  // private setStateFlgLvaoroStagionale() {
  //   let state = CONTROL_STATE.ENABLE;
  //   if (this.IS_ASSUNZIONE) {
  //     const contratto: TipoContratti = this.f.tipoContratti.value;
  //     if (contratto && contratto.flgForma === 'I') {
  //       state = CONTROL_STATE.DISABLE;
  //     }
  //   }
  //   if(state === CONTROL_STATE.ENABLE)
  //   this.utilitiesService.changeControlState(
  //   this.f.flgLavStagionale,
  //   state,
  //   state === CONTROL_STATE.DISABLE ? true : false
  //   );
  // }


  private setStateFlgLvaoroStagionale() {
    const contratto: TipoContratti = this.f.tipoContratti.value;
    if(contratto && contratto.id){
      const abiltaLavStagionale = this.codiciContratti.find(el => {return contratto.codTipoContrattoMin === el})
    if (abiltaLavStagionale) {
      this.f.flgLavStagionale[CONTROL_STATE.ENABLE]();
    } else {
      this.f.flgLavStagionale.setValue('N');
      this.f.flgLavStagionale[CONTROL_STATE.DISABLE]();
    }
    }

  }

  private setControlStateDatiTirocinio() {
    this.f.tipologiaTirocinio.clearValidators();
    this.f.categTirocinante.clearValidators();
    this.f.tipoEntePromTirocinio.clearValidators();
    this.f.cpi.clearValidators();
    this.f.cfSoggPromotoreTirocinio.clearValidators();
    this.f.dsSoggPromTirocinio.clearValidators();
    if (this.IS_TIROCINIO) {
      this.f.tipologiaTirocinio.setValidators(Validators.required);
      this.f.categTirocinante.setValidators(Validators.required);
      this.f.tipoEntePromTirocinio.setValidators(Validators.required);
      this.f.cpi.setValidators(Validators.required);
      this.f.cfSoggPromotoreTirocinio.setValidators(Validators.required);
      this.f.dsSoggPromTirocinio.setValidators([Validators.required,Validators.maxLength(100)]);
      if(this.IS_ASSUNZIONE_UNILAV){
        this.utilitiesService.changeControlState(
          this.f.flgTirocinio,
          CONTROL_STATE.DISABLE,
          false
        );
        this.f.flgTirocinio.patchValue('N');
      }
    } else {
      this.f.tipologiaTirocinio.reset();
      this.f.categTirocinante.reset();
      this.f.tipoEntePromTirocinio.reset();
      this.f.cpi.reset();
      this.f.dsSoggPromTirocinio.reset();
      this.utilitiesService.changeControlState(
        this.f.dsSoggPromTirocinio,
        CONTROL_STATE.ENABLE,
        true
      );
      if(this.IS_ASSUNZIONE_UNILAV){
        this.utilitiesService.changeControlState(
          this.f.flgTirocinio,
          CONTROL_STATE.ENABLE,
          false
        );
        if(this.INS_MODE)
          this.f.flgTirocinio.patchValue('N');
      }
    }

    this.f.tipologiaTirocinio.updateValueAndValidity();
    this.f.categTirocinante.updateValueAndValidity();
    this.f.tipoEntePromTirocinio.updateValueAndValidity();
    this.f.cpi.updateValueAndValidity();
    this.f.cfSoggPromotoreTirocinio.updateValueAndValidity();
    this.f.dsSoggPromTirocinio.updateValueAndValidity();

  }

  async onClickFindSoggPromotoreTirocinio() {
    this.utilitiesService.showSpinner();
    const cf = this.f.cfSoggPromotoreTirocinio.value;
    if(
      Utils.isNullOrUndefinedOrCampoVuoto(cf) || cf.length < 11
    ){
      this.alertMessageService.setSingleErrorMsg('Attenzione: il codice fiscale dell\'ente promotore tirocinio non è valido');
      this.utilitiesService.hideSpinner();
      return;
    }
    try {
      const res: DatiAzienda = await this.silpService.getAziendaSilp(cf).toPromise();
      if (res) {
        this.f.dsSoggPromTirocinio.setValue(res.denominazioneDatoreLavoro);
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

  setControlsStatePerCpi() {
    const entePromTirocinio = this.f.tipoEntePromTirocinio.value;
    let stateCpi = CONTROL_STATE.DISABLE;
    let stateDsSogg = CONTROL_STATE.ENABLE;
    // console.log(JSON.stringify(entePromTirocinio));
    this.flgSoggPromTirDisabled = false;
    if (entePromTirocinio) {
      if (entePromTirocinio.id === 1) { // ID PER CENTRO PER L'IMPIEGO
        stateCpi = CONTROL_STATE.ENABLE;
        stateDsSogg = CONTROL_STATE.DISABLE;
        this.flgSoggPromTirDisabled = true;
      } else {
        this.flgSoggPromTirDisabled = false;
      }
    }
    this.utilitiesService.changeControlState(
      this.f.cpi,
      stateCpi,
      stateCpi === CONTROL_STATE.DISABLE ? true : false
    );
    this.utilitiesService.changeControlState(
      this.f.dsSoggPromTirocinio,
      stateDsSogg,
      stateDsSogg === CONTROL_STATE.ENABLE ? true : false
    );
  }

  setDsSoggPromTirocinio() {
    const cpi: Cpi = this.f.cpi.value;
    if (cpi) {
      const str = 'CPI ' + cpi.dsComTCpi;
      this.f.dsSoggPromTirocinio.setValue(str);
    }
  }

  setControlStatenumOreSettMed() {

    let state = CONTROL_STATE.DISABLE;
    const tipoOrario: TipoOrario = this.f.tipoOrario.value;
    this.f.numOreSettMed.clearValidators();
    if (
      tipoOrario && (
      tipoOrario.codTipoorarioMin === TIPO_ORARIO.TEMPO_PARZIALE_ORIZZONTALE.COD_TIPO_ORARIO_MIN ||
      tipoOrario.codTipoorarioMin === TIPO_ORARIO.TEMPO_PARZIALE_VERTICALE.COD_TIPO_ORARIO_MIN ||
      tipoOrario.codTipoorarioMin === TIPO_ORARIO.TEMPO_PARZIALE_MISTO.COD_TIPO_ORARIO_MIN )
    ) {
      state = CONTROL_STATE.ENABLE;
      this.f.numOreSettMed.setValidators(Validators.required);
    }
    this.utilitiesService.changeControlState(
      this.f.numOreSettMed,
      state,
      state === CONTROL_STATE.DISABLE ? true : false
    );
    this.f.numOreSettMed.updateValueAndValidity();
  }

  setControlStateAltriDati() {
    if (!this.VIEW_MODE) {
      const flgAssunzioneObbligatoria = this.f.flgAssunzioneObbligatoria.value;
      let state = CONTROL_STATE.DISABLE;
      this.f.categLavAssObbl.clearValidators();
      if (flgAssunzioneObbligatoria === 'S') {
        state = CONTROL_STATE.ENABLE;
        this.f.categLavAssObbl.setValidators(Validators.required);
        this.f.flgLavInMobilita.setValidators(Validators.required);
        this.f.flgSocioLavoratore.setValidators(Validators.required);
        this.obbligocategLavAssObbl = true;
      } else {
        this.obbligocategLavAssObbl = false;
      }
      this.f.categLavAssObbl.updateValueAndValidity();
      this.f.flgLavInMobilita.updateValueAndValidity();
      this.utilitiesService.changeControlState(
        this.f.categLavAssObbl,
        state,
        state === CONTROL_STATE.DISABLE ? true : false
      );
      this.setControlStateLegge68Tipo()
      this.setGiornateLavPrev();
    }
  }

  setbligatrietaTriplaL68() {
    const tipoAttoL68 = this.f.tipoAttoL68.value;
    const dtLegge68 = this.f.dtLegge68.value;
    const numeroAttoLegge68 = this.f.numeroAttoLegge68.value;
    this.f.tipoAttoL68.clearValidators();
    this.f.dtLegge68.clearValidators();
    this.f.numeroAttoLegge68.clearValidators();
    if (
        tipoAttoL68 ||
        !Utils.isNullOrUndefinedOrCampoVuoto(dtLegge68) ||
        !Utils.isNullOrUndefinedOrCampoVuoto(numeroAttoLegge68)
    ) {
      this.obbligatorietaTriplaL68 = true;
      this.f.tipoAttoL68.setValidators(Validators.required);
      this.f.dtLegge68.setValidators(Validators.required);
      this.f.numeroAttoLegge68.setValidators(Validators.required);
    } else {
      this.obbligatorietaTriplaL68 = false;
    }
    this.f.tipoAttoL68.updateValueAndValidity();
    this.f.dtLegge68.updateValueAndValidity();
    this.f.numeroAttoLegge68.updateValueAndValidity();
  }

  setGiornateLavPrev() {
    const flgLavoroAgricoltura = this.f.flgLavoroAgricoltura.value;
    const state = flgLavoroAgricoltura === 'S' ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE;
    this.utilitiesService.changeControlState(
        this.f.giornateLavPreviste,
        state,
        state === CONTROL_STATE.DISABLE ? true : false
      );
    this.utilitiesService.changeControlState(
        this.f.tipoLavorazione,
        state,
        state === CONTROL_STATE.DISABLE ? true : false
      );
  }

  onClickReset() {
    this.alertMessageService.emptyMessages();
    // this.comunicazioneToSave = Utils.clone(this.comunicazione);
    // this.comunicazioneToSave = Utils.clone(this.comunicazioneToSave);
    const comunicazioneToSave = Utils.clone(this.comunicazioneToSave);
    this.formDatiRapporto.reset();
    this.patchValueInForm(comunicazioneToSave);
    this.setFormMode();
  }


  private setInquadramentoMode(clean: boolean) {
    if (this.VIEW_MODE || this.ANNULLO_MODE) {
      if(this.ccnlScaduto){
        this.f.livelloRetribuzione[CONTROL_STATE.ENABLE]();
      }else{
        this.f.livelloRetribuzione[CONTROL_STATE.DISABLE]();
      }
    } else if (this.formDatiRapporto.controls.ccnl.get('id').value) {
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

      let dtFineTipoContratti: Date;
      const tipoContratti: TipoContratti = rapporto.tipoContratti;
      if(tipoContratti && tipoContratti.dtFine){
        dtFineTipoContratti = new Date(tipoContratti.dtFine);
        dtFineTipoContratti.setHours(0,0,0,0);
      }

      if(dtFineTipoContratti && dtFineTipoContratti <= sysDate){
        this.utilitiesService.changeControlState(
          this.f.tipoContratti,
          CONTROL_STATE.ENABLE,
          true
        );
      }

      let dtFineEntePrevidenziale: Date;
      const entePrevidenziale: EntePrevidenziale = rapporto.entePrevidenziale;
      if(entePrevidenziale && entePrevidenziale.dtFine){
        dtFineEntePrevidenziale = new Date(entePrevidenziale.dtFine);
        dtFineEntePrevidenziale.setHours(0,0,0,0);
      }
      if(dtFineEntePrevidenziale && dtFineEntePrevidenziale <= sysDate){
        this.utilitiesService.changeControlState(
          this.f.entePrevidenziale,
          CONTROL_STATE.ENABLE,
          true
        );
      }
    }
    if(rapporto && this.IS_UNILAV && !(this.VIEW_MODE || this.INS_MODE)){
      const ccnl: Ccnl = rapporto.ccnl;
      const livelloRetribuzione: LivelloRetribuzione = rapporto.livelloRetribuzione;
      const istat: Istat2001livello5 = rapporto.istatLivello5;
      sysDate.setHours(0,0,0,0);
      let dtFineCcnl: Date;
      if(ccnl && ccnl.dtFine){
        dtFineCcnl = new Date(ccnl.dtFine);
        dtFineCcnl.setHours(0,0,0,0);
      }
      let dtFineLivelloRetribuzione: Date;
      if(livelloRetribuzione && livelloRetribuzione.dtFine){
        dtFineLivelloRetribuzione = new Date(livelloRetribuzione.dtFine);
        dtFineLivelloRetribuzione.setHours(0,0,0,0);
      }
      let dtFineIstat: Date;
      if(istat && istat.dtFine){
        dtFineIstat = new Date(istat.dtFine);
        dtFineIstat.setHours(0,0,0,0);
      }
      if(dtFineCcnl && dtFineCcnl <= sysDate){
        //il ccnl è scaduto

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
        this.livelloRetribuzioneScaduto = true;
        const state: string = rapporto.ccnl && rapporto.ccnl.id ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE
        this.utilitiesService.changeControlState(
          this.f.livelloRetribuzione,
          state,
          true
        )
      }
      if(!livelloRetribuzione){
        this.livelloRetribuzioneScaduto = true;
      }
      if(dtFineIstat && dtFineIstat <= sysDate){
        this.utilitiesService.changeControlState(
          this.f.istatLivello5,
          CONTROL_STATE.ENABLE,
          true
        );
        this.istatScaduto = true;
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

      let dtFinecategLavAssScaduto: Date;
      const categLavAssObbl = rapporto.categLavAssObbl;
      if(categLavAssObbl && categLavAssObbl.dtFine){
        dtFinecategLavAssScaduto = new Date(categLavAssObbl.dtFine);
        dtFinecategLavAssScaduto.setHours(0,0,0,0);
      }

      if(dtFinecategLavAssScaduto && dtFinecategLavAssScaduto <= sysDate){
        if(dtFineIstat && dtFineIstat <= sysDate){
          this.utilitiesService.changeControlState(
            this.f.categLavAssObbl,
            CONTROL_STATE.ENABLE,
            true
          );
        }
      }

      const retribuzioneCompenso: number = rapporto.retribuzioneCompenso;
      if(Utils.isNullOrUndefinedOrCampoVuoto(retribuzioneCompenso)){
        this.utilitiesService.changeControlState(
          this.f.retribuzioneCompenso,
          CONTROL_STATE.ENABLE,
          true
        );
      }

      if(this.IS_TIROCINIO){
        const tipologiaTirocinio: TipologiaTirocinio = rapporto.tipologiaTirocinio;
        let dtFineTipologiaTirocinio: Date;
        if(tipologiaTirocinio && tipologiaTirocinio.dtFine){
          dtFineTipologiaTirocinio = new Date(tipologiaTirocinio.dtFine);
          dtFineTipologiaTirocinio.setHours(0,0,0,0);
        }
        if(dtFineTipologiaTirocinio && dtFineTipologiaTirocinio <= sysDate){
          this.utilitiesService.changeControlState(
            this.f.tipologiaTirocinio,
            CONTROL_STATE.ENABLE,
            true
          );
        }
        const categTirocinante: CategTirocinante = rapporto.categTirocinante;
        let dtFineCategTirocinante: Date
        if(categTirocinante && categTirocinante.dtFine){
          dtFineCategTirocinante = new Date(dtFineCategTirocinante);
          dtFineCategTirocinante.setHours(0,0,0,0);
        }
        if(dtFineCategTirocinante && dtFineCategTirocinante <= sysDate){
          this.utilitiesService.changeControlState(
            this.f.categTirocinante,
            CONTROL_STATE.ENABLE,
            true
          );
        }
        const cpi: Cpi = rapporto.cpi;
        let dtFineCpi: Date;
        if(cpi && cpi.dtFine){
          dtFineCpi = new Date(cpi.dtFine);
          dtFineCpi.setHours(0,0,0,0);
        }
        if(dtFineCpi &&dtFineCpi <= sysDate){
          this.utilitiesService.changeControlState(
            this.f.cpi,
            CONTROL_STATE.ENABLE,
            true
          );
        }
      }

    }
  }


  async onClickDisplayWarning(){
    const title = this.translate(marker('SIDEBAR.DATI_RAPPORTO'));
    const message = this.translate(marker('MESSAGES.SYS-SYS-W-0001'));
    const pYes = this.translate(marker('APP.OK'));

    await this.promptModalService.openPrompt(title, message, pYes, "", 'warning');

  }

  private translate(key: string) {
    return this.translateService.instant(key);
  }

  findInvalidControls() {
    const controls = this.formDatiRapporto.controls;
    for (const name in controls) {
        if (controls[name].invalid) {
           console.log('invalid: ' + name);
        }
    }
  }

}
