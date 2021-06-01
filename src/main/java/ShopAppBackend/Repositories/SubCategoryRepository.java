package ShopAppBackend.Repositories;
import ShopAppBackend.Entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {

    boolean existsByName(String nameOfSubCategory);
    SubCategory findByName(String nameOfSubCategory);

    @Query(value = "SELECT name FROM subcategories",nativeQuery = true)
    List<String> GetAllSubCategories();

}
