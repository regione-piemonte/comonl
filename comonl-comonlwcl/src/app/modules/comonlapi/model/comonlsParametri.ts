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


export interface ComonlsParametri { 
    apiWarnings?: Array<ApiError>;
    id?: number;
    abilitato?: string;
    descrizione?: string;
    dsParametro?: string;
    modificabileDaMaschera?: string;
    tipoParametro?: string;
    valoreParametro?: string;
}
