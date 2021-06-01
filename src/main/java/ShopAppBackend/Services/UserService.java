package ShopAppBackend.Services;

import ShopAppBackend.DTOs.UserDto;
import ShopAppBackend.Entities.User;
import ShopAppBackend.Exceptions.EmailNotFoundException;
import ShopAppBackend.Exceptions.UserNotFoundException;
import ShopAppBackend.Logs.LogsApplication;
import ShopAppBackend.Repositories.UserRepo;
import ShopAppBackend.Repositories.ShopClientRepository;
import ShopAppBackend.Interfaces.Mailing;
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
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.IOException;
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



    @Transactional
    public Response RegistrationUser(UserDto userDto, Mailing mailing)throws MessagingException,IOException {

                 User user = objectMapper.convertValue(userDto,User.class);

        try {
            Response response = new Response();

            if (!userRepo.existsUserByEmail(user.getEmail())) {

                if (!userRepo.existsUserByUsername(user.getUsername())) {

                    user.setAuthorization(false);
                    user.setRole("ADMIN");
                    user.setShopClient(shopClientService.CreateNewShopClient(user));

                    this.codeVerification = this.GenerateRandomKey();
                    user.setCodeVerification(codeVerification);
                    String encodedPassword = passwordEncoder.encode(user.getPassword());
                    user.setPassword(encodedPassword);
                    userRepo.save(user);
//                    FilterJwt.SaveToFile("Add User of email" + " " + user.getEmail());
                    mailing.Mail();
                         response.setMessage("OK");
                         response.setStatus(201);
                         return response;
                }
                else {
                    logger.warn("Login exists");
//                    FilterJwt.SaveToFile("Login Exists");
                    response.setMessage("Login Exists");
                    response.setStatus(200);
                    return response;
                }
            }
            else {
                logger.info("Email exists.");
//                FilterJwt.SaveToFile("Email exists");
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
//            FilterJwt.SaveToFile("Successful verification by user" + userInstant.getUsername());
            response.setMessage("OK");
            response.setStatus(201);

        }
        else
        {
            logger.warn("Inauspicious verification");
//            FilterJwt.SaveToFile("No Successful verification by user" + userInstant.getUsername());
            response.setMessage("Inauspicious verification");
            response.setStatus(401);
        }
        return response;
    }



    public Response GenJsonWebToken(User user,User userInstant) throws IOException {

        Response response = new Response();

        if (passwordEncoder.matches(user.getPassword(), userInstant.getPassword())) {

            if(userInstant.isEnabled())
            {
                String token = JWT.create()
                        .withSubject(userInstant.getUsername())
                        .withClaim("role",userInstant.getRole())
                        .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                        .sign(Algorithm.HMAC256(secret));


                logger.info("Sucessfull Generate JWT");
                logsApplication.SaveToFile("Sucessfull Generate JWT from user" + userInstant.getUsername());
                response.setStatus(200);
                response.setMessage(token);

            }
            else
            {

                logger.warn("Account is deactive");
                logsApplication.SaveToFile("Account is deactive");
                response.setStatus(403);
                response.setMessage("Account is deactive");
            }

        }
        else
        {
            logger.warn("Wrong login or password");
            logsApplication.SaveToFile("Wrong login or password");
            response.setStatus(401);
            response.setMessage("Wrong login or password");
        }
        return response;
    }


    @Transactional
    public Response LoginAdminAndGenJsonWebToken(User user) throws IOException, UserNotFoundException {
        if (userRepo.existsUserByUsername(user.getUsername())) {

            User user1 = userRepo.findByUsername(user.getUsername());
            if(user1.getRole().equals("ADMIN")){
                return GenJsonWebToken(user,user1);
            }
            else

            {
                logger.info("User don't be ADMIN");
//                FilterJwt.SaveToFile("User don't be ADMIN" + user1.getUsername());
                Response response = new Response();
                response.setMessage("Not authorized");
                response.setStatus(401);
                return response;
            }
        }
        else
        {
            logger.warn("User dont exists");
//            FilterJwt.SaveToFile("Failed Attempt Login User" + user.getUsername() + "." + "User dont exists");
            throw new UserNotFoundException();
        }
    }



    @Transactional
    public Response LoginAndGenJsonWebToken(User user) throws IOException, UserNotFoundException {


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



    public String GenerateRandomKey(){
        Random random = new Random();
        String codeVerif = "";

        for (int i = 0; i < 6; i++) {
            int codeVerification = random.nextInt(9);
            codeVerif = codeVerif + codeVerification;
        }
        return codeVerif;
    }


    public User GetUserByName(String username) throws UserNotFoundException, IOException {
        System.out.println("jestem w metodzie");
        System.out.println("jestem w metodzie");

        User user = userRepo.findByUsername(username);
        if(user != null){


//           UserDto userDto  = modelMapper.map(user,UserDto.class);

            return user;
        }
        else
        {
            System.out.println("jestem w else");
            throw new UserNotFoundException();
        }

    }



    public HttpStatus SendCodeForChangePassword(String email) throws MessagingException, IOException, InterruptedException {

        if(userRepo.existsUserByEmail(email)) {

            User user = userRepo.findByEmail(email);
            SendEmail(email, "Kod do zmiany hasła", user.getCodeVerification(), false);

            logger.info("SendCodeToEmail");
//            FilterJwt.SaveToFile("SendCodeToEmail");
            return HttpStatus.OK;
        }
        else
        {
            logger.error("Email Dont Exists");
//            FilterJwt.SaveToFile("Email dont exists");
            throw new EmailNotFoundException();

        }
    }

    public HttpStatus ChangePassword(User user) throws IOException {

        if(userRepo.existsUserByEmail(user.getEmail())){


            User userInstant = userRepo.findByEmail(user.getEmail());

            if(user.getCodeVerification().equals(userInstant.getCodeVerification())) {
                userInstant.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepo.save(userInstant);
//                FilterJwt.SaveToFile(userInstant + "changed password");
                return HttpStatus.OK;

            }
            else

            {
                logger.error("CodeVerification is inncorrect");
                return HttpStatus.NOT_ACCEPTABLE;

            }

        }
        else
        {
            logger.error("Email Dont Exists");
//            FilterJwt.SaveToFile("Email dont exists");
            throw new EmailNotFoundException();
        }
    }

    public List<UserDto> GetAllUsers(){

            List<User> users  = userRepo.findAll();
            List<UserDto> usersDto  = new ArrayList<>();

            users.forEach(user -> {
                user.setShopClient(null);
                user.setPassword(null);
                user.setCodeVerification(null);
                usersDto.add(modelMapper.map(user,UserDto.class));
            });

           return usersDto;
    }

}
