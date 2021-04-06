package untiled.serwer.serweruntitled.ArtcilesOnInvoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ArtcilesOnInvoiceController {

    private ArticlesOnInvoiceService articlesOnInvoiceService;

    @Autowired
    public ArtcilesOnInvoiceController(ArticlesOnInvoiceService articlesOnInvoiceService) {
        this.articlesOnInvoiceService = articlesOnInvoiceService;
    }
}
