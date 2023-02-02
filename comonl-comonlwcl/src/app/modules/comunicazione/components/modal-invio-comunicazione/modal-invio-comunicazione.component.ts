/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Comunicazione } from './../../../comonlapi/model/comunicazione';
import { AlertMessageService } from './../../../comonlcommon/services/alert-message.service';
import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { LogService, UtilitiesService } from 'src/app/services';

@Component({
  selector: 'comonl-modal-invio-comunicazione',
  templateUrl: './modal-invio-comunicazione.component.html',
  styleUrls: ['./modal-invio-comunicazione.component.scss']
})
export class ModalInvioComunicazioneComponent implements OnInit {

  @Input() comunicazione: Comunicazione;

  constructor(public activeModal: NgbActiveModal,
              private logService: LogService,
              private alertMessageService: AlertMessageService,
              private utilitiesService: UtilitiesService) {
  }

  async ngOnInit() {
    this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    try {
      this.alertMessageService.setSuccessSingleMsg('Invio della comunicazione effettuato con successo');

    } catch (e) {
      console.log(e);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }
}
