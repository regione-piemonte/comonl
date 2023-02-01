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

import it.csi.comonl.comonlweb.ejb.entity.EntityCaricaPersonaPv;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.CaricaPersonaPv;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Personalizzazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between CaricaPersonaPv and EntityCaricaPersonaPv
 */
@Mapper(uses={Personalizzazione.class})
public interface CaricaPersonaPvMapper extends BaseMapperInterface<CaricaPersonaPv, EntityCaricaPersonaPv> {

	@Override
	@Mapping(source = "personalizzazione", target = "personalizzazione")
	CaricaPersonaPv toModel(EntityCaricaPersonaPv entity);

	@Override
	@IterableMapping(elementTargetType = CaricaPersonaPv.class)
	List<CaricaPersonaPv> toModels(Collection<EntityCaricaPersonaPv> entities);

	@Override
	@IterableMapping(elementTargetType = EntityCaricaPersonaPv.class)
	List<EntityCaricaPersonaPv> toEntities(Collection<CaricaPersonaPv> models);

}
