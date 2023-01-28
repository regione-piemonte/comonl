/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { LogService } from './log.service';

@Injectable({
  providedIn: 'root'
})
export class TitoloPaginaService {

  private readonly titoloSubject: Subject<string> = new Subject<string>();

  constructor(
    private logService: LogService
  ) {
    this.logService.info(this.constructor.name, 'constructor', 'TitoloPaginaService');
  }

  get titolo$(): Observable<string> { 
    return this.titoloSubject.asObservable(); 
  }

  triggerTitolo(titolo: string) {
    this.titoloSubject.next(titolo);
  }

}
