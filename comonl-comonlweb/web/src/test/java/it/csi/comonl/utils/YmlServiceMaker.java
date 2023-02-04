/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - WAR submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */
package it.csi.comonl.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import it.csi.comonl.comonlweb.web.business.be.api.UtenteApi;

public class YmlServiceMaker {
	// final static String PATH_ROOT =
	// "D:\\workspace-comonl\\comonlweb\\docs\\paths";
	final static String DOCS_PATH_ROOT = "..\\docs\\paths";

	final static boolean bRewrite = false;
	final static String ACAPO = "\n";

	public static void main(String[] args) {
		try {
			 go(UtenteApi.class);
			// go(CommonApi.class);
			// go(DecodificaApi.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void go(Class objClass) throws FileNotFoundException, IOException {
		String pathClassValue = null;
		Annotation[] classAnnotations = objClass.getAnnotations();
		for (int i = 0; i < classAnnotations.length; i++) {
			Annotation annotation = classAnnotations[i];
			if (annotation.annotationType().getCanonicalName().equals("javax.ws.rs.Path")) {
				String annotationName = annotation.toString();

				int iStart = annotationName.indexOf("value=\"") + "value=\"".length();
				int iStop = annotationName.indexOf("\"", iStart);
				pathClassValue = annotationName.substring(iStart, iStop);
			}
		}
		System.out.println(pathClassValue);
		StringBuilder sbOut = new StringBuilder();

		// Get the public methods associated with this class.
		Method[] methods = objClass.getMethods();
		for (Method method : methods) {
			String pathMethod = null;
			String httpMethod = null;

			System.out.println("---");
			System.out.println(method.getName());

			Annotation[] methodAnnotations = method.getAnnotations();
			for (int i = 0; i < methodAnnotations.length; i++) {
				Annotation annotation = methodAnnotations[i];
				if (annotation.annotationType().getCanonicalName().equals("javax.ws.rs.Path")) {
					String annotationName = annotation.toString();
					int iStart = annotationName.indexOf("value=\"") + "value=\"".length();
					int iStop = annotationName.indexOf("\"", iStart);
					pathMethod = annotationName.substring(iStart, iStop);

				} else if (annotation.annotationType().getCanonicalName().equals("javax.ws.rs.GET")) {
					httpMethod = "get";
				} else if (annotation.annotationType().getCanonicalName().equals("javax.ws.rs.POST")) {
					httpMethod = "post";
				} else if (annotation.annotationType().getCanonicalName().equals("javax.ws.rs.DELETE")) {
					httpMethod = "delete";
				} else if (annotation.annotationType().getCanonicalName().equals("javax.ws.rs.PUT")) {
					httpMethod = "put";
				}
			}

			StringBuilder sbParams = new StringBuilder();
			Parameter[] parameters = method.getParameters();
			for (int i = 0; i < parameters.length; i++) {
				Parameter parameter = parameters[i];
				String parameterName = parameter.getName();
				String parameterType = parameter.getType().getSimpleName().replace(".java", "");

				boolean bParamAnnotationNotFound = true;
				Annotation[] paramAnnotations = parameter.getAnnotations();
				for (int ii = 0; ii < paramAnnotations.length; ii++) {
					Annotation annotation = paramAnnotations[ii];
					System.out.println(annotation.annotationType().getCanonicalName());
					if (annotation.annotationType().getCanonicalName().equals("javax.ws.rs.PathParam")) {
						sbParams.append("      - in: path" + ACAPO);
						sbParams.append("        name: " + parameterName + ACAPO);
						sbParams.append("        required: true" + ACAPO);

						if (parameterType.equals("UUID")) {
							sbParams.append("        type: string" + ACAPO);
							// sbParams.append(" format: uuid" + ACAPO);
						} else if (parameterType.equals("Integer")) {
							sbParams.append("        type: integer" + ACAPO);
						} else {
							sbParams.append("        type: " + parameterType + ACAPO);
						}
						sbParams.append("        description: " + parameterName + ACAPO);
						bParamAnnotationNotFound = false;

					} else if (annotation.annotationType().getCanonicalName().equals("javax.ws.rs.QueryParam")) {
						sbParams.append("      - in: query" + ACAPO);
						sbParams.append("        name: " + parameterName + ACAPO);
						sbParams.append("        required: true" + ACAPO);
						sbParams.append("        type: " + parameterType + ACAPO);
						// sbParams.append(" format: uuid" + ACAPO);
						sbParams.append("        description: " + parameterName + ACAPO);
						bParamAnnotationNotFound = false;

					} else if (annotation.annotationType().getCanonicalName().equals("javax.ws.rs.core.Context")) {
						// ignore param
						bParamAnnotationNotFound = false;

					}
				}

				if (bParamAnnotationNotFound) {
					sbParams.append("      - in: body" + ACAPO);
					sbParams.append("        name: " + parameterName + ACAPO);
					sbParams.append("        required: true" + ACAPO);
					sbParams.append("        schema:" + ACAPO);
					sbParams.append("          $ref: '#/definitions/" + parameterType + "'" + ACAPO);
				}
			}

			System.out.println(pathMethod);
			System.out.println(httpMethod);
			String methodName = method.getName();
			String tag = objClass.getSimpleName().replace("Api", "");
			tag = tag.substring(0, 1).toLowerCase() + tag.substring(1);

			StringBuilder sb = new StringBuilder();
			sb.append("/" + pathClassValue);
			if (pathMethod != null) {
				sb.append("/" + pathMethod);
			}
			sb.append(":" + ACAPO);

			sb.append("  " + httpMethod + ":" + ACAPO);
			sb.append("    operationId: " + methodName + ACAPO);
			sb.append("    description: Restituisce dati registrati su sistema." + ACAPO);
			sb.append("    produces:" + ACAPO);
			sb.append("      - application/json" + ACAPO);
			sb.append("    tags:" + ACAPO);
			sb.append("      - " + tag + ACAPO);
			sb.append("    parameters:" + ACAPO);

			sb.append(sbParams);

			sb.append("    responses:" + ACAPO);
			sb.append("      '200':" + ACAPO);
			sb.append("        description: Dati registrati su sistema." + ACAPO);
			sb.append("        schema:" + ACAPO);
			sb.append("          type: array" + ACAPO);
			sb.append("          items:" + ACAPO);
			sb.append("            $ref: '#/definitions/MODEL_CLASS'" + ACAPO);
			sb.append("      default:" + ACAPO);
			sb.append("        description: Errore generico." + ACAPO);
			sb.append("        schema:" + ACAPO);
			sb.append("          $ref: '#/definitions/ApiError'" + ACAPO);

			sbOut.append(sb);
		}

		System.out.println("\n\n-----");
		System.out.println(sbOut);

		String subPath = objClass.getSimpleName().replace("Api", "");

		File file = new File(DOCS_PATH_ROOT + "\\" + subPath.toLowerCase() + "\\index.yml");
		System.out.println("output: " + file.getAbsolutePath());

		if (!bRewrite && file.exists()) {
			System.out.println("already exists");
			return;
		}

		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(sbOut.toString().getBytes());
		fos.close();
	}

}
