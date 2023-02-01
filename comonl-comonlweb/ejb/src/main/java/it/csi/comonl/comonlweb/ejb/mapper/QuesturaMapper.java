/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - EJB submodule
 * %%
 * Copyright (C) 2022 CSI Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.ejb.mapper;

import java.util.Collection;
import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import it.csi.comonl.comonlweb.ejb.entity.ComTQuestura;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Questura;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between Questura and ComTQuestura
 */
@Mapper
public interface QuesturaMapper extends BaseMapperInterface<Questura, ComTQuestura> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	Questura toModel(ComTQuestura entity);

	@Override
	@IterableMapping(elementTargetType = Questura.class)
	List<Questura> toModels(Collection<ComTQuestura> entities);

	@Override
	@IterableMapping(elementTargetType = ComTQuestura.class)
	List<ComTQuestura> toEntities(Collection<Questura> models);

}
