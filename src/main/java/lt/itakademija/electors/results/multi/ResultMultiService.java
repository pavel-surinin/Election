package lt.itakademija.electors.results.multi;

import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.district.DistrictRepository;
import lt.itakademija.electors.results.single.ResultSingleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Pavel on 2017-02-03.
 */
@Service
public class ResultMultiService {

    @Autowired
    ResultMultiRepository repository;

    @Autowired
    DistrictRepository districtRepository;

    @Transactional
    public String save(List<ResultMultiEntity> results) {
        results.stream().forEach(res -> repository.save(res));
        return "Votes registered";
    }

    @Transactional
    public String approve(Long id) {
        List<ResultMultiEntity> results = getDistrictResults(id);
        results.forEach(res -> res.setApproved(true));
        save(results);
        return "Votes Approved";
    }

    @Transactional
    public String delete(Long id) {
        getDistrictResults(id).stream().forEach(res -> repository.delete(res));
        return "Votes Deleted";
    }

    private List<ResultMultiEntity> getDistrictResults(Long id) {
        DistrictEntity district = districtRepository.findById(id);
        List<ResultMultiEntity> listOfResults = repository.getByDistrictId(district);
        return listOfResults;
    }
}
