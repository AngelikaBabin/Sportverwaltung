/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Misc;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Kraschl
 */
public class MailHandler {
    private final Session session;
    private boolean authentication=true;
    private boolean smtpServerTTLSEnabled = true;
    private String host = "smtp.gmail.com";
    private String port = "587";
    private String username="service.sporteventmanager@gmail.com";
    private String password="anconichkr";
    
    public MailHandler(){
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
    
    public void sendMail(String email, String subject, String body) throws Exception {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject(subject);
        message.setText(body);
        Transport.send(message);
    }
}
