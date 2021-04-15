package ShopAppBackend.Invoice;


import ShopAppBackend.ArtcilesOnInvoice.ArticlesOnInvoice;
import ShopAppBackend.Business.Business;
import ShopAppBackend.ServiceClient.ServiceClient.ServiceClient;
import ShopAppBackend.ServiceClient.ShopClient.ShopClient;
import ShopAppBackend.CompleteOrder.CompleteOrder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "invoices")
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shop_client")
    private ShopClient shopClient;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "service_client")
    private ServiceClient serviceClient;


    private String recipient;
    private String payForm;
    private String date;
    private String paid;
    private String spendFromStock;
    private String paymentDeadline;
    private double sumVatValue;
    private double sumNettoValue;
    private double sumBruttoValue;
    private String invoicePath;

    @OneToMany
    private List<ArticlesOnInvoice> articles;


    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "completeorder_id")
    private CompleteOrder completeOrder;


}
