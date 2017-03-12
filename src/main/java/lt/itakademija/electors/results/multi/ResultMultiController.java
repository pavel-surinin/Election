package lt.itakademija.electors.results.multi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Created by Pavel on 2017-02-03.
 */
@RestController
public class ResultMultiController {

    @Autowired
    ResultMultiService service;

    @PostMapping("/result-multi")
    public String save(@RequestBody final List<ResultMultiEntity> results){
        service.validateRatingPoints(results);
        return service.save(results);
    }

    @PatchMapping("/result/multi/{id}/approve/")
    public String approve(@PathVariable Long id){
        return service.approve(id);
    }

    @DeleteMapping("/result/multi/{id}")
    public String delete(@PathVariable Long id){
        return service.delete(id);
    }
}
