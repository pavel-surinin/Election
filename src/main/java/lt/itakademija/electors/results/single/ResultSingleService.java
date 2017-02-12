package lt.itakademija.electors.results.single;

import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.district.DistrictRepository;
import lt.itakademija.electors.district.DistrictService;
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

    @Autowired
    DistrictService districtService;

    @Transactional
    public String save(List<ResultSingleEntity> results) {
        final ResultSingleEntity spoiled = results.stream().filter(res -> res.getCandidate().getId() == -1991L).findAny().get();
        DistrictEntity district = spoiled.getDistrict();
        final DistrictEntity districtEnt = districtRepository.findById(district.getId());
        districtEnt.setSpoiledSingle(spoiled.getVotes().intValue());
        districtService.save(districtEnt);
        results.remove(spoiled);
        results.stream().forEach(res -> repository.save(res));
        return "Votes registered";
    }

    @Transactional
    public String approve(Long id) {
        List<ResultSingleEntity> listOfResults = getDistrictResults(id);
        listOfResults.stream().forEach(res -> res.setApproved(true));
        listOfResults.stream().forEach(res -> repository.save(res));
        return "Results approved";
    }

    @Transactional
    public String delete(Long id) {
        districtRepository.findById(id).setSpoiledSingle(null);
        getDistrictResults(id).stream().forEach(res -> repository.delete(res));
        return "Results Deleted";
    }

    private List<ResultSingleEntity> getDistrictResults(Long id) {
        DistrictEntity district = districtRepository.findById(id);
        List<ResultSingleEntity> listOfResults = repository.getResultsByDistrictId(district);
        return listOfResults;
    }
}