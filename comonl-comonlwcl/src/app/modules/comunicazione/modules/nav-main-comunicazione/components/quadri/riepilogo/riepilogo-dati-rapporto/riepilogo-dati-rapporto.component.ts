/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TIPO_COMUNICAZIONE } from 'src/app/constants';
import { Comunicazione, TipoComunicazione, TipoSomministrazione, TipoTrasferimento, VariazioneSomm } from 'src/app/modules/comonlapi';

@Component({
  selector: 'comonl-riepilogo-dati-rapporto',
  templateUrl: './riepilogo-dati-rapporto.component.html',
  styleUrls: ['./riepilogo-dati-rapporto.component.scss']
})
export class RiepilogoDatiRapportoComponent implements OnInit {

  @Input() comunicazione: Comunicazione;
  @Input() comunicazioneToSave: Comunicazione;
  @Input() variazioneSomm: VariazioneSomm;
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();

  routingParams;

  get IS_DISTACCO(): boolean{
    const tipoComunicazione: TipoComunicazione = this.comunicazione.tipoComunicazione;
    if(tipoComunicazione.id !== TIPO_COMUNICAZIONE.TRASFERIMENTO_DISTACCO.ID)
    return false;
    const tipoSomm: TipoSomministrazione = this.comunicazione.tipoSomministrazione;
    const tipoTrasferimento: TipoTrasferimento = this.comunicazione.tipoTrasferimento;

    return (!this.variazioneSomm && this.comunicazione.rapporto && this.comunicazione.rapporto.trasformazionerl && this.comunicazione.rapporto.trasformazionerl.codTrasformazionirlMin === 'DL');
  }

  get isDataVisibile(): boolean{
    const tipoComunicazione: TipoComunicazione = this.comunicazione.tipoComunicazione;
    const tipoSomministrazione: TipoSomministrazione = this.comunicazione.tipoSomministrazione;
    return (tipoSomministrazione && tipoSomministrazione.id != null) ||
        (tipoComunicazione.id === TIPO_COMUNICAZIONE.ASSUNZIONE.ID ||
        tipoComunicazione.id === TIPO_COMUNICAZIONE.TRASFORMAZIONE.ID ||
        tipoComunicazione.id === TIPO_COMUNICAZIONE.CESSAZIONE.ID);
  }

  constructor() { }

  ngOnInit() {
  }

}
