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
package it.csi.comonl.comonlweb.ejb.business.be.service.impl.silp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.service.impl.base.BaseService;
import it.csi.comonl.comonlweb.ejb.business.be.service.request.silp.PutAziendaRequest;
import it.csi.comonl.comonlweb.ejb.business.be.service.response.silp.PutAziendaResponse;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationHelper;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.LegaleRappr;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.dto.silp.DatiAzienda;
import it.csi.comonl.comonlweb.lib.external.impl.silp.AziendaHelperImpl;
import it.csi.comonl.comonlweb.lib.external.impl.silp.invoker.SilpBusinessException;
import it.csi.comonl.comonlweb.lib.external.impl.silp.invoker.SilpRestAdapter;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.ApiMessage;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;

public class PutAziendaService extends BaseService<PutAziendaRequest, PutAziendaResponse> {
	/**
	 * Constructor
	 * 
	 * @param configurationHelper the helper for the configuration
	 * 
	 */
	public PutAziendaService(ConfigurationHelper configurationHelper) {
		super(configurationHelper);
	}

	@Override
	protected void execute() {
		DatiAzienda aziendaDaInserireOModificare = request.getDatiAzienda();
		List<ApiError> apiErrors = new ArrayList<ApiError>();
		BigDecimal id = new BigDecimal(0);

		try {
			id = SilpRestAdapter.getInstance().saveAzienda(getUtente().getCodiceFiscale(),
					aziendaDaInserireOModificare);

		} catch (SilpBusinessException sex) {
			List<ApiMessage> listaMsgSilp = sex.getErrors();
			if (!listaMsgSilp.isEmpty()) {
				for (ApiMessage apiMessage : listaMsgSilp) {
					ApiError loErr = new ApiError();
					loErr.setCode(apiMessage.getCode());
					loErr.setErrorMessage(apiMessage.getMessage());
					apiErrors.add(loErr);
				}
			}
		} catch (Exception err) {
			err.printStackTrace();
			apiErrors.add(MsgComonl.COMCOME0001.getError());
		}

		if (id.compareTo(BigDecimal.ZERO) > 0) {
			// in questo caso il salvataggio dell'azienda e della sede sono andati a buon
			// fine quindi proseguo con la chiamata
			// al servizio per salvare il referente
			try {
				if (null != aziendaDaInserireOModificare.getIdAziendaSilp()) {
					id = new BigDecimal(aziendaDaInserireOModificare.getIdAziendaSilp());
				}

				AziendaHelperImpl aziendaHelperImpl = new AziendaHelperImpl();
				DatiAzienda laAziendaInseritaOModificata = aziendaHelperImpl.getAzienda(id);

				// a questo punto bisogna gestire il rappresentante legale
				if (null != aziendaDaInserireOModificare.getIlLegaleRapprDellaSedeLegale()) {
					LegaleRappr ilLegaleDaInserire = aziendaDaInserireOModificare.getIlLegaleRapprDellaSedeLegale();
					if (ComonlUtility.isNotVoid(ilLegaleDaInserire.getDsCognome())
							|| ComonlUtility.isNotVoid(ilLegaleDaInserire.getDsNome())
							|| ComonlUtility.isNotVoid(ilLegaleDaInserire.getDtNascita())
							|| (null != ilLegaleDaInserire.getStatiEsteri()
									&& ComonlUtility.isNotVoid(ilLegaleDaInserire.getStatiEsteri().getId()))
							|| (null != ilLegaleDaInserire.getComune()
									&& ComonlUtility.isNotVoid(ilLegaleDaInserire.getComune().getId()))
							|| (null != ilLegaleDaInserire.getCittadinanza()
									&& ComonlUtility.isNotVoid(ilLegaleDaInserire.getCittadinanza().getId()))) {
						ilLegaleDaInserire
								.setIdSedeAssociata(laAziendaInseritaOModificata.getSedeLegale().getIdSedeSilp());
						SilpRestAdapter.getInstance().saveReferente(getUtente().getCodiceFiscale(), ilLegaleDaInserire);
					} else {
						// in questo caso bisogna eventualmente cancellare il rappresentante legale
						// precedentemente associato
						if (null != ilLegaleDaInserire.getIdLegaleRapprSilp()) {
							SilpRestAdapter.getInstance().deleteReferente(getUtente().getCodiceFiscale(),
									ilLegaleDaInserire.getIdLegaleRapprSilp());
						}
					}
				}
				response.setDatiAzienda(laAziendaInseritaOModificata);
			} catch (SilpBusinessException sex) {
				List<ApiMessage> listaMsgSilp = sex.getErrors();
				if (!listaMsgSilp.isEmpty()) {
					for (ApiMessage apiMessage : listaMsgSilp) {
						ApiError loErr = new ApiError();
						loErr.setCode(apiMessage.getCode());
						loErr.setErrorMessage(apiMessage.getMessage());
						apiErrors.add(loErr);
					}
				}
			} catch (Exception err) {
				err.printStackTrace();
				ApiError loErr = new ApiError();
				loErr.setCode(MsgComonl.COMCOMW1202.getCode());
				loErr.setErrorMessage(MsgComonl.COMCOMW1202.getMessage());
				apiErrors.add(loErr);

			}
		}

		response.setApiErrors(apiErrors);
	}
}
