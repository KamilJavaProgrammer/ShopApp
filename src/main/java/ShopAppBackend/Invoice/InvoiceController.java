package ShopAppBackend.Invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/invoice/{id}")
    public ResponseEntity<?> GetInvoiceById(@PathVariable Long id) throws FileNotFoundException {
        return invoiceService.GetInvoiceById(id);
    }

    @GetMapping("/invoice/all")
    public ResponseEntity<?> GetAllInvoices(){
        return invoiceService.GetAllInvoices();
    }


    @PostMapping("/invoice")
    public ResponseEntity<?> AddOneInvoice(@RequestBody Invoice invoice){
        return null;
    }

}
