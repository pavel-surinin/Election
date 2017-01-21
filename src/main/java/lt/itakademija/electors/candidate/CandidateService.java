package lt.itakademija.electors.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.itakademija.electors.party.PartyEntity;
import lt.itakademija.electors.party.PartyReport;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 2017-01-12.
 */
@Service
public class CandidateService {

    @Autowired
    CandidateRepository repository;

    @Transactional
    public CandidateEntity save(CandidateEntity candidateEntity) {
        return repository.save(candidateEntity);
    }

    public List<CandidateReport> getKandidatasList() {
        List<CandidateReport> list = new ArrayList<>();
        for (CandidateEntity candidateEntity : repository.getCandidatesList()) {
            CandidateReport kr = new CandidateReport();
            kr.setId(candidateEntity.getId());
            kr.setName(candidateEntity.getName());
            kr.setSurname(candidateEntity.getSurname());
            kr.setDescription(candidateEntity.getDescription());
            kr.setBirthDate(candidateEntity.getBirthDate());
            kr.setPartijosId(candidateEntity.getPartyDependencies().getId());
            kr.setPartijosPavadinimas(candidateEntity.getPartyDependencies().getName());
            list.add(kr);
        }
        return list;
    }
    @Transactional
	public void delete(CandidateEntity members) {
		repository.delete(members);
	}

	

}
