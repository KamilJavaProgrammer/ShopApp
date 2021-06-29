//package ShopAppBackend.Controllers;
//
//import ShopAppBackend.DocumentsMongoDB.Data;
//import ShopAppBackend.Services.DataServiceMongo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.constraints.NotNull;
//
//@RestController
//@CrossOrigin(origins = "*", allowCredentials = "true")
//@Validated
//public class DataController {
//
//    private final DataServiceMongo dataServiceMongo;
//
//    @Autowired
//    public DataController(DataServiceMongo dataServiceMongo) {
//        this.dataServiceMongo = dataServiceMongo;
//    }
//
//
//    @GetMapping("/data/{id}")
//    public ResponseEntity<Data> GetDataById(@PathVariable @NotNull String id){
//      return  ResponseEntity.status(HttpStatus.OK).body(dataServiceMongo.GetDataById(id));
//    }
//
//    @PostMapping("/data")
//    public ResponseEntity<HttpStatus> AddDataToDatabase(@RequestBody Data data){
//        return  ResponseEntity.status(HttpStatus.OK).body(dataServiceMongo.SaveDataToMongoDatabase(data));
//    }
//}
