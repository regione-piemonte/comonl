/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LogService {

  constructor() { }

  debug(className: string, methodName: string, ...payload: any[]) {
    // tslint:disable-next-line: no-console
    console.log.call(console, this.formatMessage(className, methodName), ...payload);
  }

  info(className: string, methodName: string, ...payload: any[]) {
    // tslint:disable-next-line: no-console
    console.info.call(console, this.formatMessage(className, methodName), ...payload);
  }

  error(className: string, methodName: string, ...payload: any[]) {
    // tslint:disable-next-line: no-console
    console.error.call(console, this.formatMessage(className, methodName), ...payload);
  }

  private formatMessage(className: string, methodName: string): string {
    return `[${className}::${methodName}]`;
  }
}
