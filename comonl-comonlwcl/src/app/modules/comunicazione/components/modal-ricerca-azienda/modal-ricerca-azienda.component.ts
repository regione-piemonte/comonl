/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { REGIONE } from 'src/app/constants';
import { DecodificaService, Provincia } from 'src/app/modules/comonlapi';
import { LogService, UtilitiesService } from 'src/app/services';

@Component({
  selector: 'comonl-modal-ricerca-azienda',
  templateUrl: './modal-ricerca-azienda.component.html',
  styleUrls: ['./modal-ricerca-azienda.component.scss']
})
export class ModalRicercaAziendaComponent implements OnInit {

  @Input() title: string;

  formRicercaAzienda: FormGroup;
  showForm = false;

  listaProvince: Provincia[] = [];

  get f() { return this.formRicercaAzienda.controls as any; }
  get conditionConfermaBtnDisable(): boolean { return  this.f.urg.value !== 'Si' && !this.f.comunicazione.value; }


  constructor(public activeModal: NgbActiveModal,
              private router: Router,
              private logService: LogService,
              private decodificaService: DecodificaService,
              private utilitiesService: UtilitiesService) {
    this.initForm();
  }

  async ngOnInit() {
    this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    try {
      this.listaProvince  = await this.decodificaService.getProvincia(REGIONE.PIEMONTE.COD_MIN).toPromise();
      this.showForm = true;
    } catch (e) {

    } finally {
      this.utilitiesService.hideSpinner();
    }

  }

  private initForm() {
    this.formRicercaAzienda = new FormGroup({
      cfAzienda: new FormControl(null),
      provincia: new FormControl(null)
    });
  }

  searchSedi() {

  }

}
