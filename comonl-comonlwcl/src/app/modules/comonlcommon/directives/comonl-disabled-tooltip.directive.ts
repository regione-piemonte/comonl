/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Directive, ElementRef, Renderer2, HostListener, Injector, ComponentFactoryResolver, ViewContainerRef, NgZone, Inject, ChangeDetectorRef, ApplicationRef, Input, OnInit, Output } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { NgbTooltip, NgbTooltipConfig } from '@ng-bootstrap/ng-bootstrap';

@Directive({
  selector: '[comonlDisabledTt]'
})
export class ComonlDisabledTooltipDirective extends NgbTooltip implements OnInit {

  @Input() comonlDisabledTt: string;

  constructor(
    private elementRef: ElementRef,
    private renderer: Renderer2,
    private injector: Injector,
    private componentFactoryResolver: ComponentFactoryResolver,
    private viewContainerRef: ViewContainerRef,
    private tooltipConfig: NgbTooltipConfig,
    private ngZone: NgZone,
    @Inject(DOCUMENT) private document: any,
    private changeDetectorRef: ChangeDetectorRef,
    private applicationRef: ApplicationRef
  ) {
      super(elementRef, renderer, injector, componentFactoryResolver, viewContainerRef, tooltipConfig, ngZone, document, changeDetectorRef, applicationRef);
  }

  ngOnInit() {
    this.elementRef.nativeElement.setAttribute('style', 'display: inline');
  }

  @HostListener('mouseenter') onMouseEnter() {
    const text = this.comonlDisabledTt;
    if (text && this.isElementDisabled()) {
        this.ngbTooltip = text;
        this.open();
    }
  }

  @HostListener('mouseleave') onMouseLeave() {
    this.close();
  }

  private isElementDisabled(): boolean {
    const native = this.elementRef.nativeElement;
    const firstChild = native.children ? native.children[0] : undefined;
    if (!firstChild) {
      return false;
    }
    return firstChild.disabled;
  }

}

