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
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.EntityDepositoCommaxDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.impl.BaseEntityDaoImpl;
import it.csi.comonl.comonlweb.ejb.entity.EntityDepositoCommax;
import it.csi.comonl.comonlweb.ejb.entity.EntityDepositoCommaxPK;
import it.csi.comonl.comonlweb.ejb.util.jpa.JpaQueryHelper;

/**
 * Data Access Object implementor for the entity EntityDepositoCommax
 */
@ApplicationScoped
public class EntityDepositoCommaxDaoImpl extends BaseEntityDaoImpl<EntityDepositoCommaxPK, EntityDepositoCommax> implements EntityDepositoCommaxDao {

	@Override
	public List<EntityDepositoCommax> findByField(String field) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM EntityDepositoCommax t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.field", "field", field);

		jpql.append(" ORDER BY t.id ");

		TypedQuery<EntityDepositoCommax> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}

	@Override
	public List<EntityDepositoCommax> getDepositoCommaxByIdUplDocumenti(BigDecimal idComDUplDocumenti) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder jpql = new StringBuilder();
		jpql.append(" FROM EntityDepositoCommax t ");
		jpql.append(" WHERE 1=1 ");

		JpaQueryHelper.andFieldEquals(jpql, params, "t.idComDUplDocumenti", "idComDUplDocumenti", idComDUplDocumenti);

		jpql.append(" ORDER BY t.id ");

		TypedQuery<EntityDepositoCommax> query = composeTypedQuery(jpql, params);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EntityDepositoCommax> estraiDepositiDelleComunicazioniValide(BigDecimal idUplDocumenti) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();

		sql.append(
				" SELECT  d.ID_DEPOSITO_COMMAX , d.B_FILE_COMMAX, d.D_INSERIM, d.D_TRATTAMENTO, d.ID_COM_D_UPL_DOCUMENTI, d.STATO, d.DESC_UTENTE, d.EMAIL_UTENTE, d.CF_UTENTE, d.NOME_FILE_XML_SINGOLO ");
		sql.append(" FROM  DEPOSITO_COMMAX d,COM_R_DEP_COMMAX_COMUNIC c WHERE 1=1 ");
		sql.append(" AND d.ID_DEPOSITO_COMMAX=c.ID_DEPOSITO_COMMAX ");
		sql.append(" AND d.ID_COM_D_UPL_DOCUMENTI=c.ID_COM_D_UPL_DOCUMENTI ");
		params.put("idUplDocumenti", idUplDocumenti);
		sql.append(" AND c.ID_COM_D_UPL_DOCUMENTI = :idUplDocumenti");
		
		sql.append(" ORDER BY d.id_deposito_commax, d.prog_xml ");
		Query query = composeNativeQuery(sql.toString(), params);
		List<Object[]> elenco = query.getResultList();
		List<EntityDepositoCommax> listaDeposito = new ArrayList<EntityDepositoCommax>();

		for (Object[] obj : elenco) {
			EntityDepositoCommax dec = new EntityDepositoCommax();
			if (obj[0] != null) {
				EntityDepositoCommaxPK id = new EntityDepositoCommaxPK();
				id.setIdDepositoCommax(((BigDecimal) obj[0]).longValue());
				dec.setId(id);
			}
			if (obj[1] != null) {
				try {
					Blob blob = (Blob)obj[1];
					int blobLength = (int) blob.length();  
					byte[] blobAsBytes = blob.getBytes(1, blobLength);
					dec.setBFileCommax(blobAsBytes);
				} catch (Exception e) {
					
				}
			}
			if (obj[2] != null) {
				dec.setDInserim((Date) obj[2]);
			}
			if (obj[3] != null) {
				dec.setDTrattamento((Date) obj[3]);
			}
			if (obj[4] != null) {
				dec.setIdComDUplDocumenti((BigDecimal) obj[4]);
			}
			if (obj[5] != null) {
				dec.setStato((String) obj[5]);
			}
			if (obj[6] != null) {
				dec.setDescUtente((String) obj[6]);
			}
			if (obj[7] != null) {
				dec.setEmailUtente((String) obj[7]);
			}
			if (obj[8] != null) {
				dec.setCfUtente((String) obj[8]);
			}
			if (obj[9] != null) {
				dec.setNomeFileXmlSingolo((String) obj[9]);
			}
			listaDeposito.add(dec);
		} 
		return listaDeposito;
	}
	
	@Override
	public EntityDepositoCommaxPK getSequenceDepositoCommax(int progr){

		Query q = entityManager.createNativeQuery("SELECT SEQ_ID_DEPOSITO_COMMAX.NEXTVAL FROM DUAL");
		BigDecimal res = (java.math.BigDecimal)q.getSingleResult();
		EntityDepositoCommaxPK pk = new EntityDepositoCommaxPK();
		pk.setIdDepositoCommax(res.longValue());
		pk.setProgXml(progr);
		return pk;
	}
	


}
