/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Comunicazione } from 'src/app/modules/comonlapi';

@Component({
  selector: 'comonl-riepilogo-dati-distacco',
  templateUrl: './riepilogo-dati-distacco.component.html',
  styleUrls: ['./riepilogo-dati-distacco.component.scss']
})
export class RiepilogoDatiDistaccoComponent implements OnInit {

  @Input() comunicazione: Comunicazione;
  @Input() comunicazioneToSave: Comunicazione;
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();

  routingParams;

  constructor() { }

  ngOnInit() {
  }

}
