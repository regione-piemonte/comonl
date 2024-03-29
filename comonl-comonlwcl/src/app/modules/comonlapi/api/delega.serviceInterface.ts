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
import { HttpHeaders }                                       from '@angular/common/http';

import { Observable }                                        from 'rxjs';

import { ApiError } from '../model/apiError';
import { Delega } from '../model/delega';
import { FormRicercaDelega } from '../model/formRicercaDelega';
import { PagedResponseRicercaDeleghe } from '../model/pagedResponseRicercaDeleghe';
import { PostDelegaResponse } from '../model/postDelegaResponse';


import { Configuration }                                     from '../configuration';


export interface DelegaServiceInterface {
    defaultHeaders: HttpHeaders;
    configuration: Configuration;
    

    /**
    * aggiorna - inserisce delega
    * 
    * @param body 
    */
    aggiornaDelega(body?: Delega, extraHttpRequestParams?: any): Observable<PostDelegaResponse>;

    /**
    * aggiorna stato delega
    * 
    * @param body 
    */
    aggiornaStatoDelega(body?: Delega, extraHttpRequestParams?: any): Observable<Delega>;

    /**
    * ricerca deleghe
    * 
    * @param body 
    * @param offset 
    * @param limit 
    * @param sort 
    * @param direction 
    */
    postRicercaDeleghe(body?: FormRicercaDelega, offset?: number, limit?: number, sort?: string, direction?: string, extraHttpRequestParams?: any): Observable<PagedResponseRicercaDeleghe>;

    /**
    * Stampa ricerca deleghe
    * 
    * @param body 
    */
    stampaRicercaDeleghe(body?: FormRicercaDelega, extraHttpRequestParams?: any): Observable<Blob>;

}
