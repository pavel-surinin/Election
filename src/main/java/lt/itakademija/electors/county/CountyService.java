package lt.itakademija.electors.county;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.candidate.CandidateReport;
import lt.itakademija.electors.candidate.CandidateRepository;
import lt.itakademija.electors.candidate.CandidateService;
import lt.itakademija.electors.district.DistrictReport;
import lt.itakademija.electors.district.DistrictService;
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
public class CountyService {

    @Autowired
    CountyRepository repository;

    @Autowired
    CandidateService candidateService;

    public List<CountyEntity> getAllEntities(){
        return repository.findAll();
    }

    public List getAll() {
        return repository.findAll()
                .stream()
                .map(ent -> {
                    CountyReport rep = new CountyReport(ent.getId(),ent.getName());
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
                .map(can -> {
                    CandidateReport cr = new CandidateReport();
                    cr.setId(can.getId());
                    cr.setName(can.getName());
                    cr.setSurname(can.getSurname());
                    cr.setBirthDate(can.getBirthDate());
                    cr.setPartijosId(can.getPartyDependencies().getId());
                    cr.setPartijosPavadinimas(can.getPartyDependencies().getName());
                    return cr;
                })
                .collect(Collectors.toList());
        report.setCandidates(candidateReport);
        List<DistrictReport> districtReports = county.getDistricts()
                .stream()
                .map(d -> {
                    DistrictReport dr = new DistrictReport();
                    dr.setId(d.getId());
                    dr.setName(d.getName());
                    dr.setAdress(d.getAdress());
                    dr.setRepresentativeName(d.getRepresentative().getName() + " " + d.getRepresentative().getSurname());
                    return dr;
                })
                .collect(Collectors.toList());
        report.setDistricts(districtReports);

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

    public CountyEntity getCountyByIdCountyEnt(Long id){
        return repository.findById(id);
    }
}
