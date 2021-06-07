package ShopAppBackend.Repositories;

import ShopAppBackend.Entities.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogsRepository extends JpaRepository<Log,Long> {

}
