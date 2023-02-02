/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'decodAnagrafica'
})
export class DecodAnagraficaPipe implements PipeTransform {

  transform(value: string): any {
    switch (value.toUpperCase()) {
      case 'C':
        return 'Consulente';
      case 'E':
        return 'Persona autorizzata';
      case 'D':
        return 'Delegato';
      default:
        return value;
    }
  }

}
