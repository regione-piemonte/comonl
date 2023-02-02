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

import it.csi.comonl.comonlweb.ejb.entity.ComTAzioniSpecifiche;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.AzioniSpecifiche;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between AzioniSpecifiche and ComTAzioniSpecifiche
 */
@Mapper
public interface AzioniSpecificheMapper extends BaseMapperInterface<AzioniSpecifiche, ComTAzioniSpecifiche> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	AzioniSpecifiche toModel(ComTAzioniSpecifiche entity);

	@Override
	@IterableMapping(elementTargetType = AzioniSpecifiche.class)
	List<AzioniSpecifiche> toModels(Collection<ComTAzioniSpecifiche> entities);

	@Override
	@IterableMapping(elementTargetType = ComTAzioniSpecifiche.class)
	List<ComTAzioniSpecifiche> toEntities(Collection<AzioniSpecifiche> models);

}
