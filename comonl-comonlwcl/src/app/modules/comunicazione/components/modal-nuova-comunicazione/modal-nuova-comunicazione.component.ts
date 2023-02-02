/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Utils } from 'src/app/utils';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl } from '@angular/forms';
import { LogService, UtilitiesService } from 'src/app/services';
import { CONSTANTS_MODE, CONTROL_STATE, TIPO_TRACCIATO } from 'src/app/constants';
import { DecodificaGenerica, DecodificaService, Comunicazione, RicercaComunicazione } from 'src/app/modules/comonlapi';
import { NavComunicazioneParams } from '../../modules/nav-main-comunicazione/models/nav-comunicazione-params';

@Component({
  selector: 'comonl-modal-nuova-comunicazione',
  templateUrl: './modal-nuova-comunicazione.component.html',
  styleUrls: ['./modal-nuova-comunicazione.component.scss']
})
export class ModalNuovaComunicazioneComponent implements OnInit {

  @Input() title: string;
  @Input() comunicazione: RicercaComunicazione;
  @Input() tipo: string;
  @Input() idComunicazione: number;

  formNuovaComunicazione: FormGroup;
  showForm = false;

  flgNuovaCO: boolean;

  get f() { return this.formNuovaComunicazione.controls as any; }
  get conditionConfermaBtnDisable(): boolean { return  this.f.urg.value !== 'S' && !this.f.comunicazione.value; }

  tipiComunicazioneUnisomm: DecodificaGenerica[] = [];
  tipiComunicazioneUniLav: DecodificaGenerica[] = [];
  currentListSelected: DecodificaGenerica[] = [];

  tipiComunicazioneUnisommCo: DecodificaGenerica[] = [];
  tipiComunicazioneUniLavCo: DecodificaGenerica[] = [];
  currentListSelectedCo: DecodificaGenerica[] = [];

  constructor(public activeModal: NgbActiveModal,
              private router: Router,
              private logService: LogService,
              private decodificaService: DecodificaService,
              private utilitiesService: UtilitiesService
              ) { }

  async ngOnInit() {
    this.utilitiesService.showSpinner();
    this.logService.info(this.constructor.name, 'ngOnInit');
    const filtro: DecodificaGenerica = {
      idDecodifica: 1
    };
    try {
      const [tipiComunicazioneUnisomm, tipiComunicazioneUniLav] = await Promise.all([
        this.decodificaService.postTipoComunicazioneUnisommDecodifica(filtro).toPromise(),
        this.decodificaService.postTipoComunicazioneUnilavDecodifica({} as DecodificaGenerica).toPromise()
      ]);
      this.tipiComunicazioneUnisomm = tipiComunicazioneUnisomm;
      this.tipiComunicazioneUniLav = tipiComunicazioneUniLav;
      this.currentListSelected = this.tipiComunicazioneUniLav;
      this.initForm();
      setTimeout(() => this.showForm = true, 500);
      // SE CLICCO NUOVA COMUNICAZIONE DAL PULSANTE DAI RISULTATI RICERCA COMUNICAZIONI
      if (this.tipo) {
        this.tipiComunicazioneUnisommCo = Utils.clone(tipiComunicazioneUnisomm);
        this.tipiComunicazioneUniLavCo = Utils.clone(tipiComunicazioneUniLav);
        this.currentListSelectedCo = Utils.clone(this.currentListSelected);
        console.log('tipo: ' + this.tipo);
        console.log('comunicazione: ' + JSON.stringify(this.comunicazione));
        console.log('idComunicazione: ' + JSON.stringify(this.idComunicazione));

        if (this.comunicazione.idTipoTracciato === 'US') {
          this.f.somm.setValue('S');
          this.f.somm.disable();
          this.currentListSelectedCo = this.tipiComunicazioneUnisomm;
        }
        // RIMUOVO 'ASSUNZIONE' DA TUTTE LE COMBO QUANDO PREMO IL '+' DAI RISULTATI DELLA RICERCA COMUNICAZIONE
        if (this.comunicazione.idComTTipoComunicazione !== 'URG') {
          if (this.comunicazione.idTipoTracciato !== 'US') {
            this.currentListSelectedCo = this.currentListSelectedCo.filter( el => {
              return el.idComTipoComunicazione !== 'ASS';
            });
          } else {
            this.currentListSelectedCo = this.tipiComunicazioneUnisommCo.filter(el => {
              return el.dsDecodifica !== 'SOMMINISTRAZIONE';
            });
          }
        } else {
        // SOLO ASSUNZIONE PER URGENZA
          this.currentListSelectedCo = this.currentListSelectedCo.filter( el => {
            return el.idComTipoComunicazione === 'ASS';
          });
        }
      }
    } catch (e) {

    } finally {
      this.utilitiesService.hideSpinner();
    }

  }

  private initForm() {
    this.formNuovaComunicazione = new FormGroup({
      urg: new FormControl('N'),
      somm: new FormControl('N'),
      comunicazione: new FormControl(null)
    });
  }

  async goToNuovaComunicazione() {
    this.activeModal.close();
    const formParams = this.formNuovaComunicazione.getRawValue();
    let flgNuovaCO: boolean;
    let configurazioneQuadri: string;
    if (formParams.urg && formParams.urg === 'S' ) {
      configurazioneQuadri = 'URG';
    } else {
      if (formParams.comunicazione.idComTipoTracciato === 'UL') {
        configurazioneQuadri = formParams.comunicazione.idComTipoComunicazione;
      } else {
        if (formParams.comunicazione.dsDecodifica === 'SOMMINISTRAZIONE E MISSIONE') {
          configurazioneQuadri = formParams.comunicazione.idDecodifica + 'SOMEMISS';
        } else if (formParams.comunicazione.dsDecodifica === 'PROROGA DEL RAPPORTO DI LAVORO E DELLA MISSIONE') {
          configurazioneQuadri = formParams.comunicazione.idDecodifica + 'PROEMISS';
        } else {
          configurazioneQuadri = '' + formParams.comunicazione.idDecodifica;
        }
      }
    }

    // if (!this.tipo) {
    //   flgNuovaCO = false;
    // } else {
    //   flgNuovaCO = true;
    // }
    // const parametriNavigazione: NavComunicazioneParams = {
    //   formModalParams: formParams,
    //   flgArrivoComeNuova: true,
    //   mode: CONSTANTS_MODE.INS,
    //   configurazioneQuadri: configurazioneQuadri,
    //   flgNuovaCO: flgNuovaCO
    // };
    // this.router.navigate(['/nav-main-comunicazione'], { state: { parametriNavigazione: parametriNavigazione }});

    if (!this.tipo) {
      const parametriNavigazione: NavComunicazioneParams = {
        formModalParams: formParams,
        flgArrivoComeNuova: true,
        mode: CONSTANTS_MODE.INS,
        configurazioneQuadri: configurazioneQuadri,
        flgNuovaCO: false
      };
      this.router.navigate(['/nav-main-comunicazione'], { state: { parametriNavigazione: parametriNavigazione }});
    } else {
      let cfLavoratorePerVardatore: string;
      if(this.comunicazione.idTipoTracciato === TIPO_TRACCIATO.VARDATORI.ID){
        cfLavoratorePerVardatore = this.comunicazione.codiceFiscaleLavoratore;
      }
      const parametriNavigazione: NavComunicazioneParams = {
        formModalParams: formParams,
        flgArrivoComeNuova: true,
        mode: CONSTANTS_MODE.AGGIORNAMENTO,
        configurazioneQuadri: configurazioneQuadri,
        flgNuovaCO: true,
        idComunicazione: this.idComunicazione,
        cfLavoratorePerVardatore: cfLavoratorePerVardatore
      };
      this.router.navigate(['/nav-main-comunicazione'], { state: { parametriNavigazione: parametriNavigazione }});
    }
  }

  onClickUrgSi() {
    this.utilitiesService.changeControlState(
      this.f.somm,
      CONTROL_STATE.DISABLE,
      true );
    this.utilitiesService.changeControlState(
      this.f.comunicazione,
      CONTROL_STATE.DISABLE,
      true );
  }

  onClickUrgNo() {
    this.utilitiesService.changeControlState(
      this.f.somm,
      CONTROL_STATE.ENABLE,
      true );
    this.utilitiesService.changeControlState(
      this.f.comunicazione,
      CONTROL_STATE.ENABLE,
      true );
    this.f.somm.setValue('N');
  }

  onClickSommSi() {
    if (!this.tipo) {
      this.utilitiesService.changeControlState(
        this.f.urg,
        CONTROL_STATE.DISABLE,
        true );
      this.currentListSelected = this.tipiComunicazioneUnisomm;
    } else {
      this.f.urg.setValue('N');
      this.f.somm.setValue('S');
      this.currentListSelectedCo = this.tipiComunicazioneUnisomm;
      this.currentListSelectedCo = this.currentListSelectedCo.filter( el => {
        return el.idDecodifica !== 0;
      });

    }
  }

  onClickSommNo() {
    if (!this.tipo) {
      this.utilitiesService.changeControlState(
        this.f.urg,
        CONTROL_STATE.ENABLE,
        true );
      this.f.urg.setValue('N');
      this.f.comunicazione.reset();
      this.currentListSelected = this.tipiComunicazioneUniLav;
    } else {
      this.f.comunicazione.reset();
      this.currentListSelectedCo = this.tipiComunicazioneUniLav;
      this.currentListSelectedCo = this.currentListSelectedCo.filter( el => {
        return el.idComTipoComunicazione !== 'ASS';
      });
    }

  }

}
