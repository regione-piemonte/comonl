# Configurations
All configuration MUST be set in the `buildfiles/environment.<env>.ts` file to be substituted at compile time with `src/environments/environment.ts`.\
Whenever a new environment is created, a corresponding entry MUST be added in the `angular.json` file to reference it.

- production = whether the configuration file corresponds to a production or pre-production environment
- ambiente = the name of the environment
- shibbolethAuthentication = whether the integration with Shibboleth is actually used (NOT USED)
- publicPath = the URL at which the application is exposed
- appBaseHref = the content of the `<base>` tag specifying the local path for the hyperreferences
- beServerPrefix = the prefix used in composing the BackEnd service URL (if it may help to be kept separate)
- beService = the BackEnd service base URL
- shibbolethSSOLogoutURL = the logout URL for the SSO
- onAppExitURL = the URL to be redirected to when exiting the application

# Scripts
The projects uses the standard Angular CLI scripts (note that you do not need to install the CLI globally, for it is referenced as a development dependency).

Some goals were added that can be used to simplify the lifecycle of the application:
- package-* = scripts that execute the Angular CLI build with a predefied configuration
- extract-i18n = script that extracts the internationalization strings and adds them to JSON files
- apply-license = script that applies the EUPL-1.2 license header to all files
