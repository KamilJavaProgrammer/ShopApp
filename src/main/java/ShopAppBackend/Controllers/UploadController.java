package ShopAppBackend.Controllers;

import ShopAppBackend.Entities.User;
import ShopAppBackend.Repositories.ProductRepo;
import ShopAppBackend.Repositories.UserRepo;
import ShopAppBackend.Services.ProductService;
import ShopAppBackend.Services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")

public class UploadController {


    private final ProductService productService;
    private final StorageService service;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;



    @Autowired
    public UploadController(ProductService productService, StorageService service, PasswordEncoder passwordEncoder, UserRepo userRepo, ProductRepo productRepo) {
        this.productService = productService;
        this.service = service;
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
    }

    @GetMapping("/addAdmin")
    public ResponseEntity<String> addadmin(){

       User user = new User();
       user.setUsername("admin");
       user.setPassword(passwordEncoder.encode("adminkawix"));
       user.setRole("ADMIN");
        user.setAuthorization(true);
        userRepo.save(user);
        return ResponseEntity.ok("Dodan");
    }


    @GetMapping("image/{name}")
    public ResponseEntity downloadFile(@PathVariable String name) throws IOException {
        byte[] data = service.downloadFile(name);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + name + "\"")
                .body(resource);


    }




    @GetMapping("image1/{name}")
    public ResponseEntity showImage(@PathVariable String name) throws IOException {
        File file = new File("C:/ZdjęciaBaza/Upload/" + name);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
               .contentType(MediaType.valueOf(URLConnection.guessContentTypeFromName(name)))
                .body(Files.readAllBytes(file.toPath()));

    }







        @GetMapping("/tel/{name}")
    public ResponseEntity showImag2e(@PathVariable String name) throws IOException {

        byte[] file = service.downloadFile(name);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(URLConnection.guessContentTypeFromName(name)))
                                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + name + "\"")
                .body(file);


    }




}
