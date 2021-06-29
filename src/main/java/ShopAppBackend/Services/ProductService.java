package ShopAppBackend.Services;
import ShopAppBackend.Entities.Product;
import ShopAppBackend.Enums.SortOption;
import ShopAppBackend.Exceptions.ProductNotFound;
import ShopAppBackend.Repositories.ProductRepo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ShopAppBackend.Entities.Category;
import ShopAppBackend.Repositories.CategoryRepository;
import ShopAppBackend.Entities.SubCategory;
import ShopAppBackend.Repositories.SubCategoryRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class ProductService extends Thread {

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepo productRepo;
    private final CategoryRepository categoryRepository;
    private final static String PATH = "C:/ZdjęciaBaza/Upload/";
    private final SubCategoryRepository subCategoryRepository;
    public final String apiKey;
    public ModelMapper modelMapper;
    public final StorageService storageService;

    @PersistenceContext
    EntityManager entityManager;


    @Autowired
    public ProductService(@Value("${removebg.apikey}") String apiKey, ProductRepo productRepo, ModelMapper modelMapper,
                          SubCategoryRepository subCategoryRepository, CategoryRepository categoryRepository, StorageService storageService) {
        this.productRepo = productRepo;
        this.modelMapper = modelMapper;
        this.subCategoryRepository = subCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.apiKey = apiKey;
        this.storageService = storageService;
    }


    @Transactional
    public HttpStatus AddProductToDatabase (Product product,MultipartFile file) {


        if (!productRepo.existsByProductName(product.getProductName())) {

            try {
                if (file != null) {
                    String filename = file.getOriginalFilename();

                    product.setPathToFile(filename.replaceAll(" ",""));
                    this.storageService.uploadFile(file);

//                    File oFile = new File(file.getOriginalFilename());
//                    OutputStream os = new FileOutputStream(oFile);
//                    InputStream inputStream = file.getInputStream();
//                    IOUtils.copy(inputStream, os);
//                    os.close();
//                    inputStream.close();
//                    product.setPathToFile(file.getOriginalFilename());

                }
                else {
                    product.setPathToFile("Nie określono");
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            } finally {

                SaveProductCategory(product,product.getProductCategory());
                SaveProductSubCategory(product,product.getProductSubCategory());
                product.setDate(LocalDate.now().toString());
                productRepo.save(product);
            }

            logger.info("Add product to database");
            return HttpStatus.OK;
        }
        else {
            logger.warn("Product exists");
            return HttpStatus.NOT_ACCEPTABLE;
        }

    }



    public void SaveProductCategory(Product product,String productSubCategory){

        if (!categoryRepository.existsByName(productSubCategory)) {
            Category category = new Category();
            category.setName(productSubCategory.toLowerCase());
            this.categoryRepository.save(category);

            product.setCategory1(category);

        }
        else {
            Category category = this.categoryRepository.findByName(productSubCategory);
            product.setCategory1(category);
        }

    }



    public void SaveProductSubCategory(Product product, String productSubCategory){

        if(!subCategoryRepository.existsByName(productSubCategory)) {

            SubCategory subCategory = new SubCategory();
            subCategory.setName(productSubCategory.toLowerCase());
            this.subCategoryRepository.save(subCategory);

            List<SubCategory> categories  =  product.getCategory1().getSubCategories();

            if(categories != null) {
                categories.add(subCategory);
                product.getCategory1().setSubCategories(categories);
            }
            else
            {
                List<SubCategory> categories1 = new ArrayList<>();

                categories1.add(subCategory);
                product.getCategory1().setSubCategories(categories1);
            }
        }

        else
        {

            SubCategory subCategory2 = this.subCategoryRepository.findByName(productSubCategory);
            product.setSubCategory(subCategory2);

        }

    }



    public Product GetOneProductFromDatabase(Long id) throws ProductNotFound {


        Optional<Product> product = productRepo.findById(id);
        return product.orElseThrow(ProductNotFound::new);
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


    @Transactional
    public HttpStatus DeleteProductInDatabase2(Long id) throws FileNotFoundException {
        productRepo.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }


    @Transactional
    public HttpStatus PatchProductInDatabase(Long id, Product product,MultipartFile file) throws ProductNotFound {

        Optional<Product> productInstant  = productRepo.findById(id);
        if(!productInstant.isPresent()){
            throw  new ProductNotFound();
        }
        else
        {
           return this.PartialUpdateProduct(productInstant.get(),product,file);

        }
    }

    @SneakyThrows
    public HttpStatus PartialUpdateProduct(Product productInstant,Product product,MultipartFile file){

            if(file != null)
            {
                File file1 = new File(productInstant.getPathToFile());
                file1.delete();

                product.setPathToFile(Objects.requireNonNull(file.getOriginalFilename()).replaceAll(" ",""));
//                File oFile = new File("C:/ZdjęciaBaza/Upload/" + file.getOriginalFilename());
//                OutputStream os = new FileOutputStream(oFile);
//                InputStream inputStream = file.getInputStream();
//                IOUtils.copy(inputStream, os);
//                os.close();
//                inputStream.close();
                this.storageService.uploadFile(file);

            }

            productInstant.setProductName(product.getProductName());
            productInstant.setDescription(product.getDescription());
            productInstant.setProductPrice(product.getProductPrice());
            productInstant.setCod(product.getCod());
            productInstant.setNumberOfItems(product.getNumberOfItems());
            productInstant.setLocation(product.getLocation());
            productInstant.setStatus(product.getStatus());
            productInstant.setProductCategory(product.getProductCategory());
            productInstant.setProductSubCategory(product.getProductSubCategory());



        if(!productInstant.getProductCategory().equals(product.getProductCategory()))
        {
            String category = product.getProductCategory();
            SaveProductCategory(productInstant,category);
        }


        if(!productInstant.getProductSubCategory().equals(product.getProductSubCategory()))
            {
                String subCategory = product.getProductSubCategory();

                SaveProductSubCategory(productInstant,subCategory);
            }

        this.productRepo.save(productInstant);
        logger.info("product has been edited");
         return HttpStatus.OK;

    }



    public Map<Integer,List<Product>>  GetMainProducts(Integer page, String name, int minPrice, int maxPrice, Optional<SortOption> sortOption) throws ExecutionException, InterruptedException {

        Pattern checkInteger = Pattern.compile("[0-9]*");
        Matcher matcher = checkInteger.matcher(page.toString());

        if(matcher.matches())
        {
            Pageable pageable = sortOption.map(option -> PageRequest.of(page, 9, option.test(option))).orElseGet(() -> PageRequest.of(page, 9, Sort.unsorted()));
            ExecutorService executorService = Executors.newFixedThreadPool(2);
            Future<List<Product>> products = executorService.submit(() -> productRepo.findAllByCategory12(name,pageable,minPrice,maxPrice));
            Future<Integer> totalRecords = executorService.submit(() -> productRepo.countRecords(name,minPrice,maxPrice));
            Map<Integer,List<Product>> map = new HashMap<>();
            map.put(totalRecords.get(),products.get());
            executorService.shutdown();

            return  (map.isEmpty())?Collections.emptyMap():map;

        }
        else
        {
            throw new   IllegalArgumentException();
        }
    }



  public List<Product> GetAllProducts(){

      List<Product> products = productRepo.findAll();
      Collections.sort(products);
      return (products.isEmpty())?Collections.emptyList():products;

  }

    public List<Product> GetAllProductsByWarehousePlace(String wareHousePlace){

        List<Product> products = productRepo.GetProductsByWarehousePlace(wareHousePlace.toLowerCase());
        Collections.sort(products);
        return (products.isEmpty())?Collections.emptyList():products;
    }


    @Transactional
    public  HttpStatus DeleteMoreProducts(List<Product> products){

         products.forEach(product -> {

             File file = new File(product.getPathToFile());
             file.delete();
             productRepo.deleteById(product.getId());
         });

        return HttpStatus.NO_CONTENT;
    }

    public  HttpStatus ExportData(String routeExport,List<Product> data) {

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
        return HttpStatus.OK;
    }

    public Product GetParcelData() {
        Optional<Product> product =  productRepo.findByProductName("Przesyłka kurierska");
       return product.orElseThrow(ProductNotFound::new);
    }
}

