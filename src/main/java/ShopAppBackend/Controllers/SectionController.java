package ShopAppBackend.Controllers;


import ShopAppBackend.Entities.Section;
import ShopAppBackend.Services.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/sections")
public class SectionController {


    private final SectionService sectionService;


    @Autowired
    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping("")
    public ResponseEntity<?> GetAllSections(){
        return ResponseEntity.ok(sectionService.GetAllSectionsFromDatabase());
    }

    @PostMapping("")
    public ResponseEntity<?> AddOneSection(@RequestBody Section section){

        return ResponseEntity.ok(sectionService.AddOneSectionToDatabase(section));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteSectionById(@PathVariable Integer id){

        return ResponseEntity.ok(sectionService.DeleteOneSectionById(id));
    }

}
