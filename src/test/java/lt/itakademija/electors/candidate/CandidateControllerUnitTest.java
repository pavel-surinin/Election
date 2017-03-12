package lt.itakademija.electors.candidate;

import lt.itakademija.Application;
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
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import static org.junit.Assert.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Created by Gabriele on 2017-03-08.
 */
@RunWith(MockitoJUnitRunner.class)
public class CandidateControllerUnitTest {

    @Mock
    private CandidateService candidateService;
    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private PartyService partyService;
    @Mock
    private PartyRepository partyRepository;
    @Mock
    private CountyRepository countyRepository;
    @Mock
    private DistrictService districtService;
    @Mock
    private ResultSingleService resultSingleService;
    private ResultSingleRepository resultSingleRepository;

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
    public void deleteCandidate() throws Exception {

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