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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
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

        districtRepository.findAll().stream().forEach(c -> resultSingleService.delete(c.getId()));
        candidateRepository.getCandidatesList().stream().forEach(c -> candidateService.delete(c.getId()));
        districtRepository.findAll().stream().forEach(d -> districtService.delete(d.getId()));
        partyRepository.findAll().stream().forEach(p -> partyService.delete(p.getId()));
        countyRepository.findAll().stream().forEach(c -> countyService.delete(c.getId()));

        String vilniusString = MyUtils.getCountyJson(null, "Vilniaus");
        ResponseEntity<CountyEntity> countyEntityResponseEntity = rest.postForEntity("/county", MyUtils.parseStringToJson(vilniusString), CountyEntity.class);
        Long countyId = countyEntityResponseEntity.getBody().getId();

        DistrictEntity districtEnt = new DistrictEntity();
        districtEnt.setAdress("Saules 5-23");
        districtEnt.setName("Tautos");
        districtEnt.setCounty(countyRepository.findAll().get(0));
        districtEnt.setNumberOfElectors(5000L);
        DistrictEntity districtEntity = districtService.save(districtEnt);

        final MultipartFile file1 = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name1 = "Jonu Partija";
        Integer number1 = 45;
        partyService.save(name1, number1, file1);
        Long partyId = partyRepository.getPartyByNumber(45).getId();

    }

    @After
    public void tearDown() throws Exception {

        districtRepository.findAll().stream().forEach(c -> resultSingleService.delete(c.getId()));
        candidateRepository.getCandidatesList().stream().forEach(c -> candidateService.delete(c.getId()));
        districtRepository.findAll().stream().forEach(d -> districtService.delete(d.getId()));
        partyRepository.findAll().stream().forEach(p -> partyService.delete(p.getId()));
        countyRepository.findAll().stream().forEach(c -> countyService.delete(c.getId()));
    }

    @Test
    public void save() throws Exception {

        DistrictEntity districtEnt1 = new DistrictEntity();
        districtEnt1.setAdress("Winterfelas 5-23");
        districtEnt1.setName("Starku");
        districtEnt1.setCounty(countyRepository.findAll().get(0));
        districtEnt1.setNumberOfElectors(4900L);
        DistrictEntity districtEntity = districtService.save(districtEnt1);

        CandidateEntity candSauliusEnt = new CandidateEntity();
        candSauliusEnt.setName("Saulius");
        candSauliusEnt.setSurname("Domavicius");
        candSauliusEnt.setBirthDate(new Date());
        candSauliusEnt.setCounty(countyRepository.findAll().get(0));
        CandidateEntity candidateEntity1 = candidateService.save(candSauliusEnt);

        CandidateEntity candPauliusEnt = new CandidateEntity();
        candPauliusEnt.setName("Paulius");
        candPauliusEnt.setSurname("Tomkus");
        candPauliusEnt.setBirthDate(new Date());
        candPauliusEnt.setCounty(countyRepository.findAll().get(0));
        candPauliusEnt.setPartyDependencies(partyRepository.getPartyByNumber(45));
        candPauliusEnt.setNumberInParty(5);
        CandidateEntity candidateEntity2 = candidateService.save(candPauliusEnt);

        //votes
        final CandidateEntity spoiled = new CandidateEntity();
        spoiled.setId(-1991L);

        ResultSingleEntity res1 = new ResultSingleEntity(candSauliusEnt, districtEnt1, 200L, new Date());
        ResultSingleEntity res2 = new ResultSingleEntity(candPauliusEnt, districtEnt1, 50L, new Date());
        ResultSingleEntity res3 = new ResultSingleEntity(spoiled, districtEnt1, 100L, new Date());

        List<ResultSingleEntity> resultList = new ArrayList<>();
        resultList.add(res1);
        resultList.add(res2);
        resultList.add(res3);

        final String save = resultSingleService.save(resultList);
//        try{
//            Long sum = res1.getVotes()+res2.getVotes()+res3.getVotes();
//            if(sum <= districtEntity1.getNumberOfElectors()) {
//                save = resultSingleService.save(resultList);
//            }else{
//                throw new TooManyVotersException("There are more votes than voters in the district");
//            }
//        }catch (TooManyVotersException e){
//        }

        //verify
        assertThat(save, CoreMatchers.is("Votes registered"));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(2));

    }

    @Test
    public void negativeData() throws Exception{
        DistrictEntity districtEnt1 = new DistrictEntity();
        districtEnt1.setAdress("Winterfelas 5-23");
        districtEnt1.setName("Starku");
        districtEnt1.setCounty(countyRepository.findAll().get(0));
        districtEnt1.setNumberOfElectors(4900L);
        DistrictEntity districtEntity = districtService.save(districtEnt1);

        CandidateEntity candSauliusEnt = new CandidateEntity();
        candSauliusEnt.setName("Saulius");
        candSauliusEnt.setSurname("Domavicius");
        candSauliusEnt.setBirthDate(new Date());
        candSauliusEnt.setCounty(countyRepository.findAll().get(0));
        CandidateEntity candidateEntity1 = candidateService.save(candSauliusEnt);

        CandidateEntity candPauliusEnt = new CandidateEntity();
        candPauliusEnt.setName("Paulius");
        candPauliusEnt.setSurname("Tomkus");
        candPauliusEnt.setBirthDate(new Date());
        candPauliusEnt.setCounty(countyRepository.findAll().get(0));
        candPauliusEnt.setPartyDependencies(partyRepository.getPartyByNumber(45));
        candPauliusEnt.setNumberInParty(5);
        CandidateEntity candidateEntity2 = candidateService.save(candPauliusEnt);

        //votes
        final CandidateEntity spoiled = new CandidateEntity();
        spoiled.setId(-1991L);

        ResultSingleEntity res1 = new ResultSingleEntity(candSauliusEnt, districtEnt1, 200L, new Date());
        ResultSingleEntity res2 = new ResultSingleEntity(candPauliusEnt, districtEnt1, -50L, new Date());
        ResultSingleEntity res3 = new ResultSingleEntity(spoiled, districtEnt1, 100L, new Date());

        List<ResultSingleEntity> resultList = new ArrayList<>();
        resultList.add(res1);
        resultList.add(res2);
        resultList.add(res3);

        String save = null;
        if(res1.getVotes() >= 0 & res2.getVotes() >= 0 & res3.getVotes() >= 0){
            save = resultSingleService.save(resultList);
        }

        //verify
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(0));
    }

    @Test
    public void tooManyVotes() throws Exception{
        DistrictEntity districtEnt1 = new DistrictEntity();
        districtEnt1.setAdress("Winterfelas 5-23");
        districtEnt1.setName("Starku");
        districtEnt1.setCounty(countyRepository.findAll().get(0));
        districtEnt1.setNumberOfElectors(490L);
        DistrictEntity districtEntity = districtService.save(districtEnt1);

        CandidateEntity candSauliusEnt = new CandidateEntity();
        candSauliusEnt.setName("Saulius");
        candSauliusEnt.setSurname("Domavicius");
        candSauliusEnt.setBirthDate(new Date());
        candSauliusEnt.setCounty(countyRepository.findAll().get(0));
        CandidateEntity candidateEntity1 = candidateService.save(candSauliusEnt);

        CandidateEntity candPauliusEnt = new CandidateEntity();
        candPauliusEnt.setName("Paulius");
        candPauliusEnt.setSurname("Tomkus");
        candPauliusEnt.setBirthDate(new Date());
        candPauliusEnt.setCounty(countyRepository.findAll().get(0));
        candPauliusEnt.setPartyDependencies(partyRepository.getPartyByNumber(45));
        candPauliusEnt.setNumberInParty(5);
        CandidateEntity candidateEntity2 = candidateService.save(candPauliusEnt);

        //votes
        final CandidateEntity spoiled = new CandidateEntity();
        spoiled.setId(-1991L);

        ResultSingleEntity res1 = new ResultSingleEntity(candSauliusEnt, districtEnt1, 200L, new Date());
        ResultSingleEntity res2 = new ResultSingleEntity(candPauliusEnt, districtEnt1, 500L, new Date());
        ResultSingleEntity res3 = new ResultSingleEntity(spoiled, districtEnt1, 100L, new Date());

        List<ResultSingleEntity> resultList = new ArrayList<>();
        resultList.add(res1);
        resultList.add(res2);
        resultList.add(res3);

        String save = null;
        Long sumOfVotes = res1.getVotes() + res2.getVotes() + res3.getVotes();
        if(sumOfVotes <= districtEnt1.getNumberOfElectors()){
            save = resultSingleService.save(resultList);
        }

        //verify
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(0));
    }

    @Test
    public void approve() throws Exception {

        DistrictEntity districtEnt1 = new DistrictEntity();
        districtEnt1.setAdress("Winterfelas 5-23");
        districtEnt1.setName("Starku");
        districtEnt1.setCounty(countyRepository.findAll().get(0));
        districtEnt1.setNumberOfElectors(4900L);
        DistrictEntity districtEntity = districtService.save(districtEnt1);

        CandidateEntity candSauliusEnt = new CandidateEntity();
        candSauliusEnt.setName("Saulius");
        candSauliusEnt.setSurname("Domavicius");
        candSauliusEnt.setBirthDate(new Date());
        candSauliusEnt.setCounty(countyRepository.findAll().get(0));
        CandidateEntity candidateEntity1 = candidateService.save(candSauliusEnt);

        CandidateEntity candPauliusEnt = new CandidateEntity();
        candPauliusEnt.setName("Paulius");
        candPauliusEnt.setSurname("Tomkus");
        candPauliusEnt.setBirthDate(new Date());
        candPauliusEnt.setCounty(countyRepository.findAll().get(0));
        candPauliusEnt.setPartyDependencies(partyRepository.getPartyByNumber(45));
        candPauliusEnt.setNumberInParty(5);
        CandidateEntity candidateEntity2 = candidateService.save(candPauliusEnt);

        //votes
        final CandidateEntity spoiled = new CandidateEntity();
        spoiled.setId(-1991L);

        ResultSingleEntity res1 = new ResultSingleEntity(candSauliusEnt, districtEnt1, 200L, new Date());
        ResultSingleEntity res2 = new ResultSingleEntity(candPauliusEnt, districtEnt1, 50L, new Date());
        ResultSingleEntity res3 = new ResultSingleEntity(spoiled, districtEnt1, 100L, new Date());

        List<ResultSingleEntity> resultList = new ArrayList<>();
        resultList.add(res1);
        resultList.add(res2);
        resultList.add(res3);

        final String save = resultSingleService.save(resultList);
        final String approveResults = resultSingleService.approve(districtEntity.getId());

        //verify
        assertThat(approveResults, CoreMatchers.is("Results approved"));
        assertThat(resultSingleRepository.getResultsByDistrictId(districtEnt1).get(1).isApproved(), CoreMatchers.is(true));
    }

    @Test
    public void deleteResultsButNotCandidates() throws Exception {

        DistrictEntity districtEnt1 = new DistrictEntity();
        districtEnt1.setAdress("Winterfelas 5-23");
        districtEnt1.setName("Starku");
        districtEnt1.setCounty(countyRepository.findAll().get(0));
        districtEnt1.setNumberOfElectors(4900L);
        DistrictEntity districtEntity = districtService.save(districtEnt1);

        CandidateEntity candSauliusEnt = new CandidateEntity();
        candSauliusEnt.setName("Saulius");
        candSauliusEnt.setSurname("Domavicius");
        candSauliusEnt.setBirthDate(new Date());
        candSauliusEnt.setCounty(countyRepository.findAll().get(0));
        CandidateEntity candidateEntity1 = candidateService.save(candSauliusEnt);

        CandidateEntity candPauliusEnt = new CandidateEntity();
        candPauliusEnt.setName("Paulius");
        candPauliusEnt.setSurname("Tomkus");
        candPauliusEnt.setBirthDate(new Date());
        candPauliusEnt.setCounty(countyRepository.findAll().get(0));
        candPauliusEnt.setPartyDependencies(partyRepository.getPartyByNumber(45));
        candPauliusEnt.setNumberInParty(5);
        CandidateEntity candidateEntity2 = candidateService.save(candPauliusEnt);

        //votes
        final CandidateEntity spoiled = new CandidateEntity();
        spoiled.setId(-1991L);

        ResultSingleEntity res1 = new ResultSingleEntity(candSauliusEnt, districtEnt1, 200L, new Date());
        ResultSingleEntity res2 = new ResultSingleEntity(candPauliusEnt, districtEnt1, 50L, new Date());
        ResultSingleEntity res3 = new ResultSingleEntity(spoiled, districtEnt1, 100L, new Date());

        List<ResultSingleEntity> resultList = new ArrayList<>();
        resultList.add(res1);
        resultList.add(res2);
        resultList.add(res3);

        Long candidateSizeBefore = candidateRepository.getCandidatesCount();
        final String save = resultSingleService.save(resultList);

        //verify
        assertThat(save, CoreMatchers.is("Votes registered"));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(2));

        final String delete = resultSingleService.delete(districtEnt1.getId());
        Long candidateSizeAfter = candidateRepository.getCandidatesCount();
        assertThat(delete, CoreMatchers.is("Results Deleted"));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(0));
        assertThat(candidateSizeAfter, CoreMatchers.is(candidateSizeBefore));
    }

    @Test
    public void deleteResultsAddAgain() throws Exception {

        DistrictEntity districtEnt1 = new DistrictEntity();
        districtEnt1.setAdress("Winterfelas 5-23");
        districtEnt1.setName("Starku");
        districtEnt1.setCounty(countyRepository.findAll().get(0));
        districtEnt1.setNumberOfElectors(4900L);
        DistrictEntity districtEntity = districtService.save(districtEnt1);

        CandidateEntity candSauliusEnt = new CandidateEntity();
        candSauliusEnt.setName("Saulius");
        candSauliusEnt.setSurname("Domavicius");
        candSauliusEnt.setBirthDate(new Date());
        candSauliusEnt.setCounty(countyRepository.findAll().get(0));
        CandidateEntity candidateEntity1 = candidateService.save(candSauliusEnt);

        CandidateEntity candPauliusEnt = new CandidateEntity();
        candPauliusEnt.setName("Paulius");
        candPauliusEnt.setSurname("Tomkus");
        candPauliusEnt.setBirthDate(new Date());
        candPauliusEnt.setCounty(countyRepository.findAll().get(0));
        candPauliusEnt.setPartyDependencies(partyRepository.getPartyByNumber(45));
        candPauliusEnt.setNumberInParty(5);
        CandidateEntity candidateEntity2 = candidateService.save(candPauliusEnt);

        //votes
        final CandidateEntity spoiled = new CandidateEntity();
        spoiled.setId(-1991L);

        ResultSingleEntity res1 = new ResultSingleEntity(candSauliusEnt, districtEnt1, 200L, new Date());
        ResultSingleEntity res2 = new ResultSingleEntity(candPauliusEnt, districtEnt1, 50L, new Date());
        ResultSingleEntity res3 = new ResultSingleEntity(spoiled, districtEnt1, 100L, new Date());

        List<ResultSingleEntity> resultList = new ArrayList<>();
        resultList.add(res1);
        resultList.add(res2);
        resultList.add(res3);

        final String save1 = resultSingleService.save(resultList);

        //verify
        assertThat(save1, CoreMatchers.is("Votes registered"));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(2));
        final String delete = resultSingleService.delete(districtEnt1.getId());
        assertThat(delete, CoreMatchers.is("Results Deleted"));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(0));

        ResultSingleEntity res10 = new ResultSingleEntity(candSauliusEnt, districtEnt1, 200L, new Date());
        ResultSingleEntity res20 = new ResultSingleEntity(candPauliusEnt, districtEnt1, 500L, new Date());
        ResultSingleEntity res30 = new ResultSingleEntity(spoiled, districtEnt1, 100L, new Date());

        List<ResultSingleEntity> results2 = new ArrayList<>();
        results2.add(res10);
        results2.add(res20);
        results2.add(res30);

        String save2 = resultSingleService.save(results2);

        assertThat(save2, CoreMatchers.is("Votes registered"));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(2));
        assertThat(resultSingleRepository.getResultsByDistrictId(districtEnt1).get(1).getVotes(), CoreMatchers.is(500L));
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