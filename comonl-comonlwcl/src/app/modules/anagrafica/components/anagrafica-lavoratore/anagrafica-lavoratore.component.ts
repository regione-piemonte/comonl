/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { Router } from '@angular/router';
import { WrapperHelper } from 'src/app/models/wrappwer-helper';
import { NavComunicazioneParams } from 'src/app/modules/comunicazione/modules/nav-main-comunicazione/models/nav-comunicazione-params';

@Component({
  selector: 'comonl-anagrafica-lavoratore',
  templateUrl: './anagrafica-lavoratore.component.html',
  styleUrls: ['./anagrafica-lavoratore.component.scss']
})
export class AnagraficaLavoratoreComponent implements OnInit {

  parametriNavigazione: NavComunicazioneParams;
  wrapperHelper: WrapperHelper;
  cfLavoratoreSilp;

  constructor(
    private router: Router
  ) {
    const extras = this.router.getCurrentNavigation().extras;
    if (extras) {
      const state = extras.state;
      if (state) {
        this.parametriNavigazione = state.parametriNavigazione;
        this.wrapperHelper = state.wrapperHelper;
        this.cfLavoratoreSilp = state.cf;
      }
    }
   }

  ngOnInit() {
  }

  goToAnagrafica(modalita: string) {
    // console.log('lavoratoreSilp in form ricerca lavoratore: ' + JSON.stringify(this.lavoratoreSilp));
    this.router.navigate(['./dati'], { state: { mode: modalita, lavoratore: null } });
  }

}
