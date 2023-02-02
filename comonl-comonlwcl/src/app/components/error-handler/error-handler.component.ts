/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { JsonPipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { NgModel, ValidationErrors } from '@angular/forms';
import { Utils } from 'src/app/utils';

@Component({
  selector: 'comonl-error-handler',
  templateUrl: './error-handler.component.html',
  providers: [ JsonPipe ]
})
export class ErrorHandlerComponent implements OnInit {

  @Input() model: NgModel;
  @Input() errors: ValidationErrors;

  constructor(
    private readonly jsonPipe: JsonPipe
  ) { }

  ngOnInit() {
  }

  extractErrorMessage(error: {key: string, value: any}): any {
    if (error.value && Utils.isString(error.value)) {
      return error.value;
    }
    const tmp = this.model.errors[error.key];
    if (!error.value) {
      return tmp;
    }
    if (error.value.path) {
      return Utils.getDeepValue(tmp, error.value.path);
    }
    return this.jsonPipe.transform(tmp);
  }

}
