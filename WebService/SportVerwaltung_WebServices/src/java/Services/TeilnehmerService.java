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
import Data.Teilnehmer;
import Exceptions.RegisterExcpetion;
import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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
    private Gson gson;
    private Database db;
    private Crypt crypt;

    public TeilnehmerService() {
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeilnehmer(@HeaderParam("Token") String token) {
       Response r;
        try{
            if(Authentification.isUserAuthenticated(token)){
                Teilnehmer a = db.getTeilnehmer(Account.parseToken(token));
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
        String token = "";
        Account t = null;
        try{
            t = gson.fromJson(content, Account.class);
            System.out.print("Register: " + t + "...");
            db.addAccount(t);
            db.addTeilnehmerToAccount(t);
            token = crypt.encrypt(token);
            Authentification.loginToken(token);
            r = Response.status(Response.Status.CREATED).header("Token", token).build();
            System.out.println("Sucess");
        }
        catch(RegisterExcpetion ex){
            r = Response.status(Response.Status.CONFLICT).entity(ex).type(MediaType.APPLICATION_JSON).build();
            System.out.println("Failed");
        }
        catch(Exception ex){
            r = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            System.out.println("Failed");
        }
        return r;
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateScore(@HeaderParam("Token") String token, String content) {
       Response r;
        try{
            if(Authentification.isUserAuthenticated(token)){
                Teilnehmer t = gson.fromJson(content, Teilnehmer.class);
                db.updateTeilnehmer(t);
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
