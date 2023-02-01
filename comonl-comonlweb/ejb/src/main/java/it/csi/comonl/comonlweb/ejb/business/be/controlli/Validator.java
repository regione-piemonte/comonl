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
package it.csi.comonl.comonlweb.ejb.business.be.controlli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.ibm.icu.util.Calendar;

import it.csi.comonl.comonlweb.ejb.exception.ParamValidationException;
import it.csi.comonl.comonlweb.lib.dto.ApiError;
import it.csi.comonl.comonlweb.lib.dto.BaseDto;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;
import it.csi.comonl.comonlweb.lib.dto.decodifiche.TipoProvenienza;
import it.csi.comonl.comonlweb.lib.dto.error.CoreError;
import it.csi.comonl.comonlweb.lib.dto.error.MsgComonl;
import it.csi.comonl.comonlweb.lib.util.ComonlConstants;

public abstract class Validator<T> {
	
	protected final T controlObject;
	protected T dbObject;
	protected boolean checkDatiEssenziali;
	protected List<ApiError> apiErrors = new ArrayList<ApiError>(); 
	protected List<ApiError> apiWarnings = new ArrayList<ApiError>();
	protected Ruolo ruolo;
	protected TipoProvenienza tipoProvenienza;
	
	
	public Validator(T controlObject) {
		this(controlObject, false, null);
	}
	public Validator(T controlObject, boolean checkDatiEssenziali, T dbObject) {
		this.controlObject = controlObject;
		this.checkDatiEssenziali = checkDatiEssenziali;
		this.dbObject = dbObject;
	}
	public abstract boolean isOk();
	
	public List<ApiError> getApiErrors() {
		return apiErrors;
	}
	public List<ApiError> getApiWarnings() {
		return apiWarnings;
	}
	
	public T getControlObject() {
		return controlObject;
	}
	
	public T getDbObject() {
		return dbObject;
	}
	public boolean isCheckDatiEssenziali() {
		return checkDatiEssenziali;
	}
	public void setDbObject(T dbObject) {
		this.dbObject = dbObject;
	}
	public void setCheckDatiEssenziali(boolean checkDatiEssenziali) {
		this.checkDatiEssenziali = checkDatiEssenziali;
	}
	public Ruolo getRuolo() {
		return ruolo;
	}
	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}
	
	public TipoProvenienza getTipoProvenienza() {
		return tipoProvenienza;
	}
	public void setTipoProvenienza(TipoProvenienza tipoProvenienza) {
		this.tipoProvenienza = tipoProvenienza;
	}
	protected void addApiError(ApiError apiError) {
		if (this.apiErrors == null) {
			apiErrors = new ArrayList<ApiError>();
		}
		this.apiErrors.add(apiError);
	}
	
	protected void addWarning(ApiError apiWarning) {
		if (this.apiWarnings == null) {
			apiWarnings = new ArrayList<ApiError>();
		}
		this.apiWarnings.add(apiWarning);
	}
	
	protected void checkCondition(boolean condition, ApiError message) {
		checkCondition(condition, message, false);
	}
	/**
	 * Checks a condition
	 * @param condition the condition
	 * @param message the message
	 * @param throwException whether to throw an exception
	 * @if the condition is not satisfied and was asked to throw
	 */
	protected void checkCondition(boolean condition, ApiError message, boolean throwException) {
		if(condition) {
			return;
		}
		addApiError(message);
		if(throwException) {
			throw new ParamValidationException(message.getFullErrorMessage());
		}
	}
	
	/**
	 * Checks a condition
	 * @param condition the condition
	 * @param message the message
	 */
	protected void checkConditionWarning(boolean condition, ApiError message) {
		if(condition) {
			return;
		}
		addWarning(message);
	}
	
	/**
	 * Checks whether the model given is correctly populated with its id
	 * @param model the model to check
	 * @param fieldName the name of the field
	 * @if the field does not comply with the constraints and was asked to throw
	 */
	protected <K> void checkModel(BaseDto<K> model, String fieldName) {
		checkModel(model, fieldName, false);
	}

	/**
	 * Checks whether the model given is correctly populated with its id
	 * @param model the model to check
	 * @param fieldName the name of the field
	 * @param throwException whether to throw an exception
	 * @if the field does not comply with the constraints and was asked to throw
	 */
	protected <K> void checkModel(BaseDto<K> model, String fieldName, boolean throwException) {
		checkNotNull(model, fieldName, throwException);
		checkCondition(model == null || model.getId() != null, CoreError.REQUIRED_FIELD_OMITTED.getError("parameter", fieldName), throwException);
	}
	/**
	 * Checks that the field is not null
	 * @param field the field to check
	 * @param fieldName the field name
	 * @if the field is null
	 */
	protected void checkNotNull(Object field, String fieldName) {
		checkNotNull(field, fieldName, false);
	}
	/**
	 * Checks that the field is not null
	 * @param field the field to check
	 * @param fieldName the field name
	 * @param throwException whether to throw an exception
	 * @if the field is null and was asked to throw
	 */
	protected void checkNotNull(Object field, String fieldName, boolean throwException) {
		checkCondition(field != null, CoreError.REQUIRED_FIELD_OMITTED.getError("parameter", fieldName),throwException);
	}
	/**
	 * Checks that the field is not null nor empty
	 * @param field the field to check
	 * @param fieldName the field name
	 * @if the field is null
	 */
	protected void checkNotEmpty(CharSequence field, String fieldName) {
		checkNotEmpty(field, fieldName, false);
	}

	/**
	 * Checks that the field is not null nor empty
	 * @param field the field to check
	 * @param fieldName the field name
	 * @param throwException whether to throw an exception
	 * @if the field is null or empty and was asked to throw
	 */
	protected void checkNotEmpty(CharSequence field, String fieldName, boolean throwException) {
		checkCondition(StringUtils.isNotBlank(field), CoreError.REQUIRED_FIELD_OMITTED.getError("parameter", fieldName),throwException);
	}
	
	/**
	 * Check that the field if not null has the passed values
	 * @param field the field to check
	 * @param fieldName the field name
	 * @param throwException whether to throw an exception
	 * @if the field is null or empty and was asked to throw
	 */
	protected void checkAllowedValues(CharSequence field, ApiError message, CharSequence... values) {
		checkAllowedValues(field, null, true, message, values);
	}
	
	/**
	 * Check that the field is not null and it has the passed values
	 * @param field the field to check
	 * @param fieldName the field name
	 * @param throwException whether to throw an exception
	 * @if the field is null or empty and was asked to throw
	 */
	protected void checkAllowedValues(CharSequence field, String fieldName, boolean nullable, ApiError message, CharSequence... values) {
		if (!nullable)
			checkNotEmpty(field, fieldName,false);
		if (field==null)
			return;
		
		checkCondition(Arrays.asList(values).contains(field), message, false);
	}
	protected void checkAllowedValuesFlag(CharSequence field, String fieldName, boolean nullable) {
		checkAllowedValues(field, fieldName, nullable, MsgComonl.COMCOME0003.getError("parameter",fieldName),ComonlConstants.FLAG_S, ComonlConstants.FLAG_N);
	}
	
	protected void checkDecodingValid(Object field, String... params) {
		checkCondition(field != null, MsgComonl.COMCOME0022.getError("field", params[0], "code",params[1],"desc",params[2]),false);
	}
	/**
	 * Checks that the field respects the length constraint
	 * @param field the field to check
	 * @param fieldName the field name
	 * @param minLength the minimum length (set 0 to skip)
	 * @param maxLength the maximum length (set 0 to skip)
	 * @if the field does not comply with the constraints
	 */
	protected void checkCharSequenceLength(CharSequence field, String fieldName, int minLength, int maxLength) {
		checkCharSequenceLength(field, fieldName, minLength, maxLength, false);
	}

	/**
	 * Checks that the field respects the length constraint
	 * @param field the field to check
	 * @param fieldName the field name
	 * @param minLength the minimum length (set 0 to skip)
	 * @param maxLength the maximum length (set 0 to skip)
	 * @param throwException whether to throw an exception
	 * @if the field does not comply with the constraints and was asked to throw
	 */
	protected void checkCharSequenceLength(CharSequence field, String fieldName, int minLength, int maxLength, boolean throwException) {
		checkCondition(minLength == 0 || StringUtils.length(field) >= minLength,
			CoreError.STRING_PARAMETER_LENGTH_CHECK_FAILED.getError("fieldname", fieldName, "operator", ">=", "value", Integer.valueOf(minLength)),
			throwException);
		checkCondition(maxLength == 0 || StringUtils.length(field) <= maxLength,
			CoreError.STRING_PARAMETER_LENGTH_CHECK_FAILED.getError("fieldname", fieldName, "operator", "<=", "value", Integer.valueOf(maxLength)),
			throwException);
	}

	protected void checkDatoEssenziale(CharSequence field, CharSequence field2, String fieldName) {
		if (StringUtils.isBlank(field) && StringUtils.isBlank(field2))
			return;
		if (StringUtils.isBlank(field) && StringUtils.isNotBlank(field2)) {
			checkCondition(false, MsgComonl.COMCOME0006.getError("parameter",fieldName),false);
			return;
		}
		checkCondition(field.equals(field2), MsgComonl.COMCOME0006.getError("parameter",fieldName),false);
	}
	protected void checkDatoEssenzialeDate(Date field, Date field2, String fieldName) {
		if (field == null && field2==null)
			return;
		if ((field==null && field2!=null) || (field!=null && field2==null)) {
			checkCondition(false, MsgComonl.COMCOME0006.getError("parameter",fieldName),false);
			return;
		}
		checkCondition(compareTo(field,field2)==0, MsgComonl.COMCOME0006.getError("parameter",fieldName),false);
	}
	protected <K> void checkDatoEssenzialeModel(BaseDto<K> field, BaseDto<K> field2, String fieldName) {
		if (isModelNull(field) && isModelNull(field2))
			return;
		if (isModelNull(field) && !isModelNull(field2)) {
			checkCondition(false, MsgComonl.COMCOME0006.getError("parameter",fieldName),false);
			return;
		}
		checkCondition(field.getId()==field2.getId(), MsgComonl.COMCOME0006.getError("parameter",fieldName),false);
	}
	
	protected <K> boolean isModelNull(BaseDto<K> model) {
		return model == null || model.getId() == null;
	}
	
	protected boolean isInError() {
		return this.apiErrors!=null && this.apiErrors.size() > 0;
	}

	protected int compareTo (Date dateA, Date dateB) {
		return DateUtils.truncatedCompareTo(dateA, dateB, Calendar.DATE);
	}
}
