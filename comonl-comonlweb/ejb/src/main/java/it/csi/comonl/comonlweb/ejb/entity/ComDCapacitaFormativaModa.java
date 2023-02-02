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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.comonl.comonlweb.ejb.entity.base.BaseEntity;


/**
 * The persistent class for the COM_D_CAPACITA_FORMATIVA_MODA database table.
 * 
 */
@Entity
@Table(name="COM_D_CAPACITA_FORMATIVA_MODA")
@NamedQuery(name="ComDCapacitaFormativaModa.findAll", query="SELECT c FROM ComDCapacitaFormativaModa c")
public class ComDCapacitaFormativaModa implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComDCapacitaFormModA;
	}

	@Override
	public void setId(Long id) {
		idComDCapacitaFormModA = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_D_CAPACITA_FORM_MOD_A")
	private long idComDCapacitaFormModA;

	@Column(name="CF_AZIENDA")
	private String cfAzienda;

	@Column(name="CF_DELEGATO_RL")
	private String cfDelegatoRl;

	@Column(name="COGNOME_RL")
	private String cognomeRl;

	@Column(name="DS_AZI_DENOMINAZIONE")
	private String dsAziDenominazione;

	@Column(name="DS_INDIRIZ_AZI_SEDELEGALE")
	private String dsIndirizAziSedelegale;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INSERT")
	private Date dtInsert;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_NASC_RL")
	private Date dtNascRl;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_ULT_MOD")
	private Date dtUltMod;

	@Column(name="FLG_FIRMATO")
	private String flgFirmato;

	@Column(name="ID_COM_T_STATI_ESTERI")
	private BigDecimal idComTStatiEsteri;

	@Column(name="ID_USER_INSERT")
	private String idUserInsert;

	@Column(name="ID_USER_ULT_MOD")
	private String idUserUltMod;

	@Column(name="NOME_RL")
	private String nomeRl;

	@Lob
	@Column(name="PDF_QUESTIONARIO_MOD_A")
	private byte[] pdfQuestionarioModA;

	@Column(name="PIVA_AZIENDA")
	private String pivaAzienda;

	@Column(name="R_1_A_PERC_ACCADEMICI")
	private String r1APercAccademici;

	@Column(name="R_1_A_PERC_FORMPROF")
	private String r1APercFormprof;

	@Column(name="R_1_A_PERC_IMPRESA")
	private String r1APercImpresa;

	@Column(name="R_1_A_PERC_SCOLASTICI")
	private String r1APercScolastici;

	@Column(name="R_1_B_RISORSE_TITOLO")
	private String r1BRisorseTitolo;

	@Column(name="R_1_C_RISORSE_POSIZIONE")
	private String r1CRisorsePosizione;

	@Column(name="R_2_LUOGHI_IDONEI")
	private String r2LuoghiIdonei;

	@Column(name="R_3_TUTORE")
	private String r3Tutore;

	//bi-directional many-to-one association to ComTComune
	@ManyToOne
	@JoinColumn(name="ID_COM_T_COMUNE_L_R")
	private ComTComune comTComune1;

	//bi-directional many-to-one association to ComTComune
	@ManyToOne
	@JoinColumn(name="ID_COM_T_COMUNE_AZI")
	private ComTComune comTComune2;

	public ComDCapacitaFormativaModa() {
	}

	public long getIdComDCapacitaFormModA() {
		return this.idComDCapacitaFormModA;
	}

	public void setIdComDCapacitaFormModA(long idComDCapacitaFormModA) {
		this.idComDCapacitaFormModA = idComDCapacitaFormModA;
	}

	public String getCfAzienda() {
		return this.cfAzienda;
	}

	public void setCfAzienda(String cfAzienda) {
		this.cfAzienda = cfAzienda;
	}

	public String getCfDelegatoRl() {
		return this.cfDelegatoRl;
	}

	public void setCfDelegatoRl(String cfDelegatoRl) {
		this.cfDelegatoRl = cfDelegatoRl;
	}

	public String getCognomeRl() {
		return this.cognomeRl;
	}

	public void setCognomeRl(String cognomeRl) {
		this.cognomeRl = cognomeRl;
	}

	public String getDsAziDenominazione() {
		return this.dsAziDenominazione;
	}

	public void setDsAziDenominazione(String dsAziDenominazione) {
		this.dsAziDenominazione = dsAziDenominazione;
	}

	public String getDsIndirizAziSedelegale() {
		return this.dsIndirizAziSedelegale;
	}

	public void setDsIndirizAziSedelegale(String dsIndirizAziSedelegale) {
		this.dsIndirizAziSedelegale = dsIndirizAziSedelegale;
	}

	public Date getDtInsert() {
		return this.dtInsert;
	}

	public void setDtInsert(Date dtInsert) {
		this.dtInsert = dtInsert;
	}

	public Date getDtNascRl() {
		return this.dtNascRl;
	}

	public void setDtNascRl(Date dtNascRl) {
		this.dtNascRl = dtNascRl;
	}

	public Date getDtUltMod() {
		return this.dtUltMod;
	}

	public void setDtUltMod(Date dtUltMod) {
		this.dtUltMod = dtUltMod;
	}

	public String getFlgFirmato() {
		return this.flgFirmato;
	}

	public void setFlgFirmato(String flgFirmato) {
		this.flgFirmato = flgFirmato;
	}

	public BigDecimal getIdComTStatiEsteri() {
		return this.idComTStatiEsteri;
	}

	public void setIdComTStatiEsteri(BigDecimal idComTStatiEsteri) {
		this.idComTStatiEsteri = idComTStatiEsteri;
	}

	public String getIdUserInsert() {
		return this.idUserInsert;
	}

	public void setIdUserInsert(String idUserInsert) {
		this.idUserInsert = idUserInsert;
	}

	public String getIdUserUltMod() {
		return this.idUserUltMod;
	}

	public void setIdUserUltMod(String idUserUltMod) {
		this.idUserUltMod = idUserUltMod;
	}

	public String getNomeRl() {
		return this.nomeRl;
	}

	public void setNomeRl(String nomeRl) {
		this.nomeRl = nomeRl;
	}

	public byte[] getPdfQuestionarioModA() {
		return this.pdfQuestionarioModA;
	}

	public void setPdfQuestionarioModA(byte[] pdfQuestionarioModA) {
		this.pdfQuestionarioModA = pdfQuestionarioModA;
	}

	public String getPivaAzienda() {
		return this.pivaAzienda;
	}

	public void setPivaAzienda(String pivaAzienda) {
		this.pivaAzienda = pivaAzienda;
	}

	public String getR1APercAccademici() {
		return this.r1APercAccademici;
	}

	public void setR1APercAccademici(String r1APercAccademici) {
		this.r1APercAccademici = r1APercAccademici;
	}

	public String getR1APercFormprof() {
		return this.r1APercFormprof;
	}

	public void setR1APercFormprof(String r1APercFormprof) {
		this.r1APercFormprof = r1APercFormprof;
	}

	public String getR1APercImpresa() {
		return this.r1APercImpresa;
	}

	public void setR1APercImpresa(String r1APercImpresa) {
		this.r1APercImpresa = r1APercImpresa;
	}

	public String getR1APercScolastici() {
		return this.r1APercScolastici;
	}

	public void setR1APercScolastici(String r1APercScolastici) {
		this.r1APercScolastici = r1APercScolastici;
	}

	public String getR1BRisorseTitolo() {
		return this.r1BRisorseTitolo;
	}

	public void setR1BRisorseTitolo(String r1BRisorseTitolo) {
		this.r1BRisorseTitolo = r1BRisorseTitolo;
	}

	public String getR1CRisorsePosizione() {
		return this.r1CRisorsePosizione;
	}

	public void setR1CRisorsePosizione(String r1CRisorsePosizione) {
		this.r1CRisorsePosizione = r1CRisorsePosizione;
	}

	public String getR2LuoghiIdonei() {
		return this.r2LuoghiIdonei;
	}

	public void setR2LuoghiIdonei(String r2LuoghiIdonei) {
		this.r2LuoghiIdonei = r2LuoghiIdonei;
	}

	public String getR3Tutore() {
		return this.r3Tutore;
	}

	public void setR3Tutore(String r3Tutore) {
		this.r3Tutore = r3Tutore;
	}

	public ComTComune getComTComune1() {
		return this.comTComune1;
	}

	public void setComTComune1(ComTComune comTComune1) {
		this.comTComune1 = comTComune1;
	}

	public ComTComune getComTComune2() {
		return this.comTComune2;
	}

	public void setComTComune2(ComTComune comTComune2) {
		this.comTComune2 = comTComune2;
	}

}
