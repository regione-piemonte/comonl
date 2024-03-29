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
import { ApiError } from './apiError';
import { TipologiaRapporto } from './tipologiaRapporto';


export interface TipoContratti { 
    apiWarnings?: Array<ApiError>;
    id?: number;
    codTipoContrattoMin?: string;
    dsComTTipoContratto?: string;
    dtFine?: Date;
    dtInizio?: Date;
    dtTmst?: Date;
    flgForma?: string;
    status?: string;
    tipo?: string;
    tipologiaRapporto?: TipologiaRapporto;
}
