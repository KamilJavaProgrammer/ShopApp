package ShopAppBackend.ArtcilesOnInvoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticlesOnInvoiceService {
     private ArticlesOnInvoiceRepo articlesOnInvoiceRepo;

    @Autowired
    public ArticlesOnInvoiceService(ArticlesOnInvoiceRepo articlesOnInvoiceRepo) {
        this.articlesOnInvoiceRepo = articlesOnInvoiceRepo;
    }
}