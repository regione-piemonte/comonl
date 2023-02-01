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

import it.csi.comonl.comonlweb.ejb.entity.ComDDichiarazioniUd;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DichiarazioniUd;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between DichiarazioniUd and ComDDichiarazioniUd
 */
@Mapper
public interface DichiarazioniUdMapper extends BaseMapperInterface<DichiarazioniUd, ComDDichiarazioniUd> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	DichiarazioniUd toModel(ComDDichiarazioniUd entity);

	@Override
	@IterableMapping(elementTargetType = DichiarazioniUd.class)
	List<DichiarazioniUd> toModels(Collection<ComDDichiarazioniUd> entities);

	@Override
	@IterableMapping(elementTargetType = ComDDichiarazioniUd.class)
	List<ComDDichiarazioniUd> toEntities(Collection<DichiarazioniUd> models);

}
