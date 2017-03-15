package lt.itakademija.electors.results;

import lt.itakademija.electors.results.csv.*;
import lt.itakademija.electors.results.reports.ResultCountyReport;
import lt.itakademija.electors.results.reports.ResultDistrictReport;
import lt.itakademija.electors.results.reports.ResultsGeneralReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Pavel on 2017-02-13.
 */
@RestController
public class ResultsController {

    @Autowired
    ResultsService service;

    @Autowired
    ResultsToCsvService resultsToCsvService;

    @GetMapping("/results/csv/single")
    public ResponseEntity getCsvResultsSingle(HttpServletResponse response) {
        CSVUtils.modifyResponse(response,"single-winners");
        return resultsToCsvService.getCsvResults(new ResultsCsvSingle(service.getGeneralReport()));
    }

    @GetMapping("/results/csv/multi/winners")
    public ResponseEntity getCsvResultsMulti(HttpServletResponse response) {
        CSVUtils.modifyResponse(response,"multi-winners");
        return resultsToCsvService.getCsvResults(new ResultsCsvMulti(service.getGeneralReport()));
    }

    @GetMapping("/results/csv/mandates")
    public ResponseEntity getCsvResultsMandates(HttpServletResponse response) {
        CSVUtils.modifyResponse(response,"mandates-in-multi");
        return resultsToCsvService.getCsvResults(new ResultsCsvMandatesPerPartyMulti(service.getGeneralReport()));
    }

    @GetMapping("/results/csv/multi/votes")
    public ResponseEntity getCsvResultsMultiVotes(HttpServletResponse response) {
        CSVUtils.modifyResponse(response,"votes-in-multi");
        return resultsToCsvService.getCsvResults(new ResultsCsvVotesInMulti(service.getGeneralReport()));
    }

    @GetMapping("/results/csv/mandates/general")
    public ResponseEntity getCsvResultsMandatesGeneral(HttpServletResponse response) {
        CSVUtils.modifyResponse(response,"mandates-general");
        return resultsToCsvService.getCsvResults(new ResultsCsvMandatesPerPartyGeneral(service.getGeneralReport()));
    }

    @GetMapping("results/district/{id}")
    public ResultDistrictReport getDistrictResults(@PathVariable Long id){
        return service.getDistrictResults(id);
    }

    @GetMapping("results/county/{id}")
    public ResultCountyReport getCountyResults(@PathVariable Long id) {
        return service.formOrGetCountyResults(id);
    }

    @GetMapping("results/general")
    public ResultsGeneralReport getGeneralResults(){
        return service.getGeneralReport();
    }
}
