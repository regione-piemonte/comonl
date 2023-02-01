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

import it.csi.comonl.comonlweb.ejb.entity.ComTLivelloRetribuzione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloRetribuzione;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between LivelloRetribuzione and ComTLivelloRetribuzione
 */

@Mapper(uses = {CcnlMapper.class})
public interface LivelloRetribuzioneMapper extends BaseMapperInterface<LivelloRetribuzione, ComTLivelloRetribuzione> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	@Mapping(source = "comTCcnl", target = "ccnl")
	LivelloRetribuzione toModel(ComTLivelloRetribuzione entity);

	@Override
	@IterableMapping(elementTargetType = LivelloRetribuzione.class)
	List<LivelloRetribuzione> toModels(Collection<ComTLivelloRetribuzione> entities);

	@Override
	@IterableMapping(elementTargetType = ComTLivelloRetribuzione.class)
	List<ComTLivelloRetribuzione> toEntities(Collection<LivelloRetribuzione> models);

}
