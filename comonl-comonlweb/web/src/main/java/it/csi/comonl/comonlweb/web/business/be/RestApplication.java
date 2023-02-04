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
package it.csi.comonl.comonlweb.web.business.be;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;


@ApplicationPath("/api/v1")
public class RestApplication extends Application {

	public RestApplication() {
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setTitle("ComonlWeb");
		beanConfig.setDescription("API per ComonlWeb");
		beanConfig.setVersion("1.0.0");

		beanConfig.setHost("localhost:8080");
		beanConfig.setSchemes(new String[] { "http" });
		beanConfig.setBasePath("/comonlweb/api/v1");

		beanConfig.setResourcePackage("it.csi");
		beanConfig.setScan(true);
	}

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new HashSet();
		// resources.add(ServiziApiServiceImpl.class);
		resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
		resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
		return resources;
	}
}
