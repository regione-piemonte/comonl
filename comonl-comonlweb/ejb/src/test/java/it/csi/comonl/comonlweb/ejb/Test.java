/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - EJB submodule
 * %%
 * Copyright (C) 2022 CSI Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.comonlweb.ejb;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;

public class Test {
	public static void main(String[] args) {

		Comunicazione a = new Comunicazione();
		a.setIdRapportoLavoro(5L);
		Comunicazione b = new Comunicazione();
		b.setIdRapportoLavoro(null);
		Comunicazione c = new Comunicazione();
		c.setIdRapportoLavoro(2L);
		
		List<Comunicazione> list = new ArrayList<Comunicazione>();
		list.add(a);
		list.add(b);
		list.add(c);
		
		List<Comunicazione> listConId = list.stream().filter(x->x.getIdRapportoLavoro()!=null).collect(Collectors.toList());
		
		Comunicazione minByIdRapporto = listConId
			.stream()
			.min(Comparator.comparing(Comunicazione::getIdRapportoLavoro))
			.orElseThrow(NoSuchElementException::new);
		
		System.out.println("================minByIdRapporto======================");
		System.out.println(minByIdRapporto.getIdRapportoLavoro());
	}
}
