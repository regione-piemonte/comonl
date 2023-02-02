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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.controlli.CodiceFiscale;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.AnagraficaAziAccent;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.LegaleRappr;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Atecofin;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cittadinanza;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Provincia;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoProvenienza;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlStringUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;
import it.csi.comonl.comonlweb.lib.util.ControlliSuOggettoComunicazione;
import it.csi.comonl.comonlweb.lib.util.ValidazioneCampi;

public class ValidatorDatore extends Validator<Comunicazione>
{

	protected final ControlliDad controlliDad;
	protected final DecodificaDad decodificaDad;
	protected Datore datore = null;
	boolean skipControl = false; 

	public ValidatorDatore(Comunicazione comunicazione, ControlliDad controlliDad, DecodificaDad decodificaDad, Ruolo ruolo) {
		this(comunicazione, false, null, controlliDad, decodificaDad, ruolo);
	}
	public ValidatorDatore(Comunicazione comunicazione, boolean checkDatiEssenziali, Comunicazione dbComunicazione, ControlliDad controlliDad, DecodificaDad decodificaDad, Ruolo ruolo) {
		super(comunicazione, checkDatiEssenziali, dbComunicazione);
		this.controlliDad = controlliDad;
		this.decodificaDad = decodificaDad;
		this.ruolo = ruolo;
	}

	@Override
	public boolean isOk() {
		
		checkDatiEssenziali();

		datore = controlObject.getDatoreAttuale();
		
//		boolean isAnnullo = ComonlConstants.TIPO_COMUNICAZIONE_ANNULLAMENTO_4_STRING.equalsIgnoreCase(controlObject.getTipoComunicazioneTu().getCodTipoComunicazioneMin());
//		skipControl = isAnnullo && 
//				controlObject.getTipoProvenienza()!=null && 
//				ComonlConstants.ID_TIPO_PROVENIENZA_COMMAX==controlObject.getTipoProvenienza().getId();

		boolean isLunghezzaCampi = checkLunghezzaMaxCampi (datore);
		checkCondition(isLunghezzaCampi, MsgComonl.COMDATE001.getError());

		checkDatiGeneraliAzienda();
		
		checkLegaleRappresentante();
		
		checkAziendaAccentrata(datore);

		checkSedeLegale(datore);
		
		checkSedeOperativa(datore);

		if (ControlliSuOggettoComunicazione.isUNISOMM(controlObject)) {
			checkNotNull(datore.getNumAgenziaSommin(), "Numero Agenzia Somministrazione");

			checkNotNull(datore.getNumeroIscrizioneAlbo(), "Numero Iscrizione all'albo");

			checkCondition(ValidazioneCampi.checkNumIscrizioneAlbo(datore.getNumeroIscrizioneAlbo()), MsgComonl.COMDATE031.getError());
		}

		return (!isInError());
	}
	
	private void checkAziendaAccentrata(Datore datore) {
		boolean isComunicazioneVarDatori = ComonlConstants.TIPO_TRACCIATO_VARDATORI_ID.equalsIgnoreCase(controlObject.getTipoTracciato().getId());
		SedeLavoro sedeLavoro = isComunicazioneVarDatori ? datore.getSedeLegale() : datore.getSedeOperativa();
		
		if (!ruolo.isOperatoreProvinciale() && sedeLavoro !=null && !inPiemonte(sedeLavoro)) {
			String aziendaAccentrata = null;
			AnagraficaAziAccent anagraficaAziAccent = null;
			if (ruolo.isConsulenteRespo()) {
				aziendaAccentrata = controlliDad.getFlgAccentramento(ruolo.getCodiceFiscaleAzienda());
			}else {
				anagraficaAziAccent = controlliDad.getAnagraficaAziAccent(datore.getCodiceFiscale());
			}
			checkCondition(ComonlConstants.FLAG_S.equalsIgnoreCase(aziendaAccentrata) || anagraficaAziAccent != null, MsgComonl.COMDATE052.getError());
		}
	}
	private boolean inPiemonte(SedeLavoro sede) {
		if (sede != null) {
			return (sede.getComune() != null && sede.getComune().getProvincia() != null
					&& sede.getComune().getProvincia().getRegione() != null && ComonlConstants.CODICE_REGIONALE_PIEMONTE
							.equals(sede.getComune().getProvincia().getRegione().getCodRegioneMin()));
		}
		return false;
	}
	
	private void checkSedeLegale(Datore datore) {
		boolean isComunicazioneUniLav = ControlliSuOggettoComunicazione.getInstance().isUNILAV(controlObject);
		boolean isComunicazioneVarDatori = ControlliSuOggettoComunicazione.getInstance().isVARDATORI(controlObject);
		
		SedeLavoro sedeLegale = datore.getSedeLegale();

		//72160-1262: se il tracciato e' un UNILAV  ed e' stata selezionata una sede con stato esterto --> BLOCCO
		if ( (isComunicazioneUniLav || isComunicazioneVarDatori)
				&& ( sedeLegale.getStatiEsteri() != null 
				&& sedeLegale.getStatiEsteri().getCodNazioneMin() != null 
				&& !"".equalsIgnoreCase(sedeLegale.getStatiEsteri().getCodNazioneMin() ) )
				) {

			addApiError(new ApiError(MsgComonl.COMDATE053.getCode(), MsgComonl.COMDATE053.getMessage()));

			return;
		}
		if (sedeLegale.getComune() != null && StringUtils.isNotBlank(sedeLegale.getComune().getCodComuneMin())){
			Comune comune = (Comune) decodificaDad.getTfromMin(Comune.class, sedeLegale.getComune().getCodComuneMin(), null);
			if (comune == null) {
				addApiError(new ApiError(MsgComonl.COMDATE049.getCode(), MsgComonl.COMDATE049.getMessage()));
			}else {
				sedeLegale.setComune(comune);
			}
		}
	}
	
	private void checkSedeOperativa(Datore datore) {


		boolean msgMutuaEsistenzaStatoEsteroComuneProvCap = false;

		boolean isComunicazioneUniSomm = ControlliSuOggettoComunicazione.getInstance().isUNISOMM(controlObject);
		boolean isComunicazioneUniLav = ControlliSuOggettoComunicazione.getInstance().isUNILAV(controlObject);
		boolean isComunicazioneVarDatori = ControlliSuOggettoComunicazione.getInstance().isVARDATORI(controlObject);
		boolean isAssunzione = ControlliSuOggettoComunicazione.getInstance().isASSUNZIONE(controlObject);

		SedeLavoro sedeOperativa = datore.getSedeOperativa();

		//72160-1262: se il tracciato e' un UNILAV - ASSUNZIONE ed e' stata selezionata una sede con stato esterto --> BLOCCO
		if (isComunicazioneUniLav && isAssunzione 
				&& ( sedeOperativa.getStatiEsteri() != null 
				&& sedeOperativa.getStatiEsteri().getCodNazioneMin() != null 
				&& !"".equalsIgnoreCase(sedeOperativa.getStatiEsteri().getCodNazioneMin() ) )
				) {

			addApiError(new ApiError(MsgComonl.COMDATE032.getCode(), MsgComonl.COMDATE032.getMessage()));

			return;
		}

		//TUTTI I CONTROLLI SE almeno uno dei campi della sede operativa e' valorizzato
		if (ComonlUtility.isNotVoid(sedeOperativa.getStatiEsteri())
				|| (ComonlUtility.isNotVoid(sedeOperativa.getComune()) && ComonlUtility.isNotVoid(sedeOperativa.getComune().getProvincia())) 
				|| ComonlUtility.isNotVoid(sedeOperativa.getCodCap())
				|| ComonlUtility.isNotVoid(sedeOperativa.getComune())
				|| ComonlUtility.isNotVoid(sedeOperativa.getIndirizzo())
				|| ComonlUtility.isNotVoid(sedeOperativa.getTelefono())
				|| ComonlUtility.isNotVoid(sedeOperativa.getFax())
				|| ComonlUtility.isNotVoid(sedeOperativa.getEmail())) {

			// se lo stato estero e' valorizzato e lo sono anche uno tra cap - prov - comune:
			// "Attenzione! Non &egrave; consentito valorizzare insieme lo stato estero ed il gruppo provincia - comune - CAP per la sede operativa."
			if(ComonlUtility.isNotVoid(sedeOperativa.getStatiEsteri()) 
					&& ( (ComonlUtility.isNotVoid(sedeOperativa.getComune()) && ComonlUtility.isNotVoid(sedeOperativa.getComune().getProvincia())) 
							|| ComonlUtility.isNotVoid(sedeOperativa.getComune()) 
							|| ComonlUtility.isNotVoid(sedeOperativa.getCodCap()))) {

				addApiError(new ApiError(MsgComonl.COMDATE033.getCode(), MsgComonl.COMDATE033.getMessage()));

				return;
			}
			else if (
					(sedeOperativa.getStatiEsteri() == null || sedeOperativa.getStatiEsteri().getCodNazioneMin() == null || "".equalsIgnoreCase(sedeOperativa.getStatiEsteri().getCodNazioneMin())) 
					&&
					( (sedeOperativa.getComune() != null) && (sedeOperativa.getComune().getProvincia() == null || sedeOperativa.getComune().getProvincia().getCodProvinciaMin() == null || "".equalsIgnoreCase(sedeOperativa.getComune().getProvincia().getCodProvinciaMin()) )) 
					&&
					(sedeOperativa.getComune() == null || sedeOperativa.getComune().getCodComuneMin() == null || "".equalsIgnoreCase(sedeOperativa.getComune().getCodComuneMin()))
					) {

				if ( (sedeOperativa.getIndirizzo()!= null && !sedeOperativa.getIndirizzo().equals("")) ||
						(sedeOperativa.getCodCap() != null && !sedeOperativa.getCodCap().equals("")) ||
						(sedeOperativa.getTelefono() != null && !sedeOperativa.getTelefono().equals("")) ||
						(sedeOperativa.getFax() != null && !sedeOperativa.getFax().equals("")) ||
						(sedeOperativa.getEmail() != null && !sedeOperativa.getEmail().equals(""))) {

					addApiError(new ApiError(MsgComonl.COMDATE034.getCode(), MsgComonl.COMDATE034.getMessage()));
					msgMutuaEsistenzaStatoEsteroComuneProvCap = true;
				}
			}


			//se uno dei campi della sede operativa e' valorizzato, l'indirizzo e' sempre obbligatorio
			checkNotNull(sedeOperativa.getIndirizzo(), "Indirizzo sede operativa");
			checkCondition(sedeOperativa.getIndirizzo() != null && !sedeOperativa.getIndirizzo().trim().equals("") &&
					sedeOperativa.getIndirizzo().length() <= 100, MsgComonl.COMDATE035.getError());


			//c'e' la provincia ma non c'e' il comune -- tolgo questo controllo perche' adesso la provincia e' dentro il comune e non puo' mai succedere
			//			if (actionForm.getCOMBOcodiceProvinciaSedeOperativa() != null && !actionForm.getCOMBOcodiceProvinciaSedeOperativa().trim().equals("") &&
			//				errors.add(ActionErrors.GLOBAL_ERROR,
			//						new ActionError("datoreComuneSedeOperativa",
			//								applicationResources.getMessage("datoreComuneSedeOperativa")));
			//			}


			if (isComunicazioneUniLav ) {
				//UL/VD Cap 
				//Obbligatorio.
				if (!msgMutuaEsistenzaStatoEsteroComuneProvCap) {
					checkCap(sedeOperativa, true);

				}
			} else {
				//non c'e' lo stato estero, ma ci sono comune o provincia, il cap e' un dato obbligatorio

				if (ComonlUtility.isVoid(sedeOperativa.getStatiEsteri()) 
						&& (ComonlUtility.isNotVoid(sedeOperativa.getComune()) || ComonlUtility.isNotVoid(sedeOperativa.getComune().getProvincia()))) {
					checkNotNull(sedeOperativa.getCodCap(), "Cap sede operativa");
				}

				//Il cap della sede operativa NON e' obbligatorio in caso di stato estero della sede operativa valorizzato
				if (controlObject.getDatoreAttuale().getFlgUtilEstera() != null
						&& controlObject.getDatoreAttuale().getFlgUtilEstera().equalsIgnoreCase(ComonlConstants.FLAG_S) 
						&& ComonlUtility.isNotVoid(sedeOperativa.getStatiEsteri()) ) {

					checkCondition(ComonlUtility.isVoid(sedeOperativa.getCodCap()), MsgComonl.COMDATE042.getError());
				}

				//	1 - Se 'Agenzia somministrazione estera' = no, il cap e' obbligatorio. 
				if (controlObject.getDatoreAttuale().getFlgUtilEstera() != null
						&& !controlObject.getDatoreAttuale().getFlgUtilEstera().equalsIgnoreCase(ComonlConstants.FLAG_S)) {
					checkCap(sedeOperativa, true);
				}
			}

			if (isComunicazioneUniSomm) { //SOLO PER UNISOM
				//72160-se lo stato estero della sede operativa e' valorizzato, la nazione DEVE essere UE 
				if (ComonlUtility.isNotVoid(sedeOperativa.getStatiEsteri()) ) {
					boolean trovato = false;
					Cittadinanza cittadinanza = controlliDad.getCittadinanzaByCodMf(sedeOperativa.getStatiEsteri().getCodNazioneMin());
					if (cittadinanza != null && ComonlConstants.FLAG_S.equalsIgnoreCase(cittadinanza.getFlgUe()) ){
						trovato = true;
					}
					checkCondition(trovato, MsgComonl.COMDATE043.getError());
				} 
			}

			//telefono
			if(sedeOperativa.getTelefono() != null && !sedeOperativa.getTelefono().trim().equals("")){
				
				checkCondition(sedeOperativa.getTelefono().length() <= 15 && ValidazioneCampi.getInstance().controllaNumeroTelefonico(sedeOperativa.getTelefono()), MsgComonl.COMDATE044.getError());
			}

			//fax
			if(sedeOperativa.getFax() != null && !sedeOperativa.getFax().trim().equals("")){
				checkCondition(sedeOperativa.getFax().length() <= 15 && ValidazioneCampi.getInstance().controllaNumeroTelefonico(sedeOperativa.getFax()), MsgComonl.COMDATE045.getError());
			}

			//email
			if(sedeOperativa.getEmail() != null && !sedeOperativa.getEmail().trim().equals("")) {
				checkCondition(ValidazioneCampi.getInstance().checkMail(sedeOperativa.getEmail()), MsgComonl.COMDATE046.getError());
			}

			if((ComonlStringUtils.isStringEmpty(sedeOperativa.getTelefono()) &&
					ComonlStringUtils.isStringEmpty(sedeOperativa.getFax()) &&
					ComonlStringUtils.isStringEmpty(sedeOperativa.getEmail()))
					&&
					((sedeOperativa.getComune() != null && !"".equalsIgnoreCase(sedeOperativa.getComune().getCodComuneMin()))
							||
							(sedeOperativa.getStatiEsteri() != null && !"".equalsIgnoreCase(sedeOperativa.getStatiEsteri().getCodNazioneMin()) ) ) ){
				addApiError(new ApiError(MsgComonl.COMDATE047.getCode(),MsgComonl.COMDATE047.getMessage()));
			}


			//SE E' VALORIZZATO lo stato estero e correttamente vuoti prov-comune-cap procede a verificare se lo stato estero e' valido
			if(sedeOperativa.getStatiEsteri() != null 
					&& sedeOperativa.getStatiEsteri().getCodNazioneMin() !=  null && !"".equalsIgnoreCase(sedeOperativa.getStatiEsteri().getCodNazioneMin())  
					&&
					(
							(sedeOperativa.getComune() == null || sedeOperativa.getComune().getCodComuneMin() == null || "".equalsIgnoreCase(sedeOperativa.getComune().getCodComuneMin())) && 
							(sedeOperativa.getComune().getProvincia() == null || sedeOperativa.getComune().getProvincia().getCodProvinciaMin() == null || "".equalsIgnoreCase(sedeOperativa.getComune().getProvincia().getCodProvinciaMin())) &&
							(sedeOperativa.getCodCap() == null || sedeOperativa.getCodCap().trim().equals(""))
					)
			) {

				StatiEsteri stato = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class, sedeOperativa.getStatiEsteri().getCodNazioneMin(), null);
				if (stato == null) {
					addApiError(new ApiError(MsgComonl.COMDATE048.getCode(), MsgComonl.COMDATE048.getCode()));
				}
			}
			else { // VERIFICHE SULLA CORRETTEZZA DELLA VALORIZZAZIONE DI COMUNE-PROVINCIA

				// provincia sede operativa valorizzata dall'operatore, ma codice relativo non presente
				if (sedeOperativa.getComune() != null
						&& sedeOperativa.getComune().getProvincia() != null
						&& sedeOperativa.getComune().getProvincia().getCodProvinciaMin() != null
						&& !"".equalsIgnoreCase(sedeOperativa.getComune().getProvincia().getCodProvinciaMin())) {
					
					Provincia prov = (Provincia) decodificaDad.getTfromMin(Provincia.class, sedeOperativa.getComune().getProvincia().getCodProvinciaMin(), null);
					if (prov == null) {
						addApiError(new ApiError(MsgComonl.COMDATE049.getCode(), MsgComonl.COMDATE049.getMessage()));
					}
				}

				// comune sede operativa valorizzata dall'operatore, ma codice relativo non presente
				if (sedeOperativa.getComune() != null
						&& sedeOperativa.getComune().getCodComuneMin() != null
						&& !"".equalsIgnoreCase(sedeOperativa.getComune().getCodComuneMin())) {
					
					Comune comune = (Comune) decodificaDad.getTfromMin(Comune.class, sedeOperativa.getComune().getCodComuneMin(), null);
					if (comune == null || (comune.getDtFine() != null && comune.getDtFine().before(new Date()))) {
						addApiError(new ApiError(MsgComonl.COMDATE050.getCode(), MsgComonl.COMDATE050.getMessage()));
					}
					// comune sede operativa inserito ma provincia non inserita
					checkCondition(sedeOperativa.getComune().getProvincia() !=null, MsgComonl.COMDATE051.getError());
					if (comune != null) {
						sedeOperativa.setComune(comune);
					}
				}
			}
		}
		//-------------------------------
	}
	private void checkLegaleRappresentante ()  {
		if (datore.getLegaleRappr() != null && datore.getLegaleRappr().getIdLegaleRapprSilp() != null) {

			LegaleRappr legaleRapp = datore.getLegaleRappr();
			checkAllowedValuesFlag(legaleRapp.getCittadinanza().getFlgUe(), "Extra comunitario", false);
			checkAllowedValuesFlag(legaleRapp.getFlgSoggiornanteItalia(), "Soggiornanate Italia", false);


			Boolean soggiornoItalia = (ComonlConstants.FLAG_S.equalsIgnoreCase(legaleRapp.getFlgSoggiornanteItalia()) ? Boolean.TRUE : Boolean.FALSE); 
			Boolean extraComunitario = (ComonlConstants.FLAG_S.equalsIgnoreCase(legaleRapp.getCittadinanza().getFlgUe()) ? Boolean.FALSE : Boolean.TRUE); 

			boolean areDatiAnagraficiegaleRappresentanteCompilati = areDatiAnagraficiegaleRappresentanteCompilati(legaleRapp);

			if (extraComunitario.booleanValue()) {

				checkDatiAngraficiLegaleRappresentante(legaleRapp, areDatiAnagraficiegaleRappresentanteCompilati, extraComunitario,  Boolean.TRUE);
				checkDatiTitoloSoggiornoLegaleRappresentante(legaleRapp,  extraComunitario, Boolean.TRUE);

			} else if (!extraComunitario.booleanValue()) {

				checkDatiAngraficiLegaleRappresentante(legaleRapp,areDatiAnagraficiegaleRappresentanteCompilati, extraComunitario, null);
				checkDatiTitoloSoggiornoLegaleRappresentante(legaleRapp, extraComunitario, Boolean.FALSE);

			}
		}
	}


	private void checkDatiTitoloSoggiornoLegaleRappresentante(LegaleRappr legaleRapp, Boolean extraComunitario,  Boolean obbligatori) {

		if (ComonlUtility.isNotVoid(obbligatori) && obbligatori.booleanValue()) {

			if (!ComonlUtility.isVoid(legaleRapp.getCittadinanza())) {
				try {
					if(!"S".equalsIgnoreCase(legaleRapp.getCittadinanza().getFlgUe())) {

						// si tratta di un lavoratore extra UE

						checkCondition(ComonlUtility.isNotVoid(legaleRapp.getFlgSoggiornanteItalia()), MsgComonl.COMDATE020.getError());

						//start - 72160(RS): 1338 - fix: se flag "soggiornante in Italia" = NO i dati della sezione TITOLO DI SOGGIORNO NON sono da compilare
						if ("S".equalsIgnoreCase(legaleRapp.getFlgSoggiornanteItalia())) {

							checkNotNull(legaleRapp.getStatusStraniero(), "Tipo documento legale rappresentante");
							checkNotNull(legaleRapp.getMotivoPermesso(), "Motivo permesso legale rappresentante");
							checkNotNull(legaleRapp.getDtScadenzaPermessoSogg(), "Data scadenza permesso legale rappresentante");
							checkCondition(ComonlUtility.convertiDataInStringa(legaleRapp.getDtScadenzaPermessoSogg()) != null, MsgComonl.COMDATE021.getError());
							checkCondition(ValidazioneCampi.controllaData(ComonlUtility.convertiDataInStringa(legaleRapp.getDtScadenzaPermessoSogg())), MsgComonl.COMDATE022.getError());

							boolean ris = ComonlUtility.confrontaData1MaggioreUgualeData2(legaleRapp.getDtScadenzaPermessoSogg(),ComonlUtility.convertiStringaInData("01/01/2000"));
							if(!ris && !legaleRapp.getStatusStraniero().getCodStatusMin().equals(ComonlConstants.TIPO_DOCUMENTO_ATTESA_PERMESSO)) {
								addApiError(new ApiError(MsgComonl.COMDATE023.getCode(), MsgComonl.COMDATE023.getMessage()));
							}


						} else {
							//i dati della sezione TITOLO DI SOGGIORNO non sono da valorizzare se il flag "soggiornante in Italia"  = NO
							if (!ComonlUtility.isVoid(legaleRapp.getStatusStraniero()) 
									|| !ComonlUtility.isVoid(legaleRapp.getNumeroDocumento()) 
									|| !ComonlUtility.isVoid(legaleRapp.getMotivoPermesso()) 
									|| !ComonlUtility.isVoid(legaleRapp.getDtScadenzaPermessoSogg()) 
									|| !ComonlUtility.isVoid(legaleRapp.getQuestura())
									) {
								addApiError(new ApiError(MsgComonl.COMDATE024.getCode(), MsgComonl.COMDATE024.getMessage()));
								return;
							}
						}
						//end - 72160(RS): 1338 - fix
					}

					else {

						//se il datore e' identificato come cittadino UE (combo cittadinanza) ma il flag extra-comunitario e' SI
						//c'e' un'incongruenza, ma il controllo lo fa gia' in checkCittadinanzaLegaleRappresentante; il messaggio di errore lo scrive gia' il metodo .
						// se la cittadinanza e' UE e flag extracomunitario = NO i dati di tutta la sezione non devono essere valorizzati
						if ( (!ComonlUtility.isVoid(legaleRapp.getStatusStraniero()) 
								|| !ComonlUtility.isVoid(legaleRapp.getNumeroDocumento()) 
								|| !ComonlUtility.isVoid(legaleRapp.getMotivoPermesso()) 
								|| !ComonlUtility.isVoid(legaleRapp.getDtScadenzaPermessoSogg()) 
								|| !ComonlUtility.isVoid(legaleRapp.getQuestura()) 
								|| !ComonlUtility.isVoid(legaleRapp.getFlgSoggiornanteItalia()) ) 
								&& !extraComunitario 
								)
						{
							addApiError(new ApiError(MsgComonl.COMDATE024.getCode(), MsgComonl.COMDATE024.getMessage()));
							return;
						}

						//end COMONL-72160 - 1338 upgrade
					}

				} catch (NullPointerException e) {
					//puo' proseguire perche' il nullpointer verrebbe scatenato dalla mancata selezione della cittadinanza nella combo e 
					//siccome e' un dato obbligatorio dei dati anagrafici verra' segnalato come errore 
				}
			}



			//start 72160 - COMONL-1338 - fix: solo in caso il flas "Soggiornante in Italia" = SI deve fare questo controllo
			if (legaleRapp.getFlgSoggiornanteItalia() != null 
					&& !legaleRapp.getFlgSoggiornanteItalia().equalsIgnoreCase("") 
					&& legaleRapp.getFlgSoggiornanteItalia().equalsIgnoreCase(ComonlConstants.FLAG_S)) {

				//71862 - COMONL-1160
				if (ComonlUtility.isVoid(legaleRapp.getQuestura())
						&& ComonlUtility.isNotVoid(legaleRapp.getStatusStraniero())) {

					String codTitoloSoggiorno = legaleRapp.getStatusStraniero().getCodStatusMin();

					if ( ComonlConstants.TIPO_DOCUMENTO_PERMESSO.equalsIgnoreCase(codTitoloSoggiorno)
							|| ComonlConstants.TIPO_DOCUMENTO_CARTA.equalsIgnoreCase(codTitoloSoggiorno)
							|| ComonlConstants.TIPO_DOCUMENTO_CARTA_PERMANENTE.equalsIgnoreCase(codTitoloSoggiorno)
							|| ComonlConstants.TIPO_DOCUMENTO_PERMESSO_DI_SOGGIORNO_CE_PER_SOGGIORNANTI_DI_LUNGO_PERIODO.equalsIgnoreCase(codTitoloSoggiorno) ){

						addApiError(new ApiError(MsgComonl.COMDATE025.getCode(), MsgComonl.COMDATE025.getMessage()));
					}
					// COMONL-72160 - 1338 - fix: la combo della questura e' valorizzata 
					// la questura deve essere compilata solo nei casi tipo documento <> 'in attesa di permesso' o 'in rinnovo' o 'altro provvedimento'.
				} else if ( ComonlUtility.isNotVoid(legaleRapp.getQuestura())
						&& ComonlUtility.isNotVoid(legaleRapp.getStatusStraniero())
						&& (ComonlConstants.TIPO_DOCUMENTO_IN_RINNOVO.equalsIgnoreCase(legaleRapp.getStatusStraniero().getCodStatusMin())
								|| ComonlConstants.TIPO_DOCUMENTO_ATTESA_PERMESSO.equalsIgnoreCase(legaleRapp.getStatusStraniero().getCodStatusMin())
								|| ComonlConstants.TIPO_DOCUMENTO_ALTRO_PROVVEDIMENTO.equalsIgnoreCase(legaleRapp.getStatusStraniero().getCodStatusMin()))
						) {
					addApiError(new ApiError(MsgComonl.COMDATE026.getCode(), MsgComonl.COMDATE026.getMessage()));
				}
				//end 72160 - COMONL-1338 - fix
			}


		} else {
			//non ci devono essere
			if (ComonlUtility.isNotVoid(legaleRapp.getStatusStraniero())
					|| ComonlUtility.isNotVoid(legaleRapp.getNumeroDocumento())
					|| ComonlUtility.isNotVoid(legaleRapp.getMotivoPermesso())
					|| ComonlUtility.isNotVoid(legaleRapp.getDtScadenzaPermessoSogg())
					|| ComonlUtility.isNotVoid(legaleRapp.getQuestura())) {

				addApiError(new ApiError(MsgComonl.COMDATE019.getCode(), MsgComonl.COMDATE019.getMessage()));
				return;
			}
		}

		//start COMONL-72160 - 1338: solo in caso di flag soggiornante in italia valorizzato e uguale a si fare il controllo sulla data
		if (!ComonlUtility.isVoid(legaleRapp.getFlgSoggiornanteItalia()) && legaleRapp.getFlgSoggiornanteItalia().equalsIgnoreCase(ComonlConstants.FLAG_S)) {
			//			Scadenza titolo soggiorno
			//			Obbligatorio se motivo permesso e' valorizzato. 
			//			REGOLE : 
			//			1 – Se Tipo documento e' diverso da 'in attesa di permesso' la data deve essere >= 01/01/2000. 
			//			2 - In caso di documento 'in attesa di permesso' deve essere indicata la data 'convenzionale' 01/01/1900.
			//			3 - In caso di 'carta permanente' deve essere indicata la data convenzionale 31/12/2099.
			if (ComonlUtility.isNotVoid(legaleRapp.getMotivoPermesso())) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

				Date dataScadenzaTitoloSoggiorno = null;
				try {
					if (ComonlUtility.isVoid(legaleRapp.getDtScadenzaPermessoSogg())) {
						//						errors.add(ActionErrors.GLOBAL_ERROR,
						//								new ActionError("datoreLegaleRappDatiTitoloSoggiornoDataScadenzaTitoloDiSoggiornoObbligatoria",
						//										applicationResources.getMessage("datoreLegaleRappDatiTitoloSoggiornoDataScadenzaTitoloDiSoggiornoObbligatoria")));
					} else {
						dataScadenzaTitoloSoggiorno = sdf.parse(ComonlUtility.convertiDataInStringa(legaleRapp.getDtScadenzaPermessoSogg()));
					}
				} catch (ParseException e) {

					addApiError(new ApiError(MsgComonl.COMDATE027.getCode(), MsgComonl.COMDATE027.getMessage()));
					return;
				}

				if(dataScadenzaTitoloSoggiorno != null) {
					if (ComonlUtility.isNotVoid(legaleRapp.getStatusStraniero()) 
							&& !ComonlConstants.TIPO_DOCUMENTO_ATTESA_PERMESSO.equalsIgnoreCase(legaleRapp.getStatusStraniero().getCodStatusMin())) {
						// 1 – Se Tipo documento e' diverso da 'in attesa di permesso' la data deve essere >= 01/01/2000. 
						try {
							Date dataRiferimento = sdf.parse("01/01/2000");
							if (dataScadenzaTitoloSoggiorno.getTime() < dataRiferimento.getTime()) {
								addApiError(new ApiError(MsgComonl.COMDATE028.getCode(), MsgComonl.COMDATE028.getMessage()));
							}
						} catch (ParseException e) { 

						}
					} else if (ComonlUtility.isNotVoid(legaleRapp.getStatusStraniero()) 
							&& ComonlConstants.TIPO_DOCUMENTO_ATTESA_PERMESSO.equalsIgnoreCase(legaleRapp.getStatusStraniero().getCodStatusMin())) {
						// 2 - In caso di documento 'in attesa di permesso' deve essere indicata la data 'convenzionale' 01/01/1900.
						try {
							Date dataConvenzionale = sdf.parse("01/01/1900");
							if (dataScadenzaTitoloSoggiorno.getTime() != dataConvenzionale.getTime()) {
								addApiError(new ApiError(MsgComonl.COMDATE029.getCode(), MsgComonl.COMDATE029.getMessage()));
							}
						} catch (ParseException e) { }
					}

					if (ComonlUtility.isNotVoid(legaleRapp.getStatusStraniero()) 
							&& ComonlConstants.TIPO_DOCUMENTO_CARTA_PERMANENTE.equalsIgnoreCase(legaleRapp.getStatusStraniero().getCodStatusMin())) {
						// 3 - In caso di 'carta permanente' deve essere indicata la data convenzionale 31/12/2099.
						try {
							Date dataConvenzionale = sdf.parse("31/12/2099");
							if (dataScadenzaTitoloSoggiorno.getTime() != dataConvenzionale.getTime()) {
								addApiError(new ApiError(MsgComonl.COMDATE030.getCode(), MsgComonl.COMDATE030.getMessage()));
							}
						} catch (ParseException e) { }
					}

				}
			}
		}
	}

	private void checkDatiAngraficiLegaleRappresentante(LegaleRappr legaleRapp, boolean areDatiAnagraficiegaleRappresentanteCompilati, Boolean extraComunitario, Boolean obbligatori) {

		if (ComonlUtility.isVoid(obbligatori)) {

			//71862 - COMONL-1178 - punto 2

			if (ComonlUtility.isNotVoid(legaleRapp.getDsCognome())
					|| ComonlUtility.isNotVoid(legaleRapp.getDsNome())
					|| ComonlUtility.isNotVoid(legaleRapp.getSesso())
					|| ComonlUtility.isNotVoid(legaleRapp.getDtNascita())
					|| ComonlUtility.isNotVoid(legaleRapp.getStatiEsteri())
					|| ComonlUtility.isNotVoid(legaleRapp.getComune())
					|| ComonlUtility.isNotVoid(legaleRapp.getCittadinanza()) ) {

				if (!areDatiAnagraficiegaleRappresentanteCompilati) {
					addApiError(new ApiError(MsgComonl.COMDATE010.getCode(), MsgComonl.COMDATE010.getMessage()));
				}
			}

			checkCittadinanzaLegaleRappresentante(legaleRapp, extraComunitario);
			
			checkDateField(legaleRapp);

		} else if (obbligatori.booleanValue()) {
			if (ComonlUtility.isVoid(legaleRapp.getDsCognome())
					|| ComonlUtility.isVoid(legaleRapp.getDsNome())
					|| ComonlUtility.isVoid(legaleRapp.getSesso())
					|| ComonlUtility.isVoid(legaleRapp.getDtNascita())
					|| (ComonlUtility.isVoid(legaleRapp.getComune()) && ComonlUtility.isVoid(legaleRapp.getStatiEsteri()))
					|| ComonlUtility.isVoid(legaleRapp.getCittadinanza())) {
				addApiError(new ApiError(MsgComonl.COMDATE011.getCode(), MsgComonl.COMDATE011.getMessage()));

				return;
			}

			checkCittadinanzaLegaleRappresentante(legaleRapp, extraComunitario);

			checkDateField(legaleRapp);
		} else {

			if (ComonlUtility.isNotVoid(legaleRapp.getDsCognome())
					|| ComonlUtility.isNotVoid(legaleRapp.getDsNome())
					|| ComonlUtility.isNotVoid(legaleRapp.getSesso())
					|| ComonlUtility.isNotVoid(legaleRapp.getDtNascita())
					|| ComonlUtility.isNotVoid(legaleRapp.getComune())
					|| ComonlUtility.isNotVoid(legaleRapp.getStatiEsteri())
					|| ComonlUtility.isNotVoid(legaleRapp.getCittadinanza())) {

				addApiError(new ApiError(MsgComonl.COMDATE012.getCode(), MsgComonl.COMDATE012.getMessage()));
				return;
			}
		}

		if (ComonlUtility.isNotVoid(legaleRapp.getComune())
				&& ComonlUtility.isNotVoid(legaleRapp.getStatiEsteri())) {

			addApiError(new ApiError(MsgComonl.COMDATE013.getCode(), MsgComonl.COMDATE013.getMessage()));

		}

	}

	public void checkDateField(LegaleRappr legaleRapp) {

		checkCondition(ComonlUtility.isNotVoid(legaleRapp.getDtNascita()) && ValidazioneCampi.controllaData(ComonlUtility.convertiDataInStringa(legaleRapp.getDtNascita())), MsgComonl.COMDATE018.getError());
	}

	private void checkCittadinanzaLegaleRappresentante(LegaleRappr legaleRapp, Boolean extraComunitario) {


		if (ComonlUtility.isNotVoid(legaleRapp.getCittadinanza()) 
				|| !"".equalsIgnoreCase(legaleRapp.getCittadinanza().getCodCittadinanzaMin())) {

			if (extraComunitario.booleanValue() == false 
					&& !"S".equalsIgnoreCase(legaleRapp.getCittadinanza().getFlgUe()) ) {
				//se flg residenza extra-ue = NO e cittadinanza extra UE
				addApiError(new ApiError(MsgComonl.COMDATE014.getCode(), MsgComonl.COMDATE014.getMessage()));

			}			
			if (extraComunitario.booleanValue() == true 
					&& "S".equalsIgnoreCase(legaleRapp.getCittadinanza().getFlgUe())) {
				//se flg residenza extra-ue = SI e cittadinanza UE
				addApiError(new ApiError(MsgComonl.COMDATE015.getCode(), MsgComonl.COMDATE015.getMessage()));
			}	
		}
	}

	private void checkDatiGeneraliAzienda ()  {

		if (ControlliSuOggettoComunicazione.isUNILAV(controlObject) || ControlliSuOggettoComunicazione.isVARDATORI(controlObject)) { 
			checkCodiceFiscale(datore.getCodiceFiscale(), datore.getPartitaIva(), decodificaDad);

		} else { // e' un UNISOMM
			checkAllowedValuesFlag(datore.getFlgUtilEstera(), "Agenzia somministrazione estera", false);
			checkCondition(
					!ComonlConstants.FLAG_S.equalsIgnoreCase(datore.getFlgUtilEstera()) ||
					ComonlUtility.isNotVoid(datore.getCodiceFiscale()), MsgComonl.COMDATE005.getError());
			if (ComonlConstants.FLAG_N.equalsIgnoreCase(datore.getFlgUtilEstera())) {
				checkCodiceFiscale(datore.getCodiceFiscale(), datore.getPartitaIva(), decodificaDad);
			}
			// ******************* controllo: combo sigla stato estero
			//				if (actionForm.getCOMBOcodiceSiglaStatoEstero() == null || 
			//						"-1".equalsIgnoreCase(actionForm.getCOMBOcodiceSiglaStatoEstero())) {
			//					errors.add(ActionErrors.GLOBAL_ERROR,
			//							new ActionError("datoreCodiceSiglaStatoEstero",
			//									applicationResources.getMessage("datoreCodiceSiglaStatoEstero")));
			//				}
			//
			//				// ******************* controllo: codice identificativo stato estero
			//				if (actionForm.getCodiceIdentificativoStatoEstero() == null || 
			//						"".equalsIgnoreCase(actionForm.getCodiceIdentificativoStatoEstero())) {
			//					errors.add(ActionErrors.GLOBAL_ERROR,
			//							new ActionError("datoreCodiceIdentificativoStatoEstero",
			//									applicationResources.getMessage("datoreCodiceIdentificativoStatoEstero")));
			//				} else if (actionForm.getCodiceIdentificativoStatoEstero().length() > Constants.CODICE_IDENTIFICATIVO_STATO_ESTERO_MAX_LENGHT) {
			//					errors.add(ActionErrors.GLOBAL_ERROR,
			//							new ActionError("datoreCodiceIdentificativoStatoEsteroWarning",
			//									applicationResources.getMessage("datoreCodiceIdentificativoStatoEsteroWarning")));			
			//				}
		}

		checkNotNull(datore.getDsDenominazioneDatore(), "Denominazione datore");
		checkNotNull(datore.getFlgPubAmm(), "Flag pubblica amministrazione");
		
		if (!skipControl) {
			checkModel(datore.getNaturaGiuridica(), "Natura giuridica");
		}
		
		checkNotNull(datore.getFlgAzArtigiana(), "Flag azienda artigiana");
		checkCondition(ComonlUtility.isNotVoid(datore.getDsDenominazioneDatore()) && datore.getDsDenominazioneDatore().length() < 305, MsgComonl.COMDATE007.getError());

		// verifica presenza AtecoFin
		Atecofin atecofin = controlliDad.getAtecofinValid(datore.getAtecofin(), new Date());
		checkCondition (atecofin!=null, MsgComonl.COMCOMW101.getError());
		if(atecofin != null) {
			datore.setAtecofin(atecofin);
		}
	}

	private boolean checkLunghezzaMaxCampi(Datore datore) {
		
		//inizio modifica 06/07 per poter gestire i cf di aziende esteri(possono essere piu lunghi di 16)
		if(
				ComonlUtility.isNotVoid(datore.getCodiceFiscale()) && 
				datore.getFlgUtilEstera().equals("S") && 
				(datore.getCodiceFiscale().length() < 3 || datore.getCodiceFiscale().length() > 100)
				
		) {
			return false;
		}else if(
				ComonlUtility.isNotVoid(datore.getCodiceFiscale()) &&
				datore.getFlgUtilEstera().equals("N") &&
				datore.getCodiceFiscale().length() > 16) {
			return false;
		}
		if (ComonlUtility.isNotVoid(datore.getDsDenominazioneDatore()) &&
				datore.getDsDenominazioneDatore().length() > 305) {
			return false;
		}
		if (ComonlUtility.isNotVoid(datore.getPartitaIva()) &&
				datore.getPartitaIva().length() > 11) {
			return false;
		}
		if (ComonlUtility.isNotVoid(datore.getMatricolaInps()) &&
				datore.getMatricolaInps().length() > 16) {
			return false;
		}
		if (datore.getAtecofin() != null && datore.getAtecofin().getCodAtecofinMin() != null  && !"".equalsIgnoreCase(datore.getAtecofin().getCodAtecofinMin()) &&
				datore.getAtecofin().getCodAtecofinMin().length() > 8) {
			return false;
		}
		if (datore.getAtecofin() != null && datore.getAtecofin().getDsComTAtecofin() != null  && !"".equalsIgnoreCase(datore.getAtecofin().getDsComTAtecofin()) &&
				datore.getAtecofin().getDsComTAtecofin().length() > 430) {
			return false;
		}
		if (datore.getSedeLegale() != null &&  datore.getSedeLegale().getIndirizzo() != null && !"".equalsIgnoreCase(datore.getSedeLegale().getIndirizzo()) &&
				datore.getSedeLegale().getIndirizzo().length() > 100) {
			return false;
		}
		if (datore.getSedeLegale() != null &&  datore.getSedeLegale().getComune() != null 
				&& datore.getSedeLegale().getComune().getProvincia() != null  
				&& datore.getSedeLegale().getComune().getProvincia().getDsComTProvincia() != null
				&& datore.getSedeLegale().getComune().getProvincia().getDsComTProvincia().length() > 30) {
			return false;
		}
		if (datore.getSedeLegale() != null &&  datore.getSedeLegale().getComune() != null 
				&& datore.getSedeLegale().getComune().getDsComTComune() != null 
				&& !"".equalsIgnoreCase(datore.getSedeLegale().getComune().getDsComTComune())
				&& datore.getSedeLegale().getComune().getDsComTComune().length() > 100) {
			return false;
		}
		if (datore.getSedeLegale() != null && ComonlUtility.isNotVoid(datore.getSedeLegale().getTelefono()) &&
				datore.getSedeLegale().getTelefono().length() > 15) {
			return false;
		}
		if (datore.getSedeLegale() != null && ComonlUtility.isNotVoid(datore.getSedeLegale().getFax()) &&
				datore.getSedeLegale().getFax().length() > 15) {
			return false;
		}
		if (datore.getSedeOperativa() != null && ComonlUtility.isNotVoid(datore.getSedeOperativa().getIndirizzo()) &&
				datore.getSedeOperativa().getIndirizzo().length() > 100) {
			return false;
		}
		if (datore.getSedeOperativa() != null &&  datore.getSedeOperativa().getComune() != null 
				&& datore.getSedeOperativa().getComune().getDsComTComune() != null 
				&& !"".equalsIgnoreCase(datore.getSedeOperativa().getComune().getDsComTComune())
				&& datore.getSedeOperativa().getComune().getDsComTComune().length() > 100) {
			return false;
		}
		if (datore.getSedeOperativa() != null && ComonlUtility.isNotVoid(datore.getSedeOperativa().getTelefono()) &&
				datore.getSedeOperativa().getTelefono().length() > 15) {
			return false;
		}
		if (datore.getSedeOperativa() != null && ComonlUtility.isNotVoid(datore.getSedeOperativa().getFax()) &&
				datore.getSedeOperativa().getFax().length() > 15) {
			return false;
		}
		if (datore.getSedeOperativa() != null && ComonlUtility.isNotVoid(datore.getSedeOperativa().getEmail()) &&
				datore.getSedeOperativa().getEmail().length() > 80) {
			return false;
		}
		if(ComonlUtility.isNotVoid(datore.getNumAgenziaSommin()) &&
				datore.getNumAgenziaSommin().length() > 11) {
			return false;
		}
		return true;
	}	

	private void checkCodiceFiscale(String cf, String partitaIva, DecodificaDad decodificaDad) {
		if ( ComonlUtility.isNotVoid(cf) && cf.length() != 16 && cf.length() != 11) {
			checkCondition(ComonlUtility.isNotVoid(cf) && cf.length() != 16 && cf.length() != 11, MsgComonl.COMDATE002.getError());
		}
		// ******************* controllo: codice fiscale - partita iva
		else if (ComonlUtility.isNotVoid(cf) && cf.length() == 16) {
			checkCondition(CodiceFiscale.getInstance().controllaCF(cf, decodificaDad), MsgComonl.COMDATE002.getError());

		} else if (ComonlUtility.isNotVoid(cf) && cf.length() == 11) {
			if(!CodiceFiscale.getInstance().controllaPartitaIVA(cf)){
				checkCondition(CodiceFiscale.getInstance().controllaPartitaIVA(cf), MsgComonl.COMDATE003.getError());
			}
		} 
		else if (!CodiceFiscale.getInstance().isCodiceFiscaleInternazionaleValido(cf)) {
			checkCondition(CodiceFiscale.getInstance().isCodiceFiscaleInternazionaleValido(cf), MsgComonl.COMDATE002.getError());
		}

		if (ComonlUtility.isNotVoid(partitaIva)) {
			checkCondition(CodiceFiscale.getInstance().controllaPartitaIVA(partitaIva), MsgComonl.COMDATE003.getError());
		}
	}

	private boolean areDatiAnagraficiegaleRappresentanteCompilati(LegaleRappr legaleRapp) {		
		if (ComonlUtility.isNotVoid(legaleRapp.getDsCognome())
				&& ComonlUtility.isNotVoid(legaleRapp.getDsNome())
				&& ComonlUtility.isNotVoid(legaleRapp.getSesso())
				&& ComonlUtility.isNotVoid(legaleRapp.getDtNascita())
				&& (
						ComonlUtility.isNotVoid(legaleRapp.getComune()) 
						|| ComonlUtility.isNotVoid(legaleRapp.getStatiEsteri()))
				&& ComonlUtility.isNotVoid(legaleRapp.getCittadinanza())) {

			return true;
		} else {

			return false;
		}
	}
	private void checkCap(SedeLavoro sede, boolean isOperativa) {

		checkCondition(!ComonlUtility.isVoid(sede.getCodCap()) || !ComonlUtility.isVoid(sede.getStatiEsteri()), isOperativa ? MsgComonl.COMDATE036.getError() : MsgComonl.COMDATE039.getError());

		if (sede.getCodCap() != null && !"".equalsIgnoreCase(sede.getCodCap().trim())) {
			checkCondition(ValidazioneCampi.getInstance().controllaNumero(sede.getCodCap()), isOperativa ? MsgComonl.COMDATE037.getError() : MsgComonl.COMDATE040.getError());
			checkCondition(sede.getCodCap().length() == 5, isOperativa ? MsgComonl.COMDATE038.getError() : MsgComonl.COMDATE041.getError());

		} 
	}

	private void checkDatiEssenziali() {
		if (!checkDatiEssenziali)
			return;
		checkDatoEssenziale(controlObject.getDatoreAttuale().getCodiceFiscale(), dbObject.getDatoreAttuale().getCodiceFiscale(), "codice fiscale");
	}
}
