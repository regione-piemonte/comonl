/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { DatiAzienda } from './../../../../../comonlapi/model/datiAzienda';
import { Component, ComponentFactoryResolver, ComponentRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NgbNavChangeEvent } from '@ng-bootstrap/ng-bootstrap';
import { LogService, UtilitiesService } from 'src/app/services';
import { AddQuadroDirective } from '../../directives/add-quadro.directive';
import { AdComponent } from '../../models/ad-component';
import { LoaderService, TIPO_QUADRO_COM } from '../../services/loader-service'; 
import { AdQuadro } from '../../models/ad-quadro';
import { Comunicazione, ComunicazioneService, TipoComunicazione,  TipoTracciato, LavoratoreSilpEspanso, Datore, Ruolo, TipoSomministrazione, SedeLavoro, TipoComunicazioneTu, RapportiLavoratoriSediInteressateVD } from 'src/app/modules/comonlapi';
import { Utils } from 'src/app/utils';
import { CONSTANTS_MODE,  STATO_COMUNICAZIONE,  TIPO_COMUNICAZIONE, TIPO_COMUNICAZIONE_TU, TIPO_COMUNICAZIONE_TU_ID_ANNULLAMENTO, TIPO_COMUNICAZIONE_TU_ID_RETTIFICA, TIPO_CONTRATTI, TIPO_SOMMINISTRAZIONE, TIPO_TRACCIATO } from 'src/app/constants';
import { WrapperHelper } from 'src/app/models/wrappwer-helper';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { Location } from '@angular/common';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { NavComunicazioneParams } from '../../models/nav-comunicazione-params';
import { TrasferimentoDistaccoForm } from 'src/app/models/TrasferimentoDistaccoForm';
import { RicercaComunicazioniParams } from 'src/app/models/ricerca-comunicazioni-params';

@Component({
  selector: 'comonl-nav-main-comunicazione',
  templateUrl: './nav-main-comunicazione.component.html',
  styleUrls: ['./nav-main-comunicazione.component.scss']
})
export class NavMainComunicazioneComponent implements OnInit, OnDestroy {

  // private static readonly SCROLL_TARGET = 'em[data-scroll-marker="navMainComunicazione"]';

  // @ViewChild('quadro', {static: true, read: ViewContainerRef}) container: ViewContainerRef;
  @ViewChild(AddQuadroDirective, {static: true}) adHost!: AddQuadroDirective;
  quadri: AdQuadro[] = [];
  componentRef: ComponentRef<AdComponent>;

  parametriNavigazione: NavComunicazioneParams;
  wrapperHelper: WrapperHelper;
  idComunicazione: number;

  controlDisabled: boolean;
  comunicazione: Comunicazione = new Object() as Comunicazione;
  lavoratore: LavoratoreSilpEspanso = new Object() as LavoratoreSilpEspanso;
  azienda: DatiAzienda = new Object() as DatiAzienda;
  datore: Datore;
  sedeOperativa: SedeLavoro;
  trasferimentoDistaccoForm: TrasferimentoDistaccoForm;
  comunicazioneToSave: Comunicazione;
  indexByParam: number;
  configurazioneQuadri: string;
  ruolo: Ruolo;
  flgNuovaCO: boolean;
  flgNuovaCOFromUrg: boolean;
  saveStatusSuccess: boolean;

  sediLavoratoriRapporti: RapportiLavoratoriSediInteressateVD[];

  get VIEW_MODE() {
    const nuovaComParams = this.parametriNavigazione;
    return nuovaComParams.mode === CONSTANTS_MODE.VIEW;
  }

  get INS_MODE() {
    const nuovaComParams = this.parametriNavigazione;
    return nuovaComParams.mode === CONSTANTS_MODE.INS;
  }

  get RETTIFICA_MODE(){
    const nuovaComParams = this.parametriNavigazione;
    return nuovaComParams.mode === CONSTANTS_MODE.RETTIFICA;
  }

  get ANNULLO_MODE(){
    const nuovaComParams = this.parametriNavigazione;
    return nuovaComParams.mode === CONSTANTS_MODE.ANNULLO;
  }

  
  // get SHOW_TUTORE(){
  //   if()
  //   return this.currentIndex === 4 && this.comunicazioneToSave.rapporto.tipoContratti.id === 21 || this.comunicazioneToSave.rapporto.tipoContratti.id ===
  // }
  constructor(
    private logService: LogService,
    private utilitiesService: UtilitiesService,
    private loaderService: LoaderService,
    private resolver: ComponentFactoryResolver,
    private comunicazioneService: ComunicazioneService,
    private router: Router,
    private alertMessageService: AlertMessageService,
    private comonlStorageService: ComonlStorageService
  ) {
    const state = this.router.getCurrentNavigation().extras.state;
    this.parametriNavigazione = state.parametriNavigazione;
    this.configurazioneQuadri = this.parametriNavigazione.configurazioneQuadri;
    this.idComunicazione = this.parametriNavigazione.idComunicazione;
    this.wrapperHelper = state.wrapperHelper ? state.wrapperHelper :  null;
  }

  ricercaComunicazioniParamas: RicercaComunicazioniParams;

  active = 2;
  currentIndex = 0;

  ngOnDestroy(): void {
    this.quadri = [];
  }


  // add commento

  async ngOnInit() {
    // this.utilitiesService.scrollTo(NavMainComunicazioneComponent.SCROLL_TARGET);
    this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    this.comonlStorageService.ruolo$.subscribe(async item => {
      this.ruolo = item;
    });
    this.comonlStorageService.ricercaComunicazioniParams$.subscribe(async item => {
      this.ricercaComunicazioniParamas = item;
    });
    await this.initComunicazione(this.idComunicazione);
    if (this.wrapperHelper) {
      this.comunicazioneToSave = Utils.clone(this.wrapperHelper.comunicazione);
      if(this.comunicazioneToSave.id){
        await this.initComunicazione(this.comunicazioneToSave.id);
      }
      this.datore = this.wrapperHelper.datore;
      this.sedeOperativa = this.wrapperHelper.sedeOperativa;
      this.trasferimentoDistaccoForm = this.wrapperHelper.trasferimentoDistaccoForm;
      this.sediLavoratoriRapporti = this.wrapperHelper.sediLavoratoriRapporti
    } else {
      this.comunicazioneToSave = Utils.clone(this.comunicazione);
    }
    this.elaborazioneConfigurazioneQuadri(this.comunicazione);
    this.currentIndex = this.parametriNavigazione.quandroIndex ? this.parametriNavigazione.quandroIndex : 0;
    this.loadQuadro(this.currentIndex);

    this.utilitiesService.hideSpinner();
  }

  // private async asyncNgOnInit() {
  //   await this.utilitiesService.showSpinner();


  //   await this.utilitiesService.hideSpinner();
  // }

  private async initComunicazione(idComunicazione: number) {
    if (idComunicazione) {
      try {
        this.comunicazione = await this.comunicazioneService.getComunicazioneById(idComunicazione).toPromise();
        if (
            this.parametriNavigazione.mode === CONSTANTS_MODE.RETTIFICA && 
            (
                this.comunicazione.statoComunicazione.id === STATO_COMUNICAZIONE.VALIDATA.ID || (this.comunicazione.statoComunicazione.id === STATO_COMUNICAZIONE.INSERITA.ID && this.comunicazione.tipoComunicazioneTu.id !== 3)
            )
          ) {
          this.comunicazione.idComTuDaRettificare = this.comunicazione.id;
          this.comunicazione.id = null;
          this.comunicazione.idComDComunicPrecedente = idComunicazione;
          this.comunicazione.codiceComunRegPrec = this.comunicazione.codiceComunicazioneReg;
          this.comunicazione.codiceComunicazioneReg = null;
          this.comunicazione.provinciaPrec = this.comunicazione.provincia;
          this.comunicazione.numProtComPrec = this.comunicazione.numProtCom;
          this.comunicazione.annoProtComPrec = this.comunicazione.annoProtCom;
          
          this.comunicazione.provincia = null;
          this.comunicazione.numProtCom = null;
          this.comunicazione.annoProtCom = null;
          this.comunicazione.dtInvio = null;
          this.comunicazione.dtInvioMinistero = null;
          this.comunicazione.tipoComunicazioneTu = {
            id: TIPO_COMUNICAZIONE_TU_ID_RETTIFICA,
            dsComTTipoComunicazione: 'Rettifica',
            codTipoComunicazioneMin: TIPO_COMUNICAZIONE_TU.COD_MIN_03
          };
          this.comunicazione.statoComunicazione = {
            id: STATO_COMUNICAZIONE.INSERITA.ID,
          }
        }
        if (this.parametriNavigazione.mode === CONSTANTS_MODE.ANNULLO && this.comunicazione.tipoComunicazioneTu.id !== 4) {
          this.comunicazione.id = null;
          this.comunicazione.idComTuPrecedenteAnnullo = idComunicazione;
          this.comunicazione.idComDComunicPrecedente = idComunicazione;
          this.comunicazione.codiceComunRegPrec = this.comunicazione.codiceComunicazioneReg;
          this.comunicazione.codiceComunicazioneReg = null;
          this.comunicazione.provinciaPrec = this.comunicazione.provincia;
          this.comunicazione.numProtComPrec = this.comunicazione.numProtCom;
          this.comunicazione.annoProtComPrec = this.comunicazione.annoProtCom;
          
          this.comunicazione.provincia = null;
          this.comunicazione.numProtCom = null;
          this.comunicazione.annoProtCom = null;
          this.comunicazione.dtInvio = null;
          this.comunicazione.dtInvioMinistero = null;
          this.comunicazione.tipoComunicazioneTu = {
            id: TIPO_COMUNICAZIONE_TU_ID_ANNULLAMENTO,
            dsComTTipoComunicazione: 'Annullamento',
            codTipoComunicazioneMin: TIPO_COMUNICAZIONE_TU.COD_MIN_04
          };
          this.comunicazione.statoComunicazione = {
            id: STATO_COMUNICAZIONE.ANNULLATA.ID,
          }

        }
        // IN CASO DI AGGIORNAMENTO
       
        if (this.parametriNavigazione.mode === CONSTANTS_MODE.AGGIORNAMENTO) {
          this.comunicazione.idComDComunicPrecedente = this.comunicazione.id;
          this.comunicazione.id = null;
          this.comunicazione.dtInsert = null;
          this.comunicazione.dtProtocollo = null;
          this.comunicazione.dtUltMod = null;
          this.comunicazione.idUserUltMod = null;
          this.comunicazione.idUserInsert = null;
          this.comunicazione.email = null;
          this.comunicazione.flgComDatLav = 'N';
          this.comunicazione.codiceFiscaleSoggetto = null;
          this.comunicazione.dtTimbroPostale = null;
          this.comunicazione.tipoSoggettoAbilitato = null;
          this.comunicazione.emailsoggetto = null;
          const tipoComunicazione = this.comunicazione.tipoComunicazione;
          this.comunicazione.idComTuDaRettificare = null;
          this.comunicazione.idComTuPrecedenteAnnullo = null;
          this.comunicazione.dsMotivoUrgenza = null;
          this.comunicazione.annoProtComPrec = null;
          this.comunicazione.numProtComPrec = null;
          
          this.comunicazione.dtInvio = null;
          this.comunicazione.dtInvioMinistero = null;
          if(this.comunicazione.missione){
            this.comunicazione.missione.id = null;
          }
          if(
            this.configurazioneQuadri === TIPO_QUADRO_COM.SOMMINISTRAZIONE ||
            this.configurazioneQuadri === TIPO_QUADRO_COM.PROROGA_DEL_RAPPORTO_DI_LAVORO_IN_ASSENZA_DI_MISSIONE ||
            this.configurazioneQuadri === TIPO_QUADRO_COM.TRASFORMAZIONE_DEL_RAPPORTO_DI_LAVORO_IN_ASSENZA_DI_MISSIONE || 
            this.configurazioneQuadri === TIPO_QUADRO_COM.TRASFERIMENTO_ALTRA_SEDE_OPERATIVA_AGENZIA || 
            this.configurazioneQuadri === TIPO_QUADRO_COM.CESSAZIONE_DEL_RAPPORTO_DI_LAVORO 
            ){
              if(this.comunicazione.missione){
                this.comunicazione.missione = null;
              }
          }
          if(this.comunicazione.rapporto){
            this.comunicazione.rapporto.id = null;
          }
          if(tipoComunicazione.id === TIPO_COMUNICAZIONE.TRASFORMAZIONE.ID){
            if(this.comunicazione.missione){
              this.comunicazione.missione.dtTrasformazione = null;
              this.comunicazione.missione.trasformazionerl = null;
            }
            if(this.comunicazione.rapporto){
              this.comunicazione.rapporto.dtTrasformazione = null;
              this.comunicazione.rapporto.trasformazionerl = null;
            }
          }else if(tipoComunicazione.id === TIPO_COMUNICAZIONE.PROROGA.ID){
            if(this.comunicazione.missione){
              this.comunicazione.missione.dtFineProroga = null;
            }
            if(this.comunicazione.rapporto){
              this.comunicazione.rapporto.dtFineProroga = null;
            }
          }else if(tipoComunicazione.id === TIPO_COMUNICAZIONE.CESSAZIONE.ID){
            if(this.comunicazione.missione){
              this.comunicazione.missione.dtCessazione = null;
              this.comunicazione.missione.cessazionerl = null;
            }
            if(this.comunicazione.rapporto){
              this.comunicazione.rapporto.dtCessazione = null;
              this.comunicazione.rapporto.cessazionerl = null;
            }
          }else if(tipoComunicazione.id === TIPO_COMUNICAZIONE.TRASFERIMENTO_DISTACCO.ID){
            if(this.comunicazione.missione){

              //DISTACCO
              this.comunicazione.missione.dtTrasformazione = null;
              this.comunicazione.missione.trasformazionerl = null;
              this.comunicazione.missione.dtFineRapporto = null;
              this.comunicazione.missione.flgDistaccoParziale = null;
              this.comunicazione.missione.flgDistaccoSuAziEstera = null;
              this.comunicazione.missione.dtEvento = null;
              this.comunicazione.missione.datoreDistaccatario = null;


              //TRASFERIMENTO
              this.comunicazione.missione.sedeLavoroPrecedente = null;
            }
            if(this.comunicazione.rapporto){
              //DISTACCO
              this.comunicazione.rapporto.dtTrasformazione = null;
              this.comunicazione.rapporto.trasformazionerl = null;

              this.comunicazione.rapporto.dtFineRapporto = null;
              this.comunicazione.rapporto.flgDistaccoParziale = null;
              this.comunicazione.rapporto.flgDistaccoSuAziEstera = null;
              this.comunicazione.rapporto.dtEvento = null;
              this.comunicazione.rapporto.datoreDistaccatario = null;

              //TRASFERIMENTO
              this.comunicazione.rapporto.sedeLavoroPrecedente = null;
            }
          }else if(tipoComunicazione.id === TIPO_COMUNICAZIONE.URGENZA.ID){
            this.comunicazione.codiceComunRegPrec = this.comunicazione.codiceComunicazioneReg;
            this.comunicazione.provinciaPrec = this.comunicazione.provincia;
            this.comunicazione.numProtComUrgenza = this.comunicazione.numProtCom;
            this.comunicazione.annoProtUrgenza = this.comunicazione.annoProtCom;
            this.comunicazione.flgComunSegUrgenza = "S";
            this.flgNuovaCOFromUrg = true;
          }else if(tipoComunicazione.id === TIPO_COMUNICAZIONE.VARIAZIONE_DATORE.ID){
            this.comunicazione.dtTrasferimentoVarDatori = null;
            this.comunicazione.tipoTrasferimento = null;
          }
          this.comunicazione.codiceComunicazioneReg = null;
          this.comunicazione.provincia = null;
          this.comunicazione.numProtCom = null;
          this.comunicazione.annoProtCom = null;
          this.comunicazione.tipoComunicazioneTu = {
            id: 1
          }
          const configurazioneQuadri = this.parametriNavigazione.configurazioneQuadri
          const formModalParams = this.parametriNavigazione.formModalParams;
          if(formModalParams){
            let tipoTracciato: TipoTracciato
            if(formModalParams.somm === 'S'){
              tipoTracciato = {
                id: TIPO_TRACCIATO.UNISOMM.ID,
                dsComTTipoTracciato: TIPO_TRACCIATO.UNISOMM.DS
              };
            }else{
              tipoTracciato = {
                id: TIPO_TRACCIATO.UNILAV.ID,
                dsComTTipoTracciato: TIPO_TRACCIATO.UNILAV.DS
              };
            }
            this.comunicazione.tipoTracciato = tipoTracciato;
          }
          this.comunicazione.tipoComunicazione = this.getTipoComunicazione(this.parametriNavigazione.configurazioneQuadri);
          let tipoSomministrazione: TipoSomministrazione;
          if (this.comunicazione.tipoTracciato.id === TIPO_TRACCIATO.UNISOMM.ID) {
            tipoSomministrazione = new Object();
            if (configurazioneQuadri === TIPO_QUADRO_COM.SOMMINISTRAZIONE) {
              tipoSomministrazione.id = TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE.ID;
            } else if (configurazioneQuadri === TIPO_QUADRO_COM.SOMMINISTRAZIONE_E_MISSIONE) {
              tipoSomministrazione.id = TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE_E_MISSIONE.ID;
            } else if (configurazioneQuadri === TIPO_QUADRO_COM.PROROGA_DEL_RAPPORTO_DI_LAVORO_IN_ASSENZA_DI_MISSIONE) {
              tipoSomministrazione.id = TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE.ID;
            } else if (configurazioneQuadri === TIPO_QUADRO_COM.PROROGA_DEL_RAPPORTO_DI_LAVORO_E_DELLA_MISSIONE) {
              tipoSomministrazione.id = TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE_E_MISSIONE.ID;
            } else if (configurazioneQuadri === TIPO_QUADRO_COM.PROROGA_DELLA_MISSIONE_IN_CASO_DI_RAPPORTO_A_TEMPO_INDETERMINATO) {
              tipoSomministrazione.id = TIPO_SOMMINISTRAZIONE.MISSIONE.ID;
            } else if (configurazioneQuadri === TIPO_QUADRO_COM.TRASFORMAZIONE_IN_COSTANZA_DI_MISSIONE) {
              tipoSomministrazione.id = TIPO_SOMMINISTRAZIONE.MISSIONE.ID;
            } else if (configurazioneQuadri === TIPO_QUADRO_COM.TRASFORMAZIONE_DEL_RAPPORTO_DI_LAVORO_IN_ASSENZA_DI_MISSIONE) {
              tipoSomministrazione.id = TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE.ID;
            } else if (configurazioneQuadri === TIPO_QUADRO_COM.TRASFERIMENTO_ALTRA_SEDE_LAVORO_DITTA_UTILIZZATRICE) {
              tipoSomministrazione.id = TIPO_SOMMINISTRAZIONE.MISSIONE.ID;
            } else if (configurazioneQuadri === TIPO_QUADRO_COM.TRASFERIMENTO_ALTRA_SEDE_OPERATIVA_AGENZIA) {
              tipoSomministrazione.id = TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE.ID;
            } else if (configurazioneQuadri === TIPO_QUADRO_COM.CESSAZIONE_DEL_RAPPORTO_DI_LAVORO) {
              tipoSomministrazione.id = TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE.ID;
            } else if (configurazioneQuadri === TIPO_QUADRO_COM.CESSAZIONE_MISSIONE) {
              tipoSomministrazione.id = TIPO_SOMMINISTRAZIONE.MISSIONE.ID;
            }
          }
          this.comunicazione.tipoSomministrazione = tipoSomministrazione;
        }
        const tipoComunicazioneTu: TipoComunicazioneTu = this.comunicazione.tipoComunicazioneTu;
        if(
            this.parametriNavigazione.mode !== CONSTANTS_MODE.VIEW && (
            tipoComunicazioneTu.codTipoComunicazioneMin !== TIPO_COMUNICAZIONE_TU.COD_MIN_04 && 
            tipoComunicazioneTu.codTipoComunicazioneMin !== TIPO_COMUNICAZIONE_TU.COD_MIN_03 &&
            !Utils.isNullOrUndefinedOrCampoVuoto(this.comunicazione.idComDComunicPrecedente))
        ){
            this.parametriNavigazione.mode = CONSTANTS_MODE.AGGIORNAMENTO;
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
    } else {
      const nuovaComunicazioneParams = this.parametriNavigazione.formModalParams;

      if (nuovaComunicazioneParams) {
        /*nuova comunicazione*/
        const configurazioneQuadri = this.parametriNavigazione.configurazioneQuadri;
        let tipoComunicazione: TipoComunicazione;
        let tipoTracciato: TipoTracciato;
        this.comunicazione.flgSommin = nuovaComunicazioneParams.somm;
        if (nuovaComunicazioneParams.urg === 'S') {
          tipoComunicazione = {
            id: TIPO_COMUNICAZIONE.URGENZA.ID,
            dsComTTipoComunicazione: TIPO_COMUNICAZIONE.URGENZA.DS
          };
          tipoTracciato = {
            id: TIPO_TRACCIATO.URGENZA.ID,
            dsComTTipoTracciato: TIPO_TRACCIATO.URGENZA.DS
          };
          this.comunicazione.tipoTracciato = tipoTracciato;
        } else {
          tipoComunicazione = this.getTipoComunicazione(configurazioneQuadri);

          const comunicazioneParams = nuovaComunicazioneParams.comunicazione;
          this.comunicazione.tipoTracciato = {
            id: comunicazioneParams.idComTipoTracciato
          } as TipoTracciato;
        }
        this.comunicazione.tipoComunicazione = tipoComunicazione;
        let tipoSomministrazione: TipoSomministrazione;
        if (this.comunicazione.tipoTracciato.id === TIPO_TRACCIATO.UNISOMM.ID) {
          tipoSomministrazione = new Object();
          if (configurazioneQuadri === TIPO_QUADRO_COM.SOMMINISTRAZIONE) {
            tipoSomministrazione.id = TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE.ID;
          } else if (configurazioneQuadri === TIPO_QUADRO_COM.SOMMINISTRAZIONE_E_MISSIONE) {
            tipoSomministrazione.id = TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE_E_MISSIONE.ID;
          } else if (configurazioneQuadri === TIPO_QUADRO_COM.PROROGA_DEL_RAPPORTO_DI_LAVORO_IN_ASSENZA_DI_MISSIONE) {
            tipoSomministrazione.id = TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE.ID;
          } else if (configurazioneQuadri === TIPO_QUADRO_COM.PROROGA_DEL_RAPPORTO_DI_LAVORO_E_DELLA_MISSIONE) {
            tipoSomministrazione.id = TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE_E_MISSIONE.ID;
          } else if (configurazioneQuadri === TIPO_QUADRO_COM.PROROGA_DELLA_MISSIONE_IN_CASO_DI_RAPPORTO_A_TEMPO_INDETERMINATO) {
            tipoSomministrazione.id = TIPO_SOMMINISTRAZIONE.MISSIONE.ID;
          } else if (configurazioneQuadri === TIPO_QUADRO_COM.TRASFORMAZIONE_IN_COSTANZA_DI_MISSIONE) {
            tipoSomministrazione.id = TIPO_SOMMINISTRAZIONE.MISSIONE.ID;
          } else if (configurazioneQuadri === TIPO_QUADRO_COM.TRASFORMAZIONE_DEL_RAPPORTO_DI_LAVORO_IN_ASSENZA_DI_MISSIONE) {
            tipoSomministrazione.id = TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE.ID;
          } else if (configurazioneQuadri === TIPO_QUADRO_COM.TRASFERIMENTO_ALTRA_SEDE_LAVORO_DITTA_UTILIZZATRICE) {
            tipoSomministrazione.id = TIPO_SOMMINISTRAZIONE.MISSIONE.ID;
          } else if (configurazioneQuadri === TIPO_QUADRO_COM.TRASFERIMENTO_ALTRA_SEDE_OPERATIVA_AGENZIA) {
            tipoSomministrazione.id = TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE.ID;
          } else if (configurazioneQuadri === TIPO_QUADRO_COM.CESSAZIONE_DEL_RAPPORTO_DI_LAVORO) {
            tipoSomministrazione.id = TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE.ID;
          } else if (configurazioneQuadri === TIPO_QUADRO_COM.CESSAZIONE_MISSIONE) {
            tipoSomministrazione.id = TIPO_SOMMINISTRAZIONE.MISSIONE.ID;
          }
        }
        this.comunicazione.tipoSomministrazione = tipoSomministrazione;
      }

    }
  }


  private getTipoComunicazione(config: string): TipoComunicazione {
    let tipoComunicazione = new Object as TipoComunicazione;
    if (
        config === TIPO_QUADRO_COM.ASSUNZIONE ||
        config === TIPO_QUADRO_COM.SOMMINISTRAZIONE_E_MISSIONE ||
        config === TIPO_QUADRO_COM.SOMMINISTRAZIONE) {
        tipoComunicazione.id = TIPO_COMUNICAZIONE.ASSUNZIONE.ID;
        tipoComunicazione.dsComTTipoComunicazione = TIPO_COMUNICAZIONE.ASSUNZIONE.DS;
    }

    if (
        config === TIPO_QUADRO_COM.PROROGA_DEL_RAPPORTO_DI_LAVORO_IN_ASSENZA_DI_MISSIONE ||
        config === TIPO_QUADRO_COM.PROROGA_DEL_RAPPORTO_DI_LAVORO_E_DELLA_MISSIONE ||
        config === TIPO_QUADRO_COM.PROROGA_DELLA_MISSIONE_IN_CASO_DI_RAPPORTO_A_TEMPO_INDETERMINATO ||
        config === TIPO_QUADRO_COM.PROROGA
      ) {
        tipoComunicazione.id = TIPO_COMUNICAZIONE.PROROGA.ID;
        tipoComunicazione.dsComTTipoComunicazione = TIPO_COMUNICAZIONE.PROROGA.DS;
      }

    if (
        config === TIPO_QUADRO_COM.TRASFORMAZIONE_IN_COSTANZA_DI_MISSIONE ||
        config === TIPO_QUADRO_COM.TRASFORMAZIONE_DEL_RAPPORTO_DI_LAVORO_IN_ASSENZA_DI_MISSIONE ||
        config === TIPO_QUADRO_COM.TRASFORMAZIONE
        ) {
          tipoComunicazione.id = TIPO_COMUNICAZIONE.TRASFORMAZIONE.ID;
          tipoComunicazione.dsComTTipoComunicazione = TIPO_COMUNICAZIONE.TRASFORMAZIONE.DS;
        }

    if (
          config === TIPO_QUADRO_COM.TRASFERIMENTO_ALTRA_SEDE_LAVORO_DITTA_UTILIZZATRICE ||
          config === TIPO_QUADRO_COM.TRASFERIMENTO_ALTRA_SEDE_OPERATIVA_AGENZIA ||
          config === TIPO_QUADRO_COM.TRASFERIMENTO_DISTACCO
        ) {
          tipoComunicazione.id = TIPO_COMUNICAZIONE.TRASFERIMENTO_DISTACCO.ID;
          tipoComunicazione.dsComTTipoComunicazione = TIPO_COMUNICAZIONE.TRASFERIMENTO_DISTACCO.DS;
        }


    if (
          config === TIPO_QUADRO_COM.CESSAZIONE_MISSIONE ||
          config === TIPO_QUADRO_COM.CESSAZIONE_DEL_RAPPORTO_DI_LAVORO ||
          config === TIPO_QUADRO_COM.CESSAZIONE
        ) {
          tipoComunicazione.id = TIPO_COMUNICAZIONE.CESSAZIONE.ID;
          tipoComunicazione.dsComTTipoComunicazione = TIPO_COMUNICAZIONE.CESSAZIONE.DS;
        }

    if (
          config === TIPO_QUADRO_COM.VARIAZIONE_DATORE ||
          config === TIPO_QUADRO_COM.VARIAZIONE_DATORE_CON_TUTORE ||
          config === TIPO_QUADRO_COM.VARIAZIONE_DATORE_RAG_SOCIALE ||
          config === TIPO_QUADRO_COM.VARIAZIONE_DATORE_TRASF
        ) {
          tipoComunicazione.id = TIPO_COMUNICAZIONE.VARIAZIONE_DATORE.ID;
          tipoComunicazione.dsComTTipoComunicazione = TIPO_COMUNICAZIONE.CESSAZIONE.DS;
        }

    return tipoComunicazione;
  }

  loadQuadri(configurazioneQuadri: string) {
    this.quadri = this.loaderService.getQuadri(configurazioneQuadri);
  }

  private loadQuadro(index: number) {
    this.currentIndex = index;
    const adItem = this.quadri[index];
    const viewContainerRef = this.adHost.viewContainerRef;
    viewContainerRef.clear();
    const factory = this.resolver.resolveComponentFactory(adItem.component);
    const componentRef = viewContainerRef.createComponent<AdComponent>(factory);
    this.parametriNavigazione.quandroIndex = index;
    adItem.data.routingParams = this.parametriNavigazione;
    // adItem.data.controlDisabled = this.controlDisabled;
    adItem.data.comunicazione = this.comunicazione;
    adItem.data.comunicazioneToSave = this.comunicazioneToSave;
    adItem.data.ruolo = this.ruolo;
    adItem.data.configurazioneQuadri = this.configurazioneQuadri;
    adItem.data.lavoratore = this.lavoratore;
    adItem.data.azienda = this.azienda;
    adItem.data.datore = this.datore;
    adItem.data.trasferimentoDistaccoForm = this.trasferimentoDistaccoForm;
    adItem.data.sedeOperativa = this.sedeOperativa;
    adItem.data.flgNuovaCO = this.flgNuovaCO;
    adItem.data.flgNuovaCOFromUrg = this.flgNuovaCOFromUrg;
    adItem.data.saveStatusSuccess = this.saveStatusSuccess;
    adItem.data.sediLavoratoriRapporti = this.sediLavoratoriRapporti;
    this.saveStatusSuccess = false;

    if (this.datore) {
      this.datore = null;
    }
    if (this.sedeOperativa) {
      this.sedeOperativa = null;
    }
    if (this.trasferimentoDistaccoForm) {
      this.trasferimentoDistaccoForm = null;
    }
    if(this.sediLavoratoriRapporti){
      this.sediLavoratoriRapporti = null;
    }
    componentRef.instance.data = adItem.data;
    this.eventListener(componentRef.instance);
    /*
    this.container.clear();
    const factory =  this.resolver.resolveComponentFactory(DatiAziendaComponent);
    const ref = this.container.createComponent(factory);
    ref.changeDetectorRef.detectChanges();*/
  }

  onNavChange(changeEvent: NgbNavChangeEvent) {
    console.log('onNavChange');
    console.log('nextId: ' + changeEvent.nextId);
    this.loadQuadro(changeEvent.nextId);
  }

  getTabIndex(index: number): number {
    return index += 1;
  }

  tabDisabled(index: number): boolean {
    return;
  }

  resetForm() {
    console.log('reset form');
  }

  piripicchio() {
    // quello che vuoi
  }

  private eventListener(instanceToObserve: AdComponent) {
    console.log('nav-main-comunicazione eventListener instanceToObserve instanceOf: ' + typeof(instanceToObserve));
    instanceToObserve.setComunicazioneToSave.subscribe((item: Comunicazione) => {
      this.comunicazioneToSave = item;
      this.setNext();
    });
    instanceToObserve.persistenceNotification.subscribe((item: PersistenceComunicazioneWreapper) => {
      this.comunicazione = Utils.clone(item.comunicazione);
      this.idComunicazione = this.comunicazione.id;
      this.comunicazioneToSave = Utils.clone(this.comunicazione);
      this.saveStatusSuccess = item.success;
      if (item.ricalcoloQuadri) {
        this.elaborazioneConfigurazioneQuadri(this.comunicazione);
      }
      if (item.switchEditMode) {
        this.parametriNavigazione.mode = 'edit';
      }
      this.setNext();
    });
  }

  private setNext() {
    this.currentIndex += 1;
    this.loadQuadro(this.currentIndex);
  }

   onClickGoBackToRicerca() {
    if(this.ricercaComunicazioniParamas){
      this.ricercaComunicazioniParamas = {
        ricercaComunicazioni: {
          formRicercaComunicazione: this.ricercaComunicazioniParamas.ricercaComunicazioni.formRicercaComunicazione,
          page:  this.ricercaComunicazioniParamas.ricercaComunicazioni.page,
          limit: this.ricercaComunicazioniParamas.ricercaComunicazioni.limit,
          sort: this.ricercaComunicazioniParamas.ricercaComunicazioni.sort,
          goSearch: true
        }
       }
     }
     if(this.comunicazione.tipoComunicazione.id === TIPO_COMUNICAZIONE.VARIAZIONE_DATORE.ID){
      this.router.navigate(['/ricerca-vardatori'], { state: { ricercaComunicazioniParams: this.ricercaComunicazioniParamas }});
     }else{
        this.router.navigate(['/ricerca-comunicazioni'], { state: { ricercaComunicazioniParams: this.ricercaComunicazioniParamas }});
     }

    }

   showQuadro(flgTutore: boolean) {
     if (!flgTutore) {
       return true;
     } else {
       const rapporto = this.comunicazioneToSave.rapporto;
     }
   }

   private elaborazioneConfigurazioneQuadri(comunicazione: Comunicazione) {
     let configurazioneQuadri: string = this.configurazioneQuadri;
     if (
      this.configurazioneQuadri !== TIPO_QUADRO_COM.URGENZA ||
      this.configurazioneQuadri !== TIPO_QUADRO_COM.VARIAZIONE_DATORE
      ) {
      const rapporto = comunicazione.rapporto;
      const tipoContratti = rapporto ? rapporto.tipoContratti : null;
      if (tipoContratti) {
        const codTipoContrattoMin = tipoContratti.codTipoContrattoMin;
        if (
            codTipoContrattoMin === TIPO_CONTRATTI.TIROCINIO.COD_TIPO_CONTARTTO_MIN ||
            codTipoContrattoMin === TIPO_CONTRATTI.APPR_QUAL_DIPLOMA_PROF_DIPLOMA_ISTR_SECONDARIA_SUPERIORE_CERT_SPECIALIZ_TECNICA_SUPERIORE.COD_TIPO_CONTARTTO_MIN ||
            codTipoContrattoMin === TIPO_CONTRATTI.APPR_PROF_CONTRATTO_MESTIERE.COD_TIPO_CONTARTTO_MIN ||
            codTipoContrattoMin === TIPO_CONTRATTI.APPR_ALTA_FORMAZIONE_RICERCA.COD_TIPO_CONTARTTO_MIN ||
            codTipoContrattoMin === TIPO_CONTRATTI.APPR_EX_ART16_196_97.COD_TIPO_CONTARTTO_MIN ||
            codTipoContrattoMin === TIPO_CONTRATTI.APPR_ESPLETAMENTO_DIRITTO_DOVERE_ISTRUZIONE_FORMAZIONE.COD_TIPO_CONTARTTO_MIN ||
            codTipoContrattoMin === TIPO_CONTRATTI.APPR_PROFESSIONALIZZANTE.COD_TIPO_CONTARTTO_MIN ||
            codTipoContrattoMin === TIPO_CONTRATTI.APPR_ACQUISIZIONE_DIPLOMA_PERCORSI_ALTA_FORMAZIONE.COD_TIPO_CONTARTTO_MIN ||
            codTipoContrattoMin === TIPO_CONTRATTI.APPR_PROF_CONTR_MESTIERE_LAV_MOBILITA.COD_TIPO_CONTARTTO_MIN ||
            codTipoContrattoMin === TIPO_CONTRATTI.APPR_ALTA_FORMAZIONE_RICERCA_LAVORATORI_MOBILITA.COD_TIPO_CONTARTTO_MIN ||
            codTipoContrattoMin === TIPO_CONTRATTI.TIROCINIO_ESTIVO_ORIENTAMENTO.COD_TIPO_CONTARTTO_MIN

            ) {
            configurazioneQuadri = configurazioneQuadri + "_TUTORE";
          }
        }
      }

     this.loadQuadri(configurazioneQuadri);
   }

}

export interface PersistenceComunicazioneWreapper {
  comunicazione: Comunicazione;
  ricalcoloQuadri: boolean;
  switchEditMode?: boolean;
  success?: boolean;
}




