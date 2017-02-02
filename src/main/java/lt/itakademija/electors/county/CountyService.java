package lt.itakademija.electors.county;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.candidate.CandidateReport;
import lt.itakademija.electors.candidate.CandidateService;
import lt.itakademija.electors.district.DistrictReport;
import lt.itakademija.electors.party.PartyService;
import lt.itakademija.exceptions.BadCSVFileExceprion;
import lt.itakademija.exceptions.CandidateIsInCountyException;
import lt.itakademija.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Pavel on 2017-01-12.
 */
@Service
public class CountyService {

    @Autowired
    StorageService storageService;

    @Autowired
    CountyRepository repository;

    @Autowired
    CandidateService candidateService;

    @Autowired
    PartyService partyService;

    public List<CountyEntity> getAllEntities() {
        return repository.findAll();
    }

    public List getAll() {
        return repository.findAll()
                .stream()
                .map(ent -> {
                    CountyReport rep = new CountyReport(ent);
                    return rep;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public CountyEntity save(CountyEntity county) {
        if (county.getCandidates() != null) {
            county.getCandidates().stream()
                    .forEach(c -> {
                        CandidateEntity ce = candidateService.findByIdEntity(c.getId());
                        ce.setCounty(county);
                        candidateService.save(ce);
                    });
            county.setCandidates(null);
        }
        return repository.save(county);
    }

    public CountyDetailsReport getCountyById(Long id) {
        CountyEntity county = repository.findById(id);
        CountyDetailsReport report = new CountyDetailsReport();
        report.setId(county.getId());
        report.setName(county.getName());
        List<CandidateReport> candidateReport = county.getCandidates()
                .stream()
                .map(can -> new CandidateReport(can))
                .collect(Collectors.toList());
        report.setCandidates(candidateReport);
        if (county.getDistricts() != null) {
            List<DistrictReport> districtReports = county.getDistricts()
                    .stream()
                    .map(d -> new DistrictReport(d))
                    .collect(Collectors.toList());
            report.setDistricts(districtReports);
        } else {
            report.setDistricts(null);
        }

        return report;
    }

    @Transactional
    public boolean delete(Long id) {
        repository.findById(id).getCandidates()
                .stream().forEach(c -> {
                    c.setCounty(null);
                    candidateService.save(c);
        });
        repository.delete(id);
        return true;
    }

    public CountyEntity getCountyByIdCountyEnt(Long id) {
        return repository.findById(id);
    }

    public void update(Long countyId, MultipartFile file) {
        CountyEntity county = repository.findById(countyId);
        List<CandidateEntity> candidatesFromFile = storageService.store("County", file);
        validateCandidateIsInCounty(candidatesFromFile);
        final List<CandidateEntity> noPartyCandidates = filterNoPartyCandidates(candidatesFromFile);
        final List<CandidateEntity> existingCandidates = filterExistingCandidates(candidatesFromFile);
        final List<CandidateEntity> candidatesNotInMultiList = filterCandidatesNotInMultiList(candidatesFromFile);
        validateSplitListsAreEqualSizeWithOriginal(noPartyCandidates,existingCandidates,candidatesNotInMultiList,candidatesFromFile);

        noPartyCandidates.stream().forEach(can -> {
            can.setCounty(county);
            candidateService.save(can);
        });

        existingCandidates.stream().forEach(can -> {
            CandidateEntity existingCan = candidateService.getCandidateByNameSurnameNumberParty(can);
            existingCan.setCounty(county);
            candidateService.save(existingCan);
        });

        candidatesNotInMultiList.stream().forEach(can -> {
            can.setCounty(county);
            can.setMultiList(false);
            candidateService.save(can);
        });
    }

    private void validateSplitListsAreEqualSizeWithOriginal(List<CandidateEntity> noPartyCandidates,
                                                            List<CandidateEntity> existingCandidates,
                                                            List<CandidateEntity> candidatesNotInMultiList,
                                                            List<CandidateEntity> candidatesFromFile) {
        if(noPartyCandidates.size() + existingCandidates.size() + candidatesNotInMultiList.size() != candidatesFromFile.size()){
            throw new BadCSVFileExceprion("Bad Candidates data in CSV, not all acndidates are passing bussines logic");
        }
    }

    private void validateCandidateIsInCounty(List<CandidateEntity> cans) {
        cans.stream().forEach(can -> {
            CandidateEntity canFromDb = candidateService.getCandidateByNameSurnameNumberParty(can);
            if(canFromDb != null){
                if (canFromDb.getCounty() != null) {
                    throw new CandidateIsInCountyException("Candidate " + can.getName() + " is in County " + can.getCounty());
                }
            }
        });
    }

    private List<CandidateEntity> filterCandidatesNotInMultiList(List<CandidateEntity> candidatesFromCsv) {
        return candidatesFromCsv
                .stream()
                .filter(can -> can.getPartyDependencies() != null)
                .filter(can -> candidateService.getCandidateByNameSurnameNumberParty(can) == null)
                .collect(Collectors.toList());
    }

    private List<CandidateEntity> filterExistingCandidates(List<CandidateEntity> candidatesFromCsv) {
        return candidatesFromCsv
                .stream()
                .filter(can -> can.getPartyDependencies() != null)
                .filter(can -> candidateService.getCandidateByNameSurnameNumberParty(can) != null)
                .collect(Collectors.toList());
    }

    private List<CandidateEntity> filterNoPartyCandidates(List<CandidateEntity> candidatesFromCsv) {
        return candidatesFromCsv
                .stream()
                .filter(can -> can.getPartyDependencies() == null)
                .collect(Collectors.toList());
    }
}


