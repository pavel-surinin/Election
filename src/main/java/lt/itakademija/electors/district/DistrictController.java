package lt.itakademija.electors.district;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/district/{id}")
    public boolean deleteDistrict(@PathVariable Long id) {
        return service.delete(id);
    }
}
