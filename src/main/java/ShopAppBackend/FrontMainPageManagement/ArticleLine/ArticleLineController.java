package ShopAppBackend.FrontMainPageManagement.ArticleLine;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ArticleLineController {

    private ArticleLineService articleLineService;

    @Autowired
    public ArticleLineController(ArticleLineService articleLineService) {
        this.articleLineService = articleLineService;
    }

    @GetMapping("/articleLines")
    public ResponseEntity<?> test(){
      return   articleLineService.AddOneArticleLineToDatabase();
    }

    @GetMapping("/articleLine")
    public ResponseEntity<?> GetAllArticleLines(){
       return articleLineService.GetAllArticleLineFromDatabase();
    }
}
