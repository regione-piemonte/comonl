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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.comunciazione;


import java.util.ArrayList;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDatoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ControlliDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DatoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DatoreSedeDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.LavoratoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.RapportoLavoratoreDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.SedeLavoroDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.comunicazione.PostComunicazioneUrgenzaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.comunicazione.PostComunicazioneUrgenzaResponse;
import it.csi.comonl.comonlweb.ejb.util.ValidatorComunicazioneUrgenza;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ComunicazioneDatore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ComunicazioneDatorePK;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ComunicazioneUrgHolder;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DatoreSede;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DatoreSedePK;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RapportoLavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RapportoLavoratorePK;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.WrapperComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cpi;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoAcquisizione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazioneTu;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoDatore;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoProvenienza;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoRapportoAzienda;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoTracciato;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

/**
 * PostRicercaProspetto
 */
public class PostComunicazioneUrgenzaService
		extends BaseComunicazioneService<PostComunicazioneUrgenzaRequest, PostComunicazioneUrgenzaResponse> {
	
	
	private WrapperComunicazione wrapperComunicazione;
	
	private DecodificaDad decodificaDad;
	
	private DatoreDad datoreDad;
	
	private LavoratoreDad lavoratoreDad;
	
	private RapportoDad rapportoDad;
	
	private SedeLavoroDad sedeLavoroDad;
	
	protected final ControlliDad controlliDad;
	
	//private SedeLavore
	
	//private Sede

	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * @param prospettoDad        the DAD for the prospetto
	 */
	public PostComunicazioneUrgenzaService(ConfigurationHelper configurationHelper, 
			ComunicazioneDad comunicazioneDad,
			DecodificaDad decodificaDad,
			DatoreDad datoreDad,
			LavoratoreDad lavoratoreDad,
			RapportoDad rapportoDad,
			SedeLavoroDad sedeLavoroDad,
			ControlliDad controlliDad
			) {
		super(configurationHelper, comunicazioneDad);
		this.decodificaDad = decodificaDad;
		this.datoreDad = datoreDad;
		this.lavoratoreDad = lavoratoreDad;
		this.rapportoDad = rapportoDad;
		this.sedeLavoroDad = sedeLavoroDad;
		this.controlliDad = controlliDad;
	}

	@Override
	protected void checkServiceParams() {
		wrapperComunicazione = request.getWrapperComunicazione();
		checkNotNull(wrapperComunicazione.getComunicazione(), "comunicazione", true);
    }

	@Override
	protected void execute() {
		log.debug("execute----------->", "Entro nel metodo execute");
		Comunicazione comunicazione = setDeafultPerRuolo(wrapperComunicazione.getComunicazione(), wrapperComunicazione.getRuolo());
		List<ApiError> apiErrors = new ArrayList<ApiError>();
		List<ApiError> apiWarnings = new ArrayList<ApiError>();

		TipoComunicazione tipoComunicazioneUrgenza = new TipoComunicazione();
		tipoComunicazioneUrgenza.setId(ComonlConstants.TIPO_COMUNICAZIONE_URGENZA_ID);
		comunicazione.setTipoComunicazione(tipoComunicazioneUrgenza);
		
		TipoComunicazioneTu tipoComunicazioneTu = new TipoComunicazioneTu();
		tipoComunicazioneTu.setId(ComonlConstants.TIPO_COMUNICAZIONE_TU_COMUNICAZIONE_OBBLIGATORIA_ID);
		comunicazione.setTipoComunicazioneTu(tipoComunicazioneTu);
		
		TipoTracciato tipoTracciatoUrgenza = new TipoTracciato();
		tipoTracciatoUrgenza.setId(ComonlConstants.TIPO_TRACCIATO_URGENZA_ID);
		comunicazione.setTipoTracciato(tipoTracciatoUrgenza);
		
		TipoProvenienza tipoProvenienza = new TipoProvenienza();
		tipoProvenienza.setId(ComonlConstants.TIPO_PROVENIENZA_COMONL_VER_4);
		comunicazione.setTipoProvenienza(tipoProvenienza);
		
		comunicazione.setFlgRettifica("N");
		comunicazione.setFlgCausaForzamaggiore("N");
		comunicazione.setCfImpresa(comunicazione.getDatoreAttuale().getCodiceFiscale());
		TipoAcquisizione tipoAcquisizione = new TipoAcquisizione();
		tipoAcquisizione.setId(ComonlConstants.TIPO_ACQUISIZIONE_COMUNICAZIONE_COMPLETA_ID);
		comunicazione.setTipoAcquisizione(tipoAcquisizione);
		
		StatoComunicazione statoComunicazione = new StatoComunicazione();
		statoComunicazione.setId(ComonlConstants.STATO_COMUNICAZIONE_INSERITA_ID);
		comunicazione.setStatoComunicazione(statoComunicazione);
		
		
		ValidatorComunicazioneUrgenza validator = new ValidatorComunicazioneUrgenza(wrapperComunicazione,comunicazioneDad,decodificaDad,controlliDad);
		
		if (!validator.isOk())
		{
			response.setApiErrors(validator.getApiErrors());
			return;
		}
		comunicazione.setDataRiferimento(comunicazione.getRapporto().getDtInizioRapporto());
		SedeLavoro sedeOperativa = comunicazione.getDatoreAttuale().getSedeOperativa();
		comunicazione.setProvincia(sedeOperativa.getComune().getProvincia());
		comunicazione.setCpi(sedeOperativa.getComune().getCpi());
		Long idComunicazionePrec = comunicazione.getIdComDComunicPrecedente();
		if(idComunicazionePrec != null) {
			Comunicazione comunicazionePrecedente = comunicazioneDad.getComunicazioneById(idComunicazionePrec);
			comunicazionePrecedente.setFlgCurrentRecord(null);
			comunicazioneDad.updateComunicazione(comunicazionePrecedente);
		}


		InsertComunicazioneService insertComunicazioneService = new InsertComunicazioneService(configurationHelper,
				comunicazioneDad, datoreDad, null, sedeLavoroDad, rapportoDad, lavoratoreDad,null,null);
		comunicazione = insertComunicazioneService.insertComunicazioneUrgenza(comunicazione);

		response.setIdComunicazione(comunicazione.getId());

	}

}
