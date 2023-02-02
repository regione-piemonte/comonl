/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MESI } from 'src/app/constants';
import { LogService, UserService, UtilitiesService } from 'src/app/services';


@Component({
  selector: 'comonl-dettaglio-comunicazione',
  templateUrl: './dettaglio-comunicazione.component.html',
  styleUrls: ['./dettaglio-comunicazione.component.scss'],
})
export class DettaglioComunicazioneComponent implements OnInit {
  @Output() readonly datiForm = new EventEmitter<any>();
  @Output() readonly resetForm = new EventEmitter();

  formDettaglioComunicazione: FormGroup;
  showForm = false;

  get f() {
    return this.formDettaglioComunicazione.controls as any;
  }
  constructor(
    private logService: LogService,
    private utilitiesService: UtilitiesService,
    private userService: UserService,
    private router: Router
  ) {
    this.initForm();
  }

  async ngOnInit() {
    await this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    const now = new Date();
    console.log(now);
    this.showForm = true;
    await this.utilitiesService.hideSpinner();
  }

  private initForm() {
    this.formDettaglioComunicazione = new FormGroup({
      cfTutore: new FormControl(),
      nome: new FormControl(),
      cognome: new FormControl(),
      sesso: new FormControl(),
      dataNascita: new FormControl(),
      titolare: new FormControl(),
      codQualifica: new FormControl(),
      descrQualifica: new FormControl(),
      anniEsperienza: new FormControl(),
      livInquadramento: new FormControl(),
      grado: new FormControl(),
      dataVisita: new FormControl(),
      accettazione: new FormControl()
    });
  }

  onSubmit() {
    this.router.navigate(['/dettaglio-comunicazione']);
  }

  private dataCf(codiceFiscale) {
    // tslint:disable-next-line: prefer-const
    let [anno, giorno ] = [ codiceFiscale.substring(6, 8), codiceFiscale.substring(9, 11) ];
    if (giorno > 40) {
      giorno -= 40;
      giorno = '0' + giorno;
    }
    return (anno < 20 ? '20' : '19' ) + anno + '-' + MESI[codiceFiscale.charAt(8)] + '-' + giorno;
  }

  private sessoCf(codiceFiscale) {
    return codiceFiscale.substring(9, 11) > 40 ? 'F' : 'M';
  }

  onClickPreCompila() {
    const cfInput: string = this.f.cfTutore.value;
    console.log(cfInput);
    if (cfInput && cfInput !== '') {
      const sessoTutore = this.sessoCf(cfInput);
      const dataNascita = this.dataCf(cfInput);
      this.f.sesso.setValue(sessoTutore);
      this.f.dataNascita.setValue(new Date(dataNascita));
    } else {
      this.utilitiesService.hideSpinner();
      console.log('Errore');
    }
    this.utilitiesService.hideSpinner();
  }

}
