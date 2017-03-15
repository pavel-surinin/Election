package lt.itakademija.electors.party;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by nevyt on 3/14/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class PartyServiceTest {

    @Mock
    private PartyService partyService;

    @Mock
    private PartyRepository partyRepository;

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getPartyByNumber() throws Exception {
        PartyEntity party1 = Mockito.mock(PartyEntity.class);
        party1.setPartyNumber(99);

        PartyEntity newEntity = Mockito.mock(PartyEntity.class);
        partyService.getPartyByNumber(99);

        when(partyService.getPartyByNumber(99)).thenReturn(newEntity);
        Mockito.verify(partyService).getPartyByNumber(99);
        assertEquals(partyService.getPartyByNumber(99), newEntity);
    }

    @Test
    public void getPartyList() throws Exception {
        PartyEntity party1 = Mockito.mock(PartyEntity.class);
        PartyEntity party2 = Mockito.mock(PartyEntity.class);
        List<PartyEntity> partyList = new LinkedList();
        List spyParty = Mockito.spy(partyList);
        spyParty.add(party1);
        spyParty.add(party2);
        Mockito.verify(spyParty).add(party1);
        Mockito.verify(spyParty).add(party2);
        partyService.getPartyList();

        when(partyService.getPartyList()).thenReturn(spyParty);
        Mockito.verify(partyService).getPartyList();
        assertEquals(partyService.getPartyList(),spyParty);
    }

    @Test
    public void getPartyEntityById() throws Exception {
        PartyEntity party2 = Mockito.mock(PartyEntity.class);
        party2.setId(2L);
        PartyReport newReport = Mockito.mock(PartyReport.class);
        partyService.getPartyById(2L);

        when(partyService.getPartyById(2L)).thenReturn(newReport);
        Mockito.verify(partyService).getPartyById(2L);
        assertEquals(partyService.getPartyById(2L), newReport);
    }



    @Test
    public void detach() throws Exception {
        PartyEntity party1 = Mockito.mock(PartyEntity.class);
        PartyEntity newEntity = Mockito.mock(PartyEntity.class);


        partyService.detach(party1);


    }

    @Test
    public void getNameById() throws Exception {
        PartyEntity party1 = Mockito.mock(PartyEntity.class);
        party1.setId(21L);

        PartyEntity newEntity = Mockito.mock(PartyEntity.class);
        String newName = newEntity.getName();
        partyService.getNameById(21L);

        when(partyService.getNameById(21L)).thenReturn(newName);
        Mockito.verify(partyService).getNameById(21L);
        assertEquals((partyService.getNameById(21L)), newName);

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
    public void getPartyById() throws Exception {
        PartyEntity party1 = Mockito.mock(PartyEntity.class);
        party1.setId(1L);
        PartyReport newReport = Mockito.mock(PartyReport.class);
        partyService.getPartyById(1L);

        when(partyService.getPartyById(1L)).thenReturn(newReport);
        Mockito.verify(partyService).getPartyById(1L);
        assertEquals((partyService.getPartyById(1L)),newReport);

    }

    @Test
    public void delete() throws Exception {

    }

}