/* -------------------------------------------------------------------- */
/* Copyright Regione Piemonte - 2022									*/
/* SPDX-License-Identifier: EUPL-1.2-or-later                           */
/* Target DBMS:           Oracle 19c                                    */
/* Project name:          COMONL                                        */
/* Author:                CSI Piemonte                                  */
/* Script type:           Database creation script                      */
/* Created on:            2022-11-23		                            */
/* -------------------------------------------------------------------- */

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
