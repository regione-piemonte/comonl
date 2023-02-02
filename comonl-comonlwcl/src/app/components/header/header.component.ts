/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Component, OnInit, OnDestroy, Input, Inject, Optional, Output, EventEmitter, } from '@angular/core';
import { SidebarService, SessionStorageService, UserService, LogService, ConfigurationService } from 'src/app/services';
import { Subscription } from 'rxjs';
import { TranslateService } from '@ngx-translate/core';
import { filter } from 'rxjs/operators';
import { marker } from '@biesbjerg/ngx-translate-extract-marker';
import { Language, LANGUAGE_STORAGE, ScrollAmount, SidebarContent } from 'src/app/models';
import { DOCUMENT } from '@angular/common';
import { TitoloPaginaService } from 'src/app/services/titolo-pagina.service';
import { Router } from '@angular/router';
import { Ruolo, Utente, UtenteService } from 'src/app/modules/comonlapi';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { LOGOUT_URL, USER_MANUAL_URL } from 'src/app/modules/comonlcommon/variables';

@Component({
  selector: 'comonl-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit, OnDestroy {

  @Input() utente: Utente;
  @Output() readonly notifyStateSidebar = new EventEmitter<boolean>();

  titolo: string = 'APP.TITLE';
  sidebarContent: SidebarContent[] = [];
  stateSideBar: boolean = true;

  lang: Language;
  languages: Language[] = [
    { langCode: 'it', locale: 'it-IT', iconCode: 'it', text: marker('LANGUAGE.ITALIAN') },
    { langCode: 'en', locale: 'en-GB', iconCode: 'gb', text: marker('LANGUAGE.ENGLISH') }
  ];
  headerHidden = false;

  private subscriptions: Subscription[] = [];
  protected logoutUrl = '';
  public userManualUrl = '';
  ilRuoloUtente: Ruolo;

  constructor(
    private sidebarService: SidebarService,
    private router: Router,
    private storageService: SessionStorageService,
    private translateService: TranslateService,
    private userService: UserService,
    private logService: LogService,
    private configurationService: ConfigurationService,
    private comonlStorageService: ComonlStorageService,
    private titoloPaginaService: TitoloPaginaService,
    @Inject(DOCUMENT) private document: Document,
    @Optional() @Inject(USER_MANUAL_URL) private userManualBaseUrl: string,
    @Optional() @Inject(LOGOUT_URL) logoutUrl: string
  ) {
    this.logoutUrl = logoutUrl || '';
  }

  ngOnInit() {
    this.onChangedLanguage();
    this.subscriptions.push(
      this.storageService.keyStorage$.pipe(
        filter(key => key === LANGUAGE_STORAGE)
      ).subscribe(() => this.onChangedLanguage()),
      this.userService.scroll$.subscribe((scrollAmount) => this.onWindowScroll(scrollAmount)),
      this.sidebarService.content$.subscribe(content => this.sidebarContent = content),
      this.userService.userLinkManual$.subscribe(userLink => this.userManualUrl = this.userManualBaseUrl + userLink),
      this.titoloPaginaService.titolo$.subscribe(titolo => this.titolo = titolo),
    );
    this.comonlStorageService.ruolo$.subscribe(async item => {
      this.ilRuoloUtente = item;
    });
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  doOpenSidebar() {
    this.sidebarService.toggleCollapsed();
  }

  onLanguageSelected(language: Language) {
    this.storageService.setItem(LANGUAGE_STORAGE, language);
  }

  private onChangedLanguage() {
    this.lang = this.storageService.getItem(LANGUAGE_STORAGE);
    this.translateService.use(this.lang.langCode);
  }

  logout() {
    const methodName = 'logout';
    this.logService.debug(methodName, 'ClearStorage');
    this.storageService.clearStorage();
    this.logService.debug(methodName, `Redirect to ${this.logoutUrl}`);
    this.document.location.href = this.logoutUrl;
  }

  cambiaRuolo() {
    this.router.navigateByUrl('/scelta-ruolo');
  }

  private onWindowScroll(scrollAmount: ScrollAmount) {
    // Aggiunta scomparsa header a circa 80% dell'altezza dello stesso
    this.headerHidden = scrollAmount.scrollTop > 120;
  }

  hasPermesso(codes: string) {
    if (!codes) {
      return true;
    }
    return this.userService.hasPermesso(codes);
  }

  openCloseSideBar() {
    this.stateSideBar = !this.stateSideBar;
    this.notifyStateSidebar.emit(this.stateSideBar);

  }

}
