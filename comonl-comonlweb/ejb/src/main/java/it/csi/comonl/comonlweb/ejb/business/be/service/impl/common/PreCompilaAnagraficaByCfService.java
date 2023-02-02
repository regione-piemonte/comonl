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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.common;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.common.PreCompilaAnagraficaByCfRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.common.PreCompilaAnagraficaByCfResponse;
import it.csi.comonl.comonlweb.ejb.exception.NotFoundException;
import it.csi.comonl.comonlweb.ejb.util.Validazioni;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.common.AnagraficaGenerica;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlClassUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlDateUtils;


/**
 * PreComPilaAnagrafica
 */
public class PreCompilaAnagraficaByCfService extends BaseCommonService<PreCompilaAnagraficaByCfRequest, PreCompilaAnagraficaByCfResponse> {

	private DecodificaDad decodificaDad;
	private String cf;

	
	public PreCompilaAnagraficaByCfService(ConfigurationHelper configurationHelper, CommonDad commonDad, DecodificaDad decodificaDad) {
		super(configurationHelper, commonDad);
		this.decodificaDad = decodificaDad;
	}
	
	@Override
	protected void checkServiceParams() {
		cf = request.getCf();
		checkNotNull(cf, "cf");
	}
	
	@Override
	protected void execute() {
		
		List<ApiError> apiErrors = new ArrayList<>();
		
		Validazioni validatore = new Validazioni();
		
		//TODO da implementare una volta ottenuti i controlli
		
		if(!validatore.isValidCodiceFiscale(cf)) {
			/*ApiError error = new ApiError();
			error.setErrorMessage(MsgComonl.COMCOME0005.getMessage());*/
			apiErrors.add( new ApiError(MsgComonl.COMCOME0005.getCode(),MsgComonl.COMCOME0005.getMessage()));
			response.setApiErrors(apiErrors);
			
		}
		
		AnagraficaGenerica anagrafica = new AnagraficaGenerica();
		anagrafica.setCf(cf);
		setSessoByCf(anagrafica);
		setdataNascitaByCf(anagrafica);
		setLuogoNascita(anagrafica,apiErrors);
		if(apiErrors.size() > 0) {
			response.setApiErrors(apiErrors);
		}
		
		response.setAnagrafica(anagrafica);
		
	}
	
	
	private void setSessoByCf(AnagraficaGenerica anagrafica) { 
		try {
		String subStr = anagrafica.getCf().substring(9, 11);
		int parsStr = Integer.parseInt(subStr);
		if(parsStr > 40) {
			anagrafica.setSesso("F");
		}else {
			anagrafica.setSesso("M");
		}
		}
		catch (Exception err) {
			log.info(ComonlClassUtils.truncClassName(getClass()), "[setSessoByCf: senno no identificabile dal codice fiscale]");
		}
	}
	
	private void setdataNascitaByCf(AnagraficaGenerica anagrafica) {
		String cf = anagrafica.getCf();
		int anno = Integer.parseInt(cf.substring(6, 8));
		int giorno = Integer.parseInt(cf.substring(9,11));
		String giornoCompleto = new String("");
		if (giorno > 40) {
	       giorno -= 40;
	       giornoCompleto = "0" + giorno;
		}else {
			giornoCompleto = giorno+"";
		}
		String annoCompleto = new String("");;
		if(anno < 20) {
			annoCompleto = "20"+anno;
		}else {
			annoCompleto = "19"+anno;
		}
		String mese = ComonlDateUtils.MESI_CF.get(cf.substring(8,9));
		Date dataNascita = ComonlDateUtils.parseDate(giornoCompleto+"/"+mese+"/"+annoCompleto);
		anagrafica.setDataNascita(dataNascita);
		
	}
	
	private void setLuogoNascita(AnagraficaGenerica anagrafica,List<ApiError> apiErrors)throws NotFoundException{
		String cf = anagrafica.getCf();
		ApiError error = new ApiError();
		error.setCode("PRE-COMPILA-CODICE-FISCALE");
		 DecodificaGenerica decodificaGenerica = new DecodificaGenerica();
	    if ( cf.charAt(11) == 'Z' ||  cf.charAt(11) == 'z') {
	      String codStatoEstero = cf.substring(11, 15);
	      if (codStatoEstero != null && codStatoEstero.trim().length()>0) {
	        decodificaGenerica.setCodDecodifica(codStatoEstero);
	        List<DecodificaGenerica> res = decodificaDad.getStatiEsteriDecodifica(decodificaGenerica);
	        if(res != null && res.size() == 1) {
	        	StatiEsteri statiEsteri = new StatiEsteri();
	        	statiEsteri.setId(res.get(0).getIdDecodifica());
	        	statiEsteri.setCodNazioneMin(res.get(0).getCodDecodifica());
	        	statiEsteri.setDsStatiEsteri(res.get(0).getDsDecodifica());
	        	anagrafica.setStatiEsteri(statiEsteri);
	        }else {
	        	error.setErrorMessage("Codice fiscale non valido, stato estero con codice ministeriale: "+codStatoEstero+" non trovato!");
	        	apiErrors.add(error);
	        	return;
	        }
	      }
	    }else {
	    	String codComune = cf.substring(11, 15);
	    	 if (codComune != null && codComune.trim().length()>0) {
	    		 decodificaGenerica.setCodDecodifica(codComune);
	    		 Comune comune;
	    		 
	    			 comune = decodificaDad.getComuneByCodComuneMinNoExceptin(codComune);
		    			if(comune != null) {
			    			 anagrafica.setComune(comune);
		    			}else {
		    				 error.setErrorMessage("Codice fiscale non valido, comune con codice ministeriale: "+codComune+" non trovato!");
			    			 apiErrors.add(error);
			    			 return;
		    			}
	    			
	    	 }
	    }
	}

}
