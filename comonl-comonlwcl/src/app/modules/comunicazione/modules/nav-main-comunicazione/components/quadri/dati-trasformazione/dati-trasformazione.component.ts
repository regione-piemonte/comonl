/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Trasformazionerl } from './../../../../../../comonlapi/model/trasformazionerl';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CONSTANTS_MODE, CONTROL_STATE, TIPO_SOMMINISTRAZIONE, TIPO_TRACCIATO } from 'src/app/constants';
import { ComunicazioneService, DecodificaService, Ruolo, TipoSomministrazione, WrapperComunicazione } from 'src/app/modules/comonlapi';
import { Comunicazione } from 'src/app/modules/comonlapi/model/comunicazione';
import { LogService, UtilitiesService } from 'src/app/services';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { PersistenceComunicazioneWreapper } from '../../nav-main-comunicazione/nav-main-comunicazione.component';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';

@Component({
  selector: 'comonl-dati-trasformazione',
  templateUrl: './dati-trasformazione.component.html',
  styleUrls: ['./dati-trasformazione.component.scss']
})
export class DatiTrasformazioneComponent implements OnInit {

  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="dati-trasformazione"]';

  form: FormGroup;

  @Input() data: any;
  @Output() datiForm = new EventEmitter<any>();
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();
  @Output() persistenceNotification = new EventEmitter<PersistenceComunicazioneWreapper>();
  comunicazione: Comunicazione;
  comunicazioneToSave: Comunicazione;
  routingParams;

  listaTipoTrasformazione: Trasformazionerl[] = [];

  showForm: boolean;
  editMode: boolean;

  ruolo: Ruolo;

  saveStatusSuccess: boolean;

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
      const trasformazionerl: Trasformazionerl = this.f.trasformazionerl.value;
      return trasformazionerl && trasformazionerl.id ? false : true;
    }else{
      return false;
    }
  }

  constructor(
    private logService: LogService,
    private utilitiesService: UtilitiesService,
    private decodificaService: DecodificaService,
    private comonlStorageService: ComonlStorageService,
    private comunicazioneService: ComunicazioneService,
    private alertMessageService: AlertMessageService
  ) { }

  async ngOnInit() {
    this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    this.utilitiesService.scrollTo(DatiTrasformazioneComponent.SCROLL_TARGET);
    this.ruolo = this.data.ruolo;
    this.comunicazione = this.data.comunicazione;
    this.comunicazioneToSave = this.data.comunicazioneToSave;
    this.routingParams = this.data.routingParams;
    const nuovaComunicazioneParams = this.routingParams.nuovaComParams;
    this.saveStatusSuccess = this.data.saveStatusSuccess;
    // console.log('Comunicazione' + this.comunicazione);
    // console.log('Params nuova comunicazione: ' + nuovaComunicazioneParams);
    try {
      const [tipoTrasformazione] = await Promise.all([
        this.decodificaService.getTrasformazionerlByTipo('TRS').toPromise()
      ]);
      this.listaTipoTrasformazione = tipoTrasformazione;
      this.initForm();
      this.patchValueInForm(this.comunicazioneToSave);
      this.setFormMode();
      this.chkDecodificheScadute(this.comunicazioneToSave);
      this.showForm = true;

      if (this.saveStatusSuccess) {
        this.alertMessageService.setSuccessSingleMsg('Salvataggio effettuato con successo');
      }
      if(this.comunicazioneToSave.apiWarnings && this.comunicazioneToSave.apiWarnings.length > 0){
        this.alertMessageService.setWarningMsg(this.comunicazioneToSave.apiWarnings);
      }
    } catch (e) {

    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  private initForm() {
    this.form = new FormGroup({
      dtTrasformazione: new FormControl(Validators.required),
      trasformazionerl: new FormControl(Validators.required)
    });
  }

  private setFormMode() {
    if (this.VIEW_MODE || this.ANNULLO_MODE) {
      this.f.dtTrasformazione[CONTROL_STATE.DISABLE]();
      this.f.trasformazionerl[CONTROL_STATE.DISABLE]();
    }
  }

  async onSubmit() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const dtTrasformazione = this.f.dtTrasformazione.value;
    const trasformazionerl = this.f.trasformazionerl.value;
    const tipoSommin: TipoSomministrazione = this.comunicazioneToSave.tipoSomministrazione;
    this.comunicazioneToSave.rapporto.dtTrasformazione = dtTrasformazione;
    this.comunicazioneToSave.rapporto.trasformazionerl = trasformazionerl;

    if (tipoSommin) {
      switch (tipoSommin.id) {
        case TIPO_SOMMINISTRAZIONE.MISSIONE.ID:
          this.comunicazioneToSave.missione.dtTrasformazione = dtTrasformazione;
          this.comunicazioneToSave.missione.trasformazionerl = trasformazionerl;
          break;
        case TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE_E_MISSIONE.ID:
          this.comunicazioneToSave.missione.dtTrasformazione = dtTrasformazione;
          this.comunicazioneToSave.missione.trasformazionerl = trasformazionerl;

          this.comunicazioneToSave.rapporto.dtTrasformazione = dtTrasformazione;
          this.comunicazioneToSave.rapporto.trasformazionerl = trasformazionerl;
          break;
        case TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE.ID:
          this.comunicazioneToSave.rapporto.dtTrasformazione = dtTrasformazione;
          this.comunicazioneToSave.rapporto.trasformazionerl = trasformazionerl;
      }
    } else {
      this.comunicazioneToSave.rapporto.dtTrasformazione = dtTrasformazione;
      this.comunicazioneToSave.rapporto.trasformazionerl = trasformazionerl;
    }

    try {
      const wrapperComunicazione: WrapperComunicazione = {
        comunicazione: this.comunicazioneToSave,
        ruolo: this.ruolo
      }
      const resDatiTrasformazione = await this.comunicazioneService.putDatiTrasformazione(wrapperComunicazione).toPromise();
      if (resDatiTrasformazione) {
        this.comunicazioneToSave = await this.comunicazioneService.getComunicazioneById(this.comunicazioneToSave.id).toPromise();
        const persistenceComunicazioneWreapper: PersistenceComunicazioneWreapper = {
          comunicazione: this.comunicazioneToSave,
          ricalcoloQuadri: false
        }
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

  private patchValueInForm(comunicazione: Comunicazione) {
    // if (this.comunicazioneToSave.tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID) {
    //   this.f.dtTrasformazione.patchValue(comunicazione.rapporto.dtTrasformazione);
    //   if(comunicazione.rapporto && comunicazione.rapporto.trasformazionerl){
    //     const tipoTFinded = this.listaTipoTrasformazione.find(el => {
    //       return el.codTrasformazionirlMin === comunicazione.rapporto.trasformazionerl.codTrasformazionirlMin
    //     });
    //     this.f.trasformazionerl.patchValue(tipoTFinded);
    //   }
    // }
    // if (this.comunicazioneToSave.tipoTracciato.id === TIPO_TRACCIATO.UNISOMM.ID) {
    //   this.f.dtTrasformazione.patchValue(comunicazione.missione.dtTrasformazione);
    //   if(comunicazione.missione && comunicazione.missione.trasformazionerl){
    //     const tipoTFinded = this.listaTipoTrasformazione.find(el => {
    //       return el.codTrasformazionirlMin === comunicazione.missione.trasformazionerl.codTrasformazionirlMin
    //     });
    //     this.f.trasformazionerl.patchValue(tipoTFinded);
    //   }
    // }

    const tipoSommin: TipoSomministrazione = comunicazione.tipoSomministrazione;

      if ( tipoSommin && 
        (tipoSommin.id === TIPO_SOMMINISTRAZIONE.MISSIONE.ID || 
        tipoSommin.id === TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE_E_MISSIONE.ID)
      ) {
        this.f.dtTrasformazione.patchValue(comunicazione.missione.dtTrasformazione);
         if (comunicazione.missione && comunicazione.missione.trasformazionerl) {
           const tipoTFinded = this.listaTipoTrasformazione.find(el => {
             return el.codTrasformazionirlMin === comunicazione.missione.trasformazionerl.codTrasformazionirlMin
           });
           this.f.trasformazionerl.patchValue(tipoTFinded);
         }
      } else {
        if(comunicazione.rapporto && comunicazione.rapporto.dtTrasformazione){
          this.f.dtTrasformazione.patchValue(comunicazione.rapporto.dtTrasformazione);
        }
         if (comunicazione.rapporto && comunicazione.rapporto.trasformazionerl) {
           const tipoTFinded = this.listaTipoTrasformazione.find(el => {
             return el.codTrasformazionirlMin === comunicazione.rapporto.trasformazionerl.codTrasformazionirlMin
           });
           this.f.trasformazionerl.patchValue(tipoTFinded);
         }
      }
  }

  private chkDecodificheScadute(comunicazione: Comunicazione){
    if(comunicazione && !(this.VIEW_MODE || this.INS_MODE)){
      const tipoSommin: TipoSomministrazione = comunicazione.tipoSomministrazione;
    let trasformazionerl = comunicazione.rapporto ? comunicazione.rapporto.trasformazionerl : null;
    if (tipoSommin) {
      if (tipoSommin.id === TIPO_SOMMINISTRAZIONE.MISSIONE.ID || tipoSommin.id === TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE_E_MISSIONE.ID) {
        trasformazionerl = comunicazione.missione ? comunicazione.missione.trasformazionerl : null;
      }
    }
    let dtFineCessazionerl: Date;
    let sysDate: Date = new Date();
    sysDate.setHours(0,0,0,0);
    if(trasformazionerl && trasformazionerl.dtFine){
      dtFineCessazionerl = new Date(trasformazionerl.dtFine);
      dtFineCessazionerl.setHours(0,0,0,0);
    }
    if(dtFineCessazionerl && dtFineCessazionerl <= sysDate){
      this.utilitiesService.changeControlState(
        this.f.trasformazionerl,
        CONTROL_STATE.ENABLE,
        true
      );
    }
    }
    
  }

  onClickReset() {
    this.alertMessageService.emptyMessages();
    this.form.reset();
    this.patchValueInForm(this.comunicazione);
  }

}
