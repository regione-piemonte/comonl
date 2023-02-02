/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { StatoDelega } from './../../../../comonlapi/model/statoDelega';
import { FormRicercaDelega } from './../../../../comonlapi/model/formRicercaDelega';
import { DelegaService } from './../../../../comonlapi/api/delega.service';
import { Router } from '@angular/router';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DecodificaService, Ruolo } from 'src/app/modules/comonlapi';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { LogService, UtilitiesService } from 'src/app/services';
import { Utils } from 'src/app/utils';

@Component({
  selector: 'comonl-form-accreditamento-deleghe',
  templateUrl: './form-accreditamento-deleghe.component.html',
  styleUrls: ['./form-accreditamento-deleghe.component.scss'],
})
export class FormAccreditamentoDelegheComponent implements OnInit {

  @Input() formRicercaDelega: FormRicercaDelega;
  @Input() formPristine: FormRicercaDelega;
  // @Input() flgDettaglio: boolean;
  @Input() set FLGDETTAGLIO(flgDettaglio: boolean) {
    this.flgDettaglio = flgDettaglio;
  }

  @Output() readonly datiRicerca = new EventEmitter<any>();
  @Output() readonly resetForm = new EventEmitter();

  form: FormGroup;
  showForm = false;
  showTable = false;

  ruolo: Ruolo;
  delega: any;

  flgDettaglio: boolean;


  statiDelega: StatoDelega[] = [];
  listaStatiSelected: StatoDelega[] = [];

  // restituisce formControls
  get f() {
    return this.form.controls as any;
  }

  get FORM_INVALID(): boolean{
    return (Utils.isNullOrUndefinedOrCampoVuoto(this.f.codiceFiscaleDelegato.value) && Utils.isNullOrUndefinedOrCampoVuoto(this.f.codiceFiscaleDelegante.value) && Utils.isNullOrUndefinedOrCampoVuoto(this.f.codiceFiscaleImpresa.value));
  }


  constructor(
    private logService: LogService,
    private utilitiesService: UtilitiesService,
    private router: Router,
    private formBuilder: FormBuilder,
    private comonlStorageService: ComonlStorageService,
    private alertMessageService: AlertMessageService,
    private decodificaService: DecodificaService,
    private delegaService: DelegaService
  ) {}

  async ngOnInit() {
    await this.utilitiesService.showSpinner();
    // TODO ottenimento ruolo
    this.logService.info(this.constructor.name, 'ngOnInit');
    // chiamata ai servizi di decodifica per popolamento combo e checkbox
    try {
      const [statiDelegas] = await Promise.all([
        this.decodificaService.getStatoDelega().toPromise()
      ]);
      this.statiDelega = statiDelegas;
      this.comonlStorageService.ruolo$.subscribe(async el => {
        this.ruolo = el;
        // console.log('statiDelega: ' + JSON.stringify(this.statiDelega));
        this.defineForm();
        this.form.patchValue(this.formRicercaDelega); // al primo caricamento coincide con formPristine (vedi component padre)
        // if (this.flgDettaglio) {
        // }
        this.patchParamsInForm(this.formRicercaDelega);
        this.showForm = true;
      });
    } catch (e) {
      this.alertMessageService.setErrorMsg(e.error);
    } finally {
      await this.utilitiesService.hideSpinner();
    }
  }

  // Define form: definisce il modello strutturale del form
  private defineForm() {
    this.form = this.formBuilder.group({
      codiceFiscaleDelegato: this.formBuilder.control(null),
      cognomeDelegato: this.formBuilder.control(null),
      nomeDelegato: this.formBuilder.control(null),
      codiceFiscaleDelegante: this.formBuilder.control(null),
      cognomeDelegante: this.formBuilder.control(null),
      nomeDelegante: this.formBuilder.control(null),
      codiceFiscaleImpresa: this.formBuilder.control(null),
      denominazione: this.formBuilder.control(null),
      statoDelegas: this.formBuilder.control(null)
    });
  }

  onSubmit() {
    this.alertMessageService.emptyMessages();
    const ricercaDelegaToSend = this.form.getRawValue() as FormRicercaDelega;
    try {
      ricercaDelegaToSend.statoDelegas = this.listaStatiSelected;
      this.datiRicerca.emit(ricercaDelegaToSend);
    } catch (e) {
      this.alertMessageService.setWarningMsg(e.message);
    }

  }

  onClickReset() {
    this.form.patchValue(this.formPristine);
    this.listaStatiSelected = [];
    this.resetForm.emit();
  }

  checkSelectedStates(statoD: string) {
    if ( this.listaStatiSelected &&  this.listaStatiSelected.length > 0) {
      return  this.listaStatiSelected.find(item => item.id === statoD);
    }
  }

  goToNuovaDelega(modalita: string) {
    this.router.navigate(['./dettaglio-delega'], { state: {mode: modalita, parametriRicerca: this.formRicercaDelega}});
  }


  selectRemoveStato(statoD: StatoDelega) {
    if (this.listaStatiSelected) {
      if (this.listaStatiSelected.length > 0) {
        const statoTmp = this.listaStatiSelected.find(item => item.id === statoD.id);
        const indexItemToRemove: number = this.listaStatiSelected.indexOf(statoTmp);
        if (indexItemToRemove >= 0) {
          this.listaStatiSelected.splice(indexItemToRemove, 1);
        } else {
          this.listaStatiSelected.push(statoD);
        }
      } else {
        this.listaStatiSelected.push(statoD);
      }

    }
  }

  patchParamsInForm(pFormRicerca: any) {
    let goSearch = false;
    if (pFormRicerca && !Utils.isNullOrUndefinedOrCampoVuoto(pFormRicerca.page)) {
      goSearch = true;
    }
    if (pFormRicerca.idStatiDelega) {
      const ids: string[] = pFormRicerca.idStatiDelega.split(',');
      ids.forEach(id => {
        this.listaStatiSelected.push(this.statiDelega.find(el => el.id.toString() === id));
      });
    }

    if (goSearch) {
      this.onSubmit();
    }

  }

  // goToDettaglioDelega(modifica: boolean) {
  //   this.router.navigate(['./dettaglio-delega'], { state: { mode: modifica, azienda: this.delega } });
  // }

}
