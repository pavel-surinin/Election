package lt.itakademija.electors.results.multi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Pavel on 2017-02-03.
 */
@RestController
public class ResultMultiController {

    @Autowired
    ResultMultiService service;

    @PostMapping("/result-multi")
    public String save(@RequestBody List<ResultMultiEntity> results){
        return service.save(results);
    }
}
