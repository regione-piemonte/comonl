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
package it.csi.comonl.comonlweb.ejb.business.be.dao.impl.comonl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComRRapportoLavoratoreDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComRRapportoLavoratore;
import it.csi.comonl.comonlweb.ejb.entity.ComRRapportoLavoratorePK;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComRRapportoLavoratore
 */
@ApplicationScoped
public class ComRRapportoLavoratoreDaoImpl extends BaseEntityDaoImpl<ComRRapportoLavoratorePK, ComRRapportoLavoratore> implements ComRRapportoLavoratoreDao {

	@Override
	public List<ComRRapportoLavoratore> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComRRapportoLavoratore t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.id ");

		TypedQuery<ComRRapportoLavoratore> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public Optional<ComRRapportoLavoratore> findLavoratoreByIdRapporto(Long idRapporto) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComRRapportoLavoratore t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.comDRapporto.idComDRapporto", "field", idRapporto);

		jpql.append(" ORDER BY t.id ");

		TypedQuery<ComRRapportoLavoratore> query = composeTypedQuery(jpql, params);
		return query.getResultList().stream().findFirst();
	}
	
	@Override
	public List<ComRRapportoLavoratore> findLavoratoriByIdRapporto(Long idRapporto) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComRRapportoLavoratore t ");
		jpql.append(" WHERE 1=1 ");
		
		JpaQueryHelper.andFieldEquals(jpql, params, "t.comDRapporto.idComDRapporto", "idComDRapporto", idRapporto);
		
		jpql.append(" ORDER BY t.id ");
		
		TypedQuery<ComRRapportoLavoratore> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	@Override
	public ComRRapportoLavoratore getLavoratoreByIdRapporto(Long idRapporto) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComRRapportoLavoratore t ");
		jpql.append(" WHERE 1=1 ");
		
		JpaQueryHelper.andFieldEquals(jpql, params, "t.comDRapporto.idComDRapporto", "idComDRapporto", idRapporto);
		
		jpql.append(" ORDER BY t.id ");
		
		TypedQuery<ComRRapportoLavoratore> query = composeTypedQuery(jpql, params);
		return query.getSingleResult();
	}

	@Override
	public void deleteByIdLavoratore(Long idLavoratore) {
		StringBuilder sb = new StringBuilder()
				.append("DELETE FROM ComRRapportoLavoratore")
				.append(" WHERE ")
				.append(" comDLavoratore.id = :idLavoratore");
		Map<String, Object> params = new HashMap<>();
		params.put("idLavoratore", idLavoratore);
		Query query = composeQuery(sb, params);
		query.executeUpdate();
	}	
	
	/*
select *
from com_d_comunicazione c,  COM_D_RAPPORTO R, COM_R_RAPPORTO_LAVORATORE RL, com_d_lavoratore l, com_d_sede_lavoro sd, com_r_sede_lavoro_lavoratore sl
where c.id_com_t_tipo_comunicazione = 'VAR'
and c.id_com_t_tipo_trasferimento is not null
AND c.id_com_d_comunicazione=r.id_com_d_comunicazione
AND L.ID_COM_D_LAVORATORE = RL.ID_COM_D_LAVORATORE
AND RL.ID_COM_D_RAPPORTO = R.ID_COM_D_RAPPORTO
and sl.id_com_d_sede_lavoro = sd.id_com_d_sede_lavoro
and sl.id_com_d_lavoratore = l.id_com_d_lavoratore
AND c.id_com_d_comunicazione=505418	 * */
	
//	@Override
//	public List<ComRRapportoLavoratore> findLavoratoriByIdRapporto(Long idRapporto) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		StringBuilder jpql = new StringBuilder();
//		jpql.append(" select * from COM_D_RAPPORTO  r, COM_R_RAPPORTO_LAVORATORE rl, COM_D_LAVORATORE  l, COM_D_COMUNICAZIONE c");
//		jpql.append(" WHERE c.id_com_t_tipo_trasferimento is not null\r\n" + 
//				" AND c.id_com_d_comunicazione = r.id_com_d_comunicazione\r\n" + 
//				" AND l.ID_COM_D_LAVORATORE    = rl.ID_COM_D_LAVORATORE\r\n" + 
//				" AND rl.ID_COM_D_RAPPORTO     = r.ID_COM_D_RAPPORTO\r\n " + 
//				" AND c.id_com_d_comunicazione = :idComunicazione ");
//		
//		
//		JpaQueryHelper.andFieldEquals(jpql, params, "t.comDRapporto.idComDRapporto", "idComDRapporto", idRapporto);
//		
//		jpql.append(" ORDER BY t.id ");
//		
//		TypedQuery<ComRRapportoLavoratore> query = composeTypedQuery(jpql, params);
//		return query.getResultList();
//	}
}
