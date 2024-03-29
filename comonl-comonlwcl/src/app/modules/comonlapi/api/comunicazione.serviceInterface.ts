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
import { Comunicazione } from '../model/comunicazione';
import { ComunicazioneUrgHolder } from '../model/comunicazioneUrgHolder';
import { Datore } from '../model/datore';
import { FormRecuperoComunicazione } from '../model/formRecuperoComunicazione';
import { FormRicercaComunicazione } from '../model/formRicercaComunicazione';
import { PagedResponseRicercaComunicazioni } from '../model/pagedResponseRicercaComunicazioni';
import { PagedResponseRicercaVardatori } from '../model/pagedResponseRicercaVardatori';
import { Rapporto } from '../model/rapporto';
import { RecuperoComunicazione } from '../model/recuperoComunicazione';
import { Tutore } from '../model/tutore';
import { WrapperComunicazione } from '../model/wrapperComunicazione';
import { WrapperRapporto } from '../model/wrapperRapporto';


import { Configuration }                                     from '../configuration';


export interface ComunicazioneServiceInterface {
    defaultHeaders: HttpHeaders;
    configuration: Configuration;
    

    /**
    * Cancel Comunicazione
    * 
    * @param body 
    */
    annullaComunicazione(body?: WrapperComunicazione, extraHttpRequestParams?: any): Observable<Comunicazione>;

    /**
    * Cancel Comunicazione
    * 
    * @param body 
    */
    annullaComunicazioneVardatore(body?: WrapperComunicazione, extraHttpRequestParams?: any): Observable<Comunicazione>;

    /**
    * Cancella Comunicazione
    * 
    * @param id 
    */
    cancellaComunicazione(id: number, extraHttpRequestParams?: any): Observable<Comunicazione>;

    /**
    * Comunicazione
    * 
    * @param id 
    */
    getComunicazioneById(id: number, extraHttpRequestParams?: any): Observable<Comunicazione>;

    /**
    * Comunicazione
    * 
    * @param id 
    */
    getComunicazioneUrgenzaHolderById(id: number, extraHttpRequestParams?: any): Observable<ComunicazioneUrgHolder>;

    /**
    * Invia Comunicazione
    * 
    * @param body 
    */
    inviaComunicazione(body?: WrapperComunicazione, extraHttpRequestParams?: any): Observable<Array<Comunicazione>>;

    /**
    * Post Comunicazione
    * 
    * @param body 
    */
    postComunicazione(body?: WrapperComunicazione, extraHttpRequestParams?: any): Observable<Comunicazione>;

    /**
    * Post Comunicazione di Urgenza
    * 
    * @param body 
    */
    postComunicazioneUrgenza(body?: WrapperComunicazione, extraHttpRequestParams?: any): Observable<number>;

    /**
    * Post Comunicazione vardatore
    * 
    * @param body 
    */
    postComunicazioneVardatore(body?: WrapperComunicazione, extraHttpRequestParams?: any): Observable<Comunicazione>;

    /**
    * Post Rapporto Missione
    * 
    * @param body 
    */
    postRapportoMissione(body?: WrapperComunicazione, extraHttpRequestParams?: any): Observable<Rapporto>;

    /**
    * recupero comunicazioni
    * 
    * @param body 
    */
    postRecuperoComunicazioni(body?: FormRecuperoComunicazione, extraHttpRequestParams?: any): Observable<Array<RecuperoComunicazione>>;

    /**
    * ricerca comunicazioni
    * 
    * @param body 
    * @param offset 
    * @param limit 
    * @param sort 
    * @param direction 
    */
    postRicercaComunicazioni(body?: FormRicercaComunicazione, offset?: number, limit?: number, sort?: string, direction?: string, extraHttpRequestParams?: any): Observable<PagedResponseRicercaComunicazioni>;

    /**
    * ricerca comunicazioni vardatori
    * 
    * @param body 
    * @param offset 
    * @param limit 
    * @param sort 
    * @param direction 
    */
    postRicercaComunicazioniVardatori(body?: FormRicercaComunicazione, offset?: number, limit?: number, sort?: string, direction?: string, extraHttpRequestParams?: any): Observable<PagedResponseRicercaComunicazioni>;

    /**
    * ricerca comunicazioni vardatori
    * 
    * @param body 
    * @param offset 
    * @param limit 
    * @param sort 
    * @param direction 
    */
    postRicercaVardatori(body?: FormRicercaComunicazione, offset?: number, limit?: number, sort?: string, direction?: string, extraHttpRequestParams?: any): Observable<PagedResponseRicercaVardatori>;

    /**
    * Post Tutore
    * 
    * @param body 
    */
    postTutore(body?: Comunicazione, extraHttpRequestParams?: any): Observable<Tutore>;

    /**
    * Put Comunicazione
    * 
    * @param body 
    */
    putComunicazione(body?: WrapperComunicazione, extraHttpRequestParams?: any): Observable<Comunicazione>;

    /**
    * Post Comunicazione di Urgenza
    * 
    * @param body 
    */
    putComunicazioneUrgenza(body?: WrapperComunicazione, extraHttpRequestParams?: any): Observable<number>;

    /**
    * Put Comunicazione vardatore
    * 
    * @param body 
    */
    putComunicazioneVardatore(body?: WrapperComunicazione, extraHttpRequestParams?: any): Observable<Comunicazione>;

    /**
    * Put dati cessazione del rapporto
    * 
    * @param body 
    */
    putDatiCessazione(body?: WrapperComunicazione, extraHttpRequestParams?: any): Observable<Comunicazione>;

    /**
    * Put dati proroga del rapporto
    * 
    * @param body 
    */
    putDatiProroga(body?: WrapperComunicazione, extraHttpRequestParams?: any): Observable<Comunicazione>;

    /**
    * Put dati trasferimento e distacco
    * 
    * @param body 
    */
    putDatiTrasferimentoDistacco(body?: WrapperComunicazione, extraHttpRequestParams?: any): Observable<Comunicazione>;

    /**
    * Put dati trasformazione del rapporto
    * 
    * @param body 
    */
    putDatiTrasformazione(body?: WrapperComunicazione, extraHttpRequestParams?: any): Observable<Comunicazione>;

    /**
    * Post Rapporto Missione
    * 
    * @param body 
    */
    putRapportoMissione(body?: WrapperRapporto, extraHttpRequestParams?: any): Observable<Rapporto>;

    /**
    * Put Tutore
    * 
    * @param body 
    */
    putTutore(body?: Comunicazione, extraHttpRequestParams?: any): Observable<Tutore>;

    /**
    * Rettifica Comunicazione
    * 
    * @param body 
    */
    rettificaComunicazione(body?: WrapperComunicazione, extraHttpRequestParams?: any): Observable<Comunicazione>;

    /**
    * Rettifica Comunicazione
    * 
    * @param body 
    */
    rettificaComunicazioneVardatore(body?: WrapperComunicazione, extraHttpRequestParams?: any): Observable<Comunicazione>;

    /**
    * Ritrasmissioni commax
    * 
    */
    ritrasmettiComunicazioni(extraHttpRequestParams?: any): Observable<string>;

    /**
    * Comunicazione
    * 
    * @param id 
    * @param operatoreProvinciale 
    */
    stampaComunicazioneById(id: number, operatoreProvinciale: boolean, extraHttpRequestParams?: any): Observable<Blob>;

    /**
    * stampa ricerca comunicazioni
    * 
    * @param body 
    */
    stampaRicercaComunicazioni(body?: FormRicercaComunicazione, extraHttpRequestParams?: any): Observable<Blob>;

    /**
    * stampa ricerca comunicazioni vardatori
    * 
    * @param body 
    */
    stampaRicercaComunicazioniVardatori(body?: FormRicercaComunicazione, extraHttpRequestParams?: any): Observable<Blob>;

    /**
    * Upload per verifica file
    * 
    * @param attachment 
    * @param email 
    * @param nomeFile 
    * @param isVerifica 
    * @param ilRuolo 
    * @param codiceFiscaleUtente 
    * @param dsCognome 
    * @param dsNome 
    * @param codiceFiscaleAzienda 
    * @param consulenteRespo 
    * @param isAmministratore 
    * @param isOperatoreProvinciale 
    * @param isDelegatoRespo 
    * @param isLegaleRappresentante 
    * @param isPersonaAutorizzata 
    * @param emailRuolo 
    */
    uploadComunicazioni(attachment: Blob, email: string, nomeFile: string, isVerifica: boolean, ilRuolo: string, codiceFiscaleUtente: string, dsCognome: string, dsNome: string, codiceFiscaleAzienda: string, consulenteRespo: boolean, isAmministratore: boolean, isOperatoreProvinciale: boolean, isDelegatoRespo: boolean, isLegaleRappresentante: boolean, isPersonaAutorizzata: boolean, emailRuolo?: string, extraHttpRequestParams?: any): Observable<string>;

    /**
    * 
    * 
    * @param body 
    */
    verificaDatore1(body?: WrapperComunicazione, extraHttpRequestParams?: any): Observable<Datore>;

}
