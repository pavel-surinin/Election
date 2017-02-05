package lt.itakademija.electors.party;

import lt.itakademija.Application;
import lt.itakademija.electors.MyJsonParaser;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nevyt on 2/4/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {PartyControllerTest.class,
        Application.class})

public class PartyControllerTest {

    @Autowired
    PartyRepository partyRepository;

    @Autowired
    PartyService partyService;


    @Autowired
    TestRestTemplate rest;

    String URI = "/party/";

    @Before
    public void setUp() throws Exception {

        MultipartFile result = getMultipartFile("test-csv/data-county-party.csv");
        String resultString = result.getName();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+++++++++++++++++++++++++++++++++++HACKERZXXX : " + resultString);


        String PartyOne = "{\"name\": \"VilniusPartijaTest\", \"partyNumber\": \"11\", \"file\":" + resultString +"}";

        rest.postForEntity(URI, MyJsonParaser.parse(PartyOne), PartyEntity.class);
        String PartyTwo = "{\"name\": \"KaunasPartijaTest\", \"partyNumber\": \"22\", \"file\":" + resultString +"}";
        rest.postForEntity(URI, MyJsonParaser.parse(PartyTwo), PartyEntity.class);
        String PartyThree = "{\"name\": \"KlaipedaPartijaTest\", \"partyNumber\": \"33\", \"file\":" + resultString +"}";
        rest.postForEntity(URI, MyJsonParaser.parse(PartyThree), PartyEntity.class);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void handleFileUpload() throws Exception {



    }

    @Test
    public void handleStorageFileNotFound() throws Exception {

    }

    @Test
    public void getPartijaList() throws Exception {
        //setup

        ResponseEntity<PartyReport[]> response = rest.getForEntity(URI, PartyReport[].class);

        //verify
        assertThat(response.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(response.getBody().length, CoreMatchers.is(4)); // EROORAI. Nepaduoda partiju. Value = 0.

    }

    @Test
    public void getPartyById() throws Exception {
        ResponseEntity<PartyReport[]> response = rest.getForEntity(URI, PartyReport[].class);
        Long partyId = response.getBody()[0].getId();
        assertThat(partyId,CoreMatchers.is(0));
        assertThat(response.getBody()[0].getName(),CoreMatchers.is("VilniusPartijaTest"));

    }

    @Test
    public void save() throws Exception {
        String PartyFour = "{\"name\": \"SiauliaiPartijaTest\", \"partyNumber\": \"44\"}";
        ResponseEntity<PartyEntity> responseCreate = rest.postForEntity(URI, MyJsonParaser.parse(PartyFour), PartyEntity.class);
        assertThat(responseCreate.getStatusCodeValue(),CoreMatchers.is(200));
    }
    private List<PartyEntity> parseCSVToCandidates(String path, String entity) throws FileNotFoundException {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);

        csvParser.setPartyOrCounty(entity);
        return csvParser.extractCandidates(fis);
    }

    private MultipartFile getMultipartFile(String string) {
        Path path = Paths.get(string);
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