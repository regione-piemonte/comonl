/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

export type TreeElementSelectionType = 'single' | 'multi' | 'none';

export interface TreeElement<T> {
  id: string;
  isTerminal: boolean;
  children: TreeElement<T>[];
  expanded: boolean;
  $$expanded: boolean;
  shownText: string;
  filterText: string;
  highlighted?: boolean;
  checked?: boolean;
  wrappedElement: T;
  // Force only id and children
  [key: string]: any;
}

