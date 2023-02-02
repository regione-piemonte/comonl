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
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.ReferenteDeleteParam;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.ReferenteFindParam;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.ReferenteFindResponse;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.ReferenteParam;
import it.csi.comonl.comonlweb.lib.external.impl.silp.model.ReferenteResponse;

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
@Path("/referenti")


public interface ReferentiApi  {
   
    @POST
    @Path("/delete")
    @Consumes({ "*/*" })
    @Produces({ "application/json" })
    Response deleteReferente( ReferenteDeleteParam body,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    @POST
    @Path("/find")
    @Consumes({ "*/*" })
    @Produces({ "application/json" })
    Response findReferente( ReferenteFindParam body,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    @POST
    @Path("/save")
    @Consumes({ "*/*" })
    @Produces({ "application/json" })
    Response saveReferente( ReferenteParam body,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
