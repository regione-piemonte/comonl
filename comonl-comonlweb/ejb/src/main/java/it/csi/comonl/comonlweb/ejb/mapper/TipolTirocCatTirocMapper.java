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

import it.csi.comonl.comonlweb.ejb.entity.ComRTipolTirocCatTiroc;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.TipolTirocCatTiroc;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipolTirocCatTiroc and ComRTipolTirocCatTiroc
 */
@Mapper
public interface TipolTirocCatTirocMapper extends BaseMapperInterface<TipolTirocCatTiroc, ComRTipolTirocCatTiroc> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipolTirocCatTiroc toModel(ComRTipolTirocCatTiroc entity);

	@Override
	@IterableMapping(elementTargetType = TipolTirocCatTiroc.class)
	List<TipolTirocCatTiroc> toModels(Collection<ComRTipolTirocCatTiroc> entities);

	@Override
	@IterableMapping(elementTargetType = ComRTipolTirocCatTiroc.class)
	List<ComRTipolTirocCatTiroc> toEntities(Collection<TipolTirocCatTiroc> models);

}
