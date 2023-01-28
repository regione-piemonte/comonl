/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CONTROL_STATE, REGIONE } from 'src/app/constants';
import { DecodificaService, Provincia, Ruolo, StatoComunicazione, TipoComunicazioneTu } from 'src/app/modules/comonlapi';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { LogService, UtilitiesService } from 'src/app/services';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { FormRicercaComunicazione } from './../../../../comonlapi/model/formRicercaComunicazione';
@Component({
  selector: 'comonl-form-ricerca-vardatori',
  templateUrl: './form-ricerca-vardatori.component.html',
  styleUrls: ['./form-ricerca-vardatori.component.scss']
})
export class FormRicercaVardatoriComponent implements OnInit {

  @Input() formRicercaVardatori: FormRicercaComunicazione;
  @Input() formPristine: FormRicercaComunicazione;

  @Output() readonly datiRicerca = new EventEmitter<any>();
  @Output() readonly resetForm = new EventEmitter();

  formRicerca: FormGroup;
  showForm = false;

  ruolo: Ruolo;
  dataNonValida = false;

  statiComunicazione: StatoComunicazione[] = [];
  listaProvince: Provincia[] = [];
  listaTipoVariazione: any[] = []; // TODO aggiornare il tipo
  listaTipoComunicazioneTU: TipoComunicazioneTu[] = [];

  listaStatiSelected: StatoComunicazione[] = [];

  // restituisce formControls
  get f() {
    return this.formRicerca.controls as any;
  }

  get conditionAnnoProtocolloEnable(): boolean {
    return this.f.provinciaProtocollo.value;
  }
  get conditionNumeroProtocolloEnable(): boolean {
    return this.f.annoProtocollo.value && this.f.annoProtocollo.value !== '';
  }

  constructor(
    private logService: LogService,
    private utilitiesService: UtilitiesService,
    private formBuilder: FormBuilder,
    private alertMessageService: AlertMessageService,
    private decodificaService: DecodificaService,
    private comonlStorageService: ComonlStorageService
  ) { }

  async ngOnInit() {
    await this.utilitiesService.showSpinner();

    this.logService.info(this.constructor.name, 'ngOnInit');
    const now = new Date();
    console.log(now);
    try {
      const [provinces, tipoComunicazioneTus, statiComunicaziones] = await Promise.all([
        this.decodificaService.getProvincia(REGIONE.PIEMONTE.COD_MIN).toPromise(),
        this.decodificaService.getTipoComunicazioneTu(true).toPromise(),
        this.decodificaService.getStatoComunicazione(true).toPromise()
      ]);
      this.listaProvince = provinces;
      this.listaTipoComunicazioneTU = tipoComunicazioneTus;
      this.statiComunicazione = statiComunicaziones;
      // TODO ottenimento ruolo
      this.comonlStorageService.ruolo$.subscribe(async el => {
        this.ruolo = el;
        this.defineForm();
        this.setFormState();
        this.formRicerca.patchValue(this.formRicercaVardatori); // al primo caricamento coincide con formPristine (vedi component padre)
        await this.patchParamsInForm(this.formRicercaVardatori);
        this.showForm = true;
      });
    } catch (e) {
      this.alertMessageService.setErrorMsg(e);
    } finally {
      await this.utilitiesService.hideSpinner();
    }
  }

  // Define form: definisce il modello strutturale del form
  private defineForm() {
    const now = new Date();
    this.formRicerca = this.formBuilder.group({
      codiceFiscaleAzienda: this.formBuilder.control(null, Validators.minLength(3)),
      denominazioneAzienda: this.formBuilder.control(
        null,
        Validators.minLength(2)
      ),
      codiceFiscaleLavoratore: this.formBuilder.control(null, Validators.minLength(3)),
      cognomeLavoratore: this.formBuilder.control(
        null,
        Validators.minLength(2)
      ),
      nomeLavoratore: this.formBuilder.control(null, Validators.minLength(2)),
      dataInvioInserimentoDa: this.formBuilder.control(
        new Date(now.setMonth(now.getMonth() - 1)),
        Validators.required
      ),
      dataInvioInserimentoA: this.formBuilder.control(
        new Date(),
        Validators.required
      ),
      statoComunicaziones: this.formBuilder.control(null),
      codiceRegionale: this.formBuilder.control(null, Validators.minLength(16)),
      tipoVariazione: this.formBuilder.control(null),
      tipoComunicazioneTu: this.formBuilder.control(null),
      provinciaProtocollo: this.formBuilder.control(null),
      annoProtocollo: this.formBuilder.control({ value: null, disabled: true }, Validators.required),
      numeroProtocollo: this.formBuilder.control({
        value: null,
        disabled: true,
      },Validators.required),
    });
  }

  private setFormState() {
    /**
     * per i ruoli consulente, Cpl, amministratore;
     * bloccare e settare i campi come in dicato nel CDU-03
     */
    // da chiamare sse il ruolo corrente è uno tra quelli indicati
    this.setControlStateDatiAzienda();
    this.setControlStateDatiProtoccollo();
  }

  private setControlStateDatiAzienda() {
    // TODO da implementare una volta ottenuti i ruoli
    if (
      !this.ruolo.amministratore &&
      !this.ruolo.operatoreProvinciale &&
      !this.ruolo.consulenteRespo) {
      this.f.codiceFiscaleAzienda.setValue(this.ruolo.codiceFiscaleAzienda);
      this.f.denominazioneAzienda.setValue(this.ruolo.denominazioneAzienda);
      this.f.codiceFiscaleAzienda.disable();
      this.f.denominazioneAzienda.disable();
    }
  }

  setControlStateDatiProtoccollo() {
    this.setControlStateAnnoProtoccollo();
    this.setControlStateNumeroProtocollo();
  }

  setControlStateAnnoProtoccollo() {
    const flgEnable = this.conditionAnnoProtocolloEnable;
    this.utilitiesService.changeControlState(
      this.f.annoProtocollo,
      flgEnable ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE,
      !flgEnable
    );
  }

  setControlStateNumeroProtocollo() {
    const flgEnable = this.conditionNumeroProtocolloEnable;
    this.utilitiesService.changeControlState(
      this.f.numeroProtocollo,
      flgEnable ? CONTROL_STATE.ENABLE : CONTROL_STATE.DISABLE,
      !flgEnable
    );
  }

  async patchParamsInForm(ricercaComunicazioneParams: any) {
    let goSearch = ricercaComunicazioneParams.goSearch;

    if (!goSearch) {
      return;
    }

    const pFormRicerca = ricercaComunicazioneParams.formRicercaComunicazione;

    if (pFormRicerca.codiceFiscaleAzienda || pFormRicerca.codiceFiscaleLavoratore || pFormRicerca.cognomeLavoratore || pFormRicerca.nomeLavoratore || pFormRicerca.dataInvioInserimentoDa ||
      pFormRicerca.dataInvioInserimentoA || pFormRicerca.codiceRegionale || pFormRicerca.annoProtocollo || pFormRicerca.numProtComUrgenza || pFormRicerca.numeroProtocollo) {
      goSearch = true;
    }

    this.formRicerca.patchValue(pFormRicerca);
    if (pFormRicerca.dataInvioInserimentoDa) {
      this.formRicerca.controls.dataInvioInserimentoDa.setValue(new Date(pFormRicerca.dataInvioInserimentoDa));
    }
    if (pFormRicerca.dataInvioInserimentoA) {
      this.formRicerca.controls.dataInvioInserimentoA.setValue(new Date(pFormRicerca.dataInvioInserimentoA));
    }


    // COMBOS & objects
    if (pFormRicerca.tipoComunicazioneTu) {
      const tipoComunicazioneTu: TipoComunicazioneTu = pFormRicerca.tipoComunicazioneTu
      const tipoComunicazioneTuFinded = this.listaTipoComunicazioneTU.find( it => it.id == tipoComunicazioneTu.id);
      if (tipoComunicazioneTuFinded) {
        goSearch = true;
        this.formRicerca.controls.tipoComunicazioneTu.patchValue(tipoComunicazioneTu);
      }
    }

    if (pFormRicerca.provinciaProtocollo) {
      const provinciaProtocollo: Provincia = pFormRicerca.provinciaProtocollo;
      const provinciaProtocolloFinded = this.listaProvince.find( it => it.id == provinciaProtocollo.id);
      if (provinciaProtocolloFinded) {
        goSearch = true;
        this.formRicerca.controls.provinciaProtocollo.patchValue(provinciaProtocolloFinded);
      }
    }

    if (pFormRicerca.statoComunicaziones && pFormRicerca.statoComunicaziones.length > 0) {
      const statiComunicazione: StatoComunicazione[] =  pFormRicerca.statoComunicaziones;
      statiComunicazione.forEach(stato => {
        this.listaStatiSelected.push(this.statiComunicazione.find(el => el.id === stato.id));
      });
    }

    this.setFormState();
    if (goSearch) {
      this.onSubmit();
    }

  }

  onClickReset() {
    this.alertMessageService.emptyMessages();
    this.f.tipoVariazione.reset();
    this.formRicerca.patchValue(this.formPristine);
    this.listaStatiSelected = [];
    this.setControlStateDatiProtoccollo();
    this.resetForm.emit();
  }

  onSubmit() {
    this.alertMessageService.emptyMessages();
    let ricercaVardatoriToSend = this.formRicerca.getRawValue() as FormRicercaComunicazione;

    const date1: Date = this.f.dataInvioInserimentoDa.value;
    const date2: Date = this.f.dataInvioInserimentoA.value;
    if (this.ruolo.consulenteRespo) {
      ricercaVardatoriToSend.codiceFiscaleSoggetto = this.ruolo.codiceFiscaleAzienda;
    }
    try {
      // this.checkValidDate(date1, date2);
      ricercaVardatoriToSend.statoComunicaziones = this.listaStatiSelected;
      this.datiRicerca.emit(ricercaVardatoriToSend);
    } catch (e) {
      this.alertMessageService.setSingleErrorMsg(e.message);
    }
  }

  private checkValidDate(date1: Date, date2: Date) {
    if (date2 < date1) {
      throw new Error('La data invio A non puo essere minore di data invio Da');
    }
  }

  checkSelectedStates(statoId: number) {
    if ( this.listaStatiSelected &&  this.listaStatiSelected.length > 0) {
      return  this.listaStatiSelected.find(item => item.id === statoId);
    }
  }

  selectRemoveStato(statoC: StatoComunicazione) {
    if (this.listaStatiSelected) {
      if (this.listaStatiSelected.length > 0) {
        const statoTmp = this.listaStatiSelected.find(item => item.id === statoC.id);
        const indexItemToRemove: number = this.listaStatiSelected.indexOf(statoTmp);
        if (indexItemToRemove >= 0) {
          this.listaStatiSelected.splice(indexItemToRemove, 1);
        } else {
          this.listaStatiSelected.push(statoC);
        }
      } else {
        this.listaStatiSelected.push(statoC);
      }
    }
  }

  isInvalidDate(date1: Date, date2: Date): boolean {
    const dataDa = new Date(date1);
    const dataA = new Date(date2);
    dataDa.setHours(0, 0, 0, 0);
    if ((dataDa > dataA) || (dataA < dataDa)) {
      this.dataNonValida = true;
      return true;
    }
    this.dataNonValida = false;
  }

}
