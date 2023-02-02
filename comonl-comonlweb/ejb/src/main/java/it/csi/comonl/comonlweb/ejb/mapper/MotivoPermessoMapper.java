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

import it.csi.comonl.comonlweb.ejb.entity.ComTMotivoPermesso;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.MotivoPermesso;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between MotivoPermesso and ComTMotivoPermesso
 */
@Mapper
public interface MotivoPermessoMapper extends BaseMapperInterface<MotivoPermesso, ComTMotivoPermesso> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	MotivoPermesso toModel(ComTMotivoPermesso entity);

	@Override
	@IterableMapping(elementTargetType = MotivoPermesso.class)
	List<MotivoPermesso> toModels(Collection<ComTMotivoPermesso> entities);

	@Override
	@IterableMapping(elementTargetType = ComTMotivoPermesso.class)
	List<ComTMotivoPermesso> toEntities(Collection<MotivoPermesso> models);

}
