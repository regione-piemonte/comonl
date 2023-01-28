@ECHO OFF
echo "--- import swagger"

if exist "%~dp0import-swagger-properties.bat" (
  CALL %~dp0import-swagger-properties.bat
) else (
  echo "--- file import-swagger-properties.bat not exists. See import-swagger-properties-template-to-rename.bat END"
  exit /b 1
)

echo "--- DEST"
echo %DEST%
echo "--- SOURCE"
echo %SOURCE%


echo "--- Generate source"
rem pause
CALL %SOURCE%\gen_swagger.bat -ANGULAR

IF %ERRORLEVEL% NEQ 0 (
  exit /b
)

echo "--- Clean project"
rem pause
DEL /q /f /s %DEST%\api\* ^
  %DEST%\model\* ^
  %DEST%\api.module.ts ^
  %DEST%\configuration.ts ^
  %DEST%\form-params.ts ^
  %DEST%\encoder.ts ^
  %DEST%\index.ts ^
  %DEST%\variables.ts

echo "--- Copy resources"
rem pause
COPY /y %SOURCE%\angular\api\* %DEST%\api
COPY /y %SOURCE%\angular\model\* %DEST%\model
COPY /y %SOURCE%\angular\api.module.ts %DEST%
COPY /y %SOURCE%\angular\configuration.ts %DEST%
COPY /y %SOURCE%\angular\form-params.ts %DEST%
COPY /y %SOURCE%\angular\encoder.ts %DEST%
COPY /y %SOURCE%\angular\index.ts %DEST%
COPY /y %SOURCE%\angular\variables.ts %DEST%
