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


export interface NaturaGiuridica { 
    apiWarnings?: Array<ApiError>;
    id?: string;
    naturaGiuridica?: string;
    descrizione?: string;
    dtFineVldt?: Date;
    dtInizioVldt?: Date;
    busarl?: string;
    busc?: string;
    regolarizzazione?: string;
    iscrizione?: string;
}
