/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - EJB submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.ejb.business.be.controlli;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.util.controlli.CodiceFiscale;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.LegaleRappr;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cittadinanza;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloStudio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.MotivoPermesso;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Questura;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatusStraniero;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;

public class ValidatorLavoratore extends Validator<Comunicazione> {
	
	private static final Date DATE_DUEMILA = Date
			.from((LocalDate.of(2000, 1, 1)).atStartOfDay(ZoneId.systemDefault()).toInstant());
	private static final Date DATE_NOVECENTO = Date
			.from((LocalDate.of(1900, 1, 1)).atStartOfDay(ZoneId.systemDefault()).toInstant());
	private static final Date DATE_CARTA_PERMANENTE = Date
			.from((LocalDate.of(2099, 12, 31)).atStartOfDay(ZoneId.systemDefault()).toInstant());

	protected final ControlliDad controlliDad;
	
	public ValidatorLavoratore(Comunicazione controlObject, ControlliDad controlliDad) {
		super(controlObject);
		this.controlliDad = controlliDad;
	}
	public ValidatorLavoratore(Comunicazione controlObject, ControlliDad controlliDad, boolean checkDatiEssenziali, Comunicazione dbObject) {
		super(controlObject, checkDatiEssenziali, dbObject);
		this.controlliDad = controlliDad;
	}	
	
	private boolean isAssunzione;

	@Override
	public boolean isOk() {
		
		isAssunzione = controlObject.getTipoComunicazione()!=null && ComonlConstants.TIPO_COMUNICAZIONE_ASSUNZIONE.equalsIgnoreCase(controlObject.getTipoComunicazione().getId());
		
		Lavoratore lavoratoreDb = dbObject!=null ? dbObject.getLavoratori().get(0) : null;

		for (Lavoratore lavoratore : controlObject.getLavoratori()) {
			if (!isAssunzione) {
				pulisciModelloQ(lavoratore);
			}
			isOkLavoratore(lavoratore,lavoratoreDb);
		}
		
		if (controlObject.getLavoratoreCoObbligato()!=null) {
			if (!isAssunzione) {
				pulisciModelloQ(controlObject.getLavoratoreCoObbligato());
			}
			lavoratoreDb = dbObject!=null ? dbObject.getLavoratoreCoObbligato() : null;
			isOkLavoratore(controlObject.getLavoratoreCoObbligato(), lavoratoreDb);
		}

		return !isInError();
	}

	private void isOkLavoratore(Lavoratore controlObject, Lavoratore dbObject) {

		if (controlObject == null) {
			addApiError(MsgComonl.COMLAVE001.getError());
			return;
		}
		
		boolean skipControlResidenza = tipoProvenienza!=null && ComonlConstants.TIPO_PROVENIENZA_COMMAX.equalsIgnoreCase(tipoProvenienza.getCodTipoProvenienzaMin());
		
		checkDatiEssenziali(controlObject, dbObject);

		// anagrafici
		checkNotEmpty(controlObject.getCodiceFiscale(), "codice fiscale");
		checkNotEmpty(controlObject.getCognome(), "cognome");
		checkNotEmpty(controlObject.getNome(), "nome");
		checkModel(controlObject.getCittadinanza(), "cittadinanza");
		if ( !skipControlResidenza &&
				ComonlUtility.isVoid(controlObject.getStatiEsteriRes())) {
			checkModel(controlObject.getComuneRes(), "comune residenza");
			checkNotEmpty(controlObject.getDsIndirizzoRes(), "indirizzo residenza");
			checkNotEmpty(controlObject.getCodCapRes(), "CAP residenza");
		}
		checkModel(controlObject.getComuneDom(), "comune domicilio");
		checkNotEmpty(controlObject.getDsIndirizzoDom(), "indirizzo domicilio");
		checkNotEmpty(controlObject.getCodCapDom(), "CAP domicilio");
		checkNotNull(controlObject.getDtNascita(), "data nascita");
		checkNotEmpty(controlObject.getSesso(), "sesso");
		checkModel(controlObject.getLivelloStudio(), "livello di istruzione");

		if (ComonlUtility.isVoid(controlObject.getComuneNasc()) && ComonlUtility.isVoid(controlObject.getStatiEsteriNasc())) {
			checkNotEmpty(null, "comune nascita o stato estero di nascita");
		}
		if (!skipControlResidenza &&
				(ComonlUtility.isVoid(controlObject.getComuneRes()) && ComonlUtility.isVoid(controlObject.getStatiEsteriRes()))
			) {
			checkNotEmpty(null, "comune residenza o stato estero di residenza");
		}
		
		// CHECK DECODING VALID
		if(!isModelNull(controlObject.getStatiEsteriRes())) {
			checkDecodingValid(controlliDad.getStatiEsteriValid(controlObject.getStatiEsteriRes(), new Date()), "Stato estero residenza", controlObject.getStatiEsteriRes().getCodNazioneMin(), controlObject.getStatiEsteriRes().getDsStatiEsteri());
		}
		if(!isModelNull(controlObject.getComuneRes())) {
			controlObject.getComuneRes().setId(null);//forzo la ricerca per codiceMinisteriale
			Comune comune = checkComune(controlObject.getComuneRes(),"Comune residenza"); 
			if (comune!=null) {
				controlObject.setComuneRes(comune);
			}
		}
		if(!isModelNull(controlObject.getStatiEsteriDom())) {
			checkDecodingValid(controlliDad.getStatiEsteriValid(controlObject.getStatiEsteriDom(), new Date()), "Stato estero domicilio", controlObject.getStatiEsteriDom().getCodNazioneMin(), controlObject.getStatiEsteriDom().getDsStatiEsteri());
		}
		if(!isModelNull(controlObject.getComuneDom())) {
			controlObject.getComuneDom().setId(null);//forzo la ricerca per codiceMinisteriale
			Comune comune = checkComune(controlObject.getComuneDom(),"Comune domicilio"); 
			if (comune!=null) {
				controlObject.setComuneDom(comune);
			}
		}
		if(!isModelNull(controlObject.getCittadinanza())) {
			checkDecodingValid(controlliDad.getTfromMin(Cittadinanza.class,controlObject.getCittadinanza().getCodCittadinanzaMin(), new Date()), "Cittadinanza", controlObject.getCittadinanza().getCodCittadinanzaMin(), controlObject.getCittadinanza().getDsComTCittadinanza());
		}
		if(!isModelNull(controlObject.getMotivoPermesso())) {
			checkDecodingValid(controlliDad.getTfromMin(MotivoPermesso.class,controlObject.getMotivoPermesso().getCodMotivoMin(), new Date()), "Motivo del permesso/provvedimento", controlObject.getMotivoPermesso().getCodMotivoMin(), controlObject.getMotivoPermesso().getDsComTMotivoPermesso());
		}
		if(!isModelNull(controlObject.getQuestura())) {
			checkDecodingValid(controlliDad.getTfromMin(Questura.class,controlObject.getQuestura().getCodQuesturaMin(), new Date()), "Questura di rilascio", controlObject.getQuestura().getCodQuesturaMin(), controlObject.getQuestura().getDsQuestura());
		}
		if(!isModelNull(controlObject.getLivelloStudio())) {
			checkDecodingValid(controlliDad.getTfromMin(LivelloStudio.class,controlObject.getLivelloStudio().getCodiceLivelloMin(), new Date()), "Livello di istruzione", controlObject.getLivelloStudio().getCodiceLivelloMin(), controlObject.getLivelloStudio().getDsTitolo());
		}
		
		if (isInError()) {
			return;
		}

		// controlli consistenza

		// cittadinanza
		if (controlObject.getCittadinanza().getFlgUe().equals(ComonlConstants.FLAG_N)) { // extra UE
			if (controlObject.getFlgSistAlloggiativa()!=null && !controlObject.getFlgSistAlloggiativa().equals(controlObject.getFlgRimborsoRimpatrio())) {
				addError(MsgComonl.COMLAVE003.getError());
			}
		} else {
			if (ComonlUtility.isNotVoid(controlObject.getFlgSistAlloggiativa())
					&& ComonlUtility.isNotVoid(controlObject.getFlgRimborsoRimpatrio())
					&& (controlObject.getFlgSistAlloggiativa().equals(ComonlConstants.FLAG_S)
							|| controlObject.getFlgRimborsoRimpatrio().equals(ComonlConstants.FLAG_S))) {

				addError(MsgComonl.COMLAVE004.getError());
			}
		}

		if (isInError()) {
			return;
		}
		
		chekModelloQ(controlObject);
		if (isInError()) {
			return;
		}

		if (ComonlUtility.isNotVoid(controlObject.getComuneNasc())
				&& ComonlUtility.isNotVoid(controlObject.getStatiEsteriNasc())) {
			addError(MsgComonl.COMLAVE005.getError());
			return;
		}

		if(controlObject.getCodiceFiscale().length() != 16) { // controlla CF provvisorio
			if (!CodiceFiscale.getInstance().controllaPartitaIVA(controlObject.getCodiceFiscale())){ 
				addError(MsgComonl.COMLAVE006.getError());
			}
			
		} else {
			
			String luogoNascita = null;
			if(controlObject.getComuneNasc() != null && StringUtils.isNotBlank(controlObject.getComuneNasc().getCodComuneMin())) {
				luogoNascita = controlObject.getComuneNasc().getCodComuneMin();
			}
			if(controlObject.getStatiEsteriNasc() != null && StringUtils.isNotBlank(controlObject.getStatiEsteriNasc().getCodNazioneMin())) {
				luogoNascita = controlObject.getStatiEsteriNasc().getCodNazioneMin();
			}
			if (!CodiceFiscale.getInstance().checkCF(controlObject.getCognome(), controlObject.getNome(),
					ComonlUtility.convertiDataInStringa(controlObject.getDtNascita()), controlObject.getSesso(),
					luogoNascita, controlObject.getCodiceFiscale())) {
				addError(MsgComonl.COMLAVE006.getError());
			}
		}

		chkTitoloSoggiorno(controlObject);
		
	}

	private void chkTitoloSoggiorno(Lavoratore controlObject) {

		if (controlObject.getCittadinanza().getFlgUe().equals(ComonlConstants.FLAG_N)) {

			if (controlObject.getStatusStraniero() == null || ComonlUtility.isVoid(controlObject.getStatusStraniero().getCodStatusMin())) {
				addError(MsgComonl.COMLAVE011.getError());
			}
			if (ComonlUtility.isVoid(controlObject.getMotivoPermesso())) {
				addError(MsgComonl.COMLAVE012.getError());
			}
			if (ComonlUtility.isVoid(controlObject.getDtScadenzaPermessoSogg())) {
				addError(MsgComonl.COMLAVE013.getError());
			} 
		} else {

			if (controlObject.getStatusStraniero() != null && ComonlUtility.isNotVoid(controlObject.getStatusStraniero().getCodStatusMin())) {
				addError(MsgComonl.COMLAVE015.getError());
			}
			if (ComonlUtility.isNotVoid(controlObject.getMotivoPermesso())) {
				addError(MsgComonl.COMLAVE016.getError());
			}
			if (ComonlUtility.isNotVoid(controlObject.getDtScadenzaPermessoSogg())) {
				addError(MsgComonl.COMLAVE017.getError());
			}
// FIXME getNumeroTitoloDiSoggiorno			

			if (ComonlUtility.isNotVoid(controlObject.getQuestura())) {
				addError(MsgComonl.COMLAVE018.getError());
			}
		}
// ??? 
//		if (ComonlUtility.isNotVoid(controlObject.getQuestura())
//				&& ComonlUtility.isNotVoid(controlObject.getStatusStraniero().getCodStatusMin())) {
//			if (controlObject.getStatusStraniero().getCodStatusMin()
//					.equalsIgnoreCase(ComonlConstants.TIPO_DOCUMENTO_PERMESSO)
//					|| controlObject.getStatusStraniero().getCodStatusMin()
//							.equalsIgnoreCase(ComonlConstants.TIPO_DOCUMENTO_CARTA)
//					|| controlObject.getStatusStraniero().getCodStatusMin()
//							.equalsIgnoreCase(ComonlConstants.TIPO_DOCUMENTO_CARTA_PERMANENTE)
//					|| controlObject.getStatusStraniero().getCodStatusMin()
//							.equalsIgnoreCase(ComonlConstants.TIPO_DOCUMENTO_ALTRO_PROVVEDIMENTO)
//					|| controlObject.getStatusStraniero().getCodStatusMin().equalsIgnoreCase(
//							ComonlConstants.TIPO_DOCUMENTO_PERMESSO_DI_SOGGIORNO_CE_PER_SOGGIORNANTI_DI_LUNGO_PERIODO)) {
//
//			}
//			addError(MsgComonl.COMLAVE019.getError());
//		}
		if (ComonlUtility.isNotVoid(controlObject.getMotivoPermesso())) {
			if (ComonlUtility.isNotVoid(controlObject.getDtScadenzaPermessoSogg())) {
				if (controlObject.getStatusStraniero() != null && ComonlUtility.isNotVoid(controlObject.getStatusStraniero().getCodStatusMin())) {
					if (!controlObject.getStatusStraniero().getCodStatusMin()
							.equalsIgnoreCase(ComonlConstants.TIPO_DOCUMENTO_ATTESA_PERMESSO)
						) {
						if (compareTo(DATE_DUEMILA, controlObject.getDtScadenzaPermessoSogg())>0) {
							addError(MsgComonl.COMLAVE020.getError());
						}

					} else if (controlObject.getStatusStraniero().getCodStatusMin()
							.equalsIgnoreCase(ComonlConstants.TIPO_DOCUMENTO_ATTESA_PERMESSO)) {
						if (compareTo(DATE_NOVECENTO, controlObject.getDtScadenzaPermessoSogg())!=0) {
							addError(MsgComonl.COMLAVE021.getError());
						}
					} else if (controlObject.getStatusStraniero().getCodStatusMin()
							.equalsIgnoreCase(ComonlConstants.TIPO_DOCUMENTO_CARTA_PERMANENTE)) {
						if (compareTo(DATE_CARTA_PERMANENTE,controlObject.getDtScadenzaPermessoSogg())!=0) {
							addError(MsgComonl.COMLAVE022.getError());
						}
					}
				}
			}
		}
		if (controlObject.getStatusStraniero() != null && ComonlUtility.isNotVoid(controlObject.getStatusStraniero().getCodStatusMin())) {

			if (ComonlUtility.isVoid(controlObject.getQuestura())) {
				if (controlObject.getStatusStraniero().getCodStatusMin()
						.equalsIgnoreCase(ComonlConstants.TIPO_DOCUMENTO_PERMESSO)
						|| controlObject.getStatusStraniero().getCodStatusMin()
								.equalsIgnoreCase(ComonlConstants.TIPO_DOCUMENTO_CARTA_PERMANENTE)
						|| controlObject.getStatusStraniero().getCodStatusMin().equalsIgnoreCase(
								ComonlConstants.TIPO_DOCUMENTO_PERMESSO_DI_SOGGIORNO_CE_PER_SOGGIORNANTI_DI_LUNGO_PERIODO)) {
					addError(MsgComonl.COMLAVE023.getError());
				}

			} else {
				if (controlObject.getCittadinanza().getFlgUe().equals(ComonlConstants.FLAG_N)
						&& (controlObject.getStatusStraniero().getCodStatusMin()
								.equalsIgnoreCase(ComonlConstants.TIPO_DOCUMENTO_IN_RINNOVO)
								|| controlObject.getStatusStraniero().getCodStatusMin()
										.equalsIgnoreCase(ComonlConstants.TIPO_DOCUMENTO_ATTESA_PERMESSO)
								|| controlObject.getStatusStraniero().getCodStatusMin()
										.equalsIgnoreCase(ComonlConstants.TIPO_DOCUMENTO_ALTRO_PROVVEDIMENTO))) {
					addError(MsgComonl.COMLAVE024.getError());
				}
			}
		}
	}

//	private boolean isInError() {
//		return this.apiErrors!=null && this.apiErrors.size() > 0;
//	}

	private void addError(ApiError error) {
		addApiError(error);
	}
	
	private void checkDatiEssenziali(Lavoratore controlObject, Lavoratore dbObject) {
		if (!checkDatiEssenziali)
			return;
		checkDatoEssenziale(controlObject.getCodiceFiscale(), dbObject.getCodiceFiscale(), "codice fiscale");
		if(controlObject.getCittadinanza().getFlgUe().equals(ComonlConstants.FLAG_N)) {
			StatusStraniero statusStranieroValido = controlliDad.getStatusStranieroValid(dbObject.getStatusStraniero(), new Date());
			if (statusStranieroValido != null)
				checkDatoEssenzialeModel(controlObject.getStatusStraniero(),dbObject.getStatusStraniero(),"numero titolo soggiorno");
			checkDatoEssenzialeDate(controlObject.getDtScadenzaPermessoSogg(),dbObject.getDtScadenzaPermessoSogg(),"scadenza titolo soggiorno");
			checkDatoEssenziale(controlObject.getFlgSistAlloggiativa(),dbObject.getFlgSistAlloggiativa(),"sussitenza della sistema alloggiativa");
			checkDatoEssenziale(controlObject.getFlgRimborsoRimpatrio(),dbObject.getFlgRimborsoRimpatrio(),"Impegno del datore di lavoro al pagamento delle spese per il rimpatrio");
		}
	}
	private Comune checkComune(Comune comune, String field) {
		Comune comuneValido = controlliDad.getComuneValid(comune, new Date());
		checkDecodingValid(comuneValido, field, comune.getCodComuneMin(), comune.getDsComTComune());
		return comuneValido;
	}
	private void pulisciModelloQ(Lavoratore lav) {
		lav.setFlgRimborsoRimpatrio(ComonlConstants.FLAG_N);
		lav.setFlgSistAlloggiativa(ComonlConstants.FLAG_N);
	}
	private void chekModelloQ(Lavoratore controlObject) {
		boolean flgRimborsoRimpatrio = ComonlConstants.FLAG_S.equals(controlObject.getFlgRimborsoRimpatrio());
		boolean flgSistAlloggiativa = ComonlConstants.FLAG_S.equals(controlObject.getFlgSistAlloggiativa());
		LegaleRappr legaleRappr = this.controlObject.getDatoreAttuale()!=null ? this.controlObject.getDatoreAttuale().getLegaleRappr() : null;
		checkCondition(!isAssunzione || !flgRimborsoRimpatrio || !flgSistAlloggiativa || 
				(legaleRappr !=null && (legaleRappr.getId()!=null || legaleRappr.getIdLegaleRapprSilp()!=null)),MsgComonl.COMLAVE025.getError());

	}
}
