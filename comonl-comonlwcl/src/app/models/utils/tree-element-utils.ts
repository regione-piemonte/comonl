/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { TreeElement } from '../tree-element';

export class TreeElementUtils {

  // public static cpvToTreeElement(cpvs: Cpv[]): TreeElement<Cpv>[] {
  //   return TreeElementUtils.translateList(cpvs, ['codice', 'descrizione'], 'listCpv',
  //     cpv => (cpv.codice + ' ' + cpv.descrizione).toLowerCase(),
  //     cpv => cpv.codice + ' ' + cpv.descrizione,
  //     arr => TreeElementUtils.cpvToTreeElement(arr));
  // }

  private static translateList<T extends {id?: any}>(
      sourceList: T[],
      fieldsToCopy: string[] = [],
      childrenProperty: string = 'children',
      filterGenerator: (item: T, idx: number) => string = () => '',
      shownTextGenerator: (item: T, idx: number) => string = () => '',
      childGenerator: (arr: T[]) => TreeElement<T>[],
      resultList: TreeElement<T>[] = [],
      ): TreeElement<T>[] {
    let idx = 0;
    for (const sourceElement of sourceList) {
      const treeElement: TreeElement<T> = {
        id: sourceElement.id,
        isTerminal: true,
        expanded: false,
        $$expanded: false,
        highlighted: false,
        shownText: shownTextGenerator(sourceElement, idx),
        filterText: filterGenerator(sourceElement, idx),
        wrappedElement: sourceElement,
        children: []
      };
      fieldsToCopy.forEach(field => treeElement[field] = sourceElement[field]);
      resultList.push(treeElement);
      if (sourceElement[childrenProperty] && sourceElement[childrenProperty].length) {
        treeElement.children = childGenerator(sourceElement[childrenProperty]);
        // TreeElementUtils.translateList(sourceElement[childrenProperty], fieldsToCopy, childrenProperty, filterGenerator, treeElement.children);
        treeElement.isTerminal = false;
      }
      idx++;
    }
    return resultList;
  }

  public static getElementById<T>(id: number, list: TreeElement<T>[]): T {
    for (const el of list) {
      if (+el.id === +id) {
        return el.wrappedElement;
      }
      if (el.children && el.children.length) {
        const child = this.getElementById(id, el.children);
        if (child) {
          return child;
        }
      }
    }
    return null;
  }

  public static getElementByFilterText<T>(text: string, list: TreeElement<T>[]): TreeElement<T>[] {
    const resultList: TreeElement<T>[] = [];
    for (const el of list) {
      if (el.children && el.children.length) {
        const child: TreeElement<T>[]  = this.getElementByFilterText(text, el.children);
        if (child) {
          resultList.push(...child);
        }
      }
      const inElement = el.filterText.indexOf(text) !== -1;
      if (inElement) {
        // non è necessario appiattire l'albero
        // resultList.push(TreeElementUtils.linearize(el));
        resultList.push(el);
      }
    }
    return resultList;
  }
  static linearize(el) {
    const res = {...el};
    delete res.children;
    return res;
  }

}
