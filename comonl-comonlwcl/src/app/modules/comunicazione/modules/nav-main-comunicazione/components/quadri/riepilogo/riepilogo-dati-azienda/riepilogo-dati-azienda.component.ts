/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Comunicazione } from 'src/app/modules/comonlapi';

@Component({
  selector: 'comonl-riepilogo-dati-azienda',
  templateUrl: './riepilogo-dati-azienda.component.html',
  styleUrls: ['./riepilogo-dati-azienda.component.scss']
})
export class RiepilogoDatiAziendaComponent implements OnInit {

  @Input() comunicazione: Comunicazione;
  @Input() comunicazioneToSave: Comunicazione;
  @Output() setComunicazioneToSave = new EventEmitter<Comunicazione>();

  routingParams;

  constructor() { }

  ngOnInit() {
  }

}
