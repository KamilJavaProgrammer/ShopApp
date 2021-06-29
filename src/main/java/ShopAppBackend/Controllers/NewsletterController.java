//package ShopAppBackend.Controllers;
//
//
//import ShopAppBackend.Entities.NewsLetter;
//import ShopAppBackend.Services.NewsLetterService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotNull;
//import java.util.List;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:4200")
//@Validated
//public class NewsletterController{
//
//    private final NewsLetterService newsLetterService;
//
//
//    @Autowired
//    public NewsletterController(NewsLetterService newsLetterService) {
//        this.newsLetterService = newsLetterService;
//    }
//
//    @GetMapping("/news")
//    public ResponseEntity<List<NewsLetter>> GetAllSubscribers() {
//        return ResponseEntity.ok(newsLetterService.GetAllNewsletters());
//    }
//
//    @PostMapping("/news")
//    public  ResponseEntity<HttpStatus> AddNewSubscriber(@RequestBody @Email String email){
//       return  ResponseEntity.ok(newsLetterService.AttachObserver(email));
//    }
//
//
//    @DeleteMapping("/news/{id}")
//    public ResponseEntity<HttpStatus> DeleteSubscriberById(@PathVariable @NotNull Integer id){
//            return ResponseEntity.ok(newsLetterService.RemoveObserver(id));
//    }
//
//
//}
