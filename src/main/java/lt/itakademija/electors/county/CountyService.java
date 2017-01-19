package lt.itakademija.electors.county;

import lt.itakademija.electors.candidate.CandidateService;
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

    @Autowired
    DistrictService districtService;

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
        return repository.save(county);
    }

    public CountyDetailsReport getCountyById(Long id) {
        CountyEntity county = repository.findById(id);
        CountyDetailsReport report = new CountyDetailsReport();
        report.setId(county.getId());
        report.setName(county.getName());



        return report;


    }
}
