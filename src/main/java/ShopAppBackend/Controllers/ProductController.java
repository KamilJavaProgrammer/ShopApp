package ShopAppBackend.Controllers;
import ShopAppBackend.Entities.Product;
import ShopAppBackend.Exceptions.ProductNotFound;
import ShopAppBackend.Services.ProductService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/products")
public class ProductController{

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
        public ResponseEntity<HttpStatus> GetDataFromAngular(@RequestParam(name = "productCategory") String productCategory,
                                                    @RequestParam(name = "productSubCategory",required = false) String productSubCategory,
                                                    @RequestParam(name = "productName") String productName,
                                                    @RequestParam(name = "manufacturer",required = false)  String manufacturer,
                                                    @RequestParam(name = "serialNumber",required = false) String serialNumber,
                                                    @RequestParam(name = "model",required = false) String model,
                                                    @RequestParam(name = "productPrice") String productPrice,
                                                    @RequestParam(name = "numberOfItems") Integer numberOfItems,
                                                    @RequestParam(name = "location") String location,
                                                    @RequestParam(name = "cod") String cod,
                                                    @RequestParam(name = "status") String status,
                                                    @RequestParam(name = "description") String description,
                                                    @RequestParam(name = "placeWarehouse") String placeWarehouse,
                                                    @RequestParam(value = "fileupload" ,required = false) MultipartFile file  ){



        return  ResponseEntity.ok( this.productService.AddProductToDatabase(productCategory,productSubCategory,productName,manufacturer,serialNumber,model,
                productPrice,numberOfItems,location,cod,status,description,placeWarehouse,file));
    }







    @GetMapping("/all")
    public ResponseEntity<?> GetAllProducts(){

        return ResponseEntity.ok(productService.GetAllProducts());
   }

    @GetMapping("/parts")
    public ResponseEntity<?> GetAllProductsByWarehousePlace(@RequestParam(value = "place") String place){
        return ResponseEntity.ok(productService.GetAllProductsByWarehousePlace(place));
    }


    @DeleteMapping()
   public ResponseEntity<?> DeleteMoreProducts(@RequestBody List<Product> products){
        return ResponseEntity.ok(productService.DeleteMoreProducts(products));
   }


    @GetMapping("/name/{page}")
    public ResponseEntity<List<Product>> GetProductListsa(@PathVariable Integer page,
                                                          @RequestParam(name = "name") String name,
                                                          @RequestParam(name = "minPrice") int minPrice,
                                                          @RequestParam(name = "maxPrice") int maxPrice){


     return ResponseEntity.ok(productService.GetMainProducts(page,name,minPrice,maxPrice));

    }





//   @PostMapping("/name")
//    public ResponseEntity<?> GetProductList(@RequestPart(name = "path") String path){
//
//        return ResponseEntity.ok(productService.GetMainProducts(path.toLowerCase()));
//
//      }















    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> GetOneProduct(@PathVariable Long id) throws ProductNotFound {
        return ResponseEntity.ok(this.productService.GetOneProductFromDatabase(id));
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> DeleteProduct(@PathVariable Long id) throws FileNotFoundException {
        return ResponseEntity.ok(this.productService.DeleteProductInDatabase(id));

    }
//    @PutMapping("/products/{id}")
//    public ResponseEntity<HttpStatus> PutProduct(@PathVariable Long id, @Valid @RequestBody Product product){
//        this.productService.PutProductInDatabase(id,product);
//        return ResponseEntity.ok(HttpStatus.OK);
//    }

    @SneakyThrows
    @PatchMapping ("/products/{id}")
    public ResponseEntity<?> PatchProduct(@PathVariable Long id, @RequestParam(name = "productCategory") String productCategory,
                                          @RequestParam(name = "productSubCategory",required = false) String productSubCategory,
                                          @RequestParam(name = "productName") String productName,
                                          @RequestParam(name = "manufacturer",required = false)  String manufacturer,
                                          @RequestParam(name = "serialNumber",required = false) String serialNumber,
                                          @RequestParam(name = "model",required = false) String model,
                                          @RequestParam(name = "productPrice") String productPrice,
                                          @RequestParam(name = "numberOfItems") Integer numberOfItems,
                                          @RequestParam(name = "location") String location,
                                          @RequestParam(name = "cod") String cod,
                                          @RequestParam(name = "status") String status,
                                          @RequestParam(name = "description") String description,
                                          @RequestParam(name = "placeWarehouse") String placeWarehouse,
                                          @RequestParam(value = "fileupload") MultipartFile file ){

        Product product = new Product();
        product.setId(id);
        product.setProductCategory(productCategory);
        product.setProductSubCategory(productSubCategory);
        product.setProductName(productName);
        product.setManufacturer(manufacturer);
        product.setModel(model);
        product.setProductPrice(productPrice);
        product.setNumberOfItems(numberOfItems);
        product.setLocation(location);
        product.setCod(cod);
        product.setStatus(status);
        product.setDescription(description);
        product.setWareHouseplace(description);


       return  ResponseEntity.ok( this.productService.PatchProductInDatabase(id,product,file));
    }


    @PostMapping("/export")
    public ResponseEntity<?> ExportData(@RequestParam(name = "route" ) String routeExport,@RequestBody List<Product> products){
        return ResponseEntity.ok(productService.ExportData(routeExport,products));
    }
    @GetMapping("/parcel")
    public ResponseEntity<?> GetParcelData(){
        return ResponseEntity.ok(productService.GetParcelData());
    }


    @GetMapping("/search")
    public ResponseEntity<?> GetSearchingProducts(@RequestParam(value = "searchText") String searchText){
        return ResponseEntity.ok(productService.GetAllBySearch(searchText));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        System.out.println(errors);
        return errors;
    }





}



