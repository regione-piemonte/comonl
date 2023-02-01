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

import it.csi.comonl.comonlweb.ejb.entity.ComTUnitaMisuraDurata;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.UnitaMisuraDurata;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between UnitaMisuraDurata and ComTUnitaMisuraDurata
 */
@Mapper
public interface UnitaMisuraDurataMapper extends BaseMapperInterface<UnitaMisuraDurata, ComTUnitaMisuraDurata> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	UnitaMisuraDurata toModel(ComTUnitaMisuraDurata entity);

	@Override
	@IterableMapping(elementTargetType = UnitaMisuraDurata.class)
	List<UnitaMisuraDurata> toModels(Collection<ComTUnitaMisuraDurata> entities);

	@Override
	@IterableMapping(elementTargetType = ComTUnitaMisuraDurata.class)
	List<ComTUnitaMisuraDurata> toEntities(Collection<UnitaMisuraDurata> models);

}
