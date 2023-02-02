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

public class ParametriConstants {

	public static final Long MAX_FILE_DIMENSION_IN_MB = 1L;
	public static final Long MAX_FILE_DIMENSION_IN_KB = 2L;
	public static final Long MAX_NUMBER_OF_FILE_ADMITTED_IN_ZIP = 3L;
	public static final Long CLASS_FILE_TO_BE_INVOKED = 4L;
	public static final Long MAIL_MITTENTE = 5L;
	public static final Long MAIL_DIRECTORY_FILE_RAPPORTI = 6L;
	public static final Long MAIL_SERVER = 7L;
	public static final Long MAIL_IN_BCC = 8L;
	
	public static final Long RITRASMISSIONI_PARAMETRO_LOCK=9L;
	public static final Long RITRASMISSIONI_ORA_ULTIMA_RITRASMISSIONE=10L;
	public static final Long SECONDI_ATTESA_RITRASMISSIONI=11L;
	public static final Long MINUTI_ATTESA_PRIMA_DI_RITRASMETTERE=12L;
	
	
	// Aggiunto parametro nella tavola com_t_commax_parametri,
	// per pilotare il controllo sul crc del file uploadato.
	public static final Long IS_CONTROLLO_CRC_ABILITATO =13L;
	
	public static final Long IS_VERIFICA_ABILITATA =14L;
	public static final Long IS_UNIDOM_ABILITATO =15L;
	
	public static final Long NUMERO_VERSIONE_TRACCIATO_PER_SPICOM = 16L;
	
	public static final Long COMUNICAZIONE_ANNULLAMENTO_FUORI_TERMINE_UL_ASSUNZIONE = 17L;
	public static final Long COMUNICAZIONE_ANNULLAMENTO_FUORI_TERMINE_UL_PROROGA = 18L;
	public static final Long COMUNICAZIONE_ANNULLAMENTO_FUORI_TERMINE_UL_TRASFORMAZIONE_TRASFERIMENTO_DISTACCO = 19L;
	public static final Long COMUNICAZIONE_ANNULLAMENTO_FUORI_TERMINE_UL_CESSAZIONE = 20L;
	public static final Long COMUNICAZIONE_RETTIFICA_FUORI_TERMINE_UL_ASSUNZIONE = 21L;
	public static final Long COMUNICAZIONE_RETTIFICA_FUORI_TERMINE = 22L;
	
    public static final Long DATA_RIFERIMENTO_INIZIO_RAPPORTO_TRASFORMAZIONE_AI = 23L;    
    public static final Long DATA_RIFERIMENTO_INIZIO_RAPPORTO_TRASFORMAZIONE_FF = 24L;
    	
    public static final Long SCADENZA_PERMESSO = 27L;    
    public static final Long DATA_CONVENZIONALE_IN_ATTESA_DI_PERMESSO = 25L;
    public static final Long DATA_CONVENZIONALE_CARTA_PERMANENTE = 26L;
    

    public static final Long PROTOCOLLO_COMUNICAZIONE_IUP = 1200L;//protocollo.IUP.protocolloComunicazione

    
    public static final Long VINCOLI_CATEG_LAV_ASS_OBBL_AMMESSE_UNISOMM = 1501L;
    public static final Long VINCOLI_LISTA_TIPI_CONTRATTO_CCNL_AMMESSO_ND = 1502L;
    
    public static final Long CONTROLLI_BLOCCANTE_RETRIBUZIONE_UGUALEZERO_ATTIVO = 28L;
    public static final Long ELENCO_CONTRATTI_AMMETTONO_RETRIBUZIONEZERO_SE_CONTROLLO_BLOCCANTE_ATTIVO = 29L;
    public static final Long CONTROLLI_RETRIBUZIONECALCOLATA_RETRIBUZIONEINSERITA_ATTIVO = 30L;
    public static final Long CONTROLLI_RETRIBUZIONE_MESSAGGIO_AVVISO = 31L;
    
}
