/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { marker } from '@biesbjerg/ngx-translate-extract-marker';
import { TranslateService } from '@ngx-translate/core';
import { ANAGRAFICA_DELEGATO, CONTROL_STATE, TYPE_DECODIFICA_GENERICA } from 'src/app/constants';
import { AnagraficaDelegato, AnagraficaDelegatoService, AnagraficaGenerica, CaricaPersonaPv, Cittadinanza, CommonService, Comune, DecodificaGenerica, DecodificaService, DelegatoImpresa, DelegatoImpresaService, SoggettoAbilitatoService, StatiEsteri } from 'src/app/modules/comonlapi';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { UtilitiesService } from 'src/app/services';
import { Utils } from 'src/app/utils';

@Component({
  selector: 'comonl-form-accreditamento-consulente',
  templateUrl: './form-accreditamento-consulente.component.html',
  styleUrls: ['./form-accreditamento-consulente.component.scss']
})
export class FormAccreditamentoConsulenteComponent implements OnInit {


  form: FormGroup;
  formRicercaAzienda: FormGroup;
  cittadinanze: Cittadinanza[] = [];
  anagraficaDelegato: AnagraficaDelegato;
  mode: string;
  caricaPersonaPvs: CaricaPersonaPv[] = [];
  delegatiImpresa: DelegatoImpresa[] = [];
  showForm: boolean;

  get VIEW_MODE() { return this.mode === 'view'; }
  get EDIT_MODE() { return this.mode === 'edit'; }
  get INS_MODE() { return this.mode === 'ins'; }


  get f() { return this.form.controls as any; }
  get fRicercaAzienda() { return this.formRicercaAzienda.controls as any; }


  get LUOGO_NASCITA_VALID() {
    return (
        (
          !Utils.isNullOrUndefinedOrCampoVuoto(this.f.comuneNasc.get('codComuneMin').value) &&
          !Utils.isNullOrUndefinedOrCampoVuoto(this.f.comuneNasc.get('dsComTComune').value)
        )
       ||
        (
          !Utils.isNullOrUndefinedOrCampoVuoto(this.f.statiEsteri.get('codNazioneMin').value) &&
          !Utils.isNullOrUndefinedOrCampoVuoto(this.f.statiEsteri.get('dsStatiEsteri').value)
        )
    );
  }
  get SOGGETTO_ABILITATO_VALID() {
    // return (
    //     (
    //       !Utils.isNullOrUndefinedOrCampoVuoto(this.f.soggettoAbilitato.get('cfSoggetto').value)
    //       && (this.f.id.get('tipoAnagrafica').value === ANAGRAFICA_DELEGATO.TIPO.CONSULENTE)
    //     )
    // )
    if (this.f.id.get('tipoAnagrafica').value === ANAGRAFICA_DELEGATO.TIPO.CONSULENTE) {
      return !Utils.isNullOrUndefinedOrCampoVuoto(this.f.soggettoAbilitato.get('cfSoggetto').value);
    }
    return true;
  }


  constructor(
    private utilitiesService: UtilitiesService,
    private decodificaService: DecodificaService,
    private promptModalService: PromptModalService,
    private alertMessageService: AlertMessageService,
    private soggettoAbilitatoService: SoggettoAbilitatoService,
    private anagraficaDelegatoService: AnagraficaDelegatoService,
    private commonService: CommonService,
    private router: Router,
    private location: Location,
    private translateService: TranslateService,
    private delegatoImpresaService: DelegatoImpresaService
  ) {

    const state = this.router.getCurrentNavigation().extras.state;
    this.anagraficaDelegato = state.anagraficaDelegato;
    this.mode = state.mode;
  }

  async ngOnInit() {
    this.utilitiesService.showSpinner();
    this.cittadinanze = await this.decodificaService.getCittadinanza().toPromise();
    try {
      const [cittadinanze, cariche] = await Promise.all([
        this.decodificaService.getCittadinanza().toPromise(),
        this.decodificaService.getCaricaPersonaPv().toPromise(),
      ]);
      if (!this.INS_MODE && this.anagraficaDelegato.id.tipoAnagrafica !== ANAGRAFICA_DELEGATO.TIPO.CONSULENTE) {
        const idAnagraficaDelegato = this.anagraficaDelegato.id;
        this.delegatiImpresa = await this.anagraficaDelegatoService.getDelegatoImpresaByIdAnagraficadelegato(idAnagraficaDelegato.cfDelegato, idAnagraficaDelegato.tipoAnagrafica).toPromise();
      }
      this.caricaPersonaPvs = cariche;
      this.cittadinanze = cittadinanze;
      this.initForm();
      this.setMode();
      this.showForm = true;
    } catch (e) {
      this.alertMessageService.setErrorMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }


  private initForm() {
    this.form = new FormGroup({
      id: new FormGroup({
        cfDelegato: new FormControl(null,
          Validators.compose([Validators.required, Validators.pattern('[A-Z]{6}[0-9LMNPQRSTUV]{2}[A-Z][0-9LMNPQRSTUV]{2}[A-Z][0-9LMNPQRSTUV]{3}[A-Z]')])
        ),
        tipoAnagrafica: new FormControl(
          this.mode === 'ins' ? ANAGRAFICA_DELEGATO.TIPO.CONSULENTE : this.anagraficaDelegato.id.tipoAnagrafica,
          Validators.required)
      }),
      idUserInsert: new FormControl(),
      dtInsert: new FormControl(),
      dataAnnullamento: new FormControl({value: null, disabled: true}),
      personalizzazione: new FormGroup({
        id: new FormControl('TO')
      }),
      cognome: new FormControl(null, Validators.required),
      nome: new FormControl(null, Validators.required),
      sesso: new FormControl(null, Validators.required),
      dtNascita: new FormControl(null, Validators.required),
      comuneNasc: new FormGroup({
        id: new FormControl(null),
        codComuneMin: new FormControl(null, Validators.minLength(3)),
        dsComTComune: new FormControl(null, Validators.minLength(3))
      }),
      statiEsteri: new FormGroup({
        id: new FormControl(null),
        codNazioneMin: new FormControl(null, Validators.minLength(3)),
        dsStatiEsteri: new FormControl(null, Validators.minLength(3))
      }),
      cittadinanza: new FormControl(null, Validators.required),
      comuneRes: new FormGroup({
        id: new FormControl(null),
        codComuneMin: new FormControl(null,
          Validators.compose([
            Validators.required,
            Validators.minLength(3)
        ])),
        dsComTComune: new FormControl(null, Validators.compose([
          Validators.required,
          Validators.minLength(3)
      ]))
      }),
      indirizzoRes: new FormControl(null,
        Validators.compose([Validators.minLength(3), Validators.required])),
      capRes: new FormControl(null),
      comuneDom: new FormGroup({
        id: new FormControl(null),
        codComuneMin: new FormControl(null,
            Validators.minLength(3)
          ),
        dsComTComune: new FormControl(null,
          Validators.minLength(3)
      ),
      }),
      indirizzoDom: new FormControl(null),
      capDom: new FormControl(null),
      fax: new FormControl(null),
      email: new FormControl(null,Validators.compose([Validators.required])),
      tel: new FormControl(null),
      soggettoAbilitato: new FormGroup({
        id: new FormControl(null),
        cfSoggetto: new FormControl({value: null, disabled: true}),
        partitaIva: new FormControl({value: null, disabled: true}),
        cognomeDenominazione: new FormControl({value: null, disabled: true}),
        indirizzo: new FormControl({value: null, disabled: true}),
        comune: new FormGroup({
          id: new FormControl(null),
          dsComTComune: new FormControl({value: null, disabled: true}),
          provincia: new FormGroup({
            id: new FormControl(),
            dsComTProvincia: new FormControl({value: null, disabled: true})
          })
        }),
        tel: new FormControl({value: null, disabled: true}),
        fax: new FormControl({value: null, disabled: true}),
        email: new FormControl({value: null, disabled: true}),
        cap: new FormControl({value: null, disabled: true}),
        tipoSoggettoAbilitato: new FormGroup({
          id: new FormControl(null),
          dsComTipoSoggettoAbilitato: new FormControl({value: null, disabled: true})
        }),
      })
    });

    if (this.anagraficaDelegato && this.anagraficaDelegato.id.tipoAnagrafica === 'D') {
      this.formRicercaAzienda = new FormGroup({
        cfAzienda: new FormControl(null, Validators.required)
      });
    } else if (this.anagraficaDelegato && this.anagraficaDelegato.id.tipoAnagrafica === 'E') {
      this.formRicercaAzienda = new FormGroup({
        cfAzienda: new FormControl(null, Validators.required),
        tipoCarica: new FormControl(null, Validators.required)
      });
    }
  }

  private setMode() {
     if (!this.INS_MODE) {
      this.form.patchValue({
        ...this.anagraficaDelegato,
        soggettoAbilitato: this.anagraficaDelegato.soggettoAbilitato || {}
      });
      this.f.id[CONTROL_STATE.DISABLE]();
     }
     if (this.VIEW_MODE) {
       this.form[CONTROL_STATE.DISABLE]();
     }
  }

  async onClickPreCompila() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    try {
      const res: AnagraficaGenerica = await this.commonService.preCompilaAnagraficaByCf(this.f.id.controls.cfDelegato.value).toPromise();
      if (res) {
        this.f.sesso.setValue(res.sesso);
        this.f.dtNascita.setValue(res.dataNascita);
        if (res.comune) {
          this.f.comuneNasc.patchValue(res.comune);
          this.f.statiEsteri.reset();
        }
        if (res.statiEsteri) {
          this.f.statiEsteri.patchValue(res.statiEsteri);
          this.f.comuneNasc.reset();
        }
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
  }


  async onClickFindComune(control: AbstractControl, isComuneNas?: boolean) {
    this.alertMessageService.emptyMessages();
    this.utilitiesService.showSpinner();
    const decodifica: DecodificaGenerica = {
      codDecodifica: control.get('codComuneMin').value,
      dsDecodifica: control.get('dsComTComune').value
    };
    try {
      const res = await  this.decodificaService.postComuneDecodifica(decodifica).toPromise();
      let docodificaFinded: DecodificaGenerica;
      if (res && res.length === 1) {
       docodificaFinded = res[0];
      } else if (res && res.length > 1) {
       docodificaFinded = await this.promptModalService.openDecodificaPrompt('Selezionare un comune', res, TYPE_DECODIFICA_GENERICA.COMUNE, decodifica);
      }
      if (docodificaFinded) {
        const comune: Comune = {
          id: docodificaFinded.idDecodifica,
          codComuneMin: docodificaFinded.codDecodifica,
          dsComTComune: docodificaFinded.dsDecodifica
        };
        control.patchValue(comune);
      }
     } catch (e) {
       if(e.error && e.error.length > 0){
         this.alertMessageService.setWarningMsg(e.error);
       }else{
        if(e != 0)
          this.alertMessageService.setSingleErrorMsg(e);
       }
     } finally {
       this.utilitiesService.hideSpinner();
     }
  }

  async onClickFindStatoEstero() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const decodifica: DecodificaGenerica = {
      codDecodifica: this.f.statiEsteri.get('codNazioneMin').value,
      dsDecodifica: this.f.statiEsteri.get('dsStatiEsteri').value
    };
    try {
      const res = await  this.decodificaService.postStatiEsteriDecodifica(decodifica).toPromise();
      let docodificaFinded: DecodificaGenerica;
      if (res && res.length === 1) {
       docodificaFinded = res[0];
      } else if (res && res.length > 1) {
       docodificaFinded = await this.promptModalService.openDecodificaPrompt('Selezionare uno stato', res, TYPE_DECODIFICA_GENERICA.STATI_ESTERI, decodifica);
      }
      if (docodificaFinded) {
        const stato: StatiEsteri = {
          id: docodificaFinded.idDecodifica,
          codNazioneMin: docodificaFinded.codDecodifica,
          dsStatiEsteri: docodificaFinded.dsDecodifica
        };
        this.f.statiEsteri.patchValue(stato);
      }
     } catch (e) {
       if(e.error && e.error.length > 0){
         this.alertMessageService.setWarningMsg(e.error);
       }else{
        if(e != 0)
          this.alertMessageService.setSingleErrorMsg(e);
       }
     } finally {
       this.utilitiesService.hideSpinner();
     }
  }

  async onClickFindCfStudioP(cf: string) {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    try {
      const res = await this.soggettoAbilitatoService.getSoggettoAbilitatoBycfSoggetto(cf).toPromise();
      if (res) {
        this.f.soggettoAbilitato.patchValue(res);
        this.alertMessageService.setSuccessSingleMsg('Studio professionale inserito correttamente');
      }
    } catch (e) {
      this.alertMessageService.setWarningMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  async submitAddAzienda() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const cfimpresa = this.fRicercaAzienda.cfAzienda.value;
   
    let delegatoImpresa: DelegatoImpresa = {
      id: {
        cfImpresa: cfimpresa,
        cfDelegato: this.anagraficaDelegato.id.cfDelegato,
        tipoAnagrafica: this.anagraficaDelegato.id.tipoAnagrafica,
      }
    };
    if (this.anagraficaDelegato.id.tipoAnagrafica === 'E') {
      //const pv: string = this.fRicercaAzienda.tipoCarica.value;
      const pv = 'TO';
      const caricaPersonaPv = this.fRicercaAzienda.tipoCarica.value;
      delegatoImpresa.pv = pv;
      delegatoImpresa.idCaricaPersonaPv = caricaPersonaPv;
    }
    try {
      const res = await this.anagraficaDelegatoService.addAzienda(delegatoImpresa).toPromise();
      if (res) {
        console.log(res);
        const successMsg = 'Salvataggio effettuato con successo';
        // success meaage
        this.alertMessageService.setSuccessSingleMsg(successMsg);
        this.delegatiImpresa.unshift(res);
      }
    } catch (e) {
      console.log(e);
      this.alertMessageService.setErrorMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  onClickReset() {
    this.alertMessageService.emptyMessages();
    this.form.reset();
    if (!this.INS_MODE) {
      this.form.patchValue(this.anagraficaDelegato);
    } else {
      this.f.id.controls.tipoAnagrafica.setValue(ANAGRAFICA_DELEGATO.TIPO.CONSULENTE);
    }
  }


  async onClickAnnullaRipristina(delegatoImpresa: DelegatoImpresa, typeMessage: string) {
    const title = this.translate(marker('SIDEBAR.ACCREDITAMENTO_ANAGRAFICHE'));
    const message = this.translate(marker(typeMessage));
    const pYes = this.translate(marker('APP.YES'));
    const pNo = this.translate(marker('APP.NO'));

    const userChoice = await this.promptModalService.openPrompt(title, message, pYes, pNo, 'warning');

    if (userChoice) {
      this.setDataAnnullamento(delegatoImpresa);
    }

  }

  async setDataAnnullamento(delegatoImpresa: DelegatoImpresa) {

    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const cfDelegato: string = delegatoImpresa.id.cfDelegato;
    const tipoAnagrafica: string = delegatoImpresa.id.tipoAnagrafica;
    const cfImpresa = delegatoImpresa.id.cfImpresa;
    try {
      const res: Date = await this.delegatoImpresaService.setDataAnnullamentoDelegatoImpresa(cfDelegato, tipoAnagrafica, cfImpresa).toPromise();

      console.log(res);
      delegatoImpresa.dataAnnullamento = res;

    } catch (e) {
      if(e.error && e.error.length > 0){
        this.alertMessageService.setErrorMsg(e.error);
        if(e.error[0].code === 'COM-COM-W-1024'){
          delegatoImpresa.dataAnnullamento = null;
        }
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  private translate(key: string) {
    return this.translateService.instant(key);
  }

  async onClickSubmit() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const anagraficaDelegatoToSave = this.form.getRawValue() as AnagraficaDelegato;

    if (anagraficaDelegatoToSave.id.tipoAnagrafica !== ANAGRAFICA_DELEGATO.TIPO.CONSULENTE) {
      anagraficaDelegatoToSave.soggettoAbilitato = undefined;
    }
    if (Utils.isNullOrUndefinedOrCampoVuoto(anagraficaDelegatoToSave.comuneNasc.codComuneMin) &&
        Utils.isNullOrUndefinedOrCampoVuoto(anagraficaDelegatoToSave.comuneNasc.dsComTComune)) {
          anagraficaDelegatoToSave.comuneNasc = undefined;
        }
    if (Utils.isNullOrUndefinedOrCampoVuoto(anagraficaDelegatoToSave.statiEsteri.codNazioneMin) &&
        Utils.isNullOrUndefinedOrCampoVuoto(anagraficaDelegatoToSave.statiEsteri.dsStatiEsteri)) {
          anagraficaDelegatoToSave.statiEsteri = undefined;
      }
    try {
      let res: AnagraficaDelegato;
      let successMsg: string;
      if (this.INS_MODE) {
        res = await this.anagraficaDelegatoService.postAnagraficaDelegato(anagraficaDelegatoToSave).toPromise();
      } else {
       res = await this.anagraficaDelegatoService.putAnagraficaDelegato(anagraficaDelegatoToSave).toPromise();
      }
      if (res) {
        successMsg = 'Salvataggio effettuato con successo';
        this.anagraficaDelegato = res;
        this.mode = 'edit';
        this.setMode();
        this.alertMessageService.setSuccessSingleMsg(successMsg);
      }
      // this.alertMessageService.setSuccessMsg()
    } catch (e) {
      console.log(e);
      this.alertMessageService.setErrorMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  onClickGoBackToRicerca() {
    this.location.back();
  }

  disableB: boolean = true;

  
  setFormricercaAzienda(){
    const tipoAnagrafica = this.f.id.controls.tipoAnagrafica.value;
    if (tipoAnagrafica === 'D') {
      this.formRicercaAzienda = new FormGroup({
        cfAzienda: new FormControl(null, Validators.required)
        
      });
    } else if (tipoAnagrafica === 'E') {
      this.formRicercaAzienda = new FormGroup({
        cfAzienda: new FormControl(null, Validators.required),
        tipoCarica: new FormControl(null, Validators.required)
      });
    }
  }

  disabeleButton(event: any){
    const value = event.target.value;
    if(Utils.isNullOrUndefinedOrCampoVuoto(value)){
      this.disableB = true;
    }else{
      this.disableB = value.length < 11;
    }
  }

}
