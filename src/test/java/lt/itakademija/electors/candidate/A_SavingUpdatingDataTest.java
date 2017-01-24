package lt.itakademija.electors.candidate;

import lt.itakademija.Application;
import lt.itakademija.electors.county.CountyEntity;
import lt.itakademija.electors.county.CountyService;
import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.district.DistrictReport;
import lt.itakademija.electors.district.DistrictRepository;
import lt.itakademija.electors.district.DistrictService;
import lt.itakademija.electors.party.PartyEntity;
import lt.itakademija.electors.party.PartyService;
import lt.itakademija.electors.representative.DistrictRepresentativeEntity;
import lt.itakademija.electors.representative.DistrictRepresentativeRepository;
import lt.itakademija.users.UsersEntity;
import lt.itakademija.users.UsersRepository;
import lt.itakademija.users.UsersService;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
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

import java.util.Date;

import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {A_SavingUpdatingDataTest.Config.class,
        Application.class})
public class A_SavingUpdatingDataTest {

    @Autowired
    CandidateService candidateService;

    @Autowired
    PartyService partyService;

    @Autowired
    CountyService countyService;

    @Autowired
    DistrictService districtService;

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    DistrictRepresentativeRepository districtRepresentativeRepository;

    @Autowired
    TestRestTemplate rest;

    @Autowired
    UsersService usersService;

    PartyEntity darbo = new PartyEntity();
    PartyEntity soc = new PartyEntity();
    PartyEntity zal = new PartyEntity();

    CountyEntity vilnius = new CountyEntity();
    CountyEntity kaunas = new CountyEntity();

    DistrictEntity centroDis = new DistrictEntity();
    DistrictEntity naujanuDis = new DistrictEntity();
    DistrictEntity zaliakalnioDis = new DistrictEntity();
    DistrictEntity kaimoDis = new DistrictEntity();

    @Test
    public void testSaveUpdateParty(){
        UsersEntity admin = new UsersEntity();
        admin.setPassword("admin");
        admin.setUsername("admin");
        UsersEntity user = new UsersEntity();
        user.setPassword("admin");
        user.setUsername("admin");
        usersService.saveUser(user);

        //Setup
        darbo.setName("Darbo Partija");
        darbo.setName("Komunistu Partija");
        darbo.setName("Žaliųjų Partija");
        ResponseEntity<PartyEntity> responseDarbo = rest.postForEntity("/party", darbo, PartyEntity.class);
        ResponseEntity<PartyEntity> responseSoc = rest.postForEntity("/party", soc, PartyEntity.class);
        ResponseEntity<PartyEntity> responseZal = rest.postForEntity("/party", darbo, PartyEntity.class);
        soc.setName("SDP");
        soc.setId(responseSoc.getBody().getId());
        ResponseEntity<PartyEntity> responseSocUpdate = rest.postForEntity("/party", soc, PartyEntity.class);
        //Verify
        Assert.assertThat(responseSocUpdate.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
        Assert.assertThat("SDP",is(responseSocUpdate.getBody().getName()));
        Assert.assertThat(responseDarbo.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
        Assert.assertThat(responseSoc.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
        Assert.assertThat(responseZal.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
    }

    @Test
    public void testSaveUpdateCandidate(){
        //Setup
        CandidateEntity can = new CandidateEntity();
        can.setName("Jonas");
        can.setSurname("Petravicius");
        can.setBirthDate(new Date(-17805600000L));
        can.setDescription("Protingas ciuvakas");
        can.setPartyDependencies(partyService.getPartyEntityById(1L));
        ResponseEntity<CandidateEntity> response1 = rest.postForEntity("/candidate", can, CandidateEntity.class);

        CandidateEntity can2 = new CandidateEntity();
        can2.setName("Algis");
        can2.setSurname("Ramanauskas");
        can2.setBirthDate(new Date(-17804600000L));
        can2.setDescription("Luzeris");
        can2.setPartyDependencies(partyService.getPartyEntityById(1L));
        ResponseEntity<CandidateEntity> response2 = rest.postForEntity("/candidate", can, CandidateEntity.class);

        CandidateEntity can3 = new CandidateEntity();
        can3.setName("Jonas");
        can3.setSurname("Basanavicius");
        can3.setBirthDate(new Date(-11805600000L));
        can3.setDescription("UUU koks.");
        can3.setPartyDependencies(partyService.getPartyEntityById(2L));
        ResponseEntity<CandidateEntity> response3 = rest.postForEntity("/candidate", can, CandidateEntity.class);

        CandidateEntity can4 = new CandidateEntity();
        can4.setName("Dainius");
        can4.setSurname("Zubrus");
        can4.setBirthDate(new Date(-19805600000L));
        can4.setDescription("Ledinis");
        can4.setPartyDependencies(partyService.getPartyEntityById(2L));
        ResponseEntity<CandidateEntity> response4 = rest.postForEntity("/candidate", can, CandidateEntity.class);
        //Verify
        Assert.assertThat(response1.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
        Assert.assertThat(response2.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
        Assert.assertThat(response2.getBody().getName(), is(can.getName()));
        Assert.assertThat(candidateService.getAllCandidates().size(), is(4));
        Assert.assertThat(response4.getBody().getId(), is(4L));
    }

    @Test
    public void testSaveUpdateCounty(){
        //setup
        vilnius.setName("Vilniaus Centrine");
        ResponseEntity<CountyEntity> responseV = rest.postForEntity("/county", vilnius, CountyEntity.class);
        kaunas.setName("Kauno Kolhozine");
        ResponseEntity<CountyEntity> responseK = rest.postForEntity("/county", kaunas, CountyEntity.class);
        kaunas.setName("Kauno Kolukine");
        kaunas.setId(2L);
        ResponseEntity<CountyEntity> responseKUpdate = rest.postForEntity("/county", kaunas, CountyEntity.class);
        //verify
        Assert.assertThat(responseV.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
        Assert.assertThat(responseK.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
        Assert.assertThat(responseKUpdate.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
        Assert.assertThat(responseKUpdate.getBody().getName(), is("Kauno Kolukine"));
        Assert.assertThat(countyService.getAll().size(), is(2));
    }

    @Test
    public void testSaveUpdateDistrict(){
        //setup
        centroDis.setName("Cento");
        centroDis.setAdress("Gėėėdimino g. 9");
        centroDis.setCounty(countyService.getCountyByIdCountyEnt(1L));
        ResponseEntity<DistrictEntity> responseCentro = rest.postForEntity("/district", centroDis, DistrictEntity.class);
        naujanuDis.setName("Naujanų");
        naujanuDis.setAdress("Šaltalankių g. 10");
        naujanuDis.setCounty(countyService.getCountyByIdCountyEnt(1L));
        ResponseEntity<DistrictEntity> responseN = rest.postForEntity("/district", naujanuDis, DistrictEntity.class);
        zaliakalnioDis.setName("Žaliakalnio");
        zaliakalnioDis.setAdress("Žzaliakalnio g. 1");
        zaliakalnioDis.setCounty(countyService.getCountyByIdCountyEnt(2L));
        ResponseEntity<DistrictEntity> responseZ = rest.postForEntity("/district", zaliakalnioDis, DistrictEntity.class);
        kaimoDis.setName("Kaimo");
        kaimoDis.setAdress("Zapyškių k., prie kiosko");
        kaimoDis.setCounty(countyService.getCountyByIdCountyEnt(2L));
        ResponseEntity<DistrictEntity> responseK = rest.postForEntity("/district", kaimoDis, DistrictEntity.class);
        centroDis.setId(1L);
        centroDis.setName("Centro");
        ResponseEntity<DistrictEntity> responseCentroUpdate = rest.postForEntity("/district", centroDis, DistrictEntity.class);
        //verify
        Assert.assertThat(responseCentro.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
        Assert.assertThat(responseN.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
        Assert.assertThat(responseZ.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
        Assert.assertThat(responseK.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
        Assert.assertThat(responseCentroUpdate.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
        Assert.assertThat(districtService.getDistrictsList().get(0).getName(), is("Centro"));
    }

    @Test
    public void testSaveUpdateDistrictRepresentative(){
        //setup
        DistrictRepresentativeEntity rep1 = new DistrictRepresentativeEntity();
        DistrictRepresentativeEntity rep2 = new DistrictRepresentativeEntity();
        DistrictRepresentativeEntity rep3 = new DistrictRepresentativeEntity();
        DistrictRepresentativeEntity rep4 = new DistrictRepresentativeEntity();
        rep1.setName("Kaimo");
        rep1.setSurname("Jurgis");
        rep1.setDistrict(districtRepository.findById(1L));
        ResponseEntity<DistrictRepresentativeEntity> response1 = rest.postForEntity("/representative", rep1, DistrictRepresentativeEntity.class);
        rep2.setName("Žaltys");
        rep2.setSurname("Raudonas");
        rep2.setDistrict(districtRepository.findById(2L));
        ResponseEntity<DistrictRepresentativeEntity> response2 = rest.postForEntity("/representative", rep2, DistrictRepresentativeEntity.class);
        rep3.setName("Žaltys");
        rep3.setSurname("Mėlynas");
        rep3.setDistrict(districtRepository.findById(3L));
        ResponseEntity<DistrictRepresentativeEntity> response3 = rest.postForEntity("/representative", rep3, DistrictRepresentativeEntity.class);
        rep4.setName("Dėdė");
        rep4.setSurname("Pūlinys");
        rep4.setDistrict(districtRepository.findById(4L));
        ResponseEntity<DistrictRepresentativeEntity> response4 = rest.postForEntity("/representative", rep4, DistrictRepresentativeEntity.class);
        ResponseEntity<DistrictReport[]> responseAll = rest.getForEntity("/representative", DistrictReport[].class);
        rep3.setId(response3.getBody().getId());
        rep3.setSurname("Rudas");
        ResponseEntity<DistrictRepresentativeEntity> response3update = rest.postForEntity("/representative", rep3, DistrictRepresentativeEntity.class);
        //verify
        Assert.assertThat(response1.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
        Assert.assertThat(response2.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
        Assert.assertThat(response3.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
        Assert.assertThat(response4.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
        Assert.assertThat(response3update.getStatusCode(), CoreMatchers.is(HttpStatus.OK));
        Assert.assertThat(responseAll.getBody().length, is(4));
        Assert.assertThat(districtRepresentativeRepository.findAll().get(2).getSurname(), is("Rudas"));


    }

    @TestConfiguration
    static class Config {
        @Bean
        @Primary
        public CandidateService service() {
            return new CandidateService();
        }
    }

}