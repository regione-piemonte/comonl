/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Data } from '@angular/router';
import { UtilitiesService } from 'src/app/services';
import { ApiError } from 'src/app/modules/comonlapi';


@Component({
  selector: 'comonl-error-page',
  templateUrl: './error-page.component.html',
  styleUrls: ['./error-page.component.scss']
})
export class ErrorPageComponent implements OnInit, OnDestroy {

  errors: ApiError[] = [];
  private subscriptions: Subscription[] = [];

  constructor(
    private route: ActivatedRoute,
    private utilitiesService: UtilitiesService
  ) {}

  ngOnInit() {
    let tmp = [];
    this.subscriptions.push(
      this.route.data.subscribe((data: Data) => tmp = data.message || [])
    );
    this.errors = tmp.map(el => ({ ...el, code: `MESSAGES.${el.code}`}));
    this.utilitiesService.hideSpinner();
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

}
