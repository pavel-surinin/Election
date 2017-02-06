package lt.itakademija.electors.party;

import lt.itakademija.Application;
import lt.itakademija.electors.MyUtils;
import lt.itakademija.electors.candidate.CandidateRepository;
import lt.itakademija.electors.county.CountyControllerTest;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit4.SpringRunner;
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

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void saveParty() throws Exception {
        final int sizeBeforeSave = partyRepository.findAll().size();
        final MultipartFile file = MyUtils.parseToMultiPart("test-csv/data-party-1.csv");
        String name = "Ereliu Partija";
        Integer number = 45;
        partyService.save(name, number, file);
        final int sizeAfterSave = partyRepository.findAll().size();
        assertThat(sizeBeforeSave, CoreMatchers.is(sizeAfterSave-1));
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