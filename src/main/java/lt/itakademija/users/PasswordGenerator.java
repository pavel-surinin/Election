package lt.itakademija.users;

import java.util.Random;

/**
 * Created by Pavel on 2017-02-03.
 */
public class PasswordGenerator {

    public static String generate(){
        StringBuilder sb = new StringBuilder();
        int n = 15;
        String setLetterLower = "qwertyuiopasdfghjklzxcvbnm";
        String setLetterUpper = "QWERTYUIOPASDFGHJKLZXCVBNM";
        String setSymbosl = "[];',.-=()_+:<>?";

        for (int i= 0; i < n; i++) {
            Random r = new Random();
            r.nextInt(setLetterLower.length());
//            int k = ....;   // random number between 0 and set.length()-1 inklusive
//            sb.append(set.charAt(k));
        }
        String result = sb.toString();
        return "lol";
    }

}
