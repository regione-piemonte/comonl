/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
import { Directive, HostListener } from '@angular/core';
import { NgControl } from '@angular/forms';
import { formatNumber } from '@angular/common';

@Directive({
  selector: '[comonlFormatNumberAmount]'
})
export class FormatNumAmountDirective {

    constructor(public ngControl: NgControl) { }

    @HostListener('change', ['$event'])
    onChange(event) {
      this.localeString(event.target.value);
    }
  
    //@HostListener('keydown.backspace', ['$event'])
    //keydownBackspace(event) {
    //}

    localeString(nStr) {
        if (nStr === '') return ''; 
        console.log(nStr);
        const asNumber = Number.parseFloat(nStr);

        if(asNumber) {
            nStr = formatNumber(asNumber, 'en-US', '1.2-5').toString();
            
            let strArr = nStr.split('.');
            console.log(strArr);
            let left = strArr[0].indexOf(',') !== -1 ? strArr[0].replace(',', '.') : strArr[0];
            console.log(left);
            nStr = left + ',' + strArr[1];
            console.log(nStr);
            this.ngControl.valueAccessor.writeValue(nStr);
        } else {
            this.ngControl.valueAccessor.writeValue('error');
        }
    }

    /*localeString(nStr) {
        if (nStr === '') return ''; 
        let x, x1, x2, rgx, y1, y2;
        nStr += '';
        x = nStr.split('.');
        x1 = x[0];
        x2 = x.length > 1 ? ',' + x[1] : '';
        rgx = /(\d+)(\d{3})/;
        while (rgx.test(x1)) {
            x1 = x1.replace(rgx, '$1' + '.' + '$2');
        }

        if (x1.indexOf(',') !== -1) {
            // y1 = x1.slice(x1.lastIndexOf(',')).replace(/\./g, '');

            y2 = x1.split(',');
            x = y2[0] +  y1;
        } else {
            x = x1 + x2;
            if (this.missingOneDecimalCheck(x)) return x += '0';
            if (this.missingAllDecimalsCheck(x)) return x += ',00';
        }
        console.log(x);
        this.ngControl.valueAccessor.writeValue(x);
        return x;
    }*/
  
}