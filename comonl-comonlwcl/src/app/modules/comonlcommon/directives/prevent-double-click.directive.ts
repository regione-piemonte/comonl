/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import {
    Directive,
    EventEmitter,
    HostListener,
    Input,
    OnDestroy,
    OnInit,
    Output
  } from '@angular/core';
import { Subject, Subscription } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

@Directive({
selector: '[comonlPreventDoubleClick]'
})
export class PreventDoubleClickDirective implements OnInit, OnDestroy {

    @Input() debounceTime = 1000;

    @Output() readonly debounceClick = new EventEmitter();

    private clicks = new Subject();
    private subscription: Subscription;

    constructor() { }

    ngOnInit() {
        this.subscription = this.clicks.pipe(
        debounceTime(this.debounceTime)
        ).subscribe(e => this.debounceClick.emit(e));
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    @HostListener('click', ['$event']) clickEvent(event) {
        event.preventDefault();
        event.stopPropagation();
        this.clicks.next(event);
    }
}
