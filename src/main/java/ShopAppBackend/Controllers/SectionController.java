package ShopAppBackend.Controllers;


import ShopAppBackend.Entities.Section;
import ShopAppBackend.Services.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/sections")
@Validated
public class SectionController {


    private final SectionService sectionService;


    @Autowired
    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping("")
    public ResponseEntity<List<Section>> GetAllSections(){
        return ResponseEntity.ok(sectionService.GetAllSectionsFromDatabase());
    }

    @PostMapping("")
    public ResponseEntity<HttpStatus> AddOneSection(@RequestBody Section section){

        return ResponseEntity.ok(sectionService.AddOneSectionToDatabase(section));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> DeleteSectionById(@PathVariable @NotNull Integer id){

        return ResponseEntity.ok(sectionService.DeleteOneSectionById(id));
    }

}
