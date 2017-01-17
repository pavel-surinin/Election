package lt.itakademija.electors.party;

import lt.itakademija.electors.candidate.CandidateEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Pavel on 2017-01-12.
 */
public class PartijaEntityTest {

    @Autowired
    PartyService service;

    @Test
    public void testCreatePartija(){
        PartyEntity zal = new PartyEntity();
        zal.setName("Zalieji");
        List<CandidateEntity> zalNariai = new ArrayList();
        CandidateEntity skvernelis = new CandidateEntity();
        skvernelis.setName("Saulius");
        skvernelis.setSurname("Skvernelis");
        skvernelis.setBirthDate(new Date());
        skvernelis.setDescription("Negeriu, beveik");
        System.out.println(zal);
        zalNariai.add(skvernelis);
        zal.setMembers(zalNariai);
        service.save(zal);


    }

}