package ShopAppBackend.FrontMainPageManagement.Section.SectionCategory;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionCategoryRepository extends JpaRepository<SectionCategory,Integer> {
}
