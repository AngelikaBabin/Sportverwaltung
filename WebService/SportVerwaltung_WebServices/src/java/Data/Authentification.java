/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

/**
 *
 * @author chris
 */
public class Authentification {
    //ToDo delete token loool
    private static HashSet<String> collTokens = new HashSet<>(Arrays.asList(new String[] {"gvlXQIl+LAtN9sdXh/3EjojCIjaCFLCxcH/WETETQco="}));
    
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
