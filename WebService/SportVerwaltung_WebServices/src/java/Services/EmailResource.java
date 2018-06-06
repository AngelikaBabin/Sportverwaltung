/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author chris
 */
@Path("email")
public class EmailResource {

    @Context
    private UriInfo context;
    
    private final Session session;
 
    private boolean authentication=true;
    private boolean smtpServerTTLSEnabled = true;
    private String host = "smtp.gmail.com";
    private String port = "587";
    private String username="service.sporteventmanager@gmail.com";
    private String password="anconichkr";

    /**
     * Creates a new instance of EmailResource
     */
    public EmailResource() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", String.valueOf(authentication));
        props.put("mail.smtp.starttls.enable",smtpServerTTLSEnabled);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
            }
        });
    }

    /**
     * Retrieves representation of an instance of Services.EmailResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getXml() {
       //  logger.info("toEmailAddress {}, emailSubject {}, emailMessage {} ",
       // toEmailAddress,emailSubject,emailMessage);
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            String[] recipientList = "nico.kandut@gmail.com".split(",");
            InternetAddress[] recipientAddresses = new InternetAddress[recipientList.length];
            int counter = 0;
            for (String recipient: recipientList) {
                recipientAddresses[counter] = new InternetAddress(recipient.trim());
                counter++;
            }
            message.setRecipients(Message.RecipientType.TO, recipientAddresses);
            message.setSubject("Error when trying to register");
            message.setText("lol");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return Response.ok().build();
    }
}