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
import { Cpi } from './cpi';
import { Provincia } from './provincia';


export interface Comune { 
    apiWarnings?: Array<ApiError>;
    id?: number;
    codCap?: string;
    codComuneMin?: string;
    codInps?: string;
    codIstat?: string;
    codPrefisso?: string;
    dsComTComune?: string;
    dtFine?: Date;
    dtInizio?: Date;
    dtTmst?: Date;
    cpi?: Cpi;
    provincia?: Provincia;
}
