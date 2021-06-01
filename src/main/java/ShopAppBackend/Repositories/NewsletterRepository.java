package ShopAppBackend.Repositories;

import ShopAppBackend.Entities.NewsLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsletterRepository extends JpaRepository<NewsLetter,Integer> {

  @Query(value = "SELECT email from newsletter",nativeQuery = true)
  List<String> getEmailsSubscribers();
}
