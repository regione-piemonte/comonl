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
import { SetDataAnnullamentoSoggettoAbilitatoRes } from '../model/setDataAnnullamentoSoggettoAbilitatoRes';
import { SoggettoAbilitato } from '../model/soggettoAbilitato';


import { Configuration }                                     from '../configuration';


export interface SoggettoAbilitatoServiceInterface {
    defaultHeaders: HttpHeaders;
    configuration: Configuration;
    

    /**
    * SoggettoAbilitato
    * 
    * @param idSeoggettoAbilitato 
    */
    getSoggettoAbilitatoById(idSeoggettoAbilitato: number, extraHttpRequestParams?: any): Observable<SoggettoAbilitato>;

    /**
    * SoggettoAbilitato
    * 
    * @param cfSoggetto 
    */
    getSoggettoAbilitatoBycfSoggetto(cfSoggetto: string, extraHttpRequestParams?: any): Observable<SoggettoAbilitato>;

    /**
    * Post SoggettoAbilitato
    * 
    * @param body 
    */
    postSoggettoAbilitato(body?: SoggettoAbilitato, extraHttpRequestParams?: any): Observable<SoggettoAbilitato>;

    /**
    * Put SoggettoAbilitato
    * 
    * @param body 
    */
    putSoggettoAbilitato(body?: SoggettoAbilitato, extraHttpRequestParams?: any): Observable<SoggettoAbilitato>;

    /**
    * Annulla o ripristina un SoggettoAbilitato impostando dataAnnullamento a data di sistema
    * 
    * @param idSoggettoAbilitato 
    * @param flgAutorizzazione 
    */
    setDataAnnullamento(idSoggettoAbilitato: number, flgAutorizzazione: boolean, extraHttpRequestParams?: any): Observable<SetDataAnnullamentoSoggettoAbilitatoRes>;

}
