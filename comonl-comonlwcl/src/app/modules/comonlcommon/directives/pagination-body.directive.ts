/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Directive, TemplateRef } from '@angular/core';

@Directive({selector: 'ng-template[comonlPaginationBody]'})
export class PaginationBodyDirective<T> {
  constructor(
    public templateRef: TemplateRef<{$implicit: T}>
  ) {}
}
