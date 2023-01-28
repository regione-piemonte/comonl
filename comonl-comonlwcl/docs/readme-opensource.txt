vedi https://www.npmjs.com/package/license-checker

npm install -g license-checker
cd ......./comonl/comonlwcl
license-checker --production --csv > ./docs/BOM.csv

aggiunto in package.json
	"license": "EUPL-1.2",

	sotto "scripts"
	"apply-license": "node docs/utilities/license/apply-license.js"

aggiunto file "buildfiles/license.header.txt"
	npm run apply-license
