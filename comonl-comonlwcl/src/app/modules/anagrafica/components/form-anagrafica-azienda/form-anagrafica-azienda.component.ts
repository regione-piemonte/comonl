/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { UtilitiesService } from 'src/app/services';
import { AnagraficaAziAccent, AnagraficaDelegatoService, CaricaPersonaPv, DatiAzienda, DecodificaService, Delega, DelegatoImpresa, DelegatoImpresaService, DelegatoImpresaSpec, SilpService } from 'src/app/modules/comonlapi';
import { Router } from '@angular/router';
import { AlertMessageService } from 'src/app/modules/comonlcommon/services/alert-message.service';
import { TranslateService } from '@ngx-translate/core';
import { marker } from '@biesbjerg/ngx-translate-extract-marker';
import { PromptModalService } from 'src/app/modules/comonlcommon/services';
import { ApiError } from 'src/app/modules/comonlapi';
import { ANAGRAFICA_DELEGATO, CONTROL_STATE } from 'src/app/constants';


@Component({
  selector: 'comonl-form-anagrafica-azienda',
  templateUrl: './form-anagrafica-azienda.component.html',
  styleUrls: ['./form-anagrafica-azienda.component.scss']
})
export class FormAnagraficaAziendaComponent implements OnInit {


  form: FormGroup;
  formDelegatoPe: FormGroup;
  cfImpresa: string;
  mode: string;

  datiAzienda: DatiAzienda;
  listDelegatoImpresaSpec: DelegatoImpresaSpec[] = [];
  caricaPersonaPvs: CaricaPersonaPv[] = [];
  anagraficaAziAccent: AnagraficaAziAccent;
  listDangerMsg: ApiError[] = [];
  get faccentrata() {return this.form.controls.accentrata as AbstractControl; }

  constructor(
    private location: Location,
    private utilitiesService: UtilitiesService,
    private silpService: SilpService,
    private delegatoImpresaService: DelegatoImpresaService,
    private anagraficaDelegatoService: AnagraficaDelegatoService,
    private router: Router,
    private alertMessageService: AlertMessageService,
    private translateService: TranslateService,
    private promptModalService: PromptModalService,
    private decodificaService: DecodificaService
  ) {
    const state = this.router.getCurrentNavigation().extras.state;
    this.cfImpresa = state.cfImpresa;
    this.mode = state.mode;
  }

  async ngOnInit() {
    this.utilitiesService.showSpinner();
    try {
      this.datiAzienda = await this.silpService.getAziendaSilp(this.cfImpresa).toPromise();
      const [datiAzienda, listDelegatoImpresaSpec, anagraficaAziAccent, carica] = await Promise.all([
        this.silpService.getAziendaSilp(this.cfImpresa).toPromise(),
        this.delegatoImpresaService.getDelegatoImpresaSpec(this.cfImpresa).toPromise(),
        this.anagraficaDelegatoService.getAziendaAccentrata(this.cfImpresa).toPromise(),
        this.decodificaService.getCaricaPersonaPv().toPromise()
      ]);
      this.datiAzienda = datiAzienda;
      this.listDelegatoImpresaSpec = listDelegatoImpresaSpec;
      this.anagraficaAziAccent = anagraficaAziAccent;
      this.caricaPersonaPvs = carica;
      this.initForm();
      this.form.patchValue(this.datiAzienda);
      this.setFlgAccentramento();
    } catch (e) {
      this.alertMessageService.setErrorMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  initForm() {
    this.form = new FormGroup({
      id: new FormControl(),
      cfAzienda: new FormControl({value: null, disabled: true}),
      partitaIva: new FormControl({value: null, disabled: true}),
      denominazioneDatoreLavoro: new FormControl({value: null, disabled: true}),
      naturaGiuridica: new FormGroup({
        id: new FormControl(),
        descrizione: new FormControl({value: null, disabled: true})
      }),
      flagArtigiana: new FormControl({value: null, disabled: true}),
      accentrata: new FormControl({value: null, disabled: this.mode === 'view' ? true : false}),
      atecofin: new FormGroup({
        id: new FormControl(),
        codAtecofinMin: new FormControl({value: null, disabled: true}),
        dsComTAtecofin: new FormControl({value: null, disabled: true})
      }),
      sedeLegale: new FormGroup({
        id: new FormControl(),
        comune: new FormGroup({
          id: new FormControl(),
          dsComTComune: new FormControl({value: null, disabled: true}),
          provincia: new FormGroup({
            id: new FormControl(),
            dsTarga: new FormControl({value: null, disabled: true})
          })
        }),
        cap: new FormControl({value: null, disabled: true}),
        indirizzo: new FormControl({value: null, disabled: true}),
        telefono: new FormControl({value: null, disabled: true}),
        email: new FormControl({value: null, disabled: true}),
        fax: new FormControl({value: null, disabled: true})
      })
    });
    if (this.mode === 'edit') {
      this.formDelegatoPe = new FormGroup({
        id: new FormGroup({
          cfDelegato: new FormControl(null, [Validators.minLength(11),Validators.required]),
          tipoAnagrafica: new FormControl(null, Validators.required),
          cfImpresa: new FormControl(this.cfImpresa),
        }),
        idCaricaPersonaPv: new FormControl({value: null, disabled: true})
      });
    }
  }

  private setFlgAccentramento() {
    if (this.anagraficaAziAccent) {
      if (!this.anagraficaAziAccent.dtFine) {
        this.faccentrata.setValue(true);
      } else {
        this.faccentrata.setValue(false);
      }
    } else {
      this.faccentrata.setValue(false);
    }
  }

  async onClickAddDelegatoPa() {
    this.utilitiesService.showSpinner();
    this.alertMessageService.emptyMessages();
    const delegatoImpresa: DelegatoImpresa = this.formDelegatoPe.getRawValue() as DelegatoImpresa;
    try {
      const res = await this.delegatoImpresaService.postDelegatoImpresa(delegatoImpresa).toPromise();
      if (res) {
        console.log('salvataggio avvenuto con successo');
        this.listDelegatoImpresaSpec = await this.delegatoImpresaService.getDelegatoImpresaSpec(this.cfImpresa).toPromise();
        this.alertMessageService.setSuccessSingleMsg('Soggetto inserito correttamente');
      }
    } catch (e) {
      this.alertMessageService.setErrorMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  isDelegaValida(delegas: Delega[]): boolean {
    let res: boolean = false;
     delegas.find((item: Delega) => {
      res = item.statoDelega.dsStatoDelega === 'VALIDATA';
    });
    return res;
  }

  getCarica(delegatoImpresa: DelegatoImpresaSpec): string {
    if(delegatoImpresa.id.tipoAnagrafica === ANAGRAFICA_DELEGATO.TIPO.PERSONA_FISICA){
      return delegatoImpresa.idCaricaPersonaPv;
    }else{
      const delegas = delegatoImpresa.delegas;
      const delegaValida: Delega = delegas.find((item: Delega) => {
        return item.statoDelega.dsStatoDelega === 'VALIDATA';
      });
      return delegaValida ? delegaValida.carica : '';
    }
  }

  async onClickAnnullaRipristina(delegatoImpresa: DelegatoImpresa, typeMessage: string) {
    const title = this.translate(marker('SIDEBAR.ACCREDITAMENTO_ANAGRAFICHE'));
    const message = this.translate(marker(typeMessage));
    const pYes = this.translate(marker('APP.YES'));
    const pNo = this.translate(marker('APP.NO'));

    const userChoice = await this.promptModalService.openPrompt(title, message, pYes, pNo, 'warning');

    if (userChoice) {
      this.setDataAnnullamento(delegatoImpresa);
    }

  }

  private translate(key: string) {
    return this.translateService.instant(key);
  }

  async setDataAnnullamento(delegatoImpresa: DelegatoImpresaSpec) {
    this.utilitiesService.showSpinner();
    const cfDelegato: string = delegatoImpresa.id.cfDelegato;
    const tipoAnagrafica: string = delegatoImpresa.id.tipoAnagrafica;
    const cfImpresa = delegatoImpresa.id.cfImpresa;
    try {
      const res: Date = await this.anagraficaDelegatoService.setDataAnnullamentoCDE(cfDelegato, tipoAnagrafica).toPromise();
      console.log(res);
      delegatoImpresa.anagraficaDelegato.dataAnnullamento = res;

    } catch (e) {
      this.alertMessageService.setErrorMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  onClickReset() {
    this.setFlgAccentramento();
    this.formDelegatoPe.reset();
    this.onChangeTipoCarica();
  }

 async onClickSubmit() {
   this.utilitiesService.showSpinner();
   this.alertMessageService.emptyMessages();
   let anagraficaAziAccentToSend: AnagraficaAziAccent;
   if (this.anagraficaAziAccent) {
      anagraficaAziAccentToSend = this.anagraficaAziAccent;
    } else {
      anagraficaAziAccentToSend = new Object() as AnagraficaAziAccent;
      anagraficaAziAccentToSend.codiceFiscale = this.datiAzienda.cfAzienda;
      anagraficaAziAccentToSend.dsDenominazione = this.datiAzienda.denominazioneDatoreLavoro;
      anagraficaAziAccentToSend.dtInizio = new Date();
    }
   const accentrata = this.faccentrata.value;
   if (accentrata) {
      anagraficaAziAccentToSend.dtFine = null;
    } else {
      if (anagraficaAziAccentToSend.id) {
        anagraficaAziAccentToSend.dtFine = new Date();
      } else {
        // FIne
        this.alertMessageService.setSuccessSingleMsg('Modifica avvenuta con successo');
        this.utilitiesService.hideSpinner();
        return;
      }
    }
   try {
      let res: AnagraficaAziAccent;
      if (!anagraficaAziAccentToSend.id) {
        // POST
         res = await this.anagraficaDelegatoService.postAziendaAccentrata(anagraficaAziAccentToSend).toPromise();
      } else {
        // PUT
        res = await this.anagraficaDelegatoService.putAziendaAccentrata(anagraficaAziAccentToSend).toPromise();
      }
      if (res) {
        this.anagraficaAziAccent = res;
        this.alertMessageService.setSuccessSingleMsg('Modifica avvenuta con successo');
      }
    } catch (e) {
      this.alertMessageService.setErrorMsg(e.error);
    } finally {
      this.utilitiesService.hideSpinner();
    }
  }

  onClickGoBackToRicerca() {
    this.location.back();
  }

  onChangeTipoCarica(){
    const tipoAnagrafica = this.formDelegatoPe.controls.id.get('tipoAnagrafica').value;
    this.formDelegatoPe.controls.idCaricaPersonaPv.clearValidators();
    this.utilitiesService.changeControlState(
      this.formDelegatoPe.controls.idCaricaPersonaPv,
      CONTROL_STATE.DISABLE,
      true
      );
      if(tipoAnagrafica === 'E'){
      this.formDelegatoPe.controls.idCaricaPersonaPv.setValidators(Validators.required);
      this.utilitiesService.changeControlState(
        this.formDelegatoPe.controls.idCaricaPersonaPv,
        CONTROL_STATE.ENABLE,
        true
      );
    }
    this.formDelegatoPe.controls.idCaricaPersonaPv.updateValueAndValidity();
  }
  
}
