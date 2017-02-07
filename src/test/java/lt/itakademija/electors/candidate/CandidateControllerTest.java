package lt.itakademija.electors.candidate;

import lt.itakademija.electors.MyUtils;
import lt.itakademija.electors.county.CountyEntity;
import lt.itakademija.electors.party.PartyEntity;
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

        String sauliusString = MyUtils.getCandidateJson(null, "Saulius", "Povilaitis", "1992-12-12", "Liolekas", 12);
        ResponseEntity<CandidateEntity> candidateEntityResponseEntity = rest.postForEntity("/candidate", MyUtils.parseStringToJson(sauliusString), CandidateEntity.class);
    }

    @After
    public void tearDown() throws Exception {

        candidateRepository.getCandidatesList().stream().forEach(d -> candidateService.delete(d.getId()));
    }

    @Ignore
    @Test
    public void update() throws Exception {

        //setup
        String tvarkaPartyString = MyUtils.getPartyJson(null, "Tvarka", 1);
        ResponseEntity<PartyEntity> respPartyCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(tvarkaPartyString), PartyEntity.class);
        Long partyId = respPartyCreate.getBody().getId();

        String sauliusString = MyUtils.getCandidateJson(null, "DomasTest", "DomaviciusTest", "2000-12-12", "Adminas", 92, 1, 1);
        ResponseEntity<CandidateEntity> respCandidateCreate = rest.postForEntity("/candidate", MyUtils.parseStringToJson(sauliusString), CandidateEntity.class);


        //exercise
        Long districtId = respCandidateCreate.getBody().getId();
        String pylimoUpdateDistrictString = MyUtils.getDistrictJson(districtId, "PylimoUpdateTest", "Pylimo g. 22-33", 450, countyId);
        ResponseEntity<DistrictEntity> respDistrictUpdate = rest.postForEntity(URI, MyUtils.parseStringToJson(pylimoUpdateDistrictString), DistrictEntity.class);
        //verify
        assertThat(respCountyCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respCandidateCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respDistrictUpdate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respCandidateCreate.findById(districtId).getName(), CoreMatchers.is("PylimoUpdateTest"));
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