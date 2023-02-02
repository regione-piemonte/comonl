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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.SessionContext;
import javax.transaction.SystemException;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DatoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.LavoratoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.LegaleRapprDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoLavoratoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.SedeLavoroDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.TutoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.EseguiFaseElaborazioneMemorizzazioneEControlliRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.UploadComunicazioniResponse;
import it.csi.comonl.comonlweb.ejb.entity.ComDImportErrore;
import it.csi.comonl.comonlweb.ejb.util.commax.CtuSpicomToComunicazioneMapper;
import it.csi.comonl.comonlweb.ejb.util.commax.ParametriDiControllo;
import it.csi.comonl.comonlweb.ejb.util.commax.manager.ElaborazioneCommaxHelper;
import it.csi.comonl.comonlweb.ejb.util.commax.util.StringUtils;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.commax.vo.ComunicazioniElaborateVO;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DepCommaxComunic;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DepCommaxComunicPK;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DepositoCommax;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FzErrori;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.TListaFzErrori;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.TListaFzErroriPK;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.UplDocumenti;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoProvenienza;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.spicom.dto.ComunicazioneTracciatoUnicoDTO;

public class EseguiFaseElaborazioneMemorizzazioneEControlliService
extends BaseComunicazioneService<EseguiFaseElaborazioneMemorizzazioneEControlliRequest, UploadComunicazioniResponse> {

	private CommonDad commonDad;
	private DecodificaDad decodificaDad;

	private Ruolo ruolo;
	private UplDocumenti uploadDocumenti;
	private ComunicazioneDad comunicazioneDad;
	private AnagraficaDelegatoDad anagraficaDelegatoDad;
	private ElaborazioneCommaxHelper elaborazionehelper = new ElaborazioneCommaxHelper();
	private DatoreDad datoreDad;
	private LegaleRapprDad legaleRapprDad;
	private SedeLavoroDad sedeLavoroDad;
	private RapportoDad rapportoDad;
	private LavoratoreDad lavoratoreDad;
	private RapportoLavoratoreDad rapportoLavoratoreDad;
	private TutoreDad tutoreDad;
	private SessionContext sessionContext;

	public EseguiFaseElaborazioneMemorizzazioneEControlliService(SessionContext sessionContext, ConfigurationHelper configurationHelper,
			Ruolo ruolo, UplDocumenti uploadDocumenti,ComunicazioneDad comunicazioneDad,
			AnagraficaDelegatoDad anagraficaDelegatoDad,  CommonDad commonDad, DecodificaDad decodificaDad,
			DatoreDad datoreDad,
			LegaleRapprDad legaleRapprDad,
			SedeLavoroDad sedeLavoroDad,
			RapportoDad rapportoDad,
			LavoratoreDad lavoratoreDad,
			RapportoLavoratoreDad rapportoLavoratoreDad,
			TutoreDad tutoreDad) {

		super(configurationHelper, comunicazioneDad);
		this.anagraficaDelegatoDad = anagraficaDelegatoDad;
		this.commonDad = commonDad;
		this.decodificaDad = decodificaDad;
		this.ruolo = ruolo;
		this.uploadDocumenti = uploadDocumenti;
		this.comunicazioneDad = comunicazioneDad;
		this.datoreDad  = datoreDad;
		this.legaleRapprDad  = legaleRapprDad;
		this.sedeLavoroDad  = sedeLavoroDad;
		this.rapportoDad  = rapportoDad;
		this.lavoratoreDad  = lavoratoreDad;
		this.rapportoLavoratoreDad  = rapportoLavoratoreDad;
		this.tutoreDad  = tutoreDad;
		this.sessionContext = sessionContext;
		
	}

	@Override
	protected void execute() {

		UplDocumenti uplDoc = comunicazioneDad.getUplDocumentiById(uploadDocumenti.getId());

		if (!(StringUtils.isEmpty(uplDoc.getStatoElaborazione()) || uplDoc.getStatoElaborazione().equals(UtilConstant.STATO_ELABORAZIONE_DA_ELABORARE))){
			//Debug.print("Mi aspetto l'elaborazione nello stato E o campo nullo  invece ho '"+uplDoc.getStatoElaborazione()+"'. Salto questo passo");
			return;
		}

		List<DepositoCommax> elementi = comunicazioneDad.getDepositoCommaxByIdUplDocumenti(uplDoc.getId());

		ComunicazioniElaborateVO elaborati = new ComunicazioniElaborateVO();

		int accettate = 0;
		int scartate = 0;
		for (int i=0; i<elementi.size(); i++) {
			// Leggo il primo elemento ed applico la catena
			DepositoCommax elementoCorrente = elementi.get(i);

			//sessionContext.getUserTransaction().begin();
			boolean esito = eseguiFaseElaborazioneMemorizzazioneEControlliPerSingolaComunicazione( ruolo,
					elementoCorrente,
					uplDoc,
					anagraficaDelegatoDad,
					commonDad,
					decodificaDad,
					comunicazioneDad);
			//sessionContext.getUserTransaction().commit();


			if (esito) {
				// la comunicazione e' stata correttamente processata e quindi
				// aggiorno il contatore delle accettate.
				accettate = accettate + 1;
				elaborati.setAccettate(accettate);
			} else {
				// la comunicazione e' stata scartata aggiorno il contatore delle
				// scartate.
				scartate = scartate + 1;
				elaborati.setScartate(scartate);
			}

			// Aggiorno il contatore delle elaborate
			elaborati.setElaborate(i + 1);
		}


		// Occorre aggiornare la tavola com_d_upl_documenti con i risultati
		// dell'elaborazione.
		UplDocumenti upload = comunicazioneDad.getUplDocumentiById(uploadDocumenti.getId());
		upload.setTotComElab(new BigDecimal(elaborati.getElaborate()));
		upload.setTotComScar(new BigDecimal(elaborati.getScartate()));
		upload.setTotComWarn(new BigDecimal(elaborati.getWarning()));
		
		try {
			sessionContext.getUserTransaction().begin();
			upload = comunicazioneDad.updateUploadDocumenti(upload);
			upload.setStatoElaborazione(UtilConstant.STATO_ELABORAZIONE_REGISTRATE);
			upload = comunicazioneDad.updateStatoUploadDocumenti(upload);
			sessionContext.getUserTransaction().commit();

		} catch(Exception e) {
			log.error("execute", e);
			try {
				sessionContext.getUserTransaction().rollback();
			} catch (Exception ee) {
				
			}
		}
	}

	private boolean eseguiFaseElaborazioneMemorizzazioneEControlliPerSingolaComunicazione(
			Ruolo ruolo,
			DepositoCommax elementoCorrente,
			UplDocumenti uplDoc,
			AnagraficaDelegatoDad anagraficaDelegatoDad,
			CommonDad commonDad,
			DecodificaDad decodificaDad,
			ComunicazioneDad comunicazioneDad)  {

		boolean comunicazioneValida = false;
		
		String xmlEstratto = new String(elementoCorrente.getBFileCommax());

		ComunicazioneTracciatoUnicoDTO comunicazione = elaborazionehelper.convertiXmlInOggettone(xmlEstratto);

		elaborazionehelper.popolaCampiOggettone(ruolo, comunicazione, uplDoc.getMittenteCf(), uplDoc.getDsRuoloOperatore(), uplDoc.getDataRicezione(), elementoCorrente.getEmailUtente(), anagraficaDelegatoDad) ;

		ParametriDiControllo pc = new ParametriDiControllo();
		pc.setidReport(uplDoc.getId());
		pc.setnomeFileXml(elementoCorrente.getNomeFileXmlSingolo());
		pc.setcomunicazioneCorrenteDTO(comunicazione);
		pc.setCodiceFiscaleAzienda(uplDoc.getCfAzienda());
		
		
		if (comunicazione.getLavoratore() != null)
			pc.setCodiceFiscaleLavoratore(comunicazione.getLavoratore().getCodFiscale());

		List<ApiError> errorsControlli = elaborazionehelper.eseguoControlliSuComunicazione(comunicazione, uplDoc.getId(), uplDoc.getCfAzienda(), pc.getCodiceFiscaleLavoratore(),  elementoCorrente.getNomeFileXmlSingolo(), commonDad, decodificaDad, comunicazioneDad, ruolo);

		if (errorsControlli != null && errorsControlli.size() >0 ) {
			comunicazioneValida = false;
			memorizzaErroriBloccanti(errorsControlli, pc, comunicazioneDad);
		} else {
			comunicazioneValida = true;
		}
		
		if (comunicazioneValida == true && !(UtilConstant.FLAG_S).equalsIgnoreCase(uplDoc.getFlgVerifica())){

			// CONVERTIRE LA COMUNICAZIONE IN FORMATO CTU NELLA COMUNICAZIONE IN FORMATO COMONL
			CtuSpicomToComunicazioneMapper mappingCtuInComunicazioneComonl = new CtuSpicomToComunicazioneMapper(comunicazione, decodificaDad);
			Comunicazione comunicazioneComonl = mappingCtuInComunicazioneComonl.toComunicazioneComonl();
			comunicazioneComonl.setTipoProvenienza(new TipoProvenienza());
			comunicazioneComonl.getTipoProvenienza().setCodTipoProvenienzaMin(ComonlConstants.TIPO_PROVENIENZA_COMMAX);
			comunicazioneComonl.getTipoProvenienza().setId(ComonlConstants.ID_TIPO_PROVENIENZA_COMMAX);
			comunicazioneComonl.setEmail(uplDoc.getMittenteEmail());
			
			List<ComDImportErrore> errors = mappingCtuInComunicazioneComonl.getErrors(); // non mi aspetto errori è già validata

			if (errors != null && errors.isEmpty()) {
				try {
					sessionContext.getUserTransaction().begin();
					InsertComunicazioneService insertComunicazioneService = new InsertComunicazioneService(configurationHelper,
							comunicazioneDad,
							datoreDad,
							legaleRapprDad,
							sedeLavoroDad,
							rapportoDad,
							lavoratoreDad,
							rapportoLavoratoreDad,
							tutoreDad);
					
					
					Comunicazione comunicazioneInserita = null;
					
					setDelegatoPerRuolo(comunicazioneComonl, ruolo);
					
					if(comunicazioneComonl.getRapLavSedeVD() != null && comunicazioneComonl.getRapLavSedeVD().size() > 0) {
						comunicazioneInserita = insertComunicazioneService.insertComunicazioneSempliceVardatore(comunicazioneComonl);
					} else {
						comunicazioneInserita = insertComunicazioneService.insertComunicazione(comunicazioneComonl);
					}
					
					try {
						DepCommaxComunic depCommaxComunic = new DepCommaxComunic();
						depCommaxComunic.setComunicazione(comunicazioneInserita);
						depCommaxComunic.setUplDocumenti(new UplDocumenti());
						depCommaxComunic.getUplDocumenti().setId(elementoCorrente.getIdComDUplDocumenti().longValue());
						DepCommaxComunicPK idDeposito = new DepCommaxComunicPK();
						idDeposito.setIdDepositoCommax(elementoCorrente.getId().getIdDepositoCommax());
						idDeposito.setIdComDComunicazione(comunicazioneInserita.getId());
						idDeposito.setIdComDUplDocumenti(elementoCorrente.getIdComDUplDocumenti().longValue());
						idDeposito.setProgXml(elementoCorrente.getId().getProgXml());
						depCommaxComunic.setId(idDeposito);
						depCommaxComunic = comunicazioneDad.insertComRDepCommaxComunic(depCommaxComunic);
					
					} catch (Exception e) {
						log.error("Errore inserimento su deposito commax", e);
					}
					sessionContext.getUserTransaction().commit();
				} catch (Exception e) {
					log.error("errore inatteso", e);
					try {
						sessionContext.getUserTransaction().rollback();
					} catch (IllegalStateException | SecurityException | SystemException e1) {
						log.info("rollback", e);
					}
				}
	
				} else {
					comunicazioneValida = false;
					log.debug("eseguiFaseElaborazioneMemorizzazioneEControlliPerSingolaComunicazione", "************************ ATTENZIONE NON MI ASPETTQAVO ERRORI DALLA CONVERSIONE DEL TRACCIATO!!!! NON VA BENE");
					try {
						sessionContext.getUserTransaction().begin();
						// salva gli errori nella tabella di log importazione
						for(ComDImportErrore error: errors) {
							error.setCodComunicazioneReg("CX_"+ elementoCorrente.getId().getIdDepositoCommax()); // al posto de codice regionale c'è CX_<iddepositocommax>
							comunicazioneDad.insertImportErrori(error);
						}
						sessionContext.getUserTransaction().commit();
					} catch (Exception e) {
						try {
							sessionContext.getUserTransaction().rollback();
						} catch (IllegalStateException | SecurityException | SystemException e1) {
							log.info("rollback", e);
						}						
					}
			}
		} else {
			// si limita ad inviare la mail tutto ok
			log.debug("eseguiFaseElaborazioneMemorizzazioneEControlliPerSingolaComunicazione", "************************ ATTENZIONE SONO FINITO IN UN RAMO DA FINIRE (Comunicazione non valida)!!!! NON VA BENE");
		}
		return comunicazioneValida;
	}


	private void memorizzaErroriBloccanti(List<ApiError> errors,ParametriDiControllo parametro, ComunicazioneDad comunicazioneDad){

		FzErrori erPadre=new FzErrori();
		erPadre.setCfAziendaCfLavoratore(parametro.getCodiceFiscaleLavoratore() != null? parametro.getCodiceFiscaleLavoratore(): parametro.getCodiceFiscaleAzienda());
		erPadre.setUplDocumenti(new UplDocumenti());
		erPadre.getUplDocumenti().setId(parametro.getidReport());
		erPadre.setNomeFileXmlScartato(parametro.getnomeFileXml());

		try {
		sessionContext.getUserTransaction().begin();

		for(ApiError apiError : errors) {
			erPadre.setCfAziendaCfLavoratore(apiError.getGroup() != null? apiError.getGroup(): parametro.getCodiceFiscaleAzienda());
			erPadre = comunicazioneDad.registraEsitoErrore(erPadre);
			TListaFzErrori err = new TListaFzErrori();
			err.setFzErrori(new FzErrori());

			err.getFzErrori().setId(erPadre.getId());
			TListaFzErroriPK id = new TListaFzErroriPK();
			id.setId(Integer.valueOf(apiError.getCode()));
			err.setId(id);

			comunicazioneDad.registraDettaglioErroreBloccante(err);
		}
		sessionContext.getUserTransaction().commit();
		} catch (Exception e) {
			try {
				sessionContext.getUserTransaction().rollback();
			} catch (Exception e1) {
				log.info("impossibile rollback", e1);
			}
		}
	}

}
