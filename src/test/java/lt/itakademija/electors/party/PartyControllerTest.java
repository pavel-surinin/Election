package lt.itakademija.electors.party;

import lt.itakademija.Application;
import lt.itakademija.electors.candidate.CandidateService;
import lt.itakademija.electors.district.DistrictReport;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
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

import static org.junit.Assert.*;

/**
 * Created by Pavel on 2017-01-23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {PartyControllerTest.Config.class,
        Application.class})
public class PartyControllerTest {

    @Autowired
    TestRestTemplate rest;

    @Ignore
    @Test
    public void getPartijaList() throws Exception {
        ResponseEntity<PartyReport[]> responseAll = rest.getForEntity("/party", PartyReport[].class);
        Assert.assertThat(responseAll.getBody().length, CoreMatchers.is(3));
    }

    @Ignore
    @Test
    public void getPartyById() throws Exception {
        ResponseEntity<PartyReport> responseAll = rest.getForEntity("/party/1", PartyReport.class);
        Assert.assertThat(responseAll.getBody().getId(),CoreMatchers.is(1L));
    }


    @TestConfiguration
    static class Config {
        @Bean
        @Primary
        public PartyService service() {
            return new PartyService();
        }
    }

}