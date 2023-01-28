/*
* SPDX-FileCopyrightText: Copyright 2019 - 2022 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
export function Debounce(delay: number = 300): MethodDecorator {
  return (target: any, propertyKey: string, descriptor: PropertyDescriptor) => {
    let timeoutIndicator: ReturnType<typeof setTimeout>;

    const original = descriptor.value;

    descriptor.value = function(...args: any[]) {
      clearTimeout(timeoutIndicator);
      timeoutIndicator = setTimeout(() => original.apply(this, args), delay);
    };

    return descriptor;
  };
}
