package untiled.serwer.serweruntitled.ArtcilesOnInvoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;

@Service
public class ArticlesOnInvoiceService {
     private ArticlesOnInvoiceRepo articlesOnInvoiceRepo;

    @Autowired
    public ArticlesOnInvoiceService(ArticlesOnInvoiceRepo articlesOnInvoiceRepo) {
        this.articlesOnInvoiceRepo = articlesOnInvoiceRepo;
    }
}
