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
import org.mapstruct.Mapping;

import it.csi.comonl.comonlweb.ejb.entity.ComRSedeLavoroLavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoroLavoratore;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between SedeLavoroLavoratore and ComRSedeLavoroLavoratore
 */
@Mapper
public interface SedeLavoroLavoratoreMapper extends BaseMapperInterface<SedeLavoroLavoratore, ComRSedeLavoroLavoratore> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	@Mapping(source = "comDLavoratore", target = "lavoratore")
	@Mapping(source = "comDSedeLavoro", target = "sedeLavoro")
	SedeLavoroLavoratore toModel(ComRSedeLavoroLavoratore entity);

	@Override
	@IterableMapping(elementTargetType = SedeLavoroLavoratore.class)
	List<SedeLavoroLavoratore> toModels(Collection<ComRSedeLavoroLavoratore> entities);

	@Override
	@IterableMapping(elementTargetType = ComRSedeLavoroLavoratore.class)
	List<ComRSedeLavoroLavoratore> toEntities(Collection<SedeLavoroLavoratore> models);

}
