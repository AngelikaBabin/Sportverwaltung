/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Data.Authentification;
import java.io.IOException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
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
    public boolean putXml(String content) throws Exception {
        return Authentification.isUserAuthenticated(content);
    }
}
