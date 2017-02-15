package lt.itakademija.electors.results;

import lt.itakademija.electors.candidate.CandidateReport;
import lt.itakademija.electors.county.CountyEntity;
import lt.itakademija.electors.county.CountyRepository;
import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.district.DistrictRepository;
import lt.itakademija.electors.results.multi.ResultMultiEntity;
import lt.itakademija.electors.results.multi.rating.RatingEntity;
import lt.itakademija.electors.results.reports.ResultCountyReport;
import lt.itakademija.electors.results.reports.ResultDistrictReport;
import lt.itakademija.electors.results.reports.dto.CandidateIntDTO;
import lt.itakademija.electors.results.reports.dto.PartyIntDTO;
import lt.itakademija.electors.results.single.ResultSingleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Pavel on 2017-02-13.
 */
@Service
public class ResultsService {

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    CountyRepository countyRepository;

    public ResultDistrictReport getDistrictResults(Long id) {
        final DistrictEntity district = districtRepository.findById(id);
        return new ResultDistrictReport(district);
    }

    public ResultCountyReport getCountyResults(Long id) {
        final CountyEntity county = countyRepository.findById(id);
        ResultCountyReport report = new ResultCountyReport();

        int spoiledSingle = county.getDistricts()
                .stream()
                .filter(d -> d.getSpoiledSingle() != null)
                .mapToInt(d -> d.getSpoiledSingle())
                .sum();
        report.setSpoiledSingle(spoiledSingle);

        int spoiledMulti = county.getDistricts()
                .stream()
                .filter(d -> d.getSpoiledMulti() != null)
                .mapToInt(d -> d.getSpoiledMulti())
                .sum();
        report.setSpoiledMulti(spoiledMulti);

        List<ResultSingleEntity> allSingleResults = getCountySingleResultList(county);
        Set<CandidateIntDTO> candidatesIntDtoSet = getCandidatesIntDtoSet(allSingleResults);
        List<CandidateIntDTO> candidatesResultList = getCandidatesResults(allSingleResults, candidatesIntDtoSet);
        report.setCandidatesVotesSummary(candidatesResultList);

        if (candidatesResultList.size() != 0) {
            report.setSingleMandateWinner(candidatesResultList.get(0).getCandidate());
        }

        List<ResultMultiEntity> countyMultiResults = getCountyMultiResults(county);
        List<RatingEntity> countyRatings = getCountyRatings(countyMultiResults);
        List<PartyIntDTO> partyIntDTOList = getPartyIntDtoList(countyMultiResults);
        List<PartyIntDTO> partiesVotesSummary = getPartiesVotesSummary(countyMultiResults, countyRatings, partyIntDTOList);

        for (PartyIntDTO partyIntDTO : partiesVotesSummary) {
            List<CandidateIntDTO> newRatings = new ArrayList<>();
            for (RatingEntity countyRating : countyRatings) {
                if (countyRating.getCandidate().getPartyDependencies().getId().equals(partyIntDTO.getPar().getId())) {
                    newRatings.add(new CandidateIntDTO(countyRating.getCandidate(), countyRating.getPoints()));
                }
            }
            Map<CandidateReport, List<CandidateIntDTO>> collect = newRatings
                    .stream()
                    .collect(Collectors.groupingBy(CandidateIntDTO::getCandidate));
            List<CandidateIntDTO> ratings = new ArrayList<>();
            collect.forEach((key, value) -> {
                ratings.add(new CandidateIntDTO(key, value
                        .stream()
                        .mapToInt(CandidateIntDTO::getCount)
                        .sum()));
            });
            partyIntDTO.setRatings(ratings.stream().sorted((ra1,ra2) -> ra2.getCount().compareTo(ra1.getCount())).collect(Collectors.toList()));
        }
        report.setPartiesVotesSummary(partiesVotesSummary);

        return report;
    }

    private List<PartyIntDTO> getPartyIntDtoList(List<ResultMultiEntity> countyMultiResults) {
        return countyMultiResults
                .stream()
                .map(r -> new PartyIntDTO(r.getParty(), 0, new ArrayList<RatingEntity>()))
                .distinct()
                .collect(Collectors.toList());
    }

    private List<PartyIntDTO> getPartiesVotesSummary(List<ResultMultiEntity> countyMultiResults, List<RatingEntity> countyRatings, List<PartyIntDTO> partyIntDTOList) {
        return partyIntDTOList
                .stream()
                .map(partyDto -> {
                    countyMultiResults
                            .stream()
                            .filter(result -> result.getParty().getId().equals(partyDto.getPar().getId()))
                            .forEach(result -> {
                                partyDto.setCount(partyDto.getCount() + result.getVotes().intValue());
//                                List<CandidateIntDTO> ratings = countyRatings
//                                        .stream()
//                                        .filter(rat -> rat.getCandidate().getPartyDependencies().getId().equals(partyDto.getPar().getId()))
//                                        .map(rating -> {
//                                            if (partyDto.getRatings().contains(rating)){
//                                                int indexOf = partyDto.getRatings().indexOf(rating);
//                                                partyDto.getRatings().get(indexOf).setCount(partyDto.getRatings().get(indexOf).getCount() + rating.getPoints());
//                                                return new CandidateIntDTO(
//                                                        rating.getCandidate()
//                                                        ,partyDto.getRatings().get(indexOf).getCount() + rating.getPoints());
//                                            } else {
//                                                return new CandidateIntDTO(rating.getCandidate(), rating.getPoints());
//                                            }
//                                        })
//                                        .sorted((ra1,ra2) -> ra2.getCount().compareTo(ra1.getCount()))
//                                        .collect(Collectors.toList());
//                                partyDto.setRatings(ratings);
                            });

                    return partyDto;
                })
                .sorted((r1, r2) -> r2.getCount().compareTo(r1.getCount()))
                .collect(Collectors.toList());
    }

    private List<RatingEntity> getCountyRatings(List<ResultMultiEntity> countyMultiResults) {
        return countyMultiResults
                .stream()
                .flatMap(r -> r.getRating().stream())
                .collect(Collectors.toList());
    }

    private List<ResultMultiEntity> getCountyMultiResults(CountyEntity county) {
        return county.getDistricts()
                .stream()
                .map(d -> d.getResultMultiEntity())
                .flatMap(r -> r.stream())
                .collect(Collectors.toList());
    }

    private List<CandidateIntDTO> getCandidatesResults(List<ResultSingleEntity> allSingleResults, Set<CandidateIntDTO> candidatesIntDtoSet) {
        return candidatesIntDtoSet
                .stream()
                .map(cid -> {
                    allSingleResults
                            .stream()
                            .filter(r -> r.getCandidate().getId().equals(cid.getCandidate().getId()))
                            .forEach(r -> cid.setCount((cid.getCount() + r.getVotes().intValue())));
                    return cid;
                })
                .sorted((r1, r2) -> r2.getCount().compareTo(r1.getCount()))
                .collect(Collectors.toList());
    }

    private Set<CandidateIntDTO> getCandidatesIntDtoSet(List<ResultSingleEntity> allSingleResults) {
        return allSingleResults
                .stream()
                .map(r -> new CandidateIntDTO(r.getCandidate(), 0))
                .distinct()
                .collect(Collectors.toSet());
    }

    private List<ResultSingleEntity> getCountySingleResultList(CountyEntity county) {
        return county.getDistricts()
                .stream()
                .map(d -> d.getResultSingleEntity())
                .flatMap(r -> r.stream())
                .collect(Collectors.toList());
    }
}
