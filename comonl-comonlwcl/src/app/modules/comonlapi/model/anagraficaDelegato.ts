/**
 * ComonlWeb
 * API per ComonlWeb
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
import { AnagraficaDelegatoPK } from './anagraficaDelegatoPK';
import { ApiError } from './apiError';
import { Comune } from './comune';
import { Personalizzazione } from './personalizzazione';
import { SoggettoAbilitato } from './soggettoAbilitato';
import { StatiEsteri } from './statiEsteri';


export interface AnagraficaDelegato { 
    apiWarnings?: Array<ApiError>;
    id?: AnagraficaDelegatoPK;
    idUserInsert?: string;
    idUserUltMod?: string;
    dtInsert?: Date;
    dtUltMod?: Date;
    cittadinanza?: number;
    capDom?: string;
    capRes?: string;
    cognome?: string;
    dtNascita?: Date;
    email?: string;
    fax?: string;
    indirizzoDom?: string;
    indirizzoRes?: string;
    nome?: string;
    pivaDelegato?: string;
    sesso?: string;
    tel?: string;
    soggettoAbilitato?: SoggettoAbilitato;
    comuneDom?: Comune;
    comuneRes?: Comune;
    comuneNasc?: Comune;
    statiEsteri?: StatiEsteri;
    statiEsteriDom?: StatiEsteri;
    statiEsteriRes?: StatiEsteri;
    personalizzazione?: Personalizzazione;
    dataAnnullamento?: Date;
}
