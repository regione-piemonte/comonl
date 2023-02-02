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

import it.csi.comonl.comonlweb.ejb.entity.ComTAtecofin;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Atecofin;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between Atecofin and ComTAtecofin
 */
@Mapper
public interface AtecofinMapper extends BaseMapperInterface<Atecofin, ComTAtecofin> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	Atecofin toModel(ComTAtecofin entity);

	@Override
	@IterableMapping(elementTargetType = Atecofin.class)
	List<Atecofin> toModels(Collection<ComTAtecofin> entities);

	@Override
	@IterableMapping(elementTargetType = ComTAtecofin.class)
	List<ComTAtecofin> toEntities(Collection<Atecofin> models);

}
