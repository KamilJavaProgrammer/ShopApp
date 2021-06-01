package ShopAppBackend.DTOs;

import ShopAppBackend.Entities.Business;
import ShopAppBackend.Entities.CompleteOrder;
import ShopAppBackend.Entities.ProductBasket;
import ShopAppBackend.Entities.ServiceClient;
import ShopAppBackend.Entities.ShopClient;
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


