/*
* SPDX-FileCopyrightText: Copyright 2019 - 2020 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
// @ts-check
const fs = require('fs');

module.exports = {
  json: () => null,
  ts: (file, banner) => {
    handleFile(file, banner, 'TypeScript', (content, eol) => `/*${eol}${banner.map(chunk => `* ${chunk}`).join(eol)}${eol}*/${eol}${content}`)
  },
  js: (file, banner) => {
    handleFile(file, banner, 'Javascript', (content, eol) => `/*${eol}${banner.map(chunk => `* ${chunk}`).join(eol)}${eol}*/${eol}${content}`)
  },
  css: (file, banner) => {
    handleFile(file, banner, 'CSS', (content, eol) => `/*${eol}${banner.map(chunk => `* ${chunk}`).join(eol)}${eol}*/${eol}${content}`)
  },
  scss: (file, banner) => {
    handleFile(file, banner, 'SCSS', (content, eol) => `/*${eol}${banner.map(chunk => `* ${chunk}`).join(eol)}${eol}*/${eol}${content}`)
  },
  html: (file, banner) => {
    handleFile(file, banner, 'HTML', (content, eol) => {
      if(content.toUpperCase().indexOf('<!DOCTYPE HTML') === -1) {
        return `<!--${eol}${banner.join(eol)}${eol}-->${eol}${content}`;
      }
      const lines = content.split(eol);
      return [
        lines[0],
        '<!--',
        ...banner,
        '-->',
        ...lines.slice(1)
      ].join(eol);
    })
  },
};

function readFile(file) {
  return fs.readFileSync(file, {encoding: 'utf-8'});
}

function checkBanner(content, banner) {
  return content.indexOf(banner[1]) !== -1;
}
function writeFile(file, content) {
  return fs.writeFileSync(file, content, {encoding: 'utf-8'});
}
function getLineBreakChar(string) {
  const indexOfLF = string.indexOf('\n', 1);
  if (indexOfLF === -1) {
    if (string.indexOf('\r') !== -1) {
      return '\r';
    }
    return '\n';
  }
  if (string[indexOfLF - 1] === '\r') {
    return '\r\n';
  }
  return '\n';
}
/**
 *
 * @param {string} file
 * @param {string[]} banner
 * @param {string} type
 * @param {(content: string, eol: string) => string} transformer
 */
function handleFile(file, banner, type, transformer) {
  const content = readFile(file);
  if(checkBanner(content, banner)) {
    return;
  }
  const eol = getLineBreakChar(content) || '\n';
  writeFile(file, transformer(content, eol));
  console.log(`HANDLED file ${file} as ${type} file`);
}
