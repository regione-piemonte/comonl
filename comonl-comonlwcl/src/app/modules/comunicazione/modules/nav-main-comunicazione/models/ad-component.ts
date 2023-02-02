/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { EventEmitter } from "@angular/core";
import { Comunicazione } from 'src/app/modules/comonlapi';
import { PersistenceComunicazioneWreapper } from '../components/nav-main-comunicazione/nav-main-comunicazione.component';


export interface AdComponent {
    data?: any;
    setComunicazioneToSave?: EventEmitter<Comunicazione>;
    persistenceNotification?: EventEmitter<PersistenceComunicazioneWreapper>
    goTonext?: EventEmitter<any>;
}
