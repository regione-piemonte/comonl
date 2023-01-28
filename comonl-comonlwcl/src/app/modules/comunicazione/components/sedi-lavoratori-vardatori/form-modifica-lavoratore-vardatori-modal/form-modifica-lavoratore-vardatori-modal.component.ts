import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { TYPE_DECODIFICA_GENERICA } from 'src/app/constants';
import { Comune, DecodificaGenerica, DecodificaService, LavoratoreSilpEspanso, SilpService } from 'src/app/modules/comonlapi';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { UtilitiesService } from 'src/app/services/utilities.service';
import { Utils } from 'src/app/utils';

@Component({
  selector: 'comonl-form-modifica-lavoratore-vardatori-modal',
  templateUrl: './form-modifica-lavoratore-vardatori-modal.component.html',
  styleUrls: ['./form-modifica-lavoratore-vardatori-modal.component.scss']
})
export class FormModificaLavoratoreVardatoriModalComponent implements OnInit {

  @Input() callback;
  @Input() lavoratoreSilp: LavoratoreSilpEspanso;
  @Input() modal;
  form: FormGroup;


  get f() {
    return this.form.controls as any;
  }

  constructor(
    private utilitiesService: UtilitiesService,
    private alertMessageService: AlertMessageService,
    private decodificaService: DecodificaService,
    private promptModalService: PromptModalService,
    private silpService: SilpService
  ) { }

  ngOnInit() {
    this.alertMessageService.emptyMessages();
    this.initForm();
    this.patchValueInForm(this.lavoratoreSilp);
    this.utilitiesService.hideSpinner();
  }

  private initForm() {
    this.form = new FormGroup({
      id: new FormGroup({
        codiceFiscale: new FormControl({value: null, disabled: true}, Validators.required),
      }),
      nome: new FormControl(null,Validators.required),
      cognome: new FormControl(null,Validators.required),
      comuneDomicilio: new FormGroup({
        id: new FormControl(),
        codComuneMin: new FormControl(),
        dsComTComune: new FormControl()
      }),
    });
  }

  patchValueInForm(lavoratore: LavoratoreSilpEspanso){
    this.f.id.controls.codiceFiscale.patchValue(lavoratore.id.codiceFiscale);
    this.f.cognome.patchValue(lavoratore.cognome);
    this.f.nome.patchValue(lavoratore.nome);
    this.f.comuneDomicilio.patchValue(lavoratore.comuneDomicilio);
  }

  async onClickConferma(){
    this.alertMessageService.emptyMessages();
    this.utilitiesService.showSpinner();
    const objForm = this.form.getRawValue();
    this.lavoratoreSilp.id.codiceFiscale = objForm.id.codiceFiscale;
    this.lavoratoreSilp.cognome = objForm.cognome;
    this.lavoratoreSilp.nome = objForm.nome;
    this.lavoratoreSilp.comuneDomicilio = objForm.comuneDomicilio as Comune;
    try{
      const res: LavoratoreSilpEspanso = await this.silpService.putLavoratoreSilp(this.lavoratoreSilp).toPromise();
      if (!Utils.isNullOrUndefinedOrCampoVuoto(res)) {
        
        this.callback(this.modal, res.id.codiceFiscale);
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

  async onClickFindComune() {
    let decodifica: DecodificaGenerica;
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    decodifica = {
      codDecodifica: this.f.comuneDomicilio.get('codComuneMin').value,
      dsDecodifica: this.f.comuneDomicilio.get('dsComTComune').value
    };
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
          id: decodificaFinded.idDecodifica,
          codComuneMin: decodificaFinded.codDecodifica,
          dsComTComune: decodificaFinded.dsDecodifica
        };
        this.f.comuneDomicilio.patchValue(comune);
      }
    } catch (e) {
      if(e.error && e.error.length > 0){
        this.alertMessageService.setWarningMsg(e.error);
      }else{
        if( e != 0){
          this.alertMessageService.setSingleErrorMsg(e);
        }
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

}
