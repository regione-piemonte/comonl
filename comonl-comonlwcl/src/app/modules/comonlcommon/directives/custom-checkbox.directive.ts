/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Directive, ElementRef, Renderer2, OnInit, OnDestroy, Input } from '@angular/core';
import { UserService } from 'src/app/services';
import { Subscription } from 'rxjs';

@Directive({
  selector: '[comonlCustomCheckbox]'
})
export class CustomCheckboxDirective implements OnInit, OnDestroy {

  private span: Element;
  private checkbox: HTMLInputElement;
  // vengono salvate le funzioni di destroy del listener, e invocate nel metodo ngOnDestroy()
  private unlisten: (() => void)[] = [];
  private isControlDisabled: boolean;
  private readonly subscriptions: Subscription[] = [];

  // Servizio Angular con il quale viene astratta l'interazione con il DOM
  constructor(
    private elementRef: ElementRef,
    private renderer: Renderer2,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    const forValue = (this.elementRef.nativeElement as Element).getAttribute('for');
    if (forValue) {
      return this.bindOnInit();
    }
    // Workaround when the label is created, but the for attribute is not yet written
    setTimeout(() => this.bindOnInit(), 50);
  }

  bindOnInit() {
    this.span = this.renderer.createElement('span');
    this.renderer.addClass(this.span, 'chk-span');
    this.checkbox = document.getElementById((this.elementRef.nativeElement as Element).getAttribute('for')) as HTMLInputElement;
    if (!this.checkbox) {

    }
    if (this.checkbox.checked) {
      this.renderer.addClass(this.span, 'checked');
    }

    this.renderer.insertBefore(this.elementRef.nativeElement, this.span, this.elementRef.nativeElement.firstChild);

    this.unlisten.push(this.renderer.listen(this.span, 'click', event => this.clickHandler(true, event)));
    // this.renderer.listen(this.span, 'keyup', event => this.keyHandler(event));
    // this.renderer.listen(this.checkbox, 'keyup', event => this.keyHandler(event));
    this.unlisten.push(this.renderer.listen(this.elementRef.nativeElement, 'click', (event) => this.clickHandlerLabel(event)));

    this.subscriptions.push(
      this.userService.uiUpdate$.subscribe(() => setTimeout(() => this.clickHandler(false), 20))
      // this.clickHandler(false))
    );
  }

  ngOnDestroy(): void {
    // Rimuovere listen evento
    this.unlisten.forEach(fnc => fnc());
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  private clickHandler(invertSelection: boolean, e?: Event) {
    if (e) {
      e.stopPropagation();
    }
    if (!this.isControlDisabled) {
      const checkedValue = invertSelection ? !this.checkbox.checked : this.checkbox.checked;
      // console.log(this.checkbox);
      if (checkedValue) {
        this.renderer.addClass(this.span, 'checked');
      } else {
        this.renderer.removeClass(this.span, 'checked');
      }
    }
  }

  // private keyHandler(e: KeyboardEvent) {
  //   e.stopPropagation();
  //   if (e.key === ' ') {
  //     this.clickHandler(e);
  //     // Also update the checkbox state.
  //     this.renderer.setProperty(this.checkbox, 'checked', !this.checkbox.checked);
  //   }
  // }
  private clickHandlerLabel(e?: Event) {
    if (e) {
      e.stopPropagation();
    }
    if (!this.isControlDisabled) {
      if (this.span.className.indexOf('checked') < 0) {
        this.renderer.addClass(this.span, 'checked');
      } else {
        this.renderer.removeClass(this.span, 'checked');
      }
    }
  }
  @Input() set controlDisabled(val: boolean) {
    this.isControlDisabled = val;
  }
  // @Input() set ripristina(val: boolean) {
  //   this.checkUncheck();
  //   this._ripristina = !val;
  // }
}
