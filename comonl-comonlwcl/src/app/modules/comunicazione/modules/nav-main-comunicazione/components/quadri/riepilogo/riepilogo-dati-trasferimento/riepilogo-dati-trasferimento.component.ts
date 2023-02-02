/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TIPO_SOMMINISTRAZIONE, TIPO_TRACCIATO } from 'src/app/constants';
import { Comunicazione, Rapporto } from 'src/app/modules/comonlapi';

@Component({
  selector: 'comonl-riepilogo-dati-trasferimento',
  templateUrl: './riepilogo-dati-trasferimento.component.html',
  styleUrls: ['./riepilogo-dati-trasferimento.component.scss']
})
export class RiepilogoDatiTrasferimentoComponent implements OnInit {

  @Input() comunicazione: Comunicazione;
  @Input() comunicazioneToSave: Comunicazione;
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();

  routingParams;
  rapporto: Rapporto;
  constructor() { }

  ngOnInit() {
    if (
      this.comunicazione.tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID || (this.comunicazione.tipoTracciato.id === TIPO_TRACCIATO.UNISOMM.ID &&
      this.comunicazione.tipoSomministrazione.id !== TIPO_SOMMINISTRAZIONE.MISSIONE.ID)
    ) {
        this.rapporto = this.comunicazione.rapporto;
      }
    if (this.comunicazione.tipoTracciato.id === TIPO_TRACCIATO.UNISOMM.ID &&
        this.comunicazione.tipoSomministrazione.id === TIPO_SOMMINISTRAZIONE.MISSIONE.ID) {
          this.rapporto = this.comunicazione.missione;
    }
  }

}
