package lt.itakademija.electors.results;

import lt.itakademija.electors.results.reports.ResultCountyReport;
import lt.itakademija.electors.results.reports.ResultDistrictReport;
import lt.itakademija.electors.results.reports.ResultsGeneralReport;
import org.springframework.beans.factory.annotation.Autowired;
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
        return service.getGeneralResults();
    }
}
