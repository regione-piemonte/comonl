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
package it.csi.comonl.comonlweb.ejb.business.be.dad;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import it.csi.comonl.comonlweb.ejb.business.be.dad.sort.ComunicazioneSort;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDComunicazioneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDDatoreDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDDelegatoDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDElaboratiOkDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDFzErroriDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDImportErroreDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDLegaleRapprDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDRapportoDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDTutoreDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComDUplDocumentiDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComRComunicazioneDatoreDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComRDatoreSedeDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComRDepCommaxComunicDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComRRapportoLavoratoreDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComRSedeLavoroLavoratoreDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComRTListaFzErroriDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTCommaxParametriDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComTStatoComunicazioneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComVRicercaComunicazioneDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.ComVRicercaVardatoriDao;
import it.csi.comonl.comonlweb.ejb.business.be.dao.comonl.EntityDepositoCommaxDao;
import it.csi.comonl.comonlweb.ejb.entity.ComDComunicazione;
import it.csi.comonl.comonlweb.ejb.entity.ComDDelegato;
import it.csi.comonl.comonlweb.ejb.entity.ComDElaboratiOk;
import it.csi.comonl.comonlweb.ejb.entity.ComDFzErrori;
import it.csi.comonl.comonlweb.ejb.entity.ComDImportErrore;
import it.csi.comonl.comonlweb.ejb.entity.ComDLegaleRappr;
import it.csi.comonl.comonlweb.ejb.entity.ComDRapporto;
import it.csi.comonl.comonlweb.ejb.entity.ComDUplDocumenti;
import it.csi.comonl.comonlweb.ejb.entity.ComRComunicazioneDatore;
import it.csi.comonl.comonlweb.ejb.entity.ComRDatoreSede;
import it.csi.comonl.comonlweb.ejb.entity.ComRDepCommaxComunic;
import it.csi.comonl.comonlweb.ejb.entity.ComRRapportoLavoratore;
import it.csi.comonl.comonlweb.ejb.entity.ComRSedeLavoroLavoratore;
import it.csi.comonl.comonlweb.ejb.entity.ComRTListaFzErrori;
import it.csi.comonl.comonlweb.ejb.entity.ComRTListaFzErroriPK;
import it.csi.comonl.comonlweb.ejb.entity.ComTStatoComunicazione;
import it.csi.comonl.comonlweb.ejb.entity.ComVRicercaComunicazione;
import it.csi.comonl.comonlweb.ejb.entity.ComVRicercaVardatori;
import it.csi.comonl.comonlweb.ejb.entity.EntityDepositoCommax;
import it.csi.comonl.comonlweb.ejb.exception.NotFoundException;
import it.csi.comonl.comonlweb.ejb.mapper.ComonlMappers;
import it.csi.comonl.comonlweb.ejb.util.commax.vo.ErroriFileVO;
import it.csi.comonl.comonlweb.ejb.util.jpa.Page;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Comunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Datore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Delegato;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DepCommaxComunic;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.DepositoCommax;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.ElaboratiOk;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRecuperoComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FormRicercaComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.FzErrori;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Lavoratore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.LegaleRappr;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RapportiLavoratoriSediInteressateVD;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Rapporto;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RicercaComunicazione;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.RicercaVardatori;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.SedeLavoro;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.TListaFzErrori;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.Tutore;
import it.csi.comonl.comonlweb.lib.dto.comunicazione.UplDocumenti;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CommaxParametri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatoComunicazione;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.comonl.comonlweb.lib.util.ComonlUtility;
import it.csi.comonl.comonlweb.lib.util.pagination.PagedList;
import it.csi.comonl.comonlweb.lib.util.pagination.Sort;

/**
 * Data Access Delegate for comunicazione
 */
@ApplicationScoped
public class ComunicazioneDad extends BaseDad {

	@Inject
	private ComDComunicazioneDao comDComunicazioneDao;

	@Inject
	private ComVRicercaComunicazioneDao comVRicercaComunicazioneDao;

	@Inject
	private ComVRicercaVardatoriDao comVRicercaVardatoriDao;

	@Inject
	private ComTCommaxParametriDao comTCommaxParametriDao;

	@Inject
	private ComDUplDocumentiDao comDUplDocumentiDao;

	@Inject
	private ComRComunicazioneDatoreDao comRComunicazioneDatoreDao;

	@Inject
	private ComRDatoreSedeDao comRDatoreSedeDao;

	@Inject
	private ComDLegaleRapprDao comDLegaleRapprDao;

	@Inject
	private ComDRapportoDao comDRapportoDao;

	@Inject
	private ComDDatoreDao comDDatoreDao;

	@Inject
	private ComRRapportoLavoratoreDao comRRapportoLavoratoreDao;

	@Inject
	private ComRSedeLavoroLavoratoreDao comRSedeLavoroLavoratoreDao;

	@Inject
	private ComDTutoreDao comDTutoreDao;

	@Inject
	private EntityDepositoCommaxDao entityDepositoCommaxDao;

	@Inject
	private ComRDepCommaxComunicDao comRDepCommaxComunicDao;

	@Inject
	private ComDElaboratiOkDao comDElaboratiOkDao;

	@Inject
	private ComDFzErroriDao comDFzErroriDao;

	@Inject
	private ComRTListaFzErroriDao comRTListaFzErroriDao;

	@Inject
	private ComDImportErroreDao comDImportErroreDao;

	@Inject
	private ComTStatoComunicazioneDao comTStatoComunicazioneDao;
	
	@Inject
	private ComDDelegatoDao comDDelegatoDao;

	public Long countRicercaComunicazione(FormRicercaComunicazione ricercaComunicazione) {
		log.debug("countRicerca----------->", "Entro nel metodo countRicerca");
		Long count = comVRicercaComunicazioneDao.countRicercaComunicazione(ricercaComunicazione);
		log.debug("countRicerca----------->", "ESCO DAl metodo countRicerca--->" + count);
		return count;
	}

	public Long countRecuperoComunicazione(FormRecuperoComunicazione recuperoComunicazione) {
		log.debug("countRecupero----------->", "Entro nel metodo countRecupero");
		Long count = comDComunicazioneDao.countRecuperoComunicazione(recuperoComunicazione);
		log.debug("countRecupero----------->", "ESCO DAl metodo countRecupero--->" + count);
		return count;
	}

	public List<Long> getRecuperoComunicazione(FormRecuperoComunicazione recuperoComunicazione) {
		log.debug("getRecupero----------->", "Entro nel metodo getRecuperoComunicazione");
		List<Long> list = comDComunicazioneDao.getRecuperoComunicazione(recuperoComunicazione);
		log.debug("getRecupero----------->", "ESCO DAl metodo getRecuperoComunicazione");
		return list;
	}

	public List<Long> getIdComunicazioneByCodReg(String codiceRegionale) {
		log.debug("idRecupero----------->", "Entro nel metodo getIdComunicazioneByCodReg");
		List<Long> id = comDComunicazioneDao.getIdComunicazioneByCodReg(codiceRegionale);
		log.debug("idRecupero----------->", "ESCO DAl metodo getIdComunicazioneByCodReg--->" + id);
		return id;
	}

	public List<Long> getIdComunicazioneByCodRegPrec(String codiceRegionale) {
		log.debug("idRecupero----------->", "Entro nel metodo getIdComunicazioneByCodReg");
		List<Long> id = comDComunicazioneDao.getIdComunicazioneByCodRegPrec(codiceRegionale);
		log.debug("idRecupero----------->", "ESCO DAl metodo getIdComunicazioneByCodReg--->" + id);
		return id;
	}

	public PagedList<RicercaComunicazione> getRicerca(int page, int size, Sort sort,
			FormRicercaComunicazione ricercaComunicazione, int limMaxRisultati) {
		String sortField = null;
		String sortDirection = null;

		if (sort != null) {
			if (ComunicazioneSort.byModelName(sort.getField()) != null) {
				sortField = ComunicazioneSort.byModelName(sort.getField()).getQueryName();
			}
			sortDirection = sort.getOrder().getSortDirection();
		}

		Page<ComVRicercaComunicazione> comVComunicazioni = comVRicercaComunicazioneDao.findPaginated(page, size,
				sortField, sortDirection, ricercaComunicazione, limMaxRisultati);

		PagedList<RicercaComunicazione> pagedList = toPagedList(comVComunicazioni, page, size,
				ComonlMappers.RICERCA_COMUNICAZIONE::toModel);
		return pagedList;

	}

	public Long countRicercaVardatori(FormRicercaComunicazione ricercaComunicazione) {
		log.debug("countRicercaVardatori----------->", "Entro nel metodo countRicerca");
		Long count = comVRicercaVardatoriDao.countRicercaComunicazione(ricercaComunicazione);
		log.debug("countRicercaVardatori----------->", "ESCO DAl metodo countRicerca--->" + count);
		return count;
	}

	public PagedList<RicercaVardatori> getRicercaVardatori(int page, int size, Sort sort,
			FormRicercaComunicazione ricercaComunicazione, int limMaxRisultati) {
		String sortField = null;
		String sortDirection = null;

		if (sort != null) {
			if (ComunicazioneSort.byModelName(sort.getField()) != null) {
				sortField = ComunicazioneSort.byModelName(sort.getField()).getQueryName();
			}
			sortDirection = sort.getOrder().getSortDirection();
		}

		Page<ComVRicercaVardatori> comVComunicazioni = comVRicercaVardatoriDao.findPaginated(page, size, sortField,
				sortDirection, ricercaComunicazione, limMaxRisultati);

		PagedList<RicercaVardatori> pagedList = toPagedList(comVComunicazioni, page, size,
				ComonlMappers.RICERCA_VARDATORI::toModel);
		return pagedList;

	}

	/**
	 * Find by id
	 * 
	 * @param Long the uuid
	 * @return the model instance
	 */
	public Optional<Comunicazione> getComunicazione(Long id) {
		Optional<Comunicazione> optionalComunicazioneModel = comDComunicazioneDao.findOne(id)
				.map(ComonlMappers.COMUNICAZIONE::toModel);
		return optionalComunicazioneModel;
	}

	public Comunicazione insertComunicazione(Comunicazione model) {

		ComDComunicazione entity = ComonlMappers.COMUNICAZIONE.toEntity(model);
		if (entity.getIdRapportoLavoro() == null) {
			entity.setIdRapportoLavoro(comDComunicazioneDao.getIdRapportoNextVal());
		}
		return ComonlMappers.COMUNICAZIONE.toModel(comDComunicazioneDao.insert(entity));
	}

	public Comunicazione updateComunicazione(Comunicazione model) {
		ComDComunicazione entity = ComonlMappers.COMUNICAZIONE.toEntity(model);
		entity = comDComunicazioneDao.update(entity);
		comDComunicazioneDao.flush();
		return ComonlMappers.COMUNICAZIONE.toModel(entity);
	}

	// utility e METODI COMMAX
	public Timestamp getNowFromDB() {
		return comTCommaxParametriDao.getNowFromDB();
	}

	public CommaxParametri getParametroCommaxById(Long id) {
		Optional<CommaxParametri> optionalCommaxParametri = comTCommaxParametriDao.findOne(id)
				.map(ComonlMappers.COMMAX_PARAMETRI::toModel);
		if (!optionalCommaxParametri.isPresent()) {
			throw new NotFoundException("comTCommaxParametriDao.findOne");
		}
		return optionalCommaxParametri.get();
	}

	public Long existCRCUplDocumenti(long crcCalcolato) {
		return comDUplDocumentiDao.existCRCUplDocumenti(crcCalcolato);
	}

	public Comunicazione getComunicazioneById(Long idComunicazione) {
		Optional<ComDComunicazione> otpComunicazione = comDComunicazioneDao.findById(idComunicazione);

		if (otpComunicazione.isEmpty()) {
			throw new NotFoundException("getComunicazioneById");
		}

		Comunicazione comunicazione = ComonlMappers.COMUNICAZIONE.toModel(otpComunicazione.get());

		if (ComonlConstants.TIPO_TRACCIATO_VARDATORI_ID.equalsIgnoreCase(comunicazione.getTipoTracciato().getId())) {
			findVarDatoriForComunicazione(comunicazione);

		} else {
			Datore datore = getDatoreByIdComunicazione(idComunicazione);
			comunicazione.setDatoreAttuale(datore);
			findSedeLegaleAndOperativaForDatore(datore);
			datore.setLegaleRappr(getLegaleRappresentanteByIdDatore(datore.getId()));
			findRapportoByForComunicazione(comunicazione);
			findLavoratoreForComunicazione(comunicazione);
		}

		// comunicazione precedente
		if (comunicazione.getIdComDComunicPrecedente() != null
				&& comunicazione.getIdComDComunicPrecedente().longValue() > 0) {
			Optional<ComDComunicazione> otpComunicazionePrec = comDComunicazioneDao
					.findById(comunicazione.getIdComDComunicPrecedente());
			if (!otpComunicazionePrec.isEmpty()) {
				Comunicazione comunicazionePrec = ComonlMappers.COMUNICAZIONE.toModel(otpComunicazionePrec.get());
				comunicazione.setDtProtocolloPrec(comunicazionePrec.getDtProtocollo());
				comunicazione.setAnnoProtComPrec(comunicazionePrec.getAnnoProtCom());
				comunicazione.setNumProtComPrec(comunicazionePrec.getNumProtCom());
				comunicazione.setProvinciaPrec(comunicazionePrec.getProvincia());
			}
		}

		return comunicazione;
	}

	private Tutore getTutoreById(Long idTutore) {
		Optional<Tutore> tutoreFinded = comDTutoreDao.findOne(idTutore).map(ComonlMappers.TUTORE::toModel);
		if (!tutoreFinded.isPresent()) {
			throw new NotFoundException("getTutoreById");
		}
		return tutoreFinded.get();
	}

	private void findLavoratoreForComunicazione(Comunicazione comunicazione) {
		List<ComRRapportoLavoratore> rapportiLavoratore = comRRapportoLavoratoreDao
				.findLavoratoriByIdRapporto(comunicazione.getRapporto().getId());
		if (!rapportiLavoratore.isEmpty()) {
			for (ComRRapportoLavoratore rl : rapportiLavoratore) {
				if (rl.getComTTipoLavoratore() != null && rl.getComTTipoLavoratore().getIdComTTipoLavoratore() != null
						&& ComonlConstants.TIPO_LAVORATORE_C
								.equalsIgnoreCase(rl.getComTTipoLavoratore().getIdComTTipoLavoratore())) {
					comunicazione.setLavoratoreCoObbligato(ComonlMappers.LAVORATORE.toModel(rl.getComDLavoratore()));
				} else {
					Lavoratore l = ComonlMappers.LAVORATORE.toModel(rl.getComDLavoratore());
					if (comunicazione.getLavoratori() == null) {
						comunicazione.setLavoratori(new ArrayList<Lavoratore>());
					}
					comunicazione.getLavoratori().add(l);
				}
			}
		}
	}

	private void findRapportoByForComunicazione(Comunicazione comunicazione) {
		List<ComDRapporto> rapporti = comDRapportoDao.findRapportiByIdComunicazione(comunicazione.getId());

		Datore distacco = null;
		Datore aziUtilizzatrice = null;

		if (!rapporti.isEmpty() && rapporti.size() == 1) { // può essere un UNILAV o una Somministrazione semplice
			comunicazione.setRapporto(ComonlMappers.RAPPORTO.toModel(rapporti.get(0)));

			if (rapporti.get(0).getComDDatoreDistaccatario() != null
					&& rapporti.get(0).getComDDatoreDistaccatario().getId() != null) {
				distacco = ComonlMappers.DATORE.toModel(rapporti.get(0).getComDDatoreDistaccatario());
				findSedeLegaleAndOperativaForDatore(distacco);
			}

			if (rapporti.get(0).getComDTutore() != null
					&& ComonlUtility.isNotVoid(rapporti.get(0).getComDTutore().getIdComDTutore())) {
				Tutore t = getTutoreById(rapporti.get(0).getComDTutore().getIdComDTutore());
				comunicazione.getRapporto().setTutore(t);
			}

			if (rapporti.get(0).getComDAziUtilizzatrice() != null) {
				aziUtilizzatrice = ComonlMappers.DATORE.toModel(rapporti.get(0).getComDAziUtilizzatrice());
				findSedeLegaleAndOperativaForDatore(aziUtilizzatrice);
				comunicazione.getRapporto().setAziUtilizzatrice(aziUtilizzatrice);
			}
		} else if (!rapporti.isEmpty() && rapporti.size() == 2) { // somministrazione con missione
			for (ComDRapporto rap : rapporti) {
				if (rap.getDtInizioMissione() != null) {
					comunicazione.setMissione(ComonlMappers.RAPPORTO.toModel(rap));

					if (rap.getComDAziUtilizzatrice() != null) {
						aziUtilizzatrice = ComonlMappers.DATORE.toModel(rap.getComDAziUtilizzatrice());
						findSedeLegaleAndOperativaForDatore(aziUtilizzatrice);
					}

					if (rap.getComDTutore() != null && ComonlUtility.isNotVoid(rap.getComDTutore().getIdComDTutore())) {
						Tutore t = getTutoreById(rap.getComDTutore().getIdComDTutore());
						comunicazione.getMissione().setTutore(t);
					}
				} else if (rap.getDtInizioRapporto() != null) {
					comunicazione.setRapporto(ComonlMappers.RAPPORTO.toModel(rap));
					if (rap.getComDTutore() != null && ComonlUtility.isNotVoid(rap.getComDTutore().getIdComDTutore())) {
						Tutore t = getTutoreById(rap.getComDTutore().getIdComDTutore());
						comunicazione.getRapporto().setTutore(t);
					}
				}
			}
		}

		if (comunicazione.getRapporto() != null && distacco != null)
			comunicazione.getRapporto().setDatoreDistaccatario(distacco);

		if (comunicazione.getMissione() != null && aziUtilizzatrice != null)
			comunicazione.getMissione().setAziUtilizzatrice(aziUtilizzatrice);

	}

	private void findSedeLegaleAndOperativaForDatore(Datore datore) {

		if (datore != null) {
			List<ComRDatoreSede> legameDatoreSede = comRDatoreSedeDao.findSediLavoroByIdDatore(datore.getId());
			if (legameDatoreSede != null) {
				for (ComRDatoreSede ds : legameDatoreSede) {
					if (ds.getComDSedeLavoro() != null && null != ds.getComDSedeLavoro().getFlgSedeLegale()
							&& ds.getComDSedeLavoro().getFlgSedeLegale().equalsIgnoreCase(ComonlConstants.FLAG_S)) {
						datore.setSedeLegale(ComonlMappers.SEDE_LAVORO.toModel(ds.getComDSedeLavoro()));
					}
					if (ds.getComDSedeLavoro() != null && null != ds.getComDSedeLavoro().getFlgSedeLegale()
							&& ds.getComDSedeLavoro().getFlgSedeLegale().equalsIgnoreCase(ComonlConstants.FLAG_N)) {
						datore.setSedeOperativa(ComonlMappers.SEDE_LAVORO.toModel(ds.getComDSedeLavoro()));
					}
				}
			}
		}
	}

	private void findSedeLegaleAziendaForDatore(Datore datore) {
		if (datore != null) {
			List<ComRDatoreSede> legameDatoreSede = comRDatoreSedeDao.findSediLavoroByIdDatore(datore.getId());
			if (legameDatoreSede != null) {
				for (ComRDatoreSede ds : legameDatoreSede) {
					if (ds.getComDSedeLavoro() != null && null != ds.getComDSedeLavoro().getFlgSedeLegale()
							&& ds.getComDSedeLavoro().getFlgSedeLegale().equalsIgnoreCase(ComonlConstants.FLAG_S)) {
						datore.setSedeLegale(ComonlMappers.SEDE_LAVORO.toModel(ds.getComDSedeLavoro()));
					}
				}
			}
		}
	}

	private Datore getDatoreByIdComunicazione(Long idComunicazione) {
		List<ComRComunicazioneDatore> legameCoDatore = comRComunicazioneDatoreDao
				.findLegameCoDatoreByIdComunicazione(idComunicazione);
		if (legameCoDatore.isEmpty()) {
			throw new NotFoundException("findLegameCoDatoreByIdComunicazione");
		} else {
			return ComonlMappers.DATORE.toModel(legameCoDatore.get(0).getComDDatore());
		}
	}

	private LegaleRappr getLegaleRappresentanteByIdDatore(Long idComunicazione) {
		// Legale Rappresentante
		Optional<ComDLegaleRappr> legaleRapp = comDLegaleRapprDao.findOne(idComunicazione);
		if (legaleRapp.isPresent()) {
			return ComonlMappers.LEGALE_RAPPR.toModel(legaleRapp.get());
		}

		return null;
	}

	private void findVarDatoriForComunicazione(Comunicazione comunicazione) {
		// azienda attuale/precedente
		List<ComRComunicazioneDatore> legameCoDatore = comRComunicazioneDatoreDao
				.findLegameCoDatoreByIdComunicazione(comunicazione.getId());
		if (legameCoDatore.size() > 1) {
			for (ComRComunicazioneDatore comDatore : legameCoDatore) {
				Optional<Datore> d1 = comDDatoreDao.findOne(comDatore.getComDDatore().getId())
						.map(ComonlMappers.DATORE::toModel);
				Datore datore = d1.get();
				if (datore != null) {
					if (ComonlConstants.TIPO_DATORE_ATTUALE_ID
							.equalsIgnoreCase(comDatore.getComTTipoDatore().getId())) {
						datore.setLegaleRappr(getLegaleRappresentanteByIdDatore(datore.getId()));
						comunicazione.setDatoreAttuale(d1.get());
					}
					if (ComonlConstants.TIPO_DATORE_PRECEDENTE_ID
							.equalsIgnoreCase(comDatore.getComTTipoDatore().getId())) {
						datore.setLegaleRappr(getLegaleRappresentanteByIdDatore(datore.getId()));
						comunicazione.setDatorePrecedente(d1.get());
					}
				}
			}
		} else if (legameCoDatore.size() == 1) {
			Optional<Datore> d1 = comDDatoreDao.findOne(legameCoDatore.get(0).getComDDatore().getId())
					.map(ComonlMappers.DATORE::toModel);
			Datore datore = d1.get();
			if (datore != null) {
				if (ComonlConstants.TIPO_DATORE_A.equalsIgnoreCase(legameCoDatore.get(0).getComTTipoDatore().getId())) {
					datore.setLegaleRappr(getLegaleRappresentanteByIdDatore(datore.getId()));
					comunicazione.setDatoreAttuale(d1.get());
				}
			}
		}
		// sedi dell'attuale datore di lavoro
		findSedeLegaleAndOperativaForDatore(comunicazione.getDatoreAttuale());

		// sede del datore precedente
		if (comunicazione.getDatorePrecedente() != null) {
			findSedeLegaleAziendaForDatore(comunicazione.getDatorePrecedente());
		}

		//
		comunicazione.setRapLavSedeVD(new ArrayList<RapportiLavoratoriSediInteressateVD>());

		// FIXME da modificare per performance
		List<ComDRapporto> rapportiEnt = comDRapportoDao.findRapportiByIdComunicazione(comunicazione.getId());
		if (rapportiEnt != null) {
			for (ComDRapporto rapportoEnt : rapportiEnt) {
				Rapporto rapporto = ComonlMappers.RAPPORTO.toModel(rapportoEnt);
				RapportiLavoratoriSediInteressateVD rls = new RapportiLavoratoriSediInteressateVD();
				rls.setRapportoVD(rapporto);

				// per ogni rapporto un lavoratore
				Long idRapporto = rapporto.getId();
				ComRRapportoLavoratore rl = comRRapportoLavoratoreDao.getLavoratoreByIdRapporto(idRapporto);
				Lavoratore lav = ComonlMappers.LAVORATORE.toModel(rl.getComDLavoratore());
				rls.setLavoratoreVD(lav);

				// per ogni lavoratore una sede di lavoro
				Long idLavoratore = lav.getId();
				ComRSedeLavoroLavoratore sl = comRSedeLavoroLavoratoreDao.getSedeByIdLavoratore(idLavoratore);
				SedeLavoro sede = ComonlMappers.SEDE_LAVORO.toModel(sl.getComDSedeLavoro());
				rls.setSedeLavoroVD(sede);

				comunicazione.getRapLavSedeVD().add(rls);
			}
		}

	}

	public UplDocumenti insertUplodDocumenti(UplDocumenti model) {

		try {
			ComDUplDocumenti entity = ComonlMappers.UPL_DOCUMENTI.toEntity(model);
			entity = comDUplDocumentiDao.insert(entity);
			return ComonlMappers.UPL_DOCUMENTI.toModel(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public UplDocumenti updateUploadDocumenti(UplDocumenti model) {
		ComDUplDocumenti entity = ComonlMappers.UPL_DOCUMENTI.toEntity(model);
		entity = comDUplDocumentiDao.update(entity);
		return ComonlMappers.UPL_DOCUMENTI.toModel(entity);
	}

	public UplDocumenti updateStatoUploadDocumenti(UplDocumenti model) {
		ComDUplDocumenti entity = ComonlMappers.UPL_DOCUMENTI.toEntity(model);
		entity = comDUplDocumentiDao.update(entity);
		return ComonlMappers.UPL_DOCUMENTI.toModel(entity);
	}

	public DepositoCommax insertDepositoCommax(DepositoCommax model, int progr) {
		try {
			EntityDepositoCommax entity = ComonlMappers.DEPOSITO_COMMAX.toEntity(model);
			entity.setId(entityDepositoCommaxDao.getSequenceDepositoCommax(progr));
			entity = entityDepositoCommaxDao.insert(entity);
			return ComonlMappers.DEPOSITO_COMMAX.toModel(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<DepositoCommax> getDepositoCommaxByIdUplDocumenti(Long idUplDocumenti) {
		List<EntityDepositoCommax> entities = entityDepositoCommaxDao
				.getDepositoCommaxByIdUplDocumenti(new BigDecimal(idUplDocumenti));
		return ComonlMappers.DEPOSITO_COMMAX.toModels(entities);
	}

	public UplDocumenti getUplDocumentiById(Long idUplDocumenti) {
		Optional<ComDUplDocumenti> uplDoc = comDUplDocumentiDao.findOne(idUplDocumenti);
		if (uplDoc.isPresent()) {
			return ComonlMappers.UPL_DOCUMENTI.toModel(uplDoc.get());
		}
		return null;
	}

	public DepCommaxComunic insertComRDepCommaxComunic(DepCommaxComunic model) {
		ComRDepCommaxComunic entity = ComonlMappers.DEP_COMMAX_COMUNIC.toEntity(model);
		entity = comRDepCommaxComunicDao.insert(entity);
		return ComonlMappers.DEP_COMMAX_COMUNIC.toModel(entity);
	}

	public List<DepCommaxComunic> getDepCommaxComunicaByIdUplDocumenti(Long idUplDocumenti) {
		List<ComRDepCommaxComunic> entities = comRDepCommaxComunicDao
				.getDepCommaxComunicaByIdUplDocumenti(idUplDocumenti);
		return ComonlMappers.DEP_COMMAX_COMUNIC.toModels(entities);
	}

	public List<DepositoCommax> estraiDepositiDelleComunicazioniValide(Long idUplDocumenti) {
		List<EntityDepositoCommax> entities = entityDepositoCommaxDao
				.estraiDepositiDelleComunicazioniValide(new BigDecimal(idUplDocumenti));
		return ComonlMappers.DEPOSITO_COMMAX.toModels(entities);
	}

	public DepCommaxComunic updateComRDepCommaxComunic(DepCommaxComunic model) {
		ComRDepCommaxComunic entity = ComonlMappers.DEP_COMMAX_COMUNIC.toEntity(model);
		entity = comRDepCommaxComunicDao.update(entity);
		return ComonlMappers.DEP_COMMAX_COMUNIC.toModel(entity);
	}

	public boolean esisteEsitoInComDElaboratiOk(ElaboratiOk elaboratiOkDto) {
		ComDElaboratiOk esito = comDElaboratiOkDao.esisteEsitoInComDElaboratiOk(elaboratiOkDto);
		if (esito != null) {
			return true;
		}
		return false;
	}

	public ElaboratiOk insertElaboratiOk(ElaboratiOk model) {
		ComDElaboratiOk entity = ComonlMappers.ELABORATI_OK.toEntity(model);
		entity = comDElaboratiOkDao.insert(entity);
		return ComonlMappers.ELABORATI_OK.toModel(entity);
	}

	public List<ElaboratiOk> getEsitoComDElaboratiOkByIdUplDoc(long idComDUplDocumenti) {
		List<ComDElaboratiOk> entities = comDElaboratiOkDao.getEsitoComDElaboratiOkByIdUplDoc(idComDUplDocumenti);
		return ComonlMappers.ELABORATI_OK.toModels(entities);
	}

	public List<ErroriFileVO> getElencoErroriFzByIdUplDoc(long idComDUplDocumenti) {
		return comDFzErroriDao.getElencoErroriFzByIdUplDoc(idComDUplDocumenti);
	}

	public FzErrori registraEsitoErrore(FzErrori model) {
		ComDFzErrori entity = null;
		try {
			model.setId(0L); // FIXME trucco per evitare errore nel mapping
			entity = ComonlMappers.FZ_ERRORI.toEntity(model);
			entity.setId(null);
		} catch (Exception e) {
			log.error("registraEsitoErrore", e);
		}

		entity = comDFzErroriDao.insert(entity);
		
		return ComonlMappers.FZ_ERRORI.toModel(entity);
	}

	public TListaFzErrori registraDettaglioErroreBloccante(TListaFzErrori model) {
		ComRTListaFzErroriPK pk = new ComRTListaFzErroriPK();
		pk.setIdComDFzErrori(model.getFzErrori().getId());
		pk.setIdComTListaErroriFz(model.getId().getId());

		ComRTListaFzErrori entity = new ComRTListaFzErrori();
		entity.setId(pk);
		entity = comRTListaFzErroriDao.insert(entity);
		return ComonlMappers.TLISTA_FZ_ERRORI.toModel(entity);
	}

	public List<UplDocumenti> gestisciUploadNonCompletati(String minutiAttesaPrimaDiRitrasmettere) {

		List<ComDUplDocumenti> uplDoc = comDUplDocumentiDao
				.gestisciUploadNonCompletati(minutiAttesaPrimaDiRitrasmettere);
		return ComonlMappers.UPL_DOCUMENTI.toModels(uplDoc);
	}

	public void deleteImportErrori(String codiceComunicazioneRegionale) {
		comDImportErroreDao.deleteErroriComunicazione(codiceComunicazioneRegionale);
	}

	public void insertImportErrori(ComDImportErrore error) {
		comDImportErroreDao.insert(error);
	}

	public Long updateStatoComunicazionePrecedente(Comunicazione comunicazione) {

		if (comunicazione.getCodiceComunRegPrec() == null || comunicazione.getCodiceComunRegPrec().trim().equals("")) {
			return null;
		}

		List<Long> idComunicazionePrec = comDComunicazioneDao
				.getIdComunicazioneByCodReg(comunicazione.getCodiceComunRegPrec());

		if (idComunicazionePrec.size() == 0) {
			return null;
		}

		if (comunicazione.getStatoComunicazione() == null || !comunicazione.getStatoComunicazione().getId()
				.equals(ComonlConstants.STATO_COMUNICAZIONE_VALIDATA_ID)
				&& !comunicazione.getStatoComunicazione().getId()
						.equals(ComonlConstants.STATO_COMUNICAZIONE_RETTIFICATA_ID)
				&& !comunicazione.getStatoComunicazione().getId()
						.equals(ComonlConstants.STATO_COMUNICAZIONE_ANNULLATA_ID)) {

			return null;
		}

		Optional<ComDComunicazione> comunicazionePrec = comDComunicazioneDao.findById(idComunicazionePrec.get(0));

		if (comunicazione.getTipoComunicazioneTu().getCodTipoComunicazioneMin()
				.equals(ComonlConstants.TIPO_COMUNICAZIONE_TU_RETTIFICA_COD_MIN)) {

			Optional<ComTStatoComunicazione> stato = comTStatoComunicazioneDao
					.findOne(ComonlConstants.STATO_COMUNICAZIONE_RETTIFICATA_ID);

			if (comunicazionePrec.isPresent()) {
				comunicazionePrec.get().setComTStatoComunicazione(stato.get());
				comDComunicazioneDao.update(comunicazionePrec.get());
			}

			// annullamento
		} else if (comunicazione.getTipoComunicazioneTu().getCodTipoComunicazioneMin()
				.equals(ComonlConstants.TIPO_COMUNICAZIONE_TU_ANNULLAMENTO_COD_MIN)) {

			Optional<ComTStatoComunicazione> stato = comTStatoComunicazioneDao
					.findOne(ComonlConstants.STATO_COMUNICAZIONE_ANNULLATA_ID);

			if (comunicazionePrec.isPresent()) {
				comunicazionePrec.get().setComTStatoComunicazione(stato.get());
				comDComunicazioneDao.update(comunicazionePrec.get());
			}

		} else {
			return null;
		}

		return idComunicazionePrec.get(0);

	}

	public Comunicazione updateStatoComunicazione(Comunicazione model, Long idStato) {
		StatoComunicazione statoComunicazione = new StatoComunicazione();
		statoComunicazione.setId(idStato);
		model.setStatoComunicazione(statoComunicazione);
		return updateComunicazione(model);
	}
	
	public Delegato getLastDelegatoByCf(String cfDelegato) {
		Optional<ComDDelegato> record = comDDelegatoDao.findLastByCfDelegato(cfDelegato);
		return record.map(ComonlMappers.DELEGATO::toModel).orElse(null);
	}
	
	public Delegato insertDelegato(Delegato model) {
		Long maxId = comDDelegatoDao.getIdMax();
		model.setId(++maxId);
		ComDDelegato entity = ComonlMappers.DELEGATO.toEntity(model);
		entity = comDDelegatoDao.insert(entity);
		return ComonlMappers.DELEGATO.toModel(entity);		
	}
	
	public List<Comunicazione> getComunicazioniByIdentificativoRapporto(String codiceFiscaleDatore, String codiceFiscaleLavoratore, Date dtInizioRapporto) {
		List<ComDComunicazione> entities = comDComunicazioneDao.getByIdentificativoRapporto(codiceFiscaleDatore, codiceFiscaleLavoratore, dtInizioRapporto);
		return ComonlMappers.COMUNICAZIONE.toModels(entities);
	}
	
	public Comunicazione getComunicazioneByIdRapportoLavoro(Long idRapportoLavoro) {
		Optional<ComDComunicazione> record = comDComunicazioneDao.getByIdRapportoLavoro(idRapportoLavoro);
		return record.map(ComonlMappers.COMUNICAZIONE::toModel).orElse(null);
	}
}
