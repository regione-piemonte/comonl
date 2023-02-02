/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { AnagraficaDelegato, AnagraficaDelegatoService, Comune, DecodificaGenerica, DecodificaService, SoggettoAbilitato, SoggettoAbilitatoService, TipoSoggettoAbilitato } from 'src/app/modules/comonlapi';
import { UtilitiesService } from 'src/app/services';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CONTROL_STATE, TYPE_DECODIFICA_GENERICA } from 'src/app/constants';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';
import { TranslateService } from '@ngx-translate/core';
import { marker } from '@biesbjerg/ngx-translate-extract-marker';

@Component({
  selector: 'comonl-form-anagrafica-studio-professionale',
  templateUrl: './form-anagrafica-studio-professionale.component.html',
  styleUrls: ['./form-anagrafica-studio-professionale.component.scss']
})
export class FormAnagraficaStudioProfessionaleComponent implements OnInit {

  tipoSoggettoAbilitatos: TipoSoggettoAbilitato[] = [];
  anagraficaDelegatos: AnagraficaDelegato[] = [];

  mode: string;
  idSoggettoAbilitato: number;
  soggettoAbilitato: SoggettoAbilitato;

  form: FormGroup;
  cfForm: FormGroup;


  get VIEW_MODE (){ return this.mode === "view" };
  get EDIT_MODE (){ return this.mode === "edit" };
  get INS_MODE (){ return this.mode === "ins" };
  get f() {return this.form.controls as any};
  get cfF() {return this.cfForm.controls as any};

  constructor(
    private location: Location,
    private utilitiesService: UtilitiesService,
    private decodificaService: DecodificaService,
    private router: Router,
    private soggettoAbilitatoService: SoggettoAbilitatoService,
    private alertMessageService: AlertMessageService,
    private promptModalService: PromptModalService,
    private anagraficaDelegatoService: AnagraficaDelegatoService,
    private translateService: TranslateService
  ) {
    const state = this.router.getCurrentNavigation().extras.state;
    this.idSoggettoAbilitato = state.idSoggettoAbilitato;
    this.mode = state.mode;
  }

  async ngOnInit() {
    this.utilitiesService.showSpinner();
    try{
      this.tipoSoggettoAbilitatos = await this.decodificaService.getTipoStudioProfessionale().toPromise();
      this.initForm();
      if(this.idSoggettoAbilitato){
        this.soggettoAbilitato = await this.soggettoAbilitatoService.getSoggettoAbilitatoById(this.idSoggettoAbilitato).toPromise(),
        this.anagraficaDelegatos = await this.anagraficaDelegatoService.getAnagraficaDelegatoByIdSoggettoAbilitato(this.soggettoAbilitato.id).toPromise();
        if(this.EDIT_MODE){
          this.initCfFomr();
        }
      }
      this.setMode();
    }catch(e){

    }finally{
      this.utilitiesService.hideSpinner();
    }
  }


  private initForm(){
    this.form = new FormGroup({
      id: new FormControl(null),
      cfSoggetto: new FormControl({value: null, disabled: this.EDIT_MODE}
      ),
      partitaIva: new FormControl({value: null, disabled: this.EDIT_MODE}),
      cognomeDenominazione: new FormControl(null,Validators.required),
      flgAccentramento: new FormControl("N"),
      tipoSoggettoAbilitato: new FormControl(null,Validators.required),
      comune: new FormGroup({
        id: new FormControl(null),
        codComuneMin: new FormControl(null,
          Validators.compose([
            Validators.required,
            Validators.minLength(3)
        ])),
        dsComTComune: new FormControl(null,
          Validators.compose([
            Validators.required,
            Validators.minLength(3)
        ])),
      }),
      tel: new FormControl(null),
      cap: new FormControl(null,Validators.compose([Validators.required, Validators.minLength(5)])),
      fax: new FormControl(null),
      email: new FormControl(null,Validators.compose([Validators.required])),
      indirizzo: new FormControl(null,Validators.required),
    });
  }

  private initCfFomr(){
    this.cfForm = new FormGroup({
      cf: new FormControl(null)
    })
  }

  private setMode(){
    if(!this.INS_MODE){
      this.form.patchValue(this.soggettoAbilitato);
      const tipoSoggettoAbilitato = this.soggettoAbilitato.tipoSoggettoAbilitato;
      if(tipoSoggettoAbilitato){
        const tipoSoggettoAbilitatoFinded = this.tipoSoggettoAbilitatos.find(item =>{ return item.id === tipoSoggettoAbilitato.id});
        this.f.tipoSoggettoAbilitato.patchValue(tipoSoggettoAbilitatoFinded);
      }
      if(this.EDIT_MODE){
        this.f.cfSoggetto.disable();
        this.f.partitaIva.disable();
      }
    }
    if(this.VIEW_MODE){
      this.form[CONTROL_STATE.DISABLE]();
    }
  }

  async onClickFindComune(){
    this.utilitiesService.showSpinner();
    const decodifica: DecodificaGenerica = {
      codDecodifica: this.f.comune.get('codComuneMin').value,
      dsDecodifica: this.f.comune.get('dsComTComune').value
    }
    try{
     const res = await  this.decodificaService.postComuneDecodifica(decodifica).toPromise();
     let docodificaFinded: DecodificaGenerica;
     if(res && res.length === 1){
      docodificaFinded = res[0];
     }else if(res && res.length > 1){
      docodificaFinded = await this.promptModalService.openDecodificaPrompt('Selezionare un comune',res,TYPE_DECODIFICA_GENERICA.COMUNE,decodifica);
     }
     if(docodificaFinded){
       const comune: Comune = {
         id: docodificaFinded.idDecodifica,
         codComuneMin: docodificaFinded.codDecodifica,
         dsComTComune: docodificaFinded.dsDecodifica
       }
       this.f.comune.patchValue(comune);
     }
    }catch(e){
      if(e.error && e.error.length > 0){
        this.alertMessageService.setWarningMsg(e.error);
      }else{
        if(e != 0)
          this.alertMessageService.setSingleErrorMsg(e);
      }
    }finally{
      this.utilitiesService.hideSpinner();
    }
  }

  onClickReset(){
    this.alertMessageService.emptyMessages();
    this.form.reset();
    if(!this.INS_MODE){
      this.form.patchValue(this.soggettoAbilitato);
      const tipoSoggettoAbilitato = this.soggettoAbilitato.tipoSoggettoAbilitato;
      if(tipoSoggettoAbilitato){
        const tipoSoggettoAbilitatoFinded = this.tipoSoggettoAbilitatos.find(item =>{ return item.id === tipoSoggettoAbilitato.id});
        this.f.tipoSoggettoAbilitato.patchValue(tipoSoggettoAbilitatoFinded);
      }
    }
    if(this.EDIT_MODE){
      this.cfForm.reset();
    }
  }

  async onClickAddConsulente(){
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    try{
      const res: AnagraficaDelegato = await this.anagraficaDelegatoService.addConsulente(this.cfForm.controls.cf.value,this.soggettoAbilitato.id).toPromise();
      if(res){
        this.anagraficaDelegatos.unshift(res);
        this.alertMessageService.setSuccessSingleMsg("Consulente inserito correttamente");
      }
    }catch(e){
       if(e.error && e.error.length > 0){
         if(e.error[0].code === 'COM-COM-E-0010'){
          this.confermaDisassociazione(e.error[0].errorMessage,this.cfForm.controls.cf.value,this.soggettoAbilitato.id)
         }else{
          this.alertMessageService.setErrorMsg(e.error);
         }
       }else{
         this.alertMessageService.setSingleErrorMsg(e);
       }
    }finally{
      this.utilitiesService.hideSpinner();
    }
  }

  async confermaDisassociazione(typeMessage: string,cfDelegato: string, idSoggettoAbilitato: number){
    const title = this.translate(marker('SIDEBAR.ACCREDITAMENTO_ANAGRAFICHE'));
    const message = this.translate(marker(typeMessage));
    const pYes = this.translate(marker('APP.YES'));
    const pNo = this.translate(marker('APP.NO'));

    const userChoice = await this.promptModalService.openPrompt(title, message, pYes, pNo, 'warning');

    if (userChoice) {
      this.removeConsulente(cfDelegato,idSoggettoAbilitato);
    }
  }

  async removeConsulente(cfDelegato: string, idSoggettoAbilitato: number){
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    try{
      const res = await this.anagraficaDelegatoService.removeConsulente(cfDelegato,idSoggettoAbilitato).toPromise();
      if(res){
        this.anagraficaDelegatos.unshift(res);
        this.alertMessageService.setSuccessSingleMsg("Consulente inserito correttamente");
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

  async onClickAnnullaRipristina(anagraficaDelegato: AnagraficaDelegato,typeMessage: string){
    const title = this.translate(marker('SIDEBAR.ACCREDITAMENTO_ANAGRAFICHE'));
    const message = this.translate(marker(typeMessage));
    const pYes = this.translate(marker('APP.YES'));
    const pNo = this.translate(marker('APP.NO'));

    const userChoice = await this.promptModalService.openPrompt(title, message, pYes, pNo, 'warning');

    if(userChoice){
      this.setDataAnnullamento(anagraficaDelegato);
    }
  }

  async setDataAnnullamento(anagraficaDelegato: AnagraficaDelegato){

    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const cfDelegato: string = anagraficaDelegato.id.cfDelegato;
    const tipoAnagrafica: string = anagraficaDelegato.id.tipoAnagrafica;
    try{
      const res: Date = await this.anagraficaDelegatoService.setDataAnnullamentoCDE(cfDelegato,tipoAnagrafica).toPromise();
      console.log(res);
      anagraficaDelegato.dataAnnullamento = res;

    }catch(e){
      this.alertMessageService.setErrorMsg(e.error);
    }finally{
      this.utilitiesService.hideSpinner();
    }
  }


  private translate(key: string) {
    return this.translateService.instant(key);
  }

  async onClickSubmit(){
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    try{
      const soggettoAbilitatoToSend = this.form.getRawValue() as SoggettoAbilitato;
      let res: SoggettoAbilitato;
      if(!soggettoAbilitatoToSend.id){
        res = await this.soggettoAbilitatoService.postSoggettoAbilitato(soggettoAbilitatoToSend).toPromise();
      }else{
        res = await this.soggettoAbilitatoService.putSoggettoAbilitato(soggettoAbilitatoToSend).toPromise();
      }
      if(res){
        this.alertMessageService.setSuccessSingleMsg("Salvataggio effettuato con successo");
        this.soggettoAbilitato = res;
        this.mode = "edit";
        this.setMode();
      }
    }catch(e){
      console.log(e);
      this.alertMessageService.setErrorMsg(e.error);
    }finally{
      this.utilitiesService.hideSpinner();
    }
  }

  onClickGoBackToRicerca() {
    this.location.back();
  }


}
