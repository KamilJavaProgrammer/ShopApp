package ShopAppBackend.Services;

import ShopAppBackend.Entities.Category;
import ShopAppBackend.Entities.SubCategory;
import ShopAppBackend.Repositories.CategoryRepository;
import ShopAppBackend.Repositories.ProductRepo;
import ShopAppBackend.Repositories.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SubCategoryService {
    private SubCategoryRepository subCategoryRepository;
    private ProductRepo productRepo;
    private CategoryRepository categoryRepository;


    @Autowired
    public SubCategoryService(SubCategoryRepository subCategoryRepository, ProductRepo productRepo, CategoryRepository categoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.productRepo = productRepo;
        this.categoryRepository = categoryRepository;
    }




    public Map<String, Long> GetCategories(String path) {

        if(path != null) {

            String[] data = path.split("/");
            Category category1 = this.categoryRepository.findByName(data[0]);

            if (category1 != null) {

                List<SubCategory> categories = category1.getSubCategories();
                Map<String, Long> map = new HashMap<>();
                categories.forEach(el -> map.put(el.getName(), productRepo.countByProductSubCategory(el.getName())));
                return map;
            }

            else {
                return Collections.EMPTY_MAP;
            }

        }
        else
        {
            throw  new IllegalStateException();
        }
    }



    public Map<String, Long> GetManufacturers(String path,String subCategoryName) {

        if(path != null || subCategoryName !=null) {


            String[] data = path.split("/");

            if (subCategoryName != null) {


                List<String> manufacturers = productRepo.GetManufactures(data[0],subCategoryName);
                System.out.println(manufacturers +"jeden");
                manufacturers.removeIf(Objects::isNull);
                manufacturers.removeIf(s -> s.equals("undefined"));
                Map<String, Long> map = new HashMap<>();
                manufacturers.forEach(el -> map.put(el, productRepo.countByManufacturer(el)));
                return map;
            }

            else {
                return Collections.EMPTY_MAP;
            }

        }
        else
        {
            throw  new IllegalStateException();
        }
    }

    public List<String> GetAllSubCategories(){

        List<String> allSubCategories = subCategoryRepository.GetAllSubCategories();
        if(!allSubCategories.isEmpty()){
            Collections.sort(allSubCategories);
            return allSubCategories;
        }
        else
        {
            return Collections.EMPTY_LIST;
        }
    }
}
