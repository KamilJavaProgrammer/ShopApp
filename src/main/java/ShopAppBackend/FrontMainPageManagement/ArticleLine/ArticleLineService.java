package ShopAppBackend.FrontMainPageManagement.ArticleLine;


import ShopAppBackend.Product.Product;
import ShopAppBackend.Product.ProductRepo;
import com.sun.mail.iap.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleLineService {

    private final ArticleLineRepository articleLineRepository;
    private final ProductRepo productRepo;

    @Autowired
    public ArticleLineService(ArticleLineRepository articleLineRepository,ProductRepo productRepo) {
        this.articleLineRepository = articleLineRepository;
        this.productRepo = productRepo;
    }


    @Transactional
    public ResponseEntity<HttpStatus> AddOneArticleLineToDatabase(ArticleLine articleLine){

        ArticleLine articleLineInstant = new ArticleLine();
        articleLineInstant.setName(articleLine.getName());

        List<Product> products = new ArrayList<>();

        articleLine.getProductList().forEach(product -> {
           products.add(productRepo.getOne(product.getId()));
        });
        articleLineInstant.setProductList(products);
        articleLineRepository.save(articleLine);


        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<List<ArticleLine>> GetAllArticleLineFromDatabase(){

        List<ArticleLine> articleLineList = articleLineRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(articleLineList);
    }

    @Transactional
    public ResponseEntity<HttpStatus> DeleteOneArticleLineById(Integer id){

        articleLineRepository.deleteById(id);

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}




