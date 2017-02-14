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

        districtRepository.findAll().stream().forEach(d -> districtService.delete(d.getId()));
        countyRepository.findAll().stream().forEach(c -> countyService.delete(c.getId()));

        String vilniusString = MyUtils.getCountyJson(null, "Vilniaus");
        ResponseEntity<CountyEntity> countyEntityResponseEntity = rest.postForEntity("/county", MyUtils.parseStringToJson(vilniusString), CountyEntity.class);

        DistrictEntity districtEnt = new DistrictEntity();
        districtEnt.setAdress("Saules 5-23");
        districtEnt.setName("Tautos");
        districtEnt.setCounty(countyRepository.findAll().get(0));
        districtEnt.setNumberOfElectors(5000L);
        DistrictEntity districtEntity = districtService.save(districtEnt);

//        String SauliusString = MyUtils.getCandidateJson(null, "Saulius", "Domavicius", "2000-12-12", "Adminas", 92, null, null);
//        ResponseEntity<CandidateEntity> respCandidateCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(SauliusString), CandidateEntity.class);
        
    }

    @After
    public void tearDown() throws Exception {

        resultSingleRepository.findAll().stream().forEach(c -> resultSingleService.delete(c.getId()));
        candidateRepository.getCandidatesList().stream().forEach(c -> candidateService.delete(c.getId()));
        districtRepository.findAll().stream().forEach(d -> districtService.delete(d.getId()));
        partyRepository.findAll().stream().forEach(p -> partyService.delete(p.getId()));
        countyRepository.findAll().stream().forEach(c -> countyService.delete(c.getId()));
    }

    @Test
    public void save() throws Exception {
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

        //setup adding candidates
        final MultipartFile file1 = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name1 = "Davatku Partija";
        Integer number1 = 14;
        partyService.save(name1, number1, file1);

        //votes
        final CandidateEntity cand1 = candidateRepository.getCandidatesList().get(0);
        final CandidateEntity cand2 = candidateRepository.getCandidatesList().get(1);
        final CandidateEntity cand3 = candidateRepository.getCandidatesList().get(2);
        final CandidateEntity spoiled = new CandidateEntity();
        spoiled.setId(-1991L);

        ResultSingleEntity res1 = new ResultSingleEntity(cand1, districtEnt1, 200L, new Date());
        ResultSingleEntity res2 = new ResultSingleEntity(cand2, districtEnt1, 50L, new Date());
        ResultSingleEntity res3 = new ResultSingleEntity(cand3, districtEnt1, 100L, new Date());
        ResultSingleEntity res4 = new ResultSingleEntity(spoiled, districtEnt1, 50L, new Date());

        List<ResultSingleEntity> resultList = new ArrayList<>();
        resultList.add(res1);
        resultList.add(res2);
        resultList.add(res3);
        resultList.add(res4);

        String save = resultSingleService.save(resultList);
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
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(3));

    }

    @Ignore
    @Test
    public void approve() throws Exception {

        //setup adding candidates
        MultipartFile result = MyUtils.parseToMultiPart("test-csv/data-county-non-party.csv");
        ResponseEntity<CountyReport[]> responseCountiesList = rest.getForEntity("/county", CountyReport[].class);
        final Long id = responseCountiesList.getBody()[0].getId();
        Boolean executeUpdate = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                countyService.update(id, result);
                return true;
            }
        });
        //setup adding district
        DistrictEntity de = new DistrictEntity();
        de.setAdress("aaaaaaa");
        de.setName("AAAAAAb");
        de.setCounty(countyRepository.findAll().get(0));
        de.setNumberOfElectors(5000L);
        DistrictEntity districtEntity = districtService.save(de);

        //votes
        final CandidateEntity c1 = candidateRepository.getCandidatesList().get(0);
        final CandidateEntity c2 = candidateRepository.getCandidatesList().get(1);
        final CandidateEntity c3 = candidateRepository.getCandidatesList().get(2);
        final CandidateEntity spoiled = new CandidateEntity();
        spoiled.setId(-1991L);

        ResultSingleEntity res1 = new ResultSingleEntity(c1, districtEntity, 20L, new Date());
        ResultSingleEntity res2 = new ResultSingleEntity(c2, districtEntity, 50L, new Date());
        ResultSingleEntity res3 = new ResultSingleEntity(c3, districtEntity, 40L, new Date());
        ResultSingleEntity res4 = new ResultSingleEntity(spoiled, districtEntity, 10L, new Date());

        List<ResultSingleEntity> results = new ArrayList<>();
        results.add(res1);
        results.add(res2);
        results.add(res3);
        results.add(res4);

        final String save = resultSingleService.save(results);
        final String approveResults = resultSingleService.approve(districtEntity.getId());
        boolean approved = resultSingleRepository.findAll().get(0).isApproved();
        //verify
        assertThat(approveResults, CoreMatchers.is("Results approved"));
        assertThat(approved, CoreMatchers.is(true));

    }


    @Test
    public void deleteResultsButNotCandidates() throws Exception {
        districtRepository.findAll().stream().forEach(d -> districtService.delete(d.getId()));
        //setup adding county and district
        String vilniusString = MyUtils.getCountyJson(null, "Vilniaus");
        ResponseEntity<CountyEntity> countyEntityResponseEntity = rest.postForEntity("/county", MyUtils.parseStringToJson(vilniusString), CountyEntity.class);
        Long countyId = countyEntityResponseEntity.getBody().getId();

        DistrictEntity districtEnt1 = new DistrictEntity();
        districtEnt1.setAdress("Sales 55-23");
        districtEnt1.setName("Darbo");
        districtEnt1.setCounty(countyRepository.findAll().get(0));
        districtEnt1.setNumberOfElectors(800L);
        DistrictEntity districtEntity1 = districtService.save(districtEnt1);

        //setup adding candidates
        final MultipartFile file1 = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name1 = "Lavonu Partija";
        Integer number1 = 150;
        partyService.save(name1, number1, file1);

        //votes
        final CandidateEntity cand1 = candidateRepository.getCandidatesList().get(0);
        final CandidateEntity cand2 = candidateRepository.getCandidatesList().get(1);
        final CandidateEntity cand3 = candidateRepository.getCandidatesList().get(2);
        final CandidateEntity spoiled = new CandidateEntity();
        spoiled.setId(-1991L);

        ResultSingleEntity res1 = new ResultSingleEntity(cand1, districtEnt1, 200L, new Date());
        ResultSingleEntity res2 = new ResultSingleEntity(cand2, districtEnt1, 50L, new Date());
        ResultSingleEntity res3 = new ResultSingleEntity(cand3, districtEnt1, 100L, new Date());
        ResultSingleEntity res4 = new ResultSingleEntity(spoiled, districtEnt1, 50L, new Date());

        List<ResultSingleEntity> resultList = new ArrayList<>();
        resultList.add(res1);
        resultList.add(res2);
        resultList.add(res3);
        resultList.add(res4);

        String save = resultSingleService.save(resultList);

        //verify
        assertThat(save, CoreMatchers.is("Votes registered"));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(3));

        resultSingleRepository.findAll().stream().forEach(c -> resultSingleService.delete(c.getId()));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(0));
        assertThat(candidateRepository.getCandidatesList().size(), CoreMatchers.is(3));
    }

    @Test
    public void deleteResultsAddAgain() throws Exception {

        //setup adding county and district
        String vilniusString = MyUtils.getCountyJson(null, "Vilniaus");
        ResponseEntity<CountyEntity> countyEntityResponseEntity = rest.postForEntity("/county", MyUtils.parseStringToJson(vilniusString), CountyEntity.class);
        Long countyId = countyEntityResponseEntity.getBody().getId();

        DistrictEntity districtEnt1 = new DistrictEntity();
        districtEnt1.setAdress("Ssdfules 55-23");
        districtEnt1.setName("Namu");
        districtEnt1.setCounty(countyRepository.findAll().get(0));
        districtEnt1.setNumberOfElectors(900L);
        DistrictEntity districtEntity1 = districtService.save(districtEnt1);

        //setup adding candidates
        final MultipartFile file1 = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name1 = "Kalnu Partija";
        Integer number1 = 10;
        partyService.save(name1, number1, file1);

        //votes
        final CandidateEntity cand1 = candidateRepository.getCandidatesList().get(0);
        final CandidateEntity cand2 = candidateRepository.getCandidatesList().get(1);
        final CandidateEntity cand3 = candidateRepository.getCandidatesList().get(2);
        final CandidateEntity spoiled = new CandidateEntity();
        spoiled.setId(-1991L);

        ResultSingleEntity res1 = new ResultSingleEntity(cand1, districtEnt1, 200L, new Date());
        ResultSingleEntity res2 = new ResultSingleEntity(cand2, districtEnt1, 50L, new Date());
        ResultSingleEntity res3 = new ResultSingleEntity(cand3, districtEnt1, 100L, new Date());
        ResultSingleEntity res4 = new ResultSingleEntity(spoiled, districtEnt1, 50L, new Date());

        List<ResultSingleEntity> resultList = new ArrayList<>();
        resultList.add(res1);
        resultList.add(res2);
        resultList.add(res3);
        resultList.add(res4);

        String save1 = resultSingleService.save(resultList);

        //verify
        assertThat(save1, CoreMatchers.is("Votes registered"));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(3));
        resultSingleRepository.findAll().stream().forEach(c -> resultSingleService.delete(c.getId()));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(0));

        ResultSingleEntity res10 = new ResultSingleEntity(cand1, districtEnt1, 200L, new Date());
        ResultSingleEntity res20 = new ResultSingleEntity(cand2, districtEnt1, 500L, new Date());
        ResultSingleEntity res30 = new ResultSingleEntity(cand3, districtEnt1, 350L, new Date());
        ResultSingleEntity res40 = new ResultSingleEntity(spoiled, districtEnt1, 100L, new Date());

        List<ResultSingleEntity> results2 = new ArrayList<>();
        results2.add(res10);
        results2.add(res20);
        results2.add(res30);
        results2.add(res40);

        String save2 = resultSingleService.save(results2);

        assertThat(save2, CoreMatchers.is("Votes registered"));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(3));

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