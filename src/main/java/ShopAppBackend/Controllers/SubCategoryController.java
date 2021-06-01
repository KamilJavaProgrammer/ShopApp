package ShopAppBackend.Controllers;

import ShopAppBackend.Services.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SubCategoryController {

    private SubCategoryService subCategoryService;

    @Autowired
    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }


    @PostMapping("/subcategory")
    public ResponseEntity<Map<String,Long>> ReturnPartlyCategory(@RequestPart(name = "path") String path){

        return ResponseEntity.ok(this.subCategoryService.GetCategories(path.toLowerCase()));
    }

    @PostMapping("/subcategory/manufacturers")
    public ResponseEntity<Map<String,Long>> ReturnPartlyCategory1(@RequestPart(name = "path") String path,
                                                                  @RequestPart(name = "subCategoryName") String subCategoryName){


        return ResponseEntity.ok(this.subCategoryService.GetManufacturers(path.toLowerCase(),subCategoryName.toLowerCase()));
    }

    @GetMapping("/subcategory")
    public ResponseEntity<List<String>> ReturnAllSubCategories(){
        return ResponseEntity.ok(subCategoryService.GetAllSubCategories());
    }
}
