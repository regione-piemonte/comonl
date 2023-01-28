/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Comunicazione } from './../../../../../../../comonlapi/model/comunicazione';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'comonl-riepilogo-dati-lavoratore',
  templateUrl: './riepilogo-dati-lavoratore.component.html',
  styleUrls: ['./riepilogo-dati-lavoratore.component.scss']
})
export class RiepilogoDatiLavoratoreComponent implements OnInit {

  @Input() comunicazione: Comunicazione;
  @Input() comunicazioneToSave: Comunicazione;
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();
  routingParams;

  constructor() { }

  ngOnInit() {
  }

}
