package lt.itakademija.electors.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        return service.getKandidatasList();
    }

}
