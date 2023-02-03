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
package it.csi.comonl.comonlweb.lib.util;

public class CamelCaseUtil {

	public static void main(String[] args) {
		
		
		String s = "	private UUID ente_id;\r\n" + 
				"	private Integer impegno_anno_esercizio;\r\n" + 
				"	private Integer impegno_anno;\r\n" + 
				"	private Integer impegno_numero;;\r\n" + 
				"	private String impegno_descrizione;\r\n" + 
				"	private Integer numero_capitolo;\r\n" + 
				"	private Integer numero_articolo;\r\n" + 
				"	private String descrizione_capitolo;\r\n" + 
				"	private Integer provvedimento_anno;\r\n" + 
				"	private Integer provvedimento_numero;\r\n" + 
				"	private String provvedimento_settore;\r\n" + 
				"	private Fornitore fornitore;\r\n" + 
				"	private BigDecimal importo_iniziale;\r\n" + 
				"	private BigDecimal importo_attuale;\r\n" + 
				"	private String stato;\r\n" + 
				"	private BigDecimal liq_anno_prec;";
		
		String out = "";
		for (int i = 0; i <s.length(); i++) {
			char c = s.charAt(i);
			if (c == '_') {
				i++;
				c = s.charAt(i);
				out+= ("" + c).toUpperCase();
			} else {
				out+= c;
			}
		}
		System.out.println(out);

	}

}
