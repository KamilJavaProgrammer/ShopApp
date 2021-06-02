package ShopAppBackend.Repositories;

import ShopAppBackend.DocumentsMongoDB.Data;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DataRepositoryMongoDb extends MongoRepository<Data,String> {
}
