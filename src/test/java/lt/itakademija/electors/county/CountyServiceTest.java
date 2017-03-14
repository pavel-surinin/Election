package lt.itakademija.electors.county;

import lt.itakademija.electors.MyUtils;
import lt.itakademija.electors.candidate.CandidateRepository;
import lt.itakademija.electors.candidate.CandidateService;
import lt.itakademija.electors.party.PartyRepository;
import lt.itakademija.electors.party.PartyService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Gabriele on 2017-03-13.
 */
@RunWith(MockitoJUnitRunner.class)
public class CountyServiceTest {

    @Mock
    private CountyService countyService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getAllEntities() throws Exception {

        CountyEntity county1 = Mockito.mock(CountyEntity.class);
        CountyEntity county2 = Mockito.mock(CountyEntity.class);

        List<CountyEntity> countyList = new LinkedList();
        List spyCounty = Mockito.spy(countyList);
        spyCounty.add(county1);
        spyCounty.add(county2);
        Mockito.verify(spyCounty).add(county1);
        Mockito.verify(spyCounty).add(county2);

        countyService.getAllEntities();
        when(countyService.getAllEntities()).thenReturn(spyCounty);
        Mockito.verify(countyService).getAllEntities();
        assertEquals(countyService.getAllEntities(), spyCounty);
    }

    @Test
    public void getAll() throws Exception {
        CountyEntity county1 = Mockito.mock(CountyEntity.class);
        CountyEntity county2 = Mockito.mock(CountyEntity.class);

        List<CountyEntity> countyList = new LinkedList();
        List spyCounty = Mockito.spy(countyList);
        spyCounty.add(county1);
        spyCounty.add(county2);
        Mockito.verify(spyCounty).add(county1);
        Mockito.verify(spyCounty).add(county2);

        countyService.getAllEntities();
        when(countyService.getAllEntities()).thenReturn(spyCounty);
        Mockito.verify(countyService).getAllEntities();
        assertEquals(countyService.getAllEntities(), spyCounty);
    }

    @Test
    public void save() throws Exception {
        CountyEntity county1 = Mockito.mock(CountyEntity.class);
        countyService.save(county1);
        when(countyService.save(county1)).thenReturn(county1);
        Mockito.verify(countyService).save(county1);
        assertEquals(countyService.save(county1), county1);
    }

    @Test
    public void getCountyById() throws Exception {
        CountyEntity county1 = Mockito.mock(CountyEntity.class);
        county1.setId(2L);
        CountyEntity county2 = Mockito.mock(CountyEntity.class);
        county2.setId(4L);

        CountyDetailsReport newReport = Mockito.mock(CountyDetailsReport.class);

        countyService.getCountyById(2L);
        when(countyService.getCountyById(2L)).thenReturn(newReport);
        Mockito.verify(countyService).getCountyById(2L);
        assertEquals(countyService.getCountyById(2L), newReport);
    }

    @Test
    public void delete() throws Exception {
        CountyEntity county1 = Mockito.mock(CountyEntity.class);
        county1.setId(45L);

        countyService.delete(45L);
        when(countyService.delete(45L)).thenReturn(true);
        Mockito.verify(countyService).delete(45L);
        assertEquals(countyService.delete(45L), true);
    }

    @Test
    public void getCountyByIdCountyEnt() throws Exception {
        CountyEntity county1 = Mockito.mock(CountyEntity.class);
        county1.setId(2L);
        CountyEntity county2 = Mockito.mock(CountyEntity.class);
        county2.setId(4L);

        countyService.getCountyByIdCountyEnt(4L);
        when(countyService.getCountyByIdCountyEnt(4L)).thenReturn(county2);
        Mockito.verify(countyService).getCountyByIdCountyEnt(4L);
        assertEquals(countyService.getCountyByIdCountyEnt(4L), county2);

        countyService.getCountyByIdCountyEnt(2L);
        when(countyService.getCountyByIdCountyEnt(2L)).thenReturn(county1);
        Mockito.verify(countyService).getCountyByIdCountyEnt(2L);
        assertEquals(countyService.getCountyByIdCountyEnt(2L), county1);
    }

    @Test
    public void update() throws Exception {
        CountyEntity county1 = Mockito.mock(CountyEntity.class);
        county1.setId(43L);
        MultipartFile partyfile1 = MyUtils.parseToMultiPart("test-csv/data-party-2.csv");

        countyService.update(43L, partyfile1);
        Mockito.verify(countyService).update(43L, partyfile1);
        assertNotNull(county1.getCandidates());
    }

    @Test
    public void getNameById() throws Exception {
        CountyEntity county111 = Mockito.mock(CountyEntity.class);
        county111.setId(111L);
        county111.setName("Varėnos");
        countyService.getNameById(111L);
        when(countyService.getNameById(111L)).thenReturn("Varėnos");
        Mockito.verify(countyService).getNameById(111L);
        assertEquals(countyService.getNameById(111L), "Varėnos");
    }

}