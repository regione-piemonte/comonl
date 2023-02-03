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
/*
 *
 */
package it.csi.comonl.comonlweb.lib.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ComonlConstants {
	public static final String COMPONENT_NAME = "COMONL";

	public static final String APPLICATION_NAME_IRIDE = "COMONL";

	public static final String LOGGER = "comonlweb";

	public static final String DATO_NON_PERVENUTO = "DATO NON PERVENUTO";



	public static final String FLAG_S = "S";
	public static final String FLAG_N = "N";
	public static final String FLAG_SI_DESC = "SI";
	public static final String FLAG_N_DESC = "NO";

	public static final String NON_DISPONIBILE = "N.D.";

	public static final String AMMINISTRATORE_COMONL = "AMMINISTRATORE_COMONL";
	public static final String AMMINISTRATORE_COMONL_DESC = "Amministratore";
	public static final String CONSULENTE_RESPONSABILE_COMONL = "CONSULENTE_RESPONSABILE_COMONL";
	public static final String CONSULENTE_RESPONSABILE_COMONL_DESC = "Consulente responsabile";
	public static final String DELEGATO_RESPONSABILE_COMONL = "DELEGATO_RESPONSABILE_COMONL";
	public static final String DELEGATO_RESPONSABILE_COMONL_DESC = "Delegato Responsabile";
	public static final String PERS_AUTORIZZATA_COMONL = "PERS_AUTORIZZATA_COMONL";
	public static final String PERS_AUTORIZZATA_SCUOLA_COMONL = "PERS_AUTORIZZATA_SCUOLA_COMONL";
	public static final String PERS_AUTORIZZATA_COMONL_DESC = "Persona autorizzata";
	public static final String PERSONA_CARICA_AZIENDALE_COMONL = "PERSONA_CARICA_AZIENDALE_COMONL";
	public static final String PERSONA_CARICA_AZIENDALE_COMONL_DESC = "Persona Carica aziendale";
	public static final String LEGALE_RAPPRESENTANTE_COMONL = "LEGALE_RAPPRESENTANTE_COMONL";
	public static final String LEGALE_RAPPRESENTANTE_COMONL_DESC = "Legale Rappresentante";
	public static final String OPERATORE_APL_COMONL = "OPERATORE_APL_COMONL";
	public static final String OPERATORE_APL_COMONL_DESC = "Operatore Apl";
	public static final String PROVINCIA_OPERATORE = "OPERATORE_CPI_COMONL@PROVINCIA";

	public static final String MONITORAGGIO = "MONITORAGGIO";
	public static final String MONITORAGGIO_DESC = "Monitoraggio";

	public static final String PV_DEFAULT = "TO";

	public static final String TIPOLOGIA_ANAGRAFICA_CONSULENTE_C = "C";
	public static final String TIPOLOGIA_ANAGRAFICA_DELEGATO_D = "D";
	public static final String TIPOLOGIA_ANAGRAFICA_PERSONA_AUTORIZZATA_E = "E";

	public static final String PARAM_PROSPETTI_NUMERO_MASSIMO = "ricerca.prospetto.numeroMassimoRecord";

	public static final String SIGLA_ITALIANA = "IT";

	public static final Integer MAX_NUM_RES_RICERCA = 400; // TODO totalmente inventato temporaneamente

	public static final Long STATO_COMUNICAZIONE_INSERITA_ID = 1L;
	public static final Long STATO_COMUNICAZIONE_CONTROLLATA_ID = 2L;
	public static final Long STATO_COMUNICAZIONE_VALIDATA_ID = 3L;
	public static final Long STATO_COMUNICAZIONE_ANNULLATA_ID = 4L;
	public static final Long STATO_COMUNICAZIONE_CANCELLATA_ID = 5L;
	public static final Long STATO_COMUNICAZIONE_DA_FIRMARE_ID = 6L;
	public static final Long STATO_COMUNICAZIONE_TRANSITO_ID = 7L;
	public static final Long STATO_COMUNICAZIONE_RETTIFICATA_ID = 9L;

	public static final String STATO_COMUNICAZIONE_VALIDATA = "VALIDATA";

	public static final String CODICE_REGIONALE_PIEMONTE = "13";

	public static final Long TIPO_ACQUISIZIONE_COMUNICAZIONE_COMPLETA_ID = 1L;

	public static final int TIPO_COMUNICAZIONE_PROSPETTO_INFORMATIVO = 1;
	public static final int TIPO_COMUNICAZIONE_PROSPETTO_RETTIFICA = 2;
	public static final int TIPO_COMUNICAZIONE_PROSPETTO_ANNULLAMENTO = 3;

	public static final String TIPO_COMUNICAZIONE_PROSPETTO_INFORMATIVO_COD = "01";
	public static final String TIPO_COMUNICAZIONE_PROSPETTO_RETTIFICA_COD = "02";
	public static final String TIPO_COMUNICAZIONE_PROSPETTO_ANNULLAMENTO_COD = "03";

	public static final String TIPO_COMUNICAZIONE_PROSPETTO_ANNULLAMENTO_DESC = "Annullamento";

	public static final String TIPO_COMUNICAZIONE_TU_RETTIFICA_COD_MIN = "03";
	public static final String TIPO_COMUNICAZIONE_TU_ANNULLAMENTO_COD_MIN = "04";
	public static final String TIPO_COMUNICAZIONE_TU_COMUNICAZIONE_OBBLIGATORIA_MIN = "01";

	public static final Long TIPO_COMUNICAZIONE_TU_COMUNICAZIONE_OBBLIGATORIA_ID = 1L;
	public static final Long TIPO_COMUNICAZIONE_TU_ANNULLAMENTO_ID = 4L;

	public static final String TIPO_COMUNICAZIONE_VARIAZIONE_DATORE_ID = "VAR";
	public static final String TIPO_COMUNICAZIONE_URGENZA_ID = "URG";
	public static final String TIPO_COMUNICAZIONE_UNILAV_ID = "UL";
	public static final String TIPO_COMUNICAZIONE_UNISOMM_ID = "US";
	public static final String TIPO_COMUNICAZIONE_VARDATORI_ID = "VD";

	public static final String TIPO_DATORE_ATTUALE_ID = "A";
	public static final String TIPO_DATORE_PRECEDENTE_ID = "P";

	public static final Long TIPO_PROVENIENZA_COMONL_VER_4 = 3L;
	public static final Long TIPO_PROVENIENZA_COMMAX_VER_1 = 4L;
	public static final Long TIPO_PROVENIENZA_SPICOM = 5L;

	public static final String TIPO_RAPPORTO_AZIENDA_P = "P";
	public static final String TIPO_RAPPORTO_AZIENDA_M = "M";

	public static final String TIPO_TRACCIATO_UNISOMM_ID = "US";
	public static final String TIPO_TRACCIATO_UNILAV_ID = "UL";
	public static final String TIPO_TRACCIATO_URGENZA_ID = "UG";
	public static final String TIPO_TRACCIATO_VARDATORI_ID = "VD";
	public static final String TIPO_TRACCIATO_DOMESTICO_ID = "UD";

	public static final String TIPO_TRASFERIMENTO_AFFITTO_RAMO_COD_MIN = "02";

	public static final Long PROSPETTO_STATO_PRESENTATA_ID = 3L;

	public static final Long STATO_DELEGA_ANNULLATA_ID = 2L;

	public static final String PERSONALIZZAZIONE_TO_ID = "TO";

	public static final String COMONLS_PARAMETRI_APPLICATIVO_ABILITATO = "APPLICATIVO_ABILITATO";
	public static final String COMONLS_PARAMETRI_APPLICATIVO_NON_ABILITATO_MESSAGGIO = "APPLICATIVO_NON_ABILITATO_MESSAGGIO";
	public static final String COMONLS_PARAMETRI_RICERCA_COMUNICAZIONI_NMASSIMO = "ricerca.comunicazioni.numeroMassimoRecord";
	public static final String COMONLS_PARAMETRI_COMUNICAZIONE_TRACCIATO_2013 = "Comunicazione.dataIntroduzioneNuovoTracciatoMinisteriale.20130110.h1900";
	public static final String COMONLS_PARAMETRI_COMUNICAZIONE_TRACCIATO_2014 = "Comunicazione.dataIntroduzioneNuovoTracciatoMinisteriale.20140110.h1900";
	public static final String COMONLS_PARAMETRI_COMUNICAZIONE_TRACCIATO_2016 = "Comunicazione.dataIntroduzioneNuovoTracciatoMinisteriale.20160201.h1900";
	public static final String COMONLS_PARAMETRI_COMUNICAZIONE_TRACCIATO_2019 = "Comunicazione.dataIntroduzioneNuovoTracciatoMinisteriale.20191104.h1900";
	public static final String COMONLS_PARAMETRI_RETRIBUZIONE_0_AMMESSA = "Comunicazione.rapporto.retribuzioneUgualeZeroAmmessa.attivaControllo";
	public static final String COMONLS_PARAMETRI_RETRIBUZIONE_0_CONTRATTI = "Comunicazione.elencoContratti.retribuzioneUgualeZeroAmmessa";
	public static final String COMONLS_PARAMETRI_CCNL_ND = "Vincoli.ListaTipiContratto.CcnlAmmesso_ND";
	public static final String COMONLS_PARAMETRI_DATA_AVVIO_COMONL = "DATA_AVVIO_COMONL";
	public static final String COMONLS_PARAMETRI_DELEGA_LISTAPV = "Delega.ListaPV";



	public static final String NUMERO_VERSIONE_TRACCIATO_PER_SPICOM = "NUMERO_VERSIONE_TRACCIATO_PER_SPICOM";


	public static final String TIPO_DATORE_A = "A";
	public static final String TIPO_DATORE_P = "P";

	public static final String TIPO_LAVORATORE_P = "P";
	public static final String TIPO_LAVORATORE_C = "C";

	public static final String STATO_DELEGA_ANNULLATA_2 = "2";
	public static final String STATO_DELEGA_VALIDATA_3 = "3";
	public static final String STATO_DELEGA_REVOCATA_4 = "4";

	public static final String TIPO_PROVENIENZA_COMONL = "CM";
	public static final String TIPO_PROVENIENZA_COMMAX = "CX";
	public static final Long ID_TIPO_PROVENIENZA_COMMAX = 4L;

	// Elenco dei fruitori abilitati ( mappano i campi contenuti nella
	// COM_T_COMONLS_PARAMETRI )
	public static final String FRUITORE_FORMAZIONE_PROFESSIONALE = "COMPROF.FRUITORE.FORMAZIONE_PROFESSIONALE";
	public static final String FRUITORE_GECO = "COMPROF.FRUITORE.GECO";
	public static final String FRUITORE_PRODIS = "COMPROF.FRUITORE.PRODIS";
	public static final String FRUITORE_VOCC = "COMPROF.FRUITORE.VOCC";
	public static final String VERIFY_IF_SERVICE_IS_WORKING = "COMPROF.FLAG_ABILITAZIONE_SERVIZIO_PROFILAZIONE";

	// TIPO COMUNICAZIONE

	public static final String TIPO_COMUNICAZIONE_ASSUNZIONE = "ASS";
	public static final String TIPO_COMUNICAZIONE_PROROGA = "PRO";
	public static final String TIPO_COMUNICAZIONE_TRASFORMAZIONE = "TRS";
	public static final String TIPO_COMUNICAZIONE_TRASFERIMENTO_DISTACCO = "TRD";
	public static final String TIPO_COMUNICAZIONE_CESSAZIONE = "CES";
	public static final String TIPO_COMUNICAZIONE_VARIAZIONE_DATORE = "VAR";
	public static final String TIPO_COMUNICAZIONE_URGENZA = "URG";

	public static final Long TIPO_SOMMINISTRAZIONE = 0L;
	public static final Long TIPO_SOMMINISTRAZIONE_MISSIONE = 1L;
	public static final Long TIPO_SOMMINISTRAZIONE_SOMM_MISSIONE = 2L;

	// TIPO TRASFORMAZIONERL
	public static final String TIPO_TRASFORMAZIONERL_MIN_DL = "DL";
	public static final String TIPO_TRASFORMAZIONERL_MIN_TL = "TL";

	public static final Long TIPO_COMUNICAZIONE_TU_INSERIMENTO_UFFICIO = 6L;

	public static final Long ID_TIPO_CO_DISTACCO = 6L;
	public static final String COD_MIN_TIPO_CO_DISTACCO = "DL";
	public static final java.util.ArrayList ELENCO_CARATTERI_SPECIALI = new java.util.ArrayList();
	static
	{
		ELENCO_CARATTERI_SPECIALI.add("°");
	}

	// TIPO DOCUMENTO
	public static final String TIPO_DOCUMENTO_PERMESSO = "1";
	public static final String TIPO_DOCUMENTO_CARTA = "2";
	public static final String TIPO_DOCUMENTO_IN_RINNOVO = "3";
	public static final String TIPO_DOCUMENTO_ATTESA_PERMESSO = "4";
	public static final String TIPO_DOCUMENTO_CARTA_PERMANENTE = "5";
	public static final String TIPO_DOCUMENTO_ALTRO_PROVVEDIMENTO = "6";
	public static final String TIPO_DOCUMENTO_PERMESSO_DI_SOGGIORNO_CE_PER_SOGGIORNANTI_DI_LUNGO_PERIODO = "7";

	public static final String TIPO_CONTRATTI_FLGFORMA_D = "D";
	public static final String TIPO_CONTRATTI_FLGFORMA_I = "I";
	public static final String TIPO_CONTRATTI_FLGFORMA_E = "E";

	public static final String TIPO_CONTRATTI_COD_MIN_A0702 = "A.07.02";
	public static final String TIPO_CONTRATTI_COD_MIN_A0308 = "A.03.08";
	public static final String TIPO_CONTRATTI_COD_MIN_A0309 = "A.03.09";
	public static final String TIPO_CONTRATTI_COD_MIN_A0310 = "A.03.10";
	public static final String TIPO_CONTRATTI_COD_MIN_A0100 = "A.01.00";
	public static final String TIPO_CONTRATTI_COD_MIN_A0200 = "A.02.00";
	public static final String TIPO_CONTRATTI_COD_MIN_A0201 = "A.02.01";
	public static final String TIPO_CONTRATTI_COD_MIN_B0100 = "B.01.00";
	public static final String TIPO_CONTRATTI_COD_MIN_B0200 = "B.02.00";
	public static final String TIPO_CONTRATTI_COD_MIN_B0300 = "B.03.00";
	public static final String TIPO_CONTRATTI_COD_MIN_A0402 = "A.04.02";
	public static final String TIPO_CONTRATTI_COD_MIN_A0802 = "A.08.02";
	public static final String TIPO_CONTRATTI_COD_MIN_A0502 = "A.05.02";
	public static final String TIPO_CONTRATTI_COD_MIN_A0304 = "A.03.04";
	public static final String TIPO_CONTRATTI_COD_MIN_C0300 = "C.03.00";
	public static final String TIPO_CONTRATTI_COD_MIN_H0200 = "H.02.00";
	public static final String TIPO_CONTRATTI_COD_MIN_M0200 = "M.02.00";
	public static final String TIPO_CONTRATTI_COD_MIN_N0100 = "N.01.00";


	public static final String TIPO_CONTRATTI_COD_MIN_A0311 = "A.03.11";
	public static final String TIPO_CONTRATTI_COD_MIN_A0312 = "A.03.12";
	public static final String TIPO_CONTRATTI_COD_MIN_A0313 = "A.03.13";
	public static final String TIPO_CONTRATTI_COD_MIN_A0314 = "A.03.14";
	public static final String TIPO_CONTRATTI_COD_MIN_A0300 = "A.03.00";
	public static final String TIPO_CONTRATTI_COD_MIN_A0301 = "A.03.01";
	public static final String TIPO_CONTRATTI_COD_MIN_A0302 = "A.03.02";
	public static final String TIPO_CONTRATTI_COD_MIN_A0303 = "A.03.03";
	public static final String TIPO_CONTRATTI_COD_MIN_A0307 = "A.03.07";//CONTRATTO DI INSERIMENTO LAVORATIVO

	public static final String TIPO_CONTRATTI_TIPO_AP = "AP";

	public static final String TIPO_CONTRATTI_TIPO_TR = "TR";
	public static final String TIPO_CONTRATTI_TIPO_TIE = "TIE";





	public static final String TRASFORMAZIONERL_DI = "DI";
	public static final String TRASFORMAZIONERL_AI = "AI";
	public static final String TRASFORMAZIONERL_FI = "FI";
	public static final String TRASFORMAZIONERL_II = "II";
	public static final String TRASFORMAZIONERL_FF = "FF";
	public static final String TRASFORMAZIONERL_PP = "PP";
	public static final String TRASFORMAZIONERL_TP = "TP";
	public static final String TRASFORMAZIONERL_PV = "PV";
	public static final String TRASFORMAZIONERL_DP = "DP";

	public static final String CATEG_TIROCINANTE_A = "A";
	public static final String CATEG_TIROCINANTE_B = "B";
	public static final String CATEG_TIROCINANTE_C = "C";

	public static final String CATEG_TIROCINANTE_01 = "01";
	public static final String CATEG_TIROCINANTE_02 = "02";
	public static final String CATEG_TIROCINANTE_03 = "03";
	public static final String CATEG_TIROCINANTE_04 = "04";
	public static final String CATEG_TIROCINANTE_05 = "05";
	public static final String CATEG_TIROCINANTE_06 = "06";
	public static final String CATEG_TIROCINANTE_07 = "07";
	public static final String CATEG_TIROCINANTE_08 = "08";
	public static final String CATEG_TIROCINANTE_99 = "99";

	public static final String COD_TIPO_ENTE_PROMOTORE_TIROCINIO_01_MIN = "01";
	public static final Long   ID_TIPO_ENTE_PROMOTORE_TIROCINIO_1_COMONL = 51L;

	public static final String CCNL_SETTORE_ENTIPUBBLICI = "Enti pubblici";
	public static final String CCNL_ND = "ND";

	public static final String TIPO_ORARIO_COD_MIN_N = "N";
	public static final String TIPO_ORARIO_COD_MIN_F = "F";

	public static final String ENTE_PREVIDENZIALE_COD_MIN_29 = "29";

	public static final String CATEG_LAV_ASS_OBBL_COD_MIN_CP = "CP";
	public static final String CATEG_LAV_ASS_OBBL_COD_MIN_PD = "PD";



	public static final String VARIAZIONE_NULLA = "-";
	public static final Long ID_TIPO_ENTE_TIROCINIO_CPI = 1L;
	public static final String CPI_DI = "CPI di ";
  // mittente Comonl
  public static final String SPICOM_MITTENTE = "GE";
  public static final Long TIPO_PROVENIENZA_COMMAS_VER_2 = 2L;
  public static final int DES_DATORE_LAVORO = 100;
  public static final String CAP_DEFAULT = "00000";

  public static final String URGENZA_CODICE_REG_RETT_ANN_URGFAX = "1300000000000000";
  public static final String URGENZA_CODICE_REG_RETT_ANN_URGFAX_ZERI = "0000000000000000";

  // valore sulla tabella COM_T_TIPO_COMUNICAZIONE_TU per il tipo rettifica/annullamento/urgenza
  public static final Long TIPO_COMUNICAZIONE_OBBLIGATORIA_1_LONG = 1L;
  public static final Long TIPO_COMUNICAZIONE_RETTIFICA_3_LONG = 3L;
  public static final Long TIPO_COMUNICAZIONE_ANNULLAMENTO_4_LONG = 4L;
  public static final Long TIPO_COMUNICAZIONE_TRASFORM_5_LONG = 5L;
  public static final Long TIPO_COMUNICAZIONE_D_UFFICIO_6_LONG = 6L;
  public static final Long TIPO_COMUNICAZIONE_URGENZA_TELEM_7_LONG = 7L;
  public static final Long TIPO_COMUNICAZIONE_URGENZA_FAX_8_LONG = 8L;
  public static final Long TIPO_COMUNICAZIONE_URGENZA_TELEM_9_LONG = 9L;

  public static final String TIPO_COMUNICAZIONE_OBBLIGATORIA_1_STRING = "1";
  public static final String TIPO_COMUNICAZIONE_URGENZA_2_STRING = "2";
  public static final String TIPO_COMUNICAZIONE_RETTIFICA_3_STRING = "3";
  public static final String TIPO_COMUNICAZIONE_ANNULLAMENTO_4_STRING = "4";
  public static final String TIPO_COMUNICAZIONE_TRASFORM_5_STRING = "5";

  public static final String VARIAZIONE_DATORE_RAGIONE_SOCIALE = "VARIAZIONE DATORE - RAGIONE SOCIALE";
  public static final String VARIAZIONE_DATORE_DATORE_LAVORO = "VARIAZIONE DATORE - DATORE LAVORO";

  public static final Long SERVIZI_COMPETENTI = 12L;
  public static final String CODICE_SERVIZI_COMPETENTI = "012";
  public static final String UTENTE_IMPORT_COMUNICAZIONE_DA_SPICOM = "IMPORT_MINISTERO";

  ///////////////////////// TAbelle di decodifica
  public static final String  COM_T_ENTE_PREVIDENZIALE = "COM_T_ENTE_PREVIDENZIALE";
  public static final String  COM_T_CCNL = "COM_T_CCNL";
  public static final String  COM_T_STATI_ESTERI = "COM_T_STATI_ESTERI";
  public static final String  COM_T_ISTAT2001LIVELLO5 = "COM_T_ISTAT2001LIVELLO5";
  public static final String  COM_T_TIPO_CONTRATTI = "COM_T_TIPO_CONTRATTI";
  public static final String  COM_T_TIPO_ORARIO = "COM_T_TIPO_ORARIO";
  public static final String  COM_T_ATECOFIN = "COM_T_ATECOFIN";
  public static final String  COM_T_CATEG_LAV_ASS_OBBL = "COM_T_CATEG_LAV_ASS_OBBL";
  public static final String  COM_T_CATEG_TIROCINANTE = "COM_T_CATEG_TIROCINANTE";
  public static final String  COM_T_CESSAZIONERL = "COM_T_CESSAZIONERL";
  public static final String  COM_T_CITTADINANZA = "COM_T_CITTADINANZA";
  public static final String  COM_T_COMUNE = "COM_T_COMUNE";
  public static final String  COM_T_CPI = "COM_T_CPI";
  public static final String  COM_T_GRADO_CONTRATTUALE = "COM_T_GRADO_CONTRATTUALE";
  public static final String  COM_T_LIVELLO_RETRIBUZIONE = "COM_T_LIVELLO_RETRIBUZIONE";
  public static final String  COM_T_LIVELLO_STUDIO = "COM_T_LIVELLO_STUDIO";
  public static final String  COM_T_MOTIVO_PERMESSO = "COM_T_MOTIVO_PERMESSO";
  public static final String  COM_T_PROVINCIA = "COM_T_PROVINCIA";
  public static final String  COM_T_QUESTURA = "COM_T_QUESTURA";
  public static final String  COM_T_REGIONE = "COM_T_REGIONE";
  public static final String  COM_T_STATUS_STRANIERO = "COM_T_STATUS_STRANIERO";
  public static final String  COM_T_TIPO_COMUNICAZIONE_TU = "COM_T_TIPO_COMUNICAZIONE_TU";
  public static final String  COM_T_TIPO_ENTE_PROM_TIROCINIO = "COM_T_TIPO_ENTE_PROM_TIROCINIO";
  public static final String  COM_T_TIPOLOGIA_TIROCINIO = "COM_T_TIPOLOGIA_TIROCINIO";
  public static final String  COM_T_TIPO_SOGGETTO_ABILITATO = "COM_T_TIPO_SOGGETTO_ABILITATO";
  public static final String  COM_T_TIPO_TRASFERIMENTO = "COM_T_TIPO_TRASFERIMENTO";
  public static final String  COM_T_TRASFORMAZIONERL = "COM_T_TRASFORMAZIONERL";
  public static final String  COM_T_VARIAZIONE_SOMM = "COM_T_VARIAZIONE_SOMM";

  public static final String AMBIENTE_DEV = "DEV";

  public final static String PROROGA_DEL_RAPPORTO_DI_LAVORO_IN_ASSENZA_DI_MISSIONE = "2.01";
  public final static String PROROGA_DEL_RAPPORTO_DI_LAVORO_E_DELLA_MISSIONE = "2.02";
  public final static String PROROGA_DELLA_MISSIONE_IN_CASO_DI_RAPPORTO_A_TEMPO_INDETERMINATO = "2.03";

  public static final String TIPO_COMUNICAZIONE_A_SEGUITO_URGENZA = "02";
  public static final String TIPO_COMUNICAZIONE_A_SEGUITO_URGENZA_TELEMATICA = "07";
  public static final String TIPO_COMUNICAZIONE_A_SEGUITO_URGENZA_FAX = "08";
  public static final String TIPO_COMUNICAZIONE_A_SEGUITO_URGENZA_TURISTICO = "09";

  public static final String LOGIN_SERVIZIO = "#SERVLAV";

}
