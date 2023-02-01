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

import it.csi.comonl.comonlweb.ejb.entity.ComTRegione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Regione;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between Regione and ComTRegione
 */
@Mapper
public interface RegioneMapper extends BaseMapperInterface<Regione, ComTRegione> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	Regione toModel(ComTRegione entity);

	@Override
	@IterableMapping(elementTargetType = Regione.class)
	List<Regione> toModels(Collection<ComTRegione> entities);

	@Override
	@IterableMapping(elementTargetType = ComTRegione.class)
	List<ComTRegione> toEntities(Collection<Regione> models);

}
