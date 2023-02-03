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
package it.csi.comonl.comonlweb.lib.util.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Reflection utilities
 */
public class ReflectionHelper {

	private static final String PROXY_NAME_CONTENT = "$Proxy$";

	/** Prevent instantiation */
	private ReflectionHelper() {
		// Prevent instantiation
	}

	/**
	 * Retrieves a field by its annotation
	 * @param <A> the annotation type
	 * @param clazz the class
	 * @param annotationClass the annotation
	 * @return the field
	 */
	public static <A extends Annotation> Field getFieldByAnnotation(Class<?> clazz, Class<A> annotationClass) {
		if(clazz == null) {
			return null;
		}
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields) {
			A annotation = field.getAnnotation(annotationClass);
			if(annotation != null) {
				return field;
			}
		}
		return getFieldByAnnotation(clazz.getSuperclass(), annotationClass);
	}
	
	/**
	 * Tries to remove the proxy from a class.
	 * <p>For instance, WELD proxy classes are NON-Proxy classes, with names of the form
	 * <pre>&lt;original name&gt;$Proxy$_$$_WeldSubclass</pre>
	 * We try to intercept these cases
	 * @param initialClass the initial class
	 * @return the unproxied class
	 */
	public static Class<?> deProxy(Class<?> initialClass) {
		Class<?> targetClass = initialClass;
		while(targetClass != null && targetClass.getName().contains(PROXY_NAME_CONTENT)) {
			targetClass = targetClass.getSuperclass();
		}
		return targetClass != null ? targetClass : initialClass;
	}

}
