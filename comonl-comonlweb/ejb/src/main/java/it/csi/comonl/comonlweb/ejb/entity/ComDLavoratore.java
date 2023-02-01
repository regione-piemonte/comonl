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
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_D_LAVORATORE database table.
 * 
 */
@Entity
@Table(name="COM_D_LAVORATORE")
@NamedQuery(name="ComDLavoratore.findAll", query="SELECT c FROM ComDLavoratore c")
@SequenceGenerator(name = "SEQUENCE_LAVORATORE", sequenceName = "SEQ_ID_COM_D_LAVORATORE", initialValue = 1, allocationSize = 1)
public class ComDLavoratore implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComDLavoratore;
	}

	@Override
	public void setId(Long id) {
		idComDLavoratore = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_D_LAVORATORE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_LAVORATORE")
	private Long idComDLavoratore;

	@Column(name="COD_CAP_DOM")
	private String codCapDom;

	@Column(name="COD_CAP_RES")
	private String codCapRes;

	@Column(name="CODICE_FISCALE")
	private String codiceFiscale;

	private String cognome;

	@Column(name="DS_INDIRIZZO_DOM")
	private String dsIndirizzoDom;

	@Column(name="DS_INDIRIZZO_RES")
	private String dsIndirizzoRes;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_NASCITA")
	private Date dtNascita;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_SCADENZA_PERMESSO_SOGG")
	private Date dtScadenzaPermessoSogg;

	@Column(name="FLG_RIMBORSO_RIMPATRIO")
	private String flgRimborsoRimpatrio;

	@Column(name="FLG_SIST_ALLOGGIATIVA")
	private String flgSistAlloggiativa;

	@Column(name="ID_LAVORATORE_SILP")
	private BigDecimal idLavoratoreSilp;

	private String nome;

	@Column(name="NUMERO_DOCUMENTO")
	private String numeroDocumento;

	private String sesso;

	@Column(name="STATO_CIVILE")
	private String statoCivile;

	@Column(name="TEL_DOM")
	private String telDom;

	//bi-directional many-to-one association to ComTCittadinanza
	@ManyToOne
	@JoinColumn(name="ID_COM_T_CITTADINANZA")
	private ComTCittadinanza comTCittadinanza;

	//bi-directional many-to-one association to ComTComune
	@ManyToOne
	@JoinColumn(name="ID_COMUNE_DOM")
	private ComTComune comTComuneDom;

	//bi-directional many-to-one association to ComTComune
	@ManyToOne
	@JoinColumn(name="ID_COMUNE_RES")
	private ComTComune comTComuneRes;

	//bi-directional many-to-one association to ComTComune
	@ManyToOne
	@JoinColumn(name="ID_COMUNE_NASC")
	private ComTComune comTComuneNasc;

	//bi-directional many-to-one association to ComTLivelloStudio
	@ManyToOne
	@JoinColumn(name="ID_COM_T_LIVELLO_STUDIO")
	private ComTLivelloStudio comTLivelloStudio;

	//bi-directional many-to-one association to ComTMotivoPermesso
	@ManyToOne
	@JoinColumn(name="ID_COM_T_MOTIVO_PERMESSO")
	private ComTMotivoPermesso comTMotivoPermesso;

	//bi-directional many-to-one association to ComTQuestura
	@ManyToOne
	@JoinColumn(name="ID_QUESTURA_RILASCIO_PERM_SOGG")
	private ComTQuestura comTQuestura;

	//bi-directional many-to-one association to ComTStatiEsteri
	@ManyToOne
	@JoinColumn(name="ID_COM_T_STATI_ESTERI_NASC")
	private ComTStatiEsteri comTStatiEsteriNasc;

	//bi-directional many-to-one association to ComTStatiEsteri
	@ManyToOne
	@JoinColumn(name="ID_COM_T_STATI_ESTERI_DOM")
	private ComTStatiEsteri comTStatiEsteriDom;

	//bi-directional many-to-one association to ComTStatiEsteri
	@ManyToOne
	@JoinColumn(name="ID_COM_T_STATI_ESTERI_RES")
	private ComTStatiEsteri comTStatiEsteriRes;

	//bi-directional many-to-one association to ComTStatusStraniero
	@ManyToOne
	@JoinColumn(name="ID_COM_T_STATUS_STRANIERO")
	private ComTStatusStraniero comTStatusStraniero;

	//bi-directional many-to-one association to ComRRapportoLavoratore
//	@OneToMany(mappedBy="comDLavoratore")
//	private List<ComRRapportoLavoratore> comRRapportoLavoratores;

	//bi-directional many-to-many association to ComDSedeLavoro
//	@ManyToMany(mappedBy="comDLavoratores")
//	private List<ComDSedeLavoro> comDSedeLavoros;

	public ComDLavoratore() {
	}

	public long getIdComDLavoratore() {
		return this.idComDLavoratore;
	}

	public void setIdComDLavoratore(long idComDLavoratore) {
		this.idComDLavoratore = idComDLavoratore;
	}

	public String getCodCapDom() {
		return this.codCapDom;
	}

	public void setCodCapDom(String codCapDom) {
		this.codCapDom = codCapDom;
	}

	public String getCodCapRes() {
		return this.codCapRes;
	}

	public void setCodCapRes(String codCapRes) {
		this.codCapRes = codCapRes;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getDsIndirizzoDom() {
		return this.dsIndirizzoDom;
	}

	public void setDsIndirizzoDom(String dsIndirizzoDom) {
		this.dsIndirizzoDom = dsIndirizzoDom;
	}

	public String getDsIndirizzoRes() {
		return this.dsIndirizzoRes;
	}

	public void setDsIndirizzoRes(String dsIndirizzoRes) {
		this.dsIndirizzoRes = dsIndirizzoRes;
	}

	public Date getDtNascita() {
		return this.dtNascita;
	}

	public void setDtNascita(Date dtNascita) {
		this.dtNascita = dtNascita;
	}

	public Date getDtScadenzaPermessoSogg() {
		return this.dtScadenzaPermessoSogg;
	}

	public void setDtScadenzaPermessoSogg(Date dtScadenzaPermessoSogg) {
		this.dtScadenzaPermessoSogg = dtScadenzaPermessoSogg;
	}

	public String getFlgRimborsoRimpatrio() {
		return this.flgRimborsoRimpatrio;
	}

	public void setFlgRimborsoRimpatrio(String flgRimborsoRimpatrio) {
		this.flgRimborsoRimpatrio = flgRimborsoRimpatrio;
	}

	public String getFlgSistAlloggiativa() {
		return this.flgSistAlloggiativa;
	}

	public void setFlgSistAlloggiativa(String flgSistAlloggiativa) {
		this.flgSistAlloggiativa = flgSistAlloggiativa;
	}

	public BigDecimal getIdLavoratoreSilp() {
		return this.idLavoratoreSilp;
	}

	public void setIdLavoratoreSilp(BigDecimal idLavoratoreSilp) {
		this.idLavoratoreSilp = idLavoratoreSilp;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumeroDocumento() {
		return this.numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getStatoCivile() {
		return this.statoCivile;
	}

	public void setStatoCivile(String statoCivile) {
		this.statoCivile = statoCivile;
	}

	public String getTelDom() {
		return this.telDom;
	}

	public void setTelDom(String telDom) {
		this.telDom = telDom;
	}

	public ComTCittadinanza getComTCittadinanza() {
		return this.comTCittadinanza;
	}

	public void setComTCittadinanza(ComTCittadinanza comTCittadinanza) {
		this.comTCittadinanza = comTCittadinanza;
	}

	public ComTComune getComTComuneDom() {
		return this.comTComuneDom;
	}

	public void setComTComuneDom(ComTComune comTComuneDom) {
		this.comTComuneDom = comTComuneDom;
	}

	public ComTComune getComTComuneRes() {
		return this.comTComuneRes;
	}

	public void setComTComuneRes(ComTComune comTComuneRes) {
		this.comTComuneRes = comTComuneRes;
	}

	public ComTComune getComTComuneNasc() {
		return this.comTComuneNasc;
	}

	public void setComTComuneNasc(ComTComune comTComuneNasc) {
		this.comTComuneNasc = comTComuneNasc;
	}

	public ComTLivelloStudio getComTLivelloStudio() {
		return this.comTLivelloStudio;
	}

	public void setComTLivelloStudio(ComTLivelloStudio comTLivelloStudio) {
		this.comTLivelloStudio = comTLivelloStudio;
	}

	public ComTMotivoPermesso getComTMotivoPermesso() {
		return this.comTMotivoPermesso;
	}

	public void setComTMotivoPermesso(ComTMotivoPermesso comTMotivoPermesso) {
		this.comTMotivoPermesso = comTMotivoPermesso;
	}

	public ComTQuestura getComTQuestura() {
		return this.comTQuestura;
	}

	public void setComTQuestura(ComTQuestura comTQuestura) {
		this.comTQuestura = comTQuestura;
	}

	public ComTStatusStraniero getComTStatusStraniero() {
		return this.comTStatusStraniero;
	}

	public void setComTStatusStraniero(ComTStatusStraniero comTStatusStraniero) {
		this.comTStatusStraniero = comTStatusStraniero;
	}

	public ComTStatiEsteri getComTStatiEsteriNasc() {
		return comTStatiEsteriNasc;
	}

	public void setComTStatiEsteriNasc(ComTStatiEsteri comTStatiEsteriNasc) {
		this.comTStatiEsteriNasc = comTStatiEsteriNasc;
	}

	public ComTStatiEsteri getComTStatiEsteriDom() {
		return comTStatiEsteriDom;
	}

	public void setComTStatiEsteriDom(ComTStatiEsteri comTStatiEsteriDom) {
		this.comTStatiEsteriDom = comTStatiEsteriDom;
	}

	public ComTStatiEsteri getComTStatiEsteriRes() {
		return comTStatiEsteriRes;
	}

	public void setComTStatiEsteriRes(ComTStatiEsteri comTStatiEsteriRes) {
		this.comTStatiEsteriRes = comTStatiEsteriRes;
	}

//	public List<ComRRapportoLavoratore> getComRRapportoLavoratores() {
//		return this.comRRapportoLavoratores;
//	}
//
//	public void setComRRapportoLavoratores(List<ComRRapportoLavoratore> comRRapportoLavoratores) {
//		this.comRRapportoLavoratores = comRRapportoLavoratores;
//	}
//
//	public ComRRapportoLavoratore addComRRapportoLavoratore(ComRRapportoLavoratore comRRapportoLavoratore) {
//		getComRRapportoLavoratores().add(comRRapportoLavoratore);
//		comRRapportoLavoratore.setComDLavoratore(this);
//
//		return comRRapportoLavoratore;
//	}
//
//	public ComRRapportoLavoratore removeComRRapportoLavoratore(ComRRapportoLavoratore comRRapportoLavoratore) {
//		getComRRapportoLavoratores().remove(comRRapportoLavoratore);
//		comRRapportoLavoratore.setComDLavoratore(null);
//
//		return comRRapportoLavoratore;
//	}

//	public List<ComDSedeLavoro> getComDSedeLavoros() {
//		return this.comDSedeLavoros;
//	}
//
//	public void setComDSedeLavoros(List<ComDSedeLavoro> comDSedeLavoros) {
//		this.comDSedeLavoros = comDSedeLavoros;
//	}

}
