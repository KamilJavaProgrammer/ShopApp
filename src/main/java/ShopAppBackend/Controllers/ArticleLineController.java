package ShopAppBackend.Controllers;


import ShopAppBackend.Entities.ArticleLine;
import ShopAppBackend.Services.ArticleLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class ArticleLineController{

    private final ArticleLineService articleLineService;

    @Autowired
    public ArticleLineController(ArticleLineService articleLineService) {
        this.articleLineService = articleLineService;
    }


    @DeleteMapping("/articleLine/{id}")
    public ResponseEntity<HttpStatus> DeleteOneArticleLine(@PathVariable @NotNull Integer id){
        return  articleLineService.DeleteOneArticleLineById(id);
    }

    @GetMapping("/articleLine")
    public ResponseEntity<List<ArticleLine>> GetAllArticleLines(){
       return ResponseEntity.ok(articleLineService.GetAllArticleLineFromDatabase());
    }

    @PostMapping("/articleLine")
    public ResponseEntity<HttpStatus> AddOneArticleLine(@RequestBody @Valid ArticleLine articleLine ){
        return ResponseEntity.ok(articleLineService.AddOneArticleLineToDatabase(articleLine));
    }
}
