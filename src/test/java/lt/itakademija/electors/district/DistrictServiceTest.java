package lt.itakademija.electors.district;

import lt.itakademija.electors.representative.DistrictRepresentativeEntity;
import lt.itakademija.electors.representative.DistrictRepresentativeService;
import lt.itakademija.electors.results.multi.ResultMultiEntity;
import lt.itakademija.electors.results.single.ResultSingleEntity;
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
 * Created by Gabriele on 2017-03-14.
 */
@RunWith(MockitoJUnitRunner.class)
public class DistrictServiceTest {

    @Mock
    private DistrictService districtService;

    @Mock
    private DistrictRepository districtRepository;

    @Mock
    private DistrictRepresentativeService districtRepresentativeService;

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
        DistrictEntity district1 = Mockito.mock(DistrictEntity.class);
        districtService.save(district1);
        Mockito.verify(districtService).save(district1);

        DistrictEntity district2 = Mockito.mock(DistrictEntity.class);
        districtService.save(district2);
        Mockito.verify(districtService).save(district2);

        List<DistrictEntity> districtList = new LinkedList<>();
        List spyDistricts = Mockito.spy(districtList);
        spyDistricts.add(district1);
        spyDistricts.add(district2);
        Mockito.verify(spyDistricts).add(district1);
        Mockito.verify(spyDistricts).add(district2);

        districtService.getDistrictsList();
        when(districtService.getDistrictsList()).thenReturn(spyDistricts);
        Mockito.verify(districtService).getDistrictsList();
        assertEquals(spyDistricts, districtService.getDistrictsList());
        assertThat(districtService.getDistrictsList().size(), CoreMatchers.is(2));
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
        districtService.save(district1);
        Mockito.verify(districtService).save(district1);
        district1.setId(8L);
        districtService.getNumberOfDistricts();
        when(districtService.getNumberOfDistricts()).thenReturn(1);
        Mockito.verify(districtService).getNumberOfDistricts();
        assertThat(districtService.getNumberOfDistricts(), CoreMatchers.is(1));

        districtService.delete(8L);
        when(districtService.delete(8L)).thenReturn(true);
        Mockito.verify(districtService).delete(8L);
        assertEquals(true, districtService.delete(8L));
        assertEquals(0, districtService.getDistrictsList().size());
    }

    @Test
    public void getDistrictsWithNullRepresentativesList() throws Exception {
        DistrictRepresentativeEntity representative = Mockito.mock(DistrictRepresentativeEntity.class);
        districtRepresentativeService.save(representative);
        Mockito.verify(districtRepresentativeService).save(representative);

        DistrictEntity district1 = Mockito.mock(DistrictEntity.class);
        districtService.save(district1);
        Mockito.verify(districtService).save(district1);
        district1.setRepresentative(representative);

        DistrictEntity district2 = Mockito.mock(DistrictEntity.class);
        districtService.save(district2);
        Mockito.verify(districtService).save(district2);
        district2.setRepresentative(null);

        DistrictEntity district3 = Mockito.mock(DistrictEntity.class);
        districtService.save(district3);
        Mockito.verify(districtService).save(district3);
        district3.setRepresentative(null);

        List<DistrictEntity> districtsWithRepresentatives = new LinkedList<>();
        List spyDistrictsWithRepresentatives = Mockito.spy(districtsWithRepresentatives);
        spyDistrictsWithRepresentatives.add(district1);
        Mockito.verify(spyDistrictsWithRepresentatives).add(district1);

        List<DistrictEntity> districtsWithoutRepresentatives = new LinkedList<>();
        List spyDistrictsWithoutRepresentatives = Mockito.spy(districtsWithoutRepresentatives);
        spyDistrictsWithoutRepresentatives.add(district2);
        spyDistrictsWithoutRepresentatives.add(district3);
        Mockito.verify(spyDistrictsWithoutRepresentatives).add(district2);
        Mockito.verify(spyDistrictsWithoutRepresentatives).add(district3);

        districtService.getDistrictsWithNullRepresentativesList();
        when(districtService.getDistrictsWithNullRepresentativesList()).thenReturn(spyDistrictsWithoutRepresentatives);
        Mockito.verify(districtService).getDistrictsWithNullRepresentativesList();
        assertEquals(spyDistrictsWithoutRepresentatives, districtService.getDistrictsWithNullRepresentativesList());
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
        DistrictEntity district1 = Mockito.mock(DistrictEntity.class);
        districtService.save(district1);
        Mockito.verify(districtService).save(district1);

        ResultSingleEntity singleResult1 = Mockito.mock(ResultSingleEntity.class);
        singleResult1.setVotes(340L);
        singleResult1.setDistrict(district1);
        singleResult1.setApproved(true);

        DistrictEntity district2 = Mockito.mock(DistrictEntity.class);
        districtService.save(district2);
        Mockito.verify(districtService).save(district2);

        when(districtService.getDistrictSingleRegistered()).thenCallRealMethod();
        List singleRegisteredResultList = districtService.getDistrictSingleRegistered();

        assertEquals(singleRegisteredResultList, districtService.getDistrictSingleRegistered());
    }

    @Test
    public void getDistrictMultiRegistered() throws Exception {
        DistrictEntity district1 = Mockito.mock(DistrictEntity.class);
        districtService.save(district1);
        Mockito.verify(districtService).save(district1);

        ResultMultiEntity multiResult1 = Mockito.mock(ResultMultiEntity.class);
        multiResult1.setVotes(340L);
        multiResult1.setDistrict(district1);
        multiResult1.setApproved(true);

        DistrictEntity district2 = Mockito.mock(DistrictEntity.class);
        districtService.save(district2);
        Mockito.verify(districtService).save(district2);

        when(districtService.getDistrictMultiRegistered()).thenCallRealMethod();
        List multiRegisteredResultList = districtService.getDistrictMultiRegistered();

        assertEquals(multiRegisteredResultList, districtService.getDistrictMultiRegistered());
    }

    @Test
    public void getNameById() throws Exception {
        DistrictEntity district8 = Mockito.mock(DistrictEntity.class);
        districtService.save(district8);
        Mockito.verify(districtService).save(district8);
        district8.setName("Parko");
        district8.setId(8L);
        districtService.getNameById(8L);
        when(districtService.getNameById(8L)).thenReturn("Parko");
        Mockito.verify(districtService).getNameById(8L);
        assertEquals("Parko", districtService.getNameById(8L));
    }

    @Test
    public void getNumberOfDistricts() throws Exception {
        DistrictEntity district1 = Mockito.mock(DistrictEntity.class);
        districtService.save(district1);
        Mockito.verify(districtService).save(district1);
        DistrictEntity district2 = Mockito.mock(DistrictEntity.class);
        districtService.save(district2);
        Mockito.verify(districtService).save(district2);

        districtService.getNumberOfDistricts();
        when(districtService.getNumberOfDistricts()).thenReturn(2);
        Mockito.verify(districtService).getNumberOfDistricts();
        assertThat(districtService.getNumberOfDistricts(), CoreMatchers.is(2));
    }

    @Test
    public void getDistictsByFirstLetter() throws Exception {
        DistrictEntity districtGel = Mockito.mock(DistrictEntity.class);
        districtService.save(districtGel);
        Mockito.verify(districtService).save(districtGel);
        districtGel.setName("Geliu");
        DistrictEntity districtJ = Mockito.mock(DistrictEntity.class);
        districtService.save(districtJ);
        Mockito.verify(districtService).save(districtJ);
        districtJ.setName("Jonu");
        DistrictEntity districtGot = Mockito.mock(DistrictEntity.class);
        districtService.save(districtGot);
        Mockito.verify(districtService).save(districtGot);
        districtGot.setName("Gotu");

//        districtService.getDistictsByFirstLetter("g");
//        when(districtService.getDistictsByFirstLetter("g")).thenCallRealMethod();
//        Mockito.verify(districtService).getDistictsByFirstLetter("g");
//        List firstLetterList = districtService.getDistictsByFirstLetter("G");
        List<DistrictEntity> districtsWithG = new LinkedList<>();
        List spyDistrictsWithG = Mockito.spy(districtsWithG);
        spyDistrictsWithG.add(districtGel);
        spyDistrictsWithG.add(districtGot);
        Mockito.verify(spyDistrictsWithG).add(districtGel);
        Mockito.verify(spyDistrictsWithG).add(districtGot);

        assertThat(districtService.getDistictsByFirstLetter("g"), CoreMatchers.is(spyDistrictsWithG));
        assertThat(districtService.getDistictsByFirstLetter("g").size(), CoreMatchers.is(spyDistrictsWithG.size()));
    }

}