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
package it.csi.comonl.comonlweb.ejb.util.commax.validatori.controlli;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import it.csi.comonl.comonlweb.ejb.business.be.dad.CommonDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.ComunicazioneDad;
import it.csi.comonl.comonlweb.ejb.business.be.dad.DecodificaDad;
import it.csi.comonl.comonlweb.ejb.entity.ComRRegTracciatoContratto;
import it.csi.comonl.comonlweb.ejb.exception.NotFoundException;
import it.csi.comonl.comonlweb.ejb.util.commax.dto.EsitoControlloComunicazione;
import it.csi.comonl.comonlweb.ejb.util.commax.util.CalcolaRitardo;
import it.csi.comonl.comonlweb.ejb.util.commax.util.DateUtil;
import it.csi.comonl.comonlweb.ejb.util.commax.util.DateUtilities;
import it.csi.comonl.comonlweb.ejb.util.commax.util.Format;
import it.csi.comonl.comonlweb.ejb.util.commax.util.StringUtils;
import it.csi.comonl.comonlweb.ejb.util.commax.util.Util;
import it.csi.comonl.comonlweb.ejb.util.commax.util.UtilConstant;
import it.csi.comonl.comonlweb.ejb.util.commax.util.ValidazioneMail;
import it.csi.comonl.comonlweb.ejb.util.controlli.RegistrazioneEsitiControlli;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.CategTirocinante;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.Comune;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.StatiEsteri;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContrAmmPerCom;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoContratti;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoOrario;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipologiaTirocinio;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;
import it.csi.spicom.dto.ComunicazioneTracciatoUnicoDTO;
import it.csi.spicom.dto.DatoreDiLavoroDTO;
import it.csi.spicom.dto.LavoratoreDTO;
import it.csi.spicom.util.ComunicazioneTUValidator;
import it.csi.spicom.util.TUConstants;

/**
 * @version 1.0
 * @created 06-Mar-2008 18:07:27
 */
public abstract class  ControlloAstratto {

	private Long idReport;

	private Long idEntePromotoreTirocinio;
	protected EsitoControlloComunicazione elencoErrori;

	//	protected IRegistroEsito registratoreEsiti;



	private boolean UNILAV = false;
	private boolean UNISOM = false;
	private boolean VARDATORI = false;
	private boolean UNIDOM = false;
	private boolean UNIURG = false;


	protected ComunicazioneTracciatoUnicoDTO comunicazione;

	protected String tipoComunicazione;

	public void finalize() throws Throwable {

	}

	private void setTipoTracciato()
	{
		String tipoSelezionato = comunicazione.getDatiAggiuntivi().getTipoTracciatoXML();
		if( tipoSelezionato.equalsIgnoreCase(TUConstants.TIPO_TRACCIATO_UNILAV))
		{
			setUNILAV(true);
			setUNISOM(false);
			setVARDATORI(false);
			setUNIURG(false);
			setUNIDOM(false);
		}
		if( tipoSelezionato.equalsIgnoreCase(TUConstants.TIPO_TRACCIATO_UNISOM))
		{
			setUNILAV(false);
			setUNISOM(true);
			setVARDATORI(false);
			setUNIURG(false);
			setUNIDOM(false);
		}		
		if( tipoSelezionato.equalsIgnoreCase(TUConstants.TIPO_TRACCIATO_VARDATORI))
		{
			setUNILAV(false);
			setUNISOM(false);
			setVARDATORI(true);
			setUNIURG(false);
			setUNIDOM(false);
		}
		if( tipoSelezionato.equalsIgnoreCase(TUConstants.TIPO_TRACCIATO_UNIURG))
		{
			setUNILAV(false);
			setUNISOM(false);
			setVARDATORI(false);
			setUNIURG(true);
			setUNIDOM(false);
		}
		if( tipoSelezionato.equalsIgnoreCase(TUConstants.TIPO_TRACCIATO_UNIDOM))
		{
			setUNILAV(false);
			setUNISOM(false);
			setVARDATORI(false);
			setUNIURG(false);
			setUNIDOM(true);

		}
	}

	public String getTipoTracciato()
	{
		if(UNILAV){
			return TUConstants.TIPO_TRACCIATO_UNILAV;
		} else if(UNISOM){
			return TUConstants.TIPO_TRACCIATO_UNISOM;
		} else if(VARDATORI){
			return TUConstants.TIPO_TRACCIATO_VARDATORI;
		} else if(UNIURG){
			return TUConstants.TIPO_TRACCIATO_UNIURG;
		} else if(UNIDOM){
			return TUConstants.TIPO_TRACCIATO_UNIDOM;
		}

		return TUConstants.TIPO_TRACCIATO_SCONOSCIUTO;
	}

	public DatoreDiLavoroDTO identificaDatoreLavoroDTORispettoTipoTracciato()
	{
		DatoreDiLavoroDTO datoreLavoro = null;

		if( isVARDATORI() && comunicazione.getVariazioneRagioneSociale()!= null )
		{
			datoreLavoro = comunicazione.getVariazioneRagioneSociale().getDatoreLavoroAttuale();
		}
		else if( isVARDATORI() && comunicazione.getTrasferimentoRamoAziendale()!=null)
		{
			datoreLavoro = comunicazione.getTrasferimentoRamoAziendale().getDatoreLavoroAttuale();
		}
		else if( isUNILAV() || isUNISOM()  || isUNIDOM())
		{
			datoreLavoro = comunicazione.getDatoreDiLavoro();			
		}
		return datoreLavoro;
	}


	private void setTipoComunicazione(ComunicazioneTracciatoUnicoDTO comunicazione) {
		tipoComunicazione = ComunicazioneTUValidator.validaComunicazione(comunicazione).getTipoComunicazione();
		if (tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE) || 
				tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE_CON_MISSIONE)||
				tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO) ||
				tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO_MISSIONE)
				){
			String codTrasformazione = null;
			if (comunicazione.getDatiAggiuntivi().getTipoTracciatoXML().equalsIgnoreCase(TUConstants.TIPO_TRACCIATO_UNILAV)){
				codTrasformazione = comunicazione.getTrasformazione().getCodTrasformazione();
			}
			else{
				codTrasformazione = comunicazione.getDatiVariazione().getDatiTrasformazione().getCodiceTrasformazione();
			}
			if (codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_TL)){
				tipoComunicazione = UtilConstant.TRASFERIMENTO;
			}
			else if(codTrasformazione.equalsIgnoreCase(UtilConstant.CODICE_TRASFORMAZIONE_DL)){
				tipoComunicazione = UtilConstant.DISTACCO;
			}
		}
	}


	/**
	 * Ritorna true se ha avuto esito poisitivo
	 * 
	 * @param comunicazione
	 */
	public abstract void eseguiControllo(List<ApiError> elencoErrori , CommonDad commonDad, DecodificaDad decodificaDad, ComunicazioneDad comunicazioneDad) throws Exception;


	public Long getidReport(){
		return idReport;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setidReport(Long newVal){
		idReport = newVal;
	}


	// *********************************************************************************************	
	// ********************************* METODY UTILITY DI CONTROLLO *******************************

	public boolean isValorizzato(String campo)
	{
		return !isVuoto(campo);
	}
	public boolean isVuoto(String campo)
	{
		//		if( campo == null || campo.equalsIgnoreCase(""))
		if( campo == null || campo.equalsIgnoreCase("") || campo.trim().length()==0)
			return true;
		else
			return false;
	}

	public boolean isValorizzatoAlmenoUnCampo(String s[])
	{
		boolean result = false;

		for (int i = 0; i < s.length; i++)
		{
			if( isValorizzato( s[i] ))
			{
				result = true;
			}
		}
		return result;	
	}


	public boolean isLunghezzaMinStringaValida( String valore, int lunghezzaMinima )
	{
		boolean result = true;
		if( isVuoto(valore) ) result = true;
		else
		{
			if( valore.length() < lunghezzaMinima )
				result = false;
		}
		return result;	
	}

	public boolean isLunghezzaMaxStringaValida( String valore, int lunghezzaMassima )
	{
		boolean result = true;
		if( isVuoto(valore) ) result = true;
		else
		{
			if( valore.length() > lunghezzaMassima )
				result = false;
		}
		return result;	
	}

	public boolean isLunghezzaStringaMinEMaxValida( String valore, int lunghezzaMinima , int lunghezzaMassima )
	{
		boolean result = true;
		if( isVuoto(valore) ) result = true;
		else
		{
			if( isLunghezzaMinStringaValida( valore , lunghezzaMinima) && isLunghezzaMaxStringaValida( valore , lunghezzaMassima) ) 
				result = true;
			else
				result = false;
		}
		return result;	
	}

	public boolean isFlagValido_ValoriAmmessi_SI_o_NO( String flag )
	{
		boolean result = true;
		if( isVuoto(flag) ) result = true;
		else
		{
			if( flag.equals(UtilConstant.FLAG_SI ) || flag.equals(UtilConstant.FLAG_NO ) )
				result = true;
			else
				result = false;
		}
		return result;	
	}

	public boolean isFormatoMailValido(String mail)
	{
		boolean result = false;
		if( isVuoto(mail) ) result = true;
		else
		{
			return ValidazioneMail.checkMail(mail);
		}
		return result;
	}

	private boolean isNumeric( String s) 
	{
		final char[] numbers = s.toCharArray();
		for (int x = 0; x < numbers.length; x++) {      
			final char c = numbers[x];
			if ((c >= '0') && (c <= '9')) continue;
			return false; // invalid
		}
		return true; // valid
	}




	public boolean controllaPartitaIVA(String partitaIVA) {
		// Verifica la lunghezza della stringa
		try {
			if (partitaIVA.length() != 11) {
				return false;
			}
			if(StringUtils.containsOnly(partitaIVA, "0")) {
				return false;
			}

			// Verifica che la stringa sia composta da sole cifre
			char c;
			for (int i = 0; i < 11; i++) {
				c = partitaIVA.charAt(i);
				if ( (byte) c < 48 || (byte) c > 57) {
					return false;
				}
			}

			// Algoritmo di calcolo dell'ultima cifra
			int Vd, Vp;
			int ctrl = 0;
			String Wk = "";
			for (int i = 0; i < 9; i += 2) {
				Vd = (byte) partitaIVA.charAt(i) - 48;
				Vp = 2 * ( (byte) partitaIVA.charAt(i + 1) - 48);
				Wk = (Vp < 10) ? "0" + String.valueOf(Vp) : String.valueOf(Vp);
				ctrl += Vd + (byte) Wk.charAt(0) - 48 + (byte) Wk.charAt(1) - 48;
			}

			if(ctrl < 10) {
				Wk = "0" + String.valueOf(ctrl);
			}
			else if(ctrl > 99) {
				Wk = String.valueOf(ctrl).substring(0, 1);
			}
			else {
				Wk = String.valueOf(ctrl);
			}

			// Verifica la correttezza dell'ultima cifra
			if ( (byte) partitaIVA.charAt(10) - 48 !=
					(10 - ( (byte) Wk.charAt(1) - 48)) % 10) {
				return false;
			}
		}
		catch (Exception ex) {
			return false;

		}

		// Tutto ok
		return true;
	}


	public boolean isCodiceFiscaleOPartitaIvaValido(String codiceFiscaleOPartitaIva)
	{
		boolean result = false;
		if( isPartitaIvaValido(codiceFiscaleOPartitaIva) )
		{
			result = true;
		}
		if( isCodiceFiscaleValido(codiceFiscaleOPartitaIva) )
		{
			result = true;
		}	
		return result;
	}

	public boolean isCodiceFiscaleInternazionaleValido(String codiceFiscaleInternazionale, DecodificaDad decodificaDad) throws Exception
	{
		String  siglaNazione = codiceFiscaleInternazionale.substring(0,2);
		Long idStato = decodificaDad.getStatoBySiglaNazione(siglaNazione);

		boolean esito = (idStato != null ? true :false);

		if( !esito) 
			return false;
		else
			return true;
	}


	public boolean isPartitaIvaValido(String partitaIva)
	{
		boolean result = false;

		if( partitaIva.length() == 11 )
		{
			result = controllaPartitaIVA(partitaIva);

		}
		return result;
	}

	public boolean isCodiceFiscaleValido(String codiceFiscale)
	{
		boolean result = false;

		if( codiceFiscale.length() == 16 )
		{
			result = Format.controlloCF(codiceFiscale);
		}
		return result;
	}

	public boolean isCodiceFiscale(String codiceFiscale)
	{

		if( codiceFiscale.length() == 16 )
		{
			return true;
		}
		return false;
	}

	//	public boolean isChiavePresenteInTabellaDecodifica(String chiave, String queryDaEseguire ) throws Exception
	//	{
	//		boolean result = true;
	//		if( isVuoto(chiave) ) result = true;
	//		else
	//		{
	//			String queryCompleta = queryDaEseguire + "'"+ chiave + "'";
	//			QueryTabDecodificaGenerico qry = new QueryTabDecodificaGenerico(queryCompleta);
	//			Long resultChiave = qry.lanciaQueryLong();
	//			if( resultChiave == null )
	//				result = false;
	//		}
	//		return result;
	//	}
	//
	public boolean isTipoContrattoAmmessoPerComunicazioneDB(String tipoContratto,
			String tipoComunicazione,
			Date dtInizioRapporto,
			boolean isUnisomm ,
			boolean isUnidom,
			DecodificaDad decodificaDad) throws Exception
	{
		boolean result = true;
		if( isVuoto(tipoContratto) ){
			result = true;
		}
		else
		{
			//inizio tirando su l'id del contratto per la data inizio rapporto
			//e la tabella COM_T_TIPO_CONTR_AMM_PER_COM
			TipoContratti tipiContrattoDb = new TipoContratti();
			List<TipoContrAmmPerCom> tipiContrAmmessoPerCo = decodificaDad.getDatiTipoContrattoAmmessoPerCom();

			if( tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE)
					|| tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE_CONTESTUALE_A_INIZIO_MISSIONE )
					|| tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE )){

				tipiContrattoDb = (TipoContratti) decodificaDad.getTfromMin(TipoContratti.class, tipoContratto, dtInizioRapporto);

				if(tipiContrattoDb == null){ //non l'ha trovato con quella data valido torno false
					result = false;
				}
				else{
					// prendo l'id del tipoContratto
					Long idTipoContratto = tipiContrattoDb.getId();
					TipoContrAmmPerCom obj = getTipoContrAmmPerComByIdTipoContratto(idTipoContratto, tipiContrAmmessoPerCo);

					result = isTipoContrattoAmmessoPerComunicazione(obj, tipoComunicazione,isUnisomm, isUnidom);
				}

			}
			else{
				tipiContrattoDb = (TipoContratti) decodificaDad.getTfromMin(TipoContratti.class, tipoContratto, null);

				// prendo l'id del tipoContratto
				Long idTipoContratto = tipiContrattoDb.getId();
				TipoContrAmmPerCom obj = getTipoContrAmmPerComByIdTipoContratto(idTipoContratto, tipiContrAmmessoPerCo);

				result = isTipoContrattoAmmessoPerComunicazione(obj, tipoComunicazione,isUnisomm, isUnidom);
			}
		}
		return result;
	}

	private TipoContrAmmPerCom getTipoContrAmmPerComByIdTipoContratto(Long idTipoContratto, List<TipoContrAmmPerCom> tipiContrAmmessoPerCo ) {
		TipoContrAmmPerCom trovato = null;
		for (TipoContrAmmPerCom tipoContrAmmessoPerCo : tipiContrAmmessoPerCo) {
			if (tipoContrAmmessoPerCo.getTipoContratti().getId().equals(idTipoContratto)) {
				trovato = tipoContrAmmessoPerCo;
				break;
			}
		}
		return trovato;
	}
	//	

	//	
	//	public boolean isTipoContrattoInPeriodoNonValido(String tipoContratto, 
	//													 String dataInizioRapporto) throws Exception
	//			{
	//				QueryTipoContrattoInPeriodoNonValido qry = new QueryTipoContrattoInPeriodoNonValido(tipoContratto, dataInizioRapporto);			
	//				return qry.isTipoContrattoInPeriodoNonValido();			
	//
	//			}
	//	
	//	public boolean isIdComuneOStatoEsteroValido (String chiave) throws Exception
	//	{
	//		boolean result = false;
	//		if( isVuoto(chiave) ) result = true;
	//		else
	//		{	
	//			boolean esitoComune = isChiavePresenteInTabellaDecodifica(chiave,UtilConstant.QUERY_COM_T_COMUNE);
	//			boolean esitoStatoEstero = isChiavePresenteInTabellaDecodifica(chiave,UtilConstant.QUERY_COM_T_STATI_ESTERI);
	//			
	//			if( !esitoComune && !esitoStatoEstero)
	//				result = false;
	//			else
	//				result = true;
	//		}
	//		return result;
	//	}
	//	
	//	
	//	
	//	public boolean isComuneONazioneNascitaValido(String chiave) throws Exception{	
	//
	//		boolean result = false;
	//		if( isVuoto(chiave) ) result = true;
	//		else
	//		{
	//
	//			boolean esitoComune = isChiavePresenteInTabellaDecodifica(chiave,UtilConstant.QUERY_COM_T_COMUNE_NASCITA);
	//			boolean esitoStatoEstero = isChiavePresenteInTabellaDecodifica(chiave,UtilConstant.QUERY_COM_T_STATI_ESTERI_NASCITA);
	//
	//			if( !esitoComune && !esitoStatoEstero)
	//				result = false;
	//			else
	//				result = true;
	//		}
	//		return result;		
	//	}
	//	
	//	

	//	
	//	public boolean isIdStatoEsteroValido (String chiave) throws Exception
	//	{
	//		boolean result = false;
	//		if( isVuoto(chiave) ) result = true;
	//		else
	//		{	
	//			boolean esitoStatoEstero = isChiavePresenteInTabellaDecodifica(chiave,UtilConstant.QUERY_COM_T_STATI_ESTERI);
	//			
	//			if( !esitoStatoEstero )
	//				result = false;
	//			else
	//				result = true;
	//		}
	//		return result;
	//	}
	//	
	//	
	//	public boolean isIdComuneLavoratoreValido (String chiave) throws Exception
	//	{
	//		boolean result = false;
	//		if( isVuoto(chiave) ) result = true;
	//		else
	//		{	
	//			boolean esitoComune = isChiavePresenteInTabellaDecodifica(chiave,UtilConstant.QUERY_COM_T_COMUNE);
	//			if( !esitoComune) 
	//				result = false;
	//			else
	//				result = true;
	//		}
	//		return result;
	//	}




	public boolean isNomeCongruente(String nome , String codiceFiscale )
	{
		String codificaNomePerCF_Input = Format.calcolaNome(nome);
		String codificaNomeCF_corretta = codiceFiscale.substring(3,6);
		if( codificaNomePerCF_Input.equals(codificaNomeCF_corretta) )
			return true;
		else
			return false;
	}

	public boolean isCognomeCongruente(String cognome , String codiceFiscale)
	{
		String codificaCognomePerCF_Input = Format.calcolaCognome(cognome);
		String codificaCognomeCF_corretta = codiceFiscale.substring(0, 3);
		if( codificaCognomePerCF_Input.equals(codificaCognomeCF_corretta) )
			return true;
		else
			return false;
	}



	public boolean isSessoCongruente(String sesso , String codiceFiscale)
	{

		// Il giorno di nascita, se maggiorato di 40 vuol dire che il SESSO e' F
		// altrimenti il sesso e' M 
		String sessoCodiceFiscale = "";
		String giornoNascita = codiceFiscale.substring(9 , 11);
		int caratteriGiornoNascita = Integer.parseInt(giornoNascita);
		if( caratteriGiornoNascita > 40 )
			sessoCodiceFiscale = "F";
		else
			sessoCodiceFiscale = "M";
		// Se il sesso  e' uguale al quello in input
		if(sessoCodiceFiscale.equals(sesso))
			return true;
		else
			return false;
	}

	public boolean isDataNascitaCongruenteConCodiceFiscale(Date dataNascita , String sesso , String codiceFiscale)
	{
		boolean result = false;
		if( dataNascita == null || isVuoto(codiceFiscale) || isVuoto(sesso) ) result = true;
		else
		{	
			codiceFiscale = Format.getCodiceFiscaleRipulitoDaOmocodia(codiceFiscale);

			if ( Format.verificaCodiceFiscale(codiceFiscale) )
			{

				String strDataNasc = Format.convertiDataInStringa(dataNascita);
				String caratteridataNascitaInput = Format.calcolaDataNasc( strDataNasc , sesso );
				String caratteriDataNascitaCF = codiceFiscale.substring(6, 11);

				if( caratteridataNascitaInput.equals(caratteriDataNascitaCF) )
					result = true;
				else
					result = false;
			}
		}
		return result;
	}

	public boolean isComuneNascitaCongruenteConCodiceFiscale(String comuneNascita , String codiceFiscale) throws Exception
	{
		boolean result = false;
		if( isVuoto(comuneNascita) || isVuoto(comuneNascita)) result = true;
		else
		{	 
			codiceFiscale = Format.getCodiceFiscaleRipulitoDaOmocodia(codiceFiscale);

			String comnueDiNascitaEstrattoCF = codiceFiscale.substring(11, 15);
			if( comnueDiNascitaEstrattoCF.equals(comuneNascita) )
			{
				result = true;
			}
			else
				result = false;
		}
		return result;
	}

	public boolean isFlgFormaPerTipoContrattoDeterminata(String tipoContratto, DecodificaDad decodificaDad) throws Exception
	{
		TipoContratti tipoContrattoDeco = (TipoContratti)decodificaDad.getTfromMin(TipoContratti.class, tipoContratto, null);
		if (tipoContrattoDeco != null) {
			if(tipoContrattoDeco.getFlgForma() == null){

				return false;
			}
			else{

				return tipoContrattoDeco.getFlgForma().equals("D");
			}
		}
		return false;		

	}

	public boolean isFlgFormaPerTipoContrattoIndeterminata(String tipoContratto, DecodificaDad decodificaDad) throws Exception
	{
		TipoContratti tipoContrattoDeco = (TipoContratti)decodificaDad.getTfromMin(TipoContratti.class, tipoContratto, null);
		if (tipoContrattoDeco != null) {
			if(tipoContrattoDeco.getFlgForma() == null){

				return false;
			}
			else{

				return tipoContrattoDeco.getFlgForma().equals("I");
			}
		}
		return false;		

	}

	public boolean isFlgFormaPerTipoContrattoDeterminataEIndeterminata(String tipoContratto, DecodificaDad decodificaDad) throws Exception
	{
		TipoContratti tipoContrattoDeco = (TipoContratti)decodificaDad.getTfromMin(TipoContratti.class, tipoContratto, null);
		if (tipoContrattoDeco != null) {
			if(tipoContrattoDeco.getFlgForma() == null){

				return false;
			}
			else{

				return tipoContrattoDeco.getFlgForma().equals("E");
			}
		}
		return false;
	}

	/*
	 *  la differenza tra 'Data fine' e 'Data inizio rapporto' (7.1.a.) non deve essere > di 50 anni.
	 */

	public boolean isDifferenzaDataFineDataInizioMaggioreDi50Anni(Date dataFine,Date dataInizio) throws Exception{

		Calendar c1 = new GregorianCalendar();

		Calendar c2 = new GregorianCalendar();

		c1.setTime(dataInizio);

		c2.setTime(dataFine);

		c1.add(Calendar.YEAR, 50);

		if(c1.before(c2)){
			return true;
		}

		return false;
	}



	public boolean validateDate(String text)
	{		
		boolean isValid = false;
		Date date = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			//	impedisce che la stringa venga resa coerente (es. 32 marzo == 1 aprile)
			formatter.setLenient(false);
			//	DateFormat.parse lancia un'eccezione se il testo non e' coerente con il
			//formato.			
			date = formatter.parse(text);
			isValid = true;
		} catch (ParseException e) {
			isValid = false;
		}
		if (date == null){
			isValid = false;	
		}			
		return isValid;		
	}


	public boolean isNumIscrizioneAlboValido( String numIscrAlbo )
	{
		boolean result = false;
		if( isVuoto(numIscrAlbo) ) result = true;
		else
		{
			if( numIscrAlbo.length() != 20 )
				result = false;
			else
			{
				// String parteAlfanumerica = numIscrAlbo.substring(0, 10); 
				String parteFormatoData = numIscrAlbo.substring( 10, 20);
				if( validateDate(parteFormatoData))
				{
					result = true;
				}
				else
				{
					result = false;
				}
			}
		}
		return result;
	}

	public boolean checkIntervalloEtaLavoratoreDataInizioRapporto(Date dataPrecedente, Date dataSuccessiva) {
		int diffAnni = Format.differenzaTra_DateEspresseaInAnni(dataPrecedente, dataSuccessiva);
		return (diffAnni >= 0 && diffAnni <= 100);
	}

	public boolean isDataNascitaMaggioreDataInizioRapporto(Date data1, Date data2) {
		return Format.data2MaggioreData1(data1, data2);
	}

	public boolean isComunicazioneRiferitaAEntePubblico(String pubblicaAmministrazione) {

		return pubblicaAmministrazione.equalsIgnoreCase(UtilConstant.FLAG_SI);

	}


	public boolean isPresenteDenominazioneOCognomeENome(String denominazione, String cognome, String nome) {
		if(isVuoto(denominazione) && isVuoto(cognome) && isVuoto(nome)){
			return false;
		}
		if(isValorizzato(denominazione) && isValorizzato(cognome) && isValorizzato(nome)){
			return false;
		}
		if(isValorizzato(denominazione) && (isValorizzato(cognome) || isValorizzato(nome))){
			return false;
		}
		else
			return true;
	}

	//	public boolean isCodiceComunicazionePrecedentePresenteSuDB(String chiave, String queryDaEseguire ) throws Exception
	//	{
	//		boolean result = true;
	//		if( isVuoto(chiave) ) result = true;
	//		else
	//		{	
	//			String queryCompleta = queryDaEseguire + "'"+ chiave + "'";
	//			QueryTabDecodificaGenerico qry = new QueryTabDecodificaGenerico(queryCompleta);			
	//			Long resultChiave = qry.lanciaQueryLong();
	//			if( resultChiave == null )
	//				result = false;
	//		}
	//		return result;
	//	}


	public boolean isDataNascitaNelFuturo(Date dataNascita){
		Date dataOdierna = Util.getDataOggi();
		return DateUtilities.isData1MaggioreDiData2(dataNascita, dataOdierna);
	}


	public boolean isComunicazioneFuoriTermine(Date dataDiRiferimento, Date dataRicezione, int intervalloAmmesso, boolean checkRitardo) {
		Date dataRiferimentoIncrementata = Format.dateAddDays(dataDiRiferimento, intervalloAmmesso);

		if(checkRitardo){

			CalcolaRitardo ritardo = new CalcolaRitardo();

			while(ritardo.checkFestivita(dataRiferimentoIncrementata)) {
				dataRiferimentoIncrementata = Format.dateAddDays(dataRiferimentoIncrementata, 1);
			}
		}

		return isDataRicezioneMaggioreDataRiferimento(dataRiferimentoIncrementata, dataRicezione);
	}

	public boolean isComunicazioneFuoriTermine(Date dataDiRiferimento, Date dataRicezione) {

		Calendar temp = new GregorianCalendar();

		temp.setTime(dataDiRiferimento);

		int mese = temp.get(Calendar.MONTH) + UtilConstant.INCREMENTO_MESE;
		int anno = temp.get(Calendar.YEAR);
		int giorno = 20;

		temp.set(Calendar.DAY_OF_MONTH, giorno);
		temp.set(Calendar.MONTH, mese);
		temp.set(Calendar.YEAR, anno);

		Date dataRiferimentoIncrementata = temp.getTime();

		return isDataRicezioneMaggioreDataRiferimento(dataRiferimentoIncrementata, dataRicezione);
	}


	public boolean isDataRicezioneMaggioreDataRiferimento(Date data1, Date data2) {
		return Format.data2MaggioreData1(data1, data2);
	}


	public boolean isDateUguali(Date data1, Date data2){
		if(Format.confrontaDate(data1, data2) == 0){
			return true;
		}
		return false;
	}

	public boolean isNotDateUguali(Date data1, Date data2){
		return !isDateUguali(data1, data2);
	}

	public boolean isCodiceStatoEstero (String codiceMinisteriale) throws Exception
	{
		return codiceMinisteriale.toUpperCase().startsWith("Z");

	}

	public boolean isCodiceComune (String codiceMinisteriale) throws Exception
	{
		return !isCodiceStatoEstero(codiceMinisteriale);

	}


	//	 *********************************************************************************************	
	//	 *********************************************************************************************	
	//	 *********************************************************************************************


	public boolean isUNILAV() {
		return UNILAV;
	}

	public void setUNILAV(boolean unilav) {
		UNILAV = unilav;
	}

	public boolean isUNISOM() {
		return UNISOM;
	}

	public void setUNISOM(boolean unisom) {
		UNISOM = unisom;
	}

	public boolean isVARDATORI() {
		return VARDATORI;
	}

	public void setVARDATORI(boolean vardatori) {
		VARDATORI = vardatori;
	}

	public ComunicazioneTracciatoUnicoDTO getComunicazione() {
		return comunicazione;
	}

	public void setComunicazione(ComunicazioneTracciatoUnicoDTO comunicazione) {
		this.comunicazione = comunicazione;
		setTipoTracciato();    	
		setTipoComunicazione(comunicazione);
	}

	//	public IRegistroEsito getRegistratoreEsiti() {
	//		return registratoreEsiti;
	//	}
	//
	//	public void setRegistratoreEsiti(IRegistroEsito registratoreEsiti) {
	//		this.registratoreEsiti = registratoreEsiti;
	//	}
	//
	//	public EsitoControlloComunicazione getElencoErrori() {
	//		return elencoErrori;
	//	}
	//
	//	public void setElencoErrori(EsitoControlloComunicazione elencoErrori) {
	//		this.elencoErrori = elencoErrori;
	//	}

	public boolean isUNIDOM() {
		return UNIDOM;
	}

	public void setUNIDOM(boolean unidom) {
		UNIDOM = unidom;
	}

	public boolean isUNIURG() {
		return UNIURG;
	}

	public void setUNIURG(boolean uniurg) {
		UNIURG = uniurg;
	}

	public Long getIdEntePromotoreTirocinio() {
		return idEntePromotoreTirocinio;
	}

	public void setIdEntePromotoreTirocinio(Long idEntePromotoreTirocinio) {
		this.idEntePromotoreTirocinio = idEntePromotoreTirocinio;
	}



	/////////////////////////////////////// dati comunicazione

	protected LavoratoreDTO getLavoratoreComunicazione() {
		return comunicazione.getLavoratore();
	}
	protected LavoratoreDTO getLavoratoreCoobbligato() {
		return comunicazione.getLavoratoreCoobbligato();
	}
	/////////////////////////////////////////////

	public boolean isStatoEsteroComunitarioValido (String chiave, DecodificaDad decodificaDad, Date dataRif) throws Exception
	{
		boolean result = false;
		if( isVuoto(chiave) ) result = true;
		else
		{	
			if (chiave.startsWith("Z")) {
				StatiEsteri se = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class, chiave, dataRif);
				if (se != null && ComonlConstants.FLAG_S.equalsIgnoreCase(se.getFlgUe()) ) {
					result = true;
				} else {
					result = false;
				}
			} else {
				result = true;
			}
		}
		return result;
	}
	public boolean isStatoEsteroValido (String chiave, DecodificaDad decodificaDad, Date dataRif) throws Exception
	{
		boolean result = false;
		if( isVuoto(chiave) ) result = true;
		else
		{
			if (chiave.startsWith("Z")) {
				StatiEsteri se = (StatiEsteri) decodificaDad.getTfromMin(StatiEsteri.class, chiave, dataRif);
				if (se != null && se.getId() != null) {
					result = true;
				} else {
					result = false;
				}
			} else {
				result = false;
			}
		}
		return result;
	}

	public boolean isIdComuneValido (String chiave, DecodificaDad decodificaDad, Date dataRif) throws Exception
	{
		boolean result = false;
		if( isVuoto(chiave) ) result = true;
		else
		{	
			Comune c = (Comune) decodificaDad.getTfromMin(Comune.class, chiave, dataRif);
			if (c!= null && c.getId() != null) {
				result = true;
			} else {
				result = false;
			}
		}
		return result;
	}
	public boolean isComuneOStatoEsteroComunitarioValido (String chiave, DecodificaDad decodificaDad, Date dataRif) throws Exception
	{
		boolean result = false;
		if( isVuoto(chiave) ) result = true;
		else
		{	
			boolean esitoComune = isIdComuneValido(chiave, decodificaDad, dataRif);
			boolean esitoStatoEstero = isStatoEsteroComunitarioValido(chiave, decodificaDad, dataRif);

			if( !esitoComune && !esitoStatoEstero)
				result = false;
			else
				result = true;
		}
		return result;
	}
	public boolean isIdComuneOStatoEsteroValido (String chiave, DecodificaDad decodificaDad, Date dataRif) throws Exception
	{
		boolean result = false;
		if( isVuoto(chiave) ) result = true;
		else
		{	
			boolean esitoComune = isIdComuneValido(chiave, decodificaDad, dataRif);
			boolean esitoStatoEstero = isStatoEsteroValido(chiave, decodificaDad, dataRif);

			if( !esitoComune && !esitoStatoEstero)
				result = false;
			else
				result = true;
		}
		return result;
	}

	public boolean isSoggettoNonValorizzatoONonAbilitatoInAccentramento( String codiceFiscaleSoggettoAbilitato, DecodificaDad decodificaDad  ) throws Exception
	{


		boolean result = false;
		if( isVuoto(codiceFiscaleSoggettoAbilitato))
		{
			// SE NON C'E' IL CODICE FISCALE DEL SOGGETTO ABILITATO , VAI AVANTI
			result = true;
		}
		else
		{

			String flagAccentramento = decodificaDad.getFlgAccentramento(codiceFiscaleSoggettoAbilitato);

			if ( isValorizzato(flagAccentramento) )
			{
				// verifichiamo CHE IL FLAG SIA DIVERSO DA S
				if( !flagAccentramento.equals( UtilConstant.FLAG_S ) ){
					result = true;
				}
			}
			else 
			{
				result = true;
			}
		}
		return result;
	}

	public boolean  isDatoreLavoroPresenteInAccentramento( String codiceFiscaleDatoreLavoro , DecodificaDad decodificaDad   ) throws Exception
	{
		boolean result = false;
		String cfFromDb = null;
		try {
			cfFromDb= decodificaDad.getAnagraficaAziAccent(codiceFiscaleDatoreLavoro);			
		} catch (NotFoundException e) {
			
		}
		// Se il cofFisc Datore  e'  presente nella tabella degli accentramenti
		if(cfFromDb != null && !"".equalsIgnoreCase(cfFromDb))
		{
			result = true;
		}
		return result;
	}

	public boolean isComunePiemontese ( String codComuneMin , DecodificaDad decodificaDad) throws Exception
	{


		Comune comune = (Comune) decodificaDad.getTfromMin(Comune.class, codComuneMin, null);
		if (comune != null  
				&& comune.getProvincia() != null
				&& comune.getProvincia().getRegione() != null
				&& UtilConstant.ID_REGIONE_PIEMONTE.equals(comune.getProvincia().getRegione().getId())) {
			return true;
		}
		return false;
	}


	// GESTIONE TIPO ORARIO e NUM ore settimanale
	public void controllaERegistraErroreTipoOrarioENumOreSettimanali ( String tipoContratto, 
			String tipoOrarioOggettone,
			Long numOreSettimanaliMedie,
			long idErroreTipoOrario,
			long idErroreNumOreSettimanaliValorizzate,
			long idErroreNumOreSettimanaliNonValorizzate,
			long idErroreTipoOrarioTipoContratto,
			boolean isNotVardatore, 
			CommonDad commonDad, 
			DecodificaDad decodificaDad, 
			List<ApiError> elencoErrori, 
			String stringParametriPerDescrizoneErrore) throws Exception
	{
		// tipo orario
		if(  isValorizzato( tipoOrarioOggettone ))
		{	
			boolean esitoTipoOrario = false;
			TipoOrario tipoOrarioDB = (TipoOrario) decodificaDad.getTfromMin(TipoOrario.class, tipoOrarioOggettone, null);
			esitoTipoOrario = (tipoOrarioDB != null && tipoOrarioDB.getId() !=null ? true : false);

			if( esitoTipoOrario )
			{
				String idComTipoOrario = String.valueOf(tipoOrarioDB.getId());

				// ore settimanali medie
				// MODIFICA - ADEGUAMENTO MINISTERIALE :
				// Aggiunta voce  : N - non definito

				//				 Aggiungere i seguenti controlli che generano uno scarto dell'XML che si sta caricando, in queste casistiche:
				//
				//					1- per tutte le comunicazioni, in caso di tipo orario 'NON DEFINITO' le ore settimanali non devono essere valorizzate
				//
				//					2- in caso di comunicazioni di TRASFORMAZIONE non e' possibile indicare una trasformazione di orario (da part time a full time, 
				//					   e viceversa) se l'utente, nella sezione del rapporto trasformato, ha indicato il tipo orario "NON DEFINITO"
				//				 

				// Se  e'  un tempo pieno o un NON DEFINITO non deve avere ore settimanali specificate
				if((idComTipoOrario.equalsIgnoreCase(UtilConstant.TIPO_ORARIO_TEMPO_PIENO) 
						|| idComTipoOrario.equalsIgnoreCase(UtilConstant.TIPO_ORARIO_NON_DEFINITO)) 
						&& (numOreSettimanaliMedie != null))
				{
					if (stringParametriPerDescrizoneErrore != null) {
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, idErroreNumOreSettimanaliValorizzate, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
						
					} else {
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, idErroreNumOreSettimanaliValorizzate, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}
				}	

				else if(!(idComTipoOrario.equalsIgnoreCase(UtilConstant.TIPO_ORARIO_TEMPO_PIENO) 
						|| idComTipoOrario.equalsIgnoreCase(UtilConstant.TIPO_ORARIO_NON_DEFINITO)) 
						&& (numOreSettimanaliMedie == null))
				{
					if (stringParametriPerDescrizoneErrore != null) {
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, idErroreNumOreSettimanaliNonValorizzate, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
						
					} else {
						RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, idErroreNumOreSettimanaliNonValorizzate, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
					}

				}

				if(isValorizzato(tipoContratto) && idComTipoOrario.equalsIgnoreCase(UtilConstant.TIPO_ORARIO_NON_DEFINITO )){

					//tiro su tutti i dati della COM_T_TIPO_CONTR_AMM_PER_COM. 
					// poi dal codice min del tipo contratto prendo l'id e ciclo fino a trovarlo su COM_T_TIPO_CONTR_AMM_PER_COM
					// e prendo il flgTipoOrarioNonDefinito
					TipoContratti contratto = (TipoContratti) decodificaDad.getTfromMin(TipoContratti.class, tipoContratto, null);
					List<TipoContrAmmPerCom> contrattiAmmessi = decodificaDad.getDatiTipoContrattoAmmessoPerCom();

					if ( contratto != null && contrattiAmmessi != null && !contrattiAmmessi.isEmpty()) {
						String flgTipoOrarioNonDefinito = "";
						for (TipoContrAmmPerCom contrattoAmmesso : contrattiAmmessi) {
							if (contratto.getId().equals(contrattoAmmesso.getTipoContratti().getId())) {
								flgTipoOrarioNonDefinito = contrattoAmmesso.getFlgTipoOrarioNonDefinito();
								break;
							}
						}
						if((UtilConstant.FLAG_N).equalsIgnoreCase(flgTipoOrarioNonDefinito)) {
							if (stringParametriPerDescrizoneErrore != null) {
								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, idErroreTipoOrarioTipoContratto, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
								 
							} else {
								RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, idErroreTipoOrarioTipoContratto, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
							}
						}
					}
				}
			}
			else {
				if (stringParametriPerDescrizoneErrore != null) {
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, idErroreTipoOrario, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
				} else {
					RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, idErroreTipoOrario, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
				}
				
			}

		}else if (isNotVardatore){
			if (stringParametriPerDescrizoneErrore != null) {
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, idErroreTipoOrario, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
				
			} else {
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, idErroreTipoOrario, null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			}
		}
	}	
	/**
	 * Metodo che determina se un tipo contratto sia lecito per un certo tipo di comunicazione
	 * Nei casi dubbi sono ammessi tutti i tipi di contratto 
	 * @param obj
	 * @param tipoComunicazione
	 * @return
	 */
	private boolean isTipoContrattoAmmessoPerComunicazione(TipoContrAmmPerCom obj,
			String tipoComunicazione, 
			boolean isUnisomm,
			boolean isUnidom) 
	{
		if (obj==null){

			return false;
		}
		else if (tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_PROROGA)
				|| (tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_PROROGA_CONTESTUALE_A_PROROGA_DI_MISSIONE)
						|| (tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_PROROGA_MISSIONE))))
		{
			return isAmmessaProrogaOProrogaContestualeASomministrazioneOProrogaDiUnidom(obj, isUnisomm, isUnidom);
		}
		else if (tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE)
				|| tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_FINE_MISSIONE))
		{
			return isAmmessaCessazioneOCessazioneContestualeASomministrazioneOCessazioneDiUnidom(obj, isUnisomm, isUnidom);

		}
		else if (tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_TRASFORMAZIONE)){

			return isAmmessaTrasformazioneOTrasformazioneContestualeASomministrazioneOTrasformazioneDiUnidom(obj, isUnisomm, isUnidom);

		}
		else if (tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_TRASFERIMENTO_RAMO_AZIENDALE)
				|| tipoComunicazione.equalsIgnoreCase(UtilConstant.TRASFERIMENTO)){

			return isAmmessoTrasferimentoOTrasferimentoContestualeASomministrazioneOTrasferimentoUnidom(obj, isUnisomm, isUnidom);
		}	
		else if (tipoComunicazione.equalsIgnoreCase(UtilConstant.DISTACCO)){
			return isAmmessoDistacco(obj);
		}
		else if ((tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE_CONTESTUALE_A_INIZIO_MISSIONE) || 
				tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE)) 
				&& 
				isUnisomm){

			return isAmmessaAssunzioneContestualeASomministrazione(obj);
		}
		else if(tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE) && isUnidom){
			return isAmmessaAssunzioneUnidom(obj);

		}
		else if(tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_ASSUNZIONE)){
			return isAmmessaAssunzioneUnilav(obj);

		}
		else{
			return true;
		}
	}
	private boolean isAmmessoTrasferimentoOTrasferimentoContestualeASomministrazioneOTrasferimentoUnidom(TipoContrAmmPerCom obj, boolean isUnisomm, boolean isUnidom) {
		if (isUnisomm)
		{
			return UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldTra()) && UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldSom());
		}
		if (isUnidom)
		{
			return UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldTra()) && UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldUd());
		}
		else
		{
			return UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldTra());
		}
	}

	private boolean isAmmessoDistacco(TipoContrAmmPerCom obj) {
		return UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldDst());

	}

	private boolean isAmmessaTrasformazioneOTrasformazioneContestualeASomministrazioneOTrasformazioneDiUnidom(TipoContrAmmPerCom obj, boolean isUnisomm, boolean isUnidom) {
		if (isUnisomm)
		{
			return UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldTrs()) && UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldSom());
		}
		if (isUnidom)
		{
			return UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldTrs()) && UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldUd());
		}
		else
		{
			return UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldTrs());
		}
	}

	private boolean isAmmessaCessazioneOCessazioneContestualeASomministrazioneOCessazioneDiUnidom(TipoContrAmmPerCom obj, boolean isUnisomm, boolean isUnidom) {
		if (isUnisomm)
		{
			return UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldCes()) && UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldSom());
		}
		else if(isUnidom){
			return UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldCes()) && UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldUd());
		}
		else
		{
			return UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldCes());
		}
	}

	private boolean isAmmessaProrogaOProrogaContestualeASomministrazioneOProrogaDiUnidom(TipoContrAmmPerCom obj, boolean isUnisomm, boolean isUnidom) {		
		if (isUnisomm)
		{
			return UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldPro()) && UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldSom());
		}
		else if (isUnidom)
		{
			return UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldPro()) && UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldUd());
		}
		else
		{
			return UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldPro());
		}
	}

	private boolean isAmmessaAssunzioneContestualeASomministrazione(TipoContrAmmPerCom obj) {
		return UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldSom()) ;
	}

	private boolean isAmmessaAssunzioneUnilav(TipoContrAmmPerCom obj) {
		return UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldUl());
	}

	private boolean isAmmessaAssunzioneUnidom(TipoContrAmmPerCom obj) {
		return UtilConstant.FLAG_S.equalsIgnoreCase(obj.getFlgVldUd());
	}
	protected void addErrorsToRegistroErrori (ArrayList errors, CommonDad commonDad, List<ApiError> elencoErrori) throws Exception {
		if (errors != null && errors.size() > 0) {
			for (int i=0;i<errors.size();i++) {
				Long codiceErr = (Long)errors.get(i);
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
						codiceErr.longValue(), null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad);
			}
		}	
	}
	protected void addErrorsToRegistroErrori (ArrayList errors, CommonDad commonDad, List<ApiError> elencoErrori, String stringParametriPerDescrizoneErrore) throws Exception {
		if (errors != null && errors.size() > 0) {
			for (int i=0;i<errors.size();i++) {
				Long codiceErr = (Long)errors.get(i);
				RegistrazioneEsitiControlli.registraErroreSeEsitoControlloNegativo(false, 
						codiceErr.longValue(), null, elencoErrori, ComonlConstants.TIPO_PROVENIENZA_COMMAX, commonDad, stringParametriPerDescrizoneErrore);
			}
		}	
	}
	public boolean isTipoContrattoRegolamentatoPerComunicazione(String codContratto, String codTipoTracciato, String codTipoComunicazione, CommonDad commonDad)
			throws Exception {
		boolean result = false;

		if (tipoComunicazione.equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE)) {
			List<ComRRegTracciatoContratto> rego = commonDad.findByCodContrattoTipoTracciatoTipoCo(codContratto, codTipoTracciato, codTipoComunicazione);
			if (rego != null && !rego.isEmpty() && rego.size() == 1) {
				return true;
			}
		}
		return result;
	}
	public boolean isTipoContrattoRegolamentatoPerComunicazioneAndDataCongruente( String codContratto, String codTipoTracciato, String codTipoComunicazione, Date dtInizioRapporto, CommonDad commonDad) throws Exception {
		boolean result = false;
		if (tipoComunicazione .equalsIgnoreCase(TUConstants.TIPO_COMUNICAZIONE_CESSAZIONE)) {

			List<ComRRegTracciatoContratto> rego = commonDad.findByCodContrattoTipoTracciatoTipoCo(codContratto, codTipoTracciato, codTipoComunicazione);
			if (rego != null && !rego.isEmpty() && rego.size() == 1) {
				String dataInizioRapportoStr = DateUtil.convertiDataInStringa(dtInizioRapporto);
				String dataRiferimento = DateUtil.convertiDataInStringa(rego.get(0).getDRiferimento());
				
				return (DateUtilities.isData1MinoreOUgualeAData2(dataInizioRapportoStr, dataRiferimento));

			}
		}
		return result;
	}
	
	public boolean matchRegolaTirocinio (String nomeRegola, String codiceTipologiaTirocinioMinisteriale, String codiceCategTirocinanteMinisteriale, CommonDad commonDad, DecodificaDad decodificaDad) {
		boolean result = false;
		
		// tiro su l'id dei due codici miniseriali in input dalle tabelle di decodifica di competenza
		TipologiaTirocinio tipologiaTirocinio = (TipologiaTirocinio)decodificaDad.getTfromMin(TipologiaTirocinio.class, codiceTipologiaTirocinioMinisteriale, null);
		CategTirocinante categoriaTirocinante = (CategTirocinante)decodificaDad.getTfromMin(CategTirocinante.class, codiceCategTirocinanteMinisteriale, null);
		
		// con gli ID vado sulla tabella di relazione COM_R_TIPOL_TIROC_CAT_TIROC e cerco la tupla. Se la trovo la combinazione e' valida se no, non lo e'
		if (UtilConstant.REGOLA_TIPOLOGIA_TIROCINIO_CATEGORIA_TIROCINANTE.equals(nomeRegola)) {
			result = commonDad.findCombinazioneTipologiaTirocinioCategoriaTirocinante(tipologiaTirocinio.getId(), categoriaTirocinante.getId());
		}
		return result;
	} 

	public EsitoControlloComunicazione getElencoErrori() {
		return elencoErrori;
	}

	public void setElencoErrori(EsitoControlloComunicazione elencoErrori) {
		this.elencoErrori = elencoErrori;
	}
}
