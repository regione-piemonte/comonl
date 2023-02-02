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

import it.csi.comonl.comonlweb.ejb.entity.ComRRegTracciatoContratto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RegTracciatoContratto;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between RegTracciatoContratto and ComRRegTracciatoContratto
 */
@Mapper
public interface RegTracciatoContrattoMapper extends BaseMapperInterface<RegTracciatoContratto, ComRRegTracciatoContratto> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	RegTracciatoContratto toModel(ComRRegTracciatoContratto entity);

	@Override
	@IterableMapping(elementTargetType = RegTracciatoContratto.class)
	List<RegTracciatoContratto> toModels(Collection<ComRRegTracciatoContratto> entities);

	@Override
	@IterableMapping(elementTargetType = ComRRegTracciatoContratto.class)
	List<ComRRegTracciatoContratto> toEntities(Collection<RegTracciatoContratto> models);

}
