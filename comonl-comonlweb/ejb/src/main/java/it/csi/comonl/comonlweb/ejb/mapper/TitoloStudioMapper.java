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

import it.csi.comonl.comonlweb.ejb.entity.ComTTitoloStudio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TitoloStudio;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TitoloStudio and ComTTitoloStudio
 */
@Mapper
public interface TitoloStudioMapper extends BaseMapperInterface<TitoloStudio, ComTTitoloStudio> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TitoloStudio toModel(ComTTitoloStudio entity);

	@Override
	@IterableMapping(elementTargetType = TitoloStudio.class)
	List<TitoloStudio> toModels(Collection<ComTTitoloStudio> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTitoloStudio.class)
	List<ComTTitoloStudio> toEntities(Collection<TitoloStudio> models);

}
