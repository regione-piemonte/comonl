/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'comonl-error-modal',
  templateUrl: './error-modal.component.html',
  styleUrls: ['./error-modal.component.scss']
})
export class ErrorModalComponent {

  @Input() title: string;
  @Input() message: string;
  
  constructor() { }

}
