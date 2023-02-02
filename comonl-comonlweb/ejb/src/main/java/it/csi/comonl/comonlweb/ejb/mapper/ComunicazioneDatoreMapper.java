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

import it.csi.comonl.comonlweb.ejb.entity.ComRComunicazioneDatore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ComunicazioneDatore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoDatore;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between ComunicazioneDatore and ComRComunicazioneDatore
 */
@Mapper(uses = {ComunicazioneMapper.class, DatoreMapper.class, TipoDatoreMapper.class})
public interface ComunicazioneDatoreMapper extends BaseMapperInterface<ComunicazioneDatore, ComRComunicazioneDatore> {

	@Override
	//@Mapping(source = "entityName", target = "modelName")
	@Mapping(source = "comDComunicazione", target = "comunicazione")
	@Mapping(source = "comDDatore", target = "datore")
	@Mapping(source = "comTTipoDatore", target = "tipoDatore")
	ComunicazioneDatore toModel(ComRComunicazioneDatore entity);

	@Override
	@IterableMapping(elementTargetType = ComunicazioneDatore.class)
	List<ComunicazioneDatore> toModels(Collection<ComRComunicazioneDatore> entities);

	@Override
	@IterableMapping(elementTargetType = ComRComunicazioneDatore.class)
	List<ComRComunicazioneDatore> toEntities(Collection<ComunicazioneDatore> models);

}
