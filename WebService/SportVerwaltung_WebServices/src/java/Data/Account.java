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
public class Account {
    private int id;
    private String name;
    private String email;
    private String password;
    private static Crypt crypt = new Crypt();

    public Account(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    public Account(String email, String password) {
        this.crypt = new Crypt();
        this.email = email;
        this.password = password;
    }
    
    public Account(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    
    public String toTokenString(){
        return getEmail() + ":" + getPassword();
    }

    public static Account parseToken(String token) throws Exception{
        token = crypt.decrypt(token);
        String[] data = token.split(":");
        return new Account(data[0], data[1]);
    }
    
    @Override
    public String toString() {
        return "Account{" + "id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + '}';
    }
}
