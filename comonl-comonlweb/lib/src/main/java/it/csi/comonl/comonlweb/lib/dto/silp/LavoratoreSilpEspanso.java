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
import java.util.Date;

import it.csi.comonl.comonlweb.lib.dto.comunicazione.Toponimo;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Cittadinanza;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.LivelloStudio;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.MotivoPermesso;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Questura;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatusStraniero;

public class LavoratoreSilpEspanso extends LavoratoreSilp implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal idSilLavAnagrafica;

	private String codCpiCompentenza;
	private String descrCpiCompentenza;
	private String descrCpiProprieta;
	private String codCpiProprieta;
	private String numTelDom;
	private String numTelRes;
	private String dsMail;
	private String numTelCell;

	private String codStatoCivile;
	private String descrStatoCivile;

	private String flagCartaSoggiorno;

	private Date dataScadPermSogg;
	private Date dataRichPermSogg;

	private MotivoPermesso motivoPermesso;

	private String descrFax;
	private String descrWeb;
	private String siglaProvDomicilio;

	private StatusStraniero statusStraniero;

	private Cittadinanza cittadinanza;

	private Questura questura;

	private String descrNumDocPermSogg;

	private String numFamiliariCarico;
	private String[] idLavoratoriAccorpati = new String[] {};

	private LivelloStudio livelloStudio;

	private String capDomicilio;
	private String capResidenza;
	private String indirizzoDomicilio;
	private String indirizzoResidenza;
	private String numCivicoDomicilio;
	private String numCivicoResidenza;

	private Toponimo toponimoDomicilio;
	private Toponimo toponimoResidenza;

	public String getNumFamiliariCarico() {
		return numFamiliariCarico;
	}

	public void setNumFamiliariCarico(String numFamiliariCarico) {
		this.numFamiliariCarico = numFamiliariCarico;
	}

	public String getDescrNumDocPermSogg() {
		return descrNumDocPermSogg;
	}

	public void setDescrNumDocPermSogg(String descrNumDocPermSogg) {
		this.descrNumDocPermSogg = descrNumDocPermSogg;
	}

	public LavoratoreSilpEspanso() {
	}

	public String getCodCpiCompentenza() {
		return codCpiCompentenza;
	}

	public void setCodCpiCompentenza(String codCpiCompentenza) {
		this.codCpiCompentenza = codCpiCompentenza;
	}

	public String getDescrCpiCompentenza() {
		return descrCpiCompentenza;
	}

	public void setDescrCpiCompentenza(String descrCpiCompentenza) {
		this.descrCpiCompentenza = descrCpiCompentenza;
	}

	public String getDescrCpiProprieta() {
		return descrCpiProprieta;
	}

	public void setDescrCpiProprieta(String descrCpiProprieta) {
		this.descrCpiProprieta = descrCpiProprieta;
	}

	public String getCodCpiProprieta() {
		return codCpiProprieta;
	}

	public void setCodCpiProprieta(String codCpiProprieta) {
		this.codCpiProprieta = codCpiProprieta;
	}

	public String getNumTelDom() {
		return numTelDom;
	}

	public void setNumTelDom(String numTelDom) {
		this.numTelDom = numTelDom;
	}

	public String getNumTelRes() {
		return numTelRes;
	}

	public void setNumTelRes(String numTelRes) {
		this.numTelRes = numTelRes;
	}

	public String getDsMail() {
		return dsMail;
	}

	public void setDsMail(String dsMail) {
		this.dsMail = dsMail;
	}

	public String getNumTelCell() {
		return numTelCell;
	}

	public void setNumTelCell(String numTelCell) {
		this.numTelCell = numTelCell;
	}

	public String getCodStatoCivile() {
		return codStatoCivile;
	}

	public void setCodStatoCivile(String codStatoCivile) {
		this.codStatoCivile = codStatoCivile;
	}

	public String getDescrStatoCivile() {
		return descrStatoCivile;
	}

	public void setDescrStatoCivile(String descrStatoCivile) {
		this.descrStatoCivile = descrStatoCivile;
	}

	public String getFlagCartaSoggiorno() {
		return flagCartaSoggiorno;
	}

	public void setFlagCartaSoggiorno(String flagCartaSoggiorno) {
		this.flagCartaSoggiorno = flagCartaSoggiorno;
	}

	public String getDescrFax() {
		return descrFax;
	}

	public void setDescrFax(String descrFax) {
		this.descrFax = descrFax;
	}

	public String getDescrWeb() {
		return descrWeb;
	}

	public void setDescrWeb(String descrWeb) {
		this.descrWeb = descrWeb;
	}

	public String getSiglaProvDomicilio() {
		return siglaProvDomicilio;
	}

	public void setSiglaProvDomicilio(String siglaProvDomicilio) {
		this.siglaProvDomicilio = siglaProvDomicilio;
	}

	public String[] getIdLavoratoriAccorpati() {
		return idLavoratoriAccorpati;
	}

	public void setIdLavoratoriAccorpati(String[] idLavoratoriAccorpati) {
		this.idLavoratoriAccorpati = idLavoratoriAccorpati;
	}

	public String toString() {
		StringBuffer s = new StringBuffer("AnagraficaLavoratoreEspansa(");
		s.append(",cognome=" + getCognome());
		s.append(",nome=" + getNome());
		s.append(",idLavoratoriAccorpati=");
		if (idLavoratoriAccorpati == null) {
			s.append("null");
		} else {
			s.append("[");
			for (int i = 0; i < idLavoratoriAccorpati.length; i++) {
				if (i > 0)
					s.append(",");
				s.append(idLavoratoriAccorpati[i]);
			}
			s.append("]");
		}
		s.append(")");
		return s.toString();
	}

	public StatusStraniero getStatusStraniero() {
		return statusStraniero;
	}

	public void setStatusStraniero(StatusStraniero statusStraniero) {
		this.statusStraniero = statusStraniero;
	}

	public Cittadinanza getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(Cittadinanza cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public Questura getQuestura() {
		return questura;
	}

	public void setQuestura(Questura questura) {
		this.questura = questura;
	}

	public LivelloStudio getLivelloStudio() {
		return livelloStudio;
	}

	public void setLivelloStudio(LivelloStudio livelloStudio) {
		this.livelloStudio = livelloStudio;
	}

	public MotivoPermesso getMotivoPermesso() {
		return motivoPermesso;
	}

	public void setMotivoPermesso(MotivoPermesso motivoPermesso) {
		this.motivoPermesso = motivoPermesso;
	}

	public String getCapDomicilio() {
		return capDomicilio;
	}

	public void setCapDomicilio(String capDomicilio) {
		this.capDomicilio = capDomicilio;
	}

	public String getCapResidenza() {
		return capResidenza;
	}

	public void setCapResidenza(String capResidenza) {
		this.capResidenza = capResidenza;
	}

	public String getIndirizzoDomicilio() {
		return indirizzoDomicilio;
	}

	public void setIndirizzoDomicilio(String indirizzoDomicilio) {
		this.indirizzoDomicilio = indirizzoDomicilio;
	}

	public String getIndirizzoResidenza() {
		return indirizzoResidenza;
	}

	public void setIndirizzoResidenza(String indirizzoResidenza) {
		this.indirizzoResidenza = indirizzoResidenza;
	}

	public String getNumCivicoDomicilio() {
		return numCivicoDomicilio;
	}

	public void setNumCivicoDomicilio(String numCivicoDomicilio) {
		this.numCivicoDomicilio = numCivicoDomicilio;
	}

	public String getNumCivicoResidenza() {
		return numCivicoResidenza;
	}

	public void setNumCivicoResidenza(String numCivicoResidenza) {
		this.numCivicoResidenza = numCivicoResidenza;
	}

	public Toponimo getToponimoDomicilio() {
		return toponimoDomicilio;
	}

	public void setToponimoDomicilio(Toponimo toponimoDomicilio) {
		this.toponimoDomicilio = toponimoDomicilio;
	}

	public Toponimo getToponimoResidenza() {
		return toponimoResidenza;
	}

	public void setToponimoResidenza(Toponimo toponimoResidenza) {
		this.toponimoResidenza = toponimoResidenza;
	}

	public BigDecimal getIdSilLavAnagrafica() {
		return idSilLavAnagrafica;
	}

	public void setIdSilLavAnagrafica(BigDecimal idSilLavAnagrafica) {
		this.idSilLavAnagrafica = idSilLavAnagrafica;
	}

	public Date getDataScadPermSogg() {
		return dataScadPermSogg;
	}

	public void setDataScadPermSogg(Date dataScadPermSogg) {
		this.dataScadPermSogg = dataScadPermSogg;
	}

	public Date getDataRichPermSogg() {
		return dataRichPermSogg;
	}

	public void setDataRichPermSogg(Date dataRichPermSogg) {
		this.dataRichPermSogg = dataRichPermSogg;
	}

}
