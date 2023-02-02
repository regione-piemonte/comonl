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
package it.csi.comonl.comonlweb.ejb.util.commax.manager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.facade.BaseFacade;
import it.csi.comonl.comonlweb.ejb.business.be.facade.ComunicazioneStatelessFacade;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ParametriConstants;
import it.csi.comonl.comonlweb.ejb.util.conf.ConfigurationValue;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CommaxParametri;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;


@Singleton
@Startup
@TransactionManagement(value=TransactionManagementType.BEAN)
public class EleborazioneProcessiManager extends BaseFacade {
	
	@EJB
	private ComunicazioneStatelessFacade comunicazioneStatelessFacade;
	
	@Inject
	private CommonDad commonDad;


	protected final LogUtil log = new LogUtil(EleborazioneProcessiManager.class);

	protected String srcPath = EleborazioneProcessiManager.class.getName();

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


	@Resource
	private TimerService timerService;

	@PostConstruct
	public void init() {
		TimerConfig timerConfig = new TimerConfig("TIMER_ELABORAZIONI_APPESE", false);
		Random rand = new Random();
		/**
		 * occorre inizializzare il primo tick in modo casuale per evitare i tick contemporanei sui vari cluster
		 */
		Integer initialDuration = 60*1000 + rand.nextInt(10*60*1000); 
		Integer interval = 5*60*1000; // ping ogni 5 minuti
		
		timerService.createIntervalTimer(initialDuration, interval, timerConfig);
	}	
	
	
	@Timeout
	@Lock(LockType.WRITE)
	public void tickEvent(Timer timer) {
		if(!configurationHelper.getProperty(ConfigurationValue.APPLICATION_RUNTIME).equalsIgnoreCase("DEV")) {
			eseguiRitrasmissioni();
		}
	}
	
	public void eseguiRitrasmissioni() {

		CommaxParametri paramLock = commonDad.getParametroCommaxById(ParametriConstants.RITRASMISSIONI_PARAMETRO_LOCK);
		try {
			if(paramLock.getValoreParametro().equals("0")) { // è in atto una ritrasmissione
				return;
			}

			CommaxParametri commaxParUltimaRitr = commonDad.getParametroCommaxById(ParametriConstants.RITRASMISSIONI_ORA_ULTIMA_RITRASMISSIONE);
			CommaxParametri commaxParMinAttesa = commonDad.getParametroCommaxById(ParametriConstants.MINUTI_ATTESA_PRIMA_DI_RITRASMETTERE);

			LocalDateTime dataUltimaTrasm = LocalDateTime.parse(commaxParUltimaRitr.getValoreParametro(), formatter);
			LocalDateTime dataProssimaRitr = dataUltimaTrasm.plusSeconds(Long.parseLong(commaxParMinAttesa.getValoreParametro()));

			if(dataProssimaRitr.compareTo(LocalDateTime.now()) > 0 ) {
				return;

			}		
			
			sessionContext.getUserTransaction().begin();
			boolean locked = commonDad.tryLockSemaforo();
			sessionContext.getUserTransaction().commit();
			if(!locked) {
				return;
			}
			
			comunicazioneStatelessFacade.ritrasmettiComunicazioni(); // funzione sincrona
			
		} catch(Exception e){
			log.error("tickEvent", e);
			
		} finally {
			try {
				sessionContext.getUserTransaction().begin();
				commonDad.unLockSemaforo();
				sessionContext.getUserTransaction().commit();

			} catch(Exception ee) {
				log.error("tickEvent", ee);

				try {
					sessionContext.getUserTransaction().rollback();
				} catch(Exception eee) {}
			}
		}		
	}
	
}
