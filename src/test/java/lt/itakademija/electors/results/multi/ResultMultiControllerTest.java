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

        final MultipartFile file1 = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name1 = "Jonu Partija";
        Integer number1 = 45;
        partyService.save(name1, number1, file1);

        final MultipartFile file2 = MyUtils.parseToMultiPart("test-csv/data-party-2.csv");
        String name2 = "Ponu Partija";
        Integer number2 = 46;
        partyService.save(name2, number2, file2);

        String vilniusString = MyUtils.getCountyJson(null, "Vilniaus");
        ResponseEntity<CountyEntity> countyEntityResponseEntity = rest.postForEntity("/county", MyUtils.parseStringToJson(vilniusString), CountyEntity.class);
        Long countyId = countyEntityResponseEntity.getBody().getId();

        DistrictEntity districtEnt = new DistrictEntity();
        districtEnt.setAdress("Saules 55-23");
        districtEnt.setName("Tvarkos");
        districtEnt.setCounty(countyRepository.findAll().get(0));
        districtEnt.setNumberOfElectors(800L);
        DistrictEntity districtEntity1 = districtService.save(districtEnt);
    }

    @After
    public void tearDown() throws Exception {
        districtRepository.findAll().stream().forEach(p-> resultMultiService.delete(p.getId()));
        partyRepository.findAll().stream().forEach(p -> partyService.delete(p.getId()));
        districtRepository.findAll().stream().forEach(d -> districtService.delete(d.getId()));
        countyRepository.findAll().stream().forEach(c-> countyService.delete(c.getId()));
    }

    @Test
    public void save() throws Exception {

        DistrictEntity districtEnt1 = new DistrictEntity();
        districtEnt1.setAdress("Saules 55-23");
        districtEnt1.setName("Lanisteriu");
        districtEnt1.setCounty(countyRepository.findAll().get(0));
        districtEnt1.setNumberOfElectors(800L);
        DistrictEntity districtEntity1 = districtService.save(districtEnt1);

        //votes
        final PartyEntity p1 = partyRepository.getPartyByNumber(45);
        final PartyEntity p2 = partyRepository.getPartyByNumber(46);
        final PartyEntity spoiled = new PartyEntity();
        spoiled.setId(-1991L);

        ResultMultiEntity res1 = MyUtils.getResultMultiEntity(p1, districtEnt1, 20L);
        ResultMultiEntity res2 = MyUtils.getResultMultiEntity(p2, districtEnt1, 50L);
        ResultMultiEntity res3 = MyUtils.getResultMultiEntity(spoiled, districtEnt1, 10L);

        List<ResultMultiEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);

        final String save = resultMultiService.save(results);

        //verify
        assertThat(save, CoreMatchers.is("Votes registered"));
        assertThat(resultMultiRepository.getResultsByDistrictId(districtEnt1).size(), CoreMatchers.is(2));
    }

    @Test
    public void approve() throws Exception {

        DistrictEntity districtEnt1 = new DistrictEntity();
        districtEnt1.setAdress("Saules 55-23");
        districtEnt1.setName("Lanisteriu");
        districtEnt1.setCounty(countyRepository.findAll().get(0));
        districtEnt1.setNumberOfElectors(800L);
        DistrictEntity districtEntity1 = districtService.save(districtEnt1);

        //votes
        final PartyEntity p1 = partyRepository.getPartyByNumber(45);
        final PartyEntity p2 = partyRepository.getPartyByNumber(46);
        final PartyEntity spoiled = new PartyEntity();
        spoiled.setId(-1991L);

        ResultMultiEntity res1 = MyUtils.getResultMultiEntity(p1, districtEnt1, 20L);
        ResultMultiEntity res2 = MyUtils.getResultMultiEntity(p2, districtEnt1, 50L);
        ResultMultiEntity res3 = MyUtils.getResultMultiEntity(spoiled, districtEnt1, 10L);

        List<ResultMultiEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);

        final String save = resultMultiService.save(results);
        final String approveResults = resultMultiService.approve(districtEnt1.getId());

        //verify
        assertThat(approveResults, CoreMatchers.is("Votes Approved"));
        assertThat(resultMultiRepository.getResultsByDistrictId(districtEnt1).get(1).isApproved(), CoreMatchers.is(true));

    }

    @Test
    public void negativeData() throws Exception{
        DistrictEntity districtEnt1 = new DistrictEntity();
        districtEnt1.setAdress("Saules 55-23");
        districtEnt1.setName("Lanisteriu");
        districtEnt1.setCounty(countyRepository.findAll().get(0));
        districtEnt1.setNumberOfElectors(800L);
        DistrictEntity districtEntity1 = districtService.save(districtEnt1);

        //votes
        final PartyEntity p1 = partyRepository.getPartyByNumber(45);
        final PartyEntity p2 = partyRepository.getPartyByNumber(46);
        final PartyEntity spoiled = new PartyEntity();
        spoiled.setId(-1991L);

        ResultMultiEntity res1 = MyUtils.getResultMultiEntity(p1, districtEnt1, 20L);
        ResultMultiEntity res2 = MyUtils.getResultMultiEntity(p2, districtEnt1, -50L);
        ResultMultiEntity res3 = MyUtils.getResultMultiEntity(spoiled, districtEnt1, 10L);

        List<ResultMultiEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);

        String save = null;
        if(res1.getVotes() >= 0 & res2.getVotes() >= 0 & res3.getVotes() >= 0){
            save = resultMultiService.save(results);
        }

        //verify
        assertThat(resultMultiRepository.findAll().size(), CoreMatchers.is(0));
    }

    @Test
    public void tooManyVotes() throws Exception{
        DistrictEntity districtEnt1 = new DistrictEntity();
        districtEnt1.setAdress("Saules 55-23");
        districtEnt1.setName("Lanisteriu");
        districtEnt1.setCounty(countyRepository.findAll().get(0));
        districtEnt1.setNumberOfElectors(800L);
        DistrictEntity districtEntity1 = districtService.save(districtEnt1);

        //votes
        final PartyEntity p1 = partyRepository.getPartyByNumber(45);
        final PartyEntity p2 = partyRepository.getPartyByNumber(46);
        final PartyEntity spoiled = new PartyEntity();
        spoiled.setId(-1991L);

        ResultMultiEntity res1 = MyUtils.getResultMultiEntity(p1, districtEnt1, 200L);
        ResultMultiEntity res2 = MyUtils.getResultMultiEntity(p2, districtEnt1, 500L);
        ResultMultiEntity res3 = MyUtils.getResultMultiEntity(spoiled, districtEnt1, 200L);

        List<ResultMultiEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);

        String save = null;
        Long sumOfVotes = res1.getVotes() + res2.getVotes() + res3.getVotes();
        if(sumOfVotes <= districtEnt1.getNumberOfElectors()){
            save = resultMultiService.save(results);
        }

        //verify
        assertThat(resultMultiRepository.findAll().size(), CoreMatchers.is(0));
    }

    @Test
    public void deleteResultsButNotParties() throws Exception {
        DistrictEntity districtEnt1 = new DistrictEntity();
        districtEnt1.setAdress("Saules 55-23");
        districtEnt1.setName("Lanisteriu");
        districtEnt1.setCounty(countyRepository.findAll().get(0));
        districtEnt1.setNumberOfElectors(800L);
        DistrictEntity districtEntity1 = districtService.save(districtEnt1);

        //votes
        final PartyEntity p1 = partyRepository.getPartyByNumber(45);
        final PartyEntity p2 = partyRepository.getPartyByNumber(46);
        final PartyEntity spoiled = new PartyEntity();
        spoiled.setId(-1991L);

        ResultMultiEntity res1 = MyUtils.getResultMultiEntity(p1, districtEnt1, 20L);
        ResultMultiEntity res2 = MyUtils.getResultMultiEntity(p2, districtEnt1, 50L);
        ResultMultiEntity res3 = MyUtils.getResultMultiEntity(spoiled, districtEnt1, 10L);

        List<ResultMultiEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);

        Integer partySizeBefore = partyRepository.findAll().size();
        final String save = resultMultiService.save(results);
        final String delete = resultMultiService.delete(districtEnt1.getId());
        Integer partySizeAfter = partyRepository.findAll().size();

        //verify
        assertThat(delete, CoreMatchers.is("Votes Deleted"));
        assertThat(resultMultiRepository.getResultsByDistrictId(districtEnt1).size(), CoreMatchers.is(0));
        assertThat(partySizeBefore, CoreMatchers.is(partySizeAfter));
    }

    @Test
    public void deleteResultsAddAgain() throws Exception {

        DistrictEntity districtEnt1 = new DistrictEntity();
        districtEnt1.setAdress("Saules 55-23");
        districtEnt1.setName("Lanisteriu");
        districtEnt1.setCounty(countyRepository.findAll().get(0));
        districtEnt1.setNumberOfElectors(800L);
        DistrictEntity districtEntity1 = districtService.save(districtEnt1);

        //votes
        final PartyEntity p1 = partyRepository.getPartyByNumber(45);
        final PartyEntity p2 = partyRepository.getPartyByNumber(46);
        final PartyEntity spoiled = new PartyEntity();
        spoiled.setId(-1991L);

        ResultMultiEntity res1 = MyUtils.getResultMultiEntity(p1, districtEnt1, 20L);
        ResultMultiEntity res2 = MyUtils.getResultMultiEntity(p2, districtEnt1, 50L);
        ResultMultiEntity res3 = MyUtils.getResultMultiEntity(spoiled, districtEnt1, 10L);

        List<ResultMultiEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);

        final String save1 = resultMultiService.save(results);

        //verify
        assertThat(save1, CoreMatchers.is("Votes registered"));
        assertThat(resultMultiRepository.getResultsByDistrictId(districtEnt1).size(), CoreMatchers.is(2));
        final String delete = resultMultiService.delete(districtEnt1.getId());
        assertThat(delete, CoreMatchers.is("Votes Deleted"));
        assertThat(resultMultiRepository.getResultsByDistrictId(districtEnt1).size(), CoreMatchers.is(0));

        ResultMultiEntity res10 = MyUtils.getResultMultiEntity(p1, districtEnt1, 20L);
        ResultMultiEntity res20 = MyUtils.getResultMultiEntity(p2, districtEnt1, 60L);
        ResultMultiEntity res30 = MyUtils.getResultMultiEntity(spoiled, districtEnt1, 90L);

        List<ResultMultiEntity> results2 = new ArrayList<>();
        results2.add(res10);
        results2.add(res20);
        results2.add(res30);

        String save2 = resultMultiService.save(results2);

        assertThat(save2, CoreMatchers.is("Votes registered"));
        assertThat(resultMultiRepository.getResultsByDistrictId(districtEnt1).size(), CoreMatchers.is(2));
        assertThat(resultMultiRepository.getResultsByDistrictId(districtEnt1).get(1).getVotes(), CoreMatchers.is(60L));
    }

    @Test
    public void rating() throws Exception{

        DistrictEntity districtEnt1 = new DistrictEntity();
        districtEnt1.setAdress("Saules 55-23");
        districtEnt1.setName("Lanisteriu");
        districtEnt1.setCounty(countyRepository.findAll().get(0));
        districtEnt1.setNumberOfElectors(800L);
        DistrictEntity districtEntity1 = districtService.save(districtEnt1);

        //votes
        final PartyEntity p1 = partyRepository.getPartyByNumber(45);
        final PartyEntity p2 = partyRepository.getPartyByNumber(46);
        final PartyEntity spoiled = new PartyEntity();
        spoiled.setId(-1991L);

        ResultMultiEntity res1 = MyUtils.getResultMultiEntity(p1, districtEnt1, 20L);
        ResultMultiEntity res2 = MyUtils.getResultMultiEntity(p2, districtEnt1, 50L);
        ResultMultiEntity res3 = MyUtils.getResultMultiEntity(spoiled, districtEnt1, 10L);

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
        results.add(res2);
        results.add(res3);

        final String save = resultMultiService.save(results);

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