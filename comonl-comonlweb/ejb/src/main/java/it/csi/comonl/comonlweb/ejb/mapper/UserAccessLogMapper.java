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

import it.csi.comonl.comonlweb.ejb.entity.ComDUserAccessLog;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.UserAccessLog;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between UserAccessLog and ComDUserAccessLog
 */
@Mapper
public interface UserAccessLogMapper extends BaseMapperInterface<UserAccessLog, ComDUserAccessLog> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	UserAccessLog toModel(ComDUserAccessLog entity);

	@Override
	@IterableMapping(elementTargetType = UserAccessLog.class)
	List<UserAccessLog> toModels(Collection<ComDUserAccessLog> entities);

	@Override
	@IterableMapping(elementTargetType = ComDUserAccessLog.class)
	List<ComDUserAccessLog> toEntities(Collection<UserAccessLog> models);

}
