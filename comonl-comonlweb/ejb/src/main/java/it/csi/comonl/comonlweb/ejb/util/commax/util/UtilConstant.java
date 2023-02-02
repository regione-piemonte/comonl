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
package it.csi.comonl.comonlweb.ejb.util.commax.util;



public class UtilConstant {

  public final static String FLAG_S = "S";
  public final static String FLAG_N = "N";

  public final static String FLAG_SI = "SI";
  public final static String FLAG_NO = "NO";
  
  public final static String CODICE_AFFITTO_RAMO = "02";

  public final static String TRASFERIMENTO = "TRASFERIMENTO";
  public final static String DISTACCO = "DISTACCO";
  
  public final static String CODICE_TRASFORMAZIONE_DI = "DI";
  public final static String CODICE_TRASFORMAZIONE_AI = "AI";
  public final static String CODICE_TRASFORMAZIONE_FI = "FI";
  public final static String CODICE_TRASFORMAZIONE_II = "II";
  public final static String CODICE_TRASFORMAZIONE_PP = "PP";
  public final static String CODICE_TRASFORMAZIONE_DL = "DL";
  public final static String CODICE_TRASFORMAZIONE_TL = "TL";
  public final static String CODICE_TRASFORMAZIONE_TP = "TP";
  public final static String CODICE_TRASFORMAZIONE_PV = "PV"; 
  public final static String CODICE_TRASFORMAZIONE_FF = "FF";
  public final static String CODICE_TRASFORMAZIONE_DP = "DP";

  public final static String RUOLO_OPERATORE = "1";
  public final static String RUOLO_VALUTATORE = "2";
  public final static String DESC_RUOLO_OPERATORE = "operatore";
  public final static String DESC_RUOLO_VALUTATORE = "valutatore";
  public final static String TYPE_DATE_FORMAT = "dd/MM/yyyy";
  public final static String IMAGE_ERRORE = "/css_rupar/im/errore.gif";
  public final static int ELEMENTI_PER_PAGINA = 10;

  public final static String MODALITA_ARRIVO_TELEFONO = "1";
  public final static String TIPOLOGIA_PRIVATO = "1";
  public final static String ETA_21_40 = "2";
  public final static String TITOLO_STUDIO_SUPERIORE = "1";

  public final static String UFFICI_INTERNI = "1";

  public final static String ISTAT_PIEMONTE = "01";

// Per protocollo:
  public static final String SUITE_XML_BACK_OFFICE = "/defpdSDBO.xml";
  public static final String SUITE_XML_BACK_OFFICE_TORINO = "/defpdSDBO_Torino.xml";
  public static final String SUITE_XML_PROTOCOLLO = "/defpdPRTWSoap.xml";
  public static final String SUITE_XML_PROTOCOLLO_TORINO = "/defpdPRTWSoap_Torino.xml";
  public static final String SUITE_XML_UTENTI = "/defpdANAW.xml";
  public static final String SUITE_XML_UTENTI_TORINO = "/defpdANAW_Torino.xml";


  public static final String RUPAR_XML_ANAGR = "/defpd_gestanag.xml";
  public static final String RUPAR_XML_PROT = "/defpd_gestprot.xml";
  public static final String RUPAR_XML_STRUTTURE = "/defpd_geststrutture.xml";
  public static final String RUPAR_XML_UTENTI = "/defpd_gestutenti.xml";


  // Nuove PD per SPICOM

  // ***********************************************************************************************
  // Servizio di Invio Comunicazioni
  public static final String SPICOM_TRANSLATE_XML_INTERFACE = "/spicom_traduzione_tracciati_PD.xml";

  // Servizio di Traduzione file XML
  public static final String SPICOM_SEND_INTERFACE = "/spicom_accettazione_comunicazioni_PD.xml";

  // ***********************************************************************************************

  public static final String RUPAR_UTENTE_NON_TROVATO = "ErroreUtenteRUPAR";
  public static final String RUPAR_PROFILO_NON_TROVATO = "ErroreProfiloRUPAR";
  public static final String RUPAR_RUOLO_NON_TROVATO = "ErroreRuoloRUPAR";
  public static final String RUPAR_AOO_NON_TROVATA = "ErroreAooRUPAR";
  public static final String RUPAR_SOGGETTO_NON_TROVATO = "ErroreSoggettoRUPAR";
  public static final String RUPAR_PROT_NON_INSERITO = "ErroreProtRUPAR";
  public static final String RUPAR_SERVER_NON_RAGGIUNGIBILE = "ErroreServerProtRUPAR";

  public static final String UPLOAD_OK = "L'acquisizione del file e' avvenuta con successo. La verifica dei dati trasmessi e' attualmente in corso. Al termine dell'operazione, Vi verra' recapitata via e-mail la ricevuta contenente l'esito dell'elaborazione.";
  public static final String VERIFICA_OK1 = "VERIFICA IN CORSO.......Al termine dell'operazione, Vi verra' recapitata via e-mail la ricevuta contenente l'esito della verifica.";
  public static final String VERIFICA_OK2 =	"Attenzione: la funzione richiamata ha lo scopo di verificare il file e non prevede l'inoltro dello stesso al Ministero";
  public static final String UPLOAD_KO = "Si e' verificato un errore: file non e'conforme al formato ministeriale";
  public static final String INVALID_FILE_SIZE = "Le righe del file inviato non hanno una dimensione accettata";
  public static final String EXPIRED_CRL = "Non e'stato possibile verificare la firma. Si prega di riprovare piu' tardi.";
  public static final String MISSING_EMAIL = "Il campo EMAIL e' obbligatorio";
  public static final String INVALID_EMAIL = "Il campo EMAIL non rappresenta un indirizzo di email valido";
  public static final String INVALID_FILE_EXT = "Il file deve essere firmato e deve avere estensione .xml.p7m oppure .zip.p7m ";
  public static final String INVALID_FILE_CONTENT_TEXT = "Il formato/contenuto del file non e' corretto";
  public static final String INVALID_FILE_CONTENT_ERROR = "ErroreContenutoFile";


  public static final String TO="TO";
  public static final String NO="NO";
  public static final String AT="AT";
  public static final String AL="AL";
  public static final String VC="VC";
  public static final String VB="VB";
  public static final String CN="CN";
  public static final String BI="BI";


  public static final String CONNECTION_STRING_TEST_CASE = "ConnectionString";
  public static final String USER_CONNECTION_STRING_TEST_CASE = "User";
  public static final String PWD_CONNECTION_STRING_TEST_CASE = "Pwd";
    //Adeguamenti 2019
    public static final String COD_TIPO_ORARIO_TEMPO_PIENO = "F";
    public static final String COD_TIPO_ORARIO_NON_DEFINITO = "N";
    
    
    public static final String NESSUN_ENTE_PREVIDENZIALE = "29";
    public static final String TIPO_ORARIO_TEMPO_PIENO = "1";
    
    // MODIFICA - ADEGUAMENTO MINISTERIALE :
    // Aggiunta voce tipo orario non definito = N
    public static final String TIPO_ORARIO_NON_DEFINITO = "7";

    //contratti per cui e'previsto il flgStagionale = si
    // A.02.00,  TEMPO DETERMINATO 
    // A.02.01,  TEMPO DETERMINATO  PER SOSTITUZIONE 
    // -- ok -- B.01.00, -- dismesso
    // -- ok -- B.02.00, -- dismesso 
    // -- ok -- B.03.00, 
    // -- ok -- C.03.00, 
    // -- ok -- A.03.08, 
    // -- ok -- A.03.09, 
    // -- ok -- A.03.10, 
    // A.04.02, ---non esiste per GECO solo per ministro. 
    // A.08.02,  LAVORO A DOMICILIO
    // -- ok -- A.07.02, 
    // A.05.02 - LAVORO_INTERMITTENTE
    //72160-1504: aggiunta contratti per controllo su flag stagionale = si
    public static final String TIPO_CONTRATTO_TEMPO_DETERMINATO = "A.02.00";
    public static final String TIPO_CONTRATTO_TEMPO_DETERMINATO_PER_SOSTITUZIONE = "A.02.01";
    public static final String TIPO_CONTRATTO_LAVORO_A_DOMICILIO = "A.08.02";
    public static final String TIPO_CONTRATTO_LAVORO_INTERMITTENTE = "A.05.02";

    
    public static final String TEMPO_INDETERMINATO = "A.01.00";
    public static final String TIPO_CONTRATTO_APPRENDISTATO_ART_16 = "A.03.00";
    public static final String TIPO_CONTRATTO_APPRENDISTATO_DIRITTO_ISTRUZIONE = "A.03.01";
    public static final String TIPO_CONTRATTO_APPRENDISTATO_PROFESSIONALIZZANTE = "A.03.02";
    public static final String TIPO_CONTRATTO_APPRENDISTATO_PER_ALTA_FORMAZIONE  = "A.03.03";
    public static final String TIPO_CONTRATTO_FORMAZIONE_LAVORO_PUBBLICA_AMMINISTRAZIONE  = "A.03.04";
    
    
    public static final String TIPO_CONTRATTO_APPRENDISTATO_PER_LA_QUAL_PROFESSIONALE_E_DIPLOMA_PROF = "A.03.08";// contratto x cui e'previsto flgStagionale = si
	public static final String TIPO_CONTRATTO_APPRENDISTATO_PROF_O_CONTRATTO_DI_MESTIERE = "A.03.09";//contratto x cui e' previsto flgStagionale = si
	public static final String TIPO_CONTRATTO_APPRENDISTATO_DI_ALTA_FORMAZIONE_E_RICERCA = "A.03.10";//contratto x cui e' previsto flgStagionale = si	
    
    
    public static final String LAVORO_INTERINALE_O_A_SCOPO_DI_SOMMINISTRAZIONE_A_TEMPO_INDETERMINATO  = "A.06.00";
    public static final String TIPO_CONTRATTO_RIPARTITO = "A.07.02";//contratto x cui e' previsto flgStagionale = si
    public static final String TIPO_CONTRATTO_LAVORO_A_DOMICILIO_TEMPO_INDETERMINATO = "A.08.00";
    public static final String TIPO_CONTRATTO_LAVORO_A_DOMICILIO_TEMPO_DETERMINATO = "A.08.01";
//    public static final String TIPO_CONTRATTO_CO_CO_CO = "B.01.00"; //contratto x cui e' previsto flgStagionale = si -- dismesso 01.02.2016
//    public static final String TIPO_CONTRATTO_LAVORO_OCCASIONALE = "B.02.00"; //contratto x cui e' previsto flgStagionale = si -- dismesso 01.02.2016
    public static final String TIPO_CONTRATTO_COLLABORAZIONE_COORDINATA_CONTINUATIVA = "B.03.00"; //contratto x cui e' previsto flgStagionale = si -- attivo dal 01.02.2016
    public static final String TIPO_CONTRATTO_TIROCINIO = "C.01.00";
    //public static final String TIPO_CONTRATTO_TIROCINIO_ESTIVO = "C.02.00"; dismesso al 10.01.2014
    public static final String LAVORO_O_ATTIVITA_SOCIALMENTE_UTILE = "C.03.00";//contratto x cui e' previsto flgStagionale = si
    public static final String TIPO_CONTRATTO_AGRICOLTURA_TD = "H.01.00";
    public static final String LAVORO_DIPENDENTE_NELLA_P_A_TEMPO_INDETERMINATO = "I.01.00";
    public static final String LAVORO_DIPENDENTE_NELLA_P_A_TEMPO_DETERMINATO = "I.02.00";
    public static final String TIPO_CONTRATTO_ASSOCIAZIONE_IN_PARTECIPAZIONE_TEMPO_INDETERMINATO = "L.01.00";
    public static final String TIPO_CONTRATTO_ASSOCIAZIONE_IN_PARTECIPAZIONE_TEMPO_DETERMINATO = "L.01.01";
    public static final String TIPO_CONTRATTO_CONTRATTO_DI_AGENZIA_TEMPO_INDETERMINATO = "M.01.00";
    public static final String TIPO_CONTRATTO_CONTRATTO_DI_AGENZIA_TEMPO_DETERMINATO = "M.01.01";    
    public static final String LAVORO_INTERMITTENTE_A_TEMPO_DETERMINATO = "A.05.01";
    public static final String LAVORO_INTERINALE_O_A_SCOPO_DI_SOMMINISTRAZIONE_A_TEMPO_DETERMINATO = "A.06.01";
    public static final String CONTRATTO_DI_INSERIMENTO_LAVORATIVO = "A.03.07";

    public static final Long ENTE_PREVIDENZIALE_INPS = 1L;

    //Cause cessazione
    public static final String CAUSA_CESSAZIONE_DECADENZA_DAL_SERVIZIO_NELLA_P_A = "DS";
    public static final String CAUSA_CESSAZIONE_ENTI_PUBBLICI = "EP";
    public static final String CAUSA_CESSAZIONE_LICENZIAMENTO_INDIVIDUALE = "LI";
    public static final String CAUSA_CESSAZIONE_RECESSO_CON_PREAVVISO_AL_TERMINE_DELLA_FORMAZIONE = "AR";	
    public static final String CAUSA_CESSAZIONE_LICENZIAMENTO_PER_GIUSTA_CAUSA_DURANTE_FORMAZAZIONE = "AC";	
    public static final String CAUSA_CESSAZIONE_LICENZIAMENTO_GIUSTIFICATO_MOTIVO_DURANTE_FORMAZIONE = "AM";	
    public static final String CAUSA_CESSAZIONE_DIMISSIONI_GIUSTA_CAUSA_GIUSTIFICATO_MOTIVO_DURANTE_FORMAZIONE = "AD";	
    


	public final static String STATO_ELABORAZIONE_DA_ELABORARE="E";
	public final static String STATO_ELABORAZIONE_REGISTRATE="R";
	public final static String STATO_ELABORAZIONE_INVIATE="I";
	public final static String STATO_ELABORAZIONE_NOTIFICATI="N";
    
	
  //Aggiunta costante per porta delegata del SERVIZIO IUP

    public static final String IUP_PD_XML = "/defpd_iupsv.xml";
    
    
    public static final String PROTOCOLLO_I = "I";
    public static final String PROTOCOLLO_R = "R";
        
    public static final String PAT_INAIL = "99991000";
    
    public static final String UPLOAD = "upload";
    public static final String VERIFICA = "verifica";
    
    public static final String CODICE_REGIONALE_NULLO = "0000000000000000";
    
    public static final String CODICE_REGIONALE_DEFAULT = "1300000000000000";
    
    public static final int NUM_GIORNI_MAX = 5;
    
    public static final int INCREMENTO_MESE = 1;
    
    public static final int GIORNO_LIMITE = 20;
        
    public static final String TIPO_DOCUMENTO_IN_RINNOVO = "3";
    
    public static final String TIPO_DOCUMENTO_IN_ATTESA_DI_PERMESSO = "4";
    
    public static final String TIPO_DOCUMENTO_CARTA_PERMANENTE = "5";
    
    public static final String TIPO_DOCUMENTO_ALTRO_PROVVEDIMENTO = "6";
    
    
    public final static String LAVORATORE_PRINCIPALE = "P";
    
    public final static String LAVORATORE_COOBBLIGATO = "C";
    
    
    public final static String TIPO_RAPPORTO_AZIENDA_PRINCIPALE = "P";
    
    public final static String TIPO_RAPPORTO_AZIENDA_MISSIONE = "M";
    
    
    public final static String STATO_CODICE_SCADUTO = "SCADUTO";
    public final static String STATO_CODICE_VALIDO = "VALIDO";
    
    //Variazioni Unisomm
    public final static String PROROGA_DEL_RAPPORTO_DI_LAVORO_IN_ASSENZA_DI_MISSIONE = "2.01";
    
    public final static String PROROGA_DEL_RAPPORTO_DI_LAVORO_E_DELLA_MISSIONE = "2.02";
    
    public final static String PROROGA_DELLA_MISSIONE_IN_CASO_DI_RAPPORTO_A_TEMPO_INDETERMINATO = "2.03";
    
    public final static String TRASFORMAZIONE_DEL_RAPPORTO_DI_LAVORO_IN_COSTANZA_DI_MISSIONE = "3.01";
    
    public final static String TRASFORMAZIONE_DEL_RAPPORTO_DI_LAVORO_IN_ASSENZA_DI_MISSIONE = "3.02";
    
    public final static String TRASFERIMENTO_ALTRA_SEDE_LAVORO_DITTA_UTILIZZATRICE = "3.03";
    
    public final static String TRASFERIMENTO_ALTRA_SEDE_OPERATIVA_AGENZIA = "3.04";
    
    public final static String CESSAZIONE_DELLA_MISSIONE = "4.01";
    
    public final static String CESSAZIONE_DEL_RAPPORTO_DI_LAVORO = "4.02";
    
    
    
    public static final String CATEGORIA_TIROCINANTE_SOGGETTO_SVANTAGGIATO 					= "01";
    public static final String CATEGORIA_TIROCINANTE_DISABILE 								= "02";
    public static final String CATEGORIA_TIROCINANTE_DISOCCUPATO_INOCCUPATO 				= "03";
    public static final String CATEGORIA_TIROCINANTE_NEOQUALIFICATO 						= "04";
    public static final String CATEGORIA_TIROCINANTE_NEODIPLOMATO 							= "05";
    public static final String CATEGORIA_TIROCINANTE_NEOLAUREATO 							= "06";
    public static final String CATEGORIA_TIROCINANTE_NEODOTTORATO 							= "07";
    public static final String CATEGORIA_TIROCINANTE_LAVORATORE_MOBILITA_CASSA_INTEGRAZIONE = "08";
    public static final String STUDENTE                                                     = "99";
    
    public static final int MESI_3  = 3;
    public static final int MESI_6  = 6;
    public static final int MESI_12 = 12;
    public static final int MESI_24 = 24;

    //72160-1544 (17.04.2015)
    public static final Long ID_ENTE_PROMOTORE_TIROCINIO_SERVISI_IMPIEGO_51 = 51L;
    public static final Long ID_ENTE_PROMOTORE_TIROCINIO_CPI_1 = 1L;
    
    public static final String COMUNICAZIONE_PRECEDENTE_AMMESSA_RETTIFICA = "03";
    public static final String COMUNICAZIONE_PRECEDENTE_AMMESSA_ANNULLO = "04";
    public static final String COMUNICAZIONE_PRECEDENTE_AMMESSA_URG1 = "07";
    public static final String COMUNICAZIONE_PRECEDENTE_AMMESSA_URG2 = "08";
    public static final String COMUNICAZIONE_PRECEDENTE_AMMESSA_URG3 = "09";
    

    public static final String SETTORE_ENTE_PUBBLICO = "Enti pubblici";
    
    public static final Long ID_REGIONE_PIEMONTE = 14L;
    
	public static final String REGOLA_TIPOLOGIA_TIROCINIO_CATEGORIA_TIROCINANTE = "REGOLA_TIPOLOGIA_TIROCINIO_CATEGORIA_TIROCINANTE";//COM_R_TIPOL_TIROC_CAT_TIROC
	

	public static final String SUBJECT_MAIL_VERIFICA = "TEST DI VERIFICA: esito verifica del file "; 
	public static final String SUBJECT_MAIL_ELABORAZIONE = "Comunicazioni on line (massive): esito elaborazione del file "; 

}
