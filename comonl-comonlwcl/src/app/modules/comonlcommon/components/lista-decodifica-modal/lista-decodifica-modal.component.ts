/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TYPE_DECODIFICA_GENERICA } from 'src/app/constants';
import { DecodificaGenerica, DecodificaService } from 'src/app/modules/comonlapi';
import { UtilitiesService } from 'src/app/services';
import { AlertMessageService } from '../../services/alert-message.service';

@Component({
  selector: 'comonl-lista-decodifica-modal',
  templateUrl: './lista-decodifica-modal.component.html',
  styleUrls: ['./lista-decodifica-modal.component.scss']
})
export class ListaDecodificaModalComponent {

  @Input() title: string;
  @Input() list: DecodificaGenerica[];
  @Input() filtroDiPartenza: DecodificaGenerica;
  @Input() callback;
  @Input() modal;
  @Input() typeSearch: string;
  searchList: DecodificaGenerica[];
  formGroupDecodifica: FormGroup;
  formRicerca: FormGroup;

  typeMsg: string;
  hide = false;
  message: string;
  constructor(
    private utilitiesService: UtilitiesService,
    private decoDificaService: DecodificaService,
    private alertMessageService: AlertMessageService
  ) { }

  ngOnInit() {
    this.utilitiesService.hideSpinner();
    this.initForm();
  }

  private initForm() {
    this.formGroupDecodifica = new FormGroup({
      decodifica: new FormControl()
    });
    this.formRicerca = new FormGroup({
      codDecodifica: new FormControl(this.filtroDiPartenza ? this.filtroDiPartenza.codDecodifica : null),
      dsDecodifica: new FormControl(this.filtroDiPartenza ? this.filtroDiPartenza.dsDecodifica : null)
    });

  }

  onClickConferma() {
    const selectedItem = this.formGroupDecodifica.controls.decodifica.value;
    this.callback(this.modal, selectedItem);
    console.log(selectedItem);
  }

  onClickCerca() {
    this.formGroupDecodifica.reset();
    this.findDecodifica();
  }

  private async findDecodifica() {
    await this.utilitiesService.showSpinner();
    this.list = null;
    let decodifica: DecodificaGenerica = this.formRicerca.getRawValue() as DecodificaGenerica;
    if (this.filtroDiPartenza && this.filtroDiPartenza.idFiltroFacoltativo) {
      decodifica.idFiltroFacoltativo = this.filtroDiPartenza.idFiltroFacoltativo;
    }
    try {
      switch (this.typeSearch) {
        case TYPE_DECODIFICA_GENERICA.COMUNE:
          this.list = await this.decoDificaService.postComuneDecodifica(decodifica).toPromise();
          break;
        case TYPE_DECODIFICA_GENERICA.CCNL:
          this.list = await this.decoDificaService.postCcnlDecodifica(decodifica).toPromise();
          break;
          case TYPE_DECODIFICA_GENERICA.ATECO:
          this.list = await this.decoDificaService.postAtecofinDecodifica(decodifica).toPromise();
          break;
        case TYPE_DECODIFICA_GENERICA.LIV_RETR:
          decodifica.idCcnl = this.filtroDiPartenza.idCcnl;
          this.list = await this.decoDificaService.postLivelloRetribuzioneDecodifica(decodifica).toPromise();
          break;
        case TYPE_DECODIFICA_GENERICA.QUAL_ISTAT:
          this.list = await this.decoDificaService.postQualificaDecodifica(decodifica).toPromise();
          break;
        case TYPE_DECODIFICA_GENERICA.STATI_ESTERI:
          this.list = await this.decoDificaService.postStatiEsteriDecodifica(decodifica).toPromise();
        break;
        case TYPE_DECODIFICA_GENERICA.ATECO:
          this.list = await this.decoDificaService.postAtecofinDecodifica(decodifica).toPromise();
        break;
        default:
      }
    } catch (e) {
      this.message = e.error[0].errorMessage;
    } finally {
      await this.utilitiesService.hideSpinner();
    }
  }

}
