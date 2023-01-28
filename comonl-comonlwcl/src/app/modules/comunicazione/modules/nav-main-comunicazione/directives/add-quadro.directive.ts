/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[comonlAddQuadro]'
})
export class AddQuadroDirective {

  constructor(public viewContainerRef: ViewContainerRef) { }

}
