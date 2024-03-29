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


export interface RicercaVardatori { 
    annoProtCom?: number;
    codiceComunicazioneReg?: string;
    codiceFiscaleDatore?: string;
    codiceFiscaleLavoratore?: string;
    codiceFiscaleSoggetto?: string;
    cognomeLavoratore?: string;
    dsComTStatoComunicazione?: string;
    dsComTTipoComunicazione?: string;
    dsDenominazioneDatore?: string;
    dtEvento?: Date;
    dtInizioRapporto?: Date;
    dtInsert?: Date;
    dtInvio?: Date;
    dtTrasferimento?: Date;
    idComDComunicazione?: number;
    idComDDatore?: number;
    idComDLavoratore?: number;
    idComTProvincia?: number;
    idComTStatoComunicazione?: number;
    idComTTipoComunicazione?: string;
    idComTTipoComunicazioneTu?: number;
    nomeLavoratore?: string;
    numProtCom?: number;
    idTipoTracciato?: string;
    dsDenominazioneDatorePrec?: string;
    codiceFiscaleDatorePrec?: string;
    idComTTipoTrasferimento?: number;
}
