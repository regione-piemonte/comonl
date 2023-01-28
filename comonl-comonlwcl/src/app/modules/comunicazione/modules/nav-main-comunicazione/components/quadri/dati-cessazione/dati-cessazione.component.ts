/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Cessazionerl } from './../../../../../../comonlapi/model/cessazionerl';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CONSTANTS_MODE, CONTROL_STATE, TIPO_SOMMINISTRAZIONE, TIPO_TRACCIATO } from 'src/app/constants';
import { ApiError, ComunicazioneService, DecodificaService, Ruolo, TipoSomministrazione, WrapperComunicazione } from 'src/app/modules/comonlapi';
import { Comunicazione } from 'src/app/modules/comonlapi/model/comunicazione';
import { TipoComunicazioneTu } from 'src/app/modules/comonlapi/model/tipoComunicazioneTu';
import { TipoSoggettoAbilitato } from 'src/app/modules/comonlapi/model/tipoSoggettoAbilitato';
import { LogService, UtilitiesService } from 'src/app/services';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { PersistenceComunicazioneWreapper } from '../../nav-main-comunicazione/nav-main-comunicazione.component';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';

@Component({
  selector: 'comonl-dati-cessazione',
  templateUrl: './dati-cessazione.component.html',
  styleUrls: ['./dati-cessazione.component.scss']
})
export class DatiCessazioneComponent implements OnInit {

  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="dati-cessazione"]';

  form: FormGroup;

  @Input() data: any;
  @Output() datiForm = new EventEmitter<any>();
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();
  @Output() persistenceNotification = new EventEmitter<PersistenceComunicazioneWreapper>();
  comunicazione: Comunicazione;
  comunicazioneToSave: Comunicazione;
  routingParams;

  listaMotiviCessazione: Cessazionerl[] = [];

  showForm: boolean;
  editMode: boolean;

  ruolo: Ruolo;

  saveStatusSuccess: boolean;
  warnings: ApiError[] = [];

  get f() {
    return this.form.controls as any;
  }

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

  get DISABLE_INTERACTIONS_VIEW(): boolean {
    return this.VIEW_MODE || this.EDIT_MODE;
  }

  get INVALIDITA_FORM(){
    if(this.ANNULLO_MODE){
      return false;
    }else{
      return !this.form.valid;
    }
  }
  get INVALIDITA_ANNULLO_MODE(): boolean{
    if(this.ANNULLO_MODE){
      const cessazionerl: Cessazionerl = this.f.cessazionerl.value;
      return cessazionerl && cessazionerl.id ? false : true;
    }else{
      return false;
    }
  }

  constructor(
    private logService: LogService,
    private utilitiesService: UtilitiesService,
    private decodificaService: DecodificaService,
    private comonlStorageService: ComonlStorageService,
    private alertMessageService: AlertMessageService,
    private comunicazioneService: ComunicazioneService
  ) { }

  async ngOnInit() {
    this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    this.utilitiesService.scrollTo(DatiCessazioneComponent.SCROLL_TARGET);
    this.ruolo = this.data.ruolo;
    this.comunicazione = this.data.comunicazione;
    this.comunicazioneToSave = this.data.comunicazioneToSave;
    this.routingParams = this.data.routingParams;
    const nuovaComunicazioneParams = this.routingParams.nuovaComParams;
    this.saveStatusSuccess = this.data.saveStatusSuccess;
    // console.log('Comunicazione' + this.comunicazione);
    // console.log('Params nuova comunicazione: ' + nuovaComunicazioneParams);
    if (this.saveStatusSuccess) {
      this.alertMessageService.setSuccessSingleMsg('Salvataggio effettuato con successo');
    }
    if(this.comunicazioneToSave.apiWarnings && this.comunicazioneToSave.apiWarnings.length > 0){
      this.alertMessageService.setWarningMsg(this.comunicazioneToSave.apiWarnings);
    }
    try {
      const motivoCessazione = await this.decodificaService.getCessazionerl().toPromise();
      this.listaMotiviCessazione = motivoCessazione;
      this.initForm();
      this.patchValueInForm(this.comunicazioneToSave);
      this.setFormMode();
      this.chkDecodificheScadute(this.comunicazioneToSave)
      this.showForm = true;
    } catch (e) {

    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  private initForm() {
    this.form = new FormGroup({
      dataCessazione: new FormControl(null, Validators.required),
      cessazionerl: new FormControl(null, Validators.required)
    });
  }

  private setFormMode() {
    if (this.VIEW_MODE) {
      // READ MODE
      this.form[CONTROL_STATE.DISABLE]();
    }else if(this.ANNULLO_MODE){
      this.f.dataCessazione[CONTROL_STATE.DISABLE]();
      this.f.cessazionerl[CONTROL_STATE.DISABLE]();
    }
    
  }

  private patchValueInForm(comunicazione: Comunicazione) {
    const tipoSommin: TipoSomministrazione = comunicazione.tipoSomministrazione;
    let dtCessazione = comunicazione.rapporto ? comunicazione.rapporto.dtCessazione : null;
    let cessazionerl = comunicazione.rapporto ? comunicazione.rapporto.cessazionerl : null;
    if (tipoSommin) {
      if (tipoSommin.id === TIPO_SOMMINISTRAZIONE.MISSIONE.ID || tipoSommin.id === TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE_E_MISSIONE.ID) {
        dtCessazione = comunicazione.missione ? comunicazione.missione.dtCessazione : null;
        cessazionerl = comunicazione.missione ? comunicazione.missione.cessazionerl : null;
      }
    }
    this.f.dataCessazione.patchValue(dtCessazione);

    if (cessazionerl) {
      const cessazionerlFinded = this.listaMotiviCessazione.find(el => {
        return el.codCessazioneMin === cessazionerl.codCessazioneMin;
      });
      if (cessazionerlFinded) {
        this.f.cessazionerl.patchValue(cessazionerlFinded);
      }
    }
  }


  onClickReset() {
    this.form.reset();
    let comunicazione: Comunicazione;
    if (!this.VIEW_MODE) {
      comunicazione = this.comunicazione;
    } else {
      comunicazione = this.comunicazioneToSave;
    }
    this.patchValueInForm(comunicazione);
  }

  async onClickSubmit() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const dataCessazione = this.f.dataCessazione.value;
    const cessazionerl = this.f.cessazionerl.value;
    const tipoSommin: TipoSomministrazione = this.comunicazioneToSave.tipoSomministrazione;
    this.comunicazioneToSave.rapporto.dtCessazione = dataCessazione;
    this.comunicazioneToSave.rapporto.cessazionerl = cessazionerl;

    if (tipoSommin) {
      switch (tipoSommin.id) {
        case TIPO_SOMMINISTRAZIONE.MISSIONE.ID:
          this.comunicazioneToSave.missione.dtCessazione = dataCessazione;
          this.comunicazioneToSave.missione.cessazionerl = cessazionerl;
          break;
        case TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE_E_MISSIONE.ID:
          this.comunicazioneToSave.missione.dtCessazione = dataCessazione;
          this.comunicazioneToSave.missione.cessazionerl = cessazionerl;

          this.comunicazioneToSave.rapporto.dtCessazione = dataCessazione;
          this.comunicazioneToSave.rapporto.cessazionerl = cessazionerl;
          break;
        case TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE.ID:
          this.comunicazioneToSave.rapporto.dtCessazione = dataCessazione;
          this.comunicazioneToSave.rapporto.cessazionerl = cessazionerl;
      }
    } else {
      this.comunicazioneToSave.rapporto.dtCessazione = dataCessazione;
      this.comunicazioneToSave.rapporto.cessazionerl = cessazionerl;
    }
    const wrapperComunicazione: WrapperComunicazione = {
      comunicazione: this.comunicazioneToSave,
      ruolo: this.ruolo
    }
    try {
      const res = await this.comunicazioneService.putDatiCessazione(wrapperComunicazione).toPromise();
      if (res) {
        this.comunicazioneToSave = await this.comunicazioneService.getComunicazioneById(this.comunicazioneToSave.id).toPromise();
        const persistenceComunicazioneWreapper: PersistenceComunicazioneWreapper = {
          comunicazione: this.comunicazioneToSave,
          ricalcoloQuadri: false
        };
        this.persistenceNotification.emit(persistenceComunicazioneWreapper);
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

  private chkDecodificheScadute(comunicazione: Comunicazione){
    if(comunicazione && !(this.VIEW_MODE || this.INS_MODE)){
      const tipoSommin: TipoSomministrazione = comunicazione.tipoSomministrazione;
    let cessazionerl = comunicazione.rapporto ? comunicazione.rapporto.cessazionerl : null;
    if (tipoSommin) {
      if (tipoSommin.id === TIPO_SOMMINISTRAZIONE.MISSIONE.ID || tipoSommin.id === TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE_E_MISSIONE.ID) {
        cessazionerl = comunicazione.missione ? comunicazione.missione.cessazionerl : null;
      }
    }
    let dtFineCessazionerl: Date;
    let sysDate: Date = new Date();
    sysDate.setHours(0,0,0,0);
    if(cessazionerl && cessazionerl.dtFine){
      dtFineCessazionerl = new Date(cessazionerl.dtFine);
      dtFineCessazionerl.setHours(0,0,0,0);
    }
    if(dtFineCessazionerl && dtFineCessazionerl <= sysDate){
      this.utilitiesService.changeControlState(
        this.f.cessazionerl,
        CONTROL_STATE.ENABLE,
        true
      );
    }
    }
    
  }

}
