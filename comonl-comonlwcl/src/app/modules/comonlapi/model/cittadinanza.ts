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


export interface Cittadinanza { 
    apiWarnings?: Array<ApiError>;
    id?: number;
    codCittadinanzaMin?: string;
    codCittadinanzaOld?: string;
    codMf?: string;
    dsComTCittadinanza?: string;
    dsNazione?: string;
    dtFine?: Date;
    dtInizio?: Date;
    dtTmst?: Date;
    flgUe?: string;
}
