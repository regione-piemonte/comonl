/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Directive, ElementRef, Renderer2, HostListener, OnInit, OnDestroy } from '@angular/core';
import { NgControl } from '@angular/forms';
import { UserService } from 'src/app/services';
import { Subscription } from 'rxjs';

@Directive ({
  selector: '[comonlIsInvalidClass]'
})
export class IsInvalidClassDirective implements OnInit, OnDestroy {

  private readonly subscriptions: Subscription[] = [];

  constructor(
    private elementRef: ElementRef,
    private renderer: Renderer2,
    private control: NgControl,
    private userService: UserService
  ) { }

  ngOnInit() {
    this.subscriptions.push(
      // Observable in ascolto, se scatetano l'evento è invocato il metoto
      this.userService.uiUpdate$.subscribe(() => this.handleChange()),
      this.userService.uiUpdate$.subscribe(() => this.handleKeyUp())
    );
    this.handleChange();
    this.handleKeyUp();
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  @HostListener('change') handleChange(): void {
    if (!this.control.valid && !this.control.disabled) {
      this.renderer.addClass(this.elementRef.nativeElement, 'is-invalid');
    } else {
      this.renderer.removeClass(this.elementRef.nativeElement, 'is-invalid');
    }
  }

  @HostListener('keyup') handleKeyUp(): void {
    if (!this.control.valid && !this.control.disabled) {
      this.renderer.addClass(this.elementRef.nativeElement, 'is-invalid');
    } else {
      this.renderer.removeClass(this.elementRef.nativeElement, 'is-invalid');
    }
  }
}
