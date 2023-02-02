/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { UtenteService } from 'src/app/modules/comonlapi';
import { UserService } from 'src/app/services';

@Component({
  selector: 'comonl-header-int',
  templateUrl: './header-int.component.html',
  styleUrls: ['./header-int.component.scss']
})
export class HeaderIntComponent implements OnInit {
  // elencoProgrammi: Programma[] = [];
  // private settore: Settore;
  // private subscriptions: Subscription[] = [];
  constructor(
    // private userService: UserService,
    // private programmaService: ProgrammaService,
  ) {}

  async ngOnInit() {
    // this.subscriptions.push(
    //   this.userService.settore$.subscribe(settore => this.settore = settore)
    // );
    // const [programmi] = await Promise.all([
    //   this.programmaService.getProgrammiBySettore(this.settore.id,  true).toPromise()
    // ]);
    // this.elencoProgrammi = programmi;
  }
  // ngOnDestroy() {
  //   this.subscriptions.forEach(sub => sub.unsubscribe());
  // }

  // get programma() {
  //   return this.elencoProgrammi[0];
  // }
  // get stato() {
  //   return this.elencoProgrammi[0].stato.descrizione;
  // }
}
