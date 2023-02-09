/* -------------------------------------------------------------------- */
/* Copyright Regione Piemonte - 2022									*/
/* SPDX-License-Identifier: EUPL-1.2-or-later                           */
/* Target DBMS:           Oracle 19c                                    */
/* Project name:          COMONL                                        */
/* Author:                CSI Piemonte                                  */
/* Script type:           Database creation script                      */
/* Created on:            2022-11-23		                            */
/* -------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Add tables                                                             */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TIPO_TRASFERIMENTO"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TIPO_TRASFERIMENTO (
    ID_COM_T_TIPO_TRASFERIMENTO NUMBER(5) NOT NULL,
    DS_COM_T_TIPO_TRASFERIMENTO VARCHAR2(50),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    COD_TIPOTRASFERIMENTO_MIN VARCHAR2(2),
    PRIMARY KEY (ID_COM_T_TIPO_TRASFERIMENTO)
);

CREATE UNIQUE INDEX PK_COM_T_TIPO_TRASFERIMENTO ON COM_T_TIPO_TRASFERIMENTO (ID_COM_T_TIPO_TRASFERIMENTO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_ENTE_PREVIDENZIALE"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_ENTE_PREVIDENZIALE (
    ID_COM_T_ENTE_PREVIDENZIALE NUMBER(5) NOT NULL,
    DS_COM_T_ENTE_PREVIDENZIALE VARCHAR2(50),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    COD_ENTE_PREVIDENZIALE_MIN VARCHAR2(2),
    PRIMARY KEY (ID_COM_T_ENTE_PREVIDENZIALE)
);

CREATE UNIQUE INDEX PK_COM_T_ENTE_PREVIDENZIALE ON COM_T_ENTE_PREVIDENZIALE (ID_COM_T_ENTE_PREVIDENZIALE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_CESSAZIONERL"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_CESSAZIONERL (
    ID_COM_T_CESSAZIONERL NUMBER(5) NOT NULL,
    DS_COM_T_CESSAZIONERL VARCHAR2(50),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    COD_CESSAZIONE_MIN VARCHAR2(2),
    ID_NEW_CESSAZIONERL NUMBER(5),
    PRIMARY KEY (ID_COM_T_CESSAZIONERL)
);

CREATE UNIQUE INDEX PK_COM_T_CESSAZIONERL ON COM_T_CESSAZIONERL (ID_COM_T_CESSAZIONERL ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_STATI_ESTERI"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_STATI_ESTERI (
    ID_COM_T_STATI_ESTERI NUMBER(5) NOT NULL,
    DS_COM_T_STATI_ESTERI VARCHAR2(50),
    COD_NAZIONE_MIN VARCHAR2(4),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    SIGLA_NAZIONE VARCHAR2(3),
    PRIMARY KEY (ID_COM_T_STATI_ESTERI)
);

CREATE UNIQUE INDEX PK_COM_T_STATI_ESTERI ON COM_T_STATI_ESTERI (ID_COM_T_STATI_ESTERI ASC);

COMMENT ON COLUMN COM_T_STATI_ESTERI.SIGLA_NAZIONE IS 'Compilato con stringa di 2 caratteri, serve solo per gli stati con com_t_cittadinanza.flg_ue = ''S'' per identificare le "sigle internazionali" che servono per costruire il cf estero, per i soli paesi UE';

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_LISTA_WARNING"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_LISTA_WARNING (
    ID_COM_T_LISTA_WARNING NUMBER(5) NOT NULL,
    DS_WARNING VARCHAR2(100),
    PRIMARY KEY (ID_COM_T_LISTA_WARNING)
);

CREATE UNIQUE INDEX PK_COM_T_LISTA_WARNING ON COM_T_LISTA_WARNING (ID_COM_T_LISTA_WARNING ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_ERR_INVIO_SPICOM"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_ERR_INVIO_SPICOM (
    DS_ERR_SPICOM CLOB,
    DATA_INVIO DATE DEFAULT sysdate,
    CF_UTENTE VARCHAR2(16),
    OBJ_XML_COMUNICAZIONE CLOB,
    ID_COM_D_ERR_INVIO_SPICOM NUMBER NOT NULL,
    ID_COM_D_COMUNICAZIONE NUMBER NOT NULL,
    PRIMARY KEY (ID_COM_D_ERR_INVIO_SPICOM)
);

CREATE INDEX IDX_COM_D_COMUNICAZIONE ON COM_D_ERR_INVIO_SPICOM (ID_COM_D_COMUNICAZIONE ASC);

CREATE UNIQUE INDEX PK_COM_D_ERR_INVIO_SPICOM ON COM_D_ERR_INVIO_SPICOM (ID_COM_D_ERR_INVIO_SPICOM ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_MOTIVO_PERMESSO"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_MOTIVO_PERMESSO (
    ID_COM_T_MOTIVO_PERMESSO NUMBER(5) NOT NULL,
    DS_COM_T_MOTIVO_PERMESSO VARCHAR2(50),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    COD_MOTIVO_MIN VARCHAR2(5),
    ID_NEW_MOTIVO_PERMESSO NUMBER(5),
    PRIMARY KEY (ID_COM_T_MOTIVO_PERMESSO)
);

CREATE INDEX IE_COM_T_MOTIVO_PERMESSO_01 ON COM_T_MOTIVO_PERMESSO (COD_MOTIVO_MIN ASC);

CREATE UNIQUE INDEX PK_COM_T_MOTIVO_PERMESSO ON COM_T_MOTIVO_PERMESSO (ID_COM_T_MOTIVO_PERMESSO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TIPO_DESTINATARIO"                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TIPO_DESTINATARIO (
    ID_COM_T_TIPO_DESTINATARIO VARCHAR2(2) NOT NULL,
    DS_COM_T_TIPO_DESTINATARIO VARCHAR2(30),
    DT_INIZIO DATE,
    DT_FINE DATE,
    PRIMARY KEY (ID_COM_T_TIPO_DESTINATARIO)
);

CREATE UNIQUE INDEX PK_COM_T_TIPO_DESTINATARIO ON COM_T_TIPO_DESTINATARIO (ID_COM_T_TIPO_DESTINATARIO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_REGIONE"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_REGIONE (
    COD_REGIONE_MIN VARCHAR2(2) NOT NULL,
    ID_COM_T_REGIONE NUMBER(5) NOT NULL,
    DS_COM_T_REGIONE VARCHAR2(50),
    COD_AMBITO_DIFFUSIONE VARCHAR2(1),
    DS_AMBITO_DIFFUSIONE VARCHAR2(50),
    COD_MOBILITAGEOG VARCHAR2(2),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    PRIMARY KEY (ID_COM_T_REGIONE)
);

CREATE UNIQUE INDEX PK_COM_T_REGIONE ON COM_T_REGIONE (ID_COM_T_REGIONE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TIPO_DATORE"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TIPO_DATORE (
    ID_COM_T_TIPO_DATORE VARCHAR2(1) NOT NULL,
    DS_COM_T_TIPO_DATORE VARCHAR2(50),
    PRIMARY KEY (ID_COM_T_TIPO_DATORE)
);

CREATE UNIQUE INDEX PK_COM_T_TIPO_DATORE ON COM_T_TIPO_DATORE (ID_COM_T_TIPO_DATORE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_LISTA_ERRORI_FM"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_LISTA_ERRORI_FM (
    ID_COM_T_LISTA_ERRORI_FM NUMBER(5) NOT NULL,
    DS_ERRORE VARCHAR2(100),
    PRIMARY KEY (ID_COM_T_LISTA_ERRORI_FM)
);

CREATE UNIQUE INDEX PK_COM_T_LISTA_ERRORI_FM ON COM_T_LISTA_ERRORI_FM (ID_COM_T_LISTA_ERRORI_FM ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_LIVELLO_STUDIO"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_LIVELLO_STUDIO (
    ID_COM_T_LIVELLO_STUDIO NUMBER(5) NOT NULL,
    TITOLO_DI_STUDIO VARCHAR2(150),
    DS_TITOLO VARCHAR2(200),
    CODICE_TITOLO_STUDIO VARCHAR2(8),
    TIPO_DI_SCUOLA VARCHAR2(300),
    SINONIMO_TITOLO_STUDIO VARCHAR2(100),
    ISCED_97 VARCHAR2(2),
    ISCED_97_LEVEL_PROG_DEST VARCHAR2(10),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    CODICE_LIVELLO_MIN VARCHAR2(2),
    PRIMARY KEY (ID_COM_T_LIVELLO_STUDIO)
);

CREATE INDEX IE_COM_T_LIVELLO_STUDIO_01 ON COM_T_LIVELLO_STUDIO (CODICE_LIVELLO_MIN ASC);

CREATE UNIQUE INDEX PK_COM_T_LIVELLO_STUDIO ON COM_T_LIVELLO_STUDIO (ID_COM_T_LIVELLO_STUDIO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TOOLTIPS"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TOOLTIPS (
    ID_COM_T_TOOLTIPS NUMBER(7) NOT NULL,
    DS_TOOLTIP VARCHAR2(1000),
    DS_NOME_FORM VARCHAR2(50),
    DS_CAMPO VARCHAR2(50),
    FLG_ABILITATO VARCHAR2(1),
    DS_NOME_CAMPO_IN_MASCHERA VARCHAR2(200),
    PRIMARY KEY (ID_COM_T_TOOLTIPS)
);

CREATE UNIQUE INDEX PK_COM_T_TOOLTIPS ON COM_T_TOOLTIPS (ID_COM_T_TOOLTIPS ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_CCNL"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_CCNL (
    ID_COM_T_CCNL NUMBER(5) NOT NULL,
    SETTORE VARCHAR2(100),
    DS_COM_T_CCNL VARCHAR2(1500),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    COD_CCNL_MIN VARCHAR2(5),
    ID_NEW_CCNL NUMBER(5),
    ID_COM_T_CCNL_PREV NUMBER(5),
    PRIMARY KEY (ID_COM_T_CCNL)
);

CREATE INDEX IE_COM_T_CCNL_01 ON COM_T_CCNL (ID_COM_T_CCNL_PREV ASC);

CREATE INDEX IE_COM_T_CCNL_02 ON COM_T_CCNL (COD_CCNL_MIN ASC);

CREATE UNIQUE INDEX PK_COM_T_CCNL ON COM_T_CCNL (ID_COM_T_CCNL ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COMUNICAZIONE"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE COMUNICAZIONE (
    ID_COMUNICAZIONE NUMBER NOT NULL,
    PV_COMPETENZA CHAR(2) NOT NULL,
    C_CPI CHAR(10),
    C_ENTE VARCHAR2(32),
    C_STATO CHAR(1) NOT NULL,
    C_TIPO_COM CHAR(1) NOT NULL,
    F_RETTIFICA CHAR(1),
    ID_COM_DA_RETTIFICARE NUMBER,
    ANNO_PROT_DA_RETTIFICARE NUMBER(4),
    N_PROT_DA_RETTIFICARE NUMBER(7),
    ANNO_PROTOCOLLO NUMBER(4),
    N_PROTOCOLLO NUMBER(7),
    DT_PROTOCOLLO DATE,
    DT_TIMBRO_POSTALE DATE,
    CF_AZ_UTILIZZATRICE VARCHAR2(16),
    CF_IMPRESA VARCHAR2(16),
    PAT_INAIL VARCHAR2(10),
    MATRICOLA_INPS VARCHAR2(16),
    PIVA VARCHAR2(11),
    C_ATECO CHAR(6),
    ATTIVITA_ATECO VARCHAR2(430),
    F_ARTIGIANA CHAR(1),
    C_NAT_GIURIDICA CHAR(2),
    NATURA_GIURIDICA VARCHAR2(80),
    DENOM_COGNOME VARCHAR2(305),
    NOME VARCHAR2(30),
    INDIRIZZO_SEDE VARCHAR2(255),
    C_COM_SEDE CHAR(6),
    COMUNE_SEDE VARCHAR2(35),
    PV_SEDE CHAR(2),
    CAP_SEDE CHAR(100),
    TEL_SEDE VARCHAR2(15),
    FAX_SEDE VARCHAR2(15),
    INDIRIZZO_UL VARCHAR2(65),
    C_COM_UL CHAR(6),
    COMUNE_UL VARCHAR2(35),
    PV_UL CHAR(2),
    CAP_UL CHAR(100),
    TEL_UL VARCHAR2(15),
    FAX_UL VARCHAR2(15),
    CF_LAVORATORE VARCHAR2(16),
    SESSO_LAVORATORE CHAR(1),
    DT_NASCITA_LAVORATORE DATE,
    DT_SCA_PERMESSO DATE,
    COGNOME_LAVORATORE VARCHAR2(30),
    NOME_LAVORATORE VARCHAR2(30),
    C_COM_NASC_LAV CHAR(4),
    COMUNE_NASC_LAV VARCHAR2(35),
    PV_NASC_LAV CHAR(2),
    C_CITTADINANZA_LAV CHAR(3),
    CITTADINANZA_LAV VARCHAR2(40),
    C_COM_RES_LAV CHAR(4),
    COMUNE_RES_LAV VARCHAR2(35),
    INDIRIZZO_RES_LAV VARCHAR2(40),
    PV_RES_LAV CHAR(2),
    CAP_RES_LAV CHAR(5),
    C_COM_DOM_LAV CHAR(4),
    COMUNE_DOM_LAV VARCHAR2(35),
    INDIRIZZO_DOM_LAV VARCHAR2(40),
    PV_DOM_LAV CHAR(2),
    CAP_DOM_LAV CHAR(5),
    TEL_DOM_LAV VARCHAR2(15),
    C_TITOLO_STUDIO CHAR(4),
    TITOLO_STUDIO VARCHAR2(100),
    DT_INIZIO_RAPPORTO DATE,
    C_RAPPORTO CHAR(3),
    RAPPORTO VARCHAR2(50),
    F_SOCIO_LAVORATORE CHAR(1),
    C_QUALIFICA VARCHAR2(6),
    MANSIONE VARCHAR2(120),
    C_L68 CHAR(2),
    LEGGE_68 VARCHAR2(40),
    C_CONTRATTO CHAR(4),
    CONTRATTO VARCHAR2(70),
    RETRIBUZIONE_LORDA NUMBER(10,2),
    LIVELLO VARCHAR2(10),
    C_GRADO_QUALIFICAZIONE CHAR(2),
    QUALIFICAZIONE VARCHAR2(30),
    F_LAV_DOMICILIO CHAR(1),
    C_PART_TIME CHAR(2),
    PART_TIME VARCHAR2(40),
    ORE_SETTIMANA NUMBER(2),
    ALTRO_ENTE_PREV VARCHAR2(150),
    ENTE_PROM_TIROCINIO VARCHAR2(40),
    C_AGEVOLAZIONE CHAR(3),
    AGEVOLAZIONE VARCHAR2(200),
    DT_FINE_RAPPORTO DATE,
    DT_CRT DATE,
    CRT VARCHAR2(30),
    CF_TUTORE VARCHAR2(16),
    SESSO_TUTORE CHAR(1),
    F_TITOLARE CHAR(1),
    DT_NASC_TUTORE DATE,
    COGNOME_TUTORE VARCHAR2(30),
    NOME_TUTORE VARCHAR2(30),
    C_QUALIFICA_TUTORE VARCHAR2(6),
    QUALIFICA_TUTORE VARCHAR2(120),
    LIVELLO_TUTORE VARCHAR2(10),
    C_GRADO_QUAL_TUTORE CHAR(2),
    QUALIFICAZIONE_TUTORE VARCHAR2(30),
    ANNI_ESPERIENZA NUMBER(2),
    DT_PROROGA DATE,
    DT_TRASFORMAZIONE DATE,
    C_TRASFORMAZIONE CHAR(2),
    TRASFORMAZIONE VARCHAR2(100),
    C_PART_TIME_TRASF CHAR(2),
    PART_TIME_TRASF VARCHAR2(40),
    ORARIO_PART_TIME_TRASF NUMBER(2),
    C_AGEVOLAZIONE_TRASF CHAR(3),
    AGEVOLAZIONE_TRASF VARCHAR2(200),
    INQUADRAMENTO_TRASF VARCHAR2(10),
    DT_CESSAZIONE DATE,
    C_CESSAZIONE CHAR(2),
    CESSAZIONE VARCHAR2(40),
    C_QUALIFICA_CESS VARCHAR2(6),
    QUALIFICA_CESS VARCHAR2(120),
    LIVELLO_CESS VARCHAR2(10),
    C_GRADO_QUAL_CESS CHAR(2),
    QUALIFICAZIONE_CESS VARCHAR2(30),
    F_RITARDO CHAR(1),
    DT_INVIO DATE,
    DT_ARRIVO DATE,
    NOME_DELEGATO_IMPRESA VARCHAR2(30),
    COGNOME_DELEGATO_IMPRESA VARCHAR2(30),
    CF_DELEGATO VARCHAR2(16),
    MAIL_DELEGATO_IMPRESA VARCHAR2(50),
    ID_DELEGA NUMBER,
    F_INPS CHAR(1),
    DT_F_INPS DATE,
    F_INAIL CHAR(1),
    DT_F_INAIL DATE,
    F_NLABOR CHAR(1),
    DT_F_NLABOR DATE,
    ANNO_PROT_MSG_POSTA NUMBER(4),
    N_PROT_MSG_POSTA NUMBER(7),
    ID_USER_INSERT VARCHAR2(16) NOT NULL,
    DT_INSERT DATE NOT NULL,
    ID_USER_ULT_MOD VARCHAR2(16),
    DT_ULT_MOD DATE,
    F_TIPO_ACQUISIZ NUMBER(1),
    F_DES_TIPO_ACQUISIZ VARCHAR2(50),
    CF_LAV_C_ASSUNZIONE VARCHAR2(16),
    DT_ASS_C_ASSUNZIONE DATE,
    N_ISC_AG_INTERINALE VARCHAR2(6),
    FLG_NO_AAEP CHAR(1) DEFAULT 'S',
    DT_VISITA_MEDICA DATE,
    F_ACCETTAZIONE VARCHAR2(1),
    ESITO_NLABOR VARCHAR2(3),
    F_EXPSILP_STATO_INIZ CHAR(1),
    F_EXPSILP_STATO_FIN CHAR(1),
    D_EXPSILP_STATO_INIZ DATE,
    D_EXPSILP_STATO_FIN DATE,
    VERIFICA VARCHAR2(2) DEFAULT 'NO',
    DATA_VERIFICA DATE,
    N_GIORNO_AGRICOLTURA NUMBER,
    F_INVIO_INPS CHAR(1),
    F_INVIO_INAIL CHAR(1),
    LEGGE_68_CONVENZIONE VARCHAR2(20),
    ID_COM_D_COMUNICAZIONE NUMBER(9),
    DATA_ACQUISIZIONE DATE,
    ESITO_ACQUISIZIONE VARCHAR2(1),
    AUPIVA VARCHAR2(11),
    AURAGIONE_SOCIALE VARCHAR2(60),
    AUINDIRIZZO VARCHAR2(45),
    AUCAP VARCHAR2(5),
    AUCOMUNE VARCHAR2(4),
    AUCOD_ATTIVITA VARCHAR2(6),
    AUNUM_CONTRATTO VARCHAR2(10),
    PRIMARY KEY (ID_COMUNICAZIONE),
    CHECK (F_EXPSILP_STATO_FIN IN ('E','I','S','P')),
    CHECK (F_EXPSILP_STATO_INIZ IN ('E','I','S','P'))
);

CREATE UNIQUE INDEX PK_COMUNICAZIONE ON COMUNICAZIONE (ID_COMUNICAZIONE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_UNITA_MISURA_DURATA"                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_UNITA_MISURA_DURATA (
    COD_UNITA_MISURA_DURATA VARCHAR2(2) NOT NULL,
    DESCR_UNITA_MISURA_DURATA VARCHAR2(10) NOT NULL,
    D_INIZIO DATE NOT NULL,
    D_FINE DATE,
    PRIMARY KEY (COD_UNITA_MISURA_DURATA)
);

CREATE UNIQUE INDEX PK_COM_T_UNITA_MISURA_DURATA ON COM_T_UNITA_MISURA_DURATA (COD_UNITA_MISURA_DURATA ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_CPI"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_CPI (
    ID_COM_T_CPI NUMBER(5) NOT NULL,
    DS_COM_T_CPI VARCHAR2(50),
    COD_CPI VARCHAR2(10),
    C_UTENTE VARCHAR2(20),
    DT_INIZIO DATE,
    DT_FINE DATE,
    PRIMARY KEY (ID_COM_T_CPI)
);

CREATE INDEX IDX_COD_CPI ON COM_T_CPI (COD_CPI ASC);

CREATE UNIQUE INDEX PK_COM_T_CPI ON COM_T_CPI (ID_COM_T_CPI ASC);

/* ---------------------------------------------------------------------- */
/* Add table "TMP_DEL"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE TMP_DEL (
    ID_DELEGA NUMBER NOT NULL,
    PV_COMPETENZA CHAR(2) NOT NULL,
    C_TIPO_DELEGA CHAR(1) NOT NULL,
    C_STATO CHAR(1) NOT NULL,
    C_ENTE VARCHAR2(16),
    CARICA VARCHAR2(253) NOT NULL,
    ANNO_PROTOCOLLO NUMBER(4),
    N_PROTOCOLLO NUMBER(7),
    DT_PROTOCOLLO DATE,
    CF_DELEGATO VARCHAR2(16) NOT NULL,
    COGNOME_DELEGATO VARCHAR2(30) NOT NULL,
    NOME_DELEGATO VARCHAR2(30) NOT NULL,
    INDIRIZZO_DELEGATO VARCHAR2(40) NOT NULL,
    C_COM_DELEGATO CHAR(4) NOT NULL,
    COMUNE_DELEGATO VARCHAR2(40) NOT NULL,
    PV_DELEGATO CHAR(2) NOT NULL,
    CAP_DELEGATO CHAR(5),
    TELEFONO_DELEGATO VARCHAR2(15),
    FAX_DELEGATO VARCHAR2(15),
    MAIL_DELEGATO VARCHAR2(50),
    PIVA_DELEGATO VARCHAR2(11),
    CF_DELEGANTE VARCHAR2(16) NOT NULL,
    COGNOME_DELEGANTE VARCHAR2(30) NOT NULL,
    NOME_DELEGANTE VARCHAR2(30) NOT NULL,
    INDIRIZZO_DELEGANTE VARCHAR2(40) NOT NULL,
    C_COM_DELEGANTE CHAR(4) NOT NULL,
    COMUNE_DELEGANTE VARCHAR2(40) NOT NULL,
    PV_DELEGANTE CHAR(2) NOT NULL,
    CAP_DELEGANTE CHAR(5),
    TELEFONO_DELEGANTE VARCHAR2(15),
    MAIL_DELEGANTE VARCHAR2(50),
    DENOMINAZIONE_IMPRESA VARCHAR2(305),
    CF_IMPRESA VARCHAR2(16) NOT NULL,
    PIVA_IMPRESA VARCHAR2(11),
    DT_REVOCA DATE,
    ID_USER_INSERT VARCHAR2(16) NOT NULL,
    DT_INSERT DATE NOT NULL,
    ID_USER_ULT_MOD VARCHAR2(16),
    DT_ULT_MOD DATE,
    VERIFICA VARCHAR2(2),
    DATA_VERIFICA DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TIPO_ORARIO"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TIPO_ORARIO (
    ID_COM_T_TIPO_ORARIO NUMBER(5) NOT NULL,
    DS_COM_T_TIPO_ORARIO VARCHAR2(50),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    COD_TIPOORARIO_MIN VARCHAR2(2),
    ID_NEW_TIPOORARIO NUMBER(5),
    PRIMARY KEY (ID_COM_T_TIPO_ORARIO)
);

CREATE UNIQUE INDEX PK_COM_T_TIPO_ORARIO ON COM_T_TIPO_ORARIO (ID_COM_T_TIPO_ORARIO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_COMMAX_PARAMETRI"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_COMMAX_PARAMETRI (
    ID_PARAMETRO NUMBER NOT NULL,
    DS_PARAMETRO VARCHAR2(4000) NOT NULL,
    B_DATO BLOB,
    VALORE_PARAMETRO VARCHAR2(4000) NOT NULL,
    TIPO_PARAMETRO VARCHAR2(100) NOT NULL,
    PRIMARY KEY (ID_PARAMETRO)
);

CREATE UNIQUE INDEX PK_COM_T_COMMAX_PARAMETRI ON COM_T_COMMAX_PARAMETRI (ID_PARAMETRO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_QUESTURA"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_QUESTURA (
    ID_COM_T_QUESTURA NUMBER(3) NOT NULL,
    DS_QUESTURA VARCHAR2(100) NOT NULL,
    COD_QUESTURA_MIN VARCHAR2(3) NOT NULL,
    DT_INIZIO DATE,
    DT_FINE DATE,
    PRIMARY KEY (ID_COM_T_QUESTURA)
);

CREATE UNIQUE INDEX PK_COM_T_QUESTURA ON COM_T_QUESTURA (ID_COM_T_QUESTURA ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TIPO_SOMMINISTRAZIONE"                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TIPO_SOMMINISTRAZIONE (
    ID_COM_T_TIPO_SOMMINISTRAZIONE NUMBER(2) NOT NULL,
    DS_COM_T_TIPO_SOMMINISTRAZIONE VARCHAR2(50),
    PRIMARY KEY (ID_COM_T_TIPO_SOMMINISTRAZIONE)
);

CREATE UNIQUE INDEX PK_COM_T_TIPO_SOMMINISTRAZIONE ON COM_T_TIPO_SOMMINISTRAZIONE (ID_COM_T_TIPO_SOMMINISTRAZIONE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_PROVINCIA"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_PROVINCIA (
    ID_COM_T_PROVINCIA NUMBER(5) NOT NULL,
    ID_COM_T_REGIONE NUMBER(5),
    DS_COM_T_PROVINCIA VARCHAR2(50),
    DS_TARGA VARCHAR2(20),
    COD_PROVINCIA_MIN VARCHAR2(3),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    CODICE_FISCALE_PV VARCHAR2(16),
    PRIMARY KEY (ID_COM_T_PROVINCIA)
);

CREATE UNIQUE INDEX PK_COM_T_PROVINCIA ON COM_T_PROVINCIA (ID_COM_T_PROVINCIA ASC);

/* ---------------------------------------------------------------------- */
/* Add table "STATO_DELEGA"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE STATO_DELEGA (
    ID_STATO_DELEGA CHAR(1) NOT NULL,
    DS_STATO_DELEGA VARCHAR2(30) NOT NULL,
    T_USER CHAR(1),
    PRIMARY KEY (ID_STATO_DELEGA)
);

CREATE UNIQUE INDEX PK_STATO_DELEGA ON STATO_DELEGA (ID_STATO_DELEGA ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_ISTAT2001LIVELLO5"                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_ISTAT2001LIVELLO5 (
    ID_COM_ISTAT2001LIVELLO5 NUMBER(5) NOT NULL,
    DS_COM_ISTAT2001LIVELLO5 VARCHAR2(300),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    COD_ISTAT2001LIVELLO5_MIN VARCHAR2(12),
    ID_NEW_ISTAT NUMBER(5),
    FLG_VLD_UD VARCHAR2(1),
    PRIMARY KEY (ID_COM_ISTAT2001LIVELLO5)
);

CREATE UNIQUE INDEX PK_COM_T_ISTAT2001LIVELLO5 ON COM_T_ISTAT2001LIVELLO5 (ID_COM_ISTAT2001LIVELLO5 ASC);

COMMENT ON COLUMN COM_T_ISTAT2001LIVELLO5.FLG_VLD_UD IS 'Flag per indicare le qualifiche valide per il tracciato UNIDOM';

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TIPO_PROVENIENZA"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TIPO_PROVENIENZA (
    ID_COM_T_TIPO_PROVENIENZA NUMBER(1) NOT NULL,
    DS_COM_T_TIPO_PROVENIENZA VARCHAR2(100),
    COD_TIPO_PROVENIENZA_MIN VARCHAR2(2),
    PRIMARY KEY (ID_COM_T_TIPO_PROVENIENZA)
);

CREATE UNIQUE INDEX PK_COM_T_TIPO_PROVENIENZA ON COM_T_TIPO_PROVENIENZA (ID_COM_T_TIPO_PROVENIENZA ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TIPO_LAVORATORE"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TIPO_LAVORATORE (
    ID_COM_T_TIPO_LAVORATORE VARCHAR2(1) NOT NULL,
    DS_COM_T_TIPO_LAVORATORE VARCHAR2(50),
    PRIMARY KEY (ID_COM_T_TIPO_LAVORATORE)
);

CREATE UNIQUE INDEX PK_COM_T_TIPO_LAVORATORE ON COM_T_TIPO_LAVORATORE (ID_COM_T_TIPO_LAVORATORE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_CATEG_LAV_ASS_OBBL"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_CATEG_LAV_ASS_OBBL (
    ID_COM_T_CATEG_LAV_ASS_OBBL NUMBER NOT NULL,
    DS_COM_T_CATEG_LAV_ASS_OBBL VARCHAR2(100) NOT NULL,
    NORMA_RIF_CATEG_LAV_ASS_OBBL VARCHAR2(20),
    DT_INIZIO DATE NOT NULL,
    DT_FINE DATE,
    COD_CATEG_LAV_ASS_OBBL_MIN VARCHAR2(2) NOT NULL,
    COD_NORM_ASS_68_MIN VARCHAR2(2),
    FLG_OBBLIGATORIO_BLOCCO_L68 VARCHAR2(1) NOT NULL,
    ORDINAMENTO NUMBER(2),
    PRIMARY KEY (ID_COM_T_CATEG_LAV_ASS_OBBL),
    CHECK (FLG_OBBLIGATORIO_BLOCCO_L68 IN ('S', 'N'))
);

CREATE UNIQUE INDEX PK_COM_T_CATEG_LAV_ASS_OBBL ON COM_T_CATEG_LAV_ASS_OBBL (ID_COM_T_CATEG_LAV_ASS_OBBL ASC);

COMMENT ON COLUMN COM_T_CATEG_LAV_ASS_OBBL.FLG_OBBLIGATORIO_BLOCCO_L68 IS 'Indica se i campi relativi alla Legge68 che non vanno piu'' spediti al ministero, ma solo a SILP, devono essere compilati.';

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TIPO_ACQUISIZIONE"                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TIPO_ACQUISIZIONE (
    ID_COM_T_TIPO_ACQUISIZIONE NUMBER(1) NOT NULL,
    DS_COM_T_TIPO_ACQUISIZIONE VARCHAR2(50),
    PRIMARY KEY (ID_COM_T_TIPO_ACQUISIZIONE)
);

CREATE UNIQUE INDEX PK_COM_T_TIPO_ACQUISIZIONE ON COM_T_TIPO_ACQUISIZIONE (ID_COM_T_TIPO_ACQUISIZIONE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TIPO_ENTE_PROM_TIROCINIO"                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TIPO_ENTE_PROM_TIROCINIO (
    ID_COM_T_TIPO_ENTE_PROM_TIR NUMBER(5) NOT NULL,
    DS_COM_T_TIPO_ENTE_PROM_TIR VARCHAR2(50),
    TIP_SIGLA VARCHAR2(10),
    CODICE_ENTE_PROM_TIROCINIO_MIN VARCHAR2(2),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    PRIMARY KEY (ID_COM_T_TIPO_ENTE_PROM_TIR)
);

CREATE UNIQUE INDEX PK_COM_T_TIPO_ENTE_PROM_TIROC ON COM_T_TIPO_ENTE_PROM_TIROCINIO (ID_COM_T_TIPO_ENTE_PROM_TIR ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TIPO_LIVELLO_RETRIB"                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TIPO_LIVELLO_RETRIB (
    COD_TIPO_LIVELLO VARCHAR2(4) NOT NULL,
    DESCR_TIPO_LIVELLO VARCHAR2(40),
    DT_INIZIO DATE NOT NULL,
    DT_FINE DATE,
    PRIMARY KEY (COD_TIPO_LIVELLO)
);

CREATE UNIQUE INDEX PK_COM_T_TIPO_LIVELLO_RETRIB ON COM_T_TIPO_LIVELLO_RETRIB (COD_TIPO_LIVELLO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_DELEGATO_IMP_IRIDE"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_DELEGATO_IMP_IRIDE (
    ID_COM_D_DELEGATO_IMP_IRIDE NUMBER(7) NOT NULL,
    CF_DELEGATO VARCHAR2(16) NOT NULL,
    TIPO_ANAGRAFICA CHAR(1) NOT NULL,
    CF_IMPRESA VARCHAR2(16) NOT NULL,
    DS_DENOMINAZIONE_IMPRESA VARCHAR2(300),
    DS_INDIRIZZO_IMPRESA VARCHAR2(40),
    CAP_IMPRESA CHAR(5),
    DS_COMUNE_IMPRESA CHAR(18),
    DS_PROVINCIA_IMPRESA CHAR(18),
    FLG_IMPRESA_ABILITANTE CHAR(1),
    PRIMARY KEY (ID_COM_D_DELEGATO_IMP_IRIDE)
);

CREATE UNIQUE INDEX PK_COM_D_DELEGATO_IMP_IRIDE ON COM_D_DELEGATO_IMP_IRIDE (ID_COM_D_DELEGATO_IMP_IRIDE ASC);

CREATE UNIQUE INDEX XAK1COM_D_DELEGATO_IMP_IRIDE ON COM_D_DELEGATO_IMP_IRIDE (CF_DELEGATO ASC,TIPO_ANAGRAFICA ASC,CF_IMPRESA ASC);

/* ---------------------------------------------------------------------- */
/* Add table "ANAGRAFICA_LAVORATORE"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE ANAGRAFICA_LAVORATORE (
    CF_LAVORATORE VARCHAR2(16) NOT NULL,
    COGNOME VARCHAR2(30) NOT NULL,
    NOME VARCHAR2(20) NOT NULL,
    DT_NASCITA DATE NOT NULL,
    SESSO CHAR(1),
    C_COM_NASC CHAR(4) NOT NULL,
    C_CITTADINANZA CHAR(3),
    STATO_CIVILE CHAR(2),
    C_COM_RES CHAR(4) NOT NULL,
    INDIRIZZO_RES VARCHAR2(40),
    CAP_RES CHAR(5),
    C_COM_DOM CHAR(4) NOT NULL,
    INDIRIZZO_DOM VARCHAR2(40),
    CAP_DOM CHAR(5),
    TEL_DOM VARCHAR2(15),
    CLASSE CHAR(2),
    N_ARCHIVIO CHAR(6),
    F_STATO_LAV CHAR(1),
    CLASSE181 CHAR(4),
    DT_LAST_REV DATE,
    DT_181 DATE,
    F_GRADUATORIA CHAR(1),
    LISTE CHAR(5),
    F_VALIDITA CHAR(1),
    F_RISERVATI CHAR(1),
    C_CPI CHAR(10),
    F_OBB_SCOLASTICO CHAR(1),
    F_OBB_FORMAZIONE CHAR(1),
    DATA_ACQUISIZIONE DATE,
    ESITO_ACQUISIZIONE VARCHAR2(1),
    PRIMARY KEY (CF_LAVORATORE)
);

CREATE INDEX COMONLANL1X ON ANAGRAFICA_LAVORATORE (COGNOME ASC);

CREATE UNIQUE INDEX PK_ANAGRAFICA_LAVORATORE ON ANAGRAFICA_LAVORATORE (CF_LAVORATORE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "TIPO_DELEGA"                                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE TIPO_DELEGA (
    ID_TIPO_DELEGA CHAR(1) NOT NULL,
    DS_TIPO_DELEGA VARCHAR2(40) NOT NULL,
    PRIMARY KEY (ID_TIPO_DELEGA)
);

CREATE UNIQUE INDEX PK_TIPO_DELEGA ON TIPO_DELEGA (ID_TIPO_DELEGA ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_AZIONI_SPECIFICHE"                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_AZIONI_SPECIFICHE (
    ID_COM_T_AZIONI_SPECIFICHE NUMBER NOT NULL,
    DS_CAMPO VARCHAR2(50),
    DS_VALORE VARCHAR2(50),
    DS_NOME_PAGINA VARCHAR2(50),
    FLG_ABILITATO CHAR(1),
    PRIMARY KEY (ID_COM_T_AZIONI_SPECIFICHE)
);

CREATE UNIQUE INDEX PK_COM_T_AZIONI_SPECIFICHE ON COM_T_AZIONI_SPECIFICHE (ID_COM_T_AZIONI_SPECIFICHE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "NATURA_GIURIDICA"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE NATURA_GIURIDICA (
    C_NATURA_GIURIDICA VARCHAR2(3) NOT NULL,
    DESCRIZIONE VARCHAR2(150),
    T_ISCRIZIONE CHAR(1),
    DT_INIZIO_VLDT DATE,
    DT_FINE_VLDT DATE,
    I_REGOLARIZZAZIONE CHAR(1),
    I_BUSARL CHAR(1),
    I_BUSC CHAR(1),
    PRIMARY KEY (C_NATURA_GIURIDICA)
);

CREATE UNIQUE INDEX PK_NATURA_GIURIDICA ON NATURA_GIURIDICA (C_NATURA_GIURIDICA ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_LISTA_ERRORI_FZ"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_LISTA_ERRORI_FZ (
    ID_COM_T_LISTA_ERRORI_FZ NUMBER(5) NOT NULL,
    DS_ERRORE VARCHAR2(100),
    PRIMARY KEY (ID_COM_T_LISTA_ERRORI_FZ)
);

CREATE UNIQUE INDEX PK_COM_T_LISTA_ERRORI_FZ ON COM_T_LISTA_ERRORI_FZ (ID_COM_T_LISTA_ERRORI_FZ ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_STATO_COMUNICAZIONE"                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_STATO_COMUNICAZIONE (
    ID_COM_T_STATO_COMUNICAZIONE NUMBER(5) NOT NULL,
    DS_COM_T_STATO_COMUNICAZIONE VARCHAR2(30),
    T_USER CHAR(1),
    PRIMARY KEY (ID_COM_T_STATO_COMUNICAZIONE)
);

CREATE UNIQUE INDEX PK_COM_T_STATO_COMUNICAZIONE ON COM_T_STATO_COMUNICAZIONE (ID_COM_T_STATO_COMUNICAZIONE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_STATUS_STRANIERO"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_STATUS_STRANIERO (
    ID_COM_T_STATUS_STRANIERO NUMBER(5) NOT NULL,
    COD_STATUS_MIN VARCHAR2(1),
    DS_COM_T_STATUS_STRANIERO VARCHAR2(100),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    PRIMARY KEY (ID_COM_T_STATUS_STRANIERO)
);

CREATE INDEX IE_COM_T_STATUS_STRANIERO_01 ON COM_T_STATUS_STRANIERO (COD_STATUS_MIN ASC);

CREATE UNIQUE INDEX PK_COM_T_STATUS_STRANIERO ON COM_T_STATUS_STRANIERO (ID_COM_T_STATUS_STRANIERO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TIPOLOGIA_TIROCINIO"                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TIPOLOGIA_TIROCINIO (
    ID_COM_T_TIPOLOGIA_TIROCINIO NUMBER NOT NULL,
    DESCR_TIPOLOGIA_TIROCINIO VARCHAR2(100) NOT NULL,
    COD_TIPOLOGIA_TIROCINIO_MIN VARCHAR2(2) NOT NULL,
    DT_INIZIO DATE NOT NULL,
    DT_FINE DATE,
    PRIMARY KEY (ID_COM_T_TIPOLOGIA_TIROCINIO)
);

CREATE UNIQUE INDEX PK_COM_T_TIPOLOGIA_TIROCINIO ON COM_T_TIPOLOGIA_TIROCINIO (ID_COM_T_TIPOLOGIA_TIROCINIO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TIPO_COMUNICAZIONE"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TIPO_COMUNICAZIONE (
    ID_COM_T_TIPO_COMUNICAZIONE VARCHAR2(3) NOT NULL,
    DS_COM_T_TIPO_COMUNICAZIONE VARCHAR2(50) NOT NULL,
    VALIDITA_CPI NUMBER,
    VALIDITA_AG_INT NUMBER,
    TIPO VARCHAR2(1),
    PRIMARY KEY (ID_COM_T_TIPO_COMUNICAZIONE),
    UNIQUE (TIPO)
);

CREATE UNIQUE INDEX PK_COM_T_TIPO_COMUNICAZIONE ON COM_T_TIPO_COMUNICAZIONE (ID_COM_T_TIPO_COMUNICAZIONE ASC);

CREATE UNIQUE INDEX AK_COM_T_TIPO_COMUNICAZIONE_01 ON COM_T_TIPO_COMUNICAZIONE (TIPO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_ERR_COMUNIC"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_ERR_COMUNIC (
    ID_COMUNICAZIONE NUMBER,
    DES_ERR VARCHAR2(200),
    DATA_INS DATE
);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TIPO_COMUNICAZIONE_TU"                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TIPO_COMUNICAZIONE_TU (
    ID_COM_T_TIPO_COMUNICAZIONE_TU NUMBER(5) NOT NULL,
    DS_COM_T_TIPO_COMUNICAZIONE VARCHAR2(200),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    COD_TIPO_COMUNICAZIONE_MIN VARCHAR2(2),
    PRIMARY KEY (ID_COM_T_TIPO_COMUNICAZIONE_TU)
);

CREATE UNIQUE INDEX PK_COM_T_TIPO_COMUNICAZIONE_TU ON COM_T_TIPO_COMUNICAZIONE_TU (ID_COM_T_TIPO_COMUNICAZIONE_TU ASC);

/* ---------------------------------------------------------------------- */
/* Add table "PROT_PIEMONTE"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE PROT_PIEMONTE (
    ANNO_PROT NUMBER(4) NOT NULL,
    NRO_PROT NUMBER(7) NOT NULL,
    DTA_INS DATE NOT NULL,
    TIPO_DOC NUMBER(3) NOT NULL,
    STRUTTURA VARCHAR2(9),
    TIPO VARCHAR2(1) NOT NULL,
    MITTENTE VARCHAR2(240) NOT NULL,
    DTA_ORIGINE DATE,
    PROT_ORIGINE VARCHAR2(15),
    NOTE VARCHAR2(240),
    OGG1 VARCHAR2(65) NOT NULL,
    OGG2 VARCHAR2(65),
    OGG3 VARCHAR2(65),
    OGG4 VARCHAR2(65),
    OGG5 VARCHAR2(65),
    DTA_TRASM DATE,
    IMPORTO NUMBER(15,2),
    STRUTTURA_REG VARCHAR2(9),
    FLAG_YN VARCHAR2(1),
    LOG_NOME VARCHAR2(8),
    CATEGORIA NUMBER(2),
    CLASSE VARCHAR2(4),
    FASCICOLO VARCHAR2(20),
    NOTE_ARCH VARCHAR2(65)
);

CREATE INDEX PROT_PIEMONTE1X ON PROT_PIEMONTE (ANNO_PROT ASC,TIPO ASC,NRO_PROT ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_USER_ACCESS_LOG"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_USER_ACCESS_LOG (
    ID_COM_D_USER_ACCESS_LOG NUMBER(12) NOT NULL,
    DS_RUOLO VARCHAR2(50),
    FLG_TROVATO_SU_AAEP CHAR(1) DEFAULT 'N',
    DS_NOME VARCHAR2(50),
    DS_COGNOME VARCHAR2(50),
    CF_UTENTE VARCHAR2(16),
    DT_EVENTO DATE DEFAULT sysdate,
    PRIMARY KEY (ID_COM_D_USER_ACCESS_LOG)
);

CREATE INDEX IE_COM_D_USER_ACCESS_LOG_01 ON COM_D_USER_ACCESS_LOG (CF_UTENTE ASC);

CREATE INDEX IE_COM_D_USER_ACCESS_LOG_02 ON COM_D_USER_ACCESS_LOG (DT_EVENTO ASC);

CREATE UNIQUE INDEX PK_COM_D_USER_ACCESS_LOG ON COM_D_USER_ACCESS_LOG (ID_COM_D_USER_ACCESS_LOG ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_ATECOFIN"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_ATECOFIN (
    ID_COM_T_ATECOFIN NUMBER(5) NOT NULL,
    DS_COM_T_ATECOFIN VARCHAR2(250),
    DT_INIZIO DATE,
    DT_FINE DATE,
    COD_ATECOFIN_MIN VARCHAR2(8),
    DT_TMST DATE,
    ID_NEW_ATECOFIN NUMBER(5),
    PRIMARY KEY (ID_COM_T_ATECOFIN)
);

CREATE UNIQUE INDEX PK_COM_T_ATECOFIN ON COM_T_ATECOFIN (ID_COM_T_ATECOFIN ASC);

COMMENT ON COLUMN COM_T_ATECOFIN.COD_ATECOFIN_MIN IS 'codice verso il ministero. Formato nn.nn.n  (n = carattere numerico)';

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_PARAM_ABILITAZ_IRIDE"                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_PARAM_ABILITAZ_IRIDE (
    COD_FRUITORE VARCHAR2(10) NOT NULL,
    MAIL_MITTENTE VARCHAR2(50),
    MAIL_DESTINATARIO VARCHAR2(50),
    OGGETTO_MAIL VARCHAR2(50),
    TESTO_MAIL VARCHAR2(300),
    NUM_MAX_RISULTATI NUMBER(7),
    NUM_MAX_INVII NUMBER(7),
    PRIMARY KEY (COD_FRUITORE)
);

CREATE UNIQUE INDEX PK_COM_T_PARAM_ABILITAZ_IRIDE ON COM_T_PARAM_ABILITAZ_IRIDE (COD_FRUITORE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TIPO_ATTO_L68"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TIPO_ATTO_L68 (
    ID_COM_T_TIPO_ATTO_L68 VARCHAR2(1) NOT NULL,
    DS_COM_T_TIPO_ATTO_L68 VARCHAR2(30),
    PRIMARY KEY (ID_COM_T_TIPO_ATTO_L68)
);

CREATE UNIQUE INDEX PK_COM_T_TIPO_ATTO_L68 ON COM_T_TIPO_ATTO_L68 (ID_COM_T_TIPO_ATTO_L68 ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_COMONLS_PARAMETRI"                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_COMONLS_PARAMETRI (
    ID_PARAMETRO NUMBER NOT NULL,
    DS_PARAMETRO VARCHAR2(4000),
    VALORE_PARAMETRO VARCHAR2(4000),
    TIPO_PARAMETRO VARCHAR2(100),
    ABILITATO VARCHAR2(1),
    MODIFICABILE_DA_MASCHERA VARCHAR2(1),
    DESCRIZIONE VARCHAR2(500),
    PRIMARY KEY (ID_PARAMETRO)
);

CREATE UNIQUE INDEX PK_COM_T_COMONLS_PARAMETRI ON COM_T_COMONLS_PARAMETRI (ID_PARAMETRO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TITOLO_STUDIO"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TITOLO_STUDIO (
    ID_COM_T_TITOLO_STUDIO NUMBER(5) NOT NULL,
    C_GRADOSTUDIO CHAR(1) NOT NULL,
    GRADOSTUDIO VARCHAR2(40) NOT NULL,
    C_TIPOSTUDIO CHAR(2) NOT NULL,
    TIPOSTUDIO VARCHAR2(60) NOT NULL,
    C_TITOLOSTUDIO CHAR(4) NOT NULL,
    TITOLOSTUDIO VARCHAR2(60) NOT NULL,
    C_TITOLOSTUDIOMIN CHAR(3) NOT NULL,
    TITOLOSTUDIOMIN VARCHAR2(60) NOT NULL,
    ID_COM_T_LIVELLO_STUDIO NUMBER(5),
    PRIMARY KEY (ID_COM_T_TITOLO_STUDIO)
);

CREATE UNIQUE INDEX PK_COM_T_TITOLO_STUDIO ON COM_T_TITOLO_STUDIO (ID_COM_T_TITOLO_STUDIO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_CITTADINANZA"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_CITTADINANZA (
    ID_COM_T_CITTADINANZA NUMBER(5) NOT NULL,
    COD_MF VARCHAR2(4),
    DS_NAZIONE VARCHAR2(50),
    DS_COM_T_CITTADINANZA VARCHAR2(50),
    FLG_UE VARCHAR2(1),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    COD_CITTADINANZA_MIN VARCHAR2(3),
    COD_CITTADINANZA_OLD VARCHAR2(3),
    PRIMARY KEY (ID_COM_T_CITTADINANZA)
);

CREATE INDEX IE_COM_T_CITTADINANZA_01 ON COM_T_CITTADINANZA (COD_CITTADINANZA_MIN ASC);

CREATE UNIQUE INDEX PK_COM_T_CITTADINANZA ON COM_T_CITTADINANZA (ID_COM_T_CITTADINANZA ASC);

/* ---------------------------------------------------------------------- */
/* Add table "TMP_ANAG"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE TMP_ANAG (
    CF_DELEGATO VARCHAR2(16) NOT NULL,
    PV CHAR(2) NOT NULL,
    COGNOME VARCHAR2(30) NOT NULL,
    NOME VARCHAR2(30) NOT NULL,
    DT_NASCITA DATE NOT NULL,
    SESSO CHAR(1),
    C_COM_NASC CHAR(4) NOT NULL,
    C_CITTADINANZA CHAR(3),
    C_COM_RES CHAR(4),
    INDIRIZZO_RES VARCHAR2(40),
    CAP_RES CHAR(5),
    C_COM_DOM CHAR(4),
    INDIRIZZO_DOM VARCHAR2(40),
    CAP_DOM CHAR(5),
    TEL VARCHAR2(15),
    FAX VARCHAR2(15),
    MAIL VARCHAR2(50),
    PIVA_DELEGATO VARCHAR2(11),
    ID_USER_INSERT VARCHAR2(16) NOT NULL,
    DT_INSERT DATE NOT NULL,
    ID_USER_ULT_MOD VARCHAR2(16),
    DT_ULT_MOD DATE,
    TIPO_ANAGRAFICA CHAR(1) NOT NULL
);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_AGEVOLAZIONERL"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_AGEVOLAZIONERL (
    ID_COM_T_AGEVOLAZIONERL NUMBER(5) NOT NULL,
    DS_COM_T_AGEVOLAZIONERL VARCHAR2(1000),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    COD_AGEVOLAZIONERL_MIN VARCHAR2(4),
    PRIMARY KEY (ID_COM_T_AGEVOLAZIONERL)
);

CREATE UNIQUE INDEX PK_COM_T_AGEVOLAZIONERL ON COM_T_AGEVOLAZIONERL (ID_COM_T_AGEVOLAZIONERL ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TIPO_RAPPORTO_AZIENDA"                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TIPO_RAPPORTO_AZIENDA (
    ID_COM_T_TIPO_RAPPORTO_AZIENDA VARCHAR2(1) NOT NULL,
    DS_COM_T_TIPO_RAPPORTO_AZIENDA VARCHAR2(50),
    PRIMARY KEY (ID_COM_T_TIPO_RAPPORTO_AZIENDA)
);

CREATE UNIQUE INDEX PK_COM_T_TIPO_RAPPORTO_AZIENDA ON COM_T_TIPO_RAPPORTO_AZIENDA (ID_COM_T_TIPO_RAPPORTO_AZIENDA ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TIPO_SOGGETTO_ABILITATO"                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TIPO_SOGGETTO_ABILITATO (
    ID_COM_TIPO_SOGGETTO_ABILITATO NUMBER(5) NOT NULL,
    DS_COM_TIPO_SOGGETTO_ABILITATO VARCHAR2(100),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    COD_SOGGETTO_ABILITATO_MIN VARCHAR2(3),
    PRIMARY KEY (ID_COM_TIPO_SOGGETTO_ABILITATO)
);

CREATE UNIQUE INDEX PK_COM_T_TIPO_SOGGETTO_ABILIT ON COM_T_TIPO_SOGGETTO_ABILITATO (ID_COM_TIPO_SOGGETTO_ABILITATO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_COMMAX_AUTH"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_COMMAX_AUTH (
    ID_COM_D_COMMAX_AUTH NUMBER(7) NOT NULL,
    DS_TOKEN_AUTH VARCHAR2(40) NOT NULL,
    DS_RUOLO_OPERATORE VARCHAR2(50),
    TIPO_RUOLO VARCHAR2(2),
    CF_OPERATORE CHAR(18),
    DT_CREAZIONE CHAR(18),
    DS_NOME VARCHAR2(50),
    DS_COGNOME VARCHAR2(50),
    DS_EMAIL VARCHAR2(50),
    CF_AZIENDA VARCHAR2(16),
    PRIMARY KEY (ID_COM_D_COMMAX_AUTH)
);

CREATE UNIQUE INDEX PK_COM_D_COMMAX_AUTH ON COM_D_COMMAX_AUTH (ID_COM_D_COMMAX_AUTH ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_DELEGATO"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_DELEGATO (
    ID_COM_D_DELEGATO NUMBER(9) NOT NULL,
    NOME_DELEGATO_IMPRESA VARCHAR2(100),
    COGNOME_DELEGATO_IMPRESA VARCHAR2(100),
    MAIL_DELEGATO_IMPRESA VARCHAR2(50),
    CF_DELEGATO VARCHAR2(16),
    PRIMARY KEY (ID_COM_D_DELEGATO)
);

CREATE UNIQUE INDEX PK_COM_D_DELEGATO ON COM_D_DELEGATO (ID_COM_D_DELEGATO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "DEPOSITO_COMMAX"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE DEPOSITO_COMMAX (
    ID_DEPOSITO_COMMAX NUMBER NOT NULL,
    PROG_XML NUMBER NOT NULL,
    B_FILE_COMMAX BLOB DEFAULT EMPTY_BLOB(),
    CF_UTENTE VARCHAR2(16) NOT NULL,
    DESC_UTENTE VARCHAR2(100) NOT NULL,
    EMAIL_UTENTE VARCHAR2(100) NOT NULL,
    STATO VARCHAR2(1) DEFAULT 'N' NOT NULL,
    D_INSERIM DATE NOT NULL,
    D_TRATTAMENTO DATE,
    SEQ_ELAB_COMMAX NUMBER,
    B_FILE_PROC_COMMAX BLOB DEFAULT EMPTY_BLOB(),
    STATO_INVIO VARCHAR2(1) DEFAULT 'N' NOT NULL,
    CODICE_COMUNICAZIONE_REG VARCHAR2(16),
    CF_AZIENDA VARCHAR2(16),
    CF_LAVORATORE VARCHAR2(16),
    ID_COM_D_UPL_DOCUMENTI NUMBER(7),
    NOME_FILE_XML_SINGOLO VARCHAR2(300),
    CRC_XML NUMBER,
    PRIMARY KEY (ID_DEPOSITO_COMMAX, PROG_XML)
);

CREATE INDEX IDX_ID_COM_UPL_DOCUMENTI ON DEPOSITO_COMMAX (ID_COM_D_UPL_DOCUMENTI ASC);

CREATE UNIQUE INDEX PK_DEPOSITO_COMMAX ON DEPOSITO_COMMAX (ID_DEPOSITO_COMMAX ASC,PROG_XML ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TRASFORMAZIONERL"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TRASFORMAZIONERL (
    ID_COM_T_TRASFORMAZIONERL NUMBER(5) NOT NULL,
    DS_COM_T_TRASFORMAZIONERL VARCHAR2(100),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    COD_TRASFORMAZIONIRL_MIN VARCHAR2(2),
    TIPO VARCHAR2(3),
    PRIMARY KEY (ID_COM_T_TRASFORMAZIONERL)
);

CREATE UNIQUE INDEX PK_COM_T_TRASFORMAZIONERL ON COM_T_TRASFORMAZIONERL (ID_COM_T_TRASFORMAZIONERL ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TIPO_TRACCIATO"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TIPO_TRACCIATO (
    ID_COM_T_TIPO_TRACCIATO VARCHAR2(2) NOT NULL,
    DS_COM_T_TIPO_TRACCIATO VARCHAR2(50),
    PRIMARY KEY (ID_COM_T_TIPO_TRACCIATO)
);

CREATE UNIQUE INDEX PK_COM_T_TIPO_TRACCIATO ON COM_T_TIPO_TRACCIATO (ID_COM_T_TIPO_TRACCIATO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_CATEG_TIROCINANTE"                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_CATEG_TIROCINANTE (
    ID_COM_T_CATEG_TIROCINANTE NUMBER NOT NULL,
    DESCR_CATEG_TIROCINANTE VARCHAR2(100) NOT NULL,
    COD_CATEG_TIROCINANTE_MIN VARCHAR2(2) NOT NULL,
    DT_INIZIO DATE NOT NULL,
    DT_FINE DATE,
    PRIMARY KEY (ID_COM_T_CATEG_TIROCINANTE)
);

CREATE UNIQUE INDEX PK_COM_T_CATEG_TIROCINANTE ON COM_T_CATEG_TIROCINANTE (ID_COM_T_CATEG_TIROCINANTE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_R_PERSONALIZ_PROVINCE"                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_R_PERSONALIZ_PROVINCE (
    PV CHAR(2) NOT NULL,
    ID_COM_T_PROVINCIA NUMBER(5),
    PRIMARY KEY (PV)
);

CREATE UNIQUE INDEX PK_COM_R_PERSONALIZ_PROVINCE ON COM_R_PERSONALIZ_PROVINCE (PV ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TIPOLOGIA_RAPPORTO"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TIPOLOGIA_RAPPORTO (
    ID_COM_T_TIPOLOGIA_RAPPORTO VARCHAR2(1) NOT NULL,
    DS_COM_T_TIPOLOGIA_RAPPORTO VARCHAR2(100),
    PRIMARY KEY (ID_COM_T_TIPOLOGIA_RAPPORTO)
);

CREATE UNIQUE INDEX PK_COM_T_TIPOLOGIA_RAPPORTO ON COM_T_TIPOLOGIA_RAPPORTO (ID_COM_T_TIPOLOGIA_RAPPORTO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_UPL_DOCUMENTI"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_UPL_DOCUMENTI (
    ID_COM_D_UPL_DOCUMENTI NUMBER(7) NOT NULL,
    MITTENTE_CF VARCHAR2(16),
    MITTENTE_NOME VARCHAR2(100),
    MITTENTE_EMAIL VARCHAR2(60),
    DATA_RICEZIONE DATE,
    DATA_ELAB DATE,
    TOT_COM_ELAB NUMBER(5),
    TOT_COM_SCAR NUMBER(5),
    TOT_COM_WARN NUMBER(5),
    MITTENTE_COGNOME VARCHAR2(100),
    VERIFICA CHAR(2) DEFAULT 'NULL',
    DATA_VERIFICA DATE,
    PV VARCHAR2(2),
    NOME_FILE_UPLOAD VARCHAR2(300),
    DS_RUOLO_OPERATORE VARCHAR2(50),
    STATO_ELABORAZIONE CHAR(1) DEFAULT 'E',
    CRC_UPLOAD NUMBER,
    DT_RICHIESTA_ELABORAZIONE DATE,
    FLG_VERIFICA CHAR(1),
    CF_AZIENDA VARCHAR2(16),
    PRIMARY KEY (ID_COM_D_UPL_DOCUMENTI)
);

CREATE UNIQUE INDEX PK_COM_D_UPL_DOCUMENTI ON COM_D_UPL_DOCUMENTI (ID_COM_D_UPL_DOCUMENTI ASC);

CREATE INDEX TMP_DUPDOC_DRE_SE_ICDUD ON COM_D_UPL_DOCUMENTI (DT_RICHIESTA_ELABORAZIONE ASC,STATO_ELABORAZIONE ASC,ID_COM_D_UPL_DOCUMENTI ASC);

COMMENT ON COLUMN COM_D_UPL_DOCUMENTI.FLG_VERIFICA IS 'S=File solo verificato N=File processato';

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_GRADO_CONTRATTUALE"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_GRADO_CONTRATTUALE (
    ID_COM_T_GRADO_CONTRATTUALE NUMBER(5) NOT NULL,
    DS_COM_T_GRADO_CONTRATTUALE VARCHAR2(30),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    COD_GRADO_CONTRATTUALE VARCHAR2(2),
    PRIMARY KEY (ID_COM_T_GRADO_CONTRATTUALE)
);

CREATE UNIQUE INDEX PK_COM_T_GRADO_CONTRATTUALE ON COM_T_GRADO_CONTRATTUALE (ID_COM_T_GRADO_CONTRATTUALE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "PERSONALIZZAZIONE"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE PERSONALIZZAZIONE (
    PV CHAR(2) NOT NULL,
    DESCRIZIONE VARCHAR2(40) NOT NULL,
    DIR_IMAGES VARCHAR2(100),
    DIR_CSS VARCHAR2(100),
    MAIL_SERVER_NAME VARCHAR2(50),
    USER_SERVER_NAME VARCHAR2(50),
    TIPO_PROTOCOLLO CHAR(1),
    DIR_PDF VARCHAR2(100),
    ID_ENTE VARCHAR2(20),
    C_UTENTE VARCHAR2(20),
    URL_SITO_PV VARCHAR2(100),
    ID_AAO_DESTINATARIO VARCHAR2(20),
    DEN_DESTINATARIO VARCHAR2(100),
    TIPO_DESTINATARIO CHAR(1),
    URL_SITO_LAVORO VARCHAR2(100),
    USER_MAIL_NAME CHAR(50),
    USER_MAIL_PSW CHAR(16),
    ABILITA_SCELTA_INOLTRO_INPS CHAR(1),
    ISTANZA_DB CHAR(1),
    ABILITA_SCELTA_INOLTRO_INAIL CHAR(1),
    MAX_NUMBER_OF_LINES CHAR(4),
    MAX_RECOVERY_NUMBER CHAR(4),
    LOG_ENABLED CHAR(1),
    LINK_COMMAX_ENABLED CHAR(1),
    PRIMARY KEY (PV)
);

CREATE UNIQUE INDEX PK_PERSONALIZZAZIONE ON PERSONALIZZAZIONE (PV ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_ANAGRAFICA_AZI_ACCENT"                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_ANAGRAFICA_AZI_ACCENT (
    CODICE_FISCALE VARCHAR2(100) NOT NULL,
    ID_COM_D_ANAGRAFICA_AZI_ACCENT NUMBER(9) NOT NULL,
    DT_INIZIO DATE NOT NULL,
    DT_FINE DATE,
    DS_DENOMINAZIONE VARCHAR2(100),
    PRIMARY KEY (ID_COM_D_ANAGRAFICA_AZI_ACCENT)
);

CREATE UNIQUE INDEX PK_COM_D_ANAGRAFICA_AZI_ACCENT ON COM_D_ANAGRAFICA_AZI_ACCENT (ID_COM_D_ANAGRAFICA_AZI_ACCENT ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_S_USER_ACCESS_LOG"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_S_USER_ACCESS_LOG (
    ID_COM_D_USER_ACCESS_LOG NUMBER(12) NOT NULL,
    DS_RUOLO VARCHAR2(50),
    FLG_TROVATO_SU_AAEP CHAR(1) DEFAULT 'N',
    DS_NOME VARCHAR2(50),
    DS_COGNOME VARCHAR2(50),
    CF_UTENTE VARCHAR2(16),
    DT_EVENTO DATE DEFAULT sysdate,
    PRIMARY KEY (ID_COM_D_USER_ACCESS_LOG)
);

CREATE INDEX IE_COM_S_USER_ACCESS_LOG_01 ON COM_S_USER_ACCESS_LOG (CF_UTENTE ASC);

CREATE INDEX IE_COM_S_USER_ACCESS_LOG_02 ON COM_S_USER_ACCESS_LOG (DT_EVENTO ASC);

CREATE UNIQUE INDEX PK_COM_S_USER_ACCESS_LOG ON COM_S_USER_ACCESS_LOG (ID_COM_D_USER_ACCESS_LOG ASC);

COMMENT ON TABLE COM_S_USER_ACCESS_LOG IS 'Tabella di storico degli accessi';

COMMENT ON COLUMN COM_S_USER_ACCESS_LOG.ID_COM_D_USER_ACCESS_LOG IS 'Corrisponde esattamente alla primary key che era presente sulla COM_D_USER_ACCESS_LOG';

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_DELEGATO_IRIDE"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_DELEGATO_IRIDE (
    ID_COM_D_DELEGATO_IRIDE NUMBER(7) NOT NULL,
    CF_DELEGATO VARCHAR2(16) NOT NULL,
    TIPO_ANAGRAFICA CHAR(1) NOT NULL,
    CHK_STATO_ABILITAZIONE_IRIDE CHAR(1),
    DT_STATO_ABILITAZIONE_IRIDE DATE,
    DT_INVIO_ABILITAZIONE_IRIDE DATE,
    FLG_INVIO_ABILITAZIONE_IRIDE CHAR(1),
    CHK_TIPOLOGIA_RICHIESTA CHAR(1),
    DS_NOTE_ABILITAZIONE VARCHAR2(100),
    TELEFONO VARCHAR2(15),
    EMAIL VARCHAR2(50) NOT NULL,
    DS_DOCUMENTO VARCHAR2(100),
    DT_NASCITA DATE,
    PRIMARY KEY (ID_COM_D_DELEGATO_IRIDE),
    CHECK (CHK_STATO_ABILITAZIONE_IRIDE IN ('A', 'R')),
    CHECK (CHK_TIPOLOGIA_RICHIESTA IN ('A', 'D'))
);

CREATE UNIQUE INDEX PK_COM_D_DELEGATO_IRIDE ON COM_D_DELEGATO_IRIDE (ID_COM_D_DELEGATO_IRIDE ASC);

CREATE UNIQUE INDEX AK_COM_D_DELEGATO_IRIDE_01 ON COM_D_DELEGATO_IRIDE (TIPO_ANAGRAFICA ASC,CF_DELEGATO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_WARNING"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_WARNING (
    ID_COM_D_WARNING NUMBER(5) NOT NULL,
    ID_COM_D_UPL_DOCUMENTI NUMBER(7),
    CF_AZIENDA_CF_LAVORATORE CHAR(18),
    TIPO_WARNING VARCHAR2(2),
    IDENTIFICATIVO_ANNO_NUM CHAR(18),
    PRIMARY KEY (ID_COM_D_WARNING)
);

CREATE UNIQUE INDEX PK_COM_D_WARNING ON COM_D_WARNING (ID_COM_D_WARNING ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_COMUNICAZIONE"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_COMUNICAZIONE (
    ID_COM_D_COMUNICAZIONE NUMBER(9) NOT NULL,
    ID_COM_T_TIPO_PROVENIENZA NUMBER(1),
    ID_COM_T_TIPO_COMUNICAZIONE_TU NUMBER(5),
    ID_COM_T_CPI NUMBER(5),
    ID_COM_TIPO_SOGGETTO_ABILITATO NUMBER(5),
    ID_COM_T_STATO_COMUNICAZIONE NUMBER(5),
    DT_INVIO DATE,
    ID_COM_T_TIPO_TRACCIATO VARCHAR2(2),
    ID_COM_D_DELEGATO NUMBER(9),
    ID_COM_T_TIPO_COMUNICAZIONE VARCHAR2(3),
    ID_COM_T_TIPO_ACQUISIZIONE NUMBER(1),
    ID_COM_TU_PRECEDENTE_ANNULLO NUMBER(9),
    ID_COM_TU_DA_RETTIFICARE NUMBER(9),
    DT_INVIO_MINISTERO DATE,
    ID_COM_T_TIPO_TRASFERIMENTO NUMBER(5),
    CODICE_FISCALE_SOGGETTO VARCHAR2(16),
    CODICE_COMUNICAZIONE_REG VARCHAR2(16),
    CODICE_COMUN_REG_PREC VARCHAR2(16),
    DS_MOTIVO_URGENZA VARCHAR2(100),
    FLG_INVIO_MINISTERO CHAR(1),
    FLG_COM_DAT_LAV CHAR(1),
    FLG_COMUN_SEG_URGENZA CHAR(1),
    ANNO_DATA_ORA_REGIONALE VARCHAR2(14),
    EMAIL VARCHAR2(80),
    DT_PROTOCOLLO DATE,
    NUM_PROT_COM_RETTIFICA NUMBER(7),
    DT_TIMBRO_POSTALE DATE,
    ANNO_PROT_RETTIFICA NUMBER(4),
    DS_CAUSA_FORZAMAGGIORE VARCHAR2(100),
    NUM_PROT_COM_URGENZA NUMBER(7),
    ANNO_PROT_URGENZA NUMBER(4),
    CODICE_ENTE VARCHAR2(16),
    CF_LAV_C_ASSUNZIONE VARCHAR2(16),
    FLG_CAUSA_FORZAMAGGIORE CHAR(1),
    NUM_PROT_COM NUMBER(7),
    ANNO_PROT_COM NUMBER(4),
    NUM_AGENZ_SOMMINISTR NUMBER(11),
    NUM_PROT_MSG_POSTA NUMBER(7),
    ANNO_PROT_MSG_POSTA NUMBER(4),
    DT_TRASFERIMENTO_VAR_DATORI DATE,
    DT_VERIFICA DATE,
    DT_ASS_C_ASSUNZIONE DATE,
    FLG_RETTIFICA CHAR(1),
    FLG_SOMMIN CHAR(1),
    VERIFICA VARCHAR2(2),
    ID_USER_INSERT VARCHAR2(16) NOT NULL,
    DT_INSERT DATE NOT NULL,
    ID_USER_ULT_MOD VARCHAR2(16),
    DT_ULT_MOD DATE,
    ID_COM_T_TIPO_SOMMINISTRAZIONE NUMBER(2),
    CF_IMPRESA VARCHAR2(100),
    ID_COM_T_PROVINCIA NUMBER(5),
    ID_COM_D_COMUNIC_PRECEDENTE NUMBER(9),
    EMAILSOGGETTO VARCHAR2(80),
    DT_FINE_AFFITTO_RAMO DATE,
    FLG_MULTI_LAV VARCHAR2(1),
    ID_COM_D_COMUNICAZ_GRUPPO NUMBER(9),
    DT_RIFERIMENTO DATE,
    ID_RAPPORTO_DI_LAVORO NUMBER,
    FLG_CURRENT_RECORD VARCHAR2(1),
    PRIMARY KEY (ID_COM_D_COMUNICAZIONE),
    CHECK (FLG_MULTI_LAV IN ('S', 'N')),
    CHECK (FLG_CURRENT_RECORD = 'S')
);

CREATE UNIQUE INDEX AK_COM_D_COMUNICAZIONE_01 ON COM_D_COMUNICAZIONE (CASE "FLG_CURRENT_RECORD" WHEN 'S' THEN "ID_RAPPORTO_DI_LAVORO" ELSE NULL END  ASC);

CREATE INDEX IDX_CODICE_COMUNICAZIONE_REG ON COM_D_COMUNICAZIONE (CODICE_COMUNICAZIONE_REG ASC);

CREATE INDEX IDX_COD_FISC_SOGG ON COM_D_COMUNICAZIONE (CODICE_FISCALE_SOGGETTO ASC);

CREATE INDEX IDX_PRT ON COM_D_COMUNICAZIONE (NUM_PROT_COM ASC,ANNO_PROT_COM ASC);

CREATE INDEX IE_COM_D_COMUNICAZIONE_12 ON COM_D_COMUNICAZIONE (ID_COM_D_COMUNICAZ_GRUPPO ASC);

CREATE INDEX IE_COM_D_COMUNICAZIONE_13 ON COM_D_COMUNICAZIONE (DT_INVIO ASC);

CREATE INDEX IE_COM_D_COMUNICAZIONE_14 ON COM_D_COMUNICAZIONE (DT_INSERT ASC);

CREATE INDEX IE_COM_D_COMUNICAZIONE_15 ON COM_D_COMUNICAZIONE (ID_COM_T_STATO_COMUNICAZIONE ASC);

CREATE INDEX IE_COM_D_COMUNICAZIONE_16 ON COM_D_COMUNICAZIONE (ID_COM_T_TIPO_COMUNICAZIONE ASC);

CREATE INDEX IE_COM_D_COMUNICAZIONE_17 ON COM_D_COMUNICAZIONE (ID_COM_T_TIPO_COMUNICAZIONE_TU ASC);

CREATE INDEX IE_COM_D_COMUNICAZIONE_18 ON COM_D_COMUNICAZIONE (CF_IMPRESA ASC);

CREATE INDEX IE_COM_D_COMUNICAZIONE_19 ON COM_D_COMUNICAZIONE (CODICE_ENTE ASC,ID_COM_T_STATO_COMUNICAZIONE ASC);

CREATE INDEX IE_COM_D_COMUNICAZIONE_20 ON COM_D_COMUNICAZIONE (ID_COM_T_STATO_COMUNICAZIONE ASC,CODICE_ENTE ASC);

CREATE INDEX IE_COM_D_COMUNICAZIONE_21 ON COM_D_COMUNICAZIONE (DT_ULT_MOD ASC);

CREATE INDEX IE_COM_D_COMUNICAZIONE_22 ON COM_D_COMUNICAZIONE (CODICE_ENTE ASC,ID_COM_T_STATO_COMUNICAZIONE ASC,DT_ULT_MOD ASC,DT_INSERT ASC);

CREATE INDEX IE_COM_D_COMUNICAZIONE_23 ON COM_D_COMUNICAZIONE (ID_USER_INSERT ASC);

CREATE INDEX IE_COM_D_COMUNICAZIONE_24 ON COM_D_COMUNICAZIONE (CODICE_ENTE ASC);

CREATE INDEX IE_COM_D_COMUNICAZIONE_25 ON COM_D_COMUNICAZIONE (ID_COM_T_CPI ASC);

CREATE INDEX IE_COM_D_COMUNICAZIONE_26 ON COM_D_COMUNICAZIONE (DT_TIMBRO_POSTALE ASC);

CREATE INDEX IE_COM_D_COMUNICAZIONE_27 ON COM_D_COMUNICAZIONE (ID_RAPPORTO_DI_LAVORO ASC);

CREATE INDEX IE_COM_D_COMUNICAZIONE_28 ON COM_D_COMUNICAZIONE (CODICE_COMUN_REG_PREC ASC);

CREATE UNIQUE INDEX PK_COM_D_COMUNICAZIONE ON COM_D_COMUNICAZIONE (ID_COM_D_COMUNICAZIONE ASC);

COMMENT ON COLUMN COM_D_COMUNICAZIONE.NUM_AGENZ_SOMMINISTR IS 'DA NON UTILIZZARE; quello valido è COM_D_DATORE.NUM_AGENZIA_SOMMIN';

COMMENT ON COLUMN COM_D_COMUNICAZIONE.EMAILSOGGETTO IS 'Campo per l''e-mail in caso di operatore CPI, quando nella maschera dei dati generali l''utente indica "Si effettua la comunicazione per il Datore di Lavoro = no"';

COMMENT ON COLUMN COM_D_COMUNICAZIONE.DT_RIFERIMENTO IS 'Rappresenta la data di riferimento della comunicazione, utile per poter ordinare le comunicazioni di un dato rapporto di lavoro';

COMMENT ON COLUMN COM_D_COMUNICAZIONE.ID_RAPPORTO_DI_LAVORO IS 'Identifica il rapporto di lavoro legato alla comunicazione; da valorizzare tramite la sequence SEQ_ID_RAPPORTO_DI_LAVORO';

COMMENT ON COLUMN COM_D_COMUNICAZIONE.FLG_CURRENT_RECORD IS 'Identifica l''ultima comnunicazione di un rapporto di lavoro; per ogni rapporto di lavoro ï¿¿ possibile avere un solo FLG_CURRENT_RECORD = ''S''';

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_ELABORATI_OK"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_ELABORATI_OK (
    PROG_COM NUMBER(4) NOT NULL,
    NRO_PROT_COM VARCHAR2(7) NOT NULL,
    ANNO_PROT_COM NUMBER(4) NOT NULL,
    CF_IMPRESA VARCHAR2(100),
    CF_LAVORATORE VARCHAR2(16),
    ID_COM_D_UPL_DOCUMENTI NUMBER(7),
    TIPO_COM VARCHAR2(3),
    F_TIPO_ACQUISIZ NUMBER(1),
    PV CHAR(2) NOT NULL,
    CODICE_COMUNICAZIONE_REG VARCHAR2(16),
    NOME_FILE_XML VARCHAR2(300)
);

CREATE INDEX TMP_DELAOK_IDCDUD ON COM_D_ELABORATI_OK (ID_COM_D_UPL_DOCUMENTI ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TOPONIMO"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TOPONIMO (
    ID_COM_T_TOPONIMO VARCHAR2(3) NOT NULL,
    DS_COM_T_TOPONIMO VARCHAR2(30) NOT NULL,
    ORDINAMENTO NUMBER(3),
    PRIMARY KEY (ID_COM_T_TOPONIMO)
);

CREATE UNIQUE INDEX PK_COM_T_TOPONIMO ON COM_T_TOPONIMO (ID_COM_T_TOPONIMO ASC);

COMMENT ON TABLE COM_T_TOPONIMO IS 'Elenco dei toponimi con ordinamento; valori presi da SILP';

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_COMUNE"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_COMUNE (
    ID_COM_T_COMUNE NUMBER(5) NOT NULL,
    COD_COMUNE_MIN VARCHAR2(4),
    DS_COM_T_COMUNE VARCHAR2(100),
    COD_ISTAT VARCHAR2(6),
    ID_COM_T_PROVINCIA NUMBER(5),
    COD_INPS VARCHAR2(6),
    COD_CAP VARCHAR2(5),
    COD_PREFISSO VARCHAR2(5),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    ID_COM_T_CPI NUMBER(5),
    PRIMARY KEY (ID_COM_T_COMUNE)
);

CREATE INDEX IE_COM_T_COMUNE_01 ON COM_T_COMUNE (COD_COMUNE_MIN ASC);

CREATE UNIQUE INDEX PK_COM_T_COMUNE ON COM_T_COMUNE (ID_COM_T_COMUNE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_LIVELLO_RETRIBUZIONE"                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_LIVELLO_RETRIBUZIONE (
    ID_COM_T_LIVELLO_RETRIBUZIONE NUMBER NOT NULL,
    ID_COM_T_CCNL NUMBER(5) NOT NULL,
    COD_LIVELLO VARCHAR2(6) NOT NULL,
    COD_TIPO_LIVELLO VARCHAR2(4) NOT NULL,
    DES_LIVELLO VARCHAR2(200) NOT NULL,
    MENSILITA NUMBER NOT NULL,
    DIVISORE_ORARIO NUMBER NOT NULL,
    LORDO_MENSILE NUMBER NOT NULL,
    ORDINAMENTO NUMBER NOT NULL,
    DT_INIZIO DATE NOT NULL,
    DT_FINE DATE,
    PRIMARY KEY (ID_COM_T_LIVELLO_RETRIBUZIONE)
);

CREATE UNIQUE INDEX AK_COM_T_LIVELLO_RETRIBUZ_01 ON COM_T_LIVELLO_RETRIBUZIONE (ID_COM_T_CCNL ASC,COD_LIVELLO ASC);

CREATE INDEX IE_COM_T_LIVELLO_RETRIBUZ_01 ON COM_T_LIVELLO_RETRIBUZIONE (COD_LIVELLO ASC);

CREATE UNIQUE INDEX PK_COM_T_LIVELLO_RETRIBUZIONE ON COM_T_LIVELLO_RETRIBUZIONE (ID_COM_T_LIVELLO_RETRIBUZIONE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_DUPLICA_GRUPPO_LOG"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_DUPLICA_GRUPPO_LOG (
    ID_COM_D_DUPLICA_GRUPPO_LOG NUMBER(9) NOT NULL,
    MESSAGGIO VARCHAR2(4000) NOT NULL,
    DT_INSERT DATE NOT NULL,
    ID_COM_D_COMUNICAZ_GRUPPO NUMBER(9) NOT NULL,
    PRIMARY KEY (ID_COM_D_DUPLICA_GRUPPO_LOG)
);

CREATE INDEX IE_COM_D_DUPLICA_GRUPPO_LOG_01 ON COM_D_DUPLICA_GRUPPO_LOG (ID_COM_D_COMUNICAZ_GRUPPO ASC);

CREATE UNIQUE INDEX PK_COM_D_DUPLICA_GRUPPO_LOG ON COM_D_DUPLICA_GRUPPO_LOG (ID_COM_D_DUPLICA_GRUPPO_LOG ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_VARIAZIONE_SOMM"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_VARIAZIONE_SOMM (
    ID_COM_T_VARIAZIONE_SOMM NUMBER(3) NOT NULL,
    ID_COM_T_TIPO_SOMMINISTRAZIONE NUMBER(2),
    ID_COM_T_TIPO_COMUNICAZIONE VARCHAR2(3),
    COD_TIPO_VARIAZIONE_MIN VARCHAR2(4) NOT NULL,
    DS_VARIAZIONE VARCHAR2(100) NOT NULL,
    DT_INIZIO DATE NOT NULL,
    DT_FINE DATE,
    PRIMARY KEY (ID_COM_T_VARIAZIONE_SOMM)
);

CREATE UNIQUE INDEX AK_COM_T_VARIAZIONE_SOMM_01 ON COM_T_VARIAZIONE_SOMM (ID_COM_T_TIPO_COMUNICAZIONE ASC,ID_COM_T_TIPO_SOMMINISTRAZIONE ASC);

CREATE UNIQUE INDEX AK_COM_T_VARIAZIONE_SOMM_02 ON COM_T_VARIAZIONE_SOMM (COD_TIPO_VARIAZIONE_MIN ASC);

CREATE UNIQUE INDEX PK_COM_T_VARIAZIONE_SOMM ON COM_T_VARIAZIONE_SOMM (ID_COM_T_VARIAZIONE_SOMM ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_R_COMUNICAZ_SOMMINISTR"                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_R_COMUNICAZ_SOMMINISTR (
    ID_COM_T_TIPO_SOMMINISTRAZIONE NUMBER(2) NOT NULL,
    ID_COM_T_TIPO_COMUNICAZIONE VARCHAR2(3) NOT NULL,
    PRIMARY KEY (ID_COM_T_TIPO_SOMMINISTRAZIONE, ID_COM_T_TIPO_COMUNICAZIONE)
);

CREATE UNIQUE INDEX PK_COM_R_COMUNICAZ_SOMMINISTR ON COM_R_COMUNICAZ_SOMMINISTR (ID_COM_T_TIPO_SOMMINISTRAZIONE ASC,ID_COM_T_TIPO_COMUNICAZIONE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_SEDE_LAVORO"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_SEDE_LAVORO (
    ID_COM_D_SEDE_LAVORO NUMBER(9) NOT NULL,
    ID_COM_T_COMUNE NUMBER(5),
    INDIRIZZO VARCHAR2(100),
    TELEFONO VARCHAR2(15),
    ID_COM_T_STATI_ESTERI NUMBER(5),
    FAX VARCHAR2(15),
    EMAIL VARCHAR2(80),
    FLG_SEDE_LEGALE VARCHAR2(1),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    COD_CAP VARCHAR2(5),
    ID_SEDE_SILP NUMBER,
    FLG_COMUNE_SILP_VARIATO VARCHAR2(1),
    FLG_INDIRIZZO_SILP_VARIATO VARCHAR2(1),
    PRIMARY KEY (ID_COM_D_SEDE_LAVORO)
);

CREATE UNIQUE INDEX PK_COM_D_SEDE_LAVORO ON COM_D_SEDE_LAVORO (ID_COM_D_SEDE_LAVORO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_LAVORATORE"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_LAVORATORE (
    ID_COM_D_LAVORATORE NUMBER(9) NOT NULL,
    CODICE_FISCALE VARCHAR2(16) NOT NULL,
    COGNOME VARCHAR2(50) NOT NULL,
    NOME VARCHAR2(50) NOT NULL,
    SESSO VARCHAR2(1),
    DT_NASCITA DATE,
    ID_COMUNE_NASC NUMBER(5),
    ID_COMUNE_RES NUMBER(5),
    ID_COMUNE_DOM NUMBER(5),
    ID_COM_T_STATUS_STRANIERO NUMBER(5),
    ID_COM_T_LIVELLO_STUDIO NUMBER(5),
    ID_COM_T_MOTIVO_PERMESSO NUMBER(5),
    ID_COM_T_CITTADINANZA NUMBER(5),
    DS_INDIRIZZO_RES VARCHAR2(100),
    DS_INDIRIZZO_DOM VARCHAR2(100),
    NUMERO_DOCUMENTO VARCHAR2(15),
    DT_SCADENZA_PERMESSO_SOGG DATE,
    STATO_CIVILE CHAR(2),
    TEL_DOM VARCHAR2(15),
    COD_CAP_RES VARCHAR2(5),
    COD_CAP_DOM VARCHAR2(5),
    ID_COM_T_STATI_ESTERI_NASC NUMBER(5),
    ID_COM_T_STATI_ESTERI_RES NUMBER(5),
    ID_COM_T_STATI_ESTERI_DOM NUMBER(5),
    ID_LAVORATORE_SILP NUMBER,
    FLG_SIST_ALLOGGIATIVA VARCHAR2(1),
    FLG_RIMBORSO_RIMPATRIO VARCHAR2(1),
    ID_QUESTURA_RILASCIO_PERM_SOGG NUMBER(3),
    PRIMARY KEY (ID_COM_D_LAVORATORE),
    CHECK (FLG_SIST_ALLOGGIATIVA IN ('S', 'N')),
    CHECK (FLG_RIMBORSO_RIMPATRIO IN ('S','N')),
    CHECK (COGNOME = UPPER(TRIM(COGNOME)) AND NOME = UPPER(TRIM(NOME)))
);

CREATE INDEX IE_COM_D_LAVORATORE_01 ON COM_D_LAVORATORE (COGNOME ASC,NOME ASC);

CREATE UNIQUE INDEX PK_COM_D_LAVORATORE ON COM_D_LAVORATORE (ID_COM_D_LAVORATORE ASC);

CREATE INDEX TMP_CDL_CF_ICDL ON COM_D_LAVORATORE (CODICE_FISCALE ASC,ID_COM_D_LAVORATORE ASC);

CREATE INDEX TMP_LAVORAT_FNCOGN ON COM_D_LAVORATORE (UPPER("COGNOME") ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TIPO_CONTRATTI"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TIPO_CONTRATTI (
    ID_COM_T_TIPO_CONTRATTO NUMBER(5) NOT NULL,
    DS_COM_T_TIPO_CONTRATTO VARCHAR2(200),
    STATUS VARCHAR2(1),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    COD_TIPO_CONTRATTO_MIN VARCHAR2(7),
    TIPO VARCHAR2(3),
    FLG_FORMA VARCHAR2(1),
    TIPOLOGIA_RAPPORTO VARCHAR2(1),
    PRIMARY KEY (ID_COM_T_TIPO_CONTRATTO)
);

CREATE UNIQUE INDEX PK_COM_T_TIPO_CONTRATTI ON COM_T_TIPO_CONTRATTI (ID_COM_T_TIPO_CONTRATTO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_DATORE"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_DATORE (
    ID_COM_D_DATORE NUMBER(9) NOT NULL,
    CODICE_FISCALE VARCHAR2(100),
    ID_COM_T_ATECOFIN NUMBER(5),
    DS_DENOMINAZIONE_DATORE VARCHAR2(300),
    NUM_AGENZIA_SOMMIN VARCHAR2(11),
    C_NATURA_GIURIDICA VARCHAR2(3) NOT NULL,
    NUM_CONTR_SOMM NUMBER(9),
    DS_VARIAZIONE_RAG_SOCIALE VARCHAR2(100),
    MATRICOLA_INPS VARCHAR2(10),
    NUMERO_ISCRIZIONE_ALBO VARCHAR2(20),
    FLG_AZ_ARTIGIANA VARCHAR2(1),
    PARTITA_IVA VARCHAR2(11),
    FLG_NO_AAEP CHAR(1),
    PAT_INAIL VARCHAR2(10),
    DS_COGNOME VARCHAR2(50),
    DS_NOME VARCHAR2(50),
    ID_AZIENDA_SILP NUMBER,
    FLG_DENOM_SILP_VARIATA VARCHAR2(1),
    FLG_PUB_AMM CHAR(1),
    FLG_UTIL_ESTERA VARCHAR2(1),
    DT_INIZIO_CONTRATTO_SOM DATE,
    DT_FINE_CONTRATTO_SOM DATE,
    PRIMARY KEY (ID_COM_D_DATORE),
    CHECK (FLG_UTIL_ESTERA IN ('S', 'N'))
);

CREATE INDEX IE_COM_D_DATORE_01 ON COM_D_DATORE (TRIM("CODICE_FISCALE") ASC);

CREATE UNIQUE INDEX PK_COM_D_DATORE ON COM_D_DATORE (ID_COM_D_DATORE ASC);

CREATE INDEX TMP_DDAT_CF_ICDD ON COM_D_DATORE (CODICE_FISCALE ASC,ID_COM_D_DATORE ASC);

COMMENT ON COLUMN COM_D_DATORE.DS_COGNOME IS 'Cognome datore in caso di datore di lavoro domestico';

COMMENT ON COLUMN COM_D_DATORE.DS_NOME IS 'Nome datore in caso di datore di lavoro domestico';

/* ---------------------------------------------------------------------- */
/* Add table "COM_R_SEDE_LAVORO_LAVORATORE"                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_R_SEDE_LAVORO_LAVORATORE (
    ID_COM_D_SEDE_LAVORO NUMBER(9) NOT NULL,
    ID_COM_D_LAVORATORE NUMBER(9) NOT NULL,
    PRIMARY KEY (ID_COM_D_SEDE_LAVORO, ID_COM_D_LAVORATORE)
);

CREATE UNIQUE INDEX PK_COM_R_SEDE_LAVORO_LAVORATOR ON COM_R_SEDE_LAVORO_LAVORATORE (ID_COM_D_SEDE_LAVORO ASC,ID_COM_D_LAVORATORE ASC);

CREATE INDEX TMP_CRSLL_ ON COM_R_SEDE_LAVORO_LAVORATORE (ID_COM_D_LAVORATORE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "ANAGRAFICA_ENTE"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE ANAGRAFICA_ENTE (
    C_ENTE VARCHAR2(10) NOT NULL,
    PV CHAR(2) NOT NULL,
    DESCRIZIONE VARCHAR2(100) NOT NULL,
    INDIRIZZO VARCHAR2(40) NOT NULL,
    C_COMUNE CHAR(4) NOT NULL,
    CAP_DOM CHAR(5),
    TEL VARCHAR2(15),
    FAX VARCHAR2(15),
    MAIL VARCHAR2(50),
    ID_USER_INSERT VARCHAR2(16) NOT NULL,
    DT_INSERT DATE NOT NULL,
    ID_USER_ULT_MOD VARCHAR2(16),
    DT_ULT_MOD DATE,
    TIPO_ENTE CHAR(1) NOT NULL,
    PRIMARY KEY (C_ENTE, PV)
);

CREATE UNIQUE INDEX PK_ANAGRAFICA_ENTE ON ANAGRAFICA_ENTE (C_ENTE ASC,PV ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TIPO_CONTR_PERIODI"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TIPO_CONTR_PERIODI (
    ID_COM_T_TIPO_CONTR_PERIODI NUMBER NOT NULL,
    ID_COM_T_TIPO_CONTRATTO NUMBER(5),
    DT_NON_VALID_DA DATE,
    DT_NON_VALID_A DATE,
    PRIMARY KEY (ID_COM_T_TIPO_CONTR_PERIODI)
);

CREATE UNIQUE INDEX PK_COM_T_TIPO_CONTR_PERIODI ON COM_T_TIPO_CONTR_PERIODI (ID_COM_T_TIPO_CONTR_PERIODI ASC);

/* ---------------------------------------------------------------------- */
/* Add table "PARAMETRI_TIPO_DELEGA"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE PARAMETRI_TIPO_DELEGA (
    PV CHAR(2) NOT NULL,
    TIPO CHAR(1) NOT NULL,
    MITTENTE VARCHAR2(80),
    SUBJECT VARCHAR2(80),
    TESTO_MAIL VARCHAR2(256),
    PROT_ORIGINE VARCHAR2(15) NOT NULL,
    PROT_TIPO_DOC NUMBER(3) NOT NULL,
    TXT_DELEGA VARCHAR2(400),
    TIPO_DOC_RUPAR VARCHAR2(18),
    PRIMARY KEY (PV, TIPO)
);

CREATE UNIQUE INDEX PK_PARAMETRI_TIPO_DELEGA ON PARAMETRI_TIPO_DELEGA (PV ASC,TIPO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_R_DEP_COMMAX_COMUNIC"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_R_DEP_COMMAX_COMUNIC (
    ID_DEPOSITO_COMMAX NUMBER NOT NULL,
    PROG_XML NUMBER NOT NULL,
    ID_COM_D_UPL_DOCUMENTI NUMBER(7) NOT NULL,
    ID_COM_D_COMUNICAZIONE NUMBER(9) NOT NULL,
    CODICE_COMUNICAZIONE_REG VARCHAR2(16),
    FLG_PROT_GEST_CORRETTAMENTE CHAR(1),
    PRIMARY KEY (ID_DEPOSITO_COMMAX, PROG_XML, ID_COM_D_UPL_DOCUMENTI, ID_COM_D_COMUNICAZIONE)
);

CREATE UNIQUE INDEX PK_COM_R_DEP_COMMAX_COMUNIC ON COM_R_DEP_COMMAX_COMUNIC (PROG_XML ASC,ID_DEPOSITO_COMMAX ASC,ID_COM_D_UPL_DOCUMENTI ASC,ID_COM_D_COMUNICAZIONE ASC);

CREATE INDEX TMP_CRDCC_ICDUD ON COM_R_DEP_COMMAX_COMUNIC (ID_COM_D_UPL_DOCUMENTI ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_IMPORT_ERRORE"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_IMPORT_ERRORE (
    ID_COM_D_IMPORT_ERRORE NUMBER NOT NULL,
    COD_COMUNICAZIONE_REG CHAR(16) NOT NULL,
    ID_COM_T_TIPO_TRACCIATO VARCHAR2(2) NOT NULL,
    ID_SPI_TRASMISSIONE NUMBER NOT NULL,
    DT_ELABORAZIONE DATE NOT NULL,
    DS_ERRORE VARCHAR2(4000) NOT NULL,
    TABELLA_DESTINAZIONE VARCHAR2(50),
    DATO_INPUT VARCHAR2(1000),
    PRIMARY KEY (ID_COM_D_IMPORT_ERRORE)
);

CREATE INDEX IE_COM_D_IMPORT_ERRORE_01 ON COM_D_IMPORT_ERRORE (ID_COM_T_TIPO_TRACCIATO ASC);

CREATE INDEX IE_COM_D_IMPORT_ERRORE_02 ON COM_D_IMPORT_ERRORE (ID_SPI_TRASMISSIONE ASC);

CREATE INDEX IE_COM_D_IMPORT_ERRORE_03 ON COM_D_IMPORT_ERRORE (COD_COMUNICAZIONE_REG ASC);

CREATE UNIQUE INDEX PK_COM_D_IMPORT_ERRORE ON COM_D_IMPORT_ERRORE (ID_COM_D_IMPORT_ERRORE ASC);

COMMENT ON COLUMN COM_D_IMPORT_ERRORE.COD_COMUNICAZIONE_REG IS 'Codice comunicazione della comunicazione che si sta importando';

COMMENT ON COLUMN COM_D_IMPORT_ERRORE.ID_SPI_TRASMISSIONE IS 'Identificativo della trasmissione di SPICOM';

COMMENT ON COLUMN COM_D_IMPORT_ERRORE.DT_ELABORAZIONE IS 'Data in cui si ï¿¿ tentato di importare la comunicazione';

COMMENT ON COLUMN COM_D_IMPORT_ERRORE.DS_ERRORE IS 'Descrizione dell''errore';

COMMENT ON COLUMN COM_D_IMPORT_ERRORE.TABELLA_DESTINAZIONE IS 'Tabella sui cui non ï¿¿ stato possibile salvare il dato';

COMMENT ON COLUMN COM_D_IMPORT_ERRORE.DATO_INPUT IS 'Dato di input che non ï¿¿ stato possibile salvare';

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_ANAGRAFICA_DATORE"                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_ANAGRAFICA_DATORE (
    ID_COM_D_ANAGRAFICA_DATORE NUMBER(9) NOT NULL,
    CODICE_FISCALE VARCHAR2(16) NOT NULL,
    ID_COM_T_ATECOFIN NUMBER(5),
    DS_DENOMINAZIONE_DATORE VARCHAR2(300) NOT NULL,
    NUM_AGENZIA_SOMMIN VARCHAR2(10),
    C_NATURA_GIURIDICA VARCHAR2(3) NOT NULL,
    NUMERO_ISCRIZIONE_ALBO VARCHAR2(20),
    DS_VARIAZIONE_RAG_SOCIALE VARCHAR2(100),
    MATRICOLA_INPS VARCHAR2(10),
    FLG_AZ_ARTIGIANA VARCHAR2(1),
    PARTITA_IVA VARCHAR2(11),
    FLG_NO_AAEP CHAR(1),
    PRIMARY KEY (ID_COM_D_ANAGRAFICA_DATORE)
);

CREATE UNIQUE INDEX PK_COM_D_ANAGRAFICA_DATORE ON COM_D_ANAGRAFICA_DATORE (ID_COM_D_ANAGRAFICA_DATORE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_FM_ERRORI"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_FM_ERRORI (
    ID_COM_D_FM_ERRORI NUMBER(5) NOT NULL,
    ID_COM_D_UPL_DOCUMENTI NUMBER(7),
    IDENTIFICATIVO_ANNO_NUM CHAR(18),
    NOME_FILE_XML_SCARTATO VARCHAR2(300),
    TIPO_SCARTO CHAR(18),
    PRIMARY KEY (ID_COM_D_FM_ERRORI)
);

CREATE UNIQUE INDEX PK_COM_D_FM_ERRORI ON COM_D_FM_ERRORI (ID_COM_D_FM_ERRORI ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_FZ_ERRORI"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_FZ_ERRORI (
    ID_COM_D_FZ_ERRORI NUMBER NOT NULL,
    ID_COM_D_UPL_DOCUMENTI NUMBER(7),
    IDENTIFICATIVO_ANNO_NUM CHAR(18),
    CF_AZIENDA_CF_LAVORATORE VARCHAR2(100),
    TIPO_SCARTO VARCHAR2(2),
    NOME_FILE_XML_SCARTATO VARCHAR2(300),
    PRIMARY KEY (ID_COM_D_FZ_ERRORI)
);

CREATE UNIQUE INDEX PK_COM_D_FZ_ERRORI ON COM_D_FZ_ERRORI (ID_COM_D_FZ_ERRORI ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_ANAGRAFICA_SEDE_LAVORO"                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_ANAGRAFICA_SEDE_LAVORO (
    ID_COM_D_ANAGRAFIC_SEDE_LAVORO NUMBER(9) NOT NULL,
    ID_COM_T_COMUNE NUMBER(5),
    INDIRIZZO VARCHAR2(100) NOT NULL,
    TELEFONO VARCHAR2(15),
    ID_COM_T_STATI_ESTERI NUMBER(5),
    FAX VARCHAR2(15),
    EMAIL VARCHAR2(80),
    FLG_SEDE_LEGALE VARCHAR2(1),
    DT_INIZIO DATE,
    DT_FINE DATE,
    DT_TMST DATE,
    COD_CAP VARCHAR2(5),
    PRIMARY KEY (ID_COM_D_ANAGRAFIC_SEDE_LAVORO)
);

CREATE UNIQUE INDEX PK_COM_D_ANAGRAFICA_SEDE_LAVOR ON COM_D_ANAGRAFICA_SEDE_LAVORO (ID_COM_D_ANAGRAFIC_SEDE_LAVORO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_TUTORE"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_TUTORE (
    ID_COM_D_TUTORE NUMBER(9) NOT NULL,
    ID_COM_T_GRADO_CONTRATTUALE NUMBER(5),
    CF_TUTORE VARCHAR2(16) NOT NULL,
    COGNOME VARCHAR2(30),
    NOME VARCHAR2(30),
    DT_NASCITA DATE,
    SESSO CHAR(1),
    DS_LIVELLO_INQUADRAMENTO VARCHAR2(10),
    FLG_ACCETTAZIONE CHAR(1),
    FLG_TITOLARE CHAR(1),
    DT_VISITA_MEDICA DATE,
    NUM_ANNI_ESPERIENZA NUMBER(2),
    ID_COM_ISTAT2001LIVELLO5 NUMBER(5),
    PRIMARY KEY (ID_COM_D_TUTORE)
);

CREATE UNIQUE INDEX PK_COM_D_TUTORE ON COM_D_TUTORE (ID_COM_D_TUTORE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_RAPPORTO"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_RAPPORTO (
    ID_COM_D_RAPPORTO NUMBER(9) NOT NULL,
    ID_COM_T_CATEG_LAV_ASS_OBBL NUMBER,
    ID_COM_T_TIPO_CONTRATTO NUMBER(5),
    ID_COM_T_ENTE_PREVIDENZIALE NUMBER(5),
    ID_COM_T_TIPO_ENTE_PROM_TIR NUMBER(5),
    ID_COM_T_CPI NUMBER(5),
    ID_COM_D_COMUNICAZIONE NUMBER(9),
    ID_COM_D_TUTORE NUMBER(9),
    ID_AZI_UTILIZZATRICE NUMBER(9),
    ID_DATORE_DISTACCATARIO NUMBER(9),
    ID_COM_ISTAT2001LIVELLO5 NUMBER(5),
    ID_COM_SEDE_LAVORO_PRECEDENTE NUMBER(9),
    ID_COM_T_CESSAZIONERL NUMBER(5),
    ID_COM_T_CCNL NUMBER(5),
    ID_COM_T_TIPO_ORARIO NUMBER(5),
    ID_COM_T_TRASFORMAZIONERL NUMBER(5),
    NUM_ORE_SETT_MED NUMBER(2),
    NUM_MATRICOLA_LAV VARCHAR2(10),
    NUM_AGENZ_SOMMINISTR NUMBER(10),
    NUM_INDENNITA_SOMM NUMBER(6),
    NUMERO_ATTO_LEGGE68 VARCHAR2(10),
    DT_APPROVAZIONE_CRT DATE,
    DT_INIZIO_RAPPORTO DATE,
    DT_FINE_RAPPORTO DATE,
    DT_FINE_PROROGA DATE,
    DT_TRASFORMAZIONE DATE,
    DT_CESSAZIONE DATE,
    DT_INIZIO_MISSIONE DATE,
    DT_FINE_MISSIONE DATE,
    DT_EVENTO DATE,
    DT_LEGGE68 DATE,
    DT_CRT DATE,
    CRT VARCHAR2(30),
    DS_SOGG_PROM_TIROCINIO VARCHAR2(100),
    DS_ATTIVITA VARCHAR2(100),
    DS_ATTIVITA_AGRICOLTURA VARCHAR2(100),
    FLG_TIROCINIO CHAR(1),
    FLG_SOCIO_LAVORATORE CHAR(1),
    PAT_INAIL VARCHAR2(10),
    LIVELLO_INQUADRAMENTO VARCHAR2(10),
    RETRIBUZIONE_COMPENSO NUMBER(9),
    GIORNATE_LAV_PREVISTE NUMBER(4),
    TIPO_LAVORAZIONE VARCHAR2(100),
    DS_VOCE_TARIFFA1 VARCHAR2(4),
    DS_VOCE_TARIFFA2 VARCHAR2(4),
    DS_VOCE_TARIFFA3 VARCHAR2(4),
    FLG_RISCHIO_SILIC_ASBE CHAR(1),
    FLG_SOMM_TEMPO_DET CHAR(1),
    CODICE_ENTE_PREV VARCHAR2(20),
    ID_COM_T_TIPO_ATTO_L68 VARCHAR2(1),
    FLG_CCNL_APPLICATO VARCHAR2(1),
    FLG_DISTACCO_PARZIALE VARCHAR2(1),
    FLG_DISTACCO_SU_AZI_ESTERA VARCHAR2(1),
    FLG_LAVORO_AGRICOLTURA VARCHAR2(1),
    ID_STORICO_RAPPORTO_SILP NUMBER,
    FLG_LAV_IN_MOBILITA VARCHAR2(1),
    FLG_LAV_STAGIONALE VARCHAR2(1),
    CF_SOGG_PROMOTORE_TIROCINIO VARCHAR2(16),
    DT_FINE_PERIODO_FORMATIVO DATE,
    FLG_PROSECUZIONE_DI_FATTO VARCHAR2(1),
    ID_COM_T_TIPOLOGIA_TIROCINIO NUMBER,
    ID_COM_T_CATEG_TIROCINANTE NUMBER,
    FLG_ASS_LEGGE_68_99 VARCHAR2(1),
    FLG_ASSUNZIONE_OBBLIGATORIA VARCHAR2(1),
    ID_COM_T_LIVELLO_RETRIBUZIONE NUMBER,
    PRIMARY KEY (ID_COM_D_RAPPORTO),
    CHECK (FLG_LAV_IN_MOBILITA IN ('S', 'N')),
    CHECK (FLG_LAV_STAGIONALE IN ('S', 'N')),
    CHECK (FLG_PROSECUZIONE_DI_FATTO IN
    ('S', 'N')),
    CHECK (FLG_ASS_LEGGE_68_99 IN ('S', 'N'))
);

CREATE INDEX IDX_COMUNICAZIONE ON COM_D_RAPPORTO (ID_COM_D_COMUNICAZIONE ASC);

CREATE INDEX IE_COM_D_RAPPORTO_02 ON COM_D_RAPPORTO (ID_COM_T_LIVELLO_RETRIBUZIONE ASC);

CREATE UNIQUE INDEX PK_COM_D_RAPPORTO ON COM_D_RAPPORTO (ID_COM_D_RAPPORTO ASC);

COMMENT ON COLUMN COM_D_RAPPORTO.ID_COM_T_CATEG_LAV_ASS_OBBL IS 'Id che indica la categoria del lavoratore per assunzione obbligatoria';

COMMENT ON COLUMN COM_D_RAPPORTO.NUM_AGENZ_SOMMINISTR IS 'DA NON UTILIZZARE; quello valido è COM_D_DATORE.NUM_AGENZIA_SOMMIN';

COMMENT ON COLUMN COM_D_RAPPORTO.FLG_CCNL_APPLICATO IS 'Flag SI/NO: indica se CCNL è applicato ed è usato solo in caso di lavoro domestico';

COMMENT ON COLUMN COM_D_RAPPORTO.FLG_DISTACCO_PARZIALE IS 'campo nella maschera del trasferimento';

COMMENT ON COLUMN COM_D_RAPPORTO.FLG_DISTACCO_SU_AZI_ESTERA IS 'campo nella maschera del trasferimento';

COMMENT ON COLUMN COM_D_RAPPORTO.FLG_LAVORO_AGRICOLTURA IS 'campo della mschera inizio rapporto';

COMMENT ON COLUMN COM_D_RAPPORTO.FLG_ASSUNZIONE_OBBLIGATORIA IS 'Flag SI/NO: indica se il rapporto è un''assunzione obbliagtoria';

COMMENT ON COLUMN COM_D_RAPPORTO.ID_COM_T_LIVELLO_RETRIBUZIONE IS 'Legame con la tabella COM_T_LIVELLO_RETRIBUZIONE solo per unilav e unisomm';

/* ---------------------------------------------------------------------- */
/* Add table "COM_R_T_LISTA_FZ_ERRORI"                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_R_T_LISTA_FZ_ERRORI (
    ID_COM_T_LISTA_ERRORI_FZ NUMBER(5) NOT NULL,
    ID_COM_D_FZ_ERRORI NUMBER NOT NULL,
    PRIMARY KEY (ID_COM_T_LISTA_ERRORI_FZ, ID_COM_D_FZ_ERRORI)
);

CREATE UNIQUE INDEX PK_COM_R_T_LISTA_FZ_ERRORI ON COM_R_T_LISTA_FZ_ERRORI (ID_COM_T_LISTA_ERRORI_FZ ASC,ID_COM_D_FZ_ERRORI ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_ANAGRAFICA_LAVORATORE"                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_ANAGRAFICA_LAVORATORE (
    ID_COM_D_ANAGRAFICA_LAVORATORE NUMBER(9) NOT NULL,
    CODICE_FISCALE VARCHAR2(16) NOT NULL,
    ID_COMUNE_NASC NUMBER(5),
    COGNOME VARCHAR2(50) NOT NULL,
    ID_COMUNE_RES NUMBER(5),
    NOME VARCHAR2(50) NOT NULL,
    ID_COMUNE_DOM NUMBER(5),
    ID_COM_T_CITTADINANZA NUMBER(5),
    SESSO VARCHAR2(1),
    ID_COM_T_STATUS_STRANIERO NUMBER(5),
    ID_COM_T_LIVELLO_STUDIO NUMBER(5),
    DT_NASCITA DATE NOT NULL,
    NUMERO_DOCUMENTO VARCHAR2(15),
    ID_COM_T_MOTIVO_PERMESSO NUMBER(5),
    DT_SCADENZA_PERMESSO_SOGG DATE,
    DS_INDIRIZZO_RES VARCHAR2(100),
    DS_INDIRIZZO_DOM VARCHAR2(100),
    STATO_CIVILE CHAR(2),
    TEL_DOM VARCHAR2(15),
    COD_CAP_RES VARCHAR2(5),
    COD_CAP_DOM VARCHAR2(5),
    ID_COM_T_STATI_ESTERI_NASC NUMBER(5),
    ID_COM_T_STATI_ESTERI_DOM NUMBER(5),
    ID_COM_T_STATI_ESTERI_RES NUMBER(5),
    ID_QUESTURA_RILASCIO_PERM_SOGG NUMBER(3),
    PRIMARY KEY (ID_COM_D_ANAGRAFICA_LAVORATORE)
);

CREATE UNIQUE INDEX PK_COM_D_ANAGRAFICA_LAVORATORE ON COM_D_ANAGRAFICA_LAVORATORE (ID_COM_D_ANAGRAFICA_LAVORATORE ASC);

CREATE INDEX TMP_DANALAV_CF ON COM_D_ANAGRAFICA_LAVORATORE (CODICE_FISCALE ASC);

CREATE INDEX TMP_DANLAV_FCF_CDIDAL ON COM_D_ANAGRAFICA_LAVORATORE (UPPER("CODICE_FISCALE") ASC,ID_COM_D_ANAGRAFICA_LAVORATORE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_R_COMUNICAZIONE_DATORE"                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_R_COMUNICAZIONE_DATORE (
    ID_COM_D_DATORE NUMBER(9) NOT NULL,
    ID_COM_D_COMUNICAZIONE NUMBER(9) NOT NULL,
    ID_COM_T_TIPO_DATORE VARCHAR2(1),
    PRIMARY KEY (ID_COM_D_DATORE, ID_COM_D_COMUNICAZIONE)
);

CREATE UNIQUE INDEX PK_COM_R_COMUNICAZIONE_DATORE ON COM_R_COMUNICAZIONE_DATORE (ID_COM_D_DATORE ASC,ID_COM_D_COMUNICAZIONE ASC);

CREATE INDEX TMP_IDCOMDCOM ON COM_R_COMUNICAZIONE_DATORE (ID_COM_D_COMUNICAZIONE ASC);

CREATE INDEX TMP_RCOMDAT_ ON COM_R_COMUNICAZIONE_DATORE (ID_COM_D_DATORE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_CAPACITA_FORMATIVA_MODB"                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_CAPACITA_FORMATIVA_MODB (
    CF_AZIENDA VARCHAR2(16) NOT NULL,
    ID_COM_D_CAPACITA_FORM_MOD_B NUMBER(5) NOT NULL,
    ID_COM_T_COMUNE_AZI NUMBER(5),
    NOME_RL VARCHAR2(30),
    ID_COM_T_COMUNE_L_R NUMBER(5),
    COGNOME_RL VARCHAR2(30),
    DT_NASC_RL DATE NOT NULL,
    DS_AZI_DENOMINAZIONE VARCHAR2(300) NOT NULL,
    CF_DELEGATO_RL VARCHAR2(16),
    DS_INDIRIZ_AZI_SEDELEGALE VARCHAR2(100),
    PIVA_AZIENDA VARCHAR2(16),
    R_1_A_ESPERIENZA CHAR(1),
    R_1_B_SPECIFICHE CHAR(1),
    R_2_PROF_CONTRATTUALE CHAR(1),
    R_3_LUOGHI_SIC CHAR(1),
    R_4_LUOGHI_PFI CHAR(1),
    R_5_TUTORE CHAR(1),
    ID_USER_INSERT VARCHAR2(16) NOT NULL,
    DT_INSERT DATE NOT NULL,
    ID_USER_ULT_MOD VARCHAR2(16),
    DT_ULT_MOD DATE,
    FLG_FIRMATO CHAR(1),
    PDF_QUESTIONARIO_MOD_B BLOB,
    ID_COM_T_STATI_ESTERI NUMBER(5),
    PRIMARY KEY (ID_COM_D_CAPACITA_FORM_MOD_B)
);

CREATE UNIQUE INDEX PK_COM_D_CAPACITA_FORMAT_MODB ON COM_D_CAPACITA_FORMATIVA_MODB (ID_COM_D_CAPACITA_FORM_MOD_B ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_SOGGETTO_ABILITATO"                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_SOGGETTO_ABILITATO (
    ID_COM_D_SOGGETTO_ABILITATO NUMBER(5) NOT NULL,
    ID_COM_TIPO_SOGGETTO_ABILITATO NUMBER(5),
    ID_COM_T_COMUNE NUMBER(5),
    CF_SOGGETTO VARCHAR2(16),
    COGNOME_DENOMINAZIONE VARCHAR2(100),
    PARTITA_IVA VARCHAR2(11),
    INDIRIZZO VARCHAR2(100),
    TEL VARCHAR2(15),
    FAX VARCHAR2(15),
    EMAIL VARCHAR2(50),
    CAP VARCHAR2(5),
    ID_USER_INSERT VARCHAR2(16),
    ID_USER_ULT_MOD VARCHAR2(16),
    DT_INSERT DATE,
    DT_ULT_MOD DATE,
    FLG_ACCENTRAMENTO VARCHAR2(1),
    DT_ANNULLAMENTO DATE,
    PRIMARY KEY (ID_COM_D_SOGGETTO_ABILITATO)
);

CREATE UNIQUE INDEX PK_COM_D_SOGGETTO_ABILITATO ON COM_D_SOGGETTO_ABILITATO (ID_COM_D_SOGGETTO_ABILITATO ASC);

COMMENT ON COLUMN COM_D_SOGGETTO_ABILITATO.DT_ANNULLAMENTO IS 'Data di cancellazione logica del soggetto abilitazione';

/* ---------------------------------------------------------------------- */
/* Add table "COM_R_T_LISTA_WARNING"                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_R_T_LISTA_WARNING (
    ID_COM_D_WARNING NUMBER(5) NOT NULL,
    ID_COM_T_LISTA_WARNING NUMBER(5) NOT NULL,
    PRIMARY KEY (ID_COM_D_WARNING, ID_COM_T_LISTA_WARNING)
);

CREATE UNIQUE INDEX PK_COM_R_T_LISTA_WARNING ON COM_R_T_LISTA_WARNING (ID_COM_D_WARNING ASC,ID_COM_T_LISTA_WARNING ASC);

/* ---------------------------------------------------------------------- */
/* Add table "PARAMETRI_TIPO_COMUNICAZIONE"                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE PARAMETRI_TIPO_COMUNICAZIONE (
    PV CHAR(2) NOT NULL,
    TIPO VARCHAR2(1) NOT NULL,
    MITTENTE VARCHAR2(80),
    SUBJECT VARCHAR2(80),
    TESTO_MAIL VARCHAR2(256),
    PROT_ORIGINE VARCHAR2(15) NOT NULL,
    PROT_TIPO_DOC NUMBER(3) NOT NULL,
    TIPO_DOC_RUPAR VARCHAR2(18),
    PRIMARY KEY (PV, TIPO)
);

CREATE UNIQUE INDEX PK_PARAMETRI_TIPO_COMUNICAZION ON PARAMETRI_TIPO_COMUNICAZIONE (PV ASC,TIPO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_DICHIARAZIONI_UD"                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_DICHIARAZIONI_UD (
    ID_COM_D_COMUNICAZIONE NUMBER(9) NOT NULL,
    FLG_DAT_LAV_CONIUGE_LAVORATORE VARCHAR2(1),
    FLG_DAT_LAV_PARENTE_LAVORATORE VARCHAR2(1),
    FLG_DAT_LAV_INVALIDO VARCHAR2(1),
    FLG_DAT_LAV_CONV_LAVORATORE VARCHAR2(1),
    PRIMARY KEY (ID_COM_D_COMUNICAZIONE)
);

CREATE UNIQUE INDEX PK_COM_D_DICHIARAZIONI_UD ON COM_D_DICHIARAZIONI_UD (ID_COM_D_COMUNICAZIONE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_R_RAPPORTO_LAVORATORE"                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_R_RAPPORTO_LAVORATORE (
    ID_COM_D_LAVORATORE NUMBER(9) NOT NULL,
    ID_COM_D_RAPPORTO NUMBER(9) NOT NULL,
    ID_COM_T_TIPO_RAPPORTO_AZIENDA VARCHAR2(1),
    ID_COM_T_TIPO_LAVORATORE VARCHAR2(1),
    PRIMARY KEY (ID_COM_D_LAVORATORE, ID_COM_D_RAPPORTO)
);

CREATE UNIQUE INDEX PK_COM_R_RAPPORTO_LAVORATORE ON COM_R_RAPPORTO_LAVORATORE (ID_COM_D_LAVORATORE ASC,ID_COM_D_RAPPORTO ASC);

CREATE INDEX TMP_1 ON COM_R_RAPPORTO_LAVORATORE (ID_COM_D_LAVORATORE ASC);

CREATE INDEX TMP_2 ON COM_R_RAPPORTO_LAVORATORE (ID_COM_D_RAPPORTO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "DELEGA"                                                     */
/* ---------------------------------------------------------------------- */

CREATE TABLE DELEGA (
    ID_DELEGA NUMBER(8) NOT NULL,
    PV_COMPETENZA CHAR(2),
    C_STATO CHAR(1) NOT NULL,
    C_TIPO_DELEGA CHAR(1) NOT NULL,
    C_ENTE VARCHAR2(16),
    CARICA VARCHAR2(253) NOT NULL,
    ANNO_PROTOCOLLO NUMBER(4),
    N_PROTOCOLLO NUMBER(8),
    DT_PROTOCOLLO DATE,
    CF_DELEGATO VARCHAR2(16) NOT NULL,
    COGNOME_DELEGATO VARCHAR2(30) NOT NULL,
    NOME_DELEGATO VARCHAR2(30) NOT NULL,
    INDIRIZZO_DELEGATO VARCHAR2(40) NOT NULL,
    COMUNE_DELEGATO VARCHAR2(40) NOT NULL,
    PV_DELEGATO CHAR(2) NOT NULL,
    CAP_DELEGATO CHAR(5),
    TELEFONO_DELEGATO VARCHAR2(15),
    FAX_DELEGATO VARCHAR2(15),
    MAIL_DELEGATO VARCHAR2(50),
    PIVA_DELEGATO VARCHAR2(11),
    CF_DELEGANTE VARCHAR2(16) NOT NULL,
    COGNOME_DELEGANTE VARCHAR2(30) NOT NULL,
    NOME_DELEGANTE VARCHAR2(30) NOT NULL,
    INDIRIZZO_DELEGANTE VARCHAR2(40) NOT NULL,
    COMUNE_DELEGANTE VARCHAR2(40) NOT NULL,
    PV_DELEGANTE CHAR(2) NOT NULL,
    CAP_DELEGANTE CHAR(5),
    TELEFONO_DELEGANTE VARCHAR2(15),
    MAIL_DELEGANTE VARCHAR2(50),
    DENOMINAZIONE_IMPRESA VARCHAR2(305),
    CF_IMPRESA VARCHAR2(16) NOT NULL,
    PIVA_IMPRESA VARCHAR2(11),
    DT_REVOCA DATE,
    ID_USER_INSERT VARCHAR2(16) NOT NULL,
    DT_INSERT DATE NOT NULL,
    ID_USER_ULT_MOD VARCHAR2(16),
    DT_ULT_MOD DATE,
    VERIFICA VARCHAR2(2),
    DT_VERIFICA DATE,
    C_COM_DELEGATO NUMBER(5),
    C_COM_DELEGANTE NUMBER(5),
    C_STATO_DELEGATO NUMBER(5),
    C_STATO_DELEGANTE NUMBER(5),
    PRIMARY KEY (ID_DELEGA)
);

CREATE UNIQUE INDEX PK_DELEGA ON DELEGA (ID_DELEGA ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_R_DATORE_SEDE"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_R_DATORE_SEDE (
    ID_COM_D_DATORE NUMBER(9) NOT NULL,
    ID_COM_D_SEDE_LAVORO NUMBER(9) NOT NULL,
    PRIMARY KEY (ID_COM_D_DATORE, ID_COM_D_SEDE_LAVORO)
);

CREATE UNIQUE INDEX PK_COM_R_DATORE_SEDE ON COM_R_DATORE_SEDE (ID_COM_D_DATORE ASC,ID_COM_D_SEDE_LAVORO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_R_TIPOL_TIROC_CAT_TIROC"                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_R_TIPOL_TIROC_CAT_TIROC (
    ID_COM_T_TIPOLOGIA_TIROCINIO NUMBER NOT NULL,
    ID_COM_T_CATEG_TIROCINANTE NUMBER NOT NULL,
    D_INIZIO DATE NOT NULL,
    D_FINE DATE,
    NUM_DURATA_MAX NUMBER NOT NULL,
    COD_UNITA_MISURA_DURATA VARCHAR2(2) NOT NULL,
    FLG_VISUALIZZA_WARNING VARCHAR2(1) DEFAULT 'N' NOT NULL,
    PRIMARY KEY (ID_COM_T_TIPOLOGIA_TIROCINIO, ID_COM_T_CATEG_TIROCINANTE)
);

CREATE UNIQUE INDEX PK_COM_R_TIPOL_TIROC_CAT_TIROC ON COM_R_TIPOL_TIROC_CAT_TIROC (ID_COM_T_TIPOLOGIA_TIROCINIO ASC,ID_COM_T_CATEG_TIROCINANTE ASC);

COMMENT ON COLUMN COM_R_TIPOL_TIROC_CAT_TIROC.NUM_DURATA_MAX IS 'Durata massima del tirocinio per la coppia Tipologia tirocinio/Categoria tirocinante; un valore pari a 0 indica durata massima illimitata';

COMMENT ON COLUMN COM_R_TIPOL_TIROC_CAT_TIROC.COD_UNITA_MISURA_DURATA IS 'Rappresenta l''unità di misura della durata massima del tirocinio (campo NUM_DURATA_MAX)';

COMMENT ON COLUMN COM_R_TIPOL_TIROC_CAT_TIROC.FLG_VISUALIZZA_WARNING IS 'Indica se deve essere visualizzato un msg di warning che indichi che dovra'' essere completato il processo amministrativo compilando il Progetto Formativo sul Portale dei Tirocini.';

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_LEGALE_RAPPR"                                         */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_LEGALE_RAPPR (
    ID_COM_D_DATORE NUMBER(9) NOT NULL,
    FLG_RESIDENZA_ITALIANA VARCHAR2(1),
    DS_COGNOME VARCHAR2(50),
    DS_NOME VARCHAR2(50),
    SESSO VARCHAR2(1),
    DT_NASCITA DATE,
    ID_COM_T_COMUNE_NASC NUMBER(5),
    ID_COM_T_STATI_ESTERI_NASC NUMBER(5),
    ID_COM_T_CITTADINANZA NUMBER(5),
    ID_COM_T_STATUS_STRANIERO NUMBER(5),
    NUMERO_DOCUMENTO VARCHAR2(15),
    ID_COM_T_MOTIVO_PERMESSO NUMBER(5),
    DT_SCADENZA_PERMESSO_SOGG DATE,
    ID_COM_T_QUESTURA NUMBER(3),
    ID_USER_INSERT VARCHAR2(20) NOT NULL,
    DT_INSERT DATE NOT NULL,
    ID_USER_ULT_MOD VARCHAR2(20) NOT NULL,
    DT_ULT_MOD DATE NOT NULL,
    FLG_SOGGIORNANTE_ITALIA VARCHAR2(1),
    ID_LEGALE_RAPPR_SILP NUMBER,
    PRIMARY KEY (ID_COM_D_DATORE),
    CHECK (FLG_RESIDENZA_ITALIANA IN ('S', 'N')),
    CHECK (SESSO IN ('M', 'F')),
    CHECK (FLG_SOGGIORNANTE_ITALIA IN ('S', 'N'))
);

CREATE INDEX IE_COM_D_LEGALE_RAPPR_01 ON COM_D_LEGALE_RAPPR (ID_LEGALE_RAPPR_SILP ASC);

CREATE UNIQUE INDEX PK_COM_D_LEGALE_RAPPR ON COM_D_LEGALE_RAPPR (ID_COM_D_DATORE ASC);

COMMENT ON COLUMN COM_D_LEGALE_RAPPR.ID_LEGALE_RAPPR_SILP IS 'Corrisponde al campo ID_SIL_AZI_REFERENTE di SILP';

/* ---------------------------------------------------------------------- */
/* Add table "COM_R_ANAG_DATORE_ANAG_SEDE"                                */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_R_ANAG_DATORE_ANAG_SEDE (
    ID_COM_D_ANAGRAFICA_DATORE NUMBER(9) NOT NULL,
    ID_COM_D_ANAGRAFIC_SEDE_LAVORO NUMBER(9) NOT NULL,
    PRIMARY KEY (ID_COM_D_ANAGRAFICA_DATORE, ID_COM_D_ANAGRAFIC_SEDE_LAVORO)
);

CREATE UNIQUE INDEX PK_COM_R_ANAG_DATORE_ANAG_SEDE ON COM_R_ANAG_DATORE_ANAG_SEDE (ID_COM_D_ANAGRAFICA_DATORE ASC,ID_COM_D_ANAGRAFIC_SEDE_LAVORO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "PDF_DELEGA"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE PDF_DELEGA (
    ID_PDF_DELEGA NUMBER(8) NOT NULL,
    PDF BLOB NOT NULL,
    DT_TIME_MARCA DATE,
    ID_USER_INSERT VARCHAR2(16) NOT NULL,
    DT_INSERT DATE NOT NULL,
    ID_USER_ULT_MOD VARCHAR2(16),
    DT_ULT_MOD DATE,
    PRIMARY KEY (ID_PDF_DELEGA)
);

CREATE UNIQUE INDEX PK_PDF_DELEGA ON PDF_DELEGA (ID_PDF_DELEGA ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_R_AGEVOLAZIONERL"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_R_AGEVOLAZIONERL (
    ID_COM_D_RAPPORTO NUMBER(9) NOT NULL,
    ID_COM_T_AGEVOLAZIONERL NUMBER(5) NOT NULL,
    PRIMARY KEY (ID_COM_D_RAPPORTO, ID_COM_T_AGEVOLAZIONERL)
);

CREATE INDEX IE_COM_R_AGEVOLAZIONERL_01 ON COM_R_AGEVOLAZIONERL (ID_COM_D_RAPPORTO ASC);

CREATE UNIQUE INDEX PK_COM_R_AGEVOLAZIONERL ON COM_R_AGEVOLAZIONERL (ID_COM_D_RAPPORTO ASC,ID_COM_T_AGEVOLAZIONERL ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_CAPACITA_FORMATIVA_MODA"                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_CAPACITA_FORMATIVA_MODA (
    CF_AZIENDA VARCHAR2(16) NOT NULL,
    ID_COM_D_CAPACITA_FORM_MOD_A NUMBER(5) NOT NULL,
    ID_COM_T_COMUNE_AZI NUMBER(5) NOT NULL,
    NOME_RL VARCHAR2(30),
    ID_COM_T_COMUNE_L_R NUMBER(5),
    COGNOME_RL VARCHAR2(30),
    DT_NASC_RL DATE NOT NULL,
    DS_AZI_DENOMINAZIONE VARCHAR2(300) NOT NULL,
    CF_DELEGATO_RL VARCHAR2(16),
    DS_INDIRIZ_AZI_SEDELEGALE VARCHAR2(100),
    PIVA_AZIENDA VARCHAR2(16),
    R_1_A_PERC_SCOLASTICI CHAR(1),
    R_1_A_PERC_ACCADEMICI CHAR(1),
    R_1_A_PERC_FORMPROF CHAR(1),
    R_1_A_PERC_IMPRESA CHAR(1),
    R_1_B_RISORSE_TITOLO CHAR(1),
    R_1_C_RISORSE_POSIZIONE CHAR(1),
    R_2_LUOGHI_IDONEI CHAR(1),
    R_3_TUTORE CHAR(1),
    ID_USER_INSERT VARCHAR2(16) NOT NULL,
    DT_INSERT DATE NOT NULL,
    ID_USER_ULT_MOD VARCHAR2(16),
    DT_ULT_MOD DATE,
    FLG_FIRMATO CHAR(1),
    PDF_QUESTIONARIO_MOD_A BLOB,
    ID_COM_T_STATI_ESTERI NUMBER(5),
    PRIMARY KEY (ID_COM_D_CAPACITA_FORM_MOD_A)
);

CREATE UNIQUE INDEX PK_COM_D_CAPACITA_FORMAT_MODA ON COM_D_CAPACITA_FORMATIVA_MODA (ID_COM_D_CAPACITA_FORM_MOD_A ASC);

/* ---------------------------------------------------------------------- */
/* Add table "ANAGRAFICA_DELEGATO"                                        */
/* ---------------------------------------------------------------------- */

CREATE TABLE ANAGRAFICA_DELEGATO (
    CF_DELEGATO VARCHAR2(16) NOT NULL,
    PV CHAR(2),
    PIVA_DELEGATO VARCHAR2(11),
    COGNOME VARCHAR2(30) NOT NULL,
    NOME VARCHAR2(30) NOT NULL,
    DT_NASCITA DATE NOT NULL,
    C_COM_NASC NUMBER(5),
    C_COM_RES NUMBER(5),
    INDIRIZZO_RES VARCHAR2(40),
    CAP_RES CHAR(5),
    C_COM_DOM NUMBER(5),
    INDIRIZZO_DOM VARCHAR2(40),
    SESSO CHAR(1),
    TEL VARCHAR2(15),
    CAP_DOM CHAR(5),
    FAX VARCHAR2(15),
    EMAIL VARCHAR2(50),
    ID_USER_INSERT VARCHAR2(16) NOT NULL,
    ID_USER_ULT_MOD VARCHAR2(16),
    DT_INSERT DATE NOT NULL,
    DT_ULT_MOD DATE,
    TIPO_ANAGRAFICA CHAR(1) NOT NULL,
    C_CITTADINANZA NUMBER(5),
    ID_COM_D_SOGGETTO_ABILITATO NUMBER(5),
    C_STATO_ESTERO NUMBER(5),
    C_STATO_ESTERO_DOM NUMBER(5),
    C_STATO_ESTERO_RES NUMBER(5),
    DT_ANNULLAMENTO DATE,
    PRIMARY KEY (CF_DELEGATO, TIPO_ANAGRAFICA)
);

CREATE UNIQUE INDEX PK_ANAGRAFICA_DELEGATO ON ANAGRAFICA_DELEGATO (CF_DELEGATO ASC,TIPO_ANAGRAFICA ASC);

COMMENT ON COLUMN ANAGRAFICA_DELEGATO.C_STATO_ESTERO IS 'stato estero di nascita';

COMMENT ON COLUMN ANAGRAFICA_DELEGATO.C_STATO_ESTERO_DOM IS 'stato estero di domicilio';

COMMENT ON COLUMN ANAGRAFICA_DELEGATO.C_STATO_ESTERO_RES IS 'stato estero di residenza';

COMMENT ON COLUMN ANAGRAFICA_DELEGATO.DT_ANNULLAMENTO IS 'Data di cancellazione logica dell''anagrafica';

/* ---------------------------------------------------------------------- */
/* Add table "CARICA_PERSONA_PV"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE CARICA_PERSONA_PV (
    CARICA CHAR(3) NOT NULL,
    PV CHAR(2),
    DESCRIZIONE VARCHAR2(135)
);

/* ---------------------------------------------------------------------- */
/* Add table "ENTE_DELEGATO"                                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE ENTE_DELEGATO (
    PV CHAR(2) NOT NULL,
    C_ENTE VARCHAR2(10) NOT NULL,
    CF_DELEGATO VARCHAR2(16) NOT NULL,
    TIPO_ANAGRAFICA CHAR(1) NOT NULL,
    PRIMARY KEY (C_ENTE, CF_DELEGATO, TIPO_ANAGRAFICA)
);

CREATE UNIQUE INDEX PK_ENTE_DELEGATO ON ENTE_DELEGATO (C_ENTE ASC,CF_DELEGATO ASC,TIPO_ANAGRAFICA ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_D_DESTINATARI_IGNORARE"                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_D_DESTINATARI_IGNORARE (
    ID_COM_D_COMUNICAZIONE NUMBER(9) NOT NULL,
    ID_COM_T_TIPO_DESTINATARIO VARCHAR2(2) NOT NULL,
    PRIMARY KEY (ID_COM_D_COMUNICAZIONE, ID_COM_T_TIPO_DESTINATARIO)
);

CREATE UNIQUE INDEX PK_COM_D_DESTINATARI_IGNORARE ON COM_D_DESTINATARI_IGNORARE (ID_COM_D_COMUNICAZIONE ASC,ID_COM_T_TIPO_DESTINATARIO ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_R_REG_TRACCIATO_CONTRATTO"                              */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_R_REG_TRACCIATO_CONTRATTO (
    ID_COM_T_TIPO_CONTRATTO NUMBER NOT NULL,
    ID_COM_T_TIPO_TRACCIATO VARCHAR2(2) NOT NULL,
    ID_COM_T_TIPO_COMUNICAZIONE VARCHAR2(3) NOT NULL,
    D_RIFERIMENTO DATE NOT NULL,
    D_INIZIO DATE NOT NULL,
    D_FINE DATE,
    PRIMARY KEY (ID_COM_T_TIPO_CONTRATTO, ID_COM_T_TIPO_TRACCIATO, ID_COM_T_TIPO_COMUNICAZIONE)
);

CREATE UNIQUE INDEX PK_COM_R_REG_TRACCIATO_CONTR ON COM_R_REG_TRACCIATO_CONTRATTO (ID_COM_T_TIPO_TRACCIATO ASC,ID_COM_T_TIPO_CONTRATTO ASC,ID_COM_T_TIPO_COMUNICAZIONE ASC);

/* ---------------------------------------------------------------------- */
/* Add table "COM_T_TIPO_CONTR_AMM_PER_COM"                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_T_TIPO_CONTR_AMM_PER_COM (
    ID_COM_T_TIPO_CONTR_AMM_COM NUMBER NOT NULL,
    ID_COM_T_TIPO_CONTRATTO NUMBER(5) NOT NULL,
    FLG_VLD_CES CHAR(1),
    FLG_VLD_PRO CHAR(1) NOT NULL,
    FLG_VLD_TRS CHAR(1) NOT NULL,
    FLG_VLD_TRA CHAR(1) NOT NULL,
    FLG_VLD_DST CHAR(1) NOT NULL,
    FLG_VLD_SOM CHAR(1),
    FLG_SOCIO_LAV CHAR(1),
    FLG_VLD_UL CHAR(1),
    FLG_VLD_UD CHAR(1),
    FLG_VLD_VD CHAR(1),
    FLG_TIPO_ORARIO_NON_DEFINITO CHAR(1),
    PRIMARY KEY (ID_COM_T_TIPO_CONTR_AMM_COM),
    CHECK (FLG_VLD_CES IN ('S', 'N', 'R')),
    CHECK (FLG_VLD_PRO IN  ('S', 'N', 'R')),
    CHECK (FLG_VLD_TRS IN  ('S', 'N', 'R')),
    CHECK (FLG_VLD_TRA IN  ('S', 'N', 'R')),
    CHECK (FLG_VLD_DST IN  ('S', 'N', 'R')),
    CHECK (FLG_VLD_SOM IN  ('S', 'N', 'R')),
    CHECK (FLG_SOCIO_LAV IN  ('S', 'N', 'R')),
    CHECK (FLG_VLD_UL IN  ('S', 'N', 'R')),
    CHECK (FLG_VLD_UD IN  ('S', 'N', 'R')),
    CHECK (FLG_VLD_VD IN  ('S', 'N', 'R')),
    CHECK (FLG_VLD_CES IN ('S', 'N')),
    CHECK (FLG_VLD_SOM IN ('S', 'N'))
);

CREATE UNIQUE INDEX PK_COM_T_TIPO_CONTR_AMM_PER_CO ON COM_T_TIPO_CONTR_AMM_PER_COM (ID_COM_T_TIPO_CONTR_AMM_COM ASC);

COMMENT ON TABLE COM_T_TIPO_CONTR_AMM_PER_COM IS 'Tipi contratti ammessi per tipo comunicazione e tipo tracciato: S = ammessa; N = non ammessa; R = ammessa SOLO in caso di rettifica o annullamento';

COMMENT ON COLUMN COM_T_TIPO_CONTR_AMM_PER_COM.FLG_TIPO_ORARIO_NON_DEFINITO IS 'indica per quale tipologia di contratto è ammesso il tipo di orario "non definito" (campo della maschera dati del rapporto di lavoro)';

/* ---------------------------------------------------------------------- */
/* Add table "COM_R_MODELLI_PFI"                                          */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_R_MODELLI_PFI (
    ID_COM_R_MODELLI_PFI NUMBER(5) NOT NULL,
    ID_COM_D_CAPACITA_FORM_MOD_B NUMBER(5),
    ID_COM_D_CAPACITA_FORM_MOD_A NUMBER(5),
    PRIMARY KEY (ID_COM_R_MODELLI_PFI)
);

CREATE UNIQUE INDEX PK_COM_R_MODELLI_PFI ON COM_R_MODELLI_PFI (ID_COM_R_MODELLI_PFI ASC);

/* ---------------------------------------------------------------------- */
/* Add table "DELEGATO_IMPRESA"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE DELEGATO_IMPRESA (
    PV CHAR(2) NOT NULL,
    CF_DELEGATO VARCHAR2(16) NOT NULL,
    CF_IMPRESA VARCHAR2(16) NOT NULL,
    ID_COM_D_ANAGRAFICA_DATORE NUMBER(9),
    DT_FINE_CARICA DATE,
    ID_CARICA_PERSONA_PV CHAR(3),
    FLG_SCUOLA CHAR(1) DEFAULT 'N',
    TIPO_ANAGRAFICA CHAR(1) NOT NULL,
    DENOMINAZIONE VARCHAR2(300),
    DT_ANNULLAMENTO DATE,
    PRIMARY KEY (CF_DELEGATO, CF_IMPRESA, TIPO_ANAGRAFICA)
);

CREATE INDEX IE_DELEGATO_IMPRESA_01 ON DELEGATO_IMPRESA (CF_IMPRESA ASC);

CREATE UNIQUE INDEX PK_DELEGATO_IMPRESA ON DELEGATO_IMPRESA (CF_DELEGATO ASC,CF_IMPRESA ASC,TIPO_ANAGRAFICA ASC);

COMMENT ON COLUMN DELEGATO_IMPRESA.DT_ANNULLAMENTO IS 'Data di cancellazione logica del soggetto abilitazione';

/* ---------------------------------------------------------------------- */
/* Add table "COM_R_T_LISTA_FM_ERRORI"                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE COM_R_T_LISTA_FM_ERRORI (
    ID_COM_D_FM_ERRORI NUMBER(5) NOT NULL,
    ID_COM_T_LISTA_ERRORI_FM NUMBER(5) NOT NULL,
    PRIMARY KEY (ID_COM_D_FM_ERRORI, ID_COM_T_LISTA_ERRORI_FM)
);

CREATE UNIQUE INDEX PK_COM_R_T_LISTA_FM_ERRORI ON COM_R_T_LISTA_FM_ERRORI (ID_COM_D_FM_ERRORI ASC,ID_COM_T_LISTA_ERRORI_FM ASC);

/* ---------------------------------------------------------------------- */
/* Add foreign key constraints                                            */
/* ---------------------------------------------------------------------- */

ALTER TABLE COM_T_COMUNE ADD
    FOREIGN KEY (ID_COM_T_CPI) REFERENCES COM_T_CPI (ID_COM_T_CPI);

ALTER TABLE COM_T_COMUNE ADD
    FOREIGN KEY (ID_COM_T_PROVINCIA) REFERENCES COM_T_PROVINCIA (ID_COM_T_PROVINCIA);

ALTER TABLE COM_T_LIVELLO_RETRIBUZIONE ADD
    FOREIGN KEY (ID_COM_T_CCNL) REFERENCES COM_T_CCNL (ID_COM_T_CCNL);

ALTER TABLE COM_T_LIVELLO_RETRIBUZIONE ADD
    FOREIGN KEY (COD_TIPO_LIVELLO) REFERENCES COM_T_TIPO_LIVELLO_RETRIB (COD_TIPO_LIVELLO);

ALTER TABLE COM_D_DUPLICA_GRUPPO_LOG ADD
    FOREIGN KEY (ID_COM_D_COMUNICAZ_GRUPPO) REFERENCES COM_D_COMUNICAZIONE (ID_COM_D_COMUNICAZIONE);

ALTER TABLE COM_T_MOTIVO_PERMESSO ADD
    FOREIGN KEY (ID_NEW_MOTIVO_PERMESSO) REFERENCES COM_T_MOTIVO_PERMESSO (ID_COM_T_MOTIVO_PERMESSO);

ALTER TABLE COM_T_VARIAZIONE_SOMM ADD
    FOREIGN KEY (ID_COM_T_TIPO_COMUNICAZIONE) REFERENCES COM_T_TIPO_COMUNICAZIONE (ID_COM_T_TIPO_COMUNICAZIONE);

ALTER TABLE COM_T_VARIAZIONE_SOMM ADD
    FOREIGN KEY (ID_COM_T_TIPO_SOMMINISTRAZIONE) REFERENCES COM_T_TIPO_SOMMINISTRAZIONE (ID_COM_T_TIPO_SOMMINISTRAZIONE);

ALTER TABLE COM_R_COMUNICAZ_SOMMINISTR ADD
    FOREIGN KEY (ID_COM_T_TIPO_SOMMINISTRAZIONE) REFERENCES COM_T_TIPO_SOMMINISTRAZIONE (ID_COM_T_TIPO_SOMMINISTRAZIONE);

ALTER TABLE COM_R_COMUNICAZ_SOMMINISTR ADD
    FOREIGN KEY (ID_COM_T_TIPO_COMUNICAZIONE) REFERENCES COM_T_TIPO_COMUNICAZIONE (ID_COM_T_TIPO_COMUNICAZIONE);

ALTER TABLE COM_T_TIPO_CONTR_AMM_PER_COM ADD
    FOREIGN KEY (ID_COM_T_TIPO_CONTRATTO) REFERENCES COM_T_TIPO_CONTRATTI (ID_COM_T_TIPO_CONTRATTO);

ALTER TABLE COM_D_SEDE_LAVORO ADD
    FOREIGN KEY (ID_COM_T_STATI_ESTERI) REFERENCES COM_T_STATI_ESTERI (ID_COM_T_STATI_ESTERI);

ALTER TABLE COM_D_SEDE_LAVORO ADD
    FOREIGN KEY (ID_COM_T_COMUNE) REFERENCES COM_T_COMUNE (ID_COM_T_COMUNE);

ALTER TABLE COM_D_LAVORATORE ADD
    FOREIGN KEY (ID_COM_T_LIVELLO_STUDIO) REFERENCES COM_T_LIVELLO_STUDIO (ID_COM_T_LIVELLO_STUDIO);

ALTER TABLE COM_D_LAVORATORE ADD
    FOREIGN KEY (ID_COM_T_STATI_ESTERI_DOM) REFERENCES COM_T_STATI_ESTERI (ID_COM_T_STATI_ESTERI);

ALTER TABLE COM_D_LAVORATORE ADD
    FOREIGN KEY (ID_COMUNE_RES) REFERENCES COM_T_COMUNE (ID_COM_T_COMUNE);

ALTER TABLE COM_D_LAVORATORE ADD
    FOREIGN KEY (ID_QUESTURA_RILASCIO_PERM_SOGG) REFERENCES COM_T_QUESTURA (ID_COM_T_QUESTURA);

ALTER TABLE COM_D_LAVORATORE ADD
    FOREIGN KEY (ID_COMUNE_DOM) REFERENCES COM_T_COMUNE (ID_COM_T_COMUNE);

ALTER TABLE COM_D_LAVORATORE ADD
    FOREIGN KEY (ID_COM_T_MOTIVO_PERMESSO) REFERENCES COM_T_MOTIVO_PERMESSO (ID_COM_T_MOTIVO_PERMESSO);

ALTER TABLE COM_D_LAVORATORE ADD
    FOREIGN KEY (ID_COM_T_CITTADINANZA) REFERENCES COM_T_CITTADINANZA (ID_COM_T_CITTADINANZA);

ALTER TABLE COM_D_LAVORATORE ADD
    FOREIGN KEY (ID_COM_T_STATI_ESTERI_RES) REFERENCES COM_T_STATI_ESTERI (ID_COM_T_STATI_ESTERI);

ALTER TABLE COM_D_LAVORATORE ADD
    FOREIGN KEY (ID_COM_T_STATI_ESTERI_NASC) REFERENCES COM_T_STATI_ESTERI (ID_COM_T_STATI_ESTERI);

ALTER TABLE COM_D_LAVORATORE ADD
    FOREIGN KEY (ID_COM_T_STATUS_STRANIERO) REFERENCES COM_T_STATUS_STRANIERO (ID_COM_T_STATUS_STRANIERO);

ALTER TABLE COM_D_LAVORATORE ADD
    FOREIGN KEY (ID_COMUNE_NASC) REFERENCES COM_T_COMUNE (ID_COM_T_COMUNE);

ALTER TABLE COM_T_CCNL ADD
    FOREIGN KEY (ID_COM_T_CCNL_PREV) REFERENCES COM_T_CCNL (ID_COM_T_CCNL);

ALTER TABLE COM_T_TIPO_CONTRATTI ADD
    FOREIGN KEY (TIPOLOGIA_RAPPORTO) REFERENCES COM_T_TIPOLOGIA_RAPPORTO (ID_COM_T_TIPOLOGIA_RAPPORTO);

ALTER TABLE COM_D_DATORE ADD
    FOREIGN KEY (C_NATURA_GIURIDICA) REFERENCES NATURA_GIURIDICA (C_NATURA_GIURIDICA);

ALTER TABLE COM_D_DATORE ADD
    FOREIGN KEY (ID_COM_T_ATECOFIN) REFERENCES COM_T_ATECOFIN (ID_COM_T_ATECOFIN);

ALTER TABLE COM_R_SEDE_LAVORO_LAVORATORE ADD
    FOREIGN KEY (ID_COM_D_LAVORATORE) REFERENCES COM_D_LAVORATORE (ID_COM_D_LAVORATORE);

ALTER TABLE COM_R_SEDE_LAVORO_LAVORATORE ADD
    FOREIGN KEY (ID_COM_D_SEDE_LAVORO) REFERENCES COM_D_SEDE_LAVORO (ID_COM_D_SEDE_LAVORO);

ALTER TABLE ANAGRAFICA_ENTE ADD
    FOREIGN KEY (PV) REFERENCES PERSONALIZZAZIONE (PV);

ALTER TABLE COM_T_PROVINCIA ADD
    FOREIGN KEY (ID_COM_T_REGIONE) REFERENCES COM_T_REGIONE (ID_COM_T_REGIONE);

ALTER TABLE COM_T_TIPO_CONTR_PERIODI ADD
    FOREIGN KEY (ID_COM_T_TIPO_CONTRATTO) REFERENCES COM_T_TIPO_CONTRATTI (ID_COM_T_TIPO_CONTRATTO);

ALTER TABLE COM_R_MODELLI_PFI ADD
    FOREIGN KEY (ID_COM_D_CAPACITA_FORM_MOD_B) REFERENCES COM_D_CAPACITA_FORMATIVA_MODB (ID_COM_D_CAPACITA_FORM_MOD_B);

ALTER TABLE COM_R_MODELLI_PFI ADD
    FOREIGN KEY (ID_COM_D_CAPACITA_FORM_MOD_A) REFERENCES COM_D_CAPACITA_FORMATIVA_MODA (ID_COM_D_CAPACITA_FORM_MOD_A);

ALTER TABLE DELEGATO_IMPRESA ADD
    FOREIGN KEY (CF_DELEGATO, TIPO_ANAGRAFICA) REFERENCES ANAGRAFICA_DELEGATO (CF_DELEGATO,TIPO_ANAGRAFICA);

ALTER TABLE DELEGATO_IMPRESA ADD
    FOREIGN KEY (ID_COM_D_ANAGRAFICA_DATORE) REFERENCES COM_D_ANAGRAFICA_DATORE (ID_COM_D_ANAGRAFICA_DATORE);

ALTER TABLE PARAMETRI_TIPO_DELEGA ADD
    FOREIGN KEY (TIPO) REFERENCES TIPO_DELEGA (ID_TIPO_DELEGA);

ALTER TABLE PARAMETRI_TIPO_DELEGA ADD
    FOREIGN KEY (PV) REFERENCES PERSONALIZZAZIONE (PV);

ALTER TABLE COM_R_DEP_COMMAX_COMUNIC ADD
    FOREIGN KEY (ID_COM_D_UPL_DOCUMENTI) REFERENCES COM_D_UPL_DOCUMENTI (ID_COM_D_UPL_DOCUMENTI);

ALTER TABLE COM_R_DEP_COMMAX_COMUNIC ADD
    FOREIGN KEY (ID_COM_D_COMUNICAZIONE) REFERENCES COM_D_COMUNICAZIONE (ID_COM_D_COMUNICAZIONE);

ALTER TABLE COM_R_DEP_COMMAX_COMUNIC ADD
    FOREIGN KEY (ID_DEPOSITO_COMMAX, PROG_XML) REFERENCES DEPOSITO_COMMAX (ID_DEPOSITO_COMMAX,PROG_XML);

ALTER TABLE COM_D_IMPORT_ERRORE ADD
    FOREIGN KEY (ID_COM_T_TIPO_TRACCIATO) REFERENCES COM_T_TIPO_TRACCIATO (ID_COM_T_TIPO_TRACCIATO);

ALTER TABLE COM_R_T_LISTA_FM_ERRORI ADD
    FOREIGN KEY (ID_COM_D_FM_ERRORI) REFERENCES COM_D_FM_ERRORI (ID_COM_D_FM_ERRORI);

ALTER TABLE COM_R_T_LISTA_FM_ERRORI ADD
    FOREIGN KEY (ID_COM_T_LISTA_ERRORI_FM) REFERENCES COM_T_LISTA_ERRORI_FM (ID_COM_T_LISTA_ERRORI_FM);

ALTER TABLE COM_D_ANAGRAFICA_DATORE ADD
    FOREIGN KEY (C_NATURA_GIURIDICA) REFERENCES NATURA_GIURIDICA (C_NATURA_GIURIDICA);

ALTER TABLE COM_D_ANAGRAFICA_DATORE ADD
    FOREIGN KEY (ID_COM_T_ATECOFIN) REFERENCES COM_T_ATECOFIN (ID_COM_T_ATECOFIN);

ALTER TABLE COM_D_FM_ERRORI ADD
    FOREIGN KEY (ID_COM_D_UPL_DOCUMENTI) REFERENCES COM_D_UPL_DOCUMENTI (ID_COM_D_UPL_DOCUMENTI);

ALTER TABLE COM_D_FZ_ERRORI ADD
    FOREIGN KEY (ID_COM_D_UPL_DOCUMENTI) REFERENCES COM_D_UPL_DOCUMENTI (ID_COM_D_UPL_DOCUMENTI);

ALTER TABLE COM_D_ANAGRAFICA_SEDE_LAVORO ADD
    FOREIGN KEY (ID_COM_T_STATI_ESTERI) REFERENCES COM_T_STATI_ESTERI (ID_COM_T_STATI_ESTERI);

ALTER TABLE COM_D_ANAGRAFICA_SEDE_LAVORO ADD
    FOREIGN KEY (ID_COM_T_COMUNE) REFERENCES COM_T_COMUNE (ID_COM_T_COMUNE);

ALTER TABLE COM_D_TUTORE ADD
    FOREIGN KEY (ID_COM_T_GRADO_CONTRATTUALE) REFERENCES COM_T_GRADO_CONTRATTUALE (ID_COM_T_GRADO_CONTRATTUALE);

ALTER TABLE COM_D_TUTORE ADD
    FOREIGN KEY (ID_COM_ISTAT2001LIVELLO5) REFERENCES COM_T_ISTAT2001LIVELLO5 (ID_COM_ISTAT2001LIVELLO5);

ALTER TABLE COM_D_RAPPORTO ADD
    FOREIGN KEY (ID_COM_T_CATEG_TIROCINANTE) REFERENCES COM_T_CATEG_TIROCINANTE (ID_COM_T_CATEG_TIROCINANTE);

ALTER TABLE COM_D_RAPPORTO ADD
    FOREIGN KEY (ID_COM_T_LIVELLO_RETRIBUZIONE) REFERENCES COM_T_LIVELLO_RETRIBUZIONE (ID_COM_T_LIVELLO_RETRIBUZIONE);

ALTER TABLE COM_D_RAPPORTO ADD
    FOREIGN KEY (ID_COM_SEDE_LAVORO_PRECEDENTE) REFERENCES COM_D_SEDE_LAVORO (ID_COM_D_SEDE_LAVORO);

ALTER TABLE COM_D_RAPPORTO ADD
    FOREIGN KEY (ID_DATORE_DISTACCATARIO) REFERENCES COM_D_DATORE (ID_COM_D_DATORE);

ALTER TABLE COM_D_RAPPORTO ADD
    FOREIGN KEY (ID_COM_ISTAT2001LIVELLO5) REFERENCES COM_T_ISTAT2001LIVELLO5 (ID_COM_ISTAT2001LIVELLO5);

ALTER TABLE COM_D_RAPPORTO ADD
    FOREIGN KEY (ID_COM_T_TIPO_ORARIO) REFERENCES COM_T_TIPO_ORARIO (ID_COM_T_TIPO_ORARIO);

ALTER TABLE COM_D_RAPPORTO ADD
    FOREIGN KEY (ID_COM_T_TIPO_ATTO_L68) REFERENCES COM_T_TIPO_ATTO_L68 (ID_COM_T_TIPO_ATTO_L68);

ALTER TABLE COM_D_RAPPORTO ADD
    FOREIGN KEY (ID_COM_T_CPI) REFERENCES COM_T_CPI (ID_COM_T_CPI);

ALTER TABLE COM_D_RAPPORTO ADD
    FOREIGN KEY (ID_COM_D_COMUNICAZIONE) REFERENCES COM_D_COMUNICAZIONE (ID_COM_D_COMUNICAZIONE);

ALTER TABLE COM_D_RAPPORTO ADD
    FOREIGN KEY (ID_COM_T_TIPO_ENTE_PROM_TIR) REFERENCES COM_T_TIPO_ENTE_PROM_TIROCINIO (ID_COM_T_TIPO_ENTE_PROM_TIR);

ALTER TABLE COM_D_RAPPORTO ADD
    FOREIGN KEY (ID_COM_T_ENTE_PREVIDENZIALE) REFERENCES COM_T_ENTE_PREVIDENZIALE (ID_COM_T_ENTE_PREVIDENZIALE);

ALTER TABLE COM_D_RAPPORTO ADD
    FOREIGN KEY (ID_COM_T_CCNL) REFERENCES COM_T_CCNL (ID_COM_T_CCNL);

ALTER TABLE COM_D_RAPPORTO ADD
    FOREIGN KEY (ID_COM_D_TUTORE) REFERENCES COM_D_TUTORE (ID_COM_D_TUTORE);

ALTER TABLE COM_D_RAPPORTO ADD
    FOREIGN KEY (ID_COM_T_TIPOLOGIA_TIROCINIO) REFERENCES COM_T_TIPOLOGIA_TIROCINIO (ID_COM_T_TIPOLOGIA_TIROCINIO);

ALTER TABLE COM_D_RAPPORTO ADD
    FOREIGN KEY (ID_COM_T_TIPO_CONTRATTO) REFERENCES COM_T_TIPO_CONTRATTI (ID_COM_T_TIPO_CONTRATTO);

ALTER TABLE COM_D_RAPPORTO ADD
    FOREIGN KEY (ID_COM_T_CATEG_LAV_ASS_OBBL) REFERENCES COM_T_CATEG_LAV_ASS_OBBL (ID_COM_T_CATEG_LAV_ASS_OBBL);

ALTER TABLE COM_D_RAPPORTO ADD
    FOREIGN KEY (ID_AZI_UTILIZZATRICE) REFERENCES COM_D_DATORE (ID_COM_D_DATORE);

ALTER TABLE COM_D_RAPPORTO ADD
    FOREIGN KEY (ID_COM_T_CESSAZIONERL) REFERENCES COM_T_CESSAZIONERL (ID_COM_T_CESSAZIONERL);

ALTER TABLE COM_D_RAPPORTO ADD
    FOREIGN KEY (ID_COM_T_TRASFORMAZIONERL) REFERENCES COM_T_TRASFORMAZIONERL (ID_COM_T_TRASFORMAZIONERL);

ALTER TABLE COM_R_T_LISTA_FZ_ERRORI ADD
    FOREIGN KEY (ID_COM_D_FZ_ERRORI) REFERENCES COM_D_FZ_ERRORI (ID_COM_D_FZ_ERRORI);

ALTER TABLE COM_R_T_LISTA_FZ_ERRORI ADD
    FOREIGN KEY (ID_COM_T_LISTA_ERRORI_FZ) REFERENCES COM_T_LISTA_ERRORI_FZ (ID_COM_T_LISTA_ERRORI_FZ);

ALTER TABLE COM_D_ANAGRAFICA_LAVORATORE ADD
    FOREIGN KEY (ID_COMUNE_RES) REFERENCES COM_T_COMUNE (ID_COM_T_COMUNE);

ALTER TABLE COM_D_ANAGRAFICA_LAVORATORE ADD
    FOREIGN KEY (ID_COM_T_STATI_ESTERI_DOM) REFERENCES COM_T_STATI_ESTERI (ID_COM_T_STATI_ESTERI);

ALTER TABLE COM_D_ANAGRAFICA_LAVORATORE ADD
    FOREIGN KEY (ID_COMUNE_NASC) REFERENCES COM_T_COMUNE (ID_COM_T_COMUNE);

ALTER TABLE COM_D_ANAGRAFICA_LAVORATORE ADD
    FOREIGN KEY (ID_COM_T_MOTIVO_PERMESSO) REFERENCES COM_T_MOTIVO_PERMESSO (ID_COM_T_MOTIVO_PERMESSO);

ALTER TABLE COM_D_ANAGRAFICA_LAVORATORE ADD
    FOREIGN KEY (ID_COM_T_STATI_ESTERI_NASC) REFERENCES COM_T_STATI_ESTERI (ID_COM_T_STATI_ESTERI);

ALTER TABLE COM_D_ANAGRAFICA_LAVORATORE ADD
    FOREIGN KEY (ID_COM_T_CITTADINANZA) REFERENCES COM_T_CITTADINANZA (ID_COM_T_CITTADINANZA);

ALTER TABLE COM_D_ANAGRAFICA_LAVORATORE ADD
    FOREIGN KEY (ID_COM_T_STATI_ESTERI_RES) REFERENCES COM_T_STATI_ESTERI (ID_COM_T_STATI_ESTERI);

ALTER TABLE COM_D_ANAGRAFICA_LAVORATORE ADD
    FOREIGN KEY (ID_COM_T_LIVELLO_STUDIO) REFERENCES COM_T_LIVELLO_STUDIO (ID_COM_T_LIVELLO_STUDIO);

ALTER TABLE COM_D_ANAGRAFICA_LAVORATORE ADD
    FOREIGN KEY (ID_QUESTURA_RILASCIO_PERM_SOGG) REFERENCES COM_T_QUESTURA (ID_COM_T_QUESTURA);

ALTER TABLE COM_D_ANAGRAFICA_LAVORATORE ADD
    FOREIGN KEY (ID_COM_T_STATUS_STRANIERO) REFERENCES COM_T_STATUS_STRANIERO (ID_COM_T_STATUS_STRANIERO);

ALTER TABLE COM_D_ANAGRAFICA_LAVORATORE ADD
    FOREIGN KEY (ID_COMUNE_DOM) REFERENCES COM_T_COMUNE (ID_COM_T_COMUNE);

ALTER TABLE COM_R_COMUNICAZIONE_DATORE ADD
    FOREIGN KEY (ID_COM_D_COMUNICAZIONE) REFERENCES COM_D_COMUNICAZIONE (ID_COM_D_COMUNICAZIONE);

ALTER TABLE COM_R_COMUNICAZIONE_DATORE ADD
    FOREIGN KEY (ID_COM_T_TIPO_DATORE) REFERENCES COM_T_TIPO_DATORE (ID_COM_T_TIPO_DATORE);

ALTER TABLE COM_R_COMUNICAZIONE_DATORE ADD
    FOREIGN KEY (ID_COM_D_DATORE) REFERENCES COM_D_DATORE (ID_COM_D_DATORE);

ALTER TABLE COM_D_CAPACITA_FORMATIVA_MODB ADD
    FOREIGN KEY (ID_COM_T_COMUNE_L_R) REFERENCES COM_T_COMUNE (ID_COM_T_COMUNE);

ALTER TABLE COM_D_CAPACITA_FORMATIVA_MODB ADD
    FOREIGN KEY (ID_COM_T_COMUNE_AZI) REFERENCES COM_T_COMUNE (ID_COM_T_COMUNE);

ALTER TABLE COM_D_SOGGETTO_ABILITATO ADD
    FOREIGN KEY (ID_COM_T_COMUNE) REFERENCES COM_T_COMUNE (ID_COM_T_COMUNE);

ALTER TABLE COM_D_SOGGETTO_ABILITATO ADD
    FOREIGN KEY (ID_COM_TIPO_SOGGETTO_ABILITATO) REFERENCES COM_T_TIPO_SOGGETTO_ABILITATO (ID_COM_TIPO_SOGGETTO_ABILITATO);

ALTER TABLE COM_R_T_LISTA_WARNING ADD
    FOREIGN KEY (ID_COM_D_WARNING) REFERENCES COM_D_WARNING (ID_COM_D_WARNING);

ALTER TABLE COM_R_T_LISTA_WARNING ADD
    FOREIGN KEY (ID_COM_T_LISTA_WARNING) REFERENCES COM_T_LISTA_WARNING (ID_COM_T_LISTA_WARNING);

ALTER TABLE PARAMETRI_TIPO_COMUNICAZIONE ADD
    FOREIGN KEY (PV) REFERENCES PERSONALIZZAZIONE (PV);

ALTER TABLE PARAMETRI_TIPO_COMUNICAZIONE ADD
    FOREIGN KEY (TIPO) REFERENCES COM_T_TIPO_COMUNICAZIONE (TIPO);

ALTER TABLE COM_D_DICHIARAZIONI_UD ADD
    FOREIGN KEY (ID_COM_D_COMUNICAZIONE) REFERENCES COM_D_COMUNICAZIONE (ID_COM_D_COMUNICAZIONE);

ALTER TABLE COM_R_RAPPORTO_LAVORATORE ADD
    FOREIGN KEY (ID_COM_T_TIPO_RAPPORTO_AZIENDA) REFERENCES COM_T_TIPO_RAPPORTO_AZIENDA (ID_COM_T_TIPO_RAPPORTO_AZIENDA);

ALTER TABLE COM_R_RAPPORTO_LAVORATORE ADD
    FOREIGN KEY (ID_COM_T_TIPO_LAVORATORE) REFERENCES COM_T_TIPO_LAVORATORE (ID_COM_T_TIPO_LAVORATORE);

ALTER TABLE COM_R_RAPPORTO_LAVORATORE ADD
    FOREIGN KEY (ID_COM_D_LAVORATORE) REFERENCES COM_D_LAVORATORE (ID_COM_D_LAVORATORE);

ALTER TABLE COM_R_RAPPORTO_LAVORATORE ADD
    FOREIGN KEY (ID_COM_D_RAPPORTO) REFERENCES COM_D_RAPPORTO (ID_COM_D_RAPPORTO);

ALTER TABLE DELEGA ADD
    FOREIGN KEY (C_COM_DELEGANTE) REFERENCES COM_T_COMUNE (ID_COM_T_COMUNE);

ALTER TABLE DELEGA ADD
    FOREIGN KEY (PV_COMPETENZA) REFERENCES PERSONALIZZAZIONE (PV);

ALTER TABLE DELEGA ADD
    FOREIGN KEY (C_STATO_DELEGATO) REFERENCES COM_T_STATI_ESTERI (ID_COM_T_STATI_ESTERI);

ALTER TABLE DELEGA ADD
    FOREIGN KEY (C_COM_DELEGATO) REFERENCES COM_T_COMUNE (ID_COM_T_COMUNE);

ALTER TABLE DELEGA ADD
    FOREIGN KEY (C_STATO_DELEGANTE) REFERENCES COM_T_STATI_ESTERI (ID_COM_T_STATI_ESTERI);

ALTER TABLE COM_R_DATORE_SEDE ADD
    FOREIGN KEY (ID_COM_D_SEDE_LAVORO) REFERENCES COM_D_SEDE_LAVORO (ID_COM_D_SEDE_LAVORO);

ALTER TABLE COM_R_DATORE_SEDE ADD
    FOREIGN KEY (ID_COM_D_DATORE) REFERENCES COM_D_DATORE (ID_COM_D_DATORE);

ALTER TABLE COM_T_TITOLO_STUDIO ADD
    FOREIGN KEY (ID_COM_T_LIVELLO_STUDIO) REFERENCES COM_T_LIVELLO_STUDIO (ID_COM_T_LIVELLO_STUDIO);

ALTER TABLE COM_R_TIPOL_TIROC_CAT_TIROC ADD
    FOREIGN KEY (ID_COM_T_CATEG_TIROCINANTE) REFERENCES COM_T_CATEG_TIROCINANTE (ID_COM_T_CATEG_TIROCINANTE);

ALTER TABLE COM_R_TIPOL_TIROC_CAT_TIROC ADD
    FOREIGN KEY (ID_COM_T_TIPOLOGIA_TIROCINIO) REFERENCES COM_T_TIPOLOGIA_TIROCINIO (ID_COM_T_TIPOLOGIA_TIROCINIO);

ALTER TABLE COM_R_TIPOL_TIROC_CAT_TIROC ADD
    FOREIGN KEY (COD_UNITA_MISURA_DURATA) REFERENCES COM_T_UNITA_MISURA_DURATA (COD_UNITA_MISURA_DURATA);

ALTER TABLE COM_D_LEGALE_RAPPR ADD
    FOREIGN KEY (ID_COM_D_DATORE) REFERENCES COM_D_DATORE (ID_COM_D_DATORE);

ALTER TABLE COM_D_LEGALE_RAPPR ADD
    FOREIGN KEY (ID_COM_T_QUESTURA) REFERENCES COM_T_QUESTURA (ID_COM_T_QUESTURA);

ALTER TABLE COM_D_LEGALE_RAPPR ADD
    FOREIGN KEY (ID_COM_T_STATI_ESTERI_NASC) REFERENCES COM_T_STATI_ESTERI (ID_COM_T_STATI_ESTERI);

ALTER TABLE COM_D_LEGALE_RAPPR ADD
    FOREIGN KEY (ID_COM_T_COMUNE_NASC) REFERENCES COM_T_COMUNE (ID_COM_T_COMUNE);

ALTER TABLE COM_D_LEGALE_RAPPR ADD
    FOREIGN KEY (ID_COM_T_STATUS_STRANIERO) REFERENCES COM_T_STATUS_STRANIERO (ID_COM_T_STATUS_STRANIERO);

ALTER TABLE COM_D_LEGALE_RAPPR ADD
    FOREIGN KEY (ID_COM_T_CITTADINANZA) REFERENCES COM_T_CITTADINANZA (ID_COM_T_CITTADINANZA);

ALTER TABLE COM_D_LEGALE_RAPPR ADD
    FOREIGN KEY (ID_COM_T_MOTIVO_PERMESSO) REFERENCES COM_T_MOTIVO_PERMESSO (ID_COM_T_MOTIVO_PERMESSO);

ALTER TABLE COM_R_ANAG_DATORE_ANAG_SEDE ADD
    FOREIGN KEY (ID_COM_D_ANAGRAFIC_SEDE_LAVORO) REFERENCES COM_D_ANAGRAFICA_SEDE_LAVORO (ID_COM_D_ANAGRAFIC_SEDE_LAVORO);

ALTER TABLE COM_R_ANAG_DATORE_ANAG_SEDE ADD
    FOREIGN KEY (ID_COM_D_ANAGRAFICA_DATORE) REFERENCES COM_D_ANAGRAFICA_DATORE (ID_COM_D_ANAGRAFICA_DATORE);

ALTER TABLE PDF_DELEGA ADD
    FOREIGN KEY (ID_PDF_DELEGA) REFERENCES DELEGA (ID_DELEGA);

ALTER TABLE COM_R_AGEVOLAZIONERL ADD
    FOREIGN KEY (ID_COM_D_RAPPORTO) REFERENCES COM_D_RAPPORTO (ID_COM_D_RAPPORTO);

ALTER TABLE COM_R_AGEVOLAZIONERL ADD
    FOREIGN KEY (ID_COM_T_AGEVOLAZIONERL) REFERENCES COM_T_AGEVOLAZIONERL (ID_COM_T_AGEVOLAZIONERL);

ALTER TABLE COM_D_CAPACITA_FORMATIVA_MODA ADD
    FOREIGN KEY (ID_COM_T_COMUNE_L_R) REFERENCES COM_T_COMUNE (ID_COM_T_COMUNE);

ALTER TABLE COM_D_CAPACITA_FORMATIVA_MODA ADD
    FOREIGN KEY (ID_COM_T_COMUNE_AZI) REFERENCES COM_T_COMUNE (ID_COM_T_COMUNE);

ALTER TABLE ANAGRAFICA_DELEGATO ADD
    FOREIGN KEY (C_STATO_ESTERO_RES) REFERENCES COM_T_STATI_ESTERI (ID_COM_T_STATI_ESTERI);

ALTER TABLE ANAGRAFICA_DELEGATO ADD
    FOREIGN KEY (C_COM_RES) REFERENCES COM_T_COMUNE (ID_COM_T_COMUNE);

ALTER TABLE ANAGRAFICA_DELEGATO ADD
    FOREIGN KEY (PV) REFERENCES PERSONALIZZAZIONE (PV);

ALTER TABLE ANAGRAFICA_DELEGATO ADD
    FOREIGN KEY (C_COM_NASC) REFERENCES COM_T_COMUNE (ID_COM_T_COMUNE);

ALTER TABLE ANAGRAFICA_DELEGATO ADD
    FOREIGN KEY (ID_COM_D_SOGGETTO_ABILITATO) REFERENCES COM_D_SOGGETTO_ABILITATO (ID_COM_D_SOGGETTO_ABILITATO);

ALTER TABLE ANAGRAFICA_DELEGATO ADD
    FOREIGN KEY (C_STATO_ESTERO_DOM) REFERENCES COM_T_STATI_ESTERI (ID_COM_T_STATI_ESTERI);

ALTER TABLE ANAGRAFICA_DELEGATO ADD
    FOREIGN KEY (C_STATO_ESTERO) REFERENCES COM_T_STATI_ESTERI (ID_COM_T_STATI_ESTERI);

ALTER TABLE ANAGRAFICA_DELEGATO ADD
    FOREIGN KEY (C_COM_DOM) REFERENCES COM_T_COMUNE (ID_COM_T_COMUNE);

ALTER TABLE CARICA_PERSONA_PV ADD
    FOREIGN KEY (PV) REFERENCES PERSONALIZZAZIONE (PV);

ALTER TABLE COM_R_PERSONALIZ_PROVINCE ADD
    FOREIGN KEY (ID_COM_T_PROVINCIA) REFERENCES COM_T_PROVINCIA (ID_COM_T_PROVINCIA);

ALTER TABLE ENTE_DELEGATO ADD
    FOREIGN KEY (C_ENTE, PV) REFERENCES ANAGRAFICA_ENTE (C_ENTE,PV);

ALTER TABLE ENTE_DELEGATO ADD
    FOREIGN KEY (CF_DELEGATO, TIPO_ANAGRAFICA) REFERENCES ANAGRAFICA_DELEGATO (CF_DELEGATO,TIPO_ANAGRAFICA);

ALTER TABLE COM_D_DESTINATARI_IGNORARE ADD
    FOREIGN KEY (ID_COM_T_TIPO_DESTINATARIO) REFERENCES COM_T_TIPO_DESTINATARIO (ID_COM_T_TIPO_DESTINATARIO);

ALTER TABLE COM_D_DESTINATARI_IGNORARE ADD
    FOREIGN KEY (ID_COM_D_COMUNICAZIONE) REFERENCES COM_D_COMUNICAZIONE (ID_COM_D_COMUNICAZIONE);

ALTER TABLE PERSONALIZZAZIONE ADD
    FOREIGN KEY (PV) REFERENCES COM_R_PERSONALIZ_PROVINCE (PV);

ALTER TABLE COM_D_WARNING ADD
    FOREIGN KEY (ID_COM_D_UPL_DOCUMENTI) REFERENCES COM_D_UPL_DOCUMENTI (ID_COM_D_UPL_DOCUMENTI);

ALTER TABLE COM_D_COMUNICAZIONE ADD
    FOREIGN KEY (ID_COM_T_TIPO_SOMMINISTRAZIONE) REFERENCES COM_T_TIPO_SOMMINISTRAZIONE (ID_COM_T_TIPO_SOMMINISTRAZIONE);

ALTER TABLE COM_D_COMUNICAZIONE ADD
    FOREIGN KEY (ID_COM_TU_PRECEDENTE_ANNULLO) REFERENCES COM_D_COMUNICAZIONE (ID_COM_D_COMUNICAZIONE);

ALTER TABLE COM_D_COMUNICAZIONE ADD
    FOREIGN KEY (ID_COM_T_PROVINCIA) REFERENCES COM_T_PROVINCIA (ID_COM_T_PROVINCIA);

ALTER TABLE COM_D_COMUNICAZIONE ADD
    FOREIGN KEY (ID_COM_T_TIPO_TRASFERIMENTO) REFERENCES COM_T_TIPO_TRASFERIMENTO (ID_COM_T_TIPO_TRASFERIMENTO);

ALTER TABLE COM_D_COMUNICAZIONE ADD
    FOREIGN KEY (ID_COM_T_TIPO_PROVENIENZA) REFERENCES COM_T_TIPO_PROVENIENZA (ID_COM_T_TIPO_PROVENIENZA);

ALTER TABLE COM_D_COMUNICAZIONE ADD
    FOREIGN KEY (ID_COM_T_STATO_COMUNICAZIONE) REFERENCES COM_T_STATO_COMUNICAZIONE (ID_COM_T_STATO_COMUNICAZIONE);

ALTER TABLE COM_D_COMUNICAZIONE ADD
    FOREIGN KEY (ID_COM_TIPO_SOGGETTO_ABILITATO) REFERENCES COM_T_TIPO_SOGGETTO_ABILITATO (ID_COM_TIPO_SOGGETTO_ABILITATO);

ALTER TABLE COM_D_COMUNICAZIONE ADD
    FOREIGN KEY (ID_COM_T_CPI) REFERENCES COM_T_CPI (ID_COM_T_CPI);

ALTER TABLE COM_D_COMUNICAZIONE ADD
    FOREIGN KEY (ID_COM_T_TIPO_COMUNICAZIONE) REFERENCES COM_T_TIPO_COMUNICAZIONE (ID_COM_T_TIPO_COMUNICAZIONE);

ALTER TABLE COM_D_COMUNICAZIONE ADD
    FOREIGN KEY (ID_COM_T_TIPO_ACQUISIZIONE) REFERENCES COM_T_TIPO_ACQUISIZIONE (ID_COM_T_TIPO_ACQUISIZIONE);

ALTER TABLE COM_D_COMUNICAZIONE ADD
    FOREIGN KEY (ID_COM_TU_DA_RETTIFICARE) REFERENCES COM_D_COMUNICAZIONE (ID_COM_D_COMUNICAZIONE);

ALTER TABLE COM_D_COMUNICAZIONE ADD
    FOREIGN KEY (ID_COM_D_COMUNICAZ_GRUPPO) REFERENCES COM_D_COMUNICAZIONE (ID_COM_D_COMUNICAZIONE);

ALTER TABLE COM_D_COMUNICAZIONE ADD
    FOREIGN KEY (ID_COM_T_TIPO_COMUNICAZIONE_TU) REFERENCES COM_T_TIPO_COMUNICAZIONE_TU (ID_COM_T_TIPO_COMUNICAZIONE_TU);

ALTER TABLE COM_D_COMUNICAZIONE ADD
    FOREIGN KEY (ID_COM_T_TIPO_TRACCIATO) REFERENCES COM_T_TIPO_TRACCIATO (ID_COM_T_TIPO_TRACCIATO);

ALTER TABLE COM_D_COMUNICAZIONE ADD
    FOREIGN KEY (ID_COM_D_DELEGATO) REFERENCES COM_D_DELEGATO (ID_COM_D_DELEGATO);

ALTER TABLE COM_D_ELABORATI_OK ADD
    FOREIGN KEY (ID_COM_D_UPL_DOCUMENTI) REFERENCES COM_D_UPL_DOCUMENTI (ID_COM_D_UPL_DOCUMENTI);

ALTER TABLE COM_R_REG_TRACCIATO_CONTRATTO ADD
    FOREIGN KEY (ID_COM_T_TIPO_COMUNICAZIONE) REFERENCES COM_T_TIPO_COMUNICAZIONE (ID_COM_T_TIPO_COMUNICAZIONE);

ALTER TABLE COM_R_REG_TRACCIATO_CONTRATTO ADD
    FOREIGN KEY (ID_COM_T_TIPO_CONTRATTO) REFERENCES COM_T_TIPO_CONTRATTI (ID_COM_T_TIPO_CONTRATTO);

ALTER TABLE COM_R_REG_TRACCIATO_CONTRATTO ADD
    FOREIGN KEY (ID_COM_T_TIPO_TRACCIATO) REFERENCES COM_T_TIPO_TRACCIATO (ID_COM_T_TIPO_TRACCIATO);
