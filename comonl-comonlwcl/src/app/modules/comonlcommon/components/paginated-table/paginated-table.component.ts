/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import {
  Component, OnInit, Directive, TemplateRef, Input, Output, ViewChildren, QueryList,
  EventEmitter, ContentChild
} from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { PagedResponse } from 'src/app/models/paged-response';
import { PaginationDataChange, SortEvent } from 'src/app/models';
import { PaginationBodyDirective, PaginationHeadDirective, SortableDirective } from '../../directives';

@Component({
  selector: 'comonl-paginated-table',
  templateUrl: './paginated-table.component.html',
  styleUrls: ['./paginated-table.component.scss']
})
export class PaginatedTableComponent<T> implements OnInit {

  @Input() pagedResponse: PagedResponse<T>;
  @Input() columnNumber = 0;
  @Input() limit = 10;
  @Output() readonly changePaginationData = new EventEmitter<PaginationDataChange>();

  @ViewChildren(SortableDirective) headers: QueryList<SortableDirective>;
  @ContentChild(PaginationHeadDirective, { static: false }) tplHead: PaginationHeadDirective;
  @ContentChild(PaginationBodyDirective, { static: false }) tplBody: PaginationBodyDirective<T>;

  pageSizes: number[] = [1, 5, 10, 20, 50];
  page: number;
  sort: SortEvent;

  constructor(
    private translateService: TranslateService
  ) { }

  ngOnInit() {
      this.page = this.pagedResponse.currentPage + 1;
      if (!this.columnNumber) {
        this.columnNumber = 0;
      }
  }

  changePageSize(pageSize: number) {
    console.log('paginated', pageSize);

    const currentFirstElement = (this.page - 1) * this.limit;
    this.limit = pageSize;
    const page = Math.floor(currentFirstElement / this.limit);
    const offset = page * this.limit;
    this.page = page + 1;

    this.changePaginationData.emit({
      limit: this.limit,
      sort: this.sort,
      page,
      offset
    });
  }

  onSort(sortEvent: SortEvent) {
    this.headers
      .filter(header => header.sortable !== sortEvent.column)
      .forEach(header => header.direction = '');
    this.sort = sortEvent;
    this.changePaginationData.emit({
      limit: this.limit,
      sort: this.sort,
      page: this.page - 1,
      offset: (this.page - 1) * this.limit
    });
  }

  goToPage(pageNumber: number): void {
    this.page = Math.max(1, Math.min(this.pagedResponse.totalPages, pageNumber));

    this.changePaginationData.emit({
      limit: this.limit,
      sort: this.sort,
      page: this.page - 1,
      offset: (this.page - 1) * this.limit
    });
  }

  get paginationHeader(): string {
    if (this.pagedResponse.totalElements === 0) {
      return '';
    }
    const first = Math.min((this.page - 1) * this.limit + 1, this.pagedResponse.totalElements);
    const last = Math.min(this.page * this.limit, this.pagedResponse.totalElements);
    return this.translateService.instant('PAGINATION.INFO_HEADER', { first, last, total: this.pagedResponse.totalElements });
  }

  get paginationFooter(): string {
    if (this.pagedResponse.totalElements === 0) {
      return '';
    }
    const first = Math.min((this.page - 1) * this.limit + 1, this.pagedResponse.totalElements);
    const last = Math.min(this.page * this.limit, this.pagedResponse.totalElements);
    return this.translateService.instant('PAGINATION.INFO_FOOTER', { first, last, total: this.pagedResponse.totalElements });
  }

}
