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

import it.csi.comonl.comonlweb.ejb.entity.EntityDepositoCommax;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DepositoCommax;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between DepositoCommax and EntityDepositoCommax
 */
@Mapper
public interface DepositoCommaxMapper extends BaseMapperInterface<DepositoCommax, EntityDepositoCommax> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	DepositoCommax toModel(EntityDepositoCommax entity);

	@Override
	@IterableMapping(elementTargetType = DepositoCommax.class)
	List<DepositoCommax> toModels(Collection<EntityDepositoCommax> entities);

	@Override
	@IterableMapping(elementTargetType = EntityDepositoCommax.class)
	List<EntityDepositoCommax> toEntities(Collection<DepositoCommax> models);

}
