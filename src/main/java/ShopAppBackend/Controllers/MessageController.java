package ShopAppBackend.Controllers;

import ShopAppBackend.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
public class MessageController{


    private final UserRepo userRepo;


    @Autowired
    public MessageController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/delete")
    public void TES1T(){


        userRepo.findAll().forEach(user -> {
            user.getMessages().clear();
            userRepo.save(user);
        });
    }
}
