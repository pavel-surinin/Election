//package lt.itakademija.electors.results.multi;
//
//import lt.itakademija.Application;
//import lt.itakademija.electors.MyUtils;
//import lt.itakademija.electors.candidate.CandidateControllerTest;
//import lt.itakademija.electors.candidate.CandidateEntity;
//import lt.itakademija.electors.candidate.CandidateRepository;
//import lt.itakademija.electors.candidate.CandidateService;
//import lt.itakademija.electors.county.CountyReport;
//import lt.itakademija.electors.county.CountyRepository;
//import lt.itakademija.electors.county.CountyService;
//import lt.itakademija.electors.district.DistrictEntity;
//import lt.itakademija.electors.district.DistrictReport;
//import lt.itakademija.electors.district.DistrictRepository;
//import lt.itakademija.electors.district.DistrictService;
//import lt.itakademija.electors.party.PartyEntity;
//import lt.itakademija.electors.party.PartyRepository;
//import lt.itakademija.electors.party.PartyService;
//import lt.itakademija.electors.results.single.ResultSingleEntity;
//import lt.itakademija.electors.results.single.ResultSingleRepository;
//import lt.itakademija.electors.results.single.ResultSingleService;
//import lt.itakademija.storage.CSVParser;
//import org.hamcrest.CoreMatchers;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.support.TransactionTemplate;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
///**
// * Created by Gabriele on 2017-02-09.
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(
//        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
//        classes = {ResultMultiControllerTest.Config.class, Application.class})
//public class ResultMultiControllerTest {
//
//    @Autowired
//    CSVParser csvParser;
//
//    @Autowired
//    TestRestTemplate rest;
//
//    @Autowired
//    CountyRepository countyRepository;
//
//    @Autowired
//    CountyService countyService;
//
//    @Autowired
//    DistrictRepository districtRepository;
//
//    @Autowired
//    DistrictService districtService;
//
//    @Autowired
//    PartyService partyService;
//
//    @Autowired
//    PartyRepository partyRepository;
//
//    @Autowired
//    ResultMultiRepository resultMultiRepository;
//
//    @Autowired
//    ResultMultiService resultMultiService;
//
//    @Autowired
//    TransactionTemplate transactionTemplate;
//
//    String URI = "/result/multi";
//
//    @Before
//    public void setUp() throws Exception {
//
////        final MultipartFile file1 = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
////        String name1 = "Balvanu Partija";
////        Integer number1 = 45;
////        partyService.save(name1, number1, file1);
////
////        final MultipartFile file2 = MyUtils.parseToMultiPart("test-csv/data-party-2.csv");
////        String name2 = "Ereliu Partija";
////        Integer number2 = 46;
////        partyService.save(name2, number2, file2);
//
//    }
//
//    @After
//    public void tearDown() throws Exception {
//
////        partyRepository.findAll().stream().forEach(p -> partyService.delete(p.getId()));
//    }
//
//    @Test
//    public void save() throws Exception {
//        //setup adding parties
//        final MultipartFile file1 = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
//        String name1 = "Balvanu Partija";
//        Integer number1 = 45;
//        partyService.save(name1, number1, file1);
//        Long partyId = partyRepository.getPartyByNumber(45).getId();
//
//        final MultipartFile file2 = MyUtils.parseToMultiPart("test-csv/data-party-2.csv");
//        String name2 = "Ereliu Partija";
//        Integer number2 = 46;
//        partyService.save(name2, number2, file2);
//
//        final MultipartFile file3 = MyUtils.parseToMultiPart("test-csv/data-party-3.csv");
//        String name3 = "Sugadinti";
//        Integer number3 = 47;
//        partyService.save(name3, number3, file3);
//
//        //setup adding district
//        final Long countyId = countyRepository.findAll().get(0).getId();
//        String jsonDistrictCreate = "{\"name\" : \"Panerių\",\"adress\" : \"Ūmėdžių g. 9\",\"numberOfElectors\":500,\"county\":{\"id\":" + countyId + "}}";
//        ResponseEntity<DistrictReport> respCreateDistrict;
//        respCreateDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(jsonDistrictCreate), DistrictReport.class);
//        Long districtId = respCreateDistrict.getBody().getId();
//
//        //votes
////        final DistrictEntity d1 = districtRepository.findAll().get(0);
////
////        final PartyEntity p1 = partyRepository.getPartyByNumber(45);
////        final PartyEntity p2 = partyRepository.getPartyByNumber(46);
//        final PartyEntity spoiled = partyRepository.getPartyByNumber(47);
//        spoiled.setId(-1991L);
//
//
//
//        String jsonMultiResultCreate = "{\"party\" : {\"id\":" + partyId + "}, \"district\" : {\"id\" :" + districtId + "}, \"votes\":\" 500L \", \"datePublished\":\"new Date()\"}";
//        ResponseEntity<CandidateEntity> respMultiResultCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(jsonMultiResultCreate), ResultMultiEntity.class);
//
//        String jsonSpoiledResultCreate = "{\"party\" : {\"id\":" + spoiled + "}, \"district\" : {\"id\" :" + districtId + "}, \"votes\":\" 9L \", \"datePublished\":\"new Date()\"}";
//        ResponseEntity<CandidateEntity> jsonSpoiledResultCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(jsonMultiResultCreate), CandidateEntity.class);
////        ResultMultiEntity res1 = new ResultMultiEntity(p1, d1, 200L, new Date());
////        ResultMultiEntity res2 = new ResultMultiEntity(p2, d1, 250L, new Date());
////        ResultMultiEntity res3 = new ResultMultiEntity(spoiled, d1, 20L, new Date());
////
////        List<ResultMultiEntity> r1 = new ArrayList<>();
////        r1.add(res1);
////        r1.add(res2);
////        r1.add(res3);
//
//        final String save = resultMultiService.save(r1);
//
//        //verify
//        assertThat(save, CoreMatchers.is("Votes registered"));
//        assertThat(districtService.getDistrictMultiRegistered().size(), CoreMatchers.is(3));
//    }
//
//    @Test
//    public void approve() throws Exception {
//
//    }
//
//    @Test
//    public void delete() throws Exception {
//
//    }
//
//    @TestConfiguration
//    static class Config{
//        @Bean
//        @Primary
//        public ResultMultiService service() {
//            return new ResultMultiService();
//        }
//    }
//}