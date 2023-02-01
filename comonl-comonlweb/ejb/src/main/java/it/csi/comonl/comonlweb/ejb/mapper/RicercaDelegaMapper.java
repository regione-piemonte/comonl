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

import it.csi.comonl.comonlweb.ejb.entity.ComVRicercaComunicazione;
import it.csi.comonl.comonlweb.ejb.entity.EntityDelega;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RicercaComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RicercaDelega;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between RicercaComunicazione and ComVRicercaComunicazione
 */
@Mapper
public interface RicercaDelegaMapper extends BaseMapperInterface<RicercaDelega, EntityDelega> {

	@Override
	//@Mapping(source = "statoDelega", target = "statoDelega")
	RicercaDelega toModel(EntityDelega entity);

	@Override
	@IterableMapping(elementTargetType = RicercaDelega.class)
	List<RicercaDelega> toModels(Collection<EntityDelega> entities);

	@Override
	@IterableMapping(elementTargetType = EntityDelega.class)
	List<EntityDelega> toEntities(Collection<RicercaDelega> models);

}
