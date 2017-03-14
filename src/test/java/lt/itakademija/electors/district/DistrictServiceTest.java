package lt.itakademija.electors.district;

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
    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void getDistrictsWithNullRepresentativesList() throws Exception {

    }

    @Test
    public void getDistrictById() throws Exception {

    }

    @Test
    public void getDistrictSingleRegistered() throws Exception {

    }

    @Test
    public void getDistrictMultiRegistered() throws Exception {

    }

    @Test
    public void getNameById() throws Exception {

    }

    @Test
    public void getDistictsByPage() throws Exception {

    }

    @Test
    public void getNumberOfDistricts() throws Exception {

    }

    @Test
    public void getDistictsByFirstLetter() throws Exception {

    }

}