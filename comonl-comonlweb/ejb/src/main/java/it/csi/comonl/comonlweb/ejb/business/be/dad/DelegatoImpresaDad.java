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

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.EntityDelegatoImpresaDao;
import it.csi.comonl.comonlweb.ejb.entity.EntityDelegatoImpresa;
import it.csi.comonl.comonlweb.ejb.entity.EntityDelegatoImpresaPK;
import it.csi.comonl.comonlweb.ejb.mapper.ComonlMappers;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresaSpec;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;



@ApplicationScoped
public class DelegatoImpresaDad extends BaseDad {

	@Inject
	private EntityDelegatoImpresaDao entityDelegatoImpresaDao;
	
	@Inject
	private DelegaDad delegaDad;

	public DelegatoImpresa insertDelegatoImpresa(DelegatoImpresa delegatoImpresa) {
		EntityDelegatoImpresa entityDelegatoImpresa = ComonlMappers.DELEGATO_IMPRESA.toEntity(delegatoImpresa);
		if (StringUtils.isBlank(entityDelegatoImpresa.getPv())){
			entityDelegatoImpresa.setPv(ComonlConstants.PV_DEFAULT);
		}
		return ComonlMappers.DELEGATO_IMPRESA.toModel(entityDelegatoImpresaDao.insert(entityDelegatoImpresa));
	}
	
	public DelegatoImpresa getDelegatoImpresaById(String cfDelegato, String tipoAnagrafica, String cfImpresa) {
		EntityDelegatoImpresaPK entityDelegatoImpresaPK = new EntityDelegatoImpresaPK();
		entityDelegatoImpresaPK.setCfDelegato(cfDelegato);
		entityDelegatoImpresaPK.setCfImpresa(cfImpresa);
		entityDelegatoImpresaPK.setTipoAnagrafica(tipoAnagrafica);
		Optional<DelegatoImpresa> delegatoImpresaFinded = entityDelegatoImpresaDao.findOne(entityDelegatoImpresaPK).map(ComonlMappers.DELEGATO_IMPRESA::toModel);
		if(delegatoImpresaFinded.isPresent()) {
			return delegatoImpresaFinded.get();
		}
		return null;
	}
	
	
	public DelegatoImpresa updateDelegatoImpresa(DelegatoImpresa delegatoImpresa) {
		EntityDelegatoImpresa entityDelegatoImpresa = ComonlMappers.DELEGATO_IMPRESA.toEntity(delegatoImpresa);
		return ComonlMappers.DELEGATO_IMPRESA.toModel(entityDelegatoImpresaDao.update(entityDelegatoImpresa));
	}
	
	public List<DelegatoImpresa> getDelegatoImpresaByCfImpresa(String cfImpresa){
		List<EntityDelegatoImpresa> entities = entityDelegatoImpresaDao.findByCfImpresa(cfImpresa);
		return ComonlMappers.DELEGATO_IMPRESA.toModels(entities);
	}
	
	public List<DelegatoImpresa> getDelegatoImpresaByCfImpresaValidOrNotValid(String cfImpresa,boolean flgDataAnnullamentoNull){
		List<EntityDelegatoImpresa> entities = entityDelegatoImpresaDao.findByCfImpresaValidOrNotValid(cfImpresa,flgDataAnnullamentoNull);
		return ComonlMappers.DELEGATO_IMPRESA.toModels(entities);
	}
	
	public List<DelegatoImpresaSpec> getDelegatoImpresaSpecByCfImpresa(String cfImpresa){
		List<DelegatoImpresaSpec> delegatoImpresaSpecs = ComonlMappers.DELEGATO_IMPRESA_SPEC.toModels(entityDelegatoImpresaDao.findByCfImpresa(cfImpresa));
		for(DelegatoImpresaSpec delegatoImpresaSpec : delegatoImpresaSpecs) {
			delegatoImpresaSpec.setDelegas(delegaDad.getDelegaByCfDelegatoCfImpresa(delegatoImpresaSpec.getId().getCfDelegato(), cfImpresa));
		}
		return delegatoImpresaSpecs;
	}
	
	
}
