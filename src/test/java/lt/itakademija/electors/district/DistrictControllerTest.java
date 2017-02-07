package lt.itakademija.electors.district;

import lt.itakademija.Application;
import lt.itakademija.electors.MyUtils;
import lt.itakademija.electors.county.CountyEntity;
import lt.itakademija.electors.county.CountyRepository;
import lt.itakademija.electors.county.CountyService;
import lt.itakademija.electors.representative.DistrictRepresentativeEntity;
import lt.itakademija.electors.representative.DistrictRepresentativeReport;
import lt.itakademija.electors.representative.DistrictRepresentativeRepository;
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

import static org.junit.Assert.assertThat;

/**
 * Created by VytautasL on 2/5/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {DistrictControllerTest.Config.class, Application.class})
public class DistrictControllerTest {

    @Autowired
    TestRestTemplate rest;

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    DistrictService districtService;

    @Autowired
    CountyRepository countyRepository;

    @Autowired
    CountyService countyService;

    @Autowired
    DistrictRepresentativeRepository districtRepresentativeRepository;

    String URI = "/district";

    @Before
    public void setUp() throws Exception {

        districtRepository.findAll().stream().forEach(d -> districtService.delete(d.getId()));
        countyRepository.findAll().stream().forEach(c -> countyService.delete(c.getId()));

        String vilniusString = MyUtils.getCountyJson(null, "Vilniaus");
        ResponseEntity<CountyEntity> countyEntityResponseEntity = rest.postForEntity("/county", MyUtils.parseStringToJson(vilniusString), CountyEntity.class);

        String centerDistrictString = MyUtils.getDistrictJson(null, "CenterTest", "Centro g. 1-1", 1000, (long) 1);
        rest.postForEntity(URI, MyUtils.parseStringToJson(centerDistrictString), DistrictEntity.class);

        String newTownDistrictString = MyUtils.getDistrictJson(null, "newTownTest", "Naujamiestčio g 1-2", 2000, (long) 1);
        rest.postForEntity(URI, MyUtils.parseStringToJson(newTownDistrictString), DistrictEntity.class);

        String centerDistrictRepresentative = MyUtils.getDistrictRepresentativeJson("CenterTest", "Representative", (long) 1);
        rest.postForEntity("/representative", MyUtils.parseStringToJson(centerDistrictRepresentative), DistrictRepresentativeEntity.class);

        String newTowDistrictRepresentative = MyUtils.getDistrictRepresentativeJson("NewTownTest", "Representative", (long) 2);
        rest.postForEntity("/representative", MyUtils.parseStringToJson(newTowDistrictRepresentative), DistrictRepresentativeEntity.class);
    }

    @After
    public void tearDown() throws Exception {
        districtRepository.findAll().stream().forEach(d -> districtService.delete(d.getId()));
        countyRepository.findAll().stream().forEach(c -> countyService.delete(c.getId()));
    }

    @Test
    public void save() throws Exception {
        //setup
        String kaunoString = MyUtils.getCountyJson(null, "kaunoTest");
        ResponseEntity<CountyEntity> respCountyCreate = rest.postForEntity("/county", MyUtils.parseStringToJson(kaunoString), CountyEntity.class);
        Long id = respCountyCreate.getBody().getId();
        String centerDistrictString = MyUtils.getDistrictJson(null, "CenterTest", "Centro g. 1-1", 1000, id);
        ResponseEntity<DistrictEntity> respDistrictCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(centerDistrictString), DistrictEntity.class);
        Long districtId = respDistrictCreate.getBody().getId();
        String centerDistrictRepresentative = MyUtils.getDistrictRepresentativeJson("CenterTest", "Representative", districtId);
        ResponseEntity<DistrictRepresentativeEntity> respDistrictRepresentativeCreate = rest.postForEntity("/representative", MyUtils.parseStringToJson(centerDistrictRepresentative), DistrictRepresentativeEntity.class);
        //verify
        assertThat(respCountyCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respDistrictCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respDistrictRepresentativeCreate.getStatusCodeValue(), CoreMatchers.is(200));

    }

    @Test
    public void getDistrictList() throws Exception {
        //setup
        ResponseEntity<DistrictReport[]> resp = rest.getForEntity(URI, DistrictReport[].class);
        //verify
        assertThat(resp.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(districtRepository.findAll().size(), CoreMatchers.is(resp.getBody().length));
    }

    @Test
    public void updateDistrictsName() throws Exception {
        //setup
        String vilniusString = MyUtils.getCountyJson(null, "VilniausTest");
        ResponseEntity<CountyEntity> respCountyCreate = rest.postForEntity("/county", MyUtils.parseStringToJson(vilniusString), CountyEntity.class);
        Long countyId = respCountyCreate.getBody().getId();
        String pylimoDistrictString = MyUtils.getDistrictJson(null, "PylimoTest", "Pylimo g. 22-33", 450, countyId);
        ResponseEntity<DistrictEntity> respDistrictCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(pylimoDistrictString), DistrictEntity.class);
        //exercise
        Long districtId = respDistrictCreate.getBody().getId();
        String pylimoUpdateDistrictString = MyUtils.getDistrictJson(districtId, "PylimoUpdateTest", "Pylimo g. 22-33", 450, countyId);
        ResponseEntity<DistrictEntity> respDistrictUpdate = rest.postForEntity(URI, MyUtils.parseStringToJson(pylimoUpdateDistrictString), DistrictEntity.class);
        //verify
        assertThat(respCountyCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respDistrictCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respDistrictUpdate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(districtRepository.findById(districtId).getName(), CoreMatchers.is("PylimoUpdateTest"));
    }

    @Test
    public void updateDistrictsAdress() throws Exception {
        //setup
        String vilniusString = MyUtils.getCountyJson(null, "VilniausTest");
        ResponseEntity<CountyEntity> respCountyCreate = rest.postForEntity("/county", MyUtils.parseStringToJson(vilniusString), CountyEntity.class);
        Long countyId = respCountyCreate.getBody().getId();
        String pylimoDistrictString = MyUtils.getDistrictJson(null, "PylimoTest", "Pylimo g. 22-33", 450, countyId);
        ResponseEntity<DistrictEntity> respDistrictCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(pylimoDistrictString), DistrictEntity.class);
        //exercise
        Long districtId = respDistrictCreate.getBody().getId();
        String pylimoUpdateAdressDistrictString = MyUtils.getDistrictJson(districtId, "PylimoTest", "PylimoTest g. 33-22", 450, countyId);
        ResponseEntity<DistrictEntity> respDistrictUpdate = rest.postForEntity(URI, MyUtils.parseStringToJson(pylimoUpdateAdressDistrictString), DistrictEntity.class);
        //verify
        assertThat(respCountyCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respDistrictCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(districtRepository.findById(districtId).getAdress(), CoreMatchers.is("PylimoTest g. 33-22"));
    }

    @Test
    public void updateDistrictsNumberOfElectors() throws Exception {
        //setup
        String vilniusString = MyUtils.getCountyJson(null, "VilniausTest");
        ResponseEntity<CountyEntity> respCountyCreate = rest.postForEntity("/county", MyUtils.parseStringToJson(vilniusString), CountyEntity.class);
        Long countyId = respCountyCreate.getBody().getId();
        String pylimoDistrictString = MyUtils.getDistrictJson(null, "PylimoTest", "Pylimo g. 22-33", 450, countyId);
        ResponseEntity<DistrictEntity> respDistrictCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(pylimoDistrictString), DistrictEntity.class);
        //exercise
        Long districtId = respDistrictCreate.getBody().getId();
        String pylimoUpdateElectorsDistrictString = MyUtils.getDistrictJson(districtId, "PylimoTest", "Pylimo g. 22-33", 1450, countyId);
        ResponseEntity<DistrictEntity> respDistrictUpdate = rest.postForEntity(URI, MyUtils.parseStringToJson(pylimoUpdateElectorsDistrictString), DistrictEntity.class);
        //verify
        assertThat(respCountyCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respDistrictCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(districtRepository.findById(districtId).getNumberOfElectors().intValue(), CoreMatchers.is(1450));
    }

    @Test
    public void updateDistrictsCounty() throws Exception {
        //setup
        String vilniusString = MyUtils.getCountyJson(null, "VilniausTest");
        ResponseEntity<CountyEntity> respCountyCreate = rest.postForEntity("/county", MyUtils.parseStringToJson(vilniusString), CountyEntity.class);
        String kaunoString = MyUtils.getCountyJson(null, "kaunoTest");
        ResponseEntity<CountyEntity> respKaunoCountyCreate = rest.postForEntity("/county", MyUtils.parseStringToJson(kaunoString), CountyEntity.class);
        Long countyId = respCountyCreate.getBody().getId();
        Long kaunoCountyId = respKaunoCountyCreate.getBody().getId();
        String pylimoDistrictString = MyUtils.getDistrictJson(null, "PylimoTest", "Pylimo g. 22-33", 450, countyId);
        ResponseEntity<DistrictEntity> respDistrictCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(pylimoDistrictString), DistrictEntity.class);
        //exercise
        Long districtId = respDistrictCreate.getBody().getId();
        String pylimoUpdateCountyDistrictString = MyUtils.getDistrictJson(districtId, "PylimoTest", "Pylimo g. 22-33", 450, kaunoCountyId);
        ResponseEntity<DistrictEntity> respDistrictUpdate = rest.postForEntity(URI, MyUtils.parseStringToJson(pylimoUpdateCountyDistrictString), DistrictEntity.class);
        //verify
        assertThat(respCountyCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respDistrictCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(districtRepository.findById(districtId).getCounty().getId(), CoreMatchers.is(kaunoCountyId));
    }

    @Test
    public void updateDistrictsCountyToCountyWhereIsSameNameDistrict() throws Exception {
        //setup
        String vilniusString = MyUtils.getCountyJson(null, "VilniausTest");
        ResponseEntity<CountyEntity> respVilniausCountyCreate = rest.postForEntity("/county", MyUtils.parseStringToJson(vilniusString), CountyEntity.class);
        String kaunoString = MyUtils.getCountyJson(null, "kaunoTest");
        ResponseEntity<CountyEntity> respKaunoCountyCreate = rest.postForEntity("/county", MyUtils.parseStringToJson(kaunoString), CountyEntity.class);
        Long vilniusCountyId = respVilniausCountyCreate.getBody().getId();
        Long kaunoCountyId = respKaunoCountyCreate.getBody().getId();
        String pylimoVilniausDistrictString = MyUtils.getDistrictJson(null, "PylimoTest", "Pylimo g. 22-33", 1450, vilniusCountyId);
        ResponseEntity<DistrictEntity> respVilniausPylimoDistrictCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(pylimoVilniausDistrictString), DistrictEntity.class);
        String pylimoKaunoDistrictString = MyUtils.getDistrictJson(null, "PylimoTest", "Kauno g. 22-33", 550, kaunoCountyId);
        ResponseEntity<DistrictEntity> respKaunoPylimoDistrictCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(pylimoKaunoDistrictString), DistrictEntity.class);
        //exercise
        Long districtId = respKaunoPylimoDistrictCreate.getBody().getId();
        String pylimoUpdateKaunoCountyDistrictString = MyUtils.getDistrictJson(districtId, "PylimoTest", "Kauno g. 22-33", 450, vilniusCountyId);
        ResponseEntity<DistrictEntity> respDistrictUpdate = rest.postForEntity(URI, MyUtils.parseStringToJson(pylimoUpdateKaunoCountyDistrictString), DistrictEntity.class);
        ResponseEntity<DistrictReport[]> resp = rest.getForEntity(URI, DistrictReport[].class);
        //verify
        assertThat(respVilniausCountyCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respVilniausPylimoDistrictCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respKaunoCountyCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respKaunoPylimoDistrictCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respDistrictUpdate.getStatusCodeValue(), CoreMatchers.is(409));
    }

    @Test
    public void deleteDistricts() throws Exception {
        //setup
        String vilniusString = MyUtils.getCountyJson(null, "VilniausTest");
        ResponseEntity<CountyEntity> respCountyCreate = rest.postForEntity("/county", MyUtils.parseStringToJson(vilniusString), CountyEntity.class);
        Long countyId = respCountyCreate.getBody().getId();
        String pylimoDistrictString = MyUtils.getDistrictJson(null, "PylimoTest", "Pylimo g. 22-33", 450, countyId);
        ResponseEntity<DistrictEntity> respPylimoDistrictCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(pylimoDistrictString), DistrictEntity.class);
        String centroDistrictString = MyUtils.getDistrictJson(null, "CentroTest", "Centro g. 1-2", 1450, countyId);
        ResponseEntity<DistrictEntity> respCentroDistrictCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(centroDistrictString), DistrictEntity.class);
        //exercise
        districtService.delete(respCentroDistrictCreate.getBody().getId());
        districtService.delete(respPylimoDistrictCreate.getBody().getId());
        ResponseEntity<DistrictReport[]> resp = rest.getForEntity(URI, DistrictReport[].class);
        //verify
        assertThat(respCountyCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respPylimoDistrictCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respCentroDistrictCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(districtRepository.findAll().size(), CoreMatchers.is(resp.getBody().length));
    }

    @Test
    public void deleteDistrictById() throws Exception {
        //setup
        String vilniusString = MyUtils.getCountyJson(null, "VilniausTest");
        ResponseEntity<CountyEntity> respCountyCreate = rest.postForEntity("/county", MyUtils.parseStringToJson(vilniusString), CountyEntity.class);
        Long countyId = respCountyCreate.getBody().getId();
        String pylimoDistrictString = MyUtils.getDistrictJson(null, "PylimoTest", "Pylimo g. 1-1", 450, countyId);
        ResponseEntity<DistrictEntity> respPylimoDistrictCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(pylimoDistrictString), DistrictEntity.class);
        String centroDistrictString = MyUtils.getDistrictJson(null, "CentroTest", "Centro g. 1-2", 1450, countyId);
        ResponseEntity<DistrictEntity> respCentroDistrictCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(centroDistrictString), DistrictEntity.class);
        String newTownDistrictString = MyUtils.getDistrictJson(null, "NewTownTest", "Naujamiesčio g. 1-3", 800, countyId);
        ResponseEntity<DistrictEntity> respNewTownDistrictCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(newTownDistrictString), DistrictEntity.class);
        //exercise
        districtService.delete(respPylimoDistrictCreate.getBody().getId());
        ResponseEntity<DistrictReport[]> resp = rest.getForEntity(URI, DistrictReport[].class);
        //verify
        assertThat(respCountyCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respCentroDistrictCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respNewTownDistrictCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(districtRepository.findAll().size(), CoreMatchers.is(resp.getBody().length));
    }

    @Test
    public void deleteDistrictAndDistrictsRepresentativeAutoDelete() throws Exception {
        //setup
        String vilniusString = MyUtils.getCountyJson(null, "VilniausTest");
        ResponseEntity<CountyEntity> respCountyCreate = rest.postForEntity("/county", MyUtils.parseStringToJson(vilniusString), CountyEntity.class);
        Long countyId = respCountyCreate.getBody().getId();
        String pylimoDistrictString = MyUtils.getDistrictJson(null, "PylimoTest", "Pylimo g. 1-1", 450, countyId);
        ResponseEntity<DistrictEntity> respPylimoDistrictCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(pylimoDistrictString), DistrictEntity.class);
        String centroDistrictString = MyUtils.getDistrictJson(null, "CentroTest", "Centro g. 1-2", 1450, countyId);
        ResponseEntity<DistrictEntity> respCentroDistrictCreate = rest.postForEntity(URI, MyUtils.parseStringToJson(centroDistrictString), DistrictEntity.class);
        Long pylimoDistrictId = respPylimoDistrictCreate.getBody().getId();
        Long centroDistrictId = respCentroDistrictCreate.getBody().getId();
        String pylimoDistrictsRepresentative = MyUtils.getDistrictRepresentativeJson("PylimoAtstovasTest", "RepresentativeTest", pylimoDistrictId);
        ResponseEntity<DistrictRepresentativeEntity> respPylimoDistrictRepresentativeCreate = rest.postForEntity("/representative", MyUtils.parseStringToJson(pylimoDistrictsRepresentative), DistrictRepresentativeEntity.class);
        String centroDistrictsRepresentative = MyUtils.getDistrictRepresentativeJson("CentroAtstovasTest", "RepresentativeTest", centroDistrictId);
        ResponseEntity<DistrictRepresentativeEntity> respCentroDistrictRepresentativeCreate = rest.postForEntity("/representative", MyUtils.parseStringToJson(centroDistrictsRepresentative), DistrictRepresentativeEntity.class);
        //exercise
        districtService.delete(respPylimoDistrictCreate.getBody().getId());
        ResponseEntity<DistrictReport[]> resp = rest.getForEntity(URI, DistrictReport[].class);
        ResponseEntity<DistrictRepresentativeReport[]> respRepresentative = rest.getForEntity("/representative", DistrictRepresentativeReport[].class);
        //verify
        assertThat(respCountyCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respCentroDistrictCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respCentroDistrictRepresentativeCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(resp.getBody().length, CoreMatchers.is(districtRepository.findAll().size()));
        assertThat(districtRepresentativeRepository.findAll().size(), CoreMatchers.is(respRepresentative.getBody().length));
    }

    @TestConfiguration
    static class Config {
        @Bean
        @Primary
        public DistrictService service() {
            return new DistrictService();
        }
    }
}
