/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { TipoDelega } from './../../../../comonlapi/model/tipoDelega';
import { DelegaService } from './../../../../comonlapi/api/delega.service';
import { CaricaPersonaPv } from './../../../../comonlapi/model/caricaPersonaPv';
import { Delegato } from './../../../../comonlapi/model/delegato';
import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { TYPE_DECODIFICA_GENERICA } from 'src/app/constants';
import { Comune, DecodificaGenerica, DecodificaService, Delega, DelegatoImpresa, AnagraficaDelegatoService, StatiEsteri, AnagraficaDelegato } from 'src/app/modules/comonlapi';
import { LogService, UtilitiesService } from 'src/app/services';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { TranslateService } from '@ngx-translate/core';
import { marker } from '@biesbjerg/ngx-translate-extract-marker';
import { Utils } from 'src/app/utils';

@Component({
  selector: 'comonl-dettaglio-delega',
  templateUrl: './dettaglio-delega.component.html',
  styleUrls: ['./dettaglio-delega.component.scss']
})
export class DettaglioDelegaComponent implements OnInit {

  @Input() list: DecodificaGenerica[];

  form: FormGroup;
  formCf: FormGroup;

  listaCariche: CaricaPersonaPv[] = [];
  listaCfImprese: DelegatoImpresa[] = [];

  delega: Delega;

  delegato: Delegato;
  delegatoAggiornato: AnagraficaDelegato;
  provinciaDelegante;

  showDettaglio = false;
  INS_MODE: boolean;
  EDIT_MODE: boolean;
  READ_MODE: boolean;


  get f() {
    return this.form.controls as any;
  }

  get fCf() {
    return this.formCf.controls as any;
  }

  // get COMUNE_SEDE_VUOTO() {
  //   return (Utils.isNullOrUndefinedOrCampoVuoto(this.f.cfDelegato.value) || Utils.isNullOrUndefinedOrCampoVuoto(this.fS.comune.get('dsComTComune').value));
  // }

  constructor(
    private logService: LogService,
    private decodificaService: DecodificaService,
    private utilitiesService: UtilitiesService,
    private delegaService: DelegaService,
    private promptModalService: PromptModalService,
    private comonlStorageService: ComonlStorageService,
    private translateService: TranslateService,
    private alertMessageService: AlertMessageService,
    private anagraficaDelegatoService: AnagraficaDelegatoService,
    private location: Location,
    private router: Router) {

      this.EDIT_MODE = this.router.getCurrentNavigation().extras.state.mode === 'modifica';
      this.INS_MODE = this.router.getCurrentNavigation().extras.state.mode === 'inserimento';
      this.READ_MODE = this.router.getCurrentNavigation().extras.state.mode === 'visualizza';
      this.delega = this.router.getCurrentNavigation().extras.state.delega;
      console.log('delega: ' + JSON.stringify(this.delega));

      this.initForm();
  }

  async ngOnInit() {
    await this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    try {
      const [
        carica,
      ] = await Promise.all([
        this.decodificaService.getCaricaPersonaPv().toPromise(),
      ]);
      this.listaCariche = carica;
      this.listaCfImprese = await this.anagraficaDelegatoService.getDelegatoImpresaByIdAnagraficadelegato(this.delega.cfDelegato, 'D').toPromise();
    } catch (e) {
    } finally {
      await this.utilitiesService.hideSpinner();
    }
    if (!this.INS_MODE) {
      this.patchValuesInForm();
    }
    if (this.EDIT_MODE) {
      // Popolamento combo
      this.form.get('carica').patchValue(this.listaCariche.find((item: CaricaPersonaPv) => {
        return item.id === this.delega.carica;
      }).descrizione);
      this.fCf.cfDelegatoImpresa.patchValue(this.delega.cfDelegato);
      // this.form.get('codFiscImpresa').patchValue(this.listaCfImprese.find((item: DelegatoImpresa) => {
      //     return item.id === this.delega.id;
      //   }).id.cfImpresa);

      if (this.delega.statoDelega.dsStatoDelega !== 'INSERITA') {
        this.form.disable();
        this.f.cfDelegatoImpresa.disable();
      }
    }
  }

  initForm() {
    this.formCf = new FormGroup({
      cfDelegatoImpresa: new FormControl({value: null, disabled: this.READ_MODE}, Validators.required),
    });
    this.form = new FormGroup({
      id: new FormControl(),
      cfDelegante: new FormControl({value: null, disabled: this.READ_MODE}, Validators.compose([Validators.required, Validators.minLength(11), Validators.maxLength(16)])),
      cognomeDelegante: new FormControl({value: null, disabled: this.READ_MODE}, Validators.required),
      nomeDelegante: new FormControl({value: null, disabled: this.READ_MODE}, Validators.required),
      comDelegante: new FormGroup({
        id: new FormControl(),
        codComuneMin: new FormControl({value: null, disabled: this.READ_MODE}, Validators.required),
        dsComTComune: new FormControl({value: null, disabled: this.READ_MODE}, Validators.required)
      }),
      indirizzoDelegante: new FormControl({value: null, disabled: this.READ_MODE}, Validators.required),
      capDelegante: new FormControl({value: null, disabled: this.READ_MODE }),
      telefonoDelegante: new FormControl({value: null, disabled: this.READ_MODE}),
      emailDelegante: new FormControl({value: null, disabled: this.READ_MODE}),
      carica: new FormControl({value: null, disabled: this.READ_MODE}, Validators.required),
      cfDelegato: new FormControl({value: null, disabled: true}, Validators.required),
      cognomeDelegato: new FormControl({value: null, disabled: true}),
      nomeDelegato: new FormControl({value: null, disabled: true}),
      pivaDelegato: new FormControl({value: null, disabled: true}),
      comDelegato: new FormGroup({
        id: new FormControl(),
        codComuneMin: new FormControl({value: null, disabled: true}),
        dsComTComune: new FormControl({value: null, disabled: true})
      }),
      indirizzoDelegato: new FormControl({value: null, disabled: true}),
      capDelegato: new FormControl({value: null, disabled: true}),
      telefonoDelegato: new FormControl({value: null, disabled: true}),
      faxDelegato: new FormControl({value: null, disabled: true}),
      emailDelegato: new FormControl({value: null, disabled: true}),
      codFiscImpresa: new FormControl({value: null, disabled: this.READ_MODE }, Validators.required),
      cfImpresa: new FormControl({value: null, disabled: true}),
      pivaImpresa: new FormControl({value: null, disabled: this.READ_MODE}),
      denominazioneImpresa: new FormControl({value: null, disabled: true}),
      dtRevoca: new FormControl({value: null, disabled: true}),
      statoDelega: new FormGroup({
        id: new FormControl(),
        dsStatoDelega: new FormControl({value: this.INS_MODE ? 'INSERITA' : null, disabled: true}, Validators.required)
      }),
      dtProtocollo: new FormControl({value: null, disabled: true}),
      annoProtocollo: new FormControl({value: null, disabled: true}),
      numeroProtocollo: new FormControl({value: null, disabled: true})
    });
  }

  patchValuesInForm() {
    if (!this.INS_MODE) {
      this.form.patchValue(this.delega);
      this.formCf.get('cfDelegatoImpresa').patchValue(this.delega.cfDelegato);
    }
  }

  async onClickFindComune(section: string) {
    let decodifica: DecodificaGenerica;
    this.utilitiesService.showSpinner();
    if (section === 'delegante') {
      decodifica = {
        codDecodifica: this.f.comDelegante.get('codComuneMin').value,
        dsDecodifica: this.f.comDelegante.get('dsComTComune').value
      };
    } else if (section === 'delegato') {
      decodifica = {
        codDecodifica: this.f.comDelegato.get('codComuneMin').value,
        dsDecodifica: this.f.comDelegato.get('dsComTComune').value
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
         id: decodificaFinded.idDecodifica,
         codComuneMin: decodificaFinded.codDecodifica,
         dsComTComune: decodificaFinded.dsDecodifica
       };
       if (section === 'delegante') {
        this.f.comDelegante.patchValue(comune);
       } else if (section === 'delegato') {
        this.f.comDelegato.patchValue(comune);
       }
     }
     console.log(decodificaFinded);
     this.provinciaDelegante = decodificaFinded.dsDecodifica;
    //  console.log(this.provinciaDelegante);
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

  async onClickFindStatoEstero() {
    let decodifica: DecodificaGenerica;
    this.utilitiesService.showSpinner();
    decodifica = {
      codDecodifica: this.f.statiEsteriDelegante.get('codNazioneMin').value,
      dsDecodifica: this.f.statiEsteriDelegante.get('dsStatiEsteri').value
    };
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
        id: decodificaFinded.idDecodifica,
        codNazioneMin: decodificaFinded.codDecodifica,
        dsStatiEsteri: decodificaFinded.dsDecodifica
      };
      this.f.statiEsteriDelegante.patchValue(statoEstero);
     }
    } catch (e) {
      this.alertMessageService.setWarningMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  async onClickFindDelegato(cf: string) {
    this.utilitiesService.showSpinner();
    try {
      const delegato = await this.anagraficaDelegatoService.getAnagraficaDelegatoByCodiceFiscale(cf).toPromise();
      // console.log('delegato: ' + JSON.stringify(delegato));
      this.listaCfImprese = await this.anagraficaDelegatoService.getDelegatoImpresaByIdAnagraficadelegato(cf, 'D').toPromise();
      this.f.cfDelegato.patchValue(cf);
      this.f.cognomeDelegato.patchValue(delegato.cognome);
      this.f.nomeDelegato.patchValue(delegato.nome);
      this.f.pivaDelegato.patchValue(delegato.pivaDelegato);
      //chiedere ad Annalisa/Luisa
      if(delegato.comuneDom){
        this.f.comDelegato.get('id').patchValue(delegato.comuneDom.id);
        this.f.comDelegato.get('codComuneMin').patchValue(delegato.comuneDom.codComuneMin);
        this.f.comDelegato.get('dsComTComune').patchValue(delegato.comuneDom.dsComTComune);
        
      }else if(delegato.comuneRes){
        this.f.comDelegato.get('id').patchValue(delegato.comuneRes.id);
        this.f.comDelegato.get('codComuneMin').patchValue(delegato.comuneRes.codComuneMin);
        this.f.comDelegato.get('dsComTComune').patchValue(delegato.comuneRes.dsComTComune);
      }
      this.f.indirizzoDelegato.patchValue(delegato.indirizzoRes);
      this.f.capDelegato.patchValue(delegato.capDom !== null ? delegato.capDom : delegato.capRes !== null ? delegato.capRes : null);
      this.f.telefonoDelegato.patchValue(delegato.tel);
      this.f.faxDelegato.patchValue(delegato.fax);
      this.f.emailDelegato.patchValue(delegato.email);
      this.delegatoAggiornato = delegato;
    } catch (e) {
      console.log(e);
      if(e.error && e.error.length > 0){
        this.alertMessageService.setWarningMsg(e.error);
        
      }else{
        this.alertMessageService.setSingleErrorMsg(e);

      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  compilaImpresa(codFiscImpresa) {
    this.f.cfImpresa.patchValue(codFiscImpresa.value.id.cfImpresa);
    this.f.denominazioneImpresa.patchValue(codFiscImpresa.value.denominazione);
  }

  onClickGoBackToRicerca() {
    this.location.back();
  }

  async aggiornaDelega(typeMessage) {
    this.delega = this.form.getRawValue() as Delega;
    if (!this.INS_MODE) {
      console.log('delega da aggiornare: ' + JSON.stringify(this.delega));
      const title = this.translate(marker('SIDEBAR.ACCREDITAMENTO_DELEGHE'));
      const message = this.translate(marker(typeMessage));
      const pYes = this.translate(marker('APP.CONFIRM'));
      const pNo = this.translate(marker('APP.BACK'));

      const userChoice = await this.promptModalService.openPrompt(title, message, pYes, pNo, 'warning');
      if (userChoice) {
        this.utilitiesService.showSpinner();
        this.alertMessageService.emptyMessages();
        try {
          let res: Delega;
          res = await (await this.delegaService.aggiornaDelega(this.delega).toPromise()).delega;
          console.log('res delega: ' + JSON.stringify(res));
          if (res) {
            this.delega = res;
            this.alertMessageService.setSuccessSingleMsg('Salvataggio effettuato con successo');
          }
        } catch (e) {
          console.log(e);
          this.alertMessageService.setErrorMsg(e.error);
        } finally {
          this.utilitiesService.hideSpinner();
        }
      }
    } else {
      this.utilitiesService.showSpinner();
      this.alertMessageService.emptyMessages();
      try {
        let res: Delega;
        const tipoDelega: TipoDelega = {
          id: '1'
        };
        const caricaToSend = this.listaCariche.filter(el => {
          return el.descrizione === this.delega.carica;
        });
        const carica: CaricaPersonaPv = {
          id: caricaToSend[0].id,
          descrizione: this.delega.carica
        };
        // VIENE SETTATO L'ID DEL TIPO DELEGA A 1
        this.delega.statoDelega.id = '1';
        this.delega.tipoDelega = tipoDelega;
        // DEVE ESSERE INSERITO L'ID DELLA CARICA SU DB
        this.delega.carica = carica.id;
        this.delega.pvDelegato = this.delegatoAggiornato.comuneRes.provincia.dsTarga;
        res = await this.delegaService.aggiornaDelega(this.delega).toPromise();
        if (res) {
          this.delega = res;
          this.f.dtRevoca.patchValue(res.dtRevoca);
          this.f.dtProtocollo.patchValue(res.dtProtocollo);
          this.f.annoProtocollo.patchValue(res.annoProtocollo);
          this.f.numeroProtocollo.patchValue(res.numeroProtocollo);
          this.alertMessageService.setSuccessSingleMsg('Salvataggio effettuato con successo');
        }
      } catch (e) {
        console.log(e);
        this.alertMessageService.setErrorMsg(e.error);
      } finally {
        this.utilitiesService.hideSpinner();
      }
    }
  }

  async onClickOpenModalOperazione(delega, stato, typeMessage) {
    this.delega = delega;
    const title = this.translate(marker('SIDEBAR.ACCREDITAMENTO_DELEGHE'));
    const message = this.translate(marker(typeMessage));
    const pYes = this.translate(marker('APP.CONFIRM'));
    const pNo = this.translate(marker('APP.BACK'));

    const userChoice = await this.promptModalService.openPrompt(title, message, pYes, pNo, 'warning');

    try {
      if (userChoice) {
        switch (stato) {
          case 'revoca':
            this.delega.statoDelega.dsStatoDelega = 'REVOCATA';
            this.delega.statoDelega.id = '4';
            this.aggiornaStato();
            break;
          case 'elimina':
            this.delega.statoDelega.dsStatoDelega = 'ANNULLATA';
            this.delega.statoDelega.id = '2';
            this.aggiornaStato();
            break;
          case 'valida':
            this.delega.statoDelega.dsStatoDelega = 'VALIDATA';
            this.delega.statoDelega.id = '3';
            await this.aggiornaStato();
            this.f.annoProtocollo.patchValue(this.delega.annoProtocollo);
            this.f.numeroProtocollo.patchValue(this.delega.numeroProtocollo);
            this.f.dtProtocollo.patchValue(this.delega.dtProtocollo);

            break;
        }
      }
    } catch (e) {
      this.alertMessageService.setWarningMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  async aggiornaStato() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    try {
      let res: Delega;
      res = await this.delegaService.aggiornaStatoDelega(this.delega).toPromise();
      if (res) {
        this.delega = res;
        this.alertMessageService.setSuccessSingleMsg('Aggiornamento effettuato con successo');
      }
    } catch (e) {
      console.log(e);
      this.alertMessageService.setErrorMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
      this.f.statoDelega.get('dsStatoDelega').patchValue(this.delega.statoDelega.dsStatoDelega);
      this.f.dtRevoca.patchValue(this.delega.dtRevoca);
    }
  }

  private translate(key: string) {
    return this.translateService.instant(key);
  }
}
