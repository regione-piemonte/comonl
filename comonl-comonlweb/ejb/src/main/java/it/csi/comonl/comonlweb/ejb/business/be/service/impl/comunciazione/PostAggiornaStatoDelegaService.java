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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DelegaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostAggiornaStatoDelegaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostDelegaResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Delega;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Personalizzazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.StatoDelega;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.ComonlsParametri;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * PostRicercaProspetto
 */
public class PostAggiornaStatoDelegaService
		extends BaseDelegaService<PostAggiornaStatoDelegaRequest, PostDelegaResponse> {

	private Delega delega;
	private CommonDad commonDad;

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param prospettoDad        the DAD for the prospetto
	 */
	public PostAggiornaStatoDelegaService(ConfigurationHelper configurationHelper, DelegaDad delegaDad, DecodificaDad decodificaDad, CommonDad commonDad) {
		super(configurationHelper, delegaDad, decodificaDad);
		this.commonDad = commonDad;
	}

	@Override
	protected void checkServiceParams() {
		delega = request.getDelega();
		checkNotNull(delega, "delega", true);
	}

	@Override
	protected void execute() {
		log.debug("execute----------->", "Entro nel metodo execute");

		Delega delegaDb = delegaDad.getDelegaById(delega.getId());


		StatoDelega statoDelega = delega.getStatoDelega();
		if (ComonlConstants.STATO_DELEGA_ANNULLATA_2.equals(statoDelega.getId()) ||
				ComonlConstants.STATO_DELEGA_REVOCATA_4.equals(statoDelega.getId()))
			delegaDb.setDtRevoca(new Date());

		delegaDb.setStatoDelega(statoDelega);
		
		if (ComonlConstants.STATO_DELEGA_VALIDATA_3.equalsIgnoreCase(statoDelega.getId())) {
			String pv = delega.getPvDelegato();
			
			ComonlsParametri  param= commonDad
					.getParametroByDescrizione(ComonlConstants.COMONLS_PARAMETRI_DELEGA_LISTAPV);
			if (param!=null && StringUtils.isNotBlank(param.getValoreParametro())) {
				String[] listPv = param.getValoreParametro().split(";");
				if (!Arrays.asList(listPv).contains(pv))
				{
					pv = "XX";
				}
			}
			
			Long val = decodificaDad.nextSequenceVal("SEQ_PIEMONTE_" + pv.trim().toUpperCase());
			delegaDb.setNumeroProtocollo(val);
			
			Date today = new Date();
			Calendar dataProt = GregorianCalendar.getInstance();
			dataProt.setTime(today);
			delegaDb.setAnnoProtocollo(new BigDecimal(dataProt.get(Calendar.YEAR)));
			delegaDb.setDtProtocollo(today);
			
			
			Personalizzazione personalizzazione = new Personalizzazione();
			personalizzazione.setId(pv);
			delegaDb.setPersonalizzazione(personalizzazione);

		}
		
		delegaDb = delegaDad.updateDelega(delegaDb);
		response.setDelega(delegaDb);
	}
	

}
