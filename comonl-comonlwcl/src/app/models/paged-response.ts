/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
export interface PagedResponse<T> {
  list?: Array<T>;
  currentPage?: number;
  totalPages?: number;
  totalElements?: number;
}
