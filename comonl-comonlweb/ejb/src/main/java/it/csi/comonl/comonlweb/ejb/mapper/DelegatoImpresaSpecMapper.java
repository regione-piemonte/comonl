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
package it.csi.comonl.comonlweb.ejb.mapper;

import java.util.Collection;
import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.csi.comonl.comonlweb.ejb.entity.EntityDelegatoImpresa;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresa;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DelegatoImpresaSpec;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between DelegatoImpresaSpec and EntityDelegatoImpresa
 */
@Mapper(uses = {AnagraficaDelegatoMapper.class,AnagraficaDatoreMapper.class})
public interface DelegatoImpresaSpecMapper extends BaseMapperInterface<DelegatoImpresaSpec, EntityDelegatoImpresa> {

	@Override
	@Mapping(source = "anagraficaDelegato", target = "anagraficaDelegato")
	@Mapping(source = "comDAnagraficaDatore", target = "anagraficaDatore")
	DelegatoImpresaSpec toModel(EntityDelegatoImpresa entity);

	@Override
	@IterableMapping(elementTargetType = DelegatoImpresa.class)
	List<DelegatoImpresaSpec> toModels(Collection<EntityDelegatoImpresa> entities);
//
//	@Override
//	@IterableMapping(elementTargetType = EntityDelegatoImpresa.class)
//	List<EntityDelegatoImpresa> toEntities(Collection<DelegatoImpresa> models);

}
