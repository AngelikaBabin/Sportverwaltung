/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Data.Account;
import Data.Authentification;
import Data.Crypt;
import Data.Database;
import Data.Event;
import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
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
@Path("teilnahme")
public class TeilnahmeService {

    @Context
    private UriInfo context;
    private Gson gson;
    private Database db;

    /**
     * Creates a new instance of TeilnahmeService
     */
    public TeilnahmeService() {
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

    /**
     * PUT method for updating or creating an instance of TeilnahmeService
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTeilnahme(@HeaderParam("Token") String token, String content) {
        Response r;
        Event e;
        Account a;
        try{
            System.out.print("Add Teilnahme");
            if(Authentification.isUserAuthenticated(token)){
                e = gson.fromJson(content, Event.class);
                a = Account.parseToken(token);
                db.addTeilnahme(e, a);
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
}
