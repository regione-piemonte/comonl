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

import it.csi.comonl.comonlweb.ejb.entity.EntityDelega;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Delega;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between Delega and EntityDelega
 */
@Mapper(uses = { ComuneMapper.class, StatiEsteriMapper.class })
public interface DelegaMapper extends BaseMapperInterface<Delega, EntityDelega> {

	@Override
	@Mapping(source = "comTComDelegato", target = "comDelegato")
	@Mapping(source = "comTComDelegante", target = "comDelegante")
	@Mapping(source = "comTStatiEsteriDelegato", target = "statiEsteriDelegato")
	@Mapping(source = "comTStatiEsteriDelegante", target = "statiEsteriDelegante")
	// @Mapping(source = "personalizzazione", target = "personalizzazione")
	// @Mapping(source = "carica", target = "carica")
	Delega toModel(EntityDelega entity);

	@Override
	@IterableMapping(elementTargetType = Delega.class)
	List<Delega> toModels(Collection<EntityDelega> entities);

	@Override
	@IterableMapping(elementTargetType = EntityDelega.class)
	List<EntityDelega> toEntities(Collection<Delega> models);

}
