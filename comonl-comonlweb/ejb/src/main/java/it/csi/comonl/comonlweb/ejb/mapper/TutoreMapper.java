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

import it.csi.comonl.comonlweb.ejb.entity.ComDTutore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Tutore;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.GradoContrattuale;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Istat2001livello5;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between Tutore and ComDTutore
 */
@Mapper (uses = {
		Istat2001livello5.class,GradoContrattuale.class
	})
public interface TutoreMapper extends BaseMapperInterface<Tutore, ComDTutore> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	@Mapping(source = "comTIstat2001livello5", target = "istat")
	@Mapping(source = "comTGradoContrattuale", target = "gradoContrattuale")
	Tutore toModel(ComDTutore entity);

	@Override
	@IterableMapping(elementTargetType = Tutore.class)
	List<Tutore> toModels(Collection<ComDTutore> entities);

	@Override
	@IterableMapping(elementTargetType = ComDTutore.class)
	List<ComDTutore> toEntities(Collection<Tutore> models);

}
