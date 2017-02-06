package lt.itakademija.electors.district;

import lt.itakademija.Application;
import lt.itakademija.electors.MyJsonParser;
import lt.itakademija.electors.county.CountyEntity;
import lt.itakademija.electors.county.CountyRepository;
import lt.itakademija.electors.county.CountyService;
import lt.itakademija.electors.representative.DistrictRepresentativeEntity;
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

    String URI = "/district";
    public String getDistrictJsonString (Long districtId, String districtName, String districtAdress, int numberOfElectors, Long countyId){
        String districtString = "{\"id\" : \"" + districtId + "\",\"name\" : \"" + districtName + "\", \"adress\" : \"" + districtAdress + "\",\"numberOfElectors\" : " + numberOfElectors + ",\"county\" : {\"id\" : " + countyId + "}}";
        return districtString;
    }
    public String getCountyJsonString(Long countyId, String countyName){
        String countyString =  "{\"id\" : \"" + countyId + "\",\"name\" : \"" + countyName + "\"}";
        return countyString;
    }
    public String getDistrictRepresentativeJsonString (String name, String surname, Long representingDistrict){
        String representativeString = "{\"name\" : \"" + name + "\", \"surname\" : \"" + surname + "\", \"district\" : {\"id\" : " + representingDistrict + "}}";
        return representativeString;
    }

    @Before
    public void setUp() throws Exception {
        String vilniusString = getCountyJsonString(null,"Vilniaus");
        rest.postForEntity("/county", MyJsonParser.parse(vilniusString), CountyEntity.class);

        String centerDistrictString = getDistrictJsonString(null,"CenterTest", "Centro g. 1-1", 1000, (long) 1);
        rest.postForEntity(URI, MyJsonParser.parse(centerDistrictString), DistrictEntity.class);

        String newTownDistrictString = getDistrictJsonString(null, "newTownTest", "Naujamiestčio g 1-2", 2000, (long) 1);
        rest.postForEntity(URI, MyJsonParser.parse(newTownDistrictString), DistrictEntity.class);

        String centerDistrictRepresentative =  getDistrictRepresentativeJsonString("CenterTest", "Representative", (long)1);
        rest.postForEntity("/representative", MyJsonParser.parse(centerDistrictRepresentative), DistrictRepresentativeEntity.class);

        String newTowDistrictRepresentative =  getDistrictRepresentativeJsonString("NewTownTest", "Representative", (long) 2);
        rest.postForEntity("/representative", MyJsonParser.parse(newTowDistrictRepresentative), DistrictRepresentativeEntity.class);
    }


    @After
    public void tearDown() throws Exception {
        districtRepository.findAll().stream().forEach(d-> districtService.delete(d.getId()));
        countyRepository.findAll().stream().forEach(c -> countyService.delete(c.getId()));
    }

    @Test
    public void save() throws Exception {
        //setup
        String kaunoString = getCountyJsonString(null ,"kaunoTest");
        ResponseEntity<CountyEntity> respCountyCreate = rest.postForEntity("/county", MyJsonParser.parse(kaunoString), CountyEntity.class);
        Long id = respCountyCreate.getBody().getId();
        String centerDistrictString = getDistrictJsonString(null, "CenterTest", "Centro g. 1-1", 1000, id);
        ResponseEntity<DistrictEntity> respDistrictCreate = rest.postForEntity(URI, MyJsonParser.parse(centerDistrictString), DistrictEntity.class);
        Long districtId = respDistrictCreate.getBody().getId();
        String centerDistrictRepresentative =  getDistrictRepresentativeJsonString("CenterTest", "Representative",districtId);
        ResponseEntity<DistrictRepresentativeEntity> respDistrictRepresentativeCreate = rest.postForEntity("/representative", MyJsonParser.parse(centerDistrictRepresentative), DistrictRepresentativeEntity.class);
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
        assertThat(resp.getBody().length, CoreMatchers.is(districtRepository.findAll().size()));
    }

    @Test
    public void updateName() throws Exception {
        //setup
            String vilniusString = getCountyJsonString(null,"VilniausTest");
            ResponseEntity<CountyEntity> respCountyCreate = rest.postForEntity("/county", MyJsonParser.parse(vilniusString), CountyEntity.class);
            Long countyId = respCountyCreate.getBody().getId();
            String pylimoDistrictString = getDistrictJsonString(null,"PylimoTest","Pylimo g. 22-33", 450, countyId);
            ResponseEntity<DistrictEntity>  respDistrictCreate = rest.postForEntity(URI, MyJsonParser.parse(pylimoDistrictString), DistrictEntity.class);
        //exercise
        Long districtId = respDistrictCreate.getBody().getId();
        String pylimoUpdateDistrictString = getDistrictJsonString(districtId,"PylimoUpdateTest","Pylimo g. 22-33", 450, countyId);
        ResponseEntity<DistrictEntity>  respDistrictUpdate = rest.postForEntity(URI, MyJsonParser.parse(pylimoUpdateDistrictString), DistrictEntity.class);
        //verify
        assertThat(respCountyCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respDistrictCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respDistrictUpdate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respDistrictUpdate.getBody().getName(), CoreMatchers.is("PylimoUpdateTest"));
    }

    @Test
    public void updateAdress() throws Exception {
        //setup
        String vilniusString = getCountyJsonString(null,"VilniausTest");
        ResponseEntity<CountyEntity> respCountyCreate = rest.postForEntity("/county", MyJsonParser.parse(vilniusString), CountyEntity.class);
        Long countyId = respCountyCreate.getBody().getId();
        String pylimoDistrictString = getDistrictJsonString(null,"PylimoTest","Pylimo g. 22-33", 450, countyId);
        ResponseEntity<DistrictEntity>  respDistrictCreate = rest.postForEntity(URI, MyJsonParser.parse(pylimoDistrictString), DistrictEntity.class);
        //exercise
        Long districtId = respDistrictCreate.getBody().getId();
        String pylimoUpdateAdressDistrictString = getDistrictJsonString(districtId,"PylimoTest","PylimoTest g. 33-22", 450, countyId);
        ResponseEntity<DistrictEntity>  respDistrictUpdate = rest.postForEntity(URI, MyJsonParser.parse(pylimoUpdateAdressDistrictString), DistrictEntity.class);
        //verify
        assertThat(respCountyCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respDistrictCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respDistrictUpdate.getBody().getAdress(), CoreMatchers.is("PylimoTest g. 33-22"));
    }

    @Test
    public void updateNumberOfElectors() throws Exception {
        //setup
        String vilniusString = getCountyJsonString(null,"VilniausTest");
        ResponseEntity<CountyEntity> respCountyCreate = rest.postForEntity("/county", MyJsonParser.parse(vilniusString), CountyEntity.class);
        Long countyId = respCountyCreate.getBody().getId();
        String pylimoDistrictString = getDistrictJsonString(null,"PylimoTest","Pylimo g. 22-33", 450, countyId);
        ResponseEntity<DistrictEntity>  respDistrictCreate = rest.postForEntity(URI, MyJsonParser.parse(pylimoDistrictString), DistrictEntity.class);
        //exercise
        Long districtId = respDistrictCreate.getBody().getId();
        String pylimoUpdateElectorsDistrictString = getDistrictJsonString(districtId,"PylimoTest","Pylimo g. 22-33", 1450, countyId);
        ResponseEntity<DistrictEntity>  respDistrictUpdate = rest.postForEntity(URI, MyJsonParser.parse(pylimoUpdateElectorsDistrictString), DistrictEntity.class);
        //verify
        assertThat(respCountyCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respDistrictCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respDistrictUpdate.getBody().getNumberOfElectors().intValue(), CoreMatchers.is(1450));
    }

    @Test
    public void updateCounty() throws Exception {
        //setup
        String vilniusString = getCountyJsonString(null,"VilniausTest");
        ResponseEntity<CountyEntity> respCountyCreate = rest.postForEntity("/county", MyJsonParser.parse(vilniusString), CountyEntity.class);
        String kaunoString = getCountyJsonString(null ,"kaunoTest");
        ResponseEntity<CountyEntity> respKaunoCountyCreate = rest.postForEntity("/county", MyJsonParser.parse(kaunoString), CountyEntity.class);
        Long countyId = respCountyCreate.getBody().getId();
        Long kaunoCountyId = respKaunoCountyCreate.getBody().getId();
        String pylimoDistrictString = getDistrictJsonString(null,"PylimoTest","Pylimo g. 22-33", 450, countyId);
        ResponseEntity<DistrictEntity>  respDistrictCreate = rest.postForEntity(URI, MyJsonParser.parse(pylimoDistrictString), DistrictEntity.class);
        //exercise
        Long districtId = respDistrictCreate.getBody().getId();
        String pylimoUpdateCountyDistrictString = getDistrictJsonString(districtId,"PylimoTest","Pylimo g. 22-33", 450, kaunoCountyId);
        ResponseEntity<DistrictEntity>  respDistrictUpdate = rest.postForEntity(URI, MyJsonParser.parse(pylimoUpdateCountyDistrictString), DistrictEntity.class);
        //verify
        assertThat(respCountyCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respDistrictCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respDistrictUpdate.getBody().getCounty().getId(), CoreMatchers.is(kaunoCountyId));
    }

    @Test
    public void deleteDistricts() throws Exception {
        //setup
        String vilniusString = getCountyJsonString(null,"VilniausTest");
        ResponseEntity<CountyEntity> respCountyCreate = rest.postForEntity("/county", MyJsonParser.parse(vilniusString), CountyEntity.class);
        Long countyId = respCountyCreate.getBody().getId();
        String pylimoDistrictString = getDistrictJsonString(null,"PylimoTest","Pylimo g. 22-33", 450, countyId);
        ResponseEntity<DistrictEntity>  respPylimoDistrictCreate = rest.postForEntity(URI, MyJsonParser.parse(pylimoDistrictString), DistrictEntity.class);
        String centroDistrictString = getDistrictJsonString(null,"CentroTest","Centro g. 1-2", 1450, countyId);
        ResponseEntity<DistrictEntity>  respCentroDistrictCreate = rest.postForEntity(URI, MyJsonParser.parse(centroDistrictString), DistrictEntity.class);
        //exercise
        districtService.delete(respCentroDistrictCreate.getBody().getId());
        districtService.delete(respPylimoDistrictCreate.getBody().getId());
        //verify
        assertThat(respCountyCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respPylimoDistrictCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respCentroDistrictCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respCountyCreate.getBody().getDistricts(), CoreMatchers.nullValue());
    }

    @Test
    public void deleteDistrictById() throws Exception {
        //setup
        String vilniusString = getCountyJsonString(null,"VilniausTest");
        ResponseEntity<CountyEntity> respCountyCreate = rest.postForEntity("/county", MyJsonParser.parse(vilniusString), CountyEntity.class);
        Long countyId = respCountyCreate.getBody().getId();
        String pylimoDistrictString = getDistrictJsonString(null,"PylimoTest","Pylimo g. 1-1", 450, countyId);
        ResponseEntity<DistrictEntity>  respPylimoDistrictCreate = rest.postForEntity(URI, MyJsonParser.parse(pylimoDistrictString), DistrictEntity.class);
        String centroDistrictString = getDistrictJsonString(null,"CentroTest","Centro g. 1-2", 1450, countyId);
        ResponseEntity<DistrictEntity>  respCentroDistrictCreate = rest.postForEntity(URI, MyJsonParser.parse(centroDistrictString), DistrictEntity.class);
        String newTowDistrictString = getDistrictJsonString(null,"NewTownTest","Naujamiesčio g. 1-3", 2450, countyId);
        ResponseEntity<DistrictEntity>  respNewTowDistrictCreate = rest.postForEntity(URI, MyJsonParser.parse(newTowDistrictString), DistrictEntity.class);
        //exercise
        districtService.delete(respPylimoDistrictCreate.getBody().getId());
        //verify
        assertThat(respCountyCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respCentroDistrictCreate.getStatusCodeValue(), CoreMatchers.is(200));
        assertThat(respNewTowDistrictCreate.getStatusCodeValue(),);
        assertThat(respCountyCreate.getBody().getDistricts().size(), CoreMatchers.is(2));
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
