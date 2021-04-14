package ShopAppBackend.MongoDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemsCountService {

    private MongoDbRepo mongoDbRepo;

    @Autowired
    public ItemsCountService(MongoDbRepo mongoDbRepo) {
        this.mongoDbRepo = mongoDbRepo;
    }

}
