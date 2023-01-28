/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Atecofin, Comune, StatiEsteri, Trasformazionerl } from '../modules/comonlapi';

export interface TrasferimentoDistaccoForm{
    formDtTrasfTipoSpost?: FormDtTrasfTipoSpost,
    formTrasferimento?: FormTrasferimento,
    formDistacco?: FormDistacco
}

export interface FormDtTrasfTipoSpost{
    dtTrasferimento?: Date,
    tipoSpostamento?: Trasformazionerl
}

export interface FormTrasferimento{
    statoEsteroSedePrec?: StatiEsteri,
    comuneSedePrec?: Comune,
    indirizzoSedePrec: string,
    cap?: string,
}

export interface FormDistacco{
    dataFineDistacco?: Date,
    distaccoParziale?: string,
    distaccoAzEst?: string,
    cfDatoreDistaccatario?: string,
    idAziendaSilp: string,
    denominazione?: string,
    ateco?: Atecofin,
    patINAIL?: string,
    statoEstero?: StatiEsteri,
    comune?: Comune,
    indirizzo?: string,
    cap?: string,
    telefono?: string,
    fax?: string,
    email?: string
    idSedeSilp: string
}