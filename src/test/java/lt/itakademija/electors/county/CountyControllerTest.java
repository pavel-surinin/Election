package lt.itakademija.electors.county;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Pavel on 2017-01-24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class CountyControllerTest {

    @Autowired
    CountyService countyService;

    @Autowired
    CountyRepository countyRepository;

    @Ignore
    @Test
    public void getCountyDetails() throws Exception {
        CountyEntity vilnius = new CountyEntity();
        vilnius.setName("Vilniaus test");
        Long cId = countyService.save(vilnius).getId();
        countyRepository.findById(cId);
        Assert.assertThat(countyRepository.findById(cId).getName(), CoreMatchers.is("Vilniaus test"));
        countyService.delete(cId);
        Assert.assertThat(countyRepository.findAll().size(), CoreMatchers.is(0));

    }

    @Test
    public void deleteCounty() throws Exception {
        //setup
        CountyEntity vilnius = new CountyEntity();
        vilnius.setName("Vilniaus test");
        countyService.save(vilnius);
        int countBeforeDelete = countyRepository.findAll().size();
        countyService.delete(1L);
        int countAfterDelete = countyRepository.findAll().size();
        //verify
        Assert.assertThat(countAfterDelete, CoreMatchers.is(countBeforeDelete-1));
    }
}