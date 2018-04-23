/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Data.Authentification;
import Data.Database;
import Data.Login;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author chris
 */
@Path("account")
public class AccountService {

    @Context
    private UriInfo context;
    private Database db;

    /**
     * Creates a new instance of AccountService
     */
    public AccountService() {
        db = Database.newInstance();
    }

    /**
     * Retrieves representation of an instance of Services.AccountService
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccount(@HeaderParam("Token") String token) {
        Response r;
        try{
            if(Authentification.isUserAuthenticated(token)){
                r = Response.ok(db.getAccount(Login.parseTokenToLogin(token))).build();
            }
            else{
                
            }
            r = Response.accepted().header("Token", token).build();
        }
        catch(Exception ex){
            r = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return r;
    }

    /**
     * PUT method for updating or creating an instance of AccountService
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
