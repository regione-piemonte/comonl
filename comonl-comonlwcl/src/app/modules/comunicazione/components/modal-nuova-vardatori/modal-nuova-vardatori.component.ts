/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CONSTANTS_MODE } from 'src/app/constants';
import { LogService, UtilitiesService } from 'src/app/services';
import { NavComunicazioneParams } from '../../modules/nav-main-comunicazione/models/nav-comunicazione-params';

@Component({
  selector: 'comonl-modal-nuova-vardatori',
  templateUrl: './modal-nuova-vardatori.component.html',
  styleUrls: ['./modal-nuova-vardatori.component.scss']
})
export class ModalNuovaVardatoriComponent implements OnInit {

  @Input() title: string;

  formNuovaVardatori: FormGroup;
  showForm = false;

  get f() { return this.formNuovaVardatori.controls as any; }
  get conditionConfermaBtnDisable(): boolean { return  this.f.comunicazione.value !== 'RS' && this.f.comunicazione.value !== 'T'; }

  constructor(public activeModal: NgbActiveModal,
              private router: Router,
              private logService: LogService,
              private utilitiesService: UtilitiesService
              ) { }

  async ngOnInit() {
    this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    try {
      this.initForm();
      setTimeout(() => this.showForm = true, 500);
    } catch (e) {

    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  private initForm() {
    this.formNuovaVardatori = new FormGroup({
      // idComTipoTracciato: new FormControl('VD'),
      comunicazione: new FormControl('T')
    });
  }

  async goToNuovaComunicazione() {
    this.activeModal.close();
    const formParams = this.formNuovaVardatori.getRawValue();
    let configurazioneQuadri: string;
    if (formParams.comunicazione && formParams.comunicazione === 'RS') {
      configurazioneQuadri = 'VAR_RS';
      formParams.idComTipoTracciato = 'VD';
      formParams.tipoComunicazione = 'VARIAZIONE DATORE - RAGIONE SOCIALE';
    } else {
      configurazioneQuadri = 'VAR_T';
      formParams.idComTipoTracciato = 'VD';
      formParams.tipoComunicazione = 'VARIAZIONE DATORE - DATORE LAVORO';
    }
    const parametriNavigazione: NavComunicazioneParams = {
      formModalParams: formParams,
      mode: CONSTANTS_MODE.INS,
      configurazioneQuadri,
      flgNuovaCO: false
    };
    this.router.navigate(['/nav-main-comunicazione'], { state: { parametriNavigazione }});
  }

  // goToNuovaComunicazione() {
  //   this.activeModal.close();
  //   switch (this.f.tipoComunicazione.value) {
  //     case 'RS':
  //       this.router.navigate(['/dati-generali-vardatori'], { state: { tipo: 'RS'} });
  //       break;
  //     case 'T':
  //       this.router.navigate(['/dati-generali-vardatori'], { state: { tipo: 'T'} });
  //       break;
  //   }
  // }
}
