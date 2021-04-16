package ShopAppBackend.FrontMainPageManagement.Section;


import ShopAppBackend.FrontMainPageManagement.Section.SectionCategory.SectionCategory;
import ShopAppBackend.FrontMainPageManagement.Section.SectionCategory.SectionCategoryRepository;
import ShopAppBackend.FrontMainPageManagement.Section.SectionSubCategory.SectionSubCategory;
import ShopAppBackend.FrontMainPageManagement.Section.SectionSubCategory.SectionSubCategoryRepository;
import com.mysql.cj.xdevapi.Collection;
import liquibase.pro.packaged.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SectionController {


    private final SectionService sectionService;


    @Autowired
    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping("/section")
    public ResponseEntity<?> GetAllSections(){
        return ResponseEntity.ok(sectionService.GetAllSectionsFromDatabase());
    }

    @PostMapping("/section")
    public ResponseEntity<?> AddOneSection(@RequestBody Section section){

        return ResponseEntity.ok(sectionService.AddOneSectionToDatabase(section));
    }


}
