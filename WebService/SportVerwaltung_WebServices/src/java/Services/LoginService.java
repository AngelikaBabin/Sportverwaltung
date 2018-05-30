/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Data.Account;
import Misc.Authentification;
import Data.Database;
import Misc.Crypt;
import Exceptions.AccountNotFoundException;
import com.google.gson.Gson;
import java.io.IOException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author chris
 */
@Path("login")
public class LoginService {

    @Context
    private UriInfo context;
    private Gson gson;
    private Database db;
    private Crypt crypt;

    public LoginService() {
        try{
            db = Database.newInstance();
            gson = new Gson();
            crypt = new Crypt();
        }
        catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(String content) throws IOException {
        Response r;
        String token;
        try{
            Account a = gson.fromJson(content, Account.class);
            System.out.print("Login: " + a + "...");
            a = db.login(a);
            token = crypt.encrypt(a.toTokenString());
            Authentification.loginToken(token);
            r = Response.accepted().header("Token", token).build();
            System.out.print("Success");
        }
        catch(AccountNotFoundException ex){
            r = Response.status(Response.Status.UNAUTHORIZED).entity(ex).type(MediaType.APPLICATION_JSON).build();
            System.out.print("Failed");
        }
        catch(Exception ex){
            r = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            System.out.print("Failed");
        }
        System.out.println();
        return r;
    }
}