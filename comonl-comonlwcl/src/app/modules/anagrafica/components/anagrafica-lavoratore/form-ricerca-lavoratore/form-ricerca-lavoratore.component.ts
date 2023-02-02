/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TYPE_DECODIFICA_GENERICA } from 'src/app/constants';
import { WrapperHelper } from 'src/app/models/wrappwer-helper';
import { Comune, DecodificaGenerica, DecodificaService, LavoratoreSilpEspanso } from 'src/app/modules/comonlapi';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { NavComunicazioneParams } from 'src/app/modules/comunicazione/modules/nav-main-comunicazione/models/nav-comunicazione-params';
import { LogService, UserService, UtilitiesService } from 'src/app/services';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { SilpService } from './../../../../comonlapi/api/silp.service';

@Component({
  selector: 'comonl-form-ricerca-lavoratore',
  templateUrl: './form-ricerca-lavoratore.component.html',
  styleUrls: ['./form-ricerca-lavoratore.component.scss']
})
export class FormRicercaLavoratoreComponent implements OnInit {

  @Output() readonly datiRicerca = new EventEmitter<any>();
  @Output() readonly resetForm = new EventEmitter();

  @Input() wrapperHelper: WrapperHelper;
  @Input() parametriNavigazione: NavComunicazioneParams;
  @Input() cfLavoratoreSilp: string;

  formRicercaLavoratore: FormGroup;
  showForm = false;
  showTable = false;
  showDetail = false;
  comParams: NavComunicazioneParams;
  flgRitorno = false;
  flgIcona;

  lavoratoreSilpEspanso: LavoratoreSilpEspanso;

  get f() {
    return this.formRicercaLavoratore.controls as any;
  }

  constructor(
    private logService: LogService,
    private utilitiesService: UtilitiesService,
    private formBuilder: FormBuilder,
    private router: Router,
    private alertMessageService: AlertMessageService,
    private silpService: SilpService,
    private comonlStorageService: ComonlStorageService
  ) {

  }

  async ngOnInit() {
    this.comonlStorageService.wrapperHelper$.subscribe(el => {
      if (el) {
        this.flgIcona = el.flgIcona;
        console.log('flgIcona: ' + this.flgIcona);
      }
    });
    await this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    const now = new Date();
    console.log(now);
    try {
      this.defineForm();
      this.showForm = true;
      if (this.cfLavoratoreSilp) {
        console.log(this.cfLavoratoreSilp);
        this.f.cfLavoratore.patchValue(this.cfLavoratoreSilp);
        this.onSubmit();
      }
    } catch (e) {
      this.alertMessageService.setWarningMsg(e);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  // tslint:disable-next-line: use-lifecycle-interface
  // ngOnDestroy(): void {
  //   this.comonlStorageService.wrapperHelper$.subscribe((el: WrapperHelper) => {
  //     if (el) {
  //       el.flgIcona = false;
  //     }
  //   });
  // }

  private defineForm() {
    this.formRicercaLavoratore = this.formBuilder.group({
      cfLavoratore: this.formBuilder.control(null, Validators.compose([Validators.minLength(11), Validators.maxLength(16), Validators.required])),
      cognome: this.formBuilder.control(null, Validators.minLength(3)),
      nome: this.formBuilder.control(null, Validators.minLength(3)),
    });
  }

  async onSubmit() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    let res: LavoratoreSilpEspanso;
    try {
      res = await this.silpService.getLavoratoreSilpEspanso(this.f.cfLavoratore.value, this.f.cognome.value, this.f.nome.value).toPromise();
      this.lavoratoreSilpEspanso = res;
      if (res) {
        console.log('lavoratoreSilp: ' + JSON.stringify(this.lavoratoreSilpEspanso));
        this.showTable = true;
      }
    } catch (e) {
      console.log(e);
      this.lavoratoreSilpEspanso = res;
      this.alertMessageService.setErrorMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  onClickReset() {
    this.formRicercaLavoratore.reset();
  }

  goToAnagrafica(modalita: string) {
    // console.log('lavoratoreSilp in form ricerca lavoratore: ' + JSON.stringify(this.lavoratoreSilp));
    this.router.navigate(['./dati'], { state: { mode: modalita, lavoratore: this.lavoratoreSilpEspanso } });
  }

  goBackNavmain() {
    let lavoratore: LavoratoreSilpEspanso;
    this.wrapperHelper.lavoratore = lavoratore;
    this.router.navigate(['/nav-main-comunicazione'], { state: {parametriNavigazione: this.parametriNavigazione, wrapperHelper: this.wrapperHelper}});
  }

}
