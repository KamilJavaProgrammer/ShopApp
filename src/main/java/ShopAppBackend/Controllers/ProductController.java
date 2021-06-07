package ShopAppBackend.Controllers;
import ShopAppBackend.Entities.Product;
import ShopAppBackend.Exceptions.ProductNotFound;
import ShopAppBackend.Services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.FileNotFoundException;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/products")
@Validated
public class ProductController{

    private final ProductService productService;
    private final ObjectMapper objectMapper;

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

    @GetMapping("/all")
    public ResponseEntity<List<Product>> GetAllProducts(){
        return ResponseEntity.ok(productService.GetAllProducts());
   }

    @GetMapping("/parts")
    public ResponseEntity<List<Product> > GetAllProductsByWarehousePlace(@RequestParam(value = "place") String place){
        return ResponseEntity.ok(productService.GetAllProductsByWarehousePlace(place));
    }


    @DeleteMapping()
   public ResponseEntity<HttpStatus> DeleteMoreProducts(@RequestBody List<Product> products){
        return ResponseEntity.ok(productService.DeleteMoreProducts(products));
   }


    @GetMapping("/name/{page}")
    public ResponseEntity<List<Product>> GetProductListsa(@PathVariable Integer page,
                                                          @RequestParam(name = "name") String name,
                                                          @RequestParam(name = "minPrice") int minPrice,
                                                          @RequestParam(name = "maxPrice") int maxPrice){


     return ResponseEntity.ok(productService.GetMainProducts(page,name,minPrice,maxPrice));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> GetOneProduct(@PathVariable @NotNull Long id) throws ProductNotFound {
        return ResponseEntity.ok(this.productService.GetOneProductFromDatabase(id));
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> DeleteProduct(@PathVariable @NotNull Long id) throws FileNotFoundException {
        return ResponseEntity.ok(this.productService.DeleteProductInDatabase(id));

    }

    @SneakyThrows
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> PatchProduct( @PathVariable Long id,@RequestParam(name = "product") String json,
                                                    @RequestParam(name = "fileupload") MultipartFile file) throws JsonProcessingException {

        Product product = objectMapper.readValue(json,Product.class);
        return  ResponseEntity.ok( this.productService.PatchProductInDatabase(id,product,file));
    }



    @PostMapping("/export")
    public ResponseEntity<HttpStatus> ExportData(@RequestParam(name = "route" ) String routeExport,@RequestBody List<Product> products){
        return ResponseEntity.ok(productService.ExportData(routeExport,products));
    }
    @GetMapping("/parcel")
    public ResponseEntity<Product> GetParcelData(){
        return ResponseEntity.ok(productService.GetParcelData());
    }

}



