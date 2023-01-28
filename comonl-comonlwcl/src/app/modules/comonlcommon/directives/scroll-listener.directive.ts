/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Directive, OnInit, OnDestroy, Inject } from '@angular/core';
import { fromEvent, Subscription, asyncScheduler } from 'rxjs';
import { throttleTime, tap } from 'rxjs/operators';
import { UserService } from '../../../services';
import { DOCUMENT } from '@angular/common';

@Directive({
  selector: '[comonlScrollListener]'
})
export class ScrollListenerDirective implements OnInit, OnDestroy {

  private subscription: Subscription;

  constructor(
    private userService: UserService,
    @Inject(DOCUMENT) private document: Document
  ) { }

  ngOnInit() {
    this.subscription = fromEvent(this.document, 'scroll')
      .pipe(
        throttleTime(50, asyncScheduler, {trailing: true}),
      )
      .subscribe(() => this.userService.triggerScroll({
        scrollLeft: this.document.scrollingElement.scrollLeft,
        scrollTop: this.document.scrollingElement.scrollTop
      }));
  }

  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
