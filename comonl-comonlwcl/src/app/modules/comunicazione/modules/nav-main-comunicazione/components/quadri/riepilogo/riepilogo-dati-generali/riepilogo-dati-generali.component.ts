/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Comunicazione } from './../../../../../../../comonlapi/model/comunicazione';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Provincia, TipoComunicazioneTu } from 'src/app/modules/comonlapi';

@Component({
  selector: 'comonl-riepilogo-dati-generali',
  templateUrl: './riepilogo-dati-generali.component.html',
  styleUrls: ['./riepilogo-dati-generali.component.scss']
})
export class RiepilogoDatiGeneraliComponent implements OnInit {

  @Input() comunicazione: Comunicazione;
  @Input() comunicazioneToSave: Comunicazione;
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();
  routingParams;

  sezioneRettAnn: SezioneRettAnn;

  constructor() { }

  ngOnInit() {
  }

}

interface SezioneRettAnn {
  provincia?: Provincia;
  annoProtCom?: number;
  numProtCom?: number;
  tipoComunicazioneTu?: TipoComunicazioneTu;
  codiceRegionale?: string;
}
