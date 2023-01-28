/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
import { Component, Input, OnChanges, OnDestroy, OnInit, SimpleChange, SimpleChanges } from '@angular/core';

@Component({
  selector: 'comonl-alert-msg',
  templateUrl: './alert-msg.component.html',
  styleUrls: ['./alert-msg.component.scss']
})
export class AlertMsgComponent implements OnInit/*, OnDestroy, OnChanges*/ {

  @Input() typeMsg: string;
  @Input() listMsg: string[];
  hide: boolean;
  @Input() set show(hide: boolean){
    this.hide = hide;
  }

  // private timer;
  constructor() { }

  ngOnInit() {
    // this.timer = setInterval(() => this.logData(), 1000);
  }

  // ngOnDestroy() {
  //   clearInterval(this.timer);
  // }

  // ngOnChanges(changes: SimpleChanges) {
  //   console.log('ngOnChanges', changes);
  // }

  // private logData() {
  //   console.log(`typeMsg: ${this.typeMsg} --- listMsg: ${this.listMsg.join('__')} - hide: ${this.hide}`);
  // }

}
