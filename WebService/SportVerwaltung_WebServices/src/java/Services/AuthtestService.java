/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Data.Authentification;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author chris
 */
@Path("authtest")
public class AuthtestService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AuthtestService
     */
    public AuthtestService() {
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public boolean putXml(@HeaderParam("Token") String token) throws Exception {
        return Authentification.isUserAuthenticated(token);
    }
}
