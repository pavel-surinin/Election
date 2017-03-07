package lt.itakademija.electors.county;


import lt.itakademija.Application;
import lt.itakademija.electors.MyUtils;
import lt.itakademija.electors.candidate.*;
import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.district.DistrictReport;
import lt.itakademija.electors.district.DistrictRepository;
import lt.itakademija.electors.district.DistrictService;
import lt.itakademija.electors.party.PartyEntity;
import lt.itakademija.electors.party.PartyReport;
import lt.itakademija.electors.party.PartyRepository;
import lt.itakademija.electors.party.PartyService;
import lt.itakademija.electors.results.multi.ResultMultiEntity;
import lt.itakademija.electors.results.multi.ResultMultiRepository;
import lt.itakademija.electors.results.multi.ResultMultiService;
import lt.itakademija.electors.results.single.ResultSingleEntity;
import lt.itakademija.electors.results.single.ResultSingleRepository;
import lt.itakademija.electors.results.single.ResultSingleService;
import lt.itakademija.exceptions.BadCSVFileException;
import lt.itakademija.exceptions.CountyCandidatesAlreadyExistException;
import lt.itakademija.exceptions.NotEqualColumnsCountCsv;
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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Pavel on 2017-02-03.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {CountyControllerTest.Config.class, Application.class})
public class CountyControllerTest {

    @Autowired
    CSVParser csvParser;

    @Autowired
    TestRestTemplate rest;

    @Autowired
    ResultSingleService resultSingleService;

    @Autowired
    ResultSingleRepository resultSingleRepository;

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    CandidateService candidateService;

    @Autowired
    CountyRepository countyRepository;

    @Autowired
    CountyService countyService;

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    PartyService partyService;

    @Autowired
    DistrictService districtService;

    @Autowired
    CountyDetailsReport countyDetailsReport;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    PartyRepository partyRepository;

    @Autowired
    ResultMultiService resultMultiService;

    @Autowired
    ResultMultiRepository resultMultiRepository;

    String URI = "/county";


    @Before
    public void setUp() throws Exception {
        String VilniusString = "{\"name\":\"VilniusTest\"}";
        rest.postForEntity(URI, MyUtils.parseStringToJson(VilniusString), CountyEntity.class);
        String KaunasString = "{\"name\":\"KaunasTest\"}";
        rest.postForEntity(URI, MyUtils.parseStringToJson(KaunasString), CountyEntity.class);
        String KlaipedaString = "{\"name\":\"KlaipedaTest\"}";
        rest.postForEntity(URI, MyUtils.parseStringToJson(KlaipedaString), CountyEntity.class);
    }

    @After
    public void tearDown() throws Exception {
        countyRepository.findAll().stream().forEach(c -> countyService.delete(c.getId()));
        candidateRepository.getCandidatesList().stream().forEach(c -> candidateService.delete(c.getId()));
        resultSingleRepository.findAll().stream().forEach(sResults -> resultSingleService.delete(sResults.getId()));
        resultMultiRepository.findAll().stream().forEach(mResults -> resultMultiService.delete(mResults.getId()));
        partyRepository.findAll().stream().forEach(p -> partyService.delete(p.getId()));
    }

    @Test
    public void getCountyList() throws Exception {
        //setup
        ResponseEntity<CountyReport[]> resp = rest.getForEntity("/county", CountyReport[].class);
        //verify
        assertThat(resp.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(resp.getBody().length, CoreMatchers.is(countyRepository.findAll().size()));
    }

    @Test
    public void save() throws Exception {
        //setup
        String VilniusString = "{\"name\":\"KedainiaiTest\"}";
        ResponseEntity<CountyEntity> respCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(VilniusString), CountyEntity.class);
        //verify
        assertThat(respCreate.getStatusCodeValue(), CoreMatchers.is(200));
    }

    @Test
    public void saveCountyWithSameName() throws Exception {
        //setup
        String VilniusString = MyUtils.getCountyJson(null, "VilniusTest");
        String KaunasString = MyUtils.getCountyJson(null, "VilniusTest");
        ResponseEntity<CountyEntity> respVilniusCountyCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(VilniusString), CountyEntity.class);
        ResponseEntity<CountyEntity> respKaunasCountyCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(KaunasString), CountyEntity.class);
        //verify
        assertThat(respKaunasCountyCreate.getStatusCodeValue(), CoreMatchers.is(500));
    }

    @Test
    public void updateCountyName() throws Exception {
        //setup
        String kedainiaiString = "{\"name\":\"KedainiaiTest\"}";
        ResponseEntity<CountyEntity> respCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(kedainiaiString), CountyEntity.class);
        //exercise
        Long id = respCreate.getBody().getId();
        String kedainiaiUpdateString = "{\"id\":" + id + ", \"name\" : \"KedainiaiUpdate\"}";
        ResponseEntity<CountyEntity> respUpdate = rest.postForEntity(URI, MyUtils.parseStringToJson(kedainiaiUpdateString), CountyEntity.class);
        //verify
        assertThat(respCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respUpdate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respUpdate.getBody().getName(), CoreMatchers.is("KedainiaiUpdate"));
    }

    @Test
    public void uploadSingleCandidates() throws Exception {
        //setup

        MultipartFile result = MyUtils.parseToMultiPart("test-csv/data-county-non-party.csv");
        //execute
        ResponseEntity<CountyReport[]> resp1 = rest.getForEntity("/county", CountyReport[].class);
        final Long id = resp1.getBody()[0].getId();
        Boolean executeUpdate = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                countyService.update(id, result);
                return true;
            }
        });
        ResponseEntity<CountyReport[]> resp = rest.getForEntity("/county", CountyReport[].class);
        //verify
        assertThat(resp1.getBody()[0].getCandidatesCount(), CoreMatchers.is(0));
        assertThat(resp.getBody()[0].getCandidatesCount(), CoreMatchers.is(3));
    }

    @Test
    public void uploadSingleCandidates_NoSingleCandidatesExists() throws Exception {
        //setup
        final MultipartFile partyfile1 = MyUtils.parseToMultiPart("test-csv/Good__Skaidruoliu_Party_candidate_data.csv");
        String partyName1 = "Darbo";
        Integer partyNumber1 = 1;
        partyService.save(partyName1, partyNumber1, partyfile1);

        final MultipartFile partyfile2 = MyUtils.parseToMultiPart("test-csv/Good_Darbo_Party_candidate_data.csv");
        String partyName2 = "Skaidruolių";
        Integer partyNumber2 = 2;
        partyService.save(partyName2, partyNumber2, partyfile2);
        FileInputStream fis = new FileInputStream(new File("test-csv/Good_County_Darbo_Skaidruoliu_Party_candidate_data.csv"));
        List<CandidateEntity> candidateEntityList = csvParser.extractCandidates(fis);
        //execute
        MultipartFile result = MyUtils.parseToMultiPart("test-csv/Good_County_Darbo_Skaidruoliu_Party_candidate_data.csv");
        ResponseEntity<CountyReport[]> respCountyReport = rest.getForEntity("/county", CountyReport[].class);
        final Long id = respCountyReport.getBody()[0].getId();
        Boolean executeUpdate = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                countyService.update(id, result);
                return true;
            }
        });
        ResponseEntity<CountyReport[]> respCountyReportafterUpdate = rest.getForEntity("/county", CountyReport[].class);
        //verify
        assertThat(respCountyReport.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respCountyReport.getBody()[0].getCandidatesCount(), CoreMatchers.is(0));
        assertThat(respCountyReportafterUpdate.getBody()[0].getCandidatesCount(), CoreMatchers.is(candidateEntityList.size()));
    }

    @Test
    public void uploadSingleCandidates_CandidatesAlreadyExistsInCounty_UploadingCandidatesWhichWasNotInList() throws Exception {
        //setup
        partyRepository.findAll().stream().forEach(p -> partyService.delete(p.getId()));
        candidateRepository.getCandidatesList().stream().forEach(c -> candidateService.delete(c.getId()));

        final MultipartFile partyfile1 = MyUtils.parseToMultiPart("test-csv/Good__Skaidruoliu_Party_candidate_data.csv");
        String partyName1 = "Darbo";
        Integer partyNumber1 = 1;
        partyService.save(partyName1, partyNumber1, partyfile1);

        final MultipartFile partyfile2 = MyUtils.parseToMultiPart("test-csv/Good_Darbo_Party_candidate_data.csv");
        String partyName2 = "Skaidruolių";
        Integer partyNumber2 = 2;
        partyService.save(partyName2, partyNumber2, partyfile2);

        FileInputStream fis = new FileInputStream(new File("test-csv/Good_County_NoParty_And_Party_candidate_data.csv"));
        List<CandidateEntity> candidateEntityList = csvParser.extractCandidates(fis);

        MultipartFile result = MyUtils.parseToMultiPart("test-csv/Good_County_NoParty_And_Party_candidate_data.csv");
        ResponseEntity<CountyReport[]> respCountyReport = rest.getForEntity("/county", CountyReport[].class);
        final Long id = respCountyReport.getBody()[0].getId();
        Boolean executeUpdate = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                countyService.update(id, result);
                return true;
            }
        });
        //execute
        MultipartFile resultUpdate = MyUtils.parseToMultiPart("test-csv/Good_County_Darbo_Skaidruoliu_Party_candidate_data.csv");
        String countyName = countyRepository.findById(id).getName();
        ResponseEntity<CountyReport[]> respCountyAfterUpdateReport = rest.getForEntity("/county", CountyReport[].class);

        Integer execute = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                int numberOfCandidatesInCounty = countyRepository.findById(id).getCandidates().size();
                return numberOfCandidatesInCounty;
            }
        });
        //verify
        assertThat(execute, CoreMatchers.is(candidateEntityList.size()));
        try {
            Boolean executeAfterUpdate = transactionTemplate.execute(new TransactionCallback<Boolean>() {
                @Override
                public Boolean doInTransaction(TransactionStatus transactionStatus) {
                    countyService.update(id, resultUpdate);
                    return true;
                }
            });
        } catch (CountyCandidatesAlreadyExistException e) {
            //verify
            assertThat(e.getMessage(), CoreMatchers.is(countyName + " county already has candidates."));
        }
    }

    @Test
    public void VoteNumberOverkill() {

    }

    @Test
    public void uploadSingleCandidatesWithBadCsvFile_DiferentColumn() throws Exception {
        //setup
        MultipartFile result = MyUtils.parseToMultiPart("test-csv/Bad_data_with_different_column_count.csv");
        ResponseEntity<CountyReport[]> resp1 = rest.getForEntity("/county", CountyReport[].class);
        Long countyId = resp1.getBody()[0].getId();
        //execute
        try {
            Boolean executeUpdate = transactionTemplate.execute(new TransactionCallback<Boolean>() {
                @Override
                public Boolean doInTransaction(TransactionStatus transactionStatus) {
                    countyService.update(countyId, result);
                    return true;
                }
            });
        } catch (NotEqualColumnsCountCsv e) {
            //verify
            assertThat(e.getMessage(), CoreMatchers.is("Not equal columns count *.csv"));
        }
    }

    @Test
    public void uploadSingleCandidatesWithBadCsvFile_NoNumberInParty() throws Exception {
        //setup
        MultipartFile result = MyUtils.parseToMultiPart("test-csv/Bad_County_candidate_noNumberInParty_data.csv");
        ResponseEntity<CountyReport[]> resp1 = rest.getForEntity("/county", CountyReport[].class);
        Long countyId = resp1.getBody()[0].getId();
        //execute
        try {
            Boolean executeUpdate = transactionTemplate.execute(new TransactionCallback<Boolean>() {
                @Override
                public Boolean doInTransaction(TransactionStatus transactionStatus) {
                    countyService.update(countyId, result);
                    return true;
                }
            });
        } catch (BadCSVFileException e) {
            //verify
            assertThat(e.getMessage(), CoreMatchers.is("Not acceptable CSV data for county single-list"));
        }
    }

    @Test
    public void uploadSingleCandidatesWithBadCsvFile_NoPartyNumber() throws Exception {
        //setup
        MultipartFile result = MyUtils.parseToMultiPart("test-csv/Bad_County_candidate_noPartyNumber_data.csv");
        ResponseEntity<CountyReport[]> resp1 = rest.getForEntity("/county", CountyReport[].class);
        Long countyId = resp1.getBody()[0].getId();
        //execute
        try {
            Boolean executeUpdate = transactionTemplate.execute(new TransactionCallback<Boolean>() {
                @Override
                public Boolean doInTransaction(TransactionStatus transactionStatus) {
                    countyService.update(countyId, result);
                    return true;
                }
            });
        } catch (BadCSVFileException e) {
            //verify
            assertThat(e.getMessage(), CoreMatchers.is("Not acceptable csv data for party-list111111"));
        }
    }

    @Test
    public void uploadSingleCandidatesWithBadCsvFile_NotExistingPartyNumber() throws Exception {
        //setup
        MultipartFile result = MyUtils.parseToMultiPart("test-csv/Bad_County_candidate_notExistingPartyNumber_data.csv");
        ResponseEntity<CountyReport[]> resp1 = rest.getForEntity("/county", CountyReport[].class);
        Long countyId = resp1.getBody()[0].getId();
        //execute
        try {
            Boolean executeUpdate = transactionTemplate.execute(new TransactionCallback<Boolean>() {
                @Override
                public Boolean doInTransaction(TransactionStatus transactionStatus) {
                    countyService.update(countyId, result);
                    return true;
                }
            });
        } catch (BadCSVFileException e) {
            //verify
            assertThat(e.getMessage(), CoreMatchers.is("Not acceptable CSV data for county single-list"));
        }
    }

    @Test
    public void uploadSingleCandidatesWithBadCsvFile_badNameCharacters() throws Exception {
        //setup
        MultipartFile result = MyUtils.parseToMultiPart("test-csv/Bad_County_candidate_badNameCharacters_data.csv");
        ResponseEntity<CountyReport[]> resp1 = rest.getForEntity("/county", CountyReport[].class);
        Long countyId = resp1.getBody()[0].getId();
        //execute
        try {
            Boolean executeUpdate = transactionTemplate.execute(new TransactionCallback<Boolean>() {
                @Override
                public Boolean doInTransaction(TransactionStatus transactionStatus) {
                    countyService.update(countyId, result);
                    return true;
                }
            });
        } catch (BadCSVFileException e) {
            //verify
            assertThat(e.getMessage(), CoreMatchers.is("Not acceptable csv data for party-list"));
        }
    }

    @Test
    public void uploadSingleCandidatesWithBadCsvFile_badSurnameCharacters() throws Exception {
        //setup
        MultipartFile result = MyUtils.parseToMultiPart("test-csv/Bad_County_candidate_badSurnameCharacters_data.csv");
        ResponseEntity<CountyReport[]> resp1 = rest.getForEntity("/county", CountyReport[].class);
        Long countyId = resp1.getBody()[0].getId();
        //execute
        try {
            Boolean executeUpdate = transactionTemplate.execute(new TransactionCallback<Boolean>() {
                @Override
                public Boolean doInTransaction(TransactionStatus transactionStatus) {
                    countyService.update(countyId, result);
                    return true;
                }
            });
        } catch (BadCSVFileException e) {
            //verify
            assertThat(e.getMessage(), CoreMatchers.is("Not acceptable csv data for party-list"));
        }
    }

    @Test
    public void uploadSingleCandidatesWithBadCsvFile_badDateFormat() throws Exception {
        //setup

        MultipartFile result = MyUtils.parseToMultiPart("test-csv/Bad_County_candidate_badDateFormat_data.csv");
        ResponseEntity<CountyReport[]> resp1 = rest.getForEntity("/county", CountyReport[].class);
        Long countyId = resp1.getBody()[0].getId();
        //execute
        try {
            Boolean executeUpdate = transactionTemplate.execute(new TransactionCallback<Boolean>() {
                @Override
                public Boolean doInTransaction(TransactionStatus transactionStatus) {
                    countyService.update(countyId, result);
                    return true;
                }
            });
        } catch (BadCSVFileException e) {
            //verify
            assertThat(e.getMessage(), CoreMatchers.is("Not acceptable CSV data for county single-list"));
        }
    }

    @Test
    public void getCountyDetails() throws Exception {
        //setup
        ResponseEntity<CountyReport[]> resp = rest.getForEntity("/county", CountyReport[].class);
        final Long countyId = resp.getBody()[0].getId();
        final String countyName = resp.getBody()[0].getName();
        ResponseEntity<CountyReport> respDetailsBefore = rest.getForEntity("/county/" + countyId, CountyReport.class);
        String jsonDistrictCreate = "{\"name\" : \"Panerių\",\"adress\" : \"Ūmėdžių g. 9\",\"numberOfElectors\":500,\"county\":{\"id\":" + countyId.toString() + "}}";
        ResponseEntity<DistrictReport> respCreate = rest.postForEntity("/district",
                MyUtils.parseStringToJson(jsonDistrictCreate),
                DistrictReport.class);
        uploadSingleCandidates();
        ResponseEntity<CountyDetailsReport> respDetailsAfter = rest.getForEntity("/county/" + countyId, CountyDetailsReport.class);
        final Long districtId = respCreate.getBody().getId();
        //verify
        assertThat(respCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respDetailsBefore.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respCreate.getBody().getName(), CoreMatchers.is("Panerių"));
        assertThat(respDetailsAfter.getBody().getCandidates().size(), CoreMatchers.is(3));


    }

    @Test
    public void deleteCounty() throws Exception {
        //setup
        getCountyDetails();
//        final int[] ints = candidateRepository.getCandidatesList().stream().mapToInt(c -> c.getId().intValue()).toArray();
//        final DistrictEntity ditrictWithCand = transactionTemplate.execute(new TransactionCallback<DistrictEntity>() {
//            @Override
//            public DistrictEntity doInTransaction(TransactionStatus transactionStatus) {
//                final List<DistrictEntity> collect = districtRepository.findAll().stream().filter(d -> d.getResultSingleEntity().size() != 0).collect(Collectors.toList());
//                return collect.get(0);
//            }
//        });

        //exercise
        countyRepository.findAll().stream().forEach(c -> countyService.delete(c.getId()));
        ResponseEntity<CountyReport[]> response = rest.getForEntity("/county", CountyReport[].class);
        //verify
        assertThat(countyRepository.findAll().size(), CoreMatchers.is(0));
        assertThat(districtRepository.findAll().size(), CoreMatchers.is(0));
    }

    @Test
    public void saveSingleResultsAndDeleteCountyWithResultsTest() {
        //setup adding candidates

        MultipartFile result = MyUtils.parseToMultiPart("test-csv/data-county-non-party.csv");
        ResponseEntity<CountyReport[]> resp1 = rest.getForEntity("/county", CountyReport[].class);
        final Long id = resp1.getBody()[0].getId();
        Boolean executeUpdate = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                countyService.update(id, result);
                return true;
            }
        });
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


        ResultSingleEntity res1 = new ResultSingleEntity(c1, d1, 200L, new Date());
        ResultSingleEntity res2 = new ResultSingleEntity(c2, d1, 500L, new Date());
        ResultSingleEntity res3 = new ResultSingleEntity(c3, d1, 400L, new Date());
        ResultSingleEntity res4 = new ResultSingleEntity(spoiled, d1, 100L, new Date());

        List<ResultSingleEntity> rl = new ArrayList<>();
        rl.add(res1);
        rl.add(res2);
        rl.add(res3);
        rl.add(res4);


        final String save = resultSingleService.save(rl);
        //verify
        assertThat(save, CoreMatchers.is("Votes registered"));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(3));
//        countyRepository.findAll().stream().forEach(c -> countyService.delete(c.getId()));
//        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(0));
    }

    @Test
    public void CandidateUploadCountCheck() {
        //setup
        ResponseEntity<CountyReport[]> countyResponse = rest.getForEntity(URI, CountyReport[].class);
        Long vilniusId = countyResponse.getBody()[0].getId();
        MultipartFile darboPartijaFile = MyUtils.parseToMultiPart("test-csv/Good_Darbo_Party_candidate_data.csv");
        String partyName1 = "Darbo";
        Integer partyNumber1 = 5;
        partyService.save(partyName1, partyNumber1,darboPartijaFile);
        ResponseEntity<PartyReport[]> partyResponse = rest.getForEntity("/party", PartyReport[].class);
        Long darboPartijaId = partyResponse.getBody()[0].getId();
        String darboPartijaName = partyResponse.getBody()[0].getName();
        Integer darboPartijaNumber = partyResponse.getBody()[0].getPartyNumber();
        MultipartFile mixedFile = MyUtils.parseToMultiPart("test-csv/Good_County_NoParty_And_Party_candidate_data_3.csv");
        int candidateCountBeforeUploads = candidateService.getAllCandidates().size();
        partyService.save(darboPartijaId, darboPartijaName, darboPartijaNumber, darboPartijaFile);
        int candidateCountAfterPartyUpdate = candidateService.getAllCandidates().size();
        Boolean executeUpdate = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                countyService.update(vilniusId, mixedFile);
                return true;
            }
        });
        ResponseEntity<CandidateReport[]> responseAfterUpdate = rest.getForEntity("/candidate", CandidateReport[].class);
        int candidateCountAfterCountyUpdate = candidateService.getAllCandidates().size();
        assertThat(candidateCountAfterCountyUpdate, CoreMatchers.is(responseAfterUpdate.getBody().length));
    }

    @Test
    public void CandidateIsMultilist() {
        ResponseEntity<CountyReport[]> countyResponse = rest.getForEntity(URI, CountyReport[].class);
        Long vilniusId = countyResponse.getBody()[0].getId();
        MultipartFile darboPartijaFile = MyUtils.parseToMultiPart("test-csv/Good_Darbo_Party_candidate_data.csv");
        String partyName1 = "Darbo";
        Integer partyNumber1 = 5;
        partyService.save(partyName1, partyNumber1,darboPartijaFile);
        ResponseEntity<PartyReport[]> partyResponse = rest.getForEntity("/party", PartyReport[].class);
        Long darboPartijaId = partyResponse.getBody()[0].getId();
        String darboPartijaName = partyResponse.getBody()[0].getName();
        Integer darboPartijaNumber = partyResponse.getBody()[0].getPartyNumber();
        MultipartFile mixedFile = MyUtils.parseToMultiPart("test-csv/Good_County_NoParty_And_Party_candidate_data_3.csv");
        partyService.save(darboPartijaId, darboPartijaName, darboPartijaNumber, darboPartijaFile);
        Boolean executeUpdate = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                countyService.update(vilniusId, mixedFile);
                return true;
            }
        });
        Boolean execute = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                boolean isNotMultiMember = countyRepository.findById(vilniusId).getCandidates()
                        .stream()
                        .filter(c -> c.getPartyDependencies() == null)
                        .findFirst().get().isMultiList();

                return isNotMultiMember;
            }
        });
        assertThat(execute, CoreMatchers.is(false));
    }

    @Test
    public void CandidateCountNoParty() {
        //setup
        ResponseEntity<CountyReport[]> countyResponse = rest.getForEntity(URI, CountyReport[].class);
        Long vilniusId = countyResponse.getBody()[0].getId();
        //setup party
        MultipartFile darboPartijaFile = MyUtils.parseToMultiPart("test-csv/Good_Darbo_Party_candidate_data.csv");
        String partyName1 = "Darbo";
        Integer partyNumber1 = 5;
        partyService.save(partyName1, partyNumber1,darboPartijaFile);
        ResponseEntity<PartyReport[]> partyResponse = rest.getForEntity("/party", PartyReport[].class);
        Long darboPartijaId = partyResponse.getBody()[0].getId();
        String darboPartijaName = partyResponse.getBody()[0].getName();
        Integer darboPartijaNumber = partyResponse.getBody()[0].getPartyNumber();
        MultipartFile mixedFile = MyUtils.parseToMultiPart("test-csv/Good_County_NoParty_And_Party_candidate_data_3.csv");
        Boolean executeUpdate = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                countyService.update(vilniusId, mixedFile);
                return true;
            }
        });
        Boolean execute = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                long noPartyCandidateCount = countyRepository.findById(vilniusId).getCandidates()
                        .stream().filter(c -> c.getPartyDependencies() == null).count();
                assertThat(noPartyCandidateCount, CoreMatchers.is((1L)));
                return true;
            }

        });
    }

    @Test
    public void CandidateCountNotInMultilist() {
        ResponseEntity<CountyReport[]> countyResponse = rest.getForEntity(URI, CountyReport[].class);
        Long vilniusId = countyResponse.getBody()[0].getId();
        MultipartFile darboPartijaFile = MyUtils.parseToMultiPart("test-csv/Good_Darbo_Party_candidate_data.csv");
        String partyName1 = "Darbo";
        Integer partyNumber1 = 5;
        partyService.save(partyName1, partyNumber1,darboPartijaFile);
        ResponseEntity<PartyReport[]> partyResponse = rest.getForEntity("/party", PartyReport[].class);
        Long darboPartijaId = partyResponse.getBody()[0].getId();
        String darboPartijaName = partyResponse.getBody()[0].getName();
        Integer darboPartijaNumber = partyResponse.getBody()[0].getPartyNumber();
        MultipartFile mixedFile = MyUtils.parseToMultiPart("test-csv/Good_County_NoParty_And_Party_candidate_data_3.csv");
        partyService.save(darboPartijaId, darboPartijaName, darboPartijaNumber, darboPartijaFile);
        Boolean executeUpdate = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                countyService.update(vilniusId, mixedFile);
                return true;
            }
        });
        Long execute = transactionTemplate.execute(new TransactionCallback<Long>() {
            @Override
            public Long doInTransaction(TransactionStatus transactionStatus) {
                long count = countyRepository.findById(vilniusId).getCandidates()
                        .stream().filter(c -> c.isMultiList() == false).count();

                return count;
            }

        });
        assertThat(execute, CoreMatchers.is(2L));
    }

    @Test
    public void MultiCandidatesAfterCountyDelete() {
        ResponseEntity<CountyReport[]> countyResponse = rest.getForEntity(URI, CountyReport[].class);
        Long vilniusId = countyResponse.getBody()[0].getId();
        MultipartFile darboPartijaFile = MyUtils.parseToMultiPart("test-csv/Good_Darbo_Party_candidate_data.csv");
        String partyName1 = "Darbo";
        Integer partyNumber1 = 5;
        partyService.save(partyName1, partyNumber1,darboPartijaFile);
        ResponseEntity<PartyReport[]> partyResponse = rest.getForEntity("/party", PartyReport[].class);
        Long darboPartijaId = partyResponse.getBody()[0].getId();
        String darboPartijaName = partyResponse.getBody()[0].getName();
        Integer darboPartijaNumber = partyResponse.getBody()[0].getPartyNumber();
        MultipartFile mixedFile = MyUtils.parseToMultiPart("test-csv/Good_County_NoParty_And_Party_candidate_data_3.csv");
        partyService.save(darboPartijaId, darboPartijaName, darboPartijaNumber, darboPartijaFile);


        countyService.delete(vilniusId);
        long countAfterDel = candidateRepository.getCandidatesList().stream().filter(c -> c.getPartyDependencies() == null).count();
        assertThat(countAfterDel, CoreMatchers.is(0L));
    }

    @Test
    public void deleteSingleDistrictWithSingleResultsFromCounty() {
        //setup adding candidates
        districtRepository.findAll().stream().forEach(d -> districtService.delete(d.getId()));
        MultipartFile result = MyUtils.parseToMultiPart("test-csv/data-county-non-party.csv");
        ResponseEntity<CountyReport[]> resp1 = rest.getForEntity("/county", CountyReport[].class);
        final Long id = resp1.getBody()[0].getId();
        Boolean executeUpdate = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                countyService.update(id, result);
                return true;
            }
        });
        //setup addnig district
        final Long countyId = countyRepository.findAll().get(0).getId();
        String paneriuDistrictCreate = MyUtils.getDistrictJson(null, "Panerių", "Ūmėdžių g. 9", 500, countyId);
        ResponseEntity<DistrictReport> respPaneriuDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(paneriuDistrictCreate), DistrictReport.class);
        String karoluDistrictCreate = MyUtils.getDistrictJson(null, "Karolų", "Koralų g 15", 1000, countyId);
        ResponseEntity<DistrictReport> respKaroluDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(karoluDistrictCreate), DistrictReport.class);
        //votes
        final DistrictEntity paneriuDistrict = districtRepository.findAll().get(0);
        final DistrictEntity karoluDistrict = districtRepository.findAll().get(1);

        final CandidateEntity c1 = candidateRepository.getCandidatesList().get(0);
        final CandidateEntity c2 = candidateRepository.getCandidatesList().get(1);
        final CandidateEntity c3 = candidateRepository.getCandidatesList().get(2);
        final CandidateEntity spoiled = new CandidateEntity();
        spoiled.setId(-1991L);

        List<ResultSingleEntity> paneriuResults = new ArrayList<>();
        paneriuResults.add(new ResultSingleEntity(c1, paneriuDistrict, 200L, new Date()));
        paneriuResults.add(new ResultSingleEntity(c2, paneriuDistrict, 500L, new Date()));
        paneriuResults.add(new ResultSingleEntity(c3, paneriuDistrict, 400L, new Date()));
        paneriuResults.add(new ResultSingleEntity(spoiled, paneriuDistrict, 100L, new Date()));

        List<ResultSingleEntity> karoluResults = new ArrayList<>();
        karoluResults.add(new ResultSingleEntity(c1, karoluDistrict, 300L, new Date()));
        karoluResults.add(new ResultSingleEntity(c2, karoluDistrict, 200L, new Date()));
        karoluResults.add(new ResultSingleEntity(c3, karoluDistrict, 300L, new Date()));
        karoluResults.add(new ResultSingleEntity(spoiled, karoluDistrict, 100L, new Date()));

        String savePaneriuResults = resultSingleService.save(paneriuResults);
        String saveKaroluResults = resultSingleService.save(karoluResults);
        districtService.delete(respKaroluDistrict.getBody().getId());
        //verify
        assertThat(savePaneriuResults, CoreMatchers.is("Votes registered"));
        assertThat(saveKaroluResults, CoreMatchers.is("Votes registered"));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(3));
        countyRepository.findAll().stream().forEach(c -> countyService.delete(c.getId()));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(0));
    }

    @Test
    public void deleteDistrictsSingleResultsByAdmin() {
        //setup adding candidates
        districtRepository.findAll().stream().forEach(d -> districtService.delete(d.getId()));
        MultipartFile result = MyUtils.parseToMultiPart("test-csv/data-county-non-party.csv");
        ResponseEntity<CountyReport[]> resp1 = rest.getForEntity("/county", CountyReport[].class);
        final Long id = resp1.getBody()[0].getId();
        Boolean executeUpdate = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                countyService.update(id, result);
                return true;
            }
        });
        //setup addnig district
        final Long countyId = countyRepository.findAll().get(0).getId();
        String paneriuDistrictCreate = MyUtils.getDistrictJson(null, "Panerių", "Ūmėdžių g. 9", 500, countyId);
        ResponseEntity<DistrictReport> respPaneriuDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(paneriuDistrictCreate), DistrictReport.class);
        String karoluDistrictCreate = MyUtils.getDistrictJson(null, "Karolų", "Koralų g 15", 1000, countyId);
        ResponseEntity<DistrictReport> respKaroluDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(karoluDistrictCreate), DistrictReport.class);
        //votes
        final DistrictEntity paneriuDistrict = districtRepository.findAll().get(0);
        final DistrictEntity karoluDistrict = districtRepository.findAll().get(1);

        final CandidateEntity c1 = candidateRepository.getCandidatesList().get(0);
        final CandidateEntity c2 = candidateRepository.getCandidatesList().get(1);
        final CandidateEntity c3 = candidateRepository.getCandidatesList().get(2);
        final CandidateEntity spoiled = new CandidateEntity();
        spoiled.setId(-1991L);

        List<ResultSingleEntity> paneriuResults = new ArrayList<>();
        paneriuResults.add(new ResultSingleEntity(c1, paneriuDistrict, 200L, new Date()));
        paneriuResults.add(new ResultSingleEntity(c2, paneriuDistrict, 500L, new Date()));
        paneriuResults.add(new ResultSingleEntity(c3, paneriuDistrict, 400L, new Date()));
        paneriuResults.add(new ResultSingleEntity(spoiled, paneriuDistrict, 100L, new Date()));

        List<ResultSingleEntity> karoluResults = new ArrayList<>();
        karoluResults.add(new ResultSingleEntity(c1, karoluDistrict, 300L, new Date()));
        karoluResults.add(new ResultSingleEntity(c2, karoluDistrict, 200L, new Date()));
        karoluResults.add(new ResultSingleEntity(c3, karoluDistrict, 300L, new Date()));
        karoluResults.add(new ResultSingleEntity(spoiled, karoluDistrict, 100L, new Date()));

        String savePaneriuResults = resultSingleService.save(paneriuResults);
        String saveKaroluResults = resultSingleService.save(karoluResults);
        String deleteKaroluResults = resultSingleService.delete(respKaroluDistrict.getBody().getId());

        //verify
        assertThat(savePaneriuResults, CoreMatchers.is("Votes registered"));
        assertThat(saveKaroluResults, CoreMatchers.is("Votes registered"));
        assertThat(deleteKaroluResults, CoreMatchers.is("Results Deleted"));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(3));
        countyRepository.findAll().stream().forEach(c -> countyService.delete(c.getId()));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(0));
    }

    @Test
    public void deleteCandidatesFromCountyDeletesSingleResults() {
        //setup adding candidates
        districtRepository.findAll().stream().forEach(d -> districtService.delete(d.getId()));
        MultipartFile result = MyUtils.parseToMultiPart("test-csv/data-county-non-party.csv");
        ResponseEntity<CountyReport[]> resp1 = rest.getForEntity("/county", CountyReport[].class);
        final Long id = resp1.getBody()[0].getId();
        Boolean executeUpdate = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                countyService.update(id, result);
                return true;
            }
        });
        //setup addnig district
        final Long countyId = countyRepository.findAll().get(0).getId();
        String karoluDistrictCreate = MyUtils.getDistrictJson(null, "Karolų", "Koralų g 15", 1000, countyId);
        ResponseEntity<DistrictReport> respKaroluDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(karoluDistrictCreate), DistrictReport.class);
        //votes
        final DistrictEntity karoluDistrict = districtRepository.findAll().get(0);

        final CandidateEntity c1 = candidateRepository.getCandidatesList().get(0);
        final CandidateEntity c2 = candidateRepository.getCandidatesList().get(1);
        final CandidateEntity c3 = candidateRepository.getCandidatesList().get(2);
        final CandidateEntity spoiled = new CandidateEntity();
        spoiled.setId(-1991L);

        List<ResultSingleEntity> karoluResults = new ArrayList<>();
        karoluResults.add(new ResultSingleEntity(c1, karoluDistrict, 300L, new Date()));
        karoluResults.add(new ResultSingleEntity(c2, karoluDistrict, 200L, new Date()));
        karoluResults.add(new ResultSingleEntity(c3, karoluDistrict, 300L, new Date()));
        karoluResults.add(new ResultSingleEntity(spoiled, karoluDistrict, 100L, new Date()));

        String saveKaroluResults = resultSingleService.save(karoluResults);
        countyService.delete(id);
        //verify
        assertThat(saveKaroluResults, CoreMatchers.is("Votes registered"));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(0));
        countyRepository.findAll().stream().forEach(c -> countyService.delete(c.getId()));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(0));
    }

    @Test
    public void approveDistrictsSingleResultsByAdmin() {
        //setup adding candidates
        districtRepository.findAll().stream().forEach(d -> districtService.delete(d.getId()));
        MultipartFile result = MyUtils.parseToMultiPart("test-csv/data-county-non-party.csv");
        ResponseEntity<CountyReport[]> resp1 = rest.getForEntity("/county", CountyReport[].class);
        final Long id = resp1.getBody()[0].getId();
        Boolean executeUpdate = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                countyService.update(id, result);
                return true;
            }
        });
        //setup addnig district
        final Long countyId = countyRepository.findAll().get(0).getId();
        String karoluDistrictCreate = MyUtils.getDistrictJson(null, "Karolų", "Koralų g 15", 1000, countyId);
        ResponseEntity<DistrictReport> respKaroluDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(karoluDistrictCreate), DistrictReport.class);
        //votes
        final DistrictEntity karoluDistrict = districtRepository.findAll().get(0);

        final CandidateEntity c1 = candidateRepository.getCandidatesList().get(0);
        final CandidateEntity c2 = candidateRepository.getCandidatesList().get(1);
        final CandidateEntity c3 = candidateRepository.getCandidatesList().get(2);
        final CandidateEntity spoiled = new CandidateEntity();
        spoiled.setId(-1991L);

        List<ResultSingleEntity> karoluResults = new ArrayList<>();
        karoluResults.add(new ResultSingleEntity(c1, karoluDistrict, 300L, new Date()));
        karoluResults.add(new ResultSingleEntity(c2, karoluDistrict, 200L, new Date()));
        karoluResults.add(new ResultSingleEntity(c3, karoluDistrict, 300L, new Date()));
        karoluResults.add(new ResultSingleEntity(spoiled, karoluDistrict, 100L, new Date()));

        String saveKaroluResults = resultSingleService.save(karoluResults);
        String approveKaroluResults = resultSingleService.approve(respKaroluDistrict.getBody().getId());

        //verify
        assertThat(saveKaroluResults, CoreMatchers.is("Votes registered"));
        assertThat(approveKaroluResults, CoreMatchers.is("Results approved"));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(3));
        countyRepository.findAll().stream().forEach(c -> countyService.delete(c.getId()));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(0));
    }

    @Test
    public void saveMultiResultsAndDeleteCountyWithMultiResults() {
        //setup
        districtRepository.findAll().stream().forEach(d -> districtService.delete(d.getId()));
        //setup adding parties
        final MultipartFile partyfile1 = MyUtils.parseToMultiPart("test-csv/Good__Skaidruoliu_Party_candidate_data.csv");
        String partyName1 = "Darbo";
        Integer partyNumber1 = 1;
        partyService.save(partyName1, partyNumber1, partyfile1);

        final MultipartFile partyfile2 = MyUtils.parseToMultiPart("test-csv/Good_Darbo_Party_candidate_data.csv");
        String partyName2 = "Skaidruolių";
        Integer partyNumber2 = 2;
        partyService.save(partyName2, partyNumber2, partyfile2);
        ResponseEntity<PartyReport[]> respParty = rest.getForEntity("/party", PartyReport[].class);
        Long darboPartyID = respParty.getBody()[0].getId();
        Long skaidruoliuPartyId = respParty.getBody()[1].getId();
        PartyEntity darboPartyEntity = partyService.getPartyEntityById(darboPartyID);
        PartyEntity skaidruoliuPartyEntity = partyService.getPartyEntityById(skaidruoliuPartyId);
        //setup adding district
        ResponseEntity<CountyReport[]> resp1 = rest.getForEntity("/county", CountyReport[].class);
        final Long id = resp1.getBody()[0].getId();
        final Long countyId = countyRepository.findAll().get(0).getId();
        String paneriuDistrictCreate = MyUtils.getDistrictJson(null, "Panerių", "Ūmėdžių g. 9", 500, countyId);
        ResponseEntity<DistrictReport> respPaneriuDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(paneriuDistrictCreate), DistrictReport.class);
        String karoluDistrictCreate = MyUtils.getDistrictJson(null, "Karolų", "Koralų g 15", 1000, countyId);
        ResponseEntity<DistrictReport> respKaroluDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(karoluDistrictCreate), DistrictReport.class);
        //votes
        final DistrictEntity paneriuDistrict = districtRepository.findAll().get(0);
        final DistrictEntity karoluDistrict = districtRepository.findAll().get(1);

        final PartyEntity p1 = partyRepository.getById(darboPartyID);
        final PartyEntity p2 = partyRepository.getById(skaidruoliuPartyId);
        final PartyEntity spoiled = new PartyEntity();
        spoiled.setId(-1991L);

        ResultMultiEntity party1PaneriuVotes = MyUtils.getResultMultiEntity(darboPartyEntity, paneriuDistrict, 200L);
        ResultMultiEntity party2PaneriuVotes = MyUtils.getResultMultiEntity(skaidruoliuPartyEntity, paneriuDistrict, 200L);
        ResultMultiEntity paneriuSpoiledVotes = MyUtils.getResultMultiEntity(spoiled, paneriuDistrict, 100L);
        ResultMultiEntity party1KaroluVotes = MyUtils.getResultMultiEntity(darboPartyEntity, karoluDistrict, 150L);
        ResultMultiEntity party2KaroluVotes = MyUtils.getResultMultiEntity(skaidruoliuPartyEntity, karoluDistrict, 100L);
        ResultMultiEntity karoluSpoiledVotes = MyUtils.getResultMultiEntity(spoiled, karoluDistrict, 100L);

        List<ResultMultiEntity> paneriuResults = new ArrayList<>();
        paneriuResults.add(party1PaneriuVotes);
        paneriuResults.add(party2PaneriuVotes);
        paneriuResults.add(paneriuSpoiledVotes);

        List<ResultMultiEntity> karoluResults = new ArrayList<>();
        karoluResults.add(party1KaroluVotes);
        karoluResults.add(party2KaroluVotes);
        karoluResults.add(karoluSpoiledVotes);

        String savePaneriuResults = resultMultiService.save(paneriuResults);
        String saveKaroluResults = resultMultiService.save(karoluResults);
        //verify
        assertThat(savePaneriuResults, CoreMatchers.is("Votes registered"));
        assertThat(saveKaroluResults, CoreMatchers.is("Votes registered"));
        assertThat(resultMultiRepository.findAll().size(), CoreMatchers.is(4));
        countyRepository.findAll().stream().forEach(c -> countyService.delete(c.getId()));
        assertThat(resultMultiRepository.findAll().size(), CoreMatchers.is(0));
    }

    @Test
    public void deleteSingleDistrictWithMultiResultsFromCounty() {
        //setup
        districtRepository.findAll().stream().forEach(d -> districtService.delete(d.getId()));
        //setup adding parties
        final MultipartFile partyfile1 = MyUtils.parseToMultiPart("test-csv/Good__Skaidruoliu_Party_candidate_data.csv");
        String partyName1 = "Darbo";
        Integer partyNumber1 = 1;
        partyService.save(partyName1, partyNumber1, partyfile1);

        final MultipartFile partyfile2 = MyUtils.parseToMultiPart("test-csv/Good_Darbo_Party_candidate_data.csv");
        String partyName2 = "Skaidruolių";
        Integer partyNumber2 = 2;
        partyService.save(partyName2, partyNumber2, partyfile2);
        ResponseEntity<PartyReport[]> respParty = rest.getForEntity("/party", PartyReport[].class);
        Long darboPartyID = respParty.getBody()[0].getId();
        Long skaidruoliuPartyId = respParty.getBody()[1].getId();
        PartyEntity darboPartyEntity = partyService.getPartyEntityById(darboPartyID);
        PartyEntity skaidruoliuPartyEntity = partyService.getPartyEntityById(skaidruoliuPartyId);
        //setup adding district
        ResponseEntity<CountyReport[]> resp1 = rest.getForEntity("/county", CountyReport[].class);
        final Long id = resp1.getBody()[0].getId();
        final Long countyId = countyRepository.findAll().get(0).getId();
        String paneriuDistrictCreate = MyUtils.getDistrictJson(null, "Panerių", "Ūmėdžių g. 9", 500, countyId);
        ResponseEntity<DistrictReport> respPaneriuDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(paneriuDistrictCreate), DistrictReport.class);
        String karoluDistrictCreate = MyUtils.getDistrictJson(null, "Karolų", "Koralų g 15", 1000, countyId);
        ResponseEntity<DistrictReport> respKaroluDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(karoluDistrictCreate), DistrictReport.class);
        //votes
        final DistrictEntity paneriuDistrict = districtRepository.findAll().get(0);
        final DistrictEntity karoluDistrict = districtRepository.findAll().get(1);

        final PartyEntity p1 = partyRepository.getById(darboPartyID);
        final PartyEntity p2 = partyRepository.getById(skaidruoliuPartyId);
        final PartyEntity spoiled = new PartyEntity();
        spoiled.setId(-1991L);

        ResultMultiEntity party1PaneriuVotes = MyUtils.getResultMultiEntity(darboPartyEntity, paneriuDistrict, 200L);
        ResultMultiEntity party2PaneriuVotes = MyUtils.getResultMultiEntity(skaidruoliuPartyEntity, paneriuDistrict, 200L);
        ResultMultiEntity paneriuSpoiledVotes = MyUtils.getResultMultiEntity(spoiled, paneriuDistrict, 100L);
        ResultMultiEntity party1KaroluVotes = MyUtils.getResultMultiEntity(darboPartyEntity, karoluDistrict, 150L);
        ResultMultiEntity party2KaroluVotes = MyUtils.getResultMultiEntity(skaidruoliuPartyEntity, karoluDistrict, 100L);
        ResultMultiEntity karoluSpoiledVotes = MyUtils.getResultMultiEntity(spoiled, karoluDistrict, 100L);

        List<ResultMultiEntity> paneriuResults = new ArrayList<>();
        paneriuResults.add(party1PaneriuVotes);
        paneriuResults.add(party2PaneriuVotes);
        paneriuResults.add(paneriuSpoiledVotes);

        List<ResultMultiEntity> karoluResults = new ArrayList<>();
        karoluResults.add(party1KaroluVotes);
        karoluResults.add(party2KaroluVotes);
        karoluResults.add(karoluSpoiledVotes);

        String savePaneriuResults = resultMultiService.save(paneriuResults);
        String saveKaroluResults = resultMultiService.save(karoluResults);
        districtService.delete(respKaroluDistrict.getBody().getId());
        //verify
        assertThat(savePaneriuResults, CoreMatchers.is("Votes registered"));
        assertThat(saveKaroluResults, CoreMatchers.is("Votes registered"));
        assertThat(resultMultiRepository.findAll().size(), CoreMatchers.is(2));
    }

    @Test
    public void deleteDistrictsMultiResultsByAdmin() {
        //setup
        districtRepository.findAll().stream().forEach(d -> districtService.delete(d.getId()));
        //setup adding parties
        final MultipartFile partyfile1 = MyUtils.parseToMultiPart("test-csv/Good__Skaidruoliu_Party_candidate_data.csv");
        String partyName1 = "Darbo";
        Integer partyNumber1 = 1;
        partyService.save(partyName1, partyNumber1, partyfile1);

        final MultipartFile partyfile2 = MyUtils.parseToMultiPart("test-csv/Good_Darbo_Party_candidate_data.csv");
        String partyName2 = "Skaidruolių";
        Integer partyNumber2 = 2;
        partyService.save(partyName2, partyNumber2, partyfile2);
        ResponseEntity<PartyReport[]> respParty = rest.getForEntity("/party", PartyReport[].class);
        Long darboPartyID = respParty.getBody()[0].getId();
        Long skaidruoliuPartyId = respParty.getBody()[1].getId();
        PartyEntity darboPartyEntity = partyService.getPartyEntityById(darboPartyID);
        PartyEntity skaidruoliuPartyEntity = partyService.getPartyEntityById(skaidruoliuPartyId);
        //setup adding district
        ResponseEntity<CountyReport[]> resp1 = rest.getForEntity("/county", CountyReport[].class);
        final Long id = resp1.getBody()[0].getId();
        final Long countyId = countyRepository.findAll().get(0).getId();
        String paneriuDistrictCreate = MyUtils.getDistrictJson(null, "Panerių", "Ūmėdžių g. 9", 500, countyId);
        ResponseEntity<DistrictReport> respPaneriuDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(paneriuDistrictCreate), DistrictReport.class);
        String karoluDistrictCreate = MyUtils.getDistrictJson(null, "Karolų", "Koralų g 15", 1000, countyId);
        ResponseEntity<DistrictReport> respKaroluDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(karoluDistrictCreate), DistrictReport.class);
        //votes
        final DistrictEntity paneriuDistrict = districtRepository.findAll().get(0);
        final DistrictEntity karoluDistrict = districtRepository.findAll().get(1);

        final PartyEntity p1 = partyRepository.getById(darboPartyID);
        final PartyEntity p2 = partyRepository.getById(skaidruoliuPartyId);
        final PartyEntity spoiled = new PartyEntity();
        spoiled.setId(-1991L);
        ResultMultiEntity party1PaneriuVotes = MyUtils.getResultMultiEntity(darboPartyEntity, paneriuDistrict, 200L);
        ResultMultiEntity party2PaneriuVotes = MyUtils.getResultMultiEntity(skaidruoliuPartyEntity, paneriuDistrict, 200L);
        ResultMultiEntity paneriuSpoiledVotes = MyUtils.getResultMultiEntity(spoiled, paneriuDistrict, 100L);
        ResultMultiEntity party1KaroluVotes = MyUtils.getResultMultiEntity(darboPartyEntity, karoluDistrict, 150L);
        ResultMultiEntity party2KaroluVotes = MyUtils.getResultMultiEntity(skaidruoliuPartyEntity, karoluDistrict, 100L);
        ResultMultiEntity karoluSpoiledVotes = MyUtils.getResultMultiEntity(spoiled, karoluDistrict, 100L);

        List<ResultMultiEntity> paneriuResults = new ArrayList<>();
        paneriuResults.add(party1PaneriuVotes);
        paneriuResults.add(party2PaneriuVotes);
        paneriuResults.add(paneriuSpoiledVotes);

        List<ResultMultiEntity> karoluResults = new ArrayList<>();
        karoluResults.add(party1KaroluVotes);
        karoluResults.add(party2KaroluVotes);
        karoluResults.add(karoluSpoiledVotes);

        String savePaneriuResults = resultMultiService.save(paneriuResults);
        String saveKaroluResults = resultMultiService.save(karoluResults);
        String deleteKaroluResults = resultMultiService.delete(respKaroluDistrict.getBody().getId());

        //verify
        assertThat(savePaneriuResults, CoreMatchers.is("Votes registered"));
        assertThat(saveKaroluResults, CoreMatchers.is("Votes registered"));
        assertThat(deleteKaroluResults, CoreMatchers.is("Votes Deleted"));
        assertThat(resultMultiRepository.findAll().size(), CoreMatchers.is(2));
    }

    @Test
    public void deletePartyByIdFromPartyListDeletePartyMultiResults() {
        //setup
        districtRepository.findAll().stream().forEach(d -> districtService.delete(d.getId()));
        //setup adding parties
        final MultipartFile partyfile1 = MyUtils.parseToMultiPart("test-csv/Good__Skaidruoliu_Party_candidate_data.csv");
        String partyName1 = "Darbo";
        Integer partyNumber1 = 1;
        partyService.save(partyName1, partyNumber1, partyfile1);

        final MultipartFile partyfile2 = MyUtils.parseToMultiPart("test-csv/Good_Darbo_Party_candidate_data.csv");
        String partyName2 = "Skaidruolių";
        Integer partyNumber2 = 2;
        partyService.save(partyName2, partyNumber2, partyfile2);
        ResponseEntity<PartyReport[]> respParty = rest.getForEntity("/party", PartyReport[].class);
        Long darboPartyID = respParty.getBody()[0].getId();
        Long skaidruoliuPartyId = respParty.getBody()[1].getId();
        PartyEntity darboPartyEntity = partyService.getPartyEntityById(darboPartyID);
        PartyEntity skaidruoliuPartyEntity = partyService.getPartyEntityById(skaidruoliuPartyId);
        //setup adding district
        ResponseEntity<CountyReport[]> resp1 = rest.getForEntity("/county", CountyReport[].class);
        final Long id = resp1.getBody()[0].getId();
        final Long countyId = countyRepository.findAll().get(0).getId();
        String paneriuDistrictCreate = MyUtils.getDistrictJson(null, "Panerių", "Ūmėdžių g. 9", 500, countyId);
        ResponseEntity<DistrictReport> respPaneriuDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(paneriuDistrictCreate), DistrictReport.class);
        String karoluDistrictCreate = MyUtils.getDistrictJson(null, "Karolų", "Koralų g 15", 1000, countyId);
        ResponseEntity<DistrictReport> respKaroluDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(karoluDistrictCreate), DistrictReport.class);
        //votes
        final DistrictEntity paneriuDistrict = districtRepository.findAll().get(0);
        final DistrictEntity karoluDistrict = districtRepository.findAll().get(1);

        final PartyEntity p1 = partyRepository.getById(darboPartyID);
        final PartyEntity p2 = partyRepository.getById(skaidruoliuPartyId);
        final PartyEntity spoiled = new PartyEntity();
        spoiled.setId(-1991L);
        ResultMultiEntity party1PaneriuVotes = MyUtils.getResultMultiEntity(darboPartyEntity, paneriuDistrict, 200L);
        ResultMultiEntity party2PaneriuVotes = MyUtils.getResultMultiEntity(skaidruoliuPartyEntity, paneriuDistrict, 200L);
        ResultMultiEntity paneriuSpoiledVotes = MyUtils.getResultMultiEntity(spoiled, paneriuDistrict, 100L);
        ResultMultiEntity party1KaroluVotes = MyUtils.getResultMultiEntity(darboPartyEntity, karoluDistrict, 150L);
        ResultMultiEntity party2KaroluVotes = MyUtils.getResultMultiEntity(skaidruoliuPartyEntity, karoluDistrict, 100L);
        ResultMultiEntity karoluSpoiledVotes = MyUtils.getResultMultiEntity(spoiled, karoluDistrict, 100L);

        List<ResultMultiEntity> paneriuResults = new ArrayList<>();
        paneriuResults.add(party1PaneriuVotes);
        paneriuResults.add(party2PaneriuVotes);
        paneriuResults.add(paneriuSpoiledVotes);

        List<ResultMultiEntity> karoluResults = new ArrayList<>();
        karoluResults.add(party1KaroluVotes);
        karoluResults.add(party2KaroluVotes);
        karoluResults.add(karoluSpoiledVotes);

        String savePaneriuResults = resultMultiService.save(paneriuResults);
        String saveKaroluResults = resultMultiService.save(karoluResults);
        partyService.delete(skaidruoliuPartyId);
        //verify
        assertThat(savePaneriuResults, CoreMatchers.is("Votes registered"));
        assertThat(saveKaroluResults, CoreMatchers.is("Votes registered"));
        assertThat(resultMultiRepository.findAll().size(), CoreMatchers.is(2));
    }

    @Test
    public void approveDistrictsMultiResultsByAdmin() {
        //setup
        districtRepository.findAll().stream().forEach(d -> districtService.delete(d.getId()));
        //setup adding parties
        final MultipartFile partyfile1 = MyUtils.parseToMultiPart("test-csv/Good__Skaidruoliu_Party_candidate_data.csv");
        String partyName1 = "Darbo";
        Integer partyNumber1 = 1;
        partyService.save(partyName1, partyNumber1, partyfile1);

        final MultipartFile partyfile2 = MyUtils.parseToMultiPart("test-csv/Good_Darbo_Party_candidate_data.csv");
        String partyName2 = "Skaidruolių";
        Integer partyNumber2 = 2;
        partyService.save(partyName2, partyNumber2, partyfile2);
        ResponseEntity<PartyReport[]> respParty = rest.getForEntity("/party", PartyReport[].class);
        Long darboPartyID = respParty.getBody()[0].getId();
        Long skaidruoliuPartyId = respParty.getBody()[1].getId();
        PartyEntity darboPartyEntity = partyService.getPartyEntityById(darboPartyID);
        PartyEntity skaidruoliuPartyEntity = partyService.getPartyEntityById(skaidruoliuPartyId);
        //setup adding district
        ResponseEntity<CountyReport[]> resp1 = rest.getForEntity("/county", CountyReport[].class);
        final Long id = resp1.getBody()[0].getId();
        final Long countyId = countyRepository.findAll().get(0).getId();
        String paneriuDistrictCreate = MyUtils.getDistrictJson(null, "Panerių", "Ūmėdžių g. 9", 500, countyId);
        ResponseEntity<DistrictReport> respPaneriuDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(paneriuDistrictCreate), DistrictReport.class);
        String karoluDistrictCreate = MyUtils.getDistrictJson(null, "Karolų", "Koralų g 15", 1000, countyId);
        ResponseEntity<DistrictReport> respKaroluDistrict = rest.postForEntity("/district", MyUtils.parseStringToJson(karoluDistrictCreate), DistrictReport.class);
        //votes
        final DistrictEntity paneriuDistrict = districtRepository.findAll().get(0);
        final DistrictEntity karoluDistrict = districtRepository.findAll().get(1);

        final PartyEntity p1 = partyRepository.getById(darboPartyID);
        final PartyEntity p2 = partyRepository.getById(skaidruoliuPartyId);
        final PartyEntity spoiled = new PartyEntity();
        spoiled.setId(-1991L);
        ResultMultiEntity party1PaneriuVotes = MyUtils.getResultMultiEntity(darboPartyEntity, paneriuDistrict, 200L);
        ResultMultiEntity party2PaneriuVotes = MyUtils.getResultMultiEntity(skaidruoliuPartyEntity, paneriuDistrict, 200L);
        ResultMultiEntity paneriuSpoiledVotes = MyUtils.getResultMultiEntity(spoiled, paneriuDistrict, 100L);
        ResultMultiEntity party1KaroluVotes = MyUtils.getResultMultiEntity(darboPartyEntity, karoluDistrict, 150L);
        ResultMultiEntity party2KaroluVotes = MyUtils.getResultMultiEntity(skaidruoliuPartyEntity, karoluDistrict, 100L);
        ResultMultiEntity karoluSpoiledVotes = MyUtils.getResultMultiEntity(spoiled, karoluDistrict, 100L);

        List<ResultMultiEntity> paneriuResults = new ArrayList<>();
        paneriuResults.add(party1PaneriuVotes);
        paneriuResults.add(party2PaneriuVotes);
        paneriuResults.add(paneriuSpoiledVotes);

        List<ResultMultiEntity> karoluResults = new ArrayList<>();
        karoluResults.add(party1KaroluVotes);
        karoluResults.add(party2KaroluVotes);
        karoluResults.add(karoluSpoiledVotes);

        String savePaneriuResults = resultMultiService.save(paneriuResults);
        String saveKaroluResults = resultMultiService.save(karoluResults);
        String approveKaroluResults = resultMultiService.approve(respKaroluDistrict.getBody().getId());

        //verify
        assertThat(savePaneriuResults, CoreMatchers.is("Votes registered"));
        assertThat(saveKaroluResults, CoreMatchers.is("Votes registered"));
        assertThat(approveKaroluResults, CoreMatchers.is("Votes Approved"));
        assertThat(resultMultiRepository.findAll().size(), CoreMatchers.is(4));
    }

    @TestConfiguration
    static class Config {

        @Bean
        @Primary
        public CountyRepository repository() {
            return new CountyRepository();
        }

    }

    private List<CandidateEntity> parseCSVToCandidates(String path, String entity) throws FileNotFoundException {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        csvParser.setPartyOrCounty(entity);
        return csvParser.extractCandidates(fis);
    }

}