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
import Data.Teilnehmer;
import Exceptions.AccountAlreadyExistsException;
import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author chris
 */
@Path("teilnehmer")
public class TeilnehmerService {

    @Context
    private UriInfo context;
    private Database db;
    private Gson gson;

    /**
     * Creates a new instance of TeilnehmerService
     */
    public TeilnehmerService() {
        db = Database.newInstance();
        gson = new Gson();
    }

    /**
     * Retrieves representation of an instance of Services.TeilnehmerService
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeilnehmer(@HeaderParam("Token") String token) {
       Response r;
        try{
            if(Authentification.isUserAuthenticated(token)){
                Teilnehmer a = db.getTeilnehmer(Login.parseTokenToLogin(token));
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
    public Response registerTeilnehmer(String content){
        Response r;
        try{
            Account t = gson.fromJson(content, Account.class);
            System.out.println(t);
            db.addAccount(t);
            db.addTeilnehmerToAccount(t);
            r = Response.status(Response.Status.CREATED).build();
        }
        catch(AccountAlreadyExistsException ex){
            r = Response.status(Response.Status.CONFLICT).entity(ex).type(MediaType.APPLICATION_JSON).build();
        }
        catch(Exception ex){
            r = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return r;
    }
}
