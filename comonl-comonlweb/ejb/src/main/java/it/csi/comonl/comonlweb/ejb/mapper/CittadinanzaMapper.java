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

import it.csi.comonl.comonlweb.ejb.entity.ComTCittadinanza;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cittadinanza;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between Cittadinanza and ComTCittadinanza
 */
@Mapper
public interface CittadinanzaMapper extends BaseMapperInterface<Cittadinanza, ComTCittadinanza> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	Cittadinanza toModel(ComTCittadinanza entity);

	@Override
	@IterableMapping(elementTargetType = Cittadinanza.class)
	List<Cittadinanza> toModels(Collection<ComTCittadinanza> entities);

	@Override
	@IterableMapping(elementTargetType = ComTCittadinanza.class)
	List<ComTCittadinanza> toEntities(Collection<Cittadinanza> models);

}
