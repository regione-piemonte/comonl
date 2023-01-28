/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CONTROL_STATE, REGIONE } from 'src/app/constants';
import { DecodificaService, FormRicercaComunicazione, Provincia, Ruolo, StatoComunicazione, TipoComunicazione } from 'src/app/modules/comonlapi';
import { TipoComunicazioneTu } from 'src/app/modules/comonlapi/model/tipoComunicazioneTu';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { LogService, UserService, UtilitiesService } from 'src/app/services';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';

@Component({
  selector: 'comonl-form-ricerca-comunicazioni',
  templateUrl: './form-ricerca-comunicazioni.component.html',
  styleUrls: ['./form-ricerca-comunicazioni.component.scss'],
})
export class FormRicercaComunicazioniComponent implements OnInit {

  @Input() formRicercaComunicazione: FormRicercaComunicazione;
  @Input() formPristine: FormRicercaComunicazione;

  @Output() readonly datiRicerca = new EventEmitter<any>();
  @Output() readonly resetForm = new EventEmitter();

  formRicerca: FormGroup;
  showForm = false;

  statiComunicazione: StatoComunicazione[] = [];
  listaProvince: Provincia[] = [];
  listaTipoComunicazione: TipoComunicazione[] = [];
  listaTipoComunicazioneTU: TipoComunicazioneTu[] = [];

  listaStatiSelected: StatoComunicazione[] = [];

  ruolo: Ruolo;
  dataNonValida = false;

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
    private userService: UserService,
    private alertMessageService: AlertMessageService,
    private decodificaService: DecodificaService,
    private comonlStorageService: ComonlStorageService
  ) {}

  async ngOnInit() {
    await this.utilitiesService.showSpinner();
    this.comonlStorageService.ruolo$.subscribe(el => this.ruolo = el);
    this.logService.info(this.constructor.name, 'ngOnInit');
    // chiamata ai servizi di decodifica per popolamento combo e checkbox
    try {
      const [provinces, tipoComunicaziones, tipoComunicazioneTus, statiComunicaziones] = await Promise.all([
        this.decodificaService.getProvincia(REGIONE.PIEMONTE.COD_MIN).toPromise(),
        this.decodificaService.getTipoComunicazione().toPromise(),
        this.decodificaService.getTipoComunicazioneTu(true).toPromise(),
        this.decodificaService.getStatoComunicazione(true).toPromise()
      ]);
      this.listaProvince = provinces;
      this.listaTipoComunicazione = tipoComunicaziones;
      this.listaTipoComunicazioneTU = tipoComunicazioneTus;
      this.statiComunicazione = statiComunicaziones;
      this.defineForm();
      this.setFormState();
      this.formRicerca.patchValue(this.formRicercaComunicazione); // al primo caricamento coincide con formPristine (vedi component padre)
      await this.patchParamsInForm(this.formRicercaComunicazione);
      this.showForm = true;
    } catch (e) {
      console.log(e);
      this.alertMessageService.setErrorMsg(e.error);
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
      tipoComunicazione: this.formBuilder.control(null),
      tipoComunicazioneTu: this.formBuilder.control(null),
      provinciaProtocollo: this.formBuilder.control(null),
      annoProtocollo: this.formBuilder.control({ value: null, disabled: true },Validators.required),
      numProtComUrgenza: this.formBuilder.control({ value: null, disabled: true }),
      numeroProtocollo: this.formBuilder.control({value: null,disabled: true,},Validators.required),
    });
  }

  private setFormState() {
    this.setControlStateDatiAzienda();
    this.setControlStateDatiProtoccollo();
  }

  private setControlStateDatiAzienda() {
    if (
        !this.ruolo.amministratore &&
        !this.ruolo.operatoreProvinciale &&
        !this.ruolo.consulenteRespo) {
      this.f.codiceFiscaleAzienda.setValue(this.ruolo.codiceFiscaleAzienda);
      this.f.denominazioneAzienda.setValue(this.ruolo.denominazioneAzienda);
      this.f.codiceFiscaleAzienda.disable();
      this.f.denominazioneAzienda.disable();   }
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

  // onKeyUp(event: any){
  //   if(event && event.value.length === 1){
  //     this.setControlStateNumeroProtocollo();
  //   }else if()
  // }

  onSubmit() {
    this.alertMessageService.emptyMessages();
    let ricercaComunicazioniToSend = this.formRicerca.getRawValue() as FormRicercaComunicazione;
    const date1: Date = this.f.dataInvioInserimentoDa.value;
    const date2: Date = this.f.dataInvioInserimentoA.value;

    if (this.ruolo.consulenteRespo) {
      ricercaComunicazioniToSend.codiceFiscaleSoggetto = this.ruolo.codiceFiscaleAzienda;
    }

    try {
      // this.checkValidDate(date1, date2);
      ricercaComunicazioniToSend.statoComunicaziones = this.listaStatiSelected;
      this.datiRicerca.emit(ricercaComunicazioniToSend);
    } catch (e) {
      this.alertMessageService.setSingleErrorMsg(e.message);
    }
  }

  onClickReset() {
    this.alertMessageService.emptyMessages();
    this.formRicerca.patchValue(this.formPristine);
    this.listaStatiSelected = [];
    this.setControlStateDatiProtoccollo();
    this.resetForm.emit();
  }

  triggerUiUpdate() {
    // scatena l'evento su cui è in ascolto la direttiva HasValueClass
    this.userService.triggerUiUpdate();
  }

  // openModal() {
  //   const modalRef = this.modalService.open(ModalNuovaComunicazioneComponent, { centered: true, size: 'lg'});
  //   modalRef.componentInstance.title = 'Seleziona Comunicazione';
  // }

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
    if (pFormRicerca.tipoComunicazione) {
      const tipoComunicazione: TipoComunicazione = pFormRicerca.tipoComunicazione;
      const tipoComunicazioneFinded = this.listaTipoComunicazione.find( it => it.id == tipoComunicazione.id);
      if (tipoComunicazioneFinded) {
        goSearch = true;
        this.formRicerca.controls.tipoComunicazione.patchValue(tipoComunicazioneFinded);
      }
    }

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

  checkValidDate(date1: Date, date2: Date) {
    if (date2 < date1) {
      throw new Error('La data invio A non puo essere minore di data invio Da');
    }
  }
}
