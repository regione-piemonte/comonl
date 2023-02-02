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

import it.csi.comonl.comonlweb.ejb.entity.ComDComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.mapper.BaseMapperInterface;

/**
 * Mapper between Comunicazione and ComDComunicazione
 */
@Mapper(uses = {
			TipoTrasferimentoMapper.class,TipoTracciatoMapper.class,TipoSomministrazioneMapper.class,
			TipoSoggettoAbilitatoMapper.class,TipoProvenienzaMapper.class,TipoComunicazioneTuMapper.class,
			TipoComunicazioneMapper.class, TipoAcquisizioneMapper.class, StatoComunicazioneMapper.class,
			ProvinciaMapper.class,CpiMapper.class,DelegatoMapper.class
		})
public interface ComunicazioneMapper extends BaseMapperInterface<Comunicazione, ComDComunicazione> {
	
	
	//TODO non sono ancora stati mappati i campi che a loro volta erano altre Comunicazioni
	@Override
	//@Mapping(source = "comDComunicazioneGruppo", target = "comunicazioneGruppo")
	@Mapping(source = "comTTipoTrasferimento", target = "tipoTrasferimento")
	@Mapping(source = "comTTipoTracciato", target = "tipoTracciato")
	@Mapping(source = "comTTipoSomministrazione", target = "tipoSomministrazione")
	@Mapping(source = "comTTipoSoggettoAbilitato", target = "tipoSoggettoAbilitato")
	@Mapping(source = "comTTipoProvenienza", target = "tipoProvenienza")
	@Mapping(source = "comTTipoComunicazioneTu", target = "tipoComunicazioneTu")
	@Mapping(source = "comTTipoComunicazione", target = "tipoComunicazione")
	@Mapping(source = "comTTipoAcquisizione", target = "tipoAcquisizione")
	@Mapping(source = "comTStatoComunicazione", target = "statoComunicazione")
	@Mapping(source = "comTProvincia", target = "provincia")
	@Mapping(source = "comTCpi", target = "cpi")
	@Mapping(source = "comDDelegato", target = "delegato")
	// @Mapping(source = "entityName", target = "modelName")
	Comunicazione toModel(ComDComunicazione entity);

	@Override
	@IterableMapping(elementTargetType = Comunicazione.class)
	List<Comunicazione> toModels(Collection<ComDComunicazione> entities);

	@Override
	@IterableMapping(elementTargetType = ComDComunicazione.class)
	List<ComDComunicazione> toEntities(Collection<Comunicazione> models);

}
