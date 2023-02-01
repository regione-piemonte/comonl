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
package it.csi.comonl.comonlweb.ejb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;



/**
 * The persistent class for the COM_V_RICERCA_COMUNICAZIONE database table.
 * 
 */
@Entity
@Table(name="COM_V_RICERCA_VARDATORI")
@NamedQuery(name="ComVRicercaVardatori.findAll", query="SELECT c FROM ComVRicercaVardatori c")
public class ComVRicercaVardatori implements Serializable, BaseEntity<Long> {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public Long getId() {
		return idComDComunicazione;
	}
	
	@Override
	public void setId(Long id) {
		idComDComunicazione = id;
	}
	
	@Column(name="ANNO_PROT_COM")
	private Long annoProtCom;

	@Column(name="CODICE_COMUNICAZIONE_REG")
	private String codiceComunicazioneReg;

	@Column(name="CODICE_FISCALE_DATORE")
	private String codiceFiscaleDatore;

	@Column(name="CODICE_FISCALE_LAVORATORE")
	private String codiceFiscaleLavoratore;

	@Column(name="CODICE_FISCALE_SOGGETTO")
	private String codiceFiscaleSoggetto;

	@Column(name="COGNOME_LAVORATORE")
	private String cognomeLavoratore;

	@Column(name="DS_COM_T_STATO_COMUNICAZIONE")
	private String dsComTStatoComunicazione;

	@Column(name="DS_COM_T_TIPO_COMUNICAZIONE")
	private String dsComTTipoComunicazione;

	@Column(name="DS_DENOMINAZIONE_DATORE")
	private String dsDenominazioneDatore;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_EVENTO")
	private Date dtEvento;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO_RAPPORTO")
	private Date dtInizioRapporto;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_INSERT")
	private Date dtInsert;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_INVIO")
	private Date dtInvio;
	
	@Id
	@Column(name="ID_COM_D_COMUNICAZIONE")
	private Long idComDComunicazione;

	@Column(name="ID_COM_D_DATORE")
	private Long idComDDatore;

	/*
	@Column(name="ID_COM_D_LAVORATORE")
	private Long idComDLavoratore;*/

	@Column(name="ID_COM_T_PROVINCIA")
	private Long idComTProvincia;

	@Column(name="ID_COM_T_STATO_COMUNICAZIONE")
	private Long idComTStatoComunicazione;

	@Column(name="ID_COM_T_TIPO_COMUNICAZIONE")
	private String idComTTipoComunicazione;

	@Column(name="ID_COM_T_TIPO_COMUNICAZIONE_TU")
	private Long idComTTipoComunicazioneTu;

	@Column(name="NOME_LAVORATORE")
	private String nomeLavoratore;

	@Column(name="NUM_PROT_COM")
	private Long numProtCom;
	
	@Column(name="ID_TIPO_TRACCIATO")
	private String idTipoTracciato;
	
	@Column(name="ID_COM_D_DATORE_PREC")
	private Long idComDDatorePrec;
	
	@Column(name="CODICE_FISCALE_DATORE_PREC")
	private String codiceFiscaleDatorePrec;

	@Column(name="DS_DENOMINAZIONE_DATORE_PREC")
	private String dsDenominazioneDatorePrec;
	
	@Column(name="ID_COM_T_TIPO_TRASFERIMENTO")
	private Long idComTTipoTrasferimento;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_TRASFERIMENTO")
	private Date dtTrasferimento;
	

	public ComVRicercaVardatori() {
	}

	public Long getAnnoProtCom() {
		return this.annoProtCom;
	}

	public void setAnnoProtCom(Long annoProtCom) {
		this.annoProtCom = annoProtCom;
	}

	public String getCodiceComunicazioneReg() {
		return this.codiceComunicazioneReg;
	}

	public void setCodiceComunicazioneReg(String codiceComunicazioneReg) {
		this.codiceComunicazioneReg = codiceComunicazioneReg;
	}

	public String getCodiceFiscaleDatore() {
		return this.codiceFiscaleDatore;
	}

	public void setCodiceFiscaleDatore(String codiceFiscaleDatore) {
		this.codiceFiscaleDatore = codiceFiscaleDatore;
	}

	public String getCodiceFiscaleLavoratore() {
		return this.codiceFiscaleLavoratore;
	}

	public void setCodiceFiscaleLavoratore(String codiceFiscaleLavoratore) {
		this.codiceFiscaleLavoratore = codiceFiscaleLavoratore;
	}

	public String getCodiceFiscaleSoggetto() {
		return this.codiceFiscaleSoggetto;
	}

	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}

	public String getCognomeLavoratore() {
		return this.cognomeLavoratore;
	}

	public void setCognomeLavoratore(String cognomeLavoratore) {
		this.cognomeLavoratore = cognomeLavoratore;
	}

	public String getDsComTStatoComunicazione() {
		return this.dsComTStatoComunicazione;
	}

	public void setDsComTStatoComunicazione(String dsComTStatoComunicazione) {
		this.dsComTStatoComunicazione = dsComTStatoComunicazione;
	}

	public String getDsComTTipoComunicazione() {
		return this.dsComTTipoComunicazione;
	}

	public void setDsComTTipoComunicazione(String dsComTTipoComunicazione) {
		this.dsComTTipoComunicazione = dsComTTipoComunicazione;
	}

	public String getDsDenominazioneDatore() {
		return this.dsDenominazioneDatore;
	}

	public void setDsDenominazioneDatore(String dsDenominazioneDatore) {
		this.dsDenominazioneDatore = dsDenominazioneDatore;
	}

	public Date getDtEvento() {
		return this.dtEvento;
	}

	public void setDtEvento(Date dtEvento) {
		this.dtEvento = dtEvento;
	}

	public Date getDtInizioRapporto() {
		return this.dtInizioRapporto;
	}

	public void setDtInizioRapporto(Date dtInizioRapporto) {
		this.dtInizioRapporto = dtInizioRapporto;
	}

	public Date getDtInsert() {
		return this.dtInsert;
	}

	public void setDtInsert(Date dtInsert) {
		this.dtInsert = dtInsert;
	}

	public Date getDtInvio() {
		return this.dtInvio;
	}

	public void setDtInvio(Date dtInvio) {
		this.dtInvio = dtInvio;
	}

	public Long getIdComDComunicazione() {
		return this.idComDComunicazione;
	}

	public void setIdComDComunicazione(Long idComDComunicazione) {
		this.idComDComunicazione = idComDComunicazione;
	}

	public Long getIdComDDatore() {
		return this.idComDDatore;
	}

	public void setIdComDDatore(Long idComDDatore) {
		this.idComDDatore = idComDDatore;
	}
	
	/*
	public Long getIdComDLavoratore() {
		return this.idComDLavoratore;
	}

	public void setIdComDLavoratore(Long idComDLavoratore) {
		this.idComDLavoratore = idComDLavoratore;
	*/

	public Long getIdComTProvincia() {
		return this.idComTProvincia;
	}

	public void setIdComTProvincia(Long idComTProvincia) {
		this.idComTProvincia = idComTProvincia;
	}

	public Long getIdComTStatoComunicazione() {
		return this.idComTStatoComunicazione;
	}

	public void setIdComTStatoComunicazione(Long idComTStatoComunicazione) {
		this.idComTStatoComunicazione = idComTStatoComunicazione;
	}

	public String getIdComTTipoComunicazione() {
		return this.idComTTipoComunicazione;
	}

	public void setIdComTTipoComunicazione(String idComTTipoComunicazione) {
		this.idComTTipoComunicazione = idComTTipoComunicazione;
	}

	public Long getIdComTTipoComunicazioneTu() {
		return this.idComTTipoComunicazioneTu;
	}

	public void setIdComTTipoComunicazioneTu(Long idComTTipoComunicazioneTu) {
		this.idComTTipoComunicazioneTu = idComTTipoComunicazioneTu;
	}

	public String getNomeLavoratore() {
		return this.nomeLavoratore;
	}

	public void setNomeLavoratore(String nomeLavoratore) {
		this.nomeLavoratore = nomeLavoratore;
	}

	public Long getNumProtCom() {
		return this.numProtCom;
	}

	public void setNumProtCom(Long numProtCom) {
		this.numProtCom = numProtCom;
	}

	public String getIdTipoTracciato() {
		return idTipoTracciato;
	}

	public void setIdTipoTracciato(String idTipoTracciato) {
		this.idTipoTracciato = idTipoTracciato;
	}


	public Long getIdComDDatorePrec() {
		return idComDDatorePrec;
	}

	public void setIdComDDatorePrec(Long idComDDatorePrec) {
		this.idComDDatorePrec = idComDDatorePrec;
	}

	public String getCodiceFiscaleDatorePrec() {
		return codiceFiscaleDatorePrec;
	}

	public void setCodiceFiscaleDatorePrec(String codiceFiscaleDatorePrec) {
		this.codiceFiscaleDatorePrec = codiceFiscaleDatorePrec;
	}

	public String getDsDenominazioneDatorePrec() {
		return dsDenominazioneDatorePrec;
	}

	public void setDsDenominazioneDatorePrec(String dsDenominazioneDatorePrec) {
		this.dsDenominazioneDatorePrec = dsDenominazioneDatorePrec;
	}

	public Long getIdComTTipoTrasferimento() {
		return idComTTipoTrasferimento;
	}

	public void setIdComTTipoTrasferimento(Long idComTTipoTrasferimento) {
		this.idComTTipoTrasferimento = idComTTipoTrasferimento;
	}

	public Date getDtTrasferimento() {
		return dtTrasferimento;
	}

	public void setDtTrasferimento(Date dtTrasferimento) {
		this.dtTrasferimento = dtTrasferimento;
	}
	
	

}
