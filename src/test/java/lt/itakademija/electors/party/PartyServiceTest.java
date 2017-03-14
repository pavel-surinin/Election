package lt.itakademija.electors.party;

import lt.itakademija.electors.county.CountyService;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Gabriele on 2017-03-14.
 */
@RunWith(MockitoJUnitRunner.class)
public class PartyServiceTest {

    @Mock
    private CountyService countyService;

    @Mock
    private PartyService partyService;

    @Mock
    private PartyRepository partyRepository;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void save() throws Exception {
        PartyEntity party1 = Mockito.mock(PartyEntity.class);

        partyService.save(party1);
        when(partyService.save(party1)).thenReturn(party1);
        Mockito.verify(partyService).save(party1);
        assertEquals(partyService.save(party1), party1);
    }

    @Test
    public void saveWithData() throws Exception {
        PartyEntity party1 = Mockito.mock(PartyEntity.class);
        party1.setId(4L);
        party1.setName("TSLKD");
        party1.setPartyNumber(2);

    }

    @Test
    public void save2() throws Exception {

    }

    @Test
    public void getPartyByNumber() throws Exception {

    }

    @Test
    public void getPartyList() throws Exception {

    }

    @Test
    public void getPartyById() throws Exception {

    }

    @Test
    public void getPartyEntityById() throws Exception {

    }

    @Test
    public void detach() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void getNameById() throws Exception {

    }

}