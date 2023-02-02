/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { SortEvent } from 'src/app/models/sort-event';

export interface PaginationDataChange {
  limit: number;
  offset: number;
  page: number;
  sort: SortEvent;
}
