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
import org.mapstruct.Mapping;

import it.csi.comonl.comonlweb.ejb.entity.ComTToponimo;
import it.csi.comonl.comonlweb.ejb.entity.EntityCaricaPersonaPv;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.CaricaPersonaPv;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Personalizzazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Toponimo;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between CaricaPersonaPv and EntityCaricaPersonaPv
 */
@Mapper(uses={Personalizzazione.class})
public interface ToponimoMapper extends BaseMapperInterface<Toponimo, ComTToponimo> {

	@Override
	Toponimo toModel(ComTToponimo entity);

	@Override
	@IterableMapping(elementTargetType = CaricaPersonaPv.class)
	List<Toponimo> toModels(Collection<ComTToponimo> entities);

	@Override
	@IterableMapping(elementTargetType = EntityCaricaPersonaPv.class)
	List<ComTToponimo> toEntities(Collection<Toponimo> models);

}
