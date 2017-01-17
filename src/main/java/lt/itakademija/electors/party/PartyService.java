package lt.itakademija.electors.party;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 2017-01-12.
 */
@Service
public class PartyService {

    @Autowired
    PartyRepository repository;

    @Transactional
    public PartyEntity save(PartyEntity party) {
        return repository.save(party);
    }

    public List<PartyReport> getPartyList() {
        List<PartyReport> list = new ArrayList<>();
        for (PartyEntity party : repository.findAll()) {
            PartyReport report = new PartyReport();
            report.setId(party.getId());
            report.setName(party.getName());
            List<Long> narysIdList = new ArrayList<>();
            party.getMembers().forEach(narys -> narysIdList.add(narys.getId()));
            report.setMembers(narysIdList);
            list.add(report);
        }
        return list;
    }
}
