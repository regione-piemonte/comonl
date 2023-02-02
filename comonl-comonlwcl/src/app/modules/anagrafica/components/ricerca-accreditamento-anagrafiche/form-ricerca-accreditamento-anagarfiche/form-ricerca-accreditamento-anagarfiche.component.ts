/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FormRicercaAccreditamentoAnagrafiche } from 'src/app/modules/comonlapi';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { LogService, UserService, UtilitiesService } from 'src/app/services';
import { RicercaAccreditamentoAnagrafiche } from '../ricerca-accreditamento-anagrafiche.component';

@Component({
  selector: 'comonl-form-ricerca-accreditamento-anagarfiche',
  templateUrl: './form-ricerca-accreditamento-anagarfiche.component.html',
  styleUrls: ['./form-ricerca-accreditamento-anagarfiche.component.scss']
})
export class FormRicercaAccreditamentoAnagarficheComponent implements OnInit {

  @Output() readonly datiRicerca = new EventEmitter<RicercaAccreditamentoAnagrafiche>();
  @Output() readonly resetForm = new EventEmitter();

  @Input() formRicerca: FormRicercaAccreditamentoAnagrafiche;
  @Input() formPristine: FormRicercaAccreditamentoAnagrafiche;

  formRicercaConsulenteProf: FormGroup;

  showForm = false;
  showFormProfessionale = false;

  tipoForm: string;


  // restituisce formControls
  get f() {
    return this.formRicercaConsulenteProf.controls as any;
  }

  constructor(
    private logService: LogService,
    private utilitiesService: UtilitiesService,
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router,
    private alertMessageService: AlertMessageService
  ) { }

  async ngOnInit() {
    await this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    this.defineFormRicercaConsulente();
    this.formRicercaConsulenteProf.patchValue(this.formRicerca);
    await this.patchParamsInForm(this.formRicerca);
    await this.utilitiesService.hideSpinner();
  }

  onSubmit(){
    this.alertMessageService.emptyMessages();
    this.datiRicerca.emit({
      formRicerca: this.formRicercaConsulenteProf.getRawValue() as FormRicercaAccreditamentoAnagrafiche,
      tipoRicerca: this.tipoForm
    } as RicercaAccreditamentoAnagrafiche);
  }

  selectTipoForm(tipoForm: string) {
    this.showForm = true;
    this.tipoForm = tipoForm;
    this.onClickReset();
  }

  // Define form: definisce il modello strutturale del form
  private defineFormRicercaConsulente() {
    this.formRicercaConsulenteProf = this.formBuilder.group({
      cf: this.formBuilder.control(null, Validators.minLength(3)),
      cognome: this.formBuilder.control(null, Validators.minLength(2)),
      nome: this.formBuilder.control(null, Validators.minLength(2)),
      cfAzienda: this.formBuilder.control(null, Validators.minLength(3)),
      pIva: this.formBuilder.control(null, Validators.minLength(3)),
      denom: this.formBuilder.control(null, Validators.minLength(2)),
      annullate: this.formBuilder.control(null),
    });
  }

  onClickReset(){
    this.alertMessageService.emptyMessages();
    this.formRicercaConsulenteProf.reset();
    this.resetForm.emit();
  }
  
  onClickNuovoConsulenteDelegatoPersona(){
    this.router.navigate(['./accreditamento-anagrafiche-consulente'], { state: { mode: "ins" } });
    
  }

  onClickNuovaAzienda(){
    this.router.navigate(['./anagrafica-azienda']);
  }


  oncClickNuovoStudio(){
    this.router.navigate(['./anagrafica-studio-professionale'], { state: { mode: "ins" } });
  }

  async patchParamsInForm(pFormRicerca: any) {
    let goSearch = false;

    if (!pFormRicerca) {
      return;
    }

    if (pFormRicerca.tipoForm || pFormRicerca.cf || pFormRicerca.cognome || pFormRicerca.nome || pFormRicerca.cfAzienda ||
      pFormRicerca.getpIva || pFormRicerca.denom || pFormRicerca.annullate) {
        goSearch = true;
        this.selectTipoForm(pFormRicerca.tipoForm);
    }

    
    this.formRicercaConsulenteProf.patchValue(pFormRicerca);

     if (goSearch) {
       this.onSubmit();
     }

  }

}
