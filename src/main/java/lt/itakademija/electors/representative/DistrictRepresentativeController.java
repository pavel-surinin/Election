package lt.itakademija.electors.representative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Pavel on 2017-01-16.
 */
@RestController
public class DistrictRepresentativeController {

    @Autowired
    DistrictRepresentativeService service;

    @GetMapping("/representative")
    public List<DistrictRepresentativeReport> getAllRepresentatives(){
        return service.getAllRepresentatives();
    }

    @PostMapping("/representative")
    public DistrictRepresentativeEntity save(@RequestBody DistrictRepresentativeEntity representative){
        return service.save(representative);
    }


}
