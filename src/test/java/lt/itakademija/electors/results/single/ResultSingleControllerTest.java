package lt.itakademija.electors.results.single;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lt.itakademija.Application;
import lt.itakademija.electors.MyUtils;
import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.candidate.CandidateRepository;
import lt.itakademija.electors.candidate.CandidateService;
import lt.itakademija.electors.county.*;
import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.district.DistrictReport;
import lt.itakademija.electors.district.DistrictRepository;
import lt.itakademija.electors.district.DistrictService;
import lt.itakademija.electors.party.PartyRepository;
import lt.itakademija.electors.party.PartyService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Gabriele on 2017-02-08.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {ResultSingleControllerTest.Config.class, Application.class})
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
    CandidateService candidateService;

    @Autowired
    PartyService partyService;

    @Autowired
    PartyRepository partyRepository;

    @Autowired
    ResultSingleRepository resultSingleRepository;

    @Autowired
    ResultSingleService resultSingleService;

    @Autowired
    TransactionTemplate transactionTemplate;

    String URI = "/result/single";

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

//        resultSingleRepository.findAll().stream().forEach(c -> resultSingleService.delete(c.getId()));
//        candidateRepository.getCandidatesList().stream().forEach(c -> candidateService.delete(c.getId()));
//        districtRepository.findAll().stream().forEach(d -> districtService.delete(d.getId()));
    }

    @Test
    public void save() throws Exception {

        //setup adding candidates
        MultipartFile result = MyUtils.parseToMultiPart("test-csv/data-county-non-party.csv");
        ResponseEntity<CountyReport[]> resp1 = rest.getForEntity("/county", CountyReport[].class);
        final Long id = resp1.getBody()[0].getId();
        countyService.update(id, result);
        //setup addnig district
        final Long countyId = countyRepository.findAll().get(0).getId();
        String jsonDistrictCreate = "{\"name\" : \"Panerių\",\"adress\" : \"Ūmėdžių g. 9\",\"numberOfElectors\":500,\"county\":{\"id\":" + countyId + "}}";
        ResponseEntity<DistrictReport> respCreateDistrict;
        respCreateDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(jsonDistrictCreate), DistrictReport.class);

        //votes
        final DistrictEntity d1 = districtRepository.findAll().get(0);

        final CandidateEntity c1 = candidateRepository.getCandidatesList().get(0);
        final CandidateEntity c2 = candidateRepository.getCandidatesList().get(1);
        final CandidateEntity c3 = candidateRepository.getCandidatesList().get(2);
        final CandidateEntity spoiled = new CandidateEntity();

        spoiled.setId(-1991L);

        ResultSingleEntity res1 = new ResultSingleEntity(c1, d1, 20L, new Date());
        ResultSingleEntity res2 = new ResultSingleEntity(c2, d1, 50L, new Date());
        ResultSingleEntity res3 = new ResultSingleEntity(c3, d1, 40L, new Date());
        ResultSingleEntity res4 = new ResultSingleEntity(spoiled, d1, 10L, new Date());

        List<ResultSingleEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);
        results.add(res4);

        String save = null;
        try{
            Long sum = res1.getVotes()+res2.getVotes()+res3.getVotes()+res4.getVotes();
            if(sum <= d1.getNumberOfElectors()) {
                save = resultSingleService.save(results);
            }else{
                throw new TooManyVotersException("There are more votes than voters in the district");
            }
        }catch (TooManyVotersException e){

        }

        //verify
        assertThat(save, CoreMatchers.is("Votes registered"));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(3));

    }

    @Test
    public void moreVotesThanVoters () throws Exception{

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
        Long districtId = respCreateDistrict.getBody().getId();

        //votes
        final DistrictEntity d1 = districtRepository.findAll().get(0);
        final CandidateEntity c1 = candidateRepository.getCandidatesList().get(0);
        final CandidateEntity c2 = candidateRepository.getCandidatesList().get(1);
        final CandidateEntity c3 = candidateRepository.getCandidatesList().get(2);
        final CandidateEntity spoiled = new CandidateEntity();
        spoiled.setId(-1991L);

        ResultSingleEntity res1 = new ResultSingleEntity(c1, d1, 200L, new Date());
        ResultSingleEntity res2 = new ResultSingleEntity(c2, d1, 500L, new Date());
        ResultSingleEntity res3 = new ResultSingleEntity(c3, d1, 400L, new Date());
        ResultSingleEntity res4 = new ResultSingleEntity(spoiled, d1, 100L, new Date());

        List<ResultSingleEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);
        results.add(res4);

        try{
            Long sum = res1.getVotes()+res2.getVotes()+res3.getVotes()+res4.getVotes();
            if(sum <= d1.getNumberOfElectors()) {
                final String save = resultSingleService.save(results);
            }else{
                throw new TooManyVotersException("There are more votes than voters in the district");
            }
        }catch (TooManyVotersException e){
            assertThat(e.getMessage(), CoreMatchers.is("There are more votes than voters in the district"));
        }

        //verify
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(0));

    }

    @Test
    public void approve() throws Exception {

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
        Long districtId = respCreateDistrict.getBody().getId();

        //votes
        final DistrictEntity d1 = districtRepository.findAll().get(0);
        final CandidateEntity c1 = candidateRepository.getCandidatesList().get(0);
        final CandidateEntity c2 = candidateRepository.getCandidatesList().get(1);
        final CandidateEntity c3 = candidateRepository.getCandidatesList().get(2);
        final CandidateEntity spoiled = new CandidateEntity();
        spoiled.setId(-1991L);

        ResultSingleEntity res1 = new ResultSingleEntity(c1, d1, 20L, new Date());
        ResultSingleEntity res2 = new ResultSingleEntity(c2, d1, 50L, new Date());
        ResultSingleEntity res3 = new ResultSingleEntity(c3, d1, 40L, new Date());
        ResultSingleEntity res4 = new ResultSingleEntity(spoiled, d1, 10L, new Date());

        List<ResultSingleEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);
        results.add(res4);

        final String save = resultSingleService.save(results);

        final String approveResults = resultSingleService.approve(respCreateDistrict.getBody().getId());

        //verify
        assertThat(approveResults, CoreMatchers.is("Results approved"));

    }

    @Test
    public void negativeData() throws Exception{

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
        final CandidateEntity spoiled = new CandidateEntity();

        spoiled.setId(-1991L);

        ResultSingleEntity res1 = new ResultSingleEntity(c1, d1, -20L, new Date());
        ResultSingleEntity res2 = new ResultSingleEntity(c2, d1, 50L, new Date());
        ResultSingleEntity res3 = new ResultSingleEntity(c3, d1, 40L, new Date());
        ResultSingleEntity res4 = new ResultSingleEntity(spoiled, d1, 100L, new Date());

        List<ResultSingleEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);
        results.add(res4);

        try {
            for (int i = 0; i < results.size(); i++) {
                if (results.get(i).getVotes() < 0) {
                    throw new NegativeVoteNumberException("Negative votes number");
                }
            }
            final String save = resultSingleService.save(results);
        }catch (NegativeVoteNumberException e){
            assertThat(e.getMessage(), CoreMatchers.is("Negative votes number"));
        }
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(0));
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
        final CandidateEntity spoiled = new CandidateEntity();

        spoiled.setId(-1991L);

        ResultSingleEntity res1 = new ResultSingleEntity(c1, d1, 200L, new Date());
        ResultSingleEntity res2 = new ResultSingleEntity(c2, d1, 500L, new Date());
        ResultSingleEntity res3 = new ResultSingleEntity(c3, d1, 400L, new Date());
        ResultSingleEntity res4 = new ResultSingleEntity(spoiled, d1, 100L, new Date());

        List<ResultSingleEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);
        results.add(res4);

        final String save = resultSingleService.save(results);

        //verify
        assertThat(save, CoreMatchers.is("Votes registered"));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(3));
        resultSingleRepository.findAll().stream().forEach(c -> resultSingleService.delete(c.getId()));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(0));
    }

    @Test
    public void deleteResultsAddAgain() throws Exception {

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
        final CandidateEntity spoiled = new CandidateEntity();

        spoiled.setId(-1991L);

        ResultSingleEntity res1 = new ResultSingleEntity(c1, d1, 200L, new Date());
        ResultSingleEntity res2 = new ResultSingleEntity(c2, d1, 500L, new Date());
        ResultSingleEntity res3 = new ResultSingleEntity(c3, d1, 400L, new Date());
        ResultSingleEntity res4 = new ResultSingleEntity(spoiled, d1, 100L, new Date());

        List<ResultSingleEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);
        results.add(res4);

        String save1 = resultSingleService.save(results);

        //verify
        assertThat(save1, CoreMatchers.is("Votes registered"));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(3));
        resultSingleRepository.findAll().stream().forEach(c -> resultSingleService.delete(c.getId()));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(0));

        ResultSingleEntity res10 = new ResultSingleEntity(c1, d1, 200L, new Date());
        ResultSingleEntity res20 = new ResultSingleEntity(c2, d1, 500L, new Date());
        ResultSingleEntity res30 = new ResultSingleEntity(c3, d1, 350L, new Date());
        ResultSingleEntity res40 = new ResultSingleEntity(spoiled, d1, 100L, new Date());

        List<ResultSingleEntity> results2 = new ArrayList<>();
        results2.add(res10);
        results2.add(res20);
        results2.add(res30);
        results2.add(res40);

        String save2 = resultSingleService.save(results2);

        assertThat(save2, CoreMatchers.is("Votes registered"));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(3));

    }

    @ResponseStatus(value= HttpStatus.FAILED_DEPENDENCY, reason="More votes registered than there is voters in the district")
    public class TooManyVotersException extends RuntimeException{

        public TooManyVotersException(String message) {
            super(message);
        }

        public TooManyVotersException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    @ResponseStatus(value= HttpStatus.FAILED_DEPENDENCY, reason="Negative value of votes number")
    public class NegativeVoteNumberException extends RuntimeException{

        public NegativeVoteNumberException(String message) {
            super(message);
        }

        public NegativeVoteNumberException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    @TestConfiguration
    static class Config{
        @Bean
        @Primary
        public ResultSingleRepository repository() {
            return new ResultSingleRepository();
        }
    }
}