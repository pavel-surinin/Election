package lt.itakademija.electors.candidate;

import lt.itakademija.electors.party.PartyEntity;
import lt.itakademija.electors.party.PartyReport;
import lt.itakademija.electors.party.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 2017-01-12.
 */
@Service
public class CandidateService {

    @Autowired
    CandidateRepository repository;

    @Autowired
    PartyService partyService;
    
    @Transactional
    public CandidateEntity save(CandidateEntity candidateEntity) {
        return repository.save(candidateEntity);
    }

    public List<CandidateReport> getAllCandidates() {
        List<CandidateReport> list = new ArrayList<>();
        for (CandidateEntity candidateEntity : repository.getCandidatesList()) {
            CandidateReport kr = new CandidateReport();
            kr.setId(candidateEntity.getId());
            kr.setName(candidateEntity.getName());
            kr.setSurname(candidateEntity.getSurname());
            kr.setDescription(candidateEntity.getDescription());
            kr.setBirthDate(candidateEntity.getBirthDate());
            if (candidateEntity.getPartyDependencies() == null) {
                kr.setPartijosId(null);
                kr.setPartijosPavadinimas(null);
            } else {
                kr.setPartijosId(candidateEntity.getPartyDependencies().getId());
                kr.setPartijosPavadinimas(candidateEntity.getPartyDependencies().getName());
            }
            list.add(kr);
        }
        return list;
    }

    public CandidateEntity findByIdEntity(Long id){
        return repository.finById(id);
    }

    @Transactional
    public boolean delete(Long id){
        return repository.delete(id);
    }

    @Transactional
    public boolean deleteCandidatesByPartyId(Long id) {
        PartyEntity partyEntity = partyService.getPartyEntityById(id);
        List<CandidateReport> candidates = partyService.getPartyById(id).getMembers();
        partyService.detach(partyEntity);
        for (CandidateReport candidate : candidates) {
            System.out.println(candidate.getId());
            repository.delete(candidate.getId());
        }
    return true;
    }
}
