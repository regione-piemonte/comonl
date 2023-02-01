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
package it.csi.comonl.comonlweb.ejb.business.be.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.csi.comonl.comonlweb.ejb.business.be.dao.BaseDao;
import it.csi.comonl.comonlweb.ejb.util.jpa.Page;
import it.csi.comonl.comonlweb.ejb.util.jpa.PageImpl;
import it.csi.comonl.comonlweb.lib.util.log.LogUtil;
import it.csi.comonl.comonlweb.lib.util.pagination.Sort;
import it.csi.comonl.comonlweb.lib.util.reflection.GenericTypeResolver;

/**
 * Base Data Access Object (DAO) implementor
 * @param <T> the type
 */
public abstract class BaseDaoImpl<T> implements BaseDao<T> {

	/** The persistence context for JPA */
	@PersistenceContext protected EntityManager entityManager;
	/** The underlying class */
	protected final Class<T> clazz;
	/** The logger */
	protected final LogUtil log = new LogUtil(getClass());
	
	/**
	 * Default constructor
	 */
	@SuppressWarnings("unchecked")
	protected BaseDaoImpl() {
		this.clazz = (Class<T>) GenericTypeResolver.resolveActualTypeArgs(getClass(), BaseDaoImpl.class)[0];
	}

	@Override
	public void flush() {
		entityManager.flush();
	}

	@Override
	public void clear() {
		entityManager.clear();
	}

	@Override
	public void flushAndClear() {
		entityManager.flush();
		entityManager.clear();
	}

	/**
	 * Composes a typed query
	 * @param <E> the entity type
	 * @param jpql the JPQL to compose the query for
	 * @param params the parameters for the query
	 * @param entityClass the class for the entity
	 * @return the typed query
	 */
	protected <E> TypedQuery<E> composeTypedQuery(CharSequence jpql, Map<String, Object> params, Class<E> entityClass) {
		final String methodName = "composeTypedQuery";
		if(jpql == null) {
			throw new IllegalArgumentException("The JPQL string must be set");
		}
		log.trace(methodName, () -> traceSql(jpql, params));
		TypedQuery<E> query = entityManager.createQuery(jpql.toString(), entityClass);
		return replaceParams(query, params);
	}
	
	/**
	 * Composes a typed query
	 * @param jpql the JPQL to compose the query for
	 * @param params the parameters for the query
	 * @return the typed query
	 */
	protected TypedQuery<T> composeTypedQuery(CharSequence jpql, Map<String, Object> params) {
		return composeTypedQuery(jpql, params, clazz);
	}

	/**
	 * Composes a query
	 * @param jpql the JPQL to compose the query for
	 * @param params the parameters for the query
	 * @return the query
	 */
	protected Query composeQuery(CharSequence jpql, Map<String, Object> params) {
		final String methodName = "composeQuery";
		if(jpql == null) {
			throw new IllegalArgumentException("The JPQL string must be set");
		}
		log.trace(methodName, () -> traceSql(jpql, params));
		Query query = entityManager.createQuery(jpql.toString());
		return replaceParams(query, params);
	}

	/**
	 * Composes a native query
	 * @param sql the JPQL to compose the query for
	 * @param params the parameters for the query
	 * @return the typed query
	 */
	protected Query composeNativeQuery(CharSequence sql, Map<String, Object> params) {
		final String methodName = "composeNativeQuery";
		if(sql == null) {
			throw new IllegalArgumentException("The SQL string must be set");
		}
		log.trace(methodName, () -> traceSql(sql, params));
		Query query = entityManager.createNativeQuery(sql.toString());
		return replaceParams(query, params);
	}

	/**
	 * Composes a "typed" native query
	 * @param <E> the entity type
	 * @param sql the JPQL to compose the query for
	 * @param params the parameters for the query
	 * @param entityClass the entity class
	 * @return the query
	 */
	protected <E> Query composeTypedNativeQuery(CharSequence sql, Map<String, Object> params, Class<E> entityClass) {
		final String methodName = "composeTypedNativeQuery";
		if(sql == null) {
			throw new IllegalArgumentException("The SQL string must be set");
		}
		log.trace(methodName, () -> traceSql(sql, params));
		Query query = entityManager.createNativeQuery(sql.toString(), entityClass);
		return replaceParams(query, params);
	}
	
	/**
	 * Composes a "typed" native query
	 * @param sql the JPQL to compose the query for
	 * @param params the parameters for the query
	 * @return the query
	 */
	protected Query composeTypedNativeQuery(CharSequence sql, Map<String, Object> params) {
		return composeTypedNativeQuery(sql, params, clazz);
	}

	/**
	 * Replaces the parameters in a query
	 * @param query the query whose parameters are to be replaced
	 * @param params the parameters
	 * @return the query with the parameters replaced
	 */
	protected Query replaceParams(Query query, Map<String, Object> params) {
		if(params == null) {
			return query;
		}
		return params.entrySet()
			.stream()
			.reduce(
				query,
				(acc, entry) -> acc.setParameter(entry.getKey(), entry.getValue()),
				// Useless parameter, leaked from ParallelStream
				(acc1, acc2) -> acc1);
	}
	/**
	 * Replaces the parameters in a typed query
	 * @param query the query whose parameters are to be replaced
	 * @param params the parameters
	 * @return the query with the parameters replaced
	 */
	private <E> TypedQuery<E> replaceParams(TypedQuery<E> query, Map<String, Object> params) {
		if(params == null) {
			return query;
		}
		return params.entrySet()
			.stream()
			.reduce(
				query,
				(acc, entry) -> acc.setParameter(entry.getKey(), entry.getValue()),
				// Useless parameter, leaked from ParallelStream
				(acc1, acc2) -> acc1);
	}

	/**
	 * Retrieves a paged result
	 * @param <E> the entity type
	 * @param jpql the jpql to execute
	 * @param params the parameters
	 * @param page the page number
	 * @param size the page size
	 * @param entityClass the entity class
	 * @return the page
	 */
	protected <E> Page<E> getPagedResult(CharSequence jpql, Map<String, Object> params, int page, int size, Class<E> entityClass) {
		Query qn = composeQuery(getCountQuery(jpql), params);
		long count = ((Number) qn.getSingleResult()).longValue();
		if(count == 0) {
			return new PageImpl<>(0);
		}

		TypedQuery<E> query = composeTypedQuery(jpql, params, entityClass);
		if(size > 0) {
			query.setFirstResult(page * size);
			query.setMaxResults(size);
		}
		return new PageImpl<>(count, query.getResultList());
	}
	
	
	/**
	 * Retrieves a paged result
	 * @param jpql the jpql to execute
	 * @param params the parameters
	 * @param page the page number
	 * @param size the page size
	 * @return the page
	 */
	protected Page<T> getPagedResult(CharSequence jpql, Map<String, Object> params, int page, int size) {
		return getPagedResult(jpql, params, page, size, clazz);
	}

	/**
	 * Composes the COUNT query
	 * @param jpql the JPQL
	 * @return the query
	 */
	public String getCountQuery(final CharSequence jpql) {
		final String jpqlString = jpql.toString();
		String upperJpqlString = jpqlString.toUpperCase();
		final int fromIndex = upperJpqlString.indexOf("FROM");
		final int toIndex = upperJpqlString.lastIndexOf("ORDER BY");

		String innerQuery;
		if (toIndex != -1) {
			innerQuery = jpqlString.substring(fromIndex, toIndex);
		} else {
			innerQuery = jpqlString.substring(fromIndex);
		}
		return String.format("SELECT COUNT(*) %s", innerQuery);
	}


	/**
	 * Retrieves a native paged result
	 * @param <E> the entity type
	 * @param sql the sql to execute
	 * @param params the parameters
	 * @param page the page number
	 * @param size the page size
	 * @param entityClass the entity class
	 * @return the page
	 */
	protected <E> Page<E> getNativePagedResult(CharSequence sql, Map<String, Object> params, int page, int size, Class<E> entityClass) {
		Query qn = composeQuery(getCountQuery(sql), params);
		long count = ((Number) qn.getSingleResult()).longValue();
		if(count == 0) {
			return new PageImpl<>(0);
		}

		Query query = composeTypedNativeQuery(sql, params, entityClass);
		query.setFirstResult(page * size);
		query.setMaxResults(size);

		@SuppressWarnings("unchecked")
		List<E> resultList = query.getResultList();
		return new PageImpl<>(count, resultList);
	}
	
	/**
	 * Retrieves a native paged result
	 * @param sql the sql to execute
	 * @param params the parameters
	 * @param page the page number
	 * @param size the page size
	 * @return the page
	 */
	protected Page<T> getNativePagedResult(CharSequence sql, Map<String, Object> params, int page, int size) {
		return getNativePagedResult(sql, params, page, size, clazz);
	}

	/**
	 * Applies the sort value
	 * @param jpql the jpql
	 * @param sorts the sorts
	 * @param defaultSort the default sort
	 */
	protected void applySort(StringBuilder jpql, List<Sort> sorts, String defaultSort) {
		jpql.append(" ORDER BY ");
		if(sorts == null || sorts.isEmpty()) {
			jpql.append(defaultSort);
			return;
		}
		jpql.append(sorts
			.stream()
			.map(s -> s.getField() + " " + s.getOrder().name())
			.collect(Collectors.joining(", ")));
	}

	/**
	 * Traces the SQL/JPQL to a log
	 * @param sql the SQL/JPQL to execute
	 * @param params the parameters
	 * @return the trace
	 */
	private Object traceSql(CharSequence sql, Map<String, Object> params) {
		return sql.toString() + (params == null ? "" : " - PARAMS: " + params.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining(", ")));
	}

}
