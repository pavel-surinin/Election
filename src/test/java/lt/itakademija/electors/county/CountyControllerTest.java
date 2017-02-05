package lt.itakademija.electors.county;

import lt.itakademija.Application;
import lt.itakademija.electors.MyJsonParser;
import lt.itakademija.electors.candidate.A_SavingUpdatingDataTest;
import lt.itakademija.electors.candidate.CandidateService;
import lt.itakademija.electors.party.PartyEntity;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lenovo on 2/3/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {CountyControllerTest.Config.class, Application.class})
public class CountyControllerTest {

    @Autowired
        TestRestTemplate rest;
    @Autowired
        CountyRepository countyRepository;

    @Autowired
        CountyService countyService;
    
    String URI = "/county";
    
    @Before
    public void setUp() throws Exception {
        String klaipedaString = "{ \"name\" : \"klaipedaTest\" }";
        String vilniusString = "{ \"name\" : \"vilniusTest\" }";
        String kaunasString = "{ \"name\" : \"kaunasTest\" }";
        rest.postForEntity(URI, MyJsonParser.parse(klaipedaString), CountyEntity.class);
        rest.postForEntity(URI,  MyJsonParser.parse(vilniusString), CountyEntity.class);
        rest.postForEntity(URI,  MyJsonParser.parse(kaunasString), CountyEntity.class);
    }

    @After
    @Transactional
    public void tearDown() throws Exception {
       countyRepository.findAll().stream().forEach(c ->countyService.delete(c.getId()));
    }

    @Test
    public void getCountyList() throws Exception {
        //setup
        ResponseEntity<CountyReport[]> resp = rest.getForEntity(URI, CountyReport[].class);
        //verify
        assertThat(resp.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(resp.getBody().length, CoreMatchers.is(3));
    }

    @Test
    public void save() throws Exception {
        //setup
        String kedainiaiString = "{ \"name\" : \"kedainiaiTest\" }";
        ResponseEntity<CountyEntity> responseCreate = rest.postForEntity(URI, MyJsonParser.parse(kedainiaiString), CountyEntity.class);
        //verify
        assertThat(responseCreate.getStatusCodeValue(), CoreMatchers.is(200));

    }

    @Test
    public void update() throws Exception {
        //setup
        String kedainiaiString = "{\"name\" : \"kedainiaiTest\" }";
        ResponseEntity<CountyEntity> responseCreate = rest.postForEntity(URI, MyJsonParser.parse(kedainiaiString), CountyEntity.class);
        //exercise
        Long id = responseCreate.getBody().getId();
        String kedainiaiUpdateString = "{\"id\" : "+id+",  \"name\" : \"kedainiaiUpdate\" }";
        ResponseEntity<CountyEntity> responseUpdate = rest.postForEntity(URI, MyJsonParser.parse(kedainiaiUpdateString), CountyEntity.class);
        //verify
        assertThat(responseCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(responseUpdate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(responseUpdate.getBody().getName(), CoreMatchers.is("kedainiaiUpdate"));

    }

    @Test
    public void handleFileUpload() throws Exception {

    }

    @Test
    public void handleStorageFileNotFound() throws Exception {

    }

    @Test
    public void getCountyDetals() throws Exception {

    }

    @Test
    public void deletecounty() throws Exception {

    }

    @TestConfiguration
    static class Config {
        @Bean
        @Primary
        public CandidateService service() {
            return new CandidateService();
        }
    }
}