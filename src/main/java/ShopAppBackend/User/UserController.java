package ShopAppBackend.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
  public ResponseEntity<ResponseEntity<String>> RegistrationUser(@Valid @RequestBody User user) throws MessagingException, IOException, InterruptedException {
        return ResponseEntity.ok(userService.RegistrationUser(user, () -> userService.SendEmail(user.getEmail(),"Kod weryfikacyjny",userRepo.findByUsername(user.getUsername()).getCodeVerification(),false)));
  }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@Valid @RequestBody User user) throws IOException, UserNotFoundException {
        return ResponseEntity.ok(userService.LoginAndGenJsonWebToken(user));
    }


    @PatchMapping("/verification")
  public ResponseEntity<String> VerifyCode(@RequestBody User user ) throws IOException {
    return ResponseEntity.ok(userService.VerifyCode(user));

  }



    @PostMapping("/login/admin")
    public ResponseEntity LoginAdmin(@RequestBody User user) throws IOException, UserNotFoundException {
        return ResponseEntity.ok(userService.LoginAdminAndGenJsonWebToken(user));
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
    public ResponseEntity<?> GetUser(@AuthenticationPrincipal UsernamePasswordAuthenticationToken user) throws UserNotFoundException, IOException {
        return ResponseEntity.ok(userService.GetUserByName(user.getName()));
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        System.out.println(errors);
        return errors;
    }







}
