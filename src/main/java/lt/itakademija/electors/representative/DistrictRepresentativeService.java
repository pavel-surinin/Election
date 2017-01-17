package lt.itakademija.electors.representative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Pavel on 2017-01-16.
 */
@Service
public class DistrictRepresentativeService {

    @Autowired
    DistrictRepresentativeRepository repository;

    public List<DistrictRepresentativeReport> getAllRepresentatives() {
        List<DistrictRepresentativeReport> list = repository.findAll().stream()
                .map(ent -> {
                    DistrictRepresentativeReport rep = new DistrictRepresentativeReport();
                    rep.setId(ent.getId());
                    if (ent.getDistrict() == null) {
                        rep.setDistrictId(null);
                        rep.setDistrictName(null);
                    }else{
                        rep.setDistrictId(ent.getDistrict().getId());
                        rep.setDistrictName(ent.getDistrict().getName());
                    }
                    rep.setName(ent.getName());
                    rep.setSurname(ent.getSurname());
                    return rep;
                })
                .collect(Collectors.toList());
        return list;
    }

    @Transactional
    public DistrictRepresentativeEntity save(DistrictRepresentativeEntity representative) {
        return repository.save(representative);
    }
}
