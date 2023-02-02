/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Injectable } from '@angular/core';
import { NgbDateParserFormatter, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';

@Injectable()
export class NgbCustomDateParserFormatterService extends NgbDateParserFormatter {

  parse(value: string): NgbDateStruct {
    if (!value) {
      return null;
    }
    const dateParts = value.trim().split('/');
    if (dateParts.length < 3 || !isNumber(dateParts[0]) || !isNumber(dateParts[1]) || !isNumber(dateParts[2]) || +dateParts[2] < 1000) {
      return null;
    }
    return {day: +dateParts[0], month: +dateParts[1], year: +dateParts[2]};
  }
  format(date: NgbDateStruct): string {
    return date
        // tslint:disable-next-line: max-line-length
        ? `${isNumber(date.day) ? padStart(date.day, 2) : ''}/${isNumber(date.month) ? padStart(date.month, 2) : ''}/${padStart(date.year, 4)}`
        : '';
  }

}

function isNumber(val: any): boolean {
  return !isNaN(+val);
}

function padStart(val: number, targetLength: number, padString: string = '0'): string {
  const padding = padString.repeat(targetLength / padString.length);
  return `${padding}${val}`.slice(-targetLength);
}
