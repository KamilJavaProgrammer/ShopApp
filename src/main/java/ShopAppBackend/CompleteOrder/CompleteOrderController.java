package ShopAppBackend.CompleteOrder;
import ShopAppBackend.Security.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CompleteOrderController {

    private final CompleteOrderService completeOrderService;


    @Autowired
    public CompleteOrderController(CompleteOrderService completeOrderService) {
        this.completeOrderService = completeOrderService;

    }

    @GetMapping("/order")
    public ResponseEntity<?> GetAllOrdersForUser(@AuthenticationPrincipal UsernamePasswordAuthenticationToken user) {
        return ResponseEntity.ok(completeOrderService.GetAllOrdersByUser(user.getName()));
    }

//    @GetMapping("/order/{id}")
//    public ResponseEntity<List<CompleteOrder>> GetAllOrdersByUserId(@PathVariable Long id){
//        return ResponseEntity.ok(completeOrderService.GetAllOrdersByUserId(id));
//    }

    @PostMapping("/order")
    public ResponseEntity<HttpStatus> TakeOrder(@RequestBody CompleteOrder completeOrder) throws IOException, SQLException {
        return ResponseEntity.ok(completeOrderService.AddOrderToDatabase(completeOrder));
    }


    @GetMapping("/order/all")
    public ResponseEntity<List<CompleteOrder>> GetAllOrders() {

        return ResponseEntity.status(HttpStatus.OK).body(completeOrderService.GetAllOrders());

    }


}
