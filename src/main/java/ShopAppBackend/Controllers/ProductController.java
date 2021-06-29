package ShopAppBackend.Controllers;
import ShopAppBackend.Entities.Product;
import ShopAppBackend.Enums.SortOption;
import ShopAppBackend.Exceptions.ProductNotFound;
import ShopAppBackend.Services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//import javax.mail.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.swing.text.html.parser.Entity;
import javax.validation.constraints.NotNull;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
@RequestMapping(value = "/products")
@Validated
public class ProductController{

    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public ProductController(ProductService productService, ObjectMapper objectMapper) {
        this.productService = productService;
        this.objectMapper = objectMapper;
    }


    @PostMapping()
    public ResponseEntity<HttpStatus> GetDataFromAngular(@RequestParam(name = "product") String json,
                                                         @RequestParam(name = "fileupload") MultipartFile file) throws JsonProcessingException {

        Product product = objectMapper.readValue(json,Product.class);
        return  ResponseEntity.ok( this.productService.AddProductToDatabase(product,file));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> PatchProduct( @PathVariable Long id,@RequestParam(name = "product") String json,
                                                    @RequestParam(name = "fileupload",required = false) MultipartFile file) throws JsonProcessingException {

        Product product = objectMapper.readValue(json,Product.class);
        return  ResponseEntity.ok(this.productService.PatchProductInDatabase(id,product,file));
    }



    @GetMapping("/all")
    public ResponseEntity<List<Product>> GetAllProducts(){
        return ResponseEntity.ok(productService.GetAllProducts());
   }

    @GetMapping("/parts")
    public ResponseEntity<List<Product>> GetAllProductsByWarehousePlace(@RequestParam(value = "place") String place){
        return ResponseEntity.ok(productService.GetAllProductsByWarehousePlace(place));
    }


    @DeleteMapping()
   public ResponseEntity<HttpStatus> DeleteMoreProducts(@RequestBody List<Product> products){
        return ResponseEntity.ok(productService.DeleteMoreProducts(products));
   }


    @GetMapping("/name/{page}")
    public ResponseEntity<Map<Integer,List<Product>>> GetProductListsa(@PathVariable Integer page,
                                                                       @RequestParam(name = "name") String name,
                                                                       @RequestParam(name = "minPrice") int minPrice,
                                                                       @RequestParam(name = "maxPrice") int maxPrice,
                                                                       @RequestParam(name = "sort", required = false) Optional<SortOption> sortOption) throws ExecutionException, InterruptedException {
        
     return ResponseEntity.ok(productService.GetMainProducts(page,name,minPrice,maxPrice,sortOption));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> GetOneProduct(@PathVariable  Long id) throws ProductNotFound {

        return ResponseEntity.ok(this.productService.GetOneProductFromDatabase(id));
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> DeleteProduct(@PathVariable @NotNull Long id) throws FileNotFoundException {
        return ResponseEntity.ok(this.productService.DeleteProductInDatabase(id));

    }





    @PostMapping("/export")
    public ResponseEntity<HttpStatus> ExportData(@RequestParam(name = "route" ) String routeExport,@RequestBody List<Product> products){
        return ResponseEntity.ok(productService.ExportData(routeExport,products));
    }
    @GetMapping("/parcel")
    public ResponseEntity<Product> GetParcelData(){
        return ResponseEntity.ok(productService.GetParcelData());
    }


    @GetMapping("/des/{id}")
    public ResponseEntity<HttpStatus> DeleteProductA(@PathVariable @NotNull Long id) throws FileNotFoundException {
        return ResponseEntity.ok(this.productService.DeleteProductInDatabase2(id));

    }

}



