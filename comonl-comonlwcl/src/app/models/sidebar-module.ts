/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { marker } from '@biesbjerg/ngx-translate-extract-marker';

export interface ComonlSidebarModule {
  code: string;
  routerUrl: string[];
  urlSubpaths: string[];
  i18n: string;
  isHome?: boolean;
  ignore?: boolean;
}

export const POSSIBLE_SIDEBAR_MODULES: ComonlSidebarModule[] = [
  { code: '', routerUrl: ['/home'], urlSubpaths: ['/home'], i18n: marker('SIDEBAR.HOME'), isHome: true },
  { code: 'INT', routerUrl: ['/int'], urlSubpaths: ['/int'], i18n: marker('SIDEBAR.INT.TITLE') }
];
