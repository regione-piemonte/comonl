/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { MotivoPermesso } from './../modules/comonlapi/model/motivoPermesso';
import { StatusStraniero } from './../modules/comonlapi/model/statusStraniero';
import { Cittadinanza, ComunicazioneService, Questura, Ruolo } from 'src/app/modules/comonlapi';
import { TIPO_COMUNICAZIONE, TIPO_COMUNICAZIONE_TU, TIPO_TRACCIATO } from '../constants';
import { Atecofin, Comune, Comunicazione, DatiAzienda, Datore, Lavoratore, LavoratoreSilpEspanso, LegaleRappr, Sede, SedeLavoro, StatiEsteri, TipoComunicazione, TipoComunicazioneTu, TipoTracciato } from '../modules/comonlapi';
import { Utils } from '../utils';

export class UtilsComonl {

    // metodo pensato per ottenere un Obj di tipo Datore a partire da un oggetto DatiAzienda ricevuto da SILP
    static getDatoreFromDatiAzienda(datiAzienda: DatiAzienda) {
        // TODO da rimuovere una volta che arriva il LegaleRappr da Silp
        const legaleRappr: LegaleRappr = {
            id: null,
            idLegaleRapprSilp: datiAzienda.ilLegaleRapprDellaSedeLegale ? datiAzienda.ilLegaleRapprDellaSedeLegale.idLegaleRapprSilp : null,
            flgResidenzaItaliana: datiAzienda.ilLegaleRapprDellaSedeLegale ? datiAzienda.ilLegaleRapprDellaSedeLegale.flgResidenzaItaliana : null,
            dsCognome: datiAzienda.ilLegaleRapprDellaSedeLegale ? datiAzienda.ilLegaleRapprDellaSedeLegale.dsCognome : null,
            dsNome: datiAzienda.ilLegaleRapprDellaSedeLegale ? datiAzienda.ilLegaleRapprDellaSedeLegale.dsNome : null,
            dtNascita: datiAzienda.ilLegaleRapprDellaSedeLegale ? datiAzienda.ilLegaleRapprDellaSedeLegale.dtNascita : null,
            sesso: datiAzienda.ilLegaleRapprDellaSedeLegale ? datiAzienda.ilLegaleRapprDellaSedeLegale.sesso : null,
            statiEsteri: {
                id: (datiAzienda.ilLegaleRapprDellaSedeLegale && datiAzienda.ilLegaleRapprDellaSedeLegale.statiEsteri) ? datiAzienda.ilLegaleRapprDellaSedeLegale.statiEsteri.id : null,
                codNazioneMin: (datiAzienda.ilLegaleRapprDellaSedeLegale && datiAzienda.ilLegaleRapprDellaSedeLegale.statiEsteri) ? datiAzienda.ilLegaleRapprDellaSedeLegale.statiEsteri.codNazioneMin : null,
                dsStatiEsteri: (datiAzienda.ilLegaleRapprDellaSedeLegale && datiAzienda.ilLegaleRapprDellaSedeLegale.statiEsteri) ? datiAzienda.ilLegaleRapprDellaSedeLegale.statiEsteri.dsStatiEsteri : null
            } as StatiEsteri,
            comune: {
                id: (datiAzienda.ilLegaleRapprDellaSedeLegale && datiAzienda.ilLegaleRapprDellaSedeLegale.comune) ? datiAzienda.ilLegaleRapprDellaSedeLegale.comune.id : null,
                codComuneMin: (datiAzienda.ilLegaleRapprDellaSedeLegale && datiAzienda.ilLegaleRapprDellaSedeLegale.comune) ? datiAzienda.ilLegaleRapprDellaSedeLegale.comune.codComuneMin : null,
                dsComTComune: (datiAzienda.ilLegaleRapprDellaSedeLegale && datiAzienda.ilLegaleRapprDellaSedeLegale.comune) ? datiAzienda.ilLegaleRapprDellaSedeLegale.comune.dsComTComune : null
            } as Comune,
            cittadinanza: {
                id: (datiAzienda.ilLegaleRapprDellaSedeLegale && datiAzienda.ilLegaleRapprDellaSedeLegale.cittadinanza) ? datiAzienda.ilLegaleRapprDellaSedeLegale.cittadinanza.id : null,
                codCittadinanzaMin: (datiAzienda.ilLegaleRapprDellaSedeLegale && datiAzienda.ilLegaleRapprDellaSedeLegale.cittadinanza) ? datiAzienda.ilLegaleRapprDellaSedeLegale.cittadinanza.codCittadinanzaMin : null,
                dsComTCittadinanza: (datiAzienda.ilLegaleRapprDellaSedeLegale && datiAzienda.ilLegaleRapprDellaSedeLegale.cittadinanza) ? datiAzienda.ilLegaleRapprDellaSedeLegale.cittadinanza.dsComTCittadinanza : null,
                flgUe: (datiAzienda.ilLegaleRapprDellaSedeLegale && datiAzienda.ilLegaleRapprDellaSedeLegale.cittadinanza) ? datiAzienda.ilLegaleRapprDellaSedeLegale.cittadinanza.flgUe : null
            } as Cittadinanza,
            statusStraniero: {
                id: (datiAzienda.ilLegaleRapprDellaSedeLegale && datiAzienda.ilLegaleRapprDellaSedeLegale.statusStraniero) ? datiAzienda.ilLegaleRapprDellaSedeLegale.statusStraniero.id : null,
                codStatusMin: (datiAzienda.ilLegaleRapprDellaSedeLegale && datiAzienda.ilLegaleRapprDellaSedeLegale.statusStraniero) ? datiAzienda.ilLegaleRapprDellaSedeLegale.statusStraniero.codStatusMin : null,
                dsComTStatusStraniero: (datiAzienda.ilLegaleRapprDellaSedeLegale && datiAzienda.ilLegaleRapprDellaSedeLegale.statusStraniero) ? datiAzienda.ilLegaleRapprDellaSedeLegale.statusStraniero.dsComTStatusStraniero : null,
            } as StatusStraniero,
            numeroDocumento: datiAzienda.ilLegaleRapprDellaSedeLegale ? datiAzienda.ilLegaleRapprDellaSedeLegale.numeroDocumento : null,
            dtScadenzaPermessoSogg: datiAzienda.ilLegaleRapprDellaSedeLegale ? datiAzienda.ilLegaleRapprDellaSedeLegale.dtScadenzaPermessoSogg : null,
            motivoPermesso: {
                id: (datiAzienda.ilLegaleRapprDellaSedeLegale && datiAzienda.ilLegaleRapprDellaSedeLegale.motivoPermesso) ? datiAzienda.ilLegaleRapprDellaSedeLegale.motivoPermesso.id : null,
                codMotivoMin: (datiAzienda.ilLegaleRapprDellaSedeLegale && datiAzienda.ilLegaleRapprDellaSedeLegale.motivoPermesso) ? datiAzienda.ilLegaleRapprDellaSedeLegale.motivoPermesso.codMotivoMin : null,
                dsComTMotivoPermesso: (datiAzienda.ilLegaleRapprDellaSedeLegale && datiAzienda.ilLegaleRapprDellaSedeLegale.motivoPermesso) ? datiAzienda.ilLegaleRapprDellaSedeLegale.motivoPermesso.dsComTMotivoPermesso : null
            } as MotivoPermesso,
            questura: {
                id: (datiAzienda.ilLegaleRapprDellaSedeLegale && datiAzienda.ilLegaleRapprDellaSedeLegale.questura) ? datiAzienda.ilLegaleRapprDellaSedeLegale.questura.id : null,
                codQuesturaMin: (datiAzienda.ilLegaleRapprDellaSedeLegale && datiAzienda.ilLegaleRapprDellaSedeLegale.questura) ? datiAzienda.ilLegaleRapprDellaSedeLegale.questura.codQuesturaMin : null,
                dsQuestura: (datiAzienda.ilLegaleRapprDellaSedeLegale && datiAzienda.ilLegaleRapprDellaSedeLegale.questura) ? datiAzienda.ilLegaleRapprDellaSedeLegale.questura.dsQuestura : null
            } as Questura,
            flgSoggiornanteItalia: datiAzienda.ilLegaleRapprDellaSedeLegale ? datiAzienda.ilLegaleRapprDellaSedeLegale.flgSoggiornanteItalia : null,
        };
        
        let datore: Datore = {} as Datore;
        datore.id = null;
        datore.idAziendaSilp = Number(datiAzienda.idAziendaSilp);
        datore.codiceFiscale = datiAzienda.cfAzienda;
        datore.partitaIva = datiAzienda.partitaIva;
        datore.dsDenominazioneDatore = datiAzienda.denominazioneDatoreLavoro;
        datore.flgAzArtigiana = datiAzienda.flagArtigiana;
        datore.flgPubAmm = datiAzienda.flgPubblicaAmm;
        // datore.naturaGiuridica = datiAzienda.naturaGiuridica ? datiAzienda.naturaGiuridica.descrizione : "";
        datore.atecofin = new Object() as Atecofin;
        datore.atecofin.id = datiAzienda.atecofin ? datiAzienda.atecofin.id : null;
        datore.atecofin.codAtecofinMin = datiAzienda.atecofin ? datiAzienda.atecofin.codAtecofinMin : null;
        datore.atecofin.dsComTAtecofin = datiAzienda.atecofin ? datiAzienda.atecofin.dsComTAtecofin : null;
        datore.numeroIscrizioneAlbo = datiAzienda.codLavoroTemp;
        datore.legaleRappr = legaleRappr;
        let sedeLegale: SedeLavoro = new Object();
        const sedeLegaleSilp: Sede = datiAzienda.sedeLegale;
        if (sedeLegaleSilp) {
            sedeLegale.idSedeSilp = Number(sedeLegaleSilp.idSedeSilp);
            sedeLegale.flgSedeLegale = sedeLegaleSilp.sedeLegale ? 'S' : 'N';
            sedeLegale.statiEsteri = sedeLegaleSilp.statiEsteri ? sedeLegaleSilp.statiEsteri : {} as StatiEsteri ;
            sedeLegale.comune = sedeLegaleSilp.comune ? sedeLegaleSilp.comune : {} as Comune;
            sedeLegale.codCap = sedeLegaleSilp.cap;
            let indirizzo: string;
            if (sedeLegaleSilp.toponimoSede) {
                indirizzo = sedeLegaleSilp.toponimoSede.dsComTToponimo;
            }
            if (sedeLegaleSilp.indirizzo) {
                indirizzo = indirizzo ? (indirizzo + ' ' + sedeLegaleSilp.indirizzo) : sedeLegaleSilp.indirizzo;
            }
            if (sedeLegaleSilp.numeroCivico) {
                indirizzo = indirizzo ? (indirizzo + ' ' + sedeLegaleSilp.numeroCivico) : sedeLegaleSilp.numeroCivico;
            }

            sedeLegale.indirizzo = indirizzo;
            sedeLegale.telefono = sedeLegaleSilp.telefono;
            sedeLegale.fax = sedeLegaleSilp.fax;
            sedeLegale.email = sedeLegaleSilp.email ? sedeLegaleSilp.email : null;
            sedeLegale.codCap = sedeLegaleSilp.cap;
            const sedeOperativa = Utils.clone(sedeLegale);
            datore.sedeLegale = sedeLegale;
            datore.sedeOperativa = sedeOperativa;
            datore.sedeOperativa.flgSedeLegale = 'N';

        }
        const naturaGiuridica = datiAzienda.naturaGiuridica;
        if (naturaGiuridica) {
            datore.naturaGiuridica = naturaGiuridica;
        }

        return datore;
    }

    static getLavoratoreFromLavoratoreSilpEspanso(lavoratoreSilp: LavoratoreSilpEspanso): Lavoratore {
        let lavoratore: Lavoratore;
        if (lavoratoreSilp) {
            const str = lavoratoreSilp.dataScadPermSogg;
            let dtScadenzaPermessoSogg: any = lavoratoreSilp.dataScadPermSogg;
            // if(!Utils.isNullOrUndefinedOrCampoVuoto(str)){
            //     const [day, month, year] = str.split('/');
            //     dtScadenzaPermessoSogg = new Date(+year, Number(month) - 1, +day);
            // }
            lavoratore = {
                id: null,
                idLavoratoreSilp: lavoratoreSilp.id ? lavoratoreSilp.id.id : null,
                cognome: lavoratoreSilp.cognome,
                nome: lavoratoreSilp.nome,
                sesso: lavoratoreSilp.sesso,
                dtNascita: lavoratoreSilp.dataNascita,
                comuneNasc: {
                    id: lavoratoreSilp.comuneNascita ? lavoratoreSilp.comuneNascita.id : null,
                    codComuneMin: lavoratoreSilp.comuneNascita ? lavoratoreSilp.comuneNascita.codComuneMin : null,
                    dsComTComune: lavoratoreSilp.comuneNascita ? lavoratoreSilp.comuneNascita.dsComTComune : null,
                },
                comuneDom: {
                    id: lavoratoreSilp.comuneDomicilio ? lavoratoreSilp.comuneDomicilio.id : null ,
                    codComuneMin: lavoratoreSilp.comuneDomicilio ? lavoratoreSilp.comuneDomicilio.codComuneMin : null,
                    dsComTComune: lavoratoreSilp.comuneDomicilio ? lavoratoreSilp.comuneDomicilio.dsComTComune : null
                },
                comuneRes: {
                    id: lavoratoreSilp.comuneResidenza ? lavoratoreSilp.comuneResidenza.id : null ,
                    codComuneMin: lavoratoreSilp.comuneResidenza ? lavoratoreSilp.comuneResidenza.codComuneMin : null,
                    dsComTComune: lavoratoreSilp.comuneResidenza ? lavoratoreSilp.comuneResidenza.dsComTComune : null
                },
                statiEsteriNasc: {
                    id: lavoratoreSilp.statiEsteriNascita ? lavoratoreSilp.statiEsteriNascita.id : null,
                    codNazioneMin: lavoratoreSilp.statiEsteriNascita ? lavoratoreSilp.statiEsteriNascita.codNazioneMin : null,
                    dsStatiEsteri: lavoratoreSilp.statiEsteriNascita ? lavoratoreSilp.statiEsteriNascita.dsStatiEsteri : null,
                },
                cittadinanza: lavoratoreSilp.cittadinanza ? lavoratoreSilp.cittadinanza : null,
                numeroDocumento: lavoratoreSilp.descrNumDocPermSogg,
                dtScadenzaPermessoSogg: dtScadenzaPermessoSogg,
                questura: {
                    id: lavoratoreSilp.questura ? lavoratoreSilp.questura.id : null,
                    dsQuestura: lavoratoreSilp.questura ? lavoratoreSilp.questura.dsQuestura : null,
                    codQuesturaMin: lavoratoreSilp.questura ? lavoratoreSilp.questura.codQuesturaMin : null,

                },
                flgSistAlloggiativa: null,
                flgRimborsoRimpatrio: null,
                statusStraniero: {
                    id: lavoratoreSilp.statusStraniero ? lavoratoreSilp.statusStraniero.id : null,
                    dsComTStatusStraniero: lavoratoreSilp.statusStraniero ? lavoratoreSilp.statusStraniero.dsComTStatusStraniero : null,
                    codStatusMin: lavoratoreSilp.statusStraniero ? lavoratoreSilp.statusStraniero.codStatusMin : null

                },
                statiEsteriRes: {
                    id: lavoratoreSilp.statiEsteriResidenza ? lavoratoreSilp.statiEsteriResidenza.id : null ,
                    codNazioneMin: lavoratoreSilp.statiEsteriResidenza ? lavoratoreSilp.statiEsteriResidenza.codNazioneMin : null,
                    dsStatiEsteri: lavoratoreSilp.statiEsteriResidenza ? lavoratoreSilp.statiEsteriResidenza.dsStatiEsteri : null
                },
                motivoPermesso: {
                    id: lavoratoreSilp.motivoPermesso ? lavoratoreSilp.motivoPermesso.id : null ,
                    dsComTMotivoPermesso: lavoratoreSilp.motivoPermesso ? lavoratoreSilp.motivoPermesso.dsComTMotivoPermesso : null,
                    codMotivoMin: lavoratoreSilp.motivoPermesso ? lavoratoreSilp.motivoPermesso.codMotivoMin : null
                },
                livelloStudio: {
                    id: lavoratoreSilp.livelloStudio ? lavoratoreSilp.livelloStudio.id : null,
                    titoloDiStudio: lavoratoreSilp.livelloStudio ? lavoratoreSilp.livelloStudio.titoloDiStudio : null,
                    codiceLivelloMin: lavoratoreSilp.livelloStudio ? lavoratoreSilp.livelloStudio.codiceLivelloMin : null,
                },
                codCapRes: lavoratoreSilp.capResidenza,
                dsIndirizzoRes: lavoratoreSilp.indirizzoResidenza ? lavoratoreSilp.indirizzoResidenza : 'indirizzo res test',
                codCapDom: lavoratoreSilp.capDomicilio,
                dsIndirizzoDom: lavoratoreSilp.indirizzoDomicilio ? lavoratoreSilp.indirizzoDomicilio : 'indirizzo dom test'
            };

            const toponimoRes = lavoratoreSilp.toponimoResidenza;
            const toponimoDom = lavoratoreSilp.toponimoDomicilio;
            if (toponimoRes) {
                let indirizzo: string;
                indirizzo = toponimoRes.dsComTToponimo;
                if (lavoratoreSilp.indirizzoResidenza) {

                    indirizzo = indirizzo ? indirizzo + ' ' + lavoratoreSilp.indirizzoResidenza : lavoratoreSilp.indirizzoResidenza;
                }
                if (lavoratoreSilp.capResidenza) {
                    indirizzo = indirizzo ? indirizzo + ' ' + (lavoratoreSilp.numCivicoResidenza ? lavoratoreSilp.numCivicoResidenza : '') : lavoratoreSilp.numCivicoResidenza;
                }

                lavoratore.dsIndirizzoRes = indirizzo;
            }
            if (toponimoDom) {
                let indirizzo: string;
                indirizzo = toponimoDom.dsComTToponimo;
                if (lavoratoreSilp.indirizzoDomicilio) {

                    indirizzo = indirizzo ? indirizzo + ' ' + lavoratoreSilp.indirizzoDomicilio : lavoratoreSilp.indirizzoDomicilio;
                }
                if (lavoratoreSilp.capResidenza) {
                    indirizzo = indirizzo ? indirizzo + ' ' + (lavoratoreSilp.numCivicoDomicilio ? lavoratoreSilp.numCivicoDomicilio : '') : lavoratoreSilp.numCivicoDomicilio;
                }

                lavoratore.dsIndirizzoDom = indirizzo;
            }
        }
        return lavoratore;
    }

    // restituisce true se la comunicazione passata come parametro è entro i termini validi per la rettifica, false altrimenti
     static calcoloComunicazioneEntroITermini(comunicazione: Comunicazione): boolean {
         const dtComunicazione: Date = comunicazione.dtTimbroPostale;
         const dtInizioRapporto: Date = comunicazione.rapporto.dtInizioRapporto;
         const tipoComunicazione: TipoComunicazione = comunicazione.tipoComunicazione;
         const tipoTracciato: TipoTracciato = comunicazione.tipoTracciato;
         const tipoComunicazioneTu: TipoComunicazioneTu = comunicazione.tipoComunicazioneTu;
         const flgPubAmm: string = comunicazione.datoreAttuale.flgPubAmm;
         const settoreAteco: Atecofin = comunicazione.datoreAttuale.atecofin;
        const inizialiAteco = settoreAteco.codAtecofinMin.substring(0, 2);

         let dtRiferimento: Date; // dipende dal tipo di comunicazione
         // RIGA 2 TABELLA comunicazioni “fuori termine”
         if (
            tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID &&
            tipoComunicazione.id === TIPO_COMUNICAZIONE.ASSUNZIONE.ID &&
            inizialiAteco === '85' &&
            flgPubAmm === 'S'
         ) {
            dtRiferimento = dtInizioRapporto;
         } else if (
            tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID &&
            tipoComunicazione.id === TIPO_COMUNICAZIONE.ASSUNZIONE.ID &&
            flgPubAmm === 'S'
           ) {
               dtRiferimento = new Date(dtInizioRapporto.getFullYear(), dtInizioRapporto.getMonth(), 1);
           } else if (
            tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID &&
            tipoComunicazioneTu.codTipoComunicazioneMin === TIPO_COMUNICAZIONE_TU.COD_MIN_09
           ) {
               dtRiferimento = dtInizioRapporto;
           } else if (
            tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID &&
            tipoComunicazione.id === TIPO_COMUNICAZIONE.ASSUNZIONE.ID &&
            comunicazione.flgComunSegUrgenza === 'S' &&
            flgPubAmm === 'N'
           ) {
               dtRiferimento = dtInizioRapporto;
           } else if (
             tipoTracciato.id === TIPO_TRACCIATO.UNILAV.ID &&
             tipoComunicazione.id === TIPO_COMUNICAZIONE.ASSUNZIONE.ID
            ) {
                dtRiferimento = dtInizioRapporto;
            }

            return null;
     }

     static getSedeLavoroFromSede(sede: Sede): any {
         let sedeLavoro: any = new Object();
         sedeLavoro.id = null;
         if (sede) {
            sedeLavoro.idSedeSilp  = Number(sede.idSedeSilp);
            sedeLavoro.codCap = sede.cap;
            sedeLavoro.email = sede.email;
            sedeLavoro.fax = sede.fax;
            sedeLavoro.toponimoSede = sede.toponimoSede;
            let dsToponimoSede: string = '';
            if( sedeLavoro.toponimoSede && sedeLavoro.toponimoSede.dsComTToponimo){
                dsToponimoSede = sedeLavoro.toponimoSede.dsComTToponimo;
            }
            let numeroCivico: string = '';
            sedeLavoro.numeroCivico = sede.numeroCivico;
            if(sedeLavoro.numeroCivico){
                numeroCivico = sedeLavoro.numeroCivico;
            }

            sedeLavoro.indirizzo = dsToponimoSede+' '+sede.indirizzo+' '+numeroCivico;
            sedeLavoro.telefono = sede.telefono;
            sedeLavoro.comune = {
                id: sede.comune ? sede.comune.id : null,
                codComuneMin: sede.comune ? sede.comune.codComuneMin : null,
                dsComTComune: sede.comune ? sede.comune.dsComTComune : null,
                provincia: sede.comune ? sede.comune.provincia : null,
                cpi: sede.comune.cpi ? sede.comune.cpi : null
            };
            sedeLavoro.statiEsteri = {
                id: sede.statiEsteri ? sede.statiEsteri.id : null,
                codNazioneMin: sede.statiEsteri ? sede.statiEsteri.codNazioneMin : null,
                dsStatiEsteri: sede.statiEsteri ? sede.statiEsteri.dsStatiEsteri : null
            };
            sedeLavoro.flgSedeLegale = sede.sedeLegale ? 'S' : 'N';
            sedeLavoro.patInail = sede.patInail;
         }

        return sedeLavoro;
     }

}

