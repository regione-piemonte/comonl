/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { DatiAzienda } from './../../../../comonlapi/model/datiAzienda';
import { SilpService } from './../../../../comonlapi/api/silp.service';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { WrapperHelper } from 'src/app/models/wrappwer-helper';
import { LogService, UtilitiesService } from 'src/app/services';
import { Datore } from 'src/app/modules/comonlapi';
import { NavComunicazioneParams } from 'src/app/modules/comunicazione/modules/nav-main-comunicazione/models/nav-comunicazione-params';
import { Router } from '@angular/router';
import { UtilsComonl } from 'src/app/services/utilsComonl';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';


@Component({
  selector: 'comonl-form-ricerca-azienda',
  templateUrl: './form-ricerca-azienda.component.html',
  styleUrls: ['./form-ricerca-azienda.component.scss']
})
export class FormRicercaAziendaComponent implements OnInit {

  @Output() readonly datiRicerca = new EventEmitter<any>();
  @Output() readonly resetForm = new EventEmitter();

  @Input() wrapperHelper: WrapperHelper;
  @Input() parametriNavigazione: NavComunicazioneParams;
  @Input() cfAzienda: string;

  formRicercaAzienda: FormGroup;
  showForm = false;
  showTable = false;
  aziendaSilp: DatiAzienda;
  comParams: NavComunicazioneParams;
  flgRitorno = false;
  flgIcona;
  routingParams;

  get f() {
    return this.formRicercaAzienda.controls as any;
  }

  constructor(
    private logService: LogService,
    private utilitiesService: UtilitiesService,
    private formBuilder: FormBuilder,
    private alertMessageService: AlertMessageService,
    private router: Router,
    private silpService: SilpService
  ) {
  }

  async ngOnInit() {
    await this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    const now = new Date();
    console.log(now);
    try {
      this.defineForm();
      this.showForm = true;
      if (this.cfAzienda) {
        console.log(this.cfAzienda);
        this.f.cfAzienda.patchValue(this.cfAzienda);
        this.onSubmit();
      }
    } catch (e) {
      this.alertMessageService.setWarningMsg(e);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }


  private defineForm() {
    this.formRicercaAzienda = this.formBuilder.group({
      cfAzienda: this.formBuilder.control(null, Validators.minLength(6))
    });
  }

  async onSubmit() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    let res: DatiAzienda;
    try {
      res = await this.silpService.getAziendaSilp(this.f.cfAzienda.value.toUpperCase()).toPromise();
      this.aziendaSilp = res;
      console.log('aziendaSilp: ' + JSON.stringify(this.aziendaSilp));
      this.showTable = true;
    } catch (e) {
      console.log(e);
      this.alertMessageService.setErrorMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  onClickReset() {
    this.formRicercaAzienda.reset();
  }

  // goToNuovaAzienda(newAzienda: boolean) {
  //   // console.log('aziendaSilp in form ricerca azienda: ' + JSON.stringify(this.aziendaSilp));
  //   this.router.navigate(['./dettaglio-azienda'], { state: { new: newAzienda}});
  // }

  goToAnagrafica(modifica: string) {
    // console.log('aziendaSilp in form ricerca azienda: ' + JSON.stringify(this.aziendaSilp));
    this.router.navigate(['./dettaglio-azienda'], { state: { mode: modifica, azienda: this.aziendaSilp } });
  }

  async goBackNavmain() {
    const datore: Datore = UtilsComonl.getDatoreFromDatiAzienda(this.aziendaSilp);
    // datore.sedeOperativa = this.aziendaSilp.sedeLegale;
    this.wrapperHelper.datore = datore;
    this.router.navigate(['/nav-main-comunicazione'], { state: {parametriNavigazione: this.parametriNavigazione, wrapperHelper: this.wrapperHelper}});
  }

}
