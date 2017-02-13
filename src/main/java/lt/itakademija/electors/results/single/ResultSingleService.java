package lt.itakademija.electors.results.single;

import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.district.DistrictRepository;
import lt.itakademija.electors.district.DistrictService;
import lt.itakademija.electors.results.multi.ResultMultiEntity;
import lt.itakademija.electors.results.multi.ResultMultiRepository;
import lt.itakademija.exceptions.NotEqualVotersSumException;
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
    ResultMultiRepository resultMultiRepository;

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
        validateEqualVotersSum(results, district);
        final DistrictEntity districtEnt = districtRepository.findById(district.getId());
        districtEnt.setSpoiledSingle(spoiled.getVotes().intValue());
        districtService.save(districtEnt);
        results.remove(spoiled);
        results.stream().forEach(res -> repository.save(res));
        return "Votes registered";
    }

    private void validateEqualVotersSum(List<ResultSingleEntity> results, DistrictEntity district) {
        final int sumOfVotes = results.stream().mapToInt(r -> r.getVotes().intValue()).sum();
        final List<ResultMultiEntity> resultsMulti = resultMultiRepository.getByDistrictId(district);
        if (resultsMulti.size() != 0){
            final long votesSumMulti = resultsMulti.stream().mapToLong(r -> r.getVotes()).sum();
            if (votesSumMulti != sumOfVotes){
                throw new NotEqualVotersSumException("Not equal voters sum. MultiResuts sum is " + votesSumMulti + ". SingleResults entered " + sumOfVotes);
            }
        }
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
        List<ResultSingleEntity> listOfResults = repository.getByDistrictId(district);
        return listOfResults;
    }
}