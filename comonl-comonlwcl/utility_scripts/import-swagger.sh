#!/bin/sh
clear

echo "--- import swagger"

if test -f "import-swagger-properties.sh"; then
  source ./import-swagger-properties.sh
else
  echo "--- file import-swagger-properties.sh not exists. See import-swagger-properties-template-to-rename.sh END"
  exit
fi

echo "--- DEST"
echo $DEST
echo "--- SOURCE"
echo $SOURCE


echo "--- Generate source"
read -p "Press [Enter] key to continue..."

source $SOURCE/gen_swagger.sh --angular

#IF %ERRORLEVEL% NEQ 0 (
#  exit /b
#)

echo "--- Clean project"
rm $DEST/api/*
rm $DEST/model/*
rm $DEST/api.module.ts
rm $DEST/configuration.ts
rm $DEST/form-params.ts
rm $DEST/encoder.ts
rm $DEST/index.ts
rm $DEST/variables.ts

echo "--- Copy resources"

echo "- copy api"
cp $SOURCE/angular/api/* $DEST/api
echo "- copy model"
cp $SOURCE/angular/model/* $DEST/model
echo "- copy files"
cp $SOURCE/angular/api.module.ts $DEST
cp $SOURCE/angular/configuration.ts $DEST
cp $SOURCE/angular/form-params.ts $DEST
cp $SOURCE/angular/encoder.ts $DEST
cp $SOURCE/angular/index.ts $DEST
cp $SOURCE/angular/variables.ts $DEST
