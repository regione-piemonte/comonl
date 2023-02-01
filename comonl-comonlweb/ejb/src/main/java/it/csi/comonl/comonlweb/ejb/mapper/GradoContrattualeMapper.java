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

import it.csi.comonl.comonlweb.ejb.entity.ComTGradoContrattuale;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.GradoContrattuale;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between GradoContrattuale and ComTGradoContrattuale
 */
@Mapper
public interface GradoContrattualeMapper extends BaseMapperInterface<GradoContrattuale, ComTGradoContrattuale> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	GradoContrattuale toModel(ComTGradoContrattuale entity);

	@Override
	@IterableMapping(elementTargetType = GradoContrattuale.class)
	List<GradoContrattuale> toModels(Collection<ComTGradoContrattuale> entities);

	@Override
	@IterableMapping(elementTargetType = ComTGradoContrattuale.class)
	List<ComTGradoContrattuale> toEntities(Collection<GradoContrattuale> models);

}
