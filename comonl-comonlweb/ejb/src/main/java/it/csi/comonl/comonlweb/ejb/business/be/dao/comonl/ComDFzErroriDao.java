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

import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dao.BaseEntityDao;
import it.csi.comonl.comonlweb.ejb.entity.ComDFzErrori;
import it.csi.comonl.comonlweb.ejb.util.commax.vo.ErroriFileVO;

/**
 * Data Access Object interface for the entity ComDFzErrori
 */
public interface ComDFzErroriDao extends BaseEntityDao<Long, ComDFzErrori> {

	List<ComDFzErrori> findByField(String field);
	
	List<ErroriFileVO> getElencoErroriFzByIdUplDoc (Long idComDUplDocumenti) ;
}