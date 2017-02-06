package lt.itakademija.electors.representative;

import lt.itakademija.Application;
import lt.itakademija.electors.MyJsonParser;
import lt.itakademija.electors.county.CountyControllerTest;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by nevyt on 2/4/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {DistrictRepresentativeControllerTest.class,
                Application.class})

public class DistrictRepresentativeControllerTest {
    @Autowired
    TestRestTemplate rest;
    @Autowired
    DistrictRepresentativeRepository districtRepresentativeRepository;
    @Autowired
    DistrictRepresentativeService districtRepresentativeService;

    @Autowired


    String URI = "/representative";


    @Before
    public void setUp() throws Exception {
        String RepresentativeOne = "{\"name\": \"VilniusTest\", \"surname\": \"Preservative1\"}";
        rest.postForEntity(URI, MyJsonParser.parse(RepresentativeOne), DistrictRepresentativeEntity.class);
        String RepresentativeTwo = "{\"name\": \"KaunasTest\", \"surname\": \"Preservative2\"}";
        rest.postForEntity(URI, MyJsonParser.parse(RepresentativeTwo), DistrictRepresentativeEntity.class);
        String RepresentativeThree = "{\"name\": \"KlaipedaTest\", \"surname\": \"Preservative3\"}";
        rest.postForEntity(URI, MyJsonParser.parse(RepresentativeThree), DistrictRepresentativeEntity.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Ignore
    @Test
    public void getAllRepresentatives() throws Exception { // working
        //setup
        ResponseEntity<DistrictRepresentativeReport[]> response = rest.getForEntity(URI, DistrictRepresentativeReport[].class); // nesuprantu kodel [].
        //exercise

        //verify
        assertThat(response.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(response.getBody().length, CoreMatchers.is(3));
    }

    @Ignore
    @Test
    public void save() throws Exception {

        //setup
        String RepresentativeFour = "{\"name\": \"SiauliaiTest\", \"surname\": \"Preservative4\"}";
        //exercise
        ResponseEntity<DistrictRepresentativeEntity> responseCreate = rest.
                postForEntity(URI, MyJsonParser.parse(RepresentativeFour), DistrictRepresentativeEntity.class);
        ResponseEntity<DistrictRepresentativeReport[]> response = rest.getForEntity(URI, DistrictRepresentativeReport[].class);
        //verify
        assertThat(responseCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(response.getBody().length, CoreMatchers.is(4));

    }

}