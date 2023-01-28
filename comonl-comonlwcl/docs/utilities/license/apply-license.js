/*
* SPDX-FileCopyrightText: Copyright 2019 - 2020 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
// @ts-check
const fs = require('fs');
const path = require('path');
const basedir = path.resolve(__dirname, '..', '..', '..');

const inceptionYear = 2019;
const currentYear = new Date().getFullYear();
const params = {
  organizationName: 'CSI Piemonte',
  years: inceptionYear === currentYear ? `${inceptionYear}` : `${inceptionYear} - ${currentYear}`,
};
const bannerTemplate = fs.readFileSync(path.join(basedir, 'buildfiles', 'license.header.txt'), {encoding: 'utf-8'});
const banner = bannerTemplate.replace(/\${(.*?)}/g, (ignored, group) => params[group] || '').split('\n');
const handler = require('./handler');

recursiveFolderExec(path.join(basedir, 'src'));
recursiveFolderExec(path.join(basedir, 'e2e'));
recursiveFolderExec(path.join(basedir, 'buildfiles'));
recursiveFolderExec(path.join(basedir, 'docs'));

function recursiveFolderExec(baseFolder) {
  const contents = fs.readdirSync(baseFolder);
  for(let content of contents) {
    if(content === '.' || content === '..') {
      continue;
    }
    const newPath = path.join(baseFolder, content);
    const stat = fs.statSync(newPath);
    if(stat.isDirectory()) {
      recursiveFolderExec(newPath);
      continue;
    }
    if(stat.isFile()) {
      applyLicense(newPath);
      continue;
    }
    // Non directory e non file
    console.error(`PATH DOES NOT CORRESPOND TO A FILE OR FOLDER: ${newPath}`);
  }
}
function applyLicense(file) {
  const ext = path.extname(file).substring(1);
  if(handler[ext]) {
    handler[ext](file, banner);
  } else {
    console.log(`IGNORE FILE ${file}`);
  }
}
