/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Data.Account;
import Data.Authentification;
import Data.Database;
import Data.Crypt;
import Data.Login;
import Exceptions.AccountNotFoundException;
import com.google.gson.Gson;
import java.io.IOException;
import javax.crypto.Cipher;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

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
        String token = null;
        try{
            Login l = gson.fromJson(content, Login.class);
            System.out.println(l);
            db.login(l);
            token = crypt.encrypt(l.toString());
            Authentification.loginToken(token);
            r = Response.accepted().header("Token", token).build();
        }
        catch(AccountNotFoundException ex){
            r = Response.status(Response.Status.UNAUTHORIZED).entity(ex).type(MediaType.APPLICATION_JSON).build();
        }
        catch(Exception ex){
            r = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return r;
    }
}
