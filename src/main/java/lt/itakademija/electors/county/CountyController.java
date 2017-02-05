package lt.itakademija.electors.county;

import lt.itakademija.storage.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by Pavel on 2017-01-14.
 */
@RestController
public class CountyController {

    @Autowired
    CountyService service;

    @GetMapping("/county")
    public List getCountyList(){
        return service.getAll();
    }

    @PostMapping("/county")
    public CountyEntity save(@RequestBody CountyEntity county){
        return service.save(county);
    }

    @PutMapping("/county")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestHeader("County-id") Long countyId,
                                   RedirectAttributes redirectAttributes) {
        service.update(countyId, file);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/county/{id}")
    public CountyDetailsReport getCountyDetals(@PathVariable Long id){
        return service.getCountyById(id);
    }

    @DeleteMapping("/county/{id}")
    public boolean deletecounty(@PathVariable Long id){
        return service.delete(id);
    }
    
}
