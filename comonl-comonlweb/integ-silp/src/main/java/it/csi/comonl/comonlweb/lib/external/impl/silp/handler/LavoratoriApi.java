/*-
 * ========================LICENSE_START=================================
 * COMONL BackEnd - INTEGRATION SILP submodule
 * %%
 * Copyright (C) 2022 Regione Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2022 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2-or-later
 * =========================LICENSE_END==================================
 */

package it.csi.comonl.comonlweb.lib.external.impl.silp.handler;

import it.csi.comonl.comonlweb.lib.external.impl.silp.model.*;


import it.csi.comonl.comonlweb.lib.external.impl.silp.model.ApiError;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.LavoratoreParam;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.LavoratoreResponse;

import java.util.List;
import java.util.Map;

import java.io.InputStream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.HttpHeaders;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.validation.constraints.*;
@Path("/lavoratori")


public interface LavoratoriApi  {
   
    @POST
    @Path("/save")
    @Consumes({ "*/*" })
    @Produces({ "application/json" })
    Response saveLavoratore( LavoratoreParam body,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
