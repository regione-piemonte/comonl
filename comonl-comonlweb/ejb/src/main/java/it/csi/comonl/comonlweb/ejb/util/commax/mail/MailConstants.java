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
package it.csi.comonl.comonlweb.ejb.util.commax.mail;

public interface MailConstants {
	
	final String MAIL_SUBJECT = "Notifica di presentazione prospetto informativo";
	
	final String SCOPERTURE_TEMPLATE = "Si segnala che il presente Prospetto presenta una Scopertura su {0} quindi \n"
			+ " si evidenzia la necessità di ottemperare agli obblighi di legge entro 60 giorni dalla data di insorgenza degli \n"
			+ " stessi";

	final String PERIODO_TEMPLATE = "La dichiarazione fatta durante l''anno in corso non sostituisce la dichiarazione \"ufficiale\" da effettuare nel \n"
			+ " periodo definito dal Ministero, dato che è intercorsa una variazione di organico rispetto alla dichiarazione \n"
			+ " precedente \n";
	
	final String MAIL_PROSPETTO_TEMPLATE = "{0}\n"    // subject
			 + "per l''azienda {1} \n"                // cfAzienda
			 + "{2} \n"                               //denomAzienda
			 + "Protocollo del prospetto (anno/numero): \n"
			 + "{3} / {4} \n\n"                       // annoProt nProt
			 + "Data di protocollazione: \n"          // data protocollazione
			 + "{5} \n\n"                             //dataProt
			 + "Codice regionale:\n"
			 + "{6} \n\n\n"                           // codiceRegionale
			 + "{7} \n\n"                             // Scoperture
			 + "{8} \n\n"                             // termini
			 + "Sistema automatico SIL Piemonte \n\n\n\n"; // firma                                   
	
//	if (codiceRegionale != null) {
//		messaggio.append(codiceRegionale + "\n" + "\n" + "\n");
//	} else {
//		messaggio.append("Non presente." + "\n" + "\n" + "\n");
//	}
	
	
}
