/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { HttpClient } from '@angular/common/http';
import { MultiTranslateHttpLoader } from 'ngx-translate-multi-http-loader';

export function TranslationHttpLoaderFactory(http: HttpClient) {
  // http-loader, PR #61
  // return new TranslateHttpLoader(http, './assets/i18n/');
  return new MultiTranslateHttpLoader(http, [
    { prefix: './assets/i18n/core/', suffix: '.json' },
    { prefix: './assets/i18n/static/', suffix: '.json' },
  ]);
}
