package lt.itakademija.electors;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

/**
 * Created by nevyt on 2/3/2017.
 */
public class MyJsonParaser {

    public static JSONObject parse(String string){
    JSONParser parser = new JSONParser(0);
    JSONObject parsedJson = null;
        try {
            parsedJson = (JSONObject) parser.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedJson;
    }
}
