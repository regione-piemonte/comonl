/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2-or-later
*/
export function Throttle(delay = 300): MethodDecorator {
  return (target: object, propertyKey: string, descriptor: PropertyDescriptor) => {
    const origFunc = descriptor.value;
    let timerID: ReturnType<typeof setTimeout> = null;
    let startTime: number = null;

    descriptor.value = function(...args: any[]) {
      // discard old event timer
      clearTimeout(timerID);
      const currentTime: number = new Date().getTime();

      // set up throttle (the first one or the new one after waiting time)
      if (timerID === null) {
        startTime = currentTime;
        origFunc.apply(this, args);
      }

      // check if $limit exceeded (discard the old waiting timer)
      if (currentTime - startTime >= delay) {
        startTime = currentTime;
        origFunc.apply(this, args);
      } else {
        timerID = setTimeout(() => timerID = null, 1000);
      }
    };
    return descriptor;
  };
}
