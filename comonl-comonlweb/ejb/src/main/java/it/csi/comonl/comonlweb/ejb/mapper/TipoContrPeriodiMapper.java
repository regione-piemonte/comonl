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

import it.csi.comonl.comonlweb.ejb.entity.ComTTipoContrPeriodi;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContrPeriodi;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipoContrPeriodi and ComTTipoContrPeriodi
 */
@Mapper
public interface TipoContrPeriodiMapper extends BaseMapperInterface<TipoContrPeriodi, ComTTipoContrPeriodi> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipoContrPeriodi toModel(ComTTipoContrPeriodi entity);

	@Override
	@IterableMapping(elementTargetType = TipoContrPeriodi.class)
	List<TipoContrPeriodi> toModels(Collection<ComTTipoContrPeriodi> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTipoContrPeriodi.class)
	List<ComTTipoContrPeriodi> toEntities(Collection<TipoContrPeriodi> models);

}
