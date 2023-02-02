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
package it.csi.comonl.comonlweb.ejb.mapper;

import java.util.Collection;
import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import it.csi.comonl.comonlweb.ejb.entity.EntityStatoDelega;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.StatoDelega;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between StatoDelega and EntityStatoDelega
 */
@Mapper
public interface StatoDelegaMapper extends BaseMapperInterface<StatoDelega, EntityStatoDelega> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	StatoDelega toModel(EntityStatoDelega entity);

	@Override
	@IterableMapping(elementTargetType = StatoDelega.class)
	List<StatoDelega> toModels(Collection<EntityStatoDelega> entities);

	@Override
	@IterableMapping(elementTargetType = EntityStatoDelega.class)
	List<EntityStatoDelega> toEntities(Collection<StatoDelega> models);

}
