package lt.itakademija.electors.county;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Pavel on 2017-01-14.
 */
@RestController
public class CountyController {

    @Autowired
    CountyService service;

    @GetMapping("/county")
    public List getCountyList(){
        return service.getAll();
    }

    @PostMapping("/county")
    public CountyEntity save(@RequestBody CountyEntity county){
        return service.save(county);
    }

    @GetMapping("/county/{id}")
    public CountyDetailsReport getCountyDetals(@PathVariable Long id){
        return service.getCountyById(id);
    }

    @DeleteMapping("/county/{id}")
    public boolean deletecounty(@PathVariable Long id){
        return service.delete(id);
    }
    
}
