/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - EJB submodule
 * %%
 * Copyright (C) 2022 CSI Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.ejb.util.commax.mail;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.util.commax.util.Util;
import it.csi.comonl.comonlweb.ejb.util.commax.vo.ErroriFileVO;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ElaboratiOk;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.UplDocumenti;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;

public class GeneratoreRapportoElabHelper {

	protected final LogUtil log = new LogUtil(GeneratoreRapportoElabHelper.class);
	
	private UplDocumenti esitoElaborazione = null;
	private List<ErroriFileVO> elencoErroriFunzionali = null;
	private File fileZipRapporto = null;
	private String fileConPathScarti = null;
	private String fileConPathElaboratiOK = null;
	private List<ElaboratiOk> elencoElaborazioniOK = null;
	private String totaleElaborazioniOK = null;
	
	public File generaFileRapporto (Long idComDUplDocumenti , 
									String filePath,
									String fileNameElaboratiOK,
									String fileNameScarti, 
									ComunicazioneDad comunicazioneDad) throws FileNotFoundException, UnsupportedEncodingException, IOException{
		
		
		 String fileNameAllegatoZip= "allegatoElaborazione.zip";
		 GestoreArchivioZip archivioZip = new GestoreArchivioZip();

		 reperisciDati(idComDUplDocumenti, comunicazioneDad);
		 
		 File [] fileDaZippare = new File[2];
		 

		 if(elencoElaborazioniOK.size() > 0){
			 fileConPathElaboratiOK = filePath + "/" + fileNameElaboratiOK;
			 creaFileElaboratiOK(fileConPathElaboratiOK);
			 fileDaZippare[0] = new File(fileConPathElaboratiOK);
			 
		 }
		 		 
	 
		 if(esitoElaborazione.getTotComScar()!= null && esitoElaborazione.getTotComScar().longValue() > 0){			 
			 fileConPathScarti = filePath + "/" + fileNameScarti;
			 creaFileScarti(fileConPathScarti);			 
			 fileDaZippare[1] = new File(fileConPathScarti);
		 }

		 
         try {
        	 if(elencoElaborazioniOK.size() > 0 || (esitoElaborazione.getTotComScar()!= null && esitoElaborazione.getTotComScar().longValue() > 0) )
        		 fileZipRapporto = archivioZip.creaZip(fileDaZippare, filePath, fileNameAllegatoZip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return fileZipRapporto;
		
	}




	private void creaFileElaboratiOK(String fileConPathElaborati) throws UnsupportedEncodingException, IOException {
		
		Iterator it = null;

		StringBuffer testoFileElaboratiOK = new StringBuffer("-------------------- Rapporto Elaborati Correttamente --------------------\r\n");
		 

		testoFileElaboratiOK.append("Totale file elaborati " + elencoElaborazioniOK.size())
							.append("\r\n");

		it = elencoElaborazioniOK.iterator();
		while(it.hasNext()){
			ElaboratiOk elabOK = (ElaboratiOk)it.next();
			
			testoFileElaboratiOK.append("\r\nNOME FILE : ").append(Util.estraiNomeFile(elabOK.getNomeFileXml()))
								.append("\t ");
						

            if (elabOK.getCodiceComunicazioneReg() != null)
            	testoFileElaboratiOK.append("ID Regionale: ").append(elabOK.getCodiceComunicazioneReg())
            						.append("\t ");
            else
            	testoFileElaboratiOK.append("ID Regionale: NON PRESENTE")
            						.append("\t ");
            
            if(elabOK.getNroProtCom() != null){
            	testoFileElaboratiOK.append("Numero protocollo prov: ").append(elabOK.getNroProtCom())
            						.append("\t ");
            }
            else{
            	testoFileElaboratiOK.append("Numero protocollo prov: NON PRESENTE")
            						.append("\t ");
            }
            
            testoFileElaboratiOK.append("Anno protocollo prov: ").append(elabOK.getAnnoProtCom())
            					.append("\t ");
            testoFileElaboratiOK.append("Prov. competenza: ").append(elabOK.getPv())
              					.append("\t ");
            if(elabOK.getTipoCom()!= null){
            	testoFileElaboratiOK.append("Tipo tracciato: ").append(elabOK.getTipoCom())
            						.append("\t ");
            }
            else{
            	testoFileElaboratiOK.append("Tipo tracciato: NON PRESENTE")
            						.append("\t ");
            }
            
			
		}

		FileOutputStream fileElaboratiOK = new FileOutputStream(fileConPathElaborati);

		fileElaboratiOK.write(testoFileElaboratiOK.toString().getBytes("utf-8"));
		fileElaboratiOK.close();
	}




	private void creaFileScarti(String fileConPathScarti) throws FileNotFoundException, IOException, UnsupportedEncodingException {
		ErroriFileVO erroreFunzionale = null;
		Iterator it = null;

		StringBuffer testoFileScarti = new StringBuffer("-------------------- Rapporto Elaborati Scartati --------------------");


		testoFileScarti.append("\r\n");
		testoFileScarti.append("Elenco file scartati: ");
		testoFileScarti.append("\r\n");
		//testoFileScarti.append("\r\n\r\n");

		it = elencoErroriFunzionali.iterator();
		while(it.hasNext()){
			erroreFunzionale = (ErroriFileVO)it.next();

			testoFileScarti.append(Util.estraiNomeFile(erroreFunzionale.getNomeFile()))
			.append("\t " + erroreFunzionale.getCfLavoratore())
			.append("\t Motivo Scarto: ")
			.append(erroreFunzionale.getDescrizioneErrore())
			.append("\r\n\r\n");
		}


		FileOutputStream fileScarti = new FileOutputStream(fileConPathScarti);

		fileScarti.write(testoFileScarti.toString().getBytes("utf-8"));
		fileScarti.close();
	}




	public void reperisciDati(Long idComDUplDocumenti, ComunicazioneDad comunicazioneDad){

		try {

			esitoElaborazione = comunicazioneDad.getUplDocumentiById(idComDUplDocumenti);

			elencoElaborazioniOK = comunicazioneDad.getEsitoComDElaboratiOkByIdUplDoc(idComDUplDocumenti);

			totaleElaborazioniOK = (elencoElaborazioniOK != null && elencoElaborazioniOK.size() > 0 ? String.valueOf(elencoElaborazioniOK.size()) : "0");

			if(esitoElaborazione.getTotComScar()!= null && esitoElaborazione.getTotComScar().longValue() > 0){

				elencoErroriFunzionali = comunicazioneDad.getElencoErroriFzByIdUplDoc(idComDUplDocumenti);

			}
		} catch (Exception e1) {
			log.debug("reperisciDati", e1.getMessage());
		}

	}


	public String getTotaleElaborazioni(){
		if(esitoElaborazione != null && esitoElaborazione.getTotComElab()!= null){
			return esitoElaborazione.getTotComElab().toString();
		}
			return "0";
	}

	public String getTotaleScarti(){
		if(esitoElaborazione != null && esitoElaborazione.getTotComScar()!= null){
			return esitoElaborazione.getTotComScar().toString();
		}
			return "0";
	}

	public String getNomeFileUpload(){
		if(esitoElaborazione != null && esitoElaborazione.getNomeFileUpload()!= null){
			return esitoElaborazione.getNomeFileUpload();
		}
			return "";
	}
	/// getter and setter




	public UplDocumenti getEsitoElaborazione() {
		return esitoElaborazione;
	}




	public void setEsitoElaborazione(UplDocumenti esitoElaborazione) {
		this.esitoElaborazione = esitoElaborazione;
	}




	public List<ErroriFileVO> getElencoErroriFunzionali() {
		return elencoErroriFunzionali;
	}




	public void setElencoErroriFunzionali(List<ErroriFileVO> elencoErroriFunzionali) {
		this.elencoErroriFunzionali = elencoErroriFunzionali;
	}




	public File getFileZipRapporto() {
		return fileZipRapporto;
	}




	public void setFileZipRapporto(File fileZipRapporto) {
		this.fileZipRapporto = fileZipRapporto;
	}




	public String getFileConPathScarti() {
		return fileConPathScarti;
	}




	public void setFileConPathScarti(String fileConPathScarti) {
		this.fileConPathScarti = fileConPathScarti;
	}




	public String getFileConPathElaboratiOK() {
		return fileConPathElaboratiOK;
	}




	public void setFileConPathElaboratiOK(String fileConPathElaboratiOK) {
		this.fileConPathElaboratiOK = fileConPathElaboratiOK;
	}




	public List<ElaboratiOk> getElencoElaborazioniOK() {
		return elencoElaborazioniOK;
	}




	public void setElencoElaborazioniOK(List<ElaboratiOk> elencoElaborazioniOK) {
		this.elencoElaborazioniOK = elencoElaborazioniOK;
	}




	public String getTotaleElaborazioniOK() {
		return totaleElaborazioniOK;
	}




	public void setTotaleElaborazioniOK(String totaleElaborazioniOK) {
		this.totaleElaborazioniOK = totaleElaborazioniOK;
	}
	
}
