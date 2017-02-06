package lt.itakademija.electors.candidate;

import lt.itakademija.electors.MyUtils;
import lt.itakademija.electors.party.PartyRepository;
import lt.itakademija.electors.party.PartyService;
import lt.itakademija.storage.CSVParser;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Vartotojas on 2017-02-06.
 */
public class CandidateControllerTest {

    @Autowired
    CSVParser csvParser;

    @Autowired
    TestRestTemplate rest;

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    CandidateService candidateService;

    @Autowired
    PartyService partyService;

    @Autowired
    PartyRepository partyRepository;

    @Autowired
    TransactionTemplate transactionTemplate;

    String URI = "/candidate";

    @Before
    public void setUp() throws Exception {

        String smaliusString = "{\"name\":\"Smalius\"}";
        rest.postForEntity(URI, MyUtils.parseStringToJson(smaliusString), CandidateEntity.class);
        String daliusString = "{\"name\":\"Dalius\"}";
        rest.postForEntity(URI, MyUtils.parseStringToJson(daliusString), CandidateEntity.class);
        String mariusString = "{\"name\":\"Marius\"}";
        rest.postForEntity(URI, MyUtils.parseStringToJson(mariusString), CandidateEntity.class);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void updateName() throws Exception {

        String smaliusString = "{\"name\":\"Smalius\"}";
        ResponseEntity<CandidateEntity> respCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(smaliusString), CandidateEntity.class);
        //verify
        assertThat(respCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respUpdate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respUpdate.getBody().getName(), CoreMatchers.is("SmaliusUpdate"));
    }

    @Ignore
    @Test
    public void getKandidatasList() throws Exception {

    }
    @Ignore
    @Test
    public void getCandidateDetails() throws Exception {

    }
    @Ignore
    @Test
    public void deleteCandidateById() throws Exception {

    }
    @Ignore
    @Test
    public void deleteCandidateByPartyId() throws Exception {

    }

    @TestConfiguration
    static class Config{
        @Bean
        @Primary
        public CandidateRepository repository() {
            return new CandidateRepository();
        }
    }
}