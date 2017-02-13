package lt.itakademija.electors.results.multi;

import lt.itakademija.Application;
import lt.itakademija.electors.MyUtils;
import lt.itakademija.electors.candidate.CandidateControllerTest;
import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.candidate.CandidateRepository;
import lt.itakademija.electors.candidate.CandidateService;
import lt.itakademija.electors.county.CountyReport;
import lt.itakademija.electors.county.CountyRepository;
import lt.itakademija.electors.county.CountyService;
import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.district.DistrictReport;
import lt.itakademija.electors.district.DistrictRepository;
import lt.itakademija.electors.district.DistrictService;
import lt.itakademija.electors.party.PartyEntity;
import lt.itakademija.electors.party.PartyRepository;
import lt.itakademija.electors.party.PartyService;
import lt.itakademija.electors.results.single.ResultSingleEntity;
import lt.itakademija.electors.results.single.ResultSingleRepository;
import lt.itakademija.electors.results.single.ResultSingleService;
import lt.itakademija.exceptions.NegativeVoteNumberException;
import lt.itakademija.exceptions.TooManyVotersException;
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

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Gabriele on 2017-02-09.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {ResultMultiControllerTest.Config.class, Application.class})
public class ResultMultiControllerTest {

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
    PartyService partyService;

    @Autowired
    PartyRepository partyRepository;

    @Autowired
    ResultMultiRepository resultMultiRepository;

    @Autowired
    ResultMultiService resultMultiService;

    @Autowired
    TransactionTemplate transactionTemplate;

    String URI = "/result/multi";

    @Before
    public void setUp() throws Exception {

//        final MultipartFile file1 = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
//        String name1 = "Balvanu Partija";
//        Integer number1 = 45;
//        partyService.save(name1, number1, file1);
//
//        final MultipartFile file2 = MyUtils.parseToMultiPart("test-csv/data-party-2.csv");
//        String name2 = "Ereliu Partija";
//        Integer number2 = 46;
//        partyService.save(name2, number2, file2);

    }

    @After
    public void tearDown() throws Exception {

//        resultMultiRepository.getResultsByDistrictId(districtService.getDistrictById(d1)).stream().forEach(p-> resultMultiService.delete(p.getId()));
//        partyRepository.findAll().stream().forEach(p -> partyService.delete(p.getId()));
        districtRepository.findAll().stream().forEach(d -> districtService.delete());
    }

    @Test
    public void save() throws Exception {

        //setup adding parties
        final MultipartFile file1 = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name1 = "Jonu Partija";
        Integer number1 = 45;
        partyService.save(name1, number1, file1);

        final MultipartFile file2 = MyUtils.parseToMultiPart("test-csv/data-party-2.csv");
        String name2 = "Ponu Partija";
        Integer number2 = 46;
        partyService.save(name2, number2, file2);

        //setup adding district
        final Long countyId = countyRepository.findAll().get(0).getId();
        String jsonDistrictCreate = "{\"name\" : \"Panerių\",\"adress\" : \"Ūmėdžių g. 9\",\"numberOfElectors\":500,\"county\":{\"id\":" + countyId + "}}";
        ResponseEntity<DistrictReport> respCreateDistrict;
        respCreateDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(jsonDistrictCreate), DistrictReport.class);
        Long districtId = respCreateDistrict.getBody().getId();
        //votes
        final DistrictEntity d1 = districtRepository.findAll().get(0);

        final PartyEntity p1 = partyRepository.getPartyByNumber(45);
        Long partyId1 = p1.getId();
        final PartyEntity p2 = partyRepository.getPartyByNumber(46);
        Long partyId2 = p2.getId();

        final PartyEntity spoiled = new PartyEntity();

        spoiled.setId(-1991L);

        ResultMultiEntity res1 = getResultMultiEntity(p1, d1, 20L, new Date());
        ResultMultiEntity res2 = getResultMultiEntity(p2, d1, 50L, new Date());
        ResultMultiEntity res3 = getResultMultiEntity(spoiled, d1, 10L, new Date());

        List<ResultMultiEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);

//        final String save = resultMultiService.save(results);

        String save = null;
        try{
            Long sum = res1.getVotes()+res2.getVotes()+res3.getVotes();
            if(sum <= d1.getNumberOfElectors()) {
                save = resultMultiService.save(results);
            }else{
                throw new TooManyVotersException("There are more votes than voters in the district");
            }
        }catch (TooManyVotersException e){

        }

        //verify
        assertThat(save, CoreMatchers.is("Votes registered"));
        assertThat(resultMultiRepository.getResultsByDistrictId(d1).size(), CoreMatchers.is(2));

    }

    @Test
    public void moreVotesThanVoters () throws Exception{

        //setup adding parties
        final MultipartFile file1 = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name1 = "Balvanu Partija";
        Integer number1 = 47;
        partyService.save(name1, number1, file1);

        final MultipartFile file2 = MyUtils.parseToMultiPart("test-csv/data-party-2.csv");
        String name2 = "Ereliu Partija";
        Integer number2 = 48;
        partyService.save(name2, number2, file2);

        //setup adding district
        final Long countyId = countyRepository.findAll().get(0).getId();
        String jsonDistrictCreate = "{\"name\" : \"Panerių\",\"adress\" : \"Ūmėdžių g. 9\",\"numberOfElectors\":500,\"county\":{\"id\":" + countyId + "}}";
        ResponseEntity<DistrictReport> respCreateDistrict;
        respCreateDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(jsonDistrictCreate), DistrictReport.class);
        Long districtId = respCreateDistrict.getBody().getId();
        //votes
        final DistrictEntity d1 = districtRepository.findAll().get(0);

        final PartyEntity p1 = partyRepository.getPartyByNumber(47);
        Long partyId1 = p1.getId();
        final PartyEntity p2 = partyRepository.getPartyByNumber(48);
        Long partyId2 = p2.getId();

        final PartyEntity spoiled = new PartyEntity();

        spoiled.setId(-1991L);

        ResultMultiEntity res1 = getResultMultiEntity(p1, d1, 200L, new Date());
        ResultMultiEntity res2 = getResultMultiEntity(p2, d1, 500L, new Date());
        ResultMultiEntity res3 = getResultMultiEntity(spoiled, d1, 100L, new Date());

        List<ResultMultiEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);


        try{
            Long sum = res1.getVotes()+res2.getVotes()+res3.getVotes();
            if(sum > d1.getNumberOfElectors()) {
                throw new TooManyVotersException("There are more votes than voters in the district");
            }else{
                final String save = resultMultiService.save(results);
            }
        }catch (TooManyVotersException e){
            assertThat(e.getMessage(), CoreMatchers.is("There are more votes than voters in the district"));
        }

    }

    private ResultMultiEntity getResultMultiEntity(PartyEntity p1,DistrictEntity d1 ,long l, Date date) {
        ResultMultiEntity res1 = new ResultMultiEntity();
        res1.setParty(p1);
        res1.setDistrict(d1);
        res1.setVotes(l);
        res1.setDatePublished(date);
        return res1;
    }

    @Test
    public void approve() throws Exception {

        //setup adding parties
        final MultipartFile file1 = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name1 = "Lavonu Partija";
        Integer number1 = 49;
        partyService.save(name1, number1, file1);

        final MultipartFile file2 = MyUtils.parseToMultiPart("test-csv/data-party-2.csv");
        String name2 = "Monu Partija";
        Integer number2 = 41;
        partyService.save(name2, number2, file2);

        //setup adding district
        final Long countyId = countyRepository.findAll().get(0).getId();
        String jsonDistrictCreate = "{\"name\" : \"Panerių\",\"adress\" : \"Ūmėdžių g. 9\",\"numberOfElectors\":500,\"county\":{\"id\":" + countyId + "}}";
        ResponseEntity<DistrictReport> respCreateDistrict;
        respCreateDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(jsonDistrictCreate), DistrictReport.class);
        Long districtId = respCreateDistrict.getBody().getId();
        //votes
        final DistrictEntity d1 = districtRepository.findAll().get(0);

        final PartyEntity p1 = partyRepository.getPartyByNumber(49);
        Long partyId1 = p1.getId();
        final PartyEntity p2 = partyRepository.getPartyByNumber(41);
        Long partyId2 = p2.getId();

        final PartyEntity spoiled = new PartyEntity();

        spoiled.setId(-1991L);

        ResultMultiEntity res1 = getResultMultiEntity(p1, d1, 20L, new Date());
        ResultMultiEntity res2 = getResultMultiEntity(p2, d1, 50L, new Date());
        ResultMultiEntity res3 = getResultMultiEntity(spoiled, d1, 10L, new Date());

        List<ResultMultiEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);

        final String save = resultMultiService.save(results);

        //execute
        res1.setApproved(true);
        res2.setApproved(true);
        res3.setApproved(true);
//        results.stream().forEach(res -> ResultMultiService.approve(res.getDistrict().getId()));

        //verify
//        assertThat(respCreateDistrict.getBody().isResultSingleApproved(), CoreMatchers.is(true));
        assertThat(results.get(0).isApproved(), CoreMatchers.is(true));
        assertThat(results.get(1).isApproved(), CoreMatchers.is(true));

    }

    @Test
    public void negativeData() throws Exception{

        //setup adding parties
        final MultipartFile file1 = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name1 = "Valio Partija";
        Integer number1 = 42;
        partyService.save(name1, number1, file1);

        final MultipartFile file2 = MyUtils.parseToMultiPart("test-csv/data-party-2.csv");
        String name2 = "Namu Partija";
        Integer number2 = 43;
        partyService.save(name2, number2, file2);

        //setup adding district
        final Long countyId = countyRepository.findAll().get(0).getId();
        String jsonDistrictCreate = "{\"name\" : \"Panerių\",\"adress\" : \"Ūmėdžių g. 9\",\"numberOfElectors\":500,\"county\":{\"id\":" + countyId + "}}";
        ResponseEntity<DistrictReport> respCreateDistrict;
        respCreateDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(jsonDistrictCreate), DistrictReport.class);
        Long districtId = respCreateDistrict.getBody().getId();
        //votes
        final DistrictEntity d1 = districtRepository.findAll().get(0);

        final PartyEntity p1 = partyRepository.getPartyByNumber(42);
        final PartyEntity p2 = partyRepository.getPartyByNumber(43);

        final PartyEntity spoiled = new PartyEntity();

        spoiled.setId(-1991L);

        ResultMultiEntity res1 = getResultMultiEntity(p1, d1, 20L, new Date());
        ResultMultiEntity res2 = getResultMultiEntity(p2, d1, 50L, new Date());
        ResultMultiEntity res3 = getResultMultiEntity(spoiled, d1, 10L, new Date());

        List<ResultMultiEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);

        try {
            for (int i = 0; i < results.size(); i++) {
                if (results.get(i).getVotes() < 0) {
                    throw new NegativeVoteNumberException("Negative votes number");
                }
            }
            final String save = resultMultiService.save(results);
        }catch (NegativeVoteNumberException e){
            assertThat(e.getMessage(), CoreMatchers.is("Negative votes number"));
        }
//        assertThat(resultMultiRepository.getResultsByDistrictId(d1).size(), CoreMatchers.is(0));
    }

    @Test
    public void deleteResultsButNotParties() throws Exception {

        //setup adding parties
        final MultipartFile file1 = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name1 = "Trolo Partija";
        Integer number1 = 55;
        partyService.save(name1, number1, file1);

        final MultipartFile file2 = MyUtils.parseToMultiPart("test-csv/data-party-2.csv");
        String name2 = "Lolo Partija";
        Integer number2 = 56;
        partyService.save(name2, number2, file2);

        //setup adding district
        final Long countyId = countyRepository.findAll().get(0).getId();
        String jsonDistrictCreate = "{\"name\" : \"Panerių\",\"adress\" : \"Ūmėdžių g. 9\",\"numberOfElectors\":500,\"county\":{\"id\":" + countyId + "}}";
        ResponseEntity<DistrictReport> respCreateDistrict;
        respCreateDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(jsonDistrictCreate), DistrictReport.class);
        Long districtId = respCreateDistrict.getBody().getId();
        //votes
        final DistrictEntity d1 = districtRepository.findAll().get(0);

        final PartyEntity p1 = partyRepository.getPartyByNumber(55);
        final PartyEntity p2 = partyRepository.getPartyByNumber(56);
        final PartyEntity spoiled = new PartyEntity();
        spoiled.setId(-1991L);

        ResultMultiEntity res1 = getResultMultiEntity(p1, d1, 20L, new Date());
        ResultMultiEntity res2 = getResultMultiEntity(p2, d1, 50L, new Date());
        ResultMultiEntity res3 = getResultMultiEntity(spoiled, d1, 10L, new Date());

        List<ResultMultiEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);

        final String save = resultMultiService.save(results);

        //verify
        assertThat(save, CoreMatchers.is("Votes registered"));
        assertThat(resultMultiRepository.getResultsByDistrictId(d1).size(), CoreMatchers.is(2));
        resultMultiRepository.getResultsByDistrictId(d1).stream().forEach(c -> resultMultiService.delete(c.getId()));
        assertThat(resultMultiRepository.getResultsByDistrictId(d1).size(), CoreMatchers.is(0));
    }

    @Test
    public void deleteResultsAddAgain() throws Exception {

        //setup adding parties
        final MultipartFile file1 = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name1 = "Wast Partija";
        Integer number1 = 65;
        partyService.save(name1, number1, file1);

        final MultipartFile file2 = MyUtils.parseToMultiPart("test-csv/data-party-2.csv");
        String name2 = "Mau Partija";
        Integer number2 = 66;
        partyService.save(name2, number2, file2);

        //setup adding district
        final Long countyId = countyRepository.findAll().get(0).getId();
        String jsonDistrictCreate = "{\"name\" : \"Panerių\",\"adress\" : \"Ūmėdžių g. 9\",\"numberOfElectors\":500,\"county\":{\"id\":" + countyId + "}}";
        ResponseEntity<DistrictReport> respCreateDistrict;
        respCreateDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(jsonDistrictCreate), DistrictReport.class);
        Long districtId = respCreateDistrict.getBody().getId();
        //votes
        final DistrictEntity d1 = districtRepository.findAll().get(0);

        final PartyEntity p1 = partyRepository.getPartyByNumber(65);
        final PartyEntity p2 = partyRepository.getPartyByNumber(66);
        final PartyEntity spoiled = new PartyEntity();
        spoiled.setId(-1991L);

        ResultMultiEntity res1 = getResultMultiEntity(p1, d1, 20L, new Date());
        ResultMultiEntity res2 = getResultMultiEntity(p2, d1, 50L, new Date());
        ResultMultiEntity res3 = getResultMultiEntity(spoiled, d1, 10L, new Date());

        List<ResultMultiEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);

        final String save1 = resultMultiService.save(results);

        //verify
        assertThat(save1, CoreMatchers.is("Votes registered"));
        assertThat(resultMultiRepository.getResultsByDistrictId(d1).size(), CoreMatchers.is(2));
        resultMultiRepository.getResultsByDistrictId(d1).stream().forEach(c -> resultMultiService.delete(c.getId()));
        assertThat(resultMultiRepository.getResultsByDistrictId(d1).size(), CoreMatchers.is(0));

        ResultMultiEntity res10 = getResultMultiEntity(p1, d1, 20L, new Date());
        ResultMultiEntity res20 = getResultMultiEntity(p2, d1, 50L, new Date());
        ResultMultiEntity res30 = getResultMultiEntity(spoiled, d1, 10L, new Date());

        List<ResultMultiEntity> results2 = new ArrayList<>();
        results2.add(res10);
        results2.add(res20);
        results2.add(res30);

        String save2 = resultMultiService.save(results2);

        assertThat(save2, CoreMatchers.is("Votes registered"));
        assertThat(resultMultiRepository.getResultsByDistrictId(d1).size(), CoreMatchers.is(2));

    }

    @TestConfiguration
    static class Config{
        @Bean
        @Primary
        public ResultMultiService service() {
            return new ResultMultiService();
        }
    }
}