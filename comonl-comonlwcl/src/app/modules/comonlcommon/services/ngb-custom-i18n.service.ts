/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Injectable, OnDestroy } from '@angular/core';
import { NgbDatepickerI18n, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { TranslateService, LangChangeEvent } from '@ngx-translate/core';
import { Subscription } from 'rxjs';

@Injectable()
export class NgbCustomI18nService extends NgbDatepickerI18n implements OnDestroy {

  private subscriptions: Subscription[] = [];
  private weekdays: string[] = [];
  private monthsShort: string[] = [];
  private monthsLong: string[] = [];

  constructor(
    private ts: TranslateService
  ) {
    super();
    this.subscriptions.push(
      this.ts.onLangChange.subscribe(lce => this.handleLangChange(lce))
    );
    this.handleLangChange({lang: this.ts.currentLang, translations: this.ts.translations[this.ts.currentLang]});
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  getWeekdayShortName(weekday: number): string {
    return this.weekdays[weekday - 1];
  }
  getMonthShortName(month: number, year?: number): string {
    return this.monthsShort[month - 1];
  }
  getMonthFullName(month: number, year?: number): string {
    return this.monthsLong[month - 1];
  }
  getDayAriaLabel(date: NgbDateStruct): string {
    return `${date.day}-${date.month}-${date.year}`;
  }

  private handleLangChange(lce: LangChangeEvent) {
    this.weekdays = lce.translations.DATEPICKER.WEEKDAYS;
    this.monthsShort = lce.translations.DATEPICKER.MONTHS_SHORT;
    this.monthsLong = lce.translations.DATEPICKER.MONTHS_LONG;
  }
}
