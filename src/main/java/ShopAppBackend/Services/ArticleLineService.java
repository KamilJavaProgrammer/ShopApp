package ShopAppBackend.Services;


import ShopAppBackend.Entities.ArticleLine;
import ShopAppBackend.Entities.Product;
import ShopAppBackend.Exceptions.ArticleLineNotFoundException;
import ShopAppBackend.Logs.LogsApplication;
import ShopAppBackend.Repositories.ArticleLineRepository;
import ShopAppBackend.Repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ArticleLineService {

    private final ArticleLineRepository articleLineRepository;
    private final ProductRepo productRepo;
    private final LogsApplication logsApplication;


    @Autowired
    public ArticleLineService(ArticleLineRepository articleLineRepository,ProductRepo productRepo,LogsApplication logsApplication) {
        this.articleLineRepository = articleLineRepository;
        this.productRepo = productRepo;
        this.logsApplication = logsApplication;
    }


    @Transactional
    public HttpStatus AddOneArticleLineToDatabase(ArticleLine articleLine){

        ArticleLine articleLineInstant = new ArticleLine();
        articleLineInstant.setName(articleLine.getName());

        List<Product> products = new ArrayList<>();

        articleLine.getProductList().forEach(product -> {
           products.add(productRepo.getOne(product.getId()));
        });
        articleLineInstant.setProductList(products);
        logsApplication.SaveLogToDatabase("Create new ArticleLine");
        articleLineRepository.save(articleLine);


        return HttpStatus.CREATED;
    }

    public List<ArticleLine> GetAllArticleLineFromDatabase(){

        List<ArticleLine> articleLineList = articleLineRepository.findAll();
        return (articleLineList.isEmpty()) ? Collections.emptyList(): articleLineList;

    }

    @Transactional
    public ResponseEntity<HttpStatus> DeleteOneArticleLineById(Integer id){

        Optional<ArticleLine> articleLine = articleLineRepository.findById(id);
        articleLineRepository.delete(articleLine.orElseThrow(ArticleLineNotFoundException::new));
        logsApplication.SaveLogToDatabase("Delete ArticleLine by id =" + id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}




