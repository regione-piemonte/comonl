/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Directive, ElementRef, Renderer2, OnInit, OnDestroy, Optional } from '@angular/core';
import { NgSelectComponent } from '@ng-select/ng-select';
import { Subscription } from 'rxjs';
import { UserService } from '../../../services';
import { NgControl } from '@angular/forms';

@Directive({
  // Bind to all ng-select instances
  // tslint:disable-next-line: directive-selector
  selector: 'ng-select'
})
export class NgSelectHasValueDirective implements OnInit, OnDestroy {

  private subscriptions: Subscription[] = [];

  constructor(
    private elementRef: ElementRef,
    private hostElement: NgSelectComponent,
    @Optional() private formControl: NgControl,
    private renderer: Renderer2,
    private userService: UserService
  ) {}

  ngOnInit() {
    if (!this.hostElement || !this.hostElement.changeEvent) {
      // Bound to wrong element?
      return;
    }

    if (this.formControl) {
      // Value changes should be the most precise event
      this.subscriptions.push(
        this.formControl.valueChanges.subscribe(() => this.onChange())
      );
    } else {
      // Since we do not have a formControl, we fall back to listening to events to the host element
      this.subscriptions.push(
        this.hostElement.changeEvent.subscribe(() => this.onChange()),
        this.userService.uiUpdate$.subscribe(() => this.onChange())
      );
    }
    // Always trigger onChange at start to deal with the current value of the element
    this.onChange();
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  private onChange() {
    if (this.hostElement.hasValue) {
      this.renderer.addClass(this.elementRef.nativeElement, 'has-value');
    } else {
      this.renderer.removeClass(this.elementRef.nativeElement, 'has-value');
    }
  }
}
