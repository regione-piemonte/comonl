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

import it.csi.comonl.comonlweb.ejb.entity.ComTCommaxParametri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CommaxParametri;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between CommaxParametri and ComTCommaxParametri
 */
@Mapper
public interface CommaxParametriMapper extends BaseMapperInterface<CommaxParametri, ComTCommaxParametri> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	CommaxParametri toModel(ComTCommaxParametri entity);

	@Override
	@IterableMapping(elementTargetType = CommaxParametri.class)
	List<CommaxParametri> toModels(Collection<ComTCommaxParametri> entities);

	@Override
	@IterableMapping(elementTargetType = ComTCommaxParametri.class)
	List<ComTCommaxParametri> toEntities(Collection<CommaxParametri> models);

}
