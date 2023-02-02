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
 * The persistent class for the COM_D_TUTORE database table.
 * 
 */
@Entity
@Table(name="COM_D_TUTORE")
@NamedQuery(name="ComDTutore.findAll", query="SELECT c FROM ComDTutore c")
@SequenceGenerator(name = "SEQUENCE_TUTORE", sequenceName = "SEQ_ID_COM_D_TUTORE", initialValue = 1, allocationSize = 1)
public class ComDTutore implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComDTutore;
	}

	@Override
	public void setId(Long id) {
		idComDTutore = id;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_D_TUTORE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_TUTORE")
	private Long idComDTutore;

	@Column(name="CF_TUTORE")
	private String cfTutore;

	private String cognome;

	@Column(name="DS_LIVELLO_INQUADRAMENTO")
	private String dsLivelloInquadramento;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_NASCITA")
	private Date dtNascita;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_VISITA_MEDICA")
	private Date dtVisitaMedica;

	@Column(name="FLG_ACCETTAZIONE")
	private String flgAccettazione;

	@Column(name="FLG_TITOLARE")
	private String flgTitolare;

	private String nome;

	@Column(name="NUM_ANNI_ESPERIENZA")
	private BigDecimal numAnniEsperienza;

	private String sesso;

	//bi-directional many-to-one association to ComDRapporto
//	@OneToMany(mappedBy="comDTutore")
//	private List<ComDRapporto> comDRapportos;

	//bi-directional many-to-one association to ComTGradoContrattuale
	@ManyToOne
	@JoinColumn(name="ID_COM_T_GRADO_CONTRATTUALE")
	private ComTGradoContrattuale comTGradoContrattuale;

	//bi-directional many-to-one association to ComTIstat2001livello5
	@ManyToOne
	@JoinColumn(name="ID_COM_ISTAT2001LIVELLO5")
	private ComTIstat2001livello5 comTIstat2001livello5;

	public ComDTutore() {
	}

	public long getIdComDTutore() {
		return this.idComDTutore;
	}

	public void setIdComDTutore(long idComDTutore) {
		this.idComDTutore = idComDTutore;
	}

	public String getCfTutore() {
		return this.cfTutore;
	}

	public void setCfTutore(String cfTutore) {
		this.cfTutore = cfTutore;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getDsLivelloInquadramento() {
		return this.dsLivelloInquadramento;
	}

	public void setDsLivelloInquadramento(String dsLivelloInquadramento) {
		this.dsLivelloInquadramento = dsLivelloInquadramento;
	}

	public Date getDtNascita() {
		return this.dtNascita;
	}

	public void setDtNascita(Date dtNascita) {
		this.dtNascita = dtNascita;
	}

	public Date getDtVisitaMedica() {
		return this.dtVisitaMedica;
	}

	public void setDtVisitaMedica(Date dtVisitaMedica) {
		this.dtVisitaMedica = dtVisitaMedica;
	}

	public String getFlgAccettazione() {
		return this.flgAccettazione;
	}

	public void setFlgAccettazione(String flgAccettazione) {
		this.flgAccettazione = flgAccettazione;
	}

	public String getFlgTitolare() {
		return this.flgTitolare;
	}

	public void setFlgTitolare(String flgTitolare) {
		this.flgTitolare = flgTitolare;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getNumAnniEsperienza() {
		return this.numAnniEsperienza;
	}

	public void setNumAnniEsperienza(BigDecimal numAnniEsperienza) {
		this.numAnniEsperienza = numAnniEsperienza;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

//	public List<ComDRapporto> getComDRapportos() {
//		return this.comDRapportos;
//	}
//
//	public void setComDRapportos(List<ComDRapporto> comDRapportos) {
//		this.comDRapportos = comDRapportos;
//	}
//
//	public ComDRapporto addComDRapporto(ComDRapporto comDRapporto) {
//		getComDRapportos().add(comDRapporto);
//		comDRapporto.setComDTutore(this);
//
//		return comDRapporto;
//	}
//
//	public ComDRapporto removeComDRapporto(ComDRapporto comDRapporto) {
//		getComDRapportos().remove(comDRapporto);
//		comDRapporto.setComDTutore(null);
//
//		return comDRapporto;
//	}

	public ComTGradoContrattuale getComTGradoContrattuale() {
		return this.comTGradoContrattuale;
	}

	public void setComTGradoContrattuale(ComTGradoContrattuale comTGradoContrattuale) {
		this.comTGradoContrattuale = comTGradoContrattuale;
	}

	public ComTIstat2001livello5 getComTIstat2001livello5() {
		return this.comTIstat2001livello5;
	}

	public void setComTIstat2001livello5(ComTIstat2001livello5 comTIstat2001livello5) {
		this.comTIstat2001livello5 = comTIstat2001livello5;
	}

}
