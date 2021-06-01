package ShopAppBackend.Controllers;


import ShopAppBackend.Entities.ArticleLine;
import ShopAppBackend.Services.ArticleLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ArticleLineController {

    private ArticleLineService articleLineService;

    @Autowired
    public ArticleLineController(ArticleLineService articleLineService) {
        this.articleLineService = articleLineService;
    }



    @DeleteMapping("/articleLine/{id}")
    public ResponseEntity<?> DeleteOneArticleLine(@PathVariable Integer id){
        return  articleLineService.DeleteOneArticleLineById(id);
    }

    @GetMapping("/articleLine")
    public ResponseEntity<?> GetAllArticleLines(){
       return articleLineService.GetAllArticleLineFromDatabase();
    }

    @PostMapping("/articleLine")
    public ResponseEntity<?> AddOneArticleLine(@RequestBody ArticleLine articleLine ){
        return articleLineService.AddOneArticleLineToDatabase(articleLine);
    }
}
