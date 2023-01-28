/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { DatiAzienda } from './../../../../comonlapi/model/datiAzienda';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
@Component({
  selector: 'comonl-risultati-ricerca-azienda',
  templateUrl: './risultati-ricerca-azienda.component.html',
  styleUrls: ['./risultati-ricerca-azienda.component.scss']
})
export class RisultatiRicercaAziendaComponent implements OnInit {

  @Input() aziendaSilp: DatiAzienda;

  ricercaEffettuata: boolean;
  constructor() { }

  ngOnInit() {}

}
