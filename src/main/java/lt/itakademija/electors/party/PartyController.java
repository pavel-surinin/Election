package lt.itakademija.electors.party;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
=======
import org.springframework.web.bind.annotation.*;
>>>>>>> 3e06cced98022a8b331b3cea0cf9691b7f667ddf

import java.util.List;

/**
 * Created by Pavel on 2017-01-12.
 */
@RestController
public class PartyController {

    @Autowired
    PartyService service;

    @PostMapping("/party")
    public PartyEntity save(@RequestBody PartyEntity partija){
        return service.save(partija);
    }

    @GetMapping("/party")
    public List<PartyReport> getPartijaList(){
        return service.getPartyList();
    }
<<<<<<< HEAD
    
    
    @GetMapping("/party/{id}")
    public PartyReport getPartyById(@PathVariable Long id){
    	return service.getPartyById(id);
=======

    @GetMapping("/party/{id}")
    public PartyReport getPartyById(@PathVariable Long id){
        return service.getPartyBiId(id);
>>>>>>> 3e06cced98022a8b331b3cea0cf9691b7f667ddf
    }

}
