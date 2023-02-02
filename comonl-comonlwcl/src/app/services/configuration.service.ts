/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ConfigurationService {

  private static readonly SHIBBOLETH_COOKIE_REGEX = /_shibsession_(.*?)=/;
  private static readonly SHIBBOLETH_SUBSTITUTION_REGEX = /%%SHIB%%/;

  constructor() { }

  /**
   * Prefisso per i servizi di back end
   */
  static getBERootUrl(): string {
    return environment.beServerPrefix + environment.beService;
  }

  /**
   * Url di logout da SSO
   */
  static getSSOLogoutURL(): string {
    const shibCookie = ConfigurationService.SHIBBOLETH_COOKIE_REGEX.exec(document.cookie);
    return environment.shibbolethSSOLogoutURL.replace(ConfigurationService.SHIBBOLETH_SUBSTITUTION_REGEX, shibCookie && shibCookie[1] || '');
  }

  /**
   * URL a cui saltare in caso di logout locala
   */
  static getOnAppExitURL(): string {
    return environment.onAppExitURL;
  }

  /**
   * base href per l'index.html
   */
  static getBaseHref(): string {
    return environment.appBaseHref;
  }

  /**
   * Whether to use the authentication page
   */
  static useAutenticationPage(): boolean {
    return environment.ambiente === 'local' || !environment.shibbolethAuthentication;
  }

  /**
   * Prefisso per i servizi di back end
   */
  static getUserManualUrl(): string {
    return environment.userManualURL;
  }
}
