package lt.itakademija.electors.candidate;

import lt.itakademija.Application;
import lt.itakademija.WebSecurityConfig;
import lt.itakademija.electors.MyUtils;
import lt.itakademija.electors.county.*;
import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.district.DistrictService;
import lt.itakademija.electors.party.PartyRepository;
import lt.itakademija.electors.party.PartyService;
import lt.itakademija.electors.results.single.ResultSingleEntity;
import lt.itakademija.electors.results.single.ResultSingleRepository;
import lt.itakademija.electors.results.single.ResultSingleService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExcecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
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
        classes = {Application.class})
@ContextConfiguration
@TestExecutionListeners(listeners={ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        WithSecurityContextTestExcecutionListener.class})
@WithMockUser(username="admin",roles={"ADMIN"})
public class CandidateControllerTest {
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
    DistrictService districtService;
    @Autowired
    ResultSingleService resultSingleService;
    @Autowired
    ResultSingleRepository resultSingleRepository;

    String URI = "/candidate";

    @Before
    public void setUp() throws Exception {

        final MultipartFile file1 = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name1 = "Balvanu Partija";
        Integer number1 = 45;
        partyService.save(name1, number1, file1);

        final MultipartFile file2 = MyUtils.parseToMultiPart("test-csv/data-party-2.csv");
        String name2 = "Ereliu Partija";
        Integer number2 = 46;
        partyService.save(name2, number2, file2);

        String SauliusString = MyUtils.getCandidateJson(null, "Saulius", "Domavicius", "2000-12-12", "Adminas", 92, null, null);
        ResponseEntity<CandidateEntity> respCandidateCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(SauliusString), CandidateEntity.class);
    }

    @After
    public void tearDown() throws Exception {
        candidateRepository.getCandidatesList().stream().forEach(c -> candidateService.delete(c.getId()));
        partyRepository.findAll().stream().forEach(p -> partyService.delete(p.getId()));
    }

    @Test
    public void updateCandidateName() throws Exception {
        //setup
        final MultipartFile file = MyUtils.parseToMultiPart("test-csv/data-party-4.csv");
        String name = "Tvarka ir kt";
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
        String DomasUpdateCandidateString = MyUtils.getCandidateJson(candidateId, "Domas", "Tomavicius", "1990-06-03", "Bedarbis", 80, partyId, countyId);
        ResponseEntity<CandidateEntity> respCandidateUpdate = rest.postForEntity(URI, MyUtils.parseStringToJson(DomasUpdateCandidateString), CandidateEntity.class);
        //verify
        assertThat(respCandidateUpdate.getStatusCodeValue(), CoreMatchers.is(200));
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

        //setup adding county and district
        String vilniusString = MyUtils.getCountyJson(null, "Vilniaus");
        ResponseEntity<CountyEntity> countyEntityResponseEntity = rest.postForEntity("/county", MyUtils.parseStringToJson(vilniusString), CountyEntity.class);
        Long countyId = countyEntityResponseEntity.getBody().getId();

        DistrictEntity districtEnt = new DistrictEntity();
        districtEnt.setAdress("Saules 5-23");
        districtEnt.setName("Tautos");
        districtEnt.setCounty(countyRepository.findAll().get(0));
        districtEnt.setNumberOfElectors(5000L);
        DistrictEntity districtEntity = districtService.save(districtEnt);

        //setup adding candidates
        final MultipartFile file1 = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name1 = "TroliuMumiu Partija";
        Integer number1 = 15;
        partyService.save(name1, number1, file1);
        Long partyId = partyRepository.getPartyByNumber(15).getId();

        String SauliusString = MyUtils.getCandidateJson(null, "Saulius", "Domavicius", "2000-12-12", "Adminas", 92, partyId, countyId);
        ResponseEntity<CandidateEntity> respCandidateCreate1 = rest.postForEntity(URI, MyUtils.parseStringToJson(SauliusString), CandidateEntity.class);
        Long sauliusId = respCandidateCreate1.getBody().getId();

        String PauliusString = MyUtils.getCandidateJson(null, "Paulius", "Protavicius", "2000-10-10", "Sandelininkas", 19, partyId, countyId);
        ResponseEntity<CandidateEntity> respCandidateCreate2 = rest.postForEntity(URI, MyUtils.parseStringToJson(PauliusString), CandidateEntity.class);
        Long pauliusId = respCandidateCreate2.getBody().getId();

        //votes
        final CandidateEntity cand1 = candidateRepository.finById(sauliusId);
        final CandidateEntity cand2 = candidateRepository.finById(pauliusId);
        final CandidateEntity spoiled = new CandidateEntity();
        spoiled.setId(-1991L);

        ResultSingleEntity res1 = new ResultSingleEntity(cand1, districtEnt, 200L, new Date());
        ResultSingleEntity res2 = new ResultSingleEntity(cand2, districtEnt, 500L, new Date());
        ResultSingleEntity res3 = new ResultSingleEntity(spoiled, districtEnt, 100L, new Date());

        List<ResultSingleEntity> resultList = new ArrayList<>();
        resultList.add(res1);
        resultList.add(res2);
        resultList.add(res3);

        final String save = resultSingleService.save(resultList);

        //verify
        assertThat(save, CoreMatchers.is("Votes registered"));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(2));
        candidateRepository.getCandidatesList().stream().forEach(c -> candidateService.delete(c.getId()));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(0));
        assertThat(candidateRepository.getCandidatesList().size(), CoreMatchers.is(0));
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