/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Router } from '@angular/router';
import { Comunicazione } from './../../../comonlapi/model/comunicazione';
import { Component, OnInit } from '@angular/core';
import { UtilitiesService } from 'src/app/services';
import { ComunicazioneService, Ruolo } from 'src/app/modules/comonlapi';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { Utils } from 'src/app/utils';

@Component({
  selector: 'comonl-invio-comunicazione',
  templateUrl: './invio-comunicazione.component.html',
  styleUrls: ['./invio-comunicazione.component.scss']
})
export class InvioComunicazioneComponent implements OnInit {

  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="invio-comunicazione"]';

  listaComunicazioni: Comunicazione[];
  ruolo: Ruolo;

  constructor(
    private router: Router,
    private utilitiesService: UtilitiesService,
    private comonlStorageService: ComonlStorageService,
    private comunicazioneService: ComunicazioneService,
    private alertMessageService: AlertMessageService
  ) {
    this.listaComunicazioni = this.router.getCurrentNavigation().extras.state.esitoComunicazione;
    console.log('comunicazione inviata: ' + JSON.stringify(this.listaComunicazioni));
  }

  ngOnInit() {
    this.comonlStorageService.ruolo$.subscribe((el:Ruolo) => this.ruolo = el);
    this.utilitiesService.scrollTo(InvioComunicazioneComponent.SCROLL_TARGET);
  }

  async stampaComunicazione(idComunicazione: number){
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    try{
      const res = await this.comunicazioneService.stampaComunicazioneById(idComunicazione,this.ruolo.operatoreProvinciale, 'response').toPromise(); 
      if (res) {
        const fileName = Utils.extractFileNameFromContentDisposition(res.headers.get('Content-Disposition'));
        this.utilitiesService.downloadBlobFile(fileName, res.body);
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

}
