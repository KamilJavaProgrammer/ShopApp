package ShopAppBackend.Invoice;

import ShopAppBackend.Business.Business;
import ShopAppBackend.CompleteOrder.CompleteOrder;
import ShopAppBackend.ProductBasket.ProductBasket;
import ShopAppBackend.ServiceClient.ServiceClient.ServiceClient;
import ShopAppBackend.ServiceClient.ShopClient.ShopClient;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.List;

@Data
public class InvoiceDTO {

    private Long id;
    private String recipient;
    private String invoicePath;
    private String payForm;
    private String date;
    private String paid;
    private String spendFromStock;
    private String paymentDeadline;
    private double sumVatValue;
    private double sumNettoValue;
    private double sumBruttoValue;
    private List<ProductBasket> productBaskets;
    private Business business;
    private ShopClient shopClient;
    private ServiceClient serviceClient;
    private CompleteOrder completeOrder;

}


