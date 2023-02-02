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

import it.csi.comonl.comonlweb.ejb.entity.ComDDelegato;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Delegato;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between Delegato and ComDDelegato
 */
@Mapper
public interface DelegatoMapper extends BaseMapperInterface<Delegato, ComDDelegato> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	Delegato toModel(ComDDelegato entity);

	@Override
	@IterableMapping(elementTargetType = Delegato.class)
	List<Delegato> toModels(Collection<ComDDelegato> entities);

	@Override
	@IterableMapping(elementTargetType = ComDDelegato.class)
	List<ComDDelegato> toEntities(Collection<Delegato> models);

}
