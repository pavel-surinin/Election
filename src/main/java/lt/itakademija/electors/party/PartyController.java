package lt.itakademija.electors.party;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.storage.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by Pavel on 2017-01-12.
 */
@RestController
public class PartyController {

    @Autowired
    PartyService service;

    @PostMapping("/party")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestHeader("Party-name") String partyName,
                                   @RequestHeader("Party-number") Integer partyNumber,
                                   RedirectAttributes redirectAttributes) {
        service.save(partyName, partyNumber, file);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/party")
    public List<PartyReport> getPartijaList(){
        return service.getPartyList();
    }
 
    @GetMapping("/party/{id}")
    public PartyReport getPartyById(@PathVariable Long id){
    	return service.getPartyById(id);
    }
    @PutMapping("/party")
    public PartyEntity save(@RequestBody PartyEntity partyEntity){
      return  service.save(partyEntity); 
    }
}

