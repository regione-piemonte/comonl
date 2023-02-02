/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { TemplateRef, Directive } from '@angular/core';

@Directive({selector: 'ng-template[comonlPaginationHead]'})
export class PaginationHeadDirective {
  constructor(
    public templateRef: TemplateRef<{}>
  ) {}
}
