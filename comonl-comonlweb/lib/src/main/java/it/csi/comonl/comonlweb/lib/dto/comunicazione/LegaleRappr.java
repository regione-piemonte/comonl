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
package it.csi.comonl.comonlweb.lib.dto.comunicazione;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
public class LegaleRappr extends BaseAuditedDto<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String dsCognome;
	private String dsNome;
	private Date dtInsert;
	private Date dtNascita;
	private Date dtScadenzaPermessoSogg;
	private Date dtUltMod;
	private String flgResidenzaItaliana;
	private String flgSoggiornanteItalia;
	private String idUserInsert;
	private String idUserUltMod;
	private String numeroDocumento;
	private String sesso;
	private Datore datore;
	private Cittadinanza cittadinanza;
	private Comune comune;
	private MotivoPermesso motivoPermesso;
	private Questura questura;
	private StatiEsteri statiEsteri;
	private StatusStraniero statusStraniero;

	private String idSedeAssociata;
	private BigDecimal idLegaleRapprSilp;

	/**
	 * @return the dsCognome
	 */
	public String getDsCognome() {
		return dsCognome;
	}

	/**
	 * @param dsCognome the dsCognome to set
	 */
	public void setDsCognome(String dsCognome) {
		this.dsCognome = dsCognome;
	}

	/**
	 * @return the dsNome
	 */
	public String getDsNome() {
		return dsNome;
	}

	/**
	 * @param dsNome the dsNome to set
	 */
	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	/**
	 * @return the dtInsert
	 */
	public Date getDtInsert() {
		return dtInsert;
	}

	/**
	 * @param dtInsert the dtInsert to set
	 */
	public void setDtInsert(Date dtInsert) {
		this.dtInsert = dtInsert;
	}

	/**
	 * @return the dtNascita
	 */
	public Date getDtNascita() {
		return dtNascita;
	}

	/**
	 * @param dtNascita the dtNascita to set
	 */
	public void setDtNascita(Date dtNascita) {
		this.dtNascita = dtNascita;
	}

	/**
	 * @return the dtScadenzaPermessoSogg
	 */
	public Date getDtScadenzaPermessoSogg() {
		return dtScadenzaPermessoSogg;
	}

	/**
	 * @param dtScadenzaPermessoSogg the dtScadenzaPermessoSogg to set
	 */
	public void setDtScadenzaPermessoSogg(Date dtScadenzaPermessoSogg) {
		this.dtScadenzaPermessoSogg = dtScadenzaPermessoSogg;
	}

	/**
	 * @return the dtUltMod
	 */
	public Date getDtUltMod() {
		return dtUltMod;
	}

	/**
	 * @param dtUltMod the dtUltMod to set
	 */
	public void setDtUltMod(Date dtUltMod) {
		this.dtUltMod = dtUltMod;
	}

	/**
	 * @return the flgResidenzaItaliana
	 */
	public String getFlgResidenzaItaliana() {
		return flgResidenzaItaliana;
	}

	/**
	 * @param flgResidenzaItaliana the flgResidenzaItaliana to set
	 */
	public void setFlgResidenzaItaliana(String flgResidenzaItaliana) {
		this.flgResidenzaItaliana = flgResidenzaItaliana;
	}

	/**
	 * @return the flgSoggiornanteItalia
	 */
	public String getFlgSoggiornanteItalia() {
		return flgSoggiornanteItalia;
	}

	/**
	 * @param flgSoggiornanteItalia the flgSoggiornanteItalia to set
	 */
	public void setFlgSoggiornanteItalia(String flgSoggiornanteItalia) {
		this.flgSoggiornanteItalia = flgSoggiornanteItalia;
	}

	/**
	 * @return the idUserInsert
	 */
	public String getIdUserInsert() {
		return idUserInsert;
	}

	/**
	 * @param idUserInsert the idUserInsert to set
	 */
	public void setIdUserInsert(String idUserInsert) {
		this.idUserInsert = idUserInsert;
	}

	/**
	 * @return the idUserUltMod
	 */
	public String getIdUserUltMod() {
		return idUserUltMod;
	}

	/**
	 * @param idUserUltMod the idUserUltMod to set
	 */
	public void setIdUserUltMod(String idUserUltMod) {
		this.idUserUltMod = idUserUltMod;
	}

	/**
	 * @return the numeroDocumento
	 */
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	/**
	 * @param numeroDocumento the numeroDocumento to set
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	/**
	 * @return the sesso
	 */
	public String getSesso() {
		return sesso;
	}

	/**
	 * @param sesso the sesso to set
	 */
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	/**
	 * @return the datore
	 */
	public Datore getDatore() {
		return datore;
	}

	/**
	 * @param datore the datore to set
	 */
	public void setDatore(Datore datore) {
		this.datore = datore;
	}

	/**
	 * @return the cittadinanza
	 */
	public Cittadinanza getCittadinanza() {
		return cittadinanza;
	}

	/**
	 * @param cittadinanza the cittadinanza to set
	 */
	public void setCittadinanza(Cittadinanza cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	/**
	 * @return the comune
	 */
	public Comune getComune() {
		return comune;
	}

	/**
	 * @param comune the comune to set
	 */
	public void setComune(Comune comune) {
		this.comune = comune;
	}

	/**
	 * @return the motivoPermesso
	 */
	public MotivoPermesso getMotivoPermesso() {
		return motivoPermesso;
	}

	/**
	 * @param motivoPermesso the motivoPermesso to set
	 */
	public void setMotivoPermesso(MotivoPermesso motivoPermesso) {
		this.motivoPermesso = motivoPermesso;
	}

	/**
	 * @return the questura
	 */
	public Questura getQuestura() {
		return questura;
	}

	/**
	 * @param questura the questura to set
	 */
	public void setQuestura(Questura questura) {
		this.questura = questura;
	}

	/**
	 * @return the statiEsteri
	 */
	public StatiEsteri getStatiEsteri() {
		return statiEsteri;
	}

	/**
	 * @param statiEsteri the statiEsteri to set
	 */
	public void setStatiEsteri(StatiEsteri statiEsteri) {
		this.statiEsteri = statiEsteri;
	}

	/**
	 * @return the statusStraniero
	 */
	public StatusStraniero getStatusStraniero() {
		return statusStraniero;
	}

	/**
	 * @param statusStraniero the statusStraniero to set
	 */
	public void setStatusStraniero(StatusStraniero statusStraniero) {
		this.statusStraniero = statusStraniero;
	}

	public String getIdSedeAssociata() {
		return idSedeAssociata;
	}

	public void setIdSedeAssociata(String idSedeAssociata) {
		this.idSedeAssociata = idSedeAssociata;
	}

	public BigDecimal getIdLegaleRapprSilp() {
		return idLegaleRapprSilp;
	}

	public void setIdLegaleRapprSilp(BigDecimal idLegaleRapprSilp) {
		this.idLegaleRapprSilp = idLegaleRapprSilp;
	}

}
