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

import it.csi.comonl.comonlweb.ejb.entity.ComVRicercaVardatori;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RicercaVardatori;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between RicercaComunicazione and ComVRicercaComunicazione
 */
@Mapper
public interface RicercaVardatoriMapper extends BaseMapperInterface<RicercaVardatori, ComVRicercaVardatori> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	RicercaVardatori toModel(ComVRicercaVardatori entity);

	@Override
	@IterableMapping(elementTargetType = RicercaVardatori.class)
	List<RicercaVardatori> toModels(Collection<ComVRicercaVardatori> entities);

	@Override
	@IterableMapping(elementTargetType = ComVRicercaVardatori.class)
	List<ComVRicercaVardatori> toEntities(Collection<RicercaVardatori> models);

}
