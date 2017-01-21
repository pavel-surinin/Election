package lt.itakademija.electors.candidate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @DeleteMapping("/candidate/party/{id}")
    public boolean deleteCandidateByPartyId(@PathVariable Long id){
        return service.deleteCandidatesByPartyId(id);
    }
}
