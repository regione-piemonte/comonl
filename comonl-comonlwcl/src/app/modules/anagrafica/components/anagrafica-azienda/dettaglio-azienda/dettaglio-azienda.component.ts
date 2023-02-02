/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Location } from '@angular/common';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { marker } from '@biesbjerg/ngx-translate-extract-marker';
import { TranslateService } from '@ngx-translate/core';
import { utils } from 'protractor';
import { CONTROL_STATE, REGIONE, TYPE_DECODIFICA_GENERICA } from 'src/app/constants';
import { WrapperHelper } from 'src/app/models/wrappwer-helper';
import { ApiError, Atecofin, Ccnl, Comune, Datore, DecodificaGenerica, DecodificaService, LegaleRappr, Provincia, SedeLavoro, SilpService } from 'src/app/modules/comonlapi';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { NavComunicazioneParams } from 'src/app/modules/comunicazione/modules/nav-main-comunicazione/models/nav-comunicazione-params';
import { LogService, UtilitiesService } from 'src/app/services';
import { UtilsComonl } from 'src/app/services/utilsComonl';
import { Utils } from 'src/app/utils';
import { isNullOrUndefined } from 'util';
import { Cittadinanza } from './../../../../comonlapi/model/cittadinanza';
import { DatiAzienda } from './../../../../comonlapi/model/datiAzienda';
import { MotivoPermesso } from './../../../../comonlapi/model/motivoPermesso';
import { NaturaGiuridica } from './../../../../comonlapi/model/naturaGiuridica';
import { Questura } from './../../../../comonlapi/model/questura';
import { Sede } from './../../../../comonlapi/model/sede';
import { StatiEsteri } from './../../../../comonlapi/model/statiEsteri';
import { StatusStraniero } from './../../../../comonlapi/model/statusStraniero';
import { Toponimo } from './../../../../comonlapi/model/toponimo';

@Component({
  selector: 'comonl-dettaglio-azienda',
  templateUrl: './dettaglio-azienda.component.html',
  styleUrls: ['./dettaglio-azienda.component.scss']
})
export class DettaglioAziendaComponent implements OnInit {

  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="dettaglio-azienda"]';

  @Input() list: DecodificaGenerica[];
  @ViewChild('dtScadenzaPermessoSogg', { static: false }) datePicker: any;
  @ViewChild('iscrAlboData', { static: false }) datePickerIscrAlboData: any;

  form: FormGroup;
  formLegaleRappr: FormGroup;
  formDettaglioSede: FormGroup;

  azienda: DatiAzienda;
  listaCittadinanza: Cittadinanza[] = [];
  listaCittadinanzaFiltrata: Cittadinanza[] = [];
  listaCittadinanzaUE: Cittadinanza[] = [];
  listaCittadinanzaEXTUE: Cittadinanza[] = [];
  listaStatoEsteroCf: StatiEsteri[] = [];
  listaTitoloSoggiorno: StatusStraniero[] = [];
  listaMotivoPermesso: MotivoPermesso[] = [];
  listaQuestura: Questura[] = [];
  listaNaturaGiuridica: NaturaGiuridica[] = [];
  listaProvince: Provincia[] = [];
  listaToponimi: Toponimo[] = [];

  flgControlloDataScadenzaPerm = false;
  data2000: Date = new Date('/01/01/2000');
  elencoSedi: any;
  showDettaglio = false;
  nuovaSede = false;
  showElencoSedi = false;
  modificaSede: boolean;
  inserisciSede: boolean;
  elencoSediTmp: Sede[];
  provinceSedi: any;
  elencoSediOrdered: Sede[];
  dettaglioSede: Sede;
  codiceFiscaleDigitato: boolean;

  now: Date = new Date();

  flgComSedeVuoto = true;
  flgStatiEstSedeVuoto = true;
  flgViewSede = false;

  flgRicerca;
  elencoSediDaVardatori: any;

  INS_MODE: boolean;
  EDIT_MODE: boolean;
  READ_MODE: boolean;

  // per salto
  parametriNavigazione: NavComunicazioneParams;
  wrapperHelper: WrapperHelper;

  aziendaDaVardatori;
  showForm = false;
  disabilitaComuneEStatiEsteri: boolean = false;

  get f() {
    return this.form.controls as any;
  }

  get fL() {
    return this.formLegaleRappr.controls as any;
  }

  get fS() {
    return this.formDettaglioSede.controls as any;
  }

  get STATI_ESTERI_SEDE_VUOTO() {
    return (Utils.isNullOrUndefinedOrCampoVuoto(this.fS.statiEsteri.get('codNazioneMin').value) || Utils.isNullOrUndefinedOrCampoVuoto(this.fS.statiEsteri.get('dsStatiEsteri').value));
  }

  get COMUNE_SEDE_VUOTO() {
    return (Utils.isNullOrUndefinedOrCampoVuoto(this.fS.comune.get('codComuneMin').value) || Utils.isNullOrUndefinedOrCampoVuoto(this.fS.comune.get('dsComTComune').value));
  }

  get STATI_ESTERI_NASCITA_VUOTO() {
    return (Utils.isNullOrUndefinedOrCampoVuoto(this.fL.statiEsteri.get('codNazioneMin').value) || Utils.isNullOrUndefinedOrCampoVuoto(this.fL.statiEsteri.get('dsStatiEsteri').value));
  }

  get COMUNE_NASCITA_VUOTO() {
    return (Utils.isNullOrUndefinedOrCampoVuoto(this.fL.comune.get('codComuneMin').value) || Utils.isNullOrUndefinedOrCampoVuoto(this.fL.comune.get('dsComTComune').value));
  }

  get FLG_TELEFONO_OBBLIGATORIO() {
    return (Utils.isNullOrUndefinedOrCampoVuoto(this.fS.fax.value) && Utils.isNullOrUndefinedOrCampoVuoto(this.fS.email.value));
  }

  get FLG_FAX_OBBLIGATORIO() {
    return (Utils.isNullOrUndefinedOrCampoVuoto(this.fS.telefono.value) && Utils.isNullOrUndefinedOrCampoVuoto(this.fS.email.value));
  }

  get FLG_EMAIL_OBBLIGATORIO() {
    return (Utils.isNullOrUndefinedOrCampoVuoto(this.fS.telefono.value) && Utils.isNullOrUndefinedOrCampoVuoto(this.fS.fax.value));
  }

  get ISCR_ALBO_NUM_VUOTO() {
    return (Utils.isNullOrUndefinedOrCampoVuoto(this.f.iscrAlboNum.value));
  }

  get ISCR_ALBO_DATA_VUOTO() {
    return (Utils.isNullOrUndefinedOrCampoVuoto(this.f.iscrAlboData.value));
  }

  // obbligatorieta' campi del legale rappresentante
  get LEGALE_RAPPRESENTANTE_EXTRA_COMUNITARIO() {
    return (this.fL.extraCom.value === 'S');
  }

  get LEGALE_RAPPRESENTANTE_SOGGIORNANTE_ITALIA() {
    return (this.fL.flgResidenzaItaliana.value === 'S');
  }

  get CODICE_FISCALE_DIGITATO() {
    return this.f.cfAzienda;
  }



  constructor(
    private logService: LogService,
    private decodificaService: DecodificaService,
    private silpService: SilpService,
    private location: Location,
    private utilitiesService: UtilitiesService,
    private promptModalService: PromptModalService,
    private alertMessageService: AlertMessageService,
    private translateService: TranslateService,
    private router: Router) {

    this.EDIT_MODE = this.router.getCurrentNavigation().extras.state.mode === 'modifica';
    this.INS_MODE = this.router.getCurrentNavigation().extras.state.mode === 'inserimento';
    this.READ_MODE = this.router.getCurrentNavigation().extras.state.mode === 'visualizza';

    this.azienda = this.router.getCurrentNavigation().extras.state.azienda;
    this.parametriNavigazione = this.router.getCurrentNavigation().extras.state.parametriNavigazione;
    this.wrapperHelper = this.router.getCurrentNavigation().extras.state.wrapperHelper;
    // console.log('azienda preso da anagrafica azienda: ' + JSON.stringify(this.azienda));
    this.initFormAnagrafica();
    this.initFormSede();
    this.showForm = true;
    // Quando eseguo il giro da sedi lavoratori vardatori
    this.aziendaDaVardatori = this.router.getCurrentNavigation().extras.state.azienda;
    // console.log('azienda vardatori: ' + JSON.stringify(this.aziendaDaVardatori));
    this.flgRicerca = this.router.getCurrentNavigation().extras.state.flgRicerca;
    console.log('flg ricerca: ' + this.flgRicerca);
    console.log(this.flgRicerca === true && this.aziendaDaVardatori);

  }

  goToComunicazione() {
    this.router.navigate(['/nav-main-comunicazione'], { state: { parametriNavigazione: this.parametriNavigazione, wrapperHelper: this.wrapperHelper } });
  }

  async ngOnInit() {
    await this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    this.utilitiesService.scrollTo(DettaglioAziendaComponent.SCROLL_TARGET);
    try {
      if (!isNullOrUndefined(this.azienda) &&
        !isNullOrUndefined(this.azienda.idAziendaSilp)) {
        const [
          elencoSedi
        ] = await Promise.all([
          this.silpService.getSediSilp(this.azienda.idAziendaSilp).toPromise()
        ]);
        this.elencoSedi = elencoSedi;
      }
      const [
        cittadinanza,
        statoEsteroCf,
        naturaGiuridica,
        provincia,
        titoloSoggiorno,
        motivoPermesso,
        questura,
        toponimo
      ] = await Promise.all([
        this.decodificaService.getCittadinanza().toPromise(),
        this.decodificaService.getStatoEsteroCf().toPromise(),
        this.decodificaService.getNaturaGiuridica().toPromise(),
        this.decodificaService.getProvincia(REGIONE.PIEMONTE.COD_MIN).toPromise(),
        this.decodificaService.getTitoloSoggiorno().toPromise(),
        this.decodificaService.getMotivoPermesso().toPromise(),
        this.decodificaService.getQuestura().toPromise(),
        this.decodificaService.getToponimo().toPromise()
      ]);
      this.listaCittadinanza = cittadinanza;
      this.listaStatoEsteroCf = statoEsteroCf;
      this.listaNaturaGiuridica = naturaGiuridica;
      this.listaProvince = provincia;
      this.listaTitoloSoggiorno = titoloSoggiorno;
      this.listaMotivoPermesso = motivoPermesso;
      this.listaQuestura = questura;
      this.listaToponimi = toponimo;
    } catch (e) {
      console.log(e);
    } finally {
      await this.utilitiesService.hideSpinner();
    }

    if (!this.INS_MODE) {
      this.patchValuesInForm(this.azienda);
      this.caricaTabellaSediAndComboProvince();
    }

    if (this.READ_MODE) {
      this.form.disable();
      this.formLegaleRappr.disable();
    }

    console.log('arrivo da vardatori: ' + this.wrapperHelper);
    if (this.wrapperHelper) {
      this.form.disable();
      this.formLegaleRappr.disable();
      this.form.controls.provincia.enable();
    }

    if (this.EDIT_MODE) {
      this.patchValuesInForm(this.azienda);
      this.caricaTabellaSediAndComboProvince();
      // this.showDettaglio = true;
      // this.modificaSede = true;
      this.fS.tipoSede.disable();
      this.fS.tipoSede.patchValue('O');
      this.setValidatorsIscrizAlboNumAlboData();//Alessio aggiunto per gestire obblig dei 2 campi
    }

    if (this.INS_MODE) {
      this.f.flagArtigiana.patchValue('N');
      this.f.flgPubblicaAmm.patchValue('N');
      this.showDettaglio = true;
      // this.modificaSede = true;
      this.fS.tipoSede.disable();
      this.fS.tipoSede.patchValue('L');
      this.fL.cittadinanza.disable();
      // this.utilitiesService.changeControlState(
      //   this.fS.flgValida,
      //   CONTROL_STATE.DISABLE,
      //   false
      //   );
      this.fS.flgValida.disable();
      this.fS.flgValida.patchValue('S');
      this.setValidatorsIscrizAlboNumAlboData(); //Alessio aggiunto per gestire obblig. dei 2 campi
    }

    if (this.wrapperHelper) {
      this.showElencoSedi = true;
    }

    if (this.flgRicerca === true) {
      if (this.aziendaDaVardatori) {
        if (this.aziendaDaVardatori.codiceFiscale) {
          this.f.cfAzienda.patchValue(this.aziendaDaVardatori.codiceFiscale);
        }
        if (this.aziendaDaVardatori.partitaIva) {
          this.f.partitaIva.patchValue(this.aziendaDaVardatori.partitaIva);
        }
        if (this.aziendaDaVardatori.dsDenominazioneDatore) {
          this.f.denominazioneDatoreLavoro.patchValue(this.aziendaDaVardatori.dsDenominazioneDatore);
        }
        if (this.aziendaDaVardatori.atecofin) {
          this.f.atecofin.patchValue(this.aziendaDaVardatori.atecofin);
        }
        if (this.aziendaDaVardatori.ccnl) {
          this.f.ccnl.patchValue(this.aziendaDaVardatori.ccnl);
        }
        // console.log('flgArtigiana: ' + this.aziendaDaVardatori.flgAzArtigiana);
        this.f.flagArtigiana.patchValue(this.aziendaDaVardatori.flgAzArtigiana);
        this.f.flgPubblicaAmm.patchValue(this.aziendaDaVardatori.flgPubAmm);
        this.f.iscrAlboNum.patchValue(this.aziendaDaVardatori.iscrAlboNum);
        this.f.iscrAlboData.patchValue(this.aziendaDaVardatori.iscrAlboData);

        this.elencoSediDaVardatori = await this.silpService.getSediSilp(this.aziendaDaVardatori.idAziendaSilp).toPromise(),
          console.log('elencoSediVardatori: ' + JSON.stringify(this.elencoSediDaVardatori));
        this.elencoSedi = this.elencoSediDaVardatori;
        this.caricaTabellaSediAndComboProvince();
        this.showElencoSedi = true;
      }
    }
    this.listaNaturaGiuridica.sort((a, b) => a.descrizione.localeCompare(b.descrizione));
  }

  caricaTabellaSediAndComboProvince() {
    this.elencoSediTmp = this.elencoSedi;
    this.elencoSediOrdered = this.elencoSediTmp;
    if (this.elencoSediOrdered && this.elencoSediOrdered.length > 0) {
      this.elencoSediOrdered.sort((sede) => sede.sedeLegale ? -1 : 1);
    }
    const objProvince: Provincia[] = [];
    if (this.elencoSediTmp && this.elencoSediTmp.length > 0) {
      this.elencoSediTmp.forEach((item: Sede) => {
        if (!isNullOrUndefined(item.comune) &&
          !isNullOrUndefined(item.comune.provincia)) {
          if (objProvince.indexOf(item.comune.provincia) === -1
            || item.comune.provincia.dsComTProvincia !== '') {
            let laProv = objProvince.find(
              el => {
                return el.codProvinciaMin === item.comune.provincia.codProvinciaMin;
              }
            );
            if (!laProv) {
              objProvince.push(item.comune.provincia);
            }
          }
        }
      });
    }
    this.provinceSedi = objProvince;
  }

  initFormAnagrafica() {
    this.form = new FormGroup({
      idAziendaSilp: new FormControl(null),
      cfAzienda: new FormControl(null),
      partitaIva: new FormControl(null),
      denominazioneDatoreLavoro: new FormControl(null),
      atecofin: new FormGroup({
        id: new FormControl(),
        codAtecofinMin: new FormControl(null, Validators.minLength(3)),
        dsComTAtecofin: new FormControl(null, Validators.minLength(3)),
      }),
      ccnl: new FormGroup({
        id: new FormControl(),
        codCcnlMin: new FormControl(null),
        dsCcnl: new FormControl(null),
      }),
      naturaGiuridica: new FormControl(null),
      flagArtigiana: new FormControl(null),
      dsAnilt: new FormControl(null), // da chiedere
      flgPubblicaAmm: new FormControl('N'), // da chiedere
      iscrAlboNum: new FormControl(null), // da chiedere
      iscrAlboData: new FormControl(null), // da chiedere
      provincia: new FormControl(null)
    });
    this.formLegaleRappr = new FormGroup({
      dsCognome: new FormControl(null),
      dsNome: new FormControl(null),
      sesso: new FormControl(null), // da chiedere
      dtNascita: new FormControl(null), // da chiedere
      statiEsteri: new FormGroup({
        id: new FormControl(),
        codNazioneMin: new FormControl(null, Validators.minLength(3)),
        dsStatiEsteri: new FormControl(null, Validators.minLength(3)),
      }),
      comune: new FormGroup({
        id: new FormControl(),
        codComuneMin: new FormControl(null, Validators.minLength(3)),
        dsComTComune: new FormControl(null, Validators.minLength(3))
      }),
      cittadinanza: new FormControl(null),
      extraCom: new FormControl(null),
      flgResidenzaItaliana: new FormControl(null),
      statusStraniero: new FormControl(null),
      numeroDocumento: new FormControl(null),
      motivoPermesso: new FormControl(null),
      dtScadenzaPermessoSogg: new FormControl(null),
      questura: new FormControl(null),
      idLegaleRapprSilp: new FormControl(null),
      idSedeAssociata: new FormControl(null)
    });
  }

  initFormSede() {
    this.formDettaglioSede = new FormGroup({
      idSedeSilp: new FormControl(null),
      tipoSede: new FormControl(null),
      numAgSomm: new FormControl(null), // da chiedere
      patInail: new FormControl(null),
      statiEsteri: new FormGroup({
        id: new FormControl(),
        codNazioneMin: new FormControl(null, Validators.minLength(3)),
        dsStatiEsteri: new FormControl(null, Validators.minLength(3))
      }),
      comune: new FormGroup({
        id: new FormControl(),
        codComuneMin: new FormControl(null, Validators.minLength(3)),
        dsComTComune: new FormControl(null, Validators.minLength(3))
      }),
      toponimoSede: new FormControl(),
      indirizzo: new FormControl(null),
      numeroCivico: new FormControl(null),
      cap: new FormControl(null),
      telefono: new FormControl(null),
      fax: new FormControl(null),
      email: new FormControl(null),
      flgValida: new FormControl(null),
      motivoInvalida: new FormControl(null),
      numAgenziaSomministrazione: new FormControl(null)
    });
  }

  patchValuesInForm(azienda: DatiAzienda) {
    if (azienda) {
      if (!this.flgRicerca) {
        this.form.patchValue({
          ...azienda,
          denominazione: this.azienda.denominazioneDatoreLavoro.substring(0, 300)
        });
      } else {
        this.form.patchValue({
          ...azienda,
          denominazione: this.aziendaDaVardatori.dsDenominazioneDatore.substring(0, 300)
        });
      }
      this.formLegaleRappr.patchValue({
        ...azienda.ilLegaleRapprDellaSedeLegale,
      });

      if (this.EDIT_MODE) {
        this.utilitiesService.changeControlState(
          this.f.cfAzienda,
          CONTROL_STATE.DISABLE,
          false
        );
      }

      if (!this.READ_MODE) {
        // if (this.ISCR_ALBO_NUM_VUOTO) {
        //   this.utilitiesService.changeControlState(
        //     this.f.iscrAlboData,
        //     CONTROL_STATE.DISABLE,
        //     false
        //   );
        // } else {
        //   this.utilitiesService.changeControlState(
        //     this.f.iscrAlboData,
        //     CONTROL_STATE.ENABLE,
        //     false
        //   );
        //   this.f.iscrAlboData.setValidators(Validators.required);
        //   this.f.iscrAlboData.updateValueAndValidity();
        // }
        // if (this.ISCR_ALBO_DATA_VUOTO) {
        //   this.utilitiesService.changeControlState(
        //     this.f.iscrAlboNum,
        //     CONTROL_STATE.DISABLE,
        //     false
        //   );
        // } else {
        //   this.utilitiesService.changeControlState(
        //     this.f.iscrAlboNum,
        //     CONTROL_STATE.ENABLE,
        //     false
        //   );
        //   this.f.iscrAlboNum.setValidators(Validators.required);
        //   this.f.iscrAlboNum.updateValueAndValidity();
        // }

        if (this.LEGALE_RAPPRESENTANTE_EXTRA_COMUNITARIO) {
          this.fL.dsCognome.setValidators(Validators.required);
          this.fL.dsNome.setValidators(Validators.required);
          this.fL.dtNascita.setValidators(Validators.required);
          this.fL.cittadinanza.setValidators(Validators.required);
          this.fL.sesso.setValidators(Validators.required);
          this.caricaListaCittadinanzaFiltrata('N');
        } else {
          this.caricaListaCittadinanzaFiltrata('S');
        }
        if (this.LEGALE_RAPPRESENTANTE_SOGGIORNANTE_ITALIA) {
          this.fL.statusStraniero.setValidators(Validators.required);
          this.fL.numeroDocumento.setValidators(Validators.required);
          this.fL.motivoPermesso.setValidators(Validators.required);
          this.fL.dtScadenzaPermessoSogg.setValidators(Validators.required);
        }
        // if (this.formLegaleRappr.get('statusStraniero').value !== 'IN ATTESA DI PERMESSO' ||
        //     this.formLegaleRappr.get('statusStraniero').value !== 'IN RINNOVO' ||
        //     this.formLegaleRappr.get('statusStraniero').value !== 'ALTRO PROVVEDIMENTO') {
        //   this.fL.questura.setValidators(Validators.required);
        // } else {
        //   this.fL.questura.clearValidators();
        //   this.formLegaleRappr.updateValueAndValidity();
        // }

      }

      // Da mettere quando arrivera' il legale rappresentante --> il legale e' arrivato
      if (this.azienda.ilLegaleRapprDellaSedeLegale) {
        this.formLegaleRappr.patchValue(this.azienda.ilLegaleRapprDellaSedeLegale);
        if (this.azienda.ilLegaleRapprDellaSedeLegale.cittadinanza) {
          const laCittadinanza = this.listaCittadinanza.find((el: Cittadinanza) => {
            return el.id === this.azienda.ilLegaleRapprDellaSedeLegale.cittadinanza.id;
          });
          this.formLegaleRappr.controls.cittadinanza.patchValue(laCittadinanza);
          if (laCittadinanza.flgUe === 'S') {
            this.formLegaleRappr.controls.extraCom.setValue('N');
            this.caricaListaCittadinanzaFiltrata('S');
          } else {
            this.formLegaleRappr.controls.extraCom.setValue('S');
            this.caricaListaCittadinanzaFiltrata('N');
          }
        }
        if (this.azienda.ilLegaleRapprDellaSedeLegale.questura) {
          const laQuestura = this.listaQuestura.find((el: Questura) => {
            return el.id === this.azienda.ilLegaleRapprDellaSedeLegale.questura.id;
          });
          this.formLegaleRappr.controls.questura.patchValue(laQuestura);
        }
        if (this.azienda.ilLegaleRapprDellaSedeLegale.statusStraniero) {
          const ilTitolo = this.listaTitoloSoggiorno.find((el: StatusStraniero) => {
            return el.id === this.azienda.ilLegaleRapprDellaSedeLegale.statusStraniero.id;
          });
          this.formLegaleRappr.controls.statusStraniero.patchValue(ilTitolo);
          this.formLegaleRappr.controls.flgResidenzaItaliana.setValue('S');
        } else {
          // non ha selezionato nessun titolo di soggiorno quindi non e' soggiornante in italia
          this.formLegaleRappr.controls.flgResidenzaItaliana.setValue('N');
        }
        if (this.azienda.ilLegaleRapprDellaSedeLegale.motivoPermesso) {
          const ilMotivo = this.listaMotivoPermesso.find((el: MotivoPermesso) => {
            return el.id === this.azienda.ilLegaleRapprDellaSedeLegale.motivoPermesso.id;
          });
          this.formLegaleRappr.controls.motivoPermesso.patchValue(ilMotivo);
        }
        // radio extracomunitario solo se il flgcittadinanza e'



      }


      // POPOLAMENTO COMBO
      // Cittadinanza
      if (azienda.naturaGiuridica) {
        const naturaGiuridica = this.listaNaturaGiuridica.find((el: NaturaGiuridica) => {
          return el.id === this.azienda.naturaGiuridica.id;
        });
        this.f.naturaGiuridica.patchValue(naturaGiuridica);
      }
      if (azienda.codLavoroTemp) {
        this.f.iscrAlboNum.patchValue(azienda.codLavoroTemp.substring(0, 10));
        const extractedStringDate = azienda.codLavoroTemp.substring(10, azienda.codLavoroTemp.length);
        //Alessio aggiunto per patchare corretto formato data
        if(!Utils.isNullOrUndefinedOrCampoVuoto(extractedStringDate)){
          const [day, month, year] = extractedStringDate.split('/');
          const extractedDate = new Date(+year, +month - 1, +day);
          this.f.iscrAlboData.patchValue(extractedDate);
        }
      }
      // FINE POPOLAMENTO COMBO
    }
  }


  // MODALE CERCA COMUNE
  async onClickFindComune(section: string) {
    this.alertMessageService.emptyMessages();
    let decodifica: DecodificaGenerica;
    this.utilitiesService.showSpinner();
    if (section === 'nascita') {
      decodifica = {
        codDecodifica: this.fL.comune.get('codComuneMin').value,
        dsDecodifica: this.fL.comune.get('dsComTComune').value,
        flgAncheNonValidi: true
      };
    } else if (section === 'sede') {
      decodifica = {
        codDecodifica: this.fS.comune.get('codComuneMin').value,
        dsDecodifica: this.fS.comune.get('dsComTComune').value
      };
    }
    try {
      const res = await this.decodificaService.postComuneDecodifica(decodifica).toPromise();
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
          this.fL.comune.patchValue(comune);
          this.fL.statiEsteri.disable();
        } else if (section === 'sede') {
          this.fS.comune.patchValue(comune);
          this.fS.statiEsteri.disable();
          this.flgComSedeVuoto = false;
          this.fS.toponimoSede.enable();
          this.fS.cap.enable();
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
    this.alertMessageService.emptyMessages();
    let decodifica: DecodificaGenerica;
    this.utilitiesService.showSpinner();
    if (section === 'nascita') {
      decodifica = {
        codDecodifica: this.fL.statiEsteri.get('codNazioneMin').value,
        dsDecodifica: this.fL.statiEsteri.get('dsStatiEsteri').value,
        flgAncheNonValidi: true
      };
    } else if (section === 'sede') {
      decodifica = {
        codDecodifica: this.fS.statiEsteri.get('codNazioneMin').value,
        dsDecodifica: this.fS.statiEsteri.get('dsStatiEsteri').value
      };
    }
    try {
      const res = await this.decodificaService.postStatiEsteriDecodifica(decodifica).toPromise();
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
          this.fL.statiEsteri.patchValue(statoEstero);
          this.fL.comune.disable();
        } else if (section === 'sede') {
          this.fS.statiEsteri.patchValue(statoEstero);
          this.fS.comune.disable();
          this.flgStatiEstSedeVuoto = false;
          this.utilitiesService.changeControlState(
            this.fS.toponimoSede,
            CONTROL_STATE.DISABLE, true
          );
          this.utilitiesService.changeControlState(
            this.fS.cap,
            CONTROL_STATE.DISABLE, true
          );
          // this.fS.toponimoSede.disable();
          // this.fS.cap.disable();
        }
      }
    } catch (e) {
      this.alertMessageService.setWarningMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  onClickConfermaSede() {
    const objForm = this.formDettaglioSede.getRawValue() as Sede;
    this.utilitiesService.showSpinner();
    try {
      const sedeLegale = this.fS.tipoSede.value === 'L';
      const sedeOperativa = this.fS.tipoSede.value === 'O';
      let sedeDaAggiungere = {
        sedeLegale: sedeLegale,
        sedeOperativa: sedeOperativa,
        statiEsteri: objForm.statiEsteri,
        comune: objForm.comune,
        toponimoSede: objForm.toponimoSede,
        indirizzo: objForm.indirizzo,
        numeroCivico: objForm.numeroCivico,
        cap: objForm.cap,
        telefono: objForm.telefono,
        fax: objForm.fax,
        email: objForm.email,
        flgValida: objForm.flgValida,
        motivoInvalida: objForm.motivoInvalida
      } as Sede;
      // this.elencoSediOrdered.push(sedeDaAggiungere);
      // this.caricaTabellaSediAndComboProvince();
    } catch (e) {
      this.alertMessageService.setWarningMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }



  onClickAnnulla() {
    this.alertMessageService.emptyMessages();
    if (this.EDIT_MODE) {
      this.patchValuesInForm(this.azienda);
      this.setValidatorsIscrizAlboNumAlboData();
    }
    if (this.INS_MODE) {
      this.patchValuesInForm(this.azienda);
      this.setValidatorsIscrizAlboNumAlboData();
    }
  }

  onClickAnnullaRappresentante() {
    this.alertMessageService.emptyMessages();
    // this.fL.dsCognome.setValue(null);
    // this.fL.dsNome.setValue(null);
    // this.fL.dtNascita.setValue(null);
    // this.fL.statiEsteri.reset();
    // this.fL.comune.reset();
    // this.fL.cittadinanza.reset();
    // this.fL.statusStraniero.reset();
    // this.fL.numeroDocumento.reset();
    // this.fL.motivoPermesso.reset();
    // this.fL.dtScadenzaPermessoSogg.reset();
    // this.fL.questura.reset();
    this.formLegaleRappr.reset();

    // this.azienda.ilLegaleRapprDellaSedeLegale = null;
    // this.fL.idLegaleRapprSilp = idLegaleRapprSilp;

  }

  onSubmit() {
    console.log('Aggiorna azienda');
  }

  goToAnagrafica() {
    if (this.INS_MODE) {
      console.log('location: ' + window.location.href);
      this.location.back();
    } else {
      this.router.navigate(['./anagrafica-azienda'], { state: { cf: this.azienda.cfAzienda } });
    }
  }

  showDettaglioSede(sedeSelezionata: Sede, mode: string,disabilitaComuneEStatiEsteri?: boolean) {
    this.dettaglioSede = sedeSelezionata;
    this.initFormSede();
    console.log(mode);
    if (mode === 'visualizza') {
      this.modificaSede = false;
      this.inserisciSede = false;
      this.nuovaSede = false;
      this.flgViewSede = true;
      this.formDettaglioSede.disable();
      this.formDettaglioSede.updateValueAndValidity();
    } else if (mode === 'modifica') {
      this.modificaSede = true;
      this.inserisciSede = false;
      this.flgViewSede = false;
      this.formDettaglioSede.enable();
      this.formDettaglioSede.updateValueAndValidity();
      this.nuovaSede = true;
      this.disabilitaComuneEStatiEsteri = disabilitaComuneEStatiEsteri;
      if(this.disabilitaComuneEStatiEsteri){
        this.fS.statiEsteri['disable']();
        this.fS.comune['disable']();
      }else{
        this.disabilitaComuneEStatiEsteri = false;
        if (!this.COMUNE_SEDE_VUOTO) {
          this.fS.statiEsteri.disable();
        }
        if (!this.STATI_ESTERI_SEDE_VUOTO) {
          this.fS.comune.disable();
        }
      }
      
      this.fS.tipoSede.disable();
      this.fS.tipoSede.patchValue('O');

    }
    this.showDettaglio = true;
    // POPOLAMENTO CAMPI FORM SEDE
    this.formDettaglioSede.get('tipoSede').patchValue((this.dettaglioSede.sedeOperativa) ? 'O' : 'L');
    this.formDettaglioSede.patchValue(sedeSelezionata);
    if (sedeSelezionata.sedeLegale || mode === 'visualizza') {
      this.fS.flgValida.disable();
    } else {
      this.fS.flgValida.enable();
    }
    const toponimoSede = this.listaToponimi.find((el: Toponimo) => {
      return el.dsComTToponimo === this.dettaglioSede.toponimoSede.dsComTToponimo;
    });
    this.fS.toponimoSede.patchValue(toponimoSede);
    this.setValidatorMotivoInvalida();
  }

  onClickChooseSedeOperativa(sede: Sede) {
    let oldSedeOperativa: SedeLavoro;
    if (this.wrapperHelper.datore) {
      oldSedeOperativa = this.wrapperHelper.datore.sedeOperativa;
    }
    const sedeOperativa = UtilsComonl.getSedeLavoroFromSede(sede);
    sedeOperativa.flgSedeLegale = 'N';
    if (oldSedeOperativa) {
      sedeOperativa.id = oldSedeOperativa.id;
    }
    this.wrapperHelper.sedeOperativa = sedeOperativa;
    let datore: Datore = this.wrapperHelper.datore;
    if (datore) {
      datore.sedeOperativa = sedeOperativa;
      this.wrapperHelper.datore = datore;
    }
    this.router.navigate(['/nav-main-comunicazione'], { state: { parametriNavigazione: this.parametriNavigazione, wrapperHelper: this.wrapperHelper, flgDettAz: 'true' } });
  }

  showNuovaSede() {
    this.showDettaglio = true;
    this.nuovaSede = true;
    this.modificaSede = true;
    this.initFormSede();
    this,this.disabilitaComuneEStatiEsteri = false;
    if (this.INS_MODE) {
      this.fS.tipoSede.patchValue('L');
    } else
      if (this.EDIT_MODE) {
        this.fS.tipoSede.patchValue('O');
      }
    this.fS.tipoSede.disable();
    this.setValidatorMotivoInvalida();
  }

  async setSedeLegale(sede: Sede, sedeLegale: Sede, typeMessage) {

    const title = this.translate(marker('Variazione Sede Legale'));
    const message = this.translate(marker(typeMessage));
    const pYes = this.translate(marker('APP.CONFIRM'));
    const pNo = this.translate(marker('APP.BACK'));

    const userChoice = await this.promptModalService.openPrompt(title, message, pYes, pNo, 'warning');
    if (userChoice) {
      this.utilitiesService.showSpinner();
      this.alertMessageService.emptyMessages();
      try {
        let res: DatiAzienda;
        let aziendaDaSalvare = this.azienda;
        aziendaDaSalvare.sedeLegale = sede;
        aziendaDaSalvare.sedeLegale.sedeLegale = true;
        aziendaDaSalvare.sedeLegale.sedeOperativa = false;
        res = await this.silpService.putAziendaSilp(aziendaDaSalvare).toPromise();
        if (res) {
          this.azienda = res;
          sede.sedeLegale = true;
          sede.sedeOperativa = false;
          const index = this.elencoSedi.indexOf(sede);
          sedeLegale.sedeLegale = false;
          sedeLegale.sedeOperativa = true;
          const tmp = this.elencoSedi[0];
          this.elencoSedi[0] = sede;
          this.elencoSedi[index] = tmp;
          console.log('res: ' + JSON.stringify(res));
          console.log('legale rappresentante: ' + res.ilLegaleRapprDellaSedeLegale);
          if (res.ilLegaleRapprDellaSedeLegale) {
            this.formLegaleRappr.patchValue(res.ilLegaleRapprDellaSedeLegale);
          }
          this.alertMessageService.setSuccessSingleMsg('Variazione effettuata con successo');
        }
      } catch (e) {
        console.log(e);
        this.alertMessageService.setErrorMsg(e.error);
      } finally {
        this.utilitiesService.hideSpinner();
      }
    }
  }

  onchangeProvinciaDelleSedi() {
    if (isNullOrUndefined(this.f.provincia.value)) {
      // bisogna ricaricare tutte le sedi dell'elenco totale
      this.elencoSediOrdered = this.elencoSedi;
    } else {
      // bisogna selezionare solamente le sedi della provincia selezionata
      const sediDellaProvinciaSelezionata: Sede[] = [];
      this.elencoSedi.forEach((item: Sede) => {
        if (!isNullOrUndefined(item.comune) &&
          !isNullOrUndefined(item.comune.provincia)) {
          if (item.comune.provincia.codProvinciaMin === this.f.provincia.value.codProvinciaMin) {
            sediDellaProvinciaSelezionata.push(item);
          }
        }
      });
      this.elencoSediOrdered = sediDellaProvinciaSelezionata;
    }
  }

  onClickAnnullaDatiInseritiSede() {
    this.alertMessageService.emptyMessages();
    if (this.INS_MODE || isNullOrUndefined(this.dettaglioSede)) {
      this.formDettaglioSede.reset();
    } else {
      this.formDettaglioSede.get('tipoSede').patchValue((this.dettaglioSede.sedeOperativa) ? 'O' : 'L');
      this.formDettaglioSede.patchValue(this.dettaglioSede);
      const toponimoSede = this.listaToponimi.find((el: Toponimo) => {
        return el.dsComTToponimo === this.dettaglioSede.toponimoSede.dsComTToponimo;
      });
      this.fS.toponimoSede.patchValue(toponimoSede);
    }
  }

  onClickAnnullaModificaInserimentoSede() {
    this.alertMessageService.emptyMessages();
    this.modificaSede = false;
    this.inserisciSede = false;
    this.showDettaglio = false;
    this.nuovaSede = false;
  }

  async onClickFindAteco() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const decodifica: DecodificaGenerica = {
      codDecodifica: this.f.atecofin.get('codAtecofinMin').value,
      dsDecodifica: this.f.atecofin.get('dsComTAtecofin').value
    };
    try {
      const res = await this.decodificaService.postAtecofinDecodifica(decodifica).toPromise();
      let decodificaFinded: DecodificaGenerica;
      if (res && res.length === 1) {
        decodificaFinded = res[0];
      } else if (res && res.length > 1) {
        decodificaFinded = await this.promptModalService.openDecodificaPrompt(
          'Selezione ATECO',
          res,
          TYPE_DECODIFICA_GENERICA.ATECO,
          decodifica
        );
      }
      if (decodificaFinded) {
        const atecofin: Atecofin = {
          id: decodificaFinded.idDecodifica,
          codAtecofinMin: decodificaFinded.codDecodifica,
          dsComTAtecofin: decodificaFinded.dsDecodifica
        };
        this.f.atecofin.patchValue(atecofin);
      }
    } catch (e) {
      this.alertMessageService.setSingleErrorMsg(e);
      if (e.error) {
        this.alertMessageService.setWarningMsg(e.error);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  async onClickfindCcnl() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const decodifica: DecodificaGenerica = {
      codDecodifica: this.f.ccnl.get('codCcnlMin').value,
      dsDecodifica: this.f.ccnl.get('dsCcnl').value,
    };
    try {
      const res = await this.decodificaService.postCcnlDecodifica(decodifica).toPromise();
      let decodificaFinded: DecodificaGenerica;
      if (res && res.length === 1) {
        decodificaFinded = res[0];
      } else if (res && res.length > 1) {
        decodificaFinded = await this.promptModalService.openDecodificaPrompt(
          'Selezionare un Ccnl',
          res,
          TYPE_DECODIFICA_GENERICA.CCNL,
          decodifica
        );
      }
      if (decodificaFinded) {
        const ccnl: Ccnl = {
          id: decodificaFinded.idDecodifica,
          codCcnlMin: decodificaFinded.codDecodifica,
          dsCcnl: decodificaFinded.dsDecodifica,
        };
        this.f.ccnl.patchValue(ccnl);
      }
    } catch (e) {
      if (e.error && e.error.length > 0) {
        this.alertMessageService.setWarningMsg(e.error);
      } else {
        this.alertMessageService.setSingleErrorMsg(e);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
    // this.setInquadramentoMode(true);
  }

  checkValue(section) {
    switch (section) {
      case 'statiEsteriSede':
        this.fS.statiEsteri.controls.id.setValue(null);
        if (Utils.isNullOrUndefinedOrCampoVuoto(this.fS.statiEsteri.get('codNazioneMin').value) && Utils.isNullOrUndefinedOrCampoVuoto(this.fS.statiEsteri.get('dsStatiEsteri').value)) {
          this.utilitiesService.changeControlState(
            this.fS.comune,
            CONTROL_STATE.ENABLE,
            true
          );
        } else {
          this.utilitiesService.changeControlState(
            this.fS.comune,
            CONTROL_STATE.DISABLE,
            true
          );
        }
        break;
      case 'comuneSede':
        this.fS.comune.controls.id.setValue(null);
        if (Utils.isNullOrUndefinedOrCampoVuoto(this.fS.comune.get('codComuneMin').value) && Utils.isNullOrUndefinedOrCampoVuoto(this.fS.comune.get('dsComTComune').value)) {
          this.utilitiesService.changeControlState(
            this.fS.statiEsteri,
            CONTROL_STATE.ENABLE,
            true
          );
        } else {
          this.utilitiesService.changeControlState(
            this.fS.statiEsteri,
            CONTROL_STATE.DISABLE,
            true
          );
        }
        break;
      case 'statiEsteriNascita':
        this.fL.statiEsteri.controls.id.setValue(null);
        if (Utils.isNullOrUndefinedOrCampoVuoto(this.fL.statiEsteri.get('codNazioneMin').value) && Utils.isNullOrUndefinedOrCampoVuoto(this.fL.statiEsteri.get('dsStatiEsteri').value)) {
          this.utilitiesService.changeControlState(
            this.fL.comune,
            CONTROL_STATE.ENABLE,
            true
          );
        } else {
          this.utilitiesService.changeControlState(
            this.fL.comune,
            CONTROL_STATE.DISABLE,
            true
          );
        }
        break;
      case 'comuneNascita':
        this.fL.comune.controls.id.setValue(null);
        if (Utils.isNullOrUndefinedOrCampoVuoto(this.fL.comune.get('codComuneMin').value) && Utils.isNullOrUndefinedOrCampoVuoto(this.fL.comune.get('dsComTComune').value)) {
          this.utilitiesService.changeControlState(
            this.fL.statiEsteri,
            CONTROL_STATE.ENABLE,
            true
          );
        } else {
          this.utilitiesService.changeControlState(
            this.fL.statiEsteri,
            CONTROL_STATE.DISABLE,
            true
          );
        }
        break;
    }
  }

  async onClickSalvaAzienda() {

    let aziendaDaSalvare = this.form.getRawValue() as DatiAzienda;
    const sedeDaSalvare = this.formDettaglioSede.getRawValue() as Sede;
    aziendaDaSalvare.sedeLegale = sedeDaSalvare;
    const tipoSede = this.formDettaglioSede.get('tipoSede').value;
    let legaleRappr = this.formLegaleRappr.getRawValue() as LegaleRappr;
    if (legaleRappr.flgResidenzaItaliana) {
      if (legaleRappr.flgResidenzaItaliana === 'N') {
        legaleRappr.statusStraniero = null;
        legaleRappr.motivoPermesso = null;
        legaleRappr.numeroDocumento = null;
        legaleRappr.dtScadenzaPermessoSogg = null;
        legaleRappr.questura = null;
      }
    }
    aziendaDaSalvare.ilLegaleRapprDellaSedeLegale = legaleRappr;
    if (tipoSede === 'L') {
      aziendaDaSalvare.sedeLegale.sedeLegale = true;
      aziendaDaSalvare.sedeLegale.sedeOperativa = false;
    } else {
      aziendaDaSalvare.sedeLegale.sedeLegale = false;
      aziendaDaSalvare.sedeLegale.sedeOperativa = true;
    }
    try {
      this.utilitiesService.showSpinner();
      this.alertMessageService.emptyMessages();
      if (this.f.iscrAlboNum.value && this.f.iscrAlboData.value) {
        aziendaDaSalvare.codLavoroTemp = this.f.iscrAlboNum.value + this.f.iscrAlboData.value.toLocaleDateString();
      }
      const res = await this.silpService.putAziendaSilp(aziendaDaSalvare).toPromise();
      if (res) {
        this.alertMessageService.setSuccessSingleMsg('Salvataggio eseguito con successo');
        this.showForm = false;
      }
    } catch (e) {
      if (e.error && e.error.length > 0) {
        if (e.error.length === 1) {
          if (e.error[0].code === 'COM-COM-W-1202') {
            this.alertMessageService.setWarningMsg(e.error);
            this.showForm = false;
          } else if (e.error[0].code === 'NF00001') {
            const apiError: ApiError = {
              errorMessage: e.error[0].errorMessage + ' - Codifica non valida '
            }
            const listMessages: ApiError[] = [apiError];
            this.alertMessageService.setWarningMsg(listMessages);
          } else {
            this.alertMessageService.setErrorMsg(e.error);
          }
        } else {
          this.alertMessageService.setErrorMsg(e.error);
        }
      } else {
        this.alertMessageService.setSingleErrorMsg(e);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  private translate(key: string) {
    return this.translateService.instant(key);
  }

  onClickSoggiornanteItalia(valoreSelezionato: string) {
    if (!this.READ_MODE && valoreSelezionato === 'S') {
      this.fL.statusStraniero.setValidators(Validators.required);
      this.fL.numeroDocumento.setValidators(Validators.required);
      this.fL.motivoPermesso.setValidators(Validators.required);
      this.fL.dtScadenzaPermessoSogg.setValidators([this.datePicker, Validators.required]);
      // this.fL.questura.setValidators(Validators.required);
    } else {
      this.fL.statusStraniero.clearValidators();
      this.fL.numeroDocumento.clearValidators();
      this.fL.motivoPermesso.clearValidators();
      this.fL.dtScadenzaPermessoSogg.clearValidators();
      this.fL.questura.clearValidators();
    }
    this.fL.statusStraniero.updateValueAndValidity();
    this.fL.numeroDocumento.updateValueAndValidity();
    this.fL.motivoPermesso.updateValueAndValidity();
    this.fL.dtScadenzaPermessoSogg.updateValueAndValidity();
    this.fL.questura.updateValueAndValidity();
  }

  onClickExtraCom(valoreSelezionato: string) {
    if (!this.READ_MODE && valoreSelezionato === 'S') {
      this.fL.dsCognome.setValidators(Validators.required);
      this.fL.dsNome.setValidators(Validators.required);
      this.fL.dtNascita.setValidators(Validators.required);
      this.fL.sesso.setValidators(Validators.required);
      this.fL.cittadinanza.setValidators(Validators.required);
      /** BISOGNA CARICARE SOLAMENTE LE CITTADINANZE EXTRA_UE */
      this.caricaListaCittadinanzaFiltrata('N');
      this.fL.cittadinanza.setValue(null);
    } else {
      this.fL.dsCognome.clearValidators();
      this.fL.dsNome.clearValidators();
      this.fL.dtNascita.clearValidators();
      this.fL.cittadinanza.clearValidators();
      this.fL.sesso.clearValidators();
      this.onClickSoggiornanteItalia('N');
      /** BISOGNA CARICARE SOLAMENTE LE CITTADINANZE UE */
      this.caricaListaCittadinanzaFiltrata('S');
      this.fL.cittadinanza.setValue(null);
    }
    this.fL.dsCognome.updateValueAndValidity();
    this.fL.dsNome.updateValueAndValidity();
    this.fL.dtNascita.updateValueAndValidity();
    this.fL.cittadinanza.enable();
    this.fL.cittadinanza.updateValueAndValidity();
    this.fL.sesso.updateValueAndValidity();

  }
  caricaListaCittadinanzaFiltrata(flg: string) {
    this.listaCittadinanzaFiltrata = [];
    for (let i = 0; i < this.listaCittadinanza.length; i++) {
      const element = this.listaCittadinanza[i];
      if (element.flgUe === flg) {
        this.listaCittadinanzaFiltrata.push(element);
      }
    }
  }

  async onClickFindAziendaSuAaep() {
    this.alertMessageService.emptyMessages();
    this.utilitiesService.showSpinner();
    let res: DatiAzienda;
    if (this.f.cfAzienda) {
      try {
        res = await this.silpService.getAziendaAaep(this.f.cfAzienda.value).toPromise();
        if (!res) {
          this.alertMessageService.setSingleWarningMsg('Nessuna azienda trovata su AAEP');
        } else {
          this.azienda = res;
          console.log('azienda trovata: ' + JSON.stringify(this.azienda));
          this.patchValuesInForm(this.azienda);
          if (this.azienda.elencoSedi && this.azienda.elencoSedi.length > 0
            && this.azienda.sedeLegale ) {
            this.formDettaglioSede.patchValue(this.azienda.sedeLegale);
          }
        }
      } catch (e) {
        console.log(e);
        this.alertMessageService.setSingleWarningMsg('Nessuna azienda trovata su AAEP');
        // this.alertMessageService.setErrorMsg(e.error);
      } finally {
        this.utilitiesService.hideSpinner();
      }
    }
  }


  onClickSelectStatusStraniero() {
    if (this.formLegaleRappr.get('statusStraniero').value !== '' && this.formLegaleRappr.get('statusStraniero').value !== null) {
      switch (this.formLegaleRappr.get('statusStraniero').value.codStatusMin) {
        case '1': // PERMESSO
        case '2': // CARTA
        case '7': // PERMESSO DI SOGGIORNO CE PER SOGGIORNANTI DI LUNGO PERIODO
          this.fL.questura.setValidators(Validators.required);
          this.utilitiesService.changeControlState(
            this.fL.questura,
            CONTROL_STATE.ENABLE,
            true
          );
          this.fL.questura.updateValueAndValidity();
          this.flgControlloDataScadenzaPerm = true; // DEVO controllare se la data inserita e' minore del 2000
          break;
        case '3': // IN RINNOVO
        case '6': // ALTRO PROVVEDIMENTO
          this.flgControlloDataScadenzaPerm = true; // DEVO controllare se la data inserita e' minore del 2000
          this.fL.questura.clearValidators();
          this.utilitiesService.changeControlState(
            this.fL.questura,
            CONTROL_STATE.DISABLE,
            true
          );
          this.fL.questura.updateValueAndValidity();
          break;
        case '4': // IN ATTESA DI PERMESSO
          this.fL.dtScadenzaPermessoSogg.setValidators([this.datePicker, Validators.required]);
          // this.fL.dtScadenzaPermessoSogg.patchValue((this.lavoratoreSilp && this.lavoratoreSilp.dtScadenzaPermessoSogg) ? this.lavoratoreSilp.dtScadenzaPermessoSogg : new Date('01/01/1900'));
          this.fL.dtScadenzaPermessoSogg.patchValue(new Date('01/01/1900'));
          this.fL.dtScadenzaPermessoSogg.updateValueAndValidity();
          this.fL.motivoPermesso.setValidators(Validators.required);
          this.fL.motivoPermesso.updateValueAndValidity();
          this.flgControlloDataScadenzaPerm = false; // non devo controllare se la data inserita e' minore del 2000

          this.fL.questura.clearValidators();
          this.utilitiesService.changeControlState(
            this.fL.questura,
            CONTROL_STATE.DISABLE,
            true
          );
          this.fL.questura.updateValueAndValidity();

          break;
        case '5': // CARTA PERMANENTE
          this.fL.dtScadenzaPermessoSogg.clearValidators();
          this.fL.dtScadenzaPermessoSogg.setValidators([this.datePicker]);
          // this.fL.dtScadenzaPermessoSogg.patchValue((this.lavoratoreSilp && this.lavoratoreSilp.dtScadenzaPermessoSogg) ? this.lavoratoreSilp.dtScadenzaPermessoSogg : new Date('12/31/2099'));
          this.fL.dtScadenzaPermessoSogg.patchValue(new Date('12/31/2099'));
          this.fL.dtScadenzaPermessoSogg.updateValueAndValidity();
          this.fL.motivoPermesso.clearValidators();
          this.fL.motivoPermesso.updateValueAndValidity();
          this.flgControlloDataScadenzaPerm = true; // DEVO controllare se la data inserita e' minore del 2000
          this.fL.questura.setValidators(Validators.required);
          this.utilitiesService.changeControlState(
            this.fL.questura,
            CONTROL_STATE.ENABLE,
            true
          );
          this.fL.questura.updateValueAndValidity();
          break;
        default:
          this.flgControlloDataScadenzaPerm = true; // DEVO controllare se la data inserita e' minore del 2000
          break;
      }
    } else {
      this.fL.statusStraniero.setValue(null);
      this.flgControlloDataScadenzaPerm = false; // non devo controllare se la data inserita e' minore del 2000
    }
  }

  get controlliDisabilitazioneSalvataggio() {
    if (this.fL.dtNascita.value > this.now) {
      return true;
    }
    if (this.flgControlloDataScadenzaPerm && this.fL.dtScadenzaPermessoSogg.value < this.data2000) {
      return true;
    }
    return false;
  }

  setValidatorsIscrizAlboNumAlboData(){
    this.f.iscrAlboNum.clearValidators();
    this.f.iscrAlboData.clearValidators();
    const iscrAlboNum = this.f.iscrAlboNum.value;
    const iscrAlboData = this.f.iscrAlboData.value;
    if(!Utils.isNullOrUndefinedOrCampoVuoto(iscrAlboNum) || !Utils.isNullOrUndefinedOrCampoVuoto(iscrAlboData)){
      this.f.iscrAlboNum.setValidators(Validators.required);
      this.f.iscrAlboData.setValidators([this.datePickerIscrAlboData,Validators.required]);
    }
    this.f.iscrAlboNum.updateValueAndValidity();
    this.f.iscrAlboData.updateValueAndValidity();

  }

  setValidatorMotivoInvalida(){
    const tipoSede = this.fS.tipoSede.value;
    const flgValida = this.fS.flgValida.value
    this.fS.motivoInvalida.clearValidators();
    if(tipoSede === 'S')
      this.fS.motivoInvalida.reset();
    if(tipoSede === 'O' && flgValida === 'N')
      this.fS.motivoInvalida.setValidators(Validators.required);

    this.fS.motivoInvalida.updateValueAndValidity();

  }

  findInvalidControls() {
    let controls = this.form.controls;
    console.log('form');
    for (const name in controls) {
        if (controls[name].invalid) {
           console.log('invalid: ' + name);
        }
    }
    console.log('formLegaleRappr');
    controls = this.formLegaleRappr.controls;
    for (const name in controls) {
        if (controls[name].invalid) {
           console.log('invalid: ' + name);
        }
    }
    console.log('formDettaglioSede');
    controls = this.formDettaglioSede.controls;
    for (const name in controls) {
        if (controls[name].invalid) {
           console.log('invalid: ' + name);
        }
    }
  }
}
