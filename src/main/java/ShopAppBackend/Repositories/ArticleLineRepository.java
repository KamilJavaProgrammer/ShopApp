package ShopAppBackend.Repositories;


import ShopAppBackend.Entities.ArticleLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleLineRepository extends JpaRepository<ArticleLine,Integer> {
}
