package ShopAppBackend.MongoDB;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MongoDbRepo  extends MongoRepository<ItemsCount,String> {


}
