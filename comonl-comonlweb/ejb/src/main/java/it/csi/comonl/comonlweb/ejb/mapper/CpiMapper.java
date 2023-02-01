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

import it.csi.comonl.comonlweb.ejb.entity.ComTCpi;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cpi;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between Cpi and ComTCpi
 */
@Mapper
public interface CpiMapper extends BaseMapperInterface<Cpi, ComTCpi> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	Cpi toModel(ComTCpi entity);

	@Override
	@IterableMapping(elementTargetType = Cpi.class)
	List<Cpi> toModels(Collection<ComTCpi> entities);

	@Override
	@IterableMapping(elementTargetType = ComTCpi.class)
	List<ComTCpi> toEntities(Collection<Cpi> models);

}
