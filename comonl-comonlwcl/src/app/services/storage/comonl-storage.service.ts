/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Injectable } from '@angular/core';
import { Params } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { RicercaComunicazioniParams } from 'src/app/models/ricerca-comunicazioni-params';
import { WrapperHelper } from 'src/app/models/wrappwer-helper';
import { Ruolo } from 'src/app/modules/comonlapi';


@Injectable({
  providedIn: 'root'
})
export class ComonlStorageService {

  private ruolo: BehaviorSubject<Ruolo> = new BehaviorSubject({} as Ruolo);
  get ruolo$(): Observable<Ruolo> { return this.ruolo.asObservable(); }

  private ricercaComunicazioniParams: BehaviorSubject<RicercaComunicazioniParams> = new BehaviorSubject(null);
  get ricercaComunicazioniParams$() {return this.ricercaComunicazioniParams.asObservable();}


  private wrapperHelper: BehaviorSubject<WrapperHelper> = new BehaviorSubject(null);
  get wrapperHelper$(): Observable<WrapperHelper> { return this.wrapperHelper.asObservable(); }


  constructor() { }

  private ruoloCurrent: Ruolo = null;

  public setRuolo(ilRuolo: Ruolo){
    this.ruoloCurrent = ilRuolo;
    this.ruolo.next(ilRuolo);
  }

  public isRuoloCurrentSelected() {
    if (this.ruoloCurrent) {
      return true;
    }
    return false;
  }

  public setWrapperHelper(wrapperHelper: WrapperHelper){
    this.wrapperHelper.observers = [];
    this.wrapperHelper.next(wrapperHelper);
    console.log(this.wrapperHelper);
  }

  public setRicercaComunicazioniParams(ricercaComunicazioniParams: RicercaComunicazioniParams){
    this.ricercaComunicazioniParams.observers = [];
    this.ricercaComunicazioniParams.next(ricercaComunicazioniParams);
  }


}

