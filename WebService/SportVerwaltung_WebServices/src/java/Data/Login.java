/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author chris
 */
@XmlRootElement
public class Login {
    private String email;
    private String password;

    public Login(String usernme, String password) {
        this.email = usernme;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public static Login parseTokenToLogin(String token) throws Exception{
        token = new Crypt().decrypt(token);
        return new Login(token.split(":")[0], token.split(":")[1]);
    }

    @Override
    public String toString() {
        return getEmail() + ":" + password;
    }
}
