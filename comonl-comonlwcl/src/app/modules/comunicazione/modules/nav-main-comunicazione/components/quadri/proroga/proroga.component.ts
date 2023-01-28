/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CONSTANTS_MODE, CONTROL_STATE, REGIONE, TIPO_COMUNICAZIONE, TIPO_SOMMINISTRAZIONE, TIPO_TRACCIATO } from 'src/app/constants';
import { Comunicazione, DecodificaService, TipoComunicazioneTu, Ruolo, Rapporto, TipoSomministrazione, ComunicazioneService, WrapperComunicazione } from 'src/app/modules/comonlapi';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { LogService, UtilitiesService } from 'src/app/services';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { PersistenceComunicazioneWreapper } from '../../nav-main-comunicazione/nav-main-comunicazione.component';

@Component({
  selector: 'comonl-proroga',
  templateUrl: './proroga.component.html',
  styleUrls: ['./proroga.component.scss']
})
export class ProrogaComponent implements OnInit {

  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="proroga"]';

  form: FormGroup;

  @Input() data: any;
  @Output() datiForm = new EventEmitter<any>();
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();
  @Output() persistenceNotification = new EventEmitter<PersistenceComunicazioneWreapper>();
  comunicazione: Comunicazione;
  comunicazioneToSave: Comunicazione;
  routingParams;

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

  constructor(
    private logService: LogService,
    private utilitiesService: UtilitiesService,
    private comonlStorageService: ComonlStorageService,
    private alertMessageService: AlertMessageService,
    private comunicazioneService: ComunicazioneService
  ) { }

  async ngOnInit() {
    this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    this.utilitiesService.scrollTo(ProrogaComponent.SCROLL_TARGET);
    this.comonlStorageService.ruolo$.subscribe(async item => {
      this.ruolo = item;
    });
    this.comunicazione = this.data.comunicazione;
    this.comunicazioneToSave = this.data.comunicazioneToSave;
    this.routingParams = this.data.routingParams;
    const nuovaComunicazioneParams = this.routingParams.nuovaComParams;
    this.saveStatusSuccess = this.data.saveStatusSuccess;
    // console.log('Comunicazione' + this.comunicazione);
    // console.log('Params nuova comunicazione: ' + nuovaComunicazioneParams);
    try {
      this.initForm();
      this.setFormMode();
      this.patchValueInForm(this.comunicazioneToSave);
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
      dtFineProroga: new FormControl(null),
    });
  }


  private setFormMode() {
    if (this.VIEW_MODE || this.ANNULLO_MODE) {
      // READ MODE
      this.form[CONTROL_STATE.DISABLE]();
    } else {
      this.utilitiesService.changeControlState(
        this.f.dtFineProroga,
        CONTROL_STATE.ENABLE,
        false
      );
    }
  }

  private patchValueInForm(comunicazione: Comunicazione) {
    let dtFineProroga = comunicazione.rapporto ? comunicazione.rapporto.dtFineProroga : null;
    const tipoSommin: TipoSomministrazione = comunicazione.tipoSomministrazione;
    if (tipoSommin) {
      if (tipoSommin.id === TIPO_SOMMINISTRAZIONE.MISSIONE.ID || tipoSommin.id === TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE_E_MISSIONE.ID) {
        dtFineProroga = comunicazione.missione ? comunicazione.missione.dtFineProroga : null
      }
    }
    this.f.dtFineProroga.patchValue(dtFineProroga);

  }

  async onSubmit() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    try {
      const dtFineProroga = this.f.dtFineProroga.value;
      const tipoSommin: TipoSomministrazione = this.comunicazioneToSave.tipoSomministrazione;

      if (tipoSommin) {
        switch (tipoSommin.id) {
          case TIPO_SOMMINISTRAZIONE.MISSIONE.ID:
            this.comunicazioneToSave.missione.dtFineProroga = dtFineProroga;
            break;
          case TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE_E_MISSIONE.ID:
            this.comunicazioneToSave.missione.dtFineProroga = dtFineProroga;
            this.comunicazioneToSave.rapporto.dtFineProroga = dtFineProroga;
            break;
          case TIPO_SOMMINISTRAZIONE.SOMMINISTRAZIONE.ID:
            this.comunicazioneToSave.rapporto.dtFineProroga = dtFineProroga;
        }
      } else {
        this.comunicazioneToSave.rapporto.dtFineProroga = dtFineProroga;
      }
      let res: Comunicazione;
      const wrapperComunicazione: WrapperComunicazione = {
        comunicazione: this.comunicazioneToSave,
        ruolo: this.ruolo
      }
      res = await this.comunicazioneService.putDatiProroga(wrapperComunicazione).toPromise();
      if (res) {
        this.comunicazioneToSave = await this.comunicazioneService.getComunicazioneById(this.comunicazioneToSave.id).toPromise();
        this.comunicazioneToSave.apiWarnings = res.apiWarnings;
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

  onClickReset() {
    this.alertMessageService.emptyMessages();
    this.form.reset();
    this.patchValueInForm(this.comunicazioneToSave);
  }

}
