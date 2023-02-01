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
package it.csi.comonl.comonlweb.ejb.business.be.dad;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDComunicazioneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDUplDocumentiDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDUserAccessLogDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComRRegTracciatoContrattoDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComRTipolTirocCatTirocDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTCommaxParametriDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTComonlsParametriDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTListaErroriFzDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.EntityPersonalizzazioneDao;
import it.csi.comonl.comonlweb.ejb.entity.ComDUserAccessLog;
import it.csi.comonl.comonlweb.ejb.entity.ComRDepCommaxComunic;
import it.csi.comonl.comonlweb.ejb.entity.ComRRegTracciatoContratto;
import it.csi.comonl.comonlweb.ejb.entity.ComTCommaxParametri;
import it.csi.comonl.comonlweb.ejb.mapper.ComonlMappers;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ParametriConstants;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DepCommaxComunic;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Personalizzazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.UserAccessLog;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CommaxParametri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.ComonlsParametri;

@ApplicationScoped
public class CommonDad extends BaseDad {

	@Inject
	private ComDUserAccessLogDao comDUserAccessLogDao;

	@Inject
	private ComTComonlsParametriDao comTComonlsParametriDao;

	@Inject
	private ComTListaErroriFzDao comTListaErroriFzDao;

	@Inject
	private ComTCommaxParametriDao comTCommaxParametriDao;

	@Inject
	private ComDComunicazioneDao comDComunicazioneDao;

	@Inject
	private ComDUplDocumentiDao comDUplDocumentiDao;
	
	@Inject
	private ComRRegTracciatoContrattoDao comRRegTracciatoContrattoDao;
	
	@Inject
	private ComRTipolTirocCatTirocDao comRTipolTirocCatTirocDao;
	
	@Inject
	private  EntityPersonalizzazioneDao entityPersonalizzazioneDao;

	public UserAccessLog insertUserAccessLog(UserAccessLog loUser) {
		ComDUserAccessLog comDloUser = ComonlMappers.USER_ACCESS_LOG.toEntity(loUser);
		comDloUser = comDUserAccessLogDao.insert(comDloUser);
		loUser.setId(comDloUser.getId() != null ? comDloUser.getId().intValue() : null);
		return loUser;
	}

	public ComonlsParametri getParametroByDescrizione(String nomeParam) {
		Optional<ComonlsParametri> entities = comTComonlsParametriDao.findByDescrizioneParametro(nomeParam)
				.map(ComonlMappers.COMONLS_PARAMETRI::toModel);
		if (entities.isPresent()) {
			return entities.get();
		}
		return null;
	}

	public String getDescrizioneErroreCommaxById (long id) {
		return comTListaErroriFzDao.getErrroById(id);
	}

	public CommaxParametri getParametroCommaxById(Long key) {
		Optional<CommaxParametri> entities = comTCommaxParametriDao.findOne(key).map(ComonlMappers.COMMAX_PARAMETRI::toModel);
		if (entities.isPresent()) {
			return entities.get();
		}
		return null;
	}

	public boolean isCodiceComunicazionePresenteSuDB (String codiceComunicazione) {
		List<Long> idCo = comDComunicazioneDao.getIdComunicazioneByCodReg(codiceComunicazione);
		if (idCo.size() > 0) {
			return true;
		}
		return false;
	}

	public Date getDataRicezioneUplDocumentiById (Long idComDUplDocumenti) {
		return comDUplDocumentiDao.getDatRicezioneByIdUplDocumenti(idComDUplDocumenti);
	}

	public List<ComRRegTracciatoContratto> findByCodContrattoTipoTracciatoTipoCo (String codContratto, String tipoTracciato, String tipoComunicazione) {
		return comRRegTracciatoContrattoDao.findByCodContrattoTipoTracciatoTipoCo(codContratto, tipoTracciato, tipoComunicazione);
	}

	public boolean findCombinazioneTipologiaTirocinioCategoriaTirocinante(Long idTipologiaTirocinio, Long idcodiceCategTirocinante) {

		if (comRTipolTirocCatTirocDao.findCombinazioneTipologiaTirocinioCategoriaTirocinante(idTipologiaTirocinio, idcodiceCategTirocinante) != null) {
			return true;
		}
		return false;
	}

	public Personalizzazione getPersonalizzazioneByPv(String pv) {
		Optional<Personalizzazione> entities = entityPersonalizzazioneDao.findOne(pv).map(ComonlMappers.PERSONALIZZAZIONE::toModel);
		if (entities.isPresent()) {
			return entities.get();
		}
		return null;
	}
	public CommaxParametri updateUltimaRitrasmissione(CommaxParametri model) {
		if(model.getId() == ParametriConstants.RITRASMISSIONI_ORA_ULTIMA_RITRASMISSIONE) {
			ComTCommaxParametri entity = ComonlMappers.COMMAX_PARAMETRI.toEntity(model);
			entity = comTCommaxParametriDao.update(entity);
			return ComonlMappers.COMMAX_PARAMETRI.toModel(entity);
		}
		return model;
	}
	
	public boolean tryLockSemaforo() {
		return comTCommaxParametriDao.tryLockSemaforo();
	};
	
	public boolean unLockSemaforo(){
		return comTCommaxParametriDao.unLockSemaforo();
	};
}
