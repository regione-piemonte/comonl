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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.silp;

import java.util.ArrayList;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.business.be.service.impl.base.BaseService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.silp.GetAziendaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.silp.GetAziendaResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Atecofin;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.NaturaGiuridica;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Provincia;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.dto.silp.DatiAzienda;
import it.csi.comonl.comonlweb.lib.dto.silp.Sede;
import it.csi.comonl.comonlweb.lib.util.ComonlClassUtils;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;
import it.csi.comonl.comonlweb.profilazione.helper.AdapterAAEP;
import it.csi.servizioaaep2.vo.AziendaAAEP;
import it.csi.servizioaaep2.vo.ListaSediAAEP;

/**
 * GetAzienda
 */
public class GetAziendaAaepService extends BaseService<GetAziendaRequest, GetAziendaResponse> {
	private static final String TIPO_SEDE_LEGALE_DESCRIZIONE = "SEDE LEGALE";
//	private static final String TIPO_SEDE_UNITA_LOCALE_AMM = "UNITA LOCALE/AMM.";

	DecodificaDad decodificaDad;

	public GetAziendaAaepService(ConfigurationHelper configurationHelper, DecodificaDad decodificaDad) {
		super(configurationHelper);
		this.decodificaDad = decodificaDad;
	}

	@Override
	protected void execute() {
		try {
			String codiceFiscale = request.getCodiceFiscale();
			/*
			 * azienda presente su aaep 07183440010
			 */
			DatiAzienda datiAzienda = new DatiAzienda();
			AdapterAAEP aaep = new AdapterAAEP();
			AziendaAAEP aziendaAAEP = (aaep).cercaPerCodiceFiscaleAAEP(codiceFiscale);
			if (aziendaAAEP != null) {
				List<Sede> elencoSedi = new ArrayList<>();
				datiAzienda = new DatiAzienda();
				datiAzienda.setDenominazioneDatoreLavoro(aziendaAAEP.getRagioneSociale());
				datiAzienda.setCfAzienda(aziendaAAEP.getCodiceFiscale());
				datiAzienda.setPartitaIva(aziendaAAEP.getPartitaIva());
				setNaturaGiuridica(datiAzienda, aziendaAAEP);
				setAteco(datiAzienda, aziendaAAEP);
				datiAzienda.setFlagArtigiana(aziendaAAEP.getFlgIterIscrAlboArt());

				for (ListaSediAAEP laSedeAAEP : aziendaAAEP.getListaSediAAEP()) {
					if (laSedeAAEP.getDescrTipoSede().equals(TIPO_SEDE_LEGALE_DESCRIZIONE)) {
						Sede laSedeLegale = new Sede();
						laSedeLegale.setSedeLegale(true);
						laSedeLegale.setSedeOperativa(false);
						settaLaSede(laSedeAAEP, laSedeLegale);
						datiAzienda.setSedeLegale(laSedeLegale);
					} else {
						Sede laSede = new Sede();
						laSede.setSedeLegale(false);
						laSede.setSedeOperativa(true);
						settaLaSede(laSedeAAEP, laSede);
						elencoSedi.add(laSede);
					}
				}
				if (ComonlUtility.isNotVoid(elencoSedi)) {
					datiAzienda.setElencoSedi(elencoSedi);
				}
				response.setDatiAzienda(datiAzienda);
			} else {
				List<ApiError> apiErrors = new ArrayList<>();
				apiErrors.add(MsgComonl.COMCOMP0002.getError());
				response.setApiErrors(apiErrors);
			}

		} catch (Exception e) {
			log.error(ComonlClassUtils.truncClassName(getClass()) + " Eccezione !!", e);
			List<ApiError> apiErrors = new ArrayList<>();
			apiErrors.add(MsgComonl.COMCOMP0002.getError());
//			apiErrors.add(MsgComonl.COMCOMP0003.getError());
			response.setApiErrors(apiErrors);
		}
	}

	private void settaLaSede(ListaSediAAEP laSedeAAEP, Sede laSedeLegale) {
		laSedeLegale.setIndirizzo(laSedeAAEP.getIndirizzo());
		Comune comune = new Comune();
		comune.setDsComTComune(laSedeAAEP.getDescrComuneUL());
		Provincia laProvinciaDelComune = new Provincia();
		laProvinciaDelComune.setDsTarga(laSedeAAEP.getSiglaProvUL());
		comune.setProvincia(laProvinciaDelComune);
		laSedeLegale.setComune(comune);
	}

	private void setAteco(DatiAzienda datiAzienda, AziendaAAEP azienda) {
		if (ComonlUtility.isNotVoid(azienda.getCodATECO2007())
				|| ComonlUtility.isNotVoid(azienda.getDescrATECO2007())) {
			Atecofin atecofin = new Atecofin();
			try {
				atecofin = decodificaDad.getAtecoByCodAtecoFin(azienda.getCodATECO2007());
			} catch (Exception e) {
				atecofin.setCodAtecofinMin(azienda.getCodATECO2007());
				atecofin.setDsComTAtecofin(azienda.getDescrATECO2007());
			} finally {
				datiAzienda.setAtecofin(atecofin);
			}

		}
	}

	private void setNaturaGiuridica(DatiAzienda datiAzienda, AziendaAAEP azienda) {
		if (ComonlUtility.isNotVoid(azienda.getIdNaturaGiuridica())
				|| ComonlUtility.isNotVoid(azienda.getDescrizioneNaturaGiuridica())) {
			NaturaGiuridica laNaturaGiuridica = new NaturaGiuridica();
			laNaturaGiuridica.setId(azienda.getIdNaturaGiuridica());
			laNaturaGiuridica.setDescrizione(azienda.getDescrizioneNaturaGiuridica());
			datiAzienda.setNaturaGiuridica(laNaturaGiuridica);
		}
	}
}
