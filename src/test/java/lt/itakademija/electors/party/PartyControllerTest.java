package lt.itakademija.electors.party;

import lt.itakademija.Application;
import lt.itakademija.electors.MyUtils;
import lt.itakademija.electors.candidate.CandidateRepository;
import lt.itakademija.electors.county.CountyControllerTest;
import lt.itakademija.electors.county.CountyReport;
import lt.itakademija.exceptions.BadCSVFileExceprion;
import lt.itakademija.exceptions.PartyNameCloneException;
import lt.itakademija.exceptions.PartyNumberCloneException;
import org.codehaus.groovy.runtime.powerassert.SourceText;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
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
    PartyReport partyReport;

    @Autowired
    TestRestTemplate rest;

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

    }

    @Test
    public void saveExistingNumberParty() throws Exception {
        //setpu
        final int sizeBeforeSave = partyRepository.findAll().size();
        final MultipartFile file = MyUtils.parseToMultiPart("test-csv/data-party-4.csv");
        String name = "Testeriu Partija";
        Integer number = 45;
        //verify
        try {
            partyService.save(name, number, file);
            final int sizeAfterSave = partyRepository.findAll().size();
            assertThat(sizeBeforeSave, CoreMatchers.is(sizeAfterSave - 1));
        } catch (PartyNumberCloneException e) {
            assertThat(e.getMessage(), CoreMatchers.is("Party exists with number " + number));
        }
    }


    @TestConfiguration
    static class Config {

        @Bean
        @Primary
        public CandidateRepository repository() {
            return new CandidateRepository();
        }
    }

}