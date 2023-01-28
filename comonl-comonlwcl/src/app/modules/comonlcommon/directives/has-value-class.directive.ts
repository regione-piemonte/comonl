/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Directive, ElementRef, Renderer2, HostListener, OnInit, OnDestroy } from '@angular/core';
import { UserService } from 'src/app/services';
import { Subscription } from 'rxjs';

@Directive ({
  selector: '[comonlHasValueClass]'
})
export class HasValueClassDirective implements OnInit, OnDestroy {

  private readonly subscriptions: Subscription[] = [];

  constructor(
    private elementRef: ElementRef,
    private renderer: Renderer2,
    private userService: UserService
  ) { }

  ngOnInit() {
    this.subscriptions.push(
      // Observable in ascolto, se scatetano l'evento è invocato il metoto
      this.userService.uiUpdate$.subscribe(() => this.handleChange())
    );
    this.handleChange();
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  @HostListener('change') handleChange(): void {
    if (this.elementRef.nativeElement.value) {
      this.renderer.addClass(this.elementRef.nativeElement, 'has-value');
    } else {
      this.renderer.removeClass(this.elementRef.nativeElement, 'has-value');
    }
  }
}
