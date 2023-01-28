/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Directive, HostListener, ElementRef, Input } from '@angular/core';

@Directive({
  selector: '[comonlDigitOnly]'
})
export class DigitOnlyDirective {

  private static readonly REGEX = {
    number: /^\d+$/,
    // decimal: /^[0-9]+(\.[0-9]{0,2}){0,1}$/
  };
  private static readonly SPECIAL_KEYS = {
    number: [ 'Backspace', 'Tab', 'End', 'Home', 'ArrowLeft', 'ArrowRight' ],
    decimal: [ 'Backspace', 'Tab', 'End', 'Home', 'ArrowLeft', 'ArrowRight', 'Del', 'Delete'],
  };

  @Input() numericType = 'number';

  constructor(
    private elementRef: ElementRef
  ) { }

  @HostListener('keydown', ['$event']) onKeyDown(event: KeyboardEvent) {
    if (!DigitOnlyDirective.SPECIAL_KEYS[this.numericType]) {
      return;
    }
    if (DigitOnlyDirective.SPECIAL_KEYS[this.numericType].indexOf(event.key) !== -1) {
      return;
    }
    // Do not use event.keycode this is deprecated.
    // See: https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/keyCode
    const current: string = this.elementRef.nativeElement.value;
    const next: string = current.concat(event.key);
    if (next && !String(next).match(DigitOnlyDirective.REGEX[this.numericType])) {
      event.preventDefault();
    }
  }

}
