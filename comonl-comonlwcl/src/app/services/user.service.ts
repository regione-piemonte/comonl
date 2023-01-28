/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Injectable } from '@angular/core';
import { Subject, BehaviorSubject, Observable } from 'rxjs';
import { SessionStorageService } from 'src/app/services/storage';
import { ScrollAmount, NavigationUrl } from '../models';
import { Router } from '@angular/router';
import { UserLinkMap } from '../models/utils/user-link-map';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  public static readonly RUOLO_SESSION = 'UserService.Ruolo';
  public static readonly MODULI_SESSION = 'UserService.Moduli';
  public static readonly PERMESSI_SESSION = 'UserService.Permessi';
  public static readonly USER_MANUAL_LINK = 'UserService.UserManualLink';

  private navigationStack: NavigationUrl[] = [];

  private readonly currentUrlSubject: BehaviorSubject<string> = new BehaviorSubject(null);
  private readonly userLinkManualSubject: BehaviorSubject<string> = new BehaviorSubject(UserLinkMap.DEFAULT);

  // private readonly permessiSubject: BehaviorSubject<Permesso[]> = new BehaviorSubject([]);
  private readonly scrollSubject: Subject<ScrollAmount> = new Subject<ScrollAmount>();

  private readonly uiUpdateSubject: Subject<void> = new Subject<void>();

  constructor(
    private storageService: SessionStorageService,
    private router: Router,
  ) { }

  get getNavigationStack() {
    return this.navigationStack;
  }
  get currentUrl$(): Observable<string> { return this.currentUrlSubject.asObservable(); }
  get userLinkManual$(): Observable<string> { return this.userLinkManualSubject.asObservable(); }
  // get permessi$(): Observable<Permesso[]> { return this.permessiSubject.asObservable(); }
  get uiUpdate$(): Observable<void> { return this.uiUpdateSubject.asObservable(); }
  get scroll$(): Observable<ScrollAmount> { return this.scrollSubject.asObservable(); }

  setCurrentUrl(currentUrl: string) {
    const currentSavedUrl = this.currentUrlSubject.getValue();
    if (currentSavedUrl === currentUrl) {
      // Same URL, ignore
      return;
    }
    if (currentSavedUrl) {
      this.navigationStack.push({ url: currentSavedUrl, searchUrl: this.cleanUrl(currentSavedUrl) });
    }
    // Search in stack ignoring the "Angular URL parameters (i.e: ;)"
    const searchUrl = this.cleanUrl(currentUrl);
    const stackIndex = this.navigationStack.findIndex(el => el.searchUrl === searchUrl);
    if (stackIndex !== -1) {
      this.navigationStack = this.navigationStack.slice(0, stackIndex);
    }
    this.currentUrlSubject.next(currentUrl);
  }

  back() {
    const [url] = this.navigationStack.slice(-1);
    this.router.navigateByUrl(url.url);
  }
  
  // setPermessi(permessi: Permesso[]) {
  //   this.storageService.setItem(UserService.PERMESSI_SESSION, permessi || []);
  //   this.permessiSubject.next(permessi || []);
  // }

  setUserManualLink(userLink: string) {
    this.storageService.setItem(UserService.USER_MANUAL_LINK, userLink || UserLinkMap.DEFAULT);
    this.userLinkManualSubject.next(userLink || UserLinkMap.DEFAULT);
  }
  
  triggerUiUpdate() {
    this.uiUpdateSubject.next();
  }

  triggerScroll(scroll: ScrollAmount) {
    this.scrollSubject.next(scroll);
  }

  hasPermesso(code: string): boolean {
    return true;
    // const permessi = this.permessiSubject.getValue();
    // return permessi.some(el => el.codice === code);
  }

  hasPermessi(codes: string[]): boolean {
    return codes.some(code => this.hasPermesso(code));
  }

  hasAllPermessi(codes: string[]): boolean {
    return codes.every(code => this.hasPermesso(code));
  }

  private cleanUrl(url: string): string {
    const quotationMarkIndex = url.indexOf('?');
    if (quotationMarkIndex === -1) {
      const colonIndex = url.indexOf(';');
      if (colonIndex !== -1) {
        return url.substring(0, colonIndex);
      }
    }
    return url;
  }
  
}
