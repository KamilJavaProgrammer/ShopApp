package ShopAppBackend.CompleteOrder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ShopAppBackend.Adress.Address;
import ShopAppBackend.ServiceClient.ShopClient.ShopClient;
import ShopAppBackend.Invoice.Invoice;
import ShopAppBackend.ProductBasket.ProductBasket;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Data

public class CompleteOrder implements Comparable<CompleteOrder> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deliveryOption;
    private String paymentMethod;
    private boolean invoice;
    private double sumPaid;
    private String state;
    private String date;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ShopClient shopclient;


    @OneToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoiceVat;


    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany
    private List<ProductBasket> productsBasket;

    @Transient
    private String token;

    public CompleteOrder() {
    }

    public int compareToDate(CompleteOrder o) {
      return this.date.compareTo(o.date);
    }

    @Override
    public int compareTo(CompleteOrder o) {
        return 0;
    }
}
