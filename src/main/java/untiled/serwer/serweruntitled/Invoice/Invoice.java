package untiled.serwer.serweruntitled.Invoice;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import untiled.serwer.serweruntitled.ArtcilesOnInvoice.ArticlesOnInvoice;
import untiled.serwer.serweruntitled.Business.Business;
import untiled.serwer.serweruntitled.Client.Client;
import untiled.serwer.serweruntitled.Client.ServiceClient.ServiceClient;
import untiled.serwer.serweruntitled.Client.ShopClient.ShopClient;
import untiled.serwer.serweruntitled.CompleteOrder.CompleteOrder;

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
