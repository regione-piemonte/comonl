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

import it.csi.comonl.comonlweb.ejb.entity.ComTTipoDatore;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoDatore;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipoDatore and ComTTipoDatore
 */
@Mapper
public interface TipoDatoreMapper extends BaseMapperInterface<TipoDatore, ComTTipoDatore> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipoDatore toModel(ComTTipoDatore entity);

	@Override
	@IterableMapping(elementTargetType = TipoDatore.class)
	List<TipoDatore> toModels(Collection<ComTTipoDatore> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTipoDatore.class)
	List<ComTTipoDatore> toEntities(Collection<TipoDatore> models);

}
