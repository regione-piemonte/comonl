/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Component, OnInit, Input, OnDestroy, ViewChild, AfterViewInit, ElementRef } from '@angular/core';
import { NestableSettings } from 'ngx-nestable/lib/nestable.models';
import { TreeElement, TreeElementSelectionType, TreeElementUtils } from '../../../../models';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Subscription, fromEvent } from 'rxjs';
import { filter, debounceTime, distinctUntilChanged, map, tap } from 'rxjs/operators';
import { Utils } from '../../../../utils';
import { UserService } from '../../../../services';
import { ConsoleService } from '@ng-select/ng-select/lib/console.service';

@Component({
  selector: 'comonl-tree-modal',
  templateUrl: './tree-modal.component.html',
  styleUrls: ['./tree-modal.component.scss']
})
export class TreeModalComponent<T> implements OnInit, OnDestroy, AfterViewInit {

  private static counter = 0;

  @Input() options: NestableSettings = {
    fixedDepth: true,
    disableDrag: true
  };
  @Input() titolo: string;
  @Input() selectionType: TreeElementSelectionType;
  @Input() textFilters: string[];
  @Input() searchFieldValue = ''; // valorizzato inizialmente se si ricerca da parent

  @Input() set list(elts: TreeElement<T>[]) {
    this.originalList = elts;
    // this.viewList = Utils.clone(elts);
  }

  @ViewChild('searchField', {static: false}) searchField: ElementRef;

  viewList: TreeElement<T>[] = [];

  checkedElement: TreeElement<T>;
  instanceCounter: number;
  private originalList: TreeElement<T>[] = [];
  private subscriptions: Subscription[] = [];

  constructor(
    public activeModal: NgbActiveModal,
    private userService: UserService,
  ) {}

  async ngOnInit() {
    this.instanceCounter = ++TreeModalComponent.counter;
    await this.initList(this.searchFieldValue);
  }

  private async initList(text: string) {
    if (text) {
      this.onChangeText(text.toLowerCase());
    }
  }

  ngAfterViewInit() {
    this.searchField.nativeElement.value = this.searchFieldValue;
    this.userService.triggerUiUpdate();
    const keyup$ = fromEvent(this.searchField.nativeElement, 'keyup')
    .pipe(
      filter(Boolean),
      map(() => this.searchField.nativeElement.value.toLowerCase()),
      distinctUntilChanged(),
      debounceTime(150)
    );

    this.subscriptions.push(
      keyup$
        .pipe(
          filter(el => el.length > 3),
        )
        .subscribe(text => this.onChangeText(text))
    );

    this.subscriptions.push(
      keyup$
        .pipe(
          filter(el => el.length === 0)
        )
        .subscribe(text => {
          const list = Utils.clone(this.originalList);
          this.collapseList(list);
          this.viewList = list;
        })
    );


    if(this.textFilters && this.textFilters.length > 0) {

      let list = Utils.clone(this.originalList);
      for(let x = 0; x < this.textFilters.length; x++) {
        this.highlightList(this.textFilters[x], list);
        list = this.removeNotHightlighted(list);
      }

      this.expandList(list);
      this.viewList = list;
    } else if (!this.searchFieldValue){
      this.viewList = Utils.clone(this.originalList);
    }
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  generateId(item: TreeElement<T>) {
    return `comonl_tree_${this.instanceCounter}_${item.id}`;
  }
  changeChecked(item: TreeElement<T>) {
    if (item.children && item.children.length) {
      this.recursiveChangeChecked(item.children, !item.checked);
    }
    item.$$expanded = true;
    item.checked = !item.checked;
    this.recursiveChangeCheckedParent(this.viewList);
    this.userService.triggerUiUpdate();

  }
  recursiveChangeChecked(treeList: TreeElement<T>[], checked: boolean){
    for (const el of treeList) {
      el.checked = checked;
      el.$$expanded = true;
      if (el.children && el.children.length) {
        this.recursiveChangeChecked(el.children, checked);
      }
    }
  }
  recursiveChangeCheckedParent(treeList: TreeElement<T>[]) {
    for (const parent of treeList) {
      if (parent.children && parent.children.length) {
        this.recursiveChangeCheckedParent(parent.children);
        const el  = parent.children.filter( child => child.checked === parent.checked);
        if (!el || !el.length) {
          parent.checked = !parent.checked;
        }
      }
    }
  }

  confirm() {
    switch (this.selectionType) {
      // case 'multi': return this.activeModal.close(this.recursiveGetChecked(this.list));
      // case 'multi': return this.activeModal.close(this.recursiveGetChecked(this.viewList.filter(el => el.checked === true), []));
      case 'multi': return this.activeModal.close(this.recursiveGetChecked(this.viewList, []));
      case 'single': return this.activeModal.close([this.checkedElement]);
      default: return this.activeModal.close([]);
    }
  }

  private onChangeText(text: any) {
    const list = Utils.clone(this.originalList);
    this.highlightList(text, list);
    this.viewList = this.removeNotHightlighted(list);
  }

  private collapseList(treeList: TreeElement<T>[]) {
    for (const el of treeList) {
      if (el.children && el.children.length) {
        this.collapseList(el.children);
      }
      el.highlighted = false;
      // Per l'apertura dei nodi figli
      el.$$expanded = false;
    }
  }

  private expandList(treeList: TreeElement<T>[]) {
    for (const el of treeList) {
      if (el.children && el.children.length) {
        this.expandList(el.children);
      }
      el.highlighted = true;
      // Per l'apertura dei nodi figli
      el.expanded = true;
    }
  }

  private highlightList(text: string, treeList: TreeElement<T>[]): boolean {
    let result = false;
    for (const el of treeList) {
      let inChild = false;
      if (el.children && el.children.length) {
        inChild = this.highlightList(text, el.children);
      }
      const inElement = el.filterText.toLowerCase().indexOf(text.toLowerCase()) !== -1;
      el.highlighted = inChild || inElement;
      // Per l'apertura dei nodi figli
      el.expanded = el.$$expanded = !!(el.highlighted && el.children && el.children.length);

      result = result || el.highlighted;
    }
    return result;
  }
  private removeNotHightlighted(list: TreeElement<T>[] = []): TreeElement<T>[] {
    return list.filter(el => {
      if (!el.highlighted) {
        return false;
      }
      if (el.children && el.children.length) {
        el.children = this.removeNotHightlighted(el.children);
      }
      return true;
    });
  }

  private recursiveGetChecked(list: TreeElement<T>[], result: T[] = []): T[] {
    for (const el of list) {
      if (el.checked) {
        result.push(el.wrappedElement);
        // console.log('recursiveGetChecked', el.wrappedElement);
      }
      if (el.children && el.children.length) {
        this.recursiveGetChecked(el.children, result);
      }
    }
    return result;
  }

}
