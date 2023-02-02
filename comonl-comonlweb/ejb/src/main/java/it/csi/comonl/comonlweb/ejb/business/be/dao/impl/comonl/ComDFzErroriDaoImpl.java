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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDFzErroriDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.ComDFzErrori;
import it.csi.comonl.comonlweb.ejb.entity.EntityDepositoCommax;
import it.csi.comonl.comonlweb.ejb.entity.EntityDepositoCommaxPK;
import it.csi.comonl.comonlweb.ejb.util.commax.vo.ErroriFileVO;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity ComDFzErrori
 */
@ApplicationScoped
public class ComDFzErroriDaoImpl extends BaseEntityDaoImpl<Long, ComDFzErrori> implements ComDFzErroriDao {

	@Override
	public List<ComDFzErrori> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM ComDFzErrori t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.idComDFzErrori ");

		TypedQuery<ComDFzErrori> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ErroriFileVO> getElencoErroriFzByIdUplDoc(Long idUplDocumenti) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();

		sql.append(
				" SELECT b.ID_COM_D_FZ_ERRORI, c.DS_ERRORE as ERRORE_NEL_CONTENUTO_XML , b.NOME_FILE_XML_SCARTATO, b.CF_AZIENDA_CF_LAVORATORE ");
		sql.append(" FROM  COM_D_FZ_ERRORI b, COM_T_LISTA_ERRORI_FZ c, COM_R_T_LISTA_FZ_ERRORI d WHERE 1=1 ");
		sql.append(" AND b.ID_COM_D_FZ_ERRORI = d.ID_COM_D_FZ_ERRORI ");
		sql.append(" AND d.ID_COM_T_LISTA_ERRORI_FZ = c.ID_COM_T_LISTA_ERRORI_FZ ");
		params.put("idUplDocumenti", idUplDocumenti);
		sql.append(" AND b.ID_COM_D_UPL_DOCUMENTI = :idUplDocumenti");



		Query query = composeNativeQuery(sql.toString(), params);
		List<Object[]> elenco = query.getResultList();
		List<ErroriFileVO> listaErrori = new ArrayList<ErroriFileVO>();

		for (Object[] obj : elenco) {
			ErroriFileVO dec = new ErroriFileVO();
			if (obj[0] != null) {
				BigDecimal idBD = (BigDecimal)obj[0];
				dec.setIdErrori(idBD.longValue());
			}
			if (obj[1] != null) {
				dec.setDescrizioneErrore((String)obj[1]);
			}
			if (obj[2] != null) {
				dec.setNomeFile((String) obj[2]);
			}
			if (obj[3] != null) {
				dec.setCfLavoratore((String) obj[3]);
			}
			listaErrori.add(dec);
		}
		return listaErrori;
	}

}
