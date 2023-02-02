/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  ambiente: 'local',
  shibbolethAuthentication: true,
  publicPath: 'http://localhost:8080',

  appBaseHref: '',

  beServerPrefix: 'http://localhost:8080',
  beService_2_WAR: '/rest/api/v1',
  beService: '/comonlweb/api/v1',

  // shibbolethSSOLogoutURL: '',
  shibbolethSSOLogoutURL: 'http://localhost:8080/comonlweb/logout',
  // shibbolethSSOLogoutURL: 'about:blank',

  onAppExitURL: '',

  userManualURL: 'http://dev-comonl....it/UserManual/'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
