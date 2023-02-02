/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { CONTROL_STATE } from 'src/app/constants';
import { ComunicazioneService, FormRecuperoComunicazione, RecuperoComunicazione, Ruolo } from 'src/app/modules/comonlapi';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { UtilitiesService } from 'src/app/services';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { Utils } from 'src/app/utils';

@Component({
  selector: 'comonl-gestione-applicativa',
  templateUrl: './gestione-applicativa.component.html',
  styleUrls: ['./gestione-applicativa.component.scss']
})
export class GestioneApplicativaComponent implements OnInit {

  form: FormGroup;
  ruolo: Ruolo;

  get f() { return this.form.controls as any; }

  listaRecuperoComunicazioni: RecuperoComunicazione[] = [];

  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="gestioneApplicativa"]';

  get conditionControlsEnable(): boolean {

        return !Utils.isNullOrUndefinedOrCampoVuoto(this.f.elencoID.value) || 
               (!Utils.isNullOrUndefinedOrCampoVuoto(this.f.rangeDa.value) && !Utils.isNullOrUndefinedOrCampoVuoto(this.f.rangeA.value) && this.rangeValid);  
                                                   // this.rangeDaMaggioreRangeA
        }


  get rangeValid(): boolean {
    const rangeDa: number = this.f.rangeDa.value;
    const rangeA: number = this.f.rangeA.value;

    return !Utils.isNullOrUndefinedOrCampoVuoto(rangeDa) && !Utils.isNullOrUndefinedOrCampoVuoto(rangeA) && (rangeDa <= rangeA);
  }

  onKeyDownRange() {
    this.utilitiesService.changeControlState(
      this.f.elencoID,
      CONTROL_STATE.DISABLE,
      true
    );
  }

  onKeyDownElencoId() {
    this.utilitiesService.changeControlState(
      this.f.rangeDa,
      CONTROL_STATE.DISABLE,
      true
    );
    this.utilitiesService.changeControlState(
      this.f.rangeA,
      CONTROL_STATE.DISABLE,
      true
    );
  }

  onKeyUp()  {
    if (
      Utils.isNullOrUndefinedOrCampoVuoto(this.f.elencoID.value) &&
      Utils.isNullOrUndefinedOrCampoVuoto(this.f.rangeDa.value) &&
      Utils.isNullOrUndefinedOrCampoVuoto(this.f.rangeA.value)
    ) {
      this.form[CONTROL_STATE.ENABLE]();
    }
  }

  constructor(
    private location: Location,
    private comunicazioneService: ComunicazioneService,
    private alertMessageService: AlertMessageService,
    private utilitiesService: UtilitiesService,
    private comonlStorageService: ComonlStorageService
    ) {}

  ngOnInit() {
    this.utilitiesService.showSpinner();
    this.utilitiesService.scrollTo(GestioneApplicativaComponent.SCROLL_TARGET);
    this.comonlStorageService.ruolo$.subscribe(el => this.ruolo = el);
    this.initForm();
    this.utilitiesService.hideSpinner();
  }

  private initForm() {
    this.form = new FormGroup({
      elencoID: new FormControl(),
      rangeDa: new FormControl(),
      rangeA: new FormControl(),
    });
  }

  onClickBack() {
    this.location.back();
  }

  async onClickRitrasmetti() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    try {
      let res;
      res = await this.comunicazioneService.ritrasmettiComunicazioni().toPromise();
      console.log(JSON.stringify(res));
      if (res) {
        this.alertMessageService.setSuccessSingleMsg(res);
      }
    } catch (e) {
      console.log(e);
      this.alertMessageService.setErrorMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  async onSubmit() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    this.listaRecuperoComunicazioni = [];
    let objForm = this.form.getRawValue();
    const objToSend: FormRecuperoComunicazione = new Object();
    objToSend.elencoIds = objForm.elencoID;
    objToSend.idDa = objForm.rangeDa;
    objToSend.idA = objForm.rangeA;
    objToSend.ruolo = this.ruolo;
    try {
      const res = await this.comunicazioneService.postRecuperoComunicazioni(objToSend).toPromise();
      if (res) {
        this.listaRecuperoComunicazioni = Utils.clone(res);
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

}
