package lt.itakademija.electors.party;

import lt.itakademija.electors.candidate.CandidateReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            List<PartyEntity> narysIdList = new ArrayList<>();
            list.add(report);
        }
        return list;
    }

	public PartyReport getPartyById(Long id) {
		PartyEntity party = repository.getById(id);
		PartyReport pr =new PartyReport();
		pr.setId(party.getId());
		pr.setName(party.getName());
		List<CandidateReport> members = party.getMembers()
			.stream()	
			.map(c -> {
				CandidateReport cr = new CandidateReport();
				cr.setId(c.getId());
				cr.setName(c.getName());
				cr.setSurname(c.getSurname());
				cr.setBirthDate(c.getBirthDate());
				cr.setDescription(c.getDescription());
				cr.setNumberInParty(c.getNumberInParty());
				return cr;
			})
			.collect(Collectors.toList());
		pr.setMembers(members);
		return pr;
    }

    public PartyEntity getPartyEntityById(Long id){
    	return repository.getById(id);
	}

	public void detach(PartyEntity partyEntity) {
    	repository.detach(partyEntity);
	}
}
