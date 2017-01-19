package lt.itakademija.electors.district;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Pavel on 2017-01-14.
 */
@Service
public class DistrictService {

    @Autowired
    DistrictRepository repository;

    public List getDistrictsList() {
        List<DistrictReport> list = repository.findAll().stream()
                .map(ent -> {
                    DistrictReport rep = new DistrictReport();
                    rep.setId(ent.getId());
                    rep.setName(ent.getName());
                    rep.setAdress(ent.getAdress());
                    if (ent.getRepresentative() == null) {
                        rep.setRepresentativeId(null);
                        rep.setRepresentativeName(null);
                    } else {
                        rep.setRepresentativeId(ent.getRepresentative().getId());
                        rep.setRepresentativeName(ent.getRepresentative().getName() + " " + ent.getRepresentative().getSurname());
                    }
                    if (ent.getCounty() == null) {
                        rep.setCountyId(null);
                        rep.setCountyName(null);
                    } else {
                        rep.setCountyId(ent.getCounty().getId());
                        rep.setCountyName(ent.getCounty().getName());
                    }
                    return rep;
                })
                .collect(Collectors.toList());

        return list;
    }

    @Transactional
    public DistrictEntity save(DistrictEntity apylinke) {
        return repository.save(apylinke);
    }
}
