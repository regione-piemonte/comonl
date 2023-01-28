/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PARAMETRO_COMONL_ABILITATO, TYPE_ALERT } from 'src/app/constants';
import { CommonService, Ruolo, UserAccessLog } from 'src/app/modules/comonlapi';

import { UtilitiesService } from 'src/app/services';
import { SidebarService } from 'src/app/services/sidebar.service';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';



@Component({
  selector: 'comonl-scelta-ruolo',
  templateUrl: './scelta-ruolo.component.html',
  styleUrls: ['./scelta-ruolo.component.scss']
})
export class SceltaRuoloComponent implements OnInit {

  listaRuoli: Ruolo[];
  typeMsg: string;
  hide = false;
  listMsg: string[] = [];

  ilRuoloSelezionato: Ruolo;

  constructor(
    private sidebarService: SidebarService,
    private router: Router,
    private comonlStorageService: ComonlStorageService,
    private utilitiesService: UtilitiesService,
    private commonService: CommonService
  ) { }

  async ngOnInit() {
    // TODO - da verificare
    // const res = await this.commonService.getParametro(PARAMETRO_COMONL_ABILITATO).toPromise();
    const res = null;
    if (res && res.codDecodifica === 'N') {
      this.listMsg.push("" + res.dsDecodifica);
      this.settaErroriInPagina()
    } else {
      this.utilitiesService.showSpinner();
      this.sidebarService.hideLeftSideBar();
      this.listaRuoli = await this.utilitiesService.getRuoli();


      if (this.listaRuoli.length === 0) {
        this.listMsg.push("Nessun ruolo trovato ! ");
        this.settaErroriInPagina();
      } else {
        let sonoConsulente = 0;
        let sonoLegale = 0;
        let sonoDelegato = 0;
        let sonoOperatoreCPI = 0;
        this.listaRuoli.forEach(element => {
          if (element.consulenteRespo) {
            sonoConsulente++;
          }
          if (element.legaleRappresentante) {
            sonoLegale++;
          }
          if (element.delegatoRespo) {
            sonoDelegato++;
          }
          if (element.operatoreProvinciale) {
            sonoOperatoreCPI++;
          }
        });
        if (sonoConsulente > 0 ) {
          let mostrareErrore = false;
          let msgErrore = "L'utente possiede una combinazione di ruoli non ammessa : Consulente Responsabile";
          /*
          if (sonoLegale > 0) {
            msgErrore += ", Legale Rappresentante ";
            mostrareErrore = true;
          }
          */
          if (sonoDelegato > 0) {
            msgErrore += ", Delegato Responsabile ";
            mostrareErrore = true;
          }
          if (sonoOperatoreCPI > 0) {
            msgErrore += ", Operatore CPI ";
            mostrareErrore = true;
          }
          if (mostrareErrore) {
            this.listMsg.push(msgErrore);
            this.settaErroriInPagina();
            this.listaRuoli = null;
          }
        }
      }
    }
    this.utilitiesService.hideSpinner();
  }

  onClickChange(el) {
    this.comonlStorageService.setRuolo(el);
    this.ilRuoloSelezionato = el as Ruolo;
    let userAccessLog: UserAccessLog = {
      cfUtente: this.ilRuoloSelezionato.codiceFiscaleUtente,
      dsCognome: this.ilRuoloSelezionato.denominazioneAzienda,
      dsRuolo: this.ilRuoloSelezionato.ilRuolo,
      dsNome: this.ilRuoloSelezionato.codiceFiscaleAzienda
    }
    this.commonService.insertUserAccessLog(userAccessLog).toPromise();
    this.router.navigate(['/home']);
  }

  private settaErroriInPagina() {
    this.typeMsg = TYPE_ALERT.DANGER,
      this.hide = true,
      this.listMsg = this.listMsg
  }


}
