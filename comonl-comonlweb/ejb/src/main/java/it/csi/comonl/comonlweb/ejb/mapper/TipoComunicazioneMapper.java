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

import it.csi.comonl.comonlweb.ejb.entity.ComTTipoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazione;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between TipoComunicazione and ComTTipoComunicazione
 */
@Mapper
public interface TipoComunicazioneMapper extends BaseMapperInterface<TipoComunicazione, ComTTipoComunicazione> {

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	TipoComunicazione toModel(ComTTipoComunicazione entity);

	@Override
	@IterableMapping(elementTargetType = TipoComunicazione.class)
	List<TipoComunicazione> toModels(Collection<ComTTipoComunicazione> entities);

	@Override
	@IterableMapping(elementTargetType = ComTTipoComunicazione.class)
	List<ComTTipoComunicazione> toEntities(Collection<TipoComunicazione> models);

}
