package ShopAppBackend.Repositories;

import ShopAppBackend.Entities.Category;
import ShopAppBackend.Entities.Product;
import ShopAppBackend.Entities.SubCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

    boolean existsByProductName(String names);
    List<Product> findAllByManufacturerContainingOrProductNameContaining(String name, String name1);

    @Query(value = "SELECT manufacturer FROM products where product_category = :productCategory  and product_sub_category = :productSubCategory",nativeQuery = true)
    List<String> GetManufactures(String productCategory,String productSubCategory);

    @Query(value = "SELECT * FROM products where ware_houseplace like  %:warehouseplace%",nativeQuery = true)
    List<Product> GetProductsByWarehousePlace(String warehouseplace);

    Optional<Product> findByProductName(String productName);

    @Query(value = "SELECT DISTINCT  * FROM products where (product_category like %:name% or " +
                   "product_sub_category like %:name% or product_name like %:name%) and product_price between :minPrice and :maxPrice",nativeQuery = true)
       List<Product> findAllByCategory12(String name, Pageable pageable,int minPrice,int maxPrice);
//

       @Query(value = "Select distinct COUNT(*) FROM products where (product_category like %:name% or " +
            "product_sub_category like %:name% or product_name like %:name%) and product_price between :minPrice and :maxPrice",nativeQuery = true)
    Integer countRecords(String name,int minPrice,int maxPrice);



    Long countByProductSubCategory(String productSubCategory);
      Long countByManufacturer(String model);

      Integer countAllByCategory1(Category category);
      Integer countAllBySubCategory(SubCategory subcategory);
}
