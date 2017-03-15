package lt.itakademija.electors.party;

import lt.itakademija.Application;
import lt.itakademija.electors.MyUtils;
import lt.itakademija.electors.candidate.*;
import lt.itakademija.electors.county.*;
import lt.itakademija.exceptions.BadCSVFileException;
import lt.itakademija.exceptions.PartyNameCloneException;
import lt.itakademija.exceptions.PartyNumberCloneException;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.Assert.*;

/**
 * Created by Pavel on 2017-02-06.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {PartyControllerTest.Config.class, Application.class})
public class PartyControllerTest {

    @Autowired
    PartyRepository partyRepository;

    @Autowired
    PartyService partyService;


    @Autowired
    TestRestTemplate rest;

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    CandidateService candidateService;

    @Autowired
    CountyService countyService;

    @Autowired
    CountyRepository countyRepository;

    @Autowired
    TransactionTemplate transactionTemplate;

    String URI = "/party";

    @Before
    public void setUp() throws Exception {
        final MultipartFile file1 = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name1 = "Balvanu Partija";
        Integer number1 = 45;
        partyService.save(name1, number1, file1);

        final MultipartFile file2 = MyUtils.parseToMultiPart("test-csv/data-party-2.csv");
        String name2 = "Ereliu Partija";
        Integer number2 = 46;

        partyService.save(name2, number2, file2);

        final MultipartFile file3 = MyUtils.parseToMultiPart("test-csv/data-party-3.csv");
        String name3 = "IT Partija";
        Integer number3 = 47;

        partyService.save(name3, number3, file3);
    }

    @After
    public void tearDown() throws Exception {
        partyRepository.findAll().stream().forEach(p -> partyService.delete(p.getId()));
        candidateRepository.getCandidatesList().stream().forEach(c -> candidateService.delete(c.getId()));
    }

    @Transactional
    @Test
    public void savePartyWithExistingNumber() throws Exception {
        final int sizeBeforeSave = partyRepository.findAll().size();
        final MultipartFile file = MyUtils.parseToMultiPart("test-csv/data-party-4.csv");
        String name = "Testeriu Partija";
        Integer number = 45;
        try {
            partyService.save(name, number, file);
            final int sizeAfterSave = partyRepository.findAll().size();
            assertThat(sizeBeforeSave, CoreMatchers.is(sizeAfterSave - 1));
        } catch (PartyNumberCloneException e) {
            assertThat(e.getMessage(), CoreMatchers.is("Party exists with number " + number));
        }
    }

    @Test
    public void savePartyWithExistingName() throws Exception {

        final int sizeBeforeSave = partyRepository.findAll().size();
        final MultipartFile file = MyUtils.parseToMultiPart("test-csv/data-party-4.csv");
        String name = "Testeriu Partija";
        Integer number = 21;
        try {
            partyService.save(name, number, file);
            final int sizeAfterSave = partyRepository.findAll().size();
            assertThat(sizeBeforeSave, CoreMatchers.is(sizeAfterSave - 1));
        } catch (PartyNameCloneException e) {
            assertThat(e.getMessage(), CoreMatchers.is("Party exists with name " + name));
        }

    }

    @Test
    public void savePartyWithLtName() throws Exception {
        final int sizeBeforeSave = partyRepository.findAll().size();
        final MultipartFile file = MyUtils.parseToMultiPart("test-csv/data-party-5.csv");
        String name = "Tėsterių pąrtįjąą ";
        Integer number = 16;
        partyService.save(name, number, file);
        final int sizeAfterSave = partyRepository.findAll().size();
        assertThat(sizeBeforeSave, CoreMatchers.is(sizeAfterSave - 1));
    }

    @Test
    public void savePartyWithBadCsvData() throws Exception {
        final int sizeBeforeSave = partyRepository.findAll().size();
        final MultipartFile file = MyUtils.parseToMultiPart("test-csv/bad-data3.csv");
        String name = "Testeriu Partija";
        Integer number = 45;
        try {
            partyService.save(name, number, file);
            final int sizeAfterSave = partyRepository.findAll().size();
            assertThat(sizeBeforeSave, CoreMatchers.is(sizeAfterSave - 1));
        } catch (BadCSVFileException e) {
            assertThat(e.getMessage(), CoreMatchers.is("Not acceptable csv data for party-list"));
        }
    }

    @Test
    public void updatePartyName() throws Exception {
        final MultipartFile file = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name = "Testeriu Partija";
        Integer number = 49;
        partyService.save(name, number, file);
        Long id = partyRepository.getPartyByNumber(49).getId();
        String updatedName = "Testuotoju partija";
        partyService.save(id, updatedName, number, file);
        assertThat(partyRepository.getById(id).getName(), CoreMatchers.is(updatedName));
        assertThat(partyRepository.getById(id).getPartyNumber(), CoreMatchers.is(number));
    }

    @Test
    public void updatePartyNumber() {
        final MultipartFile file = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name = "Testeriu Partija";
        Integer number = 49;
        partyService.save(name, number, file);
        Long id = partyRepository.getPartyByNumber(49).getId();
        Integer updatedNumber = 69;
        partyService.save(id, name, updatedNumber, file);
        assertThat(partyRepository.getById(id).getPartyNumber(), CoreMatchers.is(updatedNumber));
    }

    @Test
    public void updatePartyNumberWithExistingNumber() {
        final int sizeBeforeSave = partyRepository.findAll().size();
        final MultipartFile file = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name = "Egzistuojančių numerių partija";
        Integer number = 101;
        partyService.save(name, number, file);
        Long id = partyRepository.getPartyByNumber(101).getId();
        Integer updatedNumber = 45;
        try {
            partyService.save(id, name, updatedNumber, file);
            final int sizeAfterSave = partyRepository.findAll().size();
            assertThat(sizeBeforeSave, CoreMatchers.is(sizeAfterSave - 1));
        } catch (PartyNumberCloneException e) {
            assertThat(e.getMessage(), CoreMatchers.is("Party exists with number " + updatedNumber));
        }
    }

    @Test
    public void updatePartyNameWithExistingName() throws Exception {
        final MultipartFile file = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name = "Testeriu Partija";
        Integer number = 49;
        partyService.save(name, number, file);
        Long id = partyRepository.getPartyByNumber(49).getId();
        String updatedName = "IT Partija";
        try {
            partyService.save(id, updatedName, number, file);
        } catch (PartyNameCloneException e) {
            assertThat(e.getMessage(), CoreMatchers.is("Party exists with name " + updatedName));
        }
    }

    @Test
    public void deleteParty(){
        final MultipartFile file = MyUtils.parseToMultiPart("test-csv/data-party-5.csv");
        String name = "Nolife partija";
        Integer number = 420;
        partyService.save(name, number, file);
        final int sizeBeforeDel = partyRepository.findAll().size();
        Long id = partyService.getPartyByNumber(number).getId();
        partyService.delete(id);
        final int sizeAfterDel = partyRepository.findAll().size();
        assertThat(sizeAfterDel,CoreMatchers.is(sizeBeforeDel-1));

    }

    @Test
    public void NotMultilistCheckOnPartyListDelete(){
        final MultipartFile file = MyUtils.parseToMultiPart("test-csv/Good_Darbo_Party_candidate_data.csv");
        String name = "Custom test Partija";
        Integer number = 111;
        partyService.save(name, number, file);
        ResponseEntity<PartyReport[]> partyResponse = rest.getForEntity("/party", PartyReport[].class);
        ResponseEntity<CountyReport[]> countyResponse = rest.getForEntity("/county", CountyReport[].class);
        String VilniusString = "{\"name\":\"VilniusTest\"}";
        rest.postForEntity("/county", MyUtils.parseStringToJson(VilniusString), CountyEntity.class);
        final Long vilniusId = countyRepository.findAll().get(0).getId();
        MultipartFile mixedFile = MyUtils.parseToMultiPart("test-csv/data-county-party-notmultilist.csv");
        Boolean executeUpdate = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                countyService.update(vilniusId, mixedFile);
                return true;
            }
        });
        candidateService.deleteCandidatesByPartyId(partyResponse.getBody()[3].getId());
        Integer execute = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                int size = partyService.getPartyByNumber(number).getMembers().size();
                return size;
            }
        });
        assertThat(execute,CoreMatchers.is(1));
    }

    @Test
    public void deleteFileParty() {
        //setup
        final MultipartFile file = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name = "Testeriu Partija Test";
        Integer number = 49;
        partyService.save(name, number, file);
        //exercise
        ResponseEntity<PartyRepository[]> response = rest.getForEntity(URI, PartyRepository[].class);
        Long id = partyRepository.getPartyByNumber(number).getId();
        candidateService.deleteCandidatesByPartyId(id);
        ResponseEntity<CandidateReport[]> responseCandidates = rest.getForEntity("/candidate", CandidateReport[].class);
        ResponseEntity<CandidateReport[]> responseCandidatesDelete = rest.getForEntity("/candidate", CandidateReport[].class);
        assertThat(responseCandidates.getBody().length, CoreMatchers.is(responseCandidatesDelete.getBody().length));
    }
    @TestConfiguration
    static class Config{
        @Bean
        @Primary
        public PartyRepository repository() {
            return new PartyRepository();
        }
    }
}