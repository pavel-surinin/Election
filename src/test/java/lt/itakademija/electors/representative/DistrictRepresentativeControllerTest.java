package lt.itakademija.electors.representative;

import lt.itakademija.Application;
import lt.itakademija.electors.MyUtils;
import lt.itakademija.electors.county.CountyEntity;
import lt.itakademija.electors.county.CountyRepository;
import lt.itakademija.electors.county.CountyService;
import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.district.DistrictRepository;
import lt.itakademija.electors.district.DistrictService;
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
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created by nevyt on 2/4/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {DistrictRepresentativeControllerTest.Config.class,
        Application.class})

public class DistrictRepresentativeControllerTest {
    @Autowired
    TestRestTemplate rest;

    @Autowired
    DistrictRepresentativeRepository districtRepresentativeRepository;

    @Autowired
    DistrictRepresentativeService districtRepresentativeService;

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    DistrictService districtService;

    @Autowired
    CountyRepository countyRepository;

    @Autowired
    CountyService countyService;


    String URI = "/representative";


    @Before
    public void setUp() throws Exception {
        String countyTestString = MyUtils.getCountyJson(null, "ApygardaTestName");
        ResponseEntity<CountyEntity> countyCreateResponse = rest.postForEntity("/county",
                MyUtils.parseStringToJson(countyTestString), CountyEntity.class);

        String districtTestString = MyUtils.getDistrictJson(null, "TestuotojuApylinke", "Testuotoju g. 5",
                9001, countyCreateResponse.getBody().getId());
        ResponseEntity<DistrictEntity> districtCreateResponse = rest.postForEntity("/district",
                MyUtils.parseStringToJson(districtTestString), DistrictEntity.class);

        String representativeTestString = MyUtils.getDistrictRepresentativeJson("Testijus", "Testautavicius",
                districtCreateResponse.getBody().getId());
        ResponseEntity<DistrictRepresentativeEntity> representativeCreateResponse = rest.postForEntity(URI,
                MyUtils.parseStringToJson(representativeTestString), DistrictRepresentativeEntity.class);

    }

    @After
    public void tearDown() throws Exception {
        countyRepository.findAll().stream().forEach(c -> countyService.delete(c.getId()));
        districtRepository.findAll().stream().forEach(d -> districtService.delete(d.getId()));


    }

    @Transactional
    @Test
    public void getAllRepresentatives() throws Exception {
        ResponseEntity<DistrictRepresentativeReport[]> response = rest.getForEntity("/representative/", DistrictRepresentativeReport[].class);
        int lastRepresentative = districtRepresentativeRepository.findAll().size() - 1;
        String lastRepresentativeName = districtRepresentativeRepository.findAll().get(lastRepresentative).getName();
        assertThat(response.getBody().length, CoreMatchers.is(districtRepresentativeRepository.findAll().size()));
        assertThat(response.getBody()[lastRepresentative].getName(),CoreMatchers.is(lastRepresentativeName));

    }

    @Transactional
    @Test
    public void deleteRepresentativeByCountyDelete() {
        int lastCounty = countyRepository.findAll().size() - 1;
        Long id = countyRepository.findAll().get(lastCounty).getId();
        int sizeBeforeDel = districtRepresentativeRepository.findAll().size();
        countyService.delete(id);
        int sizeAfterDel = districtRepresentativeRepository.findAll().size();
        assertThat(sizeAfterDel, CoreMatchers.is(sizeBeforeDel-1));
    }

    @Transactional
    @Test
    public void deleteRepresentativeByDistrictDelete() {

        int lastDistrict = districtRepository.findAll().size() - 1;
        Long id = districtRepository.findAll().get(lastDistrict).getId();
        int sizeBeforeDel = districtRepresentativeRepository.findAll().size();
        districtRepository.delete(id);
        int sizeAfterDel = districtRepresentativeRepository.findAll().size();


        assertThat(sizeAfterDel, CoreMatchers.is(sizeBeforeDel-1));

    }

    @Test
    public void saveRepresentative() throws Exception {

        String countyTestString = MyUtils.getCountyJson(null, "ApygardaTestName2");
        ResponseEntity<CountyEntity> countyCreateResponse = rest.postForEntity("/county",
                MyUtils.parseStringToJson(countyTestString), CountyEntity.class);

        String districtTestString = MyUtils.getDistrictJson(null, "TestuotojuApylinke2", "Testuotoju g. 52",
                9001, countyCreateResponse.getBody().getId());
        ResponseEntity<DistrictEntity> districtCreateResponse = rest.postForEntity("/district",
                MyUtils.parseStringToJson(districtTestString), DistrictEntity.class);

        String representativeTestString = MyUtils.getDistrictRepresentativeJson("Testijus2", "Testautavicius2",
                districtCreateResponse.getBody().getId());
        ResponseEntity<DistrictRepresentativeEntity> representativeCreateResponse = rest.postForEntity(URI,
                MyUtils.parseStringToJson(representativeTestString), DistrictRepresentativeEntity.class);

        ResponseEntity<DistrictRepresentativeReport[]> response = rest.getForEntity(URI, DistrictRepresentativeReport[].class);

        int lastRepresentative = districtRepresentativeRepository.findAll().size() - 1;
        String lastRepresentativesName = districtRepresentativeRepository.findAll().get(lastRepresentative).getName();
        assertThat(response.getBody().length, CoreMatchers.is(districtRepresentativeRepository.findAll().size()));
        assertThat(response.getBody()[lastRepresentative].getName(),CoreMatchers.is(lastRepresentativesName));

    }

    @TestConfiguration
    static class Config {
        @Bean
        @Primary
        public DistrictRepresentativeService service() {
            return new DistrictRepresentativeService();
        }
    }
}