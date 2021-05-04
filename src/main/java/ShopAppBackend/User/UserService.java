package ShopAppBackend.User;

import ShopAppBackend.Logs.LogsApplication;
import ShopAppBackend.ServiceClient.ShopClient.ShopClientRepository;
import ShopAppBackend.Security.FilterJwt;
import ShopAppBackend.ServiceClient.ShopClient.ShopClientService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
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
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class UserService  {


    private final LogsApplication logsApplication;
    public final long expirationTime;
    public final  String secret;
    private final UserRepo userRepo;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    public static String name = "";
    public  String codeVerification;
    public ShopClientRepository shopClientRepository;
    public ShopClientService shopClientService;
    public ModelMapper modelMapper;
    private ObjectMapper objectMapper;



    @Autowired
    public UserService(@Value("${jwt.secret}")String secret, @Value("${jwt.token.expirationTime}") long expirationTime,
                       UserRepo userRepo, JavaMailSender javaMailSender, PasswordEncoder passwordEncoder,
                       ShopClientRepository shopClientRepository, ModelMapper modelMapper, ObjectMapper objectMapper,
                       ShopClientService shopClientService,
                       LogsApplication logsApplication) {

        this.expirationTime = expirationTime;
        this.secret = secret;
        this.userRepo = userRepo;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
        this.shopClientRepository = shopClientRepository;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.shopClientService = shopClientService;
        this.logsApplication = logsApplication;

    }



    public Response RegistrationUser(UserDto userDto, Mailing mailing)throws MessagingException,IOException {

                 User user = objectMapper.convertValue(userDto,User.class);

        try {
            Response response = new Response();

            if (!userRepo.existsUserByEmail(user.getEmail())) {

                if (!userRepo.existsUserByUsername(user.getUsername())) {

                    user.setAuthorization(false);
                    user.setRole("USER");
                    user.setShopClient(shopClientService.CreateNewShopClient(user));

                    this.codeVerification = this.GenerateRandomKey();
                    user.setCodeVerification(codeVerification);
                    String encodedPassword = passwordEncoder.encode(user.getPassword());
                    user.setPassword(encodedPassword);
                    userRepo.save(user);
                    FilterJwt.SaveToFile("Add User of email" + " " + user.getEmail());
                    mailing.Mail();
                         response.setMessage("OK");
                         response.setStatus(201);
                         return response;
                }
                else {
                    logger.warn("Login exists");
                    FilterJwt.SaveToFile("Login Exists");
                    response.setMessage("Login Exists");
                    response.setStatus(200);
                    return response;
                }
            }
            else {
                logger.info("Email exists.");
                FilterJwt.SaveToFile("Email exists");
                response.setMessage("Email exists");
                response.setStatus(200);
                return response;
            }
        }
        catch (NullPointerException | InterruptedException e)
        {
            logger.warn("User is null");
        }
        return null;
    }



    public Response VerifyCode(UserDto userDto) throws IOException{

        User user = objectMapper.convertValue(userDto,User.class);

        User userInstant =  userRepo.findByUsername(user.getUsername());

        String codeFromUser = user.getCodeVerification();
        String codeVerification = userInstant.getCodeVerification();
        Response response = new Response();

        if(codeFromUser.equals(codeVerification))
        {
            userInstant.setAuthorization(true);
            userRepo.save(userInstant);

            logger.info("Successful verification");
            FilterJwt.SaveToFile("Successful verification by user" + userInstant.getUsername());
            response.setMessage("OK");
            response.setStatus(201);

        }
        else
        {
            logger.warn("Inauspicious verification");
            FilterJwt.SaveToFile("No Successful verification by user" + userInstant.getUsername());
            response.setMessage("Inauspicious verification");
            response.setStatus(401);
        }
        return response;
    }




    public ResponseEntity<String>GenJsonWebToken(User user,User userInstant) throws IOException {
        if (passwordEncoder.matches(user.getPassword(), userInstant.getPassword())) {

            String token = JWT.create()
                    .withSubject(userInstant.getUsername())
                    .withClaim("role",userInstant.getRole())
                    .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                    .sign(Algorithm.HMAC256(secret));


            logger.info("Sucessfull Generate JWT");
            logsApplication.SaveToFile("Sucessfull Generate JWT from user" + userInstant.getUsername());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);

        }
        else
        {
            logger.warn("Wrong login or password");
            logsApplication.SaveToFile("Wrong login or password");
            throw new WrongLoginData();
        }
    }

    public ResponseEntity<String> LoginAdminAndGenJsonWebToken(User user) throws IOException, UserNotFoundException {
        if (userRepo.existsUserByUsername(user.getUsername())) {

            User user1 = userRepo.findByUsername(user.getUsername());
            if(user1.getRole().equals("ADMIN")){
                return GenJsonWebToken(user,user1);
            }
            else

            {
                logger.info("User don't be ADMIN");
                FilterJwt.SaveToFile("User don't be ADMIN" + user1.getUsername());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
            }
        }
        else
        {
            logger.warn("User dont exists");
            FilterJwt.SaveToFile("Failed Attempt Login User" + user.getUsername() + "." + "User dont exists");
            throw new UserNotFoundException();
//            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("UserDontExists");

        }
    }




    public ResponseEntity<String> LoginAndGenJsonWebToken(User user) throws IOException, UserNotFoundException {


        if (userRepo.existsUserByUsername(user.getUsername())) {

            User userInstant = userRepo.findByUsername(user.getUsername());
            return GenJsonWebToken(user,userInstant);
        }
        else
        {
            logger.warn("User not found");
            this.logsApplication.SaveToFile("User not found");
            throw new UserNotFoundException();

        }
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


    public ResponseEntity<UserDto> GetUserByName(String username) throws UserNotFoundException, IOException {
        User user = userRepo.findByUsername(username);
        if(user != null){
           UserDto userDto = modelMapper.map(user,UserDto.class);
           return ResponseEntity.status(HttpStatus.OK).body(userDto);
        }
        else
        {
            throw new UserNotFoundException();
        }

    }

}

