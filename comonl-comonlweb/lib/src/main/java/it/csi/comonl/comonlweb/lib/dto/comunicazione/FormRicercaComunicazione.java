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

import java.util.Date;
import java.util.List;

import it.csi.comonl.comonlweb.lib.dto.decodifiche.Provincia;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoComunicazioneTu;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoTrasferimento;
import it.csi.comonl.comonlweb.lib.util.ComonlStringUtils;

public class FormRicercaComunicazione {
	private String codiceFiscaleAzienda;
	private String denominazioneAzienda;
	private String codiceFiscaleLavoratore;
	private String cognomeLavoratore;
	private String nomeLavoratore;
	private Date dataInvioInserimentoDa;
	private Date dataInvioInserimentoA;
	private List<StatoComunicazione> statoComunicaziones;
	private String codiceRegionale;
	private TipoComunicazione tipoComunicazione;
	private TipoComunicazioneTu tipoComunicazioneTu;
	private Provincia provinciaProtocollo;
	private Long annoProtocollo;
	private Long numeroProtocollo;

	private String codiceFiscaleSoggetto;
	private TipoTrasferimento tipoTrasferimento;
	private String tipoTracciato;

	private String tipoVariazione;

	public String getCodiceFiscaleAzienda() {
		return codiceFiscaleAzienda;
	}

	public void setCodiceFiscaleAzienda(String codiceFiscaleAzienda) {
		this.codiceFiscaleAzienda = codiceFiscaleAzienda;
	}

	public String getDenominazioneAzienda() {
		return denominazioneAzienda;
	}

	public void setDenominazioneAzienda(String denominazioneAzienda) {
		this.denominazioneAzienda = denominazioneAzienda;
	}

	public String getCodiceFiscaleLavoratore() {
		return codiceFiscaleLavoratore;
	}

	public void setCodiceFiscaleLavoratore(String codiceFiscaleLavoratore) {
		this.codiceFiscaleLavoratore = codiceFiscaleLavoratore;
	}

	public String getCognomeLavoratore() {
		return cognomeLavoratore;
	}

	public void setCognomeLavoratore(String cognomeLavoratore) {
		this.cognomeLavoratore = cognomeLavoratore;
	}

	public String getNomeLavoratore() {
		return nomeLavoratore;
	}

	public void setNomeLavoratore(String nomeLavoratore) {
		this.nomeLavoratore = nomeLavoratore;
	}

	public Date getDataInvioInserimentoDa() {
		return dataInvioInserimentoDa;
	}

	public void setDataInvioInserimentoDa(Date dataInvioInserimentoDa) {
		this.dataInvioInserimentoDa = dataInvioInserimentoDa;
	}

	public Date getDataInvioInserimentoA() {
		return dataInvioInserimentoA;
	}

	public void setDataInvioInserimentoA(Date dataInvioInserimentoA) {
		this.dataInvioInserimentoA = dataInvioInserimentoA;
	}

	public List<StatoComunicazione> getStatoComunicaziones() {
		return statoComunicaziones;
	}

	public void setStatoComunicaziones(List<StatoComunicazione> statoComunicaziones) {
		this.statoComunicaziones = statoComunicaziones;
	}

	public String getCodiceRegionale() {
		return codiceRegionale;
	}

	public void setCodiceRegionale(String codiceRegionale) {
		this.codiceRegionale = codiceRegionale;
	}

	public TipoComunicazione getTipoComunicazione() {
		return tipoComunicazione;
	}

	public void setTipoComunicazione(TipoComunicazione tipoComunicazione) {
		this.tipoComunicazione = tipoComunicazione;
	}

	public TipoComunicazioneTu getTipoComunicazioneTu() {
		return tipoComunicazioneTu;
	}

	public void setTipoComunicazioneTu(TipoComunicazioneTu tipoComunicazioneTu) {
		this.tipoComunicazioneTu = tipoComunicazioneTu;
	}

	public Provincia getProvinciaProtocollo() {
		return provinciaProtocollo;
	}

	public void setProvinciaProtocollo(Provincia provinciaProtocollo) {
		this.provinciaProtocollo = provinciaProtocollo;
	}

	public Long getAnnoProtocollo() {
		return annoProtocollo;
	}

	public void setAnnoProtocollo(Long annoProtocollo) {
		this.annoProtocollo = annoProtocollo;
	}

	public Long getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(Long numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}

	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}

	public TipoTrasferimento getTipoTrasferimento() {
		return tipoTrasferimento;
	}

	public void setTipoTrasferimento(TipoTrasferimento tipoTrasferimento) {
		this.tipoTrasferimento = tipoTrasferimento;
	}

	public String getTipoTracciato() {
		return tipoTracciato;
	}

	public void setTipoTracciato(String tipoTracciato) {
		this.tipoTracciato = tipoTracciato;
	}

	public String getTipoVariazione() {
		return tipoVariazione;
	}

	public void setTipoVariazione(String tipoVariazione) {
		this.tipoVariazione = tipoVariazione;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RicercaComunicazione [");
		builder.append("annoProtocollo=" + ComonlStringUtils.checkNull(annoProtocollo));
		builder.append(",\n");
		builder.append("numeroProtocollo=" + ComonlStringUtils.checkNull(numeroProtocollo));
		builder.append(",\n");
		builder.append("dataInvioInserimentoDa=" + ComonlStringUtils.checkNull(dataInvioInserimentoDa));
		builder.append(",\n ");
		builder.append("dataInvioInserimentoA=" + ComonlStringUtils.checkNull(dataInvioInserimentoA));
		builder.append(",\n ");
		builder.append("codiceFiscaleAzienda=" + ComonlStringUtils.checkNull(codiceFiscaleAzienda));
		builder.append(",\n ");
		builder.append("denominazioneAzienda=" + ComonlStringUtils.checkNull(denominazioneAzienda));
		builder.append(",\n ");
		builder.append("codiceFiscaleLavoratore=" + ComonlStringUtils.checkNull(codiceFiscaleLavoratore));
		builder.append(",\n ");
		builder.append("cognomeLavoratore=" + ComonlStringUtils.checkNull(cognomeLavoratore));
		builder.append(",\n ");
		builder.append("nomeLavoratore=" + ComonlStringUtils.checkNull(nomeLavoratore));
		builder.append(",\n ");
		builder.append("codiceRegionale=" + ComonlStringUtils.checkNull(codiceRegionale));
		builder.append(",\n ");
		if (provinciaProtocollo != null) {
			builder.append("provinciaProtocollo=" + ComonlStringUtils.checkNull(provinciaProtocollo.getId()));
			builder.append(",\n ");
		}
		if (tipoComunicazione != null) {
			builder.append("tipoComunicazione=" + ComonlStringUtils.checkNull(tipoComunicazione.getId()));
			builder.append(",\n ");
		}
		if (tipoComunicazioneTu != null) {
			builder.append("tipoComunicazioneTu=" + ComonlStringUtils.checkNull(tipoComunicazioneTu.getId()));
			builder.append(",\n ");
		}
		if (statoComunicaziones != null) {
			for (StatoComunicazione loStatoComunicazione : statoComunicaziones) {
				builder.append("loStatoComunicazione=" + ComonlStringUtils.checkNull(loStatoComunicazione.getId()));
				builder.append(", ");
			}
		}
		builder.append(",\n ");
		builder.append("codiceFiscaleSoggetto=" + ComonlStringUtils.checkNull(codiceFiscaleSoggetto));
		builder.append(",\n ");
		if (tipoTrasferimento != null) {
			builder.append("tipoTrasferimento=" + ComonlStringUtils.checkNull(tipoTrasferimento.getId()));
			builder.append(",\n ");
		}
		builder.append("tipoTracciato=" + ComonlStringUtils.checkNull(tipoTracciato));
		builder.append(",\n ");

		if (super.toString() != null) {
			builder.append("\n super.toString()=");
			builder.append(super.toString());
		}
		builder.append("]");
		return builder.toString();
	}

}
