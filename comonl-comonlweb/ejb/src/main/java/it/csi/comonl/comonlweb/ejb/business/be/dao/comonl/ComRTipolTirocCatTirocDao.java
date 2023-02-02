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
package it.csi.comonl.comonlweb.ejb.business.be.dao.comonl;

import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dao.BaseEntityDao;
import it.csi.comonl.comonlweb.ejb.entity.ComRTipolTirocCatTiroc;
import it.csi.comonl.comonlweb.ejb.entity.ComRTipolTirocCatTirocPK;

/**
 * Data Access Object interface for the entity ComRTipolTirocCatTiroc
 */
public interface ComRTipolTirocCatTirocDao extends BaseEntityDao<ComRTipolTirocCatTirocPK, ComRTipolTirocCatTiroc> {

	List<ComRTipolTirocCatTiroc> findByField(String field);
	
	ComRTipolTirocCatTiroc findCombinazioneTipologiaTirocinioCategoriaTirocinante (Long idTipologiaTirocinio, Long idcodiceCategTirocinante) ;
		
	
}
