package ShopAppBackend.User;


import ShopAppBackend.ServiceClient.ShopClient.ShopClient;
import ShopAppBackend.ServiceClient.ShopClient.ShopClientRepository;
import ShopAppBackend.Security.FilterJwt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.*;

@Service
public class UserService  {

    public final long expirationTime;
    public final  String secret;
    private UserRepo userRepo;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private JavaMailSender javaMailSender;
    private PasswordEncoder passwordEncoder;
    public static String name = "";
   public  String codeVerification;
   public ShopClientRepository shopClientRepository;



    @Autowired
    public UserService(@Value("${jwt.secret}")String secret, @Value("${jwt.token.expirationTime}") long expirationTime,
                       UserRepo userRepo, JavaMailSender javaMailSender, PasswordEncoder passwordEncoder,
                       ShopClientRepository shopClientRepository) {

        this.expirationTime = expirationTime;
        this.secret = secret;
        this.userRepo = userRepo;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
        this.shopClientRepository = shopClientRepository;

    }

    public ResponseEntity<String> LoginAndGenJsonWebToken(User user) throws IOException{
        if (userRepo.existsUserByUsername(user.getUsername())) {

            User user1 = userRepo.findByUsername(user.getUsername());


            if (passwordEncoder.matches(user.getPassword(), user1.getPassword())) {

                String token = JWT.create()
                             .withSubject(user1.getUsername())
                             .withClaim("role",user1.getRole())
                             .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                             .sign(Algorithm.HMAC256(secret));


                logger.info("Sucessfull Generate JWT");
                FilterJwt.SaveToFile("Sucessfull Generate JWT from user" + user1.getUsername());

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);

            }
            else
                {
                    logger.warn("Wrong login or password");
                    FilterJwt.SaveToFile("Wrong login or password");
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("WrongLoginOrPassword");
            }
        }
        else
        {
            logger.warn("User dont exists");
            FilterJwt.SaveToFile("Failed Attempt Login User" + user.getUsername() + "." + "User dont exists");
//            return HttpStatus.valueOf("UserDontExists");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("UserDontExists");

        }
    }



    public String RegistrationUser(User user,Mailing mailing)throws MessagingException,IOException {

        try {
            if (!userRepo.existsUserByEmail(user.getEmail())) {

                if (!userRepo.existsUserByUsername(user.getUsername())) {

                    user.setAuthorization(false);
                    user.setRole("USER");

                    ShopClient client = new ShopClient();

                    client.setEmail(user.getEmail());
                    client.setState("Zarejestrowany");
                    shopClientRepository.save(client);

                    user.setShopClient(client);

                    this.codeVerification = this.GenerateRandomKey();
                    user.setCodeVerification(codeVerification);
                    String encodedPassword = passwordEncoder.encode(user.getPassword());
                    user.setPassword(encodedPassword);
                    userRepo.save(user);
                    FilterJwt.SaveToFile("Add User of email" + " " + user.getEmail());
                    mailing.Mail();
                    return "OK";


                } else {
                    logger.warn("Login exists");
                    FilterJwt.SaveToFile("LoginExists");
                    return "LoginExists";
                }
            } else {
                logger.info("Email exists");
                FilterJwt.SaveToFile("Email exists");
                return "EmailExists";
            }
        }catch (NullPointerException | InterruptedException e)
        {
            logger.warn("User is null");
        }
        return null;
    }

   @Async
    public void SendEmail(String to, String subject, String text, boolean isHtmlContent) throws MessagingException, InterruptedException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHtmlContent);
        javaMailSender.send(mimeMessage);

    }

    public String VerifyCode(User user) throws IOException{

      User user1 =  userRepo.findByUsername(user.getUsername());

      String codeFromUser = user.getCodeVerification();
      String codeVerification = user1.getCodeVerification();

      if(codeFromUser.equals(codeVerification)==true)
      {
          user1.setAuthorization(true);
          userRepo.save(user1);

          logger.info("Successful verification");
          FilterJwt.SaveToFile("Successful verification by user" + user1.getUsername());
          return "OK";
      }
      else
      {
          logger.warn("Inauspicious verification");
          FilterJwt.SaveToFile("No Successful verification by user" + user1.getUsername());

          return "ERROR";
      }
    }



    public String SendCodeForChangePassword(User user) throws MessagingException, IOException, InterruptedException {

       if(userRepo.existsUserByEmail(user.getEmail())) {

           String codeVerification = this.GenerateRandomKey();
           SendEmail(user.getEmail(), "Kod do zmiany hasła", codeVerification, false);

           logger.info("SendCodeToEmail");
           FilterJwt.SaveToFile("SendCodeToEmail");
           return "OK";
       }
       else
       {
           logger.error("Email Dont Exists");
           FilterJwt.SaveToFile("Email dont exists");
           return "EmailDontExists";

       }
    }

    public String ChangePassword(User user){

        if(userRepo.existsUserByEmail(user.getEmail())){
            User user1 = userRepo.findByEmail(user.getEmail());
            user1.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user1);
            return "OK";
        }
        else
        {
            return "Dont exists User";
        }
    }

    public String GenerateRandomKey(){
        Random random = new Random();
        String codeVerif = "";

        for (int i = 0; i < 6; i++) {
            int codeVerification = random.nextInt(9);
            codeVerif = codeVerif + codeVerification;
        }
        return codeVerif;
    }


    public User GetUserByName(String name){
       return userRepo.findByUsername(name);
    }

}

