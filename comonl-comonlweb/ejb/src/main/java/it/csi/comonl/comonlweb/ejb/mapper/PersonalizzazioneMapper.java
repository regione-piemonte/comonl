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

import it.csi.comonl.comonlweb.ejb.entity.EntityPersonalizzazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Personalizzazione;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between Personalizzazione and EntityPersonalizzazione
 */
@Mapper
public interface PersonalizzazioneMapper extends BaseMapperInterface<Personalizzazione, EntityPersonalizzazione> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	Personalizzazione toModel(EntityPersonalizzazione entity);

	@Override
	@IterableMapping(elementTargetType = Personalizzazione.class)
	List<Personalizzazione> toModels(Collection<EntityPersonalizzazione> entities);

	@Override
	@IterableMapping(elementTargetType = EntityPersonalizzazione.class)
	List<EntityPersonalizzazione> toEntities(Collection<Personalizzazione> models);

}
