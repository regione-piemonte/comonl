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

import it.csi.comonl.comonlweb.ejb.entity.ComDDuplicaGruppoLog;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DuplicaGruppoLog;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between DuplicaGruppoLog and ComDDuplicaGruppoLog
 */
@Mapper
public interface DuplicaGruppoLogMapper extends BaseMapperInterface<DuplicaGruppoLog, ComDDuplicaGruppoLog> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	DuplicaGruppoLog toModel(ComDDuplicaGruppoLog entity);

	@Override
	@IterableMapping(elementTargetType = DuplicaGruppoLog.class)
	List<DuplicaGruppoLog> toModels(Collection<ComDDuplicaGruppoLog> entities);

	@Override
	@IterableMapping(elementTargetType = ComDDuplicaGruppoLog.class)
	List<ComDDuplicaGruppoLog> toEntities(Collection<DuplicaGruppoLog> models);

}
