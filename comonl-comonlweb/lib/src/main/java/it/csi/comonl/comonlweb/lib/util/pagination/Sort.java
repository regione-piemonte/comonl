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
package it.csi.comonl.comonlweb.lib.util.pagination;

/**
 * Sort informations
 */
public class Sort {

	private String field;
	private Direction order = Direction.ASC;
	
	/** Default constructor */
	public Sort() {}
	
	/**
	 * Constructor
	 * @param field the field
	 * @param order the order
	 */
	public Sort(String field, String order) {
		this(field, Direction.byString(order));
	}
	
	/**
	 * Constructor
	 * @param field the field
	 * @param order the order
	 */
	public Sort(String field, Direction order) {
		this.field = field;
		this.order = order;
	}
	
	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}
	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the order
	 */
	public Direction getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(Direction order) {
		this.order = order;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Sort [field=").append(field).append(", order=").append(order).append("]");
		return builder.toString();
	}

	/**
	 * The Enum Direction.
	 */
	public enum Direction {
		/** Ascending direction */
		ASC ("ASC"),
		/** Descending direction */
		DESC("DESC"),
		;
		private final String sortDirection;
		private Direction(String sortDirection) {
			this.sortDirection = sortDirection;
		}
		/**
		 * @return the sortDirection
		 */
		public String getSortDirection() {
			return sortDirection;
		}
		/**
		 * Retrieves the direction by text
		 * @param text the text of the direction
		 * @return the direction, if present; Direction.ASC otherwise
		 */
		public static Direction byString(String text) {
			for(Direction direction : Direction.values()) {
				if(direction.getSortDirection().equalsIgnoreCase(text)) {
					return direction;
				}
			}
			// Default
			return Direction.ASC;
		}
	}
}
