/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Injectable } from '@angular/core';
import { AbstractControl, ValidationErrors } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root'
})
export class CodiceFiscaleValidator {
  // caratteri ammessi :
  private static tuttiCaratteri = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ';

  // valori dei caratteri di ordine pari :
  private static valoriPari = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4,
    5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25];

  // valori dei caratteri di ordine dispari :
  private static valoriDispari = [1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5,
    7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23];

  constructor(
    private translateService: TranslateService
  ) {}

  private static valore(cf: string, index: number, valori: number[]): number {
    return valori[CodiceFiscaleValidator.tuttiCaratteri.indexOf(cf.charAt(index))];
  }

  validate(control: AbstractControl): ValidationErrors {
    if (control.value == null || control.value === '') {
      return null;
    }
    const cf = control.value.toUpperCase();
    // Se il codice è una stringa vuota, il codice è corretto
    if (!cf || !cf.trim()) {
      return null;
    }
    // Se la lunghezza del codice è diversa da 16, il codice è errato
    if (cf.length !== 16) {
      return { codiceFiscale: this.translateService.instant('ERROR.FIELD.FISCAL_CODE.INVALID_LENGTH') };
    }
    // Controllo che l'anno sia espresso in numero
    if (!/[0-9L-V]{2}/.test(cf.slice(6, 8))) {
      return { codiceFiscale: this.translateService.instant('ERROR.FIELD.FISCAL_CODE.INVALID_CHAR') };
    }
    // Controllo che il giorno sia espresso in numero
    if (!/[0-9L-V]{2}/.test(cf.slice(9, 11))) {
      return { codiceFiscale: this.translateService.instant('ERROR.FIELD.FISCAL_CODE.INVALID_CHAR') };
    }
    // Controllo che il codice belfiore sia espresso in numero
    if (!/[0-9L-V]{3}/.test(cf.slice(12, 15))) {
      return { codiceFiscale: this.translateService.instant('ERROR.FIELD.FISCAL_CODE.INVALID_CHAR') };
    }
    let checkDigit = 0;
    // pari
    for (let index = 1; index < 15; index += 2 ) {
      checkDigit += CodiceFiscaleValidator.valore(cf, index, CodiceFiscaleValidator.valoriPari);
    }
    // dispari
    for (let index = 0; index < 15; index += 2 ) {
      checkDigit += CodiceFiscaleValidator.valore(cf, index, CodiceFiscaleValidator.valoriDispari);
    }
    checkDigit %= 26;
    if (cf.charAt(15) !== CodiceFiscaleValidator.tuttiCaratteri.charAt(checkDigit + 10) ) {
      return { codiceFiscale: this.translateService.instant('ERROR.FIELD.FISCAL_CODE.INVALID') };
    }
    return null;
  }
}
