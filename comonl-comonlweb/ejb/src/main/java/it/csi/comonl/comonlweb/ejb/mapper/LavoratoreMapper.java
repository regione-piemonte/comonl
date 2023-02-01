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

import org.mapstruct.BeforeMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import it.csi.comonl.comonlweb.ejb.entity.ComDLavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between Lavoratore and ComDLavoratore
 */
@Mapper(uses = {CittadinanzaMapper.class, ComuneMapper.class, LivelloStudioMapper.class, MotivoPermessoMapper.class, QuesturaMapper.class, StatiEsteriMapper.class})
public interface LavoratoreMapper extends BaseMapperInterface<Lavoratore, ComDLavoratore> {
	
	
	@BeforeMapping
	default
	void checkPropertyNullBefore(Lavoratore model,  @MappingTarget ComDLavoratore entity) {
        if (model.getComuneDom() != null && model.getComuneDom().getId() == null) {
        	model.setComuneDom(null);
        }
        if (model.getStatiEsteriDom() != null && model.getStatiEsteriDom().getId() == null) {
        	model.setStatiEsteriDom(null);
        }
        if (model.getComuneNasc() != null && model.getComuneNasc().getId() == null) {
        	model.setComuneNasc(null);
        }
        if (model.getStatiEsteriNasc() != null && model.getStatiEsteriNasc().getId() == null) {
        	model.setStatiEsteriNasc(null);
        }
        
        if (model.getComuneRes() != null && model.getComuneRes().getId() == null) {
        	model.setComuneRes(null);
        }
        if (model.getStatiEsteriRes() != null && model.getStatiEsteriRes().getId() == null) {
        	model.setStatiEsteriRes(null);
        }
        if (model.getQuestura() != null && model.getQuestura().getId() == null) {
        	model.setQuestura(null);
        }
        if (model.getMotivoPermesso() != null && model.getMotivoPermesso().getId() == null) {
        	model.setMotivoPermesso(null);
        }
        if (model.getStatusStraniero() != null && model.getStatusStraniero().getId() == null) {
        	model.setStatusStraniero(null);
        }
    }

	@Override
	// @Mapping(source = "entityName", target = "modelName")
	@Mapping(source = "comTCittadinanza", target = "cittadinanza")
	@Mapping(source = "comTComuneDom", target = "comuneDom")
	@Mapping(source = "comTComuneRes", target = "comuneRes")
	@Mapping(source = "comTComuneNasc", target = "comuneNasc")
	@Mapping(source = "comTLivelloStudio", target = "livelloStudio")
	@Mapping(source = "comTMotivoPermesso", target = "motivoPermesso")
	@Mapping(source = "comTQuestura", target = "questura")
	@Mapping(source = "comTStatiEsteriNasc", target = "statiEsteriNasc")
	@Mapping(source = "comTStatiEsteriDom", target = "statiEsteriDom")
	@Mapping(source = "comTStatiEsteriRes", target = "statiEsteriRes")
	@Mapping(source = "comTStatusStraniero", target = "statusStraniero")
	Lavoratore toModel(ComDLavoratore entity);

	@Override
	@IterableMapping(elementTargetType = Lavoratore.class)
	List<Lavoratore> toModels(Collection<ComDLavoratore> entities);

	@Override
	@IterableMapping(elementTargetType = ComDLavoratore.class)
	List<ComDLavoratore> toEntities(Collection<Lavoratore> models);

}
