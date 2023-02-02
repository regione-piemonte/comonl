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

/**
 * Censimento di tutte le parti comuni dei messaggi da concatenare al prefisso che identifica
 * il tipoTracciato/tipoComunicazione
 * 
 * 
 * 
 * all'interno di questo file ci sono le costanti Stringa con la parte comune del nome della costante che rappresenta il messaggio
 * 
 * ESEMPIO :  MESSAGGIO: ConstantErrorsKey.DATI_ASSUNZIONE_DATA_FINE_PERIODO_FORMATIVO_NON_VALORIZZATO
 *            MESSAGGIO: ConstantErrorsKey.DATI_CESSAZIONE_DATA_FINE_PERIODO_FORMATIVO_NON_VALORIZZATO
 *            ChiaveDB : constanterror.properties = DATI_ASSUNZIONE_DATA_FINE_PERIODO_FORMATIVO_NON_VALORIZZATO = 70035
 *            ChiaveDB : constanterror.properties = DATI_CESSAZIONE_DATA_FINE_PERIODO_FORMATIVO_NON_VALORIZZATO = 80042
 *            
 *            questo file:  ConstantErrorsMessagesParteComune - prefisso = DATI_ASSUNZIONE
 *            questo file:  ConstantErrorsMessagesParteComune - prefisso = DATI_CESSAZIONE
 *            questo file:  ConstantErrorsMessagesParteComune - msg = _FLAG_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATO = "_FLAG_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATO"
 */

public class ConstantErrorsMessagesParteComune {

	/* PREFISSI ************************************** */
	public static String DATI_TRASFORMAZIONE = "DATI_TRASFORMAZIONE";
	public static String DATI_ASSUNZIONE = "DATI_ASSUNZIONE";
	public static String DATI_PROROGA = "DATI_PROROGA";
	public static String DATI_CESSAZIONE = "DATI_CESSAZIONE";
	public static String RAPPLAV_VARDATORI = "RAPPLAV_VARDATORI";
	public static String MISSIONE = "MISSIONE";

	/* MESSAGGIO COMUNI************************************** */
	public static final String _FLAG_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATO = "_FLAG_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATO";
	public static final String _CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATA = "_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATA";
	public static final String _CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_AMMESSA = "_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_AMMESSA";
	public static final String _FLAG_ASSUNZIONE_OBBLIGATORIA_CATEGORIA_LAVORATORE_INCONGRUENTI = "_FLAG_ASSUNZIONE_OBBLIGATORIA_CATEGORIA_LAVORATORE_INCONGRUENTI";


	public static final String _DATA_FINE_PERIODO_FORMATIVO_NON_VALORIZZATO = "_DATA_FINE_PERIODO_FORMATIVO_NON_VALORIZZATO";
	public static final String _DATA_FINE_PERIODO_FORMATIVO_NON_AMMESSO = "_DATA_FINE_PERIODO_FORMATIVO_NON_AMMESSO";
	public static final String _DATA_FINE_PERIODO_FORMATIVO_DIVERSA_DA_DATA_FINE_RAPPORTO = "_DATA_FINE_PERIODO_FORMATIVO_DIVERSA_DA_DATA_FINE_RAPPORTO";
	public static final String _DATA_INIZIO_RAPPORTO_SUCCESSIVA_ALLA_DATA_FINE_PERIODO_FORMATIVO = "_DATA_INIZIO_RAPPORTO_SUCCESSIVA_ALLA_DATA_FINE_PERIODO_FORMATIVO";
	
	/* ============================================================================= */
	/* STRINGHE PER INDIVIDUAZIONE ID TABELLA ************************************** */
	/* ============================================================================= */

	/* ASSUNZIONE*/
	public static final String DATI_ASSUNZIONE_DATA_FINE_PERIODO_FORMATIVO_NON_VALORIZZATO = "DATI_ASSUNZIONE_DATA_FINE_PERIODO_FORMATIVO_NON_VALORIZZATO";
	public static final String DATI_ASSUNZIONE_DATA_FINE_PERIODO_FORMATIVO_NON_AMMESSO = "DATI_ASSUNZIONE_DATA_FINE_PERIODO_FORMATIVO_NON_AMMESSO";
	public static final String DATI_ASSUNZIONE_DATA_FINE_PERIODO_FORMATIVO_DIVERSA_DA_DATA_FINE_RAPPORTO = "DATI_ASSUNZIONE_DATA_FINE_PERIODO_FORMATIVO_DIVERSA_DA_DATA_FINE_RAPPORTO";
	public static final String DATI_ASSUNZIONE_DATA_INIZIO_RAPPORTO_SUCCESSIVA_ALLA_DATA_FINE_PERIODO_FORMATIVO = "DATI_ASSUNZIONE_DATA_INIZIO_RAPPORTO_SUCCESSIVA_ALLA_DATA_FINE_PERIODO_FORMATIVO";
	public static final String DATI_ASSUNZIONE_FLAG_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATO = "DATI_ASSUNZIONE_FLAG_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATO";
	public static final String DATI_ASSUNZIONE_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATA = "DATI_ASSUNZIONE_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATA";
	public static final String DATI_ASSUNZIONE_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_AMMESSA = "DATI_ASSUNZIONE_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_AMMESSA";
	public static final String DATI_ASSUNZIONE_FLAG_ASSUNZIONE_OBBLIGATORIA_CATEGORIA_LAVORATORE_INCONGRUENTI = "DATI_ASSUNZIONE_FLAG_ASSUNZIONE_OBBLIGATORIA_CATEGORIA_LAVORATORE_INCONGRUENTI";

	/* CESSAZIONE */
	public static final String DATI_CESSAZIONE_DATA_FINE_PERIODO_FORMATIVO_NON_VALORIZZATO = "DATI_CESSAZIONE_DATA_FINE_PERIODO_FORMATIVO_NON_VALORIZZATO";
	public static final String DATI_CESSAZIONE_DATA_FINE_PERIODO_FORMATIVO_NON_AMMESSO = "DATI_CESSAZIONE_DATA_FINE_PERIODO_FORMATIVO_NON_AMMESSO";
	public static final String DATI_CESSAZIONE_DATA_FINE_PERIODO_FORMATIVO_DIVERSA_DA_DATA_FINE_RAPPORTO = "DATI_CESSAZIONE_DATA_FINE_PERIODO_FORMATIVO_DIVERSA_DA_DATA_FINE_RAPPORTO";
	public static final String DATI_CESSAZIONE_DATA_INIZIO_RAPPORTO_SUCCESSIVA_ALLA_DATA_FINE_PERIODO_FORMATIVO = "DATI_CESSAZIONE_DATA_INIZIO_RAPPORTO_SUCCESSIVA_ALLA_DATA_FINE_PERIODO_FORMATIVO";
	public static final String DATI_CESSAZIONE_FLAG_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATO = "DATI_CESSAZIONE_FLAG_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATO";
	public static final String DATI_CESSAZIONE_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATA = "DATI_CESSAZIONE_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATA";
	public static final String DATI_CESSAZIONE_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_AMMESSA = "DATI_CESSAZIONE_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_AMMESSA";
	public static final String DATI_CESSAZIONE_FLAG_ASSUNZIONE_OBBLIGATORIA_CATEGORIA_LAVORATORE_INCONGRUENTI = "DATI_CESSAZIONE_FLAG_ASSUNZIONE_OBBLIGATORIA_CATEGORIA_LAVORATORE_INCONGRUENTI";

	/* TRASFORMAZIONE */
	public static final String DATI_TRASFORMAZIONE_DATA_FINE_PERIODO_FORMATIVO_NON_VALORIZZATO = "DATI_TRASFORMAZIONE_DATA_FINE_PERIODO_FORMATIVO_NON_VALORIZZATO";
	public static final String DATI_TRASFORMAZIONE_DATA_FINE_PERIODO_FORMATIVO_NON_AMMESSO = "DATI_TRASFORMAZIONE_DATA_FINE_PERIODO_FORMATIVO_NON_AMMESSO";
	public static final String DATI_TRASFORMAZIONE_DATA_FINE_PERIODO_FORMATIVO_DIVERSA_DA_DATA_FINE_RAPPORTO = "DATI_TRASFORMAZIONE_DATA_FINE_PERIODO_FORMATIVO_DIVERSA_DA_DATA_FINE_RAPPORTO";
	public static final String DATI_TRASFORMAZIONE_DATA_INIZIO_RAPPORTO_SUCCESSIVA_ALLA_DATA_FINE_PERIODO_FORMATIVO = "DATI_TRASFORMAZIONE_DATA_INIZIO_RAPPORTO_SUCCESSIVA_ALLA_DATA_FINE_PERIODO_FORMATIVO";
	public static final String DATI_TRASFORMAZIONE_FLAG_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATO = "DATI_TRASFORMAZIONE_FLAG_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATO";
	public static final String DATI_TRASFORMAZIONE_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATA = "DATI_TRASFORMAZIONE_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATA";
	public static final String DATI_TRASFORMAZIONE_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_AMMESSA = "DATI_TRASFORMAZIONE_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_AMMESSA";
	public static final String DATI_TRASFORMAZIONE_FLAG_ASSUNZIONE_OBBLIGATORIA_CATEGORIA_LAVORATORE_INCONGRUENTI = "DATI_TRASFORMAZIONE_FLAG_ASSUNZIONE_OBBLIGATORIA_CATEGORIA_LAVORATORE_INCONGRUENTI";

	/* PROROGA */
	public static final String DATI_PROROGA_DATA_FINE_PERIODO_FORMATIVO_NON_AMMESSO = "DATI_PROROGA_DATA_FINE_PERIODO_FORMATIVO_NON_AMMESSO";
	public static final String DATI_PROROGA_DATA_FINE_PERIODO_FORMATIVO_NON_VALORIZZATO = "DATI_PROROGA_DATA_FINE_PERIODO_FORMATIVO_NON_VALORIZZATO";
	public static final String DATI_PROROGA_DATA_FINE_PERIODO_FORMATIVO_DIVERSA_DA_DATA_FINE_RAPPORTO = "DATI_PROROGA_DATA_FINE_PERIODO_FORMATIVO_DIVERSA_DA_DATA_FINE_RAPPORTO";
	public static final String DATI_PROROGA_DATA_INIZIO_RAPPORTO_SUCCESSIVA_ALLA_DATA_FINE_PERIODO_FORMATIVO = "DATI_PROROGA_DATA_INIZIO_RAPPORTO_SUCCESSIVA_ALLA_DATA_FINE_PERIODO_FORMATIVO";
	public static final String DATI_PROROGA_FLAG_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATO = "DATI_PROROGA_FLAG_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATO";
	public static final String DATI_PROROGA_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATA = "DATI_PROROGA_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATA";
	public static final String DATI_PROROGA_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_AMMESSA = "DATI_PROROGA_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_AMMESSA";
	public static final String DATI_PROROGA_FLAG_ASSUNZIONE_OBBLIGATORIA_CATEGORIA_LAVORATORE_INCONGRUENTI = "DATI_PROROGA_FLAG_ASSUNZIONE_OBBLIGATORIA_CATEGORIA_LAVORATORE_INCONGRUENTI";

	/* MISSIONE */
	public static final String MISSIONE_FLAG_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATO = "MISSIONE_FLAG_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATO";
	public static final String MISSIONE_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATA = "MISSIONE_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATA";
	public static final String MISSIONE_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_AMMESSA = "MISSIONE_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_AMMESSA";
	public static final String MISSIONE_FLAG_ASSUNZIONE_OBBLIGATORIA_CATEGORIA_LAVORATORE_INCONGRUENTI = "MISSIONE_FLAG_ASSUNZIONE_OBBLIGATORIA_CATEGORIA_LAVORATORE_INCONGRUENTI";

	/* VARDATORI*/
	public static final String RAPPLAV_VARDATORI_DATA_FINE_PERIODO_FORMATIVO_NON_VALORIZZATO = "RAPPLAV_VARDATORI_DATA_FINE_PERIODO_FORMATIVO_NON_VALORIZZATO";
	public static final String RAPPLAV_VARDATORI_DATA_FINE_PERIODO_FORMATIVO_NON_AMMESSO = "RAPPLAV_VARDATORI_DATA_FINE_PERIODO_FORMATIVO_NON_AMMESSO";
	public static final String RAPPLAV_VARDATORI_DATA_FINE_PERIODO_FORMATIVO_DIVERSA_DA_DATA_FINE_RAPPORTO = "RAPPLAV_VARDATORI_DATA_FINE_PERIODO_FORMATIVO_DIVERSA_DA_DATA_FINE_RAPPORTO";
	public static final String RAPPLAV_VARDATORI_DATA_INIZIO_RAPPORTO_SUCCESSIVA_ALLA_DATA_FINE_PERIODO_FORMATIVO = "RAPPLAV_VARDATORI_DATA_INIZIO_RAPPORTO_SUCCESSIVA_ALLA_DATA_FINE_PERIODO_FORMATIVO";
	public static final String RAPPLAV_VARDATORI_FLAG_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATO = "RAPPLAV_VARDATORI_FLAG_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATO";
	public static final String RAPPLAV_VARDATORI_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATA = "RAPPLAV_VARDATORI_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_VALORIZZATA";
	public static final String RAPPLAV_VARDATORI_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_AMMESSA = "RAPPLAV_VARDATORI_CATEGORIA_LAVORATORE_ASSUNZIONE_OBBLIGATORIA_NON_AMMESSA";
	public static final String RAPPLAV_VARDATORI_FLAG_ASSUNZIONE_OBBLIGATORIA_CATEGORIA_LAVORATORE_INCONGRUENTI = "RAPPLAV_VARDATORI_FLAG_ASSUNZIONE_OBBLIGATORIA_CATEGORIA_LAVORATORE_INCONGRUENTI";

	
}
