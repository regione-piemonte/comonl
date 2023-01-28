/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Ruolo } from 'src/app/modules/comonlapi';
import { SidebarService, UserService, UtilitiesService } from 'src/app/services';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { POSSIBLE_SIDEBAR_MODULES, ComonlSidebarModule } from '../../models';

@Component({
  selector: 'comonl-sidebar-left',
  templateUrl: './sidebar-left.component.html',
  styleUrls: ['./sidebar-left.component.scss']
})
export class SidebarLeftComponent implements OnInit, OnDestroy {

  close: boolean = true;
  @Input() set state(state: boolean){
    this.close = state;
    this.sidebarService.setCloseSideBar(this.close);
  }
  currentUrl: string;
  possibleModules = POSSIBLE_SIDEBAR_MODULES.filter(m => !m.ignore);
  private readonly subscriptions: Subscription[] = [];

  ilRuolo: Ruolo;

  constructor(
    private utilitiesService: UtilitiesService,
    private userService: UserService,
    private sidebarService: SidebarService,
    private router: Router,
    private comonlStorageService: ComonlStorageService

  ) { }

  ngOnInit() {
    this.subscriptions.push(
      this.userService.currentUrl$.subscribe(currentUrl => this.currentUrl = currentUrl)
    );
    this.sidebarService.setCloseSideBar(this.close);
    this.comonlStorageService.ruolo$.subscribe(el => this.ilRuolo = el);
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  inSubpath(sm: ComonlSidebarModule): boolean {
    return this.currentUrl && (sm.urlSubpaths.some(url => this.currentUrl.indexOf(url) === 0) || (sm.isHome && this.currentUrl === '/'));
  }

  setClass(){
    this.close = !this.close;
    this.sidebarService.setCloseSideBar(this.close);
  }

  async loadPermessi(sm: ComonlSidebarModule) {
    // this.utilitiesService.showSpinner();
    const code = sm && sm.code || '';
    // Impostazione link per manuale utente
    // this.userService.setUserManualLink(UserLinkMap[modulo && modulo.codice || ''] || UserLinkMap.DEFAULT);
    try {
      // const permessi = await this.utenteService.getPermessiBySettoreAndModulo(this.settore.id, modulo.id).toPromise();
      // this.userService.setPermessi(permessi);
    } catch (e) {
      this.utilitiesService.handleApiErrors(e, '');
    }

  }

  onClickInserisci() {

  }

  private redirectTo(uri:string){
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(()=>
    this.router.navigate([uri]));
 }
}
