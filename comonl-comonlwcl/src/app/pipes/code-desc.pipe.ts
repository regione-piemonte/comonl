/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Pipe, PipeTransform } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Pipe({
  name: 'comonlCodeDesc',
  pure: true
})
export class CodeDescPipe implements PipeTransform {

  constructor(
    private translateService: TranslateService
  ) {}

  transform(value: any, defaultText: string = ''): string {
    return value ? `${value.codice} - ${value.descrizione}` : this.translateService.instant(defaultText);
  }

}
