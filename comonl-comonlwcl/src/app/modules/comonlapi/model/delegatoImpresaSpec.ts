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
import { AnagraficaDatore } from './anagraficaDatore';
import { AnagraficaDelegato } from './anagraficaDelegato';
import { ApiError } from './apiError';
import { Delega } from './delega';
import { DelegatoImpresaPK } from './delegatoImpresaPK';


export interface DelegatoImpresaSpec { 
    apiWarnings?: Array<ApiError>;
    id?: DelegatoImpresaPK;
    dtFineCarica?: Date;
    flgScuola?: string;
    idCaricaPersonaPv?: string;
    pv?: string;
    anagraficaDelegato?: AnagraficaDelegato;
    anagraficaDatore?: AnagraficaDatore;
    denominazione?: string;
    dataAnnullamento?: Date;
    delegas?: Array<Delega>;
}
