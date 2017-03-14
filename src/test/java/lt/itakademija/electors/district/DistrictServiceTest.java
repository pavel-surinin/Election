package lt.itakademija.electors.district;

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
 * Created by Gabriele on 2017-03-14.
 */
@RunWith(MockitoJUnitRunner.class)
public class DistrictServiceTest {

    @Mock
    private DistrictService districtService;

    @Mock
    private DistrictRepository districtRepository;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getDistrictsList() throws Exception {

    }

    @Test
    public void save() throws Exception {
        DistrictEntity district1 = Mockito.mock(DistrictEntity.class);
        districtService.save(district1);
        when(districtService.save(district1)).thenReturn(district1);
        Mockito.verify(districtService).save(district1);
        assertEquals(districtService.save(district1), district1);
    }

    @Test
    public void delete() throws Exception {
        DistrictEntity district1 = Mockito.mock(DistrictEntity.class);
        district1.setId(8L);

        districtService.delete(8L);
        when(districtService.delete(8L)).thenReturn(true);
        Mockito.verify(districtService).delete(8L);
        assertEquals(true, districtService.delete(8L));
        assertEquals(0, districtService.getDistrictsList().size());
    }

    @Test
    public void getDistrictsWithNullRepresentativesList() throws Exception {

    }

    @Test
    public void getDistrictById() throws Exception {
        DistrictEntity district7 = Mockito.mock(DistrictEntity.class);
        district7.setId(7L);

        districtRepository.findById(7L);
        when(districtRepository.findById(7L)).thenReturn(district7);
        Mockito.verify(districtRepository).findById(7L);
        assertEquals(district7, districtRepository.findById(7L));
    }

    @Test
    public void getDistrictSingleRegistered() throws Exception {

    }

    @Test
    public void getDistrictMultiRegistered() throws Exception {

    }

    @Test
    public void getNameById() throws Exception {
        DistrictEntity district8 = Mockito.mock(DistrictEntity.class);
        district8.setName("Parko");
        district8.setId(8L);
        districtService.getNameById(8L);
        when(districtService.getNameById(8L)).thenReturn("Parko");
        Mockito.verify(districtService).getNameById(8L);
        assertEquals("Parko", districtService.getNameById(8L));
    }

    @Test
    public void getDistictsByPage() throws Exception {

    }

    @Test
    public void getNumberOfDistricts() throws Exception {
        List<DistrictEntity> districtList = new LinkedList<>();
        List spyDistrict = Mockito.spy(districtList);
//        spyDistrict.equals(100);
//        Mockito.verify(spyDistrict).equals(100);
        districtRepository.getDistrictsCount();
        when(districtRepository.getDistrictsCount()).thenReturn(100L);
        Mockito.verify(districtRepository).getDistrictsCount();
        assertThat(districtRepository.getDistrictsCount(), CoreMatchers.is(100L));
    }

    @Test
    public void getDistictsByFirstLetter() throws Exception {

    }

}