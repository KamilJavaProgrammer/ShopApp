package ShopAppBackend.Product;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ShopAppBackend.MongoDB.ItemsCount;
import ShopAppBackend.MongoDB.MongoDbRepo;
import ShopAppBackend.Product.Category.Category;
import ShopAppBackend.Product.Category.CategoryRepository;
import ShopAppBackend.Product.SubCategory.SubCategory;
import ShopAppBackend.Product.SubCategory.SubCategoryRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService extends Thread {
    private Logger logger = LoggerFactory.getLogger(ProductService.class);

    private ProductRepo productRepo;
    private CategoryRepository categoryRepository;
    private final Path rootLocation = Paths.get("C://ZdjęciaBaza/");
    private List<ProductDTO> productDTOs;
    public ModelMapper modelMapper;
    private SubCategoryRepository subCategoryRepository;
    private MongoDbRepo mongoDbRepo;
    private JavaMailSender javaMailSender;



    @Autowired
    public ProductService(ProductRepo productRepo, ModelMapper modelMapper, SubCategoryRepository subCategoryRepository, MongoDbRepo mongoDbRepo, JavaMailSender javaMailSender, CategoryRepository categoryRepository) {
       this.productRepo = productRepo;
       this.modelMapper = modelMapper;
       this.subCategoryRepository = subCategoryRepository;
       this.mongoDbRepo = mongoDbRepo;
       this.javaMailSender = javaMailSender;
       this.categoryRepository = categoryRepository;
   }

    @Transactional
    public HttpStatus AddProductToDatabase (String productCategory, String productSubCategory, String productName, String manufacturer, String serialNumber,
                                            String model, String productPrice, Integer numberOfItems, String location,
                                            String cod, String status, String description,String placeWarehouse,  MultipartFile file) {


        if (!productRepo.existsByProductName(productName)) {

            try {
                if (file != null) {
                    File oFile = new File("C:/ZdjęciaBaza/Upload/" + file.getOriginalFilename());
                    OutputStream os = new FileOutputStream(oFile);
                    InputStream inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, os);
                    os.close();
                    inputStream.close();
                } else {
                    System.out.println("file is null");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {


                if (!categoryRepository.existsByName(productCategory)) {
                    Category category = new Category();
                    category.setName(productCategory.toLowerCase());
                    this.categoryRepository.save(category);

                    this.Test(category, productCategory, productSubCategory, productName, manufacturer, serialNumber,
                            model, productPrice, numberOfItems, location,
                            cod, status, description, placeWarehouse, file);
                } else {
                    Category category = this.categoryRepository.findByName(productCategory);

                    this.Test(category, productCategory, productSubCategory, productName, manufacturer, serialNumber,
                            model, productPrice, numberOfItems, location,
                            cod, status, description, placeWarehouse, file);

                }
            }


            logger.info("Add product to database");
            return HttpStatus.OK;
        }
        else {
            logger.warn("Product exists");
            return HttpStatus.NOT_ACCEPTABLE;
        }

    }


    public void Test (Category category1, String productCategory, String productSubCategory, String productName, String manufacturer, String serialNumber,
                      String model, String productPrice, Integer numberOfItems, String location,
                      String cod, String status, String description,String placeWarehouse, MultipartFile file){



        if(!subCategoryRepository.existsByName(productSubCategory)) {

            SubCategory subCategory = new SubCategory();
            subCategory.setName(productSubCategory.toLowerCase());
            this.subCategoryRepository.save(subCategory);

            List<SubCategory> categories  =  category1.getSubCategories();

            if(categories != null) {
                categories.add(subCategory);
                category1.setSubCategories(categories);


            }
            else
            {
                List<SubCategory> categories1 = new ArrayList<>();

                categories1.add(subCategory);
                category1.setSubCategories(categories1);
            }

            categoryRepository.save(category1);
            SaveAPartialProduct(productCategory, productSubCategory, productName, manufacturer, serialNumber,
                    model, productPrice, numberOfItems, location,
                    cod, status, description,placeWarehouse, file, subCategory, category1);
        }

        else
        {

            SubCategory subCategory2 = this.subCategoryRepository.findByName(productSubCategory);

            SaveAPartialProduct(productCategory, productSubCategory, productName, manufacturer, serialNumber,
                    model, productPrice, numberOfItems, location,
                    cod, status, description,placeWarehouse, file, subCategory2, category1);

        }

    }

    public void SaveAPartialProduct(String productCategory, String productSubCategory, String productName, String manufacturer, String serialNumber,
                                    String model, String productPrice, Integer numberOfItems, String location,
                                    String cod, String status, String description,String placeWarehouse, MultipartFile file, SubCategory subCategory, Category category){

        Product product = new Product();
        if (file == null) {
            product.setPathToFile("Nie określono");
        } else {
            product.setPathToFile("C:/ZdjęciaBaza/Upload/" + file.getOriginalFilename());
        }
        product.setDate(LocalDate.now().toString());
        product.setProductSubCategory(productSubCategory);
        product.setProductCategory(productCategory);
        product.setProductName(productName);
        product.setManufacturer(manufacturer);
        product.setSerialNumber(serialNumber);
        product.setModel(model);
        product.setProductPrice(productPrice);
        product.setNumberOfItems(numberOfItems);
        product.setLocation(location);
        product.setCod(cod);
        product.setStatus(status);
        product.setDescription(description);
        product.setWareHouseplace(placeWarehouse.toLowerCase());
        product.setSubCategory(subCategory);
        product.setCategory1(category);
        productRepo.save(product);

    }









    public Optional<Product> GetOneProductFromDatabase(Long id) throws ProductNotFound {


        Optional<Product> product = productRepo.findById(id);
        if(product.isPresent())

        return product;

        else
        {
            throw  new ProductNotFound("Product Not Found");
        }
    }


    @Transactional
    public List<List<Product>> GetAllProductFromDatabase(String response, String idOne, String idTwo ) throws ExecutionException, InterruptedException {


        List<List<Product>> toReturn = new ArrayList();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<List<Product>> productEntity = executorService.submit(() -> GetFirstList(idOne));
        Future<List<Product>> productEntity1 = executorService.submit(() -> GetSecondList(response,idTwo));

        executorService.shutdown();

        toReturn.add(productEntity.get());
        toReturn.add(productEntity1.get());

        return toReturn;

    }


    public List<Product> GetFirstList(String idOne){

        List<Product> productEntities = new ArrayList<>();
        Optional<ItemsCount> items =  mongoDbRepo.findById(idOne);


        if(items.isPresent())
           {
               if(items.get().getNumber() < 8 )
                   {
                       productEntities.addAll(productRepo.GetRecentItems(16));
                   }
                else
                   {
                       productEntities.addAll(productRepo.GetRecentItems(items.get().getNumber()));
                   }
           }
           else
           {

               productEntities.addAll(productRepo.GetRecentItems(16));
           }


           return productEntities;

   }


    public List<Product> GetSecondList(String response, String idTwo){


       List<Product> productEntities1 = new ArrayList<>();

        if(response.equals("Domyślny"))
       {
           productEntities1.addAll(productRepo.GetFirstItems(12));
       }

        else if(response.equals("Indywidualny"))
        {
            Optional<ItemsCount> item =  mongoDbRepo.findById(idTwo);

            if(item.isPresent())
            {
                List<Integer> integers = item.get().getAmountOfElements();

                integers.forEach(integer -> {
                    if(productRepo.existsById(integer.longValue()))
                    {
                        productEntities1.add(productRepo.getOne(integer.longValue()));
                    }

                    else
                    {
                        logger.warn("Unable find this id");
                    }
                });

            }
            else
            {
                productEntities1.addAll(productRepo.GetFirstItems(12));
            }
        }
        return productEntities1;

    }






    @Transactional
    public HttpStatus DeleteProductInDatabase(Long id) throws FileNotFoundException {
        Product product = productRepo.getOne(id);
        File file = new File(product.getPathToFile());
        System.out.println(product.getPathToFile());
        file.delete();

       productRepo.deleteById(id);
       return HttpStatus.NO_CONTENT;
    }




    public HttpStatus PatchProductInDatabase(Long id, Product product,MultipartFile file) throws ProductNotFound {

        Optional<Product> productInstant  = productRepo.findById(id);
        if(productInstant.isEmpty()){
            throw  new ProductNotFound(String.format("Product with ID  not exists!",id));
        }
        else
        {
            this.PartialUpdateProduct(productInstant.get(),product,file);
            return HttpStatus.OK;
        }
    }

    @SneakyThrows
    public void PartialUpdateProduct(Product productInstant,Product product,MultipartFile file){

        if(product.getId()!=null){

            File file1 = new File(productInstant.getPathToFile());
            file1.delete();

            File oFile = new File("C:/ZdjęciaBaza/Upload/" + file.getOriginalFilename());
            OutputStream os = new FileOutputStream(oFile);
            InputStream inputStream = file.getInputStream();
            IOUtils.copy(inputStream, os);
            os.close();
            inputStream.close();

        }

        else
        {
            System.out.println("File is null");

        }

        if(product.getId()!=null)
            productInstant.setId(product.getId());
        else
        {
            System.out.println("Error");

        }


        if(product.getProductName()!=null)
            productInstant.setProductName(product.getProductName());
        else
        {
            System.out.println("Error");
        }

        if(product.getCod()!=null)
            productInstant.setCod(product.getCod());
        else
        {
            System.out.println("Error");
        }


        if(product.getNumberOfItems()>-1)
            productInstant.setNumberOfItems(product.getNumberOfItems());
        else
        {
            System.out.println("Error");
        }
        if(product.getLocation()!=null)
            productInstant.setLocation(product.getLocation());
        else
        {
            System.out.println("Error");
        }
        if(product.getStatus()!=null)
            productInstant.setStatus(product.getStatus());
        else
        {
            System.out.println("Error");
        }

        this.productRepo.save(productInstant);


    }

    public Long getProductCount(){
       return this.productRepo.count();
    }




    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }





    public ResponseEntity<List<Product>>  GetMainProducts(String path) {

        if(path != null) {

            String[] data = path.split("/");

            if (data.length == 1) {
                return ResponseEntity.status(HttpStatus.OK).body(this.productRepo.GetMainItems(data[0]));
                }

            else if (data.length == 2) {


                   return ResponseEntity.status(HttpStatus.OK).body( productRepo.GetMainItems(data[0]).stream().
                           filter(product -> product.getProductSubCategory().toLowerCase().equals(data[1].toLowerCase())).collect(Collectors.toList()));
                }

            else {

                return ResponseEntity.status(HttpStatus.OK).body(productRepo.GetMainItems(data[0]).stream().
                        filter(product -> product.getProductSubCategory().toLowerCase().equals(data[1].toLowerCase())).
                        filter(product -> product.getManufacturer().toLowerCase().equals(data[2].toLowerCase())).collect(Collectors.toList()));

                 }
             }


        else {
             throw new IllegalStateException();
        }
    }





      public ResponseEntity<List<Product>> GetAllBySearch(String searchText){

     List<Product> productEntities = productRepo.findAllByManufacturerContainingOrProductNameContaining(searchText,searchText);
     productEntities.sort(Product::compareTo);
      return ResponseEntity.status(HttpStatus.OK).body(productEntities);
   }




//  public List<ProductDTO> getallbysearch(String name){
//
//      this.productDTOs = new ArrayList<>();
//
//
//      List<Product> productEntities = productRepo.findAllByManufacturerContainingOrProductNameContaining(name,name);
//
//      productEntities.forEach(productEntity -> {
//          productDTOs.add(modelMapper.map(productEntity,ProductDTO.class));
//      });
//      System.out.println("start");
//
//      return productDTOs;
//   }






  public ResponseEntity<?> GetAllProducts(){

      List<Product> products = productRepo.findAll();
      Collections.sort(products);

      return ResponseEntity.status(HttpStatus.OK).body(products);
  }

    public ResponseEntity<?> GetAllProductsByWarehousePlace(String wareHousePlace){

        List<Product> products = productRepo.GetProductsByWarehousePlace(wareHousePlace.toLowerCase());
        Collections.sort(products);

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }





    public ResponseEntity<HttpStatus> DeleteMoreProducts(List<Product> products){

         products.forEach(product -> {

             File file = new File(product.getPathToFile());
             file.delete();
             productRepo.deleteById(product.getId());
         });

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }




    public ResponseEntity<HttpStatus> ExportData(String routeExport,List<Product> data) {

        if(routeExport.equals("SHOP")){
            data.forEach(product -> {
                Product productInstant = productRepo.getOne(product.getId());
                productInstant.setWareHouseplace("Sklep");
                productRepo.save(productInstant);
            });


        }
        else
        {
            data.forEach(product -> {
                Product productInstant = productRepo.getOne(product.getId());
                productInstant.setWareHouseplace("Serwis");
                productRepo.save(productInstant);
            });
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<?> GetParcelData() {

       Optional<Product> product =  productRepo.findByProductName("Przesyłka kurierska");

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
}





