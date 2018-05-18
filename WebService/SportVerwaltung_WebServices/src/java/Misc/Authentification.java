/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Misc;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

/**
 *
 * @author chris
 */
public class Authentification {
    //ToDo delete token loool
    private static HashSet<String> collTokens = new HashSet<>(Arrays.asList(new String[] {"/7jucYcuXnMX0sh59w87XFxraAHfNmUhs4MiH+63bcw=",
        "bowNAfawP1qjkpzRaoucRe9A3OJQ9Hxx0mcrYjKaH7VIRpEmzk/nrt6CV9qMXKKF/f5jR8m7Zthw BO5n3Xkl8XoW2sRXqCrafSg6NKVak70="}));
    
    public static boolean isUserAuthenticated(String token) throws IOException, Exception{
        return collTokens.contains(token);
    }
    
    public static void loginToken(String token) { //Excption if already loggede in
        if(!collTokens.contains(token))
            collTokens.add(token);
    }
    
    public static void logoutToken(String token){ //Excpetion if not logged in
        if(collTokens.contains(token))
            collTokens.remove(token);
    }
}
