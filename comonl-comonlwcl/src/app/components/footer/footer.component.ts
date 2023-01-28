/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'comonl-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  goToTop(e: Event): boolean {
    e.preventDefault();
    document.getElementsByTagName('comonl-header')[0].scrollIntoView({ behavior: 'smooth', block: 'center' });
    return false;
  }
}
