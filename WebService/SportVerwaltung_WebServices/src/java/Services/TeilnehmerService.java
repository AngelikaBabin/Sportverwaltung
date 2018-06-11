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
import Exceptions.RegisterExcpetion;
import Misc.MailHandler;
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
 * @author Kraschl
 */
@Path("teilnehmer")
public class TeilnehmerService {

    @Context
    private UriInfo context;
    private Gson gson;
    private Database db;
    private Crypt crypt;
    private MailHandler mail;

    public TeilnehmerService() {
        try{
            db = Database.newInstance();
            gson = new Gson();
            crypt = new Crypt();
            mail = new MailHandler();
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
        String token;
        Account a;
        try{
            a = gson.fromJson(content, Account.class);
            System.out.print("Register: " + a + "...");
            db.addAccount(a);
            db.addTeilnehmerToAccount(a);
            token = crypt.encrypt(a.toTokenString());
            Authentification.loginToken(token);       
            mail.sendMail(a.getEmail(), "Click the link!", 
                    "http://192.168.193.150:8080/SportVerwaltung_WebServices/webresources/verify?token=" + crypt.encryptURL(a.toTokenString()));
            r = Response.status(Response.Status.CREATED).header("Token", token).build();
            System.out.println("Sucess");
        }
        catch(RegisterExcpetion ex){
            r = Response.status(Response.Status.CONFLICT).entity(ex).type(MediaType.APPLICATION_JSON).build();
            System.out.println("Failed: already exists");
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
