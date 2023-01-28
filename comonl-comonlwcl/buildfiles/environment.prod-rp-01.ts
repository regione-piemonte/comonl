/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
export const environment = {
  production: true,
  ambiente: 'prod-rp-01',
  shibbolethAuthentication: true,
  publicPath: 'http://.....',

  appBaseHref: '/comonlweb',

  beServerPrefix: '',
  beService_2_WAR: '/rest/api/v1',
  beService: '/comonlweb/api/v1',

  shibbolethSSOLogoutURL: 'https://...../comonlweb/logout',
  onAppExitURL: '',
  userManualURL: 'http://....../UserManual/'
};
