package lt.itakademija.electors.candidate;

import lt.itakademija.electors.county.CountyEntity;
import lt.itakademija.electors.party.PartyEntity;
import lt.itakademija.electors.party.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
//      To do if Candidate has no county just save, else set Candidate County
    	return repository.save(candidateEntity);  
    }

    public List<CandidateReport> getAllCandidates() {
        return repository.getCandidatesList().stream().map(can -> new CandidateReport(can)).collect(Collectors.toList());
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
    
    public CandidateEntity getCandidateByNameSurnameNumberParty(CandidateEntity can){
        return repository.findByNumberInPartyNameSurnameParty(can);
    }
    
	public CandidateReport getCandidateById(Long id) {
		CandidateEntity candidate = new CandidateEntity();
		CandidateReport report = new CandidateReport(candidate);
		report.setId(id);
		report.setName(candidate.getName());
		report.setSurname(candidate.getSurname());
		report.setBirthDate(candidate.getBirthDate());
		if(candidate.getPartyDependencies() != null){
			report.setPartijosId(candidate.getPartyDependencies().getId());
			report.setPartijosPavadinimas(candidate.getPartyDependencies().getName());
			report.setNumberInParty(candidate.getNumberInParty());	
		}
		report.setDescription(candidate.getDescription());
		return report;
	}
}
