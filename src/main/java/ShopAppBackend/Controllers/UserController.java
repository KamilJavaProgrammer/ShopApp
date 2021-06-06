package ShopAppBackend.Controllers;

import ShopAppBackend.Entities.User;
import ShopAppBackend.DTOs.UserDto;
import ShopAppBackend.Exceptions.UserNotFoundException;
import ShopAppBackend.Repositories.UserRepo;
import ShopAppBackend.Services.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.io.IOException;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
@Validated

public class UserController  {

    private final UserService userService;
    private final UserRepo userRepo;
    private final static String SUBJECT = "Kod weryfikacyjny";

    @Autowired
    public UserController(UserService userService, UserRepo userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }


   @PostMapping("/registration")
   public ResponseEntity<Response> RegistrationUser(@Valid @RequestBody UserDto userDto) throws MessagingException, IOException {
         return ResponseEntity.ok(userService.RegistrationUser(userDto, () -> userService.SendEmail(userDto.getEmail(),SUBJECT,userRepo.findByUsername(userDto.getUsername()).getCodeVerification(),false)));
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


    @PostMapping("/password")
    public ResponseEntity<HttpStatus> SendCodeForChangePassword(@RequestBody @Email String email) throws MessagingException, IOException, InterruptedException {

        return ResponseEntity.ok(userService.SendCodeForChangePassword(email));
  }

   @GetMapping("/auth")
    public User GetUser(@AuthenticationPrincipal UsernamePasswordAuthenticationToken user) throws UserNotFoundException, IOException {
        return userService.GetUserByName(user.getName());
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> GetAllUsers(){
        return ResponseEntity.ok(userService.GetAllUsers());
    }

    @PatchMapping("/password")
    public ResponseEntity<HttpStatus> ChangePassword(@RequestBody User user) throws IOException {
        return ResponseEntity.ok(userService.ChangePassword(user));
    }

}
