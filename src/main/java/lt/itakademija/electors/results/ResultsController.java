package lt.itakademija.electors.results;

import lt.itakademija.electors.results.csv.*;
import lt.itakademija.electors.results.reports.ResultCountyReport;
import lt.itakademija.electors.results.reports.ResultDistrictReport;
import lt.itakademija.electors.results.reports.ResultsGeneralReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Pavel on 2017-02-13.
 */
@RestController
public class ResultsController {

    @Autowired
    ResultsService service;

    @Autowired
    ResultsToCsvService resultsToCsvService;

    @GetMapping(value = "/results/csv/single", produces = "application/csv")
    public ResponseEntity<InputStreamResource> getCsvResultsSingle() {
        return resultsToCsvService.gtCsvResults(new ResultsCsvSingle(service.getGeneralReport()));
    }

    @GetMapping(value = "/results/csv/multi/winners", produces = "application/csv")
    public ResponseEntity<InputStreamResource> getCsvResultsMulti() {
        return resultsToCsvService.gtCsvResults(new ResultsCsvMulti(service.getGeneralReport()));
    }

    @GetMapping(value = "/results/csv/mandates", produces = "application/csv")
    public ResponseEntity<InputStreamResource> getCsvResultsMandates() {
        return resultsToCsvService.gtCsvResults(new ResultsCsvMandatesPerPartyMulti(service.getGeneralReport()));
    }

    @GetMapping(value = "/results/csv/multi/votes", produces = "application/csv")
    public ResponseEntity<InputStreamResource> getCsvResultsMultiVotes() {
        return resultsToCsvService.gtCsvResults(new ResultsCsvVotesInMulti(service.getGeneralReport()));
    }

    @GetMapping(value = "/results/csv/mandates/general", produces = "application/csv")
    public ResponseEntity<InputStreamResource> getCsvResultsMandatesGeneral() {
        return resultsToCsvService.gtCsvResults(new ResultsCsvMandatesPerPartyGeneral(service.getGeneralReport()));
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
