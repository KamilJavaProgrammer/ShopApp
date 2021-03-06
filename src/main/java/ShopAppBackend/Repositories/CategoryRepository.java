package ShopAppBackend.Repositories;

import ShopAppBackend.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {


    boolean existsByName(String nameOfCategory);
    Category findByName(String nameOfCategory);

}
