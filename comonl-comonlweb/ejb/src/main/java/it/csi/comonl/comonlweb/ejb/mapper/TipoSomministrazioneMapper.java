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

import it.csi.comonl.comonlweb.ejb.entity.ComTTipoSomministrazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoSomministrazione;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipoSomministrazione and ComTTipoSomministrazione
 */
@Mapper
public interface TipoSomministrazioneMapper extends BaseMapperInterface<TipoSomministrazione, ComTTipoSomministrazione> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipoSomministrazione toModel(ComTTipoSomministrazione entity);

	@Override
	@IterableMapping(elementTargetType = TipoSomministrazione.class)
	List<TipoSomministrazione> toModels(Collection<ComTTipoSomministrazione> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTipoSomministrazione.class)
	List<ComTTipoSomministrazione> toEntities(Collection<TipoSomministrazione> models);

}
