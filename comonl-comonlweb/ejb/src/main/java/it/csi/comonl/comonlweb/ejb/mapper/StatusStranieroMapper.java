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

import it.csi.comonl.comonlweb.ejb.entity.ComTStatusStraniero;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatusStraniero;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between StatusStraniero and ComTStatusStraniero
 */
@Mapper
public interface StatusStranieroMapper extends BaseMapperInterface<StatusStraniero, ComTStatusStraniero> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	StatusStraniero toModel(ComTStatusStraniero entity);

	@Override
	@IterableMapping(elementTargetType = StatusStraniero.class)
	List<StatusStraniero> toModels(Collection<ComTStatusStraniero> entities);

	@Override
	@IterableMapping(elementTargetType = ComTStatusStraniero.class)
	List<ComTStatusStraniero> toEntities(Collection<StatusStraniero> models);

}
