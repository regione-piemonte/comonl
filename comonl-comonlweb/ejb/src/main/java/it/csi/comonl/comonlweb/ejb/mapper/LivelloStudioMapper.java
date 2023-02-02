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

import it.csi.comonl.comonlweb.ejb.entity.ComTLivelloStudio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloStudio;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between LivelloStudio and ComTLivelloStudio
 */
@Mapper
public interface LivelloStudioMapper extends BaseMapperInterface<LivelloStudio, ComTLivelloStudio> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	LivelloStudio toModel(ComTLivelloStudio entity);

	@Override
	@IterableMapping(elementTargetType = LivelloStudio.class)
	List<LivelloStudio> toModels(Collection<ComTLivelloStudio> entities);

	@Override
	@IterableMapping(elementTargetType = ComTLivelloStudio.class)
	List<ComTLivelloStudio> toEntities(Collection<LivelloStudio> models);

}
