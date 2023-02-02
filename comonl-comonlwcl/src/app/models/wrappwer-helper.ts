/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { SedeLavoro } from './../modules/comonlapi/model/sedeLavoro';
import { DatiAzienda } from './../modules/comonlapi/model/datiAzienda';
import { Datore, LavoratoreSilpEspanso, RapportiLavoratoriSediInteressateVD } from 'src/app/modules/comonlapi';
import { Comunicazione, Ruolo } from '../modules/comonlapi';
import { NavComunicazioneParams } from '../modules/comunicazione/modules/nav-main-comunicazione/models/nav-comunicazione-params';
import { TrasferimentoDistaccoForm } from './TrasferimentoDistaccoForm';

export interface WrapperHelper {
    comunicazione?: Comunicazione;
    datore?: Datore;
    lavoratore?: LavoratoreSilpEspanso;
    azienda?: DatiAzienda;
    anagraficaLavoratore?: any;
    anagraficaAzienda?: any;
    ruolo?: Ruolo;
    flgIcona?: boolean;
    sedeLegale?: SedeLavoro;
    sedeOperativa?: any;
    trasferimentoDistaccoForm?: TrasferimentoDistaccoForm;
    sediLavoratoriRapporti?: RapportiLavoratoriSediInteressateVD[];
}
