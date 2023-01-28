/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Directive, HostListener } from '@angular/core';

@Directive({
  selector: '[comonlFormatAmount]'
})
export class FormatAmountDirective {

  constructor() { }

  @HostListener ('blur') lostFocus() {
    // TODO?
  }
}
