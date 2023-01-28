/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Pipe, PipeTransform, SecurityContext } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Pipe({
  name: 'comonlUnsafeHtml',
  pure: true
})
export class UnsafeHtmlPipe implements PipeTransform {

  constructor(
    private domSanitizer: DomSanitizer
  ) {}

  transform(value: any, ...args: any[]): string {
    return this.domSanitizer.sanitize(SecurityContext.HTML, value);
  }

}
