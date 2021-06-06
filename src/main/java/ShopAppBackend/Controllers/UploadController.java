package ShopAppBackend.Controllers;

import ShopAppBackend.Services.ProductService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class UploadController {


    private ProductService productService;

    @Autowired
    public UploadController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("image/{name}")
    public ResponseEntity showImage(@PathVariable String name) throws IOException {
        File file = new File("C:/ZdjęciaBaza/Upload/" + name);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
               .contentType(MediaType.valueOf(URLConnection.guessContentTypeFromName(name)))
                .body(Files.readAllBytes(file.toPath()));

    }





}
