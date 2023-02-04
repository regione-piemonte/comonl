/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - SRV submodule
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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Set;
import java.util.UUID;

import it.csi.comonl.comonlweb.lib.dto.Utente;
import it.csi.comonl.comonlweb.lib.dto.common.Ruolo;

public class YmlGeneratore {
	// final static String PATH_ROOT =
	// "D:\\workspace-comonl\\comonlweb\\docs\\definitions";
	final static String PATH_ROOT = "..\\docs\\definitions";

	final static String PATH_SYSTEM = PATH_ROOT + "\\system";
	final static String PATH_DECODIFICA = PATH_ROOT + "\\decodifica";
	final static String PATH_COMMON = PATH_ROOT + "\\common";
	final static String PATH_PROSPETTO = PATH_ROOT + "\\prospetto";

	final static Class pkClassDefault = Integer.class;
	final static String ACAPO = "\n";

	public static void main(String[] args) {
		try {
			// oggetti system
			go(Utente.class, PATH_SYSTEM);

			// oggetti decodifica

			
			// oggetti common
			go(Ruolo.class, PATH_COMMON);

			// oggetti prospetto

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void go(Class objClass, String path) throws FileNotFoundException, IOException {
		go(objClass, path, pkClassDefault);
	}

	private static void go(Class objClass, String path, Class pkClass) throws FileNotFoundException, IOException {
		StringBuilder sbOut = new StringBuilder();
		sbOut.append(objClass.getSimpleName() + ":" + ACAPO);
		sbOut.append("  type: object" + ACAPO);
		sbOut.append("  properties:" + ACAPO);

		Hashtable<String, String> hstProperies = new Hashtable<String, String>();

		// Get the public methods associated with this class.
		Method[] methods = objClass.getMethods();
		for (Method method : methods) {
			// System.out.println("Public method found: " + method.toString());
			if (method.getName().startsWith("is")) {
				System.out.println(method.getReturnType() + " - " + method.getName());
				String property = method.getName().substring(2, method.getName().length());
				property = property.substring(0, 1).toLowerCase() + property.substring(1);

				StringBuilder sb = new StringBuilder();

				if (method.getReturnType().getCanonicalName().equals("java.lang.Boolean")) {
					sb.append("    " + property + ":" + ACAPO);
					sb.append("      type: integer" + ACAPO);

				} else if (method.getReturnType().getCanonicalName().equals("boolean")) {
					sb.append("    " + property + ":" + ACAPO);
					sb.append("      type: boolean" + ACAPO);

				} else {
					if (!property.equals("class")) {
						System.out.println("DA GESTIRE - property: " + property);
						System.out.println("return " + method.getReturnType().getCanonicalName());
					}
				}

				if (!sb.toString().trim().equals("")) {
					hstProperies.put(property, sb.toString());
				}
			}

			if (method.getName().startsWith("get")) {
				System.out.println(method.getReturnType() + " - " + method.getName());
				String property = method.getName().substring(3, method.getName().length());
				property = property.substring(0, 1).toLowerCase() + property.substring(1);

				StringBuilder sb = new StringBuilder();
				if (method.getReturnType().getCanonicalName().equals("java.util.UUID")) {
					sb.append("    " + property + ":" + ACAPO);
					sb.append("      type: string" + ACAPO);
					sb.append("      format: uuid" + ACAPO);

				} else if (method.getReturnType().getCanonicalName().equals("java.lang.Object")) {
					sb.append("    " + property + ":" + ACAPO);
					if (pkClass != null && pkClass.equals(UUID.class)) {
						sb.append("      type: string" + ACAPO);
						sb.append("      format: uuid" + ACAPO);
					} else {
						sb.append("      type: integer" + ACAPO);
					}

				} else if (method.getReturnType().getCanonicalName().equals("java.lang.String")) {
					sb.append("    " + property + ":" + ACAPO);
					sb.append("      type: string" + ACAPO);

				} else if (method.getReturnType().getCanonicalName().equals("java.util.Date")) {
					sb.append("    " + property + ":" + ACAPO);
					sb.append("      type: string" + ACAPO);
					sb.append("      format: date-time" + ACAPO);

				} else if (method.getReturnType().getCanonicalName().equals("java.math.BigDecimal")) {
					sb.append("    " + property + ":" + ACAPO);
					sb.append("      type: number" + ACAPO);

				} else if (method.getReturnType().getCanonicalName().equals("java.lang.Long")) {
					sb.append("    " + property + ":" + ACAPO);
					sb.append("      type: number" + ACAPO);

				} else if (method.getReturnType().getCanonicalName().equals("java.lang.Boolean")) {
					sb.append("    " + property + ":" + ACAPO);
					sb.append("      type: integer" + ACAPO);

				} else if (method.getReturnType().getCanonicalName().equals("java.lang.Integer")) {
					sb.append("    " + property + ":" + ACAPO);
					sb.append("      type: integer" + ACAPO);

				} else if (method.getReturnType().getCanonicalName().equals("java.util.List")) {
					sb.append("    " + property + ":" + ACAPO);
					sb.append("      type: array" + ACAPO);
					sb.append("      items:  " + ACAPO);

					String objName = method.getGenericReturnType().getTypeName();
					if (objName.indexOf("String") != -1) {
						objName = "String";
						sb.append("        type: string" + ACAPO);
					} else {
						int iDot = objName.lastIndexOf(".");
						objName = objName.substring(iDot + 1, objName.length() - 1);
						sb.append("        $ref: '#/definitions/" + objName + "'" + ACAPO);
					}

				} else if (method.getReturnType().getCanonicalName().startsWith("it.csi.comonl.comonlweb.lib.dto.")) {
					int iDot = method.getReturnType().getCanonicalName().lastIndexOf(".");
					String objName = method.getReturnType().getCanonicalName().substring(iDot + 1);
					// objName = objName.substring(0, 1).toLowerCase() + objName.substring(1);

					sb.append("    " + property + ":" + ACAPO);
					sb.append("      type: object" + ACAPO);
					sb.append("      $ref: '#/definitions/" + objName + "'" + ACAPO);

				} else {
					if (!property.equals("class")) {
						System.out.println("DA GESTIRE - property: " + property);
						System.out.println("return " + method.getReturnType().getCanonicalName());
					}
				}

				if (!sb.toString().trim().equals("")) {
					hstProperies.put(property, sb.toString());
				}
			}
		}

		System.out.println("-----------------");
		// ordinamento properties
		Set<String> setProperties = hstProperies.keySet();
		ArrayList<String> listProperties = new ArrayList<String>();
		listProperties.addAll(setProperties);
		Collections.sort(listProperties, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});

		for (String property : listProperties) {
			sbOut.append(hstProperies.get(property));
		}

		System.out.println(sbOut);

		String filename = "";
		String name = objClass.getSimpleName().substring(0, 1).toLowerCase() + objClass.getSimpleName().substring(1);
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			String sc = "" + c;
			if (c >= 'A' && c <= 'Z') {
				filename += "-" + sc.toLowerCase();
			} else {
				filename += c;
			}
		}

		if (path.startsWith(".")) {
			String current = new java.io.File(".").getCanonicalPath();
			path = current + "\\" + path;
		}

		File file = new File(path + "\\" + filename + ".yml");
		System.out.println("output: " + file.getAbsolutePath());

		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		FileOutputStream fos = new FileOutputStream(file);
		fos.write(sbOut.toString().getBytes());
		fos.close();
	}

}
