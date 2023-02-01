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
package it.csi.comonl.comonlweb.ejb.business.be.dao.comonl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import it.csi.comonl.comonlweb.ejb.business.be.dao.BaseAuditedEntityDao;
import it.csi.comonl.comonlweb.ejb.entity.ComDComunicazione;
import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRecuperoComunicazione;

/**
 * Data Access Object interface for the entity ComDComunicazione
 */
public interface ComDComunicazioneDao extends BaseAuditedEntityDao<Long, ComDComunicazione> {

	List<ComDComunicazione> findByField(String field);

	Long getIdRapportoNextVal();


	public <T extends BaseEntity<Long>> BaseEntity<Long> getTfromMin(Class<T> transcodifica, String fieldValue, Date dataRiferimento);
	public <T extends BaseEntity<Long>> BaseEntity<Long> getTfromMin(Class<T> transcodifica, String fieldValue);

	Long countRecuperoComunicazione(FormRecuperoComunicazione recuperoComunicazione);
	List<Long> getRecuperoComunicazione(FormRecuperoComunicazione recuperoComunicazione);

	List<Long> getIdComunicazioneByCodReg(String codiceRegionale);

	List<Long> getIdComunicazioneByCodRegPrec(String codiceRegionale);
	
	List<ComDComunicazione> getByIdentificativoRapporto(String codiceFiscaleDatore, String codiceFiscaleLavoratore, Date dtInizioRapporto);
	
	Optional<ComDComunicazione> getByIdRapportoLavoro(Long idRapportoLavoro);
}
