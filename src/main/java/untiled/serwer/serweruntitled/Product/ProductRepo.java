package untiled.serwer.serweruntitled.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

//    ProductEntity findByNameContaining(String names);
    boolean existsByProductName(String names);
//    List<ProductEntity> findByModel(String model);
    List<Product> findAllByManufacturerContainingOrProductNameContaining(String name, String name1);
    List<Product> findAllByProductCategory(String productCategory);

    @Query(value = "SELECT * FROM products ORDER BY id DESC LIMIT ?",nativeQuery = true)
    List<Product> GetRecentItems(Integer amount);


    @Query(value = "SELECT * FROM products ORDER BY id ASC LIMIT ?",nativeQuery = true)
    List<Product> GetFirstItems(Integer amount);

    @Query(value = "SELECT * FROM products where product_category = ? ORDER BY id asc LIMIT 12",nativeQuery = true)
    List<Product> GetMainItems(String productCategory);

    @Query(value = "SELECT * FROM products where product_sub_category = ? ORDER BY id asc",nativeQuery = true)
    List<Product> GetSpecifiedItems(String productSubCategory);

    @Query(value = "SELECT * FROM products where model = ? ORDER BY id asc",nativeQuery = true)
    List<Product> GetSpecifiedItems1(String productSubCategory);


    @Query(value = "SELECT * FROM products where manufacturer = ? ORDER BY id asc",nativeQuery = true)
    List<Product> GetItemsManufacturer(String manufacturer);


    @Query(value = "SELECT manufacturer FROM products where product_category = :productCategory  and product_sub_category = :productSubCategory",nativeQuery = true)
    List<String> GetManufactures(String productCategory,String productSubCategory);


    @Query(value = "SELECT * FROM products where ware_houseplace like  %:warehouseplace%",nativeQuery = true)
    List<Product> GetProductsByWarehousePlace(String warehouseplace);







      Long countByProductSubCategory(String productSubCategory);
      Long countByManufacturer(String model);
}
