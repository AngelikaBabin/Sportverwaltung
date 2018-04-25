/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Data.Account;
import Data.Authentification;
import Data.Database;
import com.google.gson.Gson;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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
    private final Database db;
    private final Gson gson;

    public AccountService() {
        db = Database.newInstance();
        gson = new Gson();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccount(@HeaderParam("Token") String token) {
        Response r;
        try{
            if(Authentification.isUserAuthenticated(token)){
                Account a = db.getAccount(Account.parseToken(token));
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
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccount(String content, @HeaderParam("Token") String token) {
        Response r;
        try{
            if(Authentification.isUserAuthenticated(token)){
                Account a = gson.fromJson(content, Account.class);
                db.updateAccount(a);
                r = Response.ok().build();
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
