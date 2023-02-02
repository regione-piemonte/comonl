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

import it.csi.comonl.comonlweb.ejb.entity.ComTCategLavAssObbl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategLavAssObbl;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between CategLavAssObbl and ComTCategLavAssObbl
 */
@Mapper
public interface CategLavAssObblMapper extends BaseMapperInterface<CategLavAssObbl, ComTCategLavAssObbl> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	CategLavAssObbl toModel(ComTCategLavAssObbl entity);

	@Override
	@IterableMapping(elementTargetType = CategLavAssObbl.class)
	List<CategLavAssObbl> toModels(Collection<ComTCategLavAssObbl> entities);

	@Override
	@IterableMapping(elementTargetType = ComTCategLavAssObbl.class)
	List<ComTCategLavAssObbl> toEntities(Collection<CategLavAssObbl> models);

}
