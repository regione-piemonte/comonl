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

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.SessionContext;
import javax.transaction.SystemException;

import org.apache.commons.io.FileUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dad.AnagraficaDelegatoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.facade.ComunicazioneStatelessFacade;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.UploadComunicazioniRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.UploadComunicazioniResponse;
import it.csi.comonl.comonlweb.ejb.util.commax.CommaxHelper;
import it.csi.comonl.comonlweb.ejb.util.commax.ConstantsCommax;
import it.csi.comonl.comonlweb.ejb.util.commax.manager.EleborazioneProcessiManager;
import it.csi.comonl.comonlweb.ejb.util.commax.util.StringUtils;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationValue;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DepositoCommax;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.UplDocumenti;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CommaxParametri;
import it.csi.comonl.comonlweb.lib.dto.error.MsgCommax;
import it.csi.comonl.comonlweb.lib.util.filesystem.EsitoEstrazioneFilesDaZip;
import it.csi.comonl.comonlweb.lib.util.filesystem.FileSystemUtil;
import it.csi.comonl.comonlweb.lib.util.filesystem.ListaEsitiEstrazioneFilesDaZip;
import it.csi.spicom.dto.ComunicazioneTracciatoUnicoDTO;

public class UploadComunicazioniService
extends BaseComunicazioneService<UploadComunicazioniRequest, UploadComunicazioniResponse> {

	private AnagraficaDelegatoDad anagraficaDelegatoDad;
	private CommonDad commonDad;
	private DecodificaDad decodificaDad;
	private SessionContext sessionContext;

	public UploadComunicazioniService(SessionContext sessionContext,
			ConfigurationHelper configurationHelper, 
			ComunicazioneDad comunicazioneDad, 
			AnagraficaDelegatoDad anagraficaDelegatoDad,
			CommonDad commonDad, 
			DecodificaDad decodificaDad) {

		super(configurationHelper, comunicazioneDad);
		this.anagraficaDelegatoDad = anagraficaDelegatoDad;
		this.commonDad = commonDad;
		this.decodificaDad = decodificaDad;
		this.sessionContext = sessionContext;
	}

	@Override
	protected void execute() {

		Ruolo ruolo = request.getRuolo();
		
//		//FIXME solo per debug verificare come impostarlo se forse non serve il 
//		//profilo perchè ok nome e cognome ma non ok ds_ruolo_operaore sulla tabella COM_D_UPL_DOC
//		// e serve anche la provincia
//		if (ruolo == null) {
//			ruolo = new Ruolo();
//			ruolo.setDenominazioneAzienda("AMMINISTRATORE_COMONL@CSI");
//			ruolo.setIlRuolo("Amministratore");
//			ruolo.setDsCognome("DEMO 28");
//			ruolo.setDsNome("CSI PIEMONTE");
//			ruolo.setCodiceFiscaleAzienda("AAAAAA00A11I000Q");
//			ruolo.setCodiceFiscaleUtente("AAAAAA00A11I000Q");
//		}

		CommaxHelper ch = new CommaxHelper();

		List<ApiError> apiErrors = new ArrayList<ApiError>();
		//Controlli preliminari
		String nomeFile    = request.getNomeFile();
		Boolean isVerifica = request.getIsVerifica();
		byte[] fileInByte  = request.getFileInByte();

		//
		String email = request.getEmail();
		if (!CommaxHelper.isValidMail(email)) {
			apiErrors.add(MsgCommax.COMXVAL0001.getError());
		}
		//il file deve essere uno zip o un xml: controllo estensione
		String estensioneFile = controllaEstensioneFile(apiErrors, nomeFile);
		if (apiErrors != null && apiErrors.size() > 0) {
			response.setApiErrors(apiErrors);
			return;
		}
		//controlla che il file non sia vuoto 
		controllaSeFileValorizzato(apiErrors, fileInByte);
		if (apiErrors != null && apiErrors.size() > 0) {
			response.setApiErrors(apiErrors);
			return;
		}	
		//Scrivere il file su file system
		String nomeFileSalvatoCorrettamente = scriviFileSuFileSystem(ch, apiErrors, nomeFile, fileInByte);

		if (apiErrors != null && apiErrors.size() > 0) {
			response.setApiErrors(apiErrors);
			return;
		}

		String basePath = configurationHelper.getProperty(ConfigurationValue.FILE_SYSTEM);
		File file = new File(FileSystemUtil.getCommaxBaseDir(basePath), nomeFileSalvatoCorrettamente);
		String contenutoDelSingoloXML = null;

		//Controllo CRC
		Long crcCalcolato = controllaCrcSeNecessario(ch, apiErrors, isVerifica, file);
		if (apiErrors != null && apiErrors.size() > 0) {
			response.setApiErrors(apiErrors);
			return;
		}

		// da qui inizia il processo dopo i controlli
		ListaEsitiEstrazioneFilesDaZip elencoFilesInviatiDaUtente = null;
		if (estensioneFile.equalsIgnoreCase("zip")) {
			//controlli dimensioni in KB e numero di file contenuti 
			FileSystemUtil fs = controllaDimensioniZip(apiErrors, file);
			if (apiErrors != null && apiErrors.size() > 0) {
				response.setApiErrors(apiErrors);
				return;
			}
			//Estrai elenco dei file dallo zip 
			elencoFilesInviatiDaUtente = estraiElencoFileDaZip(apiErrors, file, fs);
			if (apiErrors != null && apiErrors.size() > 0) {
				response.setApiErrors(apiErrors);
				return;
			}
		} else if (estensioneFile.equalsIgnoreCase("xml")) {
			contenutoDelSingoloXML = gestisciContenutoSingoloXML(file, contenutoDelSingoloXML);
			if (apiErrors != null && apiErrors.size() > 0) {
				response.setApiErrors(apiErrors);
				return;
			}
			elencoFilesInviatiDaUtente = popolaElencoFileDaInviareConSingoloXML(nomeFileSalvatoCorrettamente,contenutoDelSingoloXML);
		}

		ComunicazioneTracciatoUnicoDTO tracciatiRisultanti[] = new ComunicazioneTracciatoUnicoDTO[elencoFilesInviatiDaUtente.getArrayXMLDaConvertireTramiteSpicom().length];
		StringBuffer esitoConversioneDaSPicom = new StringBuffer();

		try {
			ch.validaXML(elencoFilesInviatiDaUtente, esitoConversioneDaSPicom,  tracciatiRisultanti);
			ch.validaXMLTipoComunicazione(elencoFilesInviatiDaUtente, esitoConversioneDaSPicom, tracciatiRisultanti);                        

		} catch (Exception e) {
			apiErrors.add(MsgCommax.COMXVAL0008.getError());
			response.setApiErrors(apiErrors);
			return;
		}

		if (!esitoConversioneDaSPicom.toString().equals("")) { // Non vanno bene 
			log.error("UploadComunicazioniService::", "I/IL FILE NON E' BUONO MANDO IL MESSAGGIO DI ERRORE!!!!!!!!!!!!!!!!!!!");
			apiErrors.add(new ApiError("CMX-VAL-E-0009", esitoConversioneDaSPicom.toString()));
			response.setApiErrors(apiErrors);
			return;
		} else {

			try {
				sessionContext.getUserTransaction().begin();
				UplDocumenti uploadDocumenti = registraTabellaUploadDocumenti(ruolo, isVerifica, email, nomeFileSalvatoCorrettamente, crcCalcolato);
				registraTabellaDepositoCommax(ruolo, ch, email, elencoFilesInviatiDaUtente, uploadDocumenti);
				sessionContext.getUserTransaction().commit();

				ComunicazioneStatelessFacade proxyFacade = sessionContext.getBusinessObject(ComunicazioneStatelessFacade.class);
				proxyFacade.eleboraProcesso(ruolo, uploadDocumenti, comunicazioneDad, anagraficaDelegatoDad,  commonDad, decodificaDad);

				if (!isVerifica) {
					response.setEsito(MsgCommax.COMXVAL0011.getMessage());
				} else {
					response.setEsito(MsgCommax.COMXVAL0012.getMessage() + " " + MsgCommax.COMXVAL0013.getMessage());
				}

			} catch (Exception e) {
				
				try {
					sessionContext.getUserTransaction().rollback();
				} catch (IllegalStateException | SecurityException | SystemException e1) {
					log.warn("execute", e1);
				}
				apiErrors.add(MsgCommax.COMXVAL0010.getError());
				response.setApiErrors(apiErrors);
				return;
			}
		}
	}



	private void registraTabellaDepositoCommax(Ruolo ruolo, CommaxHelper ch, String email,
			ListaEsitiEstrazioneFilesDaZip elencoFilesInviatiDaUtente, UplDocumenti uploadDocumenti) {
		Iterator iter = elencoFilesInviatiDaUtente.getListaEsitiPerOK().iterator();
		int i = 0;
		while (iter.hasNext()) {
			i++;
			EsitoEstrazioneFilesDaZip element = (EsitoEstrazioneFilesDaZip)iter.next();
			DepositoCommax depositoCommax = new DepositoCommax();
			depositoCommax = creaEPopolaDepositoCommaxDTO(element, ruolo, uploadDocumenti, email);
			Long crcSingoloFile = ch.calcolaCRC(element.getContenutoDelFileInFormatoXML());

			depositoCommax.setCrcXml(new BigDecimal(crcSingoloFile));
			//  helper.inserisciInCOM_DEPOSITO_COMMAX(depositoCommax);
			comunicazioneDad.insertDepositoCommax(depositoCommax, i);
		}
	}

	private UplDocumenti registraTabellaUploadDocumenti(Ruolo ruolo, Boolean isVerifica, String email,
			String nomeFileSalvatoCorrettamente, Long crcCalcolato) {
		UplDocumenti uploadDocumenti = new UplDocumenti();
		uploadDocumenti.setDsRuoloOperatore(ruolo.getIlRuolo()); 
		uploadDocumenti.setDataElab(null);
		// R.L. :: GECO-620 :: Aggiunta data verifica
		uploadDocumenti.setDataVerifica(new java.util.Date());
		uploadDocumenti.setMittenteEmail(email);
		uploadDocumenti.setNomeFileUpload(nomeFileSalvatoCorrettamente);
		uploadDocumenti.setDataRicezione(new java.util.Date());
		uploadDocumenti.setTotComElab(BigDecimal.ZERO);
		uploadDocumenti.setTotComScar(BigDecimal.ZERO);
		uploadDocumenti.setTotComWarn(BigDecimal.ZERO);
		uploadDocumenti.setPv("TO"); // FIXME non so dove sia la provincia 
		uploadDocumenti.setMittenteCf(ruolo.getCodiceFiscaleUtente());
		uploadDocumenti.setMittenteCognome(ruolo.getDsCognome());
		uploadDocumenti.setMittenteNome(ruolo.getDsNome());
		uploadDocumenti.setCfAzienda(ruolo.getCodiceFiscaleAzienda());
		uploadDocumenti.setCrcUpload(crcCalcolato != null? new BigDecimal(crcCalcolato): null);
		uploadDocumenti.setStatoElaborazione(UtilConstant.STATO_ELABORAZIONE_DA_ELABORARE);
		if (isVerifica) {
			uploadDocumenti.setFlgVerifica("S");
		} else {
			uploadDocumenti.setFlgVerifica("N");
		}
		uploadDocumenti = comunicazioneDad.insertUplodDocumenti(uploadDocumenti);
		return uploadDocumenti;
	}

	private ListaEsitiEstrazioneFilesDaZip popolaElencoFileDaInviareConSingoloXML(String nomeFileSalvatoCorrettamente,
			String contenutoDelSingoloXML) {
		ListaEsitiEstrazioneFilesDaZip elencoFilesInviatiDaUtente;
		elencoFilesInviatiDaUtente = new ListaEsitiEstrazioneFilesDaZip();
		EsitoEstrazioneFilesDaZip esito = new EsitoEstrazioneFilesDaZip();
		esito.setEsitoEstrazione(EsitoEstrazioneFilesDaZip.ESITO_ESTRAZIONE_OK);
		esito.setContenutoDelFileInFormatoXML(contenutoDelSingoloXML);
		esito.setNomeFile(nomeFileSalvatoCorrettamente);
		esito.setEstensioneFile("xml");
		elencoFilesInviatiDaUtente.addEsitoPerOK(esito);
		return elencoFilesInviatiDaUtente;
	}

	private String gestisciContenutoSingoloXML(File file, String contenutoDelSingoloXML) {
		try {
			contenutoDelSingoloXML = new String(FileUtils.readFileToByteArray(file));
			contenutoDelSingoloXML = FileSystemUtil.addSpaceAtTheEndOfAllLines(contenutoDelSingoloXML);
			contenutoDelSingoloXML = FileSystemUtil.removeInvalidXMLCharacters(contenutoDelSingoloXML);   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contenutoDelSingoloXML;
	}

	private ListaEsitiEstrazioneFilesDaZip estraiElencoFileDaZip(List<ApiError> apiErrors, File file,
			FileSystemUtil fs) {
		ListaEsitiEstrazioneFilesDaZip elencoFilesInviatiDaUtente;
		elencoFilesInviatiDaUtente = fs.estraiElencoFilesDaZip(file);
		if (elencoFilesInviatiDaUtente.hasKO()) {
			apiErrors.add(MsgCommax.COMXVAL0008.getError());
		}
		return elencoFilesInviatiDaUtente;
	}

	private FileSystemUtil controllaDimensioniZip(List<ApiError> apiErrors, File file) {
		CommaxParametri cmxPar = comunicazioneDad.getParametroCommaxById(ConstantsCommax.MAX_FILE_DIMENSION_IN_KB);
		int maxDimFileDB = Integer.parseInt(cmxPar.getValoreParametro());
		cmxPar = comunicazioneDad.getParametroCommaxById(ConstantsCommax.MAX_NUMBER_OF_FILE_ADMITTED_IN_ZIP);
		int maxNumFilesFileDB = Integer.parseInt(cmxPar.getValoreParametro());
		FileSystemUtil fs = new FileSystemUtil();
		if (!fs.isFileAmmessoPerDimensioniEPerNumeroDiXMLContenuti(file, maxDimFileDB, maxNumFilesFileDB)) {
			String msg = "Il file inviato ha dimensioni superiori a quelle consentite, oppure contiene piu' di " + maxNumFilesFileDB + " file xml";
			apiErrors.add(new ApiError("CMX-VAL-E-0007", msg));
		}
		return fs;
	}

	private String controllaEstensioneFile(List<ApiError> apiErrors, String nomeFile) {
		String estensioneFile = CommaxHelper.restituisciEstensioneFileUpload(nomeFile);
		if (estensioneFile == null) {
			apiErrors.add(MsgCommax.COMXVAL0003.getError());
		}
		return estensioneFile;
	}

	private void controllaSeFileValorizzato(List<ApiError> apiErrors, byte[] fileInByte) {
		if (fileInByte.length == 0 || fileInByte == null) {
			apiErrors.add(MsgCommax.COMXVAL0002.getError());
		}
	}

	private Long controllaCrcSeNecessario(CommaxHelper ch, List<ApiError> apiErrors, Boolean isVerifica, File file) {
		Long crcCalcolato = null;
		try {
			if(!isVerifica) {
				crcCalcolato = ch.calcolaCRC(file);
				if (CommaxHelper.isControlloSuCrcDelFileUploadatoAbilitato(comunicazioneDad.getParametroCommaxById(ConstantsCommax.IS_CONTROLLO_CRC_ABILITATO))) {
	
					Long countIdUplDocumenti = comunicazioneDad.existCRCUplDocumenti(crcCalcolato.longValue());
					if (countIdUplDocumenti.longValue() > 0) {
						apiErrors.add(MsgCommax.COMXVAL0005.getError());
					}
				} 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return crcCalcolato;
	}

	private String scriviFileSuFileSystem(CommaxHelper ch, List<ApiError> apiErrors, String nomeFile,
			byte[] fileInByte) {
		Timestamp timestamp = comunicazioneDad.getNowFromDB();
		String basePath = configurationHelper.getProperty(ConfigurationValue.FILE_SYSTEM);
		String nomeFileSalvatoCorrettamente = ch.salvaSingoloFile(basePath, timestamp, fileInByte, nomeFile);
		if ( nomeFileSalvatoCorrettamente == null ) {
			apiErrors.add(MsgCommax.COMXVAL0004.getError());
		}
		return nomeFileSalvatoCorrettamente;
	}
	private DepositoCommax creaEPopolaDepositoCommaxDTO (EsitoEstrazioneFilesDaZip esitoEstrazione, Ruolo ruolo ,UplDocumenti comDUplDocumentiDTO ,String mittente){
		DepositoCommax dto = new DepositoCommax();
		dto.setBFileCommax(esitoEstrazione.getContenutoDelFileInFormatoXML().getBytes());
		dto.setCfUtente(ruolo.getCodiceFiscaleUtente());
		dto.setIdComDUplDocumenti(new BigDecimal(comDUplDocumentiDTO.getId()));
		dto.setEmailUtente(mittente);
		dto.setDescUtente(ruolo.getDsCognome() + " " + ruolo.getDsNome());
		dto.setStato("N");
		dto.setStatoInvio("N");
		Date dataOggi = new Date();
		dto.setDInserim(dataOggi);
		dto.setDTrattamento(dataOggi);
		dto.setSeqElabCommax(BigDecimal.ONE);
		dto.setNomeFileXmlSingolo(StringUtils.substring(esitoEstrazione.getNomeFile(),0,299));
		return dto;
	}
}
