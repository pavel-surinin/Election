package lt.itakademija.electors.results.multi;

import lt.itakademija.Application;
import lt.itakademija.electors.MyUtils;
import lt.itakademija.electors.candidate.CandidateControllerTest;
import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.candidate.CandidateRepository;
import lt.itakademija.electors.candidate.CandidateService;
import lt.itakademija.electors.county.CountyEntity;
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
import lt.itakademija.electors.results.multi.rating.RatingEntity;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    CandidateService candidateService;

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    TransactionTemplate transactionTemplate;

    String URI = "/result/multi";

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {

        resultMultiRepository.findAll().stream().forEach(p-> resultMultiService.delete(p.getId()));
        partyRepository.findAll().stream().forEach(p -> partyService.delete(p.getId()));
        districtRepository.findAll().stream().forEach(d -> districtService.delete(d.getId()));
        countyRepository.findAll().stream().forEach(c-> countyService.delete(c.getId()));
    }

    @Test
    public void save() throws Exception {

        districtRepository.findAll().stream().forEach(d -> districtService.delete(d.getId()));
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
        final PartyEntity p2 = partyRepository.getPartyByNumber(46);
        final PartyEntity spoiled = new PartyEntity();
        spoiled.setId(-1991L);

        ResultMultiEntity res1 = MyUtils.getResultMultiEntity(p1, d1, 20L);
        ResultMultiEntity res2 = MyUtils.getResultMultiEntity(p2, d1, 50L);
        ResultMultiEntity res3 = MyUtils.getResultMultiEntity(spoiled, d1, 10L);

        List<ResultMultiEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);

        String save = null;
        Long sum = res1.getVotes()+res2.getVotes()+res3.getVotes();
        if(sum <= d1.getNumberOfElectors()) {
            save = resultMultiService.save(results);
        }

        //verify
        assertThat(save, CoreMatchers.is("Votes registered"));
        assertThat(resultMultiRepository.getResultsByDistrictId(d1).size(), CoreMatchers.is(2));
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
        final PartyEntity p2 = partyRepository.getPartyByNumber(41);
        final PartyEntity spoiled = new PartyEntity();
        spoiled.setId(-1991L);

        ResultMultiEntity res1 = MyUtils.getResultMultiEntity(p1, d1, 20L);
        ResultMultiEntity res2 = MyUtils.getResultMultiEntity(p2, d1, 50L);
        ResultMultiEntity res3 = MyUtils.getResultMultiEntity(spoiled, d1, 10L);

        List<ResultMultiEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);

        final String save = resultMultiService.save(results);
        final String approveResults = resultMultiService.approve(respCreateDistrict.getBody().getId());

        //verify
        assertThat(approveResults, CoreMatchers.is("Votes Approved"));

    }

    @Test
    public void negativeData() throws Exception{
        districtRepository.findAll().stream().forEach(d -> districtService.delete(d.getId()));
        //setup adding county and district
        String vilniusString = MyUtils.getCountyJson(null, "Vilniaus");
        ResponseEntity<CountyEntity> countyEntityResponseEntity = rest.postForEntity("/county", MyUtils.parseStringToJson(vilniusString), CountyEntity.class);
        Long countyId = countyEntityResponseEntity.getBody().getId();

        DistrictEntity districtEnt1 = new DistrictEntity();
        districtEnt1.setAdress("Saules 55-23");
        districtEnt1.setName("Tvarkos");
        districtEnt1.setCounty(countyRepository.findAll().get(0));
        districtEnt1.setNumberOfElectors(800L);
        DistrictEntity districtEntity1 = districtService.save(districtEnt1);
        //setup adding parties
        final MultipartFile file1 = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name1 = "Valio Partija";
        Integer number1 = 42;
        partyService.save(name1, number1, file1);

        final MultipartFile file2 = MyUtils.parseToMultiPart("test-csv/data-party-2.csv");
        String name2 = "Namu Partija";
        Integer number2 = 43;
        partyService.save(name2, number2, file2);

        //votes
        final PartyEntity p1 = partyRepository.getPartyByNumber(42);
        final PartyEntity p2 = partyRepository.getPartyByNumber(43);
        final PartyEntity spoiled = new PartyEntity();
        spoiled.setId(-1991L);

//        final int sizeBeforeSave = resultMultiRepository.findAll().size();

        ResultMultiEntity res1 = MyUtils.getResultMultiEntity(p1, districtEnt1, -20L);
        ResultMultiEntity res2 = MyUtils.getResultMultiEntity(p2, districtEnt1, 50L);
        ResultMultiEntity res3 = MyUtils.getResultMultiEntity(spoiled, districtEnt1, 10L);

        List<ResultMultiEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);

        final String save = resultMultiService.save(results);
        final int sizeAfterSave = resultMultiRepository.findAll().size();

        //verify
        assertThat(sizeAfterSave, CoreMatchers.is(0));
    }

    @Test
    public void deleteResultsButNotParties() throws Exception {
        partyRepository.findAll().stream().forEach(p -> partyService.delete(p.getId()));
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

        ResultMultiEntity res1 = MyUtils.getResultMultiEntity(p1, d1, 20L);
        ResultMultiEntity res2 = MyUtils.getResultMultiEntity(p2, d1, 50L);
        ResultMultiEntity res3 = MyUtils.getResultMultiEntity(spoiled, d1, 10L);

        List<ResultMultiEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);

        final String save = resultMultiService.save(results);

        //verify
//        assertThat(save, CoreMatchers.is("Votes registered"));
//        assertThat(resultMultiRepository.getResultsByDistrictId(d1).size(), CoreMatchers.is(2));
        resultMultiRepository.findAll().stream().forEach(c -> resultMultiService.delete(c.getId()));
        assertThat(resultMultiRepository.getResultsByDistrictId(d1).size(), CoreMatchers.is(0));
        assertThat(partyRepository.findAll().size(), CoreMatchers.is(2));
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

        ResultMultiEntity res1 = MyUtils.getResultMultiEntity(p1, d1, 20L);
        ResultMultiEntity res2 = MyUtils.getResultMultiEntity(p2, d1, 50L);
        ResultMultiEntity res3 = MyUtils.getResultMultiEntity(spoiled, d1, 10L);

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

        ResultMultiEntity res10 = MyUtils.getResultMultiEntity(p1, d1, 20L);
        ResultMultiEntity res20 = MyUtils.getResultMultiEntity(p2, d1, 50L);
        ResultMultiEntity res30 = MyUtils.getResultMultiEntity(spoiled, d1, 10L);

        List<ResultMultiEntity> results2 = new ArrayList<>();
        results2.add(res10);
        results2.add(res20);
        results2.add(res30);

        String save2 = resultMultiService.save(results2);

        assertThat(save2, CoreMatchers.is("Votes registered"));
        assertThat(resultMultiRepository.getResultsByDistrictId(d1).size(), CoreMatchers.is(2));

    }

    @Test
    public void rating() throws Exception{

        //setup adding parties
        final MultipartFile file1 = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name1 = "Bangu Partija";
        Integer number1 = 75;
        partyService.save(name1, number1, file1);

        //setup adding district
        final Long countyId = countyRepository.findAll().get(0).getId();
        String jsonDistrictCreate = "{\"name\" : \"Panerių\",\"adress\" : \"Ūmėdžių g. 9\",\"numberOfElectors\":500,\"county\":{\"id\":" + countyId + "}}";
        ResponseEntity<DistrictReport> respCreateDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(jsonDistrictCreate), DistrictReport.class);

        //votes
        final DistrictEntity d1 = districtRepository.findAll().get(0);

        final PartyEntity p1 = partyRepository.getPartyByNumber(75);
        final PartyEntity spoiled = new PartyEntity();
        spoiled.setId(-1991L);

        ResultMultiEntity res1 = MyUtils.getResultMultiEntity(p1, d1, 200L);
        ResultMultiEntity res3 = MyUtils.getResultMultiEntity(spoiled, d1, 10L);

        final CandidateEntity c1 = candidateRepository.getCandidatesList().get(0);
        final CandidateEntity c2 = candidateRepository.getCandidatesList().get(1);
        final CandidateEntity c3 = candidateRepository.getCandidatesList().get(2);

        RatingEntity rating1 = MyUtils.getRatingEntity(c1, res1, 10);
        RatingEntity rating2 = MyUtils.getRatingEntity(c2, res1, 15);
        RatingEntity rating3 = MyUtils.getRatingEntity(c3, res1, 5);

        List<RatingEntity> ratingsList = new ArrayList<>();
        ratingsList.add(rating1);
        ratingsList.add(rating2);
        ratingsList.add(rating3);

        res1.setRating(ratingsList);

        List<ResultMultiEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res3);
        String save = resultMultiService.save(results);

        //verify
        assertThat(res1.getRating().size(), CoreMatchers.is(3));
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