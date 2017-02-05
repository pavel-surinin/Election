package lt.itakademija.electors.county;

import lt.itakademija.Application;
import lt.itakademija.electors.MyJsonParser;
import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.candidate.CandidateRepository;
import lt.itakademija.electors.candidate.CandidateService;
import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.district.DistrictReport;
import lt.itakademija.electors.district.DistrictRepository;
import lt.itakademija.electors.district.DistrictService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    DistrictService districtService;

    @Autowired
    TransactionTemplate transactionTemplate;

    String URI = "/county";


    @Before
    public void setUp() throws Exception {
        String VilniusString = "{\"name\":\"VilniusTest\"}";
        rest.postForEntity(URI, MyJsonParser.parse(VilniusString), CountyEntity.class);
        String KaunasString = "{\"name\":\"KaunasTest\"}";
        rest.postForEntity(URI, MyJsonParser.parse(KaunasString), CountyEntity.class);
        String KlaipedaString = "{\"name\":\"KlaipedaTest\"}";
        rest.postForEntity(URI, MyJsonParser.parse(KlaipedaString), CountyEntity.class);
    }

    @After
    public void tearDown() throws Exception {
        countyRepository.findAll().stream().forEach(c -> countyService.delete(c.getId()));
        candidateRepository.getCandidatesList().stream().forEach(c -> candidateService.delete(c.getId()));
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
        ResponseEntity<CountyEntity> respCreate = rest.postForEntity(URI, MyJsonParser.parse(VilniusString), CountyEntity.class);
        //verify
        assertThat(respCreate.getStatusCodeValue(), CoreMatchers.is(200));
    }

    @Test
    public void updateName() throws Exception {
        //setup
        String kedainiaiString = "{\"name\":\"KedainiaiTest\"}";
        ResponseEntity<CountyEntity> respCreate = rest.postForEntity(URI, MyJsonParser.parse(kedainiaiString), CountyEntity.class);
        //exercise
        Long id = respCreate.getBody().getId();
        String kedainiaiUpdateString = "{\"id\":" + id + ", \"name\" : \"KedainiaiUpdate\"}";
        ResponseEntity<CountyEntity> respUpdate = rest.postForEntity(URI, MyJsonParser.parse(kedainiaiUpdateString), CountyEntity.class);
        //verify
        assertThat(respCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respUpdate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respUpdate.getBody().getName(), CoreMatchers.is("KedainiaiUpdate"));
    }

    @Test
    public void uploadSingleCandidates() throws Exception {
        //setup
        MultipartFile result = getMultipartFile("test-csv/data-county-non-party.csv");
        //execute
        ResponseEntity<CountyReport[]> resp1 = rest.getForEntity("/county", CountyReport[].class);
        final Long id = resp1.getBody()[0].getId();
        countyService.update(id, result);
        ResponseEntity<CountyReport[]> resp = rest.getForEntity("/county", CountyReport[].class);
        //verify
        assertThat(resp1.getBody()[0].getCandidatesCount(), CoreMatchers.is(0));
        assertThat(resp.getBody()[0].getCandidatesCount(), CoreMatchers.is(3));

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
                MyJsonParser.parse(jsonDistrictCreate),
                DistrictReport.class);
        uploadSingleCandidates();
        ResponseEntity<CountyDetailsReport> respDetailsAfter = rest.getForEntity("/county/" + countyId, CountyDetailsReport.class);
        final Long districtId = respCreate.getBody().getId();
        //verify
        assertThat(respCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respDetailsBefore.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respCreate.getBody().getName(), CoreMatchers.is("Panerių"));
        assertThat(respDetailsAfter.getBody().getCandidates().size(), CoreMatchers.is(3));
//        assertThat(respDetailsAfter.getBody().getDistricts().size(), CoreMatchers.is(1));


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
        //verify
        assertThat(countyRepository.findAll().size(), CoreMatchers.is(0));
        assertThat(districtRepository.findAll().size(), CoreMatchers.is(0));
        assertThat(candidateRepository.getCandidatesList().size(), CoreMatchers.is(3));

    }

    @Test
    public void deleteCountyWithResultsTest() {
        //setup adding candidates
        MultipartFile result = getMultipartFile("test-csv/data-county-non-party.csv");
        ResponseEntity<CountyReport[]> resp1 = rest.getForEntity("/county", CountyReport[].class);
        final Long id = resp1.getBody()[0].getId();
        countyService.update(id, result);
        //setup addnig district
        final Long countyId = countyRepository.findAll().get(0).getId();
        String jsonDistrictCreate = "{\"name\" : \"Panerių\",\"adress\" : \"Ūmėdžių g. 9\",\"numberOfElectors\":500,\"county\":{\"id\":" + countyId + "}}";
        ResponseEntity<DistrictReport> respCreateDistrict;
        respCreateDistrict = rest.postForEntity("/district", MyJsonParser.parse(jsonDistrictCreate), DistrictReport.class);
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
        countyRepository.findAll().stream().forEach(c -> countyService.delete(c.getId()));
        assertThat(resultSingleRepository.findAll().size(), CoreMatchers.is(0));
    }

    @TestConfiguration
    static class Config {

        @Bean
        @Primary
        public CandidateRepository repository() {
            return new CandidateRepository();
        }

    }

    private List<CandidateEntity> parseCSVToCandidates(String path, String entity) throws FileNotFoundException {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        csvParser.setPartyOrCounty(entity);
        return csvParser.extractCandidates(fis);
    }

    private MultipartFile getMultipartFile(String string) {
        Path path = Paths.get(string);
        String name = path.getFileName().toFile().getName();
        String originalFileName = path.getFileName().toFile().getName();
        String contentType = "multipart/form-data";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        return new MockMultipartFile(name, originalFileName, contentType, content);
    }
}

