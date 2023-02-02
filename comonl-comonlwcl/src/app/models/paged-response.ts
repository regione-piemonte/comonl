/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
export interface PagedResponse<T> {
  list?: Array<T>;
  currentPage?: number;
  totalPages?: number;
  totalElements?: number;
}
