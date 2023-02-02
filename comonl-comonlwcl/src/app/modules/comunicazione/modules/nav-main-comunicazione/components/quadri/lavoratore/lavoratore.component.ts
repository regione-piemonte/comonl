/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { utils } from 'protractor';
import { CITTADINANZA, CONSTANTS_MODE, CONTROL_STATE, STATUS_STRANIERO, TIPO_COMUNICAZIONE, TIPO_CONTRATTI, TIPO_TRACCIATO, TYPE_DECODIFICA_GENERICA } from 'src/app/constants';
import { Comune, Comunicazione, DecodificaGenerica, DecodificaService, Ruolo, SilpService, StatiEsteri, Lavoratore, LavoratoreSilpEspanso, Cittadinanza, StatusStraniero, ComunicazioneControlliService, RapportiLavoratoriSediInteressateVD, WrapperComunicazione, LavoratoreSilp } from 'src/app/modules/comonlapi';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { LogService, UtilitiesService } from 'src/app/services';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { UtilsComonl } from 'src/app/services/utilsComonl';
import { Utils } from 'src/app/utils';
import { isNumber } from 'util';
import { AdComponent } from '../../../models/ad-component';
import { PersistenceComunicazioneWreapper } from '../../nav-main-comunicazione/nav-main-comunicazione.component';

@Component({
  selector: 'comonl-lavoratore',
  templateUrl: './lavoratore.component.html',
  styleUrls: ['./lavoratore.component.scss']
})
export class LavoratoreComponent implements OnInit, AdComponent {

  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="lavoratore"]';

  form: FormGroup;

  @Input() data: any;
  @Output() datiForm = new EventEmitter<any>();
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();
  @Output() persistenceNotification = new EventEmitter<PersistenceComunicazioneWreapper>();

  comunicazione: Comunicazione;
  comunicazioneToSave: Comunicazione;
  routingParams;

  showForm: boolean;
  editMode: boolean;
  showTable: boolean;
  flgObbligatorio: boolean;
  ruolo: Ruolo;
  indexSelected: number;
  modelloQValid: boolean;

  elencoLavoratori: Lavoratore[] = [];
  elencoLavoratoriPrec: Lavoratore[] = [];

  lavoratoreSelected: Lavoratore;

  flgExtraUe: boolean;
  showBottoneinserisciLavoratore: boolean;
  showBottoneModificaLavoratore: boolean;

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

  get IS_MULTI_LAVORATORE(): boolean {
    return this.comunicazioneToSave.tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID && this.comunicazioneToSave.tipoComunicazione.id === TIPO_COMUNICAZIONE.ASSUNZIONE.ID;
  }

  get IS_ASSUNZIONE(): boolean {
    return this.comunicazioneToSave.tipoComunicazione.id === TIPO_COMUNICAZIONE.ASSUNZIONE.ID;
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


  get DISABLE_INTERACTIONS_VIEW(): boolean {
    return this.VIEW_MODE || this.EDIT_MODE;
  }

  get SEARCH_BUTTON_ENABLE() {
    if (this.RETTIFICA_MODE) {
      // calcolare i termini validi
      // non validi return false
      // validi check other conditions
      return true;
    } else {
      if (this.IS_TIPO_CONTRATTO_RIPARTITO) {
        return this.elencoLavoratori.length < 2;
      } else if (this.IS_MULTI_LAVORATORE) {
        return true;
      } else {
        return this.elencoLavoratori.length <= 1;
      }
    }
  }

  datiEssenziali: boolean;
  constructor(
    private logService: LogService,
    private decodificaService: DecodificaService,
    private silpService: SilpService,
    private utilitiesService: UtilitiesService,
    private promptModalService: PromptModalService,
    private comonlStorageService: ComonlStorageService,
    private alertMessageService: AlertMessageService,
    private router: Router,
    private comunicazioneControlliService: ComunicazioneControlliService
  ) {}


  async ngOnInit() {
    this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    this.utilitiesService.scrollTo(LavoratoreComponent.SCROLL_TARGET);
    this.comonlStorageService.ruolo$.subscribe(async item => {
      this.ruolo = item;
    });
    this.comunicazione = this.data.comunicazione;
    this.comunicazioneToSave = this.data.comunicazioneToSave;
    this.routingParams = this.data.routingParams;
    
    this.initForm();
    try {
      if(this.RETTIFICA_MODE){
        const wrapperComunicazione: WrapperComunicazione = {
          comunicazione: this.comunicazione,
          ruolo: this.ruolo
        }
       this.datiEssenziali = await this.comunicazioneControlliService.checkDatiEssenziali(wrapperComunicazione).toPromise();
      }
      const cfLavoratorePerVardatore = this.data.routingParams.cfLavoratorePerVardatore;
      //se cfLavoratorePerVardatore è valorizzato, significa che sto arrivando dall'aggiornamento di un vd
      if(!Utils.isNullOrUndefinedOrCampoVuoto(cfLavoratorePerVardatore)){
        await this.getLavoratoreDaVardatore(cfLavoratorePerVardatore);
      }else{
        await this.getLavoratori();
      }
      this.setFormMode();
      this.showForm = true;
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

  initForm() {
    this.form = new FormGroup({
      id: new FormControl(),
      codiceFiscale: new FormControl(null),
      cognome: new FormControl({ value: null, disabled: true}),
      nome: new FormControl({ value: null, disabled: true}),
      sesso: new FormControl({ value: null, disabled: true}),
      dtNascita: new FormControl({ value: null, disabled: true}),
      statiEsteriNasc: new FormGroup({
        id: new FormControl(),
        codNazioneMin: new FormControl({ value: null, disabled: true}),
        dsStatiEsteri: new FormControl({ value: null, disabled: true})
      }),
      comuneNasc: new FormGroup({
        id: new FormControl(),
        codComuneMin: new FormControl({ value: null, disabled: true}),
        dsComTComune: new FormControl({ value: null, disabled: true})
      }),
      cittadinanza: new FormGroup({
        id: new FormControl(),
        codCittadinanzaMin: new FormControl(),
        dsComTCittadinanza: new FormControl({ value: null, disabled: true}),
      }),
      statusStraniero: new FormGroup({
        id: new FormControl(),
        dsComTStatusStraniero: new FormControl({ value: null, disabled: true}),
        codStatusMin: new FormControl()
      }),
      numeroDocumento: new FormControl({ value: null, disabled: true}),
      motivoPermesso: new FormGroup({
        id: new FormControl(),
        dsComTMotivoPermesso: new FormControl({ value: null, disabled: true}),
        codMotivoMin: new FormControl()
      }),
      dtScadenzaPermessoSogg: new FormControl({ value: null, disabled: true}),
      questura: new FormGroup({
        id: new FormControl(),
        dsQuestura: new FormControl({ value: null, disabled: true}),
        codQuesturaMin: new FormControl()
      }),
      statiEsteriRes: new FormGroup({
        id: new FormControl(),
        codNazioneMin: new FormControl({ value: null, disabled: true}),
        dsStatiEsteri: new FormControl({ value: null, disabled: true})
      }),
      flgSistAlloggiativa: new FormControl(),
      flgRimborsoRimpatrio: new FormControl(),
      comuneRes: new FormGroup({
        id: new FormControl(),
        codComuneMin: new FormControl({ value: null, disabled: true}),
        dsComTComune: new FormControl({ value: null, disabled: true})
      }),
      codCapRes: new FormControl({ value: null, disabled: true}),
      dsIndirizzoRes: new FormControl({ value: null, disabled: true}),
      comuneDom: new FormGroup({
        id: new FormControl(),
        codComuneMin: new FormControl({ value: null, disabled: true}),
        dsComTComune: new FormControl({ value: null, disabled: true})
      }),
      codCapDom: new FormControl({ value: null, disabled: true}),
      dsIndirizzoDom: new FormControl({ value: null, disabled: true}),
      livelloStudio: new FormGroup({
        id: new FormControl(),
        titoloDiStudio: new FormControl({ value: null, disabled: true}),
        codiceLivelloMin: new FormControl()
      })
    });
  }

  private async getLavoratori() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    let elencoLavoratori: Lavoratore[];
    if (this.comunicazioneToSave.lavoratori) {
      elencoLavoratori  = Utils.clone(this.comunicazioneToSave.lavoratori);
    }
    let lavoratoreCoObbligato = this.comunicazioneToSave.lavoratoreCoObbligato;
    if (lavoratoreCoObbligato && lavoratoreCoObbligato.codiceFiscale) {
      if (this.AGGIORNAMENTO_MODE && !this.comunicazioneToSave.id) {
        lavoratoreCoObbligato.id = null;
      }
      elencoLavoratori.push(lavoratoreCoObbligato);
    }
    try {
      if (elencoLavoratori && elencoLavoratori.length > 0) {
        if (this.EDIT_MODE || this.RETTIFICA_MODE || this.ANNULLO_MODE || this.AGGIORNAMENTO_MODE) {
          elencoLavoratori.forEach( async (lav: Lavoratore) => {

            try {
              this.f.codiceFiscale.patchValue(lav.codiceFiscale);
              let lavoratoreTmp = lav;
              if(this.EDIT_MODE || this.RETTIFICA_MODE || this.AGGIORNAMENTO_MODE){
                const res: LavoratoreSilpEspanso = await this.silpService.getLavoratoreSilpEspanso(lav.codiceFiscale).toPromise();
                lavoratoreTmp = UtilsComonl.getLavoratoreFromLavoratoreSilpEspanso(res);
              }
              lavoratoreTmp.codiceFiscale = lav.codiceFiscale;
              lavoratoreTmp.flgSistAlloggiativa = lav.flgSistAlloggiativa;
              lavoratoreTmp.flgRimborsoRimpatrio = lav.flgRimborsoRimpatrio;
              if (this.EDIT_MODE) {
                lavoratoreTmp.id = lav.id;
              }else if(this.RETTIFICA_MODE && this.comunicazioneToSave.id){
                lavoratoreTmp.id = lav.id;
              }else if(this.ANNULLO_MODE && this.comunicazioneToSave.id){
                lavoratoreTmp.id = lav.id;
              }else if(this.AGGIORNAMENTO_MODE && this.comunicazioneToSave.id){
                lavoratoreTmp.id = lav.id;
              }
              //if(!Utils.isNullOrUndefined(lavoratoreCoObbligato) )
              this.elencoLavoratori.push(lavoratoreTmp);
              if (this.elencoLavoratori.length === 1) {
                // necessario per bypassare l'async
                this.patchValueInForm(this.elencoLavoratori[0]);
              }
              if (this.elencoLavoratori.length === elencoLavoratori.length) {
                // necessario per bypassare l'async
                this.elencoLavoratoriPrec = Utils.clone(this.elencoLavoratori);
              }
            } catch (e) {
              if (e.error && e.error.length > 0) {
                this.alertMessageService.setErrorMsg(e.error);
                this.showBottoneModificaLavoratore = false;
                this.showBottoneinserisciLavoratore = true;
              } else {
                this.alertMessageService.setSingleErrorMsg(e);
              }
            }

          });
        } else {
          this.elencoLavoratori = Utils.clone(elencoLavoratori);
          this.elencoLavoratoriPrec = Utils.clone(this.elencoLavoratori);
          this.patchValueInForm(this.elencoLavoratori[0]);
        }
        this.lavoratoreSelected = this.elencoLavoratori[0];
        this.indexSelected = 0;
        if(this.EDIT_MODE || this.RETTIFICA_MODE || this.AGGIORNAMENTO_MODE){
          this.showBottoneModificaLavoratore = true;
        }
      }
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

  private async getLavoratoreDaVardatore(cfLavoratore: string) {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    this.f.codiceFiscale.patchValue(cfLavoratore);
    let lavSediRapp: RapportiLavoratoriSediInteressateVD;
    if(this.comunicazioneToSave.rapLavSedeVD && this.comunicazioneToSave.rapLavSedeVD.length > 0){
      lavSediRapp = this.comunicazioneToSave.rapLavSedeVD.find(item => {
        return item.lavoratoreVD.codiceFiscale === cfLavoratore;
      });
    }
    if(lavSediRapp){
      this.comunicazione.rapporto = lavSediRapp.rapportoVD;
      this.comunicazioneToSave.rapporto = lavSediRapp.rapportoVD;
    }
    try{
      const resSilp: LavoratoreSilpEspanso =  await this.silpService.getLavoratoreSilpEspanso(cfLavoratore).toPromise();
      if (resSilp) {
        this.comunicazioneToSave.rapLavSedeVD = null;
        let lavoratore = UtilsComonl.getLavoratoreFromLavoratoreSilpEspanso(resSilp);
        this.comunicazione.lavoratori = [];
        this.comunicazioneToSave.datorePrecedente = null;
        lavoratore.codiceFiscale = cfLavoratore;
        if (lavoratore.cittadinanza) {
          if (lavoratore.cittadinanza.flgUe === 'S') {
            lavoratore.flgSistAlloggiativa = 'N';
            lavoratore.flgRimborsoRimpatrio = 'N';
            this.f.flgSistAlloggiativa.setValue(lavoratore.flgSistAlloggiativa);
            this.f.flgRimborsoRimpatrio.setValue(lavoratore.flgRimborsoRimpatrio);
          }
        }
        this.comunicazione.lavoratori.push(lavoratore);
        this.elencoLavoratori.push(lavoratore);
        this.patchValueInForm(lavoratore);
        this.lavoratoreSelected = lavoratore;
        
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

  // MODALE CERCA COMUNE
  async onClickFindComune(section: string) {
    let decodifica: DecodificaGenerica;
    this.utilitiesService.showSpinner();
    if (section === 'nascita') {
      decodifica = {
        codDecodifica: this.f.comune.get('codComuneMin').value,
        dsDecodifica: this.f.comune.get('dsComTComune').value
      };
    } else if (section === 'sede') {
      decodifica = {
        codDecodifica: this.f.comuneSede.get('codComuneMin').value,
        dsDecodifica: this.f.comuneSede.get('dsComTComune').value
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
         codComuneMin: decodificaFinded.codDecodifica,
         dsComTComune: decodificaFinded.dsDecodifica
       };
       if (section === 'nascita') {
       this.f.comune.patchValue(comune);
       } else if (section === 'sede') {
        this.f.comuneSede.patchValue(comune);
       }
     }
    } catch (e) {
      this.alertMessageService.setWarningMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }
  // MODALE CERCA STATO ESTERO
  async onClickFindStatoEstero(section: string) {
    let decodifica: DecodificaGenerica;
    this.utilitiesService.showSpinner();
    if (section === 'nascita') {
      decodifica = {
        codDecodifica: this.f.statoEstero.get('codNazioneMin').value,
        dsDecodifica: this.f.statoEstero.get('dsStatiEsteri').value
      };
    } else if (section === 'sede') {
      decodifica = {
        codDecodifica: this.f.statoEsteroSede.get('codNazioneMin').value,
        dsDecodifica: this.f.statoEsteroSede.get('dsStatiEsteri').value
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
        codNazioneMin: decodificaFinded.codDecodifica,
        dsStatiEsteri: decodificaFinded.dsDecodifica
       };
       if (section === 'nascita') {
        this.f.statoEstero.patchValue(statoEstero);
        } else if (section === 'sede') {
         this.f.statoEsteroSede.patchValue(statoEstero);
        }
     }
    } catch (e) {
      this.alertMessageService.setWarningMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  private patchValueInForm(lavoratore: Lavoratore) {
    if (lavoratore) {
      let cittadinanza = lavoratore.cittadinanza;
      if (!cittadinanza) {
        cittadinanza = new Object() as Cittadinanza;
        cittadinanza.id = CITTADINANZA.ITALIA.ID;
        cittadinanza.dsComTCittadinanza = CITTADINANZA.ITALIA.DS_CITTADINANZA;
        cittadinanza.flgUe = CITTADINANZA.ITALIA.FLG_UE;
        lavoratore.cittadinanza = cittadinanza;
      }else{
        const flgUe = cittadinanza.flgUe;
        if(flgUe === 'S' && Utils.isNullOrUndefinedOrCampoVuoto(lavoratore.flgSistAlloggiativa) && Utils.isNullOrUndefinedOrCampoVuoto(lavoratore.flgRimborsoRimpatrio)){
          lavoratore.flgSistAlloggiativa = 'N';
          lavoratore.flgRimborsoRimpatrio = 'N';
          // this.f.flgSistAlloggiativa.setValue('N');
          // this.f.flgRimborsoRimpatrio.setValue('N');
        }
      }
      this.form.patchValue(lavoratore);
      // this.setControlState(lavoratore);
    }
  }

  private setFormMode() {

    if (
      this.IS_MULTI_LAVORATORE ||
      this.IS_TIPO_CONTRATTO_RIPARTITO) {
      this.showTable = true;
    }

    // this.VIEW_MODE || this.ANNULLO_MODE
    if (this.VIEW_MODE || this.ANNULLO_MODE) {
      // READ MODE
      this.form[CONTROL_STATE.DISABLE]();
    } else if (this.AGGIORNAMENTO_MODE) {
      this.f.codiceFiscale[CONTROL_STATE.DISABLE]();
    }

    if (this.AGGIORNAMENTO_MODE) {
      this.utilitiesService.changeControlState(
        this.f.codiceFiscale,
        CONTROL_STATE.DISABLE,
        false
      );
    }
  }

  private setControlState(lavoratore: Lavoratore) {
  }

  goToAnagrafica() {
    this.router.navigate(['./anagrafica-lavoratore'], { state: { comingFromHome: false }});
  }

  async onClickFindlavoratoreSilp() {
    this.alertMessageService.emptyMessages();
    this.showBottoneinserisciLavoratore = false;
    this.showBottoneModificaLavoratore = false;
    this.utilitiesService.showSpinner();
    const codiceFiscale = this.f.codiceFiscale.value;
    if (this.checkCfLavisPresent(codiceFiscale)) {
      this.utilitiesService.hideSpinner();
      this.alertMessageService.setSingleErrorMsg('Il lavoratore è già presente');
      this.showBottoneModificaLavoratore = true;
      this.showBottoneinserisciLavoratore = false;
      return;
    }
    try {
      const resSilp: LavoratoreSilpEspanso =  await this.silpService.getLavoratoreSilpEspanso(codiceFiscale).toPromise();
      if (resSilp) {
        this.showBottoneModificaLavoratore = true;
        let lavoratore = UtilsComonl.getLavoratoreFromLavoratoreSilpEspanso(resSilp);
        const LENGTH_ELENCO_LAVORATORI = this.elencoLavoratori.length;
        lavoratore.codiceFiscale = this.f.codiceFiscale.value;
        if (this.IS_TIPO_CONTRATTO_RIPARTITO) {

          if (LENGTH_ELENCO_LAVORATORI < 2) {

            this.indexSelected = this.elencoLavoratori.push(lavoratore);
            this.indexSelected = this.indexSelected - 1;
          }

        } else if (this.IS_MULTI_LAVORATORE) {

          this.indexSelected = this.elencoLavoratori.push(lavoratore);
          this.indexSelected = this.indexSelected - 1;

        } else {

          if (LENGTH_ELENCO_LAVORATORI >= 1) {
            this.elencoLavoratori = [];
          }

          this.indexSelected = this.elencoLavoratori.push(lavoratore);
          this.indexSelected = this.indexSelected - 1;
        }

        if (this.INS_MODE) {
          console.log('in find lavoratore silp');
          console.log('flgUe: ' + lavoratore.cittadinanza.flgUe);
          if (lavoratore.cittadinanza) {
            if (lavoratore.cittadinanza.flgUe === 'S') {
              lavoratore.flgSistAlloggiativa = 'N';
              lavoratore.flgRimborsoRimpatrio = 'N';
              this.f.flgSistAlloggiativa.setValue(lavoratore.flgSistAlloggiativa);
              this.f.flgRimborsoRimpatrio.setValue(lavoratore.flgRimborsoRimpatrio);
            }
          }
        }

        this.patchValueInForm(lavoratore);
        this.lavoratoreSelected = lavoratore;
        // if (lavoratore.cittadinanza.flgUe === 'N' && this.FLG_OBBL_MODELLO_Q === true) {
        //   this.modelloQValid = true;
        // } else {
        //   this.modelloQValid = false;
        // }

      } else {
        this.alertMessageService.setSingleErrorMsg('Nessun risultato trovato');
      }
    } catch (e) {
      this.alertMessageService.setSingleErrorMsg(e);
      if (e.error && e.error.length) {
        this.alertMessageService.setErrorMsg(e.error);
         this.showBottoneinserisciLavoratore = !Utils.isNullOrUndefinedOrCampoVuoto(e.error.find(el => el.code === "COM-COM-P-0002")); 
         this.showBottoneModificaLavoratore = !this.showBottoneinserisciLavoratore;
        /*TODO CONTROLLARE STO PEZZO***/
        //alert(JSON.stringify(this.showBottoneinserisciLavoratore));
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  private checkCfLavisPresent(codiceFiscale: string): boolean {
    let isPresent: boolean = false;
    if (this.elencoLavoratori &&
        this.elencoLavoratori.length > 0) {
          for (let index = 0; !isPresent && index < this.elencoLavoratori.length; index++) {
            isPresent = (codiceFiscale === this.elencoLavoratori[index].codiceFiscale);
          }
        }
        
    return isPresent;
  }

  patchInForm(lavoratore: Lavoratore, index: number) {
    this.showBottoneModificaLavoratore = !(this.showBottoneinserisciLavoratore = false);
    this.indexSelected = index;
    this.lavoratoreSelected = lavoratore;
    this.patchValueInForm(lavoratore);
  }

  eliminaLavoratore(index: number) {
    this.showBottoneModificaLavoratore = false;
    const deletedItem: Lavoratore = this.elencoLavoratori.splice(index, 1)[0];
    const codiceFiscale = this.f.codiceFiscale.value;
    if (codiceFiscale === deletedItem.codiceFiscale) {
      this.form.reset();
    }else{
      this.indexSelected = this.elencoLavoratori.indexOf(this.lavoratoreSelected);
    }
    
  }

  onClickDownLavoratore(index: number) {
    if (this.elencoLavoratori &&
        this.elencoLavoratori.length > 1) {
          // const lavoratore = this.elencoLavoratori[index];
          // arr1.push.apply(arr1, arr1.splice(0,2));
        }
  }

  onClickUpLavoratore(index: number) {
    if (this.elencoLavoratori &&
      this.elencoLavoratori.length > 1) {
        this.elencoLavoratori.unshift(this.elencoLavoratori.splice(index, 1)[0]);
      }
  }

  onClickSwapLavoratore() {
    if (this.elencoLavoratori &&
      this.elencoLavoratori.length > 1) {
        const tmp = this.elencoLavoratori[1];
        this.elencoLavoratori.unshift(this.elencoLavoratori.splice(1, 1)[0]);
        this.swapIndexSelected();
      }
    console.log('lavoratore: ' + JSON.stringify(this.elencoLavoratori));
  }

  private swapIndexSelected() {
    if(this.indexSelected === 1){
      this.indexSelected = 0;
    }else{
      this.indexSelected = 1;
    }
  }

  reset() {
    this.alertMessageService.emptyMessages();
    this.form.reset();
    this.lavoratoreSelected = null;
    if (this.elencoLavoratoriPrec && this.elencoLavoratoriPrec.length > 0) {
      this.elencoLavoratori = Utils.clone(this.elencoLavoratoriPrec);
      this.lavoratoreSelected = this.elencoLavoratori[0]
      this.indexSelected = 0;
      this.form.patchValue(this.elencoLavoratori[0]);
      this.showBottoneModificaLavoratore = true;
    } else {
      this.elencoLavoratori = [];
      this.showBottoneModificaLavoratore = false;
    }
    this.showBottoneinserisciLavoratore = false;
  }

  checkCittadinanza(): boolean {
    // TODO TMP PERCHE' SE NO SI SPACCA
    return false;
    if (this.comunicazioneToSave.lavoratori[0].cittadinanza.flgUe !== 'S') {
      if (this.comunicazioneToSave.lavoratori[0].cittadinanza.codCittadinanzaMin === '246' ||
          this.comunicazioneToSave.lavoratori[0].cittadinanza.codCittadinanzaMin === '223' ||
          this.comunicazioneToSave.lavoratori[0].cittadinanza.codCittadinanzaMin === '225' ||
          this.comunicazioneToSave.lavoratori[0].cittadinanza.codCittadinanzaMin === '231' ||
          this.comunicazioneToSave.lavoratori[0].cittadinanza.codCittadinanzaMin === '236' ||
          this.comunicazioneToSave.lavoratori[0].cittadinanza.codCittadinanzaMin === '241') {
        return false;
      }
      return true;
    }
  }

  onClickflgAlloggiativa() {
    if (isNumber(this.indexSelected)) {
      const flgSistAlloggiativa = this.f.flgSistAlloggiativa.value;
      this.elencoLavoratori[this.indexSelected].flgSistAlloggiativa = flgSistAlloggiativa;
    }
  }
  onClickFlgRimborso() {
    if (isNumber(this.indexSelected)) {
      const flgRimborsoRimpatrio = this.f.flgRimborsoRimpatrio.value;
      this.elencoLavoratori[this.indexSelected].flgRimborsoRimpatrio = flgRimborsoRimpatrio;
    }

  }

  async confermaEprosegui() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    try {
      this.comunicazioneToSave.lavoratori = this.elencoLavoratori;
      const wrapperComunicazione: WrapperComunicazione = {
        comunicazione: this.comunicazioneToSave,
        ruolo: this.ruolo
      }
      const res = await this.comunicazioneControlliService.chkLavoratoriComunicazione(wrapperComunicazione).toPromise();
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

  get CAMPI_VALIDI(): boolean {
    let res: boolean = true;
    if (
        this.elencoLavoratori &&
        this.elencoLavoratori.length > 0
      ) {
        this.elencoLavoratori.forEach((item: Lavoratore) => {
          const comuneNasc = item.comuneNasc;
          const statiEsteriNasc = item.statiEsteriNasc;
          const comuneRes = item.comuneRes;
          const statiEsteriRes = item.statiEsteriRes;
          const comuneDom = item.comuneDom;
          const livelloStudio = item.livelloStudio;
          /*legato all'extra UE */
          const cittadinanza = item.cittadinanza;
          const statusStraniero = item.statusStraniero;
          const motivoPermesso = item.motivoPermesso;
          const flgRimborsoRimpatrio = item.flgRimborsoRimpatrio;
          const flgSistAlloggiativa = item.flgSistAlloggiativa;
          console.log('********************FINE***********************');
          /*legato all'extra UE */
          if (
               (this.IS_ASSUNZIONE && (Utils.isNullOrUndefinedOrCampoVuoto(item.flgRimborsoRimpatrio)  || Utils.isNullOrUndefinedOrCampoVuoto(item.flgSistAlloggiativa))) ||
               Utils.isNullOrUndefinedOrCampoVuoto(item.codiceFiscale) ||
               Utils.isNullOrUndefinedOrCampoVuoto(item.sesso) ||
               Utils.isNullOrUndefinedOrCampoVuoto(item.dtNascita) ||
               Utils.isNullOrUndefinedOrCampoVuoto(item.dsIndirizzoRes) ||
               (comuneRes && comuneRes.id && Utils.isNullOrUndefinedOrCampoVuoto(item.codCapRes)) ||
               Utils.isNullOrUndefinedOrCampoVuoto(item.dsIndirizzoDom) ||
               Utils.isNullOrUndefinedOrCampoVuoto(item.codCapDom) ||
               // step 1
               (!comuneNasc.id && !statiEsteriNasc.id) ||
               (!comuneDom.id) ||
               (!comuneRes.id && !statiEsteriRes.id) ||
               (!livelloStudio.id) ||
               (this.flgExtraUe &&
                (
                  !cittadinanza.id || !statusStraniero.id || !motivoPermesso.id ||
                  Utils.isNullOrUndefinedOrCampoVuoto(item.dtScadenzaPermessoSogg) ||
                  
                  !this.getValiditaQuestura(statusStraniero)

                ))
           ) {
            res = false;
            return res;
           }
        });
    } else {
      // non ho nessun lavoratore .... quindi non è valida
      res = false;
      return res;
    }
    return res;

  }

  findInvalidControls() {
    const controls = this.form.controls;
    for (const name in controls) {
        if (controls[name].invalid) {
           console.log('invalid: ' + name);
        }
    }
  }

  getValiditaQuestura(lavoratore: Lavoratore): boolean {
    const statusStraniero: StatusStraniero = lavoratore.statusStraniero;
    const questura = lavoratore.questura;
    if (statusStraniero && this.flgExtraUe) {
      const cod: string = statusStraniero.codStatusMin;
      if (
        cod !== STATUS_STRANIERO.IN_ATTESA_PERMESSO.COD_MIN &&
        cod !== STATUS_STRANIERO.ALTRO_PROVVEDIMENTO.COD_MIN &&
        cod !== STATUS_STRANIERO.IN_RINNOVO.COD_MIN
      ) {
        return !Utils.isNullOrUndefinedOrCampoVuoto(questura.id);
      }
    }
    return false;
  }

  get OBBLIG_QUESTURA(): boolean {
    if (this.lavoratoreSelected) {
      const statusStraniero: StatusStraniero = this.lavoratoreSelected.statusStraniero;
      if (statusStraniero && this.flgExtraUe) {
        const cod: string = statusStraniero.codStatusMin;
        return cod !== STATUS_STRANIERO.IN_ATTESA_PERMESSO.COD_MIN && cod !== STATUS_STRANIERO.ALTRO_PROVVEDIMENTO.COD_MIN && cod !== STATUS_STRANIERO.IN_RINNOVO.COD_MIN;
      }
    }
    return false;
  }

  /***********START LOGICA MODALE INSERIMENTO LAVORATORE******************/
  async onClickOpenModalAnagraficaLavoratore(mode: string){
    this.alertMessageService.emptyMessages();
    this.utilitiesService.showSpinner();
    let cfLavoratoreSilpSalvato: string;
    if(this.RETTIFICA_MODE && mode !== "ins"){
      mode = 'rettifica'
    }

    try{
      if(mode === 'edit' || mode === 'rettifica'){
      
        this.alertMessageService.emptyMessages();
        const cf = this.elencoLavoratori[this.indexSelected].codiceFiscale;
        const resSilp: LavoratoreSilpEspanso =  await this.silpService.getLavoratoreSilpEspanso(cf).toPromise();
        cfLavoratoreSilpSalvato = await this.promptModalService.openModalAnagraficaLavoratore(mode,this.comunicazione,resSilp,this.datiEssenziali);
        
    }else{
      cfLavoratoreSilpSalvato = await this.promptModalService.openModalAnagraficaLavoratore(mode,this.comunicazione);
    }
    }catch(e){
      this.alertMessageService.setSingleErrorMsg("Attenzione! Codice fiscale da modificare non valido");
    }finally{
      this.utilitiesService.hideSpinner();
    }
    try{
      this.alertMessageService.emptyMessages();
      if(!Utils.isNullOrUndefinedOrCampoVuoto(cfLavoratoreSilpSalvato)){
        this.utilitiesService.showSpinner();
        const resSilp: LavoratoreSilpEspanso =  await this.silpService.getLavoratoreSilpEspanso(cfLavoratoreSilpSalvato).toPromise();
        let lavoratore = UtilsComonl.getLavoratoreFromLavoratoreSilpEspanso(resSilp);
        lavoratore.codiceFiscale = cfLavoratoreSilpSalvato;
        if(mode === 'ins'){
          this.showBottoneModificaLavoratore = true;
          this.showBottoneinserisciLavoratore = false;
          const LENGTH_ELENCO_LAVORATORI = this.elencoLavoratori.length;
          lavoratore.codiceFiscale = this.f.codiceFiscale.value;
          if (this.IS_TIPO_CONTRATTO_RIPARTITO) {

            if (LENGTH_ELENCO_LAVORATORI < 2) {

              this.indexSelected = this.elencoLavoratori.push(lavoratore);
              this.indexSelected = this.indexSelected - 1;
            }

          } else if (this.IS_MULTI_LAVORATORE) {

            this.indexSelected = this.elencoLavoratori.push(lavoratore);
            this.indexSelected = this.indexSelected - 1;

          } else {

            if (LENGTH_ELENCO_LAVORATORI >= 1) {
              this.elencoLavoratori = [];
            }

            this.indexSelected = this.elencoLavoratori.push(lavoratore);
            this.indexSelected = this.indexSelected - 1;
          }

      
          console.log('in find lavoratore silp');
          console.log('flgUe: ' + lavoratore.cittadinanza.flgUe);
          if (!Utils.isNullOrUndefined(lavoratore.cittadinanza)) {
            if (lavoratore.cittadinanza.flgUe === 'S') {
              lavoratore.flgSistAlloggiativa = 'N';
              lavoratore.flgRimborsoRimpatrio = 'N';
              this.f.flgSistAlloggiativa.setValue(lavoratore.flgSistAlloggiativa);
              this.f.flgRimborsoRimpatrio.setValue(lavoratore.flgRimborsoRimpatrio);
            }
          }

        this.patchValueInForm(lavoratore);
        this.lavoratoreSelected = lavoratore;

        }else{
          //modifica e rettifica
          const lavoratoreDamodificare = this.elencoLavoratori[this.indexSelected];
          lavoratore.id = lavoratoreDamodificare.id;
          this.elencoLavoratori[this.indexSelected] = lavoratore;
          this.patchValueInForm(lavoratore);
          this.lavoratoreSelected = lavoratore;
        }
        this.alertMessageService.setSuccessSingleMsg('Salvataggio eseguito con successo');
      }
    }catch(e){

    }finally{
      this.utilitiesService.hideSpinner();
    }
    
    
  }


}
