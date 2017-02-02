package lt.itakademija.electors.results;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Pavel on 2017-02-02.
 */
@RestController
public class ResultSingleController {

    @Autowired
    ResultSingleService service;

    @PostMapping("/result-single")
    public String save(@RequestBody List<ResultSingleEntity> results){
        return service.save(results);
    }
}
