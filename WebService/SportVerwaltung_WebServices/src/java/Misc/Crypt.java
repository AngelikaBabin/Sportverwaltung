/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Misc;

import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
/**
 *
 * @author Kraschl
 */
public class Crypt {
    private SecretKeySpec secretKeySpec = null;

    public Crypt(){
        try{
            String keyStr = "ks934lw5e34jf35kvjc0dnß3bd0v";
            byte[] key = (keyStr).getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); 
            secretKeySpec = new SecretKeySpec(key, "AES");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public String encrypt(String text) throws Exception{
        byte[] encrypted = getChipherEncoder().doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }
    
    public String decrypt(String text) throws Exception{
        byte[] decoded = Base64.getDecoder().decode(text);
        byte[] decrypted = getChipherDecoder().doFinal(decoded);
        return new String(decrypted);
    }
    
    public String decryptURL(String text) throws Exception{
        byte[] decoded = java.util.Base64.getUrlDecoder().decode(text);
        byte[] decrypted = getChipherDecoder().doFinal(decoded);
        return new String(decrypted);
    }
    
    public String encryptURL(String text) throws Exception{
        byte[] encrypted = getChipherEncoder().doFinal(text.getBytes());
        return Base64.getUrlEncoder().withoutPadding().encodeToString(encrypted);
    }
    
    private Cipher getChipherDecoder() throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher;
    }
    
    private Cipher getChipherEncoder() throws Exception{
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher;
    }
}
