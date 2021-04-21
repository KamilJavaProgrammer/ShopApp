package ShopAppBackend.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class UserController  {

    private UserService userService;
    private UserRepo userRepo;

    @Autowired
    public UserController(UserService userService, UserRepo userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }




  @PostMapping("/registration")
  public ResponseEntity<String> RegistrationUser(@RequestBody User user) throws MessagingException, IOException, InterruptedException {
        return ResponseEntity.ok(userService.RegistrationUser(user, () -> userService.SendEmail(user.getEmail(),"Kod weryfikacyjny",userRepo.findByUsername(user.getUsername()).getCodeVerification(),false)));
  }

  @PatchMapping("/verification")
  public ResponseEntity<String> VerifyCode(@RequestBody User user ) throws IOException {
    return ResponseEntity.ok(userService.VerifyCode(user));

  }

  @PostMapping("/login")
    public ResponseEntity Login(@RequestBody User user) throws IOException{
      return ResponseEntity.ok(userService.LoginAndGenJsonWebToken(user));
  }

  @PatchMapping("/changePassword")
  public ResponseEntity<String> SendCodeForChangePassword(@RequestBody User user ) throws MessagingException, IOException, InterruptedException {
     userService.SendCodeForChangePassword(user);
     return ResponseEntity.ok(" ");
  }

//  @PostMapping("/user")
//  public ResponseEntity<User> GetUserFromDataBase(){
//     return ResponseEntity.ok(userService.GetUserByName());
//  }

  @GetMapping("/name12")
    public ResponseEntity<String> GetNameOfUser(@AuthenticationPrincipal UsernamePasswordAuthenticationToken user){
        return ResponseEntity.ok(user.getName());
  }

    @GetMapping("/user")
    public ResponseEntity<?> GetUser(@AuthenticationPrincipal UsernamePasswordAuthenticationToken user) throws UserNotFoundException {
        return ResponseEntity.ok(userService.GetUserByName(user.getName()));
    }










}
