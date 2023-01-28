/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { TemplateRef, Directive } from '@angular/core';

@Directive({selector: 'ng-template[comonlPaginationHead]'})
export class PaginationHeadDirective {
  constructor(
    public templateRef: TemplateRef<{}>
  ) {}
}
