package untiled.serwer.serweruntitled.ArtcilesOnInvoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface ArticlesOnInvoiceRepo  extends JpaRepository<ArticlesOnInvoice,Long> {
}
