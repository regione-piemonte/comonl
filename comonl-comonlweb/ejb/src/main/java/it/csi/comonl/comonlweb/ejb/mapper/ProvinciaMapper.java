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
import org.mapstruct.Mapping;

import it.csi.comonl.comonlweb.ejb.entity.ComTProvincia;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Provincia;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between Provincia and ComTProvincia
 */
@Mapper(uses = { RegioneMapper.class})
public interface ProvinciaMapper extends BaseMapperInterface<Provincia, ComTProvincia> {

	@Override
	@Mapping(source = "comTRegione", target = "regione")
	Provincia toModel(ComTProvincia entity);

	@Override
	@IterableMapping(elementTargetType = Provincia.class)
	List<Provincia> toModels(Collection<ComTProvincia> entities);

	@Override
	@IterableMapping(elementTargetType = ComTProvincia.class)
	List<ComTProvincia> toEntities(Collection<Provincia> models);

}
