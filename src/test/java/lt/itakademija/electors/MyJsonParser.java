package lt.itakademija.electors;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

/**
 * Created by lenovo on 2/3/2017.
 */
public class MyJsonParser {
    public static JSONObject parse(String stringToParse) {
        JSONParser parser = new JSONParser(0);
        JSONObject parsedJson = null;
        try {
            parsedJson = (JSONObject) parser.parse(stringToParse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedJson;
    }
}
