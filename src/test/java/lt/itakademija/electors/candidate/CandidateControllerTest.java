package lt.itakademija.electors.candidate;

import lt.itakademija.Application;
import lt.itakademija.electors.MyUtils;
import lt.itakademija.electors.county.*;
import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.district.DistrictReport;
import lt.itakademija.electors.district.DistrictRepository;
import lt.itakademija.electors.party.PartyEntity;
import lt.itakademija.electors.party.PartyRepository;
import lt.itakademija.electors.party.PartyService;
import lt.itakademija.electors.results.single.ResultSingleEntity;
import lt.itakademija.electors.results.single.ResultSingleRepository;
import lt.itakademija.electors.results.single.ResultSingleService;
import lt.itakademija.storage.CSVParser;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Gabriele on 2017-02-06.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {CountyControllerTest.Config.class, Application.class})
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
    CountyRepository countyRepository;

    @Autowired
    CountyService countyService;

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    ResultSingleService resultSingleService;

    @Autowired
    ResultSingleRepository resultSingleRepository;

    @Autowired
    TransactionTemplate transactionTemplate;

    String URI = "/candidate";

    @Before
    public void setUp() throws Exception {

        String SauliusString = MyUtils.getCandidateJson(null, "Saulius", "Domavicius", "2000-12-12", "Adminas", 92, null, null);
        ResponseEntity<CandidateEntity> respCandidateCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(SauliusString), CandidateEntity.class);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void updateCandidateName() throws Exception {

        //setup
        final MultipartFile file = MyUtils.parseToMultiPart("test-csv/data-party-4.csv");
        String name = "Tvarka ir tt";
        Integer number = 2;
        partyService.save(name, number, file);
        Long partyId = partyRepository.getPartyByNumber(2).getId();

        String UtenosCountyString = "{\"name\":\"Utenos\"}";
        ResponseEntity<CountyEntity> respCountyCreate = rest.postForEntity("/county", MyUtils.parseStringToJson(UtenosCountyString), CountyEntity.class);
        Long countyId = respCountyCreate.getBody().getId();

        String SauliusString = MyUtils.getCandidateJson(null, "Saulius", "Domavicius", "2000-12-12", "Adminas", 92, partyId, countyId);
        ResponseEntity<CandidateEntity> respCandidateCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(SauliusString), CandidateEntity.class);

        //exercise
        Long candidateId = respCandidateCreate.getBody().getId();
        String DomasUpdateCandidateString = MyUtils.getCandidateJson(candidateId, "Domas", "Tomavicius", "1990-05-05", "Bedarbis", 80, partyId, countyId);
        ResponseEntity<CandidateEntity> respCandidateUpdate = rest.postForEntity(URI, MyUtils.parseStringToJson(DomasUpdateCandidateString), CandidateEntity.class);

        //verify
        assertThat(respCandidateUpdate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(candidateRepository.finById(candidateId).getName(), CoreMatchers.is("Domas"));
    }

    @Test
    public void updateCandidateSurname() throws Exception {

        //setup
        final MultipartFile file = MyUtils.parseToMultiPart("test-csv/data-party-3.csv");
        String name = "Darbo";
        Integer number = 4;
        partyService.save(name, number, file);
        Long partyId = partyRepository.getPartyByNumber(4).getId();

        String ZarasuCountyString = "{\"name\":\"Zarasu\"}";
        ResponseEntity<CountyEntity> respCountyCreate = rest.postForEntity("/county", MyUtils.parseStringToJson(ZarasuCountyString), CountyEntity.class);
        Long countyId = respCountyCreate.getBody().getId();

        String SauliusString = MyUtils.getCandidateJson(null, "Saulius", "Domavicius", "2000-12-12", "Adminas", 92, partyId, countyId);
        ResponseEntity<CandidateEntity> respCandidateCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(SauliusString), CandidateEntity.class);

        //exercise
        Long candidateId = respCandidateCreate.getBody().getId();
        String DomasUpdateCandidateString = MyUtils.getCandidateJson(candidateId, "Domas", "Tomavicius", "1990-05-05", "Bedarbis", 80, partyId, countyId);
        ResponseEntity<CandidateEntity> respCandidateUpdate = rest.postForEntity(URI, MyUtils.parseStringToJson(DomasUpdateCandidateString), CandidateEntity.class);

        //verify
        assertThat(respCandidateUpdate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(candidateRepository.finById(candidateId).getSurname(), CoreMatchers.is("Tomavicius"));
    }

    @Ignore
    @Test
    public void updateCandidateBirthDate() throws Exception {

        //setup
        final MultipartFile file = MyUtils.parseToMultiPart("test-csv/data-party-2.csv");
        String name = "Miego";
        Integer number = 9;
        partyService.save(name, number, file);
        Long partyId = partyRepository.getPartyByNumber(9).getId();

        String KlaipedosCountyString = "{\"name\":\"Klaipedos\"}";
        ResponseEntity<CountyEntity> respCountyCreate = rest.postForEntity("/county", MyUtils.parseStringToJson(KlaipedosCountyString), CountyEntity.class);
        Long countyId = respCountyCreate.getBody().getId();

        String SauliusString = MyUtils.getCandidateJson(null, "Saulius", "Domavicius", "2000-12-12", "Adminas", 92, partyId, countyId);
        ResponseEntity<CandidateEntity> respCandidateCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(SauliusString), CandidateEntity.class);

        //exercise
        Long candidateId = respCandidateCreate.getBody().getId();
        String DomasUpdateCandidateString = MyUtils.getCandidateJson(candidateId, "Domas", "Tomavicius", "2000-01-01", "Bedarbis", 80, partyId, countyId);
        ResponseEntity<CandidateEntity> respCandidateUpdate = rest.postForEntity(URI, MyUtils.parseStringToJson(DomasUpdateCandidateString), CandidateEntity.class);

        //verify
        assertThat(respCandidateUpdate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(candidateRepository.finById(candidateId).getBirthDate(), CoreMatchers.is("2000-01-01"));
    }

    @Test
    public void updateCandidateDescription() throws Exception {

        //setup
        final MultipartFile file = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name = "Svaju";
        Integer number = 6;
        partyService.save(name, number, file);
        Long partyId = partyRepository.getPartyByNumber(6).getId();

        String MarijampolesCountyString = "{\"name\":\"Marijampoles\"}";
        ResponseEntity<CountyEntity> respCountyCreate = rest.postForEntity("/county", MyUtils.parseStringToJson(MarijampolesCountyString), CountyEntity.class);
        Long countyId = respCountyCreate.getBody().getId();

        String SauliusString = MyUtils.getCandidateJson(null, "Saulius", "Domavicius", "2000-12-12", "Adminas", 92, partyId, countyId);
        ResponseEntity<CandidateEntity> respCandidateCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(SauliusString), CandidateEntity.class);

        //exercise
        Long candidateId = respCandidateCreate.getBody().getId();
        String DomasUpdateCandidateString = MyUtils.getCandidateJson(candidateId, "Domas", "Tomavicius", "1990-05-05", "Bedarbis", 80, partyId, countyId);
        ResponseEntity<CandidateEntity> respCandidateUpdate = rest.postForEntity(URI, MyUtils.parseStringToJson(DomasUpdateCandidateString), CandidateEntity.class);

        //verify
        assertThat(respCandidateUpdate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(candidateRepository.finById(candidateId).getDescription(), CoreMatchers.is("Bedarbis"));
    }

    @Test
    public void updateCandidateNumberInParty() throws Exception {

        //setup
        final MultipartFile file = MyUtils.parseToMultiPart("test-csv/data-party-5.csv");
        String name = "Debilu";
        Integer number = 80;
        partyService.save(name, number, file);
        Long partyId = partyRepository.getPartyByNumber(80).getId();

        String VarenaCountyString = "{\"name\":\"Varena\"}";
        ResponseEntity<CountyEntity> respCountyCreate = rest.postForEntity("/county", MyUtils.parseStringToJson(VarenaCountyString), CountyEntity.class);
        Long countyId = respCountyCreate.getBody().getId();

        String SauliusString = MyUtils.getCandidateJson(null, "Saulius", "Domavicius", "2000-12-12", "Adminas", 92, partyId, countyId);
        ResponseEntity<CandidateEntity> respCandidateCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(SauliusString), CandidateEntity.class);

        //exercise
        Long candidateId = respCandidateCreate.getBody().getId();
        String DomasUpdateCandidateString = MyUtils.getCandidateJson(candidateId, "Saulius", "Tomavicius", "1990-05-05", "Bedarbis", 80, partyId, countyId);
        ResponseEntity<CandidateEntity> respCandidateUpdate = rest.postForEntity(URI, MyUtils.parseStringToJson(DomasUpdateCandidateString), CandidateEntity.class);

        //verify
        assertThat(respCandidateUpdate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(candidateRepository.finById(candidateId).getNumberInParty(), CoreMatchers.is(80));
    }

    @Test
    public void getKandidatasList() throws Exception {

        //setup
        ResponseEntity<CandidateReport[]> resp = rest.getForEntity(URI, CandidateReport[].class);

        //verify
        assertThat(resp.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(resp.getBody().length, CoreMatchers.is(candidateRepository.getCandidatesList().size()));
    }


    @Test
    public void deleteCandidateList() throws Exception {

        //setup
        final MultipartFile file = MyUtils.parseToMultiPart("test-csv/data-party-5.csv");
        String name = "Barbarosu";
        Integer number = 88;
        partyService.save(name, number, file);
        Long partyId = partyRepository.getPartyByNumber(88).getId();

        String JonavaCountyString = "{\"name\":\"Jonava\"}";
        ResponseEntity<CountyEntity> respCountyCreate = rest.postForEntity("/county", MyUtils.parseStringToJson(JonavaCountyString), CountyEntity.class);
        Long countyId = respCountyCreate.getBody().getId();

        String SauliusString = MyUtils.getCandidateJson(null, "Saulius", "Domavicius", "2000-12-12", "Adminas", 92, partyId, countyId);
        ResponseEntity<CandidateEntity> respCandidateCreate1 = rest.postForEntity(URI, MyUtils.parseStringToJson(SauliusString), CandidateEntity.class);

        String PauliusString = MyUtils.getCandidateJson(null, "Paulius", "Protavicius", "2000-10-10", "Sandelininkas", 19, partyId, countyId);
        ResponseEntity<CandidateEntity> respCandidateCreate2 = rest.postForEntity(URI, MyUtils.parseStringToJson(PauliusString), CandidateEntity.class);

        //exercise
        candidateRepository.getCandidatesList().stream().forEach(c -> candidateService.delete(c.getId()));

        //verify
        assertThat(candidateRepository.getCandidatesList().size(), CoreMatchers.is(0));

    }

    @Test
    public void deleteCandidateAndTheirResults() throws Exception {

        //setup adding candidates
        MultipartFile result = MyUtils.parseToMultiPart("test-csv/data-county-non-party.csv");
        ResponseEntity<CountyReport[]> resp1 = rest.getForEntity("/county", CountyReport[].class);
        final Long id = resp1.getBody()[0].getId();
        countyService.update(id, result);

        //setup adding district
        final Long countyId = countyRepository.findAll().get(0).getId();
        String jsonDistrictCreate = "{\"name\" : \"Panerių\",\"adress\" : \"Ūmėdžių g. 9\",\"numberOfElectors\":500,\"county\":{\"id\":" + countyId + "}}";
        ResponseEntity<DistrictReport> respCreateDistrict;
        respCreateDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(jsonDistrictCreate), DistrictReport.class);

        //votes
        final DistrictEntity d1 = districtRepository.findAll().get(0);

        final CandidateEntity c1 = candidateRepository.getCandidatesList().get(0);
        final CandidateEntity c2 = candidateRepository.getCandidatesList().get(1);
        final CandidateEntity c3 = candidateRepository.getCandidatesList().get(2);

        ResultSingleEntity res1 = new ResultSingleEntity(c1,d1,200L);
        ResultSingleEntity res2 = new ResultSingleEntity(c2,d1,500L);
        ResultSingleEntity res3 = new ResultSingleEntity(c3,d1,400L);

        List<ResultSingleEntity> rl = new ArrayList<>();
        rl.add(res1);
        rl.add(res2);
        rl.add(res3);

        final String save = resultSingleService.save(rl);

//        //exercise
//        candidateRepository.getCandidatesList().stream().forEach(c -> candidateService.delete(c.getId()));
//
//        ResponseEntity<CandidateReport[]> resp = rest.getForEntity(URI, CandidateReport[].class);
//        ResponseEntity<ResultSingleEntity[]> respResults = rest.getForEntity("/results", ResultSingleEntity[].class);

        //verify
        assertThat(save, CoreMatchers.is("Votes registered"));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(3));
        candidateRepository.getCandidatesList().stream().forEach(c -> candidateService.delete(c.getId()));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(0));
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