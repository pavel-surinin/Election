package lt.itakademija.electors.results.single;

import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.district.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Pavel on 2017-02-02.
 */
@Service
public class ResultSingleService {

    @Autowired
    ResultSingleRepository repository;

    @Autowired
    DistrictRepository districtRepository;

    @Transactional
    public String save(List<ResultSingleEntity> results) {
     results.stream().forEach(result -> repository.save(result));
     return "Votes registered";
    }

    @Transactional
    public String approve(Long id) {
        List<ResultSingleEntity> listOfResults = getDistrictResults(id);
        listOfResults.stream().forEach(res -> res.setApproved(true));
        save(listOfResults);
        return "Results approved";
    }

    @Transactional
    public String delete(Long id) {
        getDistrictResults(id).stream().forEach(res -> repository.delete(res));
        return "Results Deleted";
    }

    private List<ResultSingleEntity> getDistrictResults(Long id) {
        DistrictEntity district = districtRepository.findById(id);
        List<ResultSingleEntity> listOfResults = repository.getByDistrictId(district);
        return listOfResults;
    }
}
