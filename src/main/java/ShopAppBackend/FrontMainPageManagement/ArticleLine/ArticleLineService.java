package ShopAppBackend.FrontMainPageManagement.ArticleLine;


import ShopAppBackend.Product.Product;
import ShopAppBackend.Product.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public ResponseEntity<HttpStatus> AddOneArticleLineToDatabase(){

        ArticleLine articleLine = new ArticleLine();
        articleLine.setName("Upierdolone");

        List<Product> products = new ArrayList<>();
//        products.add(productRepo.getOne(2L));
        products.add(productRepo.getOne(3L));
//        products.add(productRepo.getOne(4L));
        articleLine.setProductList(products);
        articleLineRepository.save(articleLine);

//        List<ArticleLine> articleLine = articleLineRepository.findAll();
//        articleLine.forEach(articleLine1 -> {
//            articleLine1.setProductList(Collections.emptyList());
//        });


        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<List<ArticleLine>> GetAllArticleLineFromDatabase(){

        List<ArticleLine> articleLineList = articleLineRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(articleLineList);
    }
}




