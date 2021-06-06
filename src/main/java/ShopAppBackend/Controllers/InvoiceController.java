package ShopAppBackend.Controllers;

import ShopAppBackend.Entities.Company;
import ShopAppBackend.Entities.Invoice;
import ShopAppBackend.Services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetInvoiceById(@PathVariable Long id) throws FileNotFoundException {
        return ResponseEntity.ok(invoiceService.GetInvoiceById(id));
    }

    @GetMapping("")
    public ResponseEntity<?> GetAllInvoices(){
        return invoiceService.GetAllInvoices();
    }


    @PostMapping("")
    public ResponseEntity<?> AddOneInvoice(@RequestBody Invoice invoice) throws IOException, SQLException {

        Company company = new Company();
        company.setName("CafeKam");
        company.setHeadquarters("ul.Czerniecka 123");
        company.setTown("Łącko");
        company.setNip("123456789");
        company.setAccount("02103232323232329");

        System.out.println(invoice);
        invoiceService.generatePDF(invoice,company);
        return null;
    }


}
