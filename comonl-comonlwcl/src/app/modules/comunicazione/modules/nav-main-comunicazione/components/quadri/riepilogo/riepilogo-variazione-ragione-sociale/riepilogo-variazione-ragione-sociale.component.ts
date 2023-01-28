/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Comunicazione } from 'src/app/modules/comonlapi';

@Component({
  selector: 'comonl-riepilogo-variazione-ragione-sociale',
  templateUrl: './riepilogo-variazione-ragione-sociale.component.html',
  styleUrls: ['./riepilogo-variazione-ragione-sociale.component.scss']
})
export class RiepilogoVariazioneRagioneSocialeComponent implements OnInit {

  @Input() comunicazione: Comunicazione;
  @Input() comunicazioneToSave: Comunicazione;
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();

  routingParams;

  constructor() { }

  ngOnInit() {
  }

}
