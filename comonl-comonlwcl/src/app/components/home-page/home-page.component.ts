/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Ruolo, SystemService } from 'src/app/modules/comonlapi';
import { ComonlStorageService } from 'src/app/services/storage/comonl-storage.service';
import { TitoloPaginaService } from 'src/app/services/titolo-pagina.service';
import { SidebarService } from '../../services';


@Component({
  selector: 'comonl-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit, OnDestroy {

  constructor(
    private sidebarService: SidebarService,
    private titoloPaginaService: TitoloPaginaService,
    private router: Router,
    private comonlStorageService: ComonlStorageService,
    private systemService: SystemService,
  ) { }

  // @ViewChild('ts', { static: true }) myTabs: NgbTabset;
  tabTitolo = true;
  tabCode = false;
  ilRuolo: Ruolo;

  formSearchIntervento: FormGroup = new FormGroup({
    titolo: new FormControl(null),
    codIntervento: new FormControl(null)
  });

  clickTab(tabName: string) {
    if (tabName === 'titolo') {
      this.tabTitolo = true;
      this.tabCode = false;
    } else if (tabName === 'code') {
      this.tabTitolo = false;
      this.tabCode = true;
    }
  }

  ngOnInit() {
    // this.utilitiesService.hideSpinner();
    this.sidebarService.showLeftSideBar();
    this.sidebarService.loadContent(null);
    this.titoloPaginaService.triggerTitolo('APP.TITLE');
    this.comonlStorageService.ruolo$.subscribe(async item => {
      this.ilRuolo = item;
    });
  }

  ngOnDestroy() {
  }


  goToRicercaComunicazioni() {
    this.router.navigateByUrl('/ricerca-comunicazioni');
  }

  goToComunicazioneMassiva() {
    this.router.navigateByUrl('/comunicazione-massiva');
  }

  goToAnagraficaLavoratore() {
    this.router.navigateByUrl('/anagrafica-lavoratore', { state: {comingFromHome: true }});
  }

  goToAccreditamentoAnagrafiche() {
    // this.router.navigateByUrl('/accreditamento-anagrafiche');
    this.redirectTo('/accreditamento-anagrafiche');
  }

  goToAnagraficaAzienda() {
    this.router.navigateByUrl('/anagrafica-azienda');
  }
  goToRicercaVardator() {
    this.router.navigateByUrl('/ricerca-vardatori');
  }

  goToAccreditamentoDeleghe() {
    this.router.navigateByUrl('/accreditamento-deleghe');
  }

  goToGestioneApplicativa() {
    this.router.navigateByUrl('/gestione-applicativa');
  }

  async onClickTestError() {
    const res = await this.systemService.testError(401).toPromise();
  }

  isBlank(str: string) {
    return (!str || /^\s*$/.test(str));
  }

  onGoToLink(link: string) {
    this.router.navigateByUrl(link);
  }

  private redirectTo(uri:string){
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(()=>
    this.router.navigate([uri]));
 }

}
