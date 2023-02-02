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

import it.csi.comonl.comonlweb.ejb.entity.ComTCcnl;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Ccnl;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between Ccnl and ComTCcnl
 */
@Mapper
public interface CcnlMapper extends BaseMapperInterface<Ccnl, ComTCcnl> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	@Mapping(source = "dsComTCcnl", target = "dsCcnl")
	Ccnl toModel(ComTCcnl entity);

	@Override
	@IterableMapping(elementTargetType = Ccnl.class)
	List<Ccnl> toModels(Collection<ComTCcnl> entities);

	@Override
	@IterableMapping(elementTargetType = ComTCcnl.class)
	List<ComTCcnl> toEntities(Collection<Ccnl> models);

}
