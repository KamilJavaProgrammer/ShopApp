package ShopAppBackend.Services;

import ShopAppBackend.Entities.Category;
import ShopAppBackend.Repositories.ProductRepo;
import ShopAppBackend.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private ProductRepo productRepo;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepo productRepo) {
        this.categoryRepository = categoryRepository;
        this.productRepo = productRepo;
    }


    @Transactional
    public Map<Map<String,Integer>,Map<String,Integer>> GetAllCategories(){

        Map<Map<String,Integer>,Map<String,Integer>> categoriesAndSubCategories = new HashMap<>();

         List<Category> categoryList = categoryRepository.findAll();
          categoryList.forEach(category -> {
              Map<String,Integer> categories = new HashMap<>();
              int resultCategory  = productRepo.countAllByCategory1(category);
              categories.put(category.getName(),resultCategory);

              Map<String,Integer> subcategories = new HashMap<>();
              category.getSubCategories().forEach(subCategory -> {
                  int resultSubCategory  = productRepo.countAllBySubCategory(subCategory);
                  subcategories.put(subCategory.getName(),resultSubCategory);
              });
              categoriesAndSubCategories.put(categories,subcategories);
          });
          return categoriesAndSubCategories;
    }
}
