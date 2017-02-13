package lt.itakademija.electors;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.party.PartyEntity;
import lt.itakademija.electors.results.multi.ResultMultiEntity;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;


/**
 * Created by Pavel on 2017-02-03.
 */
public class MyUtils {


    public static JSONObject parseStringToJson(String string) {

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
     * @param pathToFile apth to csv from root
     * @return
     */
    public static MultipartFile parseToMultiPart(String pathToFile) {
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


    /**
     * @param countyId   Long as default use null, because County ID is (@GeneratedValue(strategy = GenerationType.AUTO)) {CountyEntity}. Use Id when want to update
     * @param countyName String to assign county name, can be used just letters
     * @return JSON String to parse in parseStringToJson
     */
    public static String getCountyJson(Long countyId, String countyName) {
        String countyString = "{\"id\" : \"" + countyId + "\",\"name\" : \"" + countyName + "\"}";
        return countyString;
    }

    /**
     * @param districtId       Long as default use null, because district ID is (@GeneratedValue(strategy = GenerationType.AUTO)) {DistrictEntity}. Use Id when want to update districts other params
     * @param districtName     String to assign district name, can be used just letters
     * @param districtAdress   String to assign district address
     * @param numberOfElectors Integer number of electors in this district
     * @param countyId         Long county ID. Before assign County ID, the County with that ID has to be created
     * @return JSON String to parse in parseStringToJson
     */
    public static String getDistrictJson(Long districtId, String districtName, String districtAdress, int numberOfElectors, Long countyId) {
        String districtString = "{\"id\" : \"" + districtId + "\",\"name\" : \"" + districtName + "\", \"adress\" : \"" + districtAdress + "\",\"numberOfElectors\" : " + numberOfElectors + ",\"county\" : {\"id\" : " + countyId + "}}";
        return districtString;
    }

    /**
     * @param name                 String to assign district representatives name, can be used just letters
     * @param surname              String to assign district representatives surname, can be used just letters
     * @param representingDistrict Long district ID. Before assign district ID, the district with that ID has to be created
     * @return JSON String to parse in parseStringToJson
     */
    public static String getDistrictRepresentativeJson(String name, String surname, Long representingDistrict) {
        String representativeString = "{\"name\" : \"" + name + "\", \"surname\" : \"" + surname + "\", \"district\" : {\"id\" : " + representingDistrict + "}}";
        return representativeString;
    }

    /**
     * @param candidateId
     * @param name
     * @param surname
     * @param birthDate
     * @param description
     * @param numberInParty
     * @param countyId
     * @param partyId
     * @return JSON String to parse in parseStringToJson
     */

    public static String getCandidateJson(Long candidateId, String name, String surname, String birthDate, String description, Integer numberInParty, Long countyId, Long partyId) {
        String candidateString = "{\"id\": \"" + candidateId + "\",\"name\": \"" + name + "\",\"surname\": \"" + surname + "\", \"description\": \"" + description + "\",\"numberInParty\": \"" + numberInParty + "\",\"birthDate\": \"" + birthDate + "\", \"county\" : {\"id\" : " + countyId + "}, \"party\" : {\"id\": " + partyId + "}}";
        return candidateString;
    }

    /**
     * @param partyId
     * @param name
     * @param partyNumber
     * @return
     */
    public static String getPartyJson(Long partyId, String name, Integer partyNumber, List<CandidateEntity> members) {
        String partyString = "{\"id\": \"" + partyId + "\",\"name\": \"" + name + "\", \"partyNumber\": \"" + partyNumber + "}";
        return partyString;
    }

    /**
     * @param resultId
     * @param districtId
     * @param candidateId
     * @param votes
     * @param isApproved
     * @param datePublished
     * @return
     */
    public static String getResultsJson(Long resultId, Long districtId, Long candidateId, Long votes, Boolean isApproved, Date datePublished) {
        String resultString = "{\"id\": \"" + resultId + "\", \"district\" : {\"id\" : " + districtId + "}, \"candidate\" : {\"id\": " + candidateId + "}, \"votes\": \"" + votes + "\", \"isApproved\": \"" + isApproved + "\", \"datePublished\":\"" + datePublished + "}";
        return resultString;
    }

    public static ResultMultiEntity getResultMultiEntity(PartyEntity party, DistrictEntity ditrict, long votes) {
        ResultMultiEntity res1 = new ResultMultiEntity();
        res1.setParty(party);
        res1.setDistrict(ditrict);
        res1.setVotes(votes);
        res1.setDatePublished(new Date());
        return res1;
    }
}
