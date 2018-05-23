/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Data.Account;
import Misc.Authentification;
import Data.Database;
import Data.Event;
import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author chris
 */
@Path("teilnahme")
public class TeilnahmeService {

    @Context
    private UriInfo context;
    private final Gson gson;
    private final Database db;

    /**
     * Creates a new instance of TeilnahmeService
     */
    public TeilnahmeService() {
        db = Database.newInstance();
        gson = new Gson();
    }

    /**
     * Retrieves representation of an instance of Services.TeilnahmeService
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTeilnahme(@HeaderParam("Token") String token, @QueryParam("eventId") int eventId) {
        Response r;
        Event e;
        Account a;
        try{
            System.out.print("Add Teilnahme");
            if(Authentification.isUserAuthenticated(token)){
                a = Account.parseToken(token);
                db.addTeilnahme(eventId, a);
                r = Response.status(Response.Status.CREATED).build();
                System.out.println("Sucess");
            }
            else{
                r = Response.status(Response.Status.FORBIDDEN).build();
            }
        }
        catch(Exception ex){
            r = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            System.out.println("Failed");
        }
        return r;
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteTeilnahme(@HeaderParam("Token") String token, @QueryParam("eventId") int eventId) {
        Response r;
        Event e;
        Account a;
        try{
            System.out.print("Delete Teilnahme");
            if(Authentification.isUserAuthenticated(token)){
                a = Account.parseToken(token);
                db.deleteTeilnahme(eventId, a);
                r = Response.ok().build();
                System.out.println("Sucess");
            }
            else{
                r = Response.status(Response.Status.FORBIDDEN).build();
            }
        }
        catch(Exception ex){
            r = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            System.out.println("Failed");
        }
        return r;
    }
}
