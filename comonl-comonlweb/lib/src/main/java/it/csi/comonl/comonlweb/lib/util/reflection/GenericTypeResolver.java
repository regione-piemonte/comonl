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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Resolver for generic types
 */
public class GenericTypeResolver {

	/** Prevent instantiation */
	private GenericTypeResolver() {
		// Utility classes sould NOT have public constructors
	}

	/**
	 * Resolves the actual generic type arguments for a base class, as viewed from a
	 * subclass or implementation.
	 *
	 * @param            <T> base type
	 * @param offspring  class or interface subclassing or extending the base type
	 * @param base       base class
	 * @param actualArgs the actual type arguments passed to the offspring class
	 * @return actual generic type arguments, must match the type parameters of the offspring class. If omitted, the type parameters will be used instead.
	 */
	public static <T> Type[] resolveActualTypeArgs(Class<? extends T> offspring, Class<T> base, Type... actualArgs) {
		if(offspring == null) {
			throw new IllegalArgumentException("Null offspring class");
		}
		if(base == null) {
			throw new IllegalArgumentException("Null base class");
		}
		if(actualArgs.length != 0 && actualArgs.length != offspring.getTypeParameters().length) {
			throw new IllegalArgumentException("Arguments do not match class arguments");
		}
		// If actual types are omitted, the type parameters will be used instead.
		Type[] innerArgs = actualArgs;
		if (innerArgs.length == 0) {
			innerArgs = offspring.getTypeParameters();
		}
		// Map type parameters into the actual types
		Map<String, Type> typeVariables = new HashMap<>();
		for (int i = 0; i < innerArgs.length; i++) {
			TypeVariable<?> typeVariable = offspring.getTypeParameters()[i];
			typeVariables.put(typeVariable.getName(), innerArgs[i]);
		}

		// Find direct ancestors (superclass, interfaces)
		List<Type> ancestors = new LinkedList<>();
		if (offspring.getGenericSuperclass() != null) {
			ancestors.add(offspring.getGenericSuperclass());
		}
		for (Type t : offspring.getGenericInterfaces()) {
			ancestors.add(t);
		}

		// Recurse into ancestors (superclass, interfaces)
		for (Type type : ancestors) {
			if (type instanceof Class<?>) {
				// Ancestor is non-parameterized. Recurse only if it matches the base class.
				Class<?> ancestorClass = (Class<?>) type;
				if (base.isAssignableFrom(ancestorClass)) {
					@SuppressWarnings("unchecked")
					Type[] result = resolveActualTypeArgs((Class<? extends T>) ancestorClass, base);
					if (result != null) {
						return result;
					}
				}
			}
			if (type instanceof ParameterizedType) {
				// ancestor is parameterized. Recurse only if the raw type matches the base
				// class.
				ParameterizedType parameterizedType = (ParameterizedType) type;
				Type rawType = parameterizedType.getRawType();
				if (rawType instanceof Class<?>) {
					Class<?> rawTypeClass = (Class<?>) rawType;
					if (base.isAssignableFrom(rawTypeClass)) {

						// Loop through all type arguments and replace type variables with the actually known types
						List<Type> resolvedTypes = new LinkedList<>();
						for (Type t : parameterizedType.getActualTypeArguments()) {
							if (t instanceof TypeVariable<?>) {
								Type resolvedType = typeVariables.get(((TypeVariable<?>) t).getName());
								resolvedTypes.add(resolvedType != null ? resolvedType : t);
							} else {
								resolvedTypes.add(t);
							}
						}

						@SuppressWarnings("unchecked")
						Type[] result = resolveActualTypeArgs((Class<? extends T>) rawTypeClass, base, resolvedTypes.toArray(new Type[resolvedTypes.size()]));
						if (result != null) {
							return result;
						}
					}
				}
			}
		}

		// we have a result if we reached the base class.
		return offspring.equals(base) ? innerArgs : null;
	}

}
