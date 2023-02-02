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

import it.csi.comonl.comonlweb.ejb.entity.ComTTipoOrario;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoOrario;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipoOrario and ComTTipoOrario
 */
@Mapper
public interface TipoOrarioMapper extends BaseMapperInterface<TipoOrario, ComTTipoOrario> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipoOrario toModel(ComTTipoOrario entity);

	@Override
	@IterableMapping(elementTargetType = TipoOrario.class)
	List<TipoOrario> toModels(Collection<ComTTipoOrario> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTipoOrario.class)
	List<ComTTipoOrario> toEntities(Collection<TipoOrario> models);

}
