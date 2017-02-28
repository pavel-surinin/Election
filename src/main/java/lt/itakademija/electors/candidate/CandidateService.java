package lt.itakademija.electors.candidate;

import lt.itakademija.electors.county.CountyEntity;
import lt.itakademija.electors.county.CountyRepository;
import lt.itakademija.electors.county.CountyService;
import lt.itakademija.electors.district.DistrictRepository;
import lt.itakademija.electors.party.PartyEntity;
import lt.itakademija.electors.party.PartyRepository;
import lt.itakademija.electors.party.PartyService;
import lt.itakademija.electors.results.single.ResultSingleRepository;
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
    PartyRepository partyRepository;
    @Autowired
    CandidateRepository repository;
    @Autowired
    PartyService partyService;
    @Autowired
    CountyService countyService;
    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    CountyRepository countyRepository;
    @Autowired
    ResultSingleRepository resultSingleRepository;

    @Transactional
    public CandidateEntity save(CandidateEntity can) {
        return repository.save(mapCounty(can));
    }

    public List<CandidateReport> getAllCandidates() {
        return repository.getCandidatesList().stream().map(can -> new CandidateReport(can)).collect(Collectors.toList());
    }

    public CandidateEntity findByIdEntity(Long id){
        return repository.finById(id);
    }

    @Transactional
    public boolean deleteCandidatesByPartyId(Long id) {
        PartyEntity party = partyService.getPartyEntityById(id);

        List<CandidateEntity> membersToDelete = party.getMembers()
                .stream()
                .filter(CandidateEntity::isMultiList)
                .collect(Collectors.toList());

        List<CandidateEntity> members = party.getMembers();
        members.removeAll(membersToDelete);
        party.setMembers(members);
        partyService.save(party);
        repository.deleteAll(membersToDelete);
        return true;
    }

    public CandidateEntity getCandidateByNameSurnameNumberParty(CandidateEntity can){
        return repository.findByNumberInPartyNameSurnameParty(can);
    }

	public CandidateReport getCandidateById(Long id) {
		CandidateEntity candidate = repository.finById(id);
		CandidateReport report = new CandidateReport(candidate);
		return report;
	}

    private CandidateEntity mapCounty(CandidateEntity can) {
        if (can.getCounty() != null) {
            if(can.getCounty().getId() != null){
                CountyEntity county = countyService.getCountyByIdCountyEnt(can.getCounty().getId());
                can.setCounty(county);
            } else {
                can.setCounty(null);
            }
            return can;
        } else {
            return can;
        }
    }

    public List<CandidateReport> getCandidateByDistrictId(Long id) {
        return districtRepository.findById(id).getCounty().getCandidates()
                .stream()
                .map(c-> new CandidateReport(c))
                .collect(Collectors.toList());
    }

    public List<CandidateEntity> getCandidatesByCounty(CountyEntity county){
        return repository.findByCounty(county);
    }

    @Transactional
    public boolean delete(CandidateEntity c) {
        return repository.delete(c);
    }

    @Transactional
    public boolean delete(Long id){
        return repository.delete(id);
    }
}
