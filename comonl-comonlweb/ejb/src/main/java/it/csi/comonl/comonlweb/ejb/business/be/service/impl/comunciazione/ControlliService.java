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

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.ejb.util.controlli.CodiceFiscale;
import it.csi.comonl.comonlweb.ejb.util.exception.BusinessExceptionAware;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.common.DecodificaGenerica;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;

public class ControlliService implements BusinessExceptionAware {
	

	public ControlliService(ConfigurationHelper configurationHelper) {
	}

	public void checkCampoObbligatorio(String nomeCampo, List<ApiError> apiErrors, ApiError obbligatorieta) {
		if (ComonlUtility.isEmpty(nomeCampo.trim())) {
			apiErrors.add(obbligatorieta);
		}
	}

	public void checkComuneValidity(DecodificaDad decodificaDad, Comune comune, List<ApiError> apiErrors,
			ApiError validita, ApiError obbligatorieta) {
		if (comune != null && comune.getId() == null) {
			if (ComonlUtility.isVoid(comune.getCodComuneMin()) && ComonlUtility.isVoid(comune.getDsComTComune())) {
				apiErrors.add(obbligatorieta);
				return;
			}
			Long idComune = recuperaIdComune(decodificaDad, comune.getCodComuneMin(), comune.getDsComTComune());
			if (idComune == null) {
				apiErrors.add(validita);
				return;
			}
			comune.setId(idComune);
		}
	}

	public void checkStatoEsteroValidity(DecodificaDad decodificaDad, StatiEsteri stati, List<ApiError> apiErrors,
			ApiError validita, ApiError obbligatorieta) {
		if (stati != null && stati.getId() == null) {
			if (ComonlUtility.isVoid(stati.getCodNazioneMin()) && ComonlUtility.isVoid(stati.getDsStatiEsteri())) {
				apiErrors.add(obbligatorieta);
				return;
			}
			Long idComune = recuperaIdStato(decodificaDad, stati.getCodNazioneMin(), stati.getDsStatiEsteri());
			if (idComune == null) {
				apiErrors.add(validita);
				return;
			}
			stati.setId(idComune);

		}
	}
	

	private Long recuperaIdComune(DecodificaDad decodificaDad, String codice, String descrizione) {

		Long id = null;

		DecodificaGenerica filtro = new DecodificaGenerica();
		filtro.setCodDecodifica(codice);
		filtro.setDsDecodifica(descrizione);
		List<DecodificaGenerica> decode = decodificaDad.getComuneDecodifica(filtro);
		if (decode != null && decode.size() == 1) {
			id = decode.get(0).getIdDecodifica();
		}
		return id;
	}

	private Long recuperaIdStato(DecodificaDad decodificaDad, String codice, String descrizione) {

		Long id = null;

		DecodificaGenerica filtro = new DecodificaGenerica();
		filtro.setCodDecodifica(codice);
		filtro.setDsDecodifica(descrizione);
		List<DecodificaGenerica> decode = decodificaDad.getStatiEsteriDecodifica(filtro);
		if (decode != null && decode.size() == 1) {
			id = decode.get(0).getIdDecodifica();
		}
		return id;
	}
	
	
	
	@Override
	public void checkBusinessCondition(boolean condition, ApiError message) {
	}

	@Override
	public void checkBusinessCondition(BooleanSupplier condition, ApiError message) {
	}

	@Override
	public void checkBusinessCondition(boolean condition, Supplier<ApiError> message) {
	}

	@Override
	public void checkBusinessCondition(BooleanSupplier condition, Supplier<ApiError> message) {
	}

}
