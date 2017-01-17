package lt.itakademija.electors.district;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Pavel on 2017-01-14.
 */
@RestController
public class DistrictController {

    @Autowired
    DistrictService service;

    @GetMapping("/district")
    public List getDistrictsList(){
        return service.getDistrictsList();
    }

    @PostMapping("/district")
    public DistrictEntity save(@RequestBody DistrictEntity district){
        return service.save(district);
    }
}
