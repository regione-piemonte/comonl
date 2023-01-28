/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
export const environment = {
  production: false,
  ambiente: 'local',
  shibbolethAuthentication: true,
  publicPath: 'http://localhost:4200/',

  appBaseHref: '/comonlweb',

  beServerPrefix: '',
  beService_2_WAR: '/rest/api/v1',
  beService: '/comonlweb/api/v1',

  shibbolethSSOLogoutURL: 'http://localhost:8080/comonlweb/logout',
  onAppExitURL: '',
  userManualURL: 'http://dev-comonl...../UserManual/'
};
