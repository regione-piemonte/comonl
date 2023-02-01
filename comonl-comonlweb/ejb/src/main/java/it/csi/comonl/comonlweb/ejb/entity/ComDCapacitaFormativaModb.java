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
 * The persistent class for the COM_D_CAPACITA_FORMATIVA_MODB database table.
 * 
 */
@Entity
@Table(name="COM_D_CAPACITA_FORMATIVA_MODB")
@NamedQuery(name="ComDCapacitaFormativaModb.findAll", query="SELECT c FROM ComDCapacitaFormativaModb c")
public class ComDCapacitaFormativaModb implements Serializable, BaseEntity<Long> {

	@Override
	public Long getId() {
		return idComDCapacitaFormModB;
	}

	@Override
	public void setId(Long id) {
		idComDCapacitaFormModB = id;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_COM_D_CAPACITA_FORM_MOD_B")
	private long idComDCapacitaFormModB;

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
	@Column(name="PDF_QUESTIONARIO_MOD_B")
	private byte[] pdfQuestionarioModB;

	@Column(name="PIVA_AZIENDA")
	private String pivaAzienda;

	@Column(name="R_1_A_ESPERIENZA")
	private String r1AEsperienza;

	@Column(name="R_1_B_SPECIFICHE")
	private String r1BSpecifiche;

	@Column(name="R_2_PROF_CONTRATTUALE")
	private String r2ProfContrattuale;

	@Column(name="R_3_LUOGHI_SIC")
	private String r3LuoghiSic;

	@Column(name="R_4_LUOGHI_PFI")
	private String r4LuoghiPfi;

	@Column(name="R_5_TUTORE")
	private String r5Tutore;

	//bi-directional many-to-one association to ComTComune
	@ManyToOne
	@JoinColumn(name="ID_COM_T_COMUNE_L_R")
	private ComTComune comTComune1;

	//bi-directional many-to-one association to ComTComune
	@ManyToOne
	@JoinColumn(name="ID_COM_T_COMUNE_AZI")
	private ComTComune comTComune2;

	public ComDCapacitaFormativaModb() {
	}

	public long getIdComDCapacitaFormModB() {
		return this.idComDCapacitaFormModB;
	}

	public void setIdComDCapacitaFormModB(long idComDCapacitaFormModB) {
		this.idComDCapacitaFormModB = idComDCapacitaFormModB;
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

	public byte[] getPdfQuestionarioModB() {
		return this.pdfQuestionarioModB;
	}

	public void setPdfQuestionarioModB(byte[] pdfQuestionarioModB) {
		this.pdfQuestionarioModB = pdfQuestionarioModB;
	}

	public String getPivaAzienda() {
		return this.pivaAzienda;
	}

	public void setPivaAzienda(String pivaAzienda) {
		this.pivaAzienda = pivaAzienda;
	}

	public String getR1AEsperienza() {
		return this.r1AEsperienza;
	}

	public void setR1AEsperienza(String r1AEsperienza) {
		this.r1AEsperienza = r1AEsperienza;
	}

	public String getR1BSpecifiche() {
		return this.r1BSpecifiche;
	}

	public void setR1BSpecifiche(String r1BSpecifiche) {
		this.r1BSpecifiche = r1BSpecifiche;
	}

	public String getR2ProfContrattuale() {
		return this.r2ProfContrattuale;
	}

	public void setR2ProfContrattuale(String r2ProfContrattuale) {
		this.r2ProfContrattuale = r2ProfContrattuale;
	}

	public String getR3LuoghiSic() {
		return this.r3LuoghiSic;
	}

	public void setR3LuoghiSic(String r3LuoghiSic) {
		this.r3LuoghiSic = r3LuoghiSic;
	}

	public String getR4LuoghiPfi() {
		return this.r4LuoghiPfi;
	}

	public void setR4LuoghiPfi(String r4LuoghiPfi) {
		this.r4LuoghiPfi = r4LuoghiPfi;
	}

	public String getR5Tutore() {
		return this.r5Tutore;
	}

	public void setR5Tutore(String r5Tutore) {
		this.r5Tutore = r5Tutore;
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
