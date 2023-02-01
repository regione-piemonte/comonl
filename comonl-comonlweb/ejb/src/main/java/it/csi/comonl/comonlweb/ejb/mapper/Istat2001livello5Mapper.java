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

import it.csi.comonl.comonlweb.ejb.entity.ComTIstat2001livello5;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Istat2001livello5;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between Istat2001livello5 and ComTIstat2001livello5
 */
@Mapper
public interface Istat2001livello5Mapper extends BaseMapperInterface<Istat2001livello5, ComTIstat2001livello5> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	Istat2001livello5 toModel(ComTIstat2001livello5 entity);

	@Override
	@IterableMapping(elementTargetType = Istat2001livello5.class)
	List<Istat2001livello5> toModels(Collection<ComTIstat2001livello5> entities);

	@Override
	@IterableMapping(elementTargetType = ComTIstat2001livello5.class)
	List<ComTIstat2001livello5> toEntities(Collection<Istat2001livello5> models);

}
