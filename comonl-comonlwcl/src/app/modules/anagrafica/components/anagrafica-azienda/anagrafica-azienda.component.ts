/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { UtilitiesService } from 'src/app/services';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { WrapperHelper } from 'src/app/models/wrappwer-helper';
import { NavComunicazioneParams } from 'src/app/modules/comunicazione/modules/nav-main-comunicazione/models/nav-comunicazione-params';

@Component({
  selector: 'comonl-anagrafica-azienda',
  templateUrl: './anagrafica-azienda.component.html',
  styleUrls: ['./anagrafica-azienda.component.scss']
})
export class AnagraficaAziendaComponent implements OnInit {

  private static readonly SCROLL_TARGET = 'em[data-scroll-marker="anagrafica-azienda"]';

  parametriNavigazione: NavComunicazioneParams;
  wrapperHelper: WrapperHelper;
  cfAzienda: string;

  constructor(
    private router: Router,
    private utilitiesService: UtilitiesService
  ) {
    const extras = this.router.getCurrentNavigation().extras;
    if (extras) {
       const state = extras.state;
       if (state) {
        this.parametriNavigazione = state.parametriNavigazione;
        this.wrapperHelper = state.wrapperHelper;
        this.cfAzienda = state.cf;
      }
     }
   }

  ngOnInit() {
    this.utilitiesService.scrollTo(AnagraficaAziendaComponent.SCROLL_TARGET);
  }

  goToAnagrafica(modalita: string) {
    // console.log('lavoratoreSilp in form ricerca lavoratore: ' + JSON.stringify(this.lavoratoreSilp));
    this.router.navigate(['./dettaglio-azienda'], { state: { mode: modalita, azienda: null } });
  }

}
