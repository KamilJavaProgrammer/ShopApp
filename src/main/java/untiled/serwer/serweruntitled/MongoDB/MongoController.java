package untiled.serwer.serweruntitled.MongoDB;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MongoController {

    private ItemsCountService itemsCountService;
    private MongoDbRepo mongoDbRepo;

    @Autowired
    public MongoController(ItemsCountService itemsCountService, MongoDbRepo mongoDbRepo) {
        this.itemsCountService = itemsCountService;
        this.mongoDbRepo = mongoDbRepo;
    }

    @GetMapping("/mongotest")
    public void DodajBazaMongo(){

        List<Integer> numbers = new ArrayList<>();

        numbers.add(2);
        numbers.add(3);
        numbers.add(4);

        ItemsCount itemsCount  = new ItemsCount("numberRecentlyAddedPhoto",8);
        ItemsCount itemsCount1  = new ItemsCount("listnumbers",numbers);

        mongoDbRepo.save(itemsCount);
        mongoDbRepo.save(itemsCount1);


    }
}
