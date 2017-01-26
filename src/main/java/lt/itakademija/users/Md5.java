package lt.itakademija.users;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Pavel on 2017-01-25.
 */
public class Md5 {

    public static String generate(String pass) {
        try {
            byte[] bytesOfMessage = pass.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            return toHexString(thedigest);
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException u){
            System.out.println("Error");
            u.printStackTrace();
        }
        return null;
    }

    private static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public static boolean equals(String hash, String pwFromInput){
        if (hash == generate(pwFromInput)){
            return true;
        } else {
            return false;
        }
    }
}


