/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - LIB submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.lib.dto.silp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.csi.comonl.comonlweb.lib.dto.BaseAuditedDto;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cittadinanza;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.MotivoPermesso;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Questura;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatusStraniero;


/**
 * The Class LegaleRappr.
 */
public class LegaleRapprSilp extends BaseAuditedDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	private String dsCognome;
	private String dsNome;
	  private String cap = null;
	  private String denominazione = null;
	  private String dsMotivoTitoloSoggiorno = null;
	  private String dsTitoloSoggiorno = null;
	  private Date dtRichiestaTitoloSoggiorno = null;
	  private Date dtScadenzaTitoloSoggiorno = null;
	  private String fax = null;
	  private String faxAzienda = null;
	  private String sesso;
	  private BigDecimal id = null;
	  private Cittadinanza cittadinanza;
	  private Comune comune;
	  private Comune comuneNasc;
	  
	  private String idNazione = null;
	  private String idNazioneNasc = null;
	  private Questura questura;
	  private BigDecimal idSede = null;
	  
	  private String indirizzo = null;
	  private String mail = null;
	  private String nCivico = null;
	  private String note = null;
	  
	  private String sitoWeb = null;
	  private String telefono = null;
	  private String telefonoAzienda = null;
	  private String toponimo = null;
	  
	  
	  private String idMotivoTitoloSoggiorno = null;
	  private String numeroTitoloSoggiorno = null;
	  private String idTitoloSoggiorno = null;
	  

	

	private Date dtNascita;
	private Date dtScadenzaPermessoSogg;
	private Date dtUltMod;
	private String flgResidenzaItaliana;
	private String flgSoggiornanteItalia;
	private String idUserInsert;
	private String idUserUltMod;
	private String numeroDocumento;
	

	private MotivoPermesso motivoPermesso;
	private StatiEsteri statiEsteri;
	private StatusStraniero statusStraniero;



}
