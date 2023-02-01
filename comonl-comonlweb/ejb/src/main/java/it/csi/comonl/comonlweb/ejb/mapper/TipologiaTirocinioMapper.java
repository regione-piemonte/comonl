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

import it.csi.comonl.comonlweb.ejb.entity.ComTTipologiaTirocinio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipologiaTirocinio;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipologiaTirocinio and ComTTipologiaTirocinio
 */
@Mapper
public interface TipologiaTirocinioMapper extends BaseMapperInterface<TipologiaTirocinio, ComTTipologiaTirocinio> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipologiaTirocinio toModel(ComTTipologiaTirocinio entity);

	@Override
	@IterableMapping(elementTargetType = TipologiaTirocinio.class)
	List<TipologiaTirocinio> toModels(Collection<ComTTipologiaTirocinio> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTipologiaTirocinio.class)
	List<ComTTipologiaTirocinio> toEntities(Collection<TipologiaTirocinio> models);

}
