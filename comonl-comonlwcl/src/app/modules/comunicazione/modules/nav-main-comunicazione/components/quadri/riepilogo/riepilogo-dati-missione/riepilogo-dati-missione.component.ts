/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TIPO_COMUNICAZIONE } from 'src/app/constants';
import { Comunicazione, TipoComunicazione, TipoSomministrazione } from 'src/app/modules/comonlapi';

@Component({
  selector: 'comonl-riepilogo-dati-missione',
  templateUrl: './riepilogo-dati-missione.component.html',
  styleUrls: ['./riepilogo-dati-missione.component.scss']
})
export class RiepilogoDatiMissioneComponent implements OnInit {

  @Input() comunicazione: Comunicazione;
  @Input() comunicazioneToSave: Comunicazione;
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();

  routingParams;

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
