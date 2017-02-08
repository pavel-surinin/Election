package lt.itakademija.electors.results.single;

import lt.itakademija.Application;
import lt.itakademija.electors.MyUtils;
import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.candidate.CandidateRepository;
import lt.itakademija.electors.county.*;
import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.district.DistrictReport;
import lt.itakademija.electors.district.DistrictRepository;
import lt.itakademija.electors.district.DistrictService;
import lt.itakademija.storage.CSVParser;
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
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Gabriele on 2017-02-08.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {CountyControllerTest.Config.class, Application.class})
public class ResultSingleControllerTest {

    @Autowired
    CSVParser csvParser;

    @Autowired
    TestRestTemplate rest;

    @Autowired
    CountyRepository countyRepository;

    @Autowired
    CountyService countyService;

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    DistrictService districtService;

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    ResultSingleRepository resultSingleRepository;

    @Autowired
    ResultSingleService resultSingleService;

    @Autowired
    TransactionTemplate transactionTemplate;

    String URI = "/results";

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void save() throws Exception {

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

        //verify
        assertThat(save, CoreMatchers.is("Votes registered"));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(3));
    }

    @Test
    public void approve() throws Exception {

    }

    @Test
    public void deleteResultsButNotCandidates() throws Exception {

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

        //exercise
        resultSingleRepository.findAll().stream().forEach(c -> resultSingleService.delete(c.getId()));

        //verify
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(0));
        assertThat(candidateRepository.getCandidatesList().size(), CoreMatchers.is(3));
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