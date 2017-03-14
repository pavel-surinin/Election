package lt.itakademija.electors.candidate;

import lt.itakademija.electors.county.CountyEntity;
import lt.itakademija.electors.county.CountyRepository;
import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.district.DistrictService;
import lt.itakademija.electors.party.PartyEntity;
import lt.itakademija.electors.party.PartyRepository;
import lt.itakademija.electors.party.PartyService;
import lt.itakademija.electors.results.single.ResultSingleRepository;
import lt.itakademija.electors.results.single.ResultSingleService;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Gabriele on 2017-03-13.
 */
@RunWith(MockitoJUnitRunner.class)
public class CandidateServiceTest {

    @Mock
    private CandidateService candidateService;
    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private PartyService partyService;
    @Mock
    private PartyRepository partyRepository;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        PartyEntity party1 = Mockito.mock(PartyEntity.class);
        CountyEntity county1 = Mockito.mock(CountyEntity.class);
        CandidateEntity candidate1 = Mockito.mock(CandidateEntity.class);
    }

    @After
    public void tearDown() throws Exception {
        candidateRepository.getCandidatesList().stream().forEach(c -> candidateService.delete(c.getId()));
        partyRepository.findAll().stream().forEach(p -> partyService.delete(p.getId()));
    }

    @Test
    public void save() throws Exception {
        CandidateEntity candidate3 = Mockito.mock(CandidateEntity.class);
        candidateRepository.save(candidate3);
        when(candidateRepository.save(candidate3)).thenReturn(candidate3);
        Mockito.verify(candidateRepository).save(candidate3);
        assertEquals(candidateRepository.save(candidate3), candidate3);
    }

    @Test
    public void getAllCandidates() throws Exception {

        CandidateEntity candidate1 = Mockito.mock(CandidateEntity.class);
        CandidateEntity candidate2 = Mockito.mock(CandidateEntity.class);
        List <CandidateReport> candidatesList = new LinkedList();
        List spy = Mockito.spy(candidatesList);

        spy.add(candidate1);
        spy.add(candidate2);

        Mockito.verify(spy).add(candidate1);
        Mockito.verify(spy).add(candidate2);

        assertEquals(2, spy.size());
        candidateService.getAllCandidates();
        when(candidateService.getAllCandidates()).thenReturn(spy);
        Mockito.verify(candidateService).getAllCandidates();
        assertEquals(candidateService.getAllCandidates(), spy);
    }

    @Test
    public void deleteCandidatesByPartyId() throws Exception {

        PartyEntity party1 = Mockito.mock(PartyEntity.class);
        party1.setId(6L);
        PartyEntity party2 = Mockito.mock(PartyEntity.class);
        party2.setId(76L);
        CandidateEntity candidate1 = Mockito.mock(CandidateEntity.class);
        candidate1.setPartyDependencies(party1);
        CandidateEntity candidate2 = Mockito.mock(CandidateEntity.class);
        candidate2.setPartyDependencies(party2);
        CandidateEntity candidate3 = Mockito.mock(CandidateEntity.class);
        candidate3.setPartyDependencies(party1);

        candidateService.deleteCandidatesByPartyId(76L);
        when(candidateService.deleteCandidatesByPartyId(76L)).thenReturn(true);
        Mockito.verify(candidateService).deleteCandidatesByPartyId(76L);
        assertEquals(candidateService.deleteCandidatesByPartyId(76L), true);

        candidateService.deleteCandidatesByPartyId(6L);
        when(candidateService.deleteCandidatesByPartyId(6L)).thenReturn(true);
        Mockito.verify(candidateService).deleteCandidatesByPartyId(6L);
        assertEquals(candidateService.deleteCandidatesByPartyId(6L), true);
    }

    @Test
    public void getCandidateByNameSurnameNumberParty() throws Exception {

        CandidateEntity candidate1 = Mockito.mock(CandidateEntity.class);
        candidate1.setName("Jonas");
        candidate1.setSurname("Jonaitis");
        CandidateEntity candidate2 = Mockito.mock(CandidateEntity.class);
        candidate2.setName("Petras");
        candidate2.setSurname("Petraitis");

        candidateService.getCandidateByNameSurnameNumberParty(candidate2);
        when(candidateService.getCandidateByNameSurnameNumberParty(candidate2)).thenReturn(candidate2);
        Mockito.verify(candidateService).getCandidateByNameSurnameNumberParty(candidate2);
        assertEquals(candidateService.getCandidateByNameSurnameNumberParty(candidate2), candidate2);
    }

    @Test
    public void getCandidateById() throws Exception {
        CandidateEntity candidate1 = Mockito.mock(CandidateEntity.class);
        candidate1.setId(34L);
        CandidateEntity candidate2 = Mockito.mock(CandidateEntity.class);
        candidate2.setId(23L);

        candidateRepository.finById(23L);
        when(candidateRepository.finById(23L)).thenReturn(candidate2);
        assertThat(candidateRepository.finById(23L), CoreMatchers.is(candidate2));

        candidateRepository.finById(34L);
        when(candidateRepository.finById(34L)).thenReturn(candidate1);
        assertThat(candidateRepository.finById(34L), CoreMatchers.is(candidate1));
    }

    @Test
    public void getCandidateByDistrictId() throws Exception {
        CountyEntity county1 = Mockito.mock(CountyEntity.class);
        county1.setId(3L);
        CountyEntity county2 = Mockito.mock(CountyEntity.class);
        county2.setId(29L);

        DistrictEntity district1 = Mockito.mock(DistrictEntity.class);
        district1.setId(12L);
        district1.setCounty(county1);

        DistrictEntity district2 = Mockito.mock(DistrictEntity.class);
        district1.setId(24L);
        district1.setCounty(county2);

        CandidateEntity candidate1 = Mockito.mock(CandidateEntity.class);
        candidate1.setCounty(county1);
        CandidateEntity candidate2 = Mockito.mock(CandidateEntity.class);
        candidate2.setCounty(county2);

        List <CandidateReport> candidatesListDistrict1 = new LinkedList();
        List spyCounty1 = Mockito.spy(candidatesListDistrict1);
        spyCounty1.add(candidate1);
        Mockito.verify(spyCounty1).add(candidate1);

        List <CandidateReport> candidatesListDistrict2 = new LinkedList<>();
        List spyCounty2 = Mockito.spy(candidatesListDistrict2);
        spyCounty2.add(candidate2);
        Mockito.verify(spyCounty2).add(candidate2);

        candidateService.getCandidateByDistrictId(24L);
        when(candidateService.getCandidateByDistrictId(24L)).thenReturn(spyCounty2);
        Mockito.verify(candidateService).getCandidateByDistrictId(24L);
        assertEquals(candidateService.getCandidateByDistrictId(24L), spyCounty2);

        candidateService.getCandidateByDistrictId(12L);
        when(candidateService.getCandidateByDistrictId(12L)).thenReturn(spyCounty1);
        Mockito.verify(candidateService).getCandidateByDistrictId(12L);
        assertEquals(candidateService.getCandidateByDistrictId(12L), spyCounty1);
    }

    @Test
    public void getCandidatesByCounty() throws Exception {

        CountyEntity county1 = Mockito.mock(CountyEntity.class);
        CountyEntity county2 = Mockito.mock(CountyEntity.class);
        CandidateEntity candidate1 = Mockito.mock(CandidateEntity.class);
        candidate1.setCounty(county1);
        CandidateEntity candidate2 = Mockito.mock(CandidateEntity.class);
        candidate2.setCounty(county2);

        List <CandidateEntity> candidatesListCounty1 = new LinkedList();
        List spyCounty1 = Mockito.spy(candidatesListCounty1);
        spyCounty1.add(candidate1);
        Mockito.verify(spyCounty1).add(candidate1);

        List <CandidateEntity> candidatesListCounty2 = new LinkedList();
        List spyCounty2 = Mockito.spy(candidatesListCounty2);
        spyCounty2.add(candidate2);
        Mockito.verify(spyCounty2).add(candidate2);

        when(candidateRepository.findByCounty(county1)).thenReturn(spyCounty1);
        assertThat(candidateRepository.findByCounty(county1), CoreMatchers.is(spyCounty1));
    }

    @Test
    public void deleteAsObject() throws Exception {
        PartyEntity party2 = Mockito.mock(PartyEntity.class);
        when(party2.getId()).thenReturn(55L);

        CandidateEntity candidate2 = Mockito.mock(CandidateEntity.class);
        when(candidate2.getId()).thenReturn(2L);
        when(candidate2.getPartyDependencies()).thenReturn(party2);

        assertThat(candidate2.getId(), CoreMatchers.is(2L));
        assertThat(candidate2.getPartyDependencies().getId(), CoreMatchers.is(55L));

        when(candidateRepository.delete(candidate2)).thenReturn(true);
        assertThat(candidateRepository.delete(candidate2), CoreMatchers.is(true));
    }

    @Test
    public void deleteById() throws Exception {
        PartyEntity party2 = Mockito.mock(PartyEntity.class);
        when(party2.getId()).thenReturn(55L);

        CandidateEntity candidate2 = Mockito.mock(CandidateEntity.class);
        when(candidate2.getId()).thenReturn(2L);
        when(candidate2.getPartyDependencies()).thenReturn(party2);

        assertThat(candidate2.getId(), CoreMatchers.is(2L));
        assertThat(candidate2.getPartyDependencies().getId(), CoreMatchers.is(55L));

        when(candidateRepository.delete(candidate2.getId())).thenReturn(true);
        assertThat(candidateRepository.delete(candidate2.getId()), CoreMatchers.is(true));
    }

}