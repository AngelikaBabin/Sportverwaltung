/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Data.Account;
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

    public AccountService() {
        db = Database.newInstance();
    }

    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccount(@HeaderParam("Token") String token) {
        Response r;
        try{
            if(Authentification.isUserAuthenticated(token)){
                Account a = db.getAccount(Login.parseTokenToLogin(token));
                r = Response.ok().entity(a).build();
            }
            else{
                r = Response.status(Response.Status.FORBIDDEN).build();
            }
        }
        catch(Exception ex){
            r = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return r;
    }
}
