package ShopAppBackend.Controllers;

import ShopAppBackend.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
public class CategoryController{

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<Map<Map<String,Integer>, Map<String,Integer>>> GetAllCategories(){
        return ResponseEntity.ok(categoryService.GetAllCategories());
    }
}
