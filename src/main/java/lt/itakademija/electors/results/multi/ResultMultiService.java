package lt.itakademija.electors.results.multi;

import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.district.DistrictRepository;
import lt.itakademija.electors.district.DistrictService;
import lt.itakademija.electors.results.single.ResultSingleEntity;
import lt.itakademija.electors.results.single.ResultSingleRepository;
import lt.itakademija.exceptions.NotEqualVotersSumException;
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
    ResultSingleRepository resultSingleRepository;

    @Autowired
    ResultMultiRepository repository;

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    DistrictService districtService;

    @Transactional
    public String save(List<ResultMultiEntity> results) {
        final ResultMultiEntity spoiled = results.stream().filter(res -> res.getParty().getId() == -1991L).findAny().get();
        DistrictEntity district = spoiled.getDistrict();
        final DistrictEntity districtEnt = districtRepository.findById(district.getId());
//        validateEqualVotersSum(results,district);
        districtEnt.setSpoiledMulti(spoiled.getVotes().intValue());
        districtService.save(districtEnt);
        results.remove(spoiled);
        results.stream().forEach(res -> {
            if (res.getRating() != null){
                res.getRating().stream().forEach(rat->rat.setMultiResults(res));
            }
            repository.save(res);
        });
        return "Votes registered";
    }

//    private void validateEqualVotersSum(List<ResultMultiEntity> results, DistrictEntity district) {
//        final int sumOfVotes = results.stream().mapToInt(r -> r.getVotes().intValue()).sum();
//        final List<ResultSingleEntity> resultsSingle = resultSingleRepository.getByDistrictId(district);
//        if (resultsSingle.size() != 0){
//            final long votesSumMulti = resultsSingle.stream().mapToLong(r -> r.getVotes()).sum();
//            if (votesSumMulti != sumOfVotes){
//                throw new NotEqualVotersSumException("Not equal voters sum. SingleResults sum is " + votesSumMulti + " .SingleResults entered " + sumOfVotes);
//            }
//        }
//    }

    @Transactional
    public String approve(Long id) {
        List<ResultMultiEntity> results = getDistrictResults(id);
        results.forEach(res -> res.setApproved(true));
        results.stream().forEach(res -> repository.save(res));
        return "Votes Approved";
    }

    @Transactional
    public String delete(Long id) {
        districtRepository.findById(id).setSpoiledMulti(null);
        getDistrictResults(id).stream().forEach(res -> repository.delete(res));
        return "Votes Deleted";
    }

    private List<ResultMultiEntity> getDistrictResults(Long id) {
        DistrictEntity district = districtRepository.findById(id);
        List<ResultMultiEntity> listOfResults = repository.getResultsByDistrictId(district);
        return listOfResults;
    }
}
