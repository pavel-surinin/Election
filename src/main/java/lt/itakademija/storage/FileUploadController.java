package lt.itakademija.storage;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.candidate.CandidateService;
import lt.itakademija.electors.party.PartyEntity;
import lt.itakademija.electors.party.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    CandidateService candidateService;

    @Autowired
    PartyService partyService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload/candidates")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestHeader("Party-name") String partyName,
                                   @RequestHeader("Party-number") Integer partyNumber,
                                   RedirectAttributes redirectAttributes) {
        List<CandidateEntity> candidatesFromCsv = storageService.store("Party", file);
        PartyEntity party = new PartyEntity();
        party.setName(partyName);
        party.setPartyNumber(partyNumber);
        PartyEntity createPartyRespone = partyService.save(party);
        candidatesFromCsv.stream()
                .map(can -> {
                    can.setPartyDependencies(createPartyRespone);
                    return can;
                }).forEach(can -> candidateService.save(can));

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
