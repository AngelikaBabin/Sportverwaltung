/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author chris
 */
public class Crypt {
    SecretKeySpec secretKeySpec= null;

    public Crypt() throws Exception{
        // Das Passwort bzw der Schluesseltext
        String keyStr = "ks934lw5e34jf35kvjc0dnß3bd0v";
        // byte-Array erzeugen
        byte[] key = (keyStr).getBytes("UTF-8");
        // aus dem Array einen Hash-Wert erzeugen mit MD5 oder SHA
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        key = sha.digest(key);
        // nur die ersten 128 bit nutzen
        key = Arrays.copyOf(key, 16); 
        // der fertige Schluessel
        secretKeySpec = new SecretKeySpec(key, "AES");
    }
    
    public String encrypt(String text) throws Exception{
        

        // Verschluesseln
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(text.getBytes());

        // bytes zu Base64-String konvertieren (dient der Lesbarkeit)
        BASE64Encoder myEncoder = new BASE64Encoder();
        String geheim = myEncoder.encode(encrypted);

        // Ergebnis
        return geheim;
    }
    
    public String decrypt(String text) throws Exception{
        // BASE64 String zu Byte-Array konvertieren
        BASE64Decoder myDecoder2 = new BASE64Decoder();
        byte[] crypted2 = myDecoder2.decodeBuffer(text);

        // Entschluesseln
        Cipher cipher2 = Cipher.getInstance("AES");
        cipher2.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] cipherData2 = cipher2.doFinal(crypted2);
        String erg = new String(cipherData2);

        // Klartext
        return erg;
    }
}
