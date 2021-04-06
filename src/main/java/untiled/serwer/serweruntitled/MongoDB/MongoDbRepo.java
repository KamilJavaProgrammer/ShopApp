package untiled.serwer.serweruntitled.MongoDB;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MongoDbRepo  extends MongoRepository<ItemsCount,String> {


}
