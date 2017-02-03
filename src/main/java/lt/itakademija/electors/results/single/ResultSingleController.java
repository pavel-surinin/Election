package lt.itakademija.electors.results.single;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/result-single/{id}/approve/")
    public String approve(@PathVariable Long id){
        return service.approve(id);
    }

    @DeleteMapping("/result-single/{id}")
    public String delete(@PathVariable Long id){
        return service.delete(id);
    }
}
