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
import Data.Event;
import Data.Filter;
import Exceptions.FilterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Kraschl
 */
@Path("events")
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
            System.out.println("Get Events..." + filter);
            if(Authentification.isUserAuthenticated(token)){
                List<Event> events = db.getEvents(Filter.valueOf(filter), Account.parseToken(token));
                r = Response.ok().entity( new GenericEntity<List<Event>>(events){}).build();
                System.out.println("Success");
            }
            else{
                r = Response.status(Response.Status.FORBIDDEN).build();
                System.out.println("Auth Failed");
            }
        }
        catch(FilterException ex){
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
        Event e = new Event();
        Account a;
        try{
            if(Authentification.isUserAuthenticated(token)){
                a = Account.parseToken(token);
                JsonParser parser = new JsonParser();
                JsonObject json = parser.parse(content).getAsJsonObject();
                e.setName(json.get("name").getAsString());
                e.setDetails(json.get("details").getAsString());
                e.setLocation(json.get("location").getAsString());
                e.setMaxTeilnehmer(json.get("maxTeilnehmer").getAsInt());
                e.setDatetime(json.get("datetime").getAsString()); 
                e.setSportart(json.get("sportart").getAsString()); 
                e.setVeranstalter(a);
                
                System.out.print("Add Event: " + e + "...");
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
    
    /**
     * @Kumnig
     * @Rajic
     */
    @DELETE
    public Response deleteEvent(@HeaderParam("Token") String token, @QueryParam("eventId") int eventId) {
        Response r;
        Event e;
        try{
                if(Authentification.isUserAuthenticated(token)){
                    e = db.getEventById(eventId);
                    db.deleteCompleteEvent(e);
                    System.out.print("Delete Event: " + e + "...");
                    r = Response.status(Response.Status.OK).build();
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
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEvent(@HeaderParam("Token") String token, String content) {
        Response r;
        Event e = new Event();
        try{
            if(Authentification.isUserAuthenticated(token)){
                JsonParser parser = new JsonParser();
                JsonObject json = parser.parse(content).getAsJsonObject();
                e.setId(json.get("id").getAsInt());
                e.setName(json.get("name").getAsString());
                e.setDetails(json.get("details").getAsString());
                e.setLocation(json.get("location").getAsString());
                e.setMaxTeilnehmer(json.get("maxTeilnehmer").getAsInt());
                e.setDatetime(json.get("datetime").getAsString());
                e.setSportart(json.get("sportart").getAsString());
                
                System.out.print("Update Event: " + e + "...");
                db.updateEvent(e);
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
