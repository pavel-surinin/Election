package lt.itakademija.electors.representative;

import lt.itakademija.electors.county.CountyEntity;
import lt.itakademija.electors.county.CountyRepository;
import lt.itakademija.electors.county.CountyService;
import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.district.DistrictRepository;
import lt.itakademija.electors.district.DistrictService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by VytautasL on 3/14/2017.
*/

@RunWith(MockitoJUnitRunner.class)
public class DistrictRepresentativeServiceTest {

    @Mock
    CountyRepository countyRepository;

    @Mock
    CountyService countyService;

    @Mock
    DistrictService districtService;

    @Mock
    DistrictRepository districtRepository;

    @Mock
    DistrictRepresentativeService districtRepresentativeService;

    @Mock
    DistrictRepresentativeRepository districtRepresentativeRepository;

    @Before
    public void setUp() throws Exception {
        CountyEntity county1 = Mockito.mock(CountyEntity.class);
        when(county1.getId()).thenReturn(1L);
        DistrictEntity district1 = Mockito.mock(DistrictEntity.class);
        when(district1.getId()).thenReturn(1L);
        when(district1.getCounty()).thenReturn(county1);
        DistrictEntity district2 = Mockito.mock(DistrictEntity.class);
        when(district2.getId()).thenReturn(2L);
        when(district2.getCounty()).thenReturn(county1);

    }

    @After
    public void tearDown() throws Exception {
        countyRepository.findAll().stream().forEach(c -> countyService.delete(c.getId()));
    }

    @Test
    public void save() throws Exception {
        DistrictRepresentativeEntity representative1 = Mockito.mock(DistrictRepresentativeEntity.class);
        DistrictRepresentativeEntity representative2 = Mockito.mock(DistrictRepresentativeEntity.class);
        districtRepresentativeRepository.save(representative1);
        districtRepresentativeRepository.save(representative2);

        List<DistrictRepresentativeReport> representativeList = new LinkedList<>();
        List spy = Mockito.spy(representativeList);
            spy.add(representative1);
            spy.add(representative2);

        Mockito.verify(spy).add(representative1);
        Mockito.verify(spy).add(representative2);
        assertEquals(2, spy.size());
        when(districtRepresentativeRepository.save(representative1)).thenReturn(representative1);
        when(districtRepresentativeRepository.save(representative2)).thenReturn(representative2);
        Mockito.verify(districtRepresentativeRepository).save(representative1);
        assertEquals(districtRepresentativeRepository.save(representative1), representative1);
        Mockito.verify(districtRepresentativeRepository).save(representative2);
        assertEquals(districtRepresentativeRepository.save(representative2), representative2);
    }

    @Test
    public void getRepresentativesList() throws Exception {
        DistrictRepresentativeEntity representative1 = Mockito.mock(DistrictRepresentativeEntity.class);
        DistrictRepresentativeEntity representative2 = Mockito.mock(DistrictRepresentativeEntity.class);
        districtRepresentativeRepository.save(representative1);
        districtRepresentativeRepository.save(representative2);

        List<DistrictRepresentativeReport> representativeList = new LinkedList<>();
        List spyRepresentative = Mockito.spy(representativeList);
        spyRepresentative.add(representative1);
        spyRepresentative.add(representative2);

        Mockito.verify(spyRepresentative).add(representative1);
        Mockito.verify(spyRepresentative).add(representative2);
        districtRepresentativeService.getAllRepresentatives();
        when(districtRepresentativeService.getAllRepresentatives()).thenReturn(spyRepresentative);
        Mockito.verify(districtRepresentativeService).getAllRepresentatives();
        assertEquals(districtRepresentativeService.getAllRepresentatives(),spyRepresentative);
    }

    @Test
    public void deletesRepresentativeWhenDeleteDistrict(){
        DistrictRepresentativeEntity representative1 = Mockito.mock(DistrictRepresentativeEntity.class);
        when(representative1.getDistrict()).thenReturn(districtRepository.findById(1L));
        when(representative1.getId()).thenReturn(1L);
        DistrictRepresentativeEntity representative2 = Mockito.mock(DistrictRepresentativeEntity.class);
        when(representative2.getDistrict()).thenReturn(districtRepository.findById(2L));
        when(representative2.getId()).thenReturn(2L);

        districtRepresentativeRepository.save(representative1);
        districtRepresentativeRepository.save(representative2);
        districtService.delete(1L);
        Mockito.verify(districtService.delete(1L));
        assertEquals(1, districtRepository.findAll().size());
    }

}
