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
/* tslint:disable:no-unused-variable member-ordering */

import { Inject, Injectable, Optional }                      from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams,
         HttpResponse, HttpEvent }                           from '@angular/common/http';
import { CustomHttpUrlEncodingCodec }                        from '../encoder';

import { Observable }                                        from 'rxjs';

import { ApiError } from '../model/apiError';
import { DelegatoImpresa } from '../model/delegatoImpresa';
import { DelegatoImpresaSpec } from '../model/delegatoImpresaSpec';
import { SetDataAnnullamentoAllCfImpresaRes } from '../model/setDataAnnullamentoAllCfImpresaRes';

import { BASE_PATH, COLLECTION_FORMATS }                     from '../variables';
import { Configuration, FormParams }                         from '../configuration';
import { DelegatoImpresaServiceInterface }                            from './delegatoImpresa.serviceInterface';


@Injectable()
export class DelegatoImpresaService implements DelegatoImpresaServiceInterface {

    protected basePath = 'http://localhost:8080/comonlweb/api/v1';
    public defaultHeaders = new HttpHeaders();
    public configuration = new Configuration();

    constructor(protected httpClient: HttpClient, @Optional()@Inject(BASE_PATH) basePath: string, @Optional() configuration: Configuration) {
        if (basePath) {
            this.basePath = basePath;
        }
        if (configuration) {
            this.configuration = configuration;
            this.basePath = basePath || configuration.basePath || this.basePath;
        }
    }

    /**
     * @param consumes string[] mime-types
     * @return true: consumes contains 'multipart/form-data', false: otherwise
     */
    private canConsumeForm(consumes: string[]): boolean {
        const form = 'multipart/form-data';
        for (const consume of consumes) {
            if (form === consume) {
                return true;
            }
        }
        return false;
    }


    /**
     * Restuisce i DelegatoImpresa arricchiti di una impresa
     * 
     * @param cfImpresa 
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getDelegatoImpresaSpec(cfImpresa: string, observe?: 'body', reportProgress?: boolean): Observable<Array<DelegatoImpresaSpec>>;
    public getDelegatoImpresaSpec(cfImpresa: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Array<DelegatoImpresaSpec>>>;
    public getDelegatoImpresaSpec(cfImpresa: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Array<DelegatoImpresaSpec>>>;
    public getDelegatoImpresaSpec(cfImpresa: string, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {
        if (cfImpresa === null || cfImpresa === undefined) {
            throw new Error('Required parameter cfImpresa was null or undefined when calling getDelegatoImpresaSpec.');
        }

        let headers = this.defaultHeaders;

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            'application/json'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
            'application/json'
        ];

        return this.httpClient.get<Array<DelegatoImpresaSpec>>(`${this.basePath}/delegato-impresa/delegato-impresa-spec/cf-impresa/${encodeURIComponent(String(cfImpresa))}`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * Inserisce il delegato impresa
     * 
     * @param body 
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public postDelegatoImpresa(body?: DelegatoImpresa, observe?: 'body', reportProgress?: boolean): Observable<DelegatoImpresa>;
    public postDelegatoImpresa(body?: DelegatoImpresa, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<DelegatoImpresa>>;
    public postDelegatoImpresa(body?: DelegatoImpresa, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<DelegatoImpresa>>;
    public postDelegatoImpresa(body?: DelegatoImpresa, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        let headers = this.defaultHeaders;

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];
        const httpContentTypeSelected: string | undefined = this.configuration.selectHeaderContentType(consumes);
        if (httpContentTypeSelected != undefined) {
            headers = headers.set('Content-Type', httpContentTypeSelected);
        }

        return this.httpClient.post<DelegatoImpresa>(`${this.basePath}/delegato-impresa`,
            body,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * Annulla o ripristina un DelegatoImpresa impostando dataAnnullamento a data di sistema
     * 
     * @param cfImpresa 
     * @param flgAutorizzazione 
     * @param flgAnnullamento 
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public setDataAnnullamentoAllCfImpresa(cfImpresa: string, flgAutorizzazione: boolean, flgAnnullamento: boolean, observe?: 'body', reportProgress?: boolean): Observable<SetDataAnnullamentoAllCfImpresaRes>;
    public setDataAnnullamentoAllCfImpresa(cfImpresa: string, flgAutorizzazione: boolean, flgAnnullamento: boolean, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<SetDataAnnullamentoAllCfImpresaRes>>;
    public setDataAnnullamentoAllCfImpresa(cfImpresa: string, flgAutorizzazione: boolean, flgAnnullamento: boolean, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<SetDataAnnullamentoAllCfImpresaRes>>;
    public setDataAnnullamentoAllCfImpresa(cfImpresa: string, flgAutorizzazione: boolean, flgAnnullamento: boolean, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {
        if (cfImpresa === null || cfImpresa === undefined) {
            throw new Error('Required parameter cfImpresa was null or undefined when calling setDataAnnullamentoAllCfImpresa.');
        }
        if (flgAutorizzazione === null || flgAutorizzazione === undefined) {
            throw new Error('Required parameter flgAutorizzazione was null or undefined when calling setDataAnnullamentoAllCfImpresa.');
        }
        if (flgAnnullamento === null || flgAnnullamento === undefined) {
            throw new Error('Required parameter flgAnnullamento was null or undefined when calling setDataAnnullamentoAllCfImpresa.');
        }

        let headers = this.defaultHeaders;

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            'application/json'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
            'application/json'
        ];

        return this.httpClient.put<SetDataAnnullamentoAllCfImpresaRes>(`${this.basePath}/delegato-impresa/annullamento-ripristino/cf-impresa/${encodeURIComponent(String(cfImpresa))}/auto/${encodeURIComponent(String(flgAutorizzazione))}/flg-annullamento/${encodeURIComponent(String(flgAnnullamento))}`,
            null,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * Annulla o ripristina un DelegatoImpresa impostando dataAnnullamento a data di sistema
     * 
     * @param cfDelegato 
     * @param tipoAnagrafica 
     * @param cfImpresa 
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public setDataAnnullamentoDelegatoImpresa(cfDelegato: string, tipoAnagrafica: string, cfImpresa: string, observe?: 'body', reportProgress?: boolean): Observable<Date>;
    public setDataAnnullamentoDelegatoImpresa(cfDelegato: string, tipoAnagrafica: string, cfImpresa: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Date>>;
    public setDataAnnullamentoDelegatoImpresa(cfDelegato: string, tipoAnagrafica: string, cfImpresa: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Date>>;
    public setDataAnnullamentoDelegatoImpresa(cfDelegato: string, tipoAnagrafica: string, cfImpresa: string, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {
        if (cfDelegato === null || cfDelegato === undefined) {
            throw new Error('Required parameter cfDelegato was null or undefined when calling setDataAnnullamentoDelegatoImpresa.');
        }
        if (tipoAnagrafica === null || tipoAnagrafica === undefined) {
            throw new Error('Required parameter tipoAnagrafica was null or undefined when calling setDataAnnullamentoDelegatoImpresa.');
        }
        if (cfImpresa === null || cfImpresa === undefined) {
            throw new Error('Required parameter cfImpresa was null or undefined when calling setDataAnnullamentoDelegatoImpresa.');
        }

        let headers = this.defaultHeaders;

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            'application/json'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
            'application/json'
        ];

        return this.httpClient.put<Date>(`${this.basePath}/delegato-impresa/annullamento-ripristino/cf-delegato/${encodeURIComponent(String(cfDelegato))}/tipo-anagrafica/${encodeURIComponent(String(tipoAnagrafica))}/cf-impresa${encodeURIComponent(String(cfImpresa))}`,
            null,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

}
