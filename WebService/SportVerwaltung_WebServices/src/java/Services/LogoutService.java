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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author chris
 */
@Path("logout")
public class LogoutService {

    @Context
    private UriInfo context;

    public LogoutService() {
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Response logout(String content) {
        Authentification.logoutToken(content);
        return Response.ok().build();
    }
}
