/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'siNo',
  pure: false
})
export class SiNoPipe implements PipeTransform {
  constructor() {}

  transform(value: string): string {
    if (value === null || value === undefined) {
      return null;
    }
    if (typeof value === 'boolean') {
      return value ? 'Si' : 'No';
    }
    switch (value.toUpperCase()) {
      case 'Y':
      case 'YES':
      case 'S':
      case 'SI':
        return 'Si';
      case 'N':
      case 'NO':
        return 'No';
      default:
        return value;
    }
  }

}
