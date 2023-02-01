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

import it.csi.comonl.comonlweb.ejb.entity.ComTTipoComunicazioneTu;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazioneTu;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipoComunicazioneTu and ComTTipoComunicazioneTu
 */
@Mapper
public interface TipoComunicazioneTuMapper extends BaseMapperInterface<TipoComunicazioneTu, ComTTipoComunicazioneTu> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipoComunicazioneTu toModel(ComTTipoComunicazioneTu entity);

	@Override
	@IterableMapping(elementTargetType = TipoComunicazioneTu.class)
	List<TipoComunicazioneTu> toModels(Collection<ComTTipoComunicazioneTu> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTipoComunicazioneTu.class)
	List<ComTTipoComunicazioneTu> toEntities(Collection<TipoComunicazioneTu> models);

}
