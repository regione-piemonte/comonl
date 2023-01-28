/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'maschioFemmina',
  pure: false
})
export class MaschioFemminaPipe implements PipeTransform {
  constructor() {}

  transform(value: string): string {
    if (value === null || value === undefined) {
      return null;
    }
    switch (value.toUpperCase()) {
      case 'M':
      case 'Uomo':
      case 'S':
      case 'SI':
        return 'Maschio';
      case 'F':
      case 'Donna':
        return 'Femmina';
      default:
        return value;
    }
  }

}
