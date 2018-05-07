package com.example.frosty.als_involve_v3;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by frosty on 11/30/17.
 */

class ALSEncryption {

    private static SecretKeySpec secretKey;

    public static void main(String[] args) {}

    public static void setKey(String encryptKey){

        try {

            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(encryptKey.getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256,sr); // 256 bit encryption
            secretKey = new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");

        } catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    public static byte[] encrypt(String strToEncrypt, String key){ //might need to be static?

        try {

            setKey(key);

            Cipher cipher;
            cipher = Cipher.getInstance("AES"); // should use GCM mode
            cipher.init(Cipher.ENCRYPT_MODE,  secretKey);

            byte[] result;
            result = cipher.doFinal(strToEncrypt.getBytes());
            //encodeToString(byte[] input, int flags) byte[] = ?? , flags = NO_PADDING <- (might need to be 1)
            //i think this is what i need to do instead of the result = XX above
            return result;

        } catch(Exception e){
            System.out.println("Error while encrypting.");
        }
        return null;
    }

    public static String decrypt(byte[] strToDecrypt){ //might need to be static?

        try{

            Cipher cipher;
            cipher = Cipher.getInstance("AES"); // should use GCM mode
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            String returned = new String(cipher.doFinal(strToDecrypt));

            return returned;

        } catch(Exception e){
            System.out.println("Error while decrypting.");
        }
        return null;
    }
}