/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Data.Account;
import Data.Database;
import java.net.Inet4Address;
import java.net.URI;
import javax.mail.MessagingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author chris
 */
@Path("verify")
public class VerifyService {

    @Context
    private UriInfo context;
    private Database db;
    
    /**
     * Creates a new instance of VerifyService
     */
    public VerifyService() {
        db = Database.newInstance();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response verifyAccount(@QueryParam("token") String accountToken) throws Exception {
        Account a;
        Response r;
        try {
            db.verifyAccount(Account.parseURLToken(accountToken));
            r = Response.temporaryRedirect(new URI("http://" + Inet4Address.getLocalHost().getHostAddress() + 
                    ":8080/SportVerwaltung_WebServices/verified.html")).build();
        } catch (MessagingException e) {
            r = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return r;
    }
}
