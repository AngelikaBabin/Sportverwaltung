/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Data.Account;
import Data.Database;
import Exceptions.AccountNotFoundException;
import Exceptions.RegisterExcpetion;
import Misc.Authentification;
import Misc.Crypt;
import Misc.MailHandler;
import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Kraschl
 */
@Path("recover")
public class RecoverService {

    @Context
    private UriInfo context;
    private Gson gson;
    private Database db;
    private Crypt crypt;
    private MailHandler mail;

    /**
     * Creates a new instance of RecoveryService
     */
    public RecoverService() {
        db = Database.newInstance();
        gson = new Gson();
        crypt = new Crypt();
        mail = new MailHandler();
    }

    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response recoverAccount(String content) {
        Response r;
        String token = "";
        Account a;
        try{
            a = gson.fromJson(content, Account.class);
            System.out.print("Recover: " + a + "...");
            a = db.getAccountByEmail(a);
            mail.sendMail(a.getEmail(), "Recovery Email!", 
                    "Passwort: " + a.getPassword());
            r = Response.ok().header("Token", token).build();
            System.out.println("Sucess");
        }
        catch(AccountNotFoundException ex){
            r = Response.status(Response.Status.FORBIDDEN).build();
            System.out.println("Failed");
        }
        catch(Exception ex){
            r = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            System.out.println("Failed");
        }
        return r;
    }
}
