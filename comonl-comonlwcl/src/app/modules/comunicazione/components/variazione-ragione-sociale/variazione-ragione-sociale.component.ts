/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { AdComponent } from './../../modules/nav-main-comunicazione/models/ad-component';
import { AlertMessageService } from './../../../comonlcommon/services/alert-message.service';
import { Ruolo } from './../../../comonlapi/model/ruolo';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LogService, UtilitiesService } from 'src/app/services';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { PersistenceComunicazioneWreapper } from '../../modules/nav-main-comunicazione/components/nav-main-comunicazione/nav-main-comunicazione.component';
import { Comunicazione, ComunicazioneService, WrapperComunicazione } from 'src/app/modules/comonlapi';
import { CONSTANTS_MODE, STATO_COMUNICAZIONE } from 'src/app/constants';
import { Utils } from 'src/app/utils';


@Component({
  selector: 'comonl-variazione-ragione-sociale',
  templateUrl: './variazione-ragione-sociale.component.html',
  styleUrls: ['./variazione-ragione-sociale.component.scss'],
})
export class VariazioneRagioneSocialeComponent implements OnInit, AdComponent {

  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="variazione-ragione-sociale"]';

  @Input() data: any;
  @Output() datiForm = new EventEmitter<any>();
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();
  @Output() persistenceNotification = new EventEmitter<PersistenceComunicazioneWreapper>();

  form: FormGroup;
  showForm = false;
  comunicazione: Comunicazione;
  comunicazioneToSave: Comunicazione;

  ruolo: Ruolo;
  routingParams;

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

  get f() {
    return this.form.controls as any;
  }

  constructor(
    private logService: LogService,
    private utilitiesService: UtilitiesService,
    private alertMessageService: AlertMessageService,
    private comonlStorageService: ComonlStorageService,
    private comunicazioneService: ComunicazioneService
  ) {  }

  async ngOnInit() {
    await this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    this.logService.info(this.constructor.name, 'ngOnInit');
    this.utilitiesService.scrollTo(VariazioneRagioneSocialeComponent.SCROLL_TARGET);
    this.comonlStorageService.ruolo$.subscribe(el => this.ruolo = el);
    const now = new Date();
    console.log(now);
    this.routingParams = this.data.routingParams;
    // console.log('routing params: ' + JSON.stringify(this.routingParams));
    this.comunicazione = this.data.comunicazione;
    this.comunicazioneToSave = this.data.comunicazioneToSave;
    // console.log('comunicazioneToSave: ' + JSON.stringify(this.comunicazioneToSave));
    this.ruolo = this.data.ruolo;
    try {
      this.initForm();
      this.patchValueInForm(this.comunicazioneToSave);
      this.showForm = true;
    } catch (e) {
      this.alertMessageService.setWarningMsg(e);
    } finally {
      await this.utilitiesService.hideSpinner();
    }
  }

  private initForm() {
    this.form = new FormGroup({
      dsVariazioneRagSociale: new FormControl(),
      dtTrasferimentoVarDatori: new FormControl(),
    });
  }

  patchValueInForm(comunicazioneToSave: Comunicazione) {
    if (this.VIEW_MODE || this.ANNULLO_MODE) {
      this.form.disable();
      if (comunicazioneToSave.datoreAttuale) {
        this.f.dsVariazioneRagSociale.patchValue(comunicazioneToSave.datoreAttuale.dsVariazioneRagSociale);
      }
      this.f.dtTrasferimentoVarDatori.patchValue(comunicazioneToSave.dtTrasferimentoVarDatori);
    }
    if (this.RETTIFICA_MODE) {
      if (comunicazioneToSave.datoreAttuale) {
        this.f.dsVariazioneRagSociale.patchValue(comunicazioneToSave.datoreAttuale.dsVariazioneRagSociale);
      }
      this.f.dtTrasferimentoVarDatori.patchValue(comunicazioneToSave.dtTrasferimentoVarDatori);
    }

    if (this.EDIT_MODE) {
      if (comunicazioneToSave.datoreAttuale) {
        this.f.dsVariazioneRagSociale.patchValue(comunicazioneToSave.datoreAttuale.dsVariazioneRagSociale);
      }
      this.f.dtTrasferimentoVarDatori.patchValue(comunicazioneToSave.dtTrasferimentoVarDatori);
    }

  }

  async onClickInserisciVarDatori() {
    console.log('comunicazione da inserire: ' + JSON.stringify(this.comunicazioneToSave));
    // INSERIMENTO COMUNICAZIONE VARDATORI ANAGRAFICO
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const comunicazioneToSend = Utils.clone(this.comunicazioneToSave);
    comunicazioneToSend.dtTrasferimentoVarDatori = this.f.dtTrasferimentoVarDatori.value;
    comunicazioneToSend.datoreAttuale.dsVariazioneRagSociale = this.f.dsVariazioneRagSociale.value;
    const wrapperComunicazione: WrapperComunicazione = {
      ruolo: this.ruolo,
      comunicazione: comunicazioneToSend
    };
    try {
      let res: Comunicazione;
      const id = comunicazioneToSend.id;
      if(!id){
        comunicazioneToSend.statoComunicazione = {
          id: STATO_COMUNICAZIONE.INSERITA.ID
        };
        if (this.RETTIFICA_MODE && !this.comunicazioneToSave.id) {
          res = await this.comunicazioneService.rettificaComunicazioneVardatore(wrapperComunicazione).toPromise();
        }else if(this.ANNULLO_MODE && !this.comunicazioneToSave.id){
          res = await this.comunicazioneService.annullaComunicazioneVardatore(wrapperComunicazione).toPromise();
        }else{
          res = await this.comunicazioneService.postComunicazioneVardatore(wrapperComunicazione).toPromise();
        }
      }else{
        res = await this.comunicazioneService.putComunicazioneVardatore(wrapperComunicazione).toPromise();
      }

      if (res) {
        let responseComunicazione = await this.comunicazioneService.getComunicazioneById(res.id).toPromise();
        responseComunicazione.apiWarnings = res.apiWarnings;
        const persistenceComunicazioneWreapper: PersistenceComunicazioneWreapper = {
           comunicazione: responseComunicazione,
           ricalcoloQuadri: false,
           switchEditMode: this.INS_MODE,
           success: true
         };
        this.persistenceNotification.emit(persistenceComunicazioneWreapper);
      }
    } catch (e) {
      if (e.error && e.error.length) {
        this.alertMessageService.setErrorMsg(e.error);
      } else {
        this.alertMessageService.setErrorMsg(e);
      }
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  onClickReset() {
    this.form.reset();
    this.initForm();
    this.patchValueInForm(this.comunicazione);
    // this.setFormMode();
  }
}
