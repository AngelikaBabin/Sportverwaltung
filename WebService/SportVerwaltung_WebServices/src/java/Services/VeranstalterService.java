/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Data.Account;
import Data.Database;
import Exceptions.RegisterExcpetion;
import Misc.Authentification;
import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Kraschl
 */
@Path("veranstalter")
public class VeranstalterService {

    @Context
    private UriInfo context;
    private Gson gson;
    private Database db;

    public VeranstalterService() {
        try{
            gson = new Gson();
            db = Database.newInstance();
        }
        catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVeranstalter(@HeaderParam("Token") String token) {
       Response r;
        try{
            if(Authentification.isUserAuthenticated(token)){
                Account a = db.getVeranstalter(Account.parseToken(token));
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
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerVeranstalter(String content){
        Response r;
        try{
            Account t = gson.fromJson(content, Account.class);
            System.out.println(t);
            db.addAccount(t);
            db.addVeranstalterToAccount(t);
            r = Response.status(Response.Status.CREATED).build();
        }
        catch(RegisterExcpetion ex){
            r = Response.status(Response.Status.CONFLICT).entity(ex).type(MediaType.APPLICATION_JSON).build();
        }
        catch(Exception ex){
            r = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return r;
    }
}
