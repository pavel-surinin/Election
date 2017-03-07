package lt.itakademija.electors.district;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    
    @GetMapping("/district/{id}")
    public DistrictReport getDistrictDetails(@PathVariable Long id){
        return service.getDistrictById(id);
    }

    @GetMapping("/district/{id}/name")
    public String getNameById(@PathVariable Long id){
        return service.getNameById(id);
    }

    @GetMapping("/district/nonerepresentatives")
    public List getDistrictsWithNullRepresentativesList(){
        return service.getDistrictsWithNullRepresentativesList();
    }

    @GetMapping("/district/single/registered")
    public List getDistrictsSingleRegistered(){
        return service.getDistrictSingleRegistered();
    }

    @GetMapping("/district/multi/registered")
    public List getDistrictsMultiRegistered(){
        return service.getDistrictMultiRegistered();
    }

    @GetMapping("/district/{page}/page")
    public List getDistrictsByPage(@PathVariable Long page){
        return service.getDistictsByPage(page);
    }

    @GetMapping("/district/{letter}/letter")
    public List getDistrictsByPage(@PathVariable String letter){
        return service.getDistictsByFirstLetter(letter);
    }

    @GetMapping("/district/all")
    public Integer getNumberOfDistricts(){
        return service.getNumberOfDistricts();
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
