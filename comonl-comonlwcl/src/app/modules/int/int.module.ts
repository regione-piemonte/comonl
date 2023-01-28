/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { NgModule } from '@angular/core';
import { IntRoutingModule } from 'src/app/modules/int/int-routing.module';
// import { InterventoModule } from 'src/app/modules/int/modules/intervento/intervento.module';
import { HeaderIntComponent } from './components/header-int/header-int.component';
import { NgbTabsetModule } from '@ng-bootstrap/ng-bootstrap';
import { ComonlcommonModule } from '../comonlcommon/comonlcommon.module';

// /ordine per caricare il modulo
// /ordine carica la componente TabsOrdineComponent

@NgModule({
  declarations: [
    HeaderIntComponent
  ],
  imports: [
    ComonlcommonModule,
    IntRoutingModule,
    // InterventoModule,
    NgbTabsetModule
  ],
  exports: [],
  providers: []
})
export class IntModule { }
