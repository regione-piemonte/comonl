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

import { AnagraficaAziAccent } from '../model/anagraficaAziAccent';
import { AnagraficaDelegato } from '../model/anagraficaDelegato';
import { ApiError } from '../model/apiError';
import { DelegatoImpresa } from '../model/delegatoImpresa';
import { FormRicercaAccreditamentoAnagrafiche } from '../model/formRicercaAccreditamentoAnagrafiche';
import { PagedResponseRicercaAccreditamentoAzienda } from '../model/pagedResponseRicercaAccreditamentoAzienda';
import { PagedResponseRicercaAccreditamentoConsulente } from '../model/pagedResponseRicercaAccreditamentoConsulente';


import { Configuration }                                     from '../configuration';


export interface AnagraficaDelegatoServiceInterface {
    defaultHeaders: HttpHeaders;
    configuration: Configuration;
    

    /**
    * addAzienda
    * 
    * @param body 
    */
    addAzienda(body?: DelegatoImpresa, extraHttpRequestParams?: any): Observable<DelegatoImpresa>;

    /**
    * addConsulente
    * 
    * @param cfDelegato 
    * @param idSoggettoAbilitato 
    */
    addConsulente(cfDelegato: string, idSoggettoAbilitato: number, extraHttpRequestParams?: any): Observable<AnagraficaDelegato>;

    /**
    * Restuisce AnagraficaDelegato
    * 
    * @param cfDelegato 
    */
    getAnagraficaDelegatoByCodiceFiscale(cfDelegato: string, extraHttpRequestParams?: any): Observable<AnagraficaDelegato>;

    /**
    * Restuisce un lista di AnagraficaDelegato
    * 
    * @param idSoggettoAbilitato 
    */
    getAnagraficaDelegatoByIdSoggettoAbilitato(idSoggettoAbilitato: number, extraHttpRequestParams?: any): Observable<Array<AnagraficaDelegato>>;

    /**
    * Restuisce un AnagraficaAziAccent
    * 
    * @param cfImpresa 
    */
    getAziendaAccentrata(cfImpresa: string, extraHttpRequestParams?: any): Observable<AnagraficaAziAccent>;

    /**
    * Restuisce i DelegatoImpresa di un&#39;AnagraficaDelegato
    * 
    * @param cfDelegato 
    * @param tipoAnagrafica 
    */
    getDelegatoImpresaByIdAnagraficadelegato(cfDelegato: string, tipoAnagrafica: string, extraHttpRequestParams?: any): Observable<Array<DelegatoImpresa>>;

    /**
    * Post AnagraficaDelegato
    * 
    * @param body 
    */
    postAnagraficaDelegato(body?: AnagraficaDelegato, extraHttpRequestParams?: any): Observable<AnagraficaDelegato>;

    /**
    * postAziendaAccentrata
    * 
    * @param body 
    */
    postAziendaAccentrata(body?: AnagraficaAziAccent, extraHttpRequestParams?: any): Observable<AnagraficaAziAccent>;

    /**
    * Ricerca accreditamento per studio prof. e aziende
    * 
    * @param body 
    * @param offset 
    * @param limit 
    * @param sort 
    * @param direction 
    */
    postRicercaAccreditamentoAzienda(body?: FormRicercaAccreditamentoAnagrafiche, offset?: number, limit?: number, sort?: string, direction?: string, extraHttpRequestParams?: any): Observable<PagedResponseRicercaAccreditamentoAzienda>;

    /**
    * Ricerca accreditamento per consulenti e persone fisiche
    * 
    * @param body 
    * @param offset 
    * @param limit 
    * @param sort 
    * @param direction 
    */
    postRicercaAccreditamentoConsulente(body?: FormRicercaAccreditamentoAnagrafiche, offset?: number, limit?: number, sort?: string, direction?: string, extraHttpRequestParams?: any): Observable<PagedResponseRicercaAccreditamentoConsulente>;

    /**
    * Put AnagraficaDelegato
    * 
    * @param body 
    */
    putAnagraficaDelegato(body?: AnagraficaDelegato, extraHttpRequestParams?: any): Observable<AnagraficaDelegato>;

    /**
    * putAziendaAccentrata
    * 
    * @param body 
    */
    putAziendaAccentrata(body?: AnagraficaAziAccent, extraHttpRequestParams?: any): Observable<AnagraficaAziAccent>;

    /**
    * removeConsulente
    * 
    * @param cfDelegato 
    * @param idSoggettoAbilitato 
    */
    removeConsulente(cfDelegato: string, idSoggettoAbilitato: number, extraHttpRequestParams?: any): Observable<AnagraficaDelegato>;

    /**
    * Annulla o ripristina un AnagraficaDelegato impostando dataAnnullamento a data di sistema
    * 
    * @param cfDelegato 
    * @param tipoAnagrafica 
    */
    setDataAnnullamentoCDE(cfDelegato: string, tipoAnagrafica: string, extraHttpRequestParams?: any): Observable<Date>;

    /**
    * Stampa elenco anagrafica delegati
    * 
    * @param tipoFormato 
    * @param body 
    */
    stampaElencoAnagraficheDelegato(tipoFormato: string, body?: FormRicercaAccreditamentoAnagrafiche, extraHttpRequestParams?: any): Observable<Blob>;

    /**
    * Stampa elenco studi aziende
    * 
    * @param tipoFormato 
    * @param body 
    */
    stampaElencoStudiAziende(tipoFormato: string, body?: FormRicercaAccreditamentoAnagrafiche, extraHttpRequestParams?: any): Observable<Blob>;

}
