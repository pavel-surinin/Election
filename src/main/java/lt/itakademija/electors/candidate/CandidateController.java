package lt.itakademija.electors.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lt.itakademija.electors.county.CountyDetailsReport;
import lt.itakademija.electors.district.DistrictReport;

import java.util.List;

/**
 * Created by Pavel on 2017-01-12.
 */
@RestController
public class CandidateController {

    @Autowired
    CandidateService service;

    @PostMapping("/candidate")
    public CandidateEntity save(@RequestBody CandidateEntity candidateEntity){
        return service.save(candidateEntity);
    }

    @GetMapping("/candidate")
    public List<CandidateReport> getKandidatasList(){
        return service.getAllCandidates();
    }
    
    @GetMapping("/candidate/{id}")
    public CandidateReport getCandidateDetails(@PathVariable Long id){
        return service.getCandidateById(id);
    }

    @DeleteMapping("/candidate/{id}")
    public boolean deleteCandidateById(@PathVariable Long id) {
        return service.delete(id);
    }

    @DeleteMapping("/candidate/party/{id}")
    public boolean deleteCandidateByPartyId(@PathVariable Long id){
        return service.deleteCandidatesByPartyId(id);
    }
}
