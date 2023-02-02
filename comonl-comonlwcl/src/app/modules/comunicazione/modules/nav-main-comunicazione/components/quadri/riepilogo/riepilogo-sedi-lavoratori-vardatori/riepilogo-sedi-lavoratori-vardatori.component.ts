/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Comunicazione } from 'src/app/modules/comonlapi';

@Component({
  selector: 'comonl-riepilogo-sedi-lavoratori-vardatori',
  templateUrl: './riepilogo-sedi-lavoratori-vardatori.component.html',
  styleUrls: ['./riepilogo-sedi-lavoratori-vardatori.component.scss']
})
export class RiepilogoSediLavoratoriVardatoriComponent implements OnInit {

  @Input() comunicazione: Comunicazione;
  @Input() comunicazioneToSave: Comunicazione;
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();

  routingParams;

  constructor() { }

  ngOnInit() {
  }

}
