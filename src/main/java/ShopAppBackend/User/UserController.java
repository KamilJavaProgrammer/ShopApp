package ShopAppBackend.User;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;


@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class UserController  {

    private final UserService userService;
    private final UserRepo userRepo;

    @Autowired
    public UserController(UserService userService, UserRepo userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }


   @PostMapping("/registration")
   public ResponseEntity<Response> RegistrationUser(@Valid @RequestBody UserDto userDto) throws MessagingException, IOException, InterruptedException {
         return ResponseEntity.ok(userService.RegistrationUser(userDto, () -> userService.SendEmail(userDto.getEmail(),"Kod weryfikacyjny",userRepo.findByUsername(userDto.getUsername()).getCodeVerification(),false)));
   }

    @PatchMapping("/verification")
    public ResponseEntity<Response> VerifyCode(@RequestBody UserDto userDto ) throws IOException {
        return ResponseEntity.ok(userService.VerifyCode(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@Valid @RequestBody User user) throws IOException, UserNotFoundException {
        return ResponseEntity.ok(userService.LoginAndGenJsonWebToken(user));
    }

    @PostMapping("/login/admin")
    public ResponseEntity<?> LoginAdmin(@Valid @RequestBody User user) throws IOException, UserNotFoundException {
        return ResponseEntity.ok(userService.LoginAdminAndGenJsonWebToken(user));
    }

    @PatchMapping("/changePassword")
    public ResponseEntity<String> SendCodeForChangePassword(@RequestBody User user ) throws MessagingException, IOException, InterruptedException {
     userService.SendCodeForChangePassword(user);
     return ResponseEntity.ok(" ");
  }


  @GetMapping("/user")
    public ResponseEntity<?> GetUser(@AuthenticationPrincipal UsernamePasswordAuthenticationToken user) throws UserNotFoundException, IOException {
        return ResponseEntity.ok(userService.GetUserByName(user.getName()));
    }

}
