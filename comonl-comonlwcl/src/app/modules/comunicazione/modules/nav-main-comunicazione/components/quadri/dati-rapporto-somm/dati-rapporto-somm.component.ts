/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { DecodificaService, TipologiaTirocinio } from 'src/app/modules/comonlapi';
import { LogService, UtilitiesService } from 'src/app/services';
import { PersistenceComunicazioneWreapper } from '../../nav-main-comunicazione/nav-main-comunicazione.component';

@Component({
  selector: 'comonl-dati-rapporto-somm',
  templateUrl: './dati-rapporto-somm.component.html',
  styleUrls: ['./dati-rapporto-somm.component.scss']
})
export class DatiRapportoSommComponent implements OnInit {

  @Output() readonly datiForm = new EventEmitter<any>();
  @Output() readonly resetForm = new EventEmitter();
  @Output() persistenceNotification = new EventEmitter<PersistenceComunicazioneWreapper>();

  listaTipologiaTirocinio: TipologiaTirocinio[] = [];

  formDatiRapportoSomm: FormGroup;
  showFormSomm = false;

  get f() {
    return this.formDatiRapportoSomm.controls as any;
  }

  constructor(
    private logService: LogService,
    private decodificaService: DecodificaService,
    private utilitiesService: UtilitiesService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {
    this.initForm();
  }

  async ngOnInit() {
    await this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    // TODO popolamento combo box
    try {
      const [
        tipoTirocinio
      ] = await Promise.all([
      this.decodificaService.getTipologiaTirocinio().toPromise()
      ]);
      this.listaTipologiaTirocinio = tipoTirocinio;
    } catch (e) {

    } finally {
      await this.utilitiesService.hideSpinner();
    }
  }

  initForm() {
    this.formDatiRapportoSomm = this.formBuilder.group({
      dataInizioRapportoSomm: this.formBuilder.control(null),
      tipoRapportoSomm: this.formBuilder.control(null),
      dataFineRapportoSomm: this.formBuilder.control(null),
      dataFinePeriodoFormativoSomm: this.formBuilder.control(null),
      numMatricola: this.formBuilder.control(null),
      entePrevidenzialeSomm: this.formBuilder.control(null),
      codEntePrevidenzialeSomm: this.formBuilder.control(null),
      indennita: this.formBuilder.control(null),
      lavoratoreMob: this.formBuilder.control(null)
    });
  }

  onSubmit() {
    this.router.navigate(['/dettaglio-comunicazione']);
  }

}
