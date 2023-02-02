/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { SortDirection } from 'src/app/models/sort-direction';

export interface SortEvent {
  column: string;
  direction: SortDirection;
}
