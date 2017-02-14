package lt.itakademija.electors.results;

import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.district.DistrictRepository;
import lt.itakademija.electors.results.reports.ResultDistrictReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Pavel on 2017-02-13.
 */
@Service
public class ResultsService {

    @Autowired
    DistrictRepository districtRepository;

    public ResultDistrictReport getDistrictResults(Long id) {
        final DistrictEntity district = districtRepository.findById(id);
        return new ResultDistrictReport(district);
    }
}
