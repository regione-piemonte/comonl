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

import it.csi.comonl.comonlweb.ejb.entity.ComDCommaxAuth;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.CommaxAuth;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between CommaxAuth and ComDCommaxAuth
 */
@Mapper
public interface CommaxAuthMapper extends BaseMapperInterface<CommaxAuth, ComDCommaxAuth> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	CommaxAuth toModel(ComDCommaxAuth entity);

	@Override
	@IterableMapping(elementTargetType = CommaxAuth.class)
	List<CommaxAuth> toModels(Collection<ComDCommaxAuth> entities);

	@Override
	@IterableMapping(elementTargetType = ComDCommaxAuth.class)
	List<ComDCommaxAuth> toEntities(Collection<CommaxAuth> models);

}
