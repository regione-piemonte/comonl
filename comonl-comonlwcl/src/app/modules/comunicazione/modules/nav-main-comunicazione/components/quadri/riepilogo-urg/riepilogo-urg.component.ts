/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { marker } from '@biesbjerg/ngx-translate-extract-marker';
import { TranslateService } from '@ngx-translate/core';
import { CONSTANTS_MODE } from 'src/app/constants';
import { ApiError, Comune, Comunicazione, ComunicazioneControlliService, ComunicazioneUrgHolder, Datore, Lavoratore, Rapporto, Ruolo, WrapperComunicazione } from 'src/app/modules/comonlapi';
import { ComunicazioneService } from 'src/app/modules/comonlapi/api/comunicazione.service';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { LogService, UtilitiesService } from 'src/app/services';
import { AdComponent } from '../../../models/ad-component';

@Component({
  selector: 'comonl-ripilogo-urg',
  templateUrl: './riepilogo-urg.component.html',
  styleUrls: ['./riepilogo-urg.component.scss']
})
export class RiepilogoUrgComponent implements OnInit, AdComponent {

  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="riepilogo-urg"]';

  DISABLE: boolean = true;

  @Input() data: any;

  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();
  @Output() goTonext: EventEmitter<any> = new EventEmitter<any>();

  comunicazione: Comunicazione;
  comunicazioneToSave: Comunicazione;
  routingParams;

  ruolo: Ruolo;

  form: FormGroup;
  saveStatusSuccess: boolean;
  warnings: ApiError[] = [];

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
  constructor(
    private logService: LogService,
    private utilitiesService: UtilitiesService,
    private comunicazioneService: ComunicazioneService,
    private comunicazioneControlliService: ComunicazioneControlliService,
    private router: Router,
    private translateService: TranslateService,
    private promptModalService: PromptModalService,
    private alertMessageService: AlertMessageService,
  ) { }

  async ngOnInit() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    this.utilitiesService.scrollTo(RiepilogoUrgComponent.SCROLL_TARGET);
    this.comunicazione = this.data.comunicazione;
    this.comunicazioneToSave = this.data.comunicazioneToSave;
    this.ruolo = this.data.ruolo;
    this.routingParams = this.data.routingParams;
    this.saveStatusSuccess = this.data.saveStatusSuccess;
    if (this.saveStatusSuccess) {
      this.alertMessageService.setSuccessSingleMsg('Salvataggio effettuato con successo');
    }
    try{
      if(!this.VIEW_MODE){
        const res = await this.comunicazioneControlliService.chkRiepilogo(this.comunicazione.id).toPromise();
        if(res && res.length > 0){
          this.warnings = this.warnings.concat(res);
        } 
       if(this.warnings && this.warnings.length > 0){
        this.alertMessageService.setWarningMsg(this.warnings);
        }
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

  async onClickInviaComunicazione(comunicazione, typeMessage) {
    console.log('comunicazione da inviare: ' + JSON.stringify(comunicazione));
    const title = this.translate(marker('APP.SEND_COMUNICAZIONE'));
    const message = this.translate(marker(typeMessage));
    const pYes = this.translate(marker('APP.SEND'));
    const pNo = this.translate(marker('APP.ANNULLA'));

    const userChoice = await this.promptModalService.openPrompt(title, message, pYes, pNo, 'warning');
    if (userChoice) {
      this.utilitiesService.showSpinner();
      this.alertMessageService.emptyMessages();
      const wrapperComunicazione: WrapperComunicazione = {
        ruolo: this.ruolo,
        comunicazione: comunicazione
      };
      try {
        let res: Comunicazione[];
        // let comunicazioneToSave: Comunicazione;
        res = await this.comunicazioneService.inviaComunicazione(wrapperComunicazione).toPromise();
        // console.log('res comunicazione: ' + JSON.stringify(res));
        if (res) {
          // comunicazioneToSave = await this.comunicazioneService.getComunicazioneById(res.id).toPromise();
          // console.log('comunicazioneToSave dopo l\'invio: ' + JSON.stringify(comunicazioneToSave));
          // this.alertMessageService.setSuccessSingleMsg('Salvataggio effettuato con successo');
          this.router.navigate(['/invio-comunicazione'], { state: { esitoComunicazione: res }});
        }
      } catch (e) {
        console.log(e);
        this.alertMessageService.setErrorMsg(e.error);
      } finally {
        this.utilitiesService.hideSpinner();
      }
    }
  }

  private translate(key: string) {
    return this.translateService.instant(key);
  }

}
