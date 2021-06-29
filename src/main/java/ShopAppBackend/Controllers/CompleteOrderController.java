package ShopAppBackend.Controllers;
import ShopAppBackend.Services.CompleteOrderService;
import ShopAppBackend.Entities.CompleteOrder;
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
@CrossOrigin(origins = "*", allowCredentials = "true")
public class CompleteOrderController {

    private final CompleteOrderService completeOrderService;


    @Autowired
    public CompleteOrderController(CompleteOrderService completeOrderService) {
        this.completeOrderService = completeOrderService;

    }

    @GetMapping("/orders")
    public ResponseEntity<List<CompleteOrder>> GetAllOrdersForUser(@AuthenticationPrincipal UsernamePasswordAuthenticationToken user) {
        return ResponseEntity.ok(completeOrderService.GetAllOrdersByUser(user.getName()));
    }


    @PostMapping("/orders")
    public ResponseEntity<HttpStatus> TakeOrder(@RequestBody CompleteOrder completeOrder) throws IOException, SQLException {
        return ResponseEntity.ok(completeOrderService.AddOrderToDatabase(completeOrder));
    }


    @GetMapping("/orders/all")
    public ResponseEntity<List<CompleteOrder>> GetAllOrders() {

        return ResponseEntity.status(HttpStatus.OK).body(completeOrderService.GetAllOrders());

    }


}
