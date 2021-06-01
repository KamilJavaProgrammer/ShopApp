package ShopAppBackend.Repositories;

import ShopAppBackend.Entities.SectionSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionSubCategoryRepository extends JpaRepository<SectionSubCategory,Integer> {
}
