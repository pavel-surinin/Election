package lt.itakademija.electors;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Pavel on 2017-02-03.
 */
public class MyUtils {

    public static JSONObject parseStringToJson(String string){
        JSONParser parser = new JSONParser(0);
        JSONObject parsedJson = null;
        try {
            parsedJson = (JSONObject) parser.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedJson;
    }

    /**
     *
     * @param pathToFile apth to csv from root
     * @return
     */
    public static MultipartFile parseToMultiPart(String pathToFile){
        Path path = Paths.get(pathToFile);
        String name = path.getFileName().toFile().getName();
        String originalFileName = path.getFileName().toFile().getName();
        String contentType = "multipart/form-data";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        return new MockMultipartFile(name, originalFileName, contentType, content);
    }
}
