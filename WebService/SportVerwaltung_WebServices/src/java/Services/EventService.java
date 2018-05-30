/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Data.Account;
import Misc.Authentification;
import Misc.Crypt;
import Data.Database;
import Data.Teilnehmer;
import Data.Event;
import Data.Filter;
import Exceptions.FilterExcpetion;
import Exceptions.RegisterExcpetion;
import com.google.gson.Gson;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author chris
 */
@Path("event")
public class EventService {
    @Context
    private UriInfo context;
    private Gson gson;
    private Database db;
    private Crypt crypt;

    public EventService() {
        try{
            db = Database.newInstance();
            gson = new Gson();
            crypt = new Crypt();
        }
        catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getEvents(@HeaderParam("Token") String token, @QueryParam("filter") String filter) {
        Response r;
        try{
            if(Authentification.isUserAuthenticated(token)){
                System.out.println("Get Events..");
                List<Event> events = db.getEvents(Filter.valueOf(filter), Account.parseToken(token));
                r = Response.ok().entity( new GenericEntity<List<Event>>(events){}).build();
                System.out.println("Success");
            }
            else{
                r = Response.status(Response.Status.FORBIDDEN).build();
                System.out.println("Failed");
            }
        }
        catch(FilterExcpetion ex){
            r = Response.status(Response.Status.BAD_REQUEST).build();
            System.out.println("Failed");
        }
        catch(Exception ex){
            r = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            System.out.println("Failed");
        }
        return r;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEvent(@HeaderParam("Token") String token, String content) {
        Response r;
        Event e;
        try{
            if(Authentification.isUserAuthenticated(token)){
                e = gson.fromJson(content, Event.class);            
                System.out.print("Register: " + e + "...");
                db.insertEvent(e);
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
