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
package it.csi.comonl.comonlweb.ejb.util.commax.util;

import java.util.Date;

public class DateUtilities {
	
	public static boolean isData1MaggioreDiData2(Date data1,Date data2)
	{
		if( data1.after(data2)  )
		{
			return true;
		}
		else
			return false;
	}
	
	public static boolean isData1MaggioreOUgualeAData2(Date data1,Date data2)
	{
		if( data1.after(data2)  )
		{
			return true;
		}
		else if( data1.equals(data2)  )
		{
			return true;
		}
		else
			return false;
	}
	
	public static boolean isData1MinoreOUgualeAData2(Date data1,Date data2)
	{
		if( data1.before(data2)  )
		{
			return true;
		}
		else if( data1.equals(data2)  )
		{
			return true;
		}
		else
			return false;
	}

	public static boolean isData1InferioreDiData2(Date data1,Date data2)
	{
		if( data1.before(data2)  )
		{
			return true;
		}
		else
			return false;
	}

	public static boolean isData1MaggioreOUgualeAData2(String data1, String data2) {
		Date d1 = DateUtil.convertiStringaInData(data1);
		Date d2 = DateUtil.convertiStringaInData(data2);

		return isData1MaggioreOUgualeAData2(d1, d2);
	}
	
	public static boolean isData1MinoreOUgualeAData2(String data1, String data2) {
		Date d1 = DateUtil.convertiStringaInData(data1);
		Date d2 = DateUtil.convertiStringaInData(data2);

		return isData1MinoreOUgualeAData2(d1, d2);
	}
	
}
